
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据字典实体类
 *
 *
 * @since 2021-05-27
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Dict对象", description = "数据字典")
public class SysDict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 字典名称
	*/
		@ApiModelProperty(value = "字典名称")
		private String dictName;
	/**
	* 字典编码
	*/
		@ApiModelProperty(value = "字典编码")
		private String dictCode;
	/**
	* 描述
	*/
		@ApiModelProperty(value = "描述")
		private String description;
	/**
	* 字典类型0为string,1为number
	*/
		@ApiModelProperty(value = "字典类型0为string,1为number")
		private Integer type;


}
