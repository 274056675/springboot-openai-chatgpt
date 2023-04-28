
package org.springblade.system.user.service;


import org.springblade.core.mp.base.BaseService;
import org.springblade.system.user.entity.User;

import java.util.List;

/**
 * 用户查询服务类
 *
 *
 */
public interface IUserSearchService extends BaseService<User> {

	/**
	 * 根据用户ID查询用户列表
	 *
	 * @param userId 用户ID
	 * @return 用户列表
	 */
	List<User> listByUser(List<Long> userId);

    /**
	 * 根据部门ID查询用户列表
	 *
	 * @param deptId 部门ID
	 * @return 用户列表
	 */
	List<User> listByDept(List<Long> deptId);

    /**
	 * 根据岗位ID查询用户列表
	 *
	 * @param postId 岗位ID
	 * @return 用户列表
	 */
	List<User> listByPost(List<Long> postId);

    /**
	 * 根据角色ID查询用户列表
	 *
	 * @param roleId 角色ID
	 * @return 用户列表
	 */
	List<User> listByRole(List<Long> roleId);

}
