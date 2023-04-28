
package org.springblade.system.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 *
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface ISysClient {

	String API_PREFIX = "/client";
	String MENU = API_PREFIX + "/menu";
	String DEPT = API_PREFIX + "/dept";
	String DEPT_IDS = API_PREFIX + "/dept-ids";
	String DEPT_IDS_FUZZY = API_PREFIX + "/dept-ids-fuzzy";
	String DEPT_NAME = API_PREFIX + "/dept-name";
	String DEPT_NAMES = API_PREFIX + "/dept-names";
	String DEPT_CHILD = API_PREFIX + "/dept-child";
	String POST = API_PREFIX + "/post";
	String POST_IDS = API_PREFIX + "/post-ids";
	String POST_IDS_FUZZY = API_PREFIX + "/post-ids-fuzzy";
	String POST_NAME = API_PREFIX + "/post-name";
	String POST_NAMES = API_PREFIX + "/post-names";
	String ROLE = API_PREFIX + "/role";
	String ROLE_IDS = API_PREFIX + "/role-ids";
	String ROLE_NAME = API_PREFIX + "/role-name";
	String ROLE_NAMES = API_PREFIX + "/role-names";
	String ROLE_ALIAS = API_PREFIX + "/role-alias";
	String ROLE_ALIASES = API_PREFIX + "/role-aliases";
	String TENANT = API_PREFIX + "/tenant";
	String TENANT_ID = API_PREFIX + "/tenant-id";
	String TENANT_PACKAGE = API_PREFIX + "/tenant-package";
	String PARAM = API_PREFIX + "/param";
	String PARAM_VALUE = API_PREFIX + "/param-value";
	String REGION = API_PREFIX + "/region";

	/**
	 * 获取菜单
	 *
	 * @param id 主键
	 * @return Menu
	 */
	@GetMapping(MENU)
	R<Menu> getMenu(@RequestParam("id") Long id);

	/**
	 * 获取部门
	 *
	 * @param id 主键
	 * @return Dept
	 */
	@GetMapping(DEPT)
	R<Dept> getDept(@RequestParam("id") Long id);

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	@GetMapping(DEPT_IDS)
	R<String> getDeptIds(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

	/**
	 * 获取部门id
	 *
	 * @param tenantId  租户id
	 * @param deptNames 部门名
	 * @return 部门id
	 */
	@GetMapping(DEPT_IDS_FUZZY)
	R<String> getDeptIdsByFuzzy(@RequestParam("tenantId") String tenantId, @RequestParam("deptNames") String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param id 主键
	 * @return 部门名
	 */
	@GetMapping(DEPT_NAME)
	R<String> getDeptName(@RequestParam("id") Long id);

	/**
	 * 获取部门名
	 *
	 * @param deptIds 主键
	 * @return
	 */
	@GetMapping(DEPT_NAMES)
	R<List<String>> getDeptNames(@RequestParam("deptIds") String deptIds);

	/**
	 * 获取子部门ID
	 *
	 * @param deptId
	 * @return
	 */
	@GetMapping(DEPT_CHILD)
	R<List<Dept>> getDeptChild(@RequestParam("deptId") Long deptId);

	/**
	 * 获取岗位
	 *
	 * @param id 主键
	 * @return Post
	 */
	@GetMapping(POST)
	R<Post> getPost(@RequestParam("id") Long id);

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return 岗位id
	 */
	@GetMapping(POST_IDS)
	R<String> getPostIds(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

	/**
	 * 获取岗位id
	 *
	 * @param tenantId  租户id
	 * @param postNames 岗位名
	 * @return 岗位id
	 */
	@GetMapping(POST_IDS_FUZZY)
	R<String> getPostIdsByFuzzy(@RequestParam("tenantId") String tenantId, @RequestParam("postNames") String postNames);

	/**
	 * 获取岗位名
	 *
	 * @param id 主键
	 * @return 岗位名
	 */
	@GetMapping(POST_NAME)
	R<String> getPostName(@RequestParam("id") Long id);

	/**
	 * 获取岗位名
	 *
	 * @param postIds 主键
	 * @return
	 */
	@GetMapping(POST_NAMES)
	R<List<String>> getPostNames(@RequestParam("postIds") String postIds);

	/**
	 * 获取角色
	 *
	 * @param id 主键
	 * @return Role
	 */
	@GetMapping(ROLE)
	R<Role> getRole(@RequestParam("id") Long id);

	/**
	 * 获取角色id
	 *
	 * @param tenantId  租户id
	 * @param roleNames 角色名
	 * @return 角色id
	 */
	@GetMapping(ROLE_IDS)
	R<String> getRoleIds(@RequestParam("tenantId") String tenantId, @RequestParam("roleNames") String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param id 主键
	 * @return 角色名
	 */
	@GetMapping(ROLE_NAME)
	R<String> getRoleName(@RequestParam("id") Long id);

	/**
	 * 获取角色别名
	 *
	 * @param id 主键
	 * @return 角色别名
	 */
	@GetMapping(ROLE_ALIAS)
	R<String> getRoleAlias(@RequestParam("id") Long id);

	/**
	 * 获取角色名
	 *
	 * @param roleIds 主键
	 * @return
	 */
	@GetMapping(ROLE_NAMES)
	R<List<String>> getRoleNames(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取角色别名
	 *
	 * @param roleIds 主键
	 * @return 角色别名
	 */
	@GetMapping(ROLE_ALIASES)
	R<List<String>> getRoleAliases(@RequestParam("roleIds") String roleIds);

	/**
	 * 获取租户
	 *
	 * @param id 主键
	 * @return Tenant
	 */
	@GetMapping(TENANT)
	R<Tenant> getTenant(@RequestParam("id") Long id);

	/**
	 * 获取租户
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	@GetMapping(TENANT_ID)
	R<Tenant> getTenant(@RequestParam("tenantId") String tenantId);

	/**
	 * 获取租户产品包
	 *
	 * @param tenantId 租户id
	 * @return Tenant
	 */
	@GetMapping(TENANT_PACKAGE)
	R<TenantPackage> getTenantPackage(@RequestParam("tenantId") String tenantId);

	/**
	 * 获取参数
	 *
	 * @param id 主键
	 * @return Param
	 */
	@GetMapping(PARAM)
	R<Param> getParam(@RequestParam("id") Long id);

	/**
	 * 获取参数配置
	 *
	 * @param paramKey 参数key
	 * @return String
	 */
	@GetMapping(PARAM_VALUE)
	R<String> getParamValue(@RequestParam("paramKey") String paramKey);


}
