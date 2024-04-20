package org.springblade.mng.mapper;

import java.util.List;
import java.util.Map;

/**
 * 文件管理-首页统计
 */
public interface MjkjFileIndexMapper {

	//统计相关
	Long getTjZyzlCouByTime(String time);//今日总量
	Long getTjZyzlCouByMonth(String time);//今月总量
	Long getTjWdCouByTime(String time);//文档数量
	Long getTjSpCouByTime(String time);//视频数量

	Long getTjCouByMonth(String time, String type);//下载量月
	Long getTjCouByDay(String time, String type);//下载量天
	List<Map<String, Object>> uploadList(String paramStartTime, String paramEndTime);//上传列表
	List<Map<String, Object>> downloadList(String paramStartTime, String paramEndTime);//下载列表

	Long getYlUserDay(String time);//视频数量
	Long getYlCouDay(String time);//视频数量

	Long getTjUserCouByTime(String time);//今日总量
}
