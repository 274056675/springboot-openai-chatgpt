package org.springblade.config.util.minio;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.poi.util.IOUtils;
import org.springblade.core.tool.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description： minio工具类
 * @version：3.0
 */
@Component
public class MinioUtils {
	@Autowired
	private MinioClient minioClient;


	/**
	 * description: 判断bucket是否存在，不存在则创建
	 *
	 * @return: void
	 */
	public void existBucket(String name) {
		try {
			boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
			if (!exists) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建存储bucket
	 *
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public Boolean makeBucket(String bucketName) {
		try {
			minioClient.makeBucket(MakeBucketArgs.builder()
				.bucket(bucketName)
				.build());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除存储bucket
	 *
	 * @param bucketName 存储bucket名称
	 * @return Boolean
	 */
	public Boolean removeBucket(String bucketName) {
		try {
			minioClient.removeBucket(RemoveBucketArgs.builder()
				.bucket(bucketName)
				.build());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * description: 上传文件
	 *
	 * @param file
	 * @return: java.lang.String
	 */
	public MinioBladeFile upload(MultipartFile file) {
		//String contentType = file.getContentType();//处理后缀
		String contentType =MinioUtils.getContentType(file.getOriginalFilename());


		String format = DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATE);

		String fileName = "upload/" + format + "/" + file.getOriginalFilename();
		String[] split = fileName.split("\\.");
		if (split.length > 1) {
			fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
		} else {
			fileName = fileName + System.currentTimeMillis();
		}
		InputStream in = null;
		try {
			in = file.getInputStream();
			minioClient.putObject(PutObjectArgs.builder()
				.bucket(MinIoClientConfig.getBucketName())
				.object(fileName)
				.stream(in, in.available(), -1)
				.contentType(contentType)
				.build()
			);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		MinioBladeFile minioBladeFile = new MinioBladeFile();
		minioBladeFile.setLink(MinIoClientConfig.getEndpoint() + "/" + MinIoClientConfig.getBucketName() + "/" + fileName);
		minioBladeFile.setDomain(MinIoClientConfig.getEndpoint());
		minioBladeFile.setName(fileName);
		minioBladeFile.setOriginalName(file.getOriginalFilename());
		return minioBladeFile;
	}

	public MinioBladeFile uploadInputStream(String  originalFilename ,InputStream fileInputStream) {
		//String contentType = file.getContentType();//处理后缀
		String contentType =MinioUtils.getContentType(originalFilename);


		String format = DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATE);

		String fileName = "upload/" + format + "/" + originalFilename;
		String[] split = fileName.split("\\.");
		if (split.length > 1) {
			fileName = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
		} else {
			fileName = fileName + System.currentTimeMillis();
		}
		InputStream in = null;
		try {
			in = fileInputStream;
			minioClient.putObject(PutObjectArgs.builder()
				.bucket(MinIoClientConfig.getBucketName())
				.object(fileName)
				.stream(in, in.available(), -1)
				.contentType(contentType)
				.build()
			);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		MinioBladeFile minioBladeFile = new MinioBladeFile();
		minioBladeFile.setLink(MinIoClientConfig.getEndpoint() + "/" + MinIoClientConfig.getBucketName() + "/" + fileName);
		minioBladeFile.setDomain(MinIoClientConfig.getEndpoint());
		minioBladeFile.setName(fileName);
		minioBladeFile.setOriginalName(originalFilename);
		return minioBladeFile;
	}

	/**
	 * description: 下载文件
	 *
	 * @param fileName
	 * @return: org.springframework.http.ResponseEntity<byte [ ]>
	 */
	public ResponseEntity<byte[]> download(String fileName) {
		ResponseEntity<byte[]> responseEntity = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = minioClient.getObject(GetObjectArgs.builder().bucket(MinIoClientConfig.getBucketName()).object(fileName).build());
			out = new ByteArrayOutputStream();
			IOUtils.copy(in, out);
			//封装返回值
			byte[] bytes = out.toByteArray();
			HttpHeaders headers = new HttpHeaders();
			try {
				headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			headers.setContentLength(bytes.length);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setAccessControlExposeHeaders(Arrays.asList("*"));
			responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseEntity;
	}

	/**
	 * 查看文件对象
	 *
	 * @param bucketName 存储bucket名称
	 * @return 存储bucket内文件对象信息
	 */
	public List<ObjectItem> listObjects(String bucketName) {
		Iterable<Result<Item>> results = minioClient.listObjects(
			ListObjectsArgs.builder().bucket(bucketName).build());
		List<ObjectItem> objectItems = new ArrayList<>();
		try {
			for (Result<Item> result : results) {
				Item item = result.get();
				ObjectItem objectItem = new ObjectItem();
				objectItem.setObjectName(item.objectName());
				objectItem.setSize(item.size());
				objectItems.add(objectItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return objectItems;
	}

	/**
	 * 批量删除文件对象
	 *
	 * @param bucketName 存储bucket名称
	 * @param objects    对象名称集合
	 */
	public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> objects) {
		List<DeleteObject> dos = objects.stream().map(e -> new DeleteObject(e)).collect(Collectors.toList());
		Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
		return results;
	}

	private static String getContentType(String fileName) {
		String fileExt="";
		if(fileName.lastIndexOf(".")!=-1){
			fileExt =fileName.substring(fileName.lastIndexOf("."));
		}
		fileExt=fileExt.toLowerCase();
		switch (fileExt) {
			case ".doc":
				return "application/msword";
			case "docx":
				return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			case ".rtf":
				return "application/rtf";
			case ".xls":
				return "application/vnd.ms-excel";
			case ".xlsx":
				return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			case ".ppt":
				return "application/vnd.ms-powerpoint";
			case ".pptx":
				return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
			case ".pdf":
				return "application/pdf";
			case ".swf":
				return "application/x-shockwave-flash";
			case ".rar":
				return "application/octet-stream";
			case ".zip":
				return "application/x-zip-compressed";
			case ".mp3":
				return "audio/mpeg";
			case ".gif":
				return "image/gif";
			case ".png":
				return "image/png";
			case ".jpeg":
				return "image/jpeg";
			case ".jpg":
				return "image/jpeg";
			case ".jpe":
				return "image/jpeg";
			case ".txt":
				return "text/plain";
			case ".bmp":
				return "image/jpeg";
			case ".exe":
				return "application/octet-stream";

			default:
				return "application/octet-stream";
		}
	}
}

