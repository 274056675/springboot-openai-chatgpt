package org.springblade.mng.enhance.images;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.model.CgformEnhanceJavaInter;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.config.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *view_image_list
 */
@Component("viewImageDelEnhance")
public class ViewImageDelEnhance implements CgformEnhanceJavaInter {

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;
    /**
     */
	public int execute(CgformHead head, JSONObject jsonobject)
		throws BusinessException{
		String id= IdWorker.getIdStr();

		mjkjBaseSqlService.baseDeleteSqlStr("chat_image_info",id);

		return -1;
	}

}
