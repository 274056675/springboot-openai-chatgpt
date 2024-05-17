package org.springblade.modules.mjkj.common.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springblade.modules.mjkj.common.cgform.entity.CgformField;
import org.springblade.modules.mjkj.common.cgform.entity.CgformHead;
import org.springblade.modules.mjkj.common.cgform.mapper.SqlMapper;
import org.springblade.modules.mjkj.common.cgform.model.CgformEnhanceJavaBatchInter;

import org.springblade.modules.mjkj.common.utils.MjkjUtils;
import org.springblade.modules.mjkj.common.config.exception.BusinessException;
import org.springblade.modules.mjkj.common.config.util.SqlSymbolUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.modules.mjkj.common.cgform.service.IAsyncPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 异步调用
 */
@Service
public class AsyncPoolServiceImpl implements IAsyncPoolService {

	@Autowired
	private RedisUtil redisUtil;

	//批量执行
	@Override
	@Async("asyncPoolTaskExecutor")
	public void executeBatchSql(List<Map<String, Object>> dataList, CgformHead head, List<CgformField> fieldList, SqlMapper sqlMapper, String batchCode, Integer totalNum, Map<String, Object> otherMap, CgformEnhanceJavaBatchInter enhanceJava,ServletRequestAttributes sra) throws BusinessException {
		RequestContextHolder.setRequestAttributes(sra, true);
		//走增强
		List<JSONObject> insertDataList = new ArrayList<>();
		for (Map<String, Object> dataMap : dataList) {
			String jsonStr = JSON.toJSONString(dataMap);
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			Integer result=0;
			if(Func.isNotEmpty(enhanceJava)){
				result = enhanceJava.execute(head, jsonObject, otherMap);
				if(Func.isNotEmpty(result) && result==-1){
					jsonObject.put("is_deleted",-1);
				}
			}
			jsonObject.put("id", IdWorker.getId());
			jsonObject.put("batch_code", batchCode);
			jsonObject.put("total_num", totalNum);
			insertDataList.add(jsonObject);
		}
		String tableName = head.getTableName();
		Map<String, Object> batchSqlMap = SqlSymbolUtil.getInsertBatchSql(tableName, fieldList, insertDataList);
		String ddl = MjkjUtils.getMap2Str(batchSqlMap, "ddl");
		List<Map<String, Object>> insertList = (List<Map<String, Object>>) batchSqlMap.get("dataList");
		sqlMapper.executeInsertBatchSQL(ddl, insertList);

		String redisKey = "table_" + tableName + "_code_" + batchCode + ":" + IdWorker.getId();
		String redisValue = insertDataList.size() + "_" + totalNum;
		redisUtil.set(redisKey, redisValue, 3600*24L, TimeUnit.SECONDS);
	}

	/**
	 * 入库
	 *
	 * @param dataList
	 * @param batchCode
	 * @param tableName
	 */
	@Async("asyncPoolTaskExecutor")
	public void addImportData(List<Map<String, Object>> dataList, String batchCode, String tableName, String redisKey, Map<String, Object> params, ServletRequestAttributes sra) {
		RequestContextHolder.setRequestAttributes(sra, true);
		try {


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//写redis
	private void saveRedis(String tableName, String batchCode, String key, Integer val) {
		String redisKey = "importData:" + tableName + ":" + batchCode + ":" + key + ":" + IdWorker.getId();
		redisUtil.set(redisKey, val, 3600 * 24L,TimeUnit.SECONDS);
	}
}
