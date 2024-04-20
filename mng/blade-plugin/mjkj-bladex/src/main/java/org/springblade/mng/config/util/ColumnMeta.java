package org.springblade.mng.config.util;


import org.apache.commons.lang.StringUtils;
import org.springblade.mng.config.constant.MjkjConstant;

public class ColumnMeta {
    private String tableName;
    private String columnId;
    private String columnName;
    private int columnSize;
    private String colunmType;
    private String comment;
    private String fieldDefault;
    private int decimalDigits;
    private String isNullable;
    private String pkType;
    private String oldColumnName;

    public ColumnMeta() {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ColumnMeta)) {
            return false;
        } else {
            ColumnMeta var2 = (ColumnMeta)obj;
            if (!this.colunmType.contains("date") && !this.colunmType.contains("blob") && !this.colunmType.contains("text")) {
                return this.colunmType.equals(var2.getColunmType()) && this.isNullable.equals(var2.isNullable) && this.columnSize == var2.getColumnSize() && this.a(this.comment, var2.getComment()) && this.a(this.fieldDefault, var2.getFieldDefault());
            } else {
                return this.columnName.equals(var2.getColumnName()) && this.isNullable.equals(var2.isNullable) && this.a(this.comment, var2.getComment()) && this.a(this.fieldDefault, var2.getFieldDefault());
            }
        }
    }

    public boolean a(Object var1, String var2) {
        if (var1 == this) {
            return true;
        } else if (!(var1 instanceof ColumnMeta)) {
            return false;
        } else {
            ColumnMeta var3 = (ColumnMeta)var1;
            if (MjkjConstant.DB_TYPE_SQLSERVER.equals(var2)) {
                if (!this.colunmType.contains("date") && !this.colunmType.contains("blob") && !this.colunmType.contains("text")) {
                    return this.colunmType.equals(var3.getColunmType()) && this.isNullable.equals(var3.isNullable) && this.columnSize == var3.getColumnSize() && this.a(this.fieldDefault, var3.getFieldDefault());
                } else {
                    return this.columnName.equals(var3.getColumnName()) && this.isNullable.equals(var3.isNullable);
                }
            } else if (MjkjConstant.DB_TYPE_POSTGRESQL.equals(var2)) {
                if (!this.colunmType.contains("date") && !this.colunmType.contains("blob") && !this.colunmType.contains("text")) {
                    return this.colunmType.equals(var3.getColunmType()) && this.isNullable.equals(var3.isNullable) && this.columnSize == var3.getColumnSize() && this.a(this.fieldDefault, var3.getFieldDefault());
                } else {
                    return this.columnName.equals(var3.getColumnName()) && this.isNullable.equals(var3.isNullable);
                }
            } else if (MjkjConstant.DB_TYPE_ORACLE.equals(var2)) {
                if (!this.colunmType.contains("date") && !this.colunmType.contains("blob") && !this.colunmType.contains("text")) {
                    return this.colunmType.equals(var3.getColunmType()) && this.isNullable.equals(var3.isNullable) && this.columnSize == var3.getColumnSize() && this.a(this.fieldDefault, var3.getFieldDefault());
                } else {
                    return this.columnName.equals(var3.getColumnName()) && this.isNullable.equals(var3.isNullable);
                }
            } else if (!this.colunmType.contains("date") && !this.colunmType.contains("blob") && !this.colunmType.contains("text")) {
                return this.colunmType.equals(var3.getColunmType()) && this.isNullable.equals(var3.isNullable) && this.columnSize == var3.getColumnSize() && this.a(this.comment, var3.getComment()) && this.a(this.fieldDefault, var3.getFieldDefault());
            } else {
                return this.colunmType.equals(var3.getColunmType()) && this.columnName.equals(var3.getColumnName()) && this.isNullable.equals(var3.isNullable) && this.a(this.comment, var3.getComment()) && this.a(this.fieldDefault, var3.getFieldDefault());
            }
        }
    }

    public boolean a(ColumnMeta var1) {
        return var1 == this ? true : this.a(this.comment, var1.getComment());
    }

    public boolean b(ColumnMeta var1) {
        return var1 == this ? true : this.a(this.comment, var1.getComment());
    }

    private boolean a(String var1, String var2) {
        boolean var3 = StringUtils.isNotEmpty(var1);
        boolean var4 = StringUtils.isNotEmpty(var2);
        if (var3 != var4) {
            return false;
        } else {
            return var3 ? var1.equals(var2) : true;
        }
    }

    public String getColumnName() {
        return this.columnName;
    }

    public int getColumnSize() {
        return this.columnSize;
    }

    public String getColunmType() {
        return this.colunmType;
    }

    public String getComment() {
        return this.comment;
    }

    public int getDecimalDigits() {
        return this.decimalDigits;
    }

    public String getIsNullable() {
        return this.isNullable;
    }

    public String getOldColumnName() {
        return this.oldColumnName;
    }

    public int hashCode() {
        return this.columnSize + this.colunmType.hashCode() * this.columnName.hashCode();
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public void setColunmType(String colunmType) {
        this.colunmType = colunmType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    public String toString() {
        return this.columnName + "," + this.colunmType + "," + this.isNullable + "," + this.columnSize;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldDefault() {
        return this.fieldDefault;
    }

    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    public String getPkType() {
        return this.pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }
}
