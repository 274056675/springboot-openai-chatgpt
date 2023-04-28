package org.springblade.cgform.model.generate.impl;

import org.springblade.config.db.DbConfig;
import org.springblade.cgform.model.database.DbReadTableUtil;
import org.springblade.cgform.model.generate.IGenerate;
import org.springblade.cgform.model.generate.file.FileVo;
import org.springblade.cgform.model.generate.impl.provider.FileProvider;
import org.springblade.cgform.model.generate.pojo.ColumnVo;
import org.springblade.cgform.model.generate.pojo.TableVo;
import org.springblade.cgform.model.generate.util.NonceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CodeGenerateOne extends FileProvider implements IGenerate {
	private TableVo tableVo;
	private List<ColumnVo> columns;
	private List<ColumnVo> originalColumns;

	public CodeGenerateOne(TableVo tableVo) {
		this.tableVo = tableVo;
	}

	public CodeGenerateOne(TableVo tableVo, List<ColumnVo> columns, List<ColumnVo> originalColumns) {
		this.tableVo = tableVo;
		this.columns = columns;
		this.originalColumns = originalColumns;
	}

	public Map<String, Object> dtaMapMethod() throws Exception {
		Map<String, Object> localHashMap = new HashMap<>();

		localHashMap.put("bussiPackage", DbConfig.bussiPackage);
		localHashMap.put("entityPackage", tableVo.getEntityPackage());
		localHashMap.put("entityName", tableVo.getEntityName());
		localHashMap.put("tableName", tableVo.getTableName());
		localHashMap.put("primaryKeyField", DbConfig.dbTableId);
		if (tableVo.getFieldRequiredNum() == null) {
			tableVo.setFieldRequiredNum(Integer.valueOf(StringUtils.isNotEmpty(DbConfig.pageFieldRequiredNum)
					? Integer.parseInt(DbConfig.pageFieldRequiredNum)
					: -1));
		}
		if (tableVo.getSearchFieldNum() == null) {
			tableVo.setSearchFieldNum(Integer.valueOf(StringUtils.isNotEmpty(DbConfig.pageSearchFiledNum)
					? Integer.parseInt(DbConfig.pageSearchFiledNum)
					: -1));
		}
		if (tableVo.getFieldRowNum() == null) {
			tableVo.setFieldRowNum(Integer.valueOf(Integer.parseInt(DbConfig.pageFieldRowNum)));
		}
		localHashMap.put("tableVo", tableVo);
		try {
			if ((columns == null) || (columns.size() == 0)) {
				columns = DbReadTableUtil.getColumns(tableVo.getTableName());
			}
			localHashMap.put("columns", columns);
			if ((originalColumns == null) || (originalColumns.size() == 0)) {
				originalColumns = DbReadTableUtil.getOriginalColumns(tableVo.getTableName());
			}
			localHashMap.put("originalColumns", originalColumns);
			for (ColumnVo localColumnVo : originalColumns) {
				if (localColumnVo.getFieldName().toLowerCase()
						.equals(DbConfig.dbTableId.toLowerCase())) {
					localHashMap.put("primaryKeyPolicy", localColumnVo.getFieldType());
				}
			}
		} catch (Exception localException) {
			throw localException;
		}
		long l = NonceUtils.c() + NonceUtils.g();
		localHashMap.put("serialVersionUID", String.valueOf(l));
		log.info("load template data: " + localHashMap.toString());
		return localHashMap;
	}

	public List<String> generateCodeFile(String stylePath) throws Exception {
		log.debug("----jeecg---Code----Generation----[?表模型:" + tableVo.getTableName() + "]------- 生成中。。。");

		String str1 = DbConfig.projectPath;
		Map<String,Object> localMap = dtaMapMethod();

		String templatepath = DbConfig.templatepath;
		if (strSubStartEnd(templatepath, "/").equals("jeecg/code-template")) {
			templatepath = "/" + strSubStartEnd(templatepath, "/") + "/one";
			DbConfig.setTemplatepath(templatepath);
		}
		FileVo locala = new FileVo(templatepath);
		locala.setStylePath(stylePath);
		generateCodeFile(locala, str1, localMap);
		log.info("---- generate  code  success =======> 表名：" + tableVo.getTableName() + " ");
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

	/*public static void main(String[] args) {
		TableVo localTableVo = new TableVo();
		localTableVo.setTableName("demo");
		localTableVo.setPrimaryKeyPolicy("uuid");
		localTableVo.setEntityPackage("test");
		localTableVo.setEntityName("JeecgDemo");
		localTableVo.setFtlDescription("jeecg ??demo");
		try {
			new CodeGenerateOne(localTableVo).generateCodeFile(null);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}*/
}
