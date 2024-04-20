package org.springblade.mng.cgform.model.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件返回
 */
@Data
public class FileResultListModel implements INode<FileResultListModel> {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<FileResultListModel> children;

	@Override
	public List<FileResultListModel> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		this.data=this.children;
		return this.children;
	}
	//------------------------------------
	private List<FileResultListModel> data;//子集
	private Long date;//时间戳
	private Integer downCount;//下载次数
	private Integer isFolder;///是文件夹才有这个字段
	private boolean open;//是否可以打开
	private Integer readCount;//阅读次数
	private Long size;//文件大小
	private String type; //文件类型  folder：文件夹    excel：表格   image:图片 video：视频  pdf:pdf文件
	private String value;// 文件名
	private String createName;//创建人



	public void setChildren(List<FileResultListModel> children) {
		this.children = children;
		this.data=children;
	}
}
