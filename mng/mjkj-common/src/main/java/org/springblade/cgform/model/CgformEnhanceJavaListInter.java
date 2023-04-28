package org.springblade.cgform.model;


import org.springblade.config.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * java增强需要继承该接口
 */
public interface CgformEnhanceJavaListInter  {

    void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
            throws BusinessException;
}
