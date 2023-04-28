package org.springblade.config.autopoi.poi.excel.imports.sax.parse;



import org.springblade.config.autopoi.poi.excel.entity.sax.SaxReadCellEntity;

import java.util.List;

public interface ISaxRowRead {
	/**
	 * 获取返回数据
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getList();

	/**
	 * 解析数据
	 * 
	 * @param index
	 * @param datas
	 */
	public void parse(int index, List<SaxReadCellEntity> datas);

}
