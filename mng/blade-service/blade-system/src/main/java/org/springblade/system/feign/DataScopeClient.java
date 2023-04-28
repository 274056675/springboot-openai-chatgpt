
package org.springblade.system.feign;

import lombok.RequiredArgsConstructor;
import org.springblade.core.datascope.constant.DataScopeConstant;
import org.springblade.core.datascope.model.DataScopeModel;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.utils.CollectionUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据权限Feign实现类
 *
 *
 */
@NonDS
@ApiIgnore
@RestController
@RequiredArgsConstructor
public class DataScopeClient implements IDataScopeClient {

	private static final DataScopeModel SEARCHED_DATA_SCOPE_MODEL = new DataScopeModel(Boolean.TRUE);

	private final JdbcTemplate jdbcTemplate;

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	@Override
	@GetMapping(GET_DATA_SCOPE_BY_MAPPER)
	public DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		List<Object> args = new ArrayList<>(Collections.singletonList(mapperId));
		List<Long> roleIds = Func.toLongList(roleId);
		args.addAll(roleIds);
		// 增加searched字段防止未配置的参数重复读库导致缓存击穿
		// 后续若有新增配置则会清空缓存重新加载
		DataScopeModel dataScope;
		List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.dataByMapper(roleIds.size()), args.toArray(), new BeanPropertyRowMapper<>(DataScopeModel.class));
		if (CollectionUtil.isNotEmpty(list)) {
			dataScope = list.iterator().next();
			dataScope.setSearched(Boolean.TRUE);
		} else {
			dataScope = SEARCHED_DATA_SCOPE_MODEL;
		}
		return dataScope;
	}

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	@Override
	@GetMapping(GET_DATA_SCOPE_BY_CODE)
	public DataScopeModel getDataScopeByCode(String code) {
		// 增加searched字段防止未配置的参数重复读库导致缓存击穿
		// 后续若有新增配置则会清空缓存重新加载
		DataScopeModel dataScope;
		List<DataScopeModel> list = jdbcTemplate.query(DataScopeConstant.DATA_BY_CODE, new Object[]{code}, new BeanPropertyRowMapper<>(DataScopeModel.class));
		if (CollectionUtil.isNotEmpty(list)) {
			dataScope = list.iterator().next();
			dataScope.setSearched(Boolean.TRUE);
		} else {
			dataScope = SEARCHED_DATA_SCOPE_MODEL;
		}
		return dataScope;
	}

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	@Override
	@GetMapping(GET_DEPT_ANCESTORS)
	public List<Long> getDeptAncestors(Long deptId) {
		return jdbcTemplate.queryForList(DataScopeConstant.DATA_BY_DEPT, new Object[]{deptId}, Long.class);
	}
}
