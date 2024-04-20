package org.springblade.mng.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mjkjsms")
public class MjkjSmsConfig {

	private static String accessKey;

	private static String secretKey;

	private static String signName;

	private static String templateId;


	public static String getAccessKey() {
		return accessKey;
	}

	public  void setAccessKey(String accessKey) {
		MjkjSmsConfig.accessKey = accessKey;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public  void setSecretKey(String secretKey) {
		MjkjSmsConfig.secretKey = secretKey;
	}

	public static String getSignName() {
		return signName;
	}

	public  void setSignName(String signName) {
		MjkjSmsConfig.signName = signName;
	}

	public static String getTemplateId() {
		return templateId;
	}

	public  void setTemplateId(String templateId) {
		MjkjSmsConfig.templateId = templateId;
	}
}
