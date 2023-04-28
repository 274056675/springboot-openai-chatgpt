
package org.springblade.system;

import org.springblade.core.cloud.feign.EnableBladeFeign;
import org.springblade.core.launch.BladeApplication;
import org.springblade.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 系统模块启动器
 *
 */
@EnableBladeFeign
@SpringCloudApplication
public class SystemApplication {

	public static void main(String[] args) {
		BladeApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
	}

}

