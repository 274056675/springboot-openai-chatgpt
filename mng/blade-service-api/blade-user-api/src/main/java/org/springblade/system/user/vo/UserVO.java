
package org.springblade.system.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.system.user.entity.User;

/**
 * 视图实体类
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserVO对象", description = "UserVO对象")
public class UserVO extends User {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 密码
	 */
	@JsonIgnore
	private String password;

	/**
	 * 租户名
	 */
	private String tenantName;

	/**
	 * 用户平台名
	 */
	private String userTypeName;

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 部门名
	 */
	private String deptName;

	/**
	 * 岗位名
	 */
	private String postName;

	/**
	 * 性别
	 */
	private String sexName;

	/**
	 * 拓展信息
	 */
	private String userExt;
}
