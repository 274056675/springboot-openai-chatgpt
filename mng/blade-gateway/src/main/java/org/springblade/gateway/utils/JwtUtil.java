/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.gateway.props.JwtProperties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * JwtUtil
 *
 * @author Chill
 */
public class JwtUtil {

	public static String BEARER = TokenConstant.BEARER;
	public static String CRYPTO = TokenConstant.CRYPTO;
	public static Integer AUTH_LENGTH = 7;

	/**
	 * jwt配置
	 */
	@Getter
	private static JwtProperties jwtProperties;

	public static void setJwtProperties(JwtProperties properties) {
		if (JwtUtil.jwtProperties == null) {
			JwtUtil.jwtProperties = properties;
		}
	}

	/**
	 * 签名加密
	 */
	public static String getBase64Security() {
		return Base64.getEncoder().encodeToString(getJwtProperties().getSignKey().getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 获取请求传递的token串
	 *
	 * @param auth token
	 * @return String
	 */
	public static String getToken(String auth) {
		if (isBearer(auth) || isCrypto(auth)) {
			return auth.substring(AUTH_LENGTH);
		}
		return null;
	}

	/**
	 * 判断token类型为bearer
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isBearer(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(BEARER) == 0;
		}
		return false;
	}

	/**
	 * 判断token类型为crypto
	 *
	 * @param auth token
	 * @return String
	 */
	public static Boolean isCrypto(String auth) {
		if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
			String headStr = auth.substring(0, 6).toLowerCase();
			return headStr.compareTo(CRYPTO) == 0;
		}
		return false;
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken token串
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(Base64.getDecoder().decode(getBase64Security())).build()
				.parseClaimsJws(jsonWebToken).getBody();
		} catch (Exception ex) {
			return null;
		}
	}

}
