package org.springblade.mng.enhance.images;

import com.alibaba.fastjson.JSONObject;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.model.CgformEnhanceJavaInter;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *view_image_list
 */
@Component("viewImageHandleEditEnhance")
public class ViewImageHandleEditEnhance implements CgformEnhanceJavaInter {

	@Autowired
	private IMjkjBaseSqlService mjkjBaseSqlService;
    /**
     */
	public int execute(CgformHead head, JSONObject jsonobject)
		throws BusinessException{
		String id = MjkjUtils.getMap2Str(jsonobject, "id");
		String shzt = MjkjUtils.getMap2Str(jsonobject, "shzt");
		Map<String,Object> updateMap=new HashMap<>();
		updateMap.put("examine_blade_user_id", AuthUtil.getUserId());
		updateMap.put("examine_time", DateUtil.now());
		if(Func.equals(shzt,"0")){
			updateMap.put("open_flag",0);
			mjkjBaseSqlService.baseUpdateData("chat_image_info",updateMap,id);
		}else if(Func.equals(shzt,"2")){
			updateMap.put("open_flag",2);
			mjkjBaseSqlService.baseUpdateData("chat_image_info",updateMap,id);
		}


		return -1;
	}

}
