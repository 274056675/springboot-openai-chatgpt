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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springblade.mng.cgform.entity.CgformEnhanceJava;
import org.springblade.mng.cgform.entity.CgformEnhanceSql;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.mapper.CgformEnhanceSqlMapper;
import org.springblade.mng.cgform.mapper.SqlMapper;
import org.springblade.mng.cgform.model.AccumulatorRecursiveActionParam;
import org.springblade.mng.cgform.model.CgformEnhanceJavaBatchInter;
import org.springblade.mng.cgform.service.IAsyncPoolService;
import org.springblade.mng.cgform.service.ICgformEnhanceJavaService;
import org.springblade.mng.cgform.service.ICgformEnhanceSqlService;
import org.springblade.mng.cgform.service.ICgformFieldService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.constant.MjkjConstant;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.mng.config.pool.AccumulatorRecursiveAction;
import org.springblade.mng.config.util.SqlSymbolUtil;
import org.springblade.mng.config.util.converter.ConverterUtil;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * sql增强 服务实现类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Service
@Slf4j
public class CgformEnhanceSqlServiceImpl extends BaseServiceImpl<CgformEnhanceSqlMapper, CgformEnhanceSql> implements ICgformEnhanceSqlService {


	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;


	@Autowired
	private ICgformEnhanceJavaService javaService;

	@Autowired
	private SqlMapper sqlMapper;

	@Autowired
	private IAsyncPoolService asyncPoolService;

	@Autowired
	private ICgformFieldService cgformFieldService;

	/**
	 * 执行sql增强 ok
	 *
	 * @param buttonCode
	 * @param headId
	 * @param json
	 */
	@Override
	public void executeEnhanceSqlUpdate(String buttonCode, Long headId, JSONObject json) {
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, headId);
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}

		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return;
		}
		String cgbSql =enhanceSql.getCgbSql();
		BladeUser user = AuthUtil.getUser();
		if(Func.isNotEmpty(user)){
			cgbSql = cgbSql.replace("#{default_update_user}", Func.toStr(user.getUserId()));
		}
		cgbSql = cgbSql.replace("#{default_update_time}", DateUtil.format(DateUtil.now(),DateUtil.PATTERN_DATETIME));


		String enhanceSqlStr = SqlSymbolUtil.enhanceSql(cgbSql, json);
		String[] enhanceSqls = enhanceSqlStr.split(";");

		for (String ddl : enhanceSqls) {
			if(Func.isEmpty(ddl) || ddl.toLowerCase().trim().equals("")){
				continue;
			}
			baseMapper.executeDDL(ddl);
		}

	}

	/**
	 * 新增 ok
	 * @param buttonCode
	 * @param headId
	 * @param json
	 */
	@Override
	public void executeEnhanceSqlInsert(String buttonCode, Long headId, JSONObject json) {
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, headId);
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}
		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return;
		}
		String cgbSql =enhanceSql.getCgbSql();
		cgbSql = cgbSql.replace("#{default_id}", IdWorker.getId()+"");//id 默认值
		cgbSql = cgbSql.replace("#{default_pid}",json.getString("id"));
		cgbSql = cgbSql.replace("#{default_uuid}",Func.randomUUID());
		BladeUser user = AuthUtil.getUser();

		if(Func.isNotEmpty(user)){
			cgbSql = cgbSql.replace("#{default_tenant_id}", user.getTenantId());
			cgbSql = cgbSql.replace("#{default_create_user}", Func.toStr(user.getUserId()));
			cgbSql = cgbSql.replace("#{default_update_user}", Func.toStr(user.getUserId()));
			String deptIdStr = user.getDeptId();
			List<Long> deptIdList = Func.toLongList(deptIdStr);
			cgbSql = cgbSql.replace("#{default_create_dept}", Func.toStr(deptIdList.get(0)));//替换值
		}
		cgbSql = cgbSql.replace("#{default_create_time}", DateUtil.format(DateUtil.now(),DateUtil.PATTERN_DATETIME));
		cgbSql = cgbSql.replace("#{default_update_time}", DateUtil.format(DateUtil.now(),DateUtil.PATTERN_DATETIME));

		cgbSql = cgbSql.replace("#{status}", "0");//替换值
		cgbSql = cgbSql.replace("#{is_deleted}", "0");//替换值
		String enhanceSqlStr = SqlSymbolUtil.enhanceSql(cgbSql, json);
		String[] enhanceSqls = enhanceSqlStr.split(";");

		for (String ddl : enhanceSqls) {
			if(Func.isEmpty(ddl) || ddl.toLowerCase().trim().equals("")){
				continue;
			}
			baseMapper.executeDDL(ddl);
		}

	}

	/**
	 * sql增强详情 #{ID} ok
	 * @param head
	 * @param buttonCode
	 * @param resultMap
	 * @param id
	 */
	@Override
	public Map<String, Object> executeEnhanceSqlDetail(CgformHead head, String buttonCode, Map<String, Object> resultMap, String id){
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, head.getId());
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}

		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return resultMap;
		}
		//存在增强
		String cgbSql = enhanceSql.getCgbSql();
		String enhanceSqlStr = cgbSql.replace("#{id}", "'"+id+"'");//替换值
		String[] enhanceSqls = enhanceSqlStr.split(";");
		String selectSql = enhanceSqls[0];
		List<Map<String, Object>> listData = baseMapper.getListData(selectSql);

		if(Func.isNotEmpty(listData)){
			resultMap= listData.get(0);
		}
		return resultMap;
	}

	//sql增强 列表 ok
	@Override
	public void executeEnhanceSqlList(CgformHead head, String buttonCode,  Map<String, Object> params){
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, head.getId());
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}

		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return;
		}

		LambdaQueryWrapper<CgformField> fieldWrapper = new LambdaQueryWrapper<>();
		fieldWrapper.eq(CgformField::getCgformHeadId, head.getId());
		fieldWrapper.orderByAsc(CgformField::getOrderNum);
		List<CgformField> fieldList = cgformFieldService.list(fieldWrapper);

		// 根据数据库类型制造额外SQL
		String cgbSql = enhanceSql.getCgbSql();
		String sql = SqlSymbolUtil.getEnhanceByDataType(fieldList, params, null);

		if(cgbSql.contains("#{online_user_id}")){
			cgbSql = cgbSql.replace("#{online_user_id}", "'"+AuthUtil.getUserId()+"'");//id 默认值
		}


		String executeSql =cgbSql.replaceAll("\\$\\{mjkj_where}",sql);

		//分页参数
		Page page = MjkjUtils.getPage(params);

		params.put("executeSql",executeSql);
		Page pages = baseMapper.executeSql(page,executeSql,params);
		MjkjUtils.setPageResult(params, pages);
	}



	@Override
	public void saveBatchCodeOnlineTable(CgformHead head, List<CgformField> fieldList, List<Map<String, Object>> dataList, String batchCode, Integer totalNum, SqlMapper sqlMapper, Map<String, Object> otherMap, CgformEnhanceJavaBatchInter enhanceJava, ServletRequestAttributes sra) throws BusinessException {
		ConverterUtil.converter(2, dataList, fieldList);
		asyncPoolService.executeBatchSql(dataList, head, fieldList, sqlMapper, batchCode, totalNum, otherMap,enhanceJava,sra);
	}


	private void executeSql(String jsonStr, CgformHead head, List<CgformField> fieldList, SqlMapper sqlMapper) throws BusinessException {
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		int cou = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_IMPORT, MjkjConstant.ENHANCE_START, head, jsonObject);//获取sql增强
		String tableName = head.getTableName();
		if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
			if (1 == cou) {
				Map<String, Object> map = SqlSymbolUtil.getInsertSql(tableName, fieldList, jsonObject);
				sqlMapper.executeInsertSQL(map);
			} else if (2 == cou) {
				Map<String, Object> map = SqlSymbolUtil.getUpdateSql(tableName, fieldList, jsonObject);
				sqlMapper.executeUpdatetSQL(map);
			}
		}
	}

	//批量添加
	private void executeBatchSql(String jsonStr, CgformHead head, List<CgformField> fieldList, SqlMapper sqlMapper,String batchCode) throws BusinessException {
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		int cou = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_IMPORT, MjkjConstant.ENHANCE_START, head, jsonObject);//获取sql增强
		String tableName = head.getTableName();
		jsonObject.put("batch_code",batchCode);
		if (1 == cou) {
			Map<String, Object> map = SqlSymbolUtil.getInsertSql(tableName, fieldList, jsonObject);
			sqlMapper.executeInsertSQL(map);
		} else if (2 == cou) {
			Map<String, Object> map = SqlSymbolUtil.getUpdateSql(tableName, fieldList, jsonObject);
			sqlMapper.executeUpdatetSQL(map);
		}

	}


}
