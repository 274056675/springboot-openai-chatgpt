
package org.springblade.auth.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 客户端信息
 *
 *
 */
@Component
public class BladeClientDetailsServiceImpl extends JdbcClientDetailsService {

	public BladeClientDetailsServiceImpl(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 缓存客户端信息
	 *
	 * @param clientId 客户端id
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) {
		try {
			return super.loadClientByClientId(clientId);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
