package org.springblade.mng.enhance.commission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.exception.BusinessException;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *佣金记录
 */
@Component("commissionListEnhance")
public class CommissionListEnhance implements CgformEnhanceJavaListInter {

	@Autowired
	private MngMapper mngMapper;
    /**
     *  查询增强
     * @param tableName
     * @param list
     */
    public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
            throws BusinessException {
		MjkjUtils.clearList(list);
		Page page = MjkjUtils.getPage(params);
		IPage pages = mngMapper.getCommissionLogList(page, params);
		List<Map<String,Object>> dataMapList = pages.getRecords();
		if(Func.isNotEmpty(dataMapList)){
			for(Map<String,Object> dataMap:dataMapList){
				BigDecimal rate = MjkjUtils.getMap2BigD(dataMap, "fy_rate");
				rate = rate.multiply(BigDecimal.valueOf(100));
				dataMap.put("fy_rate",rate.stripTrailingZeros().toPlainString()+"%");
			}
		}
		MjkjUtils.setPageResult(params,pages);

	}
}
