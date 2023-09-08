package org.springblade.mng.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.ErnieBotConfig;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.model.*;
import org.springblade.mng.service.IErnieBotService;
import org.springblade.plugin.message.feign.IMessageClient;
import org.springblade.plugin.message.model.ChatGptMsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

/**
 * @author JX
 * @create 2023-09-08 14:34
 * @dedescription: 文心一言 接口的实现类
 */
@Slf4j
@Service
public class ErnieBotServiceImpl implements IErnieBotService {

	@Autowired
	private BladeRedis bladeRedis;

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IMessageClient messageClient;

	@Override
//	@Async("asyncPoolTaskExecutor")
	public void sendErnieBotTurboMessage(String wxUserId, String q_logMessageId, String question, Long startMessageId, Date sendTime) {
		Map<String, Object> wxUserMap = baseSqlService.getTableById("chat_wxuser", wxUserId);
		Long bladeUserId = MjkjUtils.getMap2Long(wxUserMap, "blade_user_id");
		String chatCode = MjkjUtils.getMap2Str(wxUserMap, "chat_code");
		String appKey = ErnieBotConfig.appKey;
		String secretKey = ErnieBotConfig.secretKey;
		ErnieAccessToken ernieAccessToken = new ErnieAccessToken();
		ernieAccessToken.setAccess_token(this.getAccessToken(appKey,secretKey));

		if (Func.isEmpty(ernieAccessToken.getAccess_token())) {
			return  ;
		}

		//处理上下文
		String errorTip = "";
		List<MessageModel> messagesList = null;
		try {
			messagesList = this.handleErnieBotContext(wxUserId, question, q_logMessageId, startMessageId);
		} catch (Exception e) {
			errorTip = e.getMessage();
		}
		String result = null;


		try {
			if (Func.isEmpty(errorTip)) {//前面没有错误
				result = this.getErnieBotTurboResponse(messagesList, ernieAccessToken.getAccess_token());
			}
		} catch (Exception e) {
			String error_message = e.getMessage();
			if(Func.isNotEmpty(error_message)){
				errorTip="出错了";


			}
		}

		String view_type = ViewType.TEXT;
		if (Func.isNotEmpty(errorTip)) {//存在错误
			view_type = ViewType.ERROR;
			MessageModel messageModel = new MessageModel();
			String errTip = errorTip;
			messageModel.setContent(errTip);
			result = this.getErnieBotTurboResponse(messagesList, ernieAccessToken.getAccess_token());

		} else if (Func.isEmpty(result)) {//其他原因
			view_type = ViewType.ERROR;
			MessageModel messageModel = new MessageModel();
			messageModel.setContent(errorTip);
		}

		Date now = DateUtil.now();

		String id = IdWorker.getIdStr();
		//保存消息
		Map<String, Object> insertMap = new HashMap<>();
		insertMap.put("id", id);
		insertMap.put("pid", q_logMessageId);
		insertMap.put("wxuser_id", wxUserId);
		insertMap.put("message_type", MessageType.A);//q =问题  a=答案
		insertMap.put("message_content", result);//回答内容
		insertMap.put("message_time", now);
		insertMap.put("blade_user_id", bladeUserId);
		insertMap.put("view_type", view_type);
		insertMap.put("model_type", 0);//分类
		insertMap.put("api_account_id", ernieAccessToken.getId());//账户id
		insertMap.put("context_flag", 1);//支持上下文
		baseSqlService.baseInsertData("chat_log_message", insertMap);

		Long useTimeL = now.getTime() - sendTime.getTime();
		BigDecimal useTime = BigDecimal.valueOf(useTimeL).divide(BigDecimal.valueOf(1000L));
		ChatGptMsgModel questionMsgModel = new ChatGptMsgModel();
		questionMsgModel.setId(id);
		questionMsgModel.setPid(q_logMessageId);
		questionMsgModel.setChatCode(chatCode);
		questionMsgModel.setMessage_type(MessageType.A);//q =问题  a=答案
		questionMsgModel.setMessage_content(result);
		questionMsgModel.setMessage_time(now);
		questionMsgModel.setView_type(view_type);
		questionMsgModel.setUseTime(useTime.stripTrailingZeros().toPlainString());//耗时
		messageClient.sendChatGptMsg(questionMsgModel);
	}

	@Override
	public String getErnieBotTurboResponse(List<MessageModel> messagesList, String ernieToken) {
		try {
			if (Func.isEmpty(ernieToken)) {
				ernieToken=this.getAccessToken(ErnieBotConfig.appKey,ErnieBotConfig.secretKey);
			}


			String ErnieBotUrl = ErnieBotConfig.URL+ernieToken;  //请求地址

			if( messagesList.size()%2 == 0 && messagesList.size() != 0) {
				messagesList.remove(0);
			}

			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("messages", messagesList);
			String jsonStr = JsonUtil.toJson(bodyMap);

			String resultStr = "";
			String errorTip = "";
			//最多发送5次
			for (int i = 0; i < 5; i++) {
				resultStr = "";
				errorTip = "";
				try {
					String result = HttpRequest.post(ErnieBotUrl)
						.header("Content-Type", "application/json;charset:utf-8")
						.body(jsonStr).execute().body();

					if (Func.isEmpty(result)) {//为空，重新发送
						Thread.sleep(1000);
						continue;
					}
					ErnieBotTurboResult resultModel = JsonUtil.parse(result, ErnieBotTurboResult.class);
					if (Func.isEmpty(resultModel)) {
						Thread.sleep(1000);
						continue;//中途失败
					}

					resultStr = resultModel.getResult();
					errorTip  = resultModel.getError_msg();
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

			//		为空
			if (Func.isEmpty(resultStr)) {
				return null;
			}
			return resultStr;
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<MessageModel> handleErnieBotContext(String wxUserId, String question, String logMessageId, Long startMessageId) {
		List<MessageModel> resultModelList = new ArrayList<>();

		if (question.length() > ErnieBotConfig.getErnieBotMaxToken()) {
			throw new ServiceException("你发送的内容过长");
		}

		QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("wxuser_id", wxUserId);
		queryWrapper.eq("is_deleted", 0);
		queryWrapper.eq("view_type", "text");//文言一心的接口目前仅支持文本类型
		queryWrapper.eq("context_flag", 1);//仅支持上下文
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

		//最后一个message的content长度（即此轮对话的问题）不能超过11200个字符；如果messages中content总长度大于11200字符，系统会依次遗忘最早的历史会话，直到content的总长度不超过11200个字符
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

			if (lengthTmp < ErnieBotConfig.getErnieBotMaxToken()) {
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
		return resultModelList;
	}

	@Override
	public String getAccessToken(String appKey, String secretKey) {

		String redisErinetoken = "token:erniebot:" + appKey;

		if (bladeRedis.exists(redisErinetoken)) {
			return bladeRedis.get(redisErinetoken);
		}

		String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + appKey + "&client_secret=" + secretKey;
		String result = HttpRequest.post(url)
			.header("Content-Type", "application/json")
			.header("Accept", "application/json")
			.execute().body();
		if (Func.isEmpty(result)) {
			return null;
		}

		ErnieAccessToken tokenR = JSONUtil.toBean(result, ErnieAccessToken.class);

		if (Func.isEmpty(tokenR)) {
			return null;
		}

		String error = tokenR.getError();

		if(Func.isNotEmpty(error)){
			return null;
		}
		String token = tokenR.getAccess_token();

		//官方说token的有效期是30天，这里给他存29天
		bladeRedis.setEx(redisErinetoken, token, Duration.ofDays(29));

		return token;


	}

}
