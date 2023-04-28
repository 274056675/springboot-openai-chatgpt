
package org.springblade.system.service;

import java.util.List;
import java.util.Set;

/**
 *  魔晶
 *
 */
public interface IMjkjSystemService {


	Set<String> getUserParentDeptList(Long userId);

	String getTenanId(String tenantName);

	boolean grantClient(String roleId, List<Long> menuIds);

}
