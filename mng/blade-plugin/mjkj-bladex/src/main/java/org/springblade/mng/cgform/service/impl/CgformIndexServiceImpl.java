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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.springblade.mng.cgform.entity.CgformIndex;
import org.springblade.mng.cgform.mapper.CgformHeadMapper;
import org.springblade.mng.cgform.mapper.CgformIndexMapper;
import org.springblade.mng.cgform.service.ICgformIndexService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * 服务实现类
 *
 * @author BladeX
 * @since 2021-05-20
 */
@Service
public class CgformIndexServiceImpl extends BaseServiceImpl<CgformIndexMapper, CgformIndex> implements ICgformIndexService {

    @Autowired
    private CgformHeadMapper headMapper;

    @Override
    public List<CgformIndex> getCgformIndexsByCgformId(Long cgformId) {
        return this.baseMapper.selectList((new LambdaQueryWrapper<CgformIndex>()).in(CgformIndex::getCgformHeadId, new Object[]{cgformId}));
    }

    @Override
    public boolean isExistIndex(String countSql) {
        if (countSql == null) {
            return true;
        } else {
            Integer count = baseMapper.queryIndexCount(countSql);
            return count != null && count > 0;
        }
    }

}
