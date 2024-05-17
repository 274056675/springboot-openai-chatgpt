package org.springblade.mng.config.util.sms;

import lombok.Data;

/**
 * 移动短信验证码
 */
@Data
public class YunMasConfig {

	//移动云
	private String ecName;//企业名称。
	private String apId;//接口账号用户名
	private String secretKey;//密码
	private String mobiles;//收信手机号码。英文逗号分隔，每批次限5000个号码，例：“13800138000,13800138001,13800138002”。
	private String content;//短信内容。
	private String sign;//签名编码。在云MAS平台『管理』→『接口管理』→『短信接入用户管理』获取。
	private String addSerial;
	private String mac;//参数校验序列，生成方法：将ecName、apId、secretKey、mobiles、content、sign、addSerial按序拼接（无间隔符），通过MD5（32位小写）计算得出值。
	private String url;//请求地址


}
