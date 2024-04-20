/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.mng.cgform.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.TenantEntity;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.utils.DateUtil;

/**
 * 表单数据库字段属性
 */
@Data
@TableName("onl_cgform_field")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformField对象", description = "CgformField对象")
public class CgformField extends TenantEntity {

    private static final long serialVersionUID = 1L;

	private Long id;

    @ApiModelProperty(value = "表ID")
    private Long cgformHeadId;

    @ApiModelProperty(value = "字段名字")
    private String dbFieldName;

    @ApiModelProperty(value = "字段备注")
    private String dbFieldTxt;

    @ApiModelProperty(value = "原字段名")
    private String dbFieldNameOld;

    @ApiModelProperty(value = "是否主键 0否 1是")
    private Integer dbIsKey;

    @ApiModelProperty(value = "是否允许为空0否 1是")
    private Integer dbIsNull;

    @ApiModelProperty(value = "数据库字段类型")
    private String dbType;

    @ApiModelProperty(value = "数据库字段长度")
    private Integer dbLength;

    @ApiModelProperty(value = "小数点")
    private Integer dbPointLength;

    @ApiModelProperty(value = "表字段默认值")
    private String dbDefaultVal;

    @ApiModelProperty(value = "字典code")
    private String dictField;

    @ApiModelProperty(value = "字典表")
    private String dictTable;

    @ApiModelProperty(value = "字典Text")
    private String dictText;

    @ApiModelProperty(value = "表单控件类型")
    private String fieldShowType;
    /*label: '文本框', value: 'text',
    label: '密码', value: 'password',
    label: '下拉框', value: 'list',
    label: '单选框', value: 'radio',
    label: '多选框', value: 'checkbox',
    label: '开关', value: 'switch',
    label: '日期(yyyy-MM-dd)', value: 'date',
    label: '日期（yyyy-MM-dd HH:mm:ss）', value: 'datetime',
    label: '时间（HH:mm:ss）',  value: 'time',
    label: '文件', value: 'file',
    label: '图片', value: 'image',
    label: '多行文本', value: 'textarea',
    label: '下拉多选框', value: 'list_multi',
    label: '下拉搜索框', value: 'sel_search',
    label: 'Popup弹框', value: 'popup',
    label: '分类字典树', value: 'cat_tree',
    label: '部门选择', value: 'sel_depart',
    label: '用户选择', value: 'sel_user',
    label: '富文本', value: 'umeditor',
    label: 'MarkDown', value: 'markdown',
    label: '省市区组件', value: 'pca',
    label: '联动组件', value: 'link_down',
    label: '自定义树控件', value: 'sel_tree',*/


    @ApiModelProperty(value = "跳转URL")
    private String fieldHref;

    @ApiModelProperty(value = "表单控件长度")
    private Integer fieldLength;

    @ApiModelProperty(value = "表单字段校验规则")
    private String fieldValidType;

    @ApiModelProperty(value = "字段是否必填")
    private String fieldMustInput;

    @ApiModelProperty(value = "扩展参数JSON")
    private String fieldExtendJson;

    @ApiModelProperty(value = "控件默认值，不同的表达式展示不同的结果。	1. 纯字符串直接赋给默认值；	2. #{普通变量}；	3. {{ 动态JS表达式 }}；	4. ${填值规则编码}；	填值规则表达式只允许存在一个，且不能和其他规则混用。")
    private String fieldDefaultValue;

    @ApiModelProperty(value = "是否查询条件0否 1是")
    private Integer isQuery;

    @ApiModelProperty(value = "表单是否显示0否 1是")
    private Integer isShowForm;

    @ApiModelProperty(value = "列表是否显示0否 1是")
    private Integer isShowList;

    @ApiModelProperty(value = "是否是只读（1是 0否）")
    private Integer isReadOnly;

    @ApiModelProperty(value = "查询模式")
    private String queryMode;

    @ApiModelProperty(value = "外键主表名")
    private String mainTable;

    @ApiModelProperty(value = "外键主键字段")
    private String mainField;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "自定义值转换器")
    private String converter;

    @ApiModelProperty(value = "查询默认值")
    private String queryDefVal;

    @ApiModelProperty(value = "查询配置字典text")
    private String queryDictText;

    @ApiModelProperty(value = "查询配置字典code")
    private String queryDictField;

    @ApiModelProperty(value = "查询配置字典table")
    private String queryDictTable;

    @ApiModelProperty(value = "查询显示控件")
    private String queryShowType;

    @ApiModelProperty(value = "是否启用查询配置1是0否")
    private String queryConfigFlag;

    @ApiModelProperty(value = "查询字段校验类型")
    private String queryValidType;

    @ApiModelProperty(value = "查询字段是否必填1是0否")
    private String queryMustInput;

    @ApiModelProperty(value = "是否支持排序1是0否")
    private String sortFlag;

	@ApiModelProperty(value = "导出模板0=不导出  1=导出")
	private Long importFlag;

	@ApiModelProperty(value = "是否标题列0=否  1=是")
	private Long moveShowTitle;

	@ApiModelProperty(value = "是否展开列0=否  1=是")
	private Long moveShowUnfold;

	@ApiModelProperty(value = "是否栅格0=否  1=是")
	private Long moveSpan;

	@ApiModelProperty(value = "导出排序0=否  1=是")
	private Integer moveShowSort;

	@ApiModelProperty(value = "是否可控0=否  1=是")
	private Integer isShowColumn;

	@ApiModelProperty(value = "是否搜索0=否  1=是")
	private Integer isShowSearch;

	@ApiModelProperty(value = "连表字段")
	private String uniteFormKey;
    /**
     * js增强
     */
    @TableField(exist = false)
    private String jsEnhance;

    public static CgformField init(CgformField field, String tenantId, BladeUser createUser){
        String deptId = createUser.getDeptId();
        Long userId = createUser.getUserId();
        field.setCreateUser(userId);
        field.setCreateTime(DateUtil.now());
        field.setTenantId(tenantId);
        return field;
    }
}
