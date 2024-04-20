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
 * java增强实体类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_enhance_java")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformEnhanceJava对象", description = "java增强")
public class CgformEnhanceJava extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 按钮编码
     */
    @ApiModelProperty(value = "按钮编码")
    private String buttonCode;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String cgJavaType;
    /**
     * 数值
     */
    @ApiModelProperty(value = "数值")
    private String cgJavaValue;
    /**
     * 表单ID
     */
    @ApiModelProperty(value = "表单ID")
    private Long cgformHeadId;
    /**
     * 生效状态
     */
    @ApiModelProperty(value = "生效状态")
    private String activeStatus;
    /**
     * 事件状态(end:结束，start:开始)
     */
    @ApiModelProperty(value = "事件状态(end:结束，start:开始)")
    private String event;

	@ApiModelProperty(value = "服务名")
	private String serviceName;
}
