package org.springblade.mng.cgform.model;

import lombok.Data;

import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.mapper.SqlMapper;
import org.springblade.mng.cgform.service.ICgformEnhanceSqlService;

import java.util.List;
import java.util.Map;

@Data
public class AccumulatorRecursiveActionParam {

	private ICgformEnhanceSqlService sqlService;
	private CgformHead head;
	private List<CgformField> cgformFieldList;
	private List<Map<String, Object>> importList;
	private String batchCode;
	private SqlMapper sqlMapper;
	private Map<String, Object> otherMap;
	private CgformEnhanceJavaBatchInter java;


}
