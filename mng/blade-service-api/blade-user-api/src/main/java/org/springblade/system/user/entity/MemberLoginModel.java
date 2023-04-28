package org.springblade.system.user.entity;

import lombok.Data;

@Data
public class MemberLoginModel {
	String email;//邮箱登录
	String phone;//手机登录
	String googleCode;//谷歌验证码
	String phoneCode;//手机验证码
	String emailCode;//邮箱验证码
	String password;//密码
	String areaCode;//区号
	String step;//步骤
	String deviceId;//设备Id
	String ip;
	String level;
	String pid;

	private String InvitationCode;
}
