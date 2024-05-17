package org.springblade.mng.param;

import lombok.Data;

@Data
public class QuestionParam {
	//问题
	private String question;

	//分类
	private String modelType;

	//上下文id
	private String startMessageId;

	private String uuid;

	//回复的文本类型
	private String text_type;

	//对应聊天列表的id
	private String chatListId;

	//当前的的客户端类型 新版pc端为pc
	private String type;
}
