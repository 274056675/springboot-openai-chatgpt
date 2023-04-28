
package org.springblade.cgform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.cgform.entity.ChatgptBladeRole;
import org.springblade.cgform.mapper.RoleMapper;
import org.springblade.cgform.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 服务实现类
 *
 *
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, ChatgptBladeRole> implements IRoleService {


}
