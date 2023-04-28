
package org.springblade.cgform.mapper;

import org.springblade.cgform.model.KvModel;

import java.util.List;
import java.util.Map;

/**
 * 公共sql
 */
public interface BaseSqlMapper {

	int getProcessingProgressTotal(String tableName,String batchCode);

	List<Long> getAdminIdList();

	//查询公共接口
	List<Map<String, Object>> baseSelectSqlList(Map<String, Object> map);

	//公共新增接口
	Long baseIntegerSql(Map<String, Object> map);

	//公共修改接口
	void baseUpdateSql(Map<String, Object> map);

	//公共删除接口
	void baseDeleteSql(Map<String, Object> map);

	//查询一列
	List<String> baseSelectByOneColumnSql(Map<String, Object> map);

	//获取模型列表
	List<KvModel> getKvModelList(Map<String, Object> map);

	//查询数据字典
	String getSysDictItem(Long sysDictId, Integer type);

}
