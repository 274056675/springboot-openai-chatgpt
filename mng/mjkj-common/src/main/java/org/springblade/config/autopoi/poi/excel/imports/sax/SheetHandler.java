package org.springblade.config.autopoi.poi.excel.imports.sax;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springblade.config.autopoi.poi.excel.entity.enmus.CellValueType;
import org.springblade.config.autopoi.poi.excel.entity.sax.SaxReadCellEntity;
import org.springblade.config.autopoi.poi.excel.imports.sax.parse.ISaxRowRead;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 回调接口
 */
public class SheetHandler extends DefaultHandler {

	private SharedStringsTable sst;
	private String lastContents;

	// 当前行
	private int curRow = 0;
	// 当前列
	private int curCol = 0;

	private CellValueType type;

	private ISaxRowRead read;

	// 存储行记录的容器
	private List<SaxReadCellEntity> rowlist = Lists.newArrayList();

	public SheetHandler(SharedStringsTable sst, ISaxRowRead rowRead) {
		this.sst = sst;
		this.read = rowRead;
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		// 置空
		lastContents = "";
		// c => 单元格
		if ("c".equals(name)) {
			// 如果下一个元素是 SST 的索引，则将nextIsString标记为true
			String cellType = attributes.getValue("t");
			if ("s".equals(cellType)) {
				type = CellValueType.String;
				return;
			}
			// 日期格式
			cellType = attributes.getValue("s");
			if ("1".equals(cellType)) {
				type = CellValueType.Date;
			} else if ("2".equals(cellType)) {
				type = CellValueType.Number;
			}
		} else if ("t".equals(name)) {// 当元素为t时
			type = CellValueType.TElement;
		}

	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {

		// 根据SST的索引值的到单元格的真正要存储的字符串
		// 这时characters()方法可能会被调用多次
		if (CellValueType.String.equals(type)) {
			try {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
			} catch (Exception e) {

			}
		}
		// t元素也包含字符串
		if (CellValueType.TElement.equals(type)) {
			String value = lastContents.trim();
			rowlist.add(curCol, new SaxReadCellEntity(CellValueType.String, value));
			curCol++;
			type = CellValueType.None;
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
		} else if ("v".equals(name)) {
			String value = lastContents.trim();
			value = value.equals("") ? " " : value;
			if (CellValueType.Date.equals(type)) {
				Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
				rowlist.add(curCol, new SaxReadCellEntity(CellValueType.Date, date));
			} else if (CellValueType.Number.equals(type)) {
				BigDecimal bd = new BigDecimal(value);
				rowlist.add(curCol, new SaxReadCellEntity(CellValueType.Number, bd));
			} else if (CellValueType.String.equals(type)) {
				rowlist.add(curCol, new SaxReadCellEntity(CellValueType.String, value));
			}
			curCol++;
		} else if (name.equals("row")) {// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
			read.parse(curRow, rowlist);
			rowlist.clear();
			curRow++;
			curCol = 0;
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// 得到单元格内容的值
		lastContents += new String(ch, start, length);
	}

}
