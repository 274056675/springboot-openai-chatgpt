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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.mng.cgform.entity.CgformButton;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.mapper.CgformButtonMapper;
import org.springblade.mng.cgform.mapper.CgformFieldMapper;
import org.springblade.mng.cgform.mapper.CgformHeadMapper;
import org.springblade.mng.cgform.service.ICgformButtonService;
import org.springblade.mng.cgform.service.ICgformEnhanceJavaService;
import org.springblade.mng.cgform.service.ICgformEnhanceSqlService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.mng.config.util.SqlSymbolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 自定义表单 服务实现类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Service
public class CgformButtonServiceImpl extends BaseServiceImpl<CgformButtonMapper, CgformButton> implements ICgformButtonService {

    @Autowired
    private CgformHeadMapper headMapperMapper;

    @Autowired
    private CgformFieldMapper fieldMapper;

    @Autowired
    private ICgformEnhanceSqlService sqlService;

    @Autowired
    private ICgformEnhanceJavaService javaService;

    public List<CgformButton> queryButtonList(Long headId, boolean isListButton) {
        LambdaQueryWrapper<CgformButton> qw = new LambdaQueryWrapper<>();
        qw.eq(CgformButton::getButtonStatus, "1");
        qw.eq(CgformButton::getCgformHeadId, headId);
        if (isListButton) {
            qw.in(CgformButton::getButtonStyle, new Object[]{"link", "button"});
        } else {
            qw.eq(CgformButton::getButtonStyle, "form");
        }

        qw.orderByAsc(CgformButton::getOrderNum);
        return baseMapper.selectList(qw);
    }

    public List<CgformButton> queryButtonList(Long headId) {
        LambdaQueryWrapper<CgformButton> qw = new LambdaQueryWrapper<>();
        qw.eq(CgformButton::getButtonStatus, "1");
        qw.eq(CgformButton::getCgformHeadId, headId);
        qw.orderByAsc(CgformButton::getOrderNum);
        return baseMapper.selectList(qw);
    }

    public  void executeCustomerButton(String buttonCode,Long headId,Long dataId) throws Exception{
        CgformHead head = headMapperMapper.selectById(headId);
        if (head == null) {
            throw new Exception("未找到表配置信息");
        } else {
            Map<String, Object> map = fieldMapper.queryOneByTableNameAndId(SqlSymbolUtil.getSubstring(head.getTableName()), dataId);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map));
            javaService.executeEnhanceJava(buttonCode, "start", head, jsonObject);
            sqlService.executeEnhanceSqlUpdate(buttonCode, headId, jsonObject);
            javaService.executeEnhanceJava(buttonCode, "end", head, jsonObject);
        }
    }

//	@Override
//	public boolean saveBatch(Collection<CgformButton> entityList) {
//		return super.saveBatch(entityList);
//	}
}
