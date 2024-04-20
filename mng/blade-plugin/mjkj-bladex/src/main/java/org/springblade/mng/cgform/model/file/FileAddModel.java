package org.springblade.mng.cgform.model.file;

import lombok.Data;

@Data
public class FileAddModel {
	private Long pid;//父节点
	private String title;//文件名称
	private Integer type;//1=文件夹  2=文件
	private String fileSize;//文件大小
	private String fileUrl;//文件地址
	private String fileSuffix;//文件后缀
	private Integer downloadFlag;//0=不行  1=行
}
