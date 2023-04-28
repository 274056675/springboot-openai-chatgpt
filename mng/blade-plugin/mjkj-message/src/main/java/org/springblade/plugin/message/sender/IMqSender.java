package org.springblade.plugin.message.sender;

/**
 * mq发送消息接口
 * @author weikun
 */
public interface IMqSender {
	/**
	 * 发送socket消息
	 * @param topic
	 * @param content
	 */
	void sendSocketMsg(String topic,String content);

}
