
package org.springblade.cgform.service;

import org.springblade.cgform.entity.CgformButton;
import org.springblade.core.mp.base.BaseService;

import java.util.List;

/**
 * 自定义表单 服务类
 *
 *
 * @since 2021-05-22
 */
public interface ICgformButtonService extends BaseService<CgformButton> {

     List<CgformButton> queryButtonList(Long headId, boolean isListButton);

}
