
package org.springblade.system.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.datascope.model.DataScopeModel;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.feign.IDataScopeClient;

import java.util.List;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 数据权限缓存
 *
 *
 */
public class DataScopeCache {

	private static final String SCOPE_CACHE_CODE = "dataScope:code:";
	private static final String SCOPE_CACHE_CLASS = "dataScope:class:";
	private static final String DEPT_CACHE_ANCESTORS = "dept:ancestors:";

	private static IDataScopeClient dataScopeClient;

	private static IDataScopeClient getDataScopeClient() {
		if (dataScopeClient == null) {
			dataScopeClient = SpringUtil.getBean(IDataScopeClient.class);
		}
		return dataScopeClient;
	}

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	public static DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, DataScopeModel.class, Boolean.FALSE);
		if (dataScope == null || !dataScope.getSearched()) {
			dataScope = getDataScopeClient().getDataScopeByMapper(mapperId, roleId);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, dataScope);
		}
		return StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	public static DataScopeModel getDataScopeByCode(String code) {
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, code, DataScopeModel.class, Boolean.FALSE);
		if (dataScope == null || !dataScope.getSearched()) {
			dataScope = getDataScopeClient().getDataScopeByCode(code);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, code, dataScope);
		}
		return StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	public static List<Long> getDeptAncestors(Long deptId) {
		List ancestors = CacheUtil.get(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, List.class);
		if (CollectionUtil.isEmpty(ancestors)) {
			ancestors = getDataScopeClient().getDeptAncestors(deptId);
			CacheUtil.put(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, ancestors);
		}
		return ancestors;
	}
}
