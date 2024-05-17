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
 * 表单设计器-路由实体类
 *
 * @author BladeX
 * @since 2021-07-02
 */
@Data
@TableName("design_desform_route")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "表单设计器路由", description = "表单设计器路由")
public class DesformRoute extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;

    @ApiModelProperty(value = "表单设计id")
    private Long desFormId;

    @ApiModelProperty(value = "表单设计code")
    private String desFormCode;

    @ApiModelProperty(value = "'路由名称'")
    private String routeName;

    @ApiModelProperty(value = "路由地址")
    private String routePath;

    @ApiModelProperty(value = "路由跳转路径")
    private String routeType;

}
