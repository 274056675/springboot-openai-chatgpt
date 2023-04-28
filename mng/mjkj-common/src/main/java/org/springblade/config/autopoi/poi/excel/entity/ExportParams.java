package org.springblade.config.autopoi.poi.excel.entity;


import org.apache.poi.ss.usermodel.IndexedColors;
import org.springblade.config.autopoi.poi.excel.entity.enmus.ExcelType;
import org.springblade.config.autopoi.poi.excel.export.styler.ExcelExportStylerDefaultImpl;

/**
 * Excel 导出参数
 *
 */
public class ExportParams extends ExcelBaseParams {

	/**
	 * 表格名称
	 */
	private String title;

	/**
	 * 表格名称
	 */
	private short titleHeight = 10;

	/**
	 * 第二行名称
	 */
	private String secondTitle;

	/**
	 * 表格名称
	 */
	private short secondTitleHeight = 8;
	/**
	 * sheetName
	 */
	private String sheetName;
	/**
	 * 过滤的属性
	 */
	private String[] exclusions;
	/**
	 * 是否添加需要需要
	 */
	private boolean addIndex;
	/**
	 * 是否添加需要需要
	 */
	private String indexName = "序号";
	/**
	 * 冰冻列
	 */
	private int freezeCol;
	/**
	 * 表头颜色
	 */
	private short color = IndexedColors.WHITE.index;
	/**
	 * 属性说明行的颜色 例如:HSSFColor.SKY_BLUE.index 默认
	 */
	private short headerColor = IndexedColors.SKY_BLUE.index;
	/**
	 * Excel 导出版本
	 */
	private ExcelType type = ExcelType.HSSF;
	/**
	 * Excel 导出style
	 */
	private Class<?> style = ExcelExportStylerDefaultImpl.class;
	/**
	 * 是否创建表头
	 */
	private boolean isCreateHeadRows = true;

	/**
	 * 本地文件存储基础路径
	 */
	private String imageBasePath;

	public ExportParams() {

	}

	public ExportParams(String title, String sheetName) {
		this.title = title;
		this.sheetName = sheetName;
	}

	public ExportParams(String title, String sheetName, ExcelType type) {
		this.title = title;
		this.sheetName = sheetName;
		this.type = type;
	}

	public ExportParams(String title, String secondTitle, String sheetName) {
		this.title = title;
		this.secondTitle = secondTitle;
		this.sheetName = sheetName;
	}

	public short getColor() {
		return color;
	}

	public String[] getExclusions() {
		return exclusions;
	}

	public short getHeaderColor() {
		return headerColor;
	}

	public String getSecondTitle() {
		return secondTitle;
	}

	public short getSecondTitleHeight() {
		return (short) (secondTitleHeight * 50);
	}

	public String getSheetName() {
		return sheetName;
	}

	public String getTitle() {
		return title;
	}

	public short getTitleHeight() {
		return (short) (titleHeight * 50);
	}

	public boolean isAddIndex() {
		return addIndex;
	}

	public void setAddIndex(boolean addIndex) {
		this.addIndex = addIndex;
	}

	public void setColor(short color) {
		this.color = color;
	}

	public void setExclusions(String[] exclusions) {
		this.exclusions = exclusions;
	}

	public void setHeaderColor(short headerColor) {
		this.headerColor = headerColor;
	}

	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}

	public void setSecondTitleHeight(short secondTitleHeight) {
		this.secondTitleHeight = secondTitleHeight;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleHeight(short titleHeight) {
		this.titleHeight = titleHeight;
	}

	public ExcelType getType() {
		return type;
	}

	public void setType(ExcelType type) {
		this.type = type;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Class<?> getStyle() {
		return style;
	}

	public void setStyle(Class<?> style) {
		this.style = style;
	}

	public int getFreezeCol() {
		return freezeCol;
	}

	public void setFreezeCol(int freezeCol) {
		this.freezeCol = freezeCol;
	}

	public boolean isCreateHeadRows() {
		return isCreateHeadRows;
	}

	public void setCreateHeadRows(boolean isCreateHeadRows) {
		this.isCreateHeadRows = isCreateHeadRows;
	}

	public String getImageBasePath() {
		return imageBasePath;
	}

	public void setImageBasePath(String imageBasePath) {
		this.imageBasePath = imageBasePath;
	}

}
