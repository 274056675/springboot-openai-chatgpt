
package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.node.TreeNode;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.system.cache.SysCache;
import org.springblade.system.dto.MenuDTO;
import org.springblade.system.entity.*;
import org.springblade.system.mapper.MenuMapper;
import org.springblade.system.service.IMenuService;
import org.springblade.system.service.IRoleMenuService;
import org.springblade.system.service.IRoleScopeService;
import org.springblade.system.service.ITopMenuSettingService;
import org.springblade.system.vo.MenuVO;
import org.springblade.system.wrapper.MenuWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springblade.common.constant.CommonConstant.API_SCOPE_CATEGORY;
import static org.springblade.common.constant.CommonConstant.DATA_SCOPE_CATEGORY;
import static org.springblade.core.cache.constant.CacheConstant.MENU_CACHE;

/**
 * 服务实现类
 *
 *
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	private final IRoleMenuService roleMenuService;
	private final IRoleScopeService roleScopeService;
	private final ITopMenuSettingService topMenuSettingService;
	private final static String PARENT_ID = "parentId";
	private final static Integer MENU_CATEGORY = 1;

	@Override
	public List<MenuVO> lazyList(Long parentId, Map<String, Object> param) {
		if (Func.isEmpty(Func.toStr(param.get(PARENT_ID)))) {
			parentId = null;
		}
		return baseMapper.lazyList(parentId, param);
	}

	@Override
	public List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> param) {
		if (Func.isEmpty(Func.toStr(param.get(PARENT_ID)))) {
			parentId = null;
		}
		return baseMapper.lazyMenuList(parentId, param);
	}


	@Override
	public List<MenuVO> routes(String roleId, Long topMenuId) {
		if (StringUtil.isBlank(roleId)) {
			return null;
		}
		List<Menu> allMenus = baseMapper.allMenu();
		List<Menu> roleMenus;
		// 超级管理员并且不是顶部菜单请求则返回全部菜单
		if (AuthUtil.isAdministrator() && Func.isEmpty(topMenuId)) {
			roleMenus = allMenus;
		}
		// 非超级管理员并且不是顶部菜单请求则返回对应角色权限菜单
		else if (!AuthUtil.isAdministrator() && Func.isEmpty(topMenuId)) {
			roleMenus = tenantPackageMenu(baseMapper.roleMenuByRoleId(Func.toLongList(roleId)));
		}
		// 顶部菜单请求返回对应角色权限菜单
		else {
			// 角色配置对应菜单
			List<Menu> roleIdMenus = baseMapper.roleMenuByRoleId(Func.toLongList(roleId));
			// 反向递归角色菜单所有父级
			List<Menu> routes = new LinkedList<>(roleIdMenus);
			roleIdMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
			// 顶部配置对应菜单
			List<Menu> topIdMenus = baseMapper.roleMenuByTopMenuId(topMenuId);
			// 筛选匹配角色对应的权限菜单
			roleMenus = topIdMenus.stream().filter(x ->
				routes.stream().anyMatch(route -> route.getId().longValue() == x.getId().longValue())
			).collect(Collectors.toList());
		}
		return buildRoutes(allMenus, roleMenus);
	}

	private List<MenuVO> buildRoutes(List<Menu> allMenus, List<Menu> roleMenus) {
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}

	private void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

	@Override
	public List<MenuVO> buttons(String roleId) {
		List<Menu> buttons = (AuthUtil.isAdministrator()) ? baseMapper.allButtons() : baseMapper.buttons(Func.toLongList(roleId));
		MenuWrapper menuWrapper = new MenuWrapper();
		return menuWrapper.listNodeVO(buttons);
	}

	@Override
	public List<TreeNode> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public List<TreeNode> grantTree(BladeUser user) {
		List<TreeNode> menuTree = user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantTree() : baseMapper.grantTreeByRole(Func.toLongList(user.getRoleId()));
		return ForestNodeMerger.merge(tenantPackageTree(menuTree, user.getTenantId()));
	}

	@Override
	public List<TreeNode> grantTree() {
		List<TreeNode> list = ForestNodeMerger.merge(baseMapper.grantTreeSelf());
		return list;
	}

	@Override
	public List<TreeNode> grantTopTree(BladeUser user) {
		List<TreeNode> menuTree = user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantTopTree() : baseMapper.grantTopTreeByRole(Func.toLongList(user.getRoleId()));
		return ForestNodeMerger.merge(tenantPackageTree(menuTree, user.getTenantId()));
	}

	/**
	 * 租户菜单权限自定义筛选
	 */
	private List<TreeNode> tenantPackageTree(List<TreeNode> menuTree, String tenantId) {
		TenantPackage tenantPackage = SysCache.getTenantPackage(tenantId);
		if (!AuthUtil.isAdministrator() && Func.isNotEmpty(tenantPackage) && tenantPackage.getId() > 0L) {
			List<Long> menuIds = Func.toLongList(tenantPackage.getMenuId());
			menuTree = menuTree.stream().filter(x -> menuIds.contains(x.getId())).collect(Collectors.toList());
		}
		return menuTree;
	}

	/**
	 * 租户菜单权限自定义筛选
	 */
	private List<Menu> tenantPackageMenu(List<Menu> menu) {
		TenantPackage tenantPackage = SysCache.getTenantPackage(AuthUtil.getTenantId());
		if (Func.isNotEmpty(tenantPackage) && tenantPackage.getId() > 0L) {
			List<Long> menuIds = Func.toLongList(tenantPackage.getMenuId());
			menu = menu.stream().filter(x -> menuIds.contains(x.getId())).collect(Collectors.toList());
		}
		return menu;
	}

	@Override
	public List<TreeNode> grantDataScopeTree(BladeUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantDataScopeTree() : baseMapper.grantDataScopeTreeByRole(Func.toLongList(user.getRoleId())));
	}

	@Override
	public List<TreeNode> grantApiScopeTree(BladeUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? baseMapper.grantApiScopeTree() : baseMapper.grantApiScopeTreeByRole(Func.toLongList(user.getRoleId())));
	}

	@Override
	public List<String> roleTreeKeys(String roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
		return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<String> topTreeKeys(String topMenuIds) {
		List<TopMenuSetting> settings = topMenuSettingService.list(Wrappers.<TopMenuSetting>query().lambda().in(TopMenuSetting::getTopMenuId, Func.toLongList(topMenuIds)));
		return settings.stream().map(setting -> Func.toStr(setting.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<String> dataScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().eq(RoleScope::getScopeCategory, DATA_SCOPE_CATEGORY).in(RoleScope::getRoleId, Func.toLongList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	public List<String> apiScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().eq(RoleScope::getScopeCategory, API_SCOPE_CATEGORY).in(RoleScope::getRoleId, Func.toLongList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	@Cacheable(cacheNames = MENU_CACHE, key = "'auth:routes:' + #user.roleId")
	public List<Kv> authRoutes(BladeUser user) {
		List<MenuDTO> routes = baseMapper.authRoutes(Func.toLongList(user.getRoleId()));
		List<Kv> list = new ArrayList<>();
		routes.forEach(route -> list.add(Kv.create().set(route.getPath(), Kv.create().set("authority", Func.toStrArray(route.getAlias())))));
		return list;
	}

	@Override
	public boolean removeMenu(String ids) {
		Long cnt = baseMapper.selectCount(Wrappers.<Menu>query().lambda().in(Menu::getParentId, Func.toLongList(ids)));
		if (cnt > 0L) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeByIds(Func.toLongList(ids));
	}

	@Override
	public boolean submit(Menu menu) {
		/*LambdaQueryWrapper<Menu> menuQueryWrapper = Wrappers.lambdaQuery();
		if (menu.getId() == null) {
			menuQueryWrapper.eq(Menu::getCode, menu.getCode()).or(
				wrapper -> wrapper.eq(Menu::getName, menu.getName()).eq(Menu::getCategory, MENU_CATEGORY)
			);
		} else {
			menuQueryWrapper.ne(Menu::getId, menu.getId()).and(
				wrapper -> wrapper.eq(Menu::getCode, menu.getCode()).or(
					o -> o.eq(Menu::getName, menu.getName()).eq(Menu::getCategory, MENU_CATEGORY)
				)
			);
		}
		Long cnt = baseMapper.selectCount(menuQueryWrapper);
		if (cnt > 0L) {
			throw new ServiceException("菜单名或编号已存在!");
		}
		if (menu.getParentId() == null && menu.getId() == null) {
			menu.setParentId(BladeConstant.TOP_PARENT_ID);
		}*/
		if (menu.getParentId() != null && menu.getId() == null) {
			Menu parentMenu = baseMapper.selectById(menu.getParentId());
			if (parentMenu != null && parentMenu.getCategory() != 1) {
				throw new ServiceException("父节点只可选择菜单类型!");
			}
		}
		menu.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		return saveOrUpdate(menu);
	}

}
