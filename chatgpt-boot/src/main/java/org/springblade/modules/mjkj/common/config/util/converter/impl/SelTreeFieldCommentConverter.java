package org.springblade.modules.mjkj.common.config.util.converter.impl;


import org.springblade.modules.mjkj.common.config.util.converter.field.TableFieldCommentConverter;
import org.springblade.modules.mjkj.common.cgform.entity.CgformField;

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
