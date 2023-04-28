
package org.springblade.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.system.cache.DictCache;
import org.springblade.system.entity.DataScope;
import org.springblade.system.enums.DictEnum;
import org.springblade.system.vo.DataScopeVO;

import java.util.Objects;


/**
 * 包装类,返回视图层所需的字段
 *
 *
 */
public class DataScopeWrapper extends BaseEntityWrapper<DataScope, DataScopeVO> {

	public static DataScopeWrapper build() {
		return new DataScopeWrapper();
	}

	@Override
	public DataScopeVO entityVO(DataScope dataScope) {
		DataScopeVO dataScopeVO = Objects.requireNonNull(BeanUtil.copy(dataScope, DataScopeVO.class));
		String scopeTypeName = DictCache.getValue(DictEnum.DATA_SCOPE_TYPE, dataScope.getScopeType());
		dataScopeVO.setScopeTypeName(scopeTypeName);
		return dataScopeVO;
	}

}
