package org.springblade.config.autopoi.poi.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记是不是导出excel 标记为实体类
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelEntity {

	/**
	 * 定义excel导出ID 来限定导出字段,处理一个类对应多个不同名称的情况
	 */
	public String id() default "";

	/**
	 * 导出时，对应数据库的字段 主要是用户区分每个字段， 不能有annocation重名的 导出时的列名
	 * 导出排序跟定义了annotation的字段的顺序有关 可以使用a_id,b_id来确实是否使用
	 */
	public String name() default "";
}
