package org.springblade.mng.cgform.model;

import lombok.Data;

@Data
public class OnlForeignKey {
    private String field;
    private String table;
    private String key;

    public OnlForeignKey(String field, String key) {
        this.key = key;
        this.field = field;
    }
}
