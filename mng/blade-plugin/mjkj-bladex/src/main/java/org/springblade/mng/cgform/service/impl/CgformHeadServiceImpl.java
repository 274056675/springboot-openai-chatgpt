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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springblade.mng.cgform.entity.CgformEnhanceJava;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.entity.CgformIndex;
import org.springblade.mng.cgform.mapper.CgformHeadMapper;
import org.springblade.mng.cgform.model.CgformModel;
import org.springblade.mng.cgform.model.TreeDataModel;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.constant.MjkjConstant;
import org.springblade.mng.config.db.DataBaseConfig;
import org.springblade.mng.config.db.TableModel;
import org.springblade.mng.config.exception.BusinessException;
import org.springblade.mng.config.exception.DBException;
import org.springblade.mng.config.service.DbTableHandleI;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.cgform.service.*;
import org.springblade.mng.config.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author BladeX
 * @since 2021-05-20
 */
@Slf4j
@Service
public class CgformHeadServiceImpl extends BaseServiceImpl<CgformHeadMapper, CgformHead> implements ICgformHeadService {

	@Autowired
	private ICgformFieldService fieldService;

	@Autowired
	private ICgformEnhanceJavaService javaService;

	@Autowired
	private ICgformEnhanceSqlService sqlService;



	private void updateParentSubTableStr(Long subHeadId) {
		List<CgformField> oldFieldList = fieldService.queryFormFields(subHeadId, false);
		CgformHead subHead = this.getById(subHeadId);
		if (Func.isEmpty(subHead) || subHead.getTableType().intValue() != 3) {
			return;
		}
		//先清楚主表的外键关系
		if (Func.isEmpty(oldFieldList)) {
			return;
		}
		for (CgformField field : oldFieldList) {
			if (Func.isEmpty(field.getMainTable())) {//存在外键关联
				continue;
			}
			String mainTable = field.getMainTable();//获取到外键表 即aa表
			String tableName = subHead.getTableName();//本表，即bb表
			LambdaQueryWrapper<CgformHead> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(CgformHead::getTableName, mainTable);
			CgformHead parentHead = this.getOne(wrapper);
			if (Func.isEmpty(parentHead) || Func.isEmpty(parentHead.getSubTableStr())) {
				continue;
			}
			String subTableStr = parentHead.getSubTableStr();
			List<String> subTableNameList = new ArrayList<>(Arrays.asList(subTableStr.split(",")));
			Iterator<String> iterator = subTableNameList.iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				if (Func.equals(next, tableName)) {
					iterator.remove();
				}
			}
			subTableStr = String.join(",", subTableNameList);

			parentHead.setSubTableStr(subTableStr);
			this.updateById(parentHead);

		}


	}


	/**
	 * 保存自定义表单内容
	 *
	 * @param json
	 * @throws DBException
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String saveManyFormData(CgformHead head, JSONObject json) throws DBException, BusinessException {
		String resultId = "0";
		int addFlag = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_ADD, MjkjConstant.ENHANCE_START, head, json);//java - 开始 -增强
		if (addFlag != 1) {
			return resultId;
		}
		String tableName = SqlSymbolUtil.getSubstring(head.getTableName());//获取表明

		if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
			if (head.getTableType() == 2) {//表类型: 1单表、2主表、3附表
				this.saveSubFormData(head, json);//保存附表信息
			}
			//先保存主表，再保存附表
			if ("Y".equals(head.getIsTree())) {//树结构
				resultId = fieldService.saveTreeFormData(head.getId(), tableName, json, head.getTreeIdField(), head.getTreeParentIdField());
			} else {
				resultId = fieldService.saveFormData(head.getId(), tableName, json, false);
			}
		}




		sqlService.executeEnhanceSqlInsert(MjkjConstant.ENHANCE_ADD, head.getId(), json);//sql增强
		javaService.executeEnhanceJava(MjkjConstant.ENHANCE_ADD, MjkjConstant.ENHANCE_END, head, json);//java增强
		return resultId;
	}

	/**
	 * 保存自定义表单内容
	 *
	 * @param jsonobjectList
	 * @throws DBException
	 * @throws BusinessException
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<String> saveManyFormDataBatch(CgformHead head, List<JSONObject> jsonobjectList) throws DBException, BusinessException {
		List<String> resultIdList=new ArrayList<>();

		for (JSONObject json:jsonobjectList) {
			String id = Func.toStr(SqlSymbolUtil.getIdWorkerId());

			json.put("id", id);

			int addFlag = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_ADD, MjkjConstant.ENHANCE_START, head, json);//java - 开始 -增强
			if (addFlag != 1) {
				continue;
			}
			String tableName = SqlSymbolUtil.getSubstring(head.getTableName());//获取表明

			if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
				if (head.getTableType() == 2) {//表类型: 1单表、2主表、3附表
					this.saveSubFormData(head, json);//保存附表信息
				}
				//先保存主表，再保存附表
				if ("Y".equals(head.getIsTree())) {//树结构
					fieldService.saveTreeFormData(head.getId(), tableName, json, head.getTreeIdField(), head.getTreeParentIdField());
				} else {
					fieldService.saveFormData(head.getId(), tableName, json, false);
				}
			}

			sqlService.executeEnhanceSqlInsert(MjkjConstant.ENHANCE_ADD, head.getId(), json);//sql增强
			addFlag = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_ADD, MjkjConstant.ENHANCE_END, head, json);//java增强

			if (addFlag != 1) {
				continue;
			}
			resultIdList.add(id);
		}


		return resultIdList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void editManyFormData(CgformHead head, JSONObject json) throws DBException, BusinessException {

		javaService.executeEnhanceJava(MjkjConstant.ENHANCE_EDIT, MjkjConstant.ENHANCE_START, head, json);
		String tableName = head.getTableName();
		if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
			if ("Y".equals(head.getIsTree())) {
				this.fieldService.editTreeFormData(head.getId(), tableName, json, head.getTreeIdField(), head.getTreeParentIdField());
			} else {
				this.fieldService.editFormData(head.getId(), tableName, json, false);
			}

			if (head.getTableType() == 2) {//表类型: 1单表、2主表、3附表
				editSubFormData(head, json);//处理编辑
			}
		}

		javaService.executeEnhanceJava(MjkjConstant.ENHANCE_EDIT, MjkjConstant.ENHANCE_END, head, json);
		sqlService.executeEnhanceSqlUpdate(MjkjConstant.ENHANCE_EDIT, head.getId(), json);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void editManyFormDataBatch(CgformHead head,  List<JSONObject> jsonobjectList) throws DBException, BusinessException {

		try{
			for (JSONObject json:jsonobjectList) {
				javaService.executeEnhanceJava(MjkjConstant.ENHANCE_EDIT, MjkjConstant.ENHANCE_START, head, json);
				String tableName = head.getTableName();
				if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
					try{
						if ("Y".equals(head.getIsTree())) {
							this.fieldService.editTreeFormData(head.getId(), tableName, json, head.getTreeIdField(), head.getTreeParentIdField());
						} else {
							this.fieldService.editFormData(head.getId(), tableName, json, false);
						}
					}catch (Exception e){
						e.printStackTrace();
					}


					if (head.getTableType() == 2) {//表类型: 1单表、2主表、3附表
						editSubFormData(head, json);//处理编辑
					}
				}

				javaService.executeEnhanceJava(MjkjConstant.ENHANCE_EDIT, MjkjConstant.ENHANCE_END, head, json);
				sqlService.executeEnhanceSqlUpdate(MjkjConstant.ENHANCE_EDIT, head.getId(), json);
			}
		}catch (Exception e){
			e.printStackTrace();
		}




	}

	public Map<String, Object> queryManyFormData(Long headId, String id) throws DBException {
		CgformHead head = this.getById(headId);
		List<CgformField> fieldList = this.fieldService.queryFormFields(headId, false);
		Map<String, Object> resultMap = this.fieldService.queryFormData(fieldList, head.getTableName(), id);
		if (Func.isEmpty(resultMap)) {//判空操作
			return resultMap;
		}
		String subTableStr = head.getSubTableStr();
		if (head.getTableType() != 2 || Func.isEmpty(subTableStr)) {//表类型: 1单表、2主表、3附表
			return resultMap;
		}

		String[] subTableStrs = subTableStr.split(",");
		for (String table : subTableStrs) {
			CgformHead subHead = baseMapper.selectOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, table));
			if (Func.isEmpty(subHead)) {
				continue;
			}
			List<CgformField> subFieldList = this.fieldService.queryFormFields(subHead.getId(), false);
			String dbFieldName = "";
			String mainField = null;
			Iterator<CgformField> subIterator = subFieldList.iterator();

			while (subIterator.hasNext()) {
				CgformField subField = subIterator.next();
				if (Func.isEmpty(subField.getMainField())) {
					continue;
				}
				dbFieldName = subField.getDbFieldName();
				mainField = subField.getMainField();
				if (null == resultMap.get(mainField)) {
					mainField = resultMap.get(mainField.toUpperCase()).toString();
				} else {
					mainField = resultMap.get(mainField).toString();
				}
			}

			List<Map<String, Object>> mapList = this.fieldService.querySubFormData(subFieldList, table, dbFieldName, mainField);
			if (Func.isNotEmpty(mapList)) {
				resultMap.put(table, SqlSymbolUtil.handleClob(mapList));
			} else {
				resultMap.put(table, new String[0]);
			}

		}

		return resultMap;

	}

	@Override
	public List<TreeDataModel> getTreeAllDataList(Long headId) throws DBException {
		CgformHead head = this.getById(headId);
		if (Func.equals(head.getIsTree(), "N")) {
			return null;
		}
		List<CgformField> fieldList = this.fieldService.queryFormFields(headId, false);
		List<Map<String, Object>> mapList = this.fieldService.queryFormAllData(fieldList, head.getTableName());
		List<TreeDataModel> dataList = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			TreeDataModel model = new TreeDataModel();
			model.setId(Long.parseLong(map.get("id").toString()));
			model.setPId(Long.parseLong(map.get("pid").toString()));
			String treeFieldname = (String) map.get(head.getTreeFieldname());
			model.setTitle(treeFieldname);
			model.setParentId(model.getPId());
			model.setData(map);
			dataList.add(model);
		}
		ForestNodeMerger.merge(dataList);


		//删除最顶级不是0的数据
		Iterator<TreeDataModel> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			TreeDataModel next = iterator.next();
			if ((next.getPId() != 0)) {
				iterator.remove();
			}
		}
		return dataList;

	}

	private void recursion(List<Map<String, Object>> allList, List<Map<String, Object>> resultList, Map<String, Object> map) {
		Optional<Map<String, Object>> optional = allList.stream().filter(x -> Func.equals(x.get("id"), map.get("pid"))).findFirst();
		if (optional.isPresent() && !resultList.contains(optional.get())) {
			resultList.add(optional.get());
			recursion(allList, resultList, optional.get());
		}
	}

	@Transactional(
		rollbackFor = {Exception.class}
	)
	public void deleteOneTableInfo(Long headId, String dataId) throws BusinessException {
		CgformHead head = this.getById(headId);
		if (Func.isEmpty(head)) {
			throw new BusinessException("未找到表配置信息");
		}
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("id", dataId);


		int i = javaService.executeEnhanceJava(MjkjConstant.ENHANCE_DELETE, MjkjConstant.ENHANCE_START, head, jsonObject);//java增强-开始
		if (i == -1) {
			return;
		}
		if(!Func.equals(head.getFormCategory(),"view")) {//不是显示表
			this.updateParentNode(head, dataId);
			if (head.getTableType() == 2) {
				this.fieldService.deleteAutoListMainAndSub(head, dataId);
			} else {
				String deletesql = "update " + head.getTableName() + " set is_deleted = -1   where id = '" + dataId + "'";
				baseMapper.deleteOne(deletesql);
			}
		}

		sqlService.executeEnhanceSqlUpdate(MjkjConstant.ENHANCE_DELETE, headId, jsonObject);//sql增强-结束
		javaService.executeEnhanceJava(MjkjConstant.ENHANCE_DELETE, MjkjConstant.ENHANCE_END, head, jsonObject);//java增强-结束


	}


	public List<Map<String, Object>> queryListData(String sql) {
		return this.baseMapper.queryList(sql);
	}


	@Override
	public void initCopyState(List<CgformHead> headList) {
		List<String> copyPhysicIdList = this.baseMapper.queryCopyPhysicId();
		Iterator<CgformHead> iterator = headList.iterator();

		while (iterator.hasNext()) {
			CgformHead head = iterator.next();
			if (copyPhysicIdList.contains(head.getId())) {
				head.setHascopy(1);
			} else {
				head.setHascopy(0);
			}
		}

	}


	private String getJavaType(String type) {
		type = type.toLowerCase();
		if (type.indexOf("int") >= 0) {
			return "java.lang.Integer";
		} else if (type.indexOf("double") >= 0) {
			return "java.lang.Double";
		} else if (type.indexOf("decimal") >= 0) {
			return "java.math.BigDecimal";
		} else {
			return type.indexOf("date") >= 0 ? "java.util.Date" : "java.lang.String";
		}
	}



	/**
	 * 更新父级node
	 *
	 * @param head
	 * @param dataId
	 */
	private void updateParentNode(CgformHead head, String dataId) {
		if (!Func.equals("Y", head.getIsTree())) {
			return;
		}

		String tableName = SqlSymbolUtil.getSubstring(head.getTableName());
		String treeParentIdField = head.getTreeParentIdField();
		Map<String, Object> map = baseMapper.queryOneByTableNameAndId(tableName, dataId);
		String treeParentIdFieldStr = null;
		if (map.get(treeParentIdField) != null && !"0".equals(map.get(treeParentIdField))) {
			treeParentIdFieldStr = map.get(treeParentIdField).toString();
		} else if (map.get(treeParentIdField.toUpperCase()) != null && !"0".equals(map.get(treeParentIdField.toUpperCase()))) {
			treeParentIdFieldStr = map.get(treeParentIdField.toUpperCase()).toString();
		}

		if (Func.isNotEmpty(treeParentIdFieldStr)) {
			Integer cou = baseMapper.queryChildNode(tableName, treeParentIdField, treeParentIdFieldStr);
			if (cou == 1) {
				String treeIdField = head.getTreeIdField();
				this.fieldService.updateTreeNodeNoChild(tableName, treeIdField, treeParentIdFieldStr);
			}
		}

	}

	/**
	 * 获取java增强实体
	 *
	 * @param java
	 * @return
	 */
	private Object a(CgformEnhanceJava java) {
		if (Func.isEmpty(java)) {
			return null;
		}

		String javaType = java.getCgJavaType();
		String javaValue = java.getCgJavaValue();
		if (Func.isEmpty(javaType) || Func.isEmpty(javaValue)) {
			return null;
		}

		Object obj = null;
		if ("class".equals(javaType)) {
			try {
				obj = MyClassLoader.getClassByScn(javaValue).newInstance();
			} catch (InstantiationException e) {
				log.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				log.error(e.getMessage(), e);
			}
		} else if ("spring".equals(javaType)) {
			obj = SpringContextUtils.getBean(javaValue);
		}

		return obj;

	}

	/**
	 * 校验是否是文本型
	 *
	 * @param field
	 */
	private void checkDbType(CgformField field) {
		if (DbType.TEXT.equals(field.getDbType()) || DbType.BLOB.equals(field.getDbType())) {
			field.setDbLength(0);
			field.setDbPointLength(0);
		}
	}


	/**
	 * 处理 子表名称
	 *
	 * @param head
	 * @param fields
	 */
	private void handleSubTableStr(CgformHead head, List<CgformField> fields) {
		if (head.getTableType() == 3) {//附表
			head = baseMapper.selectById(head.getId());
			for (int i = 0; i < fields.size(); ++i) {
				CgformField field = fields.get(i);
				String mainTable = field.getMainTable();
				if (Func.isNotEmpty(mainTable)) {
					CgformHead selectHead = baseMapper.selectOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, mainTable));
					if (Func.isEmpty(selectHead)) {
						continue;
					}
					String subTableStr = selectHead.getSubTableStr();
					if (Func.isEmpty(subTableStr)) {
						subTableStr = head.getTableName();
					} else if (subTableStr.indexOf(head.getTableName()) < 0) {
						List<String> subTableNameList = new ArrayList<>(Arrays.asList(subTableStr.split(",")));
						for (int j = 0; j < subTableNameList.size(); ++j) {
							String var10 = (String) subTableNameList.get(j);
							CgformHead cfh = baseMapper.selectOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, var10));
							if (cfh != null && head.getTabOrderNum() < ConvertUtils.getInt(cfh.getTabOrderNum(), 0)) {
								subTableNameList.add(j, head.getTableName());
								break;
							}
						}

						if (subTableNameList.indexOf(head.getTableName()) < 0) {
							subTableNameList.add(head.getTableName());
						}

						subTableStr = String.join(",", subTableNameList);
					}

					selectHead.setSubTableStr(subTableStr);
					baseMapper.updateById(selectHead);
					break;

				}
			}
		} else {
			String tableName = head.getTableName();
			List<CgformHead> list = this.baseMapper.selectList((new LambdaQueryWrapper<CgformHead>()).like(CgformHead::getSubTableStr, tableName));
			if (Func.isEmpty(list)) {
				return;
			}
			Iterator<CgformHead> iterator = list.iterator();

			while (iterator.hasNext()) {
				CgformHead cgformHead = iterator.next();
				String subTableStr = cgformHead.getSubTableStr();
				if (cgformHead.getSubTableStr().equals(tableName)) {
					subTableStr = "";
				} else if (cgformHead.getSubTableStr().startsWith(tableName + ",")) {
					subTableStr = subTableStr.replace(tableName + ",", "");
				} else if (cgformHead.getSubTableStr().endsWith("," + tableName)) {
					subTableStr = subTableStr.replace("," + tableName, "");
				} else if (cgformHead.getSubTableStr().indexOf("," + tableName + ",") != -1) {
					subTableStr = subTableStr.replace("," + tableName + ",", ",");
				}
				cgformHead.setSubTableStr(subTableStr);
				this.baseMapper.updateById(cgformHead);
			}
		}


	}

	/**
	 * 处理表属性
	 *
	 * @param formHead
	 * @param formFieldList
	 */
	private void handleField(CgformHead formHead, List<CgformField> formFieldList) {
		//获取原表id
		List<CgformHead> oldformHeadList = this.list((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getPhysicId, formHead.getId()));
		if (oldformHeadList == null || oldformHeadList.size() == 0) {
			return;
		}
		Iterator<CgformHead> iterator = oldformHeadList.iterator();

		while (true) {
			List<CgformField> selectFormFieldList;
			String dbFieldName;
			List<String> editFieldNameList;
			Iterator it;
			label:
			do {
				while (iterator.hasNext()) {
					CgformHead oldHead = iterator.next();
					//获取原表字段属性列表
					selectFormFieldList = this.fieldService.list((new LambdaQueryWrapper<CgformField>()).eq(CgformField::getCgformHeadId, oldHead.getId()));
					if (Func.isNotEmpty(selectFormFieldList)) {
						//处理旧属性
						Map<String, Integer> oldMap = new HashMap<>();
						Iterator<CgformField> cgformFieldIterator = selectFormFieldList.iterator();
						while (cgformFieldIterator.hasNext()) {
							CgformField cgformField = cgformFieldIterator.next();
							oldMap.put(cgformField.getDbFieldName(), 1);
						}

						//处理新属性
						Map<String, Integer> newMap = new HashMap<>();
						Iterator<CgformField> cfIterator = formFieldList.iterator();
						while (cfIterator.hasNext()) {
							CgformField field = cfIterator.next();
							newMap.put(field.getDbFieldName(), 1);
						}

						editFieldNameList = new ArrayList<>();
						List<String> addFieldNameList = new ArrayList<>();
						//遍历新的map，查看旧的是否存在，存在则更新，不存在则新增
						Iterator<String> mapIterator = newMap.keySet().iterator();
						while (mapIterator.hasNext()) {
							String fieldName = mapIterator.next();
							if (oldMap.get(fieldName) == null) {
								addFieldNameList.add(fieldName);//新增
							} else {
								editFieldNameList.add(fieldName);//编辑
							}
						}

						//遍历旧的map，如果新的不存在，则删除
						List<String> delFieldNameList = new ArrayList<>();
						it = oldMap.keySet().iterator();

						while (it.hasNext()) {
							dbFieldName = (String) it.next();
							if (newMap.get(dbFieldName) == null) {
								delFieldNameList.add(dbFieldName);
							}
						}

						//删除
						if (delFieldNameList.size() > 0) {
							Iterator<CgformField> deleteIterator = selectFormFieldList.iterator();
							while (deleteIterator.hasNext()) {
								CgformField delField = deleteIterator.next();
								if (delFieldNameList.contains(delField.getDbFieldName())) {
									this.fieldService.removeById(delField.getId());
								}
							}
						}

						//新增
						if (addFieldNameList.size() > 0) {
							Iterator<CgformField> addIterator = formFieldList.iterator();

							while (addIterator.hasNext()) {
								CgformField addField = addIterator.next();
								if (addFieldNameList.contains(addField.getDbFieldName())) {
									CgformField addModel = new CgformField();
									addModel.setCgformHeadId(oldHead.getId());
									this.setCgformField(addField, addModel);
									this.fieldService.save(addModel);
								}
							}
						}
						continue label;
					}

					Iterator<CgformField> cfIterator = formFieldList.iterator();

					while (cfIterator.hasNext()) {
						CgformField cf = cfIterator.next();
						CgformField cgformField = new CgformField();
						cgformField.setCgformHeadId(oldHead.getId());
						this.setCgformField(cf, cgformField);
						this.fieldService.save(cgformField);
					}
				}

				return;
			} while (editFieldNameList.size() <= 0);

			it = editFieldNameList.iterator();

			while (it.hasNext()) {
				dbFieldName = (String) it.next();
				this.updateField(dbFieldName, formFieldList, selectFormFieldList);
			}
		}

	}

	/**
	 * 更新表属性
	 *
	 * @param dbFieldName
	 * @param formFieldList
	 * @param selectFormFieldList
	 */
	private void updateField(String dbFieldName, List<CgformField> formFieldList, List<CgformField> selectFormFieldList) {
		CgformField cgformField = null;
		Iterator<CgformField> iterator = formFieldList.iterator();

		while (iterator.hasNext()) {
			CgformField field = iterator.next();
			if (dbFieldName.equals(field.getDbFieldName())) {
				cgformField = field;
			}
		}

		CgformField selectCgformField = null;
		Iterator<CgformField> selectIterator = selectFormFieldList.iterator();

		while (selectIterator.hasNext()) {
			CgformField cf = selectIterator.next();
			if (dbFieldName.equals(cf.getDbFieldName())) {
				selectCgformField = cf;
			}
		}
		if (Func.isEmpty(cgformField) || Func.isEmpty(selectCgformField)) {
			return;//某一个为空，则结束
		}
		boolean updateFlag = false;
		if (!cgformField.getDbType().equals(selectCgformField.getDbType())) {
			selectCgformField.setDbType(cgformField.getDbType());
			updateFlag = true;
		}

		if (!cgformField.getDbDefaultVal().equals(selectCgformField.getDbDefaultVal())) {
			selectCgformField.setDbDefaultVal(cgformField.getDbDefaultVal());
			updateFlag = true;
		}

		if (cgformField.getDbLength() != selectCgformField.getDbLength()) {
			selectCgformField.setDbLength(cgformField.getDbLength());
			updateFlag = true;
		}

		if (cgformField.getDbIsNull() != selectCgformField.getDbIsNull()) {
			selectCgformField.setDbIsNull(cgformField.getDbIsNull());
			updateFlag = true;
		}

		if (updateFlag) {
			this.fieldService.updateById(selectCgformField);
		}


	}

	/**
	 * 默认值
	 *
	 * @param oldField
	 * @param newField
	 */
	private void setCgformField(CgformField oldField, CgformField newField) {
		newField.setDbDefaultVal(oldField.getDbDefaultVal());
		newField.setDbFieldName(oldField.getDbFieldName());
		newField.setDbFieldNameOld(oldField.getDbFieldNameOld());
		newField.setDbFieldTxt(oldField.getDbFieldTxt());
		newField.setDbIsKey(oldField.getDbIsKey());
		newField.setDbIsNull(oldField.getDbIsNull());
		newField.setDbLength(oldField.getDbLength());
		newField.setDbPointLength(oldField.getDbPointLength());
		newField.setDbType(oldField.getDbType());
		newField.setDictField(oldField.getDictField());
		newField.setDictTable(oldField.getDictTable());
		newField.setDictText(oldField.getDictText());
		newField.setFieldExtendJson(oldField.getFieldExtendJson());
		newField.setFieldHref(oldField.getFieldHref());
		newField.setFieldLength(oldField.getFieldLength());
		newField.setFieldMustInput(oldField.getFieldMustInput());
		newField.setFieldShowType(oldField.getFieldShowType());
		newField.setFieldValidType(oldField.getFieldValidType());
		newField.setFieldDefaultValue(oldField.getFieldDefaultValue());
		newField.setIsQuery(oldField.getIsQuery());
		newField.setIsShowForm(oldField.getIsShowForm());
		newField.setIsShowList(oldField.getIsShowList());
		newField.setMainField((String) null);
		newField.setMainTable((String) null);
		newField.setOrderNum(oldField.getOrderNum());
		newField.setQueryMode(oldField.getQueryMode());
		newField.setIsReadOnly(oldField.getIsReadOnly());
		newField.setSortFlag(oldField.getSortFlag());
		newField.setQueryDefVal(oldField.getQueryDefVal());
		newField.setQueryConfigFlag(oldField.getQueryConfigFlag());
		newField.setQueryDictField(oldField.getQueryDictField());
		newField.setQueryDictTable(oldField.getQueryDictTable());
		newField.setQueryDictText(oldField.getQueryDictText());
		newField.setQueryMustInput(oldField.getQueryMustInput());
		newField.setQueryShowType(oldField.getQueryShowType());
		newField.setQueryValidType(oldField.getQueryValidType());
		newField.setConverter(oldField.getConverter());
	}

	public Integer getMaxCopyVersion(Long physicId) {
		Integer version = baseMapper.getMaxCopyVersion(physicId);
		return version == null ? 0 : version;
	}

	private void subTableStr(CgformHead onlCgformHead) {
		if (onlCgformHead.getTableType() == 3) {
			LambdaQueryWrapper<CgformField> qw = (new LambdaQueryWrapper<CgformField>()).eq(CgformField::getCgformHeadId, onlCgformHead.getId());
			List<CgformField> fields = this.fieldService.list(qw);
			String mainTable = null;
			Iterator<CgformField> iterator = fields.iterator();
			if (iterator.hasNext()) {
				CgformField field = iterator.next();
				mainTable = field.getMainTable();
			}

			if (ConvertUtils.isNotEmpty(mainTable)) {
				CgformHead mainTableHead = this.baseMapper.selectOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, mainTable));
				if (mainTableHead != null) {
					String subTableStr = mainTableHead.getSubTableStr();
					if (ConvertUtils.isNotEmpty(subTableStr)) {
						List<String> subTables = Arrays.asList(subTableStr.split(",")).stream().collect(Collectors.toList());
						subTables.remove(onlCgformHead.getTableName());
						mainTableHead.setSubTableStr(String.join(",", subTables));
						baseMapper.updateById(mainTableHead);
					}
				}
			}
		}

	}

	//保存表单数据
	private void saveFormData(List fieldList, String tbname, JSONObject json) {
		Map map = SqlSymbolUtil.getInsertSql(tbname, fieldList, json);
		fieldService.executeInsertSQL(map);
		// 返回子表id,
		json.put("id", MjkjUtils.getMap2Long(map, "id"));
	}


	/**
	 * 获取sql中的#{key} 这个key组成的set
	 */
	public static Set<String> getSqlRuleParams(String sql) {
		if (ConvertUtils.isEmpty(sql)) {
			return null;
		}
		Set<String> varParams = new HashSet<String>();
		String regex = "\\#\\{\\w+\\}";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		while (m.find()) {
			String var = m.group();
			varParams.add(var.substring(var.indexOf("{") + 1, var.indexOf("}")));
		}
		return varParams;
	}

	/**
	 * 保存附表数据
	 */
	private void saveSubFormData(CgformHead head, JSONObject json) {
		String subTableStr = head.getSubTableStr();//-----以下处理附表-------
		if (Func.isEmpty(subTableStr)) {
			return;
		}
		String[] subTableStrs = subTableStr.split(",");
		for (String subTable : subTableStrs) {
			JSONArray jsonArray = json.getJSONArray(subTable);
			if (jsonArray == null || jsonArray.size() == 0) {
				continue;
			}
			//获取附表信息
			CgformHead subHead = baseMapper.selectOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, subTable));
			if (Func.isEmpty(subHead)) {
				continue;
			}
			//获取附表属性列表
			List<CgformField> fieldList = this.fieldService.list((new LambdaQueryWrapper<CgformField>()).eq(CgformField::getCgformHeadId, subHead.getId()));
			String dbFieldName = null;//字段名字
			String mainFieldStr = null;//外键主键字段
			Iterator<CgformField> iterator = fieldList.iterator();

			while (iterator.hasNext()) {
				CgformField field = iterator.next();
				if (Func.isNotEmpty(field.getMainField())) {//外键主键字段不为空
					dbFieldName = field.getDbFieldName();//字段名称
					String mainField = field.getMainField();//外键主键
					if (json.get(mainField.toLowerCase()) != null) {
						mainFieldStr = json.getString(mainField.toLowerCase());
					}

					if (json.get(mainField.toUpperCase()) != null) {
						mainFieldStr = json.getString(mainField.toUpperCase());
					}
				}
			}

			for (int j = 0; j < jsonArray.size(); ++j) {
				JSONObject jsonObject = jsonArray.getJSONObject(j);
				if (Func.isNotEmpty(mainFieldStr)) {
					jsonObject.put(dbFieldName, mainFieldStr);
				}
				//保存附表数据
				this.saveFormData(fieldList, subTable, jsonObject);
			}
		}

	}

	/**
	 * 编辑附表数据
	 */
	private void editSubFormData(CgformHead head, JSONObject json) {
		String subTableStr = head.getSubTableStr();
		if (Func.isEmpty(subTableStr)) {
			return;
		}
		String[] subTableStrs = subTableStr.split(",");
		for (String str : subTableStrs) {
			JSONArray jsonArray = json.getJSONArray(str);
			if (jsonArray == null) {
				continue;
			}
			CgformHead subHead = baseMapper.selectOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, str));
			if (Func.isEmpty(subHead)) {
				continue;
			}
			List<CgformField> list = this.fieldService.list((new LambdaQueryWrapper<CgformField>()).eq(CgformField::getCgformHeadId, subHead.getId()));
			String dbFieldName = "";
			String mainField = null;
			Iterator<CgformField> iterator = list.iterator();

			while (iterator.hasNext()) {
				CgformField field = iterator.next();
				if (!ConvertUtils.isEmpty(field.getMainField())) {
					dbFieldName = field.getDbFieldName();
					String mainFieldStr = field.getMainField();
					if (json.get(mainFieldStr.toLowerCase()) != null) {
						mainField = json.getString(mainFieldStr.toLowerCase());
					}

					if (json.get(mainFieldStr.toUpperCase()) != null) {
						mainField = json.getString(mainFieldStr.toUpperCase());
					}
				}
			}

			if (Func.isNotEmpty(mainField)) {
				this.fieldService.deleteList(str, dbFieldName, mainField);
				JSONArray strJsonArray = json.getJSONArray(str);
				if (Func.isNotEmpty(strJsonArray)) {
					for (int i = 0; i < strJsonArray.size(); ++i) {
						JSONObject jsonObject = strJsonArray.getJSONObject(i);
						if (mainField != null) {
							jsonObject.put(dbFieldName, mainField);
						}
						this.fieldService.saveFormDataList(list, str, jsonObject);
					}
				}
			}

		}
	}


}
