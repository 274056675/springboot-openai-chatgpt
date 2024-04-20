package org.springblade.mng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.freewayso.image.combiner.ImageCombiner;
import com.freewayso.image.combiner.enums.OutputFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.constant.ChatgptConfig;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.config.LocalCache;
import org.springblade.mng.mapper.WebMapper;
import org.springblade.mng.model.*;
import org.springblade.mng.param.ChatLogShareMessageParam;
import org.springblade.mng.param.MoreFunParam;
import org.springblade.mng.param.QuestionParam;
import org.springblade.mng.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;

/**
 * 前台相关
 */
@Slf4j
@Service
public class WebServiceImpl implements IWebService {

	@Resource
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IChatGPTService chatGPTService;



	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private WebMapper webMapper;



	//删除用户
	@Override
	public void delWuserInfo(String wxuserId) {
		Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
		if (Func.isEmpty(wxuserMap)) {
			throw new ServiceException("用户不存在");
		}
		baseSqlService.baseDeleteSqlStr("chat_wxuser", wxuserId);
	}

	//获取用户详情
	@Override
	public WxUserInfoModel getWxUsrInfo() {
		return this.getWxUsrInfo(null);
	}

	//获取用户详情
	@Override
	public WxUserInfoModel getWxUsrInfo(String type) {

		Long userId = AuthUtil.getUserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", userId);
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			throw new RuntimeException("数据不存在");
		}
		String wxuserId = MjkjUtils.getMap2Str(dataMap, "id");
		//获取次数
		Integer messageCou = 0;
		messageCou = this.getMessageCou(wxuserId);


		//获取等级
		String levelTitle = this.getLevelTitle(messageCou);

		//代理商
		String isAgent = MjkjUtils.getMap2Str(dataMap, "is_agent");

		Date expireTime = MjkjUtils.getMap2DateTime(dataMap, "expire_time");

		//处理会员--------开始---------
		boolean memberFlag = false;
		Date now = DateUtil.now();
		if (Func.isNotEmpty(expireTime) && expireTime.getTime() >= now.getTime()) {
			memberFlag = true;
		}
		//处理会员--------结束---------

		//禁言截至时间--------开始---------
		Date stopSendTime = MjkjUtils.getMap2DateTime(dataMap, "stop_send_time");
		if (Func.isNotEmpty(stopSendTime)) {
			if (now.getTime() > stopSendTime.getTime()) {//当前时间已经超过，说明可以放开了
				Map<String, Object> updateMap = new HashedMap();
				updateMap.put("stop_send_time", null);
				baseSqlService.baseUpdateData("chat_wxuser", updateMap, wxuserId);
				stopSendTime = null;//返回前端改为空
			}
		}
		//禁言截至时间--------结束---------

		QueryWrapper<Object> lastChatWrapper = new QueryWrapper<>();
		lastChatWrapper.eq("is_deleted", 0);
		lastChatWrapper.eq("blade_user_id", userId);
		lastChatWrapper.select("message_time");
		lastChatWrapper.orderByAsc("id+0");
		Map<String, Object> messageMap = baseSqlService.getDataOneByFieldParams("chat_log_message", lastChatWrapper);
		Date messageTime = MjkjUtils.getMap2DateTime(messageMap, "message_time");

		//获取当前的数量
		Integer viewRewardAdvertCou = 0;
		String rewardAdvertRedisKey = MjkjUtils.getRewardAdvertRedisKey(userId, true);
//		Set<String> keys = redisUtil.sGet(rewardAdvertRedisKey);
//		if (Func.isNotEmpty(keys)) {
//			viewRewardAdvertCou = keys.size();
//		}
		boolean buyFlag = true;
		if (Func.isNotEmpty(expireTime)) {
			String expireYYYY = DateUtil.format(expireTime, "yyyy");
			String nowYYYY = DateUtil.format(now, "yyyy");
			if ((Func.toInt(expireYYYY) - Func.toInt(nowYYYY)) > 100) {//大于100年就是永久
				String allTime = "9999-12-31 23:59:59";
				buyFlag = false;
				expireTime = DateUtil.parse(allTime, DateUtil.PATTERN_DATETIME);
			}
		}

		String wxName = MjkjUtils.getMap2Str(dataMap, "wx_name");

		//微信专属连接
		String wxShareUrl = MjkjUtils.getMap2Str(dataMap, "wx_share_url");
		String inviteCode = MjkjUtils.getMap2Str(dataMap, "invite_code");
		//用户信息里的邀请码为空，生成新的邀请码
		if (Func.isEmpty(inviteCode)){
			inviteCode = this.getNewInviteCode();
			Map<String,Object> updateMap = new HashMap<>();
			updateMap.put("invite_code",inviteCode);
			baseSqlService.baseUpdateData("chat_wxuser",updateMap,wxuserId);
		}


		String phone = MjkjUtils.getMap2Str(dataMap, "phone");
		if (Func.isEmpty(wxName)) {
			wxName = "匿名用户";
		} else if (Func.isNotEmpty(phone) && Func.equals(phone, wxName)) {//如果手机号码和名称一样，则强制改为匿名用户
			wxName = "匿名用户";
		}
		String userCode = MjkjUtils.getMap2Str(dataMap, "user_code");
		if (Func.isEmpty(userCode)) {//重新生成 用户号
			while (true) {
				userCode = Func.random(10, RandomType.INT);
				Map<String, Object> userCodeMap = baseSqlService.getDataOneByField("chat_wxuser", "user_code", userCode);
				if (Func.isEmpty(userCodeMap)) {
					String id = MjkjUtils.getMap2Str(dataMap, "id");
					Map<String,Object> updateMap=new HashMap<>();
					updateMap.put("user_code",userCode);
					baseSqlService.baseUpdateData("chat_wxuser",updateMap,id);
					break;
				}
			}
		}


		WxUserInfoModel model = new WxUserInfoModel();
		model.setId(MjkjUtils.getMap2Str(dataMap, "id"));
		model.setBladeUserId(userId);
		model.setWxName(wxName);
		model.setWxName_Dim(MjkjUtils.desensitizeStr(wxName, "匿名用户"));
		model.setWxAvatar(MjkjUtils.getMap2Str(dataMap, "wx_avatar"));
		model.setChatCode(MjkjUtils.getMap2Str(dataMap, "chat_code"));
		model.setOpenId(MjkjUtils.getMap2Str(dataMap, "open_id"));
		model.setMessageCou(messageCou);
		model.setLeveTitler(levelTitle);
		model.setViewModel("1");//动态模式
		model.setExpireTime(expireTime);//到期时间
		model.setBuyFlag(buyFlag);
		model.setMemberFlag(memberFlag);
		model.setLastChatTime(messageTime);
		model.setExprotMinDate(messageTime);
		model.setPhone(phone);//
		model.setQuestionCou(MjkjUtils.getMap2Integer(dataMap, "question_cou"));
		if (memberFlag) {//会员无限制
			model.setQuestionCouStr("无限次");
		}
		model.setInviteCode(inviteCode);
		model.setPosterUrl(MjkjUtils.getMap2Str(dataMap, "poster_url"));//海报地址
		model.setPosterWxUrl(MjkjUtils.getMap2Str(dataMap, "poster_wx_url"));//微信端海报地址
		model.setStopSendTime(stopSendTime);//禁言截至时间
//		model.setViewRewardAdvertCou(viewRewardAdvertCou);//观看视频次数
		model.setWxShareUrl(wxShareUrl);
		model.setUserCode(userCode);//用户号
		model.setIsAgent(isAgent);//是否代理商
		model.setRl_cou(MjkjUtils.getMap2Integer(dataMap,"rl_cou"));
		model.setRl_used_cou(MjkjUtils.getMap2Integer(dataMap,"rl_used_cou"));
		model.setSign(Func.isNotEmpty(dataMap.get("sign"))?MjkjUtils.getMap2Str(dataMap,"sign"):null);
		return model;
	}

	//获取openId
	@Override
	public String getOpenId() {
		Long userId = AuthUtil.getUserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", userId);
		wrapper.select("open_id");
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			throw new ServiceException("数据不存在");
		}
		return MjkjUtils.getMap2Str(dataMap, "open_id");
	}

	//获取微信id----走缓存
	@Override
	public String getChatCode() {
		//走缓存
		Long userId = AuthUtil.getUserId();
		String redisKey = MjkjUtils.getRedisKeyWuserId(userId);
		if (redisUtil.hasKey(redisKey)) {
			return (String) redisUtil.get(redisKey);
		}


		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", userId);
		wrapper.select("chat_code");
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			throw new ServiceException("数据不存在");
		}
		String chatCode = MjkjUtils.getMap2Str(dataMap, "chat_code");
		redisUtil.set(redisKey, chatCode);

		return chatCode;
	}

	//获取微信id ---- 走缓存
	@Override
	public String getWxuserId() {
		//走缓存
		Long userId = AuthUtil.getUserId();
		String redisKey = MjkjUtils.getRedisKeyWuserId(userId);
//		if (redisUtil.hasKey(redisKey)) {
//			return (String) redisUtil.get(redisKey);
//		}


		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", userId);
		wrapper.select("id");
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			throw new ServiceException("数据不存在");
		}
		String wuserId = MjkjUtils.getMap2Str(dataMap, "id");
		redisUtil.set(redisKey, wuserId);
		return wuserId;
	}

	//获取问题可提问次数
	@Override
	public Integer getWuserQuestionCou(Long bladeUserId , String numType) {
		if (Func.isEmpty(bladeUserId)) {
			bladeUserId = AuthUtil.getUserId();
		}

		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", bladeUserId);
		switch (numType){
			case "question" :
				wrapper.select("question_cou");//问题次数
				break;
			case  "rl" :
				wrapper.select("rl_cou");
				break;
		}
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			return 0;
		}
		switch (numType) {
			case "question" :
				return MjkjUtils.getMap2Integer(dataMap, "question_cou");
			case "rl" :
				return MjkjUtils.getMap2Integer(dataMap, "rl_cou");
		}
		return MjkjUtils.getMap2Integer(dataMap, "question_cou");
	}


	//获取提问一次消耗多少次
	@Override
	public Integer getOneQuestionUseNum() {
		try {
			String val = this.getCsszVal("question_use_num", "1");//1
			if (Func.isEmpty(val)) {
				return 1;
			}
			return Func.toInt(val);
		} catch (Exception e) {

		}
		return 1;
	}


	//发送问题
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<ChatGptMsgModel> sendQuestion(String question, Long startMessageId, Integer useNum, String textType, Long chatListIdL) {
		Long bladeUserId = AuthUtil.getUserId();
		String wxuserId = this.getWxuserId();
		String chatCode = this.getChatCode();


		Date now = DateUtil.now();
		String id = IdWorker.getIdStr();
		WxUserInfoModel wxUserInfoModel = this.getWxUsrInfo();

		//查询用户有没有设置模型
		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_wxuser_setting", "wxuser_id", wxuserId);
		String defaultmodel = ChatgptConfig.getChatgptModel();
		if (Func.isEmpty(settingMap)) { //用户没有设置模型，给他默认设置
			settingMap = new HashMap<>();
			settingMap.put("id", IdWorker.getIdStr());
			settingMap.put("wxuser_id", wxuserId);
			settingMap.put("blade_user_id", bladeUserId);
			settingMap.put("translate_lang", "中文");
			settingMap.put("ai_model", defaultmodel);
			settingMap.put("image_model", ChatgptConfig.getImageModel());
			baseSqlService.baseInsertData("chat_wxuser_setting", settingMap);
		}

		String aiModel = MjkjUtils.getMap2Str(settingMap, "ai_model");
		String imageModel = MjkjUtils.getMap2Str(settingMap, "image_model");
		String chatgptModel = aiModel;


		List<Map<String, Object>> accountMapList = baseSqlService.getDataListByField("chat_api_account", "gpt_state", 0);//1

		if (Func.isEmpty(accountMapList)) {
			throw new ServiceException("账户配置有误，请联系客服处理");
		}

		ChatGptMsgModel questionMsgModel = new ChatGptMsgModel();
		questionMsgModel.setId(id);
		questionMsgModel.setPid("-1");
		questionMsgModel.setChatCode(chatCode);
		questionMsgModel.setMessage_type(MessageType.Q);//q =问题  a=答案
		questionMsgModel.setMessage_content(question);
		questionMsgModel.setMessage_time(now);
		questionMsgModel.setView_type(ViewType.TEXT);
		questionMsgModel.setChat_list_id(String.valueOf(chatListIdL));

		//返回结果，如果有敏感字的话，直接返回
		Map<String, Object> insertMap = new HashMap<>();
		insertMap.put("id", id);
		insertMap.put("wxuser_id", wxuserId);
		insertMap.put("message_type", MessageType.Q);//q =问题  a=答案
		insertMap.put("message_content", question);
		insertMap.put("message_time", now);
		insertMap.put("blade_user_id", bladeUserId);
		insertMap.put("view_type", ViewType.TEXT);
		insertMap.put("model_type", 0);
		insertMap.put("context_flag", 1);//支持上下文
		if (chatListIdL != 0L) {
			insertMap.put("chat_list_id", String.valueOf(chatListIdL));

		}
		//校验是否有敏感词
		List<ChatGptMsgModel> resultModelList = new ArrayList<>();
		resultModelList.add(questionMsgModel);
		//判断模型需要消耗的燃料，如果剩余燃料数不够用，就切换为默认的模型
		Integer rl = 0;
		Integer nowRl = wxUserInfoModel.getRl_cou();
		if (rl > 0 && nowRl < rl) {
			chatgptModel = ChatgptConfig.getChatgptModel();
			Map<String, Object> updateMap = new HashedMap();
			updateMap.put("ai_model", chatgptModel);
			baseSqlService.baseUpdateDataWhere("chat_wxuser_setting", updateMap, "wxuser_id", wxuserId);
			rl = 0;
		}
		if (rl != 0) {
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("rl_used_cou", wxUserInfoModel.getRl_used_cou() + rl);
			baseSqlService.baseUpdateData("chat_wxuser", updateMap, wxuserId);
			this.subWxuserQuestionNum(bladeUserId, wxuserId, 4, rl, id, "AI问答->消耗燃料", "rl");
		} else {
			this.subWxuserQuestionNum(bladeUserId, wxuserId, 4, useNum, id, "AI问答->消耗积分", "question");
		}



		baseSqlService.baseInsertData("chat_log_message", insertMap);

		if (Func.equals(chatgptModel, "text-davinci-003")) {
			chatGPTService.sendChatGptMessage(wxuserId, id, question, startMessageId, chatListIdL);
		} else if (Func.equals(chatgptModel, "gpt-3.5-turbo-16k")) {
			chatGPTService.sendChatGptTurboMessage(wxuserId, id, question, startMessageId, now, chatListIdL);
		}
		return resultModelList;

	}


	//获取历史聊天记录
	@Override
	public IPage<Map<String, Object>> getMessageHistoryList(Long startNum, String modelType, IPage<Object> page, String chatListId,String type) {
		Long userId = AuthUtil.getUserId();

		//获取总数
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("blade_user_id", userId);
		totalWrapper.eq("model_type", modelType);
		totalWrapper.eq("is_deleted", 0);
		if ( Func.isNotEmpty(chatListId) && Func.equals(type,"pc") ) {
			totalWrapper.eq("chat_list_id",chatListId);
		}
		totalWrapper.select("count(1) as cou");
		Map<String, Object> totalMap = baseSqlService.getDataOneByFieldParams("chat_log_message", totalWrapper);
		Long totalCou = MjkjUtils.getMap2Long(totalMap, "cou");


		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("blade_user_id", userId);
		wrapper.eq("model_type", modelType);
		wrapper.eq("is_deleted", 0);
		if ( Func.isNotEmpty(chatListId) && Func.equals(type,"pc") ) {
			wrapper.eq("chat_list_id",chatListId);
		}
		if (Func.isNotEmpty(startNum)) {
			wrapper.le("id+0", startNum);// <=
		}
		wrapper.orderByDesc("id+0");
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_log_message", page, wrapper);
		List<Map<String, Object>> records = pages.getRecords();
		if (Func.isNotEmpty(records)) {
			Collections.reverse(records);
			pages.setRecords(records);
		}
		pages.setTotal(totalCou);//重置总数
		return pages;
	}

	//获取历史聊天记录
	@Override
	public List<Map<String, Object>> getMessageLastList(Long startNum, String modelType,String type,String chatListId) {
		Long userId = AuthUtil.getUserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("blade_user_id", userId);
		wrapper.eq("model_type", modelType);
		wrapper.eq("is_deleted", 0);
		wrapper.eq("message_type", "a");//回答
		if (Func.equals(type,"pc")){
			wrapper.eq("chat_list_id",chatListId);
		}
		if (Func.isNotEmpty(startNum)) {
			wrapper.gt("id+0", startNum);// >
		}

		wrapper.orderByDesc("id+0");
		List<Map<String, Object>> resultList = baseSqlService.getDataListByFieldParams("chat_log_message", wrapper);
		Collections.reverse(resultList);
		return resultList;
	}

	//获取消息次数
	@Override
	public Integer getMessageCou(String wxuserId) {
		Integer cou = webMapper.getMessageCou(wxuserId);
		if (Func.isEmpty(cou)) {
			cou = 0;
		}
		return cou;
	}

	//获取等级列表
	@Override
	public List<Map<String, Object>> getLevelList() {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", "0");
		wrapper.orderByDesc("ind");
		List<Map<String, Object>> dataList = baseSqlService.getDataListByFieldParams("chat_level", wrapper);
		return dataList;
	}

	//获取等级名称
	@Override
	public String getLevelTitle(int cou) {
		List<Map<String, Object>> dataMapList = this.getLevelList();
		if (Func.isEmpty(dataMapList)) {
			return "";
		}
		for (Map<String, Object> dataMap : dataMapList) {
			Integer start_num = MjkjUtils.getMap2Integer(dataMap, "start_num");
			Integer end_num = MjkjUtils.getMap2Integer(dataMap, "end_num");
			String title = MjkjUtils.getMap2Str(dataMap, "title");
			if (cou >= start_num && cou <= end_num) {
				return title;
			}

		}
		return "";
	}

	//获取参数设置
	@Override
	public String getCsszVal(String code, String defaultVal) {
		Map<String, Object> dataMap = baseSqlService.getDataOneByField("chat_system_cssz", "code", code);
		if (Func.isEmpty(dataMap)) {
			return defaultVal;
		}
		String val = MjkjUtils.getMap2Str(dataMap, "val");
		if (Func.isEmpty(val)) {
			return defaultVal;
		}
		return val;
	}

	//获取参数设置



	//增加用户次数
	@Transactional
	@Override
	public void addWxuserQuestionNum(Long bladeUserId, String wxuserId, Integer serviceType, Integer num, String questionId, String remark ,String numType) {
		if(num<=0){
			return;
		}
		this.wxuserQuestionNum(bladeUserId, "ADD", serviceType, wxuserId, num, questionId, remark, numType);
	}

	//减用户次数
	@Transactional
	@Override
	public void subWxuserQuestionNum(Long bladeUserId, String wxuserId, Integer serviceType, Integer num, String questionId, String remark,String numType) {
		if(num<=0){
			return;
		}
		this.wxuserQuestionNum(bladeUserId, "SUB", serviceType, wxuserId, num, questionId, remark,numType);
	}

	//加锁
	private synchronized void wxuserQuestionNum(Long bladeUserId, String type, Integer serviceType, String wxuserId, Integer num, String questionId, String remark ,String numType) {

		Integer before_num = this.getWuserQuestionCou(bladeUserId,numType);//用户各类可用次数
		Integer after_num = 0;

		if (Func.equals(type, "ADD")) {//增加
			after_num = before_num + num;
		} else if (Func.equals(type, "SUB")) {//减少
			after_num = before_num - num;
		}
		//更新用户使用后的次数
		Map<String, Object> wuserUpdateMap = new HashedMap();
		switch (numType) {
			case "question":
				wuserUpdateMap.put("question_cou" , after_num);
				break;
			case "rl":
				wuserUpdateMap.put("rl_cou" , after_num);
				break;
		}

		baseSqlService.baseUpdateData("chat_wxuser", wuserUpdateMap, wxuserId);

		Date now = DateUtil.now();

		//插入使用记录
		Map<String, Object> detailMap = new HashedMap();
		detailMap.put("wxuser_id", wxuserId);
		detailMap.put("service_type", serviceType);
		detailMap.put("type", type);
		detailMap.put("num", num);
		detailMap.put("before_num", before_num);
		detailMap.put("after_num", after_num);
		detailMap.put("operate_time", now);
		detailMap.put("question_id", questionId);
		detailMap.put("remark", remark);
		baseSqlService.baseInsertData("chat_log_num", detailMap);

	}


	//获取新邀请码
	@Override
	public String getNewInviteCode() {
		String inviteCode = "";
		while (true) {
			inviteCode = Func.random(8, RandomType.ALL);
			QueryWrapper<Object> wrapper = new QueryWrapper<>();
			wrapper.eq("is_deleted", 0);
			wrapper.eq("invite_code", inviteCode);
			wrapper.select("id");
			Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
			if (Func.isEmpty(dataMap)) {
				return inviteCode;
			}
		}
	}

	//根据邀请码获取用户id
	@Override
	public String getWxuseridByInvitecode(String invitecode) {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("invite_code", invitecode);
		wrapper.select("id");
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		return MjkjUtils.getMap2Str(dataMap, "id");
	}


	//根据邀请码获取用户id
	@Override
	public Map<String, Object> getWxuserMapByInvitecode(String invitecode) {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("invite_code", invitecode);
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		return dataMap;
	}

	//根据邀请码获取用户id
	@Override
	public Map<String, Object> getWxuseridByUUID(String uuid) {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("uuid", uuid);
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		return dataMap;
	}

	/**
	 * 生成邀请码
	 *
	 * @return
	 */
	@Override
	public String generateQrcode() {
		try {
			WxUserInfoModel wxUsrInfo = this.getWxUsrInfo();
			if (Func.isEmpty(wxUsrInfo)) {
				return null;
			}
			//海报地址
			String posterWxUrl = wxUsrInfo.getPosterWxUrl();
			if (Func.isNotEmpty(posterWxUrl)) {
				return posterWxUrl;//已经生成过，则直接返回
			}
			String wxName = wxUsrInfo.getWxName();

			String inviteCode = wxUsrInfo.getInviteCode();
			String path = "/pages/index/index?inviteCode=" + inviteCode;
			Integer width = 50;
			//获取微信那边二维码
			String qrcodeFileUrl = ChatgptConfig.getUploadUrl();
			String posterBgUrl = ChatgptConfig.getPosterBgUrl();

			//背景
			ImageCombiner combiner = new ImageCombiner(posterBgUrl, OutputFormat.PNG);

			int length = wxName.length();
			int x = 230 - (length * 10);
			//处理头像
			combiner.addTextElement(wxName, 24, x, 630).setColor(Color.BLACK);
			//进行合成
			combiner.combine();
			combiner.getCombinedImageStream();
			String fileName = IdWorker.getIdStr() + ".png";
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成文件下载码
	 *
	 * @return
	 */

	//获取用户自定义设置
	@Override
	public Map<String, Object> getWxUserSetting(String wxUserId) {
		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_wxuser_setting", "wxuser_id", wxUserId);
		if (Func.isEmpty(settingMap)) {
			settingMap = new HashMap<>();
			settingMap.put("translate_lang", "中文");
			settingMap.put("wxuser_id", wxUserId);
			settingMap.put("blade_user_id", AuthUtil.getUserId());
			settingMap.put("ai_model",ChatgptConfig.getChatgptModel());
			settingMap.put("image_model",ChatgptConfig.getImageModel());
			baseSqlService.baseInsertData("chat_wxuser_setting", settingMap);
		}
		return settingMap;
	}


	//获取热门消息
	@Override
	public IPage<Map<String, Object>> getMessageHotList(IPage<Object> page) {
		return webMapper.getMessageHotList(page);
	}

	//发送更多好玩
	@Async("asyncPoolTaskExecutor")
	@Override
	public void sendMoreFun(String wxuserId, Long bladeUserId, Integer useNum, MoreFunParam param) {
		String systemTitle = param.getSystemTitle();
		String content = param.getContent();
		String textType = param.getText_type();

		content = content + " 请以MarkDown格式回复";

		String funFataId = param.getFunFataId();
		Map<String, Object> funDataMap = baseSqlService.getTableById("chat_fun_data", funFataId);
		String funName = MjkjUtils.getMap2Str(funDataMap, "fun_name");//更多好玩名称
		Map<String, Object> wxUserSetting = this.getWxUserSetting(wxuserId);
		String aiModel = MjkjUtils.getMap2Str(wxUserSetting,"ai_model");
		Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
		Integer rl = MjkjUtils.getMap2Integer(wxuserMap,"rl_cou");
		Integer useRl = this.judgeModel(aiModel);
		if( rl < useRl ){
			aiModel = ChatgptConfig.getChatgptModel();
			Map<String,Object> updateMap = new HashMap<>();
			updateMap.put("ai_model",aiModel);
			baseSqlService.baseUpdateDataWhere("chat_wxuser_setting",updateMap,"wxuser_id",wxuserId);
			useRl = 0;
		}
		//需要减掉次数
		if( ( useRl>0 ) && ( (rl> useRl)|| (rl == useRl) )) {
			this.subWxuserQuestionNum(bladeUserId,wxuserId,9,useRl,null,"指令库->消耗燃料"+ funName,"rl");
			}//扣除燃料次数
		else {
			this.subWxuserQuestionNum(bladeUserId, wxuserId, 9, useNum, null, "指令库->消耗积分" + funName,"question");
			}//扣除次数
		Date requestTime = DateUtil.now();//请求时间
		//敏感词
		String result = "";
		String id = IdWorker.getIdStr();
			List<MessageModel> messagesList = new ArrayList<>();
			MessageModel sysModel = new MessageModel();
			sysModel.setRole(MessageModelRoleType.SYSTEM);
			sysModel.setContent(systemTitle);
			messagesList.add(sysModel);//封装参数

			MessageModel userModel = new MessageModel();
			userModel.setRole(MessageModelRoleType.USER);
			userModel.setContent(content);
			messagesList.add(userModel);//封装参数

			if ( Func.isEmpty( aiModel)){
				aiModel  = "ernie-bot-turbo";
			}

			result = chatGPTService.sendNowTimeChatGptTurboMessage(messagesList , aiModel);




		Date responseTime = DateUtil.now();//响应时间

//		String id = IdWorker.getIdStr();
		Map<String, Object> addLogMap = new HashMap<>();
		addLogMap.put("id", id);
		addLogMap.put("wxuser_id", wxuserId);
		addLogMap.put("fun_data_id", param.getFunFataId());
		addLogMap.put("fun_name", funName);
		addLogMap.put("system_title", systemTitle);
		addLogMap.put("message_q", content);
		addLogMap.put("message_a", result);
		addLogMap.put("message_q_time", requestTime);
		addLogMap.put("message_a_time", responseTime);
		addLogMap.put("param_json", param.getParamJson());
		addLogMap.put("store_status",1);
		if (Func.isNotEmpty(param.getChat_list_id())){
			addLogMap.put("chat_list_id",param.getChat_list_id());
		}
		baseSqlService.baseInsertData("chat_log_message_fun", addLogMap);
		//存储到redis
		String redisKey = MjkjUtils.getRedisKeyMoreFun(wxuserId, param.getFunFataId());
		redisUtil.set(redisKey, addLogMap);


		String chatCode = MjkjUtils.getMap2Str(wxuserMap, "chat_code");

	}

	//签到
	@Override
	public void sign(String wxuserId, Date date) {
		Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
		String wx_name = MjkjUtils.getMap2Str(wxuserMap, "wx_name");
		Long bladeUserId = MjkjUtils.getMap2Long(wxuserMap, "blade_user_id");
		//校验最近7天是否签到
		String week = MjkjUtils.getWeek(date);
		Integer awardCou = 0;

		if (Func.equals(week, "星期日")) {
			//如果是周日，要检验前面6次是否连续签到
			boolean lxFlag = true;
			for (int i = 1; i < 7; i++) {
				Date date1 = DateUtil.plusDays(date, -i);
				String redisKeySign = MjkjUtils.getRedisKeySign(bladeUserId, date1);
				if (!redisUtil.hasKey(redisKeySign)) {
					lxFlag = false;
					break;
				}
			}
			if (lxFlag) {//连续
				String val = this.getCsszVal("sign_week_cou", "0");//1
				awardCou = Func.toInt(val);//每天奖励次数
			} else {
				String val = this.getCsszVal("sign_day_cou", "0");//1
				awardCou = Func.toInt(val);//每天奖励次数
			}
		} else {
			String val = this.getCsszVal("sign_day_cou", "0");//1
			awardCou = Func.toInt(val);//每天奖励次数
		}

		//进行签到
		Map<String, Object> addMap = new HashMap<>();
		addMap.put("wxuser_id", wxuserId);
		addMap.put("wxuser_name", wx_name);
		addMap.put("award_cou", awardCou);
		addMap.put("sign_time", date);
		baseSqlService.baseInsertData("chat_log_sign", addMap);

		this.addWxuserQuestionNum(bladeUserId, wxuserId, 7, awardCou, null, "签到奖励积分","question");

		//写入到缓存
		String redisKey = MjkjUtils.getRedisKeySign(bladeUserId, date);
		redisUtil.set(redisKey, awardCou);
	}



	//获取下级要求数量
	public IPage<Map<String, Object>> getSubCouList(String wxuserId, IPage<Object> page) {
		return webMapper.getSubCouList(wxuserId, page);
	}


	//判断模型是否需要燃料
	@Override
	public Integer judgeModel(String modelName) {
		Map<String,Object> modelMap = baseSqlService.getDataOneByField("chat_model","mx_lx",modelName);
		if (Func.isNotEmpty(modelMap)) {
			Integer isUseRl = MjkjUtils.getMap2Integer(modelMap,"is_use_rl");
			if (isUseRl == 0){
				return 0;
			}
			else {
				return MjkjUtils.getMap2Integer(modelMap,"use_num");
			}
		}
		return 0;
	}

	//获取收藏的消息
	@Override
	public  IPage<Map<String, Object>>  getStoreMessage(IPage<Object> page){
		String wxuserId = this.getWxuserId();
		//获取总数
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("wxuser_id", wxuserId);
		totalWrapper.eq("store_status",0);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.select("count(1) as cou");
		Map<String, Object> totalMap = baseSqlService.getDataOneByFieldParams("chat_store", totalWrapper);
		Long totalCou = MjkjUtils.getMap2Long(totalMap, "cou");

		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.eq("is_deleted", 0);
		wrapper.eq("store_status",0);
		wrapper.orderByDesc("id+0");
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_store", page, wrapper);
//		List<Map<String, Object>> records = pages.getRecords();

		pages.setTotal(totalCou);//重置总数
		return pages;
	}

	//获取聊天列表
	@Override
	public  IPage<Map<String, Object>>  getChatList(IPage<Object> page){
		String userId = this.getWxuserId();
		//获取总数
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("wxuser_id", userId);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.orderByDesc("id");
		totalWrapper.select("count(*) as cou");

		Map<String,Object> totalMap =  baseSqlService.getDataOneByFieldParams("chat_list", totalWrapper);
		Integer totalCou = MjkjUtils.getMap2Integer(totalMap,"cou");


		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("wxuser_id", userId);
		wrapper.eq("is_deleted",0);
		wrapper.orderByDesc("id");
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_list", page, wrapper);

		pages.setTotal(totalCou);//重置总数
		return pages;
	}

	//新建聊天列表
	@Override
	public void createNewChatList(Long id,String type,String name,String content,String fundataId,String funJson){
		if (id==0L){
			return;
		}
		Map<String, Object> chatList = baseSqlService.getTableById("chat_list", String.valueOf(id));
		if(Func.isNotEmpty(chatList)){
			return;
		}
		Map<String,Object> insertMap = new HashMap<>();
		insertMap.put("id",id);
		insertMap.put("chat_type",type);
		insertMap.put("chat_name",name);
		insertMap.put("chat_content",content);
		String wxuserId = this.getWxuserId();
		insertMap.put("wxuser_id",wxuserId);
		insertMap.put("fundata_id",fundataId);
		insertMap.put("fun_json",funJson);
		baseSqlService.baseInsertData("chat_list", insertMap);

	}

	@Override
	public IPage<Map<String,Object>> getCreditList(IPage<Object> page){
		String wxuserId = this.getWxuserId();
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("wxuser_id", wxuserId);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.select("count(*) as cou");

		Map<String,Object> totalMap =  baseSqlService.getDataOneByFieldParams("chat_log_num", totalWrapper);
		Integer totalCou = MjkjUtils.getMap2Integer(totalMap,"cou");

		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.eq("is_deleted",0);
		wrapper.orderByDesc("id");
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_log_num", page, wrapper);
		pages.setTotal(totalCou);//重置总数
		return pages;

	}

	@Override
	public IPage<Map<String,Object>> getAddCreditList(IPage<Object> page){
		String wxuserId = this.getWxuserId();
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("wxuser_id", wxuserId);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.eq("type","ADD");
		totalWrapper.select("count(*) as cou");

		Map<String,Object> totalMap =  baseSqlService.getDataOneByFieldParams("chat_log_num", totalWrapper);
		Integer totalCou = MjkjUtils.getMap2Integer(totalMap,"cou");

		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.eq("is_deleted",0);
		wrapper.eq("type","ADD");
		wrapper.orderByDesc("id");
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_log_num", page, wrapper);
		pages.setTotal(totalCou);//重置总数
		return pages;

	}

	@Override
	public IPage<Map<String,Object>> getSubCreditList(IPage<Object> page){
		String wxuserId = this.getWxuserId();
//		String wxuserId = "1692058205276446721";
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("wxuser_id", wxuserId);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.eq("type","SUB");
		totalWrapper.select("count(*) as cou");

		Map<String,Object> totalMap =  baseSqlService.getDataOneByFieldParams("chat_log_num", totalWrapper);
		Integer totalCou = MjkjUtils.getMap2Integer(totalMap,"cou");

		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.eq("is_deleted",0);
		wrapper.eq("type","SUB");
		wrapper.orderByDesc("id");
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_log_num", page, wrapper);
		pages.setTotal(totalCou);//重置总数
		return pages;

	}

	@Override
	@Transactional(rollbackFor = Exception.class )
	public String chatStore(String messageId,String type){
		String wxuserId  = this.getWxuserId();
		if( Func.equals(type,"chat")){
			Map<String, Object> chatLogMessage = baseSqlService.getTableById("chat_log_message", messageId);
			String chatSotreId = MjkjUtils.getMap2Str(chatLogMessage,"chat_store_id");
			if ((!Func.equals(chatSotreId,"0"))&&(Func.isNotEmpty(chatSotreId))){
				Map<String, Object> chatStore = baseSqlService.getTableById("chat_store", chatSotreId);
				Integer storeStatus = MjkjUtils.getMap2Integer(chatStore,"store_status");
				storeStatus = storeStatus==0?1:0;
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("store_status",storeStatus);
				Map<String,Object> logMessagedateMap = new HashMap<>();
				logMessagedateMap.put("store_status",storeStatus);
				baseSqlService.baseUpdateData("chat_store",updateMap,chatSotreId);
				baseSqlService.baseUpdateData("chat_log_message",logMessagedateMap,messageId);
				return storeStatus.toString();
			}else {
				//插入收藏记录表
				chatSotreId = IdWorker.getIdStr();
				String chatType = "chat";
				Integer storeStatus = 0;
				String storeContent = MjkjUtils.getMap2Str(chatLogMessage,"message_content");
				Map<String,Object> insertMap = new HashMap<>();
				insertMap.put("chat_type",chatType);
				insertMap.put("chat_content",storeContent);
				insertMap.put("store_status",storeStatus);
				insertMap.put("wxuser_id",wxuserId);
				insertMap.put("id",chatSotreId);
				insertMap.put("message_id",messageId);
				insertMap.put("message_type","chat");
				baseSqlService.baseInsertData("chat_store",insertMap);
				//更新聊天记录表
				Map<String,Object> updateMap  = new HashMap<>();
				updateMap.put("chat_store_id",chatSotreId);
				updateMap.put("store_status",storeStatus);
				baseSqlService.baseUpdateData("chat_log_message",updateMap,messageId);
				return  storeStatus.toString();
			}
		}else {
			Map<String, Object> chatLogMessageFun = baseSqlService.getTableById("chat_log_message_fun", messageId);
			String chatSotreId = MjkjUtils.getMap2Str(chatLogMessageFun,"chat_store_id");
			if ((!Func.equals("0",chatSotreId)) && (Func.isNotEmpty(chatSotreId))){
				Map<String, Object> chatStore = baseSqlService.getTableById("chat_store", chatSotreId);
				Integer storeStatus = MjkjUtils.getMap2Integer(chatStore,"store_status");
				storeStatus = storeStatus==0?1:0;
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("store_status",storeStatus);
				Map<String,Object> logMessagedateMap = new HashMap<>();
				logMessagedateMap.put("store_status",storeStatus);
				baseSqlService.baseUpdateData("chat_store",updateMap,chatSotreId);
				baseSqlService.baseUpdateData("chat_log_message",logMessagedateMap,messageId);
				return storeStatus.toString();
			}
			else {
				//插入收藏记录表
				chatSotreId = IdWorker.getIdStr();
				String chatType = "tools";
				Integer storeStatus = 0;
				String storeContent = MjkjUtils.getMap2Str(chatLogMessageFun,"message_a");
				Map<String,Object> insertMap = new HashMap<>();
				insertMap.put("chat_type",chatType);
				insertMap.put("chat_content",storeContent);
				insertMap.put("store_status",storeStatus);
				insertMap.put("wxuser_id",wxuserId);
				insertMap.put("id",chatSotreId);
				insertMap.put("message_id",messageId);
				insertMap.put("message_type","tools");
				baseSqlService.baseInsertData("chat_store",insertMap);
				//更新聊天记录表
				Map<String,Object> updateMap  = new HashMap<>();
				updateMap.put("chat_store_id",chatSotreId);
				updateMap.put("store_status",storeStatus);

				baseSqlService.baseUpdateData("chat_log_message_fun",updateMap,messageId);

				return  storeStatus.toString();
			}
		}


	}

	@Override
	public IPage<Map<String,Object>> getInvitedUsers(IPage<Object> page){
		String wxuserId = this.getWxuserId();
//		String wxuserId = "1628312273179992066";
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("pid", wxuserId);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.select("count(*) as cou");

		Map<String,Object> totalMap =  baseSqlService.getDataOneByFieldParams("chat_wxuser", totalWrapper);
		Integer totalCou = MjkjUtils.getMap2Integer(totalMap,"cou");

		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("pid", wxuserId);
		wrapper.eq("is_deleted",0);
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_wxuser", page, wrapper);
		List<Map<String, Object>> records = pages.getRecords();
		if (Func.isNotEmpty(records)) {
			Collections.reverse(records);
			pages.setRecords(records);
		}
		pages.setTotal(totalCou);//重置总数
		return pages;
	}

	@Override
	public IPage<Map<String,Object>> getFunHistory(IPage<Object> page,String funDataId){
		String wxuserId = this.getWxuserId();
//		String wxuserId = "1628312273179992066";
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("wxuser_id", wxuserId);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.eq("fun_data_id",funDataId);
		totalWrapper.select("count(*) as cou");

		Map<String,Object> totalMap =  baseSqlService.getDataOneByFieldParams("chat_log_message_fun", totalWrapper);
		Integer totalCou = MjkjUtils.getMap2Integer(totalMap,"cou");

		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.eq("is_deleted",0);
		wrapper.eq("fun_data_id",funDataId);
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_log_message_fun", page, wrapper);
		pages.setTotal(totalCou);//重置总数
		return pages;
	}




}

