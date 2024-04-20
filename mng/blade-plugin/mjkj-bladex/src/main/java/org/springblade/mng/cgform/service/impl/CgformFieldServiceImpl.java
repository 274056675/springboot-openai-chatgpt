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
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.mapper.CgformFieldMapper;
import org.springblade.mng.cgform.mapper.CgformHeadMapper;
import org.springblade.mng.cgform.mapper.SqlMapper;
import org.springblade.mng.cgform.model.CommonEntity;
import org.springblade.mng.cgform.model.TreeModel;
import org.springblade.mng.cgform.service.ICgformFieldService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.RedisUtil;
import org.springblade.mng.config.util.ConvertUtils;
import org.springblade.mng.config.util.SqlSymbolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 服务实现类
 *
 * @author BladeX
 * @since 2021-05-20
 */
@Slf4j
@Service
public class CgformFieldServiceImpl extends BaseServiceImpl<CgformFieldMapper, CgformField> implements ICgformFieldService {

    @Autowired
    private CgformHeadMapper headMapper;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private RedisUtil redisUtil;

    public void executeInsertSQL(Map<String, Object> map) {
        sqlMapper.executeInsertSQL(map);
    }


    //todo  流程和权限，有待优化
    @Override
    public List<CgformField> queryAvailableFields(Long headId, String tbname, String taskId, boolean isList) {
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        if (isList) {
            wrapper.eq(CgformField::getIsShowList, 1);
        } else {
            wrapper.eq(CgformField::getIsShowForm, 1);
        }

        wrapper.orderByAsc(CgformField::getOrderNum);
        List fieldList = this.list(wrapper);
        return fieldList;
    }

    @Override
    public List<CgformField> queryAvailableFields(String tbname, boolean isList, List List, List needList) {
        List<CgformField> resultList = new ArrayList();
        String str = "online:" + tbname + "%";
        // LoginUser var7 = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //String var8 = var7.getId();
        List hideList = new ArrayList(); //((OnlCgformFieldMapper)this.baseMapper).selectOnlineHideColumns(var8, var6);
        boolean flag = true;
        if (hideList == null || hideList.size() == 0 || hideList.get(0) == null) {
            flag = false;
        }

        Iterator iterator = List.iterator();

        while (iterator.hasNext()) {
            CgformField field = (CgformField) iterator.next();
            String dbFieldName = field.getDbFieldName();
            if (needList != null && needList.contains(dbFieldName)) {
                field.setIsQuery(1);
                resultList.add(field);
            } else {
                if (isList) {
                    if (field.getIsShowList() != 1) {
                        if (Func.isNotEmpty(field.getMainTable()) && Func.isNotEmpty(field.getMainField())) {
                            resultList.add(field);
                        }
                        continue;
                    }
                } else if (field.getIsShowForm() != 1) {
                    continue;
                }

                if (flag) {
                       /* if (this.b(var13, var9)) {todo
                            var5.add(var12);
                        }*/
                } else {
                    resultList.add(field);
                }
            }
        }

        return resultList;

    }

    @Override
    public List<Map<String, String>> getAutoListQueryInfo(Long headId) {
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        wrapper.eq(CgformField::getIsQuery, 1);
        wrapper.orderByAsc(CgformField::getOrderNum);
        List<CgformField> list = this.list(wrapper);
        ArrayList resultList = new ArrayList();
        int step = 0;

        Iterator<CgformField> iterator = list.iterator();
        while (iterator.hasNext()) {
            HashMap map = new HashMap();
            CgformField field = iterator.next();
            map.put("label", field.getDbFieldTxt());
            map.put("field", field.getDbFieldName());
            map.put("mode", field.getQueryMode());
            if ("1".equals(field.getQueryConfigFlag())) {
                map.put("config", "1");
                map.put("view", field.getQueryShowType());
                map.put("defValue", field.getQueryDefVal());
                if ("cat_tree".equals(field.getFieldShowType())) {
                    map.put("pcode", field.getQueryDictField());
                } else if ("sel_tree".equals(field.getFieldShowType())) {
                    String[] queryDictTexts = field.getQueryDictText().split(",");
                    String dict = field.getQueryDictTable() + "," + queryDictTexts[2] + "," + queryDictTexts[0];
                    map.put("dict", dict);
                    map.put("pidField", queryDictTexts[1]);
                    map.put("hasChildField", queryDictTexts[3]);
                    map.put("pidValue", field.getQueryDictField());
                } else {
                    map.put("dictTable", field.getQueryDictTable());
                    map.put("dictCode", field.getQueryDictField());
                    map.put("dictText", field.getQueryDictText());
                }
            } else {
                map.put("view", field.getFieldShowType());
                if ("cat_tree".equals(field.getFieldShowType())) {
                    map.put("pcode", field.getDictField());
                } else if ("sel_tree".equals(field.getFieldShowType())) {
                    String[] queryDictTexts = field.getDictText().split(",");
                    String dict = field.getDictTable() + "," + queryDictTexts[2] + "," + queryDictTexts[0];
                    map.put("dict", dict);
                    map.put("pidField", queryDictTexts[1]);
                    map.put("hasChildField", queryDictTexts[3]);
                    map.put("pidValue", field.getDictField());
                } else if ("popup".equals(field.getFieldShowType())) {
                    map.put("dictTable", field.getDictTable());
                    map.put("dictCode", field.getDictField());
                    map.put("dictText", field.getDictText());
                }

                map.put("mode", field.getQueryMode());
            }

            ++step;
            if (step > 2) {
                map.put("hidden", "1");
            }

            resultList.add(map);
        }

        return resultList;
    }

    /**
     * 保存树形结构
     *
     * @param headId
     * @param tbname
     * @param json
     * @param hasChildField
     * @param pidField
     */
    @Override
    public String saveTreeFormData(Long headId, String tbname, JSONObject json, String hasChildField, String pidField) {
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        List<CgformField> list = this.list(wrapper);
        Iterator<CgformField> iterator = list.iterator();

        while (iterator.hasNext()) {
            CgformField field = iterator.next();
            if (hasChildField.equals(field.getDbFieldName()) && field.getIsShowForm() != 1) {
                field.setIsShowForm(1);
                json.put(hasChildField, "1");
            } else if (pidField.equals(field.getDbFieldName()) && Func.isEmpty(json.get(pidField))) {
                field.setIsShowForm(1);
                json.put(pidField, "0");
            }
        }

        Map map = SqlSymbolUtil.getInsertSql(tbname, list, json);
        sqlMapper.executeInsertSQL(map);
        if (!"0".equals(json.getString(pidField))) {//树结构
            sqlMapper.editFormData("update " + tbname + " set " + hasChildField + " = '0' where id = '" + json.getString(pidField) + "'");
        }
        return MjkjUtils.getMap2Str(map,"id");
    }


    /**
     * 保存树形数据
     *
     * @param headId
     * @param tbname
     * @param json
     * @param hasChildField
     * @param pidField
     */
    @Override
    public void editTreeFormData(Long headId, String tbname, JSONObject json, String hasChildField, String pidField) {
        String tableName = SqlSymbolUtil.getSubstring(tbname);
        String sql = "select * from " + tableName + " where id = '" + json.getString("id") + "'";
        Map map = sqlMapper.queryFormData(sql);
        Map map2 = SqlSymbolUtil.getValueType(map);
        String pidFieldStr = map2.get(pidField).toString();
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        List<CgformField> list = this.list(wrapper);
        Iterator<CgformField> iterator = list.iterator();
        while (iterator.hasNext()) {
            CgformField field = iterator.next();
            if (pidField.equals(field.getDbFieldName()) && Func.isEmpty(json.get(pidField))) {
                field.setIsShowForm(1);
                json.put(pidField, "0");
            }
        }

        Map updateMap = SqlSymbolUtil.getUpdateSql(tbname, list, json);
        sqlMapper.executeUpdatetSQL(updateMap);
        if (!pidFieldStr.equals(json.getString(pidField))) {
            if (!"0".equals(pidFieldStr)) {
                String countSql = "select count(*) from " + tableName + " where " + pidField + " = '" + pidFieldStr + "'";
                Integer cou = sqlMapper.queryCountBySql(countSql);
                if (cou == null || cou == 0) {
                    sqlMapper.editFormData("update " + tableName + " set " + hasChildField + " = '0' where id = '" + pidFieldStr + "'");
                }
            }

            if (!"0".equals(json.getString(pidField))) {
                sqlMapper.editFormData("update " + tableName + " set " + hasChildField + " = '1' where id = '" + json.getString(pidField) + "'");
            }
        }

    }


    public String saveFormData(Long headId, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        List<CgformField> list = this.list(wrapper);
        Map<String, Object> map = SqlSymbolUtil.getInsertSql(tbname, list, json);
        sqlMapper.executeInsertSQL(map);
        return MjkjUtils.getMap2Str(map,"id");
    }

    public void saveFormDataList(List<CgformField> fieldList, String tableName, JSONObject json) {
        Map map = SqlSymbolUtil.getInsertSql(tableName, fieldList, json);
        sqlMapper.executeInsertSQL(map);
    }


    public void editFormData(Long headId, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        List<CgformField> list = this.list(wrapper);
        if (isCrazy) {
            this.sqlMapper.executeUpdatetSQL(SqlSymbolUtil.getUpdateMap(tbname, list, json));
        } else {
            this.sqlMapper.executeUpdatetSQL(SqlSymbolUtil.getUpdateSql(tbname, list, json));
        }
    }


    @Override
    public Map<String, Object> queryAutolistPage(String tableName, Long headId, Map<String, Object> params, List<String> needList) {
		HashMap<String, Object> resultMap = new HashMap<>();
    	String redisKey="mjkj_sql:tableName_"+tableName+":headId_"+headId+":md5_"+Func.md5Hex(JsonUtil.toJson(params));

    	LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformField::getCgformHeadId, headId);
		wrapper.orderByAsc(CgformField::getOrderNum);
		//获取所以的字段
		List<CgformField> fieldList = this.list(wrapper);

    	String sqlStr="";
		if(redisUtil.hasKey(redisKey)){
			sqlStr = (String) redisUtil.get(redisKey);
		}else{//redis 不存在
			StringBuffer sbf = new StringBuffer();

			// 拼接查询这张表的数据的SQL语句:
			SqlSymbolUtil.getSelect(tableName, fieldList, sbf);
			// 根据数据库类型制造额外SQL
			String sql = SqlSymbolUtil.getByDataType(fieldList, params, needList);
			// 制造额外SQL(查询条件)
			String sql1 = SqlSymbolUtil.getByParams(params);
			// 拼接出完整的SQL查询语句
			sbf.append(" where is_deleted =0  " + sql + sql1);
			// 获取column字段属性
			Object column = params.get("column");
			// column != null, 制造获取排序规则:
			if (column != null) {
				String columnStr = column.toString();
				// 获取排序字段 "asc" / "desc"
				String orderStr = params.get("order").toString();
				// column 等于转换成驼峰命名后的确是表额外属性集合内的某个字段,根据这个字段制造查询排序语句:
				if (this.orderBy(columnStr, fieldList)) {
					String orderBy = ConvertUtils.camelToUnderline(columnStr);
					if(Func.equals(orderBy,"id")){
						orderBy="("+orderBy+" +0 )";
					}
					// 拼接排序语句
					sbf.append(" ORDER BY " + orderBy);
					if ("asc".equals(orderStr)) {
						sbf.append(" asc");
					} else {
						sbf.append(" desc");
					}
				}
			}
			if(params.containsKey("allIdFlag") && MjkjUtils.getMap2Integer(params,"allIdFlag")==1){//获取所有
				sbf=new StringBuffer();
				sbf.append("select id from "+tableName+" t where is_deleted =0  " + sql + sql1);
			}
			sqlStr=sbf.toString();
			//写入redis 5分钟
			redisUtil.set(redisKey,sqlStr,300L, TimeUnit.SECONDS);
		}


		if(params.containsKey("allIdFlag") && MjkjUtils.getMap2Integer(params,"allIdFlag")==1){//获取所有
			List<Map<String, Object>> dataList = sqlMapper.queryListBySqlList(sqlStr);
			// 如果没有查到数据,封装
			resultMap.put("total", 0);
			resultMap.put("fieldList", new ArrayList<>());
			resultMap.put("records",new ArrayList<>());
			resultMap.put("idList",dataList);
			return resultMap;
		}

        // pageSize是否为null?是的话,取10
        Integer pageSzie = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
        // 如果pageSize = -521
        if (pageSzie == -521) {
        	// 根据查询条件查询数据:
            List<Map<String, Object>> dataList = sqlMapper.queryListBySqlList(sqlStr);
            // 如果查询到了数据
            if (dataList != null && dataList.size() != 0) {
                resultMap.put("total", dataList.size());// 封装数据总条数
                resultMap.put("fieldList", fieldList);// 封装表额外字段
				//判断是否包含
				Integer cou = baseMapper.getBlobCou(headId);
				if(Func.isNotEmpty(cou) && cou>0){
					resultMap.put("records", SqlSymbolUtil.handleClob(dataList)); // 封装数据
				}else{
					resultMap.put("records", dataList); // 封装数据
				}
            } else {
                resultMap.put("total", 0);// 如果没有查到数据,封装
                resultMap.put("fieldList", fieldList); // 封装字段集合
                List<Map<String, Object>> list=new ArrayList<>();// 创建ArrayList
                resultMap.put("records",list );// 封装数据为空的ArrayList
            }
        } else {
            Integer pageNo = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
            Page<Map<String, Object>> page = new Page<>(pageNo, pageSzie);// 创建分页对象,封装参数
            IPage<Map<String, Object>> ipage = sqlMapper.selectPageBySqlList(page, sqlStr); // 分页查询
            resultMap.put("total", ipage.getTotal());// 封装总条数
			Integer cou = baseMapper.getBlobCou(headId);
			if(Func.isNotEmpty(cou) && cou>0){
				resultMap.put("records", SqlSymbolUtil.handleClob(ipage.getRecords())); // 封装数据
			}else{
				resultMap.put("records", ipage.getRecords()); // 封装数据
			}
        }

        return resultMap;
    }


    public Map<String, Object> queryAutoTreeNoPage(String tbname, Long headId, Map<String, Object> params, List<String> needList, String pidField) {
        Map<String, Object> resultMap = new HashMap<>();
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        wrapper.orderByAsc(CgformField::getOrderNum);
        List<CgformField> cgformFieldList = this.list(wrapper);
        List<CgformField> availableFieldsList = this.queryAvailableFields(tbname, true, cgformFieldList, needList);
        StringBuffer sb = new StringBuffer();
        SqlSymbolUtil.getSelect(tbname, availableFieldsList, sb);
        String dataTypeStr = SqlSymbolUtil.getByDataType(cgformFieldList, params, needList);
        String paramStr = SqlSymbolUtil.getByParams(params);
        sb.append(" where is_deleted =0  " + dataTypeStr + paramStr);
        Object obj = params.get("column");
        if (obj != null) {
            String column = obj.toString();
            String order = params.get("order").toString();
            if (this.orderBy(column, cgformFieldList)) {
				String orderBy = ConvertUtils.camelToUnderline(column);
				if(Func.equals(orderBy,"id")){
					orderBy="("+orderBy+" +0 )";
				}
                sb.append(" ORDER BY " + orderBy);
                if ("asc".equals(order)) {
                    sb.append(" asc");
                } else {
                    sb.append(" desc");
                }
            }
        }

        Integer pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
        if (pageSize == -521) {
            List<Map<String, Object>> mapList = sqlMapper.queryListBySqlList(sb.toString());
            if ("true".equals(params.get("hasQuery"))) {
                List<Map<String, Object>> newMapList = new ArrayList<>();
                Iterator<Map<String, Object>> iterator = mapList.iterator();

                while (true) {
                    while (iterator.hasNext()) {
                        Map<String, Object> map = iterator.next();
                        String pidFieldStr = map.get(pidField).toString();
                        if (pidFieldStr != null && !"0".equals(pidFieldStr)) {
                            Map<String, Object> var20 = this.a(pidFieldStr, tbname, headId, needList, pidField);
                            if (var20 != null && var20.size() > 0 && !newMapList.contains(var20)) {
                                newMapList.add(var20);
                            }
                        } else if (!newMapList.contains(map)) {
                            newMapList.add(map);
                        }
                    }

                    mapList = newMapList;
                    break;
                }
            }

            log.info("---Online查询sql 不分页 :>>" + sb.toString());
            if (mapList != null && mapList.size() != 0) {
                resultMap.put("total", mapList.size());
                resultMap.put("fieldList", availableFieldsList);
                resultMap.put("records", SqlSymbolUtil.handleClob(mapList));
            } else {
                resultMap.put("total", 0);
                resultMap.put("fieldList", availableFieldsList);
            }
        } else {
            Integer pageNo = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
            Page page = new Page(pageNo, pageSize);
            log.info("---Online查询sql:>>" + sb.toString());
            IPage pageData = sqlMapper.selectPageBySqlList(page, sb.toString());
            resultMap.put("total", pageData.getTotal());
            resultMap.put("records", SqlSymbolUtil.handleClob(pageData.getRecords()));
        }
        List<Map<String, Object>> recordList =( List<Map<String, Object>> ) resultMap.get("records");
        if(Func.isNotEmpty(recordList)){
            for (Map<String, Object> recordMap:recordList) {
                Long id = MjkjUtils.getMap2Long(recordMap,"id");
                String haschildren = (String)recordMap.get("haschildren");
                String sql="select * from "+tbname+" where is_deleted =0 and  pid="+id;
                List<Map<String, Object>> mapList = sqlMapper.queryListBySqlList(sql);
                if(Func.isNotEmpty(mapList)){//有数据
                    if(id!=null && Func.equals(haschildren,"1")){//无数据，
                        String updateSql="update "+tbname+" set haschildren=0 where id="+id;
                        sqlMapper.editFormData(updateSql);
                        recordMap.put("haschildren","0");
                    }
                }else{//没有数据
                    if(id!=null && Func.equals(haschildren,"0")){//有数据，
                        String updateSql="update "+tbname+" set haschildren=1 where id="+id;
                        sqlMapper.editFormData(updateSql);
                        recordMap.put("haschildren","1");
                    }
                }

            }
        }


        return resultMap;
    }

    public List<CgformField> queryFormFieldsByTableName(String tableName) {
        CgformHead head = headMapper.selectOne((Wrapper) (new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, tableName));
        if (Func.isEmpty(head)) {
            return null;
        }
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, head.getId());
        return this.list(wrapper);
    }


    private Map<String, Object> a(String pidFieldStr, String tbname, Long headId, List<String> needList, String pidField) {
        HashMap paramsMap = new HashMap();
        paramsMap.put("id", pidFieldStr);
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        wrapper.orderByAsc(CgformField::getOrderNum);
        List list = this.list(wrapper);
        List availableFieldsList = this.queryAvailableFields(tbname, true, list, needList);
        StringBuffer sb = new StringBuffer();
        SqlSymbolUtil.getSelect(tbname, availableFieldsList, sb);
        String dataType = SqlSymbolUtil.getByDataType(list, paramsMap, needList);
        sb.append(" where is_deleted =0  " + dataType + "and id='" + pidFieldStr + "'");
        List selectList = sqlMapper.queryListBySqlList(sb.toString());
        if (Func.isEmpty(selectList)) {
            return null;
        }
        Map map = (Map) selectList.get(0);
        return map != null && map.get(pidField) != null && !"0".equals(map.get(pidField)) ? this.a(map.get(pidField).toString(), tbname, headId, needList, pidField) : map;
    }

    public List<CgformField> queryFormFields(Long headId, boolean isform) {
        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        if (isform) {
            wrapper.eq(CgformField::getIsShowForm, 1);
        }

        return this.list(wrapper);
    }

    public Map<String, Object> queryFormData(List fieldList, String tbname, String id) {
        String sql = SqlSymbolUtil.getSelectSql(tbname, fieldList, id);
        return sqlMapper.queryFormData(sql);
    }

    /**
     * 获取表单的数据-所有数据
     * @param fieldList
     * @param tbname
     * @return
     */
    @Override
    public List<Map<String, Object>> queryFormAllData(List fieldList, String tbname) {
        String sql = SqlSymbolUtil.getSelectSqlAllData(tbname, fieldList);
        return sqlMapper.queryListBySqlList(sql);
    }


    public List<Map<String, Object>> querySubFormData(List fieldList, String tbname, String linkField, String value) {
        String sql = SqlSymbolUtil.getSelectSql(tbname, fieldList, linkField, value);
        return sqlMapper.queryListData(sql);
    }

    @Override
    public String queryTreeChildIds(CgformHead head, String dataIdStrs) {
        String treeParentIdField = head.getTreeParentIdField();
        String tableName = head.getTableName();
        String[] dataIds = dataIdStrs.split(",");
        StringBuffer sb = new StringBuffer();
        for (String dataId : dataIds) {
            if (Func.isEmpty(dataId) || sb.toString().contains(dataId)) {
                continue;
            }
            if (sb.toString().length() > 0) {
                sb.append(",");
            }

            sb.append(dataId);
            this.getSelectSql(dataId, treeParentIdField, tableName, sb);

        }
        return sb.toString();
    }

    /**
     * 删除主表和子表
     *
     * @param head
     * @param idStrs
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAutoListMainAndSub(CgformHead head, String idStrs) {
    	// 不能删除单表和附表的数据
        if (head.getTableType() != 2) {
            return;
        }
        Long headId = head.getId();
        String tableName = head.getTableName();
        String tableNameStr = "tableName";
        String linkField = "linkField";
        String linkValueStr = "linkValueStr";
        String mainField = "mainField";
        ArrayList mapList = new ArrayList();
        if (Func.isEmpty(head.getSubTableStr())) {
            this.deleteAutoListById(head.getTableName(), idStrs);
            return;
        }
        String[] subTableStrs = head.getSubTableStr().split(",");

        for (String subTableStr : subTableStrs) {
            CgformHead cgformHead = headMapper.selectOne((new LambdaQueryWrapper<CgformHead>()).eq(CgformHead::getTableName, subTableStr));
            if (cgformHead == null) {
                continue;
            }
            LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CgformField::getCgformHeadId, cgformHead.getId());
            wrapper.eq(CgformField::getMainTable, head.getTableName());
            List<CgformField> cfList = this.list(wrapper);
            if (Func.isEmpty(cfList)) {
                continue;
            }
            CgformField cf = cfList.get(0);
            HashMap map = new HashMap();
            map.put(linkField, cf.getDbFieldName());
            map.put(mainField, cf.getMainField());
            map.put(tableNameStr, subTableStr);
            map.put(linkValueStr, "");
            mapList.add(map);
        }

        LambdaQueryWrapper<CgformField> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CgformField::getCgformHeadId, headId);
        List<CgformField> cfList = this.list(wrapper);
        String[] ids = idStrs.split(",");
        int step = 0;

        label52:
        while (true) {
            if (step >= ids.length) {
                Iterator iterator = mapList.iterator();

                while (true) {
                    if (!iterator.hasNext()) {
                        break label52;
                    }

                    Map map = (Map) iterator.next();
                    this.deleteAutoList((String) map.get(tableNameStr), (String) map.get(linkField), (String) map.get(linkValueStr));
                }
            }

            String id = ids[step];
            String sql = SqlSymbolUtil.getSelectSql(tableName, cfList, id);
            Map map = sqlMapper.queryFormData(sql);
            Iterator<Map> iterator = mapList.iterator();
            while (iterator.hasNext()) {
                Map m = iterator.next();
                Object obj = map.get(((String) m.get(mainField)).toLowerCase());
                if (obj == null) {
                    obj = map.get(((String) m.get(mainField)).toUpperCase());
                }

                if (obj != null) {
                    String var23 = (String) m.get(linkValueStr) + String.valueOf(obj) + ",";
                    m.put(linkValueStr, var23);
                }
            }

            ++step;
        }


        this.deleteAutoListById(head.getTableName(), idStrs);


    }

    public void updateTreeNodeNoChild(String tableName, String filed, String id) {
        Map map = SqlSymbolUtil.getUpdateSqlMap(tableName, filed, id);
        this.sqlMapper.executeUpdatetSQL(map);
    }


    @Override
    public List<TreeModel> queryDataListByLinkDown(CommonEntity entity) {
        return sqlMapper.queryDataListByLinkDown(entity);
    }

    public void deleteAutoListById(String tbname, String ids) {
        this.deleteAutoList(tbname, "id", ids);
    }

    @Override
    public void deleteAutoList(String tbname, String linkField, String linkValueStr) {
        if (Func.isEmpty(linkValueStr)) {
            return;
        }
        String[] linkValues = linkValueStr.split(",");
        StringBuffer sb = new StringBuffer();
        for (String linkValue : linkValues) {
            if (Func.isEmpty(linkValue)) {
                continue;
            }
            sb.append("'" + linkValue + "',");
        }

        String likeStr = sb.toString();
        String sql = "UPDATE  " + SqlSymbolUtil.getSubstring(tbname) + " SET is_deleted =-1 where " + linkField + " in(" + likeStr.substring(0, likeStr.length() - 1) + ")";
        sqlMapper.deleteAutoList(sql);
    }

	@Override
	public void deleteList(String tbname, String linkField, String linkValueStr) {
		if (Func.isEmpty(linkValueStr)) {
			return;
		}
		String[] linkValues = linkValueStr.split(",");
		StringBuffer sb = new StringBuffer();
		for (String linkValue : linkValues) {
			if (Func.isEmpty(linkValue)) {
				continue;
			}
			sb.append("'" + linkValue + "',");
		}

		String likeStr = sb.toString();
		String sql = "DELETE FROM  " + SqlSymbolUtil.getSubstring(tbname) + " where " + linkField + " in(" + likeStr.substring(0, likeStr.length() - 1) + ")";
		sqlMapper.deleteList(sql);
	}

    public boolean orderBy(String str, List<CgformField> list) {
        boolean flag = false;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            CgformField field = (CgformField) iterator.next();
            if (ConvertUtils.camelToUnderline(str).equals(field.getDbFieldName())) {
                flag = true;
                break;
            }
        }


        return flag;
    }


    private StringBuffer getSelectSql(String dataId, String treeParentIdField, String tableName, StringBuffer sb) {
        String sql = "select * from " + SqlSymbolUtil.getSubstring(tableName) + " where is_deleted =0 AND  " + treeParentIdField + "= '" + dataId + "'";
        List list = sqlMapper.queryListBySqlList(sql);
        if (Func.isEmpty(list)) {
            return sb;
        }
        Iterator iterator = list.iterator();
        if (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            if (!sb.toString().contains(map.get("id").toString())) {
                sb.append(",").append(map.get("id"));
            }
            this.getSelectSql(map.get("id").toString(), treeParentIdField, tableName, sb);
        }
        return sb;
    }
}
