
package org.springblade.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务字典枚举类
 *
 *
 */
@Getter
@AllArgsConstructor
public enum DictBizEnum {

	/**
	 * 测试
	 */
	TEST("test"),
	;

	final String name;

}
