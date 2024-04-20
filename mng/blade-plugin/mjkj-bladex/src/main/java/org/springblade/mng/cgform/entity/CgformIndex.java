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
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.utils.DateUtil;

/**
 * 表单索引
 */
@Data
@TableName("onl_cgform_index")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformIndex对象", description = "CgformIndex对象")
public class CgformIndex extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;

    @ApiModelProperty(value = "主表id")
    private Long cgformHeadId;

    @ApiModelProperty(value = "索引名称")
    private String indexName;

    @ApiModelProperty(value = "索引栏位")
    private String indexField;

    @ApiModelProperty(value = "索引类型")
    private String indexType;

    @ApiModelProperty(value = "是否同步数据库 N未同步 Y已同步")
    private String isDbSynch;

    public static CgformIndex init(CgformIndex index, String tenantId, BladeUser createUser){
        String deptId = createUser.getDeptId();
        Long userId = createUser.getUserId();
        index.setCreateUser(userId);
        index.setCreateTime(DateUtil.now());
        index.setTenantId(tenantId);
        return index;
    }
}
