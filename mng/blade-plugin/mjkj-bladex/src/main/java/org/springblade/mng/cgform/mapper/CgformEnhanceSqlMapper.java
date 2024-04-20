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

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springblade.mng.cgform.entity.CgformEnhanceSql;


import java.util.List;
import java.util.Map;

/**
 * sql增强 Mapper 接口
 *
 * @author BladeX
 * @since 2021-05-22
 */
public interface CgformEnhanceSqlMapper extends BaseMapper<CgformEnhanceSql> {

    void executeDDL(@Param("sqlStr") String sqlStr);

	//查询多条语句
	List<Map<String,Object>> getListData(@Param("sqlStr") String sqlStr);

    //查询多条语句
	Page<Map<String, Object>> getListDataPage(@Param("page") Page<Map<String, Object>> page,Map<String, Object> params, @Param("sqlStr") String sqlStr);

	//执行sql
	Page<Map<String, Object>> executeSql(@Param("page") Page<Map<String, Object>> page,@Param("executeSql") String executeSql,@Param("params")Map<String, Object> params);
}
