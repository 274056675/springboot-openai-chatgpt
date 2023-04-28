package org.springblade.config.autopoi.poi.excel.imports;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.config.autopoi.poi.excel.annotation.ExcelTarget;
import org.springblade.config.autopoi.poi.excel.entity.ImportParams;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelCollectionParams;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelImportEntity;
import org.springblade.config.autopoi.poi.excel.entity.result.ExcelImportResult;
import org.springblade.config.autopoi.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.springblade.config.autopoi.poi.excel.imports.base.ImportBaseService;
import org.springblade.config.autopoi.poi.excel.imports.base.ImportFileServiceI;
import org.springblade.config.autopoi.poi.excel.imports.verifys.VerifyHandlerServer;
import org.springblade.config.autopoi.poi.exception.excel.ExcelImportException;
import org.springblade.config.autopoi.poi.exception.excel.enums.ExcelImportEnum;
import org.springblade.config.autopoi.poi.util.ExcelUtil;
import org.springblade.config.autopoi.poi.util.PoiPublicUtil;
import org.springblade.config.autopoi.util.ApplicationContextUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel 导入服务
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked", "hiding" })
public class ExcelImportServer extends ImportBaseService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelImportServer.class);

	private CellValueServer cellValueServer;

	private VerifyHandlerServer verifyHandlerServer;

	private boolean verfiyFail = false;
	/**
	 * 异常数据styler
	 */
	private CellStyle errorCellStyle;

	public ExcelImportServer() {
		this.cellValueServer = new CellValueServer();
		this.verifyHandlerServer = new VerifyHandlerServer();
	}

	/***
	 * 向List里面继续添加元素
	 * 
	 * @param object
	 * @param param
	 * @param row
	 * @param titlemap
	 * @param targetId
	 * @param pictures
	 * @param params
	 */
	private void addListContinue(Object object, ExcelCollectionParams param, Row row, Map<Integer, String> titlemap, String targetId, Map<String, PictureData> pictures, ImportParams params) throws Exception {
		Collection collection = (Collection) PoiPublicUtil.getMethod(param.getName(), object.getClass()).invoke(object, new Object[] {});
		Object entity = PoiPublicUtil.createObject(param.getType(), targetId);
		String picId;
		boolean isUsed = false;// 是否需要加上这个对象
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			String titleString = (String) titlemap.get(i);
			if (param.getExcelParams().containsKey(titleString)) {
				if (param.getExcelParams().get(titleString).getType() == 2) {
					picId = row.getRowNum() + "_" + i;
					saveImage(object, picId, param.getExcelParams(), titleString, pictures, params);
				} else {
					saveFieldValue(params, entity, cell, param.getExcelParams(), titleString, row);
				}
				isUsed = true;
			}
		}
		if (isUsed) {
			collection.add(entity);
		}
	}

	/**
	 * 获取key的值,针对不同类型获取不同的值
	 */
	private String getKeyValue(Cell cell) {
		if(cell==null){
			return null;
		}
		Object obj = null;
		switch (cell.getCellTypeEnum()) {
		case STRING:
			obj = cell.getStringCellValue();
			break;
		case BOOLEAN:
			obj = cell.getBooleanCellValue();
			break;
		case NUMERIC:
			obj = cell.getNumericCellValue();
			break;
		case FORMULA:
			obj = cell.getCellFormula();
			break;
		default:
			break;
		}
		return obj == null ? null : obj.toString().trim();
	}

	/**
	 * 获取保存的真实路径
	 * 
	 * @param excelImportEntity
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private String getSaveUrl(ExcelImportEntity excelImportEntity, Object object) throws Exception {
		String url = "";
		if (excelImportEntity.getSaveUrl().equals("upload")) {
			if (excelImportEntity.getMethods() != null && excelImportEntity.getMethods().size() > 0) {
				object = getFieldBySomeMethod(excelImportEntity.getMethods(), object);
			}
			url = object.getClass().getName().split("\\.")[object.getClass().getName().split("\\.").length - 1];
			return excelImportEntity.getSaveUrl() + "/" + url.substring(0, url.lastIndexOf("Entity"));
		}
		return excelImportEntity.getSaveUrl();
	}
	//update-begin--Author:xuelin  Date:20171205 for：TASK #2098 【excel问题】 Online 一对多导入失败--------------------
	private <T> List<T> importExcel(Collection<T> result, Sheet sheet, Class<?> pojoClass, ImportParams params, Map<String, PictureData> pictures) throws Exception {
		List collection = new ArrayList();
		Map<String, ExcelImportEntity> excelParams = new HashMap<String, ExcelImportEntity>();
		List<ExcelCollectionParams> excelCollection = new ArrayList<ExcelCollectionParams>();
		String targetId = null;
		if (!Map.class.equals(pojoClass)) {
			Field fileds[] = PoiPublicUtil.getClassFields(pojoClass);
			ExcelTarget etarget = pojoClass.getAnnotation(ExcelTarget.class);
			if (etarget != null) {
				targetId = etarget.value();
			}
			getAllExcelField(targetId, fileds, excelParams, excelCollection, pojoClass, null);
		}
		ignoreHeaderHandler(excelParams, params);
		Iterator<Row> rows = sheet.rowIterator();
		Map<Integer, String> titlemap = getTitleMap(sheet, rows, params, excelCollection);
        Set<Integer> columnIndexSet = titlemap.keySet();
        Integer maxColumnIndex = Collections.max(columnIndexSet);
        Integer minColumnIndex = Collections.min(columnIndexSet);
		Row row = null;
		//跳过表头和标题行
		for (int j = 0; j < params.getTitleRows() + params.getHeadRows(); j++) {
			row = rows.next();
		}
		Object object = null;
		String picId;
		while (rows.hasNext() && (row == null || sheet.getLastRowNum() - row.getRowNum() > params.getLastOfInvalidRow())) {
			row = rows.next();
			//update-begin--Author:xuelin  Date:20171017 for：TASK #2373 【bug】表改造问题，导致 3.7.1批量导入用户bug-导入不成功--------------------
			// 判断是集合元素还是不是集合元素,如果是就继续加入这个集合,不是就创建新的对象
			//update-begin--Author:xuelin  Date:20171206 for：TASK #2451 【excel导出bug】online 一对多导入成功， 但是现在代码生成后的一对多online导入有问题了
			Cell keyIndexCell = row.getCell(params.getKeyIndex());
			if (excelCollection.size()>0 && StringUtils.isEmpty(getKeyValue(keyIndexCell)) && object != null && !Map.class.equals(pojoClass)) {
				//update-end--Author:xuelin  Date:20171206 for：TASK #2451 【excel导出bug】online 一对多导入成功， 但是现在代码生成后的一对多online导入有问题了
				for (ExcelCollectionParams param : excelCollection) {
					addListContinue(object, param, row, titlemap, targetId, pictures, params);
				}
				
			} else {			
				object = PoiPublicUtil.createObject(pojoClass, targetId);
				try {
                    //update-begin-author:taoyan date:20200303 for:导入图片
				    int firstCellNum = row.getFirstCellNum();
				    if(firstCellNum>minColumnIndex){
                        firstCellNum = minColumnIndex;
                    }
                    int lastCellNum = row.getLastCellNum();
                    if(lastCellNum<maxColumnIndex+1){
                        lastCellNum = maxColumnIndex+1;
                    }
					for (int i = firstCellNum, le = lastCellNum; i < le; i++) {
						Cell cell = row.getCell(i);
						String titleString = (String) titlemap.get(i);
						if (excelParams.containsKey(titleString) || Map.class.equals(pojoClass)) {
							if (excelParams.get(titleString) != null && excelParams.get(titleString).getType() == 2) {
								picId = row.getRowNum() + "_" + i;
								saveImage(object, picId, excelParams, titleString, pictures, params);
							} else {
								if(params.getImageList()!=null && params.getImageList().contains(titleString)){
									if (pictures != null) {
										picId = row.getRowNum() + "_" + i;
										PictureData image = pictures.get(picId);
										if(image!=null){
											byte[] data = image.getData();
											params.getDataHanlder().setMapValue((Map) object, titleString, data);
										}
									}
								}else{
									saveFieldValue(params, object, cell, excelParams, titleString, row);
								}
                        //update-end-author:taoyan date:20200303 for:导入图片
							}
						}
					}

					for (ExcelCollectionParams param : excelCollection) {
						addListContinue(object, param, row, titlemap, targetId, pictures, params);
					}
					collection.add(object);
				} catch (ExcelImportException e) {
					if (!e.getType().equals(ExcelImportEnum.VERIFY_ERROR)) {
						throw new ExcelImportException(e.getType(), e);
					}
				}
			}
			//update-end--Author:xuelin  Date:20171017 for：TASK #2373 【bug】表改造问题，导致 3.7.1批量导入用户bug-导入不成功--------------------
		}
		return collection;
	}

	/**
	 * 获取忽略的表头信息
	 * @param excelParams
	 * @param params
	 */
	private void ignoreHeaderHandler(Map<String, ExcelImportEntity> excelParams,ImportParams params){
		List<String> ignoreList = new ArrayList<>();
		for(String key:excelParams.keySet()){
			String temp = excelParams.get(key).getGroupName();
			if(temp!=null && temp.length()>0){
				ignoreList.add(temp);
			}
		}
		params.setIgnoreHeaderList(ignoreList);
	}

	/**
	 * 获取表格字段列名对应信息
	 * 
	 * @param rows
	 * @param params
	 * @param excelCollection
	 * @return
	 */
	private Map<Integer, String> getTitleMap(Sheet sheet, Iterator<Row> rows, ImportParams params, List<ExcelCollectionParams> excelCollection) throws Exception {
		Map<Integer, String> titlemap = new HashMap<Integer, String>();
		Iterator<Cell> cellTitle = null;
		String collectionName = null;
		ExcelCollectionParams collectionParams = null;
		Row headRow = null;
		int headBegin = params.getTitleRows();
		//update_begin-author:taoyan date:2020622 for：当文件行数小于代码里设置的TitleRows时headRow一直为空就会出现死循环
		int allRowNum = sheet.getPhysicalNumberOfRows();
		//找到首行表头，每个sheet都必须至少有一行表头
		while(headRow == null && headBegin < allRowNum){
			headRow = sheet.getRow(headBegin++);
		}
		if(headRow==null){
			throw new Exception("不识别该文件");
		}
		//update-end-author:taoyan date:2020622 for：当文件行数小于代码里设置的TitleRows时headRow一直为空就会出现死循环

		//设置表头行数
		if (ExcelUtil.isMergedRegion(sheet, headRow.getRowNum(), 0)) {
			params.setHeadRows(2);
		}else{
			params.setHeadRows(1);
		}
		cellTitle = headRow.cellIterator();
		while (cellTitle.hasNext()) {
			Cell cell = cellTitle.next();
			String value = getKeyValue(cell);
			if (StringUtils.isNotEmpty(value)) {
				titlemap.put(cell.getColumnIndex(), value);//加入表头列表
			}
		}
		
		//多行表头
		for (int j = headBegin; j < headBegin + params.getHeadRows()-1; j++) {
			headRow = sheet.getRow(j);
			cellTitle = headRow.cellIterator();
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = getKeyValue(cell);
				if (StringUtils.isNotEmpty(value)) {
					int columnIndex = cell.getColumnIndex();
					//当前cell的上一行是否为合并单元格
					if(ExcelUtil.isMergedRegion(sheet, cell.getRowIndex()-1, columnIndex)){
						collectionName = ExcelUtil.getMergedRegionValue(sheet, cell.getRowIndex()-1, columnIndex);
						if(params.isIgnoreHeader(collectionName)){
							titlemap.put(cell.getColumnIndex(), value);
						}else{
							titlemap.put(cell.getColumnIndex(), collectionName + "_" + value);
						}
					}else{
						titlemap.put(cell.getColumnIndex(), value);
					}
					/*int i = cell.getColumnIndex();
					// 用以支持重名导入
					if (titlemap.containsKey(i)) {
						collectionName = titlemap.get(i);
						collectionParams = getCollectionParams(excelCollection, collectionName);
						titlemap.put(i, collectionName + "_" + value);
					} else if (StringUtils.isNotEmpty(collectionName) && collectionParams.getExcelParams().containsKey(collectionName + "_" + value)) {
						titlemap.put(i, collectionName + "_" + value);
					} else {
						collectionName = null;
						collectionParams = null;
					}
					if (StringUtils.isEmpty(collectionName)) {
						titlemap.put(i, value);
					}*/
				}
			}
		}
		return titlemap;
	}
	//update-end--Author:xuelin  Date:20171205 for：TASK #2098 【excel问题】 Online 一对多导入失败--------------------
	/**
	 * 获取这个名称对应的集合信息
	 * 
	 * @param excelCollection
	 * @param collectionName
	 * @return
	 */
	private ExcelCollectionParams getCollectionParams(List<ExcelCollectionParams> excelCollection, String collectionName) {
		for (ExcelCollectionParams excelCollectionParams : excelCollection) {
			if (collectionName.equals(excelCollectionParams.getExcelName())) {
				return excelCollectionParams;
			}
		}
		return null;
	}

	/**
	 * Excel 导入 field 字段类型 Integer,Long,Double,Date,String,Boolean
	 * 
	 * @param inputstream
	 * @param pojoClass
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ExcelImportResult importExcelByIs(InputStream inputstream, Class<?> pojoClass, ImportParams params) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excel import start ,class is {}", pojoClass);
		}
		List<T> result = new ArrayList<T>();
		Workbook book = null;
		boolean isXSSFWorkbook = true;
		if (!(inputstream.markSupported())) {
			inputstream = new PushbackInputStream(inputstream, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(inputstream)) {
			book = new HSSFWorkbook(inputstream);
			isXSSFWorkbook = false;
		} else if (DocumentFactoryHelper.hasOOXMLHeader(inputstream)) {
			book = new XSSFWorkbook(OPCPackage.open(inputstream));
		}
		createErrorCellStyle(book);
		Map<String, PictureData> pictures;
		for (int i = 0; i < params.getSheetNum(); i++) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" start to read excel by is ,startTime is {}", new Date().getTime());
			}
			if (isXSSFWorkbook) {
				pictures = PoiPublicUtil.getSheetPictrues07((XSSFSheet) book.getSheetAt(i), (XSSFWorkbook) book);
			} else {
				pictures = PoiPublicUtil.getSheetPictrues03((HSSFSheet) book.getSheetAt(i), (HSSFWorkbook) book);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" end to read excel by is ,endTime is {}", new Date().getTime());
			}
			result.addAll(importExcel(result, book.getSheetAt(i), pojoClass, params, pictures));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" end to read excel list by pos ,endTime is {}", new Date().getTime());
			}
		}
		if (params.isNeedSave()) {
			saveThisExcel(params, pojoClass, isXSSFWorkbook, book);
		}
		return new ExcelImportResult(result, verfiyFail, book);
	}

	/**
	 * 保存字段值(获取值,校验值,追加错误信息)
	 * 
	 * @param params
	 * @param object
	 * @param cell
	 * @param excelParams
	 * @param titleString
	 * @param row
	 * @throws Exception
	 */
	private void saveFieldValue(ImportParams params, Object object, Cell cell, Map<String, ExcelImportEntity> excelParams, String titleString, Row row) throws Exception {
		Object value = cellValueServer.getValue(params.getDataHanlder(), object, cell, excelParams, titleString);
		if (object instanceof Map) {
			if (params.getDataHanlder() != null) {
				params.getDataHanlder().setMapValue((Map) object, titleString, value);
			} else {
				((Map) object).put(titleString, value);
			}
		} else {
			ExcelVerifyHanlderResult verifyResult = verifyHandlerServer.verifyData(object, value, titleString, excelParams.get(titleString).getVerify(), params.getVerifyHanlder());
			if (verifyResult.isSuccess()) {
				setValues(excelParams.get(titleString), object, value);
			} else {
				Cell errorCell = row.createCell(row.getLastCellNum());
				errorCell.setCellValue(verifyResult.getMsg());
				errorCell.setCellStyle(errorCellStyle);
				verfiyFail = true;
				throw new ExcelImportException(ExcelImportEnum.VERIFY_ERROR);
			}
		}
	}

	/**
	 * 
	 * @param object
	 * @param picId
	 * @param excelParams
	 * @param titleString
	 * @param pictures
	 * @param params
	 * @throws Exception
	 */
	private void saveImage(Object object, String picId, Map<String, ExcelImportEntity> excelParams, String titleString, Map<String, PictureData> pictures, ImportParams params) throws Exception {
		if (pictures == null || pictures.get(picId)==null) {
			return;
		}
		PictureData image = pictures.get(picId);
		byte[] data = image.getData();
		String fileName = "pic" + Math.round(Math.random() * 100000000000L);
		fileName += "." + PoiPublicUtil.getFileExtendName(data);
		//update-beign-author:taoyan date:20200302 for:【多任务】online 专项集中问题 LOWCOD-159
		int saveType = excelParams.get(titleString).getSaveType();
		if ( saveType == 1) {
			String path = PoiPublicUtil.getWebRootPath(getSaveUrl(excelParams.get(titleString), object));
			File savefile = new File(path);
			if (!savefile.exists()) {
				savefile.mkdirs();
			}
			savefile = new File(path + "/" + fileName);
			FileOutputStream fos = new FileOutputStream(savefile);
			fos.write(data);
			fos.close();
			setValues(excelParams.get(titleString), object, getSaveUrl(excelParams.get(titleString), object) + "/" + fileName);
		} else if(saveType==2) {
			setValues(excelParams.get(titleString), object, data);
		} else {
			ImportFileServiceI importFileService = null;
			try {
				importFileService = ApplicationContextUtil.getContext().getBean(ImportFileServiceI.class);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			if(importFileService!=null){
				String dbPath = importFileService.doUpload(data);
				setValues(excelParams.get(titleString), object, dbPath);
			}
		}
		//update-end-author:taoyan date:20200302 for:【多任务】online 专项集中问题 LOWCOD-159
	}

	private void createErrorCellStyle(Workbook workbook) {
		errorCellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setColor(Font.COLOR_RED);
		errorCellStyle.setFont(font);
	}

}
