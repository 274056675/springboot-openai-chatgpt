
package org.springblade.mng.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.ChatgptConfig;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RandomType;
import org.springblade.mng.config.miniapp.WxPayProperties;
import org.springblade.mng.param.WxOpenBindPhoneParam;
import org.springblade.mng.param.WxopenAuthParam;
import org.springblade.mng.utils.imagecode.SlidePuzzleUtil;
import org.springblade.mng.utils.imagecode.SliderPuzzleInfo;
import org.springblade.mng.service.*;
import org.springblade.system.user.entity.UserInfo;
import org.springblade.system.user.entity.WxOpenParam;
import org.springblade.system.user.feign.IMjkjUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.*;


@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("chat/open")
@Api(value = "公共开放接口", tags = "公共开放接口")
public class OpenController {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private BladeRedis bladeRedis;

	@Autowired
	private ISmsService smsService;

	@Autowired
	private IMjkjUserClient mjkjUserClient;

	@Autowired
	private IWebService webService;

	@Lazy
	@Autowired
	private WxPayProperties wxPayProperties;

	@Autowired
	private IWxPayService wxPayService;


	@ApiOperationSupport(order = 2)
	@GetMapping({"/cssz/list"})
	@ApiOperation(value = "获取参数设置列表", notes = "获取参数设置列表")
	public R getCsszMapList() {
		List<Map<String, Object>> dataMapList = baseSqlService.getDataByTable("chat_cssz");
		return R.data(dataMapList);
	}


	@ApiOperationSupport(order = 10)
	@PostMapping({"/send/sms"})
	@ApiOperation(value = "发送短信验证码", notes = "发送短信验证码与滑块效验")
	public R sendSms(String phone, Integer movePosX) {
		if (Func.isEmpty(phone)) {
			return R.fail("手机号码不允许为空");
		}
		if (phone.length() != 11) {
			return R.fail("手机号码不正确");
		}

		String redisKey = "imagecode:" + phone;
		try {
			if (!bladeRedis.exists(redisKey)) {
				return R.fail("验证过期，请重试");
			}
			Integer posX = bladeRedis.get(redisKey);
			if (Func.isEmpty(posX) || Func.isEmpty(movePosX)) {
				return R.fail("验证过期，请重试");
			}
			if (Math.abs(posX - movePosX) > 10) {  //偏差大于10
				return R.fail("验证不通过");
			}
		} finally {
			bladeRedis.del(redisKey);
		}

		String code = Func.random(6, RandomType.INT);
		if (Func.equals(ChatgptConfig.getDebug(), "true")) {
			code = "123456";
		}
		boolean flag = smsService.sendSms(phone, code);
		if (flag) {
			return R.data("成功");
		}
		return R.fail("失败");
	}


	@ApiOperation(value = "生成图片")
	@ApiOperationSupport(order = 12)
	@GetMapping(value = "/get/getImageCode")
	public R getImageCode(String phone) throws Exception {
		if (Func.isEmpty(phone)) {
			return R.fail("请输入手机号码");
		}
		File file = new File(ChatgptConfig.getUploadUrl() + "/image.png");
		SliderPuzzleInfo sliderPuzzleInfo = SlidePuzzleUtil.createImage(new FileInputStream(file));
		if (Func.isEmpty(sliderPuzzleInfo)) {
			return R.fail("图片验证码生成失败");
		}
		String redisKey = "imagecode:" + phone;
		bladeRedis.set(redisKey, sliderPuzzleInfo.getPosX());

		sliderPuzzleInfo.setToken(phone);
		sliderPuzzleInfo.setBigImage(null);
		sliderPuzzleInfo.setSmallImage(null);
		return R.data(sliderPuzzleInfo);
	}

	@ApiOperationSupport(order = 13)
	@GetMapping({"/get/notshown"})
	@ApiOperation(value = "APP菜单配置数据查询", notes = "排序和过滤不显示的数据，将其返回")
	public R NotShown() {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.orderByAsc("menu_sort");
		wrapper.eq("menu_show", "Y");
		List<Map<String, Object>> dataMapList = baseSqlService.getDataListByFieldParams("chat_menu_data", wrapper);
		return R.data(dataMapList);
	}



	@ApiOperationSupport(order = 15)
	@GetMapping({"/getwxopen/userinfo"})
	@ApiOperation(value = "获取开放平台用户详情", notes = "获取开放平台用户详情")
	public R getWxOpenUserInfo(String code, String state) {
		if (Func.isEmpty(code) || Func.isEmpty(state)) {
			return R.fail("授权失败");
		}

		AuthResponse authResponse = null;
		try {
			AuthRequest authRequest = MjkjUtils.getAuthRequest(null);
			AuthCallback authCallback = new AuthCallback();
			authCallback.setCode(code);
			authCallback.setState(state);
			authResponse = authRequest.login(authCallback);
		} catch (Exception e) {

		}

		System.out.println("authResponse===========" + JsonUtil.toJson(authResponse));
		if (Func.isEmpty(authResponse) || authResponse.getCode() != 2000) {
			return R.fail("授权失败");
		}

		AuthUser authUser = (AuthUser) authResponse.getData();
		AuthToken token = authUser.getToken();
		String unionId = token.getUnionId();


		String uuid = Func.randomUUID();
		String infoRedisKey = "wxopen:info:" + uuid;
		String loginRedisKey = "wxopen:login:" + uuid;


		boolean bindPhoneFlag = false;//是否绑定用户 false是没有绑定手机号码
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("wx_union_id", unionId);
		Map<String, Object> wxUserMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isNotEmpty(wxUserMap)) {
			bindPhoneFlag = true;
		}

		if (!bindPhoneFlag) {//没有绑定，则需要绑定
			bladeRedis.setEx(infoRedisKey, authUser, Duration.ofDays(7));//7天
		} else {//
			bladeRedis.setEx(loginRedisKey, unionId, Duration.ofDays(1));//1天  登录用
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("bindPhoneFlag", bindPhoneFlag);
		resultMap.put("uuid", uuid);
		return R.data(resultMap);
	}


	@ApiOperationSupport(order = 15)
	@PostMapping({"/bindPhoneByUnionId"})
	@ApiOperation(value = "unionId绑定手机号码", notes = "unionId绑定手机号码")
	public R bindPhoneByUnionId(@RequestBody WxOpenBindPhoneParam param) {
		String uuid = param.getUuid();
		if (Func.isEmpty(uuid)) {
			return R.fail("绑定失败");
		}
		String infoRedisKey = "wxopen:info:" + uuid;
		String loginRedisKey = "wxopen:login:" + uuid;
		AuthUser authUser = bladeRedis.get(infoRedisKey);
		if (Func.isEmpty(authUser)) {
			return R.fail("绑定失败");
		}
		String unionId = authUser.getToken().getUnionId();
		String phone = param.getPhone();
		String code = param.getCode();
		String inviteCode = param.getInviteCode();

		if (Func.isEmpty(unionId) || Func.isEmpty(phone) || Func.isEmpty(code)) {
			return R.fail("绑定失败，参数为空");
		}

		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("wx_union_id", unionId);
		Map<String, Object> wxUserMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isNotEmpty(wxUserMap)) {
			return R.fail("已绑定，无需再次绑定");
		}
		//手机号码校验
		String redisKeySms = "SMS_PHONE:" + phone;
		if (!bladeRedis.exists(redisKeySms)) {
			return R.fail("验证码已过期");
		}
		String redisCode = bladeRedis.get(redisKeySms);
		if (!Func.equals(code, redisCode)) {
			return R.fail("验证码不正确");
		}
		bladeRedis.del(redisKeySms);
		//----------基本校验完成-----------------
		//校验用户是否存在，存在这直接返回
		wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("phone", phone);
		Map<String, Object> phoneMap = baseSqlService.getDataOneByFieldParams("chat_wxuser", wrapper);
		if (Func.isNotEmpty(phoneMap)) {
			String id = MjkjUtils.getMap2Str(phoneMap, "id");
			String wxUnionId = MjkjUtils.getMap2Str(phoneMap, "wx_union_id");
			if (Func.isNotEmpty(wxUnionId) && !Func.equals(wxUnionId, unionId)) {
				return R.fail("绑定失败");
			}
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("wx_union_id", unionId);
			baseSqlService.baseUpdateData("chat_wxuser", updateMap, id);

			//清除
			if (bladeRedis.exists(infoRedisKey)) {
				bladeRedis.del(infoRedisKey);
			}
			bladeRedis.setEx(loginRedisKey, unionId, Duration.ofDays(1));//1天  登录用
			return R.data("绑定成功");
		}

		//临时存储
		bladeRedis.setEx(loginRedisKey, unionId, Duration.ofDays(1));//1天  登录用，不对外暴漏unionid

		WxOpenParam wxOpenParam = new WxOpenParam();
		wxOpenParam.setType("register");//注册
		wxOpenParam.setUuid(uuid);
		wxOpenParam.setPhone(phone);
		wxOpenParam.setInviteCode(inviteCode);
		R<UserInfo> userInfoR = mjkjUserClient.wxOpen(wxOpenParam);
		if (userInfoR.isSuccess()) {
			//清除
			if (bladeRedis.exists(infoRedisKey)) {
				bladeRedis.del(infoRedisKey);
			}
			return R.data("绑定成功");
		}
		//绑定失败，需要删除登录缓存
		if (bladeRedis.exists(loginRedisKey)) {
			bladeRedis.del(loginRedisKey);
		}
		return R.fail("绑定失败");
	}

	@ApiOperationSupport(order = 2)
	@GetMapping({"/goods/list"})
	@ApiOperation(value = "获取商品列表", notes = "获取商品列表")
	public R getGoodsList() {
		QueryWrapper<Object> goodsWrapper = new QueryWrapper<>();
		goodsWrapper.eq("is_deleted", 0);
		goodsWrapper.eq("goods_status", 0);//上架

		Long userId = AuthUtil.getUserId();
		if (Func.isNotEmpty(userId)) {//当前有登录，判断该人是否购买过会员
			String wxuserId = webService.getWxuserId();
			QueryWrapper<Object> wrapper = new QueryWrapper<>();
			wrapper.eq("is_deleted", 0);
			wrapper.eq("wxuser_id", wxuserId);//微信用户id
			wrapper.eq("pay_status", "1");
			wrapper.select("id");
			List<Map<String, Object>> orderMapList = baseSqlService.getDataListByFieldParams("chat_goods_order", wrapper);

			if (Func.isNotEmpty(orderMapList)) {
				goodsWrapper.ne("is_first", 1);//排除首充
			}
		}
		goodsWrapper.orderByDesc("ind");//序降
		List<Map<String, Object>> dataMapList = baseSqlService.getDataListByFieldParams("chat_goods", goodsWrapper);
		return R.data(dataMapList);
	}


	@ApiOperationSupport(order = 15)
	@PostMapping({"/wxopen/auth"})
	@ApiOperation(value = "微信开放平台授权（网页）", notes = "微信开放平台授权（网页）")
	public R wxopenAuth(@RequestBody WxopenAuthParam param) {
		AuthRequest authRequest = MjkjUtils.getAuthRequest(param.getCallback());

		String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
		return R.data(authorizeUrl);
	}

	@ApiOperationSupport(order = 15)
	@GetMapping({"/pay/payResultSearch"})
	@ApiOperation(value = "支付结果查询", notes = "支付结果查询")
	public R payResultSearch(String orderCode) {
		String redisKey="wxpay:orderCode"+orderCode;
		if(!bladeRedis.exists(redisKey)){
			return R.data(false);//未支付
		}
		String result = bladeRedis.get(redisKey);
		if(Func.isNotEmpty(result) && Func.equals(result,"PAY")){
			return R.data(true);//true
		}else if (Func.isNotEmpty(result) && Func.equals(result,"NOPAY")){
			return R.data(false);
		}
		return R.data(false);
	}

	@ApiOperation("微信支付成功回调")
	@ApiOperationSupport(order = 3)
	@RequestMapping(value = "/wxPay/payNotify", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String payNotify(HttpServletRequest request) {
		String xmlMsg = HttpKit.readData(request);
		Map<String, String> params = WxPayKit.xmlToMap(xmlMsg);

		String returnCode = params.get("return_code");

		String out_trade_no = params.get("out_trade_no");//商户订单号
		String transaction_id = params.get("transaction_id");//微信支付订单号
		// 注意重复通知的情况，同一订单号可能收到多次通知，请注意一定先判断订单状态
		// 注意此处签名方式需与统一下单的签名类型一致
		if (WxPayKit.verifyNotify(params, wxPayProperties.getPartnerKey(), SignType.HMACSHA256)) {
			if (WxPayKit.codeIsOk(returnCode)) {
				try {
					wxPayService.payOrderSuccess(out_trade_no, transaction_id);

					// 发送通知等
					Map<String, String> xml = new HashMap<String, String>(2);
					xml.put("return_code", "SUCCESS");
					xml.put("return_msg", "OK");
					return WxPayKit.toXml(xml);
				} catch (Exception e) {
					Map<String, String> xml = new HashMap<String, String>(2);
					xml.put("return_code", "FAIL");
					xml.put("return_msg", "订单有误");
					return WxPayKit.toXml(xml);
				}
			}
		}
		return null;
	}

}

