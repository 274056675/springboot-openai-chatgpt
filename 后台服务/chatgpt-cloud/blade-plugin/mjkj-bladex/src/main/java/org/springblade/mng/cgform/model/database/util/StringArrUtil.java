package org.springblade.mng.cgform.model.database.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class StringArrUtil
{

	/**
	 * 字符数组处理：给字符串数组中的字符串加单引号和逗号
	 *
	 * @return
	 */
    public static String a(String[] array) {
        final StringBuffer sb = new StringBuffer();
        for (final String s : array) {
            if (StringUtils.isNotBlank(s)) {
                sb.append(",");
                sb.append("'");
                sb.append(s.trim());
                sb.append("'");
            }
        }
        return sb.toString().substring(1);
    }

    /**
	 * 首字母小写
	 *
	 * @param paramString
	 * @return
	 */
    public static String a(String string) {
        if (StringUtils.isNotBlank(string)) {
            string = string.substring(0, 1).toLowerCase() + string.substring(1);
        }
        return string;
    }

    /**
	 * 为数值添加默认值
	 *
	 * @param paramInteger
	 * @return
	 */
    public static Integer a(Integer n) {
        if (n == null) {
            return 0;
        }
        return n;
    }

    public static boolean isInclude(String s, String[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(String s, List<String> list) {
        String[] array = new String[0];
        if (list != null) {
            array = (String[])list.toArray();
        }
        if (array == null || array.length == 0) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equals(s)) {
                return true;
            }
        }
        return false;
    }
}
