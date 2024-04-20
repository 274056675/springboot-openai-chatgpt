package org.springblade.mng.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.config.constant.ChatgptConfig;
import org.springblade.mng.config.constant.MjkjSmsConfig;

import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 短信相关 相关
 */

@Service
@Slf4j
public class SmsServiceImpl implements ISmsService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private RedisUtil redisUtil;

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


			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", MjkjSmsConfig.getAccessKey(), MjkjSmsConfig.getSecretKey());
			IAcsClient acsClient = new DefaultAcsClient(profile);

			SendSmsRequest request = new SendSmsRequest();
			request.setPhoneNumbers(phone);
			request.setSignName(MjkjSmsConfig.getSignName());
			request.setTemplateCode(MjkjSmsConfig.getTemplateId());
			request.setTemplateParam(JsonUtil.toJson(params));
			SendSmsResponse sendSmsResponse = null;

			String sendResult="";
			boolean flag =false;
			if(Func.equals(ChatgptConfig.getDebug(),"true")){//调试
				sendSmsResponse = new SendSmsResponse();
				sendSmsResponse.setCode("OK");
				sendSmsResponse.setMessage("test");
				flag = true;
				sendResult=flag?"成功":"失败";
			}else{
				sendSmsResponse = acsClient.getAcsResponse(request);
				if ((sendSmsResponse.getCode() != null) && (sendSmsResponse.getCode().equals("OK"))) {
					flag = true;
				} else {
					flag = false;
				}

			}

			//保存日志
			Date now = DateUtil.now();
			Map<String,Object> addMap=new HashMap<>();
			addMap.put("phone",phone);
			addMap.put("code",code);
			addMap.put("send_time",now);
			addMap.put("send_result",sendResult);
			addMap.put("remark",sendSmsResponse.getMessage());
			baseSqlService.baseInsertData("chat_log_sms",addMap);
			//写入redis
			String redisKey = this.getRedisKey(phone);
			if(flag){
				redisUtil.set(redisKey,code,300L, TimeUnit.SECONDS);//5分钟
			}

			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
