package org.springblade.mng.param.mng;

import lombok.Data;

/**
 * 后台-人工充值会员
 */
@Data
public class MngMemberParam {
	private String phone;
	private String type;//add=增加  sub=减少
	private Integer day;
	private String amount;
	private String remark;
	private Integer rlCou;
}
