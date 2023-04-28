package org.springblade.config.autopoi.poi.exception.excel.enums;

/**
 * 导出异常类型枚举
 *
 */
public enum ExcelImportEnum {

	GET_VALUE_ERROR("Excel 值获取失败"), VERIFY_ERROR("值校验失败");

	private String msg;

	ExcelImportEnum(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
