
package org.springblade.system.user;

import org.springblade.core.cloud.feign.EnableBladeFeign;
import org.springblade.core.launch.BladeApplication;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 用户启动器
 *
 *
 */
@EnableBladeFeign
@SpringCloudApplication
public class UserApplication {

	public static void main(String[] args) {
		BladeApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
	}

}
