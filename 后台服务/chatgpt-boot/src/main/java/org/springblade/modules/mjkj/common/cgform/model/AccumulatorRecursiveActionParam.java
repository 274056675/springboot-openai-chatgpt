package org.springblade.modules.mjkj.common.cgform.model;

import lombok.Data;
import org.springblade.modules.mjkj.common.cgform.entity.CgformField;
import org.springblade.modules.mjkj.common.cgform.entity.CgformHead;
import org.springblade.modules.mjkj.common.cgform.mapper.SqlMapper;
import org.springblade.modules.mjkj.common.cgform.service.ICgformEnhanceSqlService;

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
	private org.springblade.modules.mjkj.common.cgform.model.CgformEnhanceJavaBatchInter java;


}
