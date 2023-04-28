
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.util.Date;

/**
 * 实体类
 *
 *
 */
@Data
@TableName("blade_user")
@EqualsAndHashCode(callSuper = true)
public class BludeUser extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	private String code;
	/**
	 * 用户平台
	 */
	private Integer userType;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 真名
	 */
	private String realName;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 部门id
	 */
	private String deptId;
	/**
	 * 岗位id
	 */
	private String postId;


}
