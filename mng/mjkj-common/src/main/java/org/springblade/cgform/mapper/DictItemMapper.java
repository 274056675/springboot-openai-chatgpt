
package org.springblade.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.SysDictItem;
import org.springblade.cgform.model.DictModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典 Mapper 接口
 *
 *
 * @since 2021-05-27
 */
public interface DictItemMapper extends BaseMapper<SysDictItem> {

    List<DictModel> queryDictItemsByCode(@Param("code") String code);

}
