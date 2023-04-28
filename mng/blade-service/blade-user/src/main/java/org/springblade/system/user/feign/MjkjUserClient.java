
package org.springblade.system.user.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.user.entity.SmsCodeParam;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.WxOpenParam;
import org.springblade.system.user.entity.WxUserParam;
import org.springblade.system.user.service.IMjkjUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务Feign实现类
 *
 *
 */
@NonDS
@RestController
@AllArgsConstructor
public class MjkjUserClient implements IMjkjUserClient {

	private IMjkjUserService mjkjLoginService;

	private BladeRedis bladeRedis;


	/**
	 * 微信小程序登录
	 */
	@Override
	public R<UserInfo> wxMiniLogin(WxUserParam param){
		UserInfo userInfo = mjkjLoginService.wxMiniLogin(param);
		return R.data(userInfo);
	}

	/**
	 * 手机号码登录
	 */
	@Override
	public R<UserInfo> phoneLogin(WxUserParam param){
		UserInfo userInfo = mjkjLoginService.phoneLogin(param);
		return R.data(userInfo);
	}

	@Override
	public R<Boolean> checkPhone(@RequestBody SmsCodeParam param){
		String phone = param.getPhone();
		String code = param.getCode();

		String redisKey="SMS_PHONE:"+phone;
		if(!bladeRedis.exists(redisKey)){
			return R.data(false);
		}
		String redisCode = bladeRedis.get(redisKey);
		if(Func.equals(code,redisCode)){
			return R.data(true);
		}
		return R.data(false);
	}

	/**
	 * 微信开放平台
	 * @param param
	 * @return
	 */
	@Override
	public R<UserInfo> wxOpen(WxOpenParam param){
		UserInfo userInfo = mjkjLoginService.wxOpenRegister(param);
		return R.data(userInfo);
	}
}
