package org.springblade.mng.config.util.converter.impl;



import org.springblade.mng.config.util.SpringContextUtils;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.model.DictModel;
import org.springblade.mng.cgform.service.IDictService;
import org.springblade.mng.config.util.ConvertUtils;
import org.springblade.mng.config.util.converter.field.FieldFieldCommentConverter;


import java.util.ArrayList;
import java.util.List;

/**
 * 单选框
 */
public class RadioFieldCommentConverter extends FieldFieldCommentConverter {

	/**
	 *
	 * @param onlCgformField 表额外字段数据(单条)
	 */
	public RadioFieldCommentConverter(CgformField onlCgformField) {
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
}
