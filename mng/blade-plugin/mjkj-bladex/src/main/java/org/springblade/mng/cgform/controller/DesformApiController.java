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
package org.springblade.mng.cgform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.entity.DesformData;
import org.springblade.mng.cgform.entity.DesformHead;
import org.springblade.mng.cgform.entity.DesformRoute;
import org.springblade.mng.cgform.model.DesformUpdateTimeModel;
import org.springblade.mng.cgform.model.param.FormDataSaveModel;
import org.springblade.mng.cgform.model.query.QueryGenerator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.cgform.service.*;
import org.springblade.mng.config.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("desform-api")
@Api(value = "表单设计器开发接口", tags = "表单设计器开发接口")
public class DesformApiController {


    private final IDesformHeadService headService;

    private final IDesformDataService dataService;

    private final IDesformRouteService routeService;

	private final RedisUtil redisUtil;

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;

	@PostMapping("/unlock/{headId}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "解锁", notes = "解锁")
	public R unlock(@PathVariable("headId") Long headId) {
		String redisKey = "DESFORM_HEADID:" + headId;
		if(redisUtil.hasKey(redisKey)){
			redisUtil.del(redisKey);
		}
		return R.success("成功");
	}


    @GetMapping("/desform/openList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "表单设计器-获取列表", notes = "传入cgformField")
    public R<List<DesformHead>> openList(DesformHead head, Query query, HttpServletRequest req) {
        List<DesformHead> list = headService.openList();
        return R.data(list);
    }

    @GetMapping("/desform/list")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "表单设计器-获取列表", notes = "传入cgformField")
    public R<IPage<DesformHead>> formList(DesformHead head, Query query, HttpServletRequest req) {
    	LambdaQueryWrapper<DesformHead> wrapper=new LambdaQueryWrapper<>();
    	if(Func.isNotEmpty(head.getId())){
    		wrapper.eq(DesformHead::getId,head.getId());
		}
		if(Func.isNotEmpty(head.getFormName())){
			wrapper.like(DesformHead::getFormName,head.getFormName());
		}
		if(Func.isNotEmpty(head.getFormType())){
			wrapper.eq(DesformHead::getFormType,head.getFormType());
		}
		if(Func.isNotEmpty(head.getFormCode())){
			wrapper.eq(DesformHead::getFormCode,head.getFormCode());
		}
        wrapper.orderByDesc(DesformHead::getId);

        IPage<DesformHead> pages = headService.page(Condition.getPage(query), wrapper);
        return R.data(pages);
    }


    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "表单设计器 - 详情数据", notes = "详情数据")
    @GetMapping({"/desform/{headId}"})
    public R getFormDetail(@PathVariable("headId") Long headId, @ApiParam(value = "锁校验 true需要校验") String lock) throws Exception {
		String redisKey="desform_data:"+headId;
		DesformHead head=null;
		boolean flag=true;
		if(redisUtil.hasKey(redisKey)){
			head=(DesformHead) redisUtil.get(redisKey);
			flag=false;
		}else{
			head = headService.getById(headId);
		}

        if(Func.isEmpty(head)){
        	return R.data(head);
		}
		//判断该接口是否要的登录
		String nologinSelect = head.getNologinSelect();
		if(Func.equals(nologinSelect,"0")){//需要登录
			BladeUser user = AuthUtil.getUser();
			if(Func.isEmpty(user)){
				return R.fail("登录已过期，请重新登录");
			}
		}
		if(flag){
			redisUtil.set(redisKey,head,600L, TimeUnit.SECONDS);//10分钟
		}

        return R.data(head);
    }
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "表单设计器 - 详情数据", notes = "详情数据")
    @GetMapping({"/desform/code/{code}"})
    public R getFormIdByCode(@PathVariable("code") String code) throws Exception {
        LambdaQueryWrapper<DesformHead> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(DesformHead::getFormCode,code);
        DesformHead head = headService.getOne(wrapper);
        return R.data(head);
    }

	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "表单设计器 - 获取更新时间", notes = "获取更新时间")
	@PostMapping({"/desform/updateTime"})
	public R getFormUpdateTimeByCode(@RequestBody DesformUpdateTimeModel model) throws Exception {
		QueryWrapper<DesformHead> wrapper=new QueryWrapper<>();
		wrapper.select("id","update_time","create_time");
		wrapper.in("id",model.getIdList());

		List<DesformHead> list = headService.list(wrapper);

		List<Map<String,Object>> resultList=new ArrayList<>();
		if(Func.isNotEmpty(list)){
			for (DesformHead head:list) {
				Date updateTime = head.getUpdateTime();
				Date createTime = head.getCreateTime();
				if(Func.isEmpty(updateTime)){
					head.setUpdateTime(createTime);
				}
				Map<String,Object> resultMap=new HashMap<>();
				resultMap.put("id",head.getId());
				resultMap.put("updateTime",head.getUpdateTime());
				resultList.add(resultMap);
			}
		}
		return R.data(resultList);
	}


    @GetMapping("/desform/data/list")
    @ApiOperationSupport(order = 14)
    @ApiOperation(value = "表单设计器-数据-获取列表")
    public R<IPage<DesformData>> formDataList(DesformData data, Query query, HttpServletRequest req) {
        QueryWrapper<DesformData> wrapper = QueryGenerator.initQueryWrapper(data,
                req.getParameterMap());
        wrapper.orderByDesc("id");
        IPage<DesformData> pages = dataService.page(Condition.getPage(query), wrapper);
        return R.data(pages);
    }


    @ApiOperationSupport(order = 15)
    @ApiOperation(value = "通过表单开发数据id -数据- 详情数据", notes = "详情数据")
    @GetMapping({"/desform/data/onlineform/{dataId}"})
    public R getFormDataDetailByOnLineFormDataId(@PathVariable("dataId") Long dataId) throws Exception {
        LambdaQueryWrapper<DesformData> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(DesformData::getOnlineFormDataId,dataId);
        wrapper.eq(DesformData::getIsDeleted,0);
        DesformData data = dataService.getOne(wrapper);
        return R.data(data);
    }

    @GetMapping("/desform/route/list")
    @ApiOperationSupport(order = 16)
    @ApiOperation(value = "表单设计器路由-获取列表")
    public R<IPage<DesformRoute>> formRoureList(DesformRoute route, Query query, HttpServletRequest req) {
        QueryWrapper<DesformRoute> wrapper = QueryGenerator.initQueryWrapper(route,
                req.getParameterMap());
        wrapper.orderByDesc("id");
        IPage<DesformRoute> pages = routeService.page(Condition.getPage(query), wrapper);
        return R.data(pages);
    }



    @PostMapping("/desform/route/save")
    @ApiOperationSupport(order = 17)
    @ApiOperation(value = "表单设计器路由-新增", notes = "表单设计器路由-新增")
    public R formRouteSave(@Valid @RequestBody DesformRoute route) {
        routeService.save(route);
        return R.success("成功");
    }


    @PostMapping("/desform/route/edit")
    @ApiOperationSupport(order =18)
    @ApiOperation(value = "表单设计器路由-修改", notes = "表单设计器路由-修改")
    public R formRouteEdit(@Valid @RequestBody DesformRoute route) {
        routeService.formRouteEdit(route);
        return R.success("成功");
    }

    @PostMapping("/desform/route/delete")
    @ApiOperationSupport(order = 19)
    @ApiOperation(value = "表单设计器路由-逻辑删除", notes = "传入ids")
    public R formRouteRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        routeService.deleteLogic(Func.toLongList(ids));
        return R.success("成功");
    }


}
