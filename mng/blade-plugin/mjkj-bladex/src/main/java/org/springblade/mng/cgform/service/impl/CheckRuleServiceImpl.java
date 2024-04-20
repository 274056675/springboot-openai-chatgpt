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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springblade.mng.cgform.entity.CheckRule;
import org.springblade.mng.cgform.mapper.CheckRuleMapper;
import org.springblade.mng.cgform.service.ICheckRuleService;
import org.apache.commons.lang.StringUtils;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 编码校验规则 服务实现类
 *
 * @author BladeX
 * @since 2021-07-03
 */
@Service
public class CheckRuleServiceImpl extends BaseServiceImpl<CheckRuleMapper, CheckRule> implements ICheckRuleService {


	/**
	 * 通过用户设定的自定义校验规则校验传入的值
	 *
	 * @param checkRule
	 * @param value
	 * @return 返回 null代表通过校验，否则就是返回的错误提示文本
	 */
	@Override
	public JSONObject checkValue(CheckRule checkRule, String value) {
		if (checkRule != null && StringUtils.isNotBlank(value)) {
			String ruleJson = checkRule.getRuleJson();
			if (StringUtils.isNotBlank(ruleJson)) {
				// 开始截取的下标，根据规则的顺序递增，但是 * 号不计入递增范围
				int beginIndex = 0;
				JSONArray rules = JSON.parseArray(ruleJson);
				for (int i = 0; i < rules.size(); i++) {
					JSONObject result = new JSONObject();
					JSONObject rule = rules.getJSONObject(i);
					// 位数
					String digits = rule.getString("digits");
					result.put("digits", digits);
					// 验证规则
					String pattern = rule.getString("pattern");
					result.put("pattern", pattern);
					// 未通过时的提示文本
					String message = rule.getString("message");
					result.put("message", message);

					// 根据用户设定的区间，截取字符串进行验证
					String checkValue;
					// 是否检查整个值而不截取
					if ("*".equals(digits)) {
						checkValue = value;
					} else {
						int num = Integer.parseInt(digits);
						int endIndex = beginIndex + num;
						// 如果结束下标大于给定的值的长度，则取到最后一位
						endIndex = endIndex > value.length() ? value.length() : endIndex;
						// 如果开始下标大于结束下标，则说明用户还尚未输入到该位置，直接赋空值
						if (beginIndex > endIndex) {
							checkValue = "";
						} else {
							checkValue = value.substring(beginIndex, endIndex);
						}
						result.put("beginIndex", beginIndex);
						result.put("endIndex", endIndex);
						beginIndex += num;
					}
					result.put("checkValue", checkValue);
					boolean passed = Pattern.matches(pattern, checkValue);
					result.put("passed", passed);
					// 如果没有通过校验就返回错误信息
					if (!passed) {
						return result;
					}
				}
			}
		}
		return null;
	}
}
