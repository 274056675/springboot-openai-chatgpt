
package org.springblade.cgform.service;

import com.alibaba.fastjson.JSONObject;
import org.springblade.config.exception.BusinessException;
import org.springblade.config.exception.DBException;
import org.springblade.cgform.entity.CgformHead;
import org.springblade.cgform.model.CgformModel;
import org.springblade.cgform.model.OnlGenerateModel;
import org.springblade.cgform.model.TreeDataModel;
import org.springblade.core.mp.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 *  服务类
 *
 *
 * @since 2021-05-20
 */
public interface ICgformHeadService extends BaseService<CgformHead> {


    //新增表数据
    String saveManyFormData(CgformHead head, JSONObject jsonobject)
            throws DBException, BusinessException;
    //新增表数据-批量新增
	List<String> saveManyFormDataBatch(CgformHead head, List<JSONObject> jsonobjectList)
		throws DBException, BusinessException;

    //编辑表数据
    void editManyFormData(CgformHead head, JSONObject jsonobject)
            throws DBException, BusinessException;

	//编辑表数据-批量编辑
	void editManyFormDataBatch(CgformHead head, List<JSONObject> jsonobjectList)
		throws DBException, BusinessException;

    //详情
     Map<String, Object> queryManyFormData(Long headId, String id)
            throws DBException;

     //获取树结构所有数据
    List<TreeDataModel> getTreeAllDataList(Long headId)
            throws DBException;

    //删除
     void deleteOneTableInfo(Long headId, String s1)
            throws BusinessException;

     //动态列表查询子表
     List<Map<String, Object>> queryListData(String s);


     //获取所有未生成过的表
     List<Map<String,String>> getAllTable();
}
