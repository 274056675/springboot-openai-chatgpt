package org.springblade.mng.config.util.converter.impl;




import org.springblade.mng.config.util.SpringContextUtils;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.model.DictModel;
import org.springblade.mng.cgform.service.IDictService;
import org.springblade.mng.config.util.converter.field.FieldFieldCommentConverter;
import org.springblade.mng.config.util.ConvertUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 多选框
 */
public class CheckboxFieldCommentConverter extends FieldFieldCommentConverter {

    public CheckboxFieldCommentConverter(CgformField onlCgformField) {
        IDictService dictService = SpringContextUtils.getBean(IDictService.class);
        String dictTable = onlCgformField.getDictTable();
        String dictText = onlCgformField.getDictText();
        String dictField = onlCgformField.getDictField();
        List<DictModel> arrayList = new ArrayList<>();
        if (ConvertUtils.isNotEmpty(dictTable)) {
            arrayList = dictService.queryTableDictItemsByCode(dictTable, dictText, dictField);
        } else if (ConvertUtils.isNotEmpty(dictField)) {
            arrayList = dictService.queryDictItemsByCode(dictField);
        }

        this.dictList = arrayList;
        this.filed = onlCgformField.getDbFieldName();
    }

    @Override
    public String converterToVal(String txt) {
        if (ConvertUtils.isEmpty(txt)) {
            return null;
        } else {
            List<String> arrayList = new ArrayList<>();
            String[] strings = txt.split(",");

            for (String s : strings) {
                String s1 = super.converterToVal(s);
                if (s1 != null) {
                    arrayList.add(s1);
                }
            }

            return String.join(",", arrayList);
        }
    }

    @Override
    public String converterToTxt(String val) {
        if (ConvertUtils.isEmpty(val)) {
            return null;
        } else {
            List<String> arrayList = new ArrayList<>();
            String[] strings = val.split(",");
            int length = strings.length;

            for(int i = 0; i < length; ++i) {
                String s = strings[i];
                String s1 = super.converterToTxt(s);
                if (s1 != null) {
                    arrayList.add(s1);
                }
            }

            return String.join(",", arrayList);
        }
    }
}
