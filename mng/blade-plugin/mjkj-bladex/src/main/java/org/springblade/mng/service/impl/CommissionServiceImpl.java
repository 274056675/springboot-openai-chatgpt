package org.springblade.mng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.service.ICommissionService;
import org.springblade.mng.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 佣金明细 相关
 */

@Service
public class CommissionServiceImpl implements ICommissionService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IWalletService walletService;

	//处理佣金
	@Async("asyncPoolTaskExecutor")
	@Override
	public void handleCoissionmm(String orderCode){
		try{
			Thread.sleep(10000);//休息10秒
			CommissionServiceImpl proxService = SpringContextUtils.getBean(CommissionServiceImpl.class);
			proxService.handleCoissionmmProx(orderCode);
		}catch (Exception e){

		}

	}

	@Transactional(rollbackFor = Exception.class)
	public void handleCoissionmmProx(String orderCode){
		QueryWrapper<Object> wrapper=new QueryWrapper<>();
		wrapper.eq("is_deleted",0);
		wrapper.eq("coissionmm_handle_flag","0");//未处理
		wrapper.eq("pay_status","1");//已付款
		wrapper.eq("order_code",orderCode);
		Map<String, Object> orderMap = baseSqlService.getDataOneByFieldParams("chat_goods_order", wrapper);
		if(Func.isEmpty(orderMap)){//订单不存在或者已处理
			return;
		}
		String orderId = MjkjUtils.getMap2Str(orderMap, "id");
		BigDecimal amount = MjkjUtils.getMap2BigD(orderMap, "amount");//金额

		//校验是否开启分佣功能
		boolean switchFlag = this.getSwitchFlag();
		if(!switchFlag){//没有开启
			//进行处理
			Map<String,Object> updateMap=new HashMap<>();
			updateMap.put("coissionmm_amount",0);//佣金为0
			updateMap.put("coissionmm_handle_flag",1);//已处理
			baseSqlService.baseUpdateData("chat_goods_order",updateMap,orderId);
			return;
		}


		String wxuserId = MjkjUtils.getMap2Str(orderMap, "wxuser_id");
		Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", wxuserId);
		String one_wxuserId = MjkjUtils.getMap2Str(wxuserMap, "pid");//一级代理
		one_wxuserId=Func.isEmpty(one_wxuserId)?"-1":one_wxuserId;
		Map<String, Object> one_wxuserMap = baseSqlService.getTableById("chat_wxuser", one_wxuserId);
		if(Func.isEmpty(one_wxuserMap)){//不存在上级
			//进行处理
			Map<String,Object> updateMap=new HashMap<>();
			updateMap.put("coissionmm_amount",0);//佣金为0
			updateMap.put("coissionmm_handle_flag",1);//已处理
			baseSqlService.baseUpdateData("chat_goods_order",updateMap,orderId);
			return;
		}
		Date now = DateUtil.now();
		BigDecimal totalYj=BigDecimal.ZERO;//总佣金
		//存在一级代理，则发放一级代理
		BigDecimal one_rate = this.getRate(one_wxuserId, 1);
		BigDecimal one_amount = amount.multiply(one_rate).setScale(2, BigDecimal.ROUND_DOWN);//一级佣金
		Map<String,Object> one_commissionMap=new HashMap<>();
		one_commissionMap.put("order_code",orderCode);
		one_commissionMap.put("order_amount",amount);
		one_commissionMap.put("fy_wxuserid",one_wxuserId);
		one_commissionMap.put("fy_level",1);
		one_commissionMap.put("fy_rate",one_rate);
		one_commissionMap.put("fy_amount",one_amount);
		one_commissionMap.put("fy_time",now);
		baseSqlService.baseInsertData("chat_commission_log",one_commissionMap);

		totalYj=totalYj.add(one_amount);
		//发放金额
		walletService.addAmount(one_wxuserId,one_amount,"0","佣金收入，订单号："+orderCode);

		String two_wxuserId = MjkjUtils.getMap2Str(one_wxuserMap, "pid");//二级代理
		two_wxuserId=Func.isEmpty(two_wxuserId)?"-1":two_wxuserId;
		Map<String, Object> two_wxuserMap = baseSqlService.getTableById("chat_wxuser", two_wxuserId);
		if(!Func.isEmpty(two_wxuserMap)){//存在上级
			//发放二级
			BigDecimal two_rate = this.getRate(two_wxuserId, 2);
			BigDecimal two_amount = amount.multiply(two_rate).setScale(2, BigDecimal.ROUND_DOWN);//二级佣金
			Map<String,Object> two_commissionMap=new HashMap<>();
			two_commissionMap.put("order_code",orderCode);
			two_commissionMap.put("order_amount",amount);
			two_commissionMap.put("fy_wxuserid",two_wxuserId);
			two_commissionMap.put("fy_level",2);
			two_commissionMap.put("fy_rate",two_rate);
			two_commissionMap.put("fy_amount",two_amount);
			two_commissionMap.put("fy_time",now);
			baseSqlService.baseInsertData("chat_commission_log",two_commissionMap);

			totalYj=totalYj.add(two_amount);
			//发放金额
			walletService.addAmount(two_wxuserId,two_amount,"0","佣金收入，订单号："+orderCode);
		}
		//发放完成，更新订单表为已处理//进行处理
		Map<String,Object> updateMap=new HashMap<>();
		updateMap.put("coissionmm_amount",totalYj);//佣金为0
		updateMap.put("coissionmm_handle_flag",1);//已处理
		baseSqlService.baseUpdateData("chat_goods_order",updateMap,orderId);
	}

	//获取分佣比例
	public BigDecimal getRate(String wxuserId,Integer level){
		Map<String, Object> userSettingMap = baseSqlService.getDataOneByField("chat_commission_setting_user", "wxuser_id", wxuserId);
		if(Func.isNotEmpty(userSettingMap)){//存在自定义佣金
			if(level==1){
				return MjkjUtils.getMap2BigD(userSettingMap,"one_rate");
			}else if(level==2){
				return MjkjUtils.getMap2BigD(userSettingMap,"two_rate");
			}else{
				return BigDecimal.ZERO;
			}
		}


		//不存在个人，则用公共
		List<Map<String, Object>> publicSettingMapList = baseSqlService.getDataByTable("chat_commission_setting");
		if(Func.isEmpty(publicSettingMapList)){
			return BigDecimal.ZERO;
		}
		Map<String, Object> publicMap = publicSettingMapList.get(0);
		if(Func.isNotEmpty(publicMap)){//存在自定义佣金
			if(level==1){
				return MjkjUtils.getMap2BigD(publicMap,"one_rate");
			}else if(level==2){
				return MjkjUtils.getMap2BigD(publicMap,"two_rate");
			}else{
				return BigDecimal.ZERO;
			}
		}
		return BigDecimal.ZERO;
	}


	/**
	 * true 开启  false =关闭
	 * @return
	 */
	private boolean getSwitchFlag(){
		try{
			List<Map<String, Object>> dataMapList = baseSqlService.getDataByTable("chat_commission_setting");
			if(Func.isEmpty(dataMapList)){
				return false;
			}
			Map<String, Object> dataMap = dataMapList.get(0);
			if(Func.isEmpty(dataMap)){
				return false;
			}
			String openFlag = MjkjUtils.getMap2Str(dataMap, "open_flag");
			return Func.equals(openFlag,"1");
		}catch (Exception e){

		}
		return false;
	}
}
