package org.springblade.config.autopoi.poi.excel.view;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springblade.config.autopoi.poi.excel.ExcelExportUtil;
import org.springblade.config.autopoi.poi.excel.def.NormalExcelConstants;
import org.springblade.config.autopoi.poi.excel.def.TemplateExcelConstants;
import org.springblade.config.autopoi.poi.excel.entity.TemplateExportParams;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Excel 模板导出
 *
 */
@SuppressWarnings("unchecked")
@Controller(TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW)
public class JeecgTemplateExcelView extends MiniAbstractExcelView {

	public JeecgTemplateExcelView() {
		super();
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String codedFileName = "临时文件";
		Workbook workbook = ExcelExportUtil.exportExcel((TemplateExportParams) model.get(TemplateExcelConstants.PARAMS), (Class<?>) model.get(TemplateExcelConstants.CLASS), (List<?>) model.get(TemplateExcelConstants.LIST_DATA), (Map<String, Object>) model.get(TemplateExcelConstants.MAP_DATA));
		if (model.containsKey(NormalExcelConstants.FILE_NAME)) {
			codedFileName = (String) model.get(NormalExcelConstants.FILE_NAME);
		}
		if (workbook instanceof HSSFWorkbook) {
			codedFileName += HSSF;
		} else {
			codedFileName += XSSF;
		}
		if (isIE(request)) {
			codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF8");
		} else {
			codedFileName = new String(codedFileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
}
