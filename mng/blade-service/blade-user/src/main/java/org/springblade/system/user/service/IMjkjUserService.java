
package org.springblade.system.user.service;


import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.WxOpenParam;
import org.springblade.system.user.entity.WxUserParam;

import java.util.Map;

/**
 * 魔晶登录
 */
public interface IMjkjUserService {

	//公共新增
	Long baseSimpleIntegerSql(String tableName, Map<String, Object> dataMap);
	//公共修改
	void baseSimpleUpdaSql(String tableName, Map<String, Object> dataMap,String id);


	//微信小程序登录
	UserInfo wxMiniLogin(WxUserParam param);

	//手机号码登录
	UserInfo phoneLogin(WxUserParam param);

	//微信开放平台
	UserInfo wxOpenRegister(WxOpenParam param);

}
