
package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Dept;
import org.springblade.system.entity.Role;
import org.springblade.system.entity.RoleMenu;
import org.springblade.system.entity.Tenant;
import org.springblade.system.mapper.MjkjSystemMapper;
import org.springblade.system.mapper.RoleMapper;
import org.springblade.system.service.IDeptService;
import org.springblade.system.service.IMjkjSystemService;
import org.springblade.system.service.IRoleMenuService;
import org.springblade.system.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 */
@Service
public class MjkjSystemServiceImpl implements IMjkjSystemService {

	@Autowired
	private MjkjSystemMapper mjkjSystemMapper;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private ITenantService tenantService;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private IRoleMenuService roleMenuService;


	@Override
	public Set<String> getUserParentDeptList(Long userId){
		Set<String> resultList=new HashSet<>();
		List<Long> userDeptList = mjkjSystemMapper.getUserDeptList(userId);
		if(Func.isEmpty(userDeptList)){
			return resultList;
		}
		for (Long deptId:userDeptList) {
			Dept dept = deptService.getById(deptId);
			if(Func.isNotEmpty(dept)){
				List<String> list = Func.toStrList(dept.getAncestors());
				resultList.addAll(list);
				resultList.add(deptId+"");
			}

		}
		return resultList;
	}

	@Override
	public String getTenanId(String tenantName) {
		Tenant tenant = tenantService.getOne(Wrappers.<Tenant>query().lambda().eq(Tenant::getTenantName, tenantName));
		if (Func.isEmpty(tenant)) {
			return tenantName;
		}
		return tenant.getTenantId();
	}

	@Override
	public boolean grantClient(String roleId, List<Long> menuIds){
		List<Long> roleIds = Func.toLongList(roleId);
		return grantRoleMenu(roleIds, menuIds);
	}



	private boolean grantRoleMenu(List<Long> roleIds, List<Long> menuIds) {
		// 防止越权配置超管角色
		Long administratorCount = roleMapper.selectCount(Wrappers.<Role>query().lambda().eq(Role::getRoleAlias, RoleConstant.ADMINISTRATOR).in(Role::getId, roleIds));
		if (!AuthUtil.isAdministrator() && (Func.isNotEmpty(administratorCount) && administratorCount > 0)) {
			throw new ServiceException("无权配置超管角色!");
		}
		// 防止越权配置管理员角色
		Long adminCount = roleMapper.selectCount(Wrappers.<Role>query().lambda().eq(Role::getRoleAlias, RoleConstant.ADMIN).in(Role::getId, roleIds));
		if (!AuthUtil.isAdmin() && (Func.isNotEmpty(adminCount) && adminCount > 0)) {
			throw new ServiceException("无权配置管理员角色!");
		}
		// 删除角色配置的菜单集合
		roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
		// 组装配置
		List<RoleMenu> roleMenus = new ArrayList<>();
		roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenus.add(roleMenu);
		}));
		// 新增配置
		roleMenuService.saveBatch(roleMenus);
		// 递归设置下属角色菜单集合
		recursionRoleMenu(roleIds, menuIds);
		return true;
	}

	private void recursionRoleMenu(List<Long> roleIds, List<Long> menuIds) {
		roleIds.forEach(roleId -> roleMapper.selectList(Wrappers.<Role>query().lambda().eq(Role::getParentId, roleId)).forEach(role -> {
			List<RoleMenu> roleMenuList = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().eq(RoleMenu::getRoleId, role.getId()));
			// 子节点过滤出父节点删除的菜单集合
			List<Long> collectRoleMenuIds = roleMenuList.stream().map(RoleMenu::getMenuId).filter(menuId -> !menuIds.contains(menuId)).collect(Collectors.toList());
			if (collectRoleMenuIds.size() > 0) {
				// 删除子节点权限外的菜单集合
				roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().eq(RoleMenu::getRoleId, role.getId()).in(RoleMenu::getMenuId, collectRoleMenuIds));
				// 递归设置下属角色菜单集合
				recursionRoleMenu(Collections.singletonList(role.getId()), menuIds);
			}
		}));
	}
}
