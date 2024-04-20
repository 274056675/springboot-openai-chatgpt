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


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.user.entity.SmsCodeParam;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.WxOpenParam;
import org.springblade.system.user.entity.WxUserParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME
)
public interface IMjkjUserClient {

	String API_PREFIX = "/client";
	String ADMINISTRATOR_SWITCH_ROLES = API_PREFIX + "/administrator-switch-roles";
	String PHONE_LOGIN = API_PREFIX + "/phone-login";//手机号码登录
	String PHONE_CHECK_CODE = API_PREFIX + "/phone-check-code";//校验手机验证码


	/**
	 * 管理员切换其他租户
	 */
	@PostMapping(ADMINISTRATOR_SWITCH_ROLES)
    R<UserInfo> administratorSwitchRoles(@RequestParam("tenantId") String tenantId);

	/**
	 * 手机号码登录
	 */
	@PostMapping(PHONE_LOGIN)
	R<UserInfo> phoneLogin(@RequestBody WxUserParam param);

	@PostMapping(PHONE_CHECK_CODE)
	R<Boolean> checkPhone(@RequestBody SmsCodeParam param);


}
