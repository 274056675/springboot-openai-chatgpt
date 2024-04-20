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
 * 表单表名称
 */
@Data
@TableName("onl_cgform_head")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CgformHead对象", description = "CgformHead对象")
public class CgformHead extends TenantEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	@ApiModelProperty(value = "表名")
	private String tableName;

	@ApiModelProperty(value = "表类型: 1单表、2主表、3附表")
	private Integer tableType;

	@ApiModelProperty(value = "表版本")
	private Integer tableVersion;

	@ApiModelProperty(value = "表说明")
	private String tableTxt;

	@ApiModelProperty(value = "表单基本属性")
	private String tableRemark;

	@ApiModelProperty(value = "是否带checkbox")
	private String isCheckbox;

	@ApiModelProperty(value = "同步数据库状态")
	private String isDbSynch;

	@ApiModelProperty(value = "是否分页")
	private String isPage;

	@ApiModelProperty(value = "是否是树")
	private String isTree;

	@ApiModelProperty(value = "主键生成序列")
	private String idSequence;

	@ApiModelProperty(value = "主键类型")
	private String idType;

	@ApiModelProperty(value = "查询模式")
	private String queryMode;

	@ApiModelProperty(value = "映射关系 0一对多  1一对一")
	private Integer relationType;

	@ApiModelProperty(value = "子表")
	private String subTableStr;

	@ApiModelProperty(value = "附表排序序号")
	private Integer tabOrderNum;

	@ApiModelProperty(value = "树形表单父id")
	private String treeParentIdField;

	@ApiModelProperty(value = "树表主键字段")
	private String treeIdField;

	@ApiModelProperty(value = "树开表单列字段")
	private String treeFieldname;

	@ApiModelProperty(value = "表单分类")
	private String formCategory;

	@ApiModelProperty(value = "PC表单模板")
	private String formTemplate;

	@ApiModelProperty(value = "表单模板样式(移动端)")
	private String formTemplateMobile;

	@ApiModelProperty(value = "是否有横向滚动条")
	private Integer scroll;

	@ApiModelProperty(value = "复制版本号")
	private Integer copyVersion;

	@ApiModelProperty(value = "复制表类型1为复制表 0为原始表")
	private Integer copyType;

	@ApiModelProperty(value = "原始表ID")
	private Long physicId;

	@ApiModelProperty(value = "主题模板")
	private String themeTemplate;

	@ApiModelProperty(value = "是否用设计器表单")
	private String isDesForm;

	@ApiModelProperty(value = "设计器表单编码")
	private String desFormCode;

	@TableField(exist = false)
	private transient Integer hascopy;

	@ApiModelProperty(value = "序号名称")
	private String indexTitle;

	@ApiModelProperty(value = "是否显示序号")
	private String indexShow;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "隐藏头部")
	private String hideHeader;

	@ApiModelProperty(value = "隐藏操作列")
	private String hideMenu;

	@ApiModelProperty(value = "基础数据")
	private String basicFunction;
	@ApiModelProperty(value = "操作列样式")
	private String menuStyle;

	@ApiModelProperty(value = "移动端表单设计id")
	private String moveFormId;

	@ApiModelProperty(value = "是否开启按钮权限")
	private String isAuthBtn;

	@ApiModelProperty(value = "不加载数据")
	private String isTableData;

	@ApiModelProperty(value = "移动端表格模式")
	private String moveTablePattern;

	@ApiModelProperty(value = "表格搜索模式")
	private String searchPattern;

	@ApiModelProperty(value = "不登录是否可以查询-0=否，1=是")
	private String nologinSelect;

	@ApiModelProperty(value = "不允许查看数据的角色")
	private String noViewDataRole;

	@ApiModelProperty(value = "不允许操作数据的角色")
	private String noOperationDataRole;

	public static CgformHead init(CgformHead head, String tenantId, BladeUser createUser) {
		String deptId = createUser.getDeptId();
		Long userId = createUser.getUserId();
		head.setCreateUser(userId);
		head.setCreateTime(DateUtil.now());
		head.setTenantId(tenantId);

		head.setIsDbSynch("N");//同步数据库状态
		head.setTableVersion(1);//表版本
		head.setCopyType(0);//0为原始表
		if (head.getTableType() == 3 && head.getTabOrderNum() == null) {
			head.setTabOrderNum(1);
		}
		return head;
	}
}
