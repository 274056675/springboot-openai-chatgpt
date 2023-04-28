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
}
