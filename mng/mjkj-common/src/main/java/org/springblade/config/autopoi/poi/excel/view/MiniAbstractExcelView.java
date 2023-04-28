package org.springblade.config.autopoi.poi.excel.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础抽象Excel View
 *
 */
public abstract class MiniAbstractExcelView extends AbstractView {

	private static final String CONTENT_TYPE = "application/vnd.ms-excel";

	protected static final String HSSF = ".xls";
	protected static final String XSSF = ".xlsx";

	public MiniAbstractExcelView() {
		setContentType(CONTENT_TYPE);
	}

	protected boolean isIE(HttpServletRequest request) {
		return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0 || request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0) ? true : false;
	}

}
