package org.springblade.mng.param;

import lombok.Data;

/**
 *更新用户信息
 */
@Data
public class WuserInfoUpdateParam {
	String wxName; //用户名
	String wxAvatar; //头像
	String phone;
	//个人设置
	String translateLang;//默认翻译语言
	//提现二维码
	String withdrawalQrcode;//提现二维码
	String aiModel;//选择模型
	String imageModel;//绘画模型
	String sign; //个性签名
	String code; //手机验证码
}
