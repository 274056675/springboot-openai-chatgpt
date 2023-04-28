package org.springblade.mng.service;

import java.math.BigDecimal;

/**
 * 钱包相关
 */
public interface IWalletService {

	//获取用户余额
	BigDecimal getAmount(String wxuserId);

	//增加钱包金额  serviceType   0=佣金 1=提现 2=退还
	void addAmount(String wxuserId, BigDecimal amount,String serviceType);
	void addAmount(String wxuserId, BigDecimal amount,String serviceType,String remark);


	//减少钱包金额 serviceType   0=佣金 1=提现 2=退还
	void subAmount(String wxuserId, BigDecimal amount,String serviceType);
	void subAmount(String wxuserId, BigDecimal amount,String serviceType,String remark);

}
