package org.springblade.config.util.converter.impl;


import org.springblade.config.util.converter.field.TableFieldCommentConverter;
import org.springblade.cgform.entity.CgformField;

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
