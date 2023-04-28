
package org.springblade.auth.endpoint;

import com.wf.captcha.SpecCaptcha;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static org.springblade.core.cache.constant.CacheConstant.*;

/**
 * BladeEndPoint
 *
 *
 */
@NonDS
@Slf4j
@RestController
@AllArgsConstructor
public class BladeTokenEndPoint {

	private final BladeRedis bladeRedis;
	private final JwtProperties jwtProperties;

	@GetMapping("/oauth/user-info")
	public R<Authentication> currentUser(Authentication authentication) {
		return R.data(authentication);
	}

	@GetMapping("/oauth/captcha")
	public Kv captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = StringUtil.randomUUID();
		// 存入redis并设置过期时间为30分钟
		bladeRedis.setEx(CacheNames.CAPTCHA_KEY + key, verCode, Duration.ofMinutes(30));
		// 将key和base64返回给前端
		return Kv.create().set("key", key).set("image", specCaptcha.toBase64());
	}

	@GetMapping("/oauth/logout")
	public Kv logout() {
		BladeUser user = AuthUtil.getUser();
		if (user != null && jwtProperties.getState()) {
			String token = JwtUtil.getToken(WebUtil.getRequest().getHeader(TokenConstant.HEADER));
			JwtUtil.removeAccessToken(user.getTenantId(), String.valueOf(user.getUserId()), token);
		}
		return Kv.create().set("success", "true").set("msg", "success");
	}

	@GetMapping("/oauth/clear-cache")
	public Kv clearCache() {
		CacheUtil.clear(BIZ_CACHE);
		CacheUtil.clear(USER_CACHE);
		CacheUtil.clear(DICT_CACHE);
		CacheUtil.clear(FLOW_CACHE);
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(PARAM_CACHE);
		CacheUtil.clear(RESOURCE_CACHE);
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		CacheUtil.clear(PARAM_CACHE, Boolean.FALSE);
		return Kv.create().set("success", "true").set("msg", "success");
	}

}
