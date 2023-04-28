
package org.springblade.system.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.mapper.UserMapper;
import org.springblade.system.user.service.IUserSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户查询服务实现类
 *
 *
 */
@Service
@AllArgsConstructor
public class UserSearchServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserSearchService {

	@Override
	public List<User> listByUser(List<Long> userId) {
		return this.list(Wrappers.<User>lambdaQuery().in(User::getId, userId));
	}

	@Override
	public List<User> listByDept(List<Long> deptId) {
		LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
		deptId.forEach(id -> queryWrapper.like(User::getDeptId, id).or());
		return this.list(queryWrapper);
	}

	@Override
	public List<User> listByPost(List<Long> postId) {
		LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
		postId.forEach(id -> queryWrapper.like(User::getPostId, id).or());
		return this.list(queryWrapper);
	}

	@Override
	public List<User> listByRole(List<Long> roleId) {
		LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
		roleId.forEach(id -> queryWrapper.like(User::getRoleId, id).or());
		return this.list(queryWrapper);
	}
}
