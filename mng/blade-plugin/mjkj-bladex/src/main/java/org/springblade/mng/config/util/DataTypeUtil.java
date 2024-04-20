package org.springblade.mng.config.util;

import com.alibaba.fastjson.JSONObject;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.cgform.entity.CgformField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 数据类型公共类
 */
public class DataTypeUtil {

	public static boolean isNumberType(String type) {
		return "int".equals(type) || "double".equals(type) || "BigDecimal".equals(type) || "Integer".equals(type) || "Double".equals(type) || "BigInt".equals(type);
	}

	public static boolean isNotNumberType(String type) {
		return !isNumberType(type);
	}

	public static boolean isDateType(String type) {
		return "Date".equalsIgnoreCase(type) || "datetime".equalsIgnoreCase(type) || "Timestamp".equalsIgnoreCase(type);
	}

	/**
	 * 拼接SQL: xxx = #{yyy}
	 * @param databaseType 数据表字段类型
	 * @param onlCgformField 数据表字段
	 * @param jsonObject 数据
	 * @param map 空HashMap
	 * @return
	 */
	public static String getSql(String databaseType, CgformField onlCgformField, JSONObject jsonObject, Map<String, Object> map) {
		// 获取数据表字段类型,转小写
		String dbType = onlCgformField.getDbType().toLowerCase();
		// 获取数据表字段名
		String dbFieldName = onlCgformField.getDbFieldName();
		// 获取数据表控件类型
		String fieldShowType = onlCgformField.getFieldShowType();
		if (jsonObject.get(dbFieldName) == null) {
			return "null";
		} else if (DbType.INT.toLowerCase().equals(dbType)) {//int
			map.put(dbFieldName, jsonObject.getIntValue(dbFieldName));
			return "#{" + dbFieldName + ",jdbcType=INTEGER}";
		} else if (DbType.BIG_INT.toLowerCase().equals(dbType)) {
			map.put(dbFieldName, jsonObject.getLongValue(dbFieldName));
			return "#{" + dbFieldName + ",jdbcType=BIGINT}";
		} else if (DbType.DOUBLE.toLowerCase().equals(dbType)) {
			map.put(dbFieldName, jsonObject.getDoubleValue(dbFieldName));
			return "#{" + dbFieldName + ",jdbcType=DOUBLE}";
		} else if (DbType.BIG_DECIMAL.toLowerCase().equals(dbType)) {
			map.put(dbFieldName, new BigDecimal(jsonObject.getString(dbFieldName)));
			return "#{" + dbFieldName + ",jdbcType=DECIMAL}";
		} else if (DbType.BLOB.toLowerCase().equals(dbType)) {
			map.put(dbFieldName, jsonObject.getString(dbFieldName) != null ? jsonObject.getString(dbFieldName).getBytes() : null);
			return "#{" + dbFieldName + ",jdbcType=BLOB}";
		} else if (DbType.DATE.toLowerCase().equals(dbType) ||
					DbType.DATE_TIME.toLowerCase().equals(dbType)||
					DbType.TIME.toLowerCase().equals(dbType)) {
			String dbFieldNameStr = jsonObject.getString(dbFieldName);
			if ("ORACLE".equals(databaseType)) {
				if ("date".equals(fieldShowType)) {
					map.put(dbFieldName, dbFieldNameStr.length() > 10 ? dbFieldNameStr.substring(0, 10) : dbFieldNameStr);
					return "to_date(#{" + dbFieldName + "},'yyyy-MM-dd')";
				} else {
					map.put(dbFieldName, dbFieldNameStr.length() == 10 ? jsonObject.getString(dbFieldName) + " 00:00:00" : dbFieldNameStr);
					return "to_date(#{" + dbFieldName + "},'yyyy-MM-dd HH24:mi:ss')";
				}
			} else if ("POSTGRESQL".equals(databaseType)) {
				if ("date".equals(fieldShowType)) {
					map.put(dbFieldName, dbFieldNameStr.length() > 10 ? dbFieldNameStr.substring(0, 10) : dbFieldNameStr);
					return "CAST(#{" + dbFieldName + "} as TIMESTAMP)";
				} else {
					map.put(dbFieldName, dbFieldNameStr.length() == 10 ? jsonObject.getString(dbFieldName) + " 00:00:00" : dbFieldNameStr);
					return "CAST(#{" + dbFieldName + "} as TIMESTAMP)";
				}
			} else {
				String time = jsonObject.getString(dbFieldName);
				time = time.replaceAll("/", "-");
				map.put(dbFieldName, time);
				return "#{" + dbFieldName + "}";
			}
		}else {
			// map.put(dbFieldName, jsonObject.getString(dbFieldName));
			String str = jsonObject.getString(dbFieldName);
			if (Func.isEmpty(str) || "".equals(str.trim())){
				map.put(dbFieldName, null);
			}else {
				map.put(dbFieldName, str.trim());
			}
			return "#{" + dbFieldName + ",jdbcType=VARCHAR}";
		}
	}

	public static String getSql(String dbFieldName, Object dbType) {
		if (dbType instanceof Integer) {
			return "#{" + dbFieldName + ",jdbcType=INTEGER}";
		} else if (dbType instanceof Long) {
			return "#{" + dbFieldName + ",jdbcType=BIGINT}";
		} else if (dbType instanceof Double) {
			return "#{" + dbFieldName + ",jdbcType=DOUBLE}";
		} else if (dbType instanceof BigDecimal) {
			return "#{" + dbFieldName + ",jdbcType=DECIMAL}";
		} else if (dbType instanceof Date) {
			return "#{" + dbFieldName + "}";
		} else {
			return "#{" + dbFieldName + ",jdbcType=VARCHAR}";
		}
	}
}
