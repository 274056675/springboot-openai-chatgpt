package org.springblade.mng.config.exception;

/**
 * 自定义数据异常
 */
public class DBException extends Exception
{
	private static final long serialVersionUID = 1L;

	public DBException(String msg)
    {
        super(msg);
    }
}
