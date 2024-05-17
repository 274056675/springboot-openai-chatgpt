package org.springblade.modules.mjkj.common.config.util.converter.impl;


import org.springblade.modules.mjkj.common.config.util.converter.field.TableFieldCommentConverter;
import org.springblade.modules.mjkj.common.cgform.entity.CgformField;

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
