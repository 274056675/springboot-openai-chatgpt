
package org.springblade.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.SysDict;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.model.DuplicateCheckVo;
import org.springblade.cgform.model.TreeSelectModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据字典 Mapper 接口
 *
 *
 * @since 2021-05-27
 */
public interface DictMapper extends BaseMapper<SysDict> {

     List<DictModel> queryTableDictItemsByCode(@Param("table") String table, @Param("text") String text, @Param("code") String code);

     List<DictModel> queryDictItemsByCode(@Param("code") String code);

     List<DictModel> queryTableDictItemsByCodeAndFilter(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("filterSql") String filterSql);

    List<TreeSelectModel> queryTreeList(@Param("query") Map<String, String> query, @Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("pidField") String pidField, @Param("pid") String pid, @Param("hasChildField") String hasChildField);

     List<DictModel> queryTableDictByKeys(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyArray") String[] keyArray);

    Long getIdByCode(String code);

    /**
     * 通过关键字查询出字典表
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    List<DictModel> queryTableDictItems(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyword") String keyword);


     Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);

     Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo);

	List<Map<String,Object>> getAllTenantList();
}
