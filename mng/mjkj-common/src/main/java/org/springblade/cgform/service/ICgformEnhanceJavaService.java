
package org.springblade.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.config.exception.BusinessException;
import org.springblade.cgform.entity.CgformEnhanceJava;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * java增强 服务类
 *
 *
 * @since 2021-05-22
 */
public interface ICgformEnhanceJavaService extends BaseService<CgformEnhanceJava> {


	/**
	 * 执行java增强对象
	 * @param buttonCode
	 * @param eventType
	 * @param mjkjcgformhead
	 * @param jsonobject
	 * @return
	 * @throws BusinessException
	 */
	int executeEnhanceJava(String buttonCode, String eventType, CgformHead mjkjcgformhead, JSONObject jsonobject)
			throws BusinessException;

	/**
	 * 执行java增强列表
	 * @param mjkjcgformhead
	 * @param buttonCode
	 * @param list
	 * @throws BusinessException
	 */
	void executeEnhanceList(CgformHead mjkjcgformhead, String buttonCode, List<Map<String, Object>> list, Map<String, Object> params)
			throws BusinessException;


	/**
	 * 执行java增强详情
	 * @param mjkjcgformhead
	 * @param buttonCode
	 * @param
	 * @throws BusinessException
	 */
	Map<String,Object> executeEnhanceDetail(CgformHead mjkjcgformhead, String buttonCode, Map<String, Object> data, Map<String, Object> params)
			throws BusinessException;

	 Object getEnhanceJavaObj(CgformEnhanceJava enhanceJava);
}
