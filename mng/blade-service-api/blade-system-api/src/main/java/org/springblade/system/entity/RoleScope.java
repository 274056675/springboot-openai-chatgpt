
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体类
 *
 *
 */
@Data
@TableName("blade_role_scope")
@ApiModel(value = "RoleScope对象", description = "RoleScope对象")
public class RoleScope implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 权限类型
	 */
	@ApiModelProperty(value = "权限类型")
	private Integer scopeCategory;

	/**
	 * 权限id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "权限id")
	private Long scopeId;

	/**
	 * 角色id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "角色id")
	private Long roleId;


}
