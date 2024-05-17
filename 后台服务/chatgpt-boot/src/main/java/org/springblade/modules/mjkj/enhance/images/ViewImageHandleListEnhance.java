package org.springblade.modules.mjkj.enhance.images;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.modules.mjkj.common.cgform.model.CgformEnhanceJavaListInter;
import org.springblade.modules.mjkj.common.config.exception.BusinessException;
import org.springblade.modules.mjkj.common.utils.MjkjUtils;
import org.springblade.modules.mjkj.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 图片审核
 *view_image_handle
 */
@Component("viewImageHandleListEnhance")
public class ViewImageHandleListEnhance implements CgformEnhanceJavaListInter {

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
		MjkjUtils.setPageResult(params,pages);

	}
}
