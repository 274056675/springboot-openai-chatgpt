package org.springblade.mng.param;

import lombok.Data;

/**
 * 更多好玩
 */
@Data
public class MoreFunParam {
	private String funFataId;//更多好玩id
	private String systemTitle;//系统标题
	private String content;//内容
	private String paramJson;//参数json
	private String text_type; //回复的格式
	private String chat_list_id; //聊天列表id
	private String type;//客户端 pc
}
