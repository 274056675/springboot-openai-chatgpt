
package org.springblade.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.DesformHead;

import java.util.List;

/**
 * 表单设计器基本属性 Mapper 接口
 *
 *
 * @since 2021-07-02
 */
public interface DesformHeadMapper extends BaseMapper<DesformHead> {

    List<DesformHead> openList();
}
