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
package org.springblade.system.user.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.system.user.entity.SmsCodeParam;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.WxOpenParam;
import org.springblade.system.user.entity.WxUserParam;
import org.springblade.system.user.service.IMjkjUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务Feign实现类
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
public class MjkjUserClient implements IMjkjUserClient {

	private IMjkjUserService mjkjLoginService;

	private RedisUtil redisUtil;
	/**
	 * 管理员切换其他租户
	 */
	@Override
	public R<UserInfo> administratorSwitchRoles(String tenantId){
		UserInfo userInfo = mjkjLoginService.administratorSwitchRoles(tenantId);
		return R.data(userInfo);
	}


	/**
	 * 手机号码登录
	 */
	@Override
	public R<UserInfo> phoneLogin(WxUserParam param){
		UserInfo userInfo = mjkjLoginService.phoneLogin(param);
		return R.data(userInfo);
	}

	@Override
	public R<Boolean> checkPhone(@RequestBody SmsCodeParam param){
		String phone = param.getPhone();
		String code = param.getCode();
		if(Func.equals(phone,"13800138000")){//苹果审核
			return R.data(true);
		}
		String redisKey="SMS_PHONE:"+phone;
		if(!redisUtil.hasKey(redisKey)){
			return R.data(false);
		}
		String redisCode = (String) redisUtil.get(redisKey);
		if(Func.equals(code,redisCode)){
			return R.data(true);
		}
		return R.data(false);
	}

}
