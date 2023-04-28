
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * js增强实体类
 *
 *
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_enhance_js")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformEnhanceJs对象", description = "js增强")
public class CgformEnhanceJs extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * JS增强内容
     */
    @ApiModelProperty(value = "JS增强内容")
    private String cgJs;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String cgJsType;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String content;
    /**
     * 表单ID
     */
    @ApiModelProperty(value = "表单ID")
    private Long cgformHeadId;


}
