package org.springblade.config.db;

import org.springblade.cgform.entity.CgformField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TableModel implements Serializable {
    private String tableName;
    private String isDbSynch;
    private String content;
    private String jformVersion;
    private Integer jformType;
    private String jformPkType;
    private String jformPkSequence;
    private Integer relationType;
    private String subTableStr;
    private Integer tabOrder;
    private List<CgformField> columns;
    private String treeParentIdFieldName;
    private String treeIdFieldname;
    private String treeFieldname;
    private DataBaseConfig dbConfig;

}
