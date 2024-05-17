package org.springblade.mng.cgform.model.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class FileCreateModel implements Serializable {
	private Integer type;
	private Long id;
	private String source;
	private String target;
	private String action;
	private MultipartFile upload;
	private String upload_fullpath;

}
