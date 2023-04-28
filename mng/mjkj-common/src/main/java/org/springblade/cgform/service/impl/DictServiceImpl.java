
package org.springblade.cgform.service.impl;

import org.springblade.cgform.entity.SysDict;
import org.springblade.cgform.mapper.DictMapper;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.model.DuplicateCheckVo;
import org.springblade.cgform.model.TreeSelectModel;
import org.springblade.cgform.model.query.QueryGenerator;
import org.springblade.cgform.service.IDictService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tenant.annotation.TenantIgnore;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据字典 服务实现类
 *
 *
 * @since 2021-05-27
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, SysDict> implements IDictService {

    @Override
    public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
        if(table.indexOf("#{")>=0){
            table = QueryGenerator.getSqlRuleValue(table);
        }
        return baseMapper.queryTableDictItemsByCode(table, text, code);
    }

    @Override
    public List<DictModel> queryDictItemsByCode(String code) {
        return baseMapper.queryDictItemsByCode(code);
    }

    @Override
    public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql) {
        return baseMapper.queryTableDictItemsByCodeAndFilter(table,text,code,filterSql);
    }

    @Override
    public List<TreeSelectModel> queryTreeList(Map<String, String> query, String table, String text, String code, String pidField, String pid, String hasChildField) {
        return baseMapper.queryTreeList(query,table, text, code, pidField, pid,hasChildField);
    }

    /**
     * 通过查询指定table的 text code 获取字典，包含text和value
     * dictTableCache采用redis缓存有效期10分钟
     * @param table
     * @param text
     * @param code
     * @param keys (逗号分隔)
     * @return
     */
    @Override
    public List<String> queryTableDictByKeys(String table, String text, String code, String keys) {
        if(Func.isEmpty(keys)){
            return null;
        }
        String[] keyArray = keys.split(",");
        List<DictModel> dictsList = baseMapper.queryTableDictByKeys(table, text, code, keyArray);
        List<String> resultList = new ArrayList<>(dictsList.size());
        // 查询出来的顺序可能是乱的，需要排个序
        for (String key : keyArray) {
            for (DictModel dict : dictsList) {
                if (key.equals(dict.getValue())) {
                    resultList.add(dict.getText());
                    break;
                }
            }
        }
        return resultList;
    }

    @Override
    public  Long getIdByCode(String code){
        return baseMapper.getIdByCode(code);
    }

    @Override
    public List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql) {
        return baseMapper.queryTableDictItemsByCodeAndFilter(table,text,code,filterSql);
    }

    /**
     * 通过关键字查询字典表
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    @Deprecated
    public List<DictModel> queryTableDictItems(String table, String text, String code,String keyword){
        return baseMapper.queryTableDictItems(table,text,code,"%"+keyword+"%");
    }


    public Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo){
        return baseMapper.duplicateCheckCountSql(duplicateCheckVo);
    }
    public Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo){
        return baseMapper.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
    }

    @TenantIgnore
    @Override
	public List<Map<String,Object>> getAllTenantList(){
		return baseMapper.getAllTenantList();
	}

}
