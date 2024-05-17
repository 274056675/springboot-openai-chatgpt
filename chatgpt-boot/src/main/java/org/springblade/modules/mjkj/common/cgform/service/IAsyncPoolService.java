package org.springblade.modules.mjkj.common.cgform.service;

import org.springblade.modules.mjkj.common.cgform.entity.CgformField;
import org.springblade.modules.mjkj.common.cgform.entity.CgformHead;
import org.springblade.modules.mjkj.common.cgform.mapper.SqlMapper;
import org.springblade.modules.mjkj.common.cgform.model.CgformEnhanceJavaBatchInter;
import org.springblade.modules.mjkj.common.config.exception.BusinessException;
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
	void addImportData(List<Map<String, Object>> dataList, String batchCode, String tableName, String key, Map<String,Object> params, ServletRequestAttributes sra);

}
