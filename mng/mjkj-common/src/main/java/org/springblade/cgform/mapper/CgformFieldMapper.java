
package org.springblade.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.CgformField;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *  Mapper 接口
 *
 *
 * @since 2021-05-20
 */
public interface CgformFieldMapper extends BaseMapper<CgformField> {

    Map<String, Object> queryOneByTableNameAndId(@Param("tbname") String tbname, @Param("dataId") Long dataId);

	Integer getBlobCou(Long headId);
}
