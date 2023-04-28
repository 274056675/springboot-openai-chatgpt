
package org.springblade.system.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.tool.support.Kv;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 *
 */
@Data
@ApiModel(description = "用户信息")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 第三方授权id
	 */
	@ApiModelProperty(value = "第三方授权id")
	private String oauthId;

	/**
	 * 用户基础信息
	 */
	@ApiModelProperty(value = "用户")
	private User user;

	/**
	 * 拓展信息
	 */
	@ApiModelProperty(value = "拓展信息")
	private Kv detail;

	/**
	 * 权限标识集合
	 */
	@ApiModelProperty(value = "权限集合")
	private List<String> permissions;

	/**
	 * 角色集合
	 */
	@ApiModelProperty(value = "角色集合")
	private List<String> roles;

	//魔晶定制
	private String errorMsg;
}
