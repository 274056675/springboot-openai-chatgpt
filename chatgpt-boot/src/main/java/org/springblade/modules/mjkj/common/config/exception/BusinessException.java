package org.springblade.modules.mjkj.common.config.exception;

/**
 * 自定义业务异常
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException(String msg) {
		super(msg);
	}
}
