
package org.springblade.cgform.service;

import org.springblade.cgform.entity.CgformEnhanceJs;
import org.springblade.core.mp.base.BaseService;

/**
 * js增强 服务类
 *
 *
 * @since 2021-05-22
 */
public interface ICgformEnhanceJsService extends BaseService<CgformEnhanceJs> {

    //获取js增强
    CgformEnhanceJs queryEnhanceJs(Long headId, String cgJsType);
}
