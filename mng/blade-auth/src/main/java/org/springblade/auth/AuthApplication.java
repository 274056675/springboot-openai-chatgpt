
package org.springblade.auth;


import org.springblade.core.cloud.feign.EnableBladeFeign;
import org.springblade.core.launch.BladeApplication;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 用户认证服务器
 *
 *
 */
@EnableBladeFeign
@SpringCloudApplication
public class AuthApplication {

	public static void main(String[] args) {
		BladeApplication.run(AppConstant.APPLICATION_AUTH_NAME, AuthApplication.class, args);
	}

}
