package org.springblade.config.util.minio;

import lombok.Data;

@Data
public class MinioBladeFile {
	private String link;
	private String domain;
	private String name;
	private String originalName;
	private Long attachId;
}
