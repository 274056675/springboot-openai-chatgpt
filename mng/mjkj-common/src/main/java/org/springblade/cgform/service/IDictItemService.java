
package org.springblade.cgform.service;

import org.springblade.cgform.entity.SysDictItem;
import org.springblade.cgform.model.DictModel;
import org.springblade.core.mp.base.BaseService;

import java.util.List;

/**
 * 数据字典 服务类
 *
 *
 * @since 2021-05-27
 */
public interface IDictItemService extends BaseService<SysDictItem> {

    List<DictModel> queryDictItemsByCode(String code);
}
