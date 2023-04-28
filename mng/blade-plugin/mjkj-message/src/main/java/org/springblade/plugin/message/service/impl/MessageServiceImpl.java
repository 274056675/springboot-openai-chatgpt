package org.springblade.plugin.message.service.impl;

import org.springblade.plugin.message.sender.IMqSender;
import org.springblade.plugin.message.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 消息相关
 */
@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private IMqSender mqSender;

	@Async("messagePoolTaskExecutor")
	@Override
	public void sendMessage(String topic,String content){
		try{
			mqSender.sendSocketMsg(topic,content);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
