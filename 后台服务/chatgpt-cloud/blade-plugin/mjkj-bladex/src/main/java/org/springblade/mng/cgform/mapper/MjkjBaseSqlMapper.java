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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.springblade.mng.cgform.model.KvModel;

import java.util.List;
import java.util.Map;

/**
 * 魔晶公共定制
 */
public interface MjkjBaseSqlMapper {

	List<Map<String, Object>> getDataByTable(String selectField,String tableName);

	//根据id获取某一个表的一条数据
	Map<String, Object> getTableById(String selectField,String tableName, String id);
	Map<String, Object> getTableByIdL(String selectField,String tableName, Long id);

	//根据id获取某一个表的一条数据 进行过滤
	List<Map<String, Object>> getDataListByField(String selectField,String tableName, String whereFieldName, Object whereFieldValue);

	//根据id获取某一个表的一条数据 进行删除过滤 排序
	List<Map<String, Object>> getDataListByFieldOrderBy(String selectField,String tableName, String whereFieldName, Object whereFieldValue,String orderByField,String orderStr);

	//根据id获取某一个表的一条数据 type=A:全部  L：左边模糊  R右模糊
	List<Map<String, Object>> getDataListByLike(String selectField,String tableName, String whereFieldName, Object whereFieldValue,String type);

	List<Map<String, Object>> getDataListByIn(String selectField,String tableName, String whereFieldName, List<Object> whereFieldValueList);

	List<KvModel> getSysDictItemList(String dictCode);

	//获取数字字典值
	String getSysDictItemValue(String dictCode,String itemValue);

	String getSysDictItemValueByText(String dictCode,String itemText);


	//公共新增
	Long baseInsertSql(Map<String, Object> map);

	//公共更新
	void baseUpdateSql(Map<String, Object> map);

	//公共删除
	Integer baseDeleteSqlStr(String tableName, String id);
	Integer baseDeleteSql(String tableName, Long id);
	//真实删除
	Integer baseRealDeleteSql(String tableName, Long id);

	//自定义公共删除
	Integer baseZdyDeleteSql(String tableName, String whereFieldName, Long id);
	//自定义公共删除 string
	Integer baseZdyDeleteSql(String tableName, String whereFieldName, String id);

	List<Long> getAdminIdList();

	int getProcessingProgress(String tableName,String batchCode);

	int getProcessingProgressTotal(String tableName,String batchCode);

	List<Map<String,Object>> getBladeUserOauth(String uuid,String source);

	//获取授权
	List<Map<String,Object>> getBladeUserOauthByUserId(Long userId);
	//解绑
	void unBindBladeUserOauth(Long id);

	// 根据表属性获取所有数据 多条件
	<T> List<Map<String, T>> getDataListByWrapper(String tableName,@Param(Constants.WRAPPER) Wrapper<T> wrapper);

	// 根据表属性获取所有数据 多条件 分页
	<T> IPage<Map<String, T>> getDataListByWrapper(String tableName, IPage page, @Param(Constants.WRAPPER) Wrapper<T> wrapper);

	//本次定制
	//获取所有全仓借款用户
	List<String> getAllBorrowMemberId();

	//获取所有全仓用户
	List<String> getAllContractMemberId();

	//获取我的全仓交易对
	List<String> getMyAllContractSymbolName(String memberId);

}
