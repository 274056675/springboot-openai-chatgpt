
package org.springblade.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springblade.system.entity.Tenant;

import java.util.Date;
import java.util.List;

/**
 * 服务类
 *
 *
 */
public interface ITenantService extends BaseService<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant);

	/**
	 * 根据租户编号获取实体
	 *
	 * @param tenantId
	 * @return
	 */
	Tenant getByTenantId(String tenantId);

	/**
	 * 新增
	 *
	 * @param tenant
	 * @return
	 */
	boolean submitTenant(Tenant tenant);

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 */
	boolean removeTenant(List<Long> ids);

	/**
	 * 配置租户授权
	 *
	 * @param accountNumber
	 * @param expireTime
	 * @param ids
	 * @return
	 */
	boolean setting(Integer accountNumber, Date expireTime, String ids);

}
