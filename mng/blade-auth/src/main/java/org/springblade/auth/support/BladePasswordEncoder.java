
package org.springblade.auth.support;

import org.springblade.core.tool.utils.DigestUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码加密
 *
 *
 */
public class BladePasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return DigestUtil.encrypt((String) rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(encode(rawPassword));
	}

}
