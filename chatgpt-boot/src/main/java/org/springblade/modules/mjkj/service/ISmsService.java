package org.springblade.modules.mjkj.service;

/**
 * 短信相关
 */
public interface ISmsService {

	//发送短信验证码
	boolean sendSms(String phone, String code);

}
