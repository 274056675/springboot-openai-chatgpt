package org.springblade.mng.service;

import org.springblade.mng.model.AccountUseCouModel;
import org.springblade.mng.model.ChatGptTurboResult;
import org.springblade.mng.model.MessageModel;

import java.util.Date;
import java.util.List;

/**
 * ChatGpt相关
 */
public interface IChatGPTService {

	//gpt3.0
	void sendChatGptMessage(String wxUserId, String q_logMessageId, String question, Long startMessageId, Long chatListIdL);

	//gpt3.5
	void sendChatGptTurboMessage(String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime, Long chatListIdL);

	//机器人发送图片



	//发送实时消息，长连接等着返回
	String sendNowTimeChatGptTurboMessage(List<MessageModel> messagesList, String model);


	AccountUseCouModel getChatGptKey();

	List<ChatGptTurboResult.ChoiceModel> getChatGptTurboResponse(List<MessageModel> messagesList, AccountUseCouModel accountModel, String aiModel);


}
