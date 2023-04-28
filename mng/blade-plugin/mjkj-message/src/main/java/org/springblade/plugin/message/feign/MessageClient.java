package org.springblade.plugin.message.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.plugin.message.model.ChatGptMoreFunModel;
import org.springblade.plugin.message.model.ChatGptMsgModel;
import org.springblade.plugin.message.sender.IMqSender;
import org.springblade.plugin.message.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@NonDS
@RestController
@AllArgsConstructor
public class MessageClient implements IMessageClient {

	@Autowired
	private IMqSender mqSender;

	@Override
	public R<Boolean> sendChatGptMsg(ChatGptMsgModel model){
		String topic= MessageUtils.getTopic(model.getChatCode());

		model.setChatCode(null);//移除消息
		mqSender.sendSocketMsg(topic, JsonUtil.toJson(model));
		return R.data(true);
	}

	/**
	 * 发送消息
	 *
	 * @param model 消息实体
	 * @return
	 */
	public R<Boolean> sendChatMoreFunMsg(ChatGptMoreFunModel model){
		String topic=MessageUtils.getMoreFunTopic(model.getChatCode());

		model.setChatCode(null);//移除消息
		mqSender.sendSocketMsg(topic, JsonUtil.toJson(model));
		return R.data(true);
	}


}
