
package org.springblade.auth.granter;

import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.social.props.SocialProperties;
import org.springblade.system.user.feign.IMjkjUserClient;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义拓展TokenGranter
 *
 *
 */
public class BladeTokenGranter {

	/**
	 * 自定义tokenGranter
	 */
	public static TokenGranter getTokenGranter(final AuthenticationManager authenticationManager, final AuthorizationServerEndpointsConfigurer endpoints, BladeRedis bladeRedis, IUserClient userClient, IMjkjUserClient mjkjUserClient, SocialProperties socialProperties) {
		// 默认tokenGranter集合
		List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
		// 增加验证码模式
		granters.add(new CaptchaTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), bladeRedis));
		// 微信小程序登录
		granters.add(new WxMiniTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), userClient, mjkjUserClient,socialProperties));
		// 组合tokenGranter集合
		return new CompositeTokenGranter(granters);
	}

}
