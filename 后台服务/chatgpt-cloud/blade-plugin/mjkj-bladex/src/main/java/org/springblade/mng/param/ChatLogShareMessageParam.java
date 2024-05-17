package org.springblade.mng.param;

import lombok.Data;

@Data
public class ChatLogShareMessageParam {
	private String[] messageIds;//messageIds对应chat_log_message的id,作消息的序列号
	private String onlyId; //唯一标识符
}
