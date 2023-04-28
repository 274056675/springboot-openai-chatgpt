
package org.springblade.cgform.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springblade.cgform.entity.CgformEnhanceJs;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.enums.CgformConstant;
import org.springblade.cgform.model.CommonEntity;
import org.springblade.cgform.model.OnlineConfigModel;
import org.springblade.cgform.model.TreeDataModel;
import org.springblade.cgform.service.*;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.MjkjConstant;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.util.ConvertUtils;
import org.springblade.config.util.EnhanceJsUtil;
import org.springblade.config.util.SqlSymbolUtil;
import org.springblade.config.util.minio.MinioBladeFile;
import org.springblade.config.util.minio.MinioUtils;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.TreeModel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 *
 * @since 2021-05-20
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("cgform-api")
@Api(value = "表单开发接口", tags = "表单开发接口")
public class CgformApiController extends BaseController {

	private final ICgformHeadService cgformHeadService;

	private final ICgformFieldService cgformFieldService;


	private final ICgformEnhanceJsService jsService;

	private final ICgformEnhanceJavaService javaService;

	private final ICgformEnhanceSqlService sqlService;

	private final IOnlineService onlineService;


	@Autowired
	private MinioUtils minioUtils;

	//比较重要的表，不允许新增修改删除，只有超级管理员才有权限
	private final String noSaveOrUpdateStr="";



	@GetMapping("/detail/listByHeadId")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取详情", notes = "获取详情")
	public R<Map> fieldListByHeadId(@RequestParam("headId") Long headId) {
		CgformHead head = cgformHeadService.getById(headId);

		LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformField::getCgformHeadId, headId);
		wrapper.orderByAsc(CgformField::getOrderNum);
		List<CgformField> fieldList = cgformFieldService.list(wrapper);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("fieldList", fieldList);
		resultMap.put("head", head);
		resultMap.put("indexList", new ArrayList<>());

		return R.data(resultMap);
	}



	@GetMapping({"/getColumns/{headId}"})
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "功能测试 - 获取表头信息", notes = "获取表头信息")
	public R getColumns(@PathVariable("headId") Long headId) {
		// 根据表单基本属性id获取表单数据
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体不存在");
		}
		OnlineConfigModel onlineConfigModel = onlineService.queryOnlineConfig(head);
		if (Func.isNotEmpty(onlineConfigModel)) {
			String enhanceJs = onlineConfigModel.getEnhanceJs();
			String str = StringEscapeUtils.unescapeHtml(enhanceJs);
			onlineConfigModel.setEnhanceJs(str);

			String enhanceJsApp = onlineConfigModel.getEnhanceJsApp();
			String strApp = StringEscapeUtils.unescapeHtml(enhanceJsApp);
			onlineConfigModel.setEnhanceJsApp(strApp);
		}
		return R.data(onlineConfigModel);
	}

	@GetMapping({"/getFormItem/{headId}"})
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "功能测试 - 获取字段信息", notes = "获取字段信息")
	public R getFormItem(@PathVariable("headId") Long headId) {
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体不存在");
		}
		//获取js增强
		CgformEnhanceJs onlCgformEnhanceJs = jsService.queryEnhanceJs(headId, "form");
		JSONObject jsonObject = onlineService.queryOnlineFormObj(head, onlCgformEnhanceJs);
		if (head.getTableType() != CgformConstant.ONLINE_TABLE_TYPE_MAIN) {//表类型: 1单表、2主表、3附表
			return R.data(jsonObject);
		}
		JSONObject schemaJson = jsonObject.getJSONObject("schema");
		String subTableListStr = head.getSubTableStr();
		if (Func.isNotEmpty(subTableListStr)) {
			List<CgformHead> subHeadList = new ArrayList<CgformHead>();
			String[] subTableStrs = subTableListStr.split(",");
			for (String subTableStr : subTableStrs) {
				CgformHead subHead = cgformHeadService.getOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, subTableStr));
				if (Func.isEmpty(subHead)) {
					continue;
				}
				subHeadList.add(subHead);
			}


			if (subHeadList.size() > 0) {
				Collections.sort(subHeadList, new Comparator<CgformHead>() {
					@Override
					public int compare(CgformHead onlCgformHead1, CgformHead onlCgformHead2) {
						Integer orderNum1 = onlCgformHead1.getTabOrderNum();
						if (orderNum1 == null) {
							orderNum1 = 0;
						}

						Integer orderNum2 = onlCgformHead2.getTabOrderNum();
						if (orderNum2 == null) {
							orderNum2 = 0;
						}

						return orderNum1.compareTo(orderNum2);
					}
				});
				Iterator<CgformHead> iterator = subHeadList.iterator();

				while (iterator.hasNext()) {
					CgformHead subHead = iterator.next();
					List<CgformField> availableFields = this.cgformFieldService.queryAvailableFields(subHead.getId(), subHead.getTableName(), (String) null, false);
					EnhanceJsUtil.getJsFunction(onlCgformEnhanceJs, subHead.getTableName(), availableFields);
					JSONObject jsonObject2 = new JSONObject();
					List<String> disabledFields = new ArrayList<>();
					if (1 == subHead.getRelationType()) {//一对一关系
						jsonObject2 = SqlSymbolUtil.getFiledJson(availableFields, disabledFields, null);
					} else {
						jsonObject2.put("columns", SqlSymbolUtil.getColumns(availableFields, disabledFields));
					}

					jsonObject2.put("id", subHead.getId());
					jsonObject2.put("describe", subHead.getTableTxt());
					jsonObject2.put("key", subHead.getTableName());
					jsonObject2.put("view", "tab");
					jsonObject2.put("order", subHead.getTabOrderNum());
					jsonObject2.put("relationType", subHead.getRelationType());
					schemaJson.getJSONObject("properties").put(subHead.getTableName(), jsonObject2);
				}
			}
		}

		if (onlCgformEnhanceJs != null && ConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs())) {
			String enhanceJs = EnhanceJsUtil.getCgJs(onlCgformEnhanceJs.getCgJs());
			String str = StringEscapeUtils.unescapeHtml(enhanceJs);

			jsonObject.put("enhanceJs", str);
		}


		return R.data(jsonObject);
	}


	@ApiOperationSupport(order = 12)
	@GetMapping({"/getData/{headId}"})
	@ApiOperation(value = "功能测试 - 获取数据列表-ok", notes = "获取数据列表")
	public R<Map<String, Object>> getData(@PathVariable("headId") Long headId, HttpServletRequest req) {
		// 根据headId查询表单
		CgformHead onlCgformHead = cgformHeadService.getById(headId);
		if (Func.isEmpty(onlCgformHead)) {
			return R.fail("实体不存在");
		}
		//判断该接口是否要的登录
		String nologinSelect = onlCgformHead.getNologinSelect();
		if(Func.equals(nologinSelect,"0")){//需要登录
			BladeUser user = AuthUtil.getUser();
			if(Func.isEmpty(user)){
				return R.fail("登录已过期，请重新登录");
			}
			//登录成功，判断改表是否对外开放
			String noViewRoleStrList = onlCgformHead.getNoViewDataRole();
			if(Func.isNotEmpty(noViewRoleStrList)){//有设置某一个角色不允许访问
				List<Long> roleList = Func.toLongList(noViewRoleStrList);

				BladeUser onlineUser = AuthUtil.getUser();
				String roleIdStrList = onlineUser.getRoleId();
				List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

				List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
				if(Func.isNotEmpty(collect)){
					return R.fail("暂无查看数据权限");
				}
			}


		}


		try {
			String tableName = onlCgformHead.getTableName();
			Map<String, Object> params = SqlSymbolUtil.getParameterMap(req);//获取查询参数
			Map<String, Object> resultMap =new HashMap<>();
			if(Func.equals(onlCgformHead.getFormCategory(),"view")){//显示表
				resultMap.put("total",0);
				resultMap.put("records",new ArrayList<>());
			}else{
				resultMap = cgformFieldService.queryAutolistPage(tableName, headId, params, null);
			}
			Long total = MjkjUtils.getMap2Long(resultMap, "total");
			List<Map<String, Object>> dataList = (List) resultMap.get("records");

			if (Func.isEmpty(dataList)) {
				dataList = new ArrayList<>();
			}
			//走增强
			javaService.executeEnhanceList(onlCgformHead, MjkjConstant.ENHANCE_QUERY, dataList, params);
			//sql增强
			sqlService.executeEnhanceSqlList(onlCgformHead, MjkjConstant.ENHANCE_QUERY, params);
			//sql增强
			sqlService.executeEnhanceSqlList(onlCgformHead, MjkjConstant.ENHANCE_QUERYANEXPORT, params);
			//分页
			if (Func.isNotEmpty(params.get("dataTotal")) && Func.isNotEmpty(params.get("dataRecords"))) {
				resultMap.put("total", params.get("dataTotal"));
				resultMap.put("records", params.get("dataRecords"));
				resultMap.put("dataOther", params.get("dataOther"));
				resultMap.put("idList", params.get("idList"));
				return R.data(resultMap);
			}
			if (Func.isNotEmpty(params.get("dataOther"))) {
				resultMap.put("dataOther", params.get("dataOther"));
			}
			resultMap.put("dataOther", params.get("dataOther"));
			//自定义分页
			Integer pageSzie = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
			if (Func.isNotEmpty(dataList) && pageSzie != -521) {//分组
				int size = dataList.size();
				Object pageNoObj = params.get("pageNo");
				Object pageSizeObj = params.get("pageSize");

				if (size > total.intValue() && Func.isNotEmpty(pageNoObj) && Func.isNotEmpty(pageNoObj)) {//查询出来的数据大于总数
					Integer page = Func.toInt(pageNoObj);
					Integer pageSize = Func.toInt(pageSizeObj);
					dataList = dataList.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
					resultMap.put("total", size);
					resultMap.put("records", dataList);
				}

			} else if (Func.isNotEmpty(dataList) && pageSzie == -521) {
				resultMap.put("records", dataList);
			} else {
				//所有id
				Object idList = params.get("idList");
				if (Func.isNotEmpty(idList)) {
					resultMap.put("idList", idList);
				}
			}

			return R.data(resultMap);
		} catch (Exception e) {
			e.getStackTrace();
			return R.fail("数据库查询失败" + e.getMessage());
		}
	}

	/**
	 * 封装好的添加数据的接口,每个表单都可以调用
	 *
	 * @param headId
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	@ApiOperationSupport(order = 13)
	@PostMapping({"/addData/{headId}"})
	@ApiOperation(value = "功能测试 - 新增数据-ok", notes = "新增数据")
	public R<String> addData(@PathVariable("headId") Long headId,
							 @RequestBody JSONObject jsonObject) {
		// 根据表头id查询表单对象
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体对象不存在");
		}
		String tableName = head.getTableName();
		//普通人不允许操作
		List<String> noSaveOrUpdateTableList = Func.toStrList(noSaveOrUpdateStr);
		if(noSaveOrUpdateTableList.contains(tableName) && !AuthUtil.isAdministrator()){//属于检测表，但是当前又不是超级管理员
			return R.fail("该表只允许超级管理员操作");
		}

		//登录成功，判断改表是否对外开放
		String noRoleStrList = head.getNoOperationDataRole();
		if(Func.isNotEmpty(noRoleStrList)){//有设置某一个角色不允许访问
			List<Long> roleList = Func.toLongList(noRoleStrList);

			BladeUser onlineUser = AuthUtil.getUser();
			String roleIdStrList = onlineUser.getRoleId();
			List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

			List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
			if(Func.isNotEmpty(collect)){
				return R.fail("暂无操作数据权限");
			}
		}

		// 生成id
		String id = Func.toStr(SqlSymbolUtil.getIdWorkerId());
		jsonObject.put("id", id);
		try {
			String resultId = cgformHeadService.saveManyFormData(head, jsonObject);
			return R.data(resultId);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

	/**
	 * 封装好的添加数据的接口,每个表单都可以调用
	 *
	 * @param headId
	 * @param jsonList
	 * @return
	 * @throws Exception
	 */
	@ApiOperationSupport(order = 13)
	@PostMapping({"/addData/batch/{headId}"})
	@ApiOperation(value = "功能测试 - 新增数据(批量)-ok", notes = "新增数据（批量）")
	public R<List<String>> addDataBatch(@PathVariable("headId") Long headId,
										@RequestBody List<JSONObject> jsonList) {
		// 根据表头id查询表单对象
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体对象不存在");
		}
		String tableName = head.getTableName();
		//普通人不允许操作
		List<String> noSaveOrUpdateTableList = Func.toStrList(noSaveOrUpdateStr);
		if(noSaveOrUpdateTableList.contains(tableName) && !AuthUtil.isAdministrator()){//属于检测表，但是当前又不是超级管理员
			return R.fail("该表只允许超级管理员操作");
		}

		//登录成功，判断改表是否对外开放
		String noRoleStrList = head.getNoOperationDataRole();
		if(Func.isNotEmpty(noRoleStrList)){//有设置某一个角色不允许访问
			List<Long> roleList = Func.toLongList(noRoleStrList);

			BladeUser onlineUser = AuthUtil.getUser();
			String roleIdStrList = onlineUser.getRoleId();
			List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

			List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
			if(Func.isNotEmpty(collect)){
				return R.fail("暂无操作数据权限");
			}
		}
		try {
			List<String> idList = cgformHeadService.saveManyFormDataBatch(head, jsonList);
			return R.data(idList);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}


	/**
	 * 封装好的修改数据的接口,每个表单都可以调用
	 * (职称活动创建后调用此接口添加可申报的职称())
	 *
	 * @param headId
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	@ApiOperationSupport(order = 14)
	@PostMapping({"/editData/{headId}"})
	@ApiOperation(value = "功能测试 - 编辑数据-ok", notes = "编辑数据")
	public R editData(@PathVariable("headId") Long headId,
					  @RequestBody JSONObject jsonObject) throws Exception {
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体对象不存在");
		}
		String tableName = head.getTableName();
		//普通人不允许操作
		List<String> noSaveOrUpdateTableList = Func.toStrList(noSaveOrUpdateStr);
		if(noSaveOrUpdateTableList.contains(tableName) && !AuthUtil.isAdministrator()){//属于检测表，但是当前又不是超级管理员
			return R.fail("该表只允许超级管理员操作");
		}
		//登录成功，判断改表是否对外开放
		String noRoleStrList = head.getNoOperationDataRole();
		if(Func.isNotEmpty(noRoleStrList)){//有设置某一个角色不允许访问
			List<Long> roleList = Func.toLongList(noRoleStrList);

			BladeUser onlineUser = AuthUtil.getUser();
			String roleIdStrList = onlineUser.getRoleId();
			List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

			List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
			if(Func.isNotEmpty(collect)){
				return R.fail("暂无操作数据权限");
			}
		}
		cgformHeadService.editManyFormData(head, jsonObject);
		return R.success("成功");
	}

	@ApiOperationSupport(order = 14)
	@PostMapping({"/editData/batch/{headId}"})
	@ApiOperation(value = "功能测试 - 编辑数据-ok", notes = "编辑数据")
	public R editDataBatch(@PathVariable("headId") Long headId,
						   @RequestBody List<JSONObject> jsonList) throws Exception {
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体对象不存在");
		}
		String tableName = head.getTableName();
		//普通人不允许操作
		List<String> noSaveOrUpdateTableList = Func.toStrList(noSaveOrUpdateStr);
		if(noSaveOrUpdateTableList.contains(tableName) && !AuthUtil.isAdministrator()){//属于检测表，但是当前又不是超级管理员
			return R.fail("该表只允许超级管理员操作");
		}
		//登录成功，判断改表是否对外开放
		String noRoleStrList = head.getNoOperationDataRole();
		if(Func.isNotEmpty(noRoleStrList)){//有设置某一个角色不允许访问
			List<Long> roleList = Func.toLongList(noRoleStrList);

			BladeUser onlineUser = AuthUtil.getUser();
			String roleIdStrList = onlineUser.getRoleId();
			List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

			List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
			if(Func.isNotEmpty(collect)){
				return R.fail("暂无操作数据权限");
			}
		}
		cgformHeadService.editManyFormDataBatch(head, jsonList);
		return R.success("成功");
	}


	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "功能测试 - 详情数据-ok", notes = "详情数据")
	@GetMapping({"/detailData/{headId}/{id}"})
	public R getForm(@PathVariable("headId") Long headId, @PathVariable("id") String id, HttpServletRequest request) throws Exception {
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体不存在");
		}
		//判断该接口是否要的登录
		String nologinSelect = head.getNologinSelect();
		if(Func.equals(nologinSelect,"0")){//需要登录
			BladeUser user = AuthUtil.getUser();
			if(Func.isEmpty(user)){
				return R.fail("登录已过期，请重新登录");
			}
			//登录成功，判断改表是否对外开放
			String noRoleStrList = head.getNoViewDataRole();
			if(Func.isNotEmpty(noRoleStrList)){//有设置某一个角色不允许访问
				List<Long> roleList = Func.toLongList(noRoleStrList);

				BladeUser onlineUser = AuthUtil.getUser();
				String roleIdStrList = onlineUser.getRoleId();
				List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

				List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
				if(Func.isNotEmpty(collect)){
					return R.fail("暂无查看数据权限");
				}
			}
		}
		Map<String, Object> param = SqlSymbolUtil.getParameterMap(request);
		param.put("id", id);

		Map<String, Object> map =new HashMap<>();
		if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
			map = cgformHeadService.queryManyFormData(headId, id);
		}
		// 这个map有大用,不能为null
		if (Func.isEmpty(map)) {
			map = new HashMap<>();
		}
		map = javaService.executeEnhanceDetail(head, MjkjConstant.ENHANCE_VIEW, map, param);
		map = sqlService.executeEnhanceSqlDetail(head, MjkjConstant.ENHANCE_VIEW, map, id);//sql 获取增强
		return R.data(SqlSymbolUtil.getValueType(map));
	}

	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "功能测试 - 删除数据-ok", notes = "删除数据")
	@PostMapping({"/delete/form/{headId}/{dataId}"})
	public R deleteForm(@PathVariable("headId") Long headId, @PathVariable("dataId") String dataIds) {
		try {
			// 获取表单对象
			CgformHead onlCgformHead = cgformHeadService.getById(headId);
			if (onlCgformHead == null) {
				return R.fail("实体不存在");
			}
			String tableName = onlCgformHead.getTableName();
			//普通人不允许操作
			List<String> noSaveOrUpdateTableList = Func.toStrList(noSaveOrUpdateStr);
			if(noSaveOrUpdateTableList.contains(tableName) && !AuthUtil.isAdministrator()){//属于检测表，但是当前又不是超级管理员
				return R.fail("该表只允许超级管理员操作");
			}
			//登录成功，判断改表是否对外开放
			String noRoleStrList = onlCgformHead.getNoOperationDataRole();
			if(Func.isNotEmpty(noRoleStrList)){//有设置某一个角色不允许访问
				List<Long> roleList = Func.toLongList(noRoleStrList);

				BladeUser onlineUser = AuthUtil.getUser();
				String roleIdStrList = onlineUser.getRoleId();
				List<Long> onlineRoleList = Func.toLongList(roleIdStrList);

				List<Long> collect = roleList.stream().filter(item -> onlineRoleList.contains(item)).collect(Collectors.toList());
				if(Func.isNotEmpty(collect)){
					return R.fail("暂无操作数据权限");
				}
			}

			if(!Func.equals(onlCgformHead.getFormCategory(),"view")) {//不是显示表
				if ("Y".equals(onlCgformHead.getIsTree())) {//树结构
					dataIds = this.cgformFieldService.queryTreeChildIds(onlCgformHead, dataIds);
				}
			}

			//主副表多记录不支持增强
			if (dataIds.indexOf(",") > 0) {
				// 删除多条数据
				if (onlCgformHead.getTableType() == 2) {//主表
					if(!Func.equals(onlCgformHead.getFormCategory(),"view")) {//不是显示表
						this.cgformFieldService.deleteAutoListMainAndSub(onlCgformHead, dataIds);
					}
				} else {
					String[] ids = dataIds.split(",");
					for (String id : ids) {
						this.cgformHeadService.deleteOneTableInfo(headId, id);
					}
				}
				// 删除单条数据
			} else {
				this.cgformHeadService.deleteOneTableInfo(headId, dataIds);
			}
		} catch (BusinessException e) {
			return R.fail(e.getMessage());
		} catch (Exception e) {
			e.getStackTrace();
		}

		return R.success("成功");
	}

	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "功能测试 - 获取树形结构", notes = "获取树形结构")
	@GetMapping({"/getTreeData/{headId}"})
	public R getTreeData(@PathVariable("headId") Long headId, HttpServletRequest request) {
		CgformHead head = cgformHeadService.getById(headId);
		if (Func.isEmpty(head)) {
			return R.fail("实体不存在");
		}
		try {
			String tableName = head.getTableName();
			String treeIdField = head.getTreeIdField();
			String treeParentIdField = head.getTreeParentIdField();
			ArrayList list = Lists.newArrayList(new String[]{treeIdField, treeParentIdField});
			Map<String, Object> params = SqlSymbolUtil.getParameterMap(request);
			if (params.get("hasQuery") != null && "false".equals(params.get("hasQuery")) && params.get(treeParentIdField) == null) {
				params.put(treeParentIdField, "0");
			} else {
				params.put("pageSize", -521);
				params.put(treeParentIdField, params.get(treeParentIdField));
			}

			params.put(treeIdField, (Object) null);
			Map resultMap =new HashMap();
			if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
				resultMap = cgformFieldService.queryAutoTreeNoPage(tableName, headId, params, list, treeParentIdField);
			}else{
				resultMap.put("total", 0L);
				resultMap.put("records",new ArrayList<>());
			}

			Long total = MjkjUtils.getMap2Long(resultMap, "total");
			List<Map<String, Object>> records = (List) resultMap.get("records");

			javaService.executeEnhanceList(head, MjkjConstant.ENHANCE_QUERY, records, params);
			//分页
			if (Func.isNotEmpty(params.get("dataTotal")) && Func.isNotEmpty(params.get("dataRecords"))) {
				resultMap.put("total", params.get("dataTotal"));
				resultMap.put("records", params.get("dataRecords"));
				resultMap.put("dataOther", params.get("dataOther"));
				resultMap.put("idList", params.get("idList"));
				return R.data(resultMap);
			}
			//自定义分页
			Integer pageSzie = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
			if (Func.isNotEmpty(records) && pageSzie != -521) {//分组
				int size = records.size();
				Object pageNoObj = params.get("pageNo");
				Object pageSizeObj = params.get("pageSize");

				if (size > total.intValue() && Func.isNotEmpty(pageNoObj) && Func.isNotEmpty(pageNoObj)) {//查询出来的数据大于总数
					Integer page = Func.toInt(pageNoObj);
					Integer pageSize = Func.toInt(pageSizeObj);
					records = records.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
					resultMap.put("total", size);
					resultMap.put("records", records);
				}

			} else if (Func.isNotEmpty(records) && pageSzie == -521) {
				resultMap.put("records", records);
			} else {
				//所有id
				Object idList = params.get("idList");
				if (Func.isNotEmpty(idList)) {
					resultMap.put("idList", idList);
				}
			}
			return R.data(resultMap);
		} catch (Exception e) {
			System.out.println(e);
			return R.fail("数据库查询失败" + e.getMessage());
		}

	}





	@ApiOperationSupport(order = 21)
	@ApiOperation(value = "根据子表表名查询子表code", notes = "根据子表表名查询子表code")
	@GetMapping({"/getSubTableCode/{tableName}"})
	public R<List<CgformHead>> getSubTableCode(@PathVariable("tableName") String tableName) {
		List<CgformHead> resultList = new ArrayList<>();
		String[] tableNames = tableName.split(",");
		for (String tn : tableNames) {
			CgformHead onlCgformHead = cgformHeadService.getOne(new QueryWrapper<CgformHead>()
				.eq("table_name", tn));
			if (onlCgformHead != null) {
				resultList.add(onlCgformHead);
			}
		}
		return R.data(resultList);
	}

	@ApiOperationSupport(order = 21)
	@ApiOperation(value = "获取树形数据", notes = "获取树形数据")
	@GetMapping({"/querySelectOptions"})
	public R<List<TreeModel>> querySelectOptions(@ModelAttribute CommonEntity entity) {
		List list = cgformFieldService.queryDataListByLinkDown(entity);
		return R.data(list);
	}

	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "上传文件", notes = "上传文件")
	@PostMapping("/upload/file")
	public R putMinioObject(@RequestParam MultipartFile file, Integer type) {
		try{
			MinioBladeFile bladeFile = minioUtils.uploadInputStream(file.getOriginalFilename(), file.getInputStream());
			CacheUtil.put("system:file", "key", "FILE_" + bladeFile.getLink(), file.getOriginalFilename());
			return R.data(bladeFile);
		}catch (Exception e){
			e.printStackTrace();
		}
		return R.fail("上传失败");
	}

	@ApiOperationSupport(order = 23)
	@ApiOperation(value = "根据link获取原名称", notes = "根据link获取原名称")
	@GetMapping("/get/original/name")
	public R getOriginalFilename(String link) {
		String key = "FILE_" + link;
		Object o = CacheUtil.get("system:file", "key", key);
		if (Func.isNotEmpty(o)) {
			return R.data(Func.toStr(o));
		}
		return R.data("");
	}





	@ApiOperationSupport(order = 28)
	@ApiOperation(value = "查询子表单", notes = "查询子表单")
	@GetMapping({"/form/table_name/{tableName}/{dataId}"})
	public R formTableName(@PathVariable("tableName") String tableName, @PathVariable("dataId") String dataId, HttpServletRequest request) {
		try {
			LambdaQueryWrapper<CgformHead> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(CgformHead::getTableName, tableName);
			CgformHead onlCgformHead = cgformHeadService.getOne(wrapper);
			if (Func.isEmpty(onlCgformHead)) {
				throw new Exception(tableName + " 不存在！");
			} else {
				return this.getForm(onlCgformHead.getId(), dataId, request);
			}
		} catch (Exception e) {
			log.error("Online表单查询异常，" + e.getMessage(), e);
			return R.fail("查询失败，" + e.getMessage());
		}
	}


	@ApiOperationSupport(order = 28)
	@ApiOperation(value = "查询表名称", notes = "查询表名称")
	@GetMapping({"/queryByTableNames"})
	public R queryByTableNames(@RequestParam(name = "tableNames", required = true) String tableNames) {
		LambdaQueryWrapper<CgformHead> qw = new LambdaQueryWrapper<>();
		String[] tableNameArr = tableNames.split(",");
		qw.in(CgformHead::getTableName, Arrays.asList(tableNameArr));
		List<CgformHead> list = this.cgformHeadService.list(qw);
		if (Func.isEmpty(list)) {
			return R.fail("未找到对应实体");
		}
		return R.data(list);
	}

	@ApiOperationSupport(order = 29)
	@ApiOperation(value = "获取树结构所有节点", notes = "获取树结构所有节点")
	@GetMapping({"/treeAllData/{headId}"})
	public R getTreeAllData(@PathVariable("headId") Long headId) throws Exception {
		List<TreeDataModel> dataList = cgformHeadService.getTreeAllDataList(headId);
		return R.data(dataList);
	}

	@ApiOperationSupport(order = 30)
	@ApiOperation(value = "获取所有表名称", notes = "获取所有表名称")
	@GetMapping({"/get/allTable"})
	public R getAllTable() throws Exception {
		List<Map<String, String>> tableList = cgformHeadService.getAllTable();
		return R.data(tableList);
	}
}
