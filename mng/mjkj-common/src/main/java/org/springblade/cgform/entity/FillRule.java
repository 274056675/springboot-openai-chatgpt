
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 填值规则实体类
 *
 *
 * @since 2021-07-03
 */
@Data
@TableName("sys_fill_rule")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FillRule对象", description = "填值规则")
public class FillRule extends BaseEntity {

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
	* 规则实现类
	*/
		@ApiModelProperty(value = "规则实现类")
		private String ruleClass;
	/**
	* 规则参数
	*/
		@ApiModelProperty(value = "规则参数")
		private String ruleParams;


}
