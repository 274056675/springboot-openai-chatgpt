
package org.springblade.cgform.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springblade.cgform.entity.ChatgptBludeUser;
import org.springblade.cgform.mapper.UserMapper;
import org.springblade.cgform.service.IUserService;
import lombok.AllArgsConstructor;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 *
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, ChatgptBludeUser> implements IUserService {



}
