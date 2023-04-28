
package org.springblade.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.system.entity.ApiScope;

/**
 * 视图实体类
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiScopeVO对象", description = "ApiScopeVO对象")
public class ApiScopeVO extends ApiScope {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则类型名
	 */
	private String scopeTypeName;
}
