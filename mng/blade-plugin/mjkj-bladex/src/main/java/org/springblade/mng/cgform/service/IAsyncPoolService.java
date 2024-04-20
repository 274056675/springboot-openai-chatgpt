package org.springblade.mng.cgform.service;


import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.mapper.SqlMapper;
import org.springblade.mng.cgform.model.CgformEnhanceJavaBatchInter;
import org.springblade.mng.config.exception.BusinessException;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

public interface IAsyncPoolService {
	//批量执行
	void executeBatchSql(List<Map<String, Object>> dataList, CgformHead head, List<CgformField> fieldList, SqlMapper sqlMapper, String batchCode, Integer totalNum, Map<String, Object> otherMap, CgformEnhanceJavaBatchInter enhanceJava, ServletRequestAttributes sra) throws BusinessException;

	/**
	 * 入库
	 * @param dataList
	 * @param batchCode
	 * @param tableName
	 */

}
