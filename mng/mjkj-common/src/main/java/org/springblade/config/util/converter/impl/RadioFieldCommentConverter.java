package org.springblade.config.util.converter.impl;


import org.springblade.config.util.ConvertUtils;
import org.springblade.config.util.SpringContextUtils;
import org.springblade.config.util.converter.field.FieldFieldCommentConverter;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.model.DictModel;
import org.springblade.cgform.service.IDictService;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选框
 */
public class RadioFieldCommentConverter extends FieldFieldCommentConverter {

	/**
	 *
	 * @param mjkjCgformField 表额外字段数据(单条)
	 */
	public RadioFieldCommentConverter(CgformField mjkjCgformField) {
		IDictService dictService = SpringContextUtils.getBean(IDictService.class);
		String dictTable = mjkjCgformField.getDictTable();
		String dictText = mjkjCgformField.getDictText();
		String dictField = mjkjCgformField.getDictField();
		List<DictModel> arrayList = new ArrayList<>();
		if (ConvertUtils.isNotEmpty(dictTable)) {
			arrayList = dictService.queryTableDictItemsByCode(dictTable, dictText, dictField);
		} else if (ConvertUtils.isNotEmpty(dictField)) {
			arrayList = dictService.queryDictItemsByCode(dictField);
		}

		this.dictList = arrayList;
		this.filed = mjkjCgformField.getDbFieldName();
	}
}
