package org.springblade.mng.cgform.model.file;

import lombok.Data;

@Data
public class FileLogModel {
	private Long file_id;
	private String type;
	private String file_title;
	private String remark;
	private Long operate_user_id;
	private String operate_user_name;
	private String tenant_id;

}
