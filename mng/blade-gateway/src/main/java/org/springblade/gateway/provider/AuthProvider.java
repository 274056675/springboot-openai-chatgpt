
package org.springblade.gateway.provider;

import org.springblade.core.launch.constant.TokenConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 *
 *
 */
public class AuthProvider {

	public static final String AUTH_KEY = TokenConstant.HEADER;
	private static final List<String> DEFAULT_SKIP_URL = new ArrayList<>();

	static {
		DEFAULT_SKIP_URL.add("/example");
		DEFAULT_SKIP_URL.add("/oauth/token/**");
		DEFAULT_SKIP_URL.add("/oauth/captcha/**");
		DEFAULT_SKIP_URL.add("/oauth/clear-cache/**");
		DEFAULT_SKIP_URL.add("/oauth/user-info");
		DEFAULT_SKIP_URL.add("/oauth/render/**");
		DEFAULT_SKIP_URL.add("/oauth/callback/**");
		DEFAULT_SKIP_URL.add("/oauth/revoke/**");
		DEFAULT_SKIP_URL.add("/oauth/refresh/**");
		DEFAULT_SKIP_URL.add("/token/**");
		DEFAULT_SKIP_URL.add("/actuator/**");
		DEFAULT_SKIP_URL.add("/v2/api-docs/**");
		DEFAULT_SKIP_URL.add("/auth/**");
		DEFAULT_SKIP_URL.add("/log/**");
		DEFAULT_SKIP_URL.add("/menu/routes");
		DEFAULT_SKIP_URL.add("/menu/auth-routes");
		DEFAULT_SKIP_URL.add("/menu/top-menu");
		DEFAULT_SKIP_URL.add("/tenant/info");
		DEFAULT_SKIP_URL.add("/process/resource-view");
		DEFAULT_SKIP_URL.add("/process/diagram-view");
		DEFAULT_SKIP_URL.add("/manager/check-upload");
		DEFAULT_SKIP_URL.add("/error/**");
		DEFAULT_SKIP_URL.add("/assets/**");
	}

	/**
	 * 默认无需鉴权的API
	 */
	public static List<String> getDefaultSkipUrl() {
		return DEFAULT_SKIP_URL;
	}

}
