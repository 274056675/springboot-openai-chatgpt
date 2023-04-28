
package org.springblade.auth.support;

import lombok.AllArgsConstructor;
import org.springblade.auth.service.BladeUserDetails;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.tool.utils.Func;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 *
 *
 */
@AllArgsConstructor
public class BladeJwtTokenEnhancer implements TokenEnhancer {

	private final JwtAccessTokenConverter jwtAccessTokenConverter;
	private final JwtProperties jwtProperties;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		BladeUserDetails principal = (BladeUserDetails) authentication.getUserAuthentication().getPrincipal();

		//token参数增强
		Map<String, Object> info = new HashMap<>(16);
		info.put(TokenUtil.CLIENT_ID, TokenUtil.getClientIdFromHeader());
		info.put(TokenUtil.USER_ID, Func.toStr(principal.getUserId()));
		info.put(TokenUtil.DEPT_ID, Func.toStr(principal.getDeptId()));
		info.put(TokenUtil.POST_ID, Func.toStr(principal.getPostId()));
		info.put(TokenUtil.ROLE_ID, Func.toStr(principal.getRoleId()));
		info.put(TokenUtil.TENANT_ID, principal.getTenantId());
		info.put(TokenUtil.OAUTH_ID, principal.getOauthId());
		info.put(TokenUtil.ACCOUNT, principal.getAccount());
		info.put(TokenUtil.USER_NAME, principal.getUsername());
		info.put(TokenUtil.NICK_NAME, principal.getName());
		info.put(TokenUtil.REAL_NAME, principal.getRealName());
		info.put(TokenUtil.ROLE_NAME, principal.getRoleName());
		info.put(TokenUtil.AVATAR, principal.getAvatar());
		info.put(TokenUtil.DETAIL, principal.getDetail());
		info.put(TokenUtil.LICENSE, TokenUtil.LICENSE_NAME);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		//token状态设置
		if (jwtProperties.getState()) {
			OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
			String accessTokenValue = oAuth2AccessToken.getValue();
			String tenantId = principal.getTenantId();
			String userId = Func.toStr(principal.getUserId());
			JwtUtil.addAccessToken(tenantId, userId, accessTokenValue, accessToken.getExpiresIn());

			if (jwtProperties.getSingle()) {
				OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
				String refreshTokenValue = oAuth2RefreshToken.getValue();
				JwtUtil.addRefreshToken(tenantId, userId, refreshTokenValue, accessToken.getExpiresIn() * 168);
			}
		}

		return accessToken;
	}
}
