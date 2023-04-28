package org.springblade.system.user.entity;

import lombok.Data;

/**
 * 短信验证码相关
 */
@Data
public class SmsCodeParam {
	String code;//验证码
	String phone;//手机号码
}
