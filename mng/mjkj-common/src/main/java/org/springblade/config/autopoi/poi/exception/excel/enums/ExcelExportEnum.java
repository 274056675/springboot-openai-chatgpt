package org.springblade.config.autopoi.poi.exception.excel.enums;

/**
 * 导出异常类型枚举
 */
public enum ExcelExportEnum {

	PARAMETER_ERROR("Excel 导出   参数错误"), EXPORT_ERROR("Excel导出错误");

	private String msg;

	ExcelExportEnum(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
