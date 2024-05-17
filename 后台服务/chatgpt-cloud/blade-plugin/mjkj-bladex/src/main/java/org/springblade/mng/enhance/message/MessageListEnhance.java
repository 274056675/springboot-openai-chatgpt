package org.springblade.mng.enhance.message;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.mng.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息列表
 *
 */
@Component("messageListEnhance")
public class MessageListEnhance implements CgformEnhanceJavaListInter {

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
		IPage pages = mngMapper.getMessageHistoryList(page, params);
		if(Func.isNotEmpty(pages)){
			List<Map<String,Object>> dataMapList = pages.getRecords();
			if(Func.isNotEmpty(dataMapList)){
				for(Map<String,Object> dataMap:dataMapList){
					try{
						Date startTime = MjkjUtils.getMap2DateTime(dataMap, "message_time");
						Date endTime = MjkjUtils.getMap2DateTime(dataMap, "answer_time");
						Long useTime = endTime.getTime()-startTime.getTime();
						BigDecimal divide = BigDecimal.valueOf(useTime).divide(BigDecimal.valueOf(1000));
						dataMap.put("use_time",divide.stripTrailingZeros().toPlainString()+" 秒");
					}catch (Exception e){

					}

				}
			}
		}

		MjkjUtils.setPageResult(params,pages);

	}
}
