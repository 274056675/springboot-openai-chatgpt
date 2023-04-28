package org.springblade.mng.enhance.wallet;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.exception.BusinessException;
import org.springblade.mng.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *钱包列表
 *
 */
@Component("walletListEnhance")
public class WalletListEnhance implements CgformEnhanceJavaListInter {

	@Autowired
	private MngMapper mngMapper;
    /**
     * 查询增强
     * @param tableName
     * @param list
     */
    public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
            throws BusinessException {
		MjkjUtils.clearList(list);
		Page page = MjkjUtils.getPage(params);
		IPage pages = mngMapper.getWalletHistoryLogList(page, params);
		MjkjUtils.setPageResult(params,pages);

	}
}
