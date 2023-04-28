
package org.springblade.system.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.system.entity.TopMenu;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 顶部菜单表 服务类
 *
 *
 */
public interface ITopMenuService extends BaseService<TopMenu> {

	/**
	 * 顶部菜单配置
	 *
	 * @param topMenuIds 顶部菜单id集合
	 * @param menuIds    菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> topMenuIds, @NotEmpty List<Long> menuIds);

}
