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
package org.springblade.mng.cgform.mapper;

import org.springblade.mng.cgform.model.KvModel;

import java.util.List;
import java.util.Map;

/**
 * 公共sql
 */
public interface BaseSqlMapper {

	int getProcessingProgressTotal(String tableName,String batchCode);

	List<Long> getAdminIdList();

	//查询公共接口
	List<Map<String, Object>> baseSelectSqlList(Map<String, Object> map);

	//公共新增接口
	Long baseIntegerSql(Map<String, Object> map);

	//公共修改接口
	void baseUpdateSql(Map<String, Object> map);

	//公共删除接口
	void baseDeleteSql(Map<String, Object> map);

	//查询一列
	List<String> baseSelectByOneColumnSql(Map<String, Object> map);

	//获取模型列表
	List<KvModel> getKvModelList(Map<String, Object> map);

	//查询数据字典
	String getSysDictItem(Long sysDictId, Integer type);

}
