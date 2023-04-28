package org.springblade.config.util.minio;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio")
public class MinIoClientConfig {

	private static String endpoint;
	private static String accessKey;
	private static String secretKey;
	private static String bucketName;


	public static String getEndpoint() {
		return endpoint;
	}

	public  void setEndpoint(String endpoint) {
		MinIoClientConfig.endpoint = endpoint;
	}

	public static String getAccessKey() {
		return accessKey;
	}

	public  void setAccessKey(String accessKey) {
		MinIoClientConfig.accessKey = accessKey;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public  void setSecretKey(String secretKey) {
		MinIoClientConfig.secretKey = secretKey;
	}

	public static String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		MinIoClientConfig.bucketName = bucketName;
	}

	/**
	 * 注入minio 客户端
	 * @return
	 */
	@Bean
	public MinioClient minioClient(){

		return MinioClient.builder()
			.endpoint(endpoint)
			.credentials(accessKey, secretKey)
			.build();
	}

}
