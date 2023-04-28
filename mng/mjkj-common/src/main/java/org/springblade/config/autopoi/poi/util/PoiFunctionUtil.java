package org.springblade.config.autopoi.poi.util;



import org.springblade.config.autopoi.poi.exception.excel.ExcelExportException;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

/**
 * if else,length,for each,fromatNumber,formatDate 满足模板的 el 表达式支持
 *
 */
public final class PoiFunctionUtil {

	private static final String TWO_DECIMAL_STR = "###.00";
	private static final String THREE_DECIMAL_STR = "###.000";

	private static final DecimalFormat TWO_DECIMAL = new DecimalFormat(TWO_DECIMAL_STR);
	private static final DecimalFormat THREE_DECIMAL = new DecimalFormat(THREE_DECIMAL_STR);

	private static final String DAY_STR = "yyyy-MM-dd";
	private static final String TIME_STR = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME__NO_S_STR = "yyyy-MM-dd HH:mm";

	private static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(DAY_STR);
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(TIME_STR);
	private static final SimpleDateFormat TIME__NO_S_FORMAT = new SimpleDateFormat(TIME__NO_S_STR);

	private PoiFunctionUtil() {
	}

	/**
	 * 获取对象的长度
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int length(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof Map) {
			return ((Map) obj).size();
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).size();
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj);
		}
		return String.valueOf(obj).length();
	}

	/**
	 * 格式化数字
	 * 
	 * @param obj
	 * @throws NumberFormatException
	 * @return
	 */
	public static String formatNumber(Object obj, String format) {
		if (obj == null || obj.toString() == "") {
			return "";
		}
		double number = Double.valueOf(obj.toString());
		DecimalFormat decimalFormat = null;
		if (TWO_DECIMAL.equals(format)) {
			decimalFormat = TWO_DECIMAL;
		} else if (THREE_DECIMAL_STR.equals(format)) {
			decimalFormat = THREE_DECIMAL;
		} else {
			decimalFormat = new DecimalFormat(format);
		}
		return decimalFormat.format(number);
	}

	/**
	 * 格式化时间
	 * 
	 * @param obj
	 * @return
	 */
	public static String formatDate(Object obj, String format) {
		if (obj == null || obj.toString() == "") {
			return "";
		}
		SimpleDateFormat dateFormat = null;
		if (DAY_STR.equals(format)) {
			dateFormat = DAY_FORMAT;
		} else if (TIME_STR.equals(format)) {
			dateFormat = TIME_FORMAT;
		} else if (TIME__NO_S_STR.equals(format)) {
			dateFormat = TIME__NO_S_FORMAT;
		} else {
			dateFormat = new SimpleDateFormat(format);
		}
		return dateFormat.format(obj);
	}

	/**
	 * 判断是不是成功
	 * 
	 * @param first
	 * @param operator
	 * @param second
	 * @return
	 */
	public static boolean isTrue(Object first, String operator, Object second) {
		if (">".endsWith(operator)) {
			return isGt(first, second);
		} else if ("<".endsWith(operator)) {
			return isGt(second, first);
		} else if ("==".endsWith(operator)) {
			if (first != null && second != null) {
				return first.equals(second);
			}
			return first == second;
		} else if ("!=".endsWith(operator)) {
			if (first != null && second != null) {
				return !first.equals(second);
			}
			return first != second;
		} else {
			throw new ExcelExportException("占不支持改操作符");
		}
	}

	/**
	 * 前者是不是大于后者
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static boolean isGt(Object first, Object second) {
		if (first == null || first.toString() == "") {
			return false;
		}
		if (second == null || second.toString() == "") {
			return true;
		}
		double one = Double.valueOf(first.toString());
		double two = Double.valueOf(second.toString());
		return one > two;
	}

}
