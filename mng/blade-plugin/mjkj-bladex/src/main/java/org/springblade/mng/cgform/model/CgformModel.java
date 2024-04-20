package org.springblade.mng.cgform.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.mng.cgform.entity.CgformField;
import org.springblade.mng.cgform.entity.CgformHead;
import org.springblade.mng.cgform.entity.CgformIndex;

import java.util.List;

/**
 * 表单新增实体
 */
@Data
public class CgformModel {
    @ApiModelProperty(value = "基本内容")
    private CgformHead head;

    @ApiModelProperty(value = "属性列表")
    private List<CgformField> fields;

    @ApiModelProperty(value = "索引列表")
    private List<CgformIndex> indexs;

    @ApiModelProperty(value = "删除字段id")
    private List<String> deleteFieldIds;

    @ApiModelProperty(value = "删除字段索引")
    private List<String> deleteIndexIds;
}
