package org.springblade.mng.config.util;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springblade.mng.config.constant.MjkjConstant;
import org.springblade.mng.config.db.DataBaseConfig;
import org.springblade.mng.config.db.TableModel;
import org.springblade.mng.config.exception.DBException;
import org.springblade.mng.config.service.DbTableHandleI;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.core.tool.utils.Func;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@Slf4j
public class SqlHelper {
    private static DbTableHandleI dbTableHandle;

    public SqlHelper() throws SQLException, DBException {
    	dbTableHandle = TableUtil.getTableHandle();
    }

    /**
     * 创建表
     * @param tableModel
     * @throws IOException
     * @throws TemplateException
     * @throws HibernateException
     * @throws SQLException
     * @throws DBException
     */
    public static void createTable(TableModel tableModel) throws IOException, TemplateException, HibernateException, SQLException, DBException {
        String databaseType = TableUtil.getDatabaseType();
        if (MjkjConstant.DB_TYPE_ORACLE.equals(databaseType)) {//oracle数据库
            List<CgformField> fieldList = new ArrayList<>();

            CgformField field;
            for(Iterator<CgformField> iterator = tableModel.getColumns().iterator(); iterator.hasNext(); fieldList.add(field)) {
            	field = iterator.next();
                if (DbType.INT.equals(field.getDbType())) {
                	field.setDbType(DbType.DOUBLE);
                	field.setDbPointLength(0);
                }
            }

            tableModel.setColumns(fieldList);
        }

        String xml = FreemarkerHelper.process("org/springblade/config/util/engine/tableTemplate.ftl", getTableData(tableModel, databaseType));
        log.info(xml);

        DataBaseConfig dbConfig = tableModel.getDbConfig();


		Configuration configuration = new Configuration().configure();
		Properties properties = configuration.getProperties();
		properties.put("hibernate.connection.driver_class", dbConfig.getDriverClassName());
		properties.put("hibernate.connection.url", dbConfig.getUrl());
		properties.put("hibernate.connection.username", dbConfig.getUsername());
		properties.put("hibernate.connection.password", dbConfig.getPassword());
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.format_sql", true);
		properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
		properties.put("hibernate.dialect",TableUtil.getJdbcDriver(databaseType));
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.connection.autocommit", false);
		properties.put("hibernate.current_session_context_class", "thread");


		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();


        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
        metadataSources.addInputStream(input);
        Metadata metadata = metadataSources.buildMetadata();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.create(EnumSet.of(TargetType.DATABASE), metadata);
        input.close();
        List<Exception> exceptions = schemaExport.getExceptions();
        Iterator<Exception> exceptionIterator = exceptions.iterator();

        Exception exception;
        while(true) {
            if (!exceptionIterator.hasNext()) {
                return;
            }
            exception = exceptionIterator.next();
            if ("java.sql.SQLSyntaxErrorException".equals(exception.getCause().getClass().getName())) {
                SQLSyntaxErrorException e = (SQLSyntaxErrorException)exception.getCause();
                if ("42000".equals(e.getSQLState())) {
                    continue;
                }
                break;
            } else {
                if (!"com.microsoft.sqlserver.jdbc.SQLServerException".equals(exception.getCause().getClass().getName())) {
                    break;
                }

                if (exception.getCause().toString().indexOf("Incorrect syntax near the keyword") != -1) {
                	exception.printStackTrace();
                    throw new DBException(exception.getCause().getMessage());
                }

                log.error(exception.getMessage());
            }
        }
        throw new DBException(exception.getMessage());
    }

    /**
     * 修改表
     * @param table
     * @return
     * @throws DBException
     * @throws SQLException
     */
    public List<String> getUpdateTableSql(TableModel table) throws DBException, SQLException {
        String databaseType =TableUtil.getDatabaseType();
        String tableName = TableUtil.fixTableName(table.getTableName(), databaseType);
        String sql = "alter table  " + tableName + " ";
        List<String> updateTableSql = new ArrayList<>();

        try {
            Map var6 = this.getColumnMetaMap(null, tableName);
            Map var7 = this.tableModel2MetaMap(table);
            Map var8 = this.fieldList2Map(table.getColumns());
            Iterator var9 = var7.keySet().iterator();

            label72:
            while(true) {
                while(true) {
                    String var10;
                    while(var9.hasNext()) {
                        var10 = (String)var9.next();
                        ColumnMeta var11;
                        if (!var6.containsKey(var10)) {
                            var11 = (ColumnMeta)var7.get(var10);
                            String var17 = (String)var8.get(var10);
                            if (var8.containsKey(var10) && var6.containsKey(var17)) {
                                ColumnMeta var13 = (ColumnMeta)var6.get(var17);
                                String var14 = dbTableHandle.getReNameFieldName(var11);
                                if (MjkjConstant.DB_TYPE_SQLSERVER.equals(databaseType)) {
                                	updateTableSql.add(var14);
                                } else {
                                	updateTableSql.add(sql + var14);
                                }

                                String var15 = this.getUpdateOnlCgformFieldSql(var10, var11.getColumnId());
                                updateTableSql.add(var15);
                                if (!var13.equals(var11)) {
                                	updateTableSql.add(sql + this.getUpdateColumnSql(var11, var13));
                                    if (MjkjConstant.DB_TYPE_POSTGRESQL.equals(databaseType)) {
                                    	updateTableSql.add(sql + this.getSpecialHandle(var11, var13));
                                    }
                                }

                                if (!MjkjConstant.DB_TYPE_SQLSERVER.equals(databaseType) && !var13.b(var11)) {
                                	updateTableSql.add(this.getCommentSql(var11));
                                }
                            } else {
                            	updateTableSql.add(sql + this.getAddColumnSql(var11));
                                if (!MjkjConstant.DB_TYPE_SQLSERVER.equals(databaseType) && StringUtils.isNotEmpty(var11.getComment())) {
                                	updateTableSql.add(this.getCommentSql(var11));
                                }
                            }
                        } else {
                            var11 = (ColumnMeta)var6.get(var10);
                            ColumnMeta var12 = (ColumnMeta)var7.get(var10);
                            if (!var11.a(var12, databaseType)) {
                            	updateTableSql.add(sql + this.getUpdateColumnSql(var12, var11));
                            }

                            if (!MjkjConstant.DB_TYPE_SQLSERVER.equals(databaseType) && !MjkjConstant.DB_TYPE_ORACLE.equals(databaseType) && !var11.b(var12)) {
                            	updateTableSql.add(this.getCommentSql(var12));
                            }
                        }
                    }

                    var9 = var6.keySet().iterator();

                    while(var9.hasNext()) {
                        var10 = (String)var9.next();
                        if (!var7.containsKey(var10.toLowerCase()) && !var8.containsValue(var10.toLowerCase())) {
                        	updateTableSql.add(sql + this.getDropColumnSql(var10));
                        }
                    }
                    break label72;
                }
            }
        } catch (SQLException var16) {
            throw new RuntimeException();
        }

        log.info(" db update sql : " + updateTableSql.toString());
        return updateTableSql;
    }

    private static Map<String, Object> getTableData(TableModel tableModel, String dataType) {
        HashMap map = new HashMap();
        Iterator iterator = tableModel.getColumns().iterator();

        while(iterator.hasNext()) {
            CgformField field = (CgformField)iterator.next();
            field.setDbDefaultVal(c(field.getDbDefaultVal()));
        }

        map.put("entity", tableModel);
        map.put("dataType", dataType);
        return map;
    }

    private Map<String, ColumnMeta> getColumnMetaMap(String schema, String tableName) throws SQLException {
        HashMap resultMap = new HashMap();
        Connection connection = null;

        try {
            connection = TableUtil.getConnection();
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet result = metaData.getColumns(
			connection.getCatalog(), connection.getSchema(),  tableName, null);
			while(result.next()){
			String columnName = result.getString(4);
			int    columnType = result.getInt(5);
		}

    ;
        ResultSet set = metaData.getColumns(connection.getCatalog(), schema, tableName, "%");
        while(set.next()) {
            ColumnMeta meta = new ColumnMeta();
            meta.setTableName(tableName);
            String columnName = set.getString("COLUMN_NAME").toLowerCase();
            meta.setColumnName(columnName);
            String typeName = set.getString("TYPE_NAME");
            int decimalDigits = set.getInt("DECIMAL_DIGITS");
            String matchClassType = dbTableHandle.getMatchClassTypeByDataType(typeName, decimalDigits);
            meta.setColunmType(matchClassType);
            int columnSize = set.getInt("COLUMN_SIZE");
            meta.setColumnSize(columnSize);
            meta.setDecimalDigits(decimalDigits);
            String isNullable = set.getInt("NULLABLE") == 1 ? "Y" : "N";
            meta.setIsNullable(isNullable);
            String remarks = set.getString("REMARKS");
            meta.setComment(remarks);
            String columnDef = set.getString("COLUMN_DEF");
            String fieldDefault = c(columnDef) == null ? "" : c(columnDef);
            meta.setFieldDefault(fieldDefault);
            log.info("getColumnMetadataFormDataBase --->COLUMN_NAME:" + columnName.toUpperCase() + " TYPE_NAME :" + typeName + " DECIMAL_DIGITS:" + decimalDigits + " COLUMN_SIZE:" + columnSize);
            resultMap.put(columnName, meta);
        }

        return resultMap;
    }

    private Map<String, ColumnMeta> tableModel2MetaMap(TableModel model) {
        HashMap resultMap = new HashMap();
        List<CgformField> list = model.getColumns();
        Iterator<CgformField> iterator = list.iterator();

        while(iterator.hasNext()) {
            CgformField field = iterator.next();
            ColumnMeta meta = new ColumnMeta();
            meta.setTableName(model.getTableName().toLowerCase());
            meta.setColumnId(Func.toStr(field.getId()));
            meta.setColumnName(field.getDbFieldName().toLowerCase());
            meta.setColumnSize(field.getDbLength());
            meta.setColunmType(field.getDbType().toLowerCase());
            meta.setIsNullable(field.getDbIsNull() == 1 ? "Y" : "N");
            meta.setComment(field.getDbFieldTxt());
            meta.setDecimalDigits(field.getDbPointLength());
            meta.setFieldDefault(c(field.getDbDefaultVal()));
            meta.setPkType(model.getJformPkType() == null ? "UUID" : model.getJformPkType());
            meta.setOldColumnName(field.getDbFieldNameOld() != null ? field.getDbFieldNameOld().toLowerCase() : null);
            log.info("getColumnMetadataFormCgForm ----> DbFieldName: " + field.getDbFieldName().toLowerCase() + " | DbType: " + field.getDbType().toLowerCase() + " | DbPointLength:" + field.getDbPointLength() + " | DbLength:" + field.getDbLength());
            resultMap.put(field.getDbFieldName().toLowerCase(), meta);
        }

        return resultMap;
    }

    private Map<String, String> fieldList2Map(List<CgformField> fieldList) {
        HashMap resultMap = new HashMap();
        Iterator<CgformField> iterator = fieldList.iterator();
        while(iterator.hasNext()) {
            CgformField field = iterator.next();
            resultMap.put(field.getDbFieldName(), field.getDbFieldNameOld());
        }

        return resultMap;
    }

    private String getDropColumnSql(String fieldName) {
        return dbTableHandle.getDropColumnSql(fieldName);
    }

    private String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) throws DBException {
        return dbTableHandle.getUpdateColumnSql(cgformcolumnMeta, datacolumnMeta);
    }

    private String getSpecialHandle(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return dbTableHandle.getSpecialHandle(cgformcolumnMeta, datacolumnMeta);
    }

    private String getReNameFieldName(ColumnMeta columnMeta) {
        return dbTableHandle.getReNameFieldName(columnMeta);
    }

    private String getAddColumnSql(ColumnMeta columnMeta) {
        return dbTableHandle.getAddColumnSql(columnMeta);
    }

    private String getCommentSql(ColumnMeta columnMeta) {
        return dbTableHandle.getCommentSql(columnMeta);
    }

    private String getUpdateOnlCgformFieldSql(String oldDbFieldName, String id) {
        return "update onl_cgform_field set DB_FIELD_NAME_OLD = '" + oldDbFieldName + "' where ID ='" + id + "'";
    }

    private int updateOldDbFieldName(String oldDbFieldName, String id, Session session) {
        return session.createSQLQuery("update onl_cgform_field set DB_FIELD_NAME_OLD= '" + oldDbFieldName + "' where ID ='" + id + "'").executeUpdate();
    }

    private static String c(String str) {
        if (StringUtils.isNotEmpty(str)) {
            try {
                Double.valueOf(str);
            } catch (Exception var2) {
                if (!str.startsWith("'") || !str.endsWith("'")) {
                    str = "'" + str + "'";
                }
            }
        }

        return str;
    }

    public String getDropIndexsSql(String indexName, String tableName) {
        return dbTableHandle.dropIndexs(indexName, tableName);
    }

    public String getCountIndexSql(String indexName, String tableName) {
        return dbTableHandle.countIndex(indexName, tableName);
    }

    public static List<String> getIndexes(String table) throws SQLException {
        Connection conn = null;
        ResultSet indexInfo = null;
        ArrayList<String> indexes = new ArrayList<>();

        try {
        	conn = TableUtil.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            indexInfo = metaData.getIndexInfo(null, null, table, false, false);
            indexInfo.getMetaData();

            while(indexInfo.next()) {
                String indexName = indexInfo.getString("INDEX_NAME");
                if (ConvertUtils.isEmpty(indexName)) {
                	indexName = indexInfo.getString("index_name");
                }

                if (ConvertUtils.isNotEmpty(indexName)) {
                    indexes.add(indexName);
                }
            }
        } catch (SQLException e) {
        	log.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
            	conn.close();
            }

        }

        return indexes;
    }
}
