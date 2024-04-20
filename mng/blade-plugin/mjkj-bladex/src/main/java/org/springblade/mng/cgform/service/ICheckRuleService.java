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

import org.springblade.core.mp.base.BaseService;
import org.springblade.mng.cgform.entity.CheckRule;

/**
 * 编码校验规则 服务类
 *
 * @author BladeX
 * @since 2021-07-03
 */
public interface ICheckRuleService extends BaseService<CheckRule> {

	/**
	 * 通过用户设定的自定义校验规则校验传入的值
	 *
	 * @param checkRule
	 * @param value
	 * @return 返回 null代表通过校验，否则就是返回的错误提示文本
	 */
	JSONObject checkValue(CheckRule checkRule, String value);
}
