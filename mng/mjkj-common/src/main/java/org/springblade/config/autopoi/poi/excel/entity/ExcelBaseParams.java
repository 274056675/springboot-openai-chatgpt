package org.springblade.config.autopoi.poi.excel.entity;


import org.springblade.config.autopoi.poi.handler.inter.IExcelDataHandler;

/**
 * 基础参数
 *
 */
public class ExcelBaseParams {

	/**
	 * 数据处理接口,以此为主,replace,format都在这后面
	 */
	private IExcelDataHandler dataHanlder;

	public IExcelDataHandler getDataHanlder() {
		return dataHanlder;
	}

	public void setDataHanlder(IExcelDataHandler dataHanlder) {
		this.dataHanlder = dataHanlder;
	}

}
