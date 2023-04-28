package org.springblade.config.autopoi.poi.excel.html;


import org.apache.poi.ss.usermodel.*;
import org.springblade.config.autopoi.poi.excel.html.helper.CellValueHelper;
import org.springblade.config.autopoi.poi.excel.html.helper.MergedRegionHelper;
import org.springblade.config.autopoi.poi.excel.html.helper.StylerHelper;

import java.util.Formatter;
import java.util.Iterator;

/**
 * Excel转换成Html 服务
 *
 */
public class ExcelToHtmlServer {

	private Workbook wb;
	private int sheetNum;
	private int cssRandom;

	/* 是不是完成界面 */
	private boolean completeHTML;
	private Formatter out;
	/* 已经完成范围处理 */
	private boolean gotBounds;
	private int firstColumn;
	private int endColumn;
	private static final String COL_HEAD_CLASS = "colHeader";
	// private static final String ROW_HEAD_CLASS = "rowHeader";

	private static final String DEFAULTS_CLASS = "excelDefaults";

	public ExcelToHtmlServer(Workbook wb, boolean completeHTML, int sheetNum) {
		this.wb = wb;
		this.completeHTML = completeHTML;
		this.sheetNum = sheetNum;
		cssRandom = (int) Math.ceil(Math.random() * 1000);
	}

	public String printPage() {
		try {
			ensureOut();
			if (completeHTML) {
				out.format("<!DOCTYPE HTML>%n");
				out.format("<html>%n");
				out.format("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">%n");
				out.format("<head>%n");
			}
			new StylerHelper(wb, out, sheetNum, cssRandom);
			if (completeHTML) {
				out.format("</head>%n");
				out.format("<body>%n");
			}
			print();
			if (completeHTML) {
				out.format("</body>%n");
				out.format("</html>%n");
			}
			return out.toString();
		} finally {
			if (out != null)
				out.close();
		}
	}

	private void print() {
		printSheets();
	}

	private void ensureOut() {
		if (out == null)
			out = new Formatter(new StringBuilder());
	}

	private void printSheets() {
		Sheet sheet = wb.getSheetAt(sheetNum);
		printSheet(sheet);
	}

	private void printSheet(Sheet sheet) {
		out.format("<table class='%s' width='%s'>%n", DEFAULTS_CLASS, getTableWidth(sheet));
		printCols(sheet);
		printSheetContent(sheet);
		out.format("</table>%n");
	}

	private void printCols(Sheet sheet) {
		// out.format("<col/>%n");
		ensureColumnBounds(sheet);
		for (int i = firstColumn; i < endColumn; i++) {
			out.format("<col style='width:%spx;' />%n", sheet.getColumnWidth(i) / 32);
		}
	}

	private int getTableWidth(Sheet sheet) {
		ensureColumnBounds(sheet);
		int width = 0;
		for (int i = firstColumn; i < endColumn; i++) {
			width = width + (sheet.getColumnWidth(i) / 32);
		}
		return width;
	}

	private void ensureColumnBounds(Sheet sheet) {
		if (gotBounds)
			return;

		Iterator<Row> iter = sheet.rowIterator();
		firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
		endColumn = 0;
		while (iter.hasNext()) {
			Row row = iter.next();
			short firstCell = row.getFirstCellNum();
			if (firstCell >= 0) {
				firstColumn = Math.min(firstColumn, firstCell);
				endColumn = Math.max(endColumn, row.getLastCellNum());
			}
		}
		gotBounds = true;
	}

	@SuppressWarnings("unused")
	/**本来是用来生成 A，B 那个列名称的**/
	private void printColumnHeads(Sheet sheet) {
		out.format("<thead>%n");
		out.format("  <tr class=%s>%n", COL_HEAD_CLASS);
		out.format("    <th class=%s>&#x25CA;</th>%n", COL_HEAD_CLASS);
		StringBuilder colName = new StringBuilder();
		for (int i = firstColumn; i < endColumn; i++) {
			colName.setLength(0);
			int cnum = i;
			do {
				colName.insert(0, (char) ('A' + cnum % 26));
				cnum /= 26;
			} while (cnum > 0);
			out.format("    <th class=%s>%s</th>%n", COL_HEAD_CLASS, colName);
		}
		out.format("  </tr>%n");
		out.format("</thead>%n");
	}

	private void printSheetContent(Sheet sheet) {
		// printColumnHeads(sheet);
		MergedRegionHelper mergedRegionHelper = new MergedRegionHelper(sheet);
		CellValueHelper cellValueHelper = new CellValueHelper(wb, cssRandom);
		out.format("<tbody>%n");
		Iterator<Row> rows = sheet.rowIterator();
		int rowIndex = 1;
		while (rows.hasNext()) {
			Row row = rows.next();
			out.format("  <tr style='height:%spx;'>%n", row.getHeight() / 15);
			// out.format("    <td class='%s'>%d</td>%n", ROW_HEAD_CLASS,
			// row.getRowNum() + 1);
			for (int i = firstColumn; i < endColumn; i++) {
				if (mergedRegionHelper.isNeedCreate(rowIndex, i)) {
					String content = "&nbsp;";
					CellStyle style = null;
					if (i >= row.getFirstCellNum() && i < row.getLastCellNum()) {
						Cell cell = row.getCell(i);
						if (cell != null) {
							style = cell.getCellStyle();
							content = cellValueHelper.getHtmlValue(cell);
						}
					}
					if (mergedRegionHelper.isMergedRegion(rowIndex, i)) {
						Integer[] rowAndColSpan = mergedRegionHelper.getRowAndColSpan(rowIndex, i);
						out.format("    <td rowspan='%s' colspan='%s' class='%s' >%s</td>%n", rowAndColSpan[0], rowAndColSpan[1], styleName(style), content);
					} else {
						out.format("    <td class='%s'>%s</td>%n", styleName(style), content);
					}
				}

			}
			out.format("  </tr>%n");
			rowIndex++;
		}
		out.format("</tbody>%n");
	}

	private String styleName(CellStyle style) {
		if (style == null)
			return "";
		return String.format("style_%02x_%s font_%s_%s", style.getIndex(), cssRandom, style.getFontIndex(), cssRandom);
	}
}
