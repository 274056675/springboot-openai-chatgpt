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
package org.springblade.mng.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.mng.cgform.entity.CgformEnhanceJava;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.config.exception.BusinessException;

import org.springblade.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * java增强 服务类
 *
 * @author BladeX
 * @since 2021-05-22
 */
public interface ICgformEnhanceJavaService extends BaseService<CgformEnhanceJava> {

	/**
	 * 校验
	 * @param onlcgformenhancejava
	 * @return
	 */
	boolean checkOnlyEnhance(CgformEnhanceJava onlcgformenhancejava);

	CgformEnhanceJava getExecuteEnhanceJavaBatch(String buttonCode, String eventType, CgformHead onlcgformhead)
		throws BusinessException;
	/**
	 * 执行java增强对象
	 * @param buttonCode
	 * @param eventType
	 * @param onlcgformhead
	 * @param jsonobject
	 * @return
	 * @throws BusinessException
	 */
	int executeEnhanceJava(String buttonCode, String eventType, CgformHead onlcgformhead, JSONObject jsonobject)
			throws BusinessException;

	/**
	 * 执行java增强列表
	 * @param onlcgformhead
	 * @param buttonCode
	 * @param list
	 * @throws BusinessException
	 */
	void executeEnhanceList(CgformHead onlcgformhead, String buttonCode, List<Map<String, Object>> list, Map<String, Object> params)
			throws BusinessException;


	/**
	 * 执行java增强详情
	 * @param onlcgformhead
	 * @param buttonCode
	 * @param
	 * @throws BusinessException
	 */
	Map<String,Object> executeEnhanceDetail(CgformHead onlcgformhead, String buttonCode, Map<String, Object> data, Map<String, Object> params)
			throws BusinessException;

	 Object getEnhanceJavaObj(CgformEnhanceJava enhanceJava);
}
