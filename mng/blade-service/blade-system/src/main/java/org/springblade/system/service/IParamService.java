
package org.springblade.system.service;

import org.springblade.core.mp.base.BaseService;
import org.springblade.system.entity.Param;

/**
 * 服务类
 *
 *
 */
public interface IParamService extends BaseService<Param> {

	/**
	 * 获取参数值
	 *
	 * @param paramKey 参数key
	 * @return String
	 */
	String getValue(String paramKey);

}
