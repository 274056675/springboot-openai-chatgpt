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

import org.springblade.core.mp.base.BaseService;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.model.CommonEntity;
import org.springblade.mng.cgform.model.TreeModel;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 *
 * @author BladeX
 * @since 2021-05-20
 */
public interface ICgformFieldService extends BaseService<CgformField> {

    //执行ddl
    void executeInsertSQL(Map<String, Object> map);

    /**
     * 获取可用的字段
     * @param headId
     * @param tableName
     * @param taskId
     * @param isList
     * @return
     */
    public List<CgformField> queryAvailableFields(Long headId, String tableName, String taskId, boolean isList);

    /**
     * 查询可用字段
     * @param tableName
     * @param isList
     * @param onlCgformFieldList
     * @param needList
     * @return
     */
    public List<CgformField> queryAvailableFields(String tableName, boolean isList, List<CgformField> onlCgformFieldList, List<String> needList);


    //获取查询条件
    public List<Map<String, String>> getAutoListQueryInfo(Long headId);

    /**
     * 保存树形
     * @param headId
     * @param tableName
     * @param json
     * @param hasChildField
     * @param pidField
     */
    public String saveTreeFormData(Long headId, String tableName, JSONObject json, String hasChildField, String pidField);


    /**
     * 编辑树形
     * @param headId
     * @param tableName
     * @param json
     * @param hasChildField
     * @param pidField
     */
    public void editTreeFormData(Long headId, String tableName, JSONObject json, String hasChildField, String pidField);


    /**
     * 保存表单的数据
     * @param headId
     * @param tableName
     * @param json
     * @param isCrazy
     */
    public String saveFormData(Long headId, String tableName, JSONObject json, boolean isCrazy);

    /**
     * 保存表单的数据
     * @param fieldList
     * @param tableName
     * @param json
     */
    public void saveFormDataList(List<CgformField> fieldList, String tableName, JSONObject json);

    /**
     * 编辑表单的数据
     * @param headId
     * @param tableName
     * @param json
     * @param isCrazy
     */
    public void editFormData(Long headId, String tableName, JSONObject json, boolean isCrazy);

    /**
     * 查询表单的数据
     * @param tableName 表名
     * @param headId 表id
     * @param params 查询条件
     * @param needList
     * @return
     */
    Map<String, Object> queryAutolistPage(String tableName, Long headId, Map<String, Object> params, List<String> needList);

    Map<String, Object> queryAutoTreeNoPage(String tbname, Long headId, Map<String, Object> params, List<String> needList, String pidField);

    /**
     * 通过表名获取字段
     * @param tableName
     * @return
     */
    public List<CgformField> queryFormFieldsByTableName(String tableName);

    /**
     * 获取表单的字段
     * @param headId
     * @param isForm
     * @return
     */
    public List<CgformField> queryFormFields(Long headId, boolean isForm);

    /**
     * 获取表单的数据
     * @param fieldList
     * @param tableName
     * @param id
     * @return
     */
    public Map<String, Object> queryFormData(List<CgformField> fieldList, String tableName, String id);

    /**
     * 获取表单的数据-所有数据
     * @param fieldList
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> queryFormAllData(List<CgformField> fieldList, String tableName);


    /**
     * 获取表单的数据
     * @param fieldList
     * @param tableName
     * @param linkField
     * @param value
     * @return
     */
    public List<Map<String, Object>> querySubFormData(List<CgformField> fieldList, String tableName, String linkField, String value);


    public String queryTreeChildIds(CgformHead onlcgformhead, String dataIdStrs);

    /**
     * 删除表单
     * @param tableName
     * @param ids
     */
    public void deleteAutoListById(String tableName, String ids);


    /**
     * 删除表单
     * @param head
     * @param ids
     */
    public void deleteAutoListMainAndSub(CgformHead head, String ids);
    /**
     * 更新无孩子节点
     * @param tableName
     * @param filed
     * @param id
     */
    public void updateTreeNodeNoChild(String tableName, String filed, String id);


    /**
     * 通过linkDown获取树形数据
     * @param entity
     * @return
     */
    public List<TreeModel> queryDataListByLinkDown(CommonEntity entity);

    /**
     * 删除表单
     * @param tableName
     * @param linkField
     * @param linkValue
     */
    public void deleteAutoList(String tableName, String linkField, String linkValue);

	public void deleteList(String tableName, String linkField, String linkValue);
}
