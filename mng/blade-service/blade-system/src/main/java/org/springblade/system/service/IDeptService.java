
package org.springblade.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.system.entity.Dept;
import org.springblade.system.vo.DeptVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 *
 */
public interface IDeptService extends IService<Dept> {

	/**
	 * 懒加载部门列表
	 *
	 * @param tenantId
	 * @param parentId
	 * @param param
	 * @return
	 */
	List<DeptVO> lazyList(String tenantId, Long parentId, Map<String, Object> param);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<DeptVO> tree(String tenantId);

	/**
	 * 懒加载树形结构
	 *
	 * @param tenantId
	 * @param parentId
	 * @return
	 */
	List<DeptVO> lazyTree(String tenantId, Long parentId);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIds(String tenantId, String deptNames);

	/**
	 * 获取部门ID
	 *
	 * @param tenantId
	 * @param deptNames
	 * @return
	 */
	String getDeptIdsByFuzzy(String tenantId, String deptNames);

	/**
	 * 获取部门名
	 *
	 * @param deptIds
	 * @return
	 */
	List<String> getDeptNames(String deptIds);

	/**
	 * 获取子部门
	 *
	 * @param deptId
	 * @return
	 */
	List<Dept> getDeptChild(Long deptId);

	/**
	 * 删除部门
	 *
	 * @param ids
	 * @return
	 */
	boolean removeDept(String ids);

	/**
	 * 提交
	 *
	 * @param dept
	 * @return
	 */
	boolean submit(Dept dept);

	/**
	 * 部门信息查询
	 *
	 * @param deptName
	 * @param parentId
	 * @return
	 */
	List<DeptVO> search(String deptName, Long parentId);

}
