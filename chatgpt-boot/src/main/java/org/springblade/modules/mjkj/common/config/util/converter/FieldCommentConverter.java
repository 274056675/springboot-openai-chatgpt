package org.springblade.modules.mjkj.common.config.util.converter;

import java.util.Map;

public interface FieldCommentConverter
{

    String converterToVal(String s);

    String converterToTxt(String s);

    Map<String, String> getConfig();
}
