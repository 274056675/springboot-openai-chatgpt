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
package org.springblade.system.user.service;


import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.WxOpenParam;
import org.springblade.system.user.entity.WxUserParam;

import java.util.Map;

/**
 * 魔晶登录
 */
public interface IMjkjUserService {

	//公共新增
	Long baseSimpleIntegerSql(String tableName, Map<String, Object> dataMap);
	//公共修改
	void baseSimpleUpdaSql(String tableName, Map<String, Object> dataMap,String id);

	//超级管理员切换
	UserInfo administratorSwitchRoles(String tenantId);

	//手机号码登录
	UserInfo phoneLogin(WxUserParam param);


}
