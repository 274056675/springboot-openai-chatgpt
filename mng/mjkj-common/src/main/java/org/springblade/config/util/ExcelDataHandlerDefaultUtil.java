package org.springblade.config.util;


import org.springblade.config.autopoi.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.springblade.config.autopoi.poi.util.PoiPublicUtil;
import org.springblade.cgform.entity.CgformField;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Excel处理时间公共类
 */
public class ExcelDataHandlerDefaultUtil extends ExcelDataHandlerDefaultImpl {
	Map<String, CgformField> onlCgformFieldMap;
	String online;

	public ExcelDataHandlerDefaultUtil(List<CgformField> fields) {
		this.onlCgformFieldMap = this.getOnlCgformFieldMapByFields(fields);
		this.online = "online";
	}

	private Map<String, CgformField> getOnlCgformFieldMapByFields(List<CgformField> fields) {
		Map<String, CgformField> result = new HashMap<>();
		Iterator<CgformField> iterator = fields.iterator();

		while (iterator.hasNext()) {
			CgformField field = iterator.next();
			result.put(field.getDbFieldTxt(), field);
		}

		return result;
	}

	public void setMapValue(Map<String, Object> map, String originKey, Object value) {
		String key = this.getKey(originKey);
		if (value instanceof Double) {
			map.put(key, PoiPublicUtil.doubleToString((Double) value));
		} else if (value instanceof byte[]) {
			byte[] var5 = (byte[]) ((byte[]) value);
			/*String var6 = SqlSymbolUtil.uploadOnlineImage(var5, this.upLoadPath, this.online,this.uploadType);
			if (var6 != null) {
				map.put(key, var6);
			}*///todo
		} else {
			map.put(key, value == null ? "" : value.toString());
		}

	}

	private String getKey(String originKey) {
		return this.onlCgformFieldMap.containsKey(originKey)
				? "$mainTable$" + ((CgformField) this.onlCgformFieldMap.get(originKey)).getDbFieldName()
				: "$subTable$" + originKey;
	}
}
