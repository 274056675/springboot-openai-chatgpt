
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

/**
 * 实体类
 *
 *
 */
@Data
@TableName("blade_scope_api")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiScope对象", description = "ApiScope对象")
public class ApiScope extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单主键
	 */
	@ApiModelProperty(value = "菜单主键")
	private Long menuId;
	/**
	 * 资源编号
	 */
	@ApiModelProperty(value = "资源编号")
	private String resourceCode;
	/**
	 * 接口权限名称
	 */
	@ApiModelProperty(value = "接口权限名称")
	private String scopeName;
	/**
	 * 接口权限字段
	 */
	@ApiModelProperty(value = "接口权限字段")
	private String scopePath;
	/**
	 * 接口权限类型
	 */
	@ApiModelProperty(value = "接口权限类型")
	private Integer scopeType;
	/**
	 * 接口权限备注
	 */
	@ApiModelProperty(value = "接口权限备注")
	private String remark;


}
