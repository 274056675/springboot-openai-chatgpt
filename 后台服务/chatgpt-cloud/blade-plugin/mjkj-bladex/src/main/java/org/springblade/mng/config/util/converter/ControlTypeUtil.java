package org.springblade.mng.config.util.converter;

import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.config.util.MyClassLoader;
import org.springblade.mng.config.util.SpringContextUtils;
import org.springblade.mng.config.util.converter.impl.*;
import org.springblade.mng.config.util.ConvertUtils;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控件类型工具类
 */
@Slf4j
@NoArgsConstructor
public class ControlTypeUtil {

	/**
	 *
	 * @param onlCgformField 表额外字段数据(单条)
	 * @return
	 */
    public static FieldCommentConverter getFieldCommentConverter(CgformField onlCgformField) {
        String fieldShowType = onlCgformField.getFieldShowType();
        FieldCommentConverter object = null;
        switch(fieldShowType) {
	        case "list":
            case "radio"://单选框
                object = new RadioFieldCommentConverter(onlCgformField);
                break;
            case "list_multi"://下拉多选框
            case "checkbox"://多选框
                object = new CheckboxFieldCommentConverter(onlCgformField);
                break;
            case "sel_search"://下拉搜索框
                object = new SelSearchFieldCommentConverter(onlCgformField);
                break;
            case "sel_tree"://自定义树控件
                object = new SelTreeFieldCommentConverter(onlCgformField);
                break;
            case "cat_tree"://分类字典树
                object = new CatTreeFieldCommentConverter(onlCgformField);
                break;
            case "link_down"://联动组件
                object = new LinkDownFieldCommentConverter(onlCgformField);
                break;
            case "sel_depart"://部门选择
                object = new SelDepartFieldCommentConverter(onlCgformField);
                break;
            case "sel_user"://用户选择
                object = new SelUserFieldCommentConverter(onlCgformField);
                break;
            default:
        }

        return object;
    }

	/**
	 *
	 * @param onlCgformFields 字段集合
	 * @return Map
	 */
	public static Map<String, FieldCommentConverter> getFieldCommentConverters(List<CgformField> onlCgformFields) {
		// 创建map
        Map<String, FieldCommentConverter> hashMap = new HashMap<>();

        // 遍历字段集合,进行值转换
        for (CgformField onlCgformField : onlCgformFields) {
            FieldCommentConverter fieldCommentConverter;
            // 如果值转换器字段的数据不为null
            if (ConvertUtils.isNotEmpty(onlCgformField.getConverter())) {
            	// 根据值转换器的值(推测为类的全限定名,比如"java.lang.String")获取fieldCommentConverter对象
                fieldCommentConverter = getFieldCommentConverter(onlCgformField.getConverter().trim());
            } else {
            	// 值转换器的数据为null
                fieldCommentConverter = getFieldCommentConverter(onlCgformField);
            }

            if (fieldCommentConverter != null) {
            	// 如果值转换对象存在,map.put(字段名字,值转换对象)
                hashMap.put(onlCgformField.getDbFieldName(), fieldCommentConverter);
            }
        }

        return hashMap;
    }

	/**
	 * @param s 表额外数据中值转换器字段的值
	 * @return
	 */
	private static FieldCommentConverter getFieldCommentConverter(String s) {
        Object object = null;
        // 如果"."不是在字符串第一个位置出现
        if (s.indexOf(".") > 0) {
            try {
            	// 类加载器,根据值转换器的值(类的全限定名)获取字节码对象,newInstance()根据字节码对象创建实例.
                object = MyClassLoader.getClassByScn(s).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        } else {
        	// 如果"."没有出现,或者在一个位置出现,说明这个字段不是全限定名,而是bean名,根据bean名从容器中获取bean
            object = SpringContextUtils.getBean(s);
        }

        // 如果bean是FieldCommentConverter的实例,转型,返回,否则,返回null
        if (object instanceof FieldCommentConverter) {
            return (FieldCommentConverter) object;
        } else {
            return null;
        }
    }
}
