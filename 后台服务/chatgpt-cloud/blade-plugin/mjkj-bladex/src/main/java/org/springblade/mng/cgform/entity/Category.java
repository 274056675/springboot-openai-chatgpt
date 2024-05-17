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
 * 分类字典实体类
 *
 * @author BladeX
 * @since 2021-05-27
 */
@Data
@TableName("sys_category")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Category对象", description = "分类字典")
public class Category extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;

    @ApiModelProperty(value = "父级节点")
    private Long pid;

    @ApiModelProperty(value = "类型名称")
    private String name;

    @ApiModelProperty(value = "类型编码")
    private String code;

    @ApiModelProperty(value = "父级id")
    private String pstr;


    @ApiModelProperty(value = "是否有子节点:0=有子集  1=没子集")
    private String hasChild;


}
