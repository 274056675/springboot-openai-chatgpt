package org.springblade.mng.cgform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class CommonEntity {
    private String table;
    private String txt;
    private String key;
    private String linkField;
    private String idField;
    private String pidField;
    private String pidValue;
    private String condition;
}
