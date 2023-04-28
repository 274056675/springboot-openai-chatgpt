package org.springblade.config.autopoi.poi.excel.entity.params;

import java.util.Map;

/**
 * Excel 对于的 Collection
 *
 */
public class ExcelCollectionParams {

	/**
	 * 集合对应的名称
	 */
	private String name;
	/**
	 * Excel 列名称
	 */
	private String excelName;
	/**
	 * 实体对象
	 */
	private Class<?> type;
	/**
	 * 这个list下面的参数集合实体对象
	 */
	private Map<String, ExcelImportEntity> excelParams;

	public Map<String, ExcelImportEntity> getExcelParams() {
		return excelParams;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setExcelParams(Map<String, ExcelImportEntity> excelParams) {
		this.excelParams = excelParams;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
}
