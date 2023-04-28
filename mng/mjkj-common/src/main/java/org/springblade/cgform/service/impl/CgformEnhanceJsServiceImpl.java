
package org.springblade.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.cgform.entity.CgformEnhanceJs;
import org.springblade.cgform.mapper.CgformEnhanceJsMapper;
import org.springblade.cgform.service.ICgformEnhanceJsService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * js增强 服务实现类
 *
 *
 * @since 2021-05-22
 */
@Service
public class CgformEnhanceJsServiceImpl extends BaseServiceImpl<CgformEnhanceJsMapper, CgformEnhanceJs> implements ICgformEnhanceJsService {

    @Override
    public CgformEnhanceJs queryEnhanceJs(Long headId, String cgJsType) {
        LambdaQueryWrapper<CgformEnhanceJs> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CgformEnhanceJs::getCgformHeadId, headId);
        wrapper.eq(CgformEnhanceJs::getCgJsType, cgJsType);
        return baseMapper.selectOne(wrapper);
    }

}
