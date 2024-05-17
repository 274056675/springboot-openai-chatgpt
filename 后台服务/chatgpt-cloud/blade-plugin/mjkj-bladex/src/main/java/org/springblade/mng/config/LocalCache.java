package org.springblade.mng.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;

/**
 * @author JX
 * @create 2023-11-17 22:30
 * @dedescription:
 */
public class LocalCache {

	public static final long TIMEOUT = 12 * DateUnit.HOUR.getMillis();
	/**
	 * 清理间隔
	 */
	private static final long CLEAN_TIMEOUT = 12 * DateUnit.HOUR.getMillis();
	/**
	 * 缓存对象
	 */
	public static final TimedCache<String, Object> CACHE = CacheUtil.newTimedCache(TIMEOUT);

	static {
		//启动定时任务
		CACHE.schedulePrune(CLEAN_TIMEOUT);
	}
}
