package org.springblade.mng.config.service;



import org.springblade.mng.config.exception.DBException;
import org.springblade.mng.config.util.ColumnMeta;


public interface DbTableHandleI {
    //新增字段接口
    String getAddColumnSql(ColumnMeta columnMeta);
    //修改字段名称
    String getReNameFieldName(ColumnMeta columnMeta);
    //修改字段属性
    String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) throws DBException;

    String getMatchClassTypeByDataType(String var1, int var2);

    //删除表
    String dropTableSQL(String tableName);
    //删除某一个字段
    String getDropColumnSql(String fieldName);

    String getCommentSql(ColumnMeta columnMeta);

    String getSpecialHandle(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta);

    String dropIndexs(String indexName, String tableName);

    String countIndex(String indexName, String tableName);
}
