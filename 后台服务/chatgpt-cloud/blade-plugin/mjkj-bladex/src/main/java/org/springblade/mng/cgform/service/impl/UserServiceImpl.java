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
package org.springblade.mng.cgform.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.mng.cgform.entity.ChatgptBludeUser;
import org.springblade.mng.cgform.mapper.UserMapper;
import org.springblade.mng.cgform.service.IUserService;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, ChatgptBludeUser> implements IUserService {

	/**
	 * 获取部门下的用户
	 * @param tenantId
	 * @param deptId
	 * @return
	 */
	public List<ChatgptBludeUser> getDeptUser(String tenantId, String deptId){
		List<ChatgptBludeUser> deptChatgptBludeUserList = baseMapper.getDeptUser(tenantId, deptId);
		return deptChatgptBludeUserList;
	}

	/**
	 *
	 * 所有用户
	 * @param tenantId
	 * @return
	 */
	public List<ChatgptBludeUser> getAllUser(String tenantId){
		LambdaQueryWrapper<ChatgptBludeUser> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(ChatgptBludeUser::getTenantId,tenantId);
		List<ChatgptBludeUser> list = this.list(wrapper);
		return list;
	}


}
