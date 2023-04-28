
package org.springblade.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.cache.SysCache;
import org.springblade.system.entity.Role;
import org.springblade.system.vo.RoleVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 *
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO> {

	public static RoleWrapper build() {
		return new RoleWrapper();
	}

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = Objects.requireNonNull(BeanUtil.copy(role, RoleVO.class));
		if (Func.equals(role.getParentId(), BladeConstant.TOP_PARENT_ID)) {
			roleVO.setParentName(BladeConstant.TOP_PARENT_NAME);
		} else {
			Role parent = SysCache.getRole(role.getParentId());
			roleVO.setParentName(parent.getRoleName());
		}
		return roleVO;
	}


	public List<RoleVO> listNodeVO(List<Role> list) {
		List<RoleVO> collect = list.stream().map(this::entityVO).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
