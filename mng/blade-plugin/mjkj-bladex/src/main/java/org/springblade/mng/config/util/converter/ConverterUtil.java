package org.springblade.mng.config.util.converter;


import lombok.NoArgsConstructor;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.config.util.ConvertUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@NoArgsConstructor
public class ConverterUtil {

    public static final int A = 2;
    public static final int B = 1;


	/**
	 *
	 * @param id
	 * @param mapList 主表数据(map集合)
	 * @param onlCgformFields 字段集合
	 */
    public static void converter(int id, List<Map<String, Object>> mapList, List<CgformField> onlCgformFields) {
    	// 值转换器
        Map<String, FieldCommentConverter> fieldCommentConverters = ControlTypeUtil.getFieldCommentConverters(onlCgformFields);

        for (Map<String, Object> stringObjectMap : mapList) {
            Iterator<Entry<String,Object>> iterator1 = stringObjectMap.entrySet().iterator();
            Map<String, Object> hashMap = new HashMap<>();

            while (iterator1.hasNext()) {
                Entry<String,Object> entry = iterator1.next();
                Object entryValue = entry.getValue();
                if (entryValue != null) {
                    String entryKey = (String) entry.getKey();
                    FieldCommentConverter fieldCommentConverter =fieldCommentConverters.get(entryKey);
                    if (fieldCommentConverter != null) {
                        String entryValueStr = entryValue.toString();
                        String s = id == 1 ? fieldCommentConverter.converterToTxt(entryValueStr) : fieldCommentConverter.converterToVal(entryValueStr);
                        if(Func.isNotEmpty(entryValueStr) && Func.isNotEmpty(s)){
							converter(fieldCommentConverter, stringObjectMap, id);
							converter(fieldCommentConverter, hashMap, entryValueStr);
							stringObjectMap.put(entryKey, s);
						}

                    }
                }
            }

            for (Object o : hashMap.keySet()) {
                String s = (String) o;
                stringObjectMap.put(s, hashMap.get(s));
            }
        }

    }

    private static void converter(FieldCommentConverter fieldCommentConverter, Map<String, Object> map, int id) {
        Map<String, String> config = fieldCommentConverter.getConfig();
        if (config != null) {
            String linkField = (String)config.get("linkField");
            if (ConvertUtils.isNotEmpty(linkField)) {
                String[] linkFields = linkField.split(",");

                for (String linkField1 : linkFields) {
                    Object linkFieldValue1 = map.get(linkField1);
                    if (linkFieldValue1 != null) {
                        String linkFieldStr = linkFieldValue1.toString();
                        String s = id == 1 ? fieldCommentConverter.converterToTxt(linkFieldStr) : fieldCommentConverter.converterToVal(linkFieldStr);
                        map.put(linkField1, s);
                    }
                }
            }
        }

    }

    private static void converter(FieldCommentConverter fieldCommentConverter, Map<String, Object> map, String s) {
        Map<String, String> config = fieldCommentConverter.getConfig();
        if (config != null) {
            String treeText = (String) config.get("treeText");
            if (ConvertUtils.isNotEmpty(treeText)) {
                map.put(treeText, s);
            }
        }

    }
}
