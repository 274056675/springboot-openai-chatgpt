package org.springblade.mng.config.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.mng.cgform.entity.CgformEnhanceJava;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.entity.CgformIndex;
import org.springblade.mng.cgform.enums.CgformValidPatternEnum;
import org.springblade.mng.cgform.mapper.CategoryMapper;
import org.springblade.mng.cgform.model.DictModel;
import org.springblade.mng.cgform.model.FieldModel;
import org.springblade.mng.cgform.model.SysPermissionDataRuleModel;
import org.springblade.mng.cgform.model.query.MatchTypeEnum;
import org.springblade.mng.cgform.model.query.QueryGenerator;
import org.springblade.mng.cgform.model.query.QueryRuleEnum;
import org.springblade.mng.cgform.service.ICgformFieldService;
import org.springblade.mng.cgform.service.IDictItemService;
import org.springblade.mng.cgform.service.IDictService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.exception.DBException;
import org.springblade.mng.config.util.jsonschema.BaseColumn;
import org.springblade.mng.config.util.jsonschema.CommonProperty;
import org.springblade.mng.config.util.jsonschema.JsonSchemaDescrip;
import org.springblade.mng.config.util.jsonschema.JsonschemaUtil;
import org.springblade.mng.config.util.jsonschema.validate.*;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class SqlSymbolUtil {

	/**
	 * 获取id
	 *
	 * @return
	 */
	public static Long getIdWorkerId() {
		long id = IdWorker.getId();
		return id;
	}

	/**
	 * 获取查询sql
	 *
	 * @param tableName
	 * @param onlCgformFields
	 * @param map
	 * @return
	 */
	public static String getSelectSql(String tableName, List<CgformField> onlCgformFields, Map<String, Object> map) {
		StringBuffer whereSb = new StringBuffer();//where条件字段
		StringBuffer selectSb = new StringBuffer();//查询字段

		for (CgformField onlCgformField : onlCgformFields) {
			String dbFieldName = onlCgformField.getDbFieldName();
			String dbType = onlCgformField.getDbType();
			if (onlCgformField.getIsShowList() == 1) {
				selectSb.append("," + dbFieldName);
			}


			if (Func.isNotEmpty(onlCgformField.getMainField())) {
				boolean flag = !DataTypeUtil.isNumberType(dbType);
				String sql = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", map.get(dbFieldName), flag);
				if (!"".equals(sql)) {
					whereSb.append(" AND " + sql);
				}
			}

			if (onlCgformField.getIsQuery() != 1) {//是否查询条件0否 1是
				continue;
			}
			if ("single".equals(onlCgformField.getQueryMode())) {//查询模式
				if (map.get(dbFieldName) != null) {
					boolean flag = DataTypeUtil.isNotNumberType(dbType);
					String sql = QueryGenerator.getSingleQueryConditionSql(dbFieldName, "", map.get(dbFieldName), flag);
					if (!"".equals(sql)) {
						whereSb.append(" AND " + sql);
					}
				}
			} else {
				Object beginObj = map.get(dbFieldName + "_begin");
				if (beginObj != null) {
					whereSb.append(" AND " + dbFieldName + ">=");
					if (DataTypeUtil.isNumberType(dbType)) {
						whereSb.append(beginObj.toString());
					} else {
						whereSb.append("'" + beginObj.toString() + "'");
					}
				}

				Object endObj = map.get(dbFieldName + "_end");
				if (endObj != null) {
					whereSb.append(" AND " + dbFieldName + "<=");
					if (DataTypeUtil.isNumberType(dbType)) {
						whereSb.append(endObj.toString());
					} else {
						whereSb.append("'" + endObj.toString() + "'");
					}
				}
			}

		}

		return "SELECT id" + selectSb.toString() + " FROM " + getSubstring(tableName) + " where is_deleted =0  " + whereSb.toString();
	}


	public static boolean equals(Object object1, Object object2) {
		if (Func.isEmpty(object1) && Func.isEmpty(object2)) {
			return true;
		} else {
			return Func.isNotEmpty(object1) && object1.equals(object2);
		}
	}

	/**
	 * 判断表名称和表说明是否一样
	 *
	 * @param onlCgformHead
	 * @param newOnlCgformHead
	 * @return
	 */
	public static boolean onlCgformHeadEquals(CgformHead onlCgformHead, CgformHead newOnlCgformHead) {
		return !equals(onlCgformHead.getTableName(), newOnlCgformHead.getTableName()) || !equals(onlCgformHead.getTableTxt(), newOnlCgformHead.getTableTxt());
	}

	/**
	 * 判断新字段和旧字段是否一致
	 *
	 * @param field
	 * @param newField
	 * @return
	 */
	public static boolean fieldEquals(CgformField field, CgformField newField) {
		return !equals(field.getDbFieldName(), newField.getDbFieldName())
			|| !equals(field.getDbFieldTxt(), newField.getDbFieldTxt())
			|| !equals(field.getDbLength(), newField.getDbLength())
			|| !equals(field.getDbPointLength(), newField.getDbPointLength())
			|| !equals(field.getDbType(), newField.getDbType())
			|| !equals(field.getDbIsNull(), newField.getDbIsNull())
			|| !equals(field.getDbIsKey(), newField.getDbIsKey())
			|| !equals(field.getDbDefaultVal(), newField.getDbDefaultVal());
	}

	/**
	 * 判断新索引和旧索引是否一致
	 *
	 * @param index
	 * @param newIndex
	 * @return
	 */
	public static boolean indexEquals(CgformIndex index, CgformIndex newIndex) {
		return !equals(index.getIndexName(), newIndex.getIndexName())
			|| !equals(index.getIndexField(), newIndex.getIndexField())
			|| !equals(index.getIndexType(), newIndex.getIndexType());
	}


	/**
	 * java增强，判断是否是java类
	 *
	 * @param onlCgformEnhanceJava
	 * @return
	 */
	public static boolean isExistJava(CgformEnhanceJava onlCgformEnhanceJava) {
		String javaType = onlCgformEnhanceJava.getCgJavaType();
		String javaValue = onlCgformEnhanceJava.getCgJavaValue();
		if (Func.isEmpty(javaValue)) {
			return true;
		}
		try {
			if ("class".equals(javaType)) {
				Class clazz = Class.forName(javaValue);
				if (clazz == null || clazz.newInstance() == null) {
					return false;
				}
			} else if ("spring".equals(javaType)) {
				Object bean = SpringContextUtils.getBean(javaValue);
				if (bean == null) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	public static String getSubstring(String s) {
		return Pattern.matches("^[a-zA-z].*\\$\\d+$", s) ? s.substring(0, s.lastIndexOf("$")) : s;
	}

	/**
	 * 将表字段转为json
	 *
	 * @param availableFieldsList
	 * @param disabledFieldsLiast
	 * @param fieldModel
	 * @return
	 */
	public static JSONObject getFiledJson(List<CgformField> availableFieldsList, List<String> disabledFieldsLiast, FieldModel fieldModel) {
		List<String> mustAddList = new ArrayList<>();//必填字段
		List<CommonProperty> hiddenList = new ArrayList<>();//隐藏列表
		List<String> arrayList2 = new ArrayList<>();
		IDictService dictService = SpringContextUtils.getBean(IDictService.class);
		IDictItemService dictItemService = SpringContextUtils.getBean(IDictItemService.class);
		CategoryMapper categoryMapper = SpringContextUtils.getBean(CategoryMapper.class);

		Iterator<CgformField> iterator = availableFieldsList.iterator();

		while (true) {
			CgformField onlCgformField;
			String dbFieldName;
			do {
				do {
					if (!iterator.hasNext()) {//没有下一个
						if (mustAddList.size() > 0) {
							JsonSchemaDescrip jsonSchemaDescrip = new JsonSchemaDescrip(mustAddList);
							return JsonschemaUtil.getJsonSchema(jsonSchemaDescrip, hiddenList);
						} else {
							JsonSchemaDescrip jsonSchemaDescrip = new JsonSchemaDescrip();
							return JsonschemaUtil.getJsonSchema(jsonSchemaDescrip, hiddenList);
						}
					}
					onlCgformField = iterator.next();
					dbFieldName = onlCgformField.getDbFieldName();
				} while ("id".equals(dbFieldName));
			} while (arrayList2.contains(dbFieldName));

			String dbFieldTxt = onlCgformField.getDbFieldTxt();
			if ("1".equals(onlCgformField.getFieldMustInput())) {//字段是否必填
				mustAddList.add(dbFieldName);
			}

			String fieldShowType = onlCgformField.getFieldShowType();
			CommonProperty property = null;
			if ("switch".equals(fieldShowType)) {
				property = new SwitchProperty(dbFieldName, dbFieldTxt, onlCgformField.getFieldExtendJson());
			} else if (isListType(fieldShowType)) {
				List<DictModel> dictModelList = new ArrayList<>();
				if (Func.isNotEmpty(onlCgformField.getDictTable())) {
					dictModelList = dictService.queryTableDictItemsByCode(onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField());
				} else if (Func.isNotEmpty(onlCgformField.getDictField())) {
					dictModelList = dictItemService.queryDictItemsByCode(onlCgformField.getDictField());
				}

				property = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength(), dictModelList);
				if (DataTypeUtil.isNumberType(onlCgformField.getDbType())) {
					property.setType("number");
				}
			} else if (DataTypeUtil.isNumberType(onlCgformField.getDbType())) {
				NumberProperty numberProperty = new NumberProperty(dbFieldName, dbFieldTxt, "number");
				if (CgformValidPatternEnum.INTEGER.getType().equals(onlCgformField.getFieldValidType())) {
					numberProperty.setPattern(CgformValidPatternEnum.INTEGER.getPattern());
				}

				property = numberProperty;
			} else {
				if (!"popup".equals(fieldShowType)) {//表单控件类型
					if ("sel_search".equals(fieldShowType)) {//下拉搜索
						property = new DictProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
					} else if ("link_down".equals(fieldShowType)) {//联动
						LinkDownProperty linkDownProperty = new LinkDownProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable());
						setOtherColumns(linkDownProperty, availableFieldsList, arrayList2);
						property = linkDownProperty;
					} else {
						if ("sel_tree".equals(fieldShowType)) {//树控件
							String dictText = onlCgformField.getDictText();
							if (Func.isNotEmpty(dictText)) {
								String[] dictTexts = dictText.split(",");
								String dictTable = onlCgformField.getDictTable() + "," + dictTexts[2] + "," + dictTexts[0];
								TreeSelectProperty treeSelectProperty = new TreeSelectProperty(dbFieldName, dbFieldTxt, dictTable, dictTexts[1], onlCgformField.getDictField());
								if (dictTexts.length > 3) {
									treeSelectProperty.setHasChildField(dictTexts[3]);
								}
								property = treeSelectProperty;
							}
						} else if ("cat_tree".equals(fieldShowType)) {//分类字典树
							String dictText = onlCgformField.getDictText();
							String dictField = onlCgformField.getDictField();
							String dictTable = "0";
							if (Func.isNotEmpty(dictField) && !"0".equals(dictField)) {
								dictTable = categoryMapper.queryCategoryIdByCode(dictField);
							}

							if (Func.isEmpty(dictText)) {
								property = new TreeSelectProperty(dbFieldName, dbFieldTxt, dictTable);
							} else {
								property = new TreeSelectProperty(dbFieldName, dbFieldTxt, dictTable, dictText);
								HiddenProperty hiddenProperty = new HiddenProperty(dictText, dictText);
								hiddenList.add(hiddenProperty);
							}
						} else if (fieldModel != null && dbFieldName.equals(fieldModel.getFieldName())) {
							String dictText = fieldModel.getTableName() + "," + fieldModel.getTextField() + "," + fieldModel.getCodeField();
							TreeSelectProperty treeSelectProperty = new TreeSelectProperty(dbFieldName, dbFieldTxt, dictText, fieldModel.getPidField(), fieldModel.getPidValue());
							treeSelectProperty.setHasChildField(fieldModel.getHsaChildField());
							treeSelectProperty.setPidComponent(1);
							property = treeSelectProperty;
						} else {
							StringProperty stringProperty = new StringProperty(dbFieldName, dbFieldTxt, fieldShowType, onlCgformField.getDbLength());
							if (Func.isNotEmpty(onlCgformField.getFieldValidType())) {
								CgformValidPatternEnum cgformValidPatternEnum = CgformValidPatternEnum.getPatternInfoByType(onlCgformField.getFieldValidType());
								if (Func.isNotEmpty(cgformValidPatternEnum)) {
									if (CgformValidPatternEnum.NOTNULL == cgformValidPatternEnum) {
										mustAddList.add(dbFieldName);
									} else {
										stringProperty.setPattern(cgformValidPatternEnum.getPattern());
										stringProperty.setErrorInfo(cgformValidPatternEnum.getMsg());
									}
								} else {
									stringProperty.setPattern(onlCgformField.getFieldValidType());
									stringProperty.setErrorInfo("输入的值不合法");
								}
							}

							property = stringProperty;
						}
					}
				} else {
					PopupProperty popupProperty = new PopupProperty(dbFieldName, dbFieldTxt, onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField());
					String dictField = onlCgformField.getDictText();
					if (Func.isNotEmpty(dictField)) {
						String[] dictFields = dictField.split(",");
						for (String dictField1 : dictFields) {
							if (isExistField(dictField1, availableFieldsList)) {
								continue;
							}
							HiddenProperty hiddenProperty = new HiddenProperty(dictField1, dictField1);
							hiddenProperty.setOrder(onlCgformField.getOrderNum());
							hiddenList.add(hiddenProperty);
						}
					}

					property = popupProperty;
				}
			}
			//隐藏字段  是否是只读（1是 0否）
			if (onlCgformField.getIsReadOnly() == 1 || disabledFieldsLiast != null && disabledFieldsLiast.indexOf(dbFieldName) >= 0) {
				property.setDisabled(true);
			}

			if (Func.isNotEmpty(property)) {
				property.setOrder(onlCgformField.getOrderNum());
				property.setDefVal(onlCgformField.getFieldDefaultValue());
				hiddenList.add(property);
			}

		}
	}

	/**
	 * 是否是list类型
	 *
	 * @param fieldShowType
	 * @return
	 */
	public static boolean isListType(String fieldShowType) {
		if ("list".equals(fieldShowType)) {
			return true;
		} else if ("radio".equals(fieldShowType)) {
			return true;
		} else if ("checkbox".equals(fieldShowType)) {
			return true;
		} else {
			return "list_multi".equals(fieldShowType);
		}
	}

	/**
	 * 字段是否存在
	 *
	 * @param onlCgformField
	 * @param onlCgformFieldList
	 * @return
	 */
	public static boolean isExistField(String onlCgformField, List<CgformField> onlCgformFieldList) {
		Iterator<CgformField> iterator = onlCgformFieldList.iterator();

		CgformField field;
		do {
			if (!iterator.hasNext()) {
				return false;
			}

			field = iterator.next();
		} while (!onlCgformField.equals(field.getDbFieldName()));

		return true;
	}

	/**
	 * 设置其他字段
	 *
	 * @param linkDownProperty
	 * @param var1
	 * @param fieldNameList
	 */
	public static void setOtherColumns(LinkDownProperty linkDownProperty, List<CgformField> var1, List<String> fieldNameList) {
		String dictTable = linkDownProperty.getDictTable();
		JSONObject jsonObject = JSONObject.parseObject(dictTable);
		if (Func.isEmpty(jsonObject)) {
			return;
		}
		String linkField = jsonObject.getString("linkField");
		ArrayList list = new ArrayList();
		if (Func.isNotEmpty(linkField)) {
			String[] strs = linkField.split(",");
			Iterator iterator = var1.iterator();

			labelFlag:
			while (true) {
				while (true) {
					if (!iterator.hasNext()) {
						break labelFlag;
					}
					CgformField field = (CgformField) iterator.next();
					String dbFieldName = field.getDbFieldName();
					for (String str : strs) {
						if (str.equals(dbFieldName)) {
							fieldNameList.add(dbFieldName);
							list.add(new BaseColumn(field.getDbFieldTxt(), dbFieldName));
							break;
						}
					}
				}
			}
		}

		linkDownProperty.setOtherColumns(list);
	}

	public static JSONArray getColumns(List<CgformField> fieldList, List<String> fieldNameList) {
		JSONArray resultJson = new JSONArray();
		//ISysBaseAPI var3 = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
		IDictService dictService = SpringContextUtils.getBean(IDictService.class);
		IDictItemService dictItemService = SpringContextUtils.getBean(IDictItemService.class);
		CategoryMapper categoryMapper = SpringContextUtils.getBean(CategoryMapper.class);
		Iterator iterator = fieldList.iterator();

		while (true) {
			CgformField formField;
			String dbFieldName;
			do {
				if (!iterator.hasNext()) {
					return resultJson;
				}

				formField = (CgformField) iterator.next();
				dbFieldName = formField.getDbFieldName();
			} while ("id".equals(dbFieldName));

			JSONObject jsonObject = new JSONObject();
			if (fieldNameList.indexOf(dbFieldName) >= 0) {
				jsonObject.put("disabled", true);
			}

			jsonObject.put("title", formField.getDbFieldTxt());
			jsonObject.put("key", dbFieldName);
			jsonObject.put("width", "186px");
			String formFieldStr = getFieldShowType(formField);
			jsonObject.put("type", formFieldStr);
			if (formFieldStr.equals("file") || formFieldStr.equals("image")) {
				jsonObject.put("responseName", "message");
				jsonObject.put("token", true);
			}

			if (formFieldStr.equals("switch")) {
				jsonObject.put("type", "checkbox");
				JSONArray customValueJson = new JSONArray();
				if (Func.isEmpty(formField.getFieldExtendJson())) {
					customValueJson.add("Y");
					customValueJson.add("N");
				} else {
					customValueJson = JSONArray.parseArray(formField.getFieldExtendJson());
				}

				jsonObject.put("customValue", customValueJson);
			}

			if (formFieldStr.equals("popup")) {
				jsonObject.put("popupCode", formField.getDictTable());
				jsonObject.put("orgFields", formField.getDictField());
				jsonObject.put("destFields", formField.getDictText());
				String dictText = formField.getDictText();
				if (Func.isNotEmpty(dictText)) {
					String[] dictTextStrs = dictText.split(",");
					for (String str : dictTextStrs) {
						if (isExistField(str, fieldList)) {
							continue;
						}
						JSONObject jo = new JSONObject();
						jo.put("title", str);
						jo.put("key", str);
						jo.put("type", "hidden");
						resultJson.add(jo);
					}
				}
			}

			jsonObject.put("defaultValue", formField.getDbDefaultVal());
			jsonObject.put("fieldDefaultValue", formField.getFieldDefaultValue());
			jsonObject.put("placeholder", "请输入" + formField.getDbFieldTxt());
			jsonObject.put("validateRules", getFieldValidType(formField));
			if ("list".equals(formField.getFieldShowType())
				|| "radio".equals(formField.getFieldShowType())
				|| "checkbox_meta".equals(formField.getFieldShowType())
				|| "list_multi".equals(formField.getFieldShowType())
				|| "sel_search".equals(formField.getFieldShowType())) {
				List optionList = new ArrayList();
				if (Func.isNotEmpty(formField.getDictTable())) {
					optionList = dictService.queryTableDictItemsByCode(formField.getDictTable(), formField.getDictText(), formField.getDictField());
				} else if (Func.isNotEmpty(formField.getDictField())) {
					optionList = dictItemService.queryDictItemsByCode(formField.getDictField());
				}

				jsonObject.put("options", optionList);
				if ("list_multi".equals(formField.getFieldShowType())) {
					jsonObject.put("width", "230px");
				}
			}

			resultJson.add(jsonObject);
		}
	}

	/**
	 * 获取字段显示类型
	 *
	 * @param field
	 * @return
	 */
	private static String getFieldShowType(CgformField field) {
		if ("checkbox".equals(field.getFieldShowType())) {
			return "checkbox";
		} else if ("list".equals(field.getFieldShowType())) {
			return "select";
		} else if ("switch".equals(field.getFieldShowType())) {
			return "switch";
		} else if (!"image".equals(field.getFieldShowType()) && !"file".equals(field.getFieldShowType()) && !"radio".equals(field.getFieldShowType()) && !"popup".equals(field.getFieldShowType()) && !"list_multi".equals(field.getFieldShowType()) && !"sel_search".equals(field.getFieldShowType())) {
			if ("datetime".equals(field.getFieldShowType())) {
				return "datetime";
			} else if ("date".equals(field.getFieldShowType())) {
				return "date";
			} else if (DbType.INT.equals(field.getDbType())) {
				return "inputNumber";
			} else {
				return !DbType.DOUBLE.equals(field.getDbType()) && !DbType.BIG_DECIMAL.equals(field.getDbType()) ? "input" : "inputNumber";
			}
		} else {
			return field.getFieldShowType();
		}
	}

	private static JSONArray getFieldValidType(CgformField var0) {
		JSONArray resultJson = new JSONArray();

		if (var0.getDbIsNull() == 0 || "1".equals(var0.getFieldMustInput())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("required", true);
			jsonObject.put("message", var0.getDbFieldTxt() + "不能为空!");
			resultJson.add(jsonObject);
		}

		if (Func.isNotEmpty(var0.getFieldValidType())) {
			JSONObject jsonObject = new JSONObject();
			if ("only".equals(var0.getFieldValidType())) {
				jsonObject.put("pattern", (Object) null);
			} else {
				jsonObject.put("pattern", var0.getFieldValidType());
			}

			jsonObject.put("message", var0.getDbFieldTxt() + "格式不正确");
			resultJson.add(jsonObject);
		}

		return resultJson;
	}

	/**
	 * 获取参数
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		// 获取request的queryParameters数据
		Map<String, String[]> map = request.getParameterMap();
		// 创建新的HashMap
		Map<String, Object> hashMap = new HashMap<>();
		// map转set,才能获取迭代器
		Iterator<Map.Entry<String, String[]>> iterator = map.entrySet().iterator();
		// 迭代器遍历
		while (iterator.hasNext()) {
			// 获取键值对
			Map.Entry<String, String[]> var4 = iterator.next();
			// 获取key
			String key = var4.getKey();
			// 获取值(数组)
			String[] values = var4.getValue();
			// 字符串计数器
			String vauleStr = "";
			// key 不等于 _t 或者 value != null.
			if (!"_t".equals(key) && null != values) {
				// value不是字符数组,转成字符串
				if (!(values instanceof String[])) {
					vauleStr = values.toString();
				} else {
					// value是字符数组,拼接"","",""......
					for (String val : values) {
						vauleStr = val + ",";
					}
					// 忽略字符串末尾的","
					vauleStr = vauleStr.substring(0, vauleStr.length() - 1);
				}
			} else {
				vauleStr = "";
			}
			// 重新封装请求参数:
			hashMap.put(key, vauleStr);
		}
		return hashMap;
	}

	/**
	 * 获取下拉选修
	 * 根据表名和表字段额外属性拼接查询这张表的SQL语句
	 *
	 * @param tableName          表名
	 * @param fieldList 表额外属性集合
	 * @param sbf                StringBuffer
	 */
	public static void getSelect(String tableName, List<CgformField> fieldList, StringBuffer sbf) {
		if (fieldList != null && fieldList.size() != 0) {
			sbf.append("SELECT ");
			int size = fieldList.size();
			boolean idFlag = false;

			for (int i = 0; i < size; ++i) {
				CgformField onlCgformField = fieldList.get(i);
				if ("id".equals(onlCgformField.getDbFieldName())) {
					idFlag = true;
				}

				if ("cat_tree".equals(onlCgformField.getFieldShowType()) && Func.isNotEmpty(onlCgformField.getDictText())) {
					sbf.append(onlCgformField.getDictText() + ",");
				}

				if (i == size - 1) {
					sbf.append(onlCgformField.getDbFieldName() + " ");
				} else {
					sbf.append(onlCgformField.getDbFieldName() + ",");
				}
			}

			if (!idFlag) {
				sbf.append(",id");
			}
		} else {
			sbf.append("SELECT id");
		}

		sbf.append(" FROM " + getSubstring(tableName));
	}

	public static List<DictModel> getYNDict(CgformField onlCgformField) {
		ArrayList<DictModel> dictModels = new ArrayList<>();
		String fieldExtendJson = onlCgformField.getFieldExtendJson();
		String y = "是";
		String n = "否";
		JSONArray jsonArray = JSONArray.parseArray("[\"Y\",\"N\"]");
		if (Func.isNotEmpty(fieldExtendJson)) {
			jsonArray = JSONArray.parseArray(fieldExtendJson);
		}

		DictModel dictModelY = new DictModel(jsonArray.getString(0), y);
		DictModel dictModelN = new DictModel(jsonArray.getString(1), n);
		dictModels.add(dictModelY);
		dictModels.add(dictModelN);
		return dictModels;
	}

	public static String getFilterSql(String dictField) {
		return dictField != null && !"".equals(dictField) && !"0".equals(dictField) ? "CODE like '" + dictField + "%" + "'" : "";
	}

	/**
	 * 获取插入数据语句--ok
	 *
	 * @param tbname
	 * @param fieldList
	 * @param jsonObject
	 * @return
	 */
	public static Map<String, Object> getInsertSql(String tbname, List<CgformField> fieldList, JSONObject jsonObject) {
		StringBuffer fieldSql = new StringBuffer();
		StringBuffer valueSql = new StringBuffer();
		Long idValue = null;
		String databaseType = getDatabaseType();//获取数据库类型

		Map<String, Object> hashMap = new HashMap<>();
		BladeUser createUser = AuthUtil.getUser();//操作人

		Set<String> set = getByShowType(fieldList);//获取要显示的类型
		Iterator<CgformField> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			CgformField onlCgformField = iterator.next();
			String dbFieldName = onlCgformField.getDbFieldName();
			//这几个字段不操作
			if (Func.isEmpty(dbFieldName)) {
				continue;
			}
			if (Func.equals("id", dbFieldName.toLowerCase())) {
				idValue = jsonObject.getLong(dbFieldName);
				if(Func.isEmpty(idValue)){
					idValue=IdWorker.getId();
				}
			} else {
				//创建人，创建时间，创建部门给予默认值
				appendUserData(onlCgformField, createUser, jsonObject, "CREATE_USER", "UPDATE_USER", "CREATE_DEPT", "CREATE_TIME", "UPDATE_TIME", "TENANT_ID", "STATUS", "IS_DELETED");

				if (onlCgformField.getIsShowForm() == 1
					|| Func.isNotEmpty(onlCgformField.getMainField())
					|| Func.isNotEmpty(onlCgformField.getDbDefaultVal())) {
					if (jsonObject.get(dbFieldName) == null) {
						if (Func.isEmpty(onlCgformField.getDbDefaultVal())) {
							continue;
						}
						jsonObject.put(dbFieldName, onlCgformField.getDbDefaultVal());
					}
				}

				if ("".equals(jsonObject.get(dbFieldName))) {
					String str = onlCgformField.getDbType();
					if (DataTypeUtil.isNumberType(str) || DataTypeUtil.isDateType(str)) {
						continue;
					}
				}

				// 拼接SQL: xxx = #{yyy}
				String str = DataTypeUtil.getSql(databaseType, onlCgformField, jsonObject, hashMap);
				if (fieldSql.length() == 0) {
					fieldSql.append(dbFieldName);
					valueSql.append(str);
				} else {
					fieldSql.append("," + dbFieldName);
					valueSql.append("," + str);
				}

			}
		}
		String ddl = "";
		/*if (Func.isEmpty(idValue)) {
			ddl = "insert into " + getSubstring(tbname) + "(" + fieldSql.toString() + ") values(" + valueSql.toString() + ")";
		} else {*/
		ddl = "insert into " + getSubstring(tbname) + "(" + "id," + fieldSql.toString() + ") values(" + idValue + "," + valueSql.toString() + ")";
		//}

		hashMap.put("execute_sql_string", ddl);
		hashMap.put("id", Func.toStr(idValue));
		log.info("--动态表单保存sql-->" + ddl);
		return hashMap;

	}

	/**
	 * 批量插入
	 *
	 * @param tbname
	 * @param fieldList
	 * @return
	 */
	public static Map<String, Object> getInsertBatchSql(String tbname, List<CgformField> fieldList,List<JSONObject> paramList) {
		List<String> fieldSqlList=new ArrayList<>();
		Iterator<CgformField> iterator = fieldList.iterator();
		while (iterator.hasNext()) {
			CgformField onlCgformField = iterator.next();
			String dbFieldName = onlCgformField.getDbFieldName();
			fieldSqlList.add(dbFieldName);
		}
		BladeUser user = AuthUtil.getUser();

		//处理字段
		StringBuffer fieldSql = new StringBuffer();
		for (String dbFieldName:fieldSqlList) {
			if (fieldSql.length() == 0) {
				fieldSql.append(dbFieldName);
			} else {
				fieldSql.append("," + dbFieldName);
			}
		}
		String ddl = "insert into " + getSubstring(tbname) + "(" + fieldSql.toString() + ")";
		//处理值
		List<Map<String,Object>> dataList=new ArrayList<>();
		for (JSONObject paramMap:paramList) {
			Map<String,Object> dataMap=new LinkedHashMap<>();
			for (String dbFieldName:fieldSqlList) {
				if(Func.equals("ID",dbFieldName.toUpperCase())){
					dataMap.put(dbFieldName,getIdWorkerId());
				}else if(Func.equals("CREATE_USER",dbFieldName.toUpperCase())){
					dataMap.put(dbFieldName,user.getUserId());
				}else if(Func.equals("CREATE_DEPT",dbFieldName.toUpperCase())){
					dataMap.put(dbFieldName,user.getDeptId());
				}else if(Func.equals("CREATE_TIME",dbFieldName.toUpperCase())){
					dataMap.put(dbFieldName,DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATETIME));
				}else if(Func.equals("TENANT_ID",dbFieldName.toUpperCase())){
					dataMap.put(dbFieldName,user.getTenantId());
				}else if(Func.equals("STATUS",dbFieldName.toUpperCase())){
					dataMap.put(dbFieldName,0);
				}else if(Func.equals("IS_DELETED",dbFieldName.toUpperCase())){
					int intValue = paramMap.getIntValue(dbFieldName);
					dataMap.put(dbFieldName,intValue);
				}else{
					dataMap.put(dbFieldName,paramMap.get(dbFieldName));
				}

			}
			dataList.add(dataMap);
		}

		Map<String, Object> resultMap=new HashMap<>();
		resultMap.put("ddl",ddl);
		resultMap.put("dataList",dataList);
		return resultMap;

	}

	//默认值
	public static void appendUserData(CgformField onlCgformField, BladeUser loginUser, JSONObject jsonObject, String... strings) {
		String dbFieldName = onlCgformField.getDbFieldName();


		for (int i = 0; i < strings.length; ++i) {
			String str = strings[i];
			if (!dbFieldName.toUpperCase().equals(str)) {
				continue;
			}

			byte bt = -1;
			switch (str) {
				case "CREATE_USER":
					bt = 1;
					break;
				case "CREATE_DEPT":
					bt = 2;
					break;
				case "CREATE_TIME":
					bt = 3;
					break;
				case "UPDATE_USER":
					bt = 4;
					break;
				case "UPDATE_TIME":
					bt = 5;
					break;
				case "TENANT_ID":
					bt = 6;
					break;
				case "STATUS":
					bt = 7;
					break;
				case "IS_DELETED":
					bt = 8;
					break;
			}

			switch (bt) {
				case 1:
					Long userId = null;
					if (Func.isNotEmpty(loginUser)) {
						userId = loginUser.getUserId();
					}
					jsonObject.put(dbFieldName, userId);
					return;
				case 2:
					String deptId = null;
					if (Func.isEmpty(jsonObject.get("create_dept")) && Func.isNotEmpty(loginUser)) {
						deptId = loginUser.getDeptId();
					} else if (Func.isNotEmpty(jsonObject.get("create_dept"))) {
						deptId = jsonObject.getString("create_dept");
					}
					try{
						deptId =Func.toStrList(deptId).get(0);
					}catch (Exception e){

					}
					if(deptId.length()>50){
						deptId=deptId.substring(0,50);
					}
					jsonObject.put(dbFieldName, deptId);
					return;
				case 3:
					onlCgformField.setFieldShowType("datetime");
					jsonObject.put(dbFieldName, DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATETIME));
					return;
				case 4:
					Long updateUserId = null;
					if (Func.isNotEmpty(loginUser)) {
						updateUserId = loginUser.getUserId();
					}
					jsonObject.put(dbFieldName, updateUserId);
					return;
				case 5:
					onlCgformField.setFieldShowType("datetime");
					jsonObject.put(dbFieldName, DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATETIME));
					return;
				case 6:
					String tenantId = null;
					if (Func.isNotEmpty(loginUser)) {
						tenantId = loginUser.getTenantId();
					}
					jsonObject.put(dbFieldName, tenantId);
					return;
				case 7:
					jsonObject.put(dbFieldName, 0);
					return;
				case 8:
					jsonObject.put(dbFieldName, 0);
					return;
			}

			break;

		}

	}

	/**
	 * 获取要显示的字段列表
	 *
	 * @param onlCgformFields
	 * @return
	 */
	public static Set<String> getByShowType(List<CgformField> onlCgformFields) {
		Set<String> hashSet = new HashSet<>();
		Iterator<CgformField> iterator = onlCgformFields.iterator();


		while (iterator.hasNext()) {
			CgformField onlCgformField = iterator.next();
			if ("popup".equals(onlCgformField.getFieldShowType())) {//Popup弹框
				String dictText = onlCgformField.getDictText();
				if (Func.isNotEmpty(dictText)) {
					hashSet.addAll(Arrays.stream(dictText.split(",")).collect(Collectors.toSet()));
				}
			}

			if ("cat_tree".equals(onlCgformField.getFieldShowType())) {//分类字典树
				String dictText = onlCgformField.getDictText();
				if (Func.isNotEmpty(dictText)) {
					hashSet.add(dictText);
				}
			}
		}

		iterator = onlCgformFields.iterator();

		while (iterator.hasNext()) {
			CgformField onlCgformField = iterator.next();
			String fieldName = onlCgformField.getDbFieldName();
			if (onlCgformField.getIsShowForm() == 1 && hashSet.contains(fieldName)) {//把显示字段移除
				hashSet.remove(fieldName);
			}
		}

		return hashSet;
	}

	/**
	 * sql增强
	 *
	 * @param cgbSql
	 * @param json
	 * @return
	 */
	public static String enhanceSql(String cgbSql, JSONObject json) {
		if (json == null) {
			return cgbSql;
		}
		cgbSql = cgbSql.replace("#{UUID}", Func.randomUUID());//uuid
		cgbSql = cgbSql.replace("#{MJPID}", json.getString("id"));//父级id
		Set<String> set = getSqlRuleParams(cgbSql);
		Iterator<String> iterator = set.iterator();

		while (iterator.hasNext()) {
			String str = iterator.next();
			if (json.get(str.toUpperCase()) == null && json.get(str.toLowerCase()) == null) {//json里面没有值
				String sql = QueryGenerator.converRuleValue(str);
				cgbSql = cgbSql.replace("#{" + str + "}", sql);
			} else {
				String sql = null;
				if (json.containsKey(str.toLowerCase())) {
					sql = json.getString(str.toLowerCase());
				} else if (json.containsKey(str.toUpperCase())) {
					sql = json.getString(str.toUpperCase());
				}

				cgbSql = cgbSql.replace("#{" + str + "}", sql);
			}
		}

		return cgbSql;
	}

	/**
	 * sql增强
	 *
	 * @param cgbSql
	 * @return
	 */
	public static String enhanceSql(String cgbSql, Map<String,Object> map) {
		if (Func.isEmpty(map)) {
			return cgbSql;
		}

		Set<String> set = getSqlRuleParams(cgbSql);
		Iterator<String> iterator = set.iterator();

		while (iterator.hasNext()) {
			String str = iterator.next();
			String value = MjkjUtils.getMap2Str(map, str.toLowerCase());
			cgbSql = cgbSql.replace("#{" + str + "}", value);
		}
		return cgbSql;
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
			String group = m.group();
			varParams.add(group.substring(group.indexOf("{") + 1, group.indexOf("}")));
		}
		return varParams;
	}

	/**
	 * 获取数据库类型
	 *
	 * @return
	 */
	private static String getDatabaseType() {
		try {
			return TableUtil.getDatabaseType();
		} catch (SQLException | DBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> getValueType(Map<String, Object> map) {
		HashMap resultMap = new HashMap();
		if (map != null && !map.isEmpty()) {
			Set set = map.keySet();
			Iterator iterator = set.iterator();

			while (iterator.hasNext()) {
				String keyStr = (String) iterator.next();
				Object obj = map.get(keyStr);
				if (obj instanceof Clob) {
					obj = clob2Str((Clob) obj);
				} else if (obj instanceof byte[]) {
					obj = new String((byte[]) ((byte[]) obj));
				} else if (obj instanceof Blob) {
					try {
						if (obj != null) {
							Blob var6 = (Blob) obj;
							obj = new String(var6.getBytes(1L, (int) var6.length()), "UTF-8");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				String key = keyStr.toLowerCase();
				resultMap.put(key, obj);
			}

			return resultMap;
		} else {
			return resultMap;
		}
	}

	public static String clob2Str(Clob clob) {
		String resultStr = "";

		try {
			Reader reader = clob.getCharacterStream();
			char[] chars = new char[(int) clob.length()];
			reader.read(chars);
			resultStr = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultStr;
	}

	public static Map<String, Object> getUpdateSql(String tableName, List<CgformField> onlCgformFields, JSONObject jsonObject) {
		StringBuffer stringBuilder = new StringBuffer();
		Map<String, Object> hashMap = new HashMap<>();
		String databaseType = getDatabaseType();
		BladeUser createUser = AuthUtil.getUser();//操作人
		Set<String> showType = getByShowType(onlCgformFields);
		Iterator<CgformField> iterator = onlCgformFields.iterator();

		while (iterator.hasNext()) {
			CgformField onlCgformField = iterator.next();
			String dbFieldName = onlCgformField.getDbFieldName();
			if (Func.isEmpty(dbFieldName)) {
				log.info("--------online修改表单数据遇见空名称的字段------->>" + onlCgformField.getId());
				continue;
			}

			appendUserData(onlCgformField, createUser, jsonObject, "UPDATE_USER", "UPDATE_TIME");

			if (showType.contains(dbFieldName) && jsonObject.get(dbFieldName) != null && !"".equals(jsonObject.getString(dbFieldName))) {
				String str = DataTypeUtil.getSql(databaseType, onlCgformField, jsonObject, hashMap);
				stringBuilder.append(dbFieldName + "=" + str + ",");
			} else if (!"id".equals(dbFieldName)) {
				if ("".equals(jsonObject.get(dbFieldName))) {
					String str = onlCgformField.getDbType();
					if (DataTypeUtil.isNumberType(str) || DataTypeUtil.isDateType(str)) {
						continue;
					}
				}

				if (!Func.isNotEmpty(onlCgformField.getMainTable()) || !Func.isNotEmpty(onlCgformField.getMainField())) {
					String str = DataTypeUtil.getSql(databaseType, onlCgformField, jsonObject, hashMap);
					stringBuilder.append(dbFieldName + "=" + str + ",");
				}
			}

		}

		String updateField = stringBuilder.toString();
		if (updateField.endsWith(",")) {
			updateField = updateField.substring(0, updateField.length() - 1);
		}

		String sql = "update " + getSubstring(tableName) + " set " + updateField + " where 1=1  " + " AND " + "id" + "='" + jsonObject.getString("id")+"'";
		log.info("--动态表单编辑sql-->" + sql);
		hashMap.put("execute_sql_string", sql);
		return hashMap;

	}

	/**
	 * @param tableName
	 * @param onlCgformFields
	 * @param jsonObject
	 * @param updateFieldList 要更新的字段
	 * @return
	 */
	public static Map<String, Object> getUpdateSql(String tableName, List<CgformField> onlCgformFields, JSONObject jsonObject, String[] updateFieldList) {
		StringBuffer stringBuilder = new StringBuffer();
		Map<String, Object> hashMap = new HashMap<>();
		String databaseType = getDatabaseType();
		BladeUser createUser = AuthUtil.getUser();//操作人
		Set<String> showType = getByShowType(onlCgformFields);
		Iterator<CgformField> iterator = onlCgformFields.iterator();

		while (iterator.hasNext()) {
			CgformField onlCgformField = iterator.next();
			String dbFieldName = onlCgformField.getDbFieldName();
			if (Func.isEmpty(dbFieldName)) {
				log.info("--------online修改表单数据遇见空名称的字段------->>" + onlCgformField.getId());
				continue;
			}
			//要更新的字段
			boolean updateFlag = false;
			for (String updateField : updateFieldList) {
				if (Func.equals(updateField.toUpperCase(), dbFieldName.toUpperCase())) {
					updateFlag = true;
				}
			}
			if (!updateFlag) {
				continue;
			}

			appendUserData(onlCgformField, createUser, jsonObject, "UPDATE_USER", "UPDATE_TIME");

			if (showType.contains(dbFieldName) && jsonObject.get(dbFieldName) != null && !"".equals(jsonObject.getString(dbFieldName))) {
				String str = DataTypeUtil.getSql(databaseType, onlCgformField, jsonObject, hashMap);
				stringBuilder.append(dbFieldName + "=" + str + ",");
			} else if (onlCgformField.getIsShowForm() == 1 && !"id".equals(dbFieldName)) {
				if ("".equals(jsonObject.get(dbFieldName))) {
					String str = onlCgformField.getDbType();
					if (DataTypeUtil.isNumberType(str) || DataTypeUtil.isDateType(str)) {
						continue;
					}
				}

				if (!Func.isNotEmpty(onlCgformField.getMainTable()) || !Func.isNotEmpty(onlCgformField.getMainField())) {
					String str = DataTypeUtil.getSql(databaseType, onlCgformField, jsonObject, hashMap);
					stringBuilder.append(dbFieldName + "=" + str + ",");
				}
			}

		}

		String updateField = stringBuilder.toString();
		if (updateField.endsWith(",")) {
			updateField = updateField.substring(0, updateField.length() - 1);
		}

		String sql = "update " + getSubstring(tableName) + " set " + updateField + " where 1=1  " + " AND " + "id" + "=" + jsonObject.getString("id");
		log.info("--动态表单编辑sql-->" + sql);
		hashMap.put("execute_sql_string", sql);
		return hashMap;

	}

	public static Map<String, Object> getUpdateMap(String tableName, List<CgformField> fields, JSONObject json) {
		StringBuffer sb = new StringBuffer();
		HashMap resultMap = new HashMap();
		BladeUser createUser = AuthUtil.getUser();//操作人

		Iterator<CgformField> iterator = fields.iterator();
		while (iterator.hasNext()) {
			CgformField field = iterator.next();
			String dbFieldName = field.getDbFieldName();
			if (Func.isEmpty(dbFieldName)) {
				log.info("--------online修改表单数据遇见空名称的字段------->>" + field.getId());
				continue;
			}


			if (!"id".equals(dbFieldName) && (json.get(dbFieldName) != null || "UPDATE_USER".equalsIgnoreCase(dbFieldName) || "UPDATE_TIME".equalsIgnoreCase(dbFieldName))) {
				appendUserData(field, createUser, json, "UPDATE_USER", "UPDATE_TIME");
				String dbType;
				if ("".equals(json.get(dbFieldName))) {
					dbType = field.getDbType();
					if (DataTypeUtil.isNumberType(dbType) || DataTypeUtil.isDateType(dbType)) {
						continue;
					}
				}

				dbType = DataTypeUtil.getSql(getDatabaseType(), field, json, resultMap);
				sb.append(dbFieldName + "=" + dbType + ",");
			}
		}

		String str = sb.toString();
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}

		String sql = "update " + getSubstring(tableName) + " set " + str + " where 1=1  " + " AND " + "id" + "=" + "'" + json.getString("id") + "'";
		log.info("--表单设计器表单编辑sql-->" + sql);
		resultMap.put("execute_sql_string", sql);
		return resultMap;

	}

	public static List<Map<String, Object>> handleClob(List<Map<String, Object>> list) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		Iterator<Map<String, Object>> iterator = list.iterator();

		while (iterator.hasNext()) {
			Map<String, Object> map = iterator.next();
			Map<String, Object> resultMap = new HashMap<>();
			Set<String> set = map.keySet();
			Iterator<String> strIterator = set.iterator();

			while (strIterator.hasNext()) {
				String str = strIterator.next();
				Object obj = map.get(str);
				if (obj instanceof Clob) {
					obj = clob2Str((Clob) obj);
				} else if (obj instanceof byte[]) {
					obj = new String((byte[]) ((byte[]) obj));
				} else if (obj instanceof Blob) {
					try {
						if (obj != null) {
							Blob var9 = (Blob) obj;
							obj = new String(var9.getBytes(1L, (int) var9.length()), "UTF-8");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				String lowerCase = str.toLowerCase();
				resultMap.put(lowerCase, obj);
			}

			resultList.add(resultMap);
		}

		return resultList;
	}

	//查本表
	public static String getByDataType(List<CgformField> onlCgformField, Map<String, Object> params, List<String> needList) {
		StringBuffer sbf = new StringBuffer();
		String databaseType = getDatabaseType();//获取数据库类型
		Iterator<CgformField> iterator2 = onlCgformField.iterator();
		while (iterator2.hasNext()){
			CgformField onlCgF = iterator2.next();

			String dbFieldName = onlCgF.getDbFieldName();
			String dbType = onlCgF.getDbType();
			Integer isQuery = onlCgF.getIsQuery();

			if (Func.isNotEmpty(onlCgF.getMainField()) && Func.isNotEmpty(onlCgF.getMainTable())) {
				isQuery=1;
				onlCgF.setQueryMode("single");
			}
			if(Func.isEmpty(isQuery) || isQuery!=1){
				continue;
			}

			String andSql="";
			if ("single".equals(onlCgF.getQueryMode())) {//单个，精确查询
				Object queryValue = params.get(dbFieldName);
				if(Func.isEmpty(queryValue)){
					continue;
				}
				if (DataTypeUtil.isDateType(dbType)) {
					if(Func.equals("DATE".toUpperCase(),dbType.toUpperCase())){//年月日
						String format = DateUtil.format(DateUtil.parse(Func.toStr(queryValue), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
						andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d') ='"+format+"'";
					}else if(Func.equals("DATETIME".toUpperCase(),dbType.toUpperCase())){//年月日时分秒
						String format = DateUtil.format(DateUtil.parse(Func.toStr(queryValue), DateUtil.PATTERN_DATETIME), DateUtil.PATTERN_DATETIME);
						andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d %H:%i:%s') ='"+format+"'";
					}else{//时分秒
						String format = DateUtil.format(DateUtil.parse(Func.toStr(queryValue), DateUtil.PATTERN_TIME), DateUtil.PATTERN_TIME);
						andSql += " AND DATE_FORMAT("+dbFieldName+",'%H:%i:%s') ='"+format+"'";
					}
				}else if (DataTypeUtil.isNumberType(dbType)) {//数字
					andSql += " AND "+dbFieldName+"="+params.get(dbFieldName);
				} else {//其他文本
					andSql += " AND "+dbFieldName+"= '"+params.get(dbFieldName)+"'";
				}
			}else{//模糊查询
				if (DataTypeUtil.isDateType(dbType)) {//日期
					Object qBegin = params.get(dbFieldName + "_begin");
					Object qEnd = params.get(dbFieldName + "_end");
					if(Func.equals("DATE".toUpperCase(),dbType.toUpperCase())){//年月日
						if (Func.isNotEmpty(qBegin)) {//开始
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qBegin), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d') >='"+format+"'";
						}
						if (Func.isNotEmpty(qEnd)) {
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qEnd), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d') <='"+format+"'";
						}
					}else if(Func.equals("DATETIME".toUpperCase(),dbType.toUpperCase())){//年月日时分秒
						if (Func.isNotEmpty(qBegin)) {//开始
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qBegin), DateUtil.PATTERN_DATETIME), DateUtil.PATTERN_DATETIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d %H:%i:%s') >='"+format+"'";
						}
						if (Func.isNotEmpty(qEnd)) {
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qEnd), DateUtil.PATTERN_DATETIME), DateUtil.PATTERN_DATETIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d %H:%i:%s') <='"+format+"'";
						}
					}else {//时分秒
						if (Func.isNotEmpty(qBegin)) {//开始
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qBegin), DateUtil.PATTERN_TIME), DateUtil.PATTERN_TIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%H:%i:%s') >='"+format+"'";
						}
						if (Func.isNotEmpty(qEnd)) {
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qEnd), DateUtil.PATTERN_TIME), DateUtil.PATTERN_TIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%H:%i:%s') <='"+format+"'";
						}
					}
				}else{//其他为like查询
					Object queryValue = params.get(dbFieldName);
					if(Func.isNotEmpty(queryValue)){
						andSql += " AND "+dbFieldName+" like '%"+queryValue+"%'";
					}
				}
			}
			sbf.append(andSql);
		}
		return sbf.toString();

	}

	//查增强sql
	public static String getEnhanceByDataType(List<CgformField> onlCgformField, Map<String, Object> params, List<String> needList) {
		StringBuffer sbf = new StringBuffer();
		String databaseType = getDatabaseType();//获取数据库类型
		Iterator<CgformField> iterator2 = onlCgformField.iterator();
		while (iterator2.hasNext()){
			CgformField onlCgF = iterator2.next();

			String dbFieldName = onlCgF.getDbFieldName();
			String dbType = onlCgF.getDbType();
			Integer isQuery = onlCgF.getIsQuery();

			if (Func.isNotEmpty(onlCgF.getMainField()) && Func.isNotEmpty(onlCgF.getMainTable())) {
				isQuery=1;
				onlCgF.setQueryMode("single");
			}
			if(Func.isEmpty(isQuery) || isQuery!=1){
				continue;
			}

			String eq="#eq#";
			String andSql="";
			if ("single".equals(onlCgF.getQueryMode())) {//单个，精确查询
				Object queryValue = params.get(dbFieldName);
				if(Func.isEmpty(queryValue)){
					continue;
				}

				String fieldName =dbFieldName;
				String fieldValue = Func.toStr(queryValue);
				if(fieldValue.contains(eq)){//额外别名
					fieldName = fieldValue.split(eq)[0];
					fieldValue = fieldValue.split(eq)[1];
				}
				if (DataTypeUtil.isDateType(dbType)) {
					if(Func.equals("DATE".toUpperCase(),dbType.toUpperCase())){//年月日
						String format = DateUtil.format(DateUtil.parse(fieldValue, DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
						andSql += " AND DATE_FORMAT("+fieldName+",'%Y-%m-%d') ='"+format+"'";
					}else if(Func.equals("DATETIME".toUpperCase(),dbType.toUpperCase())){//年月日时分秒
						String format = DateUtil.format(DateUtil.parse(fieldValue, DateUtil.PATTERN_DATETIME), DateUtil.PATTERN_DATETIME);
						andSql += " AND DATE_FORMAT("+fieldName+",'%Y-%m-%d %H:%i:%s') ='"+format+"'";
					}else{//时分秒
						String format = DateUtil.format(DateUtil.parse(fieldValue, DateUtil.PATTERN_TIME), DateUtil.PATTERN_TIME);
						andSql += " AND DATE_FORMAT("+fieldName+",'%H:%i:%s') ='"+format+"'";
					}
				}else if (DataTypeUtil.isNumberType(dbType)) {//数字
					andSql += " AND "+fieldName+"="+fieldValue;
				} else {//其他文本
					andSql += " AND "+fieldName+"= '"+fieldValue+"'";
				}
			}else{//模糊查询
				if (DataTypeUtil.isDateType(dbType)) {//日期
					Object qBegin = params.get(dbFieldName + "_begin");
					Object qEnd = params.get(dbFieldName + "_end");
					if(Func.equals("DATE".toUpperCase(),dbType.toUpperCase())){//年月日
						if (Func.isNotEmpty(qBegin)) {//开始
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qBegin), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d') >='"+format+"'";
						}
						if (Func.isNotEmpty(qEnd)) {
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qEnd), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d') <='"+format+"'";
						}
					}else if(Func.equals("DATETIME".toUpperCase(),dbType.toUpperCase())){//年月日时分秒
						if (Func.isNotEmpty(qBegin)) {//开始
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qBegin), DateUtil.PATTERN_DATETIME), DateUtil.PATTERN_DATETIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d %H:%i:%s') >='"+format+"'";
						}
						if (Func.isNotEmpty(qEnd)) {
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qEnd), DateUtil.PATTERN_DATETIME), DateUtil.PATTERN_DATETIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%Y-%m-%d %H:%i:%s') <='"+format+"'";
						}
					}else {//时分秒
						if (Func.isNotEmpty(qBegin)) {//开始
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qBegin), DateUtil.PATTERN_TIME), DateUtil.PATTERN_TIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%H:%i:%s') >='"+format+"'";
						}
						if (Func.isNotEmpty(qEnd)) {
							String format = DateUtil.format(DateUtil.parse(Func.toStr(qEnd), DateUtil.PATTERN_TIME), DateUtil.PATTERN_TIME);
							andSql += " AND DATE_FORMAT("+dbFieldName+",'%H:%i:%s') <='"+format+"'";
						}
					}
				}else{//其他为like查询
					Object queryValue = params.get(dbFieldName);
					String fieldName =dbFieldName;
					String fieldValue = Func.toStr(queryValue);
					if(fieldValue.contains(eq)){//额外别名
						fieldName = fieldValue.split(eq)[0];
						fieldValue = fieldValue.split(eq)[1];
					}
					if(Func.isNotEmpty(queryValue)){
						andSql += " AND "+fieldName+" like '%"+fieldValue+"%'";
					}
				}
			}
			sbf.append(andSql);
		}
		return sbf.toString();

	}



	//超级查询
	public static String getByParams(Map<String, Object> params) {
		Object object = params.get("superQueryParams");
		if (object != null && !StringUtils.isBlank(object.toString())) {
			ICgformFieldService onlCgformFieldService = SpringContextUtils.getBean(ICgformFieldService.class);
			String s = null;

			try {
				s = URLDecoder.decode(object.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}

			JSONArray jsonArray = JSONArray.parseArray(s);
			Object object1 = params.get("superQueryMatchType");
			MatchTypeEnum matchTypeEnum = MatchTypeEnum.getByValue(object1);
			if (matchTypeEnum == null) {
				matchTypeEnum = MatchTypeEnum.AND;
			}

			Map<String, JSONObject> hashMap = new HashMap<>();
			StringBuilder stringBuilder = (new StringBuilder(" AND ")).append("(");

			for (int i = 0; i < jsonArray.size(); ++i) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String field = jsonObject.getString("field");
				String[] fields = field.split(",");
				if (fields.length == 1) {
					append(stringBuilder, field, jsonObject, matchTypeEnum, (JSONObject) null, i == 0);
				} else if (fields.length == 2) {
					String field1 = fields[0];
					String field2 = fields[1];
					JSONObject jsonObject1 = hashMap.get(field1);
					if (jsonObject1 == null) {
						List<CgformField> cgformFields = onlCgformFieldService.queryFormFieldsByTableName(field1);
						jsonObject1 = new JSONObject(3);
						Iterator<CgformField> iterator = cgformFields.iterator();

						while (iterator.hasNext()) {
							CgformField onlCgformField = iterator.next();
							if (StringUtils.isNotBlank(onlCgformField.getMainTable())) {
								jsonObject1.put("subTableName", field1);
								jsonObject1.put("subField", onlCgformField.getDbFieldName());
								jsonObject1.put("mainTable", onlCgformField.getMainTable());
								jsonObject1.put("mainField", onlCgformField.getMainField());
							}
						}

						hashMap.put(field1, jsonObject1);
					}

					append(stringBuilder, field2, jsonObject, matchTypeEnum, jsonObject1, i == 0);
				}
			}

			return stringBuilder.append(")").toString();
		} else {
			return "";
		}
	}

	private static void rule(String var0, SysPermissionDataRuleModel var1, String var2, String var3, StringBuffer var4) {
		QueryRuleEnum var5 = QueryRuleEnum.getByValue(var1.getRuleConditions());
		boolean var6 = !DataTypeUtil.isNumberType(var3);
		String var7 = a(var1.getRuleValue(), var6);
		if (var7 != null && var5 != null) {
			if ("ORACLE".equalsIgnoreCase(var0) && "Date".equals(var3)) {
				var7 = var7.replace("'", "");
				if (var7.length() == 10) {
					var7 = toDateyMd(var7);
				} else {
					var7 = toDateyMdHms(var7);
				}
			}

			switch (var5) {
				case GT:
					var4.append(" AND " + var2 + ">" + var7);
					break;
				case GE:
					var4.append(" AND " + var2 + ">=" + var7);
					break;
				case LT:
					var4.append(" AND " + var2 + "<" + var7);
					break;
				case LE:
					var4.append(" AND " + var2 + "<=" + var7);
					break;
				case NE:
					var4.append(" AND " + var2 + " <> " + var7);
					break;
				case IN:
					var4.append(" AND " + var2 + " IN " + var7);
					break;
				case LIKE:
					var4.append(" AND " + var2 + " LIKE '%" + QueryGenerator.trimSingleQuote(var7) + "%'");
					break;
				case LEFT_LIKE:
					var4.append(" AND " + var2 + " LIKE '%" + QueryGenerator.trimSingleQuote(var7) + "'");
					break;
				case RIGHT_LIKE:
					var4.append(" AND " + var2 + " LIKE '" + QueryGenerator.trimSingleQuote(var7) + "%'");
					break;
				case EQ:
					var4.append(" AND " + var2 + "=" + var7);
					break;
				default:
					log.info("--查询规则未匹配到---");
			}

		}
	}


	private static void append(StringBuilder stringBuilder, String s, JSONObject jsonObject, MatchTypeEnum matchTypeEnum, JSONObject jsonObject1, boolean b) {
		if (!b) {
			stringBuilder.append(" ").append(matchTypeEnum.getValue()).append(" ");
		}

		String type = jsonObject.getString("type");
		String val = jsonObject.getString("val");
		String sql = appendSpace(type, val);
		QueryRuleEnum queryRuleEnum = QueryRuleEnum.getByValue(jsonObject.getString("rule"));
		if (queryRuleEnum == null) {
			queryRuleEnum = QueryRuleEnum.EQ;
		}

		if (jsonObject1 != null) {
			String subTableName = jsonObject1.getString("subTableName");
			String subField = jsonObject1.getString("subField");
			// String mainTable = jsonObject1.getString("mainTable");
			String mainField = jsonObject1.getString("mainField");
			stringBuilder.append("(").append(mainField).append(" IN (SELECT ").append(subField).append(" FROM ").append(subTableName).append(" WHERE ").append(s);
			appendSymbol(stringBuilder, queryRuleEnum, val, sql, type);
			stringBuilder.append("))");
		} else {
			stringBuilder.append(s);
			appendSymbol(stringBuilder, queryRuleEnum, val, sql, type);
		}

	}

	private static void appendSymbol(StringBuilder stringBuilder, QueryRuleEnum queryRuleEnum, String value, String sql, String dataType) {
		if ("date".equals(dataType) && "ORACLE".equalsIgnoreCase("MYSQL")) {
			sql = sql.replace("'", "");
			if (sql.length() == 10) {
				sql = toDateyMd(sql);
			} else {
				sql = toDateyMdHms(sql);
			}
		}

		switch (queryRuleEnum) {
			case GT:
				stringBuilder.append(">").append(sql);
				break;
			case GE:
				stringBuilder.append(">=").append(sql);
				break;
			case LT:
				stringBuilder.append("<").append(sql);
				break;
			case LE:
				stringBuilder.append("<=").append(sql);
				break;
			case NE:
				stringBuilder.append("!=").append(sql);
				break;
			case IN:
				stringBuilder.append(" IN (");
				String[] values = value.split(",");

				for (int i = 0; i < values.length; ++i) {
					String value1 = values[i];
					if (StringUtils.isNotBlank(value1)) {
						String s = appendSpace(dataType, value1);
						stringBuilder.append(s);
						if (i < values.length - 1) {
							stringBuilder.append(",");
						}
					}
				}

				stringBuilder.append(")");
				break;
			case LIKE:
				stringBuilder.append(" like ").append("N").append("'").append("%").append(value).append("%").append("'");
				break;
			case LEFT_LIKE:
				stringBuilder.append(" like ").append("N").append("'").append("%").append(value).append("'");
				break;
			case RIGHT_LIKE:
				stringBuilder.append(" like ").append("N").append("'").append(value).append("%").append("'");
				break;
			case EQ:
			default:
				stringBuilder.append("=").append(sql);
		}

	}


	private static String appendSpace(String dataType, String value) {
		if (!"int".equals(dataType) && !"number".equals(dataType)) {
			if ("date".equals(dataType)) {
				return "'" + value + "'";
			}
			return "SQLSERVER".equals("MYSQL") ? "N'" + value + "'" : "'" + value + "'";
		} else {
			return value;
		}
	}

	private static String a(String ruleValue, boolean flag) {
		return flag ? "'" + QueryGenerator.converRuleValue(ruleValue) + "'" : QueryGenerator.converRuleValue(ruleValue);
	}

	public static String toDateyMdHms(String dateStr) {
		return " to_date('" + dateStr + "','yyyy-MM-dd HH24:mi:ss')";
	}

	public static String toDateyMd(String dateStr) {
		return " to_date('" + dateStr + "','yyyy-MM-dd')";
	}

	public static String getSelectSql(String tbname, List<CgformField> onlCgformFields, String id) {
		return getSelectSql(tbname, onlCgformFields, "id", id);
	}

	public static String getSelectSqlAllData(String tbname, List<CgformField> onlCgformFields) {
		return getSelectAllDataSql(tbname, onlCgformFields);
	}


	public static String getSelectAllDataSql(String tbname, List<CgformField> onlCgformFields) {
		StringBuffer stringBuilder = new StringBuffer();
		stringBuilder.append("SELECT ");
		int size = onlCgformFields.size();
		boolean idFlag = false;

		for (int i = 0; i < size; ++i) {
			String dbFieldName = onlCgformFields.get(i).getDbFieldName();
			if ("id".equals(dbFieldName)) {
				idFlag = true;
			}

			stringBuilder.append(dbFieldName);
			if (size > i + 1) {
				stringBuilder.append(",");
			}
		}

		if (!idFlag) {
			stringBuilder.append(",id");
		}

		stringBuilder.append(" FROM " + getSubstring(tbname));
		stringBuilder.append(" WHERE is_deleted =0");
		return stringBuilder.toString();
	}


	public static String getSelectSql(String tbname, List<CgformField> onlCgformFields, String field, String value) {
		StringBuffer stringBuilder = new StringBuffer();
		stringBuilder.append("SELECT ");
		int size = onlCgformFields.size();
		boolean idFlag = false;

		for (int i = 0; i < size; ++i) {
			String dbFieldName = onlCgformFields.get(i).getDbFieldName();
			if ("id".equals(dbFieldName)) {
				idFlag = true;
			}

			stringBuilder.append(dbFieldName);
			if (size > i + 1) {
				stringBuilder.append(",");
			}
		}

		if (!idFlag) {
			stringBuilder.append(",id");
		}
		if(Func.equals("id",field)){
			stringBuilder.append(" FROM " + getSubstring(tbname) + " where  " + field + "=" + "'" + value + "'");
		}else{
			stringBuilder.append(" FROM " + getSubstring(tbname) + " where is_deleted =0  " + " AND " + field + "=" + "'" + value + "'");
		}

		return stringBuilder.toString();
	}


	public static Map<String, Object> getUpdateSqlMap(String var0, String fileSql, String id) {
		HashMap<String, Object> map = new HashMap();
		String updateSql = "update " + getSubstring(var0) + " set " + fileSql + "=" + "'" + 0 + "'" + " where 1=1  " + " AND " + "id" + "=" + "'" + id + "'";
		log.info("--修改树节点状态：为无子节点sql-->" + updateSql);
		map.put("execute_sql_string", updateSql);
		return map;
	}


	/**
	 * 格式化文件类型
	 *
	 * @param fieldList
	 * @param imageStr
	 * @return
	 */
	public static List<String> getDbFieldTxt(List<CgformField> fieldList, String imageStr) {
		ArrayList resultList = new ArrayList();
		Iterator<CgformField> iterator = fieldList.iterator();

		while (iterator.hasNext()) {
			CgformField field = iterator.next();
			if (!Func.equals("image", field.getFieldShowType())) {
				continue;
			}
			if (Func.isEmpty(imageStr)) {
				resultList.add(field.getDbFieldTxt());
			} else {
				resultList.add(imageStr + "_" + field.getDbFieldTxt());
			}
		}

		return resultList;
	}

}
