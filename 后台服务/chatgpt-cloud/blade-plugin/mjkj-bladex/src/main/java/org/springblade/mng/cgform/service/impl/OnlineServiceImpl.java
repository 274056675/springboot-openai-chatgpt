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
package org.springblade.mng.cgform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;



import lombok.extern.slf4j.Slf4j;

import org.springblade.core.tool.utils.Func;
import org.springblade.mng.cgform.entity.*;
import org.springblade.mng.cgform.model.*;
import org.springblade.mng.cgform.service.*;
import org.springblade.mng.config.util.ConvertUtils;
import org.springblade.mng.config.util.EnhanceJsUtil;
import org.springblade.mng.config.util.SqlSymbolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 服务实现类
 *
 * @author BladeX
 * @since 2021-05-20
 */
@Slf4j
@Service
public class OnlineServiceImpl implements IOnlineService {

    @Autowired
    private ICgformFieldService fieldService;

    @Autowired
    private ICgformIndexService indexService;

    @Autowired
    private ICgformButtonService buttonService;

    @Autowired
    private ICgformEnhanceJsService jsService;

    @Autowired
    private IDictService dictService;


    @Autowired
    private IDeptService deptService;

    @Autowired
    private IUserService userService;

    @Override
    public OnlineConfigModel queryOnlineConfig(CgformHead head) {
        Long headId = head.getId();

        List<String> hideColumns = new ArrayList<>();//隐藏树形列表 todo
        List<OnlColumn> columns = new ArrayList<>();//列属性
        Map<String, List<DictModel>> dictOptions = new HashMap<>();//字典
        Map<String, List<MjkjBladeDept>> deptOptions = new HashMap<>();//部门列表
        Map<String, List<ChatgptBludeUser>> userOptions = new HashMap<>();//用户列表
        List<HrefSlots> fieldHrefSlots = new ArrayList<>();//跳转列表
        List<OnlForeignKey> foreignKeys = new ArrayList<>();
        List<String> dbFieldNameList = new ArrayList<>();//属性名称列表

		// 根据表单基本属性id获取表单额外属性集合
        //处理属性
        List<CgformField> onlCgformFields = this.getFields(headId);//属性列表
		// 迭代器遍历字段属性集合
        Iterator<CgformField> iterator = onlCgformFields.iterator();
        while (iterator.hasNext()) {
        	// 获取对象
            CgformField onlCgformField = iterator.next();
            String dbFieldName = onlCgformField.getDbFieldName();//字段名称
            String mainTable = onlCgformField.getMainTable();//外键主表名
            String mainField = onlCgformField.getMainField();//外键主键字段
            if (Func.isNotEmpty(mainField) && Func.isNotEmpty(mainTable)) {
            	// 创建外部键对象,封装字段名称/外主键字段名
                OnlForeignKey onlForeignKey = new OnlForeignKey(dbFieldName, mainField);
                // 封装到集合内
                foreignKeys.add(onlForeignKey);
            }
            //列表是否显示0否 1是
            if (!"id".equals(dbFieldName)
                    && !dbFieldNameList.contains(dbFieldName)) {
                OnlColumn onlColumn = new OnlColumn(onlCgformField.getDbFieldTxt(), dbFieldName, onlCgformField.getFieldLength());
                String dictField = onlCgformField.getDictField();
                String fieldShowType = onlCgformField.getFieldShowType();//表单控件类型
                // 表单控件类型存在,且不为"popup"
				if (Func.isNotEmpty(dictField) && !"popup".equals(fieldShowType)) {//Popup弹框
					// 创建字典集合
                    List<DictModel> dictModelList = new ArrayList<>();
                    // 判断字段数据的字典表数据是否存在
                    if (Func.isNotEmpty(onlCgformField.getDictTable())) {
                    	// 存在,根据字典表数据,字典表text/字典code查询表数据的字典
                        dictModelList = dictService.queryTableDictItemsByCode(onlCgformField.getDictTable(), onlCgformField.getDictText(), dictField);
                    } else if (Func.isNotEmpty(onlCgformField.getDictField())) {
                        dictModelList = dictService.queryDictItemsByCode(dictField);
                    }

                    dictOptions.put(dbFieldName, dictModelList);
                    onlColumn.setCustomRender(dbFieldName);
                }

                if (Func.equals(fieldShowType, "switch")) {//开关
                    List<DictModel> sysDictOption = SqlSymbolUtil.getYNDict(onlCgformField);
                    dictOptions.put(dbFieldName, sysDictOption);
                    onlColumn.setCustomRender(dbFieldName);
                }


                if (Func.equals(fieldShowType, "link_down")) {//联动组件
                    String dictTable = onlCgformField.getDictTable();
                    if (Func.isNotEmpty(dictTable)) {
                        CommonEntity commonEntity = JSONObject.parseObject(dictTable, CommonEntity.class);
                        List<DictModel> tableDictOption = dictService.queryTableDictItemsByCode(commonEntity.getTable(), commonEntity.getTxt(), commonEntity.getKey());
                        dictOptions.put(dbFieldName, tableDictOption);
                        onlColumn.setCustomRender(dbFieldName);
                        columns.add(onlColumn);
                        String linkField = commonEntity.getLinkField();
                        this.linkFieldArr(onlCgformFields, dbFieldNameList, columns, dbFieldName, linkField);
                    }
                }

                if (Func.equals(fieldShowType, "sel_tree")) {//自定义树控件
                    String dictText = onlCgformField.getDictText();
                    if(Func.isNotEmpty(dictText)){
                        String[] dictTexts = onlCgformField.getDictText().split(",");
                        List<DictModel> tableDictItems = dictService.queryTableDictItemsByCode(onlCgformField.getDictTable(), dictTexts[2], dictTexts[0]);
                        dictOptions.put(dbFieldName, tableDictItems);
                        onlColumn.setCustomRender(dbFieldName);
                    }

                }

                if ("cat_tree".equals(fieldShowType)) {//分类字典树
                    String dictTable = onlCgformField.getDictText();
                    if (Func.isNotEmpty(dictTable)) {
                        String dictCode = onlCgformField.getDictField();
                        Long idByCode = dictService.getIdByCode(dictCode);
                        String filterSql = "(id ="+idByCode+" or pstr like '" + idByCode + "#%" + "')";
                        //String filterSql = SqlSymbolUtil.getFilterSql(onlCgformField.getDictField());
                        List<DictModel> tableDictOption = dictService.queryFilterTableDictInfo("sys_category", "name", "id", filterSql);
                        dictOptions.put(dbFieldName, tableDictOption);
                        onlColumn.setCustomRender(dbFieldName);
                    } else {
                        onlColumn.setCustomRender("_replace_text_" + dictTable);
                    }
                }

                if ("sel_depart".equals(fieldShowType)) {//部门选择
                    List<MjkjBladeDept> mjkjBladeDeptList = deptService.list();
                    deptOptions.put(dbFieldName, mjkjBladeDeptList);
                    onlColumn.setCustomRender(dbFieldName);
                }

                if ("sel_user".equals(onlCgformField.getFieldShowType())) {//用户选择
                    List<ChatgptBludeUser> chatgptBludeUserList = userService.list();
                    userOptions.put(dbFieldName, chatgptBludeUserList);
                    onlColumn.setCustomRender(dbFieldName);
                }


                if (fieldShowType.indexOf(FieldSlotType.FILE) >= 0) {
                    onlColumn.setScopedSlots(new ScopedSlots("fileSlot"));
                } else if (fieldShowType.indexOf(FieldSlotType.IMAGE) >= 0) {
                    onlColumn.setScopedSlots(new ScopedSlots("imgSlot"));
                } else if (fieldShowType.indexOf(FieldSlotType.EDITOR) >= 0) {
                    onlColumn.setScopedSlots(new ScopedSlots("htmlSlot"));
                } else if (fieldShowType.equals(FieldSlotType.DATE)) {
                    onlColumn.setScopedSlots(new ScopedSlots("dateSlot"));
                } else if (fieldShowType.equals(FieldSlotType.PCA)) {
                    onlColumn.setScopedSlots(new ScopedSlots("pcaSlot"));
                } else {
                    onlColumn.setScopedSlots(new ScopedSlots("formatSlot"));
                }

                //js增强
                String jsEnhance = onlCgformField.getJsEnhance();
                if (!Func.isEmpty(jsEnhance)) {
                    onlColumn.setJsEnhance(onlCgformField.getJsEnhance());
                }

                if (Func.isNotBlank(onlCgformField.getFieldHref())) {
                    String dictTable = "fieldHref_" + dbFieldName;
                    onlColumn.setHrefSlotName(dictTable);
                    fieldHrefSlots.add(new HrefSlots(dictTable, onlCgformField.getFieldHref()));
                }

                if ("1".equals(onlCgformField.getSortFlag())) {//是否支持排序1是0否
                    onlColumn.setSorter(true);
                }

                if (!"link_down".equals(fieldShowType)) {
                    columns.add(onlColumn);
                }
            }
        }

        OnlineConfigModel onlineConfigModel = new OnlineConfigModel();
        onlineConfigModel.setHeadId(headId);
        onlineConfigModel.setTableType(head.getTableType());
        onlineConfigModel.setFormTemplate(head.getFormTemplate());
        onlineConfigModel.setDescription(head.getTableTxt());
        onlineConfigModel.setCurrentTableName(head.getTableName());
        onlineConfigModel.setPaginationFlag(head.getIsPage());
        onlineConfigModel.setCheckboxFlag(head.getIsCheckbox());
        onlineConfigModel.setScrollFlag(head.getScroll());
        onlineConfigModel.setColumns(columns);
        onlineConfigModel.setDictOptions(dictOptions);
        onlineConfigModel.setDeptOptions(deptOptions);
        onlineConfigModel.setUserOptions(userOptions);
        onlineConfigModel.setFieldHrefSlots(fieldHrefSlots);
        onlineConfigModel.setForeignKeys(foreignKeys);
        onlineConfigModel.setHideColumns(hideColumns);
        List<CgformButton> buttonList = buttonService.queryButtonList(headId, true);
        List<CgformButton> showButtonList = new ArrayList<>();
        Iterator<CgformButton> it = buttonList.iterator();

        while (it.hasNext()) {
            CgformButton button = it.next();
            if (!hideColumns.contains(button.getButtonCode())) {
                showButtonList.add(button);
            }
        }

        onlineConfigModel.setCgButtonList(showButtonList);
        CgformEnhanceJs enhanceJs = jsService.queryEnhanceJs(headId, "list");
        if (enhanceJs != null && Func.isNotEmpty(enhanceJs.getCgJs())) {
            String mainField = EnhanceJsUtil.getJsFunction(enhanceJs.getCgJs(), buttonList);
            onlineConfigModel.setEnhanceJs(mainField);
        }

		CgformEnhanceJs enhanceJsApp = jsService.queryEnhanceJs(headId, "appList");
		if (enhanceJsApp != null && Func.isNotEmpty(enhanceJsApp.getCgJs())) {
			String mainField = EnhanceJsUtil.getJsFunction(enhanceJsApp.getCgJs(), buttonList);
			onlineConfigModel.setEnhanceJsApp(mainField);
		}

        if ("Y".equals(head.getIsTree())) {//树结构
            onlineConfigModel.setPidField(head.getTreeParentIdField());
            onlineConfigModel.setHasChildrenField(head.getTreeIdField());
            onlineConfigModel.setTextField(head.getTreeFieldname());
        }

        return onlineConfigModel;
    }

    @Override
    public JSONObject queryOnlineFormObj(CgformHead head, CgformEnhanceJs onlCgformEnhanceJs) {
        JSONObject result = new JSONObject();
        //获取要显示的字段
        List<CgformField> availableFields = fieldService.queryAvailableFields(head.getId(), head.getTableName(), (String) null, false);
        List<String> disabledFields = new ArrayList<>();//获取要隐藏的字段 todo 权限 this.onlCgformFieldService.queryDisabledFields(head.getTableName());
        EnhanceJsUtil.enhanceJs(onlCgformEnhanceJs, head.getTableName(), availableFields);//js增强
        FieldModel fieldModel = null;
        if ("Y".equals(head.getIsTree())) {//树形结构
            fieldModel = new FieldModel();
            fieldModel.setCodeField("id");
            fieldModel.setFieldName(head.getTreeParentIdField());
            fieldModel.setPidField(head.getTreeParentIdField());
            fieldModel.setPidValue("0");
            fieldModel.setHsaChildField(head.getTreeIdField());
            fieldModel.setTableName(SqlSymbolUtil.getSubstring(head.getTableName()));
            fieldModel.setTextField(head.getTreeFieldname());
        }

        JSONObject filedJson = SqlSymbolUtil.getFiledJson(availableFields, disabledFields, fieldModel);
        filedJson.put("table", head.getTableName());
        result.put("schema", filedJson);
        result.put("head", head);
        List<CgformButton> buttons = this.buttonService.queryButtonList(head.getId(), false);
        if (buttons != null && buttons.size() > 0) {
            result.put("cgButtonList", buttons);
        }

        if (onlCgformEnhanceJs != null && ConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs())) {
            String cgJs = EnhanceJsUtil.getCgJs(onlCgformEnhanceJs.getCgJs(), buttons);
            onlCgformEnhanceJs.setCgJs(cgJs);
            result.put("enhanceJs", EnhanceJsUtil.getCgJs(onlCgformEnhanceJs.getCgJs()));
        }

        return result;
    }

    @Override
    public JSONObject queryOnlineFormObj(CgformHead head) {
        CgformEnhanceJs js = jsService.queryEnhanceJs(head.getId(), "form");
        return this.queryOnlineFormObj(head, js);
    }


    private List<CgformField> getFields(Long headId) {
        LambdaQueryWrapper<CgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CgformField::getCgformHeadId, headId);
        lambdaQueryWrapper.orderByAsc(CgformField::getOrderNum);
        return fieldService.list(lambdaQueryWrapper);
    }

    private void linkFieldArr(List<CgformField> onlCgformFields, List<String> dbFieldNameList, List<OnlColumn> onlColumns, String render, String linkFields) {
        if (ConvertUtils.isEmpty(linkFields)) {
            return;
        }
        String[] linkFieldArr = linkFields.split(",");

        for (int i = 0; i < linkFieldArr.length; ++i) {
            String linkField = linkFieldArr[i];
            Iterator<CgformField> iterator = onlCgformFields.iterator();

            while (iterator.hasNext()) {
                CgformField onlCgformField = iterator.next();
                String dbFieldName = onlCgformField.getDbFieldName();
                if (1 == onlCgformField.getIsShowList() && linkField.equals(dbFieldName)) {
                    dbFieldNameList.add(linkField);
                    OnlColumn onlColumn = new OnlColumn(onlCgformField.getDbFieldTxt(), dbFieldName, onlCgformField.getFieldLength());
                    onlColumn.setCustomRender(render);
                    onlColumns.add(onlColumn);
                    break;
                }
            }
        }


    }
}
