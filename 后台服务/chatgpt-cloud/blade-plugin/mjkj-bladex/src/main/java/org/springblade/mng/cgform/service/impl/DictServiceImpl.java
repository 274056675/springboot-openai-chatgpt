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


import org.springblade.mng.cgform.entity.SysDict;
import org.springblade.mng.cgform.mapper.DictMapper;
import org.springblade.mng.cgform.model.DictModel;
import org.springblade.mng.cgform.model.DuplicateCheckVo;
import org.springblade.mng.cgform.model.TreeSelectModel;
import org.springblade.mng.cgform.model.query.QueryGenerator;
import org.springblade.mng.cgform.service.IDictService;
import org.springblade.core.mp.base.BaseServiceImpl;

import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据字典 服务实现类
 *
 * @author BladeX
 * @since 2021-05-27
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, SysDict> implements IDictService {

    @Override
    public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
        if(table.indexOf("#{")>=0){
            table = QueryGenerator.getSqlRuleValue(table);
        }
        return baseMapper.queryTableDictItemsByCode(table, text, code);
    }

    @Override
    public List<DictModel> queryDictItemsByCode(String code) {
        return baseMapper.queryDictItemsByCode(code);
    }

    @Override
    public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql) {
        return baseMapper.queryTableDictItemsByCodeAndFilter(table,text,code,filterSql);
    }

    @Override
    public List<TreeSelectModel> queryTreeList(Map<String, String> query, String table, String text, String code, String pidField, String pid, String hasChildField) {
        return baseMapper.queryTreeList(query,table, text, code, pidField, pid,hasChildField);
    }

    /**
     * 通过查询指定table的 text code 获取字典，包含text和value
     * dictTableCache采用redis缓存有效期10分钟
     * @param table
     * @param text
     * @param code
     * @param keys (逗号分隔)
     * @return
     */
    @Override
    public List<String> queryTableDictByKeys(String table, String text, String code, String keys) {
        if(Func.isEmpty(keys)){
            return null;
        }
        String[] keyArray = keys.split(",");
        List<DictModel> dictsList = baseMapper.queryTableDictByKeys(table, text, code, keyArray);
        List<String> resultList = new ArrayList<>(dictsList.size());
        // 查询出来的顺序可能是乱的，需要排个序
        for (String key : keyArray) {
            for (DictModel dict : dictsList) {
                if (key.equals(dict.getValue())) {
                    resultList.add(dict.getText());
                    break;
                }
            }
        }
        return resultList;
    }

    @Override
    public  Long getIdByCode(String code){
        return baseMapper.getIdByCode(code);
    }

    @Override
    public List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql) {
        return baseMapper.queryTableDictItemsByCodeAndFilter(table,text,code,filterSql);
    }

    /**
     * 通过关键字查询字典表
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    @Deprecated
    public List<DictModel> queryTableDictItems(String table, String text, String code,String keyword){
        return baseMapper.queryTableDictItems(table,text,code,"%"+keyword+"%");
    }


    public Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo){
        return baseMapper.duplicateCheckCountSql(duplicateCheckVo);
    }
    public Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo){
        return baseMapper.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
    }


    @Override
	public List<Map<String,Object>> getAllTenantList(){
		return baseMapper.getAllTenantList();
	}

}
