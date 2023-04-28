package org.springblade.config.autopoi.poi.excel.entity.params;


import org.apache.poi.ss.usermodel.CellStyle;

import java.io.Serializable;

/**
 * 模板便利是的参数
 */
public class ExcelTemplateParams implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	/**
	 * key
	 */
	private String name;
	/**
	 * 模板的cellStyle
	 */
	private CellStyle cellStyle;
	/**
	 * 行高
	 */
	private short height;

	public ExcelTemplateParams() {

	}

	public ExcelTemplateParams(String name, CellStyle cellStyle, short height) {
		this.name = name;
		this.cellStyle = cellStyle;
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}

}
