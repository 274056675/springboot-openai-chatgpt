
package org.springblade.system.user.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.UserOauth;
import org.springblade.system.user.enums.UserEnum;
import org.springblade.system.user.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务Feign实现类
 *
 *
 */
@NonDS
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

	private final IUserService service;

	@Override
	@GetMapping(USER_INFO_BY_ID)
	public R<User> userInfoById(Long userId) {
		return R.data(service.getById(userId));
	}

	@Override
	@GetMapping(USER_INFO_BY_ACCOUNT)
	public R<User> userByAccount(String tenantId, String account) {
		return R.data(service.userByAccount(tenantId, account));
	}

	@Override
	@GetMapping(USER_INFO)
	public R<UserInfo> userInfo(String tenantId, String account) {
		return R.data(service.userInfo(tenantId, account));
	}

	@Override
	@GetMapping(USER_INFO_BY_TYPE)
	public R<UserInfo> userInfo(String tenantId, String account, String userType) {
		return R.data(service.userInfo(tenantId, account, UserEnum.of(userType)));
	}

	@Override
	@PostMapping(USER_AUTH_INFO)
	public R<UserInfo> userAuthInfo(@RequestBody UserOauth userOauth) {
		return R.data(service.userInfo(userOauth));
	}

	@Override
	@PostMapping(SAVE_USER)
	public R<Boolean> saveUser(@RequestBody User user) {
		return R.data(service.submit(user));
	}

	@Override
	@PostMapping(REMOVE_USER)
	public R<Boolean> removeUser(String tenantIds) {
		return R.data(service.remove(Wrappers.<User>query().lambda().in(User::getTenantId, Func.toStrList(tenantIds))));
	}

}
