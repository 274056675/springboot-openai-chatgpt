
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 租户产品表实体类
 *
 *
 */
@Data
@TableName("blade_tenant_package")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TenantPackage对象", description = "租户产品表")
public class TenantPackage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 产品包名称
	 */
	@ApiModelProperty(value = "产品包名称")
	private String packageName;
	/**
	 * 菜单ID
	 */
	@ApiModelProperty(value = "菜单ID")
	private String menuId;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;


}
