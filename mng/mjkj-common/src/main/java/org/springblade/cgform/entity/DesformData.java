
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * 表单设计器-数据实体类
 *
 *
 * @since 2021-07-02
 */
@Data
@TableName("design_desform_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DesformData对象", description = "表单设计器-数据")
public class DesformData extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表单设计id
     */
    @ApiModelProperty(value = "表单设计id")
    private Long desformHeadId;
    /**
     * 表单json
     */
    @ApiModelProperty(value = "表单json")
    private String formDataJson;


    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 表单开发表名
     */
    private String onlineFormCode;

    /**
     * 表单开发数据id
     */
    private String onlineFormDataId;


}
