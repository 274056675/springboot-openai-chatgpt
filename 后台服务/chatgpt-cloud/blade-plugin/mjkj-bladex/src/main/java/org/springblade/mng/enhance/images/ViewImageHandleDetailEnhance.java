package org.springblade.mng.enhance.images;

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
 * 图片审核
 *view_image_handle
 */
@Component("viewImageHandleDetailEnhance")
public class ViewImageHandleDetailEnhance implements CgformEnhanceJavaListInter {

	@Autowired
	private MngMapper mngMapper;

    /**
     * @param tableName
     * @param list
     */
    public void execute(String tableName, String tenantId, List<Map<String, Object>> list, Map<String, Object> params)
            throws BusinessException {
		MjkjUtils.clearList(list);
		Page page = MjkjUtils.getPage(params);
		IPage pages = mngMapper.getViewImageTodoList(page, params);
		List<Map<String,Object>> dataMapList = pages.getRecords();
		list.addAll(dataMapList);

	}
}
