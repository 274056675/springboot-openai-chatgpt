
package org.springblade.system.user.mapper;

import org.springblade.system.user.entity.User;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.poi.ss.formula.functions.T;
/**
 * 魔晶登录
 */
public interface MjkjLoginMapper{

	Map<String,Object> getAdministrator(String tenantId);

	Long getTopDeptd(String tenantId);
	Long getTopRoleId(String tenantId);
	Long getTopPostId(String tenantId);

	/**
	 * 公共新增接口
	 *
	 * @param map 一定包含 select_sql 字段
	 * @return
	 */
	Long baseIntegerSql(Map<String, Object> map);

	//公共修改接口
	void baseUpdateSql(Map<String, Object> map);

	/**
	 * 获取角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleAliases(Long[] ids);

	/**
	 * 根据openId 获取用户id
	 * @param openId
	 * @return
	 */
	Long getBladeUserIdByOpenId(String openId);

	//根据手机号码登录
	Long getBladeUserIdByPhone(String phone);

	//获取微信用户id
	String getWxUserId(Long bladeUserId);

	//获取微信用户id
	String getWxUserIdByUnioId(String unionId);
	//获取用户di
	Long getBladeUserIdByUnioId(String unionId);

}
