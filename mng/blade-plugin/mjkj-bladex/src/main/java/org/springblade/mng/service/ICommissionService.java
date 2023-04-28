package org.springblade.mng.service;

import java.math.BigDecimal;

/**
 * 佣金明细
 */
public interface ICommissionService {

	//处理佣金
	void handleCoissionmm(String orderCode);

	//获取我的佣金比例
	BigDecimal getRate(String wxuserId, Integer level);
}
