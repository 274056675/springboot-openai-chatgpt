package org.springblade.mng.service;

import org.springblade.mng.model.AccountUseCouModel;
import org.springblade.mng.model.ChatGptTurboResult;
import org.springblade.mng.model.MessageModel;
import org.springblade.plugin.message.model.ChatGptMsgModel;

import java.util.Date;
import java.util.List;

/**
 * ChatGpt相关
 */
public interface IChatGPTService {

	//gpt3.0
	void sendChatGptMessage(String modelType,String wxUserId,String q_logMessageId,String question,Long startMessageId);

	//gpt3.5
	void sendChatGptTurboMessage(String modelType, String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime);

	//机器人发送图片
	void sendImageMessage(String modelType, String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime, String size);

	//发送实时消息，长连接等着返回
	String sendNowTimeChatGptTurboMessage(List<MessageModel> messagesList);

	//获取图片
	String getDALLEImages(String prompt,String size);

	AccountUseCouModel getChatGptKey();

	List<ChatGptTurboResult.ChoiceModel> getChatGptTurboResponse(List<MessageModel> messagesList, AccountUseCouModel accountModel);

	//flagstudio 生成图片
	String getFlagstudioImages(String prompt);

}
