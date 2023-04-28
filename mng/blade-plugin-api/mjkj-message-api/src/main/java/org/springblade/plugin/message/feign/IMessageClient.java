package org.springblade.plugin.message.feign;


import org.springblade.core.tool.api.R;
import org.springblade.plugin.message.model.ChatGptMoreFunModel;
import org.springblade.plugin.message.model.ChatGptMsgModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 消息
 */
@FeignClient(
	value = "open-message"
)
public interface IMessageClient {

	String API_PREFIX = "/client";
	String SEND_CHAT_GPT_MSG = API_PREFIX + "/send-chatgpt-msg";

	//更多好玩
	String SEND_CHAT_GPT_MORE_FUN_MSG = API_PREFIX + "/send-chatgpt-more-fun-msg";

	/**
	 * 发送消息
	 *
	 * @param model 消息实体
	 * @return
	 */
	@PostMapping(SEND_CHAT_GPT_MSG)
	R<Boolean> sendChatGptMsg(@RequestBody ChatGptMsgModel model);


	/**
	 * 发送消息
	 *
	 * @param model 消息实体
	 * @return
	 */
	@PostMapping(SEND_CHAT_GPT_MORE_FUN_MSG)
	R<Boolean> sendChatMoreFunMsg(@RequestBody ChatGptMoreFunModel model);


}
