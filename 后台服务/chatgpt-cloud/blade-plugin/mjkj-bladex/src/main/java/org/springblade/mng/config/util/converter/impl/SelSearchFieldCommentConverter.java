package org.springblade.mng.config.util.converter.impl;


import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.config.util.converter.field.TableFieldCommentConverter;

/**
 * 下拉搜索框
 */
public class SelSearchFieldCommentConverter extends TableFieldCommentConverter
{

    public SelSearchFieldCommentConverter(CgformField onlcgformfield)
    {
        super(onlcgformfield.getDictTable(), onlcgformfield.getDictField(), onlcgformfield.getDictText());
    }
}
