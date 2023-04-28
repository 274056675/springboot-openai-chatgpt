
package org.springblade.system.user.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.feign.IUserClient;

import static org.springblade.core.cache.constant.CacheConstant.USER_CACHE;
import static org.springblade.core.launch.constant.FlowConstant.TASK_USR_PREFIX;

/**
 * 系统缓存
 *
 *
 */
public class UserCache {
	private static final String USER_CACHE_ID = "user:id:";
	private static final String USER_CACHE_ACCOUNT = "user:account:";

	private static IUserClient userClient;

	private static IUserClient getUserClient() {
		if (userClient == null) {
			userClient = SpringUtil.getBean(IUserClient.class);
		}
		return userClient;
	}

	/**
	 * 根据任务用户id获取用户信息
	 *
	 * @param taskUserId 任务用户id
	 * @return
	 */
	public static User getUserByTaskUser(String taskUserId) {
		Long userId = Func.toLong(StringUtil.removePrefix(taskUserId, TASK_USR_PREFIX));
		return getUser(userId);
	}

	/**
	 * 获取用户
	 *
	 * @param userId 用户id
	 * @return
	 */
	public static User getUser(Long userId) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ID, userId, () -> {
			R<User> result = getUserClient().userInfoById(userId);
			return result.getData();
		});
	}

	/**
	 * 获取用户
	 *
	 * @param tenantId 租户id
	 * @param account  账号名
	 * @return
	 */
	public static User getUser(String tenantId, String account) {
		return CacheUtil.get(USER_CACHE, USER_CACHE_ACCOUNT, tenantId + StringPool.DASH + account, () -> {
			R<User> result = getUserClient().userByAccount(tenantId, account);
			return result.getData();
		});
	}

}
