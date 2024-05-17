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
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.model.CgformModel;
import org.springblade.mng.cgform.model.OnlGenerateModel;
import org.springblade.mng.cgform.model.TreeDataModel;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.mng.config.exception.DBException;

import org.springblade.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 *
 * @author BladeX
 * @since 2021-05-20
 */
public interface ICgformHeadService extends BaseService<CgformHead> {

    //新增表数据
    String saveManyFormData(CgformHead head, JSONObject jsonobject)
            throws DBException, BusinessException;
    //新增表数据-批量新增
	List<String> saveManyFormDataBatch(CgformHead head, List<JSONObject> jsonobjectList)
		throws DBException, BusinessException;

    //编辑表数据
    void editManyFormData(CgformHead head, JSONObject jsonobject)
            throws DBException, BusinessException;

	//编辑表数据-批量编辑
	void editManyFormDataBatch(CgformHead head, List<JSONObject> jsonobjectList)
		throws DBException, BusinessException;

    //详情
     Map<String, Object> queryManyFormData(Long headId, String id)
            throws DBException;

     //获取树结构所有数据
    List<TreeDataModel> getTreeAllDataList(Long headId)
            throws DBException;

    //删除
     void deleteOneTableInfo(Long headId, String s1)
            throws BusinessException;

     //动态列表查询子表
     List<Map<String, Object>> queryListData(String s);

     void initCopyState(List<CgformHead> list);


}
