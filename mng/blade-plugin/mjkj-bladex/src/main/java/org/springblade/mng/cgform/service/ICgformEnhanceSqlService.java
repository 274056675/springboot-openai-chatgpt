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
import org.springblade.mng.cgform.entity.CgformEnhanceSql;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.mapper.SqlMapper;
import org.springblade.mng.cgform.model.CgformEnhanceJavaBatchInter;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.core.mp.base.BaseService;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

/**
 * sql增强 服务类
 *
 * @author BladeX
 * @since 2021-05-22
 */
public interface ICgformEnhanceSqlService extends BaseService<CgformEnhanceSql> {

    /**
     * 执行sql增强
     * @param buttonCode
     * @param headId
     * @param json
     */
    void executeEnhanceSqlUpdate(String buttonCode, Long headId, JSONObject json);

	void executeEnhanceSqlInsert(String buttonCode, Long headId, JSONObject json);

    //sql增强详情
	Map<String, Object> executeEnhanceSqlDetail(CgformHead head, String buttonCode, Map<String, Object> map, String id);
	//sql增强 列表
	void executeEnhanceSqlList(CgformHead head, String buttonCode, Map<String, Object> params);

	void saveBatchCodeOnlineTable(CgformHead onlcgformhead, List<CgformField> fieldList, List<Map<String, Object>> dataList, String batchCode, Integer totalNum, SqlMapper sqlMapper, Map<String, Object> otherMap, CgformEnhanceJavaBatchInter enhanceJava, ServletRequestAttributes sra) throws BusinessException;

}
