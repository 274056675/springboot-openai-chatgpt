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
 * 自定义表单实体类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_button")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformButton对象", description = "自定义表单")
public class CgformButton extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;

    @ApiModelProperty(value = "按钮编码")
    private String buttonCode;

    @ApiModelProperty(value = "按钮图标")
    private String buttonIcon;

    @ApiModelProperty(value = "按钮名称")
    private String buttonName;

    @ApiModelProperty(value = "按钮状态")
    private String buttonStatus;

    @ApiModelProperty(value = "按钮样式")
    private String buttonStyle;

    @ApiModelProperty(value = "表达式")
    private String exp;

    @ApiModelProperty(value = "表单ID")
    private Long cgformHeadId;

    @ApiModelProperty(value = "按钮类型")
    private String optType;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "按钮位置1侧面 2底部")
    private String optPosition;


}
