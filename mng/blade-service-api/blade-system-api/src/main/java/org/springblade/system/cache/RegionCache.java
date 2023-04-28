
package org.springblade.system.cache;

import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.system.feign.ISysClient;

/**
 * 行政区划缓存工具类
 *
 *
 */
public class RegionCache {
	public static final int PROVINCE_LEVEL = 1;
	public static final int CITY_LEVEL = 2;
	public static final int DISTRICT_LEVEL = 3;
	public static final int TOWN_LEVEL = 4;
	public static final int VILLAGE_LEVEL = 5;

	private static final String REGION_CODE = "region:code:";

	private static ISysClient sysClient;

	private static ISysClient getSysClient() {
		if (sysClient == null) {
			sysClient = SpringUtil.getBean(ISysClient.class);
		}
		return sysClient;
	}



}
