package org.springblade.mng.cgform.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mng.cgform.model.KvModel;

import java.util.List;
import java.util.Map;

/**
 * 魔晶公共定制
 */
public interface IMjkjBaseSqlService {
	/*
	* customFieldList 为自定义列
	*
	* */

	//获取某个表的所有数据
	List<Map<String, Object>> getDataByTable(String tableName);
	List<Map<String, Object>> getDataByTable(String tableName,List<String> customFieldList);
	List<Map<String, Object>> getDataByTableRedis(String tableName,Long time);//走缓存，秒

	//根据id获取某一个表的一条数据
	Map<String, Object> getTableById(String tableName, String id);
	Map<String, Object> getTableByIdL(String tableName, Long id);
	Map<String, Object> getTableById(String tableName, String id,List<String> customFieldList);//自定义列
	Map<String, Object> getTableByIdRedis(String tableName, String id,Long time);//走缓存，秒


	//根据id获取某一个表的数据
	List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue);
	List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue,List<String> customFieldList);


	//根据id获取某一个表的数据  排序
	List<Map<String, Object>> getDataListByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue,String orderByField,String orderStr);
	List<Map<String, Object>> getDataListByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue,String orderByField,String orderStr,List<String> customFieldList);


	//获取一条数据
	Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue);
	Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue,List<String> customFieldList);
	Map<String, Object> getDataOneByFieldRedis(String tableName, String whereFieldName, Object whereFieldValue,Long time);

	//获取一条数据 排序
	Map<String, Object> getDataOneByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue,String orderByField,String orderStr);
	Map<String, Object> getDataOneByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue,String orderByField,String orderStr,List<String> customFieldList);


	//根据id获取某一个表的数据 type=A:全部  L：左边模糊  R右模糊
	List<Map<String, Object>> getDataListByLike(String tableName, String whereFieldName, Object whereFieldValue,String type);
	List<Map<String, Object>> getDataListByLike(String tableName, String whereFieldName, Object whereFieldValue,String type,List<String> customFieldList);

	//根据id获取某一个表的数据
	List<Map<String, Object>> getDataListByIn(String tableName, String whereFieldName, List<Object> whereFieldValue);
	List<Map<String, Object>> getDataListByIn(String tableName, String whereFieldName, List<Object> whereFieldValue,List<String> customFieldList);

	//获取数据字典 是否开启缓存 true=开启缓存  false = 不开启缓存
	List<KvModel> getSysDictItemList(String dictCode, Boolean redisFlag);
	//获取数字字段值
	String getSysDictItemValue(String dictCode,String itemValue,Boolean redisFlag);

	String getSysDictItemValueByText(String dictCode,String itemText,Boolean redisFlag);

	//将表数据封装为map
	Map<String,Map<String,Object>> getData2Map(String tableName,String key,Boolean redisFlag);
	//------------------查询封装已完成---------------------------

	//单个新增
	Long baseInsertData(String tableName, Map<String, Object> dataMap);

	//不对操作人进行封装
	Long baseSimpleIntegerSql(String tableName, Map<String, Object> dataMap);

	//单个修改
	void baseUpdateData(String tableName,Map<String, Object> map,String id);
	void baseUpdateDataLong(String tableName,Map<String, Object> map,Long id);

	void baseUpdateDataWhere(String tableName,Map<String, Object> map,String whereCol, String whereVal);

	void baseUpdateDataTenantIgnore(String tableName,Map<String, Object> map,String id);


	//公共删除-逻辑
	Integer baseDeleteSqlStr(String tableName, String id);
	Integer baseDeleteSql(String tableName, Long id);
	//真实删除
	Integer baseRealDeleteSql(String tableName, Long id);

	//	自定义公共删除-逻辑
	Integer baseZdyDeleteSql(String tableName, String whereFieldName, Long id);
	//	自定义公共删除-逻辑
	Integer baseZdyDeleteSql(String tableName, String whereFieldName, String id);

	//--------------其他--------------------
	List<Long> getAdminIdList();
	//获取处理进度
	int getProcessingProgress(String tableName,String batchCode);

	//获取总处理进度
	int getProcessingProgressTotal(String tableName,String batchCode);

	//获取授权
	List<Map<String,Object>> getBladeUserOauth(String uuid,String source);

	//获取授权
	List<Map<String,Object>> getBladeUserOauthByUserId(Long userId);

	//解绑
	void unBindBladeUserOauth(Long id);

	//根据表属性获取所有数据 多条件
	<T> List<Map<String, T>> getDataListByFieldParams(String tableName, Wrapper<T> wrapper);

	<T> Map<String, T> getDataOneByFieldParams(String tableName, Wrapper<T> wrapper);

	//根据表属性获取所有数据 多条件 分页
	<T> IPage<Map<String, T>> getDataIPageByFieldParams(String tableName, IPage page, Wrapper<T> wrapper);

	//--------------本次定制---------------
	//获取所有全仓借款用户
	List<String> getAllBorrowMemberId();

	//获取所有合约存在全仓仓位的用户
	List<String> getAllContractMemberId();

	//获取我的所有全仓交易对
	List<String> getMyAllContractSymbolName(String memberId);


	//获取后台语言
	String getMngLanguage(String str);

	//获取后台语言
	String getMngLanguage(String lang,String str);
}
