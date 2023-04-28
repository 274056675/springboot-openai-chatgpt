package org.springblade.config.autopoi.poi.cache;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.config.autopoi.poi.cache.manager.POICacheManager;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Excel类型的缓存
 */
public final class ExcelCache {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelCache.class);

	public static Workbook getWorkbook(String url, Integer[] sheetNums, boolean needAll) {
		InputStream is = null;
		List<Integer> sheetList = Arrays.asList(sheetNums);
		try {
			is = POICacheManager.getFile(url);
			Workbook wb = WorkbookFactory.create(is);
			// 删除其他的sheet
			if (!needAll) {
				for (int i = wb.getNumberOfSheets() - 1; i >= 0; i--) {
					if (!sheetList.contains(i)) {
						wb.removeSheetAt(i);
					}
				}
			}
			return wb;
		} catch (InvalidFormatException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return null;
	}
	//update-begin-author:wangshuai date:20200730 for:jar 包上传到服务器后 autopoi 读取不到excel模版文件 #1505
	public static Workbook getWorkbookByTemplate(String url, Integer[] sheetNums, boolean needAll) {
		List<Integer> sheetList = Arrays.asList(sheetNums);
		InputStream fis = null;
		try {
			ClassPathResource resource = new ClassPathResource(url);
			fis = resource.getInputStream();
			Workbook wb = WorkbookFactory.create(fis);
			// 删除其他的sheet
			if (!needAll) {
				for (int i = wb.getNumberOfSheets() - 1; i >= 0; i--) {
					if (!sheetList.contains(i)) {
						wb.removeSheetAt(i);
					}
				}
			}
			return wb;
		} catch (InvalidFormatException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return null;
	}
    //update-end-author:wangshuai date:20200730 for:jar 包上传到服务器后 autopoi 读取不到excel模版文件 #1505
}
