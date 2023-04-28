package org.springblade.config.autopoi.poi.excel.html.helper;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springblade.config.autopoi.poi.util.PoiPublicUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 样式帮助类
 *
 */
public class StylerHelper {

	private static String DEFAULTS_CLASS_CSS = ".excelDefaults {background-color: white;color: black;text-decoration: none;direction: ltr;text-transform: none;text-indent: 0;letter-spacing: 0;word-spacing: 0;white-space: normal;unicode-bidi: normal;vertical-align: 0;text-shadow: none;padding: 0;margin: 0;border-collapse: collapse;white-space: pre-wrap;word-wrap: break-word;word-break: break-all;}.excelDefaults td {padding: 1px 5px;border: 1px solid silver;border-color: #000000;text-align: center;vertical-align: middle;font-size: 12pt;}.excelDefaults .colHeader {background-color: silver;font-weight: bold;border: 1px solid black;text-align: center;padding: 1px 5px;}.excelDefaults .rowHeader {background-color: silver;font-weight: bold;border: 1px solid black;text-align: right;padding: 1px 5px;}";

	private static final String DEFAULTS_CLASS = "excelDefaults";

	private static final Map<Short, String> ALIGN = PoiPublicUtil.mapFor(HorizontalAlignment.LEFT.getCode(), "left", HorizontalAlignment.CENTER.getCode(), "center", HorizontalAlignment.RIGHT.getCode(), "right", HorizontalAlignment.FILL.getCode(), "left", HorizontalAlignment.JUSTIFY.getCode(), "left", HorizontalAlignment.CENTER_SELECTION.getCode(), "center");

	private static final Map<Short, String> VERTICAL_ALIGN = PoiPublicUtil.mapFor(VerticalAlignment.BOTTOM.getCode(), "bottom", VerticalAlignment.CENTER.getCode(), "middle", VerticalAlignment.TOP.getCode(), "top");

	private Formatter out;

	private Sheet sheet;

	private HtmlHelper helper;

	private int sheetNum;

	private int cssRandom;

	public StylerHelper(Workbook wb, Formatter out, int sheetNum, int cssRandom) {
		this.out = out;
		this.sheetNum = sheetNum;
		this.cssRandom = cssRandom;
		if (wb instanceof HSSFWorkbook)
			helper = new HSSFHtmlHelper((HSSFWorkbook) wb);
		else if (wb instanceof XSSFWorkbook)
			helper = new XSSFHtmlHelper((XSSFWorkbook) wb);
		else
			throw new IllegalArgumentException("unknown workbook type: " + wb.getClass().getSimpleName());
		printInlineStyle(wb);
	}

	private void printInlineStyle(Workbook wb) {
		out.format("<style type=\"text/css\">%n");
		printStyles(wb);
		prontFonts(wb);
		out.format("</style>%n");
	}

	private void prontFonts(Workbook wb) {
		for (short i = 0, le = wb.getNumberOfFonts(); i <= le; i++) {
			Font font = wb.getFontAt(i);
			out.format(".%s .%s {%n", DEFAULTS_CLASS, "font_" + i + "_" + cssRandom);
			fontStyle(font);
			out.format("}%n");
		}

	}

	public void printStyles(Workbook wb) {
		if (DEFAULTS_CLASS_CSS == null) {
			DEFAULTS_CLASS_CSS = getDefaultsClassCss();
		}
		out.format(DEFAULTS_CLASS_CSS);
		Set<CellStyle> seen = new HashSet<CellStyle>();
		sheet = wb.getSheetAt(sheetNum);
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = rows.next();
			for (Cell cell : row) {
				CellStyle style = cell.getCellStyle();
				if (!seen.contains(style)) {
					printStyle(style);
					seen.add(style);
				}
			}
		}
	}

	private String getDefaultsClassCss() {
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		try {
			in = new BufferedReader(new InputStreamReader(StylerHelper.class.getResourceAsStream("excelStyle.css")));
			String line;
			while ((line = in.readLine()) != null) {
				formatter.format("%s%n", line);
			}
			return formatter.toString();
		} catch (IOException e) {
			throw new IllegalStateException("Reading standard css", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new IllegalStateException("Reading standard css", e);
				}
			}
			formatter.close();
		}
	}

	private void printStyle(CellStyle style) {
		out.format(".%s .%s {%n", DEFAULTS_CLASS, styleName(style));
		styleContents(style);
		out.format("}%n");
	}

	private void styleContents(CellStyle style) {
		if (style.getAlignmentEnum() != HorizontalAlignment.CENTER) {
			styleOut("text-align", style.getAlignmentEnum().getCode(), ALIGN);
			styleOut("vertical-align", style.getAlignmentEnum().getCode(), VERTICAL_ALIGN);
		}
		helper.colorStyles(style, out);
	}

	private void fontStyle(Font font) {
		if (font.getBold())
			out.format("  font-weight: bold;%n");
		if (font.getItalic())
			out.format("  font-style: italic;%n");
		out.format("  font-family: %s;%n", font.getFontName());

		int fontheight = font.getFontHeightInPoints();
		if (fontheight == 9) {
			fontheight = 10;
		}
		out.format("  font-size: %dpt;%n", fontheight);
		helper.styleColor(out, "color", getColor(font));
	}

	private Color getColor(Font font) {
		if (helper instanceof HSSFHtmlHelper) {
			return ((HSSFWorkbook) sheet.getWorkbook()).getCustomPalette().getColor(font.getColor());
		} else {
			return ((XSSFFont) font).getXSSFColor();
		}
	}

	private String styleName(CellStyle style) {
		if (style == null)
			return "";
		return String.format("style_%02x_%s", style.getIndex(), cssRandom);
	}

	private <K> void styleOut(String attr, K key, Map<K, String> mapping) {
		String value = mapping.get(key);
		if (value != null) {
			out.format("  %s: %s;%n", attr, value);
		}
	}

	private interface HtmlHelper {
		/**
		 * Outputs the appropriate CSS style for the given cell style.
		 * 
		 * @param style
		 *            The cell style.
		 * @param out
		 *            The place to write the output.
		 */
		void colorStyles(CellStyle style, Formatter out);

		void styleColor(Formatter out, String attr, Color color);
	}

	private class HSSFHtmlHelper implements HtmlHelper {
		private final HSSFWorkbook wb;
		private final HSSFPalette colors;

		private HSSFColor.HSSFColorPredefined HSSF_AUTO = HSSFColor.HSSFColorPredefined.AUTOMATIC;

		public HSSFHtmlHelper(HSSFWorkbook wb) {
			this.wb = wb;
			colors = wb.getCustomPalette();
		}

		public void colorStyles(CellStyle style, Formatter out) {
			HSSFCellStyle cs = (HSSFCellStyle) style;
			out.format("  /* fill pattern = %d */%n", cs.getFillPatternEnum().getCode());
			styleColor(out, "background-color", cs.getFillForegroundColor());
			styleColor(out, "color", colors.getColor(cs.getFont(wb).getColor()));
		}

		private void styleColor(Formatter out, String attr, short index) {
			HSSFColor color = colors.getColor(index);
			if (index == HSSF_AUTO.getIndex() || color == null) {
				out.format("  /* %s: index = %d */%n", attr, index);
			} else {
				short[] rgb = color.getTriplet();
				out.format("  %s: #%02x%02x%02x; /* index = %d */%n", attr, rgb[0], rgb[1], rgb[2], index);
			}
		}

		public void styleColor(Formatter out, String attr, Color color) {
			if (color == null) {
				return;
			}
			HSSFColor hSSFColor = (HSSFColor) color;
			short[] rgb = hSSFColor.getTriplet();
			out.format("  %s: #%02x%02x%02x; %n", attr, rgb[0], rgb[1], rgb[2]);
		}
	}

	/**
	 * Implementation of {@link HtmlHelper} for XSSF files.
	 * 
	 * @author Ken Arnold, Industrious Media LLC
	 */
	private class XSSFHtmlHelper implements HtmlHelper {

		public XSSFHtmlHelper(XSSFWorkbook wb) {
		}

		public void colorStyles(CellStyle style, Formatter out) {
			XSSFCellStyle cs = (XSSFCellStyle) style;
			styleColor(out, "background-color", cs.getFillForegroundXSSFColor());
			styleColor(out, "color", cs.getFont().getXSSFColor());
		}

		public void styleColor(Formatter out, String attr, Color color) {
			XSSFColor xSSFColor = (XSSFColor) color;
			if (color == null || xSSFColor.isAuto())
				return;

			byte[] rgb = xSSFColor.getRGB();
			if (rgb == null) {
				return;
			}
			out.format("  %s: #%02x%02x%02x;%n", attr, rgb[0], rgb[1], rgb[2]);
		}
	}

}
