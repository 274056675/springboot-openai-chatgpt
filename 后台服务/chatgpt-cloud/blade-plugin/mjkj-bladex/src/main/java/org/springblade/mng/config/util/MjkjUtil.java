package org.springblade.mng.config.util;

import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author jh
 * @version 1.0
 * @date 2022/6/14 10:38
 */
public class MjkjUtil {

	// json转换
	public static String stringTojson(String text) {
		String header = WebUtil.getRequest().getHeader("mj-lang");
		if (Func.isNotEmpty(text)) {
			Map<String, String> map = JsonUtil.parse(text, Map.class);
			String s = map.get(header);
			return s;
		}
		return "";
	}
}
