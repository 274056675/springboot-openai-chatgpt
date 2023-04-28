package org.springblade.plugin.message.utils;

public class MessageUtils {
	//获取消息主题
	public static String getTopic(String chatId){
		String topic="chatgpt_"+chatId;
		return topic;
	}

	//获取更多好玩主题
	public static String getMoreFunTopic(String chatId){
		String topic="more_fun_"+chatId;
		return topic;
	}
}
