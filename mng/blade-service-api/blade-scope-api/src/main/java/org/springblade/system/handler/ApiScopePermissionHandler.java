
package org.springblade.system.handler;

import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.handler.IPermissionHandler;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springblade.system.cache.ApiScopeCache.permissionCode;
import static org.springblade.system.cache.ApiScopeCache.permissionPath;

/**
 * 接口权限校验类
 *
 *
 */
public class ApiScopePermissionHandler implements IPermissionHandler {

	@Override
	public boolean permissionAll() {
		HttpServletRequest request = WebUtil.getRequest();
		BladeUser user = AuthUtil.getUser();
		if (request == null || user == null) {
			return false;
		}
		String uri = request.getRequestURI();
		List<String> paths = permissionPath(user.getRoleId());
		if (paths == null || paths.size() == 0) {
			return false;
		}
		return paths.stream().anyMatch(uri::contains);
	}

	@Override
	public boolean hasPermission(String permission) {
		HttpServletRequest request = WebUtil.getRequest();
		BladeUser user = AuthUtil.getUser();
		if (request == null || user == null) {
			return false;
		}
		List<String> codes = permissionCode(permission, user.getRoleId());
		return codes != null && codes.size() != 0;
	}

}
