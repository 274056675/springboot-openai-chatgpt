package org.springblade.config.util;

import lombok.extern.slf4j.Slf4j;

/**
 * sql注入处理工具类
 * 
 * @author zhoujf
 */
@Slf4j
public class SqlInjectionUtil {
	/**
	 * sign 用于表字典加签的盐值【SQL漏洞】
	 * （上线修改值 20200501，同步修改前端的盐值）
	 */
	private final static String TABLE_DICT_SIGN_SALT = "20200501";
	private final static String xssStr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,";



	/**
	 * sql注入过滤处理，遇到注入关键字抛异常
	 * 
	 * @param value
	 * @return
	 */
	public static void filterContent(String value) {
		if (value == null || "".equals(value)) {
			return;
		}
		// 统一转为小写
		value = value.toLowerCase();
		String[] xssArr = xssStr.split("\\|");
		for (int i = 0; i < xssArr.length; i++) {
			if (value.indexOf(xssArr[i]) > -1) {
				log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
				log.error("请注意，值可能存在SQL注入风险!---> {}", value);
				throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
			}
		}
		return;
	}

	/**
	 * sql注入过滤处理，遇到注入关键字抛异常
	 * 
	 * @param values
	 * @return
	 */
	public static void filterContent(String[] values) {
		String[] xssArr = xssStr.split("\\|");
		for (String value : values) {
			if (value == null || "".equals(value)) {
				return;
			}
			// 统一转为小写
			value = value.toLowerCase();
			for (int i = 0; i < xssArr.length; i++) {
				if (value.indexOf(xssArr[i]) > -1) {
					log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
					log.error("请注意，值可能存在SQL注入风险!---> {}", value);
					throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
				}
			}
		}
		return;
	}

	/**
	 * @特殊方法(不通用) 仅用于字典条件SQL参数，注入过滤
	 * @param value
	 * @return
	 */
	@Deprecated
	public static void specialFilterContent(String value) {
		String specialXssStr = " exec | insert | select | delete | update | drop | count | chr | mid | master | truncate | char | declare |;|+|";
		String[] xssArr = specialXssStr.split("\\|");
		if (value == null || "".equals(value)) {
			return;
		}
		// 统一转为小写
		value = value.toLowerCase();
		for (int i = 0; i < xssArr.length; i++) {
			if (value.indexOf(xssArr[i]) > -1 || value.startsWith(xssArr[i].trim())) {
				log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
				log.error("请注意，值可能存在SQL注入风险!---> {}", value);
				throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
			}
		}
		return;
	}


    /**
     * @特殊方法(不通用) 仅用于Online报表SQL解析，注入过滤
     * @param value
     * @return
     */
	@Deprecated
	public static void specialFilterContentForOnlineReport(String value) {
		String specialXssStr = " exec | insert | delete | update | drop | chr | mid | master | truncate | char | declare |";
		String[] xssArr = specialXssStr.split("\\|");
		if (value == null || "".equals(value)) {
			return;
		}
		// 统一转为小写
		value = value.toLowerCase();
		for (int i = 0; i < xssArr.length; i++) {
			if (value.indexOf(xssArr[i]) > -1 || value.startsWith(xssArr[i].trim())) {
				log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
				log.error("请注意，值可能存在SQL注入风险!---> {}", value);
				throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
			}
		}
		return;
	}

}
