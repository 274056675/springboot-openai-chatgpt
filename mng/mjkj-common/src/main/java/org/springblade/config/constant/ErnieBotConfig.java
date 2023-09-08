package org.springblade.config.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JX
 * @create 2023-09-08 14:43
 * @dedescription: 文心一言的各个配置 需要在nacos中配置1
 */


@Data
@Component
@ConfigurationProperties(prefix = "erniebot")
public class ErnieBotConfig {
	public static String  URL;

	public static String appKey;

	public static String secretKey;


	//文本长度
	private static Integer ernieBotMaxToken;

	public static Integer getErnieBotMaxToken() {
		return ernieBotMaxToken;
	}

	public  void setErnieBotMaxToken(Integer ernieBotMaxToken) {
		ErnieBotConfig.ernieBotMaxToken = ernieBotMaxToken;
	}


	public static String getURL() {
		return URL;
	}

	public  void setURL(String URL) {
		ErnieBotConfig.URL = URL;
	}

	public static String getAppKey() {
		return appKey;
	}

	public  void setAppKey(String appKey) {
		ErnieBotConfig.appKey = appKey;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public  void setSecretKey(String secretKey) {
		ErnieBotConfig.secretKey = secretKey;
	}
}
