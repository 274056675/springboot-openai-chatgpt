
package org.springblade.system.user.feign;


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User Search Feign接口类
 *
 *
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME
)
public interface IUserSearchClient {

	String API_PREFIX = "/client";
	String LIST_BY_USER = API_PREFIX + "/user/list-by-user";
	String LIST_BY_DEPT = API_PREFIX + "/user/list-by-dept";
	String LIST_BY_POST = API_PREFIX + "/user/list-by-post";
	String LIST_BY_ROLE = API_PREFIX + "/user/list-by-role";

	/**
	 * 根据用户ID查询用户列表
	 *
	 * @param userId 用户ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_USER)
	R<List<User>> listByUser(@RequestParam("userId") String userId);

	/**
	 * 根据部门ID查询用户列表
	 *
	 * @param deptId 部门ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_DEPT)
	R<List<User>> listByDept(@RequestParam("deptId") String deptId);

	/**
	 * 根据岗位ID查询用户列表
	 *
	 * @param postId 岗位ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_POST)
	R<List<User>> listByPost(@RequestParam("postId") String postId);

	/**
	 * 根据角色ID查询用户列表
	 *
	 * @param roleId 角色ID
	 * @return 用户列表
	 */
	@GetMapping(LIST_BY_ROLE)
	R<List<User>> listByRole(@RequestParam("roleId") String roleId);

}
