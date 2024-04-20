package org.springblade.mng.config.util.converter.field;

import org.springblade.mng.cgform.model.DictModel;
import org.springblade.mng.cgform.service.IDictService;
import org.springblade.mng.config.util.ConvertUtils;

import lombok.Data;
import org.springblade.mng.config.util.SpringContextUtils;
import org.springblade.mng.config.util.converter.FieldCommentConverter;

import java.util.List;
import java.util.Map;


@Data
public class TableFieldCommentConverter implements FieldCommentConverter {

    protected IDictService dictService;
    protected String field;
    protected String table;
    protected String code;
    protected String text;

    public TableFieldCommentConverter() {
        this.dictService = SpringContextUtils.getBean(IDictService.class);
    }

    public TableFieldCommentConverter(String table, String code, String text) {
        this();
        this.table = table;
        this.code = code;
        this.text = text;
    }


    @Override
    public String converterToVal(String txt) {
        if (ConvertUtils.isNotEmpty(txt)) {
            String s = this.text + "= '" + txt + "'";
            String s1;
            int where = this.table.indexOf("where");
            if (where > 0) {
                s1 = this.table.substring(0, where).trim();
                s = s + " and " + this.table.substring(where + 5);
            } else {
                s1 = this.table;
            }

            List<DictModel> filterTableDictInfo = this.dictService.queryFilterTableDictInfo(s1, this.text, this.code, s);
            if (filterTableDictInfo != null && filterTableDictInfo.size() > 0) {
                return filterTableDictInfo.get(0).getValue();
            }
        }

        return null;
    }

    @Override
    public String converterToTxt(String val) {
        if (ConvertUtils.isNotEmpty(val)) {
            String s = this.code + "= '" + val + "'";
            String s1;
            int where = this.table.indexOf("where");
            if (where > 0) {
                s1 = this.table.substring(0, where).trim();
                s = s + " and " + this.table.substring(where + 5);
            } else {
                s1 = this.table;
            }

            List<DictModel> queryFilterTableDictInfo = this.dictService.queryFilterTableDictInfo(s1, this.text, this.code, s);
            if (queryFilterTableDictInfo != null && queryFilterTableDictInfo.size() > 0) {
                return queryFilterTableDictInfo.get(0).getText();
            }
        }

        return null;
    }

    @Override
    public Map<String, String> getConfig() {
        return null;
    }
}

