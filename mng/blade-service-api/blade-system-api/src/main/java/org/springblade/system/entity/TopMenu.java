
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * 顶部菜单表实体类
 *
 *
 */
@Data
@TableName("blade_top_menu")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TopMenu对象", description = "顶部菜单表")
public class TopMenu extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 顶部菜单编号
	 */
	@ApiModelProperty(value = "顶部菜单编号")
	private String code;
	/**
	 * 顶部菜单名
	 */
	@ApiModelProperty(value = "顶部菜单名")
	private String name;
	/**
	 * 顶部菜单资源
	 */
	@ApiModelProperty(value = "顶部菜单资源")
	private String source;
	/**
	 * 顶部菜单排序
	 */
	@ApiModelProperty(value = "顶部菜单排序")
	private Integer sort;


}
