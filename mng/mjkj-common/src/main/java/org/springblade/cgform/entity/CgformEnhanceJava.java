
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * java增强实体类
 *
 *
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_enhance_java")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformEnhanceJava对象", description = "java增强")
public class CgformEnhanceJava extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 按钮编码
     */
    @ApiModelProperty(value = "按钮编码")
    private String buttonCode;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String cgJavaType;
    /**
     * 数值
     */
    @ApiModelProperty(value = "数值")
    private String cgJavaValue;
    /**
     * 表单ID
     */
    @ApiModelProperty(value = "表单ID")
    private Long cgformHeadId;
    /**
     * 生效状态
     */
    @ApiModelProperty(value = "生效状态")
    private String activeStatus;
    /**
     * 事件状态(end:结束，start:开始)
     */
    @ApiModelProperty(value = "事件状态(end:结束，start:开始)")
    private String event;

	@ApiModelProperty(value = "服务名")
	private String serviceName;
}
