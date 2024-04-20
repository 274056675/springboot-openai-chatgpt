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

import org.springblade.mng.cgform.entity.SysDict;
import org.springblade.mng.cgform.model.DictModel;
import org.springblade.mng.cgform.model.DuplicateCheckVo;
import org.springblade.mng.cgform.model.TreeSelectModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据字典 Mapper 接口
 *
 * @author BladeX
 * @since 2021-05-27
 */
public interface DictMapper extends BaseMapper<SysDict> {

     List<DictModel> queryTableDictItemsByCode(@Param("table") String table, @Param("text") String text, @Param("code") String code);

     List<DictModel> queryDictItemsByCode(@Param("code") String code);

     List<DictModel> queryTableDictItemsByCodeAndFilter(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("filterSql") String filterSql);

    List<TreeSelectModel> queryTreeList(@Param("query") Map<String, String> query, @Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("pidField") String pidField, @Param("pid") String pid, @Param("hasChildField") String hasChildField);

     List<DictModel> queryTableDictByKeys(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyArray") String[] keyArray);

    Long getIdByCode(String code);

    /**
     * 通过关键字查询出字典表
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    List<DictModel> queryTableDictItems(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyword") String keyword);


     Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);

     Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo);

	List<Map<String,Object>> getAllTenantList();
}
