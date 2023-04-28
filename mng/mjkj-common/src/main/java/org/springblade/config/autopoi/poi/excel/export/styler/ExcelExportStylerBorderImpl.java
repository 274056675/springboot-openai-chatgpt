package org.springblade.config.autopoi.poi.excel.export.styler;


import org.apache.poi.ss.usermodel.*;

/**
 * 带有边框的Excel样式
 *
 */
public class ExcelExportStylerBorderImpl extends AbstractExcelExportStyler implements IExcelExportStyler {

	public ExcelExportStylerBorderImpl(Workbook workbook) {
		super.createStyles(workbook);
	}

	@Override
	public CellStyle getHeaderStyle(short color) {
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		titleStyle.setFont(font);
		titleStyle.setBorderLeft(BorderStyle.THIN); // 左边框
		titleStyle.setBorderRight(BorderStyle.THIN); // 右边框
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		return titleStyle;
	}

	@Override
	public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
		CellStyle style = workbook.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN); // 左边框
		style.setBorderRight(BorderStyle.THIN); // 右边框
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setDataFormat(STRING_FORMAT);
		if (isWarp) {
			style.setWrapText(true);
		}
		return style;
	}

	@Override
	public CellStyle getTitleStyle(short color) {
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderLeft(BorderStyle.THIN); // 左边框
		titleStyle.setBorderRight(BorderStyle.THIN); // 右边框
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setWrapText(true);
		return titleStyle;
	}

	@Override
	public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
		return isWarp ? stringNoneWrapStyle : stringNoneStyle;
	}

}
