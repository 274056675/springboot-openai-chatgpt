package org.springblade.mng.config.util;

import org.springblade.mng.config.constant.MjkjConstant;
import org.springblade.mng.config.exception.DBException;
import org.springblade.mng.config.service.DbTableHandleI;
import org.springblade.mng.config.service.impl.MysqlTableHandle;
import org.springblade.mng.config.service.impl.OracleTableHandle;
import org.springblade.mng.config.service.impl.PgTableHandle;
import org.springblade.mng.config.service.impl.SqlServerTableHandle;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TableUtil {
    public static String DATABASE_TYPE = "";

    public TableUtil() {
    }

    /**
     * 获取不同类型的数据库
     * @return
     * @throws SQLException
     * @throws DBException
     */
    public static DbTableHandleI getTableHandle() throws SQLException, DBException {
    	DbTableHandleI dbTableHandle = null;
        String databaseType = getDatabaseType();

        switch(databaseType) {
            case MjkjConstant.DB_TYPE_ORACLE:
            	dbTableHandle = new OracleTableHandle();
                break;
            case MjkjConstant.DB_TYPE_POSTGRESQL:
            	dbTableHandle = new PgTableHandle();
                break;
            case MjkjConstant.DB_TYPE_MYSQL:
            	dbTableHandle = new MysqlTableHandle();
                break;
            case MjkjConstant.DB_TYPE_SQLSERVER:
            	dbTableHandle = new SqlServerTableHandle();
        }

        return dbTableHandle;
    }

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        DataSource ds = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
        return ds.getConnection();
    }

    /**
     * 获取数据库类型
     * @return
     * @throws SQLException
     * @throws DBException
     */
    public static String getDatabaseType() throws SQLException, DBException {
        if (ConvertUtils.isNotEmpty(DATABASE_TYPE)) {
            return DATABASE_TYPE;
        } else {
            DataSource ds = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
            return getDatabaseType(ds);
        }
    }

    /**
     * 是否是oracle数据库
     * @return
     */
    public static boolean isOracle() {
        try {
            return MjkjConstant.DB_TYPE_ORACLE.equals(getDatabaseType());
        } catch (SQLException | DBException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取数据库类型
     * @param ds
     * @return
     * @throws SQLException
     * @throws DBException
     */
    public static String getDatabaseType(DataSource ds) throws SQLException, DBException {
        if ("".equals(DATABASE_TYPE)) {
            Connection conn = ds.getConnection();

            try {
                DatabaseMetaData dbMetaData = conn.getMetaData();
                String dbProductName = dbMetaData.getDatabaseProductName().toLowerCase();
                if (dbProductName.indexOf("mysql") >= 0) {
                	DATABASE_TYPE = MjkjConstant.DB_TYPE_MYSQL;
                } else if (dbProductName.indexOf("oracle") >= 0) {
                	DATABASE_TYPE = MjkjConstant.DB_TYPE_ORACLE;
                } else if (dbProductName.indexOf("sqlserver") < 0 && dbProductName.indexOf("sql server") < 0) {
                    if (dbProductName.indexOf("postgresql") < 0) {
                        throw new DBException("数据库类型:[" + dbProductName + "]不识别!");
                    }

                    DATABASE_TYPE = MjkjConstant.DB_TYPE_POSTGRESQL;
                } else {
                	DATABASE_TYPE = MjkjConstant.DB_TYPE_SQLSERVER;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
            	conn.close();
            }
        }

        return DATABASE_TYPE;
    }

    /**
     * 获取数据库类型
     * @param conn
     * @return
     * @throws SQLException
     * @throws DBException
     */
    public static String getDatabaseType(Connection conn) throws SQLException, DBException {
        if ("".equals(DATABASE_TYPE)) {
            DatabaseMetaData var1 = conn.getMetaData();
            String type = var1.getDatabaseProductName().toLowerCase();
            if (type.indexOf("mysql") >= 0) {
            	DATABASE_TYPE = MjkjConstant.DB_TYPE_MYSQL;
            } else if (type.indexOf("oracle") >= 0) {
            	DATABASE_TYPE = MjkjConstant.DB_TYPE_ORACLE;
            } else if (type.indexOf("sqlserver") < 0 && type.indexOf("sql server") < 0) {
                if (type.indexOf("postgresql") < 0) {
                    throw new DBException("数据库类型:[" + type + "]不识别!");
                }

                DATABASE_TYPE = MjkjConstant.DB_TYPE_POSTGRESQL;
            } else {
            	DATABASE_TYPE = MjkjConstant.DB_TYPE_SQLSERVER;
            }
        }

        return DATABASE_TYPE;
    }

    /**
     * 获取表名称
     * @param tableName
     * @param databaseType
     * @return
     */
    public static String fixTableName(String tableName, String databaseType) {
        switch(databaseType) {
            case MjkjConstant.DB_TYPE_ORACLE:
            	return tableName.toUpperCase();
            case MjkjConstant.DB_TYPE_POSTGRESQL:
            	return tableName.toLowerCase();
            default:
            	return tableName;
        }
    }

    //获取所有表
	public static List<Map<String,String>> getDatabaseAlTable() {
		Connection conn = null;
		ResultSet rs = null;
		List<Map<String,String>> resultList=new ArrayList<>();
		try {
			String[] tables = new String[]{"TABLE"};
			conn = getConnection();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			rs = databaseMetaData.getTables(conn.getCatalog(), null, null, tables);
			while(rs.next()) {
				Map<String,String> dataMap=new HashMap<>();
				dataMap.put("tableName",rs.getString("TABLE_NAME"));
				dataMap.put("remarks",rs.getString("REMARKS"));
				resultList.add(dataMap);

			}
			return resultList;
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}

		}
	}



    public static String getDialect() throws SQLException, DBException {
        String databaseType = getDatabaseType();
        return getJdbcDriver(databaseType);
    }

    public static String getJdbcDriver(String databaseType) throws SQLException, DBException {
        String driver = "org.hibernate.dialect.MySQL5InnoDBDialect";
        switch(databaseType) {
            case MjkjConstant.DB_TYPE_ORACLE:
            	driver = "org.hibernate.dialect.OracleDialect";
                break;
            case MjkjConstant.DB_TYPE_POSTGRESQL:
            	driver = "org.hibernate.dialect.PostgreSQLDialect";
                break;
            case MjkjConstant.DB_TYPE_SQLSERVER:
            	driver = "org.hibernate.dialect.SQLServerDialect";
        }
        return driver;
    }

}
