
package org.springblade.system.feign;

import org.springblade.core.datascope.model.DataScopeModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * IDataScopeClientFallback
 *
 *
 */
@Component
public class IDataScopeClientFallback implements IDataScopeClient {
	@Override
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		return null;
	}

	@Override
	public DataScopeModel getDataScopeByCode(String code) {
		return null;
	}

	@Override
	public List<Long> getDeptAncestors(Long deptId) {
		return null;
	}
}
