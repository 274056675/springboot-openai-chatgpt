package org.springblade.mng.model;

import lombok.Data;

import java.util.Date;

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

	private Integer store_status; //收藏状态


	private String size;//尺寸

	private String proportion;//比例

	private String picture;

	private String uuid;

	private String chat_list_id;
}
