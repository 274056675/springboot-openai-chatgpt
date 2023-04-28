
package org.springblade.auth.constant;

/**
 * 授权校验常量
 *
 *
 */
public interface AuthConstant {

	/**
	 * 密码加密规则
	 */
	String ENCRYPT = "{blade}";

	/**
	 * blade_client表字段
	 */
	String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, authorized_grant_types, " +
		"web_server_redirect_uri, authorities, access_token_validity, " +
		"refresh_token_validity, additional_information, autoapprove";

	/**
	 * blade_client查询语句
	 */
	String BASE_STATEMENT = "select " + CLIENT_FIELDS + " from blade_client";

	/**
	 * blade_client查询排序
	 */
	String DEFAULT_FIND_STATEMENT = BASE_STATEMENT + " order by client_id";

	/**
	 * 查询client_id
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_STATEMENT + " where client_id = ?";

}
