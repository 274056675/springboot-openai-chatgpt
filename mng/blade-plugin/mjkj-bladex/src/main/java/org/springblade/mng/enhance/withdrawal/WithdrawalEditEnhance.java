package org.springblade.mng.enhance.withdrawal;

import com.alibaba.fastjson.JSONObject;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.model.CgformEnhanceJavaInter;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.exception.BusinessException;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.service.IWalletService;
import org.springblade.mng.service.IWxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 *
 *提现审核
 *
 */
@Component("withdrawalEditEnhance")
public class WithdrawalEditEnhance implements CgformEnhanceJavaInter {


	@Autowired
	private IWalletService walletService;

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IWxPayService wxPayService;

    /**
	 *
     */
	public int execute(CgformHead head, JSONObject jsonobject)
		throws BusinessException {

		String withdrawalState = jsonobject.getString("withdrawal_state");
		if(Func.equals(withdrawalState,"0")){//不做其他操作
			return 2;
		}

		Date now = DateUtil.now();

		jsonobject.put("auditor", AuthUtil.getNickName());
		jsonobject.put("auditor_blade_user_id", AuthUtil.getUserId());
		jsonobject.put("auditor_time", DateUtil.format(now, DateUtil.PATTERN_DATETIME));

		String wxuserId = jsonobject.getString("wxuser_id");
		BigDecimal withdrawalAmount = jsonobject.getBigDecimal("withdrawal_amount");

		if(Func.equals(withdrawalState,"-1")){//审核不通过，退还用户余额
			walletService.addAmount(wxuserId,withdrawalAmount,"2","审核不通过");
		}else{//审核通过
			String withdrawalId = jsonobject.getString("id");

			Map<String, Object> autoMap = baseSqlService.getDataOneByField("chat_withdrawal_setting", "withdrawal_type", "withdrawal_auto");
			String withdrawalAutoValue = MjkjUtils.getMap2Str(autoMap, "withdrawal_value");
			if(Func.equals(withdrawalAutoValue,"true")){//开启自动转账功能-------
				String wxResult = wxPayService.handleAutoWithdrawal(withdrawalId);
				if(!Func.equals(wxResult,"成功")){
					throw new ServiceException(wxResult);//抛出原因
				}
				String remark = jsonobject.getString("remark");
				if(Func.isNotEmpty(remark) && remark.contains("【自动审核】，失败原因")){
					jsonobject.put("remark","");
				}
			}
		}
		return 2;
	}

}


