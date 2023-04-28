
package org.springblade.system.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.system.entity.Dict;
import org.springblade.system.enums.DictEnum;
import org.springblade.system.feign.IDictClient;

import java.util.List;
import java.util.Optional;

import static org.springblade.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 字典缓存工具类
 *
 *
 */
public class DictCache {

	private static final String DICT_ID = "dict:id:";
	private static final String DICT_KEY = "dict:key:";
	private static final String DICT_VALUE = "dict:value:";
	private static final String DICT_LIST = "dict:list:";

	private static final Boolean TENANT_MODE = Boolean.FALSE;

	private static IDictClient dictClient;

	private static IDictClient getDictClient() {
		if (dictClient == null) {
			dictClient = SpringUtil.getBean(IDictClient.class);
		}
		return dictClient;
	}

	/**
	 * 获取字典实体
	 *
	 * @param id 主键
	 * @return Dict
	 */
	public static Dict getById(Long id) {
		return CacheUtil.get(DICT_CACHE, DICT_ID, id, () -> {
			R<Dict> result = getDictClient().getById(id);
			return result.getData();
		}, TENANT_MODE);
	}

	/**
	 * 获取字典值
	 *
	 * @param code      字典编号枚举
	 * @param dictValue 字典值
	 * @return String
	 */
	public static String getKey(DictEnum code, String dictValue) {
		return getKey(code.getName(), dictValue);
	}

	/**
	 * 获取字典键
	 *
	 * @param code      字典编号
	 * @param dictValue 字典值
	 * @return String
	 */
	public static String getKey(String code, String dictValue) {
		return CacheUtil.get(DICT_CACHE, DICT_KEY + code + StringPool.COLON, dictValue, () -> {
			List<Dict> list = getList(code);
			Optional<String> key = list.stream().filter(
				dict -> dict.getDictValue().equalsIgnoreCase(dictValue)
			).map(Dict::getDictKey).findFirst();
			return key.orElse(StringPool.EMPTY);
		}, TENANT_MODE);
	}

	/**
	 * 获取字典值
	 *
	 * @param code    字典编号枚举
	 * @param dictKey Integer型字典键
	 * @return String
	 */
	public static String getValue(DictEnum code, Integer dictKey) {
		return getValue(code.getName(), dictKey);
	}


	/**
	 * 获取字典值
	 *
	 * @param code    字典编号
	 * @param dictKey Integer型字典键
	 * @return String
	 */
	public static String getValue(String code, Integer dictKey) {
		return CacheUtil.get(DICT_CACHE, DICT_VALUE + code + StringPool.COLON, String.valueOf(dictKey), () -> {
			R<String> result = getDictClient().getValue(code, String.valueOf(dictKey));
			return result.getData();
		}, TENANT_MODE);
	}

	/**
	 * 获取字典值
	 *
	 * @param code    字典编号枚举
	 * @param dictKey String型字典键
	 * @return String
	 */
	public static String getValue(DictEnum code, String dictKey) {
		return getValue(code.getName(), dictKey);
	}

	/**
	 * 获取字典值
	 *
	 * @param code    字典编号
	 * @param dictKey String型字典键
	 * @return String
	 */
	public static String getValue(String code, String dictKey) {
		return CacheUtil.get(DICT_CACHE, DICT_VALUE + code + StringPool.COLON, dictKey, () -> {
			R<String> result = getDictClient().getValue(code, dictKey);
			return result.getData();
		}, TENANT_MODE);
	}

	/**
	 * 获取字典集合
	 *
	 * @param code 字典编号
	 * @return List<Dict>
	 */
	public static List<Dict> getList(String code) {
		return CacheUtil.get(DICT_CACHE, DICT_LIST, code, () -> {
			R<List<Dict>> result = getDictClient().getList(code);
			return result.getData();
		}, TENANT_MODE);
	}

}
