package org.springblade.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mjkj")
public class MjjyConfig {

    private static String excludeTable;//排除表名

	private static String databaseSchema;//数据库名称

	private static String generateExcludeTable;//反向生成数据库排除表

	private static boolean debug=false;

	//微信开放平台登录
	private static String wx_open_appId;
	private static String wx_open_appSecret;
	private static String wx_open_redirectUri;


    public static String getExcludeTable() { return excludeTable;}

    public void setExcludeTable(String excludeTable) {
        MjjyConfig.excludeTable = excludeTable;
    }

	public static String getWx_open_appId() {
		return wx_open_appId;
	}

	public  void setWx_open_appId(String wx_open_appId) {
		MjjyConfig.wx_open_appId = wx_open_appId;
	}

	public static String getWx_open_appSecret() {
		return wx_open_appSecret;
	}

	public  void setWx_open_appSecret(String wx_open_appSecret) {
		MjjyConfig.wx_open_appSecret = wx_open_appSecret;
	}

	public static String getWx_open_redirectUri() {
		return wx_open_redirectUri;
	}

	public void setWx_open_redirectUri(String wx_open_redirectUri) {
		MjjyConfig.wx_open_redirectUri = wx_open_redirectUri;
	}

	public static String getDatabaseSchema() {
		return databaseSchema;
	}

	public void setDatabaseSchema(String databaseSchema) {
		MjjyConfig.databaseSchema = databaseSchema;
	}

	public static String getGenerateExcludeTable() {
		return generateExcludeTable;
	}

	public void setGenerateExcludeTable(String generateExcludeTable) {
		MjjyConfig.generateExcludeTable = generateExcludeTable;
	}


	public static boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		MjjyConfig.debug = debug;
	}
}
