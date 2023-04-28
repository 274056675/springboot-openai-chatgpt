package org.springblade.cgform.model.database;

import org.springblade.config.constant.MjjyConfig;
import org.springblade.config.db.DbConfig;
import org.springblade.cgform.model.database.util.DbConvertDef;
import org.springblade.cgform.model.generate.pojo.ColumnVo;
import org.springblade.cgform.model.generate.util.StringBoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.config.util.TableUtil;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DbReadTableUtil {
	private static Connection conn;
	private static Statement statement;


	public static List<String> getTables() throws SQLException {
		String format = null;
		ArrayList<String> list = new ArrayList<String>(0);
		try {
			Class.forName(DbConfig.driver);
			DbReadTableUtil.conn = DriverManager.getConnection(DbConfig.url, DbConfig.username, DbConfig.password);
			DbReadTableUtil.statement = DbReadTableUtil.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (DbConfig.dbType.equals(DbConvertDef.MYSQL)) {
				format = MessageFormat.format(
						DbConvertDef.MYSQL_TABLES_SQL,
						StringBoolUtil.strToStr(DbConfig.dbName));
			}
			if (DbConfig.dbType.equals(DbConvertDef.ORACLE)) {
				format = DbConvertDef.ORACLE_TABLES_SQL;
			}
			if (DbConfig.dbType.equals(DbConvertDef.POSTGRESQL)) {
				format = DbConvertDef.POSTGRESQL_TABLES_SQL;
			}
			if (DbConfig.dbType.equals(DbConvertDef.SQLSERVER)) {
				format = DbConvertDef.SQLSERVER_TABLES_SQL;
			}
			log.debug("--------------sql-------------" + format);
			ResultSet executeQuery = DbReadTableUtil.statement.executeQuery(format);
			while (executeQuery.next()) {
				list.add(executeQuery.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (DbReadTableUtil.statement != null) {
					DbReadTableUtil.statement.close();
					DbReadTableUtil.statement = null;
					System.gc();
				}
				if (DbReadTableUtil.conn != null) {
					DbReadTableUtil.conn.close();
					DbReadTableUtil.conn = null;
					System.gc();
				}
			} catch (SQLException e) {
				throw e;
			}
		} finally {
			try {
				if (DbReadTableUtil.statement != null) {
					DbReadTableUtil.statement.close();
					DbReadTableUtil.statement = null;
					System.gc();
				}
				if (DbReadTableUtil.conn != null) {
					DbReadTableUtil.conn.close();
					DbReadTableUtil.conn = null;
					System.gc();
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		return list;
	}

	public static List<ColumnVo> getColumns(String tableName) throws Exception {
		String s2 = null;
		List<ColumnVo> list = new ArrayList<ColumnVo>();
		try {

			Connection conn = TableUtil.getConnection();
			String databaseType = TableUtil.getDatabaseType(conn);
			DbReadTableUtil.statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (databaseType.equals(DbConvertDef.MYSQL.toUpperCase())) {
				s2 = MessageFormat.format(DbConvertDef.MYSQL_COLUMNS_SQL,StringBoolUtil.strToStr(tableName), "'"+MjjyConfig.getDatabaseSchema()+"'");
			}
			if (databaseType.equals(DbConvertDef.ORACLE.toUpperCase())) {
				s2 = MessageFormat.format(DbConvertDef.ORACLE_COLUMNS_SQL,StringBoolUtil.strToStr(tableName.toUpperCase()));
			}
			if (databaseType.equals(DbConvertDef.POSTGRESQL.toUpperCase())) {
				s2 = MessageFormat.format(DbConvertDef.POSTGRESQL_COLUMNS_SQL,StringBoolUtil.strToStr(tableName), StringBoolUtil.strToStr(tableName));
			}
			if (databaseType.equals(DbConvertDef.SQLSERVER.toUpperCase())) {
				s2 = MessageFormat.format(DbConvertDef.SQLSERVER_COLUMNS_SQL,StringBoolUtil.strToStr(tableName));
			}
			log.debug("--------------sql-------------" + s2);
			ResultSet executeQuery = DbReadTableUtil.statement.executeQuery(s2);
			executeQuery.last();
			if (executeQuery.getRow() <= 0) {
				throw new Exception("该表不存在或者表中没有字段");
			}
			ColumnVo columnVo = new ColumnVo();
			columnVo.setFieldName(executeQuery.getString(1).toLowerCase());
			columnVo.setFieldDbName(executeQuery.getString(1).toUpperCase());
			columnVo.setFieldType(humpConver(executeQuery.getString(2).toLowerCase()));
			columnVo.setFieldDbType(humpConver(executeQuery.getString(2).toLowerCase()));
			columnVo.setPrecision(executeQuery.getString(4));
			columnVo.setScale(executeQuery.getString(5));
			columnVo.setCharmaxLength(executeQuery.getString(6));
			columnVo.setNullable(StringBoolUtil.strBool(executeQuery.getString(7)));
			setAttributes(columnVo);
			columnVo.setFiledComment(StringUtils.isBlank(executeQuery.getString(3)) ? columnVo.getFieldName()
					: executeQuery.getString(3));
			log.debug("columnt.getFieldName() -------------" + columnVo.getFieldName());
			list.add(columnVo);
			while (executeQuery.previous()) {
				ColumnVo columnVo2 = new ColumnVo();
				columnVo2.setFieldName(executeQuery.getString(1).toLowerCase());
				columnVo2.setFieldDbName(executeQuery.getString(1).toUpperCase());
				log.debug("columnt.getFieldName() -------------" + columnVo2.getFieldName());
				columnVo2.setFieldType(humpConver(executeQuery.getString(2).toLowerCase()));
				columnVo2.setFieldDbType(humpConver(executeQuery.getString(2).toLowerCase()));
				log.debug("-----po.setFieldType------------" + columnVo2.getFieldType());
				columnVo2.setPrecision(executeQuery.getString(4));
				columnVo2.setScale(executeQuery.getString(5));
				columnVo2.setCharmaxLength(executeQuery.getString(6));
				columnVo2.setNullable(StringBoolUtil.strBool(executeQuery.getString(7)));
				setAttributes(columnVo2);
				columnVo2.setFiledComment(StringUtils.isBlank(executeQuery.getString(3)) ? columnVo2.getFieldName()
						: executeQuery.getString(3));
				list.add(columnVo2);
			}
			log.debug("读取表成功");
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		} finally {
			try {
				if (DbReadTableUtil.statement != null) {
					DbReadTableUtil.statement.close();
					DbReadTableUtil.statement = null;
					System.gc();
				}
				if (DbReadTableUtil.conn != null) {
					DbReadTableUtil.conn.close();
					DbReadTableUtil.conn = null;
					System.gc();
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		ArrayList<ColumnVo> list2 = new ArrayList<ColumnVo>();
		for (int i = list.size() - 1; i >= 0; --i) {
			list2.add(list.get(i));
		}
		return list2;
	}

	public static List<ColumnVo> getOriginalColumns(String tableName) throws Exception {
		String s2 = null;
		List<ColumnVo> list = new ArrayList<ColumnVo>();
		try {
			Class.forName(DbConfig.driver);
			DbReadTableUtil.conn = DriverManager.getConnection(DbConfig.url, DbConfig.username, DbConfig.password);
			DbReadTableUtil.statement = DbReadTableUtil.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (DbConfig.dbType.equals(DbConvertDef.MYSQL)) {
				s2 = MessageFormat.format(
						DbConvertDef.MYSQL_COLUMNS_SQL,
						StringBoolUtil.strToStr(tableName), StringBoolUtil.strToStr(DbConfig.dbName));
			}
			if (DbConfig.dbType.equals(DbConvertDef.ORACLE)) {
				s2 = MessageFormat.format(
						DbConvertDef.ORACLE_COLUMNS_SQL,
						StringBoolUtil.strToStr(tableName.toUpperCase()));
			}
			if (DbConfig.dbType.equals(DbConvertDef.POSTGRESQL)) {
				s2 = MessageFormat.format(
						DbConvertDef.POSTGRESQL_COLUMNS_SQL,
						StringBoolUtil.strToStr(tableName), StringBoolUtil.strToStr(tableName));
			}
			if (DbConfig.dbType.equals(DbConvertDef.SQLSERVER)) {
				s2 = MessageFormat.format(
						DbConvertDef.SQLSERVER_COLUMNS_SQL,
						StringBoolUtil.strToStr(tableName));
			}
			ResultSet executeQuery = DbReadTableUtil.statement.executeQuery(s2);
			executeQuery.last();
			if (executeQuery.getRow() <= 0) {
				throw new Exception("该表不存在或者表中没有字段");
			}
			ColumnVo columnVo = new ColumnVo();
			if (DbConfig.dbFiledConvert) {
				columnVo.setFieldName(humpConver(executeQuery.getString(1).toLowerCase()));
			} else {
				columnVo.setFieldName(executeQuery.getString(1).toLowerCase());
			}
			columnVo.setFieldDbName(executeQuery.getString(1).toUpperCase());
			columnVo.setPrecision(StringBoolUtil.isBlank(executeQuery.getString(4)));
			columnVo.setScale(StringBoolUtil.isBlank(executeQuery.getString(5)));
			columnVo.setCharmaxLength(StringBoolUtil.isBlank(executeQuery.getString(6)));
			columnVo.setNullable(StringBoolUtil.strBool(executeQuery.getString(7)));
			columnVo.setFieldType(
					jdbc2JavaType(executeQuery.getString(2).toLowerCase(), columnVo.getPrecision(), columnVo.getScale()));
			columnVo.setFieldDbType(humpConver(executeQuery.getString(2).toLowerCase()));
			setAttributes(columnVo);
			columnVo.setFiledComment(StringUtils.isBlank(executeQuery.getString(3)) ? columnVo.getFieldName()
					: executeQuery.getString(3));
			log.debug("columnt.getFieldName() -------------" + columnVo.getFieldName());
			list.add(columnVo);
			while (executeQuery.previous()) {
				ColumnVo columnVo2 = new ColumnVo();
				if (DbConfig.dbFiledConvert) {
					columnVo2.setFieldName(humpConver(executeQuery.getString(1).toLowerCase()));
				} else {
					columnVo2.setFieldName(executeQuery.getString(1).toLowerCase());
				}
				columnVo2.setFieldDbName(executeQuery.getString(1).toUpperCase());
				columnVo2.setPrecision(StringBoolUtil.isBlank(executeQuery.getString(4)));
				columnVo2.setScale(StringBoolUtil.strBool(executeQuery.getString(5)));
				columnVo2.setCharmaxLength(StringBoolUtil.isBlank(executeQuery.getString(6)));
				columnVo2.setNullable(StringBoolUtil.strBool(executeQuery.getString(7)));
				columnVo2.setFieldType(
						jdbc2JavaType(executeQuery.getString(2).toLowerCase(), columnVo2.getPrecision(), columnVo2.getScale()));
				columnVo2.setFieldDbType(humpConver(executeQuery.getString(2).toLowerCase()));
				setAttributes(columnVo2);
				columnVo2.setFiledComment(StringUtils.isBlank(executeQuery.getString(3)) ? columnVo2.getFieldName()
						: executeQuery.getString(3));
				list.add(columnVo2);
			}
			log.debug("读取表成功");
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		} finally {
			try {
				if (DbReadTableUtil.statement != null) {
					DbReadTableUtil.statement.close();
					DbReadTableUtil.statement = null;
					System.gc();
				}
				if (DbReadTableUtil.conn != null) {
					DbReadTableUtil.conn.close();
					DbReadTableUtil.conn = null;
					System.gc();
				}
			} catch (SQLException ex3) {
				throw ex3;
			}
		}
		List<ColumnVo> list2 = new ArrayList<ColumnVo>();
		for (int i = list.size() - 1; i >= 0; --i) {
			list2.add(list.get(i));
		}
		return list2;
	}

	public static boolean isTableExist(String tableName) {
		String sql = null;
		try {
			log.debug("数据库驱动: " + DbConfig.driver);
			Class.forName(DbConfig.driver);
			DbReadTableUtil.conn = DriverManager.getConnection(DbConfig.url, DbConfig.username, DbConfig.password);
			DbReadTableUtil.statement = DbReadTableUtil.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (DbConfig.dbType.equals(DbConvertDef.MYSQL)) {
				sql = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '"
						+ tableName + "' and table_schema = '" + DbConfig.dbName + "'";
			}
			if (DbConfig.dbType.equals(DbConvertDef.ORACLE)) {
				sql = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '"
						+ tableName.toUpperCase() + "'";
			}
			if (DbConfig.dbType.equals(DbConvertDef.POSTGRESQL)) {
				sql = MessageFormat.format(
						DbConvertDef.POSTGRESQL_COLUMNS_SQL,
						StringBoolUtil.strToStr(tableName), StringBoolUtil.strToStr(tableName));
			}
			if (DbConfig.dbType.equals(DbConvertDef.SQLSERVER)) {
				sql = MessageFormat.format(
						DbConvertDef.SQLSERVER_COLUMNS_SQL,
						StringBoolUtil.strToStr(tableName));
			}
			ResultSet executeQuery = DbReadTableUtil.statement.executeQuery(sql);
			executeQuery.last();
			if (executeQuery.getRow() > 0) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return false;
	}

	private static String humpConver(String s) {
		return humpConver(s, false);
	}

	public static String humpConver(String s, boolean upperInitials) {
		String[] split = s.split("_");
		s = "";
		for (int i = 0; i < split.length; ++i) {
			if (i > 0) {
				String lowerCase = split[i].toLowerCase();
				s += lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1, lowerCase.length());
			} else {
				s += split[i].toLowerCase();
			}
		}
		if (upperInitials) {
			s = s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return s;
	}

	private static void setAttributes(ColumnVo columnVo) {
		String fieldType = columnVo.getFieldType();
		String scale = columnVo.getScale();
		columnVo.setClassType("inputxt");
		if ("N".equals(columnVo.getNullable())) {
			columnVo.setOptionType("*");
		}
		if ("datetime".equals(fieldType) || fieldType.contains("time")) {
			columnVo.setClassType("easyui-datetimebox");
		} else if ("date".equals(fieldType)) {
			columnVo.setClassType("easyui-datebox");
		} else if (fieldType.contains("int")) {
			columnVo.setOptionType("n");
		} else if ("number".equals(fieldType)) {
			if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
				columnVo.setOptionType("d");
			}
		} else if ("float".equals(fieldType) || "double".equals(fieldType) || "decimal".equals(fieldType)) {
			columnVo.setOptionType("d");
		} else if ("numeric".equals(fieldType)) {
			columnVo.setOptionType("d");
		}
	}

	private static String jdbc2JavaType(String type, String precision, String scale) {
		if (type.contains("char")) {
			type = "java.lang.String";
		} else if (type.contains("int")) {
			type = "java.lang.Integer";
		} else if (type.contains("float")) {
			type = "java.lang.Float";
		} else if (type.contains("double")) {
			type = "java.lang.Double";
		} else if (type.contains("number")) {
			if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
				type = "java.math.BigDecimal";
			} else if (StringUtils.isNotBlank(precision) && Integer.parseInt(precision) > 10) {
				type = "java.lang.Long";
			} else {
				type = "java.lang.Integer";
			}
		} else if (type.contains("decimal")) {
			type = "java.math.BigDecimal";
		} else if (type.contains("date")) {
			type = "java.util.Date";
		} else if (type.contains("time")) {
			type = "java.util.Date";
		} else if (type.contains("blob")) {
			type = "byte[]";
		} else if (type.contains("clob")) {
			type = "java.sql.Clob";
		} else if (type.contains("numeric")) {
			type = "java.math.BigDecimal";
		} else {
			type = "java.lang.Object";
		}
		return type;
	}

}
