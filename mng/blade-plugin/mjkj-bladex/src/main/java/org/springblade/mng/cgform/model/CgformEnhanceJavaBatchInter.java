package org.springblade.mng.cgform.model;

import com.alibaba.fastjson.JSONObject;

import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.config.exception.BusinessException;

import java.util.Map;

public interface CgformEnhanceJavaBatchInter {

	Map<String,Object> getOtherParam(String batchCode);

	/**
	 *
	 * @param head
	 * @param jsonobject
	 * @return 1=新增  2=更新 -1=不操作
	 * @throws BusinessException
	 */
	int execute(CgformHead head, JSONObject jsonobject, Map<String,Object> otherMap)
            throws BusinessException;
}
