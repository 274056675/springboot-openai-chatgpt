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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.TenantEntity;


/**
 * 表单校验
 */
@Data
@TableName("onl_cgform_valid")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformValid对象", description = "自定义表单字段校验规则表")
public class CgformValid extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "校验表达式")
    private String pattern;

    @ApiModelProperty(value = "描述")
    private String msg;

    @ApiModelProperty(value = "校验字段名")
    private String name;


}
