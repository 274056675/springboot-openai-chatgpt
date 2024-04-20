package org.springblade.mng.config.db;


import lombok.Data;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformIndex;

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
    private List<CgformIndex> indexes;
    private String treeParentIdFieldName;
    private String treeIdFieldname;
    private String treeFieldname;
    private DataBaseConfig dbConfig;

}
