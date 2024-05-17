package org.springblade.modules.mjkj.common.config.util.converter.impl;


import org.springblade.modules.mjkj.common.config.util.ConvertUtils;
import org.springblade.modules.mjkj.common.config.util.SpringContextUtils;
import org.springblade.modules.mjkj.common.config.util.converter.field.FieldFieldCommentConverter;
import org.springblade.modules.mjkj.common.cgform.entity.CgformField;
import org.springblade.modules.mjkj.common.cgform.model.DictModel;
import org.springblade.modules.mjkj.common.cgform.service.ISysDictService;

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
		ISysDictService dictService = SpringContextUtils.getBean(ISysDictService.class);
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
