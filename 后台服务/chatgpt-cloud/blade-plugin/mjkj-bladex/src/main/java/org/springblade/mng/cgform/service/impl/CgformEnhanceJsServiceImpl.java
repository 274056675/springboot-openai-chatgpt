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

import org.springblade.mng.cgform.entity.CgformEnhanceJs;
import org.springblade.mng.cgform.mapper.CgformEnhanceJsMapper;
import org.springblade.mng.cgform.service.ICgformEnhanceJsService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * js增强 服务实现类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Service
public class CgformEnhanceJsServiceImpl extends BaseServiceImpl<CgformEnhanceJsMapper, CgformEnhanceJs> implements ICgformEnhanceJsService {

    @Override
    public CgformEnhanceJs queryEnhanceJs(Long headId, String cgJsType) {
        LambdaQueryWrapper<CgformEnhanceJs> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CgformEnhanceJs::getCgformHeadId, headId);
        wrapper.eq(CgformEnhanceJs::getCgJsType, cgJsType);
        return baseMapper.selectOne(wrapper);
    }

}
