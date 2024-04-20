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
package org.springblade.mng.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springblade.mng.cgform.entity.CgformHead;

import java.util.List;
import java.util.Map;

/**
 *  Mapper 接口
 *
 * @author BladeX
 * @since 2021-05-20
 */

public interface CgformHeadMapper extends BaseMapper<CgformHead> {

    List<Map<String, Object>> queryList(@Param("sqlStr") String var1);

    void executeDDL(@Param("sqlStr") String var1);

    Integer getMaxCopyVersion(Long physicId);

    Map<String, Object> queryOneByTableNameAndId(@Param("tbname") String var1, @Param("dataId") String var2);

	//逻辑删除
    void deleteOne(@Param("sqlStr") String var1);

    @Select({"select count(*) from ${tableName} where ${pidField} = #{pidValue}"})
    Integer queryChildNode(@Param("tableName") String var1, @Param("pidField") String var2, @Param("pidValue") String var3);

    @Select({"select physic_id from onl_cgform_head GROUP BY physic_id"})
    List<String> queryCopyPhysicId();

	@Select({"select table_name from onl_cgform_head where is_deleted=0"})
	List<String> getAllTableName();
}
