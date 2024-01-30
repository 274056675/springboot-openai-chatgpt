package org.springblade.mng.service.impl;

import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.sms.model.SmsData;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tool.utils.DateUtil;
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



			SmsData smsData = new SmsData(params);

			List<String> phones = new ArrayList<>();
			phones.add(phone);

			SmsResponse smsResponse =null;
			String sendResult="";
			boolean flag =false;
			//调试
			smsResponse = new SmsResponse(true,200,null);
			flag = true;
			sendResult=flag?"成功":"失败";

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
