package org.springblade.config.autopoi.poi.excel.entity.params;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Excel 导入导出基础对象类
 *
 */
public class ExcelBaseEntity {
	/**
	 * 对应name
	 */
	protected String name;
	/**
	 * 对应type
	 */
	private int type = 1;
	/**
	 * 数据库格式
	 */
	private String databaseFormat;
	/**
	 * 导出日期格式
	 */
	private String format;

	/**
	 * 数字格式化,参数是Pattern,使用的对象是DecimalFormat
	 */
	private String numFormat;
	/**
	 * 替换值表达式 ："男_1","女_0"
	 */
	private String[] replace;
	/**
	 * 替换是否是替换多个值
	 */
	private boolean multiReplace;

	/**
	 * 表头组名称
	 */
	private String groupName;
	
	/**
	 * set/get方法
	 */
	private Method method;

	private List<Method> methods;

	public String getDatabaseFormat() {
		return databaseFormat;
	}

	public String getFormat() {
		return format;
	}

	public Method getMethod() {
		return method;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public String getName() {
		return name;
	}

	public String[] getReplace() {
		return replace;
	}

	public int getType() {
		return type;
	}

	public void setDatabaseFormat(String databaseFormat) {
		this.databaseFormat = databaseFormat;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReplace(String[] replace) {
		this.replace = replace;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isMultiReplace() {
		return multiReplace;
	}
	public void setMultiReplace(boolean multiReplace) {
		this.multiReplace = multiReplace;
	}

	public String getNumFormat() {
		return numFormat;
	}

	public void setNumFormat(String numFormat) {
		this.numFormat = numFormat;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
