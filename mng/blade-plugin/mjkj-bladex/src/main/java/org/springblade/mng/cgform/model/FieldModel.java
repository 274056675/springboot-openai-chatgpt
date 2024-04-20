package org.springblade.mng.cgform.model;

import lombok.Data;

@Data
public class FieldModel {
    private String fieldName;
    private String tableName;
    private String codeField;
    private String textField;
    private String pidField;
    private String pidValue;
    private String hsaChildField;
}
