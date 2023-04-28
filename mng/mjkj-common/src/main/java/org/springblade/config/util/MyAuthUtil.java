package org.springblade.config.util;

import io.jsonwebtoken.Claims;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

public class MyAuthUtil {
	private static final String BLADE_USER_REQUEST_ATTR = "_BLADE_USER_REQUEST_ATTR_";
	private static final String HEADER = "Blade-Auth";
	private static final String ACCOUNT = "account";
	private static final String USER_NAME = "user_name";
	private static final String NICK_NAME = "nick_name";
	private static final String USER_ID = "user_id";
	private static final String DEPT_ID = "dept_id";
	private static final String POST_ID = "post_id";
	private static final String ROLE_ID = "role_id";
	private static final String ROLE_NAME = "role_name";
	private static final String TENANT_ID = "tenant_id";
	private static final String OAUTH_ID = "oauth_id";
	private static final String CLIENT_ID = "client_id";
	private static final String DETAIL = "detail";
	private static JwtProperties jwtProperties;

	public MyAuthUtil() {
	}

	private static JwtProperties getJwtProperties() {
		if (jwtProperties == null) {
			jwtProperties = (JwtProperties) SpringUtil.getBean(JwtProperties.class);
		}

		return jwtProperties;
	}

	public static BladeUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		} else {
			Object bladeUser = request.getAttribute("_BLADE_USER_REQUEST_ATTR_");
			if (bladeUser == null) {
				bladeUser = getUser(request);
				if (bladeUser != null) {
					request.setAttribute("_BLADE_USER_REQUEST_ATTR_", bladeUser);
				}
			}

			return (BladeUser) bladeUser;
		}
	}

	public static BladeUser getUser(HttpServletRequest request) {
		Claims claims = getClaims(request);
		if (claims == null) {
			return null;
		} else {
			String clientId = Func.toStr(claims.get("client_id"));
			Long userId = Func.toLong(claims.get("user_id"));
			String tenantId = Func.toStr(claims.get("tenant_id"));
			String oauthId = Func.toStr(claims.get("oauth_id"));
			String deptId = Func.toStrWithEmpty(claims.get("dept_id"), "-1");
			String postId = Func.toStrWithEmpty(claims.get("post_id"), "-1");
			String roleId = Func.toStrWithEmpty(claims.get("role_id"), "-1");
			String account = Func.toStr(claims.get("account"));
			String roleName = Func.toStr(claims.get("role_name"));
			String userName = Func.toStr(claims.get("user_name"));
			String nickName = Func.toStr(claims.get("nick_name"));
			Kv detail = Kv.create().setAll((Map) claims.get("detail"));
			BladeUser bladeUser = new BladeUser();
			bladeUser.setClientId(clientId);
			bladeUser.setUserId(userId);
			bladeUser.setTenantId(tenantId);
			bladeUser.setOauthId(oauthId);
			bladeUser.setAccount(account);
			bladeUser.setDeptId(deptId);
			bladeUser.setPostId(postId);
			bladeUser.setRoleId(roleId);
			bladeUser.setRoleName(roleName);
			bladeUser.setUserName(userName);
			bladeUser.setNickName(nickName);
			bladeUser.setDetail(detail);
			return bladeUser;
		}
	}

	public static boolean isAdministrator() {
		return StringUtil.containsAny(getUserRole(), new CharSequence[]{"administrator"});
	}

	public static boolean isAdmin() {
		return StringUtil.containsAny(getUserRole(), new CharSequence[]{"admin"});
	}

	public static Long getUserId() {
		BladeUser user = getUser();
		return null == user ? -1L : user.getUserId();
	}

	public static Long getUserId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? -1L : user.getUserId();
	}

	public static String getUserAccount() {
		BladeUser user = getUser();
		return null == user ? "" : user.getAccount();
	}

	public static String getUserAccount(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getAccount();
	}

	public static String getUserName() {
		BladeUser user = getUser();
		return null == user ? "" : user.getUserName();
	}

	public static String getUserName(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getUserName();
	}

	public static String getNickName() {
		BladeUser user = getUser();
		return null == user ? "" : user.getNickName();
	}

	public static String getNickName(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getNickName();
	}

	public static String getDeptId() {
		BladeUser user = getUser();
		return null == user ? "" : user.getDeptId();
	}

	public static String getDeptId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getDeptId();
	}

	public static String getPostId() {
		BladeUser user = getUser();
		return null == user ? "" : user.getPostId();
	}

	public static String getPostId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getPostId();
	}

	public static String getUserRole() {
		BladeUser user = getUser();
		return null == user ? "" : user.getRoleName();
	}

	public static String getUserRole(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getRoleName();
	}

	public static String getTenantId() {
		BladeUser user = getUser();
		return null == user ? "" : user.getTenantId();
	}

	public static String getTenantId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getTenantId();
	}

	public static String getOauthId() {
		BladeUser user = getUser();
		return null == user ? "" : user.getOauthId();
	}

	public static String getOauthId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getOauthId();
	}

	public static String getClientId() {
		BladeUser user = getUser();
		return null == user ? "" : user.getClientId();
	}

	public static String getClientId(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? "" : user.getClientId();
	}

	public static Kv getDetail() {
		BladeUser user = getUser();
		return null == user ? Kv.create() : user.getDetail();
	}

	public static Kv getDetail(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return null == user ? Kv.create() : user.getDetail();
	}

	public static Claims getClaims(HttpServletRequest request) {
		String auth =request.getParameter("token");// request.getHeader("Blade-Auth");
		Claims claims = null;
		String token;
		String tenantId;
		if (StringUtil.isNotBlank(auth)) {
			token = JwtUtil.getToken(auth);
		} else {
			tenantId = request.getParameter("token");
			token = JwtUtil.getToken(tenantId);
		}

		if (StringUtil.isNotBlank(token)) {
			claims = parseJWT(token);
		}

		if (ObjectUtil.isNotEmpty(claims) && getJwtProperties().getState()) {
			tenantId = Func.toStr(claims.get("tenant_id"));
			String userId = Func.toStr(claims.get("user_id"));
			String accessToken = JwtUtil.getAccessToken(tenantId, userId, token);
			if (!token.equalsIgnoreCase(accessToken)) {
				return null;
			}
		}

		return claims;
	}

	public static String getHeader() {
		return getHeader((HttpServletRequest) Objects.requireNonNull(WebUtil.getRequest()));
	}

	public static String getHeader(HttpServletRequest request) {
		return request.getHeader("Blade-Auth");
	}

	public static Claims parseJWT(String jsonWebToken) {
		return JwtUtil.parseJWT(jsonWebToken);
	}
}
