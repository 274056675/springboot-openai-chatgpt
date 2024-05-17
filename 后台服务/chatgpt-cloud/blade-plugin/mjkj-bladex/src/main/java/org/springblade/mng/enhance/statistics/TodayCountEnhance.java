package org.springblade.mng.enhance.statistics;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.mng.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 今日统计-往日数据
 */
@Component("todayCountEnhance")
public class TodayCountEnhance implements CgformEnhanceJavaListInter {

	@Autowired
	private MngMapper mngMapper;


	/**
	 * 人员数量 查询增强
	 *
	 * @param tableName
	 * @param list
	 */
	public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
		throws BusinessException {
		MjkjUtils.clearList(list);
		Page page = MjkjUtils.getPage(params);
		String view_time = MjkjUtils.getMap2Str(params, "view_time");
		if(Func.isEmpty(view_time)){
			Date now = DateUtil.now();
			String yyyyMMdd = DateUtil.format(now, DateUtil.PATTERN_DATE);
			params.put("view_time",yyyyMMdd);
		}else{
			String yyyyMMdd = view_time.substring(0, 10);
			params.put("view_time",yyyyMMdd);
		}
		String column = MjkjUtils.getMap2Str(params, "column");
		if(Func.equals(column,"id")){
			params.put("column","yq_cou");
		}

		IPage pages = mngMapper.getTodayCountList(page, params);
		MjkjUtils.setPageResult(params,pages);
	}



}
