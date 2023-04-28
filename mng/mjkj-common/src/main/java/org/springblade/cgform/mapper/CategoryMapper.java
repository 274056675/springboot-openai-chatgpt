
package org.springblade.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类字典 Mapper 接口
 *
 *
 * @since 2021-05-27
 */
public interface CategoryMapper extends BaseMapper<Category> {

    String queryCategoryIdByCode(@Param("code") String code);

    List<Category> getAllSubList(Long id);
}
