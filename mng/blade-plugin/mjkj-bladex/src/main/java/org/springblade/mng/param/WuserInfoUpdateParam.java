package org.springblade.mng.param;

import lombok.Data;

/**
 *更新用户信息
 */
@Data
public class WuserInfoUpdateParam {
	String wxName;
	String wxAvatar;
	String phone;

	//个人设置
	String translateLang;//默认翻译语言
	//提现二维码
	String withdrawalQrcode;//提现二维码
}
