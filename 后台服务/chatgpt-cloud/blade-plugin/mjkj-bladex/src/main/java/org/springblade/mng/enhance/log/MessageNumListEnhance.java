package org.springblade.mng.enhance.log;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.mng.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.mng.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 消息次数列表
 *
 */
@Component("messageNumListEnhance")
public class MessageNumListEnhance implements CgformEnhanceJavaListInter {

	@Autowired
	private MngMapper mngMapper;
    /**
     * 消息次数 查询增强
     * @param tableName
     * @param list
     */
    public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
            throws BusinessException {
		MjkjUtils.clearList(list);
		Page page = MjkjUtils.getPage(params);
		IPage pages = mngMapper.getNumLogList(page, params);
		MjkjUtils.setPageResult(params,pages);
	}
}
