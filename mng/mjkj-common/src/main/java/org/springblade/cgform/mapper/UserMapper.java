
package org.springblade.cgform.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springblade.cgform.entity.ChatgptBludeUser;

import java.util.List;

/**
 * Mapper 接口
 *
 *
 */
public interface UserMapper extends BaseMapper<ChatgptBludeUser> {

	List<ChatgptBludeUser> getDeptUser(String tenantId, String deptId);

}
