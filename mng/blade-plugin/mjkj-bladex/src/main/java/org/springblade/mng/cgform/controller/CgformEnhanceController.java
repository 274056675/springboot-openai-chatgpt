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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

import org.springblade.mng.config.util.DateUtils;
import org.springblade.mng.config.util.SqlSymbolUtil;
import org.springblade.mng.cgform.entity.CgformButton;
import org.springblade.mng.cgform.entity.CgformEnhanceJava;
import org.springblade.mng.cgform.entity.CgformEnhanceJs;
import org.springblade.mng.cgform.entity.CgformEnhanceSql;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;

import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;

import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.cgform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("cgform-java/cgformenhance")
@Api(value = "表单增强", tags = "表单增强")
public class CgformEnhanceController extends BladeController {

	private final ICgformEnhanceJsService jsService;

	private final ICgformEnhanceJavaService javaService;

	private final ICgformEnhanceSqlService sqlService;

	private final ICgformButtonService buttonService;

	private final RedisUtil redisUtil;

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;

	@PostMapping("/unlock/{headId}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "解锁", notes = "解锁")
	public R unlock(@PathVariable("headId") Long headId) {
		String redisKey = "CGFORMENHANCE_HEADID:" + headId;
		if(redisUtil.hasKey(redisKey)){
			redisUtil.del(redisKey);
		}
		return R.success("成功");
	}

	@GetMapping("/js/detail/{headId}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "js增强-详情", notes = "js-详情")
	public R<CgformEnhanceJs> detail(@PathVariable("headId") Long headId,
									 @ApiParam(value = "类型：list 或者 form", required = true) String type,
									 @ApiParam(value = "锁校验 true需要校验") String lock) {
		if (Func.isNotEmpty(lock) && Func.equals(lock, "true")) {
			String redisKey = "CGFORMENHANCE_HEADID:" + headId;
			if (redisUtil.hasKey(redisKey)) {
				return R.fail("该增强已锁");
			}
			redisUtil.set(redisKey, "true");//上锁
		}

		LambdaQueryWrapper<CgformEnhanceJs> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJs::getCgformHeadId, headId);
		wrapper.eq(CgformEnhanceJs::getCgJsType, type);
		CgformEnhanceJs detail = jsService.getOne(wrapper);
		if (Func.isNotEmpty(detail)) {
			String cgJs = detail.getCgJs();
			String str = StringEscapeUtils.unescapeHtml(cgJs);
			detail.setCgJs(str);
		}
		return R.data(detail);
	}


	@PostMapping("/js/saveOrUpdate")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "js增强-新增或修改")
	public R cryptoByType(@RequestBody CgformEnhanceJs model) {
		LambdaQueryWrapper<CgformEnhanceJs> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJs::getCgformHeadId, model.getCgformHeadId());
		wrapper.eq(CgformEnhanceJs::getCgJsType, model.getCgJsType());
		CgformEnhanceJs detail = jsService.getOne(wrapper);
		if (Func.isNotEmpty(detail)) {
			model.setId(detail.getId());
		}

		String redisKey = "CGFORMENHANCE_HEADID:" + model.getCgformHeadId();
		if (redisUtil.hasKey(redisKey)) {
			redisUtil.del(redisKey);//解锁
		}
		jsService.saveOrUpdate(model);//操作完成，写入操作记录

		//-----写入操作记录--------
		detail = jsService.getOne(wrapper);
		Map<String,Object> historyMap=new HashMap<>();
		historyMap.put("id", IdWorker.getId());
		historyMap.put("cgform_head_id",model.getCgformHeadId());
		historyMap.put("cg_js",model.getCgJs());
		historyMap.put("cg_js_type",model.getCgJsType());
		historyMap.put("content", model.getContent());
		historyMap.put("czr", AuthUtil.getUserName());
		historyMap.put("czsj", DateUtils.formatDate());
		mjkjBaseSqlService.baseInsertData("onl_cgform_enhance_js_history",historyMap);
		return R.success("成功");
	}

	@GetMapping("/java/list/{headId}")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "java增强 - 获取列表")
	public R<IPage<CgformEnhanceJava>> javaList(@PathVariable("headId") Long headId, Query query) {
		LambdaQueryWrapper<CgformEnhanceJava> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceJava::getCgformHeadId, headId);
		wrapper.orderByDesc(CgformEnhanceJava::getCreateTime);
		IPage<CgformEnhanceJava> pages = javaService.page(Condition.getPage(query), wrapper);
		return R.data(pages);
	}

	@PostMapping("/java/save/{headId}")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "java增强 - 新增", notes = "java增强 - 新增")
	public R javaSave(@PathVariable("headId") Long headId, @RequestBody CgformEnhanceJava model) {
		model.setCgformHeadId(headId);
		if (!SqlSymbolUtil.isExistJava(model)) {
			return R.fail("类实例化失败，请检查!");
		}
		if (!javaService.checkOnlyEnhance(model)) {
			return R.fail("保存失败,数据不唯一!");
		}
		this.javaService.save(model);
		return R.success("成功");
	}

	@PostMapping("/java/edit/{headId}")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "java增强 - 编辑", notes = "java增强 - 编辑")
	public R javaEdit(@PathVariable("headId") Long headId, @RequestBody CgformEnhanceJava model) {
		model.setCgformHeadId(headId);
		if (!SqlSymbolUtil.isExistJava(model)) {
			return R.fail("类实例化失败，请检查!");
		}
		if (!javaService.checkOnlyEnhance(model)) {
			return R.fail("保存失败,数据不唯一!");
		}
		this.javaService.updateById(model);
		return R.success("成功");
	}

	@PostMapping("/java/delete")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "java增强 - 删除", notes = "传入ids")
	public R javaRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		javaService.deleteLogic(Func.toLongList(ids));
		return R.success("成功");
	}

	@GetMapping("/sql/list/{headId}")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "sql增强 - 获取列表")
	public R<IPage<CgformEnhanceSql>> sqlList(@PathVariable("headId") Long headId, Query query) {
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, headId);
		wrapper.orderByDesc(CgformEnhanceSql::getId);
		IPage<CgformEnhanceSql> pages = sqlService.page(Condition.getPage(query), wrapper);
		return R.data(pages);
	}

	@PostMapping("/sql/save/{headId}")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "sql增强 - 新增", notes = "sql增强 - 新增")
	public R sqlSave(@PathVariable("headId") Long headId, @RequestBody CgformEnhanceSql model) {
		model.setCgformHeadId(headId);
		this.sqlService.save(model);

		//-----写入操作记录--------
		Map<String,Object> historyMap=new HashMap<>();
		historyMap.put("id", IdWorker.getId());
		historyMap.put("cgform_head_id",model.getCgformHeadId());
		historyMap.put("button_code", model.getButtonCode());
		historyMap.put("cgb_sql", model.getCgbSql());
		historyMap.put("cgb_sql_name",model.getCgbSqlName());
		historyMap.put("content",model.getContent());
		historyMap.put("cgform_head_id",model.getCgformHeadId());
		historyMap.put("czr", AuthUtil.getUserName());
		historyMap.put("czsj", DateUtils.formatDate());
		historyMap.put("service_name",model.getServiceName());
		mjkjBaseSqlService.baseInsertData("onl_cgform_enhance_sql_history",historyMap);

		return R.success("成功");
	}

	@PostMapping("/sql/edit/{headId}")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "sql增强 - 编辑", notes = "sql增强 - 编辑")
	public R sqlEdit(@PathVariable("headId") Long headId, @RequestBody CgformEnhanceSql model) {
		model.setCgformHeadId(headId);
		sqlService.updateById(model);

		//-----写入操作记录--------
		Map<String,Object> historyMap=new HashMap<>();
		historyMap.put("id", IdWorker.getId());
		historyMap.put("cgform_head_id",model.getCgformHeadId());
		historyMap.put("button_code", model.getButtonCode());
		historyMap.put("cgb_sql", model.getCgbSql());
		historyMap.put("cgb_sql_name",model.getCgbSqlName());
		historyMap.put("content",model.getContent());
		historyMap.put("cgform_head_id",model.getCgformHeadId());
		historyMap.put("czr", AuthUtil.getUserName());
		historyMap.put("czsj", DateUtils.formatDate());
		historyMap.put("service_name",model.getServiceName());
		mjkjBaseSqlService.baseInsertData("onl_cgform_enhance_sql_history",historyMap);

		return R.success("成功");
	}

	@PostMapping("/sql/delete")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "sql增强 - 删除", notes = "传入ids")
	public R sqlRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		sqlService.deleteLogic(Func.toLongList(ids));
		return R.success("成功");
	}

	@ApiOperation(value = "sql增强 - 获取按钮", notes = "sql增强 - 获取按钮")
	@ApiOperationSupport(order = 10)
	@GetMapping({"/sql/enhanceButton/{headId}"})
	public R enhanceButton(@PathVariable("headId") Long headId, HttpServletRequest request) {
		List<CgformButton> list = buttonService.queryButtonList(headId);
		return R.data(list);
	}

	@ApiOperation(value = " 触发自定义增强", notes = "触发自定义增强")
	@PostMapping({"/doButton"})
	public R doButton(@RequestBody JSONObject jsonObject) {
		Long formId = jsonObject.getLong("formId");
		Long dataId = jsonObject.getLong("dataId");
		String buttonCode = jsonObject.getString("buttonCode");
		try {
			buttonService.executeCustomerButton(buttonCode, formId, dataId);
		} catch (Exception e) {
			return R.fail("执行失败!");
		}

		return R.success("执行成功!");
	}

}
