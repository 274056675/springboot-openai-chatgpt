
package org.springblade.cgform.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.service.*;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.autopoi.poi.excel.ExcelExportUtil;
import org.springblade.config.autopoi.poi.excel.ExcelImportUtil;
import org.springblade.config.autopoi.poi.excel.entity.ExportParams;
import org.springblade.config.autopoi.poi.excel.entity.ImportParams;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelExportEntity;
import org.springblade.config.constant.MjkjConstant;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.util.*;
import org.springblade.config.util.converter.ConverterUtil;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("excel-api")
@Api(value = "excel相关接口", tags = "excel相关接口")
public class ExcelController extends BaseController {

	private final ICgformHeadService cgformHeadService;

	private final ICgformFieldService cgformFieldService;

	private final ICgformEnhanceJavaService javaService;

	private final ICgformEnhanceSqlService sqlService;


	@Autowired
	private BladeRedis bladeRedis;



	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "功能测试 - 导出", notes = "导出")
	@GetMapping({"/exportXls/{headId}"})
	public void exportXls(@PathVariable("headId") Long headId, HttpServletRequest request, HttpServletResponse response) {
		CgformHead onlCgformHead = cgformHeadService.getById(headId);
		if (Func.isEmpty(onlCgformHead)) {
			return;
		}

		LambdaQueryWrapper<CgformField> fieldWrapper = new LambdaQueryWrapper<>();
		fieldWrapper.eq(CgformField::getCgformHeadId, headId);
		fieldWrapper.orderByAsc(CgformField::getOrderNum);
		List<CgformField> allFieldList = cgformFieldService.list(fieldWrapper);//获取所有字段

		List<CgformField> templateList = new ArrayList<>();
		Iterator<CgformField> iterator = allFieldList.iterator();
		while (iterator.hasNext()) {
			CgformField cgformField = iterator.next();
			if (cgformField.getImportFlag() == 0) {
				continue;
			}
			templateList.add(cgformField);
		}

		Map<String, Object> param = SqlSymbolUtil.getParameterMap(request);

		String tableTxt = onlCgformHead.getTableTxt();
		Map<String, Object> hashMap = super.paramStr2Map(request);
		hashMap.put("pageSize", -521);
		List<Map<String, Object>> recordsList=null;
		if(!Func.equals(onlCgformHead.getFormCategory(),"view")) {//不是显示表
			Map<String, Object> autolistPage = cgformFieldService.queryAutolistPage(onlCgformHead.getTableName(), onlCgformHead.getId(), hashMap, null);
			recordsList = (List<Map<String, Object>>) autolistPage.get("records");//数据
		}
		if(Func.isEmpty(recordsList)){
			recordsList=new ArrayList<>();
		}

		try {
			String paramsStr = MjkjUtils.getMap2Str(param,"paramsStr");
			if(Func.isNotEmpty(paramsStr)){
				Map map = JSONObject.parseObject(paramsStr, Map.class);
				param.putAll(map);
			}
			//导出增强
			javaService.executeEnhanceList(onlCgformHead,  MjkjConstant.ENHANCE_EXPORT, recordsList, param);
			//sql增强
			sqlService.executeEnhanceSqlList(onlCgformHead, MjkjConstant.ENHANCE_EXPORT,param);
			//sql增强
			sqlService.executeEnhanceSqlList(onlCgformHead,MjkjConstant.ENHANCE_QUERYANEXPORT, param);

			if (Func.isNotEmpty(param.get("dataTotal")) && Func.isNotEmpty(param.get("dataRecords"))) {
				recordsList = (List<Map<String, Object>>) param.get("dataRecords");
			}
		} catch (BusinessException e) {
			e.getStackTrace();
			log.error("导出java增强处理出错", e.getMessage());
		}


		List<Map<String, Object>> idList = new ArrayList<>();
		String selectionsStr = hashMap.get("selections") == null ? null : hashMap.get("selections").toString();
		if (ConvertUtils.isNotEmpty(selectionsStr)) {
			List finalVar1 = Arrays.asList(selectionsStr.split(","));
			idList = recordsList.stream().filter((map) -> {
				String idStr = map.get("id").toString();
				return finalVar1.contains(idStr);
			}).collect(Collectors.toList());
		} else {
			if (recordsList == null) {
				recordsList = new ArrayList();
			}
			idList.addAll(recordsList);
		}


		ConverterUtil.converter(1, idList, templateList);

		List<ExcelExportEntity> list = super.excelField(templateList, "id");
		//当前是主表
		if (onlCgformHead.getTableType() == 2 && ConvertUtils.isEmpty(hashMap.get("exportSingleOnly"))) {
			String subTableStr = onlCgformHead.getSubTableStr();
			if (ConvertUtils.isNotEmpty(subTableStr)) {
				String[] subTableStrs = subTableStr.split(",");
				for (String str : subTableStrs) {
					super.getSubData(str, hashMap, idList, list);//获取附表数据
				}
			}
		}

		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams((String) null, tableTxt), list, (Collection) idList);
		super.outpuExcel(onlCgformHead, workbook, request, response);//输出到浏览器
	}



}
