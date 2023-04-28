
package org.springblade.system.feign;

import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dict;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 *
 */
@Component
public class IDictClientFallback implements IDictClient {
	@Override
	public R<Dict> getById(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getValue(String code, String dictKey) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dict>> getList(String code) {
		return R.fail("获取数据失败");
	}
}
