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
package org.springblade.mng.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 *后台相关
 */
public interface WebMapper {

	//获使用最小次数
	Map<String,Object> getMinAccountCou();

	//获取消息次数
	Integer getMessageCou(String wxuserId);

	//获取热门消息
	IPage<Map<String, Object>> getMessageHotList(IPage page);

	//获取历史聊天记录
	IPage<Map<String, Object>>  getSubCouList(String wxuserId, IPage<Object> page);
}
