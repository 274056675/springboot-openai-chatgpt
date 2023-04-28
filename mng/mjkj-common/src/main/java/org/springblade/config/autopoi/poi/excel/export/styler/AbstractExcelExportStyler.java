package org.springblade.config.autopoi.poi.excel.export.styler;


import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelExportEntity;

/**
 * 抽象接口提供两个公共方法
 *
 */
public abstract class AbstractExcelExportStyler implements IExcelExportStyler {
	// 单行
	protected CellStyle stringNoneStyle;
	protected CellStyle stringNoneWrapStyle;
	// 间隔行
	protected CellStyle stringSeptailStyle;
	protected CellStyle stringSeptailWrapStyle;

	protected Workbook workbook;

	protected static final short STRING_FORMAT = (short) BuiltinFormats.getBuiltinFormat("TEXT");

	protected void createStyles(Workbook workbook) {
		this.stringNoneStyle = stringNoneStyle(workbook, false);
		this.stringNoneWrapStyle = stringNoneStyle(workbook, true);
		this.stringSeptailStyle = stringSeptailStyle(workbook, false);
		this.stringSeptailWrapStyle = stringSeptailStyle(workbook, true);
		this.workbook = workbook;
	}

	@Override
	public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
		if (noneStyler && (entity == null || entity.isWrap())) {
			return stringNoneWrapStyle;
		}
		if (noneStyler) {
			return stringNoneStyle;
		}
		if (noneStyler == false && (entity == null || entity.isWrap())) {
			return stringSeptailWrapStyle;
		}
		return stringSeptailStyle;
	}

	public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
		return null;
	}

	public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
		return null;
	}

}
