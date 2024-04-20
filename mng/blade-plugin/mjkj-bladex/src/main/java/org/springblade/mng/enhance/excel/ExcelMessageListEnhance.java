package org.springblade.mng.enhance.excel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.mng.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.mng.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 消息列表
 *
 */
@Component("excelMessageListEnhance")
public class ExcelMessageListEnhance implements CgformEnhanceJavaListInter {

	@Autowired
	private MngMapper mngMapper;


    /**
     * 人员数量 查询增强
     * @param tableName
     * @param list
     */
    public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
            throws BusinessException {
		MjkjUtils.clearList(list);
		Page<Map<String, Object>> page = new Page<>(1, -1);

		Long bladeUserId = AuthUtil.getUserId();
		params.put("blade_user_id",bladeUserId);
		IPage<Map<String, Object>> pages = mngMapper.getMessageHistoryList(page, params);
		MjkjUtils.setPageResult(params,pages);

	}
}
