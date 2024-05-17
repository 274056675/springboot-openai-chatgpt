package org.springblade.mng.service;

import org.springblade.mng.param.mng.MngMemberParam;
import org.springblade.mng.param.mng.MngQuestionCouParam;

import java.util.Map;

/**
 * 后台相关
 */
public interface IMngService {

	//修改可提问问题次数
	void updateQuestionCou(MngQuestionCouParam param);
	//人工充值
	void addGoodsOrder(Map<String, Object> wxuserMap, MngMemberParam param);
}
