/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.modules.mjkj.common.cgform.service;

import org.springblade.modules.mjkj.common.cgform.entity.CgformEnhanceJs;
import org.springblade.core.mp.base.BaseService;

/**
 * js增强 服务类
 *
 * @author BladeX
 * @since 2021-05-22
 */
public interface ICgformEnhanceJsService extends BaseService<CgformEnhanceJs> {

    //获取js增强
    CgformEnhanceJs queryEnhanceJs(Long headId, String cgJsType);
}
