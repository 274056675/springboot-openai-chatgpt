package org.springblade.mng.cgform.model.generate.util;

import org.apache.commons.lang.StringUtils;

public class StringBoolUtil {

    public static String strBool(String s) {
        if ("YES".equals(s) || "yes".equals(s) || "y".equals(s) || "Y".equals(s) || "f".equals(s)) {
            return "Y";
        }
        if ("NO".equals(s) || "N".equals(s) || "no".equals(s) || "n".equals(s) || "t".equals(s)) {
            return "N";
        }
        return null;
    }

    public static String isBlank(String s) {
        if (StringUtils.isBlank(s)) {
            return "";
        }
        return s;
    }

    public static String strToStr(String s) {
        return "'" + s + "'";
    }
}
