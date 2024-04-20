package org.springblade.mng.cgform.model;

import com.alibaba.fastjson.JSONObject;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.config.exception.BusinessException;


public interface CgformEnhanceJavaInter {
	/**
	 *
	 * @param head
	 * @param jsonobject
	 * @return 1=新增  2=更新 -1=不操作
	 * @throws BusinessException
	 */
	int execute(CgformHead head, JSONObject jsonobject)
            throws BusinessException;
}
