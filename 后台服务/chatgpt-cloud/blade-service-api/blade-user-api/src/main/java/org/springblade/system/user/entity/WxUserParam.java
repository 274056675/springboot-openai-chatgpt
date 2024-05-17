package org.springblade.system.user.entity;

import lombok.Data;

/**
 * 微信用户相关
 */
@Data
public class WxUserParam {
	String code;
	String wxName;
	String wxAvatar;
	String inviteCode;//邀请码
	String phone;//手机号码
}
