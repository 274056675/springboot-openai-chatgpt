
package org.springblade.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.system.entity.Param;
import org.springblade.system.mapper.ParamMapper;
import org.springblade.system.service.IParamService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 *
 */
@Service
public class ParamServiceImpl extends BaseServiceImpl<ParamMapper, Param> implements IParamService {

	@Override
	public String getValue(String paramKey) {
		Param param = this.getOne(Wrappers.<Param>query().lambda().eq(Param::getParamKey, paramKey));
		return param.getParamValue();
	}

}
