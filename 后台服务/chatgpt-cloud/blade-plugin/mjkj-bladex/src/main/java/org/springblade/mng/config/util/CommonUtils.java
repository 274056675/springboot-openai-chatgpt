package org.springblade.mng.config.util;


import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.config.constant.MjkjConstant;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Slf4j
public class CommonUtils {


    /** 当前系统数据库类型 */
    private static String DB_TYPE = "";
    public static String getDatabaseType() {
        if(Func.isNotEmpty(DB_TYPE)){
            return DB_TYPE;
        }
        DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
        try {
            return getDatabaseTypeByDataSource(dataSource);
        } catch (SQLException e) {
            //e.printStackTrace();
            log.warn(e.getMessage());
            return "";
        }
    }

    /**
     * 获取数据库类型
     * @param dataSource
     * @return
     * @throws SQLException
     */
    private static String getDatabaseTypeByDataSource(DataSource dataSource) throws SQLException{
        if("".equals(DB_TYPE)) {
            Connection connection = dataSource.getConnection();
            try {
                DatabaseMetaData md = connection.getMetaData();
                String dbType = md.getDatabaseProductName().toLowerCase();
                if(dbType.indexOf("mysql")>=0) {
                    DB_TYPE = MjkjConstant.DB_TYPE_MYSQL;
                }else if(dbType.indexOf("oracle")>=0) {
                    DB_TYPE = MjkjConstant.DB_TYPE_ORACLE;
                }else if(dbType.indexOf("sqlserver")>=0||dbType.indexOf("sql server")>=0) {
                    DB_TYPE = MjkjConstant.DB_TYPE_SQLSERVER;
                }else if(dbType.indexOf("postgresql")>=0) {
                    DB_TYPE = MjkjConstant.DB_TYPE_POSTGRESQL;
                }else {
                    throw new Exception("数据库类型:["+dbType+"]不识别!");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }finally {
                connection.close();
            }
        }
        return DB_TYPE;

    }
}
