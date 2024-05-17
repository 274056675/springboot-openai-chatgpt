package org.springblade.mng.config.service.impl;

import org.springblade.mng.config.service.DbTableHandleI;
import org.springblade.mng.config.util.ColumnMeta;
import org.apache.commons.lang.StringUtils;


public class MysqlTableHandle implements DbTableHandleI {
    public MysqlTableHandle() {
    }

    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD COLUMN " + this.a(columnMeta) + ";";
    }

    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "CHANGE COLUMN " + columnMeta.getOldColumnName() + " " + this.b(columnMeta) + " ;";
    }

    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return " MODIFY COLUMN " + this.b(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String classType = "";
        if (dataType.equalsIgnoreCase("varchar")) {
            classType = "string";
        } else if (dataType.equalsIgnoreCase("double")) {
            classType = "double";
        } else if (dataType.equalsIgnoreCase("int")) {
            classType = "int";
        } else if (dataType.equalsIgnoreCase("bigint")) {
            classType = "bigint";
        } else if (dataType.equalsIgnoreCase("Date")) {
            classType = "date";
        } else if (dataType.equalsIgnoreCase("Time")) {
			classType = "date";
		} else if (dataType.equalsIgnoreCase("Datetime")) {
            classType = "date";
        } else if (dataType.equalsIgnoreCase("decimal")) {
            classType = "bigdecimal";
        } else if (dataType.equalsIgnoreCase("text")) {
            classType = "text";
        } else if (dataType.equalsIgnoreCase("blob")) {
            classType = "blob";
        }

        return classType;
    }

    public String dropTableSQL(String tableName) {
        return " DROP TABLE IF EXISTS " + tableName + " ;";
    }

    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private String a(ColumnMeta meta, ColumnMeta meta2) {
        String var3 = "";
        if (meta.getColunmType().equalsIgnoreCase("string")) {
            var3 = meta.getColumnName() + " varchar(" + meta.getColumnSize() + ") " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (meta.getColunmType().equalsIgnoreCase("date")) {
            var3 = meta.getColumnName() + " date " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (meta.getColunmType().equalsIgnoreCase("datetime")) {
			var3 = meta.getColumnName() + " datetime " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (meta.getColunmType().equalsIgnoreCase("time")) {
			var3 = meta.getColumnName() + " time " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (meta.getColunmType().equalsIgnoreCase("int")) {
            var3 = meta.getColumnName() + " int(" + meta.getColumnSize() + ") " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (meta.getColunmType().equalsIgnoreCase("bigint")) {
            var3 = meta.getColumnName() + " bigint(" + meta.getColumnSize() + ") " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (meta.getColunmType().equalsIgnoreCase("double")) {
            var3 = meta.getColumnName() + " double(" + meta.getColumnSize() + "," + meta.getDecimalDigits() + ") " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (meta.getColunmType().equalsIgnoreCase("bigdecimal")) {
            var3 = meta.getColumnName() + " decimal(" + meta.getColumnSize() + "," + meta.getDecimalDigits() + ") " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (meta.getColunmType().equalsIgnoreCase("text")) {
            var3 = meta.getColumnName() + " text " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (meta.getColunmType().equalsIgnoreCase("blob")) {
            var3 = meta.getColumnName() + " blob " + ("Y".equals(meta.getIsNullable()) ? "NULL" : "NOT NULL");
        }

        var3 = var3 + (StringUtils.isNotEmpty(meta.getComment()) ? " COMMENT '" + meta.getComment() + "'" : " ");
        var3 = var3 + (StringUtils.isNotEmpty(meta.getFieldDefault()) ? " DEFAULT " + meta.getFieldDefault() : " ");
        String var4 = meta.getPkType();
        if ("id".equalsIgnoreCase(meta.getColumnName()) && var4 != null && ("SEQUENCE".equalsIgnoreCase(var4) || "NATIVE".equalsIgnoreCase(var4))) {
            var3 = var3 + " AUTO_INCREMENT ";
        }

        return var3;
    }

    private String b(ColumnMeta meta1, ColumnMeta meta2) {
        String var3 = this.a(meta1, meta2);
        return var3;
    }

    private String a(ColumnMeta meta) {
        String var2 = this.a(meta, (ColumnMeta)null);
        return var2;
    }

    private String b(ColumnMeta meta) {
        String var2 = this.a(meta, (ColumnMeta)null);
        return var2;
    }

    public String getCommentSql(ColumnMeta columnMeta) {
        return "";
    }

    public String getSpecialHandle(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return null;
    }

    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName + " ON " + tableName;
    }

    public String countIndex(String indexName, String tableName) {
        return "select COUNT(*) from information_schema.statistics where table_name = '" + tableName + "'  AND index_name = '" + indexName + "'";
    }
}
