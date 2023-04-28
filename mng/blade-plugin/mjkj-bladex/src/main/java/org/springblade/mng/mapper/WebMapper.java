
package org.springblade.mng.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 *后台相关
 */
public interface WebMapper {

	//获使用最小次数
	Map<String,Object> getMinAccountCou();

	//获取消息次数
	Integer getMessageCou(Long bladeUserId);

	//获取热门消息
	IPage<Map<String, Object>> getMessageHotList(IPage page);

	//获取历史聊天记录
	IPage<Map<String, Object>> getSubCouList(String wxuserId, IPage<Object> page);
}
