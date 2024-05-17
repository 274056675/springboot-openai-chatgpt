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
package org.springblade.modules.mjkj.common.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.modules.mjkj.common.config.exception.BusinessException;
import org.springblade.modules.mjkj.common.config.exception.DBException;
import org.springblade.modules.mjkj.common.cgform.entity.CgformHead;
import org.springblade.modules.mjkj.common.cgform.model.CgformModel;
import org.springblade.modules.mjkj.common.cgform.model.OnlGenerateModel;
import org.springblade.modules.mjkj.common.cgform.model.TreeDataModel;
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


    //编辑表数据
    void editManyFormData(CgformHead head, JSONObject jsonobject)
            throws DBException, BusinessException;


    //详情
     Map<String, Object> queryManyFormData(Long headId, String id)
            throws DBException;


    //删除
     void deleteOneTableInfo(Long headId, String s1)
            throws BusinessException;


     void initCopyState(List<CgformHead> list);


}
