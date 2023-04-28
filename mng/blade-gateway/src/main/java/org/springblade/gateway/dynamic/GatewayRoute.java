
package org.springblade.gateway.dynamic;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway的路由定义模型
 *
 *
 */
@Data
public class GatewayRoute {

	/**
	 * 路由的id
	 */
	private String id;

	/**
	 * 路由断言集合配置
	 */
	private List<GatewayPredicate> predicates = new ArrayList<>();

	/**
	 * 路由过滤器集合配置
	 */
	private List<GatewayFilter> filters = new ArrayList<>();

	/**
	 * 路由规则转发的目标uri
	 */
	private String uri;

	/**
	 * 路由执行的顺序
	 */
	private int order = 0;
}
