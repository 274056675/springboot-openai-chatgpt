
package org.springblade.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.system.entity.DictBiz;
import org.springblade.system.vo.DictBizVO;

import java.util.List;

/**
 * Mapper 接口
 *
 *
 */
public interface DictBizMapper extends BaseMapper<DictBiz> {

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	String getValue(String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<DictBiz> getList(String code);

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> tree();

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> parentTree();

}
