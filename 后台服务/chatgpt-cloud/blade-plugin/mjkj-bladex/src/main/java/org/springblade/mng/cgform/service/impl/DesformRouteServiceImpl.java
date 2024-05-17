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

import org.springblade.mng.cgform.entity.DesformRoute;
import org.springblade.mng.cgform.mapper.DesformRouteMapper;
import org.springblade.mng.cgform.service.IDesformRouteService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 表单设计器-数据 服务实现类
 *
 * @author BladeX
 * @since 2021-07-02
 */
@Service
public class DesformRouteServiceImpl extends BaseServiceImpl<DesformRouteMapper, DesformRoute> implements IDesformRouteService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void formRouteEdit(DesformRoute route){
        if(route.getStatus()==1){//当前开启，需要查出所有开启的进行关闭
            LambdaQueryWrapper<DesformRoute> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(DesformRoute::getStatus,1);
            DesformRoute select = this.getOne(wrapper);
            if(Func.isNotEmpty(select)){
                select.setStatus(0);
                this.updateById(select);
            }

        }
        this.updateById(route);
    }
}
