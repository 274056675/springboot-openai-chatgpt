
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * 自定义表单实体类
 *
 *
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_button")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformButton对象", description = "自定义表单")
public class CgformButton extends TenantEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "按钮编码")
    private String buttonCode;

    @ApiModelProperty(value = "按钮图标")
    private String buttonIcon;

    @ApiModelProperty(value = "按钮名称")
    private String buttonName;

    @ApiModelProperty(value = "按钮状态")
    private String buttonStatus;

    @ApiModelProperty(value = "按钮样式")
    private String buttonStyle;

    @ApiModelProperty(value = "表达式")
    private String exp;

    @ApiModelProperty(value = "表单ID")
    private Long cgformHeadId;

    @ApiModelProperty(value = "按钮类型")
    private String optType;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "按钮位置1侧面 2底部")
    private String optPosition;


}
