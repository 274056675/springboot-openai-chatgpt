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



import org.springblade.core.mp.base.BaseService;
import org.springblade.mng.cgform.entity.SysDict;
import org.springblade.mng.cgform.model.DictModel;
import org.springblade.mng.cgform.model.DuplicateCheckVo;
import org.springblade.mng.cgform.model.TreeSelectModel;

import java.util.List;
import java.util.Map;

/**
 * 数据字典 服务类
 *
 * @author BladeX
 * @since 2021-05-27
 */
public interface IDictService extends BaseService<SysDict> {

    /**
     * 13获取表数据字典
     * @param table
     * @param text
     * @param code
     * @return
     */
    List<DictModel> queryTableDictItemsByCode(String table, String text, String code);

    /**
     * 10获取数据字典
     * @param code
     * @return
     */
    public List<DictModel> queryDictItemsByCode(String code);

    /**
     * 16查询表字典 支持过滤数据
     * @param table
     * @param text
     * @param code
     * @param filterSql
     * @return
     */
    public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql);

    List<TreeSelectModel> queryTreeList(Map<String, String> query, String table, String text, String code, String pidField, String pid, String hasChildField);

    List<String> queryTableDictByKeys(String table, String text, String code, String keys);

    Long getIdByCode(String code);

    @Deprecated
     List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql);

    /**
     * 通过关键字查询字典表
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    List<DictModel> queryTableDictItems(String table, String text, String code, String keyword);


     Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);
     Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo);

     List<Map<String,Object>> getAllTenantList();
}
