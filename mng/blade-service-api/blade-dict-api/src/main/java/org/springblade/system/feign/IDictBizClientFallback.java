
package org.springblade.system.feign;

import org.springblade.core.tool.api.R;
import org.springblade.system.entity.DictBiz;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 *
 */
@Component
public class IDictBizClientFallback implements IDictBizClient {
	@Override
	public R<DictBiz> getById(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getValue(String code, String dictKey) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<DictBiz>> getList(String code) {
		return R.fail("获取数据失败");
	}
}
