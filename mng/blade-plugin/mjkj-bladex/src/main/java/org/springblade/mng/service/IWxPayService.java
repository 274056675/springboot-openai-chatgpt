package org.springblade.mng.service;

import java.util.Map;

/**
 * 微信支付相关
 */
public interface IWxPayService {

	//自动处理审核
	String handleAutoWithdrawal(String withdrawalLogId);

	/**
	 * 商品下单
	 * @param goodsId
	 * @param type qrcode=二维码  其他为小程序
	 * @return
	 */
	Map<String,String> addOrder(String goodsId,String type);
	//订单付款成功回调
	void payOrderSuccess(String orderCode,String payCode);

}
