
package org.springblade.cgform.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.cgform.entity.MjkjBladeDept;
import org.springblade.cgform.mapper.DeptMapper;
import org.springblade.cgform.service.IDeptService;
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
public class DeptServiceImpl extends ServiceImpl<DeptMapper, MjkjBladeDept> implements IDeptService {


}
