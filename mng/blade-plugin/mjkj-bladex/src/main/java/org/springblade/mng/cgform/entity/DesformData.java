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
 * 表单设计器-数据实体类
 *
 * @author BladeX
 * @since 2021-07-02
 */
@Data
@TableName("design_desform_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DesformData对象", description = "表单设计器-数据")
public class DesformData extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 表单设计id
     */
    @ApiModelProperty(value = "表单设计id")
    private Long desformHeadId;
    /**
     * 表单json
     */
    @ApiModelProperty(value = "表单json")
    private String formDataJson;


    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 表单开发表名
     */
    private String onlineFormCode;

    /**
     * 表单开发数据id
     */
    private String onlineFormDataId;


}
