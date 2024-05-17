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
 * sql增强实体类
 *
 * @author BladeX
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_enhance_sql")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformEnhanceSql对象", description = "sql增强")
public class CgformEnhanceSql extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 按钮编码
     */
    @ApiModelProperty(value = "按钮编码")
    private String buttonCode;
    /**
     * SQL内容
     */
    @ApiModelProperty(value = "SQL内容")
    private String cgbSql;
    /**
     * Sql名称
     */
    @ApiModelProperty(value = "Sql名称")
    private String cgbSqlName;
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

	@ApiModelProperty(value = "服务名")
	private String serviceName;
}
