/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.auth.granter;


import lombok.AllArgsConstructor;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.user.entity.*;
import org.springblade.system.user.feign.IMjkjUserClient;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.stereotype.Component;

/**
 * 登录
 */
@Component
public class WxMiniTokenGranter implements ITokenGranter {
	public static final String GRANT_TYPE = "wxmini";
	private static final Integer AUTH_SUCCESS_CODE = 2000;

	private final IUserClient userClient;

	private IMjkjUserClient mjkjUserClient;

    public WxMiniTokenGranter(IUserClient userClient,IMjkjUserClient mjkjUserClient) {
        this.userClient = userClient;
		this.mjkjUserClient = mjkjUserClient;
    }

    @Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String wxCode = tokenParameter.getArgs().getStr("wxCode");//微信-code
		String wxName = tokenParameter.getArgs().getStr("wxName");//微信-wxName
		String wxAvatar = tokenParameter.getArgs().getStr("wxAvatar");//微信-code
		String inviteCode = tokenParameter.getArgs().getStr("invite_code");//邀请码
		String loginType = tokenParameter.getArgs().getStr("login_type");//登录类型 wx=微信小程序   phone=手机号码登录 wxopen_qrcode=微信开放平台 //wx_app=app微信登录
		String phone = tokenParameter.getArgs().getStr("phone");//手机号码
		String phoneCode = tokenParameter.getArgs().getStr("phoneCode");//手机-code
		String uuid = tokenParameter.getArgs().getStr("uuid");
		String password = tokenParameter.getArgs().getStr("password");
		if(Func.isEmpty(loginType)){
			loginType="wx";
		}
		//登录人id
		Long userId = AuthUtil.getUserId();
		R<UserInfo> userInfoR =null;
		if(Func.isNotEmpty(userId) && userId!=-1){//当前人是登录状态
			String userAccount = AuthUtil.getUserAccount();
			String tenantId = AuthUtil.getTenantId();
			userInfoR = userClient.userInfo(tenantId, userAccount,password);
		}
		if(Func.isEmpty(userInfoR)){//登录-------------
			WxUserParam wxUserParam=new WxUserParam();
			wxUserParam.setCode(wxCode);
			wxUserParam.setWxName(wxName);
			wxUserParam.setWxAvatar(wxAvatar);
			wxUserParam.setInviteCode(inviteCode);
			wxUserParam.setPhone(phone);
			 if(Func.equals(loginType,"phone")){//手机号码登录
				SmsCodeParam codeParam=new SmsCodeParam();
				codeParam.setPhone(phone);
				codeParam.setCode(phoneCode);
				R<Boolean> checkPhoneR = mjkjUserClient.checkPhone(codeParam);
				if(Func.isEmpty(checkPhoneR)){
					throw new ServiceException("验证码不正确");
				}
				Boolean data = checkPhoneR.getData();
				if(!data){
					throw new ServiceException("验证码不正确");
				}
				wxUserParam=new WxUserParam();
				wxUserParam.setCode(phoneCode);
				wxUserParam.setPhone(phone);
				wxUserParam.setInviteCode(inviteCode);
				userInfoR = mjkjUserClient.phoneLogin(wxUserParam);
			}
			else{
				throw new ServiceException("登录类型有误");
			}
		}

			return userInfoR.getData();

		// 远程调用，获取认证信息

	}
}
