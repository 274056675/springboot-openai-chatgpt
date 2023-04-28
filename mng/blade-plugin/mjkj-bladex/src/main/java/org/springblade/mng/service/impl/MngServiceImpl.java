package org.springblade.mng.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.param.mng.MngMemberParam;
import org.springblade.mng.param.mng.MngQuestionCouParam;
import org.springblade.mng.service.ICommissionService;
import org.springblade.mng.service.IMngService;
import org.springblade.mng.service.IWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台相关
 */
@Slf4j
@Service
public class MngServiceImpl implements IMngService {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IWebService webService;

	@Autowired
	private ICommissionService commissionService;

	//修改可提问问题次数
	@Transactional
	@Override
	public void updateQuestionCou(MngQuestionCouParam param){
		List<String> ids = param.getIds();
		String type = param.getType();
		Integer num = param.getNum();
		String remark = param.getRemark();

		for (String id:ids) {
			Map<String, Object> wxuserMap = baseSqlService.getTableById("chat_wxuser", id);
			if(Func.isEmpty(wxuserMap)){
				continue;
			}
			Long bladeUserId = MjkjUtils.getMap2Long(wxuserMap, "blade_user_id");
			String remarkStr="【"+ AuthUtil.getNickName() +"】"+remark;
			if(Func.equals(type,"add")){
				webService.addWxuserQuestionNum(bladeUserId,id,5,num,null,remarkStr);
			}else{//减少
				webService.subWxuserQuestionNum(bladeUserId,id,5,num,null,remarkStr);
			}
		}

	}

	//人工充值
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addGoodsOrder(Map<String, Object> wxuserMap , MngMemberParam param){
		////add=增加  sub=减少
		String type= Func.equals(param.getType(),"add")?"增加":"减少";

		String title="人工【"+type+"】 "+param.getDay()+" 天";
		String wxuserId = MjkjUtils.getMap2Str(wxuserMap, "id");
		String wxName = MjkjUtils.getMap2Str(wxuserMap, "wx_name");
		String orderCode="ORD_"+ IdWorker.getIdStr();
		Integer day = param.getDay();
		Date now = DateUtil.now();
		String goodsId="-1";
		//下单
		Map<String,Object> orderMap=new HashMap<>();
		orderMap.put("goods_title",title);
		orderMap.put("wxuser_id",wxuserId);
		orderMap.put("wxuser_name",wxName);
		orderMap.put("order_code",orderCode);
		orderMap.put("amount",param.getAmount());
		orderMap.put("day",day);
		orderMap.put("order_time",now);
		orderMap.put("chat_goods_id",goodsId);
		orderMap.put("pay_status",1);//已支付
		orderMap.put("pay_type",1);//人工

		orderMap.put("pay_code", IdWorker.getIdStr());
		orderMap.put("pay_time",now);
		orderMap.put("pay_status",1);//已付款
		orderMap.put("remark","【"+ AuthUtil.getNickName()+"】"+param.getRemark());
		baseSqlService.baseInsertData("chat_goods_order",orderMap);

		//加入到用户时长里面
		Date expireTime = MjkjUtils.getMap2DateTime(wxuserMap, "expire_time");
		if(Func.isEmpty(expireTime) || expireTime.getTime()<=now.getTime()){//如果已经过期了的话，则从现在开始
			expireTime=now;
		}

		//写入历史记录
		Date newExpireTime = null;
		if(Func.equals(param.getType(),"add")){//增加
			newExpireTime = DateUtil.plusDays(expireTime, day);
		}else{//减少
			newExpireTime = DateUtil.plusDays(expireTime, -day);
		}
		//写充值记录
		Map<String,Object> logMap=new HashMap<>();
		logMap.put("wxuser_id",wxuserId);
		logMap.put("order_code",orderCode);
		logMap.put("start_time",expireTime);
		logMap.put("end_time",newExpireTime);
		baseSqlService.baseInsertData("chat_log_wxuser_time",logMap);

		//更新用户
		Map<String,Object> wuserUpateMap=new HashMap<>();
		wuserUpateMap.put("expire_time",newExpireTime);//新的到期时间
		baseSqlService.baseUpdateData("chat_wxuser",wuserUpateMap,wxuserId);

		//购买成功，处理分佣金
		if(Func.equals(param.getType(),"add")){
			commissionService.handleCoissionmm(orderCode);
		}

	}
}
