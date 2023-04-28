package org.springblade.mng.enhance.commission;

import com.alibaba.fastjson.JSONObject;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.model.CgformEnhanceJavaInter;
import org.springblade.cgform.service.IMjkjBaseSqlService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.exception.BusinessException;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *个人自定义佣金设置-新增
 */
@Component("commissionUserSettingAddEnhance")
public class CommissionUserSettingAddEnhance implements CgformEnhanceJavaInter {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

    /**
     *  查询增强
     */
    public int execute(CgformHead head, JSONObject jsonobject)
            throws BusinessException {
		String phone = jsonobject.getString("view_phone");
		if(Func.isEmpty(phone)){
			throw new BusinessException("手机号码不允许为空");
		}


		Map<String, Object> dataMap = baseSqlService.getDataOneByField("chat_wxuser", "phone", phone);
		if(Func.isEmpty(dataMap)){
			throw new BusinessException("用户不存在");
		}
		String wxuserId = MjkjUtils.getMap2Str(dataMap, "id");
		String wxName = MjkjUtils.getMap2Str(dataMap, "wx_name");

		Map<String, Object> settingMap = baseSqlService.getDataOneByField("chat_commission_setting_user", "wxuser_id", wxuserId);
		if(Func.isNotEmpty(settingMap)){
			throw new BusinessException("账户已存在");
		}

		jsonobject.put("wxuser_id",wxuserId);
		jsonobject.put("view_wxuser_name",wxName);
		return 1;
	}
}
