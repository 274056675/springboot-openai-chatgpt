
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 编码校验规则实体类
 *
 *
 * @since 2021-07-03
 */
@Data
@TableName("sys_check_rule")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CheckRule对象", description = "编码校验规则")
public class CheckRule extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 规则名称
	*/
		@ApiModelProperty(value = "规则名称")
		private String ruleName;
	/**
	* 规则Code
	*/
		@ApiModelProperty(value = "规则Code")
		private String ruleCode;
	/**
	* 规则JSON
	*/
		@ApiModelProperty(value = "规则JSON")
		private String ruleJson;
	/**
	* 规则描述
	*/
		@ApiModelProperty(value = "规则描述")
		private String ruleDescription;


}
