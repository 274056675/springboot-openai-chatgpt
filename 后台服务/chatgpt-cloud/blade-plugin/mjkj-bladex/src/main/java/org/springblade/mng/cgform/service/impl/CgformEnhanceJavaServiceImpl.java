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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.mng.cgform.entity.CgformEnhanceJava;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.mapper.CgformEnhanceJavaMapper;
import org.springblade.mng.cgform.model.CgformEnhanceJavaInter;
import org.springblade.mng.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.mng.config.exception.BusinessException;

import org.springblade.mng.config.util.MyClassLoader;
import org.springblade.mng.config.util.SpringContextUtils;

import org.springblade.mng.cgform.service.ICgformEnhanceJavaService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.config.util.ConvertUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * java增强 服务实现类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Service
public class CgformEnhanceJavaServiceImpl extends BaseServiceImpl<CgformEnhanceJavaMapper, CgformEnhanceJava> implements ICgformEnhanceJavaService {


	@Override
	public boolean checkOnlyEnhance(CgformEnhanceJava onlcgformenhancejava) {
		LambdaQueryWrapper<CgformEnhanceJava> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJava::getButtonCode, onlcgformenhancejava.getButtonCode());
		wrapper.eq(CgformEnhanceJava::getCgformHeadId, onlcgformenhancejava.getCgformHeadId());

		List<CgformEnhanceJava> enhanceJavaList = baseMapper.selectList(wrapper);
		if (Func.isEmpty(enhanceJavaList)) {//没有增强
			return true;
		}
		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceJava enhanceJava = null;
		for (CgformEnhanceJava enhance : enhanceJavaList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceJava = enhance;
				break;
			}
		}
		if (Func.isEmpty(enhanceJava)) {
			return true;
		}
		if (Func.isNotEmpty(enhanceJava) && Func.isEmpty(onlcgformenhancejava.getId())) {
			return false;
		}

		return true;
	}

	//java增强 ok
	@Override
	public CgformEnhanceJava getExecuteEnhanceJavaBatch(String buttonCode, String eventType, CgformHead head) throws BusinessException {
		LambdaQueryWrapper<CgformEnhanceJava> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJava::getActiveStatus, "1");
		wrapper.eq(CgformEnhanceJava::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceJava::getCgformHeadId, head.getId());
		wrapper.eq(CgformEnhanceJava::getEvent, eventType);

		List<CgformEnhanceJava> enhanceJavaList = baseMapper.selectList(wrapper);
		if (Func.isEmpty(enhanceJavaList)) {//没有增强
			return null;
		}
		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceJava enhanceJava = null;
		for (CgformEnhanceJava enhance : enhanceJavaList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceJava = enhance;
				break;
			}
		}

		return enhanceJava;
	}

	//java增强 ok
	@Override
	public int executeEnhanceJava(String buttonCode, String eventType, CgformHead head, JSONObject json) throws BusinessException {
		LambdaQueryWrapper<CgformEnhanceJava> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJava::getActiveStatus, "1");
		wrapper.eq(CgformEnhanceJava::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceJava::getCgformHeadId, head.getId());
		wrapper.eq(CgformEnhanceJava::getEvent, eventType);

		List<CgformEnhanceJava> enhanceJavaList = baseMapper.selectList(wrapper);
		if (Func.isEmpty(enhanceJavaList)) {//没有增强
			return 1;
		}
		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceJava enhanceJava = null;
		for (CgformEnhanceJava enhance : enhanceJavaList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceJava = enhance;
				break;
			}
		}
		if (Func.isEmpty(enhanceJava)) {
			return 1;
		}


		Object obj = this.getEnhanceJavaObj(enhanceJava);//获取要执行的对象
		if (obj != null && obj instanceof CgformEnhanceJavaInter) {//参考
			CgformEnhanceJavaInter enhanceJavaInter = (CgformEnhanceJavaInter) obj;
			return enhanceJavaInter.execute(head, json);
		} else {
			return 1;
		}
	}


	//ok
	public void executeEnhanceList(CgformHead head, String buttonCode, List<Map<String, Object>> dataList, Map<String, Object> params) throws BusinessException {
		LambdaQueryWrapper<CgformEnhanceJava> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJava::getActiveStatus, "1");
		wrapper.eq(CgformEnhanceJava::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceJava::getCgformHeadId, head.getId());


		List<CgformEnhanceJava> enhanceJavaList = baseMapper.selectList(wrapper);
		if (Func.isEmpty(enhanceJavaList)) {//没有增强
			return;
		}
		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceJava enhanceJava = null;
		for (CgformEnhanceJava enhance : enhanceJavaList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceJava = enhance;
				break;
			}
		}
		if (Func.isEmpty(enhanceJava)) {
			return;
		}

		Object obj = this.getEnhanceJavaObj(enhanceJava);//获取要执行的对象
		if (obj != null && obj instanceof CgformEnhanceJavaListInter) {
			CgformEnhanceJavaListInter listInter = (CgformEnhanceJavaListInter) obj;
			listInter.execute(head.getTableName(), head.getTenantId(), dataList, params);
		}

	}

	/***
	 * 详情增强 ok
	 * @param head
	 * @param buttonCode
	 * @param data
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> executeEnhanceDetail(CgformHead head, String buttonCode, Map<String, Object> data, Map<String, Object> params) throws BusinessException {
		LambdaQueryWrapper<CgformEnhanceJava> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJava::getActiveStatus, "1");
		wrapper.eq(CgformEnhanceJava::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceJava::getCgformHeadId, head.getId());

		List<CgformEnhanceJava> enhanceJavaList = baseMapper.selectList(wrapper);
		if (Func.isEmpty(enhanceJavaList)) {//没有增强
			return data;
		}
		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceJava enhanceJava = null;
		for (CgformEnhanceJava enhance : enhanceJavaList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceJava = enhance;
				break;
			}
		}
		if (Func.isEmpty(enhanceJava)) {
			return data;
		}

		List<Map<String, Object>> dataList = new ArrayList<>();
		dataList.add(data);
		Object obj = this.getEnhanceJavaObj(enhanceJava);//获取要执行的对象
		if (obj != null && obj instanceof CgformEnhanceJavaListInter) {
			CgformEnhanceJavaListInter listInter = (CgformEnhanceJavaListInter) obj;
			listInter.execute(head.getTableName(), head.getTenantId(), dataList, params);
		}
		if (Func.isNotEmpty(dataList)) {
			return dataList.get(0);
		}
		return data;
	}

	/**
	 * 获取java增强对象
	 *
	 * @param enhanceJava
	 * @return
	 */
	@Override
	public Object getEnhanceJavaObj(CgformEnhanceJava enhanceJava) {
		if (Func.isEmpty(enhanceJava)) {
			return null;
		}
		String javaType = enhanceJava.getCgJavaType();//类型
		String javaValue = enhanceJava.getCgJavaValue();//内容
		if (ConvertUtils.isNotEmpty(javaValue)) {
			Object resultObject = null;
			if ("class".equals(javaType)) {
				try {
					resultObject = MyClassLoader.getClassByScn(javaValue).newInstance();
				} catch (InstantiationException e) {
					log.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				}
			} else if ("spring".equals(javaType)) {
				resultObject = SpringContextUtils.getBean(javaValue);
			}

			return resultObject;
		}
		return null;
	}

}
