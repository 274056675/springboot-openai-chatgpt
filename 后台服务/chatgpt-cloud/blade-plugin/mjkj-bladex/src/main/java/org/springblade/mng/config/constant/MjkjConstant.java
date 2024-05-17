package org.springblade.mng.config.constant;

/**
 * 魔晶公共
 */
public interface MjkjConstant {
	//normal 普通同步(保留数据)  force 强制同步（删除表）
	String FORM_SYNC_DB_NORMAL = "normal";
	String FORM_SYNC_DB_FORCE = "force";

	//---------------js  sql  java 增强--------------------------
	String ENHANCE_ADD = "add";//新增
	String ENHANCE_EDIT = "edit";//修改
	String ENHANCE_DELETE = "delete";//删除
	String ENHANCE_IMPORT = "import";//导入
	String ENHANCE_EXPORT = "export";//导出
	String ENHANCE_QUERY = "query";//查询
	String ENHANCE_VIEW = "view";//详情
	String ENHANCE_START = "start";//开始
	String ENHANCE_END = "end";//结束
	String ENHANCE_QUERYANEXPORT = "queryAnExport";//查询和导出
	//---------------js  sql  java 增强 结束--------------------------

	/**
	 * 字典翻译文本后缀
	 */
	String DICT_TEXT_SUFFIX = "_dictText";


	//*********数据库类型****************************************
	String DB_TYPE_MYSQL = "MYSQL";
	String DB_TYPE_ORACLE = "ORACLE";
	String DB_TYPE_POSTGRESQL = "POSTGRESQL";
	String DB_TYPE_SQLSERVER = "SQLSERVER";


    //*********************************************业务信息***************************************************************

}
