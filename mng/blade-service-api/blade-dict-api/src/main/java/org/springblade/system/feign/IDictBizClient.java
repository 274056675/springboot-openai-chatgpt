
package org.springblade.system.feign;


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.entity.DictBiz;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 *
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IDictBizClientFallback.class
)
public interface IDictBizClient {

	String API_PREFIX = "/client";
	String GET_BY_ID = API_PREFIX + "/dict-biz/get-by-id";
	String GET_VALUE = API_PREFIX + "/dict-biz/get-value";
	String GET_LIST = API_PREFIX + "/dict-biz/get-list";

	/**
	 * 获取字典实体
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping(GET_BY_ID)
	R<DictBiz> getById(@RequestParam("id") Long id);

	/**
	 * 获取字典表对应值
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	@GetMapping(GET_VALUE)
	R<String> getValue(@RequestParam("code") String code, @RequestParam("dictKey") String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	@GetMapping(GET_LIST)
	R<List<DictBiz>> getList(@RequestParam("code") String code);

}
