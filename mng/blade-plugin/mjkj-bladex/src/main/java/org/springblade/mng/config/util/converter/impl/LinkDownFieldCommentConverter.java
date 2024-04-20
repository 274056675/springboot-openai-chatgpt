package org.springblade.mng.config.util.converter.impl;

import com.alibaba.fastjson.JSONObject;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.model.CommonEntity;
import org.springblade.mng.config.util.converter.field.TableFieldCommentConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 联动组件
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LinkDownFieldCommentConverter extends TableFieldCommentConverter {

    private String linkField;

    public LinkDownFieldCommentConverter(CgformField onlCgformField) {
        String dictTable = onlCgformField.getDictTable();
        CommonEntity linkDown = JSONObject.parseObject(dictTable, CommonEntity.class);
        this.setTable(linkDown.getTable());
        this.setCode(linkDown.getKey());
        this.setText(linkDown.getTxt());
        this.linkField = linkDown.getLinkField();
    }

    @Override
    public Map<String, String> getConfig() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("linkField", this.linkField);
        return hashMap;
    }
}
