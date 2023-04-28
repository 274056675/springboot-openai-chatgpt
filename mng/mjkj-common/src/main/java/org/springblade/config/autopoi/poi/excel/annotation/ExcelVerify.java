package org.springblade.config.autopoi.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 导入校验
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelVerify {
	/**
	 * 接口校验
	 * 
	 * @return
	 */
	public boolean interHandler() default false;

	/**
	 * 是电子邮件
	 * 
	 * @return
	 */
	public boolean isEmail() default false;

	/**
	 * 是13位移动电话
	 * 
	 * @return
	 */
	public boolean isMobile() default false;

	/**
	 * 是座机号码
	 * 
	 * @return
	 */
	public boolean isTel() default false;

	/**
	 * 最大长度
	 * 
	 * @return
	 */
	public int maxLength() default -1;

	/**
	 * 最小长度
	 * 
	 * @return
	 */
	public int minLength() default -1;

	/**
	 * 不允许空
	 * 
	 * @return
	 */
	public boolean notNull() default false;

	/**
	 * 正在表达式
	 * 
	 * @return
	 */
	public String regex() default "";

	/**
	 * 正在表达式,错误提示信息
	 * 
	 * @return
	 */
	public String regexTip() default "数据不符合规范";

}
