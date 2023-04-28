
package org.springblade.system.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.system.entity.*;
import org.springblade.system.feign.ISysClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 系统缓存
 *
 *
 */
public class SysCache {
	private static final String MENU_ID = "menu:id:";
	private static final String DEPT_ID = "dept:id:";
	private static final String DEPT_NAME = "dept:name:";
	private static final String DEPT_NAME_FUZZY = "dept:nameFuzzy:";
	private static final String DEPT_NAME_ID = "deptName:id:";
	private static final String DEPT_NAMES_ID = "deptNames:id:";
	private static final String DEPT_CHILD_ID = "deptChild:id:";
	private static final String DEPT_CHILDIDS_ID = "deptChildIds:id:";
	private static final String POST_ID = "post:id:";
	private static final String POST_NAME = "post:name:";
	private static final String POST_NAME_FUZZY = "post:nameFuzzy:";
	private static final String POST_NAME_ID = "postName:id:";
	private static final String POST_NAMES_ID = "postNames:id:";
	private static final String ROLE_ID = "role:id:";
	private static final String ROLE_NAME = "role:name:";
	private static final String ROLE_NAME_ID = "roleName:id:";
	private static final String ROLE_NAMES_ID = "roleNames:id:";
	private static final String ROLE_ALIAS_ID = "roleAlias:id:";
	private static final String ROLE_ALIASES_ID = "roleAliases:id:";
	private static final String TENANT_ID = "tenant:id:";
	private static final String TENANT_TENANT_ID = "tenant:tenantId:";
	private static final String TENANT_PACKAGE_ID = "tenant:packageId:";

	private static ISysClient sysClient;

	private static ISysClient getSysClient() {
		if (sysClient == null) {
			sysClient = SpringUtil.getBean(ISysClient.class);
		}
		return sysClient;
	}

	/**
	 * 获取菜单
	 *
	 * @param id 主键
	 * @return 菜单
	 */
	public static Menu getMenu(Long id) {
		return CacheUtil.get(SYS_CACHE, MENU_ID, id, () -> {
			R<Menu> result = getSysClient().getMenu(id);
			return result.getData();
		});
	}

	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return 部门
	 */
	public static Dept getDept(Long id) {
		return CacheUtil.get(SYS_CACHE, DEPT_ID, id, () -> {
			R<Dept> result = getSysClient().getDept(id);
			return result.getData();
		});
	}

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	public static String getDeptIds(String tenantId, String deptNames) {
		return CacheUtil.get(SYS_CACHE, DEPT_NAME, tenantId + StringPool.DASH + deptNames, () -> {
			R<String> result = getSysClient().getDeptIds(tenantId, deptNames);
			return result.getData();
		});
	}

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名模糊查询
	 * @return 部门id
	 */
	public static String getDeptIdsByFuzzy(String tenantId, String deptNames) {
		return CacheUtil.get(SYS_CACHE, DEPT_NAME_FUZZY, tenantId + StringPool.DASH + deptNames, () -> {
			R<String> result = getSysClient().getDeptIdsByFuzzy(tenantId, deptNames);
			return result.getData();
		});
	}

	/**
	 * 获取部门名
	 *
	 * @param id 主键
	 * @return 部门名
	 */
	public static String getDeptName(Long id) {
		return CacheUtil.get(SYS_CACHE, DEPT_NAME_ID, id, () -> {
			R<String> result = getSysClient().getDeptName(id);
			return result.getData();
		});
	}


	/**
	 * 获取部门名集合
	 *
	 * @param deptIds 主键集合
	 * @return 部门名
	 */
	public static List<String> getDeptNames(String deptIds) {
		return CacheUtil.get(SYS_CACHE, DEPT_NAMES_ID, deptIds, () -> {
			R<List<String>> result = getSysClient().getDeptNames(deptIds);
			return result.getData();
		});
	}

	/**
	 * 获取子部门集合
	 *
	 * @param deptId 主键
	 * @return 子部门
	 */
	public static List<Dept> getDeptChild(Long deptId) {
		return CacheUtil.get(SYS_CACHE, DEPT_CHILD_ID, deptId, () -> {
			R<List<Dept>> result = getSysClient().getDeptChild(deptId);
			return result.getData();
		});
	}

	/**
	 * 获取子部门ID集合
	 *
	 * @param deptId 主键
	 * @return 子部门ID
	 */
	public static List<Long> getDeptChildIds(Long deptId) {
		if (deptId == null) {
			return null;
		}
		List<Long> deptIdList = CacheUtil.get(SYS_CACHE, DEPT_CHILDIDS_ID, deptId, List.class);
		if (deptIdList == null) {
			deptIdList = new ArrayList<>();
			List<Dept> deptChild = getDeptChild(deptId);
			if (deptChild != null) {
				List<Long> collect = deptChild.stream().map(Dept::getId).collect(Collectors.toList());
				deptIdList.addAll(collect);
			}
			deptIdList.add(deptId);
			CacheUtil.put(SYS_CACHE, DEPT_CHILDIDS_ID, deptId, deptIdList);
		}
		return deptIdList;
	}

	/**
	 * 获取岗位
	 *
	 * @param id 主键
	 * @return
	 */
	public static Post getPost(Long id) {
		return CacheUtil.get(SYS_CACHE, POST_ID, id, () -> {
			R<Post> result = getSysClient().getPost(id);
			return result.getData();
		});
	}

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return
	 */
	public static String getPostIds(String tenantId, String postNames) {
		return CacheUtil.get(SYS_CACHE, POST_NAME, tenantId + StringPool.DASH + postNames, () -> {
			R<String> result = getSysClient().getPostIds(tenantId, postNames);
			return result.getData();
		});
	}

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名模糊查询
	 * @return
	 */
	public static String getPostIdsByFuzzy(String tenantId, String postNames) {
		return CacheUtil.get(SYS_CACHE, POST_NAME_FUZZY, tenantId + StringPool.DASH + postNames, () -> {
			R<String> result = getSysClient().getPostIdsByFuzzy(tenantId, postNames);
			return result.getData();
		});
	}

	/**
	 * 获取岗位名
	 *
	 * @param id 主键
	 * @return 岗位名
	 */
	public static String getPostName(Long id) {
		return CacheUtil.get(SYS_CACHE, POST_NAME_ID, id, () -> {
			R<String> result = getSysClient().getPostName(id);
			return result.getData();
		});
	}

	/**
	 * 获取岗位名集合
	 *
	 * @param postIds 主键集合
	 * @return 岗位名
	 */
	public static List<String> getPostNames(String postIds) {
		return CacheUtil.get(SYS_CACHE, POST_NAMES_ID, postIds, () -> {
			R<List<String>> result = getSysClient().getPostNames(postIds);
			return result.getData();
		});
	}

	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	public static Role getRole(Long id) {
		return CacheUtil.get(SYS_CACHE, ROLE_ID, id, () -> {
			R<Role> result = getSysClient().getRole(id);
			return result.getData();
		});
	}

	/**
	 * 获取角色id
	 *
	 * @param tenantId  租户id
	 * @param roleNames 角色名
	 * @return
	 */
	public static String getRoleIds(String tenantId, String roleNames) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAME, tenantId + StringPool.DASH + roleNames, () -> {
			R<String> result = getSysClient().getRoleIds(tenantId, roleNames);
			return result.getData();
		});
	}

	/**
	 * 获取角色名
	 *
	 * @param id 主键
	 * @return 角色名
	 */
	public static String getRoleName(Long id) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAME_ID, id, () -> {
			R<String> result = getSysClient().getRoleName(id);
			return result.getData();
		});
	}

	/**
	 * 获取角色别名
	 *
	 * @param id 主键
	 * @return 角色别名
	 */
	public static String getRoleAlias(Long id) {
		return CacheUtil.get(SYS_CACHE, ROLE_ALIAS_ID, id, () -> {
			R<String> result = getSysClient().getRoleAlias(id);
			return result.getData();
		});
	}

	/**
	 * 获取角色名集合
	 *
	 * @param roleIds 主键集合
	 * @return 角色名
	 */
	public static List<String> getRoleNames(String roleIds) {
		return CacheUtil.get(SYS_CACHE, ROLE_NAMES_ID, roleIds, () -> {
			R<List<String>> result = getSysClient().getRoleNames(roleIds);
			return result.getData();
		});
	}

	/**
	 * 获取角色别名集合
	 *
	 * @param roleIds 主键集合
	 * @return 角色别名
	 */
	public static List<String> getRoleAliases(String roleIds) {
		return CacheUtil.get(SYS_CACHE, ROLE_ALIASES_ID, roleIds, () -> {
			R<List<String>> result = getSysClient().getRoleAliases(roleIds);
			return result.getData();
		});
	}

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	public static Tenant getTenant(Long id) {
		return CacheUtil.get(SYS_CACHE, TENANT_ID, id, () -> {
			R<Tenant> result = getSysClient().getTenant(id);
			return result.getData();
		});
	}

	/**
	 * 获取租户
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	public static Tenant getTenant(String tenantId) {
		return CacheUtil.get(SYS_CACHE, TENANT_TENANT_ID, tenantId, () -> {
			R<Tenant> result = getSysClient().getTenant(tenantId);
			return result.getData();
		});
	}

	/**
	 * 获取租户产品包
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	public static TenantPackage getTenantPackage(String tenantId) {
		return CacheUtil.get(SYS_CACHE, TENANT_PACKAGE_ID, tenantId, () -> {
			R<TenantPackage> result = getSysClient().getTenantPackage(tenantId);
			return result.getData();
		}, Boolean.FALSE);
	}

}
