
package org.springblade.system.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.CommonConstant;
import org.springblade.common.constant.TenantConstant;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.BladeTenantProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.*;
import org.springblade.system.cache.DictCache;
import org.springblade.system.cache.ParamCache;
import org.springblade.system.cache.SysCache;
import org.springblade.system.entity.Tenant;
import org.springblade.system.enums.DictEnum;
import org.springblade.system.feign.ISysClient;
import org.springblade.system.user.cache.UserCache;
import org.springblade.system.user.entity.*;
import org.springblade.system.user.enums.UserEnum;
import org.springblade.system.user.excel.UserExcel;
import org.springblade.system.user.mapper.MjkjLoginMapper;
import org.springblade.system.user.mapper.UserMapper;
import org.springblade.system.user.service.IUserDeptService;
import org.springblade.system.user.service.IUserOauthService;
import org.springblade.system.user.service.IUserService;
import org.springblade.system.user.vo.UserVO;
import org.springblade.system.user.wrapper.UserWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.springblade.common.constant.CommonConstant.DEFAULT_PARAM_PASSWORD;

/**
 * 服务实现类
 *
 *
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
	private static final String GUEST_NAME = "guest";

	private final IUserDeptService userDeptService;
	private final IUserOauthService userOauthService;
	private final ISysClient sysClient;
	private final BladeTenantProperties tenantProperties;
	private final MjkjLoginMapper loginMapper;


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean submit(User user) {
		if (StringUtil.isBlank(user.getTenantId())) {
			user.setTenantId(BladeConstant.ADMIN_TENANT_ID);
		}
		String tenantId = user.getTenantId();
		Tenant tenant = SysCache.getTenant(tenantId);
		if (Func.isNotEmpty(tenant)) {
			Integer accountNumber = tenant.getAccountNumber();
			if (tenantProperties.getLicense()) {
				String licenseKey = tenant.getLicenseKey();
				String decrypt = DesUtil.decryptFormHex(licenseKey, TenantConstant.DES_KEY);
				accountNumber = JsonUtil.parse(decrypt, Tenant.class).getAccountNumber();
			}
			Long tenantCount = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId));
			if (accountNumber != null && accountNumber > 0 && accountNumber <= tenantCount) {
				throw new ServiceException("当前租户已到最大账号额度!");
			}
		}
		if (Func.isNotEmpty(user.getPassword())) {
			user.setPassword(DigestUtil.encrypt(user.getPassword()));
		}
		Long userCount = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId).eq(User::getAccount, user.getAccount()));
		if (userCount > 0L && Func.isEmpty(user.getId())) {
			throw new ServiceException(StringUtil.format("当前用户 [{}] 已存在!", user.getAccount()));
		}
		boolean flag = save(user);

		return flag && submitUserDept(user);
	}

	private Map<String, Object> insertSimpleData(String tableName, Map<String, Object> map) {
		if (Func.isEmpty(map)) {
			map = new LinkedHashMap<>();
		}
		Object id = map.get("id");
		if (Func.isEmpty(id)) {
			map.put("id", IdWorker.getId());
		}
		String feildSql = "";
		String dataSql = "";
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String field = entry.getKey();
			Object value = entry.getValue();
			feildSql += field + ",";
			dataSql += getSql(field, value) + ",";
		}
		if (feildSql.endsWith(",")) {
			feildSql = feildSql.substring(0, feildSql.length() - 1);
			dataSql = dataSql.substring(0, dataSql.length() - 1);
		}
		String sql = "insert into " + tableName + "(" + feildSql + ") values (" + dataSql + ")";

		map.put("select_sql", sql);
		return map;
	}

	private String getSql(String dbFieldName, Object dbType) {
		if (dbType instanceof Integer) {
			return "#{" + dbFieldName + ",jdbcType=INTEGER}";
		} else if (dbType instanceof Long) {
			return "#{" + dbFieldName + ",jdbcType=BIGINT}";
		} else if (dbType instanceof Double) {
			return "#{" + dbFieldName + ",jdbcType=DOUBLE}";
		} else if (dbType instanceof BigDecimal) {
			return "#{" + dbFieldName + ",jdbcType=DECIMAL}";
		} else if (dbType instanceof Date) {
			return "#{" + dbFieldName + "}";
		} else {
			return "#{" + dbFieldName + ",jdbcType=VARCHAR}";
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUser(User user) {
		String tenantId = user.getTenantId();
		Long userCount = baseMapper.selectCount(
			Wrappers.<User>query().lambda()
				.eq(User::getTenantId, tenantId)
				.eq(User::getAccount, user.getAccount())
				.notIn(User::getId, user.getId())
		);
		if (userCount > 0L) {
			throw new ServiceException(StringUtil.format("当前用户 [{}] 已存在!", user.getAccount()));
		}
		return updateUserInfo(user) && submitUserDept(user);
	}

	@Override
	public boolean updateUserInfo(User user) {
		user.setPassword(null);
		return updateById(user);
	}

	private boolean submitUserDept(User user) {
		List<Long> deptIdList = Func.toLongList(user.getDeptId());
		List<UserDept> userDeptList = new ArrayList<>();
		deptIdList.forEach(deptId -> {
			UserDept userDept = new UserDept();
			userDept.setUserId(user.getId());
			userDept.setDeptId(deptId);
			userDeptList.add(userDept);
		});
		userDeptService.remove(Wrappers.<UserDept>update().lambda().eq(UserDept::getUserId, user.getId()));
		return userDeptService.saveBatch(userDeptList);
	}

	@Override
	public IPage<User> selectUserPage(IPage<User> page, User user, Long deptId, String tenantId) {
		List<Long> deptIdList = SysCache.getDeptChildIds(deptId);
		List<String> memberRoleIdList = baseMapper.getMemberRoleId();
		return page.setRecords(baseMapper.selectUserPage(page, user, deptIdList, tenantId,memberRoleIdList));
	}

	@Override
	public IPage<UserVO> selectUserSearch(UserVO user, Query query) {
		LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>query().lambda();
		String tenantId = AuthUtil.getTenantId();
		if (StringUtil.isNotBlank(tenantId)) {
			queryWrapper.eq(User::getTenantId, tenantId);
		}
		if (StringUtil.isNotBlank(user.getName())) {
			queryWrapper.like(User::getName, user.getName());
		}
		if (StringUtil.isNotBlank(user.getDeptName())) {
			String deptIds = SysCache.getDeptIdsByFuzzy(AuthUtil.getTenantId(), user.getDeptName());
			if (StringUtil.isNotBlank(deptIds)) {
				queryWrapper.and(wrapper -> {
					List<String> ids = Func.toStrList(deptIds);
					ids.forEach(id -> wrapper.like(User::getDeptId, id).or());
				});
			}
		}
		if (StringUtil.isNotBlank(user.getPostName())) {
			String postIds = SysCache.getPostIdsByFuzzy(AuthUtil.getTenantId(), user.getPostName());
			if (StringUtil.isNotBlank(postIds)) {
				queryWrapper.and(wrapper -> {
					List<String> ids = Func.toStrList(postIds);
					ids.forEach(id -> wrapper.like(User::getPostId, id).or());
				});
			}
		}
		IPage<User> pages = this.page(Condition.getPage(query), queryWrapper);
		return UserWrapper.build().pageVO(pages);
	}

	@Override
	public User userByAccount(String tenantId, String account) {
		return baseMapper.selectOne(Wrappers.<User>query().lambda().eq(User::getTenantId, tenantId).eq(User::getAccount, account).eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED));
	}

	@Override
	public UserInfo userInfo(Long userId) {
		User user = baseMapper.selectById(userId);
		return buildUserInfo(user);
	}

	@Override
	public UserInfo userInfo(String tenantId, String account) {
		User user = baseMapper.getUser(tenantId, account);
		return buildUserInfo(user);
	}

	@Override
	public UserInfo userInfo(String tenantId, String account, UserEnum userEnum) {
		User user = baseMapper.getUser(tenantId, account);
		return buildUserInfo(user, userEnum);
	}

	private UserInfo buildUserInfo(User user) {
		return buildUserInfo(user, UserEnum.WEB);
	}

	private UserInfo buildUserInfo(User user, UserEnum userEnum) {
		if (ObjectUtil.isEmpty(user)) {
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		if (Func.isNotEmpty(user)) {

			R<List<String>> result = sysClient.getRoleAliases(user.getRoleId());
			if (result.isSuccess()) {
				List<String> roleAlias = result.getData();
				userInfo.setRoles(roleAlias);
			}
		}
		R<Tenant> tenant = sysClient.getTenant(user.getTenantId());//魔晶定制
		// 根据每个用户平台，建立对应的detail表，通过查询将结果集写入到detail字段
		Kv detail = Kv.create().set("type", userEnum.getName());
		detail.put("tenant_name",tenant.getData().getTenantName());//魔晶定制
		userInfo.setDetail(detail);
		return userInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserInfo userInfo(UserOauth userOauth) {
		UserOauth uo = userOauthService.getOne(Wrappers.<UserOauth>query().lambda().eq(UserOauth::getUuid, userOauth.getUuid()).eq(UserOauth::getSource, userOauth.getSource()));
		UserInfo userInfo;
		if (Func.isNotEmpty(uo) && Func.isNotEmpty(uo.getUserId())) {
			userInfo = this.userInfo(uo.getUserId());
			userInfo.setOauthId(Func.toStr(uo.getId()));
		} else {
			userInfo = new UserInfo();
			if (Func.isEmpty(uo)) {
				//userOauthService.save(userOauth); 魔晶定制，不存在不给登录
				userInfo.setOauthId(Func.toStr(userOauth.getId()));
			} else {
				userInfo.setOauthId(Func.toStr(uo.getId()));
			}
			User user = new User();
			user.setAccount(userOauth.getUsername());
			user.setTenantId(userOauth.getTenantId());
			userInfo.setUser(user);
			userInfo.setRoles(Collections.singletonList(GUEST_NAME));
		}
		return userInfo;
	}

	@Override
	public boolean grant(String userIds, String roleIds) {
		User user = new User();
		user.setRoleId(roleIds);
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
	}

	@Override
	public boolean resetPassword(String userIds) {
		User user = new User();
		user.setPassword(DigestUtil.encrypt(DigestUtil.md5Hex(CommonConstant.DEFAULT_PASSWORD)));
		user.setUpdateTime(DateUtil.now());
		return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
	}

	@Override
	public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
		User user = getById(userId);
		if (!newPassword.equals(newPassword1)) {
			throw new ServiceException("请输入正确的确认密码!");
		}
		if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
			throw new ServiceException("原密码不正确!");
		}
		return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.encrypt(newPassword)).eq(User::getId, userId));
	}

	@Override
	public boolean removeUser(String userIds) {
		if (Func.contains(Func.toLongArray(userIds), AuthUtil.getUserId())) {
			throw new ServiceException("不能删除本账号!");
		}
		return deleteLogic(Func.toLongList(userIds));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void importUser(List<UserExcel> data, Boolean isCovered) {
		data.forEach(userExcel -> {
			User user = Objects.requireNonNull(BeanUtil.copy(userExcel, User.class));
			// 设置用户平台
			user.setUserType(Func.toInt(DictCache.getKey(DictEnum.USER_TYPE, userExcel.getUserTypeName()), 1));
			// 设置部门ID
			user.setDeptId(Func.toStrWithEmpty(SysCache.getDeptIds(userExcel.getTenantId(), userExcel.getDeptName()), StringPool.EMPTY));
			// 设置岗位ID
			user.setPostId(Func.toStrWithEmpty(SysCache.getPostIds(userExcel.getTenantId(), userExcel.getPostName()), StringPool.EMPTY));
			// 设置角色ID
			user.setRoleId(Func.toStrWithEmpty(SysCache.getRoleIds(userExcel.getTenantId(), userExcel.getRoleName()), StringPool.EMPTY));
			// 设置租户ID
			if (!AuthUtil.isAdministrator() || StringUtil.isBlank(user.getTenantId())) {
				user.setTenantId(AuthUtil.getTenantId());
			}
			// 覆盖数据
			if (isCovered) {
				// 查询用户是否存在
				User oldUser = UserCache.getUser(userExcel.getTenantId(), userExcel.getAccount());
				if (oldUser != null && oldUser.getId() != null) {
					user.setId(oldUser.getId());
					this.updateUser(user);
					return;
				}
			}
			// 获取默认密码配置
			String initPassword = ParamCache.getValue(DEFAULT_PARAM_PASSWORD);
			user.setPassword(initPassword);
			this.submit(user);
		});
	}

	@Override
	public List<UserExcel> exportUser(Wrapper<User> queryWrapper) {
		List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
		userList.forEach(user -> {
			user.setUserTypeName(DictCache.getValue(DictEnum.USER_TYPE, user.getUserType()));
			user.setRoleName(StringUtil.join(SysCache.getRoleNames(user.getRoleId())));
			user.setDeptName(StringUtil.join(SysCache.getDeptNames(user.getDeptId())));
			user.setPostName(StringUtil.join(SysCache.getPostNames(user.getPostId())));
		});
		return userList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerGuest(User user, Long oauthId) {
		Tenant tenant = SysCache.getTenant(user.getTenantId());
		if (tenant == null || tenant.getId() == null) {
			throw new ServiceException("租户信息错误!");
		}
		UserOauth userOauth = userOauthService.getById(oauthId);
		if (userOauth == null || userOauth.getId() == null) {
			throw new ServiceException("第三方登陆信息错误!");
		}
		user.setRealName(user.getName());
		user.setAvatar(userOauth.getAvatar());
		user.setRoleId(StringPool.MINUS_ONE);
		user.setDeptId(StringPool.MINUS_ONE);
		user.setPostId(StringPool.MINUS_ONE);
		boolean userTemp = this.submit(user);
		userOauth.setUserId(user.getId());
		userOauth.setTenantId(user.getTenantId());
		boolean oauthTemp = userOauthService.updateById(userOauth);
		return (userTemp && oauthTemp);
	}
	@Override
	public boolean updatePlatform(Long userId, Integer userType, String userExt) {
		if (userType.equals(UserEnum.WEB.getCategory())) {
			UserWeb userWeb = new UserWeb();
			UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, userId));
			if (ObjectUtil.isNotEmpty(query)) {
				userWeb.setId(query.getId());
			}
			userWeb.setUserId(userId);
			userWeb.setUserExt(userExt);
			return userWeb.insertOrUpdate();
		} else if (userType.equals(UserEnum.APP.getCategory())) {
			UserApp userApp = new UserApp();
			UserApp query = userApp.selectOne(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserId, userId));
			if (ObjectUtil.isNotEmpty(query)) {
				userApp.setId(query.getId());
			}
			userApp.setUserId(userId);
			userApp.setUserExt(userExt);
			return userApp.insertOrUpdate();
		} else {
			UserOther userOther = new UserOther();
			UserOther query = userOther.selectOne(Wrappers.<UserOther>lambdaQuery().eq(UserOther::getUserId, userId));
			if (ObjectUtil.isNotEmpty(query)) {
				userOther.setId(query.getId());
			}
			userOther.setUserId(userId);
			userOther.setUserExt(userExt);
			return userOther.insertOrUpdate();
		}
	}

	@Override
	public UserVO platformDetail(User user) {
		User detail = baseMapper.selectOne(Condition.getQueryWrapper(user));
		UserVO userVO = UserWrapper.build().entityVO(detail);
		if (userVO.getUserType().equals(UserEnum.WEB.getCategory())) {
			UserWeb userWeb = new UserWeb();
			UserWeb query = userWeb.selectOne(Wrappers.<UserWeb>lambdaQuery().eq(UserWeb::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				userVO.setUserExt(query.getUserExt());
			}
		} else if (userVO.getUserType().equals(UserEnum.APP.getCategory())) {
			UserApp userApp = new UserApp();
			UserApp query = userApp.selectOne(Wrappers.<UserApp>lambdaQuery().eq(UserApp::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				userVO.setUserExt(query.getUserExt());
			}
		} else {
			UserOther userOther = new UserOther();
			UserOther query = userOther.selectOne(Wrappers.<UserOther>lambdaQuery().eq(UserOther::getUserId, user.getId()));
			if (ObjectUtil.isNotEmpty(query)) {
				userVO.setUserExt(query.getUserExt());
			}
		}
		return userVO;
	}

	//魔晶定制
	@Override
	public UserInfo userInfoDel(Long userId){
		User user = baseMapper.getUserByIdDel(userId);
		return buildUserInfo(user);
	}

	//获取所有后台用户
	@Override
	public List<User> getAllHtyh(){
		return baseMapper.getAllHtyh();
	}


}
