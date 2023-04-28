
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * 分类字典实体类
 *
 *
 * @since 2021-05-27
 */
@Data
@TableName("sys_category")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Category对象", description = "分类字典")
public class Category extends TenantEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级节点")
    private Long pid;

    @ApiModelProperty(value = "类型名称")
    private String name;

    @ApiModelProperty(value = "类型编码")
    private String code;

    @ApiModelProperty(value = "父级id")
    private String pstr;


    @ApiModelProperty(value = "是否有子节点:0=有子集  1=没子集")
    private String hasChild;


}
