package org.springblade.config.autopoi.poi.excel.entity.result;


import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * 导入返回类
 *
 */
public class ExcelImportResult<T> {

	/**
	 * 结果集
	 */
	private List<T> list;

	/**
	 * 是否存在校验失败
	 */
	private boolean verfiyFail;

	/**
	 * 数据源
	 */
	private Workbook workbook;

	public ExcelImportResult() {

	}

	public ExcelImportResult(List<T> list, boolean verfiyFail, Workbook workbook) {
		this.list = list;
		this.verfiyFail = verfiyFail;
		this.workbook = workbook;
	}

	public List<T> getList() {
		return list;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public boolean isVerfiyFail() {
		return verfiyFail;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setVerfiyFail(boolean verfiyFail) {
		this.verfiyFail = verfiyFail;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

}
