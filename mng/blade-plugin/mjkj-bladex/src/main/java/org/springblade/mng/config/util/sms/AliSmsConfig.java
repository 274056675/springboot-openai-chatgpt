package org.springblade.mng.config.util.sms;

import lombok.Data;

/**
 * 阿里云
 */
@Data
public class AliSmsConfig {

	private String accessKey;
	private String secretKey;
	private String qm; //签名

	private String yjdx;//预缴短信
	private String sfdx;//收费短信
	private String xzdx;//销账短信
	private String sczddx;//生成账单短信

	private String qfdx;//欠费短信
	private String tyyzm;//体验验证码
	private String zhdqdx;//租户到期短信
	private String gzgzhdx;//关注公众号短信
	private String bytzdx;//保养通知短信
	private String byhtdqdx;//保养合同到期
}
