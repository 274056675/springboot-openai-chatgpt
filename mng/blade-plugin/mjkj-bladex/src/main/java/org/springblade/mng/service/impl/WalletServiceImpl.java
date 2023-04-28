package org.springblade.mng.service.impl;

import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 佣金明细 相关
 */

@Service
public class WalletServiceImpl implements IWalletService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;


	//获取用户余额
	@Override
	public BigDecimal getAmount(String wxuserId){
		synchronized (this) {
			Map<String, Object> walletMap = baseSqlService.getDataOneByField("chat_wxuser_wallet", "wxuser_id", wxuserId);
			if (Func.isEmpty(walletMap)) {
				Map<String, Object> addMap = new HashMap<>();
				addMap.put("wxuser_id", wxuserId);
				addMap.put("amount", BigDecimal.ZERO);
				baseSqlService.baseInsertData("chat_wxuser_wallet", addMap);
				walletMap = baseSqlService.getDataOneByField("chat_wxuser_wallet", "wxuser_id", wxuserId);
			}
			return MjkjUtils.getMap2BigD(walletMap,"amount");//用户金额
		}
	}


	@Override
	public void addAmount(String wxuserId, BigDecimal amount,String serviceType){
		this.addAmount(wxuserId,amount,serviceType,null);
	}


	//增加钱包金额
	@Override
	public void addAmount(String wxuserId, BigDecimal amount, String serviceType, String remark){
		this.operateAmount("ADD",wxuserId,amount,serviceType,remark);
	}

	@Override
	public void subAmount(String wxuserId, BigDecimal amount,String serviceType){
		this.subAmount(wxuserId,amount,serviceType,null);
	}
	//减少钱包金额
	@Override
	public void subAmount(String wxuserId, BigDecimal amount,String serviceType,String remark){
		this.operateAmount("SUB",wxuserId,amount,serviceType,remark);
	}


	//操作钱包金额
	@Transactional(rollbackFor = Exception.class)
	public void operateAmount(String type,String wxuserId, BigDecimal amount,String serviceType,String remark){
		synchronized (this){
			Map<String, Object> walletMap = baseSqlService.getDataOneByField("chat_wxuser_wallet", "wxuser_id", wxuserId);
			if(Func.isEmpty(walletMap)){
				Map<String,Object> addMap=new HashMap<>();
				addMap.put("wxuser_id",wxuserId);
				addMap.put("amount",BigDecimal.ZERO);
				baseSqlService.baseInsertData("chat_wxuser_wallet",addMap);
				walletMap = baseSqlService.getDataOneByField("chat_wxuser_wallet", "wxuser_id", wxuserId);
			}

			String walletId = MjkjUtils.getMap2Str(walletMap, "id");
			BigDecimal amount_befor = MjkjUtils.getMap2BigD(walletMap, "amount");
			BigDecimal amount_after=BigDecimal.ZERO;
			if(Func.equals(type,"ADD")) {
				amount_after = amount_befor.add(amount.abs());
			}else if(Func.equals(type,"SUB")){
				amount_after = amount_befor.subtract(amount.abs());
			}
			if(amount_after.compareTo(BigDecimal.ZERO)==-1){
				throw new ServiceException("余额有误，余额小于0");
			}

			//更新钱包
			Map<String,Object> updateMap=new HashMap<>();
			updateMap.put("amount",amount_after);
			baseSqlService.baseUpdateData("chat_wxuser_wallet",updateMap,walletId);

			//写入操作记录
			Map<String,Object> addMap=new HashMap<>();
			addMap.put("wxuser_id",wxuserId);
			addMap.put("wxuser_wallet_id",walletId);
			addMap.put("type",type);
			addMap.put("service_type",serviceType);
			addMap.put("amount",amount);
			addMap.put("before_amount",amount_befor);
			addMap.put("after_amount",amount_after);
			addMap.put("remark",remark);
			baseSqlService.baseInsertData("chat_wxuser_wallet_history",addMap);
		}

	}
}
