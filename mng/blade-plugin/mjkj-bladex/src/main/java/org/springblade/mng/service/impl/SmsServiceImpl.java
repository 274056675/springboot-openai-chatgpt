package org.springblade.mng.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.config.constant.ChatgptConfig;
import org.springblade.config.constant.MjkjSmsConfig;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.sms.AliSmsTemplate;
import org.springblade.core.sms.SmsTemplate;
import org.springblade.core.sms.model.SmsData;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.sms.props.SmsProperties;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 短信相关 相关
 */

@Service
public class SmsServiceImpl implements ISmsService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private BladeRedis bladeRedis;

	private String getRedisKey(String phone){
		String redisKey="SMS_PHONE:"+phone;
		return redisKey;
	}

	//发送短信验证码
	@Override
	public boolean sendSms(String phone,String code){
		try {
			Map<String, String> params = new HashMap<>();
			params.put("code", code);

			String templateId = MjkjSmsConfig.getTemplateId();

			SmsProperties smsProperties = new SmsProperties();
			smsProperties.setTemplateId(templateId.trim());
			smsProperties.setAccessKey(MjkjSmsConfig.getAccessKey());
			smsProperties.setSecretKey(MjkjSmsConfig.getSecretKey());
			smsProperties.setRegionId("cn-hangzhou");
			smsProperties.setSignName(MjkjSmsConfig.getSignName());
			IClientProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKey(), smsProperties.getSecretKey());
			IAcsClient acsClient = new DefaultAcsClient(profile);
			SmsTemplate template = new AliSmsTemplate(smsProperties, acsClient, bladeRedis);


			SmsData smsData = new SmsData(params);
			smsData.setKey(templateId);


			List<String> phones = new ArrayList<>();
			phones.add(phone);

			SmsResponse smsResponse =null;
			String sendResult="";
			boolean flag =false;
			if(Func.equals(ChatgptConfig.getDebug(),"true")){//调试
				smsResponse = new SmsResponse(true,200,null);
				flag = true;
				sendResult=flag?"成功":"失败";
			}else{
				smsResponse = template.sendMessage(smsData, phones);
				flag = smsResponse.isSuccess();
				sendResult=flag?"成功":"失败";
			}

			//保存日志
			Date now = DateUtil.now();
			Map<String,Object> addMap=new HashMap<>();
			addMap.put("phone",phone);
			addMap.put("code",code);
			addMap.put("send_time",now);
			addMap.put("send_result",sendResult);
			addMap.put("remark",smsResponse.getMsg());
			baseSqlService.baseInsertData("chat_log_sms",addMap);
			//写入redis
			String redisKey = this.getRedisKey(phone);
			if(flag){
				bladeRedis.setEx(redisKey,code,300L);//5分钟
			}

			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
