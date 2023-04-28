
package org.springblade.system.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.node.TreeNode;
import org.springblade.system.service.IMenuService;
import org.springblade.system.service.IMjkjSystemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;
import java.util.Set;

/**
 * 系统服务Feign实现类
 *
 *
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class MjkjSysClient implements IMjkjSysClient {

	private final IMjkjSystemService mjkjSystemService;

	private final IMenuService menuService;

	@Override
	@GetMapping(USER_PARENT_DEPT_LIST)
	public R<Set<String>> getUserParentDeptList(Long userId) {
		return R.data(mjkjSystemService.getUserParentDeptList(userId));
	}

	@Override
	@GetMapping(TENANTID)
	public R<String> getTenantId(String tenanName) {
		String tenanId = mjkjSystemService.getTenanId(tenanName);
		return R.data(tenanId);
	}

	@Override
	@GetMapping(SAVE_ROLE_MENU)
	public R<String> saveRoleMenu(@RequestParam("roleId") String roleId, @RequestParam("menuIdList") String menuIdList){
		List<Long> list = JsonUtil.parse(menuIdList, List.class);
		boolean flag = mjkjSystemService.grantClient(roleId, list);
		return R.data(flag?"成功":"失败");
	}

	@Override
	@GetMapping(GRANT_TREE)
	public R<List<TreeNode>> grantTree(){
		List<TreeNode> list = menuService.grantTree();
		return R.data(list);
	}



}
