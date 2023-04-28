package org.springblade.config.util.minio;

import lombok.Data;

@Data
public class ObjectItem {
	private String objectName;
	private Long size;
}
