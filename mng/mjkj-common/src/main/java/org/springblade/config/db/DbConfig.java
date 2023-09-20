package org.springblade.config.db;

import org.springblade.cgform.model.database.util.DbConvertDef;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * todo  未优化
 */
@Slf4j
public class DbConfig {
	private static final String DATABASE_CONFIG = "mjkj/mjkj_database";
	private static final String CODE_CONFIG = "mjkj/mjkj_config";
	private static ResourceBundle DATABASE_RESOURCE_BUNDLE;
	private static ResourceBundle CODE_RESOURCE_BUNDLE;
	public static String dbType;
	public static String driver;
	public static String url;
	public static String username;
	public static String password;
	public static String dbName;
	public static String projectPath;
	public static String bussiPackage;
	public static String sourceRoot;
	public static String webRoot;
	public static String templatepath;
	public static boolean dbFiledConvert;
	public static String dbTableId;
	public static String pageFieldRequiredNum;
	public static String pageSearchFiledNum;
	public static String pageFilterFields;
	public static String pageFieldRowNum;

	private static ResourceBundle getResourceBundleInConfig(final String s) {
		ResourceBundle resourceBundle = null;
		InputStream inputStream = null;
		final String string = System.getProperty("user.dir") + File.separator + "config" + File.separator + s
				+ ".properties";
		try {
			inputStream = new BufferedInputStream(new FileInputStream(string));
			resourceBundle = new PropertyResourceBundle(inputStream);
			((BufferedInputStream) inputStream).close();
			if (resourceBundle != null) {
				log.debug(" JAR方式部署，通过config目录读取配置：" + string);
			}
		} catch (IOException e) {
			if (inputStream != null) {
				try {
					((BufferedInputStream) inputStream).close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			if (inputStream != null) {
				try {
					((BufferedInputStream) inputStream).close();
				} catch (IOException ex2) {
					ex2.printStackTrace();
				}
			}
		}
		return resourceBundle;
	}

	public static final String getDriver() {
		return DATABASE_RESOURCE_BUNDLE.getString("diver_name");
	}

	public static final String getUrl() {
		return DATABASE_RESOURCE_BUNDLE.getString("url");
	}

	public static final String getUsername() {
		return DATABASE_RESOURCE_BUNDLE.getString("username");
	}

	public static final String getPassword() {
		return DATABASE_RESOURCE_BUNDLE.getString("password");
	}

	public static final String getDbName() {
		return DATABASE_RESOURCE_BUNDLE.getString("database_name");
	}

	public static final boolean getDbFiledConvert() {
		return !CODE_RESOURCE_BUNDLE.getString("db_filed_convert").toString().equals("false");
	}

	private static String getBussiPackage() {
		return CODE_RESOURCE_BUNDLE.getString("bussi_package");
	}

	private static String getTemplatepath() {
		return CODE_RESOURCE_BUNDLE.getString("templatepath");
	}

	public static final String getSourceRoot() {
		return CODE_RESOURCE_BUNDLE.getString("source_root_package");
	}

	public static final String getWebRoot() {
		return CODE_RESOURCE_BUNDLE.getString("webroot_package");
	}

	public static final String getDbTableId() {
		return CODE_RESOURCE_BUNDLE.getString("db_table_id");
	}

	public static final String getPageFilterFields() {
		return CODE_RESOURCE_BUNDLE.getString("page_filter_fields");
	}

	public static final String getPageSearchFiledNum() {
		return CODE_RESOURCE_BUNDLE.getString("page_search_filed_num");
	}

	public static final String getPageFieldRequiredNum() {
		return CODE_RESOURCE_BUNDLE.getString("page_field_required_num");
	}

	private static String getPageFieldRowNum() {
		return CODE_RESOURCE_BUNDLE.getString("page_field_row_num");
	}

	public static String getProjectPath() {
		final String string = CODE_RESOURCE_BUNDLE.getString("project_path");
		if (string != null && !"".equals(string)) {
			DbConfig.projectPath = string;
		}
		return DbConfig.projectPath;
	}

	public static void setProjectPath(String projectPath) {
		DbConfig.projectPath = projectPath;
	}

	public static void setTemplatepath(String templatepath) {
		DbConfig.templatepath = templatepath;
	}

	static {
		DATABASE_RESOURCE_BUNDLE = getResourceBundleInConfig(DATABASE_CONFIG);
		if (DATABASE_RESOURCE_BUNDLE == null) {
			log.debug("通过class目录加载配置文件 " + DATABASE_CONFIG);
			DATABASE_RESOURCE_BUNDLE = ResourceBundle.getBundle(DATABASE_CONFIG);
		}
		CODE_RESOURCE_BUNDLE = getResourceBundleInConfig(CODE_CONFIG);
		if (CODE_RESOURCE_BUNDLE == null) {
			log.debug("通过class目录加载配置文件 " + CODE_CONFIG);
			CODE_RESOURCE_BUNDLE = ResourceBundle.getBundle(CODE_CONFIG);
		}
		DbConfig.dbType = "mysql";
		DbConfig.driver = "com.mysql.jdbc.Driver";
		DbConfig.url = "jdbc:mysql://localhost:3306/mjkj-boot?useUnicode=true&characterEncoding=UTF-8";
		DbConfig.username = "root";
		DbConfig.password = "root";
		DbConfig.dbName = "mjkj-boot";
		DbConfig.projectPath = "c:/workspace/mjkj";
		DbConfig.bussiPackage = "com.mjkj";
		DbConfig.sourceRoot = "src";
		DbConfig.webRoot = "WebRoot";
		DbConfig.templatepath = "/mjkj/code-template/";
		DbConfig.dbFiledConvert = true;
		DbConfig.pageFieldRequiredNum = "4";
		DbConfig.pageSearchFiledNum = "3";
		DbConfig.pageFieldRowNum = "1";
		DbConfig.driver = getDriver();
		DbConfig.url = getUrl();
		DbConfig.username = getUsername();
		DbConfig.password = getPassword();
		DbConfig.dbName = getDbName();
		DbConfig.sourceRoot = getSourceRoot();
		DbConfig.webRoot = getWebRoot();
		DbConfig.bussiPackage = getBussiPackage();
		DbConfig.templatepath = getTemplatepath();
		DbConfig.projectPath = getProjectPath();
		DbConfig.dbTableId = getDbTableId();
		DbConfig.dbFiledConvert = getDbFiledConvert();
		DbConfig.pageFilterFields = getPageFilterFields();
		DbConfig.pageSearchFiledNum = getPageSearchFiledNum();
		DbConfig.pageFieldRequiredNum = getPageFieldRequiredNum();
		DbConfig.pageFieldRowNum = getPageFieldRowNum();
		if (url.indexOf("mysql") >= 0 || url.indexOf("MYSQL") >= 0) {
			DbConfig.dbType = DbConvertDef.MYSQL;
		} else if (url.indexOf("oracle") >= 0 || url.indexOf("ORACLE") >= 0) {
			DbConfig.dbType = DbConvertDef.ORACLE;
		} else if (url.indexOf("postgresql") >= 0 || url.indexOf("POSTGRESQL") >= 0) {
			DbConfig.dbType = DbConvertDef.POSTGRESQL;
		} else if (url.indexOf("sqlserver") >= 0 || url.indexOf("sqlserver") >= 0) {
			DbConfig.dbType = DbConvertDef.SQLSERVER;
		}
		DbConfig.sourceRoot = DbConfig.sourceRoot.replace(".", "/");
		DbConfig.webRoot = DbConfig.webRoot.replace(".", "/");
	}

}
