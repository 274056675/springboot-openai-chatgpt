package org.springblade.config.autopoi.poi.excel.imports.sax.parse;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.config.autopoi.poi.excel.annotation.ExcelTarget;
import org.springblade.config.autopoi.poi.excel.entity.ImportParams;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelCollectionParams;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelImportEntity;
import org.springblade.config.autopoi.poi.excel.entity.sax.SaxReadCellEntity;
import org.springblade.config.autopoi.poi.excel.imports.CellValueServer;
import org.springblade.config.autopoi.poi.excel.imports.base.ImportBaseService;
import org.springblade.config.autopoi.poi.exception.excel.ExcelImportException;
import org.springblade.config.autopoi.poi.handler.inter.IExcelReadRowHanlder;
import org.springblade.config.autopoi.poi.util.PoiPublicUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 当行读取数据
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SaxRowRead extends ImportBaseService implements ISaxRowRead {

	private static final Logger LOGGER = LoggerFactory.getLogger(SaxRowRead.class);
	/** 需要返回的数据 **/
	private List list;
	/** 导出的对象 **/
	private Class<?> pojoClass;
	/** 导入参数 **/
	private ImportParams params;
	/** 列表头对应关系 **/
	private Map<Integer, String> titlemap = new HashMap<Integer, String>();
	/** 当前的对象 **/
	private Object object = null;

	private Map<String, ExcelImportEntity> excelParams = new HashMap<String, ExcelImportEntity>();

	private List<ExcelCollectionParams> excelCollection = new ArrayList<ExcelCollectionParams>();

	private String targetId;

	private CellValueServer cellValueServer;

	private IExcelReadRowHanlder hanlder;

	public SaxRowRead(Class<?> pojoClass, ImportParams params, IExcelReadRowHanlder hanlder) {
		list = Lists.newArrayList();
		this.params = params;
		this.pojoClass = pojoClass;
		cellValueServer = new CellValueServer();
		this.hanlder = hanlder;
		initParams(pojoClass, params);
	}

	private void initParams(Class<?> pojoClass, ImportParams params) {
		try {

			Field fileds[] = PoiPublicUtil.getClassFields(pojoClass);
			ExcelTarget etarget = pojoClass.getAnnotation(ExcelTarget.class);
			if (etarget != null) {
				targetId = etarget.value();
			}
			getAllExcelField(targetId, fileds, excelParams, excelCollection, pojoClass, null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ExcelImportException(e.getMessage());
		}

	}

	@Override
	public <T> List<T> getList() {
		return list;
	}

	@Override
	public void parse(int index, List<SaxReadCellEntity> datas) {
		try {
			if (datas == null || datas.size() == 0) {
				return;
			}
			// 标题行跳过
			if (index < params.getTitleRows()) {
				return;
			}
			// 表头行
			if (index < params.getTitleRows() + params.getHeadRows()) {
				addHeadData(datas);
			} else {
				addListData(datas);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ExcelImportException(e.getMessage());
		}
	}

	/**
	 * 集合元素处理
	 * 
	 * @param datas
	 */
	private void addListData(List<SaxReadCellEntity> datas) throws Exception {
		// 判断是集合元素还是不是集合元素,如果是就继续加入这个集合,不是就创建新的对象
		if ((datas.get(params.getKeyIndex()) == null || StringUtils.isEmpty(String.valueOf(datas.get(params.getKeyIndex()).getValue()))) && object != null) {
			for (ExcelCollectionParams param : excelCollection) {
				addListContinue(object, param, datas, titlemap, targetId, params);
			}
		} else {
			if (object != null && hanlder != null) {
				hanlder.hanlder(object);
			}
			object = PoiPublicUtil.createObject(pojoClass, targetId);
			SaxReadCellEntity entity;
			for (int i = 0, le = datas.size(); i < le; i++) {
				entity = datas.get(i);
				String titleString = (String) titlemap.get(i);
				if (excelParams.containsKey(titleString)) {
					saveFieldValue(params, object, entity, excelParams, titleString);
				}
			}
			for (ExcelCollectionParams param : excelCollection) {
				addListContinue(object, param, datas, titlemap, targetId, params);
			}
			if (hanlder == null) {
				list.add(object);
			}
		}

	}

	/***
	 * 向List里面继续添加元素
	 * 
	 * @param exclusions
	 * @param object
	 * @param param
	 * @param datas
	 * @param titlemap
	 * @param targetId
	 * @param params
	 */
	private void addListContinue(Object object, ExcelCollectionParams param, List<SaxReadCellEntity> datas, Map<Integer, String> titlemap, String targetId, ImportParams params) throws Exception {
		Collection collection = (Collection) PoiPublicUtil.getMethod(param.getName(), object.getClass()).invoke(object, new Object[] {});
		Object entity = PoiPublicUtil.createObject(param.getType(), targetId);
		boolean isUsed = false;// 是否需要加上这个对象
		for (int i = 0; i < datas.size(); i++) {
			String titleString = (String) titlemap.get(i);
			if (param.getExcelParams().containsKey(titleString)) {
				saveFieldValue(params, entity, datas.get(i), param.getExcelParams(), titleString);
				isUsed = true;
			}
		}
		if (isUsed) {
			collection.add(entity);
		}
	}

	/**
	 * 设置值
	 * 
	 * @param params
	 * @param object
	 * @param entity
	 * @param excelParams
	 * @param titleString
	 * @throws Exception
	 */
	private void saveFieldValue(ImportParams params, Object object, SaxReadCellEntity entity, Map<String, ExcelImportEntity> excelParams, String titleString) throws Exception {
		Object value = cellValueServer.getValue(params.getDataHanlder(), object, entity, excelParams, titleString);
		setValues(excelParams.get(titleString), object, value);
	}

	/**
	 * put 表头数据
	 * 
	 * @param datas
	 */
	private void addHeadData(List<SaxReadCellEntity> datas) {
		for (int i = 0; i < datas.size(); i++) {
			if (StringUtils.isNotEmpty(String.valueOf(datas.get(i).getValue()))) {
				titlemap.put(i, String.valueOf(datas.get(i).getValue()));
			}
		}
	}
}
