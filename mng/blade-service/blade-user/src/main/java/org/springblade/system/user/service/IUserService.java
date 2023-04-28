
package org.springblade.system.user.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.core.mp.support.Query;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.UserOauth;
import org.springblade.system.user.enums.UserEnum;
import org.springblade.system.user.excel.UserExcel;
import org.springblade.system.user.vo.UserVO;

import java.util.List;

/**
 * 服务类
 *
 *
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 新增用户
	 *
	 * @param user
	 * @return
	 */
	boolean submit(User user);

	/**
	 * 修改用户
	 *
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	/**
	 * 修改用户基本信息
	 *
	 * @param user
	 * @return
	 */
	boolean updateUserInfo(User user);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @param deptId
	 * @param tenantId
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId);

	/**
	 * 自定义分页
	 *
	 * @param user
	 * @param query
	 * @return
	 */
	IPage<UserVO> selectUserSearch(UserVO user, Query query);


	/**
	 * 用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo userInfo(Long userId);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account);

	/**
	 * 用户信息
	 *
	 * @param tenantId
	 * @param account
	 * @param userEnum
	 * @return
	 */
	UserInfo userInfo(String tenantId, String account, UserEnum userEnum);

	/**
	 * 用户信息
	 *
	 * @param userOauth
	 * @return
	 */
	UserInfo userInfo(UserOauth userOauth);

	/**
	 * 根据账号获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User userByAccount(String tenantId, String account);

	/**
	 * 给用户设置角色
	 *
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 初始化密码
	 *
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 *
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);


	/**
	 * 删除用户
	 *
	 * @param userIds
	 * @return
	 */
	boolean removeUser(String userIds);


	/**
	 * 导入用户数据
	 *
	 * @param data
	 * @param isCovered
	 * @return
	 */
	void importUser(List<UserExcel> data, Boolean isCovered);

	/**
	 * 导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(Wrapper<User> queryWrapper);

	/**
	 * 注册用户
	 *
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 配置用户平台
	 *
	 * @param userId
	 * @param userType
	 * @param userExt
	 * @return
	 */
	boolean updatePlatform(Long userId, Integer userType, String userExt);

	/**
	 * 用户详细信息
	 *
	 * @param user
	 * @return
	 */
	UserVO platformDetail(User user);

	//魔晶定制
	UserInfo userInfoDel(Long userId);

	//获取所以后台用户 魔晶定制
	List<User> getAllHtyh();
}
