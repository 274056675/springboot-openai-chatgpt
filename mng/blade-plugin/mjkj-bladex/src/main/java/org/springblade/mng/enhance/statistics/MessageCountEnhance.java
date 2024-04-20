package org.springblade.mng.enhance.statistics;

import org.springblade.mng.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 消息统计
 */
@Component("messageCountEnhance")
public class MessageCountEnhance implements CgformEnhanceJavaListInter {

	@Autowired
	private MngMapper mngMapper;

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	/**
	 * 人员数量 查询增强
	 *
	 * @param tableName
	 * @param list
	 */
	public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
		throws BusinessException {
		MjkjUtils.clearList(list);
		List<String> dateList = this.getNext30Day();
		//使用次数
		List<Map<String, Object>> messageCouList = mngMapper.getMessageCouList();
		//使用人数
		List<Map<String, Object>> registerCouList = mngMapper.getRegisterCouList();
		//注册人数
		List<Map<String, Object>> useWuserCouList = mngMapper.getUseWuserCouList();
		//分享人数
		List<Map<String, Object>> shareCouList = mngMapper.getShareCouList();
		//签到次数
		List<Map<String, Object>> signCouList = mngMapper.getSignCouList();
		//文件下载数
		List<Map<String, Object>> fileCouList = mngMapper.getFileCouList();

		//获取收款总额
		List<Map<String, Object>> orderMapList = baseSqlService.getDataListByField("chat_goods_order", "pay_status", "1");//已支付
		Map<String, Object> payMap =new HashMap<>();
		if(Func.isNotEmpty(orderMapList)){
			for (Map<String, Object> orderMap:orderMapList){
				try{
					BigDecimal amount = MjkjUtils.getMap2BigD(orderMap, "amount");
					Date orderTime = MjkjUtils.getMap2DateTime(orderMap, "order_time");
					String timeStr = DateUtil.format(orderTime, DateUtil.PATTERN_DATE);
					BigDecimal amountTmp = MjkjUtils.getMap2BigD(payMap, timeStr);
					if(Func.isNotEmpty(amountTmp)){
						amountTmp=amountTmp.add(amount);
					}else{
						amountTmp=amount;
					}
					payMap.put(timeStr,amountTmp);
				}catch (Exception e){

				}

			}
		}



		Map<String, Map<String, Object>> messageCouMap = MjkjUtils.list2Map(messageCouList, "timeStr");
		Map<String, Map<String, Object>> registerCouMap = MjkjUtils.list2Map(registerCouList, "timeStr");
		Map<String, Map<String, Object>> useWuserCouMap = MjkjUtils.list2Map(useWuserCouList, "timeStr");
		Map<String, Map<String, Object>> shareCouMap = MjkjUtils.list2Map(shareCouList, "timeStr");
		Map<String, Map<String, Object>> signCouMap = MjkjUtils.list2Map(signCouList, "timeStr");
		Map<String, Map<String, Object>> fileCouMap = MjkjUtils.list2Map(fileCouList, "timeStr");


		for (String dateStr : dateList) {

			Map<String, Object> twMap = messageCouMap.get(dateStr);
			String twCou = MjkjUtils.getMap2Str(twMap, "cou");

			Map<String, Object> zcMap = registerCouMap.get(dateStr);
			String zcCou = MjkjUtils.getMap2Str(zcMap, "cou");

			Map<String, Object> useMap = useWuserCouMap.get(dateStr);
			String useCou = MjkjUtils.getMap2Str(useMap, "cou");

			Map<String, Object> fxMap = shareCouMap.get(dateStr);
			String fxCou = MjkjUtils.getMap2Str(fxMap, "cou");

			Map<String, Object> signMap = signCouMap.get(dateStr);
			String qdCou = MjkjUtils.getMap2Str(signMap, "cou");

			Map<String, Object> fileMap = fileCouMap.get(dateStr);
			String wjCou = MjkjUtils.getMap2Str(fileMap, "cou");



			BigDecimal amountCou = MjkjUtils.getMap2BigD(payMap, dateStr);
			String amountCouStr=Func.isEmpty(amountCou)||amountCou.compareTo(BigDecimal.ZERO)!=1?"-":amountCou.setScale(2).stripTrailingZeros().toPlainString();

			twCou= Func.isEmpty(twCou)?"-":twCou;
			zcCou= Func.isEmpty(zcCou)?"-":zcCou;
			useCou= Func.isEmpty(useCou)?"-":useCou;
			fxCou= Func.isEmpty(fxCou)?"-":fxCou;
			qdCou= Func.isEmpty(qdCou)?"-":qdCou;
			wjCou= Func.isEmpty(wjCou)?"-":wjCou;


			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("time", dateStr);
			resultMap.put("tw_cou", twCou);
			resultMap.put("zc_cou", zcCou);
			resultMap.put("use_cou", useCou);
			resultMap.put("sk_cou",amountCouStr);
			resultMap.put("fx_cou",fxCou);
			resultMap.put("qd_cou",qdCou);
			resultMap.put("wj_cou",wjCou);
			list.add(resultMap);
		}
	}

	//获取最近30天
	private List<String> getNext30Day() {
		Date now = DateUtil.now();
		List<String> dateList = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			Date date = DateUtil.plusDays(now, -i);
			String yyyyMMdd = DateUtil.format(date, DateUtil.PATTERN_DATE);
			dateList.add(yyyyMMdd);
		}
		return dateList;
	}

}
