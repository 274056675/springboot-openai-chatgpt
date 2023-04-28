package org.springblade.config.util.converter.impl;

import org.springblade.config.util.ConvertUtils;
import org.springblade.config.util.converter.field.TableFieldCommentConverter;
import org.springblade.cgform.entity.CgformField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 分类字典树
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CatTreeFieldCommentConverter extends TableFieldCommentConverter {
    private String treeText;


    public CatTreeFieldCommentConverter(CgformField onlCgformField) {
        super("sys_category", "id", "name");
        this.treeText = onlCgformField.getDictText();
        this.field = onlCgformField.getDbFieldName();
    }

    @Override
    public Map<String, String> getConfig() {
        if (ConvertUtils.isEmpty(this.treeText)) {
            return null;
        } else {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("treeText", this.treeText);
            hashMap.put("field", this.field);
            return hashMap;
        }
    }
}

