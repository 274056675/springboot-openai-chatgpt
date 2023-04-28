package org.springblade.cgform.model.generate.pojo.onetomany;

import java.util.List;
import java.util.Map;

public class MainTableVo
{
    private String entityPackage;
    private String tableName;
    private String entityName;
    private String ftlDescription;
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String ftl_mode;
    List<SubTableVo> subTables;
    public Integer fieldRowNum;
    public Integer searchFieldNum;
    public Integer fieldRequiredNum;
    private Map<?, ?> extendParams;
    
    public MainTableVo() {
        this.ftl_mode = "A";
    }
    
    public Map<?, ?> getExtendParams() {
        return this.extendParams;
    }
    
    public void setExtendParams(Map<?, ?> extendParams) {
        this.extendParams = extendParams;
    }
    
    public Integer getFieldRowNum() {
        return this.fieldRowNum;
    }
    
    public void setFieldRowNum(Integer fieldRowNum) {
        this.fieldRowNum = fieldRowNum;
    }
    
    public Integer getSearchFieldNum() {
        return this.searchFieldNum;
    }
    
    public void setSearchFieldNum(Integer searchFieldNum) {
        this.searchFieldNum = searchFieldNum;
    }
    
    public Integer getFieldRequiredNum() {
        return this.fieldRequiredNum;
    }
    
    public void setFieldRequiredNum(Integer fieldRequiredNum) {
        this.fieldRequiredNum = fieldRequiredNum;
    }
    
    public List<SubTableVo> getSubTables() {
        return this.subTables;
    }
    
    public void setSubTables(List<SubTableVo> subTables) {
        this.subTables = subTables;
    }
    
    public String getEntityPackage() {
        return this.entityPackage;
    }
    
    public String getTableName() {
        return this.tableName;
    }
    
    public String getEntityName() {
        return this.entityName;
    }
    
    public String getFtlDescription() {
        return this.ftlDescription;
    }
    
    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    
    public void setFtlDescription(String ftlDescription) {
        this.ftlDescription = ftlDescription;
    }
    
    public String getFtl_mode() {
        return this.ftl_mode;
    }
    
    public void setFtl_mode(String ftl_mode) {
        this.ftl_mode = ftl_mode;
    }
    
    public String getPrimaryKeyPolicy() {
        return this.primaryKeyPolicy;
    }
    
    public String getSequenceCode() {
        return this.sequenceCode;
    }
    
    public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
        this.primaryKeyPolicy = primaryKeyPolicy;
    }
    
    public void setSequenceCode(String sequenceCode) {
        this.sequenceCode = sequenceCode;
    }
    
    @Override
    public String toString() {
        return "{\"entityPackage\":\"" + this.entityPackage + "\",\"tableName\":\"" + this.tableName + "\",\"entityName\":\"" + this.entityName + "\",\"ftlDescription\":\"" + this.ftlDescription + "\",\"primaryKeyPolicy\":\"" + this.primaryKeyPolicy + "\",\"sequenceCode\":\"" + this.sequenceCode + "\",\"ftl_mode\":\"" + this.ftl_mode + "\",\"subTables\":" + this.subTables + ",\"fieldRowNum\":\"" + this.fieldRowNum + "\",\"searchFieldNum\":\"" + this.searchFieldNum + "\",\"fieldRequiredNum\":\"" + this.fieldRequiredNum + "\"}";
    }
}
