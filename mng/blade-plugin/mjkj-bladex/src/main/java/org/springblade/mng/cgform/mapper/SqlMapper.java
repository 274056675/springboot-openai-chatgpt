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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.mng.cgform.model.CommonEntity;
import org.springblade.mng.cgform.model.TreeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义查询sql，无租户id
 */
public interface SqlMapper {

    /**
     * 查询列表
     * @param sqlStr
     * @return
     */
    List<Map<String, Object>> queryListBySqlList(@Param("sqlStr") String sqlStr);


    /**
     * 查询分页
     * @param page
     * @param sqlStr
     * @return
     */
    IPage<Map<String, Object>> selectPageBySqlList(Page<Map<String, Object>> page, @Param("sqlStr") String sqlStr);

    /**
     * 新增
     * @param map
     */
    void executeInsertSQL(Map<String, Object> map);



	/**
	 * 批量新增
	 * @param map
	 */
	void executeInsertBatchSQL(String fieldStrList,List<Map<String, Object>> mapList);

	/**
     * 修改
     * @param map
     */
    void executeUpdatetSQL(Map<String, Object> map);

    /**
     * 保存数据
     * @param sqlStr
     */
    void saveFormData(@Param("sqlStr") String sqlStr);

    /**
     * 更新数据
     * @param sqlStr
     */
    void editFormData(@Param("sqlStr") String sqlStr);

    /**
     * 删除数据
     * @param sqlStr
     */
    void deleteAutoList(@Param("sqlStr") String sqlStr);

    //真实删除
	void deleteList(@Param("sqlStr") String sqlStr);

    /**
     * 查询单个数据
     * @param sqlStr
     * @return
     */
    Map<String, Object> queryFormData(@Param("sqlStr") String sqlStr);

    /**
     * 查询数据总条数
     * @param sqlStr
     * @return
     */
    Integer queryCountBySql(@Param("sqlStr") String sqlStr);

    /**
     * 查询数据列表
     * @param sqlStr
     * @return
     */
    List<Map<String, Object>> queryListData(@Param("sqlStr") String sqlStr);

    /**
     *
     * @param entity
     * @return
     */
    List<TreeModel> queryDataListByLinkDown(@Param("query") CommonEntity entity);



}
