package org.springblade.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springblade.cgform.mapper.MjkjBaseSqlMapper;
import org.springblade.cgform.model.KvModel;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.util.DataTypeUtil;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.annotation.TenantIgnore;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共调用
 */
@Service
public class MjkjBaseSqlServiceImpl implements IMjkjBaseSqlService {


	@Autowired
	private MjkjBaseSqlMapper mjkjBaseSqlMapper;

	@Autowired
	private BladeRedis bladeRedis;


	private String getSelectField() {
		return "*";
	}

	private String getSelectField(List<String> customFieldList) {
		String selectField = "";
		if (Func.isNotEmpty(customFieldList)) {
			for (int i = 0; i < customFieldList.size(); i++) {
				String field = customFieldList.get(i);
				if (i == 0) {
					selectField = field;
				} else {
					selectField += "," + field;
				}
			}
		} else {
			selectField = "*";
		}
		return selectField;
	}

	//获取某个表的所有数据
	@Override
	public List<Map<String, Object>> getDataByTable(String tableName) {
		String selectField = this.getSelectField();
		return mjkjBaseSqlMapper.getDataByTable(selectField, tableName);
	}


	//自定义字段
	@Override
	public List<Map<String, Object>> getDataByTable(String tableName, List<String> customFieldList) {
		return mjkjBaseSqlMapper.getDataByTable(this.getSelectField(customFieldList), tableName);
	}

	@Override
	public List<Map<String, Object>> getDataByTableRedis(String tableName, Long time) {
		String redisKey = "MJKJBASE_TABLE_ALL:" + tableName;
		if (bladeRedis.exists(redisKey)) {//缓存存在
			List<Map<String, Object>> list = (List<Map<String, Object>>) bladeRedis.get(redisKey);
			if (Func.isNotEmpty(list)) {
				return list;
			}
		} //缓存不存在
		if (Func.isEmpty(time)) {
			time = 30L;//默认30秒
		}
		List<Map<String, Object>> dataList = mjkjBaseSqlMapper.getDataByTable(this.getSelectField(), tableName);
		if (Func.isNotEmpty(dataList)) {
			bladeRedis.setEx(redisKey, dataList, time);//写入缓存
		}
		return dataList;


	}


	//根据id获取某一个表的一条数据
	@Override
	public Map<String, Object> getTableById(String tableName, String id) {
		return mjkjBaseSqlMapper.getTableById(this.getSelectField(), tableName, id);
	}

	@Override
	public Map<String, Object> getTableByIdL(String tableName, Long id) {
		return mjkjBaseSqlMapper.getTableByIdL(this.getSelectField(), tableName, id);
	}

	@Override
	public Map<String, Object> getTableById(String tableName, String id, List<String> customFieldList) {
		return mjkjBaseSqlMapper.getTableById(this.getSelectField(customFieldList), tableName, id);
	}

	@Override
	public Map<String, Object> getTableByIdRedis(String tableName, String id, Long time) {
		String redisKey = "MJKJBASE_TABLE_ID:" + tableName + ":id_" + id;
		if (bladeRedis.exists(redisKey)) {//缓存存在
			String str = Func.toStr(bladeRedis.get(redisKey));
			Map<String, Object> resultMap = JsonUtil.parse(str, Map.class);
			if (Func.isNotEmpty(resultMap)) {
				return resultMap;
			}
		}//缓存不存在
		if (Func.isEmpty(time)) {
			time = 30L;//默认30秒
		}
		Map<String, Object> resultMap = mjkjBaseSqlMapper.getTableById(this.getSelectField(), tableName, id);
		if (Func.isNotEmpty(resultMap)) {
			bladeRedis.setEx(redisKey, JsonUtil.toJson(resultMap), time);//写入缓存
		}
		return resultMap;

	}


	//根据id获取某一个表的一条数据 进行过滤
	@Override
	public List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue) {
		return mjkjBaseSqlMapper.getDataListByField(this.getSelectField(), tableName, whereFieldName, whereFieldValue);
	}

	@Override
	public List<Map<String, Object>> getDataListByField(String tableName, String whereFieldName, Object whereFieldValue, List<String> customFieldList) {
		return mjkjBaseSqlMapper.getDataListByField(this.getSelectField(customFieldList), tableName, whereFieldName, whereFieldValue);
	}


	//根据id获取某一个表的一条数据 进行删除过滤 排序
	@Override
	public List<Map<String, Object>> getDataListByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue, String orderByField, String orderStr) {
		if (Func.isEmpty(orderStr)) {
			orderStr = "DESC";
		}
		return mjkjBaseSqlMapper.getDataListByFieldOrderBy(this.getSelectField(), tableName, whereFieldName, whereFieldValue, orderByField, orderStr);
	}

	@Override
	public List<Map<String, Object>> getDataListByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue, String orderByField, String orderStr, List<String> customFieldList) {
		if (Func.isEmpty(orderStr)) {
			orderStr = "DESC";
		}
		return mjkjBaseSqlMapper.getDataListByFieldOrderBy(this.getSelectField(customFieldList), tableName, whereFieldName, whereFieldValue, orderByField, orderStr);
	}


	//获取一条数据
	@Override
	public Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue) {
		List<Map<String, Object>> dataList = this.getDataListByField(tableName, whereFieldName, whereFieldValue);
		if (Func.isEmpty(dataList)) {
			return null;
		}
		return dataList.get(0);
	}

	@Override
	public Map<String, Object> getDataOneByField(String tableName, String whereFieldName, Object whereFieldValue, List<String> customFieldList) {
		List<Map<String, Object>> dataList = this.getDataListByField(tableName, whereFieldName, whereFieldValue, customFieldList);
		if (Func.isEmpty(dataList)) {
			return null;
		}
		return dataList.get(0);
	}

	@Override
	public Map<String, Object> getDataOneByFieldRedis(String tableName, String whereFieldName, Object whereFieldValue, Long time) {
		String redisKey = "MJKJBASE_TABLE_FIELD:" + tableName + ":whereFieldName_" + whereFieldName + ":whereFieldValue" + JsonUtil.toJson(whereFieldValue);
		if (bladeRedis.exists(redisKey)) {//缓存存在
			String str = Func.toStr(bladeRedis.get(redisKey));
			Map<String, Object> resultMap = JsonUtil.parse(str, Map.class);
			if (Func.isNotEmpty(resultMap)) {
				return resultMap;
			}

		}//缓存不存在
		if (Func.isEmpty(time)) {
			time = 30L;//默认30秒
		}
		List<Map<String, Object>> dataList = this.getDataListByField(tableName, whereFieldName, whereFieldValue, null);
		if (Func.isEmpty(dataList)) {
			return null;
		}
		Map<String, Object> resultMap = dataList.get(0);
		bladeRedis.setEx(redisKey, JsonUtil.toJson(resultMap), time);//写入缓存
		return resultMap;
	}

	//获取一条数据 排序
	@Override
	public Map<String, Object> getDataOneByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue, String orderByField, String orderStr) {
		if (Func.isEmpty(orderStr)) {
			orderStr = "DESC";
		}
		List<Map<String, Object>> dataList = this.getDataListByFieldOrderBy(tableName, whereFieldName, whereFieldValue, orderByField, orderStr);
		if (Func.isEmpty(dataList)) {
			return null;
		}
		return dataList.get(0);
	}

	//获取一条数据 排序
	@Override
	public Map<String, Object> getDataOneByFieldOrderBy(String tableName, String whereFieldName, Object whereFieldValue, String orderByField, String orderStr, List<String> customFieldList) {
		if (Func.isEmpty(orderStr)) {
			orderStr = "DESC";
		}
		List<Map<String, Object>> dataList = this.getDataListByFieldOrderBy(tableName, whereFieldName, whereFieldValue, orderByField, orderStr, customFieldList);
		if (Func.isEmpty(dataList)) {
			return null;
		}
		return dataList.get(0);
	}

	//根据id获取某一个表的一条数据 type=ALL:全部  L：左边模糊  R右模糊
	@Override
	public List<Map<String, Object>> getDataListByLike(String tableName, String whereFieldName, Object whereFieldValue, String type) {
		if (Func.isEmpty(type)) {
			type = "ALL";
		}
		return mjkjBaseSqlMapper.getDataListByLike(this.getSelectField(), tableName, whereFieldName, whereFieldValue, type);
	}

	@Override
	public List<Map<String, Object>> getDataListByLike(String tableName, String whereFieldName, Object whereFieldValue, String type, List<String> customFieldList) {
		if (Func.isEmpty(type)) {
			type = "ALL";
		}
		return mjkjBaseSqlMapper.getDataListByLike(this.getSelectField(customFieldList), tableName, whereFieldName, whereFieldValue, type);
	}

	@Override
	public List<Map<String, Object>> getDataListByIn(String tableName, String whereFieldName, List<Object> whereFieldValue) {
		return mjkjBaseSqlMapper.getDataListByIn(this.getSelectField(), tableName, whereFieldName, whereFieldValue);
	}

	@Override
	public List<Map<String, Object>> getDataListByIn(String tableName, String whereFieldName, List<Object> whereFieldValue, List<String> customFieldList) {
		return mjkjBaseSqlMapper.getDataListByIn(this.getSelectField(customFieldList), tableName, whereFieldName, whereFieldValue);
	}

	//获取数据字典
	@Transactional
	@Override
	public List<KvModel> getSysDictItemList(String dictCode, Boolean redisFlag) {
		String redisKey = "MJKJBASE_DICTITEM_ALL:" + dictCode;
		if (bladeRedis.exists(redisKey) && redisFlag) {//开启缓存
			List<KvModel> list = (List<KvModel>) bladeRedis.get(redisKey);
			if (Func.isNotEmpty(list)) {
				return list;
			}
		}

		List<KvModel> list = mjkjBaseSqlMapper.getSysDictItemList(dictCode);
		if (Func.isNotEmpty(list) && redisFlag) {
			bladeRedis.setEx(redisKey, list, 30L);//30秒
		}
		return list;
	}

	//获取数字字段值
	@Override
	@Transactional
	public String getSysDictItemValue(String dictCode, String itemValue, Boolean redisFlag) {
		String redisKey = "MJKJBASE_DICTITEM_VALUE:CODE_" + dictCode + ":VALUE_" + itemValue;
		if (bladeRedis.exists(redisKey) && redisFlag) {//开启缓存
			String str = (String) bladeRedis.get(redisKey);
			if (Func.isNotEmpty(str)) {
				return str;
			}
		}

		String itemText = mjkjBaseSqlMapper.getSysDictItemValue(dictCode, itemValue);
		if (Func.isNotEmpty(itemText) && redisFlag) {
			bladeRedis.setEx(redisKey, itemText, 30L);//30秒
		}
		return itemText;
	}

	//获取数字字段值
	@Override
	@Transactional
	public String getSysDictItemValueByText(String dictCode, String itemText, Boolean redisFlag) {
		String redisKey = "MJKJBASE_DICTITEM_VALUE:CODE_" + dictCode + ":TEXT_" + itemText;
		if (bladeRedis.exists(redisKey) && redisFlag) {//开启缓存
			String str = (String) bladeRedis.get(redisKey);
			if (Func.isNotEmpty(str)) {
				return str;
			}
		}

		String itemValue = mjkjBaseSqlMapper.getSysDictItemValueByText(dictCode, itemText);
		if (Func.isNotEmpty(itemValue) && redisFlag) {
			bladeRedis.setEx(redisKey, itemValue, 30L);//30秒
		}
		return itemValue;
	}


	//将表数据封装为map
	@Override
	public Map<String, Map<String, Object>> getData2Map(String tableName, String key, Boolean redisFlag) {
		String redisKey = "MJKJBASE_DATA2MAP:tableName_" + tableName + ":key_" + key;
		if (bladeRedis.exists(redisKey) && redisFlag) {//开启缓存
			Map<String, Map<String, Object>> resultMap = (Map<String, Map<String, Object>>) bladeRedis.get(redisKey);
			if (Func.isNotEmpty(resultMap)) {
				return resultMap;
			}
		}
		List<Map<String, Object>> dataList = mjkjBaseSqlMapper.getDataByTable(this.getSelectField(), tableName);
		Map<String, Map<String, Object>> resultMap = new HashMap<>();
		if (Func.isNotEmpty(dataList)) {
			dataList.forEach(map -> {
				resultMap.put(Func.toStr(map.get(key)), map);
			});
		}
		if (Func.isNotEmpty(resultMap) && redisFlag) {//写入缓存
			bladeRedis.setEx(redisKey, resultMap, 30L);//30秒
		}
		return resultMap;
	}

	//初始化默认值
	private Map<String, Object> initMap(Map<String, Object> map) {
		if (Func.isEmpty(map)) {
			map = new LinkedHashMap<>();
		}
		Object id = map.get("id");
		if (Func.isEmpty(id)) {
			map.put("id", IdWorker.getId());
		}
		/*if (Func.isEmpty(MjkjUtils.getMap2Str(map, "status"))) {
			map.put("status", 1);
		}*/
		String is_deleted = MjkjUtils.getMap2Str(map, "is_deleted");
		if (Func.isEmpty(is_deleted)) {
			map.put("is_deleted", "0");
		}
		map.put("create_time", DateUtil.now());
		if (Func.isNotEmpty(map.get("tenant_id"))) {
			map.put("tenant_id", MjkjUtils.getMap2Str(map, "tenant_id"));
		}

		BladeUser user = AuthUtil.getUser();
		if (Func.isNotEmpty(user)) {
			String tenantId = user.getTenantId();
			Long userId = user.getUserId();
			String deptId = Func.toStrList(user.getDeptId()).get(0);
			if (Func.isEmpty(map.get("tenant_id"))) {
				map.put("tenant_id", tenantId);
			}

			if (Func.isEmpty(map.get("create_user"))) {
				map.put("create_user", userId);
			}

			if (Func.isEmpty(map.get("create_dept"))) {
				map.put("create_dept", deptId);
			}
		} else {
			if (Func.isNotEmpty(map.get("create_dept"))) {
				String deptId = MjkjUtils.getMap2Str(map, "create_dept");
				deptId = Func.toStrList(deptId).get(0);
				map.put("create_dept", deptId);
			}
		}
		return map;
	}

	//单个新增
	@Override
	public Long baseInsertData(String tableName, Map<String, Object> map) {
		map = this.initMap(map);

		String feildSql = "";
		String dataSql = "";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			feildSql += field + ",";
			dataSql += DataTypeUtil.getSql(field, value) + ",";
		}
		if (feildSql.endsWith(",")) {
			feildSql = feildSql.substring(0, feildSql.length() - 1);
			dataSql = dataSql.substring(0, dataSql.length() - 1);
		}
		String sql = "insert into " + tableName + "(" + feildSql + ") values (" + dataSql + ")";

		map.put("select_sql", sql);
		System.out.println(map.get(sql));
		Long result = mjkjBaseSqlMapper.baseInsertSql(map);
		return result;
	}

	//不对操作人进行封装
	@Override
	public Long baseSimpleIntegerSql(String tableName, Map<String, Object> dataMap) {
		String feildSql = "";
		String dataSql = "";
		for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			feildSql += field + ",";
			dataSql += DataTypeUtil.getSql(field, value) + ",";
		}
		if (feildSql.endsWith(",")) {
			feildSql = feildSql.substring(0, feildSql.length() - 1);
			dataSql = dataSql.substring(0, dataSql.length() - 1);
		}
		String sql = "insert into " + tableName + "(" + feildSql + ") values (" + dataSql + ")";

		dataMap.put("select_sql", sql);
		Long result = mjkjBaseSqlMapper.baseInsertSql(dataMap);
		return result;
	}

	//单个修改
	@Override
	public void baseUpdateData(String tableName, Map<String, Object> map, String id) {

		map.put("update_time", DateUtil.now());
		BladeUser user = AuthUtil.getUser();
		if (Func.isNotEmpty(user)) {
			Long userId = user.getUserId();
			map.put("update_user", userId);
		}


		String updateSql = "update " + tableName + " set ";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			if (Func.equals("id", field)) {
				continue;
			}

			updateSql += field + " = " + DataTypeUtil.getSql(field, value) + ",";
		}
		if (updateSql.endsWith(",")) {
			updateSql = updateSql.substring(0, updateSql.length() - 1);
		}
		String sql = updateSql + " where id ='" + id + "'";
		map.put("select_sql", sql);
		mjkjBaseSqlMapper.baseUpdateSql(map);
	}

	//单个修改
	@Override
	public void baseUpdateDataLong(String tableName, Map<String, Object> map, Long id) {

		map.put("update_time", DateUtil.now());
		BladeUser user = AuthUtil.getUser();
		if (Func.isNotEmpty(user)) {
			Long userId = user.getUserId();
			map.put("update_user", userId);
		}


		String updateSql = "update " + tableName + " set ";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			if (Func.equals("id", field)) {
				continue;
			}

			updateSql += field + " = " + DataTypeUtil.getSql(field, value) + ",";
		}
		if (updateSql.endsWith(",")) {
			updateSql = updateSql.substring(0, updateSql.length() - 1);
		}
		String sql = updateSql + " where id =" + id;
		map.put("select_sql", sql);
		mjkjBaseSqlMapper.baseUpdateSql(map);
	}

	//单个修改
	@Override
	public void baseUpdateDataWhere(String tableName, Map<String, Object> map, String whereCol, String whereVal) {

		map.put("update_time", DateUtil.now());
		BladeUser user = AuthUtil.getUser();
		if (Func.isNotEmpty(user)) {
			Long userId = user.getUserId();
			map.put("update_user", userId);
		}


		String updateSql = "update " + tableName + " set ";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			if (Func.equals("id", field)) {
				continue;
			}

			updateSql += field + " = " + DataTypeUtil.getSql(field, value) + ",";
		}
		if (updateSql.endsWith(",")) {
			updateSql = updateSql.substring(0, updateSql.length() - 1);
		}
		String sql = updateSql + " where " + whereCol + " = '" + whereVal + "'";
		map.put("select_sql", sql);
		mjkjBaseSqlMapper.baseUpdateSql(map);
	}

	@TenantIgnore
	@Override
	public void baseUpdateDataTenantIgnore(String tableName, Map<String, Object> map, String id) {
		this.baseUpdateData(tableName, map, id);
	}

	//公共删除
	@Override
	public Integer baseDeleteSqlStr(String tableName, String id) {
		return mjkjBaseSqlMapper.baseDeleteSqlStr(tableName, id);
	}

	@Override
	public Integer baseDeleteSql(String tableName, Long id) {
		return mjkjBaseSqlMapper.baseDeleteSql(tableName, id);
	}

	//真实删除
	@Override
	public Integer baseRealDeleteSql(String tableName, Long id) {
		return mjkjBaseSqlMapper.baseRealDeleteSql(tableName, id);
	}

	//	自定义公共删除
	@Override
	public Integer baseZdyDeleteSql(String tableName, String whereFieldName, Long id) {
		return mjkjBaseSqlMapper.baseZdyDeleteSql(tableName, whereFieldName, id);
	}

	//	自定义公共删除 string
	@Override
	public Integer baseZdyDeleteSql(String tableName, String whereFieldName, String id) {
		return mjkjBaseSqlMapper.baseZdyDeleteSql(tableName, whereFieldName, id);
	}

	@Override
	public List<Long> getAdminIdList() {
		return mjkjBaseSqlMapper.getAdminIdList();
	}

	//获取处理进度
	@Override
	public int getProcessingProgress(String tableName, String batchCode) {
		return mjkjBaseSqlMapper.getProcessingProgress(tableName, batchCode);
	}

	//获取总处理进度
	@Override
	public int getProcessingProgressTotal(String tableName, String batchCode) {
		return mjkjBaseSqlMapper.getProcessingProgressTotal(tableName, batchCode);
	}

	//获取授权
	@TenantIgnore
	@Override
	public List<Map<String, Object>> getBladeUserOauth(String uuid, String source) {
		return mjkjBaseSqlMapper.getBladeUserOauth(uuid, source);
	}

	//获取授权
	@Override
	public List<Map<String, Object>> getBladeUserOauthByUserId(Long userId) {
		return mjkjBaseSqlMapper.getBladeUserOauthByUserId(userId);
	}

	//解绑
	@Override
	public void unBindBladeUserOauth(Long id) {
		mjkjBaseSqlMapper.unBindBladeUserOauth(id);
	}

	// 根据表属性获取所有数据 多条件
	@Override
	public <T> List<Map<String, T>> getDataListByFieldParams(String tableName, Wrapper<T> wrapper) {
		if (Func.isEmpty(tableName)) {
			return null;
		}
		return mjkjBaseSqlMapper.getDataListByWrapper(tableName, wrapper);
	}

	// 根据表属性获取所有数据 多条件
	@Override
	public <T> Map<String, T> getDataOneByFieldParams(String tableName, Wrapper<T> wrapper) {
		if (Func.isEmpty(tableName)) {
			return new HashMap<>();
		}
		List<Map<String, T>> dataMapList = mjkjBaseSqlMapper.getDataListByWrapper(tableName, wrapper);
		if (Func.isEmpty(dataMapList)) {
			return new HashMap<>();
		}
		return dataMapList.get(0);
	}


	// 根据表属性获取所有数据 多条件 分页
	@Override
	public <T> IPage<Map<String, T>> getDataIPageByFieldParams(String tableName, IPage page, Wrapper<T> wrapper) {
		if (Func.isEmpty(tableName)) {
			return null;
		}
		return mjkjBaseSqlMapper.getDataListByWrapper(tableName, page, wrapper);
	}

}
