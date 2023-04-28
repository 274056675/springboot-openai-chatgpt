
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
@TableName("blade_scope_data")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DataScope对象", description = "DataScope对象")
public class DataScope extends BaseEntity {

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
	 * 数据权限名称
	 */
	@ApiModelProperty(value = "数据权限名称")
	private String scopeName;
	/**
	 * 数据权限可见字段
	 */
	@ApiModelProperty(value = "数据权限可见字段")
	private String scopeField;
	/**
	 * 数据权限类名
	 */
	@ApiModelProperty(value = "数据权限类名")
	private String scopeClass;
	/**
	 * 数据权限字段
	 */
	@ApiModelProperty(value = "数据权限字段")
	private String scopeColumn;
	/**
	 * 数据权限类型
	 */
	@ApiModelProperty(value = "数据权限类型")
	private Integer scopeType;
	/**
	 * 数据权限值域
	 */
	@ApiModelProperty(value = "数据权限值域")
	private String scopeValue;
	/**
	 * 数据权限备注
	 */
	@ApiModelProperty(value = "数据权限备注")
	private String remark;


}
