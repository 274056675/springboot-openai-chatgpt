package org.springblade.config.autopoi.poi.excel.entity.params;

import java.util.List;

/**
 * excel 导入工具类,对cell类型做映射
 *
 */
public class ExcelImportEntity extends ExcelBaseEntity {
	/**
	 * 对应 Collection NAME
	 */
	private String collectionName;
	/**
	 * 保存图片的地址 当saveType设置为3/4时，此值可以设置为：local,minio,alioss
	 */
	private String saveUrl;
	/**
	 * 保存图片的类型,1是文件_old,2是数据库字节,3文件地址_new,4网络地址
	 */
	private int saveType;
	/**
	 * 对应exportType
	 */
	private String classType;
	/**
	 * 校驗參數
	 */
	private ExcelVerifyEntity verify;
	/**
	 * 后缀
	 */
	private String suffix;

	private List<ExcelImportEntity> list;

	public String getClassType() {
		return classType;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public List<ExcelImportEntity> getList() {
		return list;
	}

	public int getSaveType() {
		return saveType;
	}

	public String getSaveUrl() {
		return saveUrl;
	}

	public ExcelVerifyEntity getVerify() {
		return verify;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void setList(List<ExcelImportEntity> list) {
		this.list = list;
	}

	public void setSaveType(int saveType) {
		this.saveType = saveType;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public void setVerify(ExcelVerifyEntity verify) {
		this.verify = verify;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
