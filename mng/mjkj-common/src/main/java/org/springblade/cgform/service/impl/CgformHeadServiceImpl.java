
package org.springblade.cgform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.entity.CgformEnhanceJava;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.mapper.CgformHeadMapper;
import org.springblade.cgform.model.TreeDataModel;
import org.springblade.cgform.model.generate.pojo.ColumnExpVo;
import org.springblade.cgform.model.generate.pojo.ColumnVo;
import org.springblade.cgform.service.ICgformEnhanceJavaService;
import org.springblade.cgform.service.ICgformEnhanceSqlService;
import org.springblade.cgform.service.ICgformFieldService;
import org.springblade.cgform.service.ICgformHeadService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.MjjyConfig;
import org.springblade.config.constant.MjkjConstant;
import org.springblade.config.db.DataBaseConfig;
import org.springblade.config.db.TableModel;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.exception.DBException;
import org.springblade.config.service.DbTableHandleI;
import org.springblade.config.util.*;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
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
 *
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
	 * @param
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



	//获取所有未生成过的表
	@Override
	public List<Map<String, String>> getAllTable() {


		List<Map<String, String>> tableList = TableUtil.getDatabaseAlTable();
		//查询表是否已经存在
		if (Func.isEmpty(tableList)) {
			return tableList;
		}
		Iterator<Map<String, String>> iterator = tableList.iterator();
		List<String> tableNameList = baseMapper.getAllTableName();

		String generateExcludeTable = MjjyConfig.getGenerateExcludeTable();
		List<String> excludeTableList = Func.toStrList(generateExcludeTable);

		while (iterator.hasNext()) {
			Map<String, String> next = iterator.next();
			String tableName = next.get("tableName");
			if (Func.isNotEmpty(tableNameList) && tableNameList.contains(tableName.toLowerCase().trim())) {
				iterator.remove();
				continue;
			}
			//排除一些表结构------------
			for (String excludeTable:excludeTableList) {
				if(excludeTable.endsWith("*")){//模拟查询
					excludeTable=excludeTable.substring(0,excludeTable.length()-1);
					if(tableName.startsWith(excludeTable)){
						iterator.remove();
						break;
					}
				}else if(Func.equals(excludeTable,tableName)){
					iterator.remove();
					break;
				}
			}
		}

		return tableList;
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




	//保存表单数据
	private void saveFormData(List fieldList, String tbname, JSONObject json) {
		Map map = SqlSymbolUtil.getInsertSql(tbname, fieldList, json);
		fieldService.executeInsertSQL(map);
		// 返回子表id,
		json.put("id", MjkjUtils.getMap2Long(map, "id"));
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
