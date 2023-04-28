package org.springblade.mng.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springblade.cgform.model.ImageSizeModel;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.ChatgptConfig;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.AesUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.config.util.minio.MinioBladeFile;
import org.springblade.config.util.minio.MinioUtils;
import org.springblade.mng.mapper.WebMapper;
import org.springblade.mng.model.*;
import org.springblade.mng.param.ChatGptParam;
import org.springblade.mng.param.ChatGptPublicParam;
import org.springblade.mng.service.IChatGPTService;
import org.springblade.plugin.message.feign.IMessageClient;
import org.springblade.plugin.message.model.ChatGptMsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

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
	private IMessageClient messageClient;

	@Autowired
	private BladeRedis bladeRedis;

	private static String aesKey = "vWkzDxDfXruFpgjDH7Jy0mIWamCQvdct";

	@Lazy
	@Autowired
	private IChatGPTService chatGPTService;

	@Autowired
	private MinioUtils minioUtils;


	/**
	 * 异步处理
	 *
	 * @param wxUserId
	 * @param q_logMessageId
	 */
	@Async("asyncPoolTaskExecutor")
	@Override
	public void sendChatGptMessage(String modelType, String wxUserId, String q_logMessageId, String question, Long startMessageId) {
		//处理上下文
		String sendChatGptQuestion = this.handleContext(wxUserId, question, q_logMessageId, startMessageId);

		AccountUseCouModel accountModel = this.getChatGptKey();
		if (Func.isEmpty(accountModel)) {
			return;
		}

		ChatGptParam param = new ChatGptParam();
		param.setUrl(ChatgptConfig.getChatgptUrl());
		param.setKey(accountModel.getApiKey());
		//param.setKey("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");
		param.setModel(ChatgptConfig.getChatgptModel());
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
					insertMap.put("model_type", modelType);//分类
					insertMap.put("api_account_id", accountModel.getId());//账户id
					baseSqlService.baseInsertData("chat_log_message", insertMap);

					//发送到前端
					ChatGptMsgModel questionMsgModel = new ChatGptMsgModel();
					questionMsgModel.setId(id);
					questionMsgModel.setPid(q_logMessageId);
					questionMsgModel.setChatCode(chatCode);
					questionMsgModel.setMessage_type(MessageType.A);//q =问题  a=答案
					questionMsgModel.setMessage_content(choiceText);
					questionMsgModel.setMessage_time(now);
					questionMsgModel.setView_type(ViewType.TEXT);
					//log.info("questionMsgModel================"+JsonUtil.toJson(questionMsgModel));
					messageClient.sendChatGptMsg(questionMsgModel);
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
	 */
	@Async("asyncPoolTaskExecutor")
	@Override
	public void sendChatGptTurboMessage(String modelType, String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime) {
		Map<String, Object> wxUserMap = baseSqlService.getTableById("chat_wxuser", wxUserId);
		Long bladeUserId = MjkjUtils.getMap2Long(wxUserMap, "blade_user_id");
		String chatCode = MjkjUtils.getMap2Str(wxUserMap, "chat_code");

		AccountUseCouModel accountModel = this.getChatGptKey();
		if (Func.isEmpty(accountModel)) {
			return;
		}

		//处理上下文
		List<MessageModel> messagesList = this.handleTurboContext(wxUserId, question, q_logMessageId, startMessageId);

		List<ChatGptTurboResult.ChoiceModel> choices = null;
		String errorTip = "";
		try {
			choices = this.getChatGptTurboResponse(messagesList, accountModel);
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
		insertMap.put("model_type", modelType);//分类
		insertMap.put("api_account_id", accountModel.getId());//账户id
		insertMap.put("context_flag",1);//支持上下文
		baseSqlService.baseInsertData("chat_log_message", insertMap);

		//发送到前端
		//算出耗时
		Long useTimeL = now.getTime() - sendTime.getTime();
		BigDecimal useTime = BigDecimal.valueOf(useTimeL).divide(BigDecimal.valueOf(1000L));

		ChatGptMsgModel questionMsgModel = new ChatGptMsgModel();
		questionMsgModel.setId(id);
		questionMsgModel.setPid(q_logMessageId);
		questionMsgModel.setChatCode(chatCode);
		questionMsgModel.setMessage_type(MessageType.A);//q =问题  a=答案
		questionMsgModel.setMessage_content(totalContent);
		questionMsgModel.setMessage_time(now);
		questionMsgModel.setView_type(view_type);
		questionMsgModel.setUseTime(useTime.stripTrailingZeros().toPlainString());//耗时
		messageClient.sendChatGptMsg(questionMsgModel);
	}

	@Async("asyncPoolTaskExecutor")
	@Override
	public void sendImageMessage(String modelType, String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime, String size) {
		String imageModel = ChatgptConfig.getImageModel();
		boolean gptModelFlag = true;
		if (Func.equals(imageModel, "flagstudio")) {
			gptModelFlag = false;//不是gpt模型
		}

		String englishContent = "";
		//翻译成英文
		try {
			if (gptModelFlag) {//gpt模型
				List<MessageModel> messagesList = new ArrayList<>();
				MessageModel model1 = new MessageModel();
				model1.setRole(MessageModelRoleType.USER);
				model1.setContent(question);
				messagesList.add(model1);

				MessageModel model2 = new MessageModel();
				model2.setRole(MessageModelRoleType.USER);
				model2.setContent("将上面一句话翻译成英文，并且只返回英文");
				messagesList.add(model2);
				englishContent = chatGPTService.sendNowTimeChatGptTurboMessage(messagesList);
				log.info(question + "  =====> 翻译成英文=====>" + englishContent);
			}

		} catch (Exception e) {
			e.printStackTrace();
			englishContent = question;
		}


		Map<String, Object> wxUserMap = baseSqlService.getTableById("chat_wxuser", wxUserId);
		Long bladeUserId = MjkjUtils.getMap2Long(wxUserMap, "blade_user_id");
		String chatCode = MjkjUtils.getMap2Str(wxUserMap, "chat_code");

		String link = "";//保存图片地址
		String api_account_id="-1";
		try {
			if (gptModelFlag) {
				AccountUseCouModel accountModel = this.getChatGptKey();//获取聊天 gpt 密钥
				api_account_id=accountModel.getId();

				String dalleImageUrl = chatGPTService.getDALLEImages(englishContent, size);//获取图片
				String PNG = ".png";
				long time = System.currentTimeMillis();
				String qrcodeFileUrl = ChatgptConfig.getUploadUrl();//获取上传网址

				String savePath = qrcodeFileUrl + "/dalle_" + time + PNG;
				// 发送GET请求下载图片
				byte[] imageBytes = HttpUtil.downloadBytes(dalleImageUrl);
				// 将字节数组保存为图片文件
				File myFile = FileUtil.writeBytes(imageBytes, savePath);

				InputStream inputStream = new FileInputStream(myFile);

				MinioBladeFile minioBladeFile = minioUtils.uploadInputStream(time + PNG, inputStream);
				if(Func.isNotEmpty(inputStream)){
					try{
						inputStream.close();
					}catch (Exception e){

					}
				}
				link = minioBladeFile.getLink();//返回的oss图片路径

			} else {
				link = this.getFlagstudioImages(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//保存并且推送
		Date now = DateUtil.now();
		String id = IdWorker.getIdStr();
		//保存消息
		Map<String, Object> insertMap = new HashMap<>();
		insertMap.put("id", id);
		insertMap.put("pid", q_logMessageId);
		insertMap.put("wxuser_id", wxUserId);
		insertMap.put("message_type", MessageType.A);//q =问题  a=答案
		insertMap.put("message_time", now);
		insertMap.put("blade_user_id", bladeUserId);
		if (Func.isEmpty(link)) {//生成图片失败
			insertMap.put("message_content", "图片生成失败");//回答内容
			insertMap.put("view_type", ViewType.TEXT);//类型图片
		} else {
			insertMap.put("message_content", link);//回答内容
			insertMap.put("view_type", ViewType.IMAGE);//类型图片
		}
		insertMap.put("context_flag",0);//不支持上下文
		insertMap.put("model_type", modelType);//分类
		insertMap.put("api_account_id", api_account_id);//账户id
		baseSqlService.baseInsertData("chat_log_message", insertMap);

		//发送到前端
		//算出耗时
		Long useTimeL = now.getTime() - sendTime.getTime();
		BigDecimal useTime = BigDecimal.valueOf(useTimeL).divide(BigDecimal.valueOf(1000L));

		ChatGptMsgModel questionMsgModel = new ChatGptMsgModel();
		questionMsgModel.setId(id);
		questionMsgModel.setPid(q_logMessageId);
		questionMsgModel.setChatCode(chatCode);
		questionMsgModel.setMessage_type(MessageType.A);//q =问题  a=答案
		if (Func.isEmpty(link)) {//生成图片失败
			questionMsgModel.setMessage_content("图片生成失败");//图片
			questionMsgModel.setView_type(ViewType.TEXT);//类型图片
		} else {
			questionMsgModel.setMessage_content(link);//图片
			questionMsgModel.setView_type(ViewType.IMAGE);//类型图片
		}
		questionMsgModel.setMessage_time(now);
		questionMsgModel.setUseTime(useTime.stripTrailingZeros().toPlainString());//耗时
		messageClient.sendChatGptMsg(questionMsgModel);
	}

	/**
	 * 发送实时消息，长连接等着返回
	 *
	 * @param
	 * @return
	 */
	@Override
	public String sendNowTimeChatGptTurboMessage(List<MessageModel> messagesList) {
		List<ChatGptTurboResult.ChoiceModel> choices = null;
		try {
			choices = this.getChatGptTurboResponse(messagesList, null);
		} catch (Exception e) {
			return "";
		}

		if (Func.isEmpty(choices)) {
			return "";
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

	//获取图片
	@Override
	public String getDALLEImages(String prompt, String size) {
		String url = "https://api.openai.com/v1/images/generations";
		AccountUseCouModel accountModel = this.getChatGptKey();


		ChatGptPublicParam publicParam = new ChatGptPublicParam();
		publicParam.setKey(accountModel.getApiKey());
		publicParam.setUrl(url);

		Map<String, Object> bodyMap = new HashMap<>();
		bodyMap.put("prompt", prompt);
		bodyMap.put("n", 1);
		bodyMap.put("size", size);
		publicParam.setBody(JSONUtil.toJsonStr(bodyMap));
		String jsonStr = JsonUtil.toJson(publicParam);

		AES aes = SecureUtil.aes(aesKey.getBytes());
		String body = aes.encryptHex(jsonStr);// 加密为16进制表示

		String result = HttpRequest.post(ChatgptConfig.getHttpUrl())
			.header("Content-Type", "application/json;charset:utf-8")
			.body(body).execute().body();
		DALLEImagesResult model = JsonUtil.parse(result, DALLEImagesResult.class);
		Integer code = model.getCode();
		if (code == 200) {
			String resultStr = model.getResultStr();
			DALLEImagesResult.ResultStrModel resultStrModel = JsonUtil.parse(resultStr, DALLEImagesResult.ResultStrModel.class);
			DALLEImagesResult.DataModel dataModel = resultStrModel.getData().get(0);
			String imagesUrl = dataModel.getUrl();
			return imagesUrl;
		}
		throw new ServiceException("获取失败");
	}


	/**
	 * 获取chatgpt 返回内容
	 *
	 * @param messagesList
	 * @return
	 */
	public List<ChatGptTurboResult.ChoiceModel> getChatGptTurboResponse(List<MessageModel> messagesList, AccountUseCouModel accountModel) {
		try {//获取chatgpt 参数
			if (Func.isEmpty(accountModel)) {
				accountModel = this.getChatGptKey();
				if (Func.isEmpty(accountModel)) {
					return null;
				}
			}

			String chaggptModel = ChatgptConfig.getChatgptModel();

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
			for (int i = 0; i < 5; i++) {
				try {
					String result = HttpRequest.post(ChatgptConfig.getHttpUrl())
						.header("Content-Type", "application/json;charset:utf-8")
						.body(body).execute().body();
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

	//flagstudio 生成图片
	@Override
	public String getFlagstudioImages(String prompt) {

		String englishContent = "";
		//翻译成英文
		try {
				List<MessageModel> messagesList = new ArrayList<>();
				MessageModel model1 = new MessageModel();
				model1.setRole(MessageModelRoleType.USER);
				model1.setContent(prompt);
				messagesList.add(model1);

				MessageModel model2 = new MessageModel();
				model2.setRole(MessageModelRoleType.USER);
				model2.setContent("将上面一句话翻译成英文，并且只返回英文");
				messagesList.add(model2);
				englishContent = chatGPTService.sendNowTimeChatGptTurboMessage(messagesList);
				log.info(prompt + "  =====> 翻译成英文=====>" + englishContent);
		} catch (Exception e) {
			e.printStackTrace();
			englishContent = prompt;
		}

		String account = this.getAccount();
		if (Func.isEmpty(account)) {
			return null;
		}
		String token = this.getFlagstudioToken(account);
		if (Func.isEmpty(token)) {
			return null;
		}
		ImageSizeModel imageSize = MjkjUtils.getIMageSize(prompt);


		Map<String, Object> bodyMap = new HashMap<>();
		bodyMap.put("prompt", englishContent);
		bodyMap.put("height", imageSize.getHeight());
		bodyMap.put("width", imageSize.getWidth());
		String imageStyle = ChatgptConfig.getImageStyle();
		if(Func.isNotEmpty(imageStyle)){
			bodyMap.put("style",imageStyle);
		}
		String body = JsonUtil.toJson(bodyMap);

		//参数 http://flagstudio.baai.ac.cn/document#37586eb6befaf0d6ed90255f38dbf5ab
		String url = "https://flagopen.baai.ac.cn/flagStudio/v1/text2img";
		String result = HttpRequest.post(url)
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.header("token", token)
			.body(body)
			.execute().body();
		if (Func.isEmpty(result)) {
			return null;
		}
		FlagstudioImageR imageR = JsonUtil.parse(result, FlagstudioImageR.class);
		Integer code = imageR.getCode();
		if (Func.isEmpty(code) || code != 200) {
			return null;
		}
		String imageData = imageR.getData();//图片地址
		InputStream inputStream = MjkjUtils.base2InputStream(imageData);
		String fileName = IdWorker.getIdStr() + ".png";
		MinioBladeFile minioBladeFile = minioUtils.uploadInputStream(fileName, inputStream);
		if(Func.isNotEmpty(inputStream)){
			try{
				inputStream.close();
			}catch (Exception e){

			}
		}
		//图片上次到100
		return minioBladeFile.getLink();
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
			if (!bladeRedis.exists(redisKey)) {
				bladeRedis.setEx(redisKey, 1, Duration.ofDays(1));//天有效期
				return account;
			}
			Integer num = bladeRedis.get(account);
			if(Func.isEmpty(num)){
				num=1;
			}
			if (num >= 490) {//换下一个账号
				continue;
			}
			++num;
			bladeRedis.setEx(redisKey, num, Duration.ofDays(1));//天有效期
			return account;
		}
		return null;

	}

	//官方有效期为30天
	private String getFlagstudioToken(String account) {
		/*String redisKey = "token:flagstudio:" + account;
		if (bladeRedis.exists(redisKey)) {
			return bladeRedis.get(redisKey);
		}*/

		String url = "https://flagopen.baai.ac.cn/flagStudio/auth/getToken?apikey=" + account;
		String result = HttpRequest.get(url)
			.header("Accept", "application/json")
			.execute().body();
		if (Func.isEmpty(result)) {
			return null;
		}
		FlagstudioTokenR tokenR = JsonUtil.parse(result, FlagstudioTokenR.class);
		if (Func.isEmpty(tokenR)) {
			return null;
		}
		Integer code = tokenR.getCode();
		if (Func.isEmpty(code) || code != 200) {
			return null;
		}
		FlagstudioTokenR.dataModel data = tokenR.getData();
		String token = data.getToken();
		//bladeRedis.setEx(redisKey, token, Duration.ofDays(29));//29天，官方是30天到期
		return token;

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
			if (bladeRedis.exists(redisKey)) {
				cou = bladeRedis.get(redisKey);
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
				bladeRedis.setEx(redisKey, ++cou, 7200L);//2小时
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
				bladeRedis.setEx(redisKey, ++cou, 7200L);//2小时
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
	 * @return
	 */
	private String handleContext(String wxUserId, String question, String logMessageId, Long startMessageId) {
		String sendChatGptQuestion = "";
		QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("wxuser_id", wxUserId);
		queryWrapper.eq("is_deleted", 0);
		queryWrapper.eq("view_type", "text");//文本类型
		queryWrapper.ne("id", logMessageId);//不等于
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
	 * @return
	 */
	private List<MessageModel> handleTurboContext(String wxUserId, String question, String logMessageId, Long startMessageId) {
		List<MessageModel> resultModelList = new ArrayList<>();

		QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("wxuser_id", wxUserId);
		queryWrapper.eq("is_deleted", 0);
		queryWrapper.eq("view_type", "text");//文本类型
		queryWrapper.eq("context_flag",1);//仅支持上下文
		queryWrapper.ne("id", logMessageId);//不等于
		queryWrapper.gt("id+0", startMessageId);//大于
		queryWrapper.orderByAsc("id+0");
		List<Map<String, Object>> dataMapList = baseSqlService.getDataListByFieldParams("chat_log_message", queryWrapper);
		if (Func.isEmpty(dataMapList)) {
			MessageModel model = new MessageModel();
			model.setRole(MessageModelRoleType.USER);
			model.setContent(question);
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
//			System.out.println("ChatgptConfig.getChatgptMaxToken()="+ChatgptConfig.getChatgptMaxToken());
//			System.out.println("ChatgptConfig.getChatgptRequestMaxToken()="+ChatgptConfig.getChatgptRequestMaxToken());
			//System.out.println("lengthTmp="+lengthTmp);
			if (lengthTmp < ChatgptConfig.getChatgptRequestMaxToken()) {
				break;//跳出死循环
			} else {
				dataMapList.remove(0);//移除第一位
			}
		}
		//上次，加上本次
		MessageModel model = new MessageModel();
		model.setRole(MessageModelRoleType.USER);
		model.setContent(question);
		resultModelList.add(model);
		return resultModelList;
	}

	//获取密钥
	private String getChatGptKeyOld(String wxUserId) {
		Map<String, Object> wxUserMap = baseSqlService.getTableById("chat_wxuser", wxUserId);
		String apiAccountId = MjkjUtils.getMap2Str(wxUserMap, "api_account_id");


		if (Func.isNotEmpty(apiAccountId)) {//为空，则重新绑定一个
			Map<String, Object> accountMap = baseSqlService.getTableById("chat_api_account", apiAccountId);
			if (Func.isNotEmpty(accountMap)) {
				return MjkjUtils.getMap2Str(accountMap, "api_key");
			}
		}


		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("gpt_state", 0);
		List<Map<String, Object>> accountMapList = baseSqlService.getDataListByFieldParams("chat_api_account", wrapper);
		if (Func.isEmpty(accountMapList)) {//还没有配置账户
			return null;
		}

		//校验是否存在新的密钥
		for (Map<String, Object> dataMap : accountMapList) {
			String accountId = MjkjUtils.getMap2Str(dataMap, "id");
			Map<String, Object> wuserMap = baseSqlService.getDataOneByField("chat_wxuser", "api_account_id", accountId);
			if (Func.isEmpty(wuserMap)) {//当前是新账户，
				String chatgptKey = MjkjUtils.getMap2Str(dataMap, "api_key");
				//用户绑定机器人
				Map<String, Object> updateMap = new HashMap<>();
				updateMap.put("api_account_id", accountId);
				baseSqlService.baseUpdateData("chat_wxuser", updateMap, wxUserId);
				return chatgptKey;//存在新的账户
			}
		}

		Map<String, Object> couMap = webMapper.getMinAccountCou();
		String accountId = MjkjUtils.getMap2Str(couMap, "api_account_id");
		Map<String, Object> accountMap = baseSqlService.getTableById("chat_api_account", accountId);
		String chatgptKey = MjkjUtils.getMap2Str(accountMap, "api_key");

		//用户绑定机器人
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("api_account_id", accountId);
		baseSqlService.baseUpdateData("chat_wxuser", updateMap, wxUserId);
		return chatgptKey;

	}
}
