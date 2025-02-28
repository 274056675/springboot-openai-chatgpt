/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.*;
import org.springblade.modules.mjkj.common.constant.ServiceTypeConstant;
import org.springblade.modules.mjkj.service.IWebService;
import org.springblade.modules.system.entity.*;
import org.springblade.modules.system.mapper.MjkjLoginMapper;
import org.springblade.modules.system.service.IMjkjUserService;
import org.springblade.modules.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 服务实现类
 *
 * @author Chill
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
	private IWebService webService;

	@Autowired
	private RedisUtil redisUtil;

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




	//手机号登录
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
				//注册赠送积分次数
				String registerNum = webService.getCsszVal("question_register_num","0");//1
				String registerRl = webService.getCsszVal("rl_register_num","0");//1
				String newInviteCode = webService.getNewInviteCode();
				String pid = "";
				Long pBladeUserId = 0L;
				if (Func.isNotEmpty(inviteCode)) {//有存在邀请码，则返佣给邀请码
					Map<String, Object> wuserMap  = webService.getWxuserMapByInvitecode(inviteCode);
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
				wxUserMap.put("last_login_ip", WebUtil.getIP());//最近一次登录的IP
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
				addParam.setServiceType(ServiceTypeConstant.zc);//注册
				addParam.setNum(Func.toInt(registerNum));
				webService.addWxuserQuestionNum(addParam.getBladeUsrId(),addParam.getWxuserId(),addParam.getServiceType(),addParam.getNum(),null,"注册赠送积分","question");

				QuestionNumParam addRl = new QuestionNumParam();
				addRl.setWxuserId(wxuserId);
				addRl.setServiceType(ServiceTypeConstant.zc);
				addRl.setNum(Func.toInt(registerRl));
				webService.addWxuserQuestionNum(addRl.getBladeUsrId(),addRl.getWxuserId(),addRl.getServiceType(),addRl.getNum(),null,"注册赠送燃料","rl");


				if (Func.isNotEmpty(pid) && !Func.equals(pid, "0")) {//有存在邀请码，则返佣给邀请码---------
					String successNum = webService.getCsszVal("question_share_success_num","0");

					//邀请人的
					QuestionNumParam addParamP = new QuestionNumParam();
					addParamP.setBladeUsrId(pBladeUserId);
					addParamP.setWxuserId(pid);
					addParamP.setServiceType(ServiceTypeConstant.fxzc);
					addParamP.setNum(Func.toInt(successNum));
					webService.addWxuserQuestionNum(addParam.getBladeUsrId(),addParam.getWxuserId(),addParam.getServiceType(),addParam.getNum(),null,"注册赠送积分","question");

				}
			} else {
				String wxUserId = loginMapper.getWxUserId(bladeUserId);
				Map<String, Object> updateMap = new HashMap<>();
				updateMap.put("last_login_time", now);//最近登录时间
				updateMap.put("last_login_ip", WebUtil.getIP());//最近一次登录的IP
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


	@Override
	public boolean checkPhone(SmsCodeParam param) {
		String phone = param.getPhone();
		String code = param.getCode();
		if(Func.equals(phone,"13800138000")){//苹果审核
			return true;
		}
		String redisKey="SMS_PHONE:"+phone;
		if(!redisUtil.hasKey(redisKey)){
			return true;
		}
		String redisCode = (String) redisUtil.get(redisKey);
		if(Func.equals(code,redisCode)){
			return true;
		}
		return false;
	}
}
