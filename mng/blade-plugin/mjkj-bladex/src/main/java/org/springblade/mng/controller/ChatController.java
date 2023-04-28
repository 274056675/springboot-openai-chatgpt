
package org.springblade.mng.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.freewayso.image.combiner.ImageCombiner;
import com.freewayso.image.combiner.enums.OutputFormat;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.ChatgptConfig;
import org.springblade.config.util.minio.MinioBladeFile;
import org.springblade.config.util.minio.MinioUtils;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.model.MessageModel;
import org.springblade.mng.model.MessageModelRoleType;
import org.springblade.mng.model.WxUserInfoModel;
import org.springblade.mng.param.*;
import org.springblade.mng.service.*;
import org.springblade.mng.utils.ltsms.MakeerWeimaUtils;
import org.springblade.plugin.message.model.ChatGptMsgModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.Duration;
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
	private BladeRedis bladeRedis;

	@Autowired
	private ICommissionService commissionService;


	@Autowired
	private IWalletService walletService;

	@Autowired
	private MinioUtils minioUtils;

	@Autowired
	private IWxPayService wxPayService;


	@ApiOperationSupport(order = 1)
	@GetMapping({"/getWxUserInfo"})
	@ApiOperation(value = "获取用户详情", notes = "获取用户详情")
	public R getWxUserInfo() {
		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		return R.data(wxUsrInfo);
	}

	@ApiOperationSupport(order = 2)
	@GetMapping({"/getPhoneNum"})
	@ApiOperation(value = "获取手机号码", notes = "获取手机号码")
	public R getPhoneNum(String code) {
		String phoneNum = webService.getPhoneNum(code);
		return R.data(phoneNum);
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
		boolean questionNumFlag = webService.getQuestionNumFlag();
		boolean memberFlag = wxUsrInfo.isMemberFlag();
		if (!memberFlag && questionNumFlag) {//当前不是会员 有开启消耗次数
			Integer oneUseNum = webService.getOneQuestionUseNum();//一次消耗多少次
			Integer wuserQuestionCou = webService.getWuserQuestionCou(AuthUtil.getUserId());//用户总次数
			if (oneUseNum.intValue() > wuserQuestionCou.intValue()) {
				return R.fail("可提问次数不足,可通过分享增加可用次数");
			}
		}

		//校验次数-------结束----------

		String modelType = param.getModelType();
		if (Func.isEmpty(modelType)) {
			modelType = "0";
		}

		// 创建 HTTP 请求对
		String question = param.getQuestion();


		List<ChatGptMsgModel> list = webService.sendQuestion(question, startMessageIdL, modelType, memberFlag);
		System.gc();//手动回收垃圾
		return R.data(list);
	}


	@ApiOperationSupport(order = 4)
	@PostMapping({"/message/last"})
	@ApiOperation(value = "获取我的最新消息", notes = "获取我的历史消息")
	public R getMessageLastList(@RequestBody MessageHistoryParam param ) {
		Long startNum = param.getStartNum();
		if (Func.isEmpty(startNum)) {
			return R.fail("参数为空");
		}
		String modelType = param.getModelType();
		if (Func.isEmpty(modelType)) {
			modelType = "0";
		}
		List<Map<String, Object>> dataList = webService.getMessageLastList(startNum, modelType);
		return R.data(dataList);
	}

     /**
	 * 第一个动作，微信勾选后存到数据库chat_log_share_message表中
	 * @param param
	 * @return
	 */
	@ApiOperationSupport(order = 4)
	@PostMapping({"/message/addShareLog"})
	@ApiOperation(value = "获取微信多选后消息并且存入到log表中", notes = "获取我的历史消息")
	public R setShareMessage(@RequestBody ChatLogShareMessageParam param) {
		String wxuserId = webService.getWxuserId();
		String[] messageIds = param.getMessageIds();
		if (Func.isEmpty(messageIds)){
			return R.fail("messageIds参数为空");
		}
		String onlyId = param.getOnlyId();
		if (Func.isEmpty(onlyId)){
			return R.fail("onlyId参数为空");
		}
		webService.addShareLog(wxuserId,param);
		return R.success("添加分享记录成功");
	}



	@ApiOperationSupport(order = 5)
	@PostMapping({"/message/history"})
	@ApiOperation(value = "获取我的历史消息", notes = "获取我的历史消息")
	public R getMessageHistoryList(@RequestBody MessageHistoryParam param) {
		Long startNum = param.getStartNum();
		String modelType = param.getModelType();
		if (Func.isEmpty(modelType)) {
			modelType = "0";
		}
		IPage<Object> page = Condition.getPage(param);
		IPage<Map<String, Object>> pages = webService.getMessageHistoryList(startNum, modelType, page);
		return R.data(pages);
	}


	@ApiOperationSupport(order = 6)
	@PostMapping({"/update/wuserInfo"})
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	public R updateWuserInfo(@RequestBody WuserInfoUpdateParam param) {
		String wxName = param.getWxName();
		String wxAvatar = param.getWxAvatar();
		String phone = param.getPhone();
		if (Func.isEmpty(wxName) && Func.isEmpty(wxAvatar) && Func.isEmpty(phone)) {
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
			updateMap.put("phone", phone);
		}


		baseSqlService.baseUpdateData("chat_wxuser", updateMap, wxuserId);

		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		return R.data(wxUsrInfo);
	}

	@ApiOperationSupport(order = 7)
	@GetMapping(value = "/buy/order")
	@ApiOperation(value = "用户下单", notes = "用户下单")
	public R buyOrder(String goodsId,String type) {//type=qrcode  二维码登录
		Map<String, Object> goodsMap = baseSqlService.getTableById("chat_goods", goodsId);
		if (Func.isEmpty(goodsMap)) {
			return R.fail("商品不存在");
		}
		String goodsStatus = MjkjUtils.getMap2Str(goodsMap, "goods_status");
		if (Func.isEmpty(goodsStatus) || !Func.equals(goodsStatus, "0")) {
			return R.fail("商品状态有误");
		}
		Map<String, String> resultMap = wxPayService.addOrder(goodsId, type);

		return R.data(resultMap.get("wx_result"));
	}

	@ApiOperationSupport(order = 7)
	@GetMapping(value = "/buy/new/order")
	@ApiOperation(value = "用户下单-新", notes = "用户下单-新")
	public R buyNewOrder(String goodsId,String type) {//type=qrcode  二维码登录
		Map<String, Object> goodsMap = baseSqlService.getTableById("chat_goods", goodsId);
		if (Func.isEmpty(goodsMap)) {
			return R.fail("商品不存在");
		}
		String goodsStatus = MjkjUtils.getMap2Str(goodsMap, "goods_status");
		if (Func.isEmpty(goodsStatus) || !Func.equals(goodsStatus, "0")) {
			return R.fail("商品状态有误");
		}
		Map<String, String> resultMap = wxPayService.addOrder(goodsId, type);
		String wx_result = resultMap.get("wx_result");
		String orderCode = resultMap.get("orderCode");

		String redisKey="wxpay:orderCode"+orderCode;
		bladeRedis.setEx(redisKey,"NOPAY", Duration.ofDays(7));//未支付 7天
		return R.data(resultMap);
	}






	@ApiOperation("翻译")
	@ApiOperationSupport(order = 10)
	@PostMapping(value = "/translate")
	public R translate(String messageId) {
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
		Map<String, Object> settingMap = webService.getWxUserSetting(wxuserId);
		String targetLang = MjkjUtils.getMap2Str(settingMap, "translate_lang");
		if (Func.isEmpty(targetLang)) {
			targetLang = "中文";
		}
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
		MessageModel model = new MessageModel();
		model.setRole(MessageModelRoleType.USER);
		model.setContent(messageContent);
		messagesList.add(model);//封装参数

		MessageModel model2 = new MessageModel();
		model2.setRole(MessageModelRoleType.USER);
		model2.setContent("将上面一句话翻译成：" + targetLang);
		messagesList.add(model2);//封装参数
		//开始翻译，实时，不走异步
		String result = chatGPTService.sendNowTimeChatGptTurboMessage(messagesList);

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


	@ApiOperationSupport(order = 12)
	@GetMapping({"/getWxUserSetting"})
	@ApiOperation(value = "获取用户自定义设置", notes = "获取用户自定义设置")
	public R getWxUserSetting() {
		String wxuserId = webService.getWxuserId();
		Long userId = AuthUtil.getUserId();
		//获取个人设置
		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_wxuser_setting", "wxuser_id", wxuserId);
		if (Func.isEmpty(settingMap)) {
			settingMap = new HashMap<>();
			settingMap.put("id", IdWorker.getIdStr());
			settingMap.put("wxuser_id", wxuserId);
			settingMap.put("blade_user_id", userId);
			settingMap.put("translate_lang", "中文");
			baseSqlService.baseInsertData("chat_wxuser_setting", settingMap);
		}
		return R.data(settingMap);
	}


	@ApiOperationSupport(order = 13)
	@PostMapping({"/update/wuserSetting"})
	@ApiOperation(value = "修改用户自定义设置", notes = "修改用户信息")
	public R updateWuserSetting(@RequestBody WuserInfoUpdateParam param) {
		String translateLang = param.getTranslateLang();//默认翻译语言
		String withdrawalQrcode = param.getWithdrawalQrcode();

		if (Func.isEmpty(translateLang) && Func.isEmpty(withdrawalQrcode)) {
			return R.fail("参数不允许为空");
		}


		String wxuserId = webService.getWxuserId();
		Map<String, Object> updateMap = new HashMap<>();
		if (Func.isNotEmpty(translateLang)) {
			updateMap.put("translate_lang", translateLang);
		}
		if(Func.isNotEmpty(withdrawalQrcode)){
			updateMap.put("withdrawal_qrcode", withdrawalQrcode);
		}
		baseSqlService.baseUpdateDataWhere("chat_wxuser_setting", updateMap, "wxuser_id", wxuserId);

		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		return R.data(wxUsrInfo);
	}


	@ApiOperationSupport(order = 21)
	@GetMapping({"/get/chatShare/list"})
	@ApiOperation(value = "获取分享展示数据", notes = "获取分享展示数据")
	public R getChatShare() {

		List<Map<String, Object>> shareData = baseSqlService.getDataByTable("chat_share_data");
		//前端说这个表只会有一条数据，他要对象格式，所以返回第一条数据
		Map<String, Object> shareDataMap = shareData.get(0);

		if(Func.isEmpty(shareDataMap)){
			return R.fail("暂无数据");
		}
		return R.data(shareDataMap);
	}

	@ApiOperationSupport(order = 22)
	@GetMapping({"/generate/h5/qrcode"})
	@ApiOperation(value = "生成推广二维码", notes = "生成推广二维码")
	public  R  generateH5Qrcode()  {
		try {
			WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
			if (Func.isEmpty(wxUsrInfo)) {
				return null;
			}
			//海报地址
			String posterUrl = wxUsrInfo.getPosterUrl();
			if (Func.isNotEmpty(posterUrl)) {
				return R.data(posterUrl);//已经生成过，则直接返回
			}
			String wxName = wxUsrInfo.getWxName();

			String inviteCode = wxUsrInfo.getInviteCode();//用户邀请码

			List<Map<String, Object>> shareData = baseSqlService.getDataByTable("chat_share_data");
			Map<String, Object> shareDataMap = shareData.get(0);
			String httpUrl =MjkjUtils.getMap2Str(shareDataMap,"url");//地址路径



			String posterBgUrl = ChatgptConfig.getPosterBgUrl();

			//背景
			ImageCombiner combiner = new ImageCombiner(posterBgUrl, OutputFormat.PNG);

			//处理二维码
			FileInputStream inputStream = MakeerWeimaUtils.generateQrCode(httpUrl,inviteCode);
			BufferedImage qrcodeImage = ImageIO.read(inputStream);
			combiner.addImageElement(qrcodeImage, 107, 333);

			int length = wxName.length();
			int x = 230 - (length * 10);
			//处理头像
			combiner.addTextElement(wxName, 24, x, 630).setColor(Color.BLACK);
			//进行合成
			combiner.combine();
			combiner.getCombinedImageStream();
			String fileName = IdWorker.getIdStr() + ".png";
			MinioBladeFile bladeFile = minioUtils.uploadInputStream(fileName, combiner.getCombinedImageStream());
			String link = bladeFile.getLink();
			if (Func.isNotEmpty(link)) {//不为空
				Map<String, Object> updateMap = new HashedMap();
				updateMap.put("poster_url", link);
				baseSqlService.baseUpdateData("chat_wxuser", updateMap, wxUsrInfo.getId());
			}
			return R.data(link);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.fail("暂无");
	}
}

