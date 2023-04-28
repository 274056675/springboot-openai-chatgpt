package org.springblade.config.autopoi.poi.excel.entity.params;

import java.util.ArrayList;
import java.util.List;

/**
 * excel 导出工具类,对cell类型做映射
 *
 */
public class ExcelExportEntity extends ExcelBaseEntity implements Comparable<ExcelExportEntity> {

	/**
	 * 如果是MAP导出,这个是map的key
	 */
	private Object key;

	private double width = 10;

	private double height = 10;

	/**
	 * 图片的类型,1是文件地址(class目录),2是数据库字节,3是文件地址(磁盘目录)，4网络图片
	 */
	private int exportImageType = 3;

	/**
	 * 图片储存位置(磁盘目录) 用于导出获取图片绝对路径
	 */
	private String imageBasePath;

	/**
	 * 排序顺序
	 */
	private int orderNum = 0;

	/**
	 * 是否支持换行
	 */
	private boolean isWrap;

	/**
	 * 是否需要合并
	 */
	private boolean needMerge;
	/**
	 * 单元格纵向合并
	 */
	private boolean mergeVertical;
	/**
	 * 合并依赖
	 */
	private int[] mergeRely;
	/**
	 * 后缀
	 */
	private String suffix;
	/**
	 * 统计
	 */
	private boolean isStatistics;

	/**
	 * 是否横向合并
	 */
	private boolean colspan;

	/**
	 * 被横向合并的列名称
	 */
	private List<String> subColumnList;

	/**
	 * 父表头的名称
	 */
	private String groupName;

	private List<ExcelExportEntity> list;

	public ExcelExportEntity() {

	}

	public ExcelExportEntity(String name) {
		super.name = name;
	}

	public ExcelExportEntity(String name, Object key) {
		super.name = name;
		this.key = key;
	}

	/**
	 * 构造器
	 * @param name 描述-文字
	 * @param key 存储key 如果是MAP导出,这个是map的key
	 * @param colspan 是否为合并列（a,b列公用一个表头c,则a,b,c都需要设置为true）
	 */
	public ExcelExportEntity(String name, Object key, boolean colspan) {
		super.name = name;
		this.key = key;
		this.colspan = colspan;
	}

	public ExcelExportEntity(String name, Object key, int width) {
		super.name = name;
		this.width = width;
		this.key = key;
	}

	public int getExportImageType() {
		return exportImageType;
	}

	public double getHeight() {
		return height;
	}

	public Object getKey() {
		return key;
	}

	public List<ExcelExportEntity> getList() {
		return list;
	}

	public int[] getMergeRely() {
		return mergeRely == null ? new int[0] : mergeRely;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public double getWidth() {
		return width;
	}

	public boolean isMergeVertical() {
		return mergeVertical;
	}

	public boolean isNeedMerge() {
		return needMerge;
	}

	public boolean isWrap() {
		return isWrap;
	}

	public void setExportImageType(int exportImageType) {
		this.exportImageType = exportImageType;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public void setList(List<ExcelExportEntity> list) {
		this.list = list;
	}

	public void setMergeRely(int[] mergeRely) {
		this.mergeRely = mergeRely;
	}

	public void setMergeVertical(boolean mergeVertical) {
		this.mergeVertical = mergeVertical;
	}

	public void setNeedMerge(boolean needMerge) {
		this.needMerge = needMerge;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setWrap(boolean isWrap) {
		this.isWrap = isWrap;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public boolean isStatistics() {
		return isStatistics;
	}

	public void setStatistics(boolean isStatistics) {
		this.isStatistics = isStatistics;
	}

	public String getImageBasePath() {
		return imageBasePath;
	}

	public void setImageBasePath(String imageBasePath) {
		this.imageBasePath = imageBasePath;
	}

	public boolean isColspan() {
		return colspan;
	}

	public void setColspan(boolean colspan) {
		this.colspan = colspan;
	}

	public List<String> getSubColumnList() {
		return subColumnList;
	}

	public void setSubColumnList(List<String> subColumnList) {
		this.subColumnList = subColumnList;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 是否为合并子列
	 * @return
	 */
	public boolean isSubColumn(){
		return this.colspan && (this.subColumnList==null || this.subColumnList.size()==0);
	}

	/**
	 * 是否为合并父列
	 * @return
	 */
	public boolean isMergeColumn(){
		return this.colspan && this.subColumnList!=null && this.subColumnList.size()>0;
	}


	/**
	 * 获取被合并的子列
	 * @param all
	 * @return
	 */
	public List<ExcelExportEntity> initSubExportEntity(List<ExcelExportEntity> all){
		List<ExcelExportEntity> sub = new ArrayList<ExcelExportEntity>();
		for (ExcelExportEntity temp : all) {
			if(this.subColumnList.contains(temp.getKey())){
				sub.add(temp);
			}
		}
		this.setList(sub);
		return sub;
	}

	@Override
	public int compareTo(ExcelExportEntity prev) {
		return this.getOrderNum() - prev.getOrderNum();
	}

}
