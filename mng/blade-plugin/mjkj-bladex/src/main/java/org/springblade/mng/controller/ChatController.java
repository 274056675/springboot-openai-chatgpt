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
package org.springblade.mng.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.constant.ChatgptConfig;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.model.ChatGptMsgModel;
import org.springblade.mng.model.MessageModel;
import org.springblade.mng.model.MessageModelRoleType;
import org.springblade.mng.model.WxUserInfoModel;
import org.springblade.mng.param.*;
import org.springblade.mng.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.*;



@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("chat/ai")
@Api(value = "超级AI智能助手开放接口", tags = "超级AI智能助手开放接口")
public class ChatController {

	@Autowired
	private IWebService webService;

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IChatGPTService chatGPTService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IMngMessageService mngMessageService;



	@ApiOperationSupport(order = 1)
	@PostMapping({"/del/wxuser"})
	@ApiOperation(value = "注销用户", notes = "注销用户")
	public R delWxuser() {
		String wxuserId = webService.getWxuserId();
		webService.delWuserInfo(wxuserId);
		return R.data("成功");
	}

	@ApiOperationSupport(order = 1)
	@GetMapping({"/getWxUserInfo"})
	@ApiOperation(value = "获取用户详情", notes = "获取用户详情")
	public R getWxUserInfo() {
		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		return R.data(wxUsrInfo);
	}


	@ApiOperationSupport(order = 3)
	@PostMapping({"/send/question"})
	@ApiOperation(value = "发送问题", notes = "发送问题")
	public R sendQuestion(@RequestBody QuestionParam param) {
		if (Func.isEmpty(param) || Func.isEmpty(param.getQuestion())) {
			return R.fail("问题不允许为空");
		}
		String startMessageId = param.getStartMessageId();//上下文id
		Long startMessageIdL = Func.isEmpty(startMessageId) ? 0L : Func.toLong(startMessageId);
		//校验次数-------开始----------
		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		String type = param.getType();
		String chatListId = "";
		if(Func.equals(type,"pc")){
			chatListId = Func.isEmpty(param.getChatListId()) ? IdWorker.getIdStr():param.getChatListId();
		}

		boolean memberFlag = wxUsrInfo.isMemberFlag();
		Integer useNum=0;
		Integer rlcou = wxUsrInfo.getRl_cou();
		if (!memberFlag) {//当前不是会员 有开启消耗次数
			useNum = webService.getOneQuestionUseNum();//一次消耗多少次
			Integer wuserQuestionCou = webService.getWuserQuestionCou(AuthUtil.getUserId(),"question");//用户总次数
			if (useNum.intValue() > wuserQuestionCou.intValue() && rlcou==0) {
				return R.fail("可提问次数不足,可通过分享增加可用次数");
			}
		}

		Long chatListIdL = Func.toLong(chatListId);

		//校验次数-------结束----------
		//新建聊天列表
		Date now = DateUtil.now();
		String dateNow = DateUtil.format(now, "yyyy-MM-dd");
		if ( Func.isEmpty(param.getChatListId()) && Func.equals(param.getType(),"pc") ){
			webService.createNewChatList(chatListIdL,"chat",dateNow+" AI问答", param.getQuestion(),"","");
		}
		if(Func.isNotEmpty(param.getChatListId())){
			Map<String,Object> updateMap = new HashMap<>();
			updateMap.put("chat_content",param.getQuestion());
			baseSqlService.baseUpdateData("chat_list",updateMap,param.getChatListId());
		}
		// 创建 HTTP 请求对
		String question = param.getQuestion();
		List<ChatGptMsgModel> list = webService.sendQuestion(question, startMessageIdL, useNum, param.getText_type(),chatListIdL);
		if(Func.isNotEmpty(list)){
			ChatGptMsgModel chatGptMsgModel = list.get(0);
			chatGptMsgModel.setUuid(param.getUuid());
		}

		System.gc();//手动回收垃圾
		return R.data(list);
	}


	@ApiOperationSupport(order = 4)
	@PostMapping({"/message/last"})
	@ApiOperation(value = "获取我的最新消息", notes = "获取我的历史消息")
	public R getMessageLastList(@RequestBody MessageHistoryParam param) {
		Long startNum = param.getStartNum();
		if (Func.isEmpty(startNum)) {
			return R.fail("参数为空");
		}
		String modelType = param.getModelType();
		if (Func.isEmpty(modelType)) {
			modelType = "0";
		}
		String type = param.getType();
		String chatListId = param.getChatListId();
		List<Map<String, Object>> dataList = webService.getMessageLastList(startNum, modelType,type,chatListId);
		return R.data(dataList);
	}

	/**
	 * 第一个动作，微信勾选后存到数据库chat_log_share_message表中
	 *
	 * @param param
	 * @return
	 */


	@ApiOperationSupport(order = 5)
	@PostMapping({"/message/history"})
	@ApiOperation(value = "获取我的历史消息", notes = "获取我的历史消息")
	public R getMessageHistoryList(@RequestBody MessageHistoryParam param) {
		Long startNum = param.getStartNum();
		String modelType = param.getModelType();
		String chatListId = param.getChatListId();
		String type = param.getType();
		if (Func.isEmpty(modelType)) {
			modelType = "0";
		}

		IPage<Object> page = Condition.getPage(param);
		IPage<Map<String, Object>> pages = webService.getMessageHistoryList(startNum, modelType, page, chatListId,type);
		return R.data(pages);
	}


	@ApiOperationSupport(order = 6)
	@PostMapping({"/update/wuserInfo"})
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	public R updateWuserInfo(@RequestBody WuserInfoUpdateParam param) {
		String wxName = param.getWxName();
		String wxAvatar = param.getWxAvatar();
		String phone = param.getPhone();
		String sign = param.getSign();
		if (Func.isEmpty(wxName) && Func.isEmpty(wxAvatar) && Func.isEmpty(phone) && Func.isEmpty(sign)) {
			return R.fail("参数不允许为空");
		}
		String wxuserId = webService.getWxuserId();
		Map<String, Object> updateMap = new HashMap<>();
		if (Func.isNotEmpty(wxName)) {
			updateMap.put("wx_name", wxName);
			WxUserInfoModel oldWxUsrInfo = webService.getWxUsrInfo();
			String oldWxName = oldWxUsrInfo.getWxName();
			if (Func.isEmpty(oldWxName) || !Func.equals(oldWxName, wxName)) {//名称已改变
				updateMap.put("poster_url", "");//需要重新生成海报
			}
		}
		if (Func.isNotEmpty(wxAvatar)) {
			updateMap.put("wx_avatar", wxAvatar);
		}
		if (Func.isNotEmpty(phone)) {
			if(phone.length()!=11){
				return R.fail("请检查手机号");
			}
			String code  = param.getCode();
			String redisKeySms = "SMS_PHONE:" + phone;
			if (!redisUtil.hasKey(redisKeySms)) {
				return R.fail("验证码已过期");
			}
			String redisCode = (String) redisUtil.get(redisKeySms);
			if (!Func.equals(code, redisCode)) {
				return R.fail("验证码不正确");
			}
			redisUtil.del(redisKeySms);

			updateMap.put("phone", phone);
		}
		if (Func.isNotEmpty(sign)) {
			updateMap.put("sign",sign);
		}


		baseSqlService.baseUpdateData("chat_wxuser", updateMap, wxuserId);

		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		return R.data(wxUsrInfo);
	}


	@ApiOperation("获取我的下级用户")
	@ApiOperationSupport(order = 9)
	@PostMapping(value = "/get/subWxUser")
	public R getSubWxUser(@RequestBody WebQueryParam param) {
		String wxuserId = param.getWxuserId();
		if (Func.isEmpty(wxuserId)) {
			//如果传参过来有wxuserId则使用传参的，如果没有就使用下面的
			wxuserId = webService.getWxuserId();
		}
		IPage<Object> page = Condition.getPage(param);//这里的作用主要就是传入了当前页码和页数，然后返回一个page对象
		IPage<Map<String, Object>> pages = webService.getSubCouList(wxuserId, page);

		//数据脱敏
		List<Map<String, Object>> dataMapList = pages.getRecords();
		if (Func.isNotEmpty(dataMapList)) {
			for (Map<String, Object> dataMap : dataMapList) {
				String wxName = MjkjUtils.getMap2Str(dataMap, "wx_name");
				String cou = MjkjUtils.getMap2Str(dataMap, "cou");
				wxName = MjkjUtils.desensitizeStr(wxName, "匿名用户");
				dataMap.put("wx_name", wxName);
				dataMap.put("view_sub_member", cou);//下级
			}
		}

		return R.data(pages);
	}

	@ApiOperation("翻译")
	@ApiOperationSupport(order = 10)
	@PostMapping(value = "/translate")
	public R translate(String messageId, @RequestBody TargetLangModel paramModel) {
		if (Func.isEmpty(messageId)) {
			return R.fail("翻译失败");
		}
		Date now = DateUtil.now();

		String wxuserId = webService.getWxuserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.eq("id", messageId);
		wrapper.select("message_content");
		Map<String, Object> messageMap = baseSqlService.getDataOneByFieldParams("chat_log_message", wrapper);
		if (Func.isEmpty(messageMap)) {
			return R.data("翻译失败");
		}
		String messageContent = MjkjUtils.getMap2Str(messageMap, "message_content");
		if (Func.isEmpty(messageContent)) {
			return R.data("翻译失败");
		}
		//获取个人设置
//		Map<String, Object> settingMap = webService.getWxUserSetting(wxuserId);
		//String targetLang = MjkjUtils.getMap2Str(settingMap, "translate_lang");
		String targetLang = paramModel.getTargetLang();
		if (Func.isEmpty(targetLang)) {
			targetLang = "英文";
		}

//		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_wxuser_setting","wxuser_id",wxuserId);
//		String aiModel = MjkjUtils.getMap2Str(settingMap,"ai_model");
		String aiModel = "gpt-3.5-turbo-16k";
		//先从数据库里面获取，没有的话，则到ai那边获取
		QueryWrapper<Object> translateWrapper = new QueryWrapper<>();
		translateWrapper.eq("is_deleted", 0);
		translateWrapper.eq("wxuser_id", wxuserId);
		translateWrapper.eq("target_lang", targetLang);
		translateWrapper.eq("log_message_id", messageId);
		Map<String, Object> translateMap = baseSqlService.getDataOneByFieldParams("chat_log_translate", translateWrapper);
		if (Func.isNotEmpty(translateMap)) {//存在缓存
			translateMap.remove("translate_before");
			return R.data(translateMap);
		}

		List<MessageModel> messagesList = new ArrayList<>();
		MessageModel model0 = new MessageModel();
		model0.setRole(MessageModelRoleType.SYSTEM);
		model0.setContent("角色扮演，你现在是翻译大师，我需要你协助我完成如下工作。");
		messagesList.add(model0);//封装参数

		MessageModel model = new MessageModel();
		model.setRole(MessageModelRoleType.USER);
		model.setContent(messageContent);
		messagesList.add(model);//封装参数

		MessageModel model2 = new MessageModel();
		model2.setRole(MessageModelRoleType.USER);
		model2.setContent("将上面一段话翻译成：" + targetLang);
		messagesList.add(model2);//封装参数
		//开始翻译，实时，不走异步

		String result = chatGPTService.sendNowTimeChatGptTurboMessage(messagesList,aiModel);


		//插入到数据库
		if (Func.isNotEmpty(result)) {
			Map<String, Object> logMap = new HashMap<>();
			logMap.put("wxuser_id", wxuserId);
			logMap.put("target_lang", targetLang);//目标语言
			logMap.put("log_message_id", messageId);
			logMap.put("translate_before", messageContent);
			logMap.put("translate_after", result);
			logMap.put("translate_time", now);
			baseSqlService.baseInsertData("chat_log_translate", logMap);
			return R.data(logMap);
		}
		return R.fail("翻译失败");
	}

	@ApiOperation("点赞热门消息（新版本丢弃）")
	@ApiOperationSupport(order = 10)
	@PostMapping(value = "/hot/saveStar")
	public R hotSaveStar(String messageId) {
		if (Func.isEmpty(messageId)) {
			return R.fail("点赞失败");
		}
		String wxuserId = webService.getWxuserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.eq("log_message_id", messageId);
		Map<String, Object> messageMap = baseSqlService.getDataOneByFieldParams("chat_hot_message_star", wrapper);
		if (Func.isNotEmpty(messageMap)) {
			return R.data("成功");
		}
		Date now = DateUtil.now();

		Map<String, Object> addMap = new HashMap<>();
		addMap.put("log_message_id", messageId);
		addMap.put("wxuser_id", wxuserId);
		addMap.put("star_time", now);
		baseSqlService.baseInsertData("chat_hot_message_star", addMap);

		return R.data("成功");
	}


	@ApiOperationSupport(order = 12)
	@GetMapping({"/getWxUserSetting"})
	@ApiOperation(value = "获取用户自定义设置", notes = "获取用户自定义设置")
	public R getWxUserSetting() {
		String wxuserId = webService.getWxuserId();
		Long userId = AuthUtil.getUserId();
		//获取个人设置
		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_wxuser_setting", "wxuser_id", wxuserId);
		String aiModel = ChatgptConfig.getChatgptModel();
		String imageModel = ChatgptConfig.getImageModel();
		if (Func.isEmpty(settingMap)) {
			settingMap =  new HashMap<>();
			settingMap.put("id", IdWorker.getIdStr());
			settingMap.put("wxuser_id", wxuserId);
			settingMap.put("blade_user_id", userId);
			settingMap.put("translate_lang", "中文");
			settingMap.put("ai_model",aiModel);
			settingMap.put("image_model",imageModel);
			baseSqlService.baseInsertData("chat_wxuser_setting", settingMap);
		}
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.select("mx_lx");
		queryWrapper.ne("model_status",1);
		List<Map<String,Object>> modellist = baseSqlService.getDataListByFieldParams("chat_model", queryWrapper);
		for (int i = 0; i < modellist.size(); i++) {
			Map<String,Object> map= modellist.get(i);
			String mxLx = MjkjUtils.getMap2Str(map, "mx_lx");
			if (Func.equals(mxLx,MjkjUtils.getMap2Str(settingMap, "ai_model"))){
				settingMap.replace("ai_model",aiModel);
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("ai_model",aiModel);
				baseSqlService.baseUpdateDataWhere("chat_wxuser_setting", updateMap, "wxuser_id", wxuserId);
			}
			if (Func.equals(mxLx,MjkjUtils.getMap2Str(settingMap, "image_model"))){
				settingMap.replace("image_model",imageModel);
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("image_model",imageModel);
				baseSqlService.baseUpdateDataWhere("chat_wxuser_setting", updateMap, "wxuser_id", wxuserId);
			}
		}

		Integer chatRl = webService.judgeModel(MjkjUtils.getMap2Str(settingMap, "ai_model"));
		Integer imageRl = webService.judgeModel(MjkjUtils.getMap2Str(settingMap, "image_model"));
		settingMap.put("chatUseRl",chatRl>0?1:0);
		settingMap.put("imageUseRl",imageRl>0?1:0);
		return R.data(settingMap);
	}


	@ApiOperationSupport(order = 13)
	@PostMapping({"/update/wuserSetting"})
	@ApiOperation(value = "修改用户自定义设置", notes = "修改用户信息")
	public R updateWuserSetting(@RequestBody WuserInfoUpdateParam param) {
		String translateLang = param.getTranslateLang();//默认翻译语言
		String withdrawalQrcode = param.getWithdrawalQrcode();
		String aiModel = param.getAiModel();
		String imgaeModel = param.getImageModel();
		if (Func.isEmpty(translateLang) && Func.isEmpty(withdrawalQrcode) && Func.isEmpty(aiModel) && Func.isEmpty(imgaeModel)) {
			return R.fail("参数不允许为空");
		}
		String wxuserId = webService.getWxuserId();
		Map<String, Object> updateMap = new HashMap<>();
		if (Func.isNotEmpty(translateLang)) {
			updateMap.put("translate_lang", translateLang);
		}
		if (Func.isNotEmpty(withdrawalQrcode)) {
			updateMap.put("withdrawal_qrcode", withdrawalQrcode);
		}
		if(Func.isNotEmpty(aiModel)) {
			updateMap.put("ai_model", aiModel);
		}
		if(Func.isNotEmpty(imgaeModel)) {
			updateMap.put("image_model", imgaeModel );
		}
		baseSqlService.baseUpdateDataWhere("chat_wxuser_setting", updateMap, "wxuser_id", wxuserId);

		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		return R.data(wxUsrInfo);
	}

	@ApiOperationSupport(order = 14)
	@PostMapping({"/send/moreFun"})
	@ApiOperation(value = "发送更多好玩", notes = "发送更多好玩")
	public R sendMoreFun(@RequestBody MoreFunParam param) {
		String systemTitle = param.getSystemTitle();
		String content = param.getContent();
		if (Func.isEmpty(systemTitle)) {
			return R.fail("参数有误");
		}

		if (Func.isEmpty(content)) {
			return R.fail("内容为空");
		}
		String wxuserId = webService.getWxuserId();
		//判断是否是手机端  手机端没有做聊天列表给他设置默认聊天id

		//校验次数-------开始----------
		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		boolean memberFlag = wxUsrInfo.isMemberFlag();
		Integer rlCou =wxUsrInfo.getRl_cou();
		Integer useNum=0;
		if (!memberFlag ) {//当前不是会员 有开启消耗次数
			useNum = webService.getOneQuestionUseNum();//一次消耗多少次
			Integer wuserQuestionCou = webService.getWuserQuestionCou(AuthUtil.getUserId(),"question");//用户总次数
			if (useNum.intValue() > wuserQuestionCou.intValue() && rlCou==0 )  {
				return R.fail("可提问积分不足");
			}
		}
		if(Func.equals(param.getType(),"pc")&&Func.isEmpty(param.getChat_list_id())){
			QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("fundata_id",param.getFunFataId());
			queryWrapper.eq("is_deleted",0);
			queryWrapper.eq("wxuser_id",wxuserId);
			Map<String, Object> chatList = baseSqlService.getDataOneByFieldParams("chat_list", queryWrapper);
			if (Func.isEmpty(chatList)){
				param.setChat_list_id(IdWorker.getIdStr());
				Map<String, Object> chatGjglFunData = baseSqlService.getTableById("chat_gjgl_fun_data", param.getFunFataId());
				String funJson = MjkjUtils.getMap2Str(chatGjglFunData, "fun_json");
				String funName = MjkjUtils.getMap2Str(chatGjglFunData, "fun_name");
				Date now = DateUtil.now();
				String dateNow = DateUtil.format(now, "yyyy-MM-dd");
				webService.createNewChatList(Func.toLong(param.getChat_list_id()),"tools",dateNow+" "+funName,param.getContent(),param.getFunFataId(),funJson);
			}else {
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("chat_content",param.getContent());
				baseSqlService.baseUpdateData("chat_list",updateMap,MjkjUtils.getMap2Str(chatList,"id"));
			}

		}
		//校验次数-------结束----------
		Long userId = AuthUtil.getUserId();
		webService.sendMoreFun(wxuserId, userId, useNum, param);
		return R.data("成功");
	}

	@ApiOperationSupport(order = 15)
	@GetMapping({"/get/lastMoreFunLog"})
	@ApiOperation(value = "获取上一次更多的好玩记录", notes = "获取上一次更多的好玩")
	public R getLastMoreFunLog(String funFataId) {
		String wxuserId = webService.getWxuserId();
		String redisKey = MjkjUtils.getRedisKeyMoreFun(wxuserId, funFataId);
		if (redisUtil.hasKey(redisKey)) {
			QueryWrapper<Object> qw = new QueryWrapper<>();
			qw.eq("fun_data_id",funFataId);
			qw.eq("wxuser_id",wxuserId);
			qw.isNotNull("message_a");
			qw.orderByDesc("id");
			qw.eq("is_deleted",0);
			qw.last("limit 1");
			Map<String, Object> chatMessageFun = baseSqlService.getDataOneByFieldParams("chat_log_message_fun", qw);
			return R.data(chatMessageFun);
		}

		return R.data(null);
	}

	@ApiOperationSupport(order = 16)
	@PostMapping({"/sign"})
	@ApiOperation(value = "签到", notes = "签到")
	public R sign() {
		Long userId = AuthUtil.getUserId();
		Date now = DateUtil.now();

		String redisKeySign = MjkjUtils.getRedisKeySign(userId, now);
		if (redisUtil.hasKey(redisKeySign)) {
			return R.fail("签到失败，您今天已经签到过了");
		}
		String wxuserId = webService.getWxuserId();
		webService.sign(wxuserId, now);
		return R.data("成功");
	}


	@ApiOperationSupport(order = 17)
	@GetMapping({"/get/signCou"})
	@ApiOperation(value = "获取我的签到总数", notes = "获取我的签到总数")
	public R bladeUserId() {
		Map<String, Object> resultMap = new HashMap<>();
		Long userId = AuthUtil.getUserId();
		List<String> dateWeek = MjkjUtils.getDateWeek();
		int week = 0;
		//最近一周
		for (String yyyyMMdd : dateWeek) {
			week++;
			String redisKey = "WXUSER:SIGN:" + userId + ":yyyyMMdd_" + yyyyMMdd;
			int cou = 0;
			if (redisUtil.hasKey(redisKey)) {
				cou = 1;
			}
			resultMap.put("week" + week, cou);
		}

		//获取总数
		String wxuserId = webService.getWxuserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("wxuser_id", wxuserId);
		wrapper.select("count(id) as cou");
		Map<String, Object> signMap = baseSqlService.getDataOneByFieldParams("chat_log_sign", wrapper);
		String cou = MjkjUtils.getMap2Str(signMap, "cou");
		if (Func.isEmpty(cou)) {
			cou = "0";
		}
		resultMap.put("cou", cou);
		return R.data(resultMap);
	}










	@ApiOperationSupport(order = 26)
	@GetMapping(value = "/store")
	@ApiOperation(value = "收藏", notes = "收藏")
	public R store(String messageId,String type){
		String store = webService.chatStore(messageId,type);
		return R.data(store);
	}

	@ApiOperationSupport(order = 27)
	@PostMapping(value = "/store/list")
	@ApiOperation(value = "收藏列表", notes ="收藏列表")
	public R storelist(@RequestBody MessageHistoryParam param) {
		IPage<Object> page = Condition.getPage(param);
		IPage<Map<String, Object>> pages = webService.getStoreMessage( page);
		return R.data(pages);
	}

	@ApiOperationSupport(order = 28)
	@PostMapping(value = "/get/chat")
	@ApiOperation(value = "获取聊天列表", notes ="获取聊天列表")
	public R getChatList(@RequestBody Query param) {
		IPage<Object> page = Condition.getPage(param);
		IPage<Map<String, Object>> pages = webService.getChatList(page);
		return R.data(pages);
	}


	@ApiOperationSupport(order = 29)
	@GetMapping(value = "/delete/chat")
	@ApiOperation(value = "删除聊天列表" , notes = "删除聊天列表")
	public R deleteChat(String chatListId){
		if (Func.isEmpty(chatListId)){
			return R.data("聊天列表id为空");
		}
		Map<String, Object> chatList = baseSqlService.getTableById("chat_list", chatListId);
		baseSqlService.baseDeleteSqlStr("chat_list",chatListId);
		String chatType = MjkjUtils.getMap2Str(chatList, "chat_type");
		if(Func.equals(chatType,"chat")){
			QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("chat_list_id",chatListId);
			List<Map<String, Object>> messages = baseSqlService.getDataListByFieldParams("chat_log_message", queryWrapper);
			//批量删除
			for (Map<String, Object> dataMap : messages) {
				String id = MjkjUtils.getMap2Str(dataMap, "id");
				baseSqlService.baseDeleteSqlStr("chat_log_message", id);
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("is_deleted",-1);
				baseSqlService.baseUpdateDataWhere("chat_store",updateMap,"message_id",id);
			}
		}
		if(Func.equals(chatType,"tools")){
			String wxuserId = webService.getWxuserId();
			QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("chat_list_id",chatListId);
			List<Map<String, Object>> messages = baseSqlService.getDataListByFieldParams("chat_log_message_fun", queryWrapper);
			for (Map<String, Object> dataMap : messages) {
				String id = MjkjUtils.getMap2Str(dataMap, "id");
				String funFataId = MjkjUtils.getMap2Str(dataMap,"fun_data_id");
				baseSqlService.baseDeleteSqlStr("chat_log_message_fun", id);
				String redisKey = MjkjUtils.getRedisKeyMoreFun(wxuserId, funFataId);
				redisUtil.del(redisKey);
				Map<String,Object> updateMap = new HashMap<>();
				updateMap.put("is_deleted",-1);
				baseSqlService.baseUpdateDataWhere("chat_store",updateMap,"message_id",id);
			}
		}
		return R.success("删除聊天列表成功");
	}


	@ApiOperationSupport(order = 32)
	@GetMapping(value = "/delete/allchatList")
	@ApiOperation(value = "清空聊天列表",notes = "清空聊天列表")
	public R deleteAllChatlist(){
		String wxuserId = webService.getWxuserId();
		Map<String,Object> updateMap = new HashMap<>();
		updateMap.put("is_deleted",-1);
		baseSqlService.baseUpdateDataWhere("chat_log_message",updateMap,"wxuser_id",wxuserId);

		Map<String,Object> updateMap2 = new HashMap<>();
		updateMap2.put("is_deleted",-1);
		baseSqlService.baseUpdateDataWhere("chat_log_message_fun",updateMap2,"wxuser_id",wxuserId);

		Map<String,Object> updateMap3 = new HashMap<>();
		updateMap3.put("is_deleted",-1);
		baseSqlService.baseUpdateDataWhere("chat_list",updateMap3,"wxuser_id",wxuserId);

		Map<String,Object> updateMap4 = new HashMap<>();
		updateMap4.put("is_deleted",-1);
		baseSqlService.baseUpdateDataWhere("chat_store",updateMap4,"wxuser_id",wxuserId);
		return R.success("清空聊天列表成功");
	}

	@ApiOperationSupport(order = 33)
	@PostMapping(value = "/get/creditList")
	@ApiOperation(value = "积分列表",notes = "积分列表")
	public R creditList(@RequestBody CreditListParam creditListParam){
		String type = creditListParam.getType();
		IPage<Object> page = Condition.getPage(creditListParam);
		if (Func.equals("sub",type)){
			IPage<Map<String, Object>> creditList = webService.getSubCreditList(page);
			return R.data(creditList);
		}else if(Func.equals("add",type)){
			IPage<Map<String, Object>> creditList = webService.getAddCreditList(page);
			return R.data(creditList);
		}else{
			IPage<Map<String, Object>> creditList = webService.getCreditList(page);
			return R.data(creditList);
		}

	}




	@ApiOperationSupport(order = 37)
	@PostMapping(value = "/get/invited")
	@ApiOperation(value = "展示已经邀请的人数",notes = "展示已经邀请的人数")
	public R getInvited(@RequestBody Query query){

		IPage<Object> page = Condition.getPage(query);
		IPage<Map<String, Object>> invitedUsers = webService.getInvitedUsers(page);
		return R.data(invitedUsers);
	}

	@ApiOperationSupport(order = 38)
	@PostMapping(value = "/get/funhistory" )
	@ApiOperation(value = "获取工具历史记录",notes = "获取工具历史记录")
	public R getFunHistory(@RequestBody FunHistoryParam param){
		String funDataId = param.getFunDataId();
		IPage<Object> page = Condition.getPage(param);
		IPage<Map<String,Object>> funHistory = webService.getFunHistory(page,funDataId);
		return R.data(funHistory);
	}

	@ApiOperationSupport(order = 40)
	@GetMapping(value = "/get/inviteurl" )
	@ApiOperation(value = "获取邀请链接",notes = "获取邀请链接")
	public R  getInviteUrl(){
		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		String inviteCode = wxUsrInfo.getInviteCode();
		Map<String, Object> shareData = baseSqlService.getDataOneByField("chat_share_data","is_deleted",0);
		String url = MjkjUtils.getMap2Str(shareData, "url");
		String content = MjkjUtils.getMap2Str(shareData,"content");
		url =content+" "+ url+"?invite_code="+inviteCode;
		return R.success(url);

	}




}

