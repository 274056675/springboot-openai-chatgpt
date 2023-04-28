
package org.springblade.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.cgform.entity.CheckRule;
import org.springblade.core.mp.base.BaseService;

/**
 * 编码校验规则 服务类
 *
 *
 * @since 2021-07-03
 */
public interface ICheckRuleService extends BaseService<CheckRule> {

	/**
	 * 通过用户设定的自定义校验规则校验传入的值
	 *
	 * @param checkRule
	 * @param value
	 * @return 返回 null代表通过校验，否则就是返回的错误提示文本
	 */
	JSONObject checkValue(CheckRule checkRule, String value);
}
