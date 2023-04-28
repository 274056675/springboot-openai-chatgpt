package org.springblade.mng.config.miniapp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 小程序支付配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {

	private String appId;
	private String mchId;
	private String partnerKey;
	private String certPath;
	private String domain;

}
