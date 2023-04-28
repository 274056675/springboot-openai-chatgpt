
package org.springblade.cgform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springblade.config.autopoi.poi.excel.ExcelImportUtil;
import org.springblade.config.autopoi.poi.excel.def.NormalExcelConstants;
import org.springblade.config.autopoi.poi.excel.entity.ExportParams;
import org.springblade.config.autopoi.poi.excel.entity.ImportParams;
import org.springblade.config.autopoi.poi.excel.view.JeecgEntityExcelView;
import org.springblade.config.util.FillRuleUtil;
import org.springblade.config.util.SqlInjectionUtil;
import org.springblade.cgform.entity.*;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.model.DuplicateCheckVo;
import org.springblade.cgform.model.TreeSelectModel;
import org.springblade.cgform.model.query.QueryGenerator;
import org.springblade.cgform.service.*;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.base.BaseEntity;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 系统参数
 */
@RestController
@AllArgsConstructor
@RequestMapping("sys")
@Api(value = "分类字典", tags = "分类字典接口")
public class SystemController extends BladeController {

	private final ICategoryService categoryService;

	private final IDictService dictService;

	private final IDictItemService dictItemService;

	private final IFillRuleService fillRuleService;


	private final ICheckRuleService checkRuleService;

	private final IMjkjBaseSqlService mjkjBaseSqlService;

	@GetMapping("/sys/category/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分类字典-详情", notes = "传入category")
	public R<Category> categoryDetail(Category category) {
		Category detail = categoryService.getOne(Condition.getQueryWrapper(category));
		return R.data(detail);
	}


	@GetMapping("/sys/category/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分类字典-分页", notes = "传入category")
	public R<IPage<Category>> categoryList(Category category, Query query) {
		IPage<Category> pages = categoryService.page(Condition.getPage(query), Condition.getQueryWrapper(category));
		return R.data(pages);
	}


	@PostMapping("/sys/category/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "分类字典-新增", notes = "传入category")
	public R categorySave(@Valid @RequestBody Category category) {
		LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Category::getCode,category.getCode());
		List<Category> list = categoryService.list(wrapper);
		if(Func.isNotEmpty(list)){
			return R.fail("编码已存在");
		}
		categoryService.addSysCategory(category);
		return R.success("成功");
	}

	@PostMapping("/sys/category/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "分类字典-修改", notes = "传入category")
	public R categoryUpdate(@Valid @RequestBody Category category) {
		LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Category::getCode,category.getCode());
		List<Category> list = categoryService.list(wrapper);
		if(Func.isNotEmpty(list) && list.size()>1){
			return R.fail("编码已存在");
		}else if(Func.isNotEmpty(list)){
			Category select = list.get(0);
			if(select.getId()!=select.getId().longValue()){
				return R.fail("编码已存在");
			}
		}
		categoryService.updateSysCategory(category);
		return R.success("成功");
	}

	@PostMapping("/sys/category/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "分类字典-逻辑删除", notes = "传入ids")
	public R categoryRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<Long> idList = Func.toLongList(ids);
		categoryService.batchDelete(idList);
		return R.success("成功");
	}

	@GetMapping(value = "/sys/category/childList")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "分类字典-获取子集", notes = "传入ids")
	public R<List<Category>> queryPageList(Category category,HttpServletRequest req) {
		QueryWrapper<Category> wrapper = Condition.getQueryWrapper(category);
		List<Category> list = categoryService.list(wrapper);
		return R.data(list);
	}


	@GetMapping("/sys/dict/detail")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "字典-详情", notes = "传入dict")
	public R<SysDict> dictDetail(SysDict sysDict) {
		SysDict detail = dictService.getOne(Condition.getQueryWrapper(sysDict));
		return R.data(detail);
	}

	@GetMapping("/sys/dict/list")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "字典-分页", notes = "传入dict")
	public R<IPage<SysDict>> dictList(SysDict sysDict, Query query) {
		LambdaQueryWrapper<SysDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (Func.isNotBlank(sysDict.getDictName())){
			lambdaQueryWrapper.like(SysDict::getDictName, sysDict.getDictName());
		}
		if (Func.isNotBlank(sysDict.getDictCode())){
			lambdaQueryWrapper.like(SysDict::getDictCode, sysDict.getDictCode());
		}
		lambdaQueryWrapper.orderByDesc(BaseEntity::getId);
		IPage<SysDict> pages = dictService.page(Condition.getPage(query),lambdaQueryWrapper );
		if(Func.isNotEmpty(pages)){
			List<SysDict> dictList = pages.getRecords();
			if(Func.isNotEmpty(dictList)){
				for (SysDict dict:dictList) {
					String remark="";
					LambdaQueryWrapper<SysDictItem> itemQueryWrapper = new LambdaQueryWrapper<>();
					itemQueryWrapper.eq(SysDictItem::getDictId,dict.getId());
					List<SysDictItem> itemList = dictItemService.list(itemQueryWrapper);
					if(Func.isEmpty(itemList)){
						continue;
					}
					for (SysDictItem item:itemList) {
						remark+="【"+item.getItemText()+"->"+item.getItemValue()+"】";
					}
					dict.setDescription(remark);
				}
			}

		}

		return R.data(pages);
	}


	@PostMapping("/sys/dict/save")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "字典-新增", notes = "传入dict")
	public R dictSave(@Valid @RequestBody SysDict sysDict) {
		LambdaQueryWrapper<SysDict> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(SysDict::getDictCode, sysDict.getDictCode());
		List<SysDict> list = dictService.list(wrapper);
		if(Func.isNotEmpty(list)){
			return R.fail("编码已存在");
		}

		return R.status(dictService.save(sysDict));
	}

	@PostMapping("/sys/dict/update")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "字典-修改", notes = "传入dict")
	public R dicUupdate(@Valid @RequestBody SysDict sysDict) {
		LambdaQueryWrapper<SysDict> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(SysDict::getDictCode, sysDict.getDictCode());
		List<SysDict> list = dictService.list(wrapper);
		if(Func.isNotEmpty(list) && list.size()>1){
			return R.fail("编码已存在");
		}else if(Func.isNotEmpty(list)){
			SysDict select = list.get(0);
			if(select.getId()!= sysDict.getId().longValue()){
				return R.fail("编码已存在");
			}

		}


		return R.status(dictService.updateById(sysDict));
	}


	@PostMapping("/sys/dict/remove")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "字典-逻辑删除", notes = "传入ids")
	public R dictRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(dictService.deleteLogic(Func.toLongList(ids)));
	}

	@GetMapping("/sys/dictitem/detail")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "字典内容-详情", notes = "传入dictItem")
	public R<SysDictItem> dictItemDetail(SysDictItem sysDictItem) {
		SysDictItem detail = dictItemService.getOne(Condition.getQueryWrapper(sysDictItem));
		return R.data(detail);
	}

	@GetMapping("/sys/dictitem/list")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "字典内容-分页", notes = "传入dictItem")
	public R<IPage<SysDictItem>> dictItemList(SysDictItem sysDictItem, Query query) {
		IPage<SysDictItem> pages = dictItemService.page(Condition.getPage(query), Condition.getQueryWrapper(sysDictItem));
		return R.data(pages);
	}


	@PostMapping("/sys/dictitem/save")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "字典内容-新增", notes = "传入dictItem")
	public R dictItemSave(@Valid @RequestBody SysDictItem sysDictItem) {
		return R.status(dictItemService.save(sysDictItem));
	}


	@PostMapping("/sys/dictitem/update")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "字典内容-修改", notes = "传入dictItem")
	public R dictItemUpdate(@Valid @RequestBody SysDictItem sysDictItem) {
		return R.status(dictItemService.updateById(sysDictItem));
	}


	@PostMapping("/sys/dictitem/remove")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "字典内容-逻辑删除", notes = "传入ids")
	public R dictItemRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(dictItemService.deleteLogic(Func.toLongList(ids)));
	}

	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "获取树数据类型", notes = "获取树数据类型")
	@RequestMapping(value = "/loadTreeData", method = RequestMethod.GET)
	public R<List<TreeSelectModel>> loadTreeData(@RequestParam(name="pid") String pid, @RequestParam(name="pidField") String pidField,
                                                 @RequestParam(name="tableName") String tbname,
                                                 @RequestParam(name="text") String text,
                                                 @RequestParam(name="code") String code,
                                                 @RequestParam(name="hasChildField") String hasChildField,
                                                 @RequestParam(name="condition") String condition,
                                                 @RequestParam(value = "sign",required = false) String sign, HttpServletRequest request) {
		Map<String, String> query = null;
		if(Func.isNotEmpty(condition)) {
			query = JSON.parseObject(condition, Map.class);
		}
		// SQL注入漏洞 sign签名校验(表名,label字段,val字段,条件)
		//String dictCode = tbname+","+text+","+code+","+condition;
		List<TreeSelectModel> list = dictService.queryTreeList(query,tbname, text, code, pidField, pid,hasChildField);
		return R.data(list);
	}


	@ApiOperationSupport(order = 19)
	@ApiOperation(value = "根据字典code加载字典text 返回", notes = "根据字典code加载字典text 返回")
	@RequestMapping(value = "/sys/dictitem/loadDictItem/{dictCode}", method = RequestMethod.GET)
	public R loadDictItem(@PathVariable String dictCode,@RequestParam(name="key") String keys,HttpServletRequest request) {
		if(dictCode.indexOf(",")==-1) {
			return R.fail("字典Code格式不正确!");
		}
		String[] params = dictCode.split(",");
		if(params.length!=3) {
			return R.fail("字典Code格式不正确！");
		}
		List<String> list = dictService.queryTableDictByKeys(params[0], params[1], params[2], keys);
		return R.data(list);
	}

	/**
	 * 获取字典数据
	 * @param dictCode 字典code
	 * @param dictCode 表名,文本字段,code字段  | 举例：sys_user,realname,id
	 * @return
	 */
	@ApiOperationSupport(order = 20)
	@ApiOperation(value = "获取字典数据", notes = "获取字典数据")
	@RequestMapping(value = "/sys/dict/getDictItems/{dictCode}", method = RequestMethod.GET)
	public R<List<DictModel>> getDictItems(@PathVariable String dictCode, HttpServletRequest request) {

		List<DictModel> resultList = null;
		try {
			if(dictCode.indexOf(",")!=-1) {
				//关联表字典（举例：sys_user,realname,id）
				String[] params = dictCode.split(",");

				if(params.length<3) {
					return R.fail("字典Code格式不正确!");
				}
				//SQL注入校验（只限制非法串改数据库）
				final String[] sqlInjCheck = {params[0],params[1],params[2]};
				SqlInjectionUtil.filterContent(sqlInjCheck);

				if(params.length==4) {
					//SQL注入校验（查询条件SQL 特殊check，此方法仅供此处使用）
					SqlInjectionUtil.specialFilterContent(params[3]);
					resultList = dictService.queryTableDictItemsByCodeAndFilter(params[0],params[1],params[2],params[3]);
				}else if (params.length==3) {
					resultList = dictService.queryTableDictItemsByCode(params[0],params[1],params[2]);
				}else{
					return R.fail("字典Code格式不正确!");
				}
			}else {
				//字典表
				resultList = dictService.queryDictItemsByCode(dictCode);
			}
		} catch (Exception e) {
			return R.fail("操作失败");
		}

		return R.data(resultList);
	}

	/**
	 * 大数据量的字典表 走异步加载  即前端输入内容过滤数据
	 * @param dictCode
	 * @return
	 */
	@ApiOperationSupport(order = 21)
	@ApiOperation(value = "获取字典数据", notes = "获取字典数据")
	@RequestMapping(value = "/sys/dict/getDict/{dictCode}", method = RequestMethod.GET)
	public R<List<DictModel>> loadDict(@PathVariable String dictCode,@RequestParam(name="keyword") String keyword) {

		if(dictCode.indexOf(",")!=-1) {
			String[] params = dictCode.split(",");
			if(params.length!=3) {
				return R.fail("字典Code格式不正确！");
			}
			List<DictModel> list = dictService.queryTableDictItems(params[0], params[1], params[2], keyword);
			return R.data(list);
		}else {
			return R.fail("字典Code格式不正确！");
		}
	}



	@GetMapping("/sys/fill/list")
	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "填值规则-列表", notes = "填值规则-列表")
	public R<IPage<FillRule>> fillList(FillRule fillRule, Query query) {
		IPage<FillRule> pages = fillRuleService.page(Condition.getPage(query), Condition.getQueryWrapper(fillRule));
		return R.data(pages);
	}

	@PostMapping("/sys/fill/save")
	@ApiOperationSupport(order = 23)
	@ApiOperation(value = "填值规则-新增", notes = "填值规则-新增")
	public R fillSave(@Valid @RequestBody FillRule fillRule) {
		return R.status(fillRuleService.save(fillRule));
	}

	@PostMapping("/sys/fill/update")
	@ApiOperationSupport(order = 24)
	@ApiOperation(value = "填值规则-修改", notes = "填值规则-修改")
	public R fillUpdate(@Valid @RequestBody FillRule fillRule) {
		return R.status(fillRuleService.updateById(fillRule));
	}

	@PostMapping("/sys/fill/remove")
	@ApiOperationSupport(order = 25)
	@ApiOperation(value = "填值规则-逻辑删除", notes = "填值规则-逻辑删除")
	public R fillRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(fillRuleService.deleteLogic(Func.toLongList(ids)));
	}


	@GetMapping("/sys/fill/detail")
	@ApiOperationSupport(order = 26)
	@ApiOperation(value = "填值规则-详情", notes = "填值规则-详情")
	public R<FillRule> fillDetail(FillRule fillRule) {
		FillRule detail = fillRuleService.getOne(Condition.getQueryWrapper(fillRule));
		return R.data(detail);
	}

	@PostMapping(value = "/sys/fill/importExcel")
	@ApiOperationSupport(order = 27)
	@ApiOperation(value = "填值规则-导入", notes = "填值规则-导入")
	public R fillImportExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<FillRule> list = ExcelImportUtil.importExcel(file.getInputStream(), FillRule.class, params);

				long start = System.currentTimeMillis();
				fillRuleService.saveBatch(list);
				return R.success("文件导入成功！数据行数：" + list.size());
			} catch (Exception e) {
				return R.fail("文件导入失败:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return R.fail("文件导入失败！");

	}

	@ApiOperationSupport(order = 28)
	@ApiOperation(value = "填值规则-导出", notes = "填值规则-导出")
	@GetMapping(value = "/sys/fill/exportXls")
	public ModelAndView fillExportXls(HttpServletRequest request, FillRule fillRule) {
		// Step.1 组装查询条件
		QueryWrapper<FillRule> queryWrapper = QueryGenerator.initQueryWrapper(fillRule, request.getParameterMap());

		// Step.2 获取导出数据
		List<FillRule> pageList = fillRuleService.list(queryWrapper);
		List<FillRule> exportList = null;

		// 过滤选中数据
		String selections = request.getParameter("selections");
		if (Func.isNotEmpty(selections)) {
			List<String> selectionList = Arrays.asList(selections.split(","));
			exportList = pageList.stream().filter(item -> selectionList.contains(getFillRuleId(item))).collect(Collectors.toList());
		} else {
			exportList = pageList;
		}

		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "填值规则"); //此处设置的filename无效 ,前端会重更新设置一下
		mv.addObject(NormalExcelConstants.CLASS, FillRule.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams(  "填值规则报表", "导出人:" + AuthUtil.getNickName(), "填值规则"));
		mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		return mv;
	}



	@ApiOperationSupport(order = 29)
	@ApiOperation(value = "填值规则-执行自定义填值规则", notes = "填值规则-执行自定义填值规则")
	@PostMapping("/sys/fill/executeRuleByCode/{ruleCode}")
	public R executeByRuleCode(@PathVariable("ruleCode") String ruleCode, @RequestBody JSONObject formData) {
		Object result = FillRuleUtil.executeRule(ruleCode, formData);
		return R.data(result);
	}

	@ApiOperationSupport(order = 30)
	@ApiOperation(value = "填值规则-批量通过 ruleCode 执行自定义填值规则", notes = "填值规则-批量通过 ruleCode 执行自定义填值规则")
	@PutMapping("/executeRuleByCodeBatch")
	public R executeByRuleCodeBatch(@RequestBody JSONObject ruleData) {
		JSONObject commonFormData = ruleData.getJSONObject("commonFormData");
		JSONArray rules = ruleData.getJSONArray("rules");
		// 遍历 rules ，批量执行规则
		JSONArray results = new JSONArray(rules.size());
		for (int i = 0; i < rules.size(); i++) {
			JSONObject rule = rules.getJSONObject(i);
			String ruleCode = rule.getString("ruleCode");
			JSONObject formData = rule.getJSONObject("formData");
			// 如果没有传递 formData，就用common的
			if (formData == null) {
				formData = commonFormData;
			}
			// 执行填值规则
			Object result = FillRuleUtil.executeRule(ruleCode, formData);
			JSONObject obj = new JSONObject(rules.size());
			obj.put("ruleCode", ruleCode);
			obj.put("result", result);
			results.add(obj);
		}
		return R.data(results);
	}


	@GetMapping("/sys/check/list")
	@ApiOperationSupport(order = 31)
	@ApiOperation(value = "编码校验规则 - 列表", notes = "传入checkRule")
	public R<IPage<CheckRule>> checkRuleList(CheckRule checkRule, Query query) {
		IPage<CheckRule> pages = checkRuleService.page(Condition.getPage(query), Condition.getQueryWrapper(checkRule));
		return R.data(pages);
	}


	@GetMapping("/sys/check/detail")
	@ApiOperationSupport(order = 32)
	@ApiOperation(value = "编码校验规则 - 详情", notes = "编码校验规则 - 详情")
	public R<CheckRule> checkRuleDetail(CheckRule checkRule) {
		CheckRule detail = checkRuleService.getOne(Condition.getQueryWrapper(checkRule));
		return R.data(detail);
	}

	@PostMapping("/sys/check/save")
	@ApiOperationSupport(order = 33)
	@ApiOperation(value = "编码校验规则 - 新增", notes = "编码校验规则 - 新增")
	public R checkRuleSave(@Valid @RequestBody CheckRule checkRule) {
		return R.status(checkRuleService.save(checkRule));
	}

	@PostMapping("/sys/check/update")
	@ApiOperationSupport(order = 34)
	@ApiOperation(value = "编码校验规则 - 修改", notes = "编码校验规则 - 修改")
	public R checkRuleUpdate(@Valid @RequestBody CheckRule checkRule) {
		return R.status(checkRuleService.updateById(checkRule));
	}

	@PostMapping("/sys/check/remove")
	@ApiOperationSupport(order = 35)
	@ApiOperation(value = "编码校验规则 - 逻辑删除", notes = "编码校验规则 - 逻辑删除")
	public R checkRuleRemove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(checkRuleService.deleteLogic(Func.toLongList(ids)));
	}


	@ApiOperationSupport(order = 36)
	@ApiOperation(value = "编码校验规则-通过Code校验传入的值", notes = "编码校验规则-通过Code校验传入的值")
	@GetMapping(value = "/sys/check/checkByCode")
	public R<Object> checkByCode(
			@RequestParam(name = "ruleCode") String ruleCode,
			@RequestParam(name = "value") String value
	) throws UnsupportedEncodingException {
		LambdaQueryWrapper<CheckRule> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CheckRule::getRuleCode, ruleCode);
		CheckRule sysCheckRule = checkRuleService.getOne(queryWrapper);

		if (sysCheckRule == null) {
			return R.fail("该编码不存在");
		}
		JSONObject errorResult = checkRuleService.checkValue(sysCheckRule, URLDecoder.decode(value, "UTF-8"));
		if (errorResult == null) {
			return R.success("成功");
		} else {
			return R.fail(errorResult.getString("message"));
		}
	}

	@PostMapping(value = "/sys/check/importExcel")
	@ApiOperationSupport(order = 37)
	@ApiOperation(value = "填值规则-导入", notes = "填值规则-导入")
	public R checkImportExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<CheckRule> list = ExcelImportUtil.importExcel(file.getInputStream(), CheckRule.class, params);
				checkRuleService.saveBatch(list);
				return R.success("文件导入成功！数据行数：" + list.size());
			} catch (Exception e) {
				return R.fail("文件导入失败:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return R.fail("文件导入失败！");

	}

	@GetMapping(value = "/sys/getAllTenant")
	@ApiOperationSupport(order = 37)
	@ApiOperation(value = "获取所有租户信息", notes = "获取所有租户")
	public R getAllTenant() {
		List<Map<String, Object>> list = dictService.getAllTenantList();
		return R.data(list);
	}

	@ApiOperationSupport(order = 38)
	@ApiOperation(value = "填值规则-导出", notes = "填值规则-导出")
	@GetMapping(value = "/sys/check/exportXls")
	public ModelAndView checkExportXls(HttpServletRequest request, CheckRule fillRule) {
		// Step.1 组装查询条件
		QueryWrapper<CheckRule> queryWrapper = QueryGenerator.initQueryWrapper(fillRule, request.getParameterMap());

		// Step.2 获取导出数据
		List<CheckRule> pageList = checkRuleService.list(queryWrapper);
		List<CheckRule> exportList = null;

		// 过滤选中数据
		String selections = request.getParameter("selections");
		if (Func.isNotEmpty(selections)) {
			List<String> selectionList = Arrays.asList(selections.split(","));
			exportList = pageList.stream().filter(item -> selectionList.contains(getCheckRuleId(item))).collect(Collectors.toList());
		} else {
			exportList = pageList;
		}

		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "填值规则"); //此处设置的filename无效 ,前端会重更新设置一下
		mv.addObject(NormalExcelConstants.CLASS, CheckRule.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams(  "填值规则报表", "导出人:" + AuthUtil.getNickName(), "填值规则"));
		mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		return mv;
	}


	@ApiOperationSupport(order = 38)
	@RequestMapping(value = "/duplicate/check", method = RequestMethod.GET)
	@ApiOperation("校验数据是否在系统中是否存在")
	public R doDuplicateCheck(DuplicateCheckVo duplicateCheckVo, HttpServletRequest request) {
		Long num = null;
		if (StringUtils.isNotBlank(duplicateCheckVo.getDataId())) {
			// [2].编辑页面校验
			num = dictService.duplicateCheckCountSql(duplicateCheckVo);
		} else {
			// [1].添加页面校验
			num = dictService.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
		}

		if (num == null || num == 0) {
			// 该值可用
			return R.success("该值可用！");
		} else {
			return R.fail("该值不可用，系统中已存在！");
		}
	}

	/**
	 * 获取对象ID
	 *
	 * @return
	 */
	private String getFillRuleId(FillRule item) {
		try {
			return PropertyUtils.getProperty(item, "id").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取对象ID
	 *
	 * @return
	 */
	private String getCheckRuleId(CheckRule item) {
		try {
			return PropertyUtils.getProperty(item, "id").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
