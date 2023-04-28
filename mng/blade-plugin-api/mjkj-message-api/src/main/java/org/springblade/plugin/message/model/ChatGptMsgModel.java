package org.springblade.plugin.message.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChatGptMsgModel {

	private String id;
	private String pid;
	//消息id
	private String chatCode;

	private String message_type;//q=提问  a=回答

	private String message_content;//内存

	private Date message_time;//时间

	private String view_type;//显示方式

	private String useTime;//耗时
}
