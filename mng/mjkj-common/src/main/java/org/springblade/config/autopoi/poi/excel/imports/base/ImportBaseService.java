package org.springblade.config.autopoi.poi.excel.imports.base;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springblade.config.autopoi.poi.excel.annotation.Excel;
import org.springblade.config.autopoi.poi.excel.annotation.ExcelCollection;
import org.springblade.config.autopoi.poi.excel.annotation.ExcelVerify;
import org.springblade.config.autopoi.poi.excel.entity.ImportParams;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelCollectionParams;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelImportEntity;
import org.springblade.config.autopoi.poi.excel.entity.params.ExcelVerifyEntity;
import org.springblade.config.autopoi.poi.util.PoiPublicUtil;
import org.springblade.config.autopoi.service.AutoPoiDictServiceI;
import org.springblade.config.autopoi.util.ApplicationContextUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导入基础和,普通方法和Sax共用
 *
 */
public class ImportBaseService {

	/**
	 * 把这个注解解析放到类型对象中
	 * 
	 * @param targetId
	 * @param field
	 * @param excelEntity
	 * @param pojoClass
	 * @param getMethods
	 * @param temp
	 * @throws Exception
	 */
	public void addEntityToMap(String targetId, Field field, ExcelImportEntity excelEntity, Class<?> pojoClass, List<Method> getMethods, Map<String, ExcelImportEntity> temp) throws Exception {
		Excel excel = field.getAnnotation(Excel.class);
		excelEntity = new ExcelImportEntity();
		excelEntity.setType(excel.type());
		excelEntity.setSaveUrl(excel.savePath());
		excelEntity.setSaveType(excel.imageType());
		excelEntity.setReplace(excel.replace());
		excelEntity.setDatabaseFormat(excel.databaseFormat());
		excelEntity.setVerify(getImportVerify(field));
		excelEntity.setSuffix(excel.suffix());
		excelEntity.setNumFormat(excel.numFormat());
		excelEntity.setGroupName(excel.groupName());
		//update-begin-author:taoYan date:20180202 for:TASK #2067 【bug excel 问题】excel导入字典文本翻译问题
		excelEntity.setMultiReplace(excel.multiReplace());
		if(StringUtils.isNotEmpty(excel.dicCode())){
			AutoPoiDictServiceI jeecgDictService = null;
			try {
				jeecgDictService = ApplicationContextUtil.getContext().getBean(AutoPoiDictServiceI.class);
			} catch (Exception e) {
			}
			if(jeecgDictService!=null){
				 String[] dictReplace = jeecgDictService.queryDict(excel.dictTable(), excel.dicCode(), excel.dicText());
				 if(excelEntity.getReplace()!=null && dictReplace!=null && dictReplace.length!=0){
					 excelEntity.setReplace(dictReplace);
				 }
			}
		}
		//update-end-author:taoYan date:20180202 for:TASK #2067 【bug excel 问题】excel导入字典文本翻译问题
		getExcelField(targetId, field, excelEntity, excel, pojoClass);
		if (getMethods != null) {
			List<Method> newMethods = new ArrayList<Method>();
			newMethods.addAll(getMethods);
			newMethods.add(excelEntity.getMethod());
			excelEntity.setMethods(newMethods);
		}
		temp.put(excelEntity.getName(), excelEntity);

	}

	/**
	 * 获取导入校验参数
	 * 
	 * @param field
	 * @return
	 */
	public ExcelVerifyEntity getImportVerify(Field field) {
		ExcelVerify verify = field.getAnnotation(ExcelVerify.class);
		if (verify != null) {
			ExcelVerifyEntity entity = new ExcelVerifyEntity();
			entity.setEmail(verify.isEmail());
			entity.setInterHandler(verify.interHandler());
			entity.setMaxLength(verify.maxLength());
			entity.setMinLength(verify.minLength());
			entity.setMobile(verify.isMobile());
			entity.setNotNull(verify.notNull());
			entity.setRegex(verify.regex());
			entity.setRegexTip(verify.regexTip());
			entity.setTel(verify.isTel());
			return entity;
		}
		return null;
	}

	/**
	 * 获取需要导出的全部字段
	 * 
	 * 
	 * @param exclusions
	 * @param targetId
	 *            目标ID
	 * @param fields
	 * @param excelCollection
	 * @throws Exception
	 */
	public void getAllExcelField(String targetId, Field[] fields, Map<String, ExcelImportEntity> excelParams, List<ExcelCollectionParams> excelCollection, Class<?> pojoClass, List<Method> getMethods) throws Exception {
		ExcelImportEntity excelEntity = null;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (PoiPublicUtil.isNotUserExcelUserThis(null, field, targetId)) {
				continue;
			}
			if (PoiPublicUtil.isCollection(field.getType())) {
				// 集合对象设置属性
				ExcelCollectionParams collection = new ExcelCollectionParams();
				collection.setName(field.getName());
				Map<String, ExcelImportEntity> temp = new HashMap<String, ExcelImportEntity>();
				ParameterizedType pt = (ParameterizedType) field.getGenericType();
				Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
				collection.setType(clz);
				getExcelFieldList(targetId, PoiPublicUtil.getClassFields(clz), clz, temp, null);
				collection.setExcelParams(temp);
				collection.setExcelName(field.getAnnotation(ExcelCollection.class).name());
				additionalCollectionName(collection);
				excelCollection.add(collection);
			} else if (PoiPublicUtil.isJavaClass(field)) {
				addEntityToMap(targetId, field, excelEntity, pojoClass, getMethods, excelParams);
			} else {
				List<Method> newMethods = new ArrayList<Method>();
				if (getMethods != null) {
					newMethods.addAll(getMethods);
				}
				newMethods.add(PoiPublicUtil.getMethod(field.getName(), pojoClass));
				getAllExcelField(targetId, PoiPublicUtil.getClassFields(field.getType()), excelParams, excelCollection, field.getType(), newMethods);
			}
		}
	}

	/**
	 * 追加集合名称到前面
	 * 
	 * @param collection
	 */
	private void additionalCollectionName(ExcelCollectionParams collection) {
		Set<String> keys = new HashSet<String>();
		keys.addAll(collection.getExcelParams().keySet());
		for (String key : keys) {
			collection.getExcelParams().put(collection.getExcelName() + "_" + key, collection.getExcelParams().get(key));
			collection.getExcelParams().remove(key);
		}
	}

	public void getExcelField(String targetId, Field field, ExcelImportEntity excelEntity, Excel excel, Class<?> pojoClass) throws Exception {
		excelEntity.setName(getExcelName(excel.name(), targetId));
		String fieldname = field.getName();
		//update-begin-author:taoyan for:TASK #2798 【例子】导入扩展方法，支持自定义导入字段转换规则
		excelEntity.setMethod(PoiPublicUtil.getMethod(fieldname, pojoClass, field.getType(),excel.importConvert()));
		//update-end-author:taoyan for:TASK #2798 【例子】导入扩展方法，支持自定义导入字段转换规则
		if (StringUtils.isNotEmpty(excel.importFormat())) {
			excelEntity.setFormat(excel.importFormat());
		} else {
			excelEntity.setFormat(excel.format());
		}
	}

	public void getExcelFieldList(String targetId, Field[] fields, Class<?> pojoClass, Map<String, ExcelImportEntity> temp, List<Method> getMethods) throws Exception {
		ExcelImportEntity excelEntity = null;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (PoiPublicUtil.isNotUserExcelUserThis(null, field, targetId)) {
				continue;
			}
			if (PoiPublicUtil.isJavaClass(field)) {
				addEntityToMap(targetId, field, excelEntity, pojoClass, getMethods, temp);
			} else {
				List<Method> newMethods = new ArrayList<Method>();
				if (getMethods != null) {
					newMethods.addAll(getMethods);
				}
				newMethods.add(PoiPublicUtil.getMethod(field.getName(), pojoClass, field.getType()));
				getExcelFieldList(targetId, PoiPublicUtil.getClassFields(field.getType()), field.getType(), temp, newMethods);
			}
		}
	}

	/**
	 * 判断在这个单元格显示的名称
	 * 
	 * @param exportName
	 * @param targetId
	 * @return
	 */
	public String getExcelName(String exportName, String targetId) {
		if (exportName.indexOf("_") < 0) {
			return exportName;
		}
		String[] arr = exportName.split(",");
		for (String str : arr) {
			if (str.indexOf(targetId) != -1) {
				return str.split("_")[0];
			}
		}
		return null;
	}

	public Object getFieldBySomeMethod(List<Method> list, Object t) throws Exception {
		Method m;
		for (int i = 0; i < list.size() - 1; i++) {
			m = list.get(i);
			t = m.invoke(t, new Object[] {});
		}
		return t;
	}

	public void saveThisExcel(ImportParams params, Class<?> pojoClass, boolean isXSSFWorkbook, Workbook book) throws Exception {
		String path = PoiPublicUtil.getWebRootPath(getSaveExcelUrl(params, pojoClass));
		File savefile = new File(path);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyMMddHHmmss");
		FileOutputStream fos = new FileOutputStream(path + "/" + format.format(new Date()) + "_" + Math.round(Math.random() * 100000) + (isXSSFWorkbook == true ? ".xlsx" : ".xls"));
		book.write(fos);
		fos.close();
	}

	/**
	 * 获取保存的Excel 的真实路径
	 * 
	 * @param params
	 * @param pojoClass
	 * @return
	 * @throws Exception
	 */
	public String getSaveExcelUrl(ImportParams params, Class<?> pojoClass) throws Exception {
		String url = "";
		if (params.getSaveUrl().equals("upload/excelUpload")) {
			url = pojoClass.getName().split("\\.")[pojoClass.getName().split("\\.").length - 1];
			return params.getSaveUrl() + "/" + url;
		}
		return params.getSaveUrl();
	}

	/**
	 * 多个get 最后再set
	 * 
	 * @param setMethods
	 * @param object
	 */
	public void setFieldBySomeMethod(List<Method> setMethods, Object object, Object value) throws Exception {
		Object t = getFieldBySomeMethod(setMethods, object);
		setMethods.get(setMethods.size() - 1).invoke(t, value);
	}

	/**
	 * 
	 * @param entity
	 * @param object
	 * @param value
	 * @throws Exception
	 */
	public void setValues(ExcelImportEntity entity, Object object, Object value) throws Exception {
		if (entity.getMethods() != null) {
			setFieldBySomeMethod(entity.getMethods(), object, value);
		} else {
			entity.getMethod().invoke(object, value);
		}
	}

}
