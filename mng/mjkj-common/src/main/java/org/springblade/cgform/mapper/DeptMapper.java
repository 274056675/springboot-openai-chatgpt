
package org.springblade.cgform.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.MjkjBladeDept;

import java.util.List;

/**
 * DeptMapper 接口
 *
 *
 */
public interface DeptMapper extends BaseMapper<MjkjBladeDept> {


	List<Long> getUserDeptList(Long userId);

	Integer getMaxSort(Long parentId);

	Long getDeptIdByYytId(String yytId);

	//添加部门用户中间表信息
	Integer insertDeptUser(String userId,String deptId);

	/**
	 * 根据部门获取人员
	 * @param deptId
	 * @return
	 */
	String getRenYuanByDeptId(String deptId);
}
