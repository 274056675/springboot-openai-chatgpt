
package org.springblade.system.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.system.user.entity.User;
import org.springblade.system.user.excel.UserExcel;

import java.util.List;

/**
 * Mapper 接口
 *
 *
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param user
	 * @param deptIdList
	 * @param tenantId
	 * @return
	 */
	List<User> selectUserPage(IPage<User> page, @Param("user") User user, @Param("deptIdList") List<Long> deptIdList, @Param("tenantId") String tenantId,@Param("memberRoleIdList")List<String> memberRoleIdList);

	/**
	 * 获取用户
	 *
	 * @param tenantId
	 * @param account
	 * @return
	 */
	User getUser(String tenantId, String account);

	/**
	 * 获取导出用户数据
	 *
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(@Param("ew") Wrapper<User> queryWrapper);


	User getUserByIdDel(Long userId);

	//获取用户id
	List<String> getMemberRoleId();

	List<User> getAllHtyh();
}
