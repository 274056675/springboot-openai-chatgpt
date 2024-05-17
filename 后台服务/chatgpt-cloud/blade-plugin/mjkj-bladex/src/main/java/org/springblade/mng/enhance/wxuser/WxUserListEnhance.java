package org.springblade.mng.enhance.wxuser;

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
 *用户列表
 *
 */
@Component("wxUserListEnhance")
public class WxUserListEnhance implements CgformEnhanceJavaListInter {

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
		Page page = MjkjUtils.getPage(params);
		IPage pages = mngMapper.getWxUserList(page, params);
		MjkjUtils.setPageResult(params,pages);

	}
}
