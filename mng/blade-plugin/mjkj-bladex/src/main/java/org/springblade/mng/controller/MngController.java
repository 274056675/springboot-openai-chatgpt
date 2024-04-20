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
package org.springblade.mng.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.param.mng.MngMemberParam;
import org.springblade.mng.param.mng.MngQuestionCouParam;
import org.springblade.mng.service.IMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("chat/mng")
@Api(value = "超级AI智能助手开放接口", tags = "超级AI智能助手开放接口")
public class MngController {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IMngService mngService;

	@ApiOperationSupport(order = 1)
	@PostMapping({"/update/questionCou"})
	@ApiOperation(value = "更新可提问问题次数", notes = "更新可提问问题次数")
	public R updateQuestionCou(@RequestBody MngQuestionCouParam param) {
		List<String> ids = param.getIds();
		if(Func.isEmpty(ids)){
			return R.fail("请勾选要更改的用户");
		}
		Integer num = param.getNum();
		if(num<0){
			return R.fail("次数不允许小于0");
		}

		mngService.updateQuestionCou(param);
		return R.data("成功");
	}

	@ApiOperationSupport(order = 2)
	@PostMapping({"/add/goodsOrder"})
	@ApiOperation(value = "人工充值会员", notes = "人工充值会员")
	public R addGoodsOrder(@RequestBody MngMemberParam param) {
		String phone = param.getPhone();
		QueryWrapper<Object> wxuserWrapper=new QueryWrapper<>();
		wxuserWrapper.eq("phone",phone);
		wxuserWrapper.eq("is_deleted",0);
		Map<String, Object> wxuserMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wxuserWrapper);
		if(Func.isEmpty(wxuserMap)){
			return R.fail("用户不存在");
		}
		mngService.addGoodsOrder(wxuserMap,param);
		return R.data("成功");
	}

}

