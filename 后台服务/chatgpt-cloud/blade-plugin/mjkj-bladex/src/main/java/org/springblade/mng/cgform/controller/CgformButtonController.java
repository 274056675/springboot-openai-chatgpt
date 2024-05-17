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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springblade.mng.cgform.entity.CgformButton;
import org.springblade.mng.cgform.service.ICgformButtonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 *  控制器
 *
 * @author BladeX
 * @since 2021-05-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("cgform-btton")
@Api(value = "自定义表单按钮", tags = "自定义表单按钮")
public class CgformButtonController extends BladeController {

	private final ICgformButtonService cgformButtonService;

	@GetMapping("/list/{headId}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取按钮列表")
	public R<IPage<CgformButton>> list(@PathVariable("headId") Long headId, Query query) {
		LambdaQueryWrapper<CgformButton> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(CgformButton::getCgformHeadId,headId);
		wrapper.orderByDesc(CgformButton::getId);
		IPage<CgformButton> pages = cgformButtonService.page(Condition.getPage(query), wrapper);
		return R.data(pages);
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "新增自定义按钮", notes = "新增自定义按钮")
	public R save(@Valid @RequestBody CgformButton model) {
		return R.status(cgformButtonService.save(model));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改", notes = "修改")
	public R update(@Valid @RequestBody CgformButton model) {
		return R.status(cgformButtonService.updateById(model));
	}

	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cgformButtonService.deleteLogic(Func.toLongList(ids)));
	}

	@GetMapping("/detail")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "详情", notes = "详情")
	public R<CgformButton> detail(@ApiParam(value = "主键", required = true)Long id) {
		CgformButton detail = cgformButtonService.getById(id);
		return R.data(detail);
	}
}
