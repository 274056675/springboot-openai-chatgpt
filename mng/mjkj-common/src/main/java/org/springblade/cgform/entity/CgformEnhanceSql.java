
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * sql增强实体类
 *
 *
 * @since 2021-05-22
 */
@Data
@TableName("onl_cgform_enhance_sql")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformEnhanceSql对象", description = "sql增强")
public class CgformEnhanceSql extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 按钮编码
     */
    @ApiModelProperty(value = "按钮编码")
    private String buttonCode;
    /**
     * SQL内容
     */
    @ApiModelProperty(value = "SQL内容")
    private String cgbSql;
    /**
     * Sql名称
     */
    @ApiModelProperty(value = "Sql名称")
    private String cgbSqlName;
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

	@ApiModelProperty(value = "服务名")
	private String serviceName;
}
