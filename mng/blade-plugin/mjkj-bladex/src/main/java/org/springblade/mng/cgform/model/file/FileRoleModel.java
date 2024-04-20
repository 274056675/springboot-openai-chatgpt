package org.springblade.mng.cgform.model.file;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FileRoleModel implements Serializable {
	private List<Long> fileIdList;
	private List<Long> userList;
	private List<Long> roleList;
	private List<Long> deptList;
}
