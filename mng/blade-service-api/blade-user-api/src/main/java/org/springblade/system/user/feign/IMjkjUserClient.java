
package org.springblade.system.user.feign;


import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.system.user.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * User Feign接口类
 *
 *
 */
@FeignClient(
	value = AppConstant.APPLICATION_USER_NAME
)
public interface IMjkjUserClient {

	String API_PREFIX = "/client";
	String WX_MINI_LOGIN = API_PREFIX + "/wx-mini-login";//微信小程序登录
	String PHONE_LOGIN = API_PREFIX + "/phone-login";//手机号码登录
	String PHONE_CHECK_CODE = API_PREFIX + "/phone-check-code";//校验手机验证码

	String WX_OPEN = API_PREFIX + "/wx-open";//微信开放平台

	/**
	 * 微信小程序登录
	 */
	@PostMapping(WX_MINI_LOGIN)
	R<UserInfo> wxMiniLogin(@RequestBody WxUserParam param);

	/**
	 * 手机号码登录
	 */
	@PostMapping(PHONE_LOGIN)
	R<UserInfo> phoneLogin(@RequestBody WxUserParam param);

	@PostMapping(PHONE_CHECK_CODE)
	R<Boolean> checkPhone(@RequestBody SmsCodeParam param);

	/**
	 * 微信开放平台
	 * @param param
	 * @return
	 */
	@PostMapping(WX_OPEN)
	R<UserInfo> wxOpen(@RequestBody WxOpenParam param);
}
