
package org.springblade.cgform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springblade.cgform.entity.CgformEnhanceSql;
import org.springblade.cgform.entity.CgformField;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.mapper.CgformEnhanceSqlMapper;
import org.springblade.cgform.mapper.SqlMapper;
import org.springblade.cgform.model.CgformEnhanceJavaBatchInter;
import org.springblade.cgform.service.ICgformEnhanceJavaService;
import org.springblade.cgform.service.ICgformEnhanceSqlService;
import org.springblade.cgform.service.ICgformFieldService;
import org.springblade.common.utils.MjkjUtils;
import org.springblade.config.constant.MjkjConstant;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.util.SqlSymbolUtil;
import org.springblade.config.util.converter.ConverterUtil;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * sql增强 服务实现类
 *
 *
 * @since 2021-05-22
 */
@Service
@Slf4j
public class CgformEnhanceSqlServiceImpl extends BaseServiceImpl<CgformEnhanceSqlMapper, CgformEnhanceSql> implements ICgformEnhanceSqlService {



	@Autowired
	private ICgformEnhanceJavaService javaService;


	@Autowired
	private ICgformFieldService cgformFieldService;

	/**
	 * 执行sql增强 ok
	 *
	 * @param buttonCode
	 * @param headId
	 * @param json
	 */
	@Override
	public void executeEnhanceSqlUpdate(String buttonCode, Long headId, JSONObject json) {
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, headId);
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}

		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return;
		}
		String cgbSql =enhanceSql.getCgbSql();
		BladeUser user = AuthUtil.getUser();
		if(Func.isNotEmpty(user)){
			cgbSql = cgbSql.replace("#{default_update_user}", Func.toStr(user.getUserId()));
		}
		cgbSql = cgbSql.replace("#{default_update_time}", DateUtil.format(DateUtil.now(),DateUtil.PATTERN_DATETIME));


		String enhanceSqlStr = SqlSymbolUtil.enhanceSql(cgbSql, json);
		String[] enhanceSqls = enhanceSqlStr.split(";");

		for (String ddl : enhanceSqls) {
			if(Func.isEmpty(ddl) || ddl.toLowerCase().trim().equals("")){
				continue;
			}
			baseMapper.executeDDL(ddl);
		}

	}

	/**
	 * 新增 ok
	 * @param buttonCode
	 * @param headId
	 * @param json
	 */
	@Override
	public void executeEnhanceSqlInsert(String buttonCode, Long headId, JSONObject json) {
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, headId);
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}
		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return;
		}
		String cgbSql =enhanceSql.getCgbSql();
		cgbSql = cgbSql.replace("#{default_id}", IdWorker.getId()+"");//id 默认值
		cgbSql = cgbSql.replace("#{default_pid}",json.getString("id"));
		cgbSql = cgbSql.replace("#{default_uuid}",Func.randomUUID());
		BladeUser user = AuthUtil.getUser();

		if(Func.isNotEmpty(user)){
			cgbSql = cgbSql.replace("#{default_tenant_id}", user.getTenantId());
			cgbSql = cgbSql.replace("#{default_create_user}", Func.toStr(user.getUserId()));
			cgbSql = cgbSql.replace("#{default_update_user}", Func.toStr(user.getUserId()));
			String deptIdStr = user.getDeptId();
			List<Long> deptIdList = Func.toLongList(deptIdStr);
			cgbSql = cgbSql.replace("#{default_create_dept}", Func.toStr(deptIdList.get(0)));//替换值
		}
		cgbSql = cgbSql.replace("#{default_create_time}", DateUtil.format(DateUtil.now(),DateUtil.PATTERN_DATETIME));
		cgbSql = cgbSql.replace("#{default_update_time}", DateUtil.format(DateUtil.now(),DateUtil.PATTERN_DATETIME));

		cgbSql = cgbSql.replace("#{status}", "0");//替换值
		cgbSql = cgbSql.replace("#{is_deleted}", "0");//替换值
		String enhanceSqlStr = SqlSymbolUtil.enhanceSql(cgbSql, json);
		String[] enhanceSqls = enhanceSqlStr.split(";");

		for (String ddl : enhanceSqls) {
			if(Func.isEmpty(ddl) || ddl.toLowerCase().trim().equals("")){
				continue;
			}
			baseMapper.executeDDL(ddl);
		}

	}

	/**
	 * sql增强详情 #{ID} ok
	 * @param head
	 * @param buttonCode
	 * @param resultMap
	 * @param id
	 */
	@Override
	public Map<String, Object> executeEnhanceSqlDetail(CgformHead head,String buttonCode, Map<String, Object> resultMap, String id){
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, head.getId());
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}

		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return resultMap;
		}
		//存在增强
		String cgbSql = enhanceSql.getCgbSql();
		String enhanceSqlStr = cgbSql.replace("#{id}", "'"+id+"'");//替换值
		String[] enhanceSqls = enhanceSqlStr.split(";");
		String selectSql = enhanceSqls[0];
		List<Map<String, Object>> listData = baseMapper.getListData(selectSql);

		if(Func.isNotEmpty(listData)){
			resultMap= listData.get(0);
		}
		return resultMap;
	}

	//sql增强 列表 ok
	@Override
	public void executeEnhanceSqlList(CgformHead head, String buttonCode,  Map<String, Object> params){
		LambdaQueryWrapper<CgformEnhanceSql> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CgformEnhanceSql::getButtonCode, buttonCode);
		wrapper.eq(CgformEnhanceSql::getCgformHeadId, head.getId());
		List<CgformEnhanceSql> cgformEnhanceSqlList = baseMapper.selectList(wrapper);

		Properties props = System.getProperties();
		String property = props.getProperty("spring.application.name");
		CgformEnhanceSql enhanceSql = null;
		for (CgformEnhanceSql enhance : cgformEnhanceSqlList) {
			String serviceName = enhance.getServiceName();
			if (Func.isEmpty(serviceName) || Func.equals(serviceName, "all") || Func.equals(property, serviceName)) {
				enhanceSql = enhance;
				break;
			}
		}

		if (Func.isEmpty(enhanceSql) || Func.isEmpty(enhanceSql.getCgbSql())) {
			return;
		}

		LambdaQueryWrapper<CgformField> fieldWrapper = new LambdaQueryWrapper<>();
		fieldWrapper.eq(CgformField::getCgformHeadId, head.getId());
		fieldWrapper.orderByAsc(CgformField::getOrderNum);
		List<CgformField> fieldList = cgformFieldService.list(fieldWrapper);

		// 根据数据库类型制造额外SQL
		String cgbSql = enhanceSql.getCgbSql();
		String sql = SqlSymbolUtil.getEnhanceByDataType(fieldList, params, null);

		if(cgbSql.contains("#{online_user_id}")){
			cgbSql = cgbSql.replace("#{online_user_id}", "'"+AuthUtil.getUserId()+"'");//id 默认值
		}


		String executeSql =cgbSql.replaceAll("\\$\\{mjkj_where}",sql);

		//分页参数
		Page page = MjkjUtils.getPage(params);

		params.put("executeSql",executeSql);
		Page pages = baseMapper.executeSql(page,executeSql,params);
		MjkjUtils.setPageResult(params, pages);
	}






}
