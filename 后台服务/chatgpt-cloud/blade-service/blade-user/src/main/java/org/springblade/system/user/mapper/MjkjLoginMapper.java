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
package org.springblade.system.user.mapper;

import java.util.List;
import java.util.Map;

/**
 * 魔晶登录
 */
public interface MjkjLoginMapper{

	Map<String,Object> getAdministrator(String tenantId);

	Long getTopDeptd(String tenantId);
	Long getTopRoleId(String tenantId);
	Long getTopPostId(String tenantId);

	/**
	 * 公共新增接口
	 *
	 * @param map 一定包含 select_sql 字段
	 * @return
	 */
	Long baseIntegerSql(Map<String, Object> map);

	//公共修改接口
	void baseUpdateSql(Map<String, Object> map);

	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleAliases(Long[] ids);

	/**
	 * 根据openId 获取用户id
	 * @param openId
	 * @return
	 */
	Long getBladeUserIdByOpenId(String openId);

	//根据手机号码登录
	Long getBladeUserIdByPhone(String phone);

	//获取微信用户id
	String getWxUserId(Long bladeUserId);

	//获取微信用户id
	String getWxUserIdByUnioId(String unionId);
	//获取用户di
	Long getBladeUserIdByUnioId(String unionId);

	Long getBladeUserIdByUUID(String uuid);

	Long getBladeUserIdByOpenid(String openid);
}
