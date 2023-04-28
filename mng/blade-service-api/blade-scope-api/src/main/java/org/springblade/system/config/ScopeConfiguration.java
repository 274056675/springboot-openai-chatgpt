
package org.springblade.system.config;


import lombok.AllArgsConstructor;
import org.springblade.core.datascope.handler.ScopeModelHandler;
import org.springblade.core.secure.config.RegistryConfiguration;
import org.springblade.core.secure.handler.IPermissionHandler;
import org.springblade.system.handler.ApiScopePermissionHandler;
import org.springblade.system.handler.DataScopeModelHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 公共封装包配置类
 *
 *
 */
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(RegistryConfiguration.class)
public class ScopeConfiguration {

	@Bean
	public ScopeModelHandler scopeModelHandler() {
		return new DataScopeModelHandler();
	}

	@Bean
	public IPermissionHandler permissionHandler() {
		return new ApiScopePermissionHandler();
	}

}
