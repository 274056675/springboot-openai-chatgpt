
package org.springblade.system.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 接口权限Feign接口类
 *
 *
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IApiScopeClientFallback.class
)
public interface IApiScopeClient {

	String API_PREFIX = "/client/api-scope";
	String PERMISSION_PATH = API_PREFIX + "/permission-path";
	String PERMISSION_CODE = API_PREFIX + "/permission-code";

	/**
	 * 获取接口权限地址
	 *
	 * @param roleId 角色id
	 * @return permissions
	 */
	@GetMapping(PERMISSION_PATH)
	List<String> permissionPath(@RequestParam("roleId") String roleId);

	/**
	 * 获取接口权限信息
	 *
	 * @param permission 权限编号
	 * @param roleId     角色id
	 * @return permissions
	 */
	@GetMapping(PERMISSION_CODE)
	List<String> permissionCode(@RequestParam("permission") String permission, @RequestParam("roleId") String roleId);

}
