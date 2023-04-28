
package org.springblade.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * GrantVO
 *
 *
 */
@Data
public class GrantVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "roleIds集合")
	private List<Long> roleIds;

	@ApiModelProperty(value = "menuIds集合")
	private List<Long> menuIds;

	@ApiModelProperty(value = "topMenuIds集合")
	private List<Long> topMenuIds;

	@ApiModelProperty(value = "dataScopeIds集合")
	private List<Long> dataScopeIds;

	@ApiModelProperty(value = "apiScopeIds集合")
	private List<Long> apiScopeIds;

}
