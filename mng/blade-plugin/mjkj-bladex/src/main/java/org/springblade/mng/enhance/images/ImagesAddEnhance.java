package org.springblade.mng.enhance.images;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.model.CgformEnhanceJavaInter;
import org.springblade.config.exception.BusinessException;
import org.springblade.mng.service.IWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *图片新增
 */
@Component("imagesAddEnhance")
public class ImagesAddEnhance implements CgformEnhanceJavaInter {

	@Autowired
	private IWebService webService;
    /**
     */
	public int execute(CgformHead head, JSONObject jsonobject)
		throws BusinessException {
		String id= IdWorker.getIdStr();
		String prompt = jsonobject.getString("prompt");
		String size = jsonobject.getString("size");

		jsonobject.put("id",id);
		jsonobject.put("image_url",null);
		webService.saveDALLEImages(id,prompt,size);
		return 1;
	}

}
