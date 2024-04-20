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
package org.springblade.mng.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 填值规则实体类
 *
 * @author BladeX
 * @since 2021-07-03
 */
@Data
@TableName("sys_fill_rule")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FillRule对象", description = "填值规则")
public class FillRule extends BaseEntity {

	private static final long serialVersionUID = 1L;


		@TableId
		private Long id;
	/**
	* 规则名称
	*/
		@ApiModelProperty(value = "规则名称")
		private String ruleName;
	/**
	* 规则Code
	*/
		@ApiModelProperty(value = "规则Code")
		private String ruleCode;
	/**
	* 规则实现类
	*/
		@ApiModelProperty(value = "规则实现类")
		private String ruleClass;
	/**
	* 规则参数
	*/
		@ApiModelProperty(value = "规则参数")
		private String ruleParams;


}
