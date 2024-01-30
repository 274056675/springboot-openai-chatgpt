package org.springblade.cgform.model.generate.impl;

import org.springblade.config.db.DbConfig;
import org.springblade.cgform.model.database.DbReadTableUtil;
import org.springblade.cgform.model.generate.IGenerate;
import org.springblade.cgform.model.generate.file.FileVo;
import org.springblade.cgform.model.generate.impl.provider.FileProvider;
import org.springblade.cgform.model.generate.pojo.ColumnVo;
import org.springblade.cgform.model.generate.pojo.onetomany.MainTableVo;
import org.springblade.cgform.model.generate.pojo.onetomany.SubTableVo;
import org.springblade.cgform.model.generate.util.NonceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CodeGenerateOneToMany extends FileProvider implements IGenerate {
	//public static String a = "A";
	//public static String b = "B";
	private MainTableVo mainTableVo;
	private List<ColumnVo> mainColums;
	private List<ColumnVo> originalMainColumns;
	private List<SubTableVo> subTables;

	public CodeGenerateOneToMany(MainTableVo mainTableVo, List<SubTableVo> subTables) {
		this.subTables = subTables;
		this.mainTableVo = mainTableVo;
	}

	public CodeGenerateOneToMany(MainTableVo mainTableVo, List<ColumnVo> mainColums, List<ColumnVo> originalMainColumns,
			List<SubTableVo> subTables) {
		this.mainTableVo = mainTableVo;
		this.mainColums = mainColums;
		this.originalMainColumns = originalMainColumns;
		this.subTables = subTables;
	}

	public Map<String, Object> dtaMapMethod() throws Exception {
		Map<String, Object> localHashMap = new HashMap<>();

		localHashMap.put("bussiPackage", DbConfig.bussiPackage);
		localHashMap.put("entityPackage", mainTableVo.getEntityPackage());
		localHashMap.put("entityName", mainTableVo.getEntityName());
		localHashMap.put("tableName", mainTableVo.getTableName());
		localHashMap.put("ftl_description", mainTableVo.getFtlDescription());
		localHashMap.put("primaryKeyField", DbConfig.dbTableId);
		if (mainTableVo.getFieldRequiredNum() == null) {
			mainTableVo.setFieldRequiredNum(Integer.valueOf(StringUtils.isNotEmpty(DbConfig.pageFieldRequiredNum)
					? Integer.parseInt(DbConfig.pageFieldRequiredNum)
					: -1));
		}
		if (mainTableVo.getSearchFieldNum() == null) {
			mainTableVo.setSearchFieldNum(Integer.valueOf(StringUtils.isNotEmpty(DbConfig.pageSearchFiledNum)
					? Integer.parseInt(DbConfig.pageSearchFiledNum)
					: -1));
		}
		if (mainTableVo.getFieldRowNum() == null) {
			mainTableVo.setFieldRowNum(Integer.valueOf(Integer.parseInt(DbConfig.pageFieldRowNum)));
		}
		localHashMap.put("tableVo", mainTableVo);
		try {
			if ((mainColums == null) || (mainColums.size() == 0)) {
				mainColums = DbReadTableUtil.getColumns(mainTableVo.getTableName());
			}
			if ((originalMainColumns == null) || (originalMainColumns.size() == 0)) {
				originalMainColumns = DbReadTableUtil.getOriginalColumns(mainTableVo.getTableName());
			}
			localHashMap.put("columns", mainColums);

			localHashMap.put("originalColumns", originalMainColumns);
			for (ColumnVo columnVo : this.originalMainColumns) {
				if (columnVo.getFieldName().toLowerCase()
						.equals(DbConfig.dbTableId.toLowerCase())) {
					localHashMap.put("primaryKeyPolicy", columnVo.getFieldType());
				}
			}
			for (SubTableVo subTableVo : this.subTables) {
				if (subTableVo.getColums() == null || subTableVo.getColums().size() == 0) {
					subTableVo.setColums(DbReadTableUtil.getColumns(subTableVo.getTableName()));
				}
				if (subTableVo.getOriginalColumns() == null || subTableVo.getOriginalColumns().size() == 0) {
					subTableVo.setOriginalColumns(DbReadTableUtil.getOriginalColumns(subTableVo.getTableName()));
				}
				String[] foreignKeys = subTableVo.getForeignKeys();
				ArrayList<String> list = new ArrayList<String>();
				String[] array = foreignKeys;
				for (int length = array.length, i = 0; i < length; ++i) {
					list.add(DbReadTableUtil.humpConver(array[i],true));
				}
				subTableVo.setForeignKeys(list.toArray(new String[0]));
				subTableVo.setOriginalForeignKeys(foreignKeys);
			}
			localHashMap.put("subTables", subTables);
		} catch (Exception localException) {
			throw localException;
		}
		long l = NonceUtils.c() + NonceUtils.g();
		localHashMap.put("serialVersionUID", String.valueOf(l));
		log.info("code template data: " + localHashMap.toString());
		return localHashMap;
	}

	public List<String> generateCodeFile(String stylePath) throws Exception {
		String str1 = DbConfig.projectPath;
		Map<String,Object> localMap = dtaMapMethod();

		String templatepath = DbConfig.templatepath;
		if (strSubStartEnd(templatepath, "/").equals("mjkj/code-template")) {
			templatepath = "/" + strSubStartEnd(templatepath, "/") + "/onetomany";
			DbConfig.setTemplatepath(templatepath);
		}
		FileVo locala = new FileVo(templatepath);
		locala.setStylePath(stylePath);
		generateCodeFile(locala, str1, localMap);
		log.info(" ---- generate  code  success =======> 主表名：" + mainTableVo.getTableName());
		return msg;
	}

	public List<String> generateCodeFile(String projectPath, String templatePath, String stylePath) throws Exception {
		if ((projectPath != null) && (!"".equals(projectPath))) {
			DbConfig.setProjectPath(projectPath);
		}
		if ((templatePath != null) && (!"".equals(templatePath))) {
			DbConfig.setTemplatepath(templatePath);
		}
		generateCodeFile(stylePath);
		return msg;
	}
}
