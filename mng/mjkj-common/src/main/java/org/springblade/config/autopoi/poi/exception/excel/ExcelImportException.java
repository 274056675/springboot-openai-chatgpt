package org.springblade.config.autopoi.poi.exception.excel;


import org.springblade.config.autopoi.poi.exception.excel.enums.ExcelImportEnum;

/**
 * 导入异常
 *
 */
public class ExcelImportException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ExcelImportEnum type;

	public ExcelImportException() {
		super();
	}

	public ExcelImportException(ExcelImportEnum type) {
		super(type.getMsg());
		this.type = type;
	}

	public ExcelImportException(ExcelImportEnum type, Throwable cause) {
		super(type.getMsg(), cause);
	}

	public ExcelImportException(String message) {
		super(message);
	}

	public ExcelImportException(String message, ExcelImportEnum type) {
		super(message);
		this.type = type;
	}

	public ExcelImportEnum getType() {
		return type;
	}

	public void setType(ExcelImportEnum type) {
		this.type = type;
	}

}
