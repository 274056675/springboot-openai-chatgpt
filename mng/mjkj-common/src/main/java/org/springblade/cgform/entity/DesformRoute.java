
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * 表单设计器-路由实体类
 *
 *
 * @since 2021-07-02
 */
@Data
@TableName("design_desform_route")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "表单设计器路由", description = "表单设计器路由")
public class DesformRoute extends TenantEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表单设计id")
    private Long desFormId;

    @ApiModelProperty(value = "表单设计code")
    private String desFormCode;

    @ApiModelProperty(value = "'路由名称'")
    private String routeName;

    @ApiModelProperty(value = "路由地址")
    private String routePath;

    @ApiModelProperty(value = "路由跳转路径")
    private String routeType;

}
