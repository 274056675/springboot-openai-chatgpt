package org.springblade.config.util.converter.field;

import org.springblade.config.util.ConvertUtils;
import org.springblade.config.util.converter.FieldCommentConverter;
import org.springblade.cgform.model.DictModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class FieldFieldCommentConverter implements FieldCommentConverter {
    protected String filed;
    protected List<DictModel> dictList;

    @Override
    public String converterToVal(String txt) {
        if (ConvertUtils.isNotEmpty(txt)) {

            for (DictModel dictModel : this.dictList) {
                if (dictModel.getText().equals(txt)) {
                    return dictModel.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public String converterToTxt(String val) {
        if (ConvertUtils.isNotEmpty(val)) {

            for (DictModel dictModel : this.dictList) {
                if (dictModel.getValue().equals(val)) {
                    return dictModel.getText();
                }
            }
        }

        return null;
    }

    @Override
    public Map<String, String> getConfig() {
        return null;
    }
}
