
package org.springblade.auth.config;

import org.springblade.auth.support.BladeJwtTokenEnhancer;
import org.springblade.core.jwt.props.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * JwtTokenStore
 *
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "blade.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenStoreConfiguration {

	/**
	 * 使用jwtTokenStore存储token
	 */
	@Bean
	public TokenStore jwtTokenStore(JwtProperties jwtProperties) {
		return new JwtTokenStore(jwtAccessTokenConverter(jwtProperties));
	}

	/**
	 * 用于生成jwt
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(JwtProperties jwtProperties) {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey(jwtProperties.getSignKey());
		return accessTokenConverter;
	}

	/**
	 * 用于扩展jwt
	 */
	@Bean
	@ConditionalOnMissingBean(name = "jwtTokenEnhancer")
	public TokenEnhancer jwtTokenEnhancer(JwtAccessTokenConverter jwtAccessTokenConverter, JwtProperties jwtProperties) {
		return new BladeJwtTokenEnhancer(jwtAccessTokenConverter, jwtProperties);
	}

}
