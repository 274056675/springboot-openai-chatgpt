package org.springblade.mng.param.mng;

import lombok.Data;

import java.util.List;

/**
 * 后台-人工修改次数
 */
@Data
public class MngQuestionCouParam {
	private List<String> ids;
	private String type;//add=增加  sub=减少
	private Integer num;
	private String remark;

	private String manageType; //question 提问次数 rl 燃料次数
}
