/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.modules.mjkj.common.cgform.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.mjkj.common.cgform.entity.*;
import org.springblade.modules.mjkj.common.cgform.enums.CgformConstant;
import org.springblade.modules.mjkj.common.cgform.model.CommonEntity;
import org.springblade.modules.mjkj.common.cgform.model.OnlineConfigModel;
import org.springblade.modules.mjkj.common.cgform.model.TreeDataModel;
import org.springblade.modules.mjkj.common.cgform.model.query.QueryGenerator;
import org.springblade.modules.mjkj.common.cgform.service.*;
import org.springblade.modules.mjkj.common.config.constant.MjkjConstant;
import org.springblade.modules.mjkj.common.config.exception.BusinessException;
import org.springblade.modules.mjkj.common.config.util.ConvertUtils;
import org.springblade.modules.mjkj.common.config.util.EnhanceJsUtil;
import org.springblade.modules.mjkj.common.config.util.SqlSymbolUtil;
import org.springblade.modules.mjkj.common.utils.MjkjUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.TreeModel;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author BladeX
 * @since 2021-05-20
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("mjkj-chat/cgform-api")
@Api(value = "表单开发接口", tags = "表单开发接口")
public class CgformApiController extends BaseController {

	private final ICgformHeadService cgformHeadService;

	private final ICgformFieldService cgformFieldService;

	private final ICgformIndexService cgformIndexService;

	private final ICgformEnhanceJsService jsService;

	private final ICgformEnhanceJavaService javaService;

	private final ICgformEnhanceSqlService sqlService;

	private final IOnlineService onlineService;

	private final IMjkjBaseSqlService mjkjBaseSqlService;


	//比较重要的表，不允许新增修改删除，只有超级管理员才有权限
	private final String noSaveOrUpdateStr="coin_system_wallet";


	/**
	 * @param cgformHead
	 * @param query
	 * @param req
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取列表", notes = "传入cgformField")
	public R<IPage<CgformHead>> list(CgformHead cgformHead, Query query, HttpServletRequest req) {

		QueryWrapper<CgformHead> wrapper = QueryGenerator.initQueryWrapper(cgformHead,
			req.getParameterMap());
		wrapper.orderByDesc("id");
		IPage<CgformHead> pages = cgformHeadService.page(Condition.getPage(query), wrapper);

		List<CgformHead> records = pages.getRecords();
		if (cgformHead.getCopyType() != null && cgformHead.getCopyType() == 0) {
			cgformHeadService.initCopyState(records);
		}
		Map<String, Map<String, Object>> allRoleMap = mjkjBaseSqlService.getData2Map("blade_role", "id", false);

		if (Func.isNotEmpty(records)) {
			//获取所有表
			List<CgformHead> allHeadList = cgformHeadService.list();

			for (CgformHead head : records) {
				String remark = head.getRemark();
				String nologinSelect = head.getNologinSelect();
				String str=Func.equals(nologinSelect,"0")?"否":"是";
				String nologinSelectStr="不登录是否可以查询:["+str+"]";
				remark+=nologinSelectStr+"/n";
				head.setRemark(remark);

				//处理为不能操作 和不能查看的角色
				String noViewDataRoleListStr = head.getNoViewDataRole();//不允许查看数据的角色

				if(Func.isNotEmpty(noViewDataRoleListStr)){
					List<String> noViewDataRoleList = Func.toStrList(noViewDataRoleListStr);
					String noViewDataRoleStr="不允许查看数据的角色:";
					for (String noViewDataRole:noViewDataRoleList) {
						Map<String, Object> roleMap = allRoleMap.get(noViewDataRole);
						if(Func.isEmpty(roleMap)){
							noViewDataRoleStr+="["+noViewDataRole+"]";
						}else{
							noViewDataRoleStr+="["+ MjkjUtils.getMap2Str(roleMap,"role_name")+"]";
						}
					}
					remark+=noViewDataRoleStr+"/n";
					head.setRemark(remark);
				}

				String noOperationDataRoleListStr = head.getNoOperationDataRole();//不允许操作
				if(Func.isNotEmpty(noOperationDataRoleListStr)){
					List<String> noOperationDataRoleList = Func.toStrList(noOperationDataRoleListStr);
					String noViewDataRoleStr="不允许操作数据的角色:";
					for (String noViewDataRole:noOperationDataRoleList) {
						Map<String, Object> roleMap = allRoleMap.get(noViewDataRole);
						if(Func.isEmpty(roleMap)){
							noViewDataRoleStr+="["+noViewDataRole+"]";
						}else{
							noViewDataRoleStr+="["+MjkjUtils.getMap2Str(roleMap,"role_name")+"]";
						}
					}
					remark+=noViewDataRoleStr+"/n";
					head.setRemark(remark);
				}



				//获取所有sql增强
				LambdaQueryWrapper<CgformEnhanceSql> sqlWrapper = new LambdaQueryWrapper<>();
				sqlWrapper.eq(CgformEnhanceSql::getCgformHeadId, head.getPhysicId());
				List<CgformEnhanceSql> sqlList = sqlService.list(sqlWrapper);
				if (Func.isEmpty(sqlList)) {
					continue;
				}
				Set<String> tableNameList = new HashSet<>();
				for (CgformEnhanceSql sql : sqlList) {
					String content = sql.getCgbSql();
					if (Func.isEmpty(content)) {
						continue;
					}

					content = content.replaceAll("\r", " ");
					content = content.replaceAll("\n", " ");
					String[] contens = content.split(" ");
					for (String conten : contens) {
						if(Func.isEmpty(conten)){
							continue;
						}
						if(!conten.startsWith("zhdt_")){
							continue;
						}
						for (CgformHead myHead : allHeadList) {
							if (Func.equals(conten.trim(), myHead.getTableName())) {
								tableNameList.add(myHead.getTableName());
							}
						}
					}
				}

				if (Func.isNotEmpty(tableNameList)) {
					remark += "；sql增强涉及表：" + JsonUtil.toJson(tableNameList);
					head.setRemark(remark);
				}


			}
		}

		return R.data(pages);
	}





	@GetMapping("/detail/listByHeadId")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取详情", notes = "获取详情")
	public R<Map> fieldListByHeadId(@RequestParam("headId") Long headId) {
		CgformHead head = cgformHeadService.getById(headId);

		LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformField::getCgformHeadId, headId);
		wrapper.orderByAsc(CgformField::getOrderNum);
		List<CgformField> fieldList = cgformFieldService.list(wrapper);

		LambdaQueryWrapper<CgformIndex> indexWrapper = new LambdaQueryWrapper<>();
		indexWrapper.eq(CgformIndex::getCgformHeadId, headId);
		indexWrapper.orderByAsc(CgformIndex::getCreateTime);
		List<CgformIndex> indexList = cgformIndexService.list(indexWrapper);


		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("fieldList", fieldList);
		resultMap.put("head", head);
		resultMap.put("indexList", indexList);

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
					List<CgformField> availableFields = this.cgformFieldService.queryAvailableFields(subHead.getPhysicId(), subHead.getTableName(), (String) null, false);
					EnhanceJsUtil.getJsFunction(onlCgformEnhanceJs, subHead.getTableName(), availableFields);
					JSONObject jsonObject2 = new JSONObject();
					List<String> disabledFields = new ArrayList<>();
					if (1 == subHead.getRelationType()) {//一对一关系
						jsonObject2 = SqlSymbolUtil.getFiledJson(availableFields, disabledFields, null);
					} else {
						jsonObject2.put("columns", SqlSymbolUtil.getColumns(availableFields, disabledFields));
					}

					jsonObject2.put("id", subHead.getPhysicId());
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

	@ApiOperationSupport(order = 11)
	@GetMapping({"/getQueryInfo/{headId}"})
	@ApiOperation(value = "功能测试 - 获取查询条件", notes = "获取查询条件")
	public R getQueryInfo(@PathVariable("headId") Long headId) {
		List<Map<String, String>> list = cgformFieldService.getAutoListQueryInfo(headId);
		return R.data(list);
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




	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "上传文件", notes = "上传文件")
	@PostMapping("/upload/file")
	public R putMinioObject(@RequestParam MultipartFile file, String type) {

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









}
