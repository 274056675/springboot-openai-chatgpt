package org.springblade.mng.config.service.impl;


import org.springblade.mng.config.exception.DBException;
import org.springblade.mng.config.service.DbTableHandleI;

import org.apache.commons.lang.StringUtils;
import org.springblade.mng.config.util.ColumnMeta;


public class PgTableHandle implements DbTableHandleI {
    public PgTableHandle() {
    }

    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD COLUMN " + this.a(columnMeta) + ";";
    }

    public String getReNameFieldName(ColumnMeta columnMeta) {
        return " RENAME  COLUMN  " + columnMeta.getOldColumnName() + " to " + columnMeta.getColumnName() + ";";
    }

    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) throws DBException {
        return "  ALTER  COLUMN   " + this.a(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    public String getSpecialHandle(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return "  ALTER  COLUMN   " + this.b(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String var3 = "";
        if (dataType.equalsIgnoreCase("varchar")) {
            var3 = "string";
        } else if (dataType.equalsIgnoreCase("double")) {
            var3 = "double";
        } else if (dataType.contains("int")) {
            var3 = "int";
        }  else if (dataType.contains("bigint")) {
            var3 = "bigint";
        } else if (dataType.equalsIgnoreCase("Date")) {
            var3 = "date";
        } else if (dataType.equalsIgnoreCase("timestamp")) {
            var3 = "date";
        } else if (dataType.equalsIgnoreCase("bytea")) {
            var3 = "blob";
        } else if (dataType.equalsIgnoreCase("text")) {
            var3 = "text";
        } else if (dataType.equalsIgnoreCase("decimal")) {
            var3 = "bigdecimal";
        } else if (dataType.equalsIgnoreCase("numeric")) {
            var3 = "bigdecimal";
        }

        return var3;
    }

    public String dropTableSQL(String tableName) {
        return " DROP TABLE  " + tableName + " ;";
    }

    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private String a(ColumnMeta var1, ColumnMeta var2) throws DBException {
        String var3 = "";
        if (var1.getColunmType().equalsIgnoreCase("string")) {
            var3 = var1.getColumnName() + "  type character varying(" + var1.getColumnSize() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("date")) {
            var3 = var1.getColumnName() + "  type timestamp ";
        } else if (var1.getColunmType().equalsIgnoreCase("int")) {
            var3 = var1.getColumnName() + " type int4 ";
        } else if (var1.getColunmType().equalsIgnoreCase("bigint")) {
            var3 = var1.getColumnName() + " type int8 ";
        } else if (var1.getColunmType().equalsIgnoreCase("double")) {
            var3 = var1.getColumnName() + " type  numeric(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("BigDecimal")) {
            var3 = var1.getColumnName() + " type  decimal(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("text")) {
            var3 = var1.getColumnName() + " text ";
        } else if (var1.getColunmType().equalsIgnoreCase("blob")) {
            throw new DBException("blob类型不可修改");
        }

        return var3;
    }

    private String b(ColumnMeta var1, ColumnMeta var2) {
        String var3 = "";
        if (!var1.a(var2)) {
            if (var1.getColunmType().equalsIgnoreCase("string")) {
                var3 = var1.getColumnName();
                var3 = var3 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " SET DEFAULT " + var1.getFieldDefault() : " DROP DEFAULT");
            } else if (var1.getColunmType().equalsIgnoreCase("date")) {
                var3 = var1.getColumnName();
                var3 = var3 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " SET DEFAULT " + var1.getFieldDefault() : " DROP DEFAULT");
            } else if (var1.getColunmType().equalsIgnoreCase("int")) {
                var3 = var1.getColumnName();
                var3 = var3 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " SET DEFAULT " + var1.getFieldDefault() : " DROP DEFAULT");
            } else if (var1.getColunmType().equalsIgnoreCase("double")) {
                var3 = var1.getColumnName();
                var3 = var3 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " SET DEFAULT " + var1.getFieldDefault() : " DROP DEFAULT");
            } else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
                var3 = var1.getColumnName();
                var3 = var3 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " SET DEFAULT " + var1.getFieldDefault() : " DROP DEFAULT");
            } else if (var1.getColunmType().equalsIgnoreCase("text")) {
                var3 = var1.getColumnName();
                var3 = var3 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " SET DEFAULT " + var1.getFieldDefault() : " DROP DEFAULT");
            }
        }

        return var3;
    }

    private String a(ColumnMeta var1) {
        String var2 = "";
        if (var1.getColunmType().equalsIgnoreCase("string")) {
            var2 = var1.getColumnName() + " character varying(" + var1.getColumnSize() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("date")) {
            var2 = var1.getColumnName() + " timestamp ";
        } else if (var1.getColunmType().equalsIgnoreCase("int")) {
            var2 = var1.getColumnName() + " int4";
        } else if (var1.getColunmType().equalsIgnoreCase("double")) {
            var2 = var1.getColumnName() + " numeric(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
            var2 = var1.getColumnName() + " decimal(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("blob")) {
            var2 = var1.getColumnName() + " bytea(" + var1.getColumnSize() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("text")) {
            var2 = var1.getColumnName() + " text ";
        }

        var2 = var2 + (StringUtils.isNotEmpty(var1.getFieldDefault()) ? " DEFAULT " + var1.getFieldDefault() : " ");
        return var2;
    }

    private String b(ColumnMeta var1) {
        String var2 = "";
        if (var1.getColunmType().equalsIgnoreCase("string")) {
            var2 = var1.getColumnName() + " character varying(" + var1.getColumnSize() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("date")) {
            var2 = var1.getColumnName() + " datetime ";
        } else if (var1.getColunmType().equalsIgnoreCase("int")) {
            var2 = var1.getColumnName() + " int(" + var1.getColumnSize() + ") ";
        } else if (var1.getColunmType().equalsIgnoreCase("double")) {
            var2 = var1.getColumnName() + " numeric(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") ";
        }

        return var2;
    }

    public String getCommentSql(ColumnMeta columnMeta) {
        return "COMMENT ON COLUMN " + columnMeta.getTableName() + "." + columnMeta.getColumnName() + " IS '" + columnMeta.getComment() + "'";
    }

    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName;
    }

    public String countIndex(String indexName, String tableName) {
        return "SELECT count(*) FROM pg_indexes WHERE indexname = '" + indexName + "' and tablename = '" + tableName + "'";
    }
}
