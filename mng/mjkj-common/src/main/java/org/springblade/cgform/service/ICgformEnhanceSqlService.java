
package org.springblade.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.cgform.entity.CgformEnhanceSql;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.core.mp.base.BaseService;

import java.util.Map;

/**
 * sql增强 服务类
 *
 *
 * @since 2021-05-22
 */
public interface ICgformEnhanceSqlService extends BaseService<CgformEnhanceSql> {

    /**
     * 执行sql增强
     * @param buttonCode
     * @param headId
     * @param json
     */
    void executeEnhanceSqlUpdate(String buttonCode, Long headId, JSONObject json);

	void executeEnhanceSqlInsert(String buttonCode, Long headId, JSONObject json);

    //sql增强详情
	Map<String, Object> executeEnhanceSqlDetail(CgformHead head,String buttonCode, Map<String, Object> map, String id);
	//sql增强 列表
	void executeEnhanceSqlList(CgformHead head, String buttonCode, Map<String, Object> params);

}
