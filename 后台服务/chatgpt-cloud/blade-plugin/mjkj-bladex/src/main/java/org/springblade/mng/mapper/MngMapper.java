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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *后台相关
 */
public interface MngMapper {

	/**
	 * 聊天记录
	 * @return
	 */
	IPage<Map<String, Object>> getMessageHistoryList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 更多好玩-聊天记录
	 * @return
	 */
	IPage<Map<String, Object>> getMessageMoreFunHistoryList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 十牛 - 更多好玩-聊天记录
	 * @return
	 */
	IPage<Map<String, Object>> getShiniuMessageMoreFunHistoryList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 十牛 - 更多好玩-聊天记录2
	 * @param page
	 * @param params
	 * @return
	 */
	IPage getShiniuMessageMoreFunHistoryListTwo(Page page, Map<String, Object> params);

	/**
	 * 获取用户列表
	 * @return
	 */
	IPage<Map<String, Object>> getWxUserList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	//获取最近三十天的数据-提问次数
	List<Map<String,Object>> getMessageCouList();

	//获取最近三十天的数据-注册人数
	List<Map<String,Object>> getRegisterCouList();

	//获取最近三十天的数据-使用人数
	List<Map<String,Object>> getUseWuserCouList();

	//获取最近三十天的数据-分享次数
	List<Map<String,Object>> getShareCouList();

	//获取最近三十天的数据-签到次数
	List<Map<String,Object>> getSignCouList();

	//获取最近三十天的数据-文件下载次数
	List<Map<String,Object>> getFileCouList();

	/**
	 * 分享记录
	 * @return
	 */
	IPage<Map<String, Object>> getShareHistoryList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 消息次数详情
	 * @return
	 */
	IPage<Map<String, Object>> getNumLogList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 今日统计
	 * @return
	 */
	IPage<Map<String, Object>> getTodayCountList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 佣金记录
	 * @return
	 */
	IPage<Map<String, Object>> getCommissionLogList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 钱包记录
	 * @return
	 */
	IPage<Map<String, Object>> getWalletHistoryLogList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 订单列表
	 * @return
	 */
	IPage<Map<String, Object>> getOrderList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);


	/**
	 * 绘图记录
	 * @return
	 */
	IPage<Map<String, Object>> getViewImageList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

	/**
	 * 绘图记录-待审核
	 * @return
	 */
	IPage<Map<String, Object>> getViewImageTodoList(@Param("page") IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

}
