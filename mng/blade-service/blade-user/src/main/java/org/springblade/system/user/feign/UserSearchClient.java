
package org.springblade.system.user.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.service.IUserSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户查询服务Feign实现类
 *
 *
 */
@NonDS
@RestController
@AllArgsConstructor
public class UserSearchClient implements IUserSearchClient {

	private final IUserSearchService service;

	@Override
	@GetMapping(LIST_BY_USER)
	public R<List<User>> listByUser(String userId) {
		return R.data(service.listByUser(Func.toLongList(userId)));
	}

	@Override
	@GetMapping(LIST_BY_DEPT)
	public R<List<User>> listByDept(String deptId) {
		return R.data(service.listByDept(Func.toLongList(deptId)));
	}

	@Override
	@GetMapping(LIST_BY_POST)
	public R<List<User>> listByPost(String postId) {
		return R.data(service.listByPost(Func.toLongList(postId)));
	}

	@Override
	@GetMapping(LIST_BY_ROLE)
	public R<List<User>> listByRole(String roleId) {
		return R.data(service.listByRole(Func.toLongList(roleId)));
	}
}
