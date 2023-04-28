
package org.springblade.system.feign;


import lombok.AllArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.Dict;
import org.springblade.system.service.IDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 字典服务Feign实现类
 *
 *
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictClient implements IDictClient {

	private final IDictService service;

	@Override
	@GetMapping(GET_BY_ID)
	public R<Dict> getById(Long id) {
		return R.data(service.getById(id));
	}

	@Override
	@GetMapping(GET_VALUE)
	public R<String> getValue(String code, String dictKey) {
		return R.data(service.getValue(code, dictKey));
	}

	@Override
	@GetMapping(GET_LIST)
	public R<List<Dict>> getList(String code) {
		return R.data(service.getList(code));
	}

}
