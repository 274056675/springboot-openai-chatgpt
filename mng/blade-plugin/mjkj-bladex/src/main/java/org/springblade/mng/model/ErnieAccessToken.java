package org.springblade.mng.model;

import lombok.Data;

/**
 * @author JX
 * @create 2023-09-08 14:37
 * @dedescription:获取token baidu的返回类型
 * */
@Data
public class ErnieAccessToken {
	private String id;
	private String access_token; //通过API Key和Secret Key获取的access_token
	private String error;
	private String error_description;
	private Integer expires_in; //有效时间，官方默认30天


}
