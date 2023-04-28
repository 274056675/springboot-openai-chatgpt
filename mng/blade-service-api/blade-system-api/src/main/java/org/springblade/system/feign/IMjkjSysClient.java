
package org.springblade.system.feign;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.node.TreeNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * Feign接口类
 *
 *
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface IMjkjSysClient {

	String USER_PARENT_DEPT_LIST = "/client/user-parent-dept-list";
	String TENANTID = "/tenantId";
	String SAVE_ROLE_MENU = "/roleMenu";
	String GRANT_TREE = "/grantTree";

	@GetMapping(USER_PARENT_DEPT_LIST)
    R<Set<String>> getUserParentDeptList(@RequestParam("userId") Long userId);

	@GetMapping(TENANTID)
    R<String> getTenantId(@RequestParam("tenanName") String tenanName);

	@GetMapping(SAVE_ROLE_MENU)
    R<String> saveRoleMenu(@RequestParam("roleId") String roleId, @RequestParam("menuIdList") String menuIdList);

	@GetMapping(GRANT_TREE)
    R<List<TreeNode>> grantTree();
}
