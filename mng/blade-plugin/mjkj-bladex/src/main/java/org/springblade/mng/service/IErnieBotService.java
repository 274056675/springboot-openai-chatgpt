package org.springblade.mng.service;

import org.springblade.mng.model.MessageModel;

import java.util.Date;
import java.util.List;

/**
 * @author JX
 * @create 2023-09-08 14:28
 * @dedescription:文心一言 接口
 */
public interface IErnieBotService {

	//给文心一言发消息
	void sendErnieBotTurboMessage(String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime);

	//获取文心一言的回复
	String getErnieBotTurboResponse(List<MessageModel> messagesList, String ernieToken);

	//上下文处理
	List<MessageModel> handleErnieBotContext(String wxUserId, String question, String logMessageId, Long startMessageId);

	//获取文心一言的token
	String getAccessToken(String appKey, String secretKey);
}
