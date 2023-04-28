
package org.springblade;

import org.springblade.core.cloud.feign.EnableBladeFeign;
import org.springblade.core.launch.BladeApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动器
 *
 *
 */
@EnableAsync
@EnableBladeFeign
@SpringCloudApplication
public class MjkjApplication {

	public static void main(String[] args) {
		BladeApplication.run("open-chat", MjkjApplication.class, args);
	}

}

