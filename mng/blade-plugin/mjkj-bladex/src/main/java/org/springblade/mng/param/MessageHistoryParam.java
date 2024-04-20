package org.springblade.mng.param;

import lombok.Data;
import org.springblade.core.mp.support.Query;

@Data
public class MessageHistoryParam extends Query {
	private Long startNum;
	private String modelType;//分类
	private String chatListId;
	private String type; //pc端
}
