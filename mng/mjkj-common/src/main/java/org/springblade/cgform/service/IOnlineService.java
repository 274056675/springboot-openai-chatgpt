
package org.springblade.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.cgform.entity.CgformEnhanceJs;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.model.OnlineConfigModel;

/**
 *
 * @since 2021-05-22
 */
public interface IOnlineService {

    //获取头部信息
    OnlineConfigModel queryOnlineConfig(CgformHead head);

    //获取字段内容
    JSONObject queryOnlineFormObj(CgformHead onlcgformhead, CgformEnhanceJs onlcgformenhancejs);

}
