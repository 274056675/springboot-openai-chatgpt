package org.springblade.config.autopoi.poi.handler.inter;

import java.util.Map;

/**
 * Excel 导入导出 数据处理接口
 */
public interface IExcelDataHandler {

	/**
	 * 导出处理方法
	 * 
	 * @param obj
	 *            当前对象
	 * @param name
	 *            当前字段名称
	 * @param value
	 *            当前值
	 * @return
	 */
	public Object exportHandler(Object obj, String name, Object value);

	/**
	 * 获取需要处理的字段,导入和导出统一处理了, 减少书写的字段
	 * 
	 * @return
	 */
	public String[] getNeedHandlerFields();

	/**
	 * 导入处理方法 当前对象,当前字段名称,当前值
	 * 
	 * @param obj
	 *            当前对象
	 * @param name
	 *            当前字段名称
	 * @param value
	 *            当前值
	 * @return
	 */
	public Object importHandler(Object obj, String name, Object value);

	/**
	 * 设置需要处理的属性列表
	 * 
	 * @param fields
	 */
	public void setNeedHandlerFields(String[] fields);

	/**
	 * 设置Map导入,自定义 put
	 * 
	 * @param map
	 * @param originKey
	 * @param value
	 */
	public void setMapValue(Map<String, Object> map, String originKey, Object value);

}
