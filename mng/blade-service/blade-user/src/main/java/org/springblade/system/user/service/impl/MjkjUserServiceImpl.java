
package org.springblade.system.user.service.impl;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.annotation.TenantIgnore;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.entity.QuestionNumParam;
import org.springblade.feign.IMjkjBladexClient;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.WxOpenParam;
import org.springblade.system.user.entity.WxUserParam;
import org.springblade.system.user.mapper.MjkjLoginMapper;
import org.springblade.system.user.miniapp.WxMaConfiguration;
import org.springblade.system.user.miniapp.WxMaProperties;
import org.springblade.system.user.service.IMjkjUserService;
import org.springblade.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 服务实现类
 *
 *
 */
@Slf4j
@Service
public class MjkjUserServiceImpl implements IMjkjUserService {

	@Autowired
	private MjkjLoginMapper loginMapper;

	@Lazy
	@Autowired
	private IUserService userService;

	@Autowired
	private WxMaProperties wxMaProperties;

	@Autowired
	private IMjkjBladexClient bladexClient;

	@Autowired
	private BladeRedis bladeRedis;

	public static String getSql(String dbFieldName, Object dbType) {
		if (dbType instanceof Integer) {
			return "#{" + dbFieldName + ",jdbcType=INTEGER}";
		} else if (dbType instanceof Long) {
			return "#{" + dbFieldName + ",jdbcType=BIGINT}";
		} else if (dbType instanceof Double) {
			return "#{" + dbFieldName + ",jdbcType=DOUBLE}";
		} else if (dbType instanceof BigDecimal) {
			return "#{" + dbFieldName + ",jdbcType=DECIMAL}";
		} else if (dbType instanceof Date) {
			return "#{" + dbFieldName + "}";
		} else {
			return "#{" + dbFieldName + ",jdbcType=VARCHAR}";
		}
	}

	/**
	 * 公共新增
	 *
	 * @param tableName
	 * @param map
	 */
	public static Map<String, Object> insertSimpleData(String tableName, Map<String, Object> map) {
		if (Func.isEmpty(map)) {
			map = new LinkedHashMap<>();
		}
		Object id = map.get("id");
		if (Func.isEmpty(id)) {
			map.put("id", IdWorker.getId());
		}
		String feildSql = "";
		String dataSql = "";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			feildSql += field + ",";
			dataSql += getSql(field, value) + ",";
		}
		if (feildSql.endsWith(",")) {
			feildSql = feildSql.substring(0, feildSql.length() - 1);
			dataSql = dataSql.substring(0, dataSql.length() - 1);
		}
		String sql = "insert into " + tableName + "(" + feildSql + ") values (" + dataSql + ")";

		map.put("select_sql", sql);
		return map;
	}

	/**
	 * 公共修改
	 *
	 * @param tableName
	 * @param map
	 */
	public static Map<String, Object> updateSimpleData(String tableName, Map<String, Object> map, String id) {
		map.put("update_time", DateUtil.now());
		BladeUser user = AuthUtil.getUser();
		if (Func.isNotEmpty(user)) {
			Long userId = user.getUserId();
			map.put("update_user", userId);
		}
		String updateSql = "update " + tableName + " set ";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			if (Func.equals("id", field)) {
				continue;
			}
			updateSql += field + " = " + getSql(field, value) + ",";
		}
		if (updateSql.endsWith(",")) {
			updateSql = updateSql.substring(0, updateSql.length() - 1);
		}
		String sql = updateSql + " where id ='" + id + "'";
		map.put("select_sql", sql);
		return map;
	}

	//公共新增
	@Override
	public Long baseSimpleIntegerSql(String tableName, Map<String, Object> map) {
		Map<String, Object> insertMap = insertSimpleData(tableName, map);
		//log.info("执行sql------》》》》"+insertMap.toString());
		return loginMapper.baseIntegerSql(insertMap);
	}

	//公共修改
	@Override
	public void baseSimpleUpdaSql(String tableName, Map<String, Object> map, String id) {
		Map<String, Object> updateMap = updateSimpleData(tableName, map, id);
		//log.info("执行sql------》》》》"+updateMap.toString());
		loginMapper.baseUpdateSql(updateMap);
	}


	//微信小程序登录
	@Override
	public UserInfo wxMiniLogin(WxUserParam param) {
		String inviteCode = param.getInviteCode();//邀请码
		String code = param.getCode();
		Date now = DateUtil.now();
		try {
			final WxMaService wxService = WxMaConfiguration.getMaService(wxMaProperties.getConfigs().get(0).getAppid());
			WxMaJscode2SessionResult sessionInfo = wxService.getUserService().getSessionInfo(code);


			String openId = sessionInfo.getOpenid();
			if (Func.isEmpty(openId)) {
				UserInfo userInfo = new UserInfo();
				userInfo.setErrorMsg("ER001");//小程序授权未授
				return userInfo;
			}

			String name = param.getWxName();//名称
			String avatar = param.getWxAvatar();//头像
			String deptId = "1626054643370754049";//微信用户
			String roleId = "1626051216012017666";//微信用户
			String postId = "1123598817738675201";//微信用户
			//通过openId获取
			Long bladeUserId = loginMapper.getBladeUserIdByOpenId(openId);
			if (Func.isEmpty(bladeUserId)) {//不存在，则需要新建
				//-------校验通过，进行注册-------------
				name = name + IdWorker.getIdStr();
				String tenantId = "000000";
				bladeUserId = IdWorker.getId();
				User addUser = new User();
				addUser.setId(bladeUserId);
				addUser.setTenantId(tenantId);
				addUser.setAccount(Func.randomUUID());//账户
				addUser.setUserType(4);//微信
				addUser.setPassword(IdWorker.getIdStr());//密码
				addUser.setName(name);
				addUser.setRealName(name);
				addUser.setAvatar(avatar);//头像
				addUser.setDeptId(deptId);//部门
				addUser.setRoleId(roleId);//角色
				addUser.setPostId(postId);//用户
				userService.submit(addUser);

				//注册赠送次数
				R<String> registerNumR = bladexClient.getCsszValue("question_register_num");
				String registerNum = "";
				if (registerNumR.isSuccess()) {
					registerNum = registerNumR.getData();
				}
				R<String> newInviteCodeR = bladexClient.getNewInviteCode();
				String newInviteCode = newInviteCodeR.getData();

				String pid = "";
				Long pBladeUserId = 0L;
				if (Func.isNotEmpty(inviteCode)) {//有存在邀请码，则返佣给邀请码
					R<Map<String, Object>> wuserMapR = bladexClient.getWxuseridByInvitecode(inviteCode);
					Map<String, Object> wuserMap = wuserMapR.getData();
					pid = Func.toStr(wuserMap.get("id"));
					pBladeUserId = Func.toLong(wuserMap.get("blade_user_id"));
				}
				pid = Func.isEmpty(pid) ? "0" : pid;
				//添加会员
				String wxuserId = IdWorker.getIdStr();
				Map<String, Object> wxUserMap = new HashMap<>();
				wxUserMap.put("id", wxuserId);
				wxUserMap.put("chat_code", Func.randomUUID());//聊天id
				wxUserMap.put("blade_user_id", bladeUserId);
				wxUserMap.put("open_id", openId);
				wxUserMap.put("last_login_time", now);//最近登录时间
				wxUserMap.put("register_time", now);//注册时间
				wxUserMap.put("invite_code", newInviteCode);//自己的邀请码
				wxUserMap.put("pid", pid);//父节点id
				wxUserMap.put("phone", param.getPhone());//手机号码
				if (Func.isNotEmpty(param.getPhone())) {
					wxUserMap.put("wx_name", param.getPhone());//名称
				} else {
					String wxName = Func.random(8, RandomType.ALL);
					wxUserMap.put("wx_name", "用户" + wxName);//名称
				}

				wxUserMap.put("tenant_id", tenantId);
				wxUserMap.put("create_user", bladeUserId);
				wxUserMap.put("create_time", DateUtil.now());
				wxUserMap.put("status", 0);
				wxUserMap.put("is_deleted", 0);
				this.baseSimpleIntegerSql("chat_wxuser", wxUserMap);//保存成功

				//注册成功，发放奖励(自己)  【注册->1】【分享->2】【分享注册->3】【提问->4】
				QuestionNumParam addParam = new QuestionNumParam();
				addParam.setWxuserId(wxuserId);
				addParam.setServiceType(1);//注册
				addParam.setNum(Func.toInt(registerNum));
				bladexClient.addQuestionNum(addParam);

				if (Func.isNotEmpty(pid) && !Func.equals(pid, "0")) {//有存在邀请码，则返佣给邀请码---------
					R<String> pNumR = bladexClient.getCsszValue("question_share_success_num");
					String successNum = pNumR.getData();

					//邀请人的
					QuestionNumParam addParamP = new QuestionNumParam();
					addParamP.setBladeUsrId(pBladeUserId);
					addParamP.setWxuserId(pid);
					addParamP.setServiceType(3);//注册
					addParamP.setNum(Func.toInt(successNum));
					bladexClient.addQuestionNum(addParamP);
				}
			} else {
				String wxUserId = loginMapper.getWxUserId(bladeUserId);
				Map<String, Object> updateMap = new HashMap<>();
				updateMap.put("last_login_time", now);//最近登录时间
				if (Func.isNotEmpty(param.getPhone())) {
					updateMap.put("phone", param.getPhone());//手机号码
				}
				this.baseSimpleUpdaSql("chat_wxuser", updateMap, wxUserId);
			}

			UserInfo userInfo = userService.userInfo(bladeUserId);
			userInfo.getUser().setAvatar(avatar);//重新覆盖头像
			userInfo.getUser().setName(name);//重新覆盖名称
			userInfo.getUser().setRealName(name);//重新覆盖名称
			return userInfo;
		} catch (Exception e) {
			UserInfo userInfo = new UserInfo();
			userInfo.setErrorMsg(e.getMessage());
			return userInfo;
		}
	}

	//微信小程序登录
	@Override
	public UserInfo phoneLogin(WxUserParam param) {
		String inviteCode = param.getInviteCode();//邀请码
		String phone = param.getPhone();//手机号

		Date now = DateUtil.now();
		try {


			String name = param.getWxName();//名称
			String avatar = param.getWxAvatar();//头像
			String deptId = "1626054643370754049";//微信用户
			String roleId = "1626051216012017666";//微信用户
			String postId = "1123598817738675201";//微信用户
			//通过openId获取
			Long bladeUserId = loginMapper.getBladeUserIdByPhone(phone);
			if (Func.isEmpty(bladeUserId)) {//不存在，则需要新建
				//-------校验通过，进行注册-------------
				name = name + IdWorker.getIdStr();
				String tenantId = "000000";
				bladeUserId = IdWorker.getId();
				User addUser = new User();
				addUser.setId(bladeUserId);
				addUser.setTenantId(tenantId);
				addUser.setAccount(Func.randomUUID());//账户
				addUser.setUserType(4);//微信
				addUser.setPassword(IdWorker.getIdStr());//密码
				addUser.setName(name);
				addUser.setRealName(name);
				addUser.setAvatar(avatar);//头像
				addUser.setDeptId(deptId);//部门
				addUser.setRoleId(roleId);//角色
				addUser.setPostId(postId);//用户
				userService.submit(addUser);

				//注册赠送次数
				R<String> registerNumR = bladexClient.getCsszValue("question_register_num");
				String registerNum = "";
				if (registerNumR.isSuccess()) {
					registerNum = registerNumR.getData();
				}
				R<String> newInviteCodeR = bladexClient.getNewInviteCode();
				String newInviteCode = newInviteCodeR.getData();

				String pid = "";
				Long pBladeUserId = 0L;
				if (Func.isNotEmpty(inviteCode)) {//有存在邀请码，则返佣给邀请码
					R<Map<String, Object>> wuserMapR = bladexClient.getWxuseridByInvitecode(inviteCode);
					Map<String, Object> wuserMap = wuserMapR.getData();
					pid = Func.toStr(wuserMap.get("id"));
					pBladeUserId = Func.toLong(wuserMap.get("blade_user_id"));
				}
				pid = Func.isEmpty(pid) ? "0" : pid;
				//添加会员
				String wxuserId = IdWorker.getIdStr();
				Map<String, Object> wxUserMap = new HashMap<>();
				wxUserMap.put("id", wxuserId);
				wxUserMap.put("chat_code", Func.randomUUID());//聊天id
				wxUserMap.put("blade_user_id", bladeUserId);
				wxUserMap.put("last_login_time", now);//最近登录时间
				wxUserMap.put("register_time", now);//注册时间
				wxUserMap.put("invite_code", newInviteCode);//自己的邀请码
				wxUserMap.put("pid", pid);//父节点id
				wxUserMap.put("phone", phone);//手机号码
				String wxName = Func.random(8, RandomType.ALL);
				wxUserMap.put("wx_name", "用户" + wxName);//名称
				wxUserMap.put("app_flag", "1");

				wxUserMap.put("tenant_id", tenantId);
				wxUserMap.put("create_user", bladeUserId);
				wxUserMap.put("create_time", DateUtil.now());
				wxUserMap.put("status", 0);
				wxUserMap.put("is_deleted", 0);
				this.baseSimpleIntegerSql("chat_wxuser", wxUserMap);//保存成功

				//注册成功，发放奖励(自己)  【注册->1】【分享->2】【分享注册->3】【提问->4】
				QuestionNumParam addParam = new QuestionNumParam();
				addParam.setWxuserId(wxuserId);
				addParam.setServiceType(1);//注册
				addParam.setNum(Func.toInt(registerNum));
				bladexClient.addQuestionNum(addParam);

				if (Func.isNotEmpty(pid) && !Func.equals(pid, "0")) {//有存在邀请码，则返佣给邀请码---------
					R<String> pNumR = bladexClient.getCsszValue("question_share_success_num");
					String successNum = pNumR.getData();

					//邀请人的
					QuestionNumParam addParamP = new QuestionNumParam();
					addParamP.setBladeUsrId(pBladeUserId);
					addParamP.setWxuserId(pid);
					addParamP.setServiceType(3);//注册
					addParamP.setNum(Func.toInt(successNum));
					bladexClient.addQuestionNum(addParamP);
				}
			} else {
				String wxUserId = loginMapper.getWxUserId(bladeUserId);
				Map<String, Object> updateMap = new HashMap<>();
				updateMap.put("last_login_time", now);//最近登录时间
				updateMap.put("app_flag", "1");
				if (Func.isNotEmpty(param.getPhone())) {
					updateMap.put("phone", param.getPhone());//手机号码
				}
				this.baseSimpleUpdaSql("chat_wxuser", updateMap, wxUserId);
			}

			UserInfo userInfo = userService.userInfo(bladeUserId);
			userInfo.getUser().setAvatar(avatar);//重新覆盖头像
			userInfo.getUser().setName(name);//重新覆盖名称
			userInfo.getUser().setRealName(name);//重新覆盖名称
			return userInfo;
		} catch (Exception e) {
			UserInfo userInfo = new UserInfo();
			userInfo.setErrorMsg(e.getMessage());
			return userInfo;
		}
	}


	//微信开放平台
	@Override
	public UserInfo wxOpenRegister(WxOpenParam param) {
		Date now = DateUtil.now();

		String inviteCode = param.getInviteCode();//邀请码
		String phone = param.getPhone();//手机号
		String type = param.getType();
		String uuid = param.getUuid();

		String loginRedisKey="wxopen:login:"+uuid;
		String unionId =  bladeRedis.get(loginRedisKey);
		if(Func.isEmpty(unionId)){//登录失败
			UserInfo userInfo=new UserInfo();
			userInfo.setErrorMsg("登录失败");
			return userInfo;
		}


		if (Func.equals(type, "register")) {//注册
			String deptId = "1626054643370754049";//微信用户
			String roleId = "1626051216012017666";//微信用户
			String postId = "1123598817738675201";//微信用户

			String wxName = Func.random(8, RandomType.ALL);

			//-------校验通过，进行注册-------------
			String tenantId = "000000";
			Long bladeUserId = IdWorker.getId();
			User addUser = new User();
			addUser.setId(bladeUserId);
			addUser.setTenantId(tenantId);
			addUser.setAccount(Func.randomUUID());//账户
			addUser.setUserType(4);//微信
			addUser.setPassword(IdWorker.getIdStr());//密码
			addUser.setName(wxName);
			addUser.setRealName(wxName);
			addUser.setAvatar(null);//头像
			addUser.setDeptId(deptId);//部门
			addUser.setRoleId(roleId);//角色
			addUser.setPostId(postId);//用户
			userService.submit(addUser);

			//注册赠送次数
			R<String> registerNumR = bladexClient.getCsszValue("question_register_num");
			String registerNum = "";
			if (registerNumR.isSuccess()) {
				registerNum = registerNumR.getData();
			}
			R<String> newInviteCodeR = bladexClient.getNewInviteCode();
			String newInviteCode = newInviteCodeR.getData();

			String pid = "";
			Long pBladeUserId = 0L;
			if (Func.isNotEmpty(inviteCode)) {//有存在邀请码，则返佣给邀请码
				R<Map<String, Object>> wuserMapR = bladexClient.getWxuseridByInvitecode(inviteCode);
				Map<String, Object> wuserMap = wuserMapR.getData();
				if (Func.isNotEmpty(wuserMap)) {//存在邀请用户
					pid = Func.toStr(wuserMap.get("id"));
					pBladeUserId = Func.toLong(wuserMap.get("blade_user_id"));
				}

			}
			pid = Func.isEmpty(pid) ? "0" : pid;
			//添加会员
			String wxuserId = IdWorker.getIdStr();
			Map<String, Object> wxUserMap = new HashMap<>();
			wxUserMap.put("id", wxuserId);
			wxUserMap.put("chat_code", Func.randomUUID());//聊天id
			wxUserMap.put("blade_user_id", bladeUserId);
			wxUserMap.put("last_login_time", now);//最近登录时间
			wxUserMap.put("register_time", now);//注册时间
			wxUserMap.put("invite_code", newInviteCode);//自己的邀请码
			wxUserMap.put("pid", pid);//父节点id
			wxUserMap.put("phone", phone);//手机号码
			wxUserMap.put("wx_name", "用户" + wxName);//名称
			wxUserMap.put("app_flag", "1");
			wxUserMap.put("tenant_id", tenantId);
			wxUserMap.put("create_user", bladeUserId);
			wxUserMap.put("create_time", DateUtil.now());
			wxUserMap.put("status", 0);
			wxUserMap.put("is_deleted", 0);
			wxUserMap.put("wx_union_id",unionId);//唯一id
			this.baseSimpleIntegerSql("chat_wxuser", wxUserMap);//保存成功

			//注册成功，发放奖励(自己)  【注册->1】【分享->2】【分享注册->3】【提问->4】
			QuestionNumParam addParam = new QuestionNumParam();
			addParam.setWxuserId(wxuserId);
			addParam.setServiceType(1);//注册
			addParam.setNum(Func.toInt(registerNum));
			bladexClient.addQuestionNum(addParam);

			if (Func.isNotEmpty(pid) && !Func.equals(pid, "0")) {//有存在邀请码，则返佣给邀请码---------
				R<String> pNumR = bladexClient.getCsszValue("question_share_success_num");
				String successNum = pNumR.getData();

				//邀请人的
				QuestionNumParam addParamP = new QuestionNumParam();
				addParamP.setBladeUsrId(pBladeUserId);
				addParamP.setWxuserId(pid);
				addParamP.setServiceType(3);//注册
				addParamP.setNum(Func.toInt(successNum));
				bladexClient.addQuestionNum(addParamP);
			}
			return null;
		} else {//登录
			String wxUserId = loginMapper.getWxUserIdByUnioId(unionId);
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("last_login_time", now);//最近登录时间
			updateMap.put("app_flag", "1");
			if (Func.isNotEmpty(param.getPhone())) {
				updateMap.put("phone", param.getPhone());//手机号码
			}
			this.baseSimpleUpdaSql("chat_wxuser", updateMap, wxUserId);

			Long bladeUserId = loginMapper.getBladeUserIdByUnioId(unionId);
			UserInfo userInfo = userService.userInfo(bladeUserId);
			return userInfo;
		}
	}

}
