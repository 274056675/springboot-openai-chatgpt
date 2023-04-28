
package org.springblade.system.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.system.user.entity.UserOauth;
import org.springblade.system.user.mapper.UserOauthMapper;
import org.springblade.system.user.service.IUserOauthService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 *
 */
@Service
@AllArgsConstructor
public class UserOauthServiceImpl extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {

}
