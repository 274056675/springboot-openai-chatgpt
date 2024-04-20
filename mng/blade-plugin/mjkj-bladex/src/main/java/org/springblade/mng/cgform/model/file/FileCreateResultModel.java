package org.springblade.mng.cgform.model.file;

import lombok.Data;

@Data
public class FileCreateResultModel {
	private String date;
	private Long size;
	private Long id;
	private String type;
	private String value;
	private Long fileId;
}
