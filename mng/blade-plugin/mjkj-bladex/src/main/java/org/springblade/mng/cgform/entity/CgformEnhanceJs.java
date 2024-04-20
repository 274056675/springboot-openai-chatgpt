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
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.TenantEntity;


/**
 * js增强实体类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_enhance_js")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformEnhanceJs对象", description = "js增强")
public class CgformEnhanceJs extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private String id;

    /**
     * JS增强内容
     */
    @ApiModelProperty(value = "JS增强内容")
    private String cgJs;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String cgJsType;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String content;
    /**
     * 表单ID
     */
    @ApiModelProperty(value = "表单ID")
    private Long cgformHeadId;


}
