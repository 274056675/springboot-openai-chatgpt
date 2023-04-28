package org.springblade.mng.param;

import lombok.Data;

@Data
public class WxOpenBindPhoneParam {
	String uuid;
	String phone;
	String code;
	String inviteCode;
}
