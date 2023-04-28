package org.springblade.config.autopoi.poi.exception.word.enmus;

/**
 * 导出异常枚举
 *
 */
public enum WordExportEnum {

	EXCEL_PARAMS_ERROR("Excel 导出 参数错误"), EXCEL_HEAD_HAVA_NULL("Excel 表头 有的字段为空"), EXCEL_NO_HEAD("Excel 没有表头");

	private String msg;

	WordExportEnum(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
