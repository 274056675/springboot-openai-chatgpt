
package org.springblade.auth.service;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springblade.auth.constant.AuthConstant;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.*;
import org.springblade.system.cache.ParamCache;
import org.springblade.system.entity.Tenant;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.enums.UserEnum;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.List;

/**
 * 用户信息
 *
 *
 */
@Service
@AllArgsConstructor
public class BladeUserDetailsServiceImpl implements UserDetailsService {

	public static final Integer FAIL_COUNT = 5;
	public static final String FAIL_COUNT_VALUE = "account.failCount";

	private final IUserClient userClient;
	private final ISysClient sysClient;

	private final BladeRedis bladeRedis;
	private final JwtProperties jwtProperties;

	@Override
	@SneakyThrows
	public BladeUserDetails loadUserByUsername(String username) {
		HttpServletRequest request = WebUtil.getRequest();
		// 获取用户绑定ID
		String headerDept = request.getHeader(TokenUtil.DEPT_HEADER_KEY);
		String headerRole = request.getHeader(TokenUtil.ROLE_HEADER_KEY);
		// 获取租户ID
		String headerTenant = request.getHeader(TokenUtil.TENANT_HEADER_KEY);
		String paramTenant = request.getParameter(TokenUtil.TENANT_PARAM_KEY);
		String password = request.getParameter(TokenUtil.PASSWORD_KEY);
		String grantType = request.getParameter(TokenUtil.GRANT_TYPE_KEY);
		// 判断租户请求头
		if (StringUtil.isAllBlank(headerTenant, paramTenant)) {
			throw new UserDeniedAuthorizationException(TokenUtil.TENANT_NOT_FOUND);
		}
		// 判断令牌合法性
		if (!judgeRefreshToken(grantType, request)) {
			throw new UserDeniedAuthorizationException(TokenUtil.TOKEN_NOT_PERMISSION);
		}

		// 指定租户ID
		String tenantId = StringUtils.isBlank(headerTenant) ? paramTenant : headerTenant;
		// 判断登录是否锁定
		int count = getFailCount(tenantId, username);
		int failCount = Func.toInt(ParamCache.getValue(FAIL_COUNT_VALUE), FAIL_COUNT);
		if (count >= failCount) {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_TOO_MANY_FAILS);
		}

		// 获取租户信息
		R<Tenant> tenant = sysClient.getTenant(tenantId);
		if (tenant.isSuccess()) {
			if (TokenUtil.judgeTenant(tenant.getData())) {
				throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_TENANT_PERMISSION);
			}
		} else {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_TENANT);
		}

		// 获取用户类型
		String userType = Func.toStr(request.getHeader(TokenUtil.USER_TYPE_HEADER_KEY), TokenUtil.DEFAULT_USER_TYPE);

		// 远程调用返回数据
		R<UserInfo> result;
		// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
		if (userType.equals(UserEnum.WEB.getName())) {
			result = userClient.userInfo(tenantId, username, UserEnum.WEB.getName());
		} else if (userType.equals(UserEnum.APP.getName())) {
			result = userClient.userInfo(tenantId, username, UserEnum.APP.getName());
		} else {
			result = userClient.userInfo(tenantId, username, UserEnum.OTHER.getName());
		}

		// 判断返回信息
		if (result.isSuccess()) {
			UserInfo userInfo = result.getData();
			User user = userInfo.getUser();
//			if (Func.isEmpty(user.getUserType())||user.getUserType()==4) {
//				throw new UserDeniedAuthorizationException("暂无权限");
//			}
			// 用户不存在,但提示用户名与密码错误并锁定账号
			if (user == null || user.getId() == null) {
				setFailCount(tenantId, username, count);
				throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
			}
			// 用户存在但密码错误,超过次数则锁定账号
			if (grantType != null && !grantType.equals(TokenUtil.REFRESH_TOKEN_KEY) && !user.getPassword().equals(DigestUtil.encrypt(password))) {
				setFailCount(tenantId, username, count);
				throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
			}
			// 用户角色不存在
			if (Func.isEmpty(userInfo.getRoles())) {
				throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_ROLE);
			}
			// 多部门情况下指定单部门
			if (Func.isNotEmpty(headerDept) && user.getDeptId().contains(headerDept)) {
				user.setDeptId(headerDept);
			}
			// 多角色情况下指定单角色
			if (Func.isNotEmpty(headerRole) && user.getRoleId().contains(headerRole)) {
				R<List<String>> roleResult = sysClient.getRoleAliases(headerRole);
				if (roleResult.isSuccess()) {
					userInfo.setRoles(roleResult.getData());
				}
				user.setRoleId(headerRole);
			}
			//魔晶定制
			Kv detail = userInfo.getDetail();
			detail.put("tenant_name",tenant.getData().getTenantName());
			detail.put("pid", user.getPid());
			detail.put("level", user.getLevel());
			// 成功则清除登录错误次数
			delFailCount(tenantId, username);
			return new BladeUserDetails(user.getId(),
				user.getTenantId(), StringPool.EMPTY, user.getName(), user.getRealName(), user.getDeptId(), user.getPostId(), user.getRoleId(), Func.join(userInfo.getRoles()), Func.toStr(user.getAvatar(), TokenUtil.DEFAULT_AVATAR),
				username, AuthConstant.ENCRYPT + user.getPassword(), userInfo.getDetail(), true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList(Func.join(result.getData().getRoles())));
		} else {
			throw new UsernameNotFoundException(result.getMsg());
		}
	}

	/**
	 * 获取账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @return int
	 */
	private int getFailCount(String tenantId, String username) {
		return Func.toInt(bladeRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username)), 0);
	}

	/**
	 * 设置账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @param count    次数
	 */
	private void setFailCount(String tenantId, String username, int count) {
		if(Func.equals(tenantId,"000000")&& Func.equals(username,"admin")){//魔晶定制，超级 管理员不操作
			return;
		}
		bladeRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username), count + 1, Duration.ofMinutes(30));
	}

	/**
	 * 清空账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 */
	private void delFailCount(String tenantId, String username) {
		bladeRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username));
	}

	/**
	 * 校验refreshToken合法性
	 *
	 * @param grantType 认证类型
	 * @param request   请求
	 */
	private boolean judgeRefreshToken(String grantType, HttpServletRequest request) {
		if (jwtProperties.getState() && jwtProperties.getSingle() && StringUtil.equals(grantType, TokenUtil.REFRESH_TOKEN_KEY)) {
			String refreshToken = request.getParameter(TokenUtil.REFRESH_TOKEN_KEY);
			Claims claims = JwtUtil.parseJWT(refreshToken);
			String tenantId = String.valueOf(claims.get("tenant_id"));
			String userId = String.valueOf(claims.get("user_id"));
			String token = JwtUtil.getRefreshToken(tenantId, userId, refreshToken);
			return StringUtil.equalsIgnoreCase(token, refreshToken);
		}
		return true;
	}


}
