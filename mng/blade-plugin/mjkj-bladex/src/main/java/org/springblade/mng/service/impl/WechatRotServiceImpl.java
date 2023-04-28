package org.springblade.mng.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.ChatgptConfig;
import org.springblade.config.util.minio.MinioBladeFile;
import org.springblade.config.util.minio.MinioUtils;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.model.AccountUseCouModel;
import org.springblade.mng.model.ChatGptTurboResult;
import org.springblade.mng.model.MessageModel;
import org.springblade.mng.model.MessageModelRoleType;
import org.springblade.mng.param.wechatrot.WechatUserParam;
import org.springblade.mng.service.IChatGPTService;
import org.springblade.mng.service.IWebService;
import org.springblade.mng.service.IWechatRotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 微信机器人
 */
@Slf4j
@Service
public class WechatRotServiceImpl implements IWechatRotService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IWebService webService;

	@Autowired
	private IChatGPTService chatGPTService;

	@Autowired
	private MinioUtils minioUtils;

	//机器人发送消息
	@Override
	public String sendText(WechatUserParam param){
		String wechatId = param.getWechatId();
		String wechatName = param.getWechatName();
		//1.判断用户是否存在
		Map<String, Object> wechatbotUserMap = baseSqlService.getDataOneByField("chat_wechatbot_user", "wechatbot_user_name", wechatName);
		if(Func.isEmpty(wechatbotUserMap)){//新增用户
			wechatbotUserMap=new HashMap<>();
			wechatbotUserMap.put("id", IdWorker.getId());
			wechatbotUserMap.put("wechatbot_user_id",wechatId);
			wechatbotUserMap.put("wechatbot_user_name",param.getWechatName());
			wechatbotUserMap.put("wechatbot_user_type",param.getWechatMessageType());
			baseSqlService.baseInsertData("chat_wechatbot_user",wechatbotUserMap);
		}
		Date now = DateUtil.now();
		String wechatbotUserId = MjkjUtils.getMap2Str(wechatbotUserMap, "id");
		//2.存储发送问题
		String messageId = IdWorker.getIdStr();
		Map<String,Object> qmessageMap=new HashMap<>();
		qmessageMap.put("id",messageId);
		qmessageMap.put("wechatbot_user_name",param.getWechatName());
		qmessageMap.put("wechatbot_message_id",param.getWechatMessageId());
		qmessageMap.put("wechatbot_user_id",wechatbotUserId );
		qmessageMap.put("message_content_q",param.getWechatContent());
		qmessageMap.put("message_time_q",now);
		baseSqlService.baseInsertData("chat_wechatbot_message",qmessageMap);

		//3.校验是否有铭感词
		boolean sensitiveWordFlag = webService.checkSensitiveWord(param.getWechatContent());
		if(!sensitiveWordFlag){//存在敏感词，直接返回
			String answerStr = webService.getCsszVal("sensitive_word_msg", "您发送的内容存在敏感词");

			Map<String,Object> updateMap=new HashMap<>();
			updateMap.put("message_content_a",answerStr);
			updateMap.put("message_time_a",now);
			baseSqlService.baseUpdateData("chat_wechatbot_message",updateMap,messageId);
			return answerStr;
		}
		//4,没有存在铭感词，封装上下文
		List<MessageModel> messagesList = this.handleTurboContext(wechatbotUserId, param.getWechatContent(),MjkjUtils.getMap2Str(qmessageMap,"id"));
		List<ChatGptTurboResult.ChoiceModel> choices =null;
		try{
			AccountUseCouModel accountModel = chatGPTService.getChatGptKey();
			choices = chatGPTService.getChatGptTurboResponse(messagesList,accountModel);
		}catch (Exception e){
			return null;
		}
		if(Func.isEmpty(choices)){
			return null;
		}
		ChatGptTurboResult.ChoiceModel choiceModel = choices.get(0);
		ChatGptTurboResult.MessageModel message = choiceModel.getMessage();
		String content = message.getContent();

		Date timea = DateUtil.now();
		Map<String,Object> updateMap=new HashMap<>();
		updateMap.put("message_content_a",content);
		updateMap.put("message_time_a",timea);
		baseSqlService.baseUpdateData("chat_wechatbot_message",updateMap,messageId);
		return content;
	}

	//机器人发送图片
	@Override
	public String sendImage(WechatUserParam param,String size,boolean autoEnglish){
		String imageModel = ChatgptConfig.getImageModelWechatRot();//机器人模型
		boolean gptModelFlag=true;
		if(Func.equals(imageModel,"flagstudio")){
			gptModelFlag=false;//不是gpt模型
		}

		String englishContent="";
		if(gptModelFlag && autoEnglish){//gpt 模型 并且翻译成英文
			try{
				List<MessageModel> messagesList=new ArrayList<>();
				String wechatContent = param.getWechatContent();
				MessageModel model1=new MessageModel();
				model1.setRole(MessageModelRoleType.USER);
				model1.setContent(wechatContent);
				messagesList.add(model1);

				MessageModel model2=new MessageModel();
				model2.setRole(MessageModelRoleType.USER);
				model2.setContent("将上面一句话翻译成英文，并且只返回英文");
				messagesList.add(model2);
				englishContent = chatGPTService.sendNowTimeChatGptTurboMessage(messagesList);
				log.info(wechatContent+"  =====> 翻译成英文=====>"+englishContent);
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		String wechatId = param.getWechatId();
		String wechatName = param.getWechatName();
		//1.判断用户是否存在
		Map<String, Object> wechatbotUserMap = baseSqlService.getDataOneByField("chat_wechatbot_user", "wechatbot_user_name", wechatName);
		if(Func.isEmpty(wechatbotUserMap)){//新增用户
			wechatbotUserMap=new HashMap<>();
			wechatbotUserMap.put("id", IdWorker.getId());
			wechatbotUserMap.put("wechatbot_user_id",wechatId);
			wechatbotUserMap.put("wechatbot_user_name",param.getWechatName());
			wechatbotUserMap.put("wechatbot_user_type",param.getWechatMessageType());
			baseSqlService.baseInsertData("chat_wechatbot_user",wechatbotUserMap);
		}
		Date now = DateUtil.now();
		String wechatbotUserId = MjkjUtils.getMap2Str(wechatbotUserMap, "id");
		//2.存储发送问题
		String messageId = IdWorker.getIdStr();
		Map<String,Object> qmessageMap=new HashMap<>();
		qmessageMap.put("id",messageId);
		qmessageMap.put("wechatbot_user_name",param.getWechatName());
		qmessageMap.put("wechatbot_message_id",param.getWechatMessageId());
		qmessageMap.put("wechatbot_user_id",wechatbotUserId );
		qmessageMap.put("message_content_q",param.getWechatContent());
		qmessageMap.put("message_time_q",now);
		baseSqlService.baseInsertData("chat_wechatbot_message",qmessageMap);

		//3.校验是否有铭感词
		boolean sensitiveWordFlag = webService.checkSensitiveWord(param.getWechatContent());
		if(!sensitiveWordFlag){//存在敏感词，直接返回
			String answerStr = webService.getCsszVal("sensitive_word_msg", "您发送的内容存在敏感词");

			Map<String,Object> updateMap=new HashMap<>();
			updateMap.put("message_content_a",answerStr);
			updateMap.put("message_time_a",now);
			baseSqlService.baseUpdateData("chat_wechatbot_message",updateMap,messageId);
			return answerStr;
		}
		//4,图片没有上下文
		try{
			String link ="";
			if(gptModelFlag){
				String prompt ="";
				if(autoEnglish && Func.isNotEmpty(englishContent)){
					prompt =englishContent;
				}else{
					prompt =param.getWechatContent();
				}
				if(Func.isEmpty(size)){
					size = "256x256";
				}
				String dalleImageUrl = chatGPTService.getDALLEImages(prompt, size);

				String PNG=".png";
				long time = System.currentTimeMillis();
				String qrcodeFileUrl = ChatgptConfig.getUploadUrl();

				String savePath = qrcodeFileUrl+"/dalle_"+time+PNG;
				// 发送GET请求下载图片
				byte[] imageBytes = HttpUtil.downloadBytes(dalleImageUrl);
				// 将字节数组保存为图片文件
				File myFile = FileUtil.writeBytes(imageBytes, savePath);


				InputStream inputStream = new FileInputStream(myFile);

				MinioBladeFile bladeFile = minioUtils.uploadInputStream(time + PNG, inputStream);
				link = bladeFile.getLink();
			}else{
				//国产
				link = chatGPTService.getFlagstudioImages(param.getWechatContent());
			}
			Map<String,Object> updateMap=new HashMap<>();
			updateMap.put("message_content_a",link);
			updateMap.put("message_time_a",DateUtil.now());
			baseSqlService.baseUpdateData("chat_wechatbot_message",updateMap,messageId);
			return link;
		}catch (Exception e){

		}
		return null;
	}


	/**
	 * 3.5版本
	 * 处理上下文 获取最近10条
	 * @return
	 */
	private List<MessageModel> handleTurboContext(String wechatbotUserId, String question,String logMessageId) {
		List<MessageModel> resultModelList = new ArrayList<>();

		Page<Map<String, Object>> page = new Page<>(1, 10);//最近10条
		QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("wechatbot_user_id", wechatbotUserId);
		queryWrapper.ne("id", logMessageId);//不等于
		queryWrapper.eq("is_deleted", 0);
		queryWrapper.orderByDesc("id+0");//倒序
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_wechatbot_message", page, queryWrapper);
		List<Map<String, Object>> tmpMapList = pages.getRecords();
		if (Func.isEmpty(tmpMapList)) {
			MessageModel model = new MessageModel();
			model.setRole(MessageModelRoleType.USER);
			model.setContent(question);
			resultModelList.add(model);
			return resultModelList;
		}
		List<Map<String, Object>> dataMapList=new ArrayList<>();
		//反转
		Collections.reverse(tmpMapList);
		dataMapList.addAll(tmpMapList);

		//最大数量不能大于
		while (true) {
			Integer lengthTmp = question.length();
			resultModelList = new ArrayList<>();
			List<MessageModel> tmpModelList = new ArrayList<>();
			for (int i = 0; i < dataMapList.size(); i++) {
				Map<String, Object> dataMap = dataMapList.get(i);
				String message_content_q = MjkjUtils.getMap2Str(dataMap, "message_content_q");
				String message_content_a = MjkjUtils.getMap2Str(dataMap, "message_content_a");
				if(Func.isNotEmpty(message_content_q) && !message_content_q.startsWith("画")){
					MessageModel model = new MessageModel();
					model.setRole(MessageModelRoleType.USER);
					model.setContent(message_content_q);
					resultModelList.add(model);
					tmpModelList.add(model);
				}
				if(Func.isNotEmpty(message_content_a) && !message_content_a.startsWith("https")){
					MessageModel model = new MessageModel();
					model.setRole(MessageModelRoleType.CHATGPT);
					model.setContent(message_content_a);
					resultModelList.add(model);
					tmpModelList.add(model);
				}
				if(Func.isEmpty(tmpModelList)){
					continue;
				}


				if(i==dataMapList.size()-1){//把本次的加上
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
}
