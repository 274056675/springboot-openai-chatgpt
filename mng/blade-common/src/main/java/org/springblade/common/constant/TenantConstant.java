
package org.springblade.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 租户常量
 *
 *
 */
public interface TenantConstant {

	/**
	 * 租户默认密码KEY
	 */
	String PASSWORD_KEY = "tenant.default.password";

	/**
	 * 租户默认账号额度KEY
	 */
	String ACCOUNT_NUMBER_KEY = "tenant.default.accountNumber";

	/**
	 * 租户默认菜单集合KEY
	 */
	String ACCOUNT_MENU_CODE_KEY = "tenant.default.menuCode";

	/**
	 * 租户默认密码
	 */
	String DEFAULT_PASSWORD = "123456";

	/**
	 * 租户授权码默认16位密钥
	 */
	String DES_KEY = "0000000000000000";

	/**
	 * 租户默认账号额度
	 */
	Integer DEFAULT_ACCOUNT_NUMBER = -1;

	/**
	 * 租户默认菜单集合
	 */
	List<String> MENU_CODES = Arrays.asList(
		"desk", "flow", "work", "monitor", "resource", "role", "user", "dept", "dictbiz", "topmenu"
	);

}
