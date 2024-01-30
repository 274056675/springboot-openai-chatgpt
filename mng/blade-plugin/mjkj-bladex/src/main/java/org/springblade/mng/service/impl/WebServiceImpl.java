package org.springblade.mng.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.ChatgptConfig;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.mng.mapper.WebMapper;
import org.springblade.mng.model.*;
import org.springblade.mng.param.ChatLogShareMessageParam;
import org.springblade.mng.param.MoreFunParam;
import org.springblade.mng.service.*;
import org.springblade.plugin.message.feign.IMessageClient;
import org.springblade.plugin.message.model.ChatGptMoreFunModel;
import org.springblade.plugin.message.model.ChatGptMsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
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

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IChatGPTService chatGPTService;

	@Autowired
	private WebMapper webMapper;

	@Autowired
	private BladeRedis bladeRedis;

	@Autowired
	private ICommissionService commissionService;

	@Autowired
	private IMessageClient messageClient;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IErnieBotService ernieBotService;

	//获取用户详情
	@Override
	public WxUserInfoModel getWxUsrInfo() {
		Long userId = AuthUtil.getUserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", userId);
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			throw new ServiceException("数据不存在");
		}
		String wxuserId = MjkjUtils.getMap2Str(dataMap, "id");
		//获取次数
		Integer messageCou = this.getMessageCou(userId);
		//获取等级
		String levelTitle = this.getLevelTitle(messageCou);

		//获取消息显示默认
		String viewModel = this.getCsszVal("view_model", "0");

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
		Set<String> keys = bladeRedis.keys(rewardAdvertRedisKey);
		if (Func.isNotEmpty(keys)) {
			viewRewardAdvertCou = keys.size();
		}
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
//
//		if(Func.isEmpty(wxShareUrl)){//需要生成专属连接
//			try{
//				String path = "/pages/index/index";
//				//获取微信那边二维码
//				final WxMaService wxService = WxMaConfiguration.getMaService(wxMaProperties.getConfigs().get(0).getAppid());
//				GenerateUrlLinkRequest request=new GenerateUrlLinkRequest();
//				request.setPath(path);
//				request.setQuery("inviteCode=" + inviteCode);
//				wxShareUrl= wxService.getLinkService().generateUrlLink(request);
//				if(Func.isNotEmpty(wxShareUrl)){
//					Map<String,Object> updateMap=new HashMap<>();
//					updateMap.put("wx_share_url",wxShareUrl);
//					baseSqlService.baseUpdateData("chat_wxuser",updateMap,wxuserId);
//				}
//			}catch (Exception e){
//				//e.printStackTrace();
//			}
//		}

		String phone = MjkjUtils.getMap2Str(dataMap, "phone");
		if(Func.isEmpty(wxName)){
			wxName="匿名用户";
		}else if(Func.isNotEmpty(phone) && Func.equals(phone,wxName)){//如果手机号码和名称一样，则强制改为匿名用户
			wxName="匿名用户";
		}


		WxUserInfoModel model = new WxUserInfoModel();
		model.setId(MjkjUtils.getMap2Str(dataMap, "id"));
		model.setBladeUserId(userId);
		model.setWxName(wxName);
		model.setWxName_Dim(MjkjUtils.desensitizeStr(wxName,"匿名用户"));
		model.setWxAvatar(MjkjUtils.getMap2Str(dataMap, "wx_avatar"));
		model.setChatCode(MjkjUtils.getMap2Str(dataMap, "chat_code"));
		model.setOpenId(MjkjUtils.getMap2Str(dataMap, "open_id"));
		model.setMessageCou(messageCou);
		model.setLeveTitler(levelTitle);
		model.setViewModel(viewModel);
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
		model.setInviteCode(MjkjUtils.getMap2Str(dataMap, "invite_code"));
		model.setPosterUrl(MjkjUtils.getMap2Str(dataMap, "poster_url"));//海报地址
		model.setPosterWxUrl(MjkjUtils.getMap2Str(dataMap,"poster_wx_url"));//微信端海报地址
		model.setStopSendTime(stopSendTime);//禁言截至时间
		model.setViewRewardAdvertCou(viewRewardAdvertCou);//观看视频次数
		model.setWxShareUrl(wxShareUrl);
		model.setIsAgent(isAgent);//是否代理商
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
		if (bladeRedis.exists(redisKey)) {
			return bladeRedis.get(redisKey);
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
		bladeRedis.set(redisKey, chatCode);

		return chatCode;
	}

	//获取微信id ---- 走缓存
	@Override
	public String getWxuserId() {
		//走缓存
		Long userId = AuthUtil.getUserId();
		String redisKey = MjkjUtils.getRedisKeyWuserId(userId);
		if (bladeRedis.exists(redisKey)) {
			return bladeRedis.get(redisKey);
		}


		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", userId);
		wrapper.select("id");
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			throw new ServiceException("数据不存在");
		}
		String wuserId = MjkjUtils.getMap2Str(dataMap, "id");
		bladeRedis.set(redisKey, wuserId);
		return wuserId;
	}

	//获取问题可提问次数
	@Override
	public Integer getWuserQuestionCou(Long bladeUserId) {
		if (Func.isEmpty(bladeUserId)) {
			bladeUserId = AuthUtil.getUserId();
		}

		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("blade_user_id", bladeUserId);
		wrapper.select("question_cou");//问题次数
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isEmpty(dataMap)) {
			return 0;
		}
		return MjkjUtils.getMap2Integer(dataMap, "question_cou");
	}

	//获取是否开启 提问消耗次数
	@Override
	public boolean getQuestionNumFlag() {
		try {
			String val = this.getCsszVal("question_num_flag", "false");
			if (Func.isEmpty(val)) {
				return false;
			}
			return Func.equals(val, "true");
		} catch (Exception e) {

		}
		return false;
	}

	//更多好玩 提问消耗次数
	@Override
	public boolean getMoreFunQuestionNumFlag() {
		try {
			String val = this.getCsszVal("more_fun_question_num_flag", "false");
			if (Func.isEmpty(val)) {
				return false;
			}
			return Func.equals(val, "true");
		} catch (Exception e) {

		}
		return false;
	}

	//获取提问一次消耗多少次
	@Override
	public Integer getOneQuestionUseNum() {
		try {
			String val = this.getCsszVal("question_use_num", "1");
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
	public List<ChatGptMsgModel> sendQuestion(String question, Long startMessageId, String modelType, boolean memberFlag) {
		Long bladeUserId = AuthUtil.getUserId();
		String wxuserId = this.getWxuserId();
		String chatCode = this.getChatCode();

		Date now = DateUtil.now();
		String id = IdWorker.getIdStr();

		List<Map<String, Object>> accountMapList = baseSqlService.getDataListByField("chat_api_account", "gpt_state", 0);//1

		if (Func.isEmpty(accountMapList)) {
			throw new ServiceException("账户配置有误，请联系客服处理");
		}


		//返回结果，如果有敏感字的话，直接返回
		Map<String, Object> insertMap = new HashMap<>();
		insertMap.put("id", id);
		insertMap.put("wxuser_id", wxuserId);
		insertMap.put("message_type", MessageType.Q);//q =问题  a=答案
		insertMap.put("message_content", question);
		insertMap.put("message_time", now);
		insertMap.put("blade_user_id", bladeUserId);
		insertMap.put("view_type", ViewType.TEXT);
		insertMap.put("model_type", modelType);
		insertMap.put("context_flag",1);//支持上下文


		baseSqlService.baseInsertData("chat_log_message", insertMap);

		boolean questionNumFlag = this.getQuestionNumFlag();
		if (!memberFlag && questionNumFlag) {//非会员，有开启消耗次数，则需要消耗次数
			Integer oneUseNum = this.getOneQuestionUseNum();//一次消耗多少次
			this.subWxuserQuestionNum(bladeUserId, wxuserId, 4, oneUseNum, id, null);//扣除次数
		}

		ChatGptMsgModel questionMsgModel = new ChatGptMsgModel();
		questionMsgModel.setId(id);
		questionMsgModel.setPid("-1");
		questionMsgModel.setChatCode(chatCode);
		questionMsgModel.setMessage_type(MessageType.Q);//q =问题  a=答案
		questionMsgModel.setMessage_content(question);
		questionMsgModel.setMessage_time(now);
		questionMsgModel.setView_type(ViewType.TEXT);

		//校验是否有敏感词
		List<ChatGptMsgModel> resultModelList = this.checkSensitiveWord(questionMsgModel, insertMap, chatCode);
		if (Func.isNotEmpty(resultModelList)) {
			return resultModelList;
		}
		//校验是否是口令福利
		resultModelList = this.checkPasswordBenefits(questionMsgModel, insertMap, chatCode);
		if (Func.isNotEmpty(resultModelList)) {
			return resultModelList;
		}

		resultModelList=new ArrayList<>();
		resultModelList.add(questionMsgModel);
		String chatgptModel = ChatgptConfig.getChatgptModel();

		//生成图片
			if (Func.equals(chatgptModel, "text-davinci-003")) {
				chatGPTService.sendChatGptMessage(modelType, wxuserId, id, question, startMessageId);
			} else if (Func.equals(chatgptModel,"gpt-3.5-turbo")){
				chatGPTService.sendChatGptTurboMessage(modelType, wxuserId, id, question, startMessageId, now);
			}  else if(Func.equals(chatgptModel,"ernie-bot-turbo"))  {
				ernieBotService.sendErnieBotTurboMessage( wxuserId, id, question, startMessageId, now);
			}



		return resultModelList;
	}

	/**
	 * 校验是否有敏感词
	 *
	 * @param questionMsgModel
	 * @param questionMap
	 * @param chatCode
	 * @return
	 */
	private List<ChatGptMsgModel> checkSensitiveWord(ChatGptMsgModel questionMsgModel, Map<String, Object> questionMap, String chatCode) {
		try{
			String wxuserId = MjkjUtils.getMap2Str(questionMap, "wxuser_id");
			String pid = MjkjUtils.getMap2Str(questionMap, "id");
			String question = MjkjUtils.getMap2Str(questionMap, "message_content");
			String modelType = MjkjUtils.getMap2Str(questionMap, "model_type");
			String bladeUserId = MjkjUtils.getMap2Str(questionMap, "blade_user_id");


			//校验是否有敏感字
			boolean wordFlag = this.checkSensitiveWord(question);
			if (wordFlag) {//不存在敏感词
				return null;
			}
			Date now = DateUtil.now();
			List<ChatGptMsgModel> resultModelList = new ArrayList<>();
			resultModelList.add(questionMsgModel);

			String answerStr = this.getCsszVal("sensitive_word_msg", "您发送的内容存在敏感词");
			//保存消息
			String answerId = IdWorker.getIdStr();
			Map<String, Object> insertAnswerMap = new HashMap<>();
			insertAnswerMap.put("id", answerId);
			insertAnswerMap.put("pid", pid);
			insertAnswerMap.put("wxuser_id", wxuserId);
			insertAnswerMap.put("message_type", MessageType.A);//q =问题  a=答案
			insertAnswerMap.put("message_content", answerStr);//回答内容
			insertAnswerMap.put("message_time", now);
			insertAnswerMap.put("blade_user_id", bladeUserId);
			insertAnswerMap.put("view_type", ViewType.ERROR);
			insertAnswerMap.put("model_type", modelType);//分类
			baseSqlService.baseInsertData("chat_log_message", insertAnswerMap);

			//将父级别改为铭感词
			Map<String, Object> updatePMap = new HashedMap();
			updatePMap.put("view_type", ViewType.ERROR);
			baseSqlService.baseUpdateData("chat_log_message", updatePMap, pid);

			//发送到前端
			ChatGptMsgModel answerMsgModel = new ChatGptMsgModel();
			answerMsgModel.setId(answerId);
			answerMsgModel.setPid(pid);
			answerMsgModel.setChatCode(chatCode);
			answerMsgModel.setMessage_type(MessageType.A);//q =问题  a=答案
			answerMsgModel.setMessage_content(answerStr);
			answerMsgModel.setMessage_time(now);
			answerMsgModel.setView_type(ViewType.ERROR);
			resultModelList.add(answerMsgModel);
			return resultModelList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 校验是否口令福利
	 *
	 * @param questionMsgModel
	 * @param questionMap
	 * @param chatCode
	 * @return
	 */
	private List<ChatGptMsgModel> checkPasswordBenefits(ChatGptMsgModel questionMsgModel, Map<String, Object> questionMap, String chatCode) {
		try{
			String passwordTextListStr = this.getCsszVal("password_text", "");
			if (Func.isEmpty(passwordTextListStr)) {//没有开启口令福利
				return null;
			}
			String wxuserId = MjkjUtils.getMap2Str(questionMap, "wxuser_id");
			String pid = MjkjUtils.getMap2Str(questionMap, "id");
			String question = MjkjUtils.getMap2Str(questionMap, "message_content");
			String modelType = MjkjUtils.getMap2Str(questionMap, "model_type");
			String bladeUserId = MjkjUtils.getMap2Str(questionMap, "blade_user_id");

			List<String> passwordTextList = Func.toStrList(passwordTextListStr);
			if (!passwordTextList.contains(question)) {//没有开启口令福利
				return null;
			}

			//校验该指令是否领取过
			QueryWrapper<Object> wrapper = new QueryWrapper<>();
			wrapper.eq("wxuser_id", wxuserId);
			wrapper.eq("is_deleted", 0);
			wrapper.eq("password_text", question);
			Map<String, Object> chatLogCommandMap = baseSqlService.getDataOneByFieldParams("chat_log_command", wrapper);

			Date now = DateUtil.now();
			String answerStr = "领取成功";
			String view_type=ViewType.TEXT;
			if (Func.isNotEmpty(chatLogCommandMap)) {//已经领取过了
				answerStr = "您已经领取过福利了！";
				view_type=ViewType.ERROR;
			} else {
				String passwordCou = this.getCsszVal("password_cou", "0");
				this.addWxuserQuestionNum(Func.toLong(bladeUserId), wxuserId, 10, Func.toInt(passwordCou), null, question);
				Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
				//保存记录
				Map<String,Object> commandMap=new HashMap<>();
				commandMap.put("wxuser_id",wxuserId);
				commandMap.put("wxuser_name",MjkjUtils.getMap2Str(wxuserMap,"wx_name"));
				commandMap.put("password_text",question);
				commandMap.put("password_cou",passwordCou);
				commandMap.put("password_time",now);
				baseSqlService.baseInsertData("chat_log_command",commandMap);
			}


			List<ChatGptMsgModel> resultModelList = new ArrayList<>();
			resultModelList.add(questionMsgModel);//问题

			//保存消息
			String answerId = IdWorker.getIdStr();
			Map<String, Object> insertAnswerMap = new HashMap<>();
			insertAnswerMap.put("id", answerId);
			insertAnswerMap.put("pid", pid);
			insertAnswerMap.put("wxuser_id", wxuserId);
			insertAnswerMap.put("message_type", MessageType.A);//q =问题  a=答案
			insertAnswerMap.put("message_content", answerStr);//回答内容
			insertAnswerMap.put("message_time", now);
			insertAnswerMap.put("blade_user_id", bladeUserId);
			insertAnswerMap.put("view_type", view_type);
			insertAnswerMap.put("model_type", modelType);//分类
			baseSqlService.baseInsertData("chat_log_message", insertAnswerMap);


			//发送到前端
			ChatGptMsgModel answerMsgModel = new ChatGptMsgModel();
			answerMsgModel.setId(answerId);
			answerMsgModel.setPid(pid);
			answerMsgModel.setChatCode(chatCode);
			answerMsgModel.setMessage_type(MessageType.A);//q =问题  a=答案
			answerMsgModel.setMessage_content(answerStr);
			answerMsgModel.setMessage_time(now);
			answerMsgModel.setView_type(view_type);
			resultModelList.add(answerMsgModel);

			return resultModelList;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	//获取历史聊天记录
	@Override
	public IPage<Map<String, Object>> getMessageHistoryList(Long startNum, String modelType, IPage<Object> page) {
		Long userId = AuthUtil.getUserId();
		//获取总数
		QueryWrapper<Object> totalWrapper = new QueryWrapper<Object>();
		totalWrapper.eq("blade_user_id", userId);
		totalWrapper.eq("model_type", modelType);
		totalWrapper.eq("is_deleted", 0);
		totalWrapper.select("count(1) as cou");
		Map<String, Object> totalMap = baseSqlService.getDataOneByFieldParams("chat_log_message", totalWrapper);
		Long totalCou = MjkjUtils.getMap2Long(totalMap, "cou");


		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("blade_user_id", userId);
		wrapper.eq("model_type", modelType);
		wrapper.eq("is_deleted", 0);
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
	public List<Map<String, Object>> getMessageLastList(Long startNum, String modelType) {
		Long userId = AuthUtil.getUserId();
		QueryWrapper<Object> wrapper = new QueryWrapper<Object>();
		wrapper.eq("blade_user_id", userId);
		wrapper.eq("model_type", modelType);
		wrapper.eq("is_deleted", 0);
		wrapper.eq("message_type", "a");//回答
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
	public Integer getMessageCou(Long bladeUserId) {
		Integer cou = webMapper.getMessageCou(bladeUserId);
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
		Map<String, Object> dataMap = baseSqlService.getDataOneByField("chat_cssz", "code", code);
		if (Func.isEmpty(dataMap)) {
			return defaultVal;
		}
		String val = MjkjUtils.getMap2Str(dataMap, "val");
		if (Func.isEmpty(val)) {
			return defaultVal;
		}
		return val;
	}

	//校验敏感词
	@Override
	public boolean checkSensitiveWord(String str) {
//		try {
//			if (Func.isEmpty(str)) {
//				return false;
//			}
//			String appId = wxMaProperties.getConfigs().get(0).getAppid();
//			final WxMaService wxService = WxMaConfiguration.getMaService(appId);
//			WxMaSecCheckService secCheckService = wxService.getSecCheckService();
//			boolean b = secCheckService.checkMessage(str);
//			return b;
//		} catch (Exception e) {
//			//e.printStackTrace();
//		}
		return true;
	}

	//增加用户次数
	@Transactional
	@Override
	public void addWxuserQuestionNum(Long bladeUserId, String wxuserId, Integer serviceType, Integer num, String questionId, String remark) {
		this.wxuserQuestionNum(bladeUserId, "ADD", serviceType, wxuserId, num, questionId, remark);
	}

	//减用户次数
	@Transactional
	@Override
	public void subWxuserQuestionNum(Long bladeUserId, String wxuserId, Integer serviceType, Integer num, String questionId, String remark) {
		this.wxuserQuestionNum(bladeUserId, "SUB", serviceType, wxuserId, num, questionId, remark);
	}

	//加锁
	private synchronized void wxuserQuestionNum(Long bladeUserId, String type, Integer serviceType, String wxuserId, Integer num, String questionId, String remark) {
		Integer before_num = this.getWuserQuestionCou(bladeUserId);//用户可用次数
		Integer after_num = 0;

		if (Func.equals(type, "ADD")) {//增加
			after_num = before_num + num;
		} else if (Func.equals(type, "SUB")) {//减少
			after_num = before_num - num;
		}
		//更新用户使用后的次数
		Map<String, Object> wuserUpdateMap = new HashedMap();
		wuserUpdateMap.put("question_cou", after_num);
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



	//获取用户自定义设置
	@Override
	public Map<String, Object> getWxUserSetting(String wxUserId) {
		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_wxuser_setting", "wxuser_id", wxUserId);
		if (Func.isEmpty(settingMap)) {
			settingMap = new HashMap<>();
			settingMap.put("translate_lang", "中文");
			settingMap.put("wxuser_id", wxUserId);
			settingMap.put("blade_user_id", AuthUtil.getUserId());
			baseSqlService.baseInsertData("chat_wxuser_setting", settingMap);
		}
		return settingMap;
	}



	//发送更多好玩
	@Async("asyncPoolTaskExecutor")
	@Override
	public void sendMoreFun(String wxuserId, Long bladeUserId, boolean memberFlag, MoreFunParam param) {
		String systemTitle = param.getSystemTitle();
		String content = param.getContent();
		String funFataId = param.getFunFataId();
		Map<String, Object> funDataMap = baseSqlService.getTableById("chat_fun_data", funFataId);
		String funName = MjkjUtils.getMap2Str(funDataMap, "fun_name");//更多好玩名称

		//需要减掉次数
		boolean moreQuestionNumFlag = this.getMoreFunQuestionNumFlag();
		if (!memberFlag && moreQuestionNumFlag) {//非会员，有开启消耗次数，则需要消耗次数
			Integer oneUseNum = this.getOneQuestionUseNum();//一次消耗多少次
			this.subWxuserQuestionNum(bladeUserId, wxuserId, 9, oneUseNum, null, "更多好玩->" + funName);//扣除次数
		}

		Date requestTime = DateUtil.now();//请求时间
		//敏感词
		boolean wordFlag = this.checkSensitiveWord(content);
		String result = "";
		if (!wordFlag) {//存在敏感字，直接把消息体也返回返回
			result = this.getCsszVal("sensitive_word_msg", "您发送的内容存在敏感词");
		} else {//不存在敏感词
			List<MessageModel> messagesList = new ArrayList<>();
			MessageModel sysModel = new MessageModel();
			sysModel.setRole(MessageModelRoleType.SYSTEM);
			sysModel.setContent(systemTitle);
			messagesList.add(sysModel);//封装参数

			MessageModel userModel = new MessageModel();
			userModel.setRole(MessageModelRoleType.USER);
			userModel.setContent(content);
			messagesList.add(userModel);//封装参数

			result = chatGPTService.sendNowTimeChatGptTurboMessage(messagesList);
		}


		Date responseTime = DateUtil.now();//响应时间

		String id = IdWorker.getIdStr();
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
		baseSqlService.baseInsertData("chat_log_message_fun", addLogMap);

		//存储到redis
		String redisKey = MjkjUtils.getRedisKeyMoreFun(wxuserId, param.getFunFataId());
		bladeRedis.set(redisKey, addLogMap);

		Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
		String chatCode = MjkjUtils.getMap2Str(wxuserMap, "chat_code");

		//发送
		ChatGptMoreFunModel msgModel = new ChatGptMoreFunModel();
		msgModel.setId(id);
		msgModel.setChatCode(chatCode);
		msgModel.setFun_data_id(funFataId);
		msgModel.setMessage_a(result);
		msgModel.setMessage_q_time(requestTime);
		messageClient.sendChatMoreFunMsg(msgModel);
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
				if (!bladeRedis.exists(redisKeySign)) {
					lxFlag = false;
					break;
				}
			}
			if (lxFlag) {//连续
				String val = this.getCsszVal("sign_week_cou", "0");
				awardCou = Func.toInt(val);//每天奖励次数
			} else {
				String val = this.getCsszVal("sign_day_cou", "0");
				awardCou = Func.toInt(val);//每天奖励次数
			}
		} else {
			String val = this.getCsszVal("sign_day_cou", "0");
			awardCou = Func.toInt(val);//每天奖励次数
		}

		//进行签到
		Map<String, Object> addMap = new HashMap<>();
		addMap.put("wxuser_id", wxuserId);
		addMap.put("wxuser_name", wx_name);
		addMap.put("award_cou", awardCou);
		addMap.put("sign_time", date);
		baseSqlService.baseInsertData("chat_log_sign", addMap);

		this.addWxuserQuestionNum(bladeUserId, wxuserId, 7, awardCou, null, "签到奖励");

		//写入到缓存
		String redisKey = MjkjUtils.getRedisKeySign(bladeUserId, date);
		bladeRedis.set(redisKey, awardCou);
	}

	/**
	 * 添加用户分享的消息
	 * @param param
	 */
	@Override
	public void addShareLog(String wxuserId,ChatLogShareMessageParam param) {
		HashMap<String, Object> insertMap = new HashMap<>();
		String[] messageIds = param.getMessageIds();
		String message = "";
		for (int i = 0; i < messageIds.length; i++) {
			if (i==messageIds.length-1){
				message+=messageIds[i];
				break;
			}
			message+=messageIds[i]+",";
		}

		insertMap.put("wxuser_id",wxuserId);
		insertMap.put("message_ids",message);
		insertMap.put("only_id",param.getOnlyId());
		baseSqlService.baseInsertData("chat_log_share_message",insertMap);
	}

	/**
	 * 用户获取被分享的消息
	 * @param onlyId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getShareLog(String onlyId) {
		//
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		//根据唯一标识符找到对应的message
		wrapper.eq("only_id", onlyId);
		wrapper.select("message_ids");
		Map<String, Object> dataMap = baseSqlService.getDataOneByFieldParams("chat_log_share_message", wrapper);
		String messageIds = MjkjUtils.getMap2Str(dataMap, "message_ids");

		//将messageIds分割开来
		List<String> messageList = Func.toStrList(messageIds);
		//
		QueryWrapper<Object> wrapper2 = new QueryWrapper<>();
		wrapper2.eq("is_deleted", 0);
		wrapper2.in("id", messageList);
		List<Map<String, Object>> result = baseSqlService.getDataListByFieldParams("chat_log_message", wrapper2);

		//使用唯一标识符找到
		return result;
	}

	/**
	 * 获取本微信用户id下所有的子用户(推广)
	 * @param wxuserId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getChildUsers(String wxuserId) {
		QueryWrapper<Object> qw = new QueryWrapper<>();
		qw.eq("is_deleted",0);
		qw.eq("pid",wxuserId);
		List<Map<String, Object>> dataMapper = baseSqlService.getDataListByFieldParams("chat_wxuser", qw);
		return dataMapper;
	}

	//获取下级要求数量
	public IPage<Map<String, Object>>  getSubCouList(String wxuserId,IPage<Object> page){
		return webMapper.getSubCouList(wxuserId,page);
	}



}

