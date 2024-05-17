package org.springblade.mng.config.service.impl;


import org.springblade.mng.config.service.DbTableHandleI;
import org.springblade.mng.config.util.ColumnMeta;
import org.springblade.mng.config.util.ConvertUtils;

public class SqlServerTableHandle implements DbTableHandleI {
    public SqlServerTableHandle() {
    }

    public String getAddColumnSql(ColumnMeta columnMeta) {
        return " ADD  " + this.getSql(columnMeta) + ";";
    }

    public String getReNameFieldName(ColumnMeta columnMeta) {
        return "  sp_rename '" + columnMeta.getTableName() + "." + columnMeta.getOldColumnName() + "', '" + columnMeta.getColumnName() + "', 'COLUMN';";
    }

    public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return " ALTER COLUMN  " + this.getSql(cgformcolumnMeta, datacolumnMeta) + ";";
    }

    public String getMatchClassTypeByDataType(String dataType, int digits) {
        String var3 = "";
        if (!dataType.equalsIgnoreCase("varchar") && !dataType.equalsIgnoreCase("nvarchar")) {
            if (dataType.equalsIgnoreCase("float")) {
                var3 = "double";
            } else if (dataType.equalsIgnoreCase("int")) {
                var3 = "int";
            }  else if (dataType.equalsIgnoreCase("bigint")) {
                var3 = "bigint";
            } else if (dataType.equalsIgnoreCase("Date")) {
                var3 = "date";
            } else if (dataType.equalsIgnoreCase("DateTime")) {
                var3 = "date";
            } else if (dataType.equalsIgnoreCase("Time")) {
				var3 = "date";
			} else if (dataType.equalsIgnoreCase("numeric")) {
                var3 = "bigdecimal";
            } else if (!dataType.equalsIgnoreCase("varbinary") && !dataType.equalsIgnoreCase("image")) {
                if (dataType.equalsIgnoreCase("text") || dataType.equalsIgnoreCase("ntext")) {
                    var3 = "text";
                }
            } else {
                var3 = "blob";
            }
        } else {
            var3 = "string";
        }

        return var3;
    }

    public String dropTableSQL(String tableName) {
        return " DROP TABLE " + tableName + " ;";
    }

    public String getDropColumnSql(String fieldName) {
        return " DROP COLUMN " + fieldName + ";";
    }

    private String getSql(ColumnMeta var1, ColumnMeta var2) {
        String sql = "";
        if (var1.getColunmType().equalsIgnoreCase("string")) {
			sql = var1.getColumnName() + " varchar(" + var1.getColumnSize() + ") " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("date")) {
			sql = var1.getColumnName() + " date " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("datetime")) {
			sql = var1.getColumnName() + " datetime " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("time")) {
			sql = var1.getColumnName() + " time " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
			sql = var1.getColumnName() + " int " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("bigint")) {
			sql = var1.getColumnName() + " bigint " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("double")) {
			sql = var1.getColumnName() + " float " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
			sql = var1.getColumnName() + " numeric(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("text")) {
			sql = var1.getColumnName() + " ntext " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("blob")) {
			sql = var1.getColumnName() + " image";
        }

        return sql;
    }

    private String getSql(ColumnMeta var1) {
        String sql = "";
        if (var1.getColunmType().equalsIgnoreCase("string")) {
			sql = var1.getColumnName() + " varchar(" + var1.getColumnSize() + ") " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("date")) {
			sql = var1.getColumnName() + " date " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("datetime")) {
			sql = var1.getColumnName() + " datetime " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("time")) {
			sql = var1.getColumnName() + " time " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
			sql = var1.getColumnName() + " int " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("double")) {
			sql = var1.getColumnName() + " float " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("bigdecimal")) {
			sql = var1.getColumnName() + " numeric(" + var1.getColumnSize() + "," + var1.getDecimalDigits() + ") " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("text")) {
			sql = var1.getColumnName() + " ntext " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("blob")) {
			sql = var1.getColumnName() + " image";
        }

        return sql;
    }

    private String b(ColumnMeta var1) {
        String var2 = "";
        if (var1.getColunmType().equalsIgnoreCase("string")) {
            var2 = var1.getColumnName() + " varchar(" + var1.getColumnSize() + ") " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("date")) {
			var2 = var1.getColumnName() + " date " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("datetime")) {
			var2 = var1.getColumnName() + " datetime " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("time")) {
			var2 = var1.getColumnName() + " time " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
		} else if (var1.getColunmType().equalsIgnoreCase("int")) {
            var2 = var1.getColumnName() + " int " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        } else if (var1.getColunmType().equalsIgnoreCase("double")) {
            var2 = var1.getColumnName() + " float " + ("Y".equals(var1.getIsNullable()) ? "NULL" : "NOT NULL");
        }

        return var2;
    }

    public String getCommentSql(ColumnMeta columnMeta) {
        StringBuffer var2 = new StringBuffer("EXECUTE ");
        if (ConvertUtils.isEmpty(columnMeta.getOldColumnName())) {
            var2.append("sp_addextendedproperty");
        } else {
            var2.append("sp_updateextendedproperty");
        }

        var2.append(" N'MS_Description', '");
        var2.append(columnMeta.getComment());
        var2.append("', N'SCHEMA', N'dbo', N'TABLE', N'");
        var2.append(columnMeta.getTableName());
        var2.append("', N'COLUMN', N'");
        var2.append(columnMeta.getColumnName() + "'");
        return var2.toString();
    }

    public String getSpecialHandle(ColumnMeta cgformcolumnMeta, ColumnMeta datacolumnMeta) {
        return null;
    }

    public String dropIndexs(String indexName, String tableName) {
        return "DROP INDEX " + indexName + " ON " + tableName;
    }

    public String countIndex(String indexName, String tableName) {
        return "SELECT count(*) FROM sys.indexes WHERE object_id=OBJECT_ID('" + tableName + "') and NAME= '" + indexName + "'";
    }
}
