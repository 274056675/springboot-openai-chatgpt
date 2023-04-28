
package org.springblade.cgform.service;

import org.springblade.cgform.entity.Category;
import org.springblade.core.mp.base.BaseService;

import java.util.List;

/**
 * 分类字典 服务类
 *
 *
 * @since 2021-05-27
 */
public interface ICategoryService extends BaseService<Category> {

    void addSysCategory(Category category);

    void updateSysCategory(Category category);

    void batchDelete(List<Long> idList);
}
