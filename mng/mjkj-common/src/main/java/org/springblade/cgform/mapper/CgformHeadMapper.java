
package org.springblade.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.CgformHead;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 *  Mapper 接口
 *
 *
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
