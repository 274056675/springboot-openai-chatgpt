
package org.springblade.common.launch;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.common.constant.LauncherConstant;
import org.springblade.core.auto.service.AutoService;
import org.springblade.core.launch.constant.NacosConstant;
import org.springblade.core.launch.service.LauncherService;
import org.springblade.core.launch.utils.PropsUtil;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Map;
import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author smallchil
 */
@Slf4j
@AutoService(LauncherService.class)
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();

		String nacosUrl=LauncherConstant.nacosAddr(profile);

		// 通用注册
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.server-addr", nacosUrl);
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.server-addr", nacosUrl);
		PropsUtil.setProperty(props, "spring.cloud.sentinel.transport.dashboard", nacosUrl);
		PropsUtil.setProperty(props, "spring.datasource.dynamic.enabled", "false");

		//配置文件的m默认组名为：DEFAULT_GROUP,如果修改了这里要修改

		String GROUP_STR="DEFAULT_GROUP";
		PropsUtil.setProperty(props,"spring.cloud.nacos.discovery.group", GROUP_STR);
		//chatgpt模块
		//nacos上命名空间为：chatgpt_open，如果修改了这里也要修改
		PropsUtil.setProperty(props, "spring.cloud.nacos.discovery.namespace", "chatgpt_open");
		PropsUtil.setProperty(props, "spring.cloud.nacos.config.namespace", "chatgpt_open");
	}


}
