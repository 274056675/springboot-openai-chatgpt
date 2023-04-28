
package org.springblade.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springblade.cgform.entity.CgformEnhanceSql;

import java.util.List;
import java.util.Map;

/**
 * sql增强 Mapper 接口
 *
 *
 * @since 2021-05-22
 */
public interface CgformEnhanceSqlMapper extends BaseMapper<CgformEnhanceSql> {

    void executeDDL(@Param("sqlStr") String sqlStr);

	//查询多条语句
	List<Map<String,Object>> getListData(@Param("sqlStr") String sqlStr);

    //查询多条语句
	Page<Map<String, Object>> getListDataPage(@Param("page") Page<Map<String, Object>> page,Map<String, Object> params, @Param("sqlStr") String sqlStr);

	//执行sql
	Page<Map<String, Object>> executeSql(@Param("page") Page<Map<String, Object>> page,@Param("executeSql") String executeSql,@Param("params")Map<String, Object> params);
}
