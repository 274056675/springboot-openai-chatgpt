
package org.springblade.common.cache;

/**
 * 缓存名
 *
 *
 */
public interface CacheNames {

	/**
	 * 返回拼接后的key
	 *
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String cacheKey(String cacheKey, String cacheKeyValue) {
		return cacheKey.concat(cacheKeyValue);
	}

	/**
	 * 返回租户格式的key
	 *
	 * @param tenantId      租户编号
	 * @param cacheKey      缓存key
	 * @param cacheKeyValue 缓存key值
	 * @return tenantKey
	 */
	static String tenantKey(String tenantId, String cacheKey, String cacheKeyValue) {
		return tenantId.concat(":").concat(cacheKey).concat(cacheKeyValue);
	}

	/**
	 * 验证码key
	 */
	String CAPTCHA_KEY = "blade:auth::blade:captcha:";

	/**
	 * EMAIL验证码key
	 */
	String EMAIL_KEY = "blade:auth::blade:email:";

	/**
	 * 登录失败key
	 */
	String USER_FAIL_KEY = "blade:user::blade:fail:";

}
