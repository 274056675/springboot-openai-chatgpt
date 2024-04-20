package org.springblade.mng.cgform.model.generate.pojo;

public class ColumnVo extends CgFormColumnExtendVo
{
    public static String OPTION_REQUIRED = "required:true";
    public static String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
    private String fieldDbName;
    private String fieldName;
    private String filedComment;
    private String fieldType;
    private String fieldDbType;
    private String charmaxLength;
    private String precision;
    private String scale;
    private String nullable;
    private String sort;
    private String classType;
    private String classType_row;
    private String optionType;

    public ColumnVo() {
        this.filedComment = "";
        this.fieldType = "";
        this.fieldDbType = "";
        this.charmaxLength = "";
        this.sort = "N";
        this.classType = "";
        this.classType_row = "";
        this.optionType = "";
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getFieldDbType() {
        return this.fieldDbType;
    }

    public void setFieldDbType(String fieldDbType) {
        this.fieldDbType = fieldDbType;
    }

    public String getNullable() {
        return this.nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getPrecision() {
        return this.precision;
    }

    public String getScale() {
        return this.scale;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getOptionType() {
        return this.optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getClassType() {
        return this.classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFiledComment() {
        return this.filedComment;
    }

    public void setFiledComment(String filedComment) {
        this.filedComment = filedComment;
    }

    public String getClassType_row() {
        if (this.classType != null && this.classType.indexOf("easyui-") >= 0) {
            return this.classType.replaceAll("easyui-", "");
        }
        return this.classType_row;
    }

    public void setClassType_row(String classType_row) {
        this.classType_row = classType_row;
    }

    public String getCharmaxLength() {
        if (this.charmaxLength == null || "0".equals(this.charmaxLength)) {
            return "";
        }
        return this.charmaxLength;
    }

    public void setCharmaxLength(String charmaxLength) {
        this.charmaxLength = charmaxLength;
    }

    public String getFieldDbName() {
        return this.fieldDbName;
    }

    public void setFieldDbName(String fieldDbName) {
        this.fieldDbName = fieldDbName;
    }

    @Override
    public String toString() {
        return "{\"fieldDbName\":\"" + this.fieldDbName + "\",\"fieldName\":\"" + this.fieldName + "\",\"filedComment\":\"" + this.filedComment + "\",\"fieldType\":\"" + this.fieldType + "\",\"fieldDbType\":\"" + this.fieldDbType + "\",\"classType\":\"" + this.classType + "\",\"classType_row\":\"" + this.classType_row + "\",\"optionType\":\"" + this.optionType + "\",\"charmaxLength\":\"" + this.charmaxLength + "\",\"precision\":\"" + this.precision + "\",\"scale\":\"" + this.scale + "\",\"nullable\":\"" + this.nullable + "\",\"fieldLength\":\"" + this.fieldLength + "\",\"fieldHref\":\"" + this.fieldHref + "\",\"fieldValidType\":\"" + this.fieldValidType + "\",\"fieldDefault\":\"" + this.fieldDefault + "\",\"fieldShowType\":\"" + this.fieldShowType + "\",\"fieldOrderNum\":\"" + this.fieldOrderNum + "\",\"isKey\":\"" + this.isKey + "\",\"isShow\":\"" + this.isShow + "\",\"isShowList\":\"" + this.isShowList + "\",\"isQuery\":\"" + this.isQuery + "\",\"queryMode\":\"" + this.queryMode + "\",\"dictField\":\"" + this.dictField + "\",\"dictTable\":\"" + this.dictTable + "\",\"dictText\":\"" + this.dictText + "\"}";
    }
}
