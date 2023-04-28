package org.springblade.config.util.converter.impl;



import org.springblade.config.util.ConvertUtils;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.config.util.converter.field.FieldFieldCommentConverter;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.service.IDictService;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门选择
 */
public class SelDepartFieldCommentConverter extends FieldFieldCommentConverter {

    public SelDepartFieldCommentConverter(CgformField onlCgformField) {
        IDictService sysBaseApi = SpringContextUtils.getBean(IDictService.class);
        String sysDepart = "blade_dept";
        String departName = "dept_name";
        String id = "id";
        this.dictList = sysBaseApi.queryTableDictItemsByCode(sysDepart, departName, id);
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

            for (String s : strings) {
                String s1 = super.converterToTxt(s);
                if (s1 != null) {
                    arrayList.add(s1);
                }
            }

            return String.join(",", arrayList);
        }
    }
}
