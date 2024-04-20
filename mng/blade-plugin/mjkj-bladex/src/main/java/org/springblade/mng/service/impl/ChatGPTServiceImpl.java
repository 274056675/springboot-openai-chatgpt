package org.springblade.mng.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springblade.mng.cgform.model.ImageSizeModel;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.constant.ChatgptConfig;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.config.LocalCache;
import org.springblade.mng.mapper.WebMapper;
import org.springblade.mng.model.*;
import org.springblade.mng.param.ChatGptParam;
import org.springblade.mng.param.ChatGptPublicParam;
import org.springblade.mng.service.IChatGPTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CHATGPT相关
 */
@Slf4j
@Service
public class ChatGPTServiceImpl implements IChatGPTService {

	String MJKJ_USER = "魔晶人工智能: ";//提问者
	String CHATGPT_USER = "CHATGPT人工智能: ";//回答者
	String MJKJ_USER2 = "魔晶人工智能:";//提问者
	String CHATGPT_USER2 = "CHATGPT人工智能:";//回答者
	String MJKJ_USER3 = "魔晶人工智能";//提问者
	String CHATGPT_USER3 = "CHATGPT人工智能";//回答者

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private WebMapper webMapper;

	@Autowired
	private RedisUtil redisUtil;

	private static String aesKey = "EgzdVGYalHE1pUNMO3CeIKatKmuocz07";


	/**
	 * 异步处理
	 *
	 * @param wxUserId
	 * @param q_logMessageId
	 * @param chatListIdL
	 */
	@Async("asyncPoolTaskExecutor")
	@Override
	public void sendChatGptMessage(String wxUserId, String q_logMessageId, String question, Long startMessageId, Long chatListIdL) {
		//处理上下文
		String sendChatGptQuestion = this.handleContext(wxUserId, question, q_logMessageId, startMessageId,chatListIdL);

		AccountUseCouModel accountModel = this.getChatGptKey();
		if (Func.isEmpty(accountModel)) {
			return;
		}

		//使用的模型是GPT3
		String model = "text-davinci-003";

		ChatGptParam param = new ChatGptParam();
		param.setUrl(ChatgptConfig.getChatgptUrl());
		param.setKey(accountModel.getApiKey());
		param.setModel(model); //发送的model参数值为gpt3的值
		param.setMax_tokens(ChatgptConfig.getChatgptMaxToken());
		param.setTop_p(ChatgptConfig.getChatgptTopP());
		param.setPrompt(sendChatGptQuestion);
		String jsonStr = JsonUtil.toJson(param);

		AES aes = SecureUtil.aes(aesKey.getBytes());
		String body = aes.encryptHex(jsonStr);// 加密为16进制表示
		try {
			String result = "";
			try {
				result = HttpRequest.post(ChatgptConfig.getHttpUrl())
					.header("Content-Type", "application/json;charset:utf-8")
					.body(body).execute().body();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ResultModel resultModel = JsonUtil.parse(result, ResultModel.class);
			if (Func.isEmpty(resultModel) || resultModel.getCode() != 200) {
				return;
			}
			String resultStr = resultModel.getResultStr();

			Map<String, Object> wxUserMap = baseSqlService.getTableById("chat_wxuser", wxUserId);
			Long bladeUserId = MjkjUtils.getMap2Long(wxUserMap, "blade_user_id");
			String chatCode = MjkjUtils.getMap2Str(wxUserMap, "chat_code");

			ChatGptResult chatGptResult = JsonUtil.parse(resultStr, ChatGptResult.class);
			String object = chatGptResult.getObject();
			if (Func.isNotEmpty(object) && Func.equals(object, "text_completion")) {//当前是文本形式
				List<ChatGptResult.choiceModel> choices = chatGptResult.getChoices();
				for (ChatGptResult.choiceModel choice : choices) {//推送给用户
					Date now = DateUtil.now();
					String id = IdWorker.getIdStr();
					String choiceText = choice.getText();
					choiceText = choiceText.replaceAll(MJKJ_USER, "").replaceAll(CHATGPT_USER, "");
					choiceText = choiceText.replaceAll(MJKJ_USER2, "").replaceAll(CHATGPT_USER2, "");
					choiceText = choiceText.replaceAll(MJKJ_USER3, "").replaceAll(CHATGPT_USER3, "");
					choiceText = this.handleChatgptResult(choiceText);
					//保存消息
					Map<String, Object> insertMap = new HashMap<>();
					insertMap.put("id", id);
					insertMap.put("pid", q_logMessageId);
					insertMap.put("wxuser_id", wxUserId);
					insertMap.put("message_type", MessageType.A);//q =问题  a=答案
					insertMap.put("message_content", choiceText);//回答内容
					insertMap.put("message_time", now);
					insertMap.put("blade_user_id", bladeUserId);
					insertMap.put("view_type", ViewType.TEXT);
					insertMap.put("model_type", 0);//分类
					insertMap.put("api_account_id", accountModel.getId());//账户id
					insertMap.put("chat_list_id",String.valueOf(chatListIdL));
					baseSqlService.baseInsertData("chat_log_message", insertMap);


				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * 异步处理
	 *
	 * @param wxUserId
	 * @param q_logMessageId
	 * @param chatListIdL
	 */
	@Async("asyncPoolTaskExecutor")
	@Override
	public void sendChatGptTurboMessage(String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime, Long chatListIdL) {
		Map<String, Object> wxUserMap = baseSqlService.getTableById("chat_wxuser", wxUserId);
		Long bladeUserId = MjkjUtils.getMap2Long(wxUserMap, "blade_user_id");
		String chatCode = MjkjUtils.getMap2Str(wxUserMap, "chat_code");

		// 获取用户选择的模型
		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_wxuser_setting", "wxuser_id", wxUserId);
		String aiModel = MjkjUtils.getMap2Str(settingMap,"ai_model");

		if( Func.isEmpty(aiModel)){ //拿不到，设为默认模型
			aiModel = ChatgptConfig.getChatgptModel();
			Map<String,Object> updateMap = new HashMap<>();
			updateMap.put("ai_model",aiModel);
			baseSqlService.baseUpdateDataWhere("chat_wxuser_setting",updateMap,"wxuser_id",wxUserId);
		}
		AccountUseCouModel accountModel = this.getChatGptKey();
		if (Func.isEmpty(accountModel)) {
			return;
		}

		//处理上下文
		String errorTip = "";
		List<MessageModel> messagesList = null;
		try {
			messagesList = this.handleTurboContext(wxUserId, question, q_logMessageId, startMessageId,chatListIdL);

		} catch (Exception e) {
			errorTip = e.getMessage();
		}


		List<ChatGptTurboResult.ChoiceModel> choices = null;

		try {
			if (Func.isEmpty(errorTip)) {//前面没有错误
				choices = this.getChatGptTurboResponse(messagesList, accountModel, aiModel); //此方法添加了一个参数：选择的模型
			}
		} catch (Exception e) {
			String error_message = e.getMessage();
			if (Func.isNotEmpty(error_message) && Func.equals(error_message, "余额不足")) {//说明账号没有余额了
				errorTip = "账户额度已用完，请联系客服";
				String id = accountModel.getId();
				Map<String, Object> apiAccountMap = baseSqlService.getTableById("chat_api_account", id);
				if (Func.isNotEmpty(apiAccountMap)) {
					String remark = MjkjUtils.getMap2Str(apiAccountMap, "remark");
					if (Func.isNotEmpty(remark) && !remark.startsWith("【账户可用额度已用完】")) {
						remark = "【账户可用额度已用完】" + remark;
					}
					Map<String, Object> updateMap = new HashMap<>();
					updateMap.put("gpt_state", 1);
					updateMap.put("remark", remark);
					baseSqlService.baseUpdateData("chat_api_account", updateMap, id);
				}
			}else{
				errorTip = "网络走过太平洋，没有游回来";
			}
		}
		String view_type = ViewType.TEXT;
		if (Func.isNotEmpty(errorTip)) {//存在错误
			view_type = ViewType.ERROR;
			choices = new ArrayList<>();
			ChatGptTurboResult.MessageModel messageModel = new ChatGptTurboResult.MessageModel();
			String errTip = errorTip;
			messageModel.setContent(errTip);

			ChatGptTurboResult.ChoiceModel choiceModel = new ChatGptTurboResult.ChoiceModel();
			choiceModel.setMessage(messageModel);
			choices.add(choiceModel);
		} else if (Func.isEmpty(choices)) {//其他原因
			view_type = ViewType.ERROR;
			choices = new ArrayList<>();
			ChatGptTurboResult.MessageModel messageModel = new ChatGptTurboResult.MessageModel();
			String errTip = "非常抱歉，我是AI语言模型，我回答的长度有限制。请退出重新进入继续提问";
			messageModel.setContent(errTip);

			ChatGptTurboResult.ChoiceModel choiceModel = new ChatGptTurboResult.ChoiceModel();
			choiceModel.setMessage(messageModel);
			choices.add(choiceModel);
		}
		String totalContent = "";
		for (ChatGptTurboResult.ChoiceModel choice : choices) {//推送给用户
			ChatGptTurboResult.MessageModel message = choice.getMessage();
			String content = message.getContent();

			content = this.handleChatgptResult(content);
			totalContent += content;
		}
		Date now = DateUtil.now();
		String id = IdWorker.getIdStr();
		//保存消息
		Map<String, Object> insertMap = new HashMap<>();
		insertMap.put("id", id);
		insertMap.put("pid", q_logMessageId);
		insertMap.put("wxuser_id", wxUserId);
		insertMap.put("message_type", MessageType.A);//q =问题  a=答案
		insertMap.put("message_content", totalContent);//回答内容
		insertMap.put("message_time", now);
		insertMap.put("blade_user_id", bladeUserId);
		insertMap.put("view_type", view_type);
		insertMap.put("model_type", 0);//分类
		insertMap.put("api_account_id", accountModel.getId());//账户id
		insertMap.put("context_flag", 1);//支持上下文
		insertMap.put("chat_list_id",String.valueOf(chatListIdL));
		baseSqlService.baseInsertData("chat_log_message", insertMap);

	}

	/**
	 * 发送实时消息，长连接等着返回
	 *
	 * @param
	 * @return
	 */
	@Override
	public String sendNowTimeChatGptTurboMessage(List<MessageModel> messagesList , String model) {
		List<ChatGptTurboResult.ChoiceModel> choices = null;

		try {
			choices = this.getChatGptTurboResponse(messagesList, null, model);
		} catch (Exception e) {
			return "出现异常，请您重新提问";
		}

		if (Func.isEmpty(choices)) {
			return "出现错误，请您重新提问";
		}
		String result = "";
		for (ChatGptTurboResult.ChoiceModel choice : choices) {//推送给用户
			ChatGptTurboResult.MessageModel message = choice.getMessage();
			String content = message.getContent();

			content = this.handleChatgptResult(content);
			result += content;
		}
		return result;
	}


	/**
	 * 获取chatgpt 返回内容
	 *
	 * @param messagesList
	 * @return
	 */
	public List<ChatGptTurboResult.ChoiceModel> getChatGptTurboResponse(List<MessageModel> messagesList, AccountUseCouModel accountModel, String aiModel) {
		try {//获取chatgpt 参数
			if (Func.isEmpty(accountModel)) {
				accountModel = this.getChatGptKey();
				if (Func.isEmpty(accountModel)) {
					return null;
				}
			}

			String chaggptModel = aiModel;  //换成用户选择的模型

			ChatGptPublicParam publicParam = new ChatGptPublicParam();
			publicParam.setKey(accountModel.getApiKey());
			publicParam.setUrl(ChatgptConfig.getChatgptUrl());


			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("model", chaggptModel);
			bodyMap.put("messages", messagesList);
			bodyMap.put("max_tokens", ChatgptConfig.getChatgptMaxToken());
			publicParam.setBody(JSONUtil.toJsonStr(bodyMap));
			String jsonStr = JsonUtil.toJson(publicParam);

			AES aes = SecureUtil.aes(aesKey.getBytes());
			String body = aes.encryptHex(jsonStr);// 加密为16进制表示

			String resultStr = "";
			String errorTip = "";
			//最多发送5次
			for (int i = 0; i < 10; i++) {
				resultStr = "";
				errorTip = "";
				try {
					String result = HttpRequest.post(ChatgptConfig.getHttpUrl())
						.header("Content-Type", "application/json;charset:utf-8")
						.body(body).execute().body();

					if(!result.contains("choices"))
					{
						log.error("-------------gpt返回错误"+result+"--------------");
					}
					if (result.contains("This model's maximum context length")) {//说明token太长
						List<MessageModel> messagesNewList = new ArrayList<>();
						messagesNewList.add(messagesList.get(messagesList.size() - 1));//最后一条
						//重新封装body
						bodyMap = new HashMap<>();
						bodyMap.put("model", chaggptModel);
						bodyMap.put("messages", messagesNewList);
						bodyMap.put("max_tokens", ChatgptConfig.getChatgptMaxToken());
						publicParam.setBody(JSONUtil.toJsonStr(bodyMap));
						jsonStr = JsonUtil.toJson(publicParam);
						body = aes.encryptHex(jsonStr);// 加密为16进制表示
						continue;
					} else if (result.contains("You exceeded your current quota")) {//余额不足
						errorTip = "余额不足";
						break;
					} else if (result.contains("Rate limit reached for")) {//被限制
						log.error("被限制"+messagesList);
						List<MessageModel> messagesNewList = new ArrayList<>();
						messagesNewList.add(messagesList.get(messagesList.size() - 1));//最后一条
						//重新封装body
						bodyMap = new HashMap<>();
						bodyMap.put("model", chaggptModel);
						bodyMap.put("messages", messagesNewList);
						bodyMap.put("max_tokens", ChatgptConfig.getChatgptMaxToken());
						publicParam.setBody(JSONUtil.toJsonStr(bodyMap));
						jsonStr = JsonUtil.toJson(publicParam);
						body = aes.encryptHex(jsonStr);// 加密为16进制表示
						Thread.sleep(2000);
						continue;
					}


					if (Func.isEmpty(result)) {//为空，重新发送
						Thread.sleep(1000);
						continue;
					}
					ResultModel resultModel = JsonUtil.parse(result, ResultModel.class);
					if (Func.isEmpty(resultModel) || resultModel.getCode() != 200) {
						Thread.sleep(1000);
						continue;//中途失败
					}
					resultStr = resultModel.getResultStr();
					if (Func.isEmpty(resultStr)) {
						Thread.sleep(2000);
						continue;//中途失败
					}
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (Func.isNotEmpty(errorTip)) {//有错误
				throw new ServiceException(errorTip);
			}

			//为空
			if (Func.isEmpty(resultStr)) {
				return null;
			}


			ChatGptTurboResult chatGptTurboResult = JsonUtil.parse(resultStr, ChatGptTurboResult.class);
			List<ChatGptTurboResult.ChoiceModel> choices = chatGptTurboResult.getChoices();
			return choices;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}


	//获取账号
	private String getAccount() {
		String flagstudioToken = ChatgptConfig.getFlagstudioToken();
		List<String> accountList = Func.toStrList(flagstudioToken);
		if (Func.isEmpty(accountList)) {
			return null;
		}
		Date now = DateUtil.now();
		String yyyyMMdd = DateUtil.format(now, DateUtil.PATTERN_DATE);
		for (String account : accountList) {
			String redisKey = yyyyMMdd + ":" + account;
			if (!redisUtil.hasKey(redisKey)) {
				redisUtil.set(redisKey, 1, 1,TimeUnit.DAYS);//天有效期
				return account;
			}
			Integer num = (Integer) redisUtil.get(account);
			if (Func.isEmpty(num)) {
				num = 1;
			}
			if (num >= 490) {//换下一个账号
				continue;
			}
			++num;
			redisUtil.set(redisKey, num, 1,TimeUnit.DAYS);//天有效期
			return account;
		}
		return null;

	}

	private String handleChatgptResult(String result) {
		result = result.trim();
		String startTitleListStr = ChatgptConfig.getStartTitleRemove();
		if (Func.isEmpty(startTitleListStr)) {
			return result;
		}
		List<String> startTitleList = Func.toStrList(startTitleListStr);
		while (true) {
			boolean whileFlag = true;
			for (String str : startTitleList) {
				boolean flag = result.startsWith(str);
				if (!flag) {
					continue;
				}
				whileFlag = false;
				result.replaceFirst(str, "");
			}
			if (whileFlag) {
				break;
			}
		}
		return result;
	}

	public synchronized AccountUseCouModel getChatGptKey() {
		List<Map<String, Object>> accountMapList = baseSqlService.getDataListByField("chat_api_account", "gpt_state", 0);//1

		if (Func.isEmpty(accountMapList)) {
			return null;
		}
		Date now = DateUtil.now();
		String nowStr = DateUtil.format(now, "yyyyMMddHH");//每小时次数应该要一样

		Map<String, AccountUseCouModel> modelMap = new HashedMap();
		for (Map<String, Object> accountMap : accountMapList) {
			String id = MjkjUtils.getMap2Str(accountMap, "id");
			String apiKey = MjkjUtils.getMap2Str(accountMap, "api_key");
			String redisKey = "CHAT_GPT:" + nowStr + ":ACCOUNT_ID_" + id;
			Integer cou = 0;
			if (redisUtil.hasKey(redisKey)) {
				cou = (Integer) redisUtil.get(redisKey);
			}
			//使用次数
			AccountUseCouModel couModel = new AccountUseCouModel();
			couModel.setId(id);
			couModel.setApiKey(apiKey);
			couModel.setCou(cou);
			modelMap.put(id, couModel);
		}

		//获取最小值
		Set<Map.Entry<String, AccountUseCouModel>> en = modelMap.entrySet();
		Integer mincou = null;
		for (Map.Entry<String, AccountUseCouModel> entry : en) {
			String key = entry.getKey();
			AccountUseCouModel model = entry.getValue();
			int cou = model.getCou();
			if (cou == 0) {//新数据
				String redisKey = "CHAT_GPT:" + nowStr + ":ACCOUNT_ID_" + model.getId();
				redisUtil.set(redisKey, ++cou, 7200L,TimeUnit.SECONDS);//2小时
				return model;
			}
			if (Func.isEmpty(mincou)) {//第一个
				mincou = cou;
				continue;
			}
			if (mincou > cou) {
				mincou = cou;//新的最小值
			}
		}

		//根据最小值，获取最新的key
		for (Map.Entry<String, AccountUseCouModel> entry : en) {
			AccountUseCouModel model = entry.getValue();
			int cou = model.getCou();
			if (mincou == cou) {
				String redisKey = "CHAT_GPT:" + nowStr + ":ACCOUNT_ID_" + model.getId();
				redisUtil.set(redisKey, ++cou, 7200L,TimeUnit.DAYS);//2小时
				return model;
			}
		}
		return null;
	}

	/**
	 * 处理上下文
	 *
	 * @param wxUserId
	 * @param question
	 * @param logMessageId   不保护该条id
	 * @param startMessageId
	 * @param chatListIdL
	 * @return
	 */
	private String handleContext(String wxUserId, String question, String logMessageId, Long startMessageId, Long chatListIdL) {
		String sendChatGptQuestion = "";
		QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("wxuser_id", wxUserId);
		queryWrapper.eq("is_deleted", 0);
		queryWrapper.eq("view_type", "text");//文本类型
		queryWrapper.ne("id", logMessageId);//不等于
		if(chatListIdL!=0L){
			queryWrapper.eq("chat_list_id",String.valueOf(chatListIdL));
		}
		queryWrapper.gt("id+0", startMessageId);//大于
		queryWrapper.orderByAsc("id+0");
		List<Map<String, Object>> dataMapList = baseSqlService.getDataListByFieldParams("chat_log_message", queryWrapper);
		if (Func.isEmpty(dataMapList)) {
			return question;
		}

		//最大数量不能大于
		while (true) {
			Integer lengthTmp = question.length();
			sendChatGptQuestion = "";
			for (Map<String, Object> dataMap : dataMapList) {
				String message_content = MjkjUtils.getMap2Str(dataMap, "message_content");
				String message_type = MjkjUtils.getMap2Str(dataMap, "message_type");
				if (Func.equals(message_type, "q")) {//我提的问题
					if (message_content.endsWith("?") || message_content.endsWith("？")) {
						sendChatGptQuestion += MJKJ_USER + message_content + "\n";
					} else {
						sendChatGptQuestion += MJKJ_USER + message_content + "?\n";
					}
				} else {

					sendChatGptQuestion += CHATGPT_USER + message_content + "\n";
				}
				lengthTmp += sendChatGptQuestion.length();
			}
			if (lengthTmp < ChatgptConfig.getChatgptMaxToken()) {
				break;//跳出死循环
			} else {
				dataMapList.remove(0);//移除第一位
			}
		}
		//上次，加上本次
		if (question.endsWith("?") || question.endsWith("？")) {
			sendChatGptQuestion += MJKJ_USER + question + "\n";
		} else {
			sendChatGptQuestion += MJKJ_USER + question + "?\n";
		}
		return sendChatGptQuestion;
	}

	/**
	 * 3.5版本
	 * 处理上下文
	 *
	 * @param wxUserId
	 * @param question
	 * @param logMessageId   不保护该条id
	 * @param startMessageId
	 * @param chatListIdL
	 * @return
	 */
	private List<MessageModel> handleTurboContext(String wxUserId, String question, String logMessageId, Long startMessageId, Long chatListIdL) {
		List<MessageModel> resultModelList = new ArrayList<>();

		if (question.length() > ChatgptConfig.getChatgptRequestMaxToken()) {
			throw new ServiceException("你发送的内容过长");
		}

		QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("wxuser_id", wxUserId);
		queryWrapper.eq("is_deleted", 0);
		queryWrapper.eq("view_type", "text");//文本类型
		queryWrapper.eq("context_flag", 1);//仅支持上下文
		queryWrapper.ne("id", logMessageId);//不等于
		if(chatListIdL!=0L){
			queryWrapper.eq("chat_list_id",String.valueOf(chatListIdL));
		}
		queryWrapper.gt("id+0", startMessageId);//大于
		queryWrapper.orderByAsc("id+0");
		List<Map<String, Object>> dataMapList = baseSqlService.getDataListByFieldParams("chat_log_message", queryWrapper);
		if (Func.isEmpty(dataMapList)) {
			MessageModel model0 = new MessageModel();
			model0.setRole(MessageModelRoleType.SYSTEM);
			model0.setContent("请你使用MarkDown格式回答");
			MessageModel model = new MessageModel();
			model.setRole(MessageModelRoleType.USER);
			model.setContent(question);
			resultModelList.add(model0);
			resultModelList.add(model);
			return resultModelList;
		}

		//最大数量不能大于
		while (true) {
			Integer lengthTmp = question.length();
			resultModelList = new ArrayList<>();
			List<MessageModel> tmpModelList = new ArrayList<>();
			for (int i = 0; i < dataMapList.size(); i++) {
				Map<String, Object> dataMap = dataMapList.get(i);
				String message_content = MjkjUtils.getMap2Str(dataMap, "message_content");
				String message_type = MjkjUtils.getMap2Str(dataMap, "message_type");
				if (Func.equals(message_type, "q")) {//我提的问题
					MessageModel model = new MessageModel();
					model.setRole(MessageModelRoleType.USER);
					model.setContent(message_content);
					resultModelList.add(model);
					tmpModelList.add(model);
				} else {
					MessageModel model = new MessageModel();
					model.setRole(MessageModelRoleType.CHATGPT);
					model.setContent(message_content);
					resultModelList.add(model);
					tmpModelList.add(model);
				}
				if (i == dataMapList.size() - 1) {//把本次的加上
					//上次，加上本次
					MessageModel model = new MessageModel();
					model.setRole(MessageModelRoleType.USER);
					model.setContent(question);
					tmpModelList.add(model);
				}
				lengthTmp += JsonUtil.toJson(tmpModelList).length();
			}
			if (lengthTmp < ChatgptConfig.getChatgptRequestMaxToken()) {
				break;//跳出死循环
			} else {
				if (dataMapList.size() == 1) {//只剩一条
					resultModelList = new ArrayList<>();
					break;
				}
				dataMapList.remove(0);//移除第一位
			}
		}

		//上次，加上本次
		MessageModel model = new MessageModel();
		model.setRole(MessageModelRoleType.USER);
		model.setContent(question);
		resultModelList.add(model);
		MessageModel model0 = new MessageModel();
		model0.setRole(MessageModelRoleType.SYSTEM);
		model0.setContent("请你使用MarkDown格式回答");
		resultModelList.add(model0);
		return resultModelList;
	}

}
