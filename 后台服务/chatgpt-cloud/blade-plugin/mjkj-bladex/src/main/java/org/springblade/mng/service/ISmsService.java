package org.springblade.mng.service;

/**
 * 短信相关
 */
public interface ISmsService {

	//发送短信验证码
	boolean sendSms(String phone, String code);

}
