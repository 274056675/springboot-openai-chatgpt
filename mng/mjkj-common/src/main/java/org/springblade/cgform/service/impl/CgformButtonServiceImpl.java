
package org.springblade.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.config.util.SqlSymbolUtil;
import org.springblade.cgform.entity.CgformButton;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.mapper.CgformButtonMapper;
import org.springblade.cgform.mapper.CgformFieldMapper;
import org.springblade.cgform.mapper.CgformHeadMapper;
import org.springblade.cgform.service.ICgformButtonService;
import org.springblade.cgform.service.ICgformEnhanceJavaService;
import org.springblade.cgform.service.ICgformEnhanceSqlService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 自定义表单 服务实现类
 *
 *
 * @since 2021-05-22
 */
@Service
public class CgformButtonServiceImpl extends BaseServiceImpl<CgformButtonMapper, CgformButton> implements ICgformButtonService {

    @Autowired
    private CgformHeadMapper headMapperMapper;

    @Autowired
    private CgformFieldMapper fieldMapper;

    @Autowired
    private ICgformEnhanceSqlService sqlService;

    @Autowired
    private ICgformEnhanceJavaService javaService;

    public List<CgformButton> queryButtonList(Long headId, boolean isListButton) {
        LambdaQueryWrapper<CgformButton> qw = new LambdaQueryWrapper<>();
        qw.eq(CgformButton::getButtonStatus, "1");
        qw.eq(CgformButton::getCgformHeadId, headId);
        if (isListButton) {
            qw.in(CgformButton::getButtonStyle, new Object[]{"link", "button"});
        } else {
            qw.eq(CgformButton::getButtonStyle, "form");
        }

        qw.orderByAsc(CgformButton::getOrderNum);
        return baseMapper.selectList(qw);
    }

    public List<CgformButton> queryButtonList(Long headId) {
        LambdaQueryWrapper<CgformButton> qw = new LambdaQueryWrapper<>();
        qw.eq(CgformButton::getButtonStatus, "1");
        qw.eq(CgformButton::getCgformHeadId, headId);
        qw.orderByAsc(CgformButton::getOrderNum);
        return baseMapper.selectList(qw);
    }


}
