package org.springblade.mng.model;

import lombok.Data;

import java.util.Date;

/**
 * 更多好玩
 */
@Data
public class ChatGptMoreFunModel {
	private String id;
	private String chatCode;//消息号
	private String fun_data_id;//更多好玩id
	private String message_a;//内存
	private Date message_q_time;
	private Integer store_status;
}
