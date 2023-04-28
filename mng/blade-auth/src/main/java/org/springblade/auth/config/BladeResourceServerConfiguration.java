
package org.springblade.auth.config;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 自定义登录成功配置
 *
 *
 */
@Configuration
@AllArgsConstructor
@EnableResourceServer
public class BladeResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	@SneakyThrows
	public void configure(HttpSecurity http) {
		http.headers().frameOptions().disable();
		http.formLogin()
			.and()
			.authorizeRequests()
			.antMatchers(
				"/actuator/**",
				"/oauth/captcha",
				"/oauth/logout",
				"/oauth/clear-cache",
				"/oauth/render/**",
				"/oauth/callback/**",
				"/oauth/revoke/**",
				"/oauth/refresh/**",
				"/token/**",
				"/mobile/**",
				"/v2/api-docs").permitAll()
			.anyRequest().authenticated().and()
			.csrf().disable();
	}

}
