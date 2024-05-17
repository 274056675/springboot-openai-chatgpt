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
import org.springblade.mng.cgform.entity.MjkjBladeDept;


import java.util.List;

/**
 * DeptMapper 接口
 *
 * @author Chill
 */
public interface DeptMapper extends BaseMapper<MjkjBladeDept> {


	List<Long> getUserDeptList(Long userId);

	Integer getMaxSort(Long parentId);

	Long getDeptIdByYytId(String yytId);

	//添加部门用户中间表信息
	Integer insertDeptUser(String userId,String deptId);

	/**
	 * 根据部门获取人员
	 * @param deptId
	 * @return
	 */
	String getRenYuanByDeptId(String deptId);
}
