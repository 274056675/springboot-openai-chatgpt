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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.TenantEntity;


/**
 * 表单设计器基本属性实体类
 *
 * @author BladeX
 * @since 2021-07-02
 */
@Data
@TableName("design_desform_head")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DesformHead对象", description = "表单设计器基本属性")
public class DesformHead extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private String id;

    @ApiModelProperty(value = "online表名称")
    @TableField(exist = false)
    private String cgformCode;

    /**
     * 表单id
     */
    @ApiModelProperty(value = "表单id")
    private Long cgformHeadId;
    /**
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    private String formName;
    /**
     * 表单编码
     */
    @ApiModelProperty(value = "表单编码")
    private String formCode;
    /**
     * 表单图标
     */
    @ApiModelProperty(value = "表单图标")
    private String formIcon;
    /**
     * 表单json
     */
    @ApiModelProperty(value = "表单json")
    private String formDesignJson;
    /**
     * 类型：1=主表，2=子表
     */
    @ApiModelProperty(value = "类型：1=主表，2=子表")
    private Integer formType;
    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long parentId;
    /**
     * 父级编码
     */
    @ApiModelProperty(value = "父级编码")
    private String parentCode;
    /**
     * 手机模式：0=否 1=是
     */
    @ApiModelProperty(value = "手机模式：0=否 1=是")
    private Integer mobileView;

	@ApiModelProperty(value = "不登录是否可以查询-0=否，1=是")
	private String nologinSelect;
}
