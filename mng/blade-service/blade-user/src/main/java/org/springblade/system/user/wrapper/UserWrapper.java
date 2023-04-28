
package org.springblade.system.user.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.cache.DictCache;
import org.springblade.system.cache.SysCache;
import org.springblade.system.entity.Tenant;
import org.springblade.system.enums.DictEnum;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.vo.UserVO;

import java.util.List;
import java.util.Objects;

/**
 * 包装类,返回视图层所需的字段
 *
 *
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = Objects.requireNonNull(BeanUtil.copy(user, UserVO.class));
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		List<String> roleName = SysCache.getRoleNames(user.getRoleId());
		List<String> deptName = SysCache.getDeptNames(user.getDeptId());
		List<String> postName = SysCache.getPostNames(user.getPostId());
		userVO.setTenantName(tenant.getTenantName());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		userVO.setPostName(Func.join(postName));
		userVO.setSexName(DictCache.getValue(DictEnum.SEX, user.getSex()));
		userVO.setUserTypeName(DictCache.getValue(DictEnum.USER_TYPE, user.getUserType()));
		return userVO;
	}

}
