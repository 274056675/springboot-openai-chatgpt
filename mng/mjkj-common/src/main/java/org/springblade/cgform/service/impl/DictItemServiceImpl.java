
package org.springblade.cgform.service.impl;

import org.springblade.cgform.entity.SysDictItem;
import org.springblade.cgform.mapper.DictItemMapper;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.service.IDictItemService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典 服务实现类
 *
 *
 * @since 2021-05-27
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemMapper, SysDictItem> implements IDictItemService {

    /**
     * 通过查询指定code 获取字典
     * @param code
     * @return
     */
    @Override
    public List<DictModel> queryDictItemsByCode(String code) {
        return baseMapper.queryDictItemsByCode(code);
    }


}
