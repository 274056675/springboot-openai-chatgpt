package org.springblade.mng.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.QrCodeKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.model.TransferModel;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.ChatgptConfig;
import org.springblade.config.util.minio.MinioBladeFile;
import org.springblade.config.util.minio.MinioUtils;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.mng.config.miniapp.WxPayProperties;
import org.springblade.mng.service.ICommissionService;
import org.springblade.mng.service.IWebService;
import org.springblade.mng.service.IWxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付相关 相关
 */

@Service
public class WxPayServiceImpl implements IWxPayService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Lazy
	@Autowired
	private WxPayProperties wxPayProperties;//微信支付

	@Lazy
	@Autowired
	private IWebService webService;

	@Autowired
	private ICommissionService commissionService;


	@Autowired
	private MinioUtils minioUtils;

	@Autowired
	private BladeRedis bladeRedis;

	/**
	 * 微信回调路径
	 */
	String NOTIFY_URL = "/open-chat/chat/open/wxPay/payNotify";

	//自动处理审核
	@Override
	public String handleAutoWithdrawal(String withdrawalLogId){
		Map<String, Object> withdrawalMap = baseSqlService.getTableById("chat_withdrawal_log", withdrawalLogId);
		if(Func.isEmpty(withdrawalMap)){
			return "提现记录不存在";
		}
		String withdrawalState = MjkjUtils.getMap2Str(withdrawalMap, "withdrawal_state");
		if(!Func.equals(withdrawalState,"0")){
			return "状态不符合";
		}
		//到账金额
		BigDecimal receivedAmount = MjkjUtils.getMap2BigD(withdrawalMap, "received_amount");//元
		String wxuserId = MjkjUtils.getMap2Str(withdrawalMap, "wxuser_id");
		Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
		String openId = MjkjUtils.getMap2Str(wxuserMap, "open_id");

		//获取转账备注
		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_withdrawal_setting", "withdrawal_type", "withdrawal_tip");
		String tip = MjkjUtils.getMap2Str(settingMap, "withdrawal_value");

		//转为分
		long amountFen = receivedAmount.multiply(BigDecimal.valueOf(100)).longValue();

		Map<String, String> params = TransferModel.builder()
			.mch_appid(wxPayProperties.getAppId())
			.mchid(wxPayProperties.getMchId())
			.nonce_str(WxPayKit.generateStr())
			.partner_trade_no(WxPayKit.generateStr())
			.openid(openId)
			.check_name("NO_CHECK")
			.amount(Func.toStr(amountFen))
			.desc(tip)
			.spbill_create_ip(WebUtil.getIP())
			.build()
			.createSign(wxPayProperties.getPartnerKey(), SignType.MD5, false);

		// 提现
		String certPath=wxPayProperties.getCertPath();
		String transfers = WxPayApi.transfers(params, certPath, wxPayProperties.getMchId());

		Map<String, String> map = WxPayKit.xmlToMap(transfers);
		String returnCode = map.get("return_code");
		String resultCode = map.get("result_code");
		if (WxPayKit.codeIsOk(returnCode) && WxPayKit.codeIsOk(resultCode)) {
			// 提现成功
			return "成功";
		} else {
			// 提现失败
			return map.get("err_code_des");
		}
	}

	//商品下单
	@Override
	public Map<String,String> addOrder(String goodsId,String type) {
		Date now = DateUtil.now();

		String wxuserId = webService.getWxuserId();
		Map<String, Object> wuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
		String wxName = MjkjUtils.getMap2Str(wuserMap, "wx_name");
		wxName = Func.isEmpty(wxName) ? "微信用户" : wxName;


		Map<String, Object> goodsMap = baseSqlService.getTableById("chat_goods", goodsId);
		BigDecimal amount = MjkjUtils.getMap2BigD(goodsMap, "price");
		amount = amount.setScale(2);
		if (amount.compareTo(BigDecimal.ZERO) != 1) {
			throw new ServiceException("售价必须大于0");
		}
		String title = MjkjUtils.getMap2Str(goodsMap, "title");
		String body = title;
		Integer day = MjkjUtils.getMap2Integer(goodsMap, "day");//天数

		String orderCode = "ORD_" + IdWorker.getIdStr();
		//下单
		Map<String, Object> orderMap = new HashMap<>();
		orderMap.put("goods_title", title);
		orderMap.put("wxuser_id", wxuserId);
		orderMap.put("wxuser_name", wxName);
		orderMap.put("order_code", orderCode);
		orderMap.put("amount", amount);
		orderMap.put("day", day);
		orderMap.put("order_time", now);
		orderMap.put("chat_goods_id", goodsId);
		orderMap.put("pay_status", 0);//未支付
		orderMap.put("pay_type", 0);//微信
		baseSqlService.baseInsertData("chat_goods_order", orderMap);

		if(Func.isNotEmpty(type) && Func.equals(type,"qrcode")){//走二维码支付
			try{
				String xmlResult = this.addWxQrCodeOrder( body, amount, orderCode);
				Map<String, String> result = WxPayKit.xmlToMap(xmlResult);
				String returnCode = result.get("return_code");
				String returnMsg = result.get("return_msg");
				System.out.println(returnMsg);
				if (!WxPayKit.codeIsOk(returnCode)) {
					throw new ServiceException(returnMsg);
				}
				String resultCode = result.get("result_code");
				if (!WxPayKit.codeIsOk(resultCode)) {
					throw new ServiceException(returnMsg);
				}

				//获取扫码支付url,qrCodeUrl保存二维码的链接
				String qrCodeUrl = result.get("code_url");
				String fileName = IdWorker.getIdStr()+".png";
				String qrcodeUrl = ChatgptConfig.getUploadUrl() + File.separator +"pay"+ File.separator + fileName;
				boolean encode = QrCodeKit.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 3, ErrorCorrectionLevel.H, "png", 200, 200,
					qrcodeUrl);
				File file=new File(qrcodeUrl);
				InputStream inputStream = new FileInputStream(file);

				//上传到阿里云
				MinioBladeFile bladeFile = minioUtils.uploadInputStream(fileName, inputStream);
				if(Func.isNotEmpty(inputStream)){
					try{
						inputStream.close();
					}catch (Exception e){

					}
				}
				if (Func.isNotEmpty(bladeFile)) {
					Map<String,String> resultMap=new HashMap<>();
					resultMap.put("wx_result",bladeFile.getLink());
					resultMap.put("orderCode",orderCode);
					return resultMap;
				}
			}catch (Exception e){

			}
			throw new ServiceException("生成二维码失败");
		}else{//其他走小程序jsapi支付
			String openId = webService.getOpenId();
			String xmlResult = this.addWxOrder(openId, body, amount, orderCode);

			Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

			String returnCode = result.get("return_code");
			String returnMsg = result.get("return_msg");
			if (!WxPayKit.codeIsOk(returnCode)) {
				throw new ServiceException(returnMsg);
			}
			String resultCode = result.get("result_code");
			if (!WxPayKit.codeIsOk(resultCode)) {
				throw new ServiceException(returnMsg);
			}
			// 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
			String prepayId = result.get("prepay_id");

			Map<String, String> packageParams = WxPayKit.prepayIdCreateSign(prepayId, wxPayProperties.getAppId(),
				wxPayProperties.getPartnerKey(), SignType.HMACSHA256);

			String jsonStr = JSON.toJSONString(packageParams);
			Map<String,String> resultMap=new HashMap<>();
			resultMap.put("wx_result",jsonStr);
			resultMap.put("orderCode",orderCode);
			return resultMap;
		}


	}

	//订单付款成功回调
	@Transactional
	@Override
	public void payOrderSuccess(String orderCode, String payCode) {
		Map<String, Object> orderMap = baseSqlService.getDataOneByField("chat_goods_order", "order_code", orderCode);
		if (Func.isEmpty(orderMap)) {
			return;
		}
		String payStatus = MjkjUtils.getMap2Str(orderMap, "pay_status");
		if (!Func.equals(payStatus, "0")) {//当前不是待付款状态
			return;
		}
		Date now = DateUtil.now();

		String goodsId = MjkjUtils.getMap2Str(orderMap, "chat_goods_id");
		String orderId = MjkjUtils.getMap2Str(orderMap, "id");
		String wuserId = MjkjUtils.getMap2Str(orderMap, "wxuser_id");
		Integer day = MjkjUtils.getMap2Integer(orderMap, "day");
		Map<String, Object> wuserMap = baseSqlService.getTableById("chat_wxuser", wuserId);

		Map<String, Object> updateOrderMap = new HashMap<>();
		updateOrderMap.put("pay_code", payCode);
		updateOrderMap.put("pay_time", now);
		updateOrderMap.put("pay_status", "1");//已付款
		baseSqlService.baseUpdateData("chat_goods_order", updateOrderMap, orderId);

		//加入到用户时长里面
		Date expireTime = MjkjUtils.getMap2DateTime(wuserMap, "expire_time");
		if (Func.isEmpty(expireTime) || expireTime.getTime() <= now.getTime()) {//如果已经过期了的话，则从现在开始
			expireTime = now;
		}
		Date newExpireTime = DateUtil.plusDays(expireTime, day);
		Map<String, Object> wuserUpateMap = new HashMap<>();
		wuserUpateMap.put("expire_time", newExpireTime);//新的到期时间
		baseSqlService.baseUpdateData("chat_wxuser", wuserUpateMap, wuserId);

		//写入历史记录
		Map<String, Object> logMap = new HashMap<>();
		logMap.put("wxuser_id", wuserId);
		logMap.put("order_code", orderCode);
		logMap.put("start_time", expireTime);
		logMap.put("end_time", newExpireTime);
		baseSqlService.baseInsertData("chat_log_wxuser_time", logMap);

		//赠送次数
		Map<String, Object> goodsMap = baseSqlService.getTableById("chat_goods", goodsId);
		if (Func.isNotEmpty(goodsMap)) {
			try {
				String giveNum = MjkjUtils.getMap2Str(goodsMap, "give_num");
				Long bladeUserId = MjkjUtils.getMap2Long(wuserMap, "blade_user_id");
				webService.addWxuserQuestionNum(bladeUserId, wuserId, 8, Func.toInt(giveNum), null, "购买会员后奖励");
			} catch (Exception e) {

			}
		}
		String redisKey="wxpay:orderCode"+orderCode;
		bladeRedis.setEx(redisKey,"PAY", Duration.ofDays(7));//改为已支付 7天

		//购买成功，处理分佣金
		commissionService.handleCoissionmm(orderCode);
	}

	/**
	 * 微信统一下单
	 *
	 * @param body       支付标题
	 * @param amount     支付金额
	 * @param outTradeNo 订单号
	 * @return
	 */
	private String addWxOrder(String openId, String body, BigDecimal amount, String outTradeNo) {
		String ip = WebUtil.getIP();
		Long fee = amount.multiply(BigDecimal.valueOf(100D)).setScale(0).longValue();//转为分
		Map<String, String> params = UnifiedOrderModel
			.builder()
			.appid(wxPayProperties.getAppId())
			.mch_id(wxPayProperties.getMchId())
			.nonce_str(WxPayKit.generateStr())
			.openid(openId)
			.body(body)
			.out_trade_no(outTradeNo)
			.total_fee(Func.toStr(fee))
			.spbill_create_ip(ip)
			.notify_url(wxPayProperties.getDomain() + NOTIFY_URL)//微信回调
			.trade_type(TradeType.JSAPI.getTradeType())
			.build()
			.createSign(wxPayProperties.getPartnerKey(), SignType.HMACSHA256);
		String xmlResult = WxPayApi.pushOrder(false, params);
		return xmlResult;
	}

	/**
	 * 微信统一下单 -二维码支付
	 * @param body       会员类型
	 * @param amount     支付金额
	 * @param outTradeNo 订单号
	 **/
	private String addWxQrCodeOrder(String body, BigDecimal amount, String outTradeNo) {
		String ip = WebUtil.getIP();
		Long fee = amount.multiply(BigDecimal.valueOf(100D)).setScale(0).longValue();//转为分
		//生成预付订单success
		Map<String, String> params = UnifiedOrderModel
			.builder()
			.appid(wxPayProperties.getAppId())
			.mch_id(wxPayProperties.getMchId())
			.nonce_str(WxPayKit.generateStr())
			.body(body)
			.out_trade_no(outTradeNo)
			.total_fee(Func.toStr(fee))
			.spbill_create_ip(ip)
			.notify_url(wxPayProperties.getDomain() +NOTIFY_URL)
			.trade_type(TradeType.NATIVE.getTradeType())
			.build()
			.createSign(wxPayProperties.getPartnerKey(), SignType.HMACSHA256);
		String xmlResult = WxPayApi.pushOrder(false, params);
		return xmlResult;
	}
}
