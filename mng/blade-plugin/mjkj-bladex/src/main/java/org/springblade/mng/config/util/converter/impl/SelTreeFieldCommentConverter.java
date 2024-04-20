package org.springblade.mng.config.util.converter.impl;


import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.config.util.converter.field.TableFieldCommentConverter;

/**
 * 树型
 */
public class SelTreeFieldCommentConverter extends TableFieldCommentConverter {

	public SelTreeFieldCommentConverter(CgformField onlCgformField) {

		String dictText = onlCgformField.getDictText();
		String[] strings = dictText.split(",");
		this.setTable(onlCgformField.getDictTable());
		this.setCode(strings[0]);
		this.setText(strings[2]);
	}
}
