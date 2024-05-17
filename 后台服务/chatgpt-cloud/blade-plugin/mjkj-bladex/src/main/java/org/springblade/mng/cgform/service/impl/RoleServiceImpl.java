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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.mng.cgform.entity.ChatgptBladeRole;
import org.springblade.mng.cgform.mapper.RoleMapper;
import org.springblade.mng.cgform.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, ChatgptBladeRole> implements IRoleService {


	@Override
	public List<ChatgptBladeRole> getRoleListByAlias(String tenantId, String alias){
		LambdaQueryWrapper<ChatgptBladeRole> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(ChatgptBladeRole::getTenantId,tenantId);
		wrapper.eq(ChatgptBladeRole::getRoleAlias,alias);
		List<ChatgptBladeRole> chatgptBladeRoleList = this.list(wrapper);
		return chatgptBladeRoleList;
	}
}
