package org.springblade.config.util.converter.impl;


import org.springblade.config.util.converter.field.TableFieldCommentConverter;
import org.springblade.cgform.entity.CgformField;

/**
 * 下拉搜索框
 */
public class SelSearchFieldCommentConverter extends TableFieldCommentConverter
{

    public SelSearchFieldCommentConverter(CgformField mjkjcgformfield)
    {
        super(mjkjcgformfield.getDictTable(), mjkjcgformfield.getDictField(), mjkjcgformfield.getDictText());
    }
}
