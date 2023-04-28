package org.springblade.plugin.message;

import org.springblade.core.cloud.feign.EnableBladeFeign;
import org.springblade.core.launch.BladeApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Message启动器
 *
 * @author weikun
 */
@EnableBladeFeign
@SpringCloudApplication
public class MessageApplication {

	public static void main(String[] args) {
		BladeApplication.run("open-message", MessageApplication.class, args);
	}

}

