package org.springblade.system.user.entity;

import lombok.Data;

/**
 * 微信开放平台
 */
@Data
public class WxOpenParam {
	String type;//register=注册   login=登录
	String uuid;//唯一id
	String phone;//手机号码

	//注册
	String inviteCode;
}
