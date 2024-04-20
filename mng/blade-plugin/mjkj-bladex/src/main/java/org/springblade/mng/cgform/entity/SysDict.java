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

import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据字典实体类
 *
 * @author BladeX
 * @since 2021-05-27
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Dict对象", description = "数据字典")
public class SysDict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String id;
	/**
	* 字典名称
	*/
		@ApiModelProperty(value = "字典名称")
		private String dictName;
	/**
	* 字典编码
	*/
		@ApiModelProperty(value = "字典编码")
		private String dictCode;
	/**
	* 描述
	*/
		@ApiModelProperty(value = "描述")
		private String description;
	/**
	* 字典类型0为string,1为number
	*/
		@ApiModelProperty(value = "字典类型0为string,1为number")
		private Integer type;


}
