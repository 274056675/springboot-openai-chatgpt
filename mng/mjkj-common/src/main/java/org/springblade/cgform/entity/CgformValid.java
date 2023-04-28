
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * 表单校验
 */
@Data
@TableName("onl_cgform_valid")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformValid对象", description = "自定义表单字段校验规则表")
public class CgformValid extends TenantEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "校验表达式")
    private String pattern;

    @ApiModelProperty(value = "描述")
    private String msg;

    @ApiModelProperty(value = "校验字段名")
    private String name;


}
