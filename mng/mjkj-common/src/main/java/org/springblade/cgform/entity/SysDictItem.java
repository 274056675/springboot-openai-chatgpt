
package org.springblade.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.tenant.mp.TenantEntity;

/**
 * 数据字典实体类
 *
 *
 * @since 2021-05-27
 */
@Data
@TableName("sys_dict_item")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DictItem对象", description = "数据字典")
public class SysDictItem extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	* 字典id
	*/
		@ApiModelProperty(value = "字典id")
		private String dictId;
	/**
	* 字典项文本
	*/
		@ApiModelProperty(value = "字典项文本")
		private String itemText;
	/**
	* 字典项值
	*/
		@ApiModelProperty(value = "字典项值")
		private String itemValue;
	/**
	* 描述
	*/
		@ApiModelProperty(value = "描述")
		private String description;
	/**
	* 排序
	*/
		@ApiModelProperty(value = "排序")
		private Integer sortOrder;


}
