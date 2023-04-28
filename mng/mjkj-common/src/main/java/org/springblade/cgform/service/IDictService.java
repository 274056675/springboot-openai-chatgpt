
package org.springblade.cgform.service;

import org.springblade.cgform.entity.SysDict;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.model.DuplicateCheckVo;
import org.springblade.cgform.model.TreeSelectModel;
import org.springblade.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 数据字典 服务类
 *
 *
 * @since 2021-05-27
 */
public interface IDictService extends BaseService<SysDict> {

    /**
     * 13获取表数据字典
     * @param table
     * @param text
     * @param code
     * @return
     */
    List<DictModel> queryTableDictItemsByCode(String table, String text, String code);

    /**
     * 10获取数据字典
     * @param code
     * @return
     */
    public List<DictModel> queryDictItemsByCode(String code);

    /**
     * 16查询表字典 支持过滤数据
     * @param table
     * @param text
     * @param code
     * @param filterSql
     * @return
     */
    public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql);

    List<TreeSelectModel> queryTreeList(Map<String, String> query, String table, String text, String code, String pidField, String pid, String hasChildField);

    List<String> queryTableDictByKeys(String table, String text, String code, String keys);

    Long getIdByCode(String code);

    @Deprecated
     List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql);

    /**
     * 通过关键字查询字典表
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    List<DictModel> queryTableDictItems(String table, String text, String code, String keyword);


     Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);
     Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo);

     List<Map<String,Object>> getAllTenantList();
}
