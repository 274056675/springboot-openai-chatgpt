package org.springblade.config.autopoi.poi.handler.inter;

/**
 * 接口自定义处理类
 */
public interface IExcelReadRowHanlder<T> {
	/**
	 * 处理解析对象
	 * 
	 * @param t
	 */
	public void hanlder(T t);

}
