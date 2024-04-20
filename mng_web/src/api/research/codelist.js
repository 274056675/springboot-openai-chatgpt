import request from '@/router/axios';
import { apiRequestHead } from '@/config/url.js';


//获取表头信息
export const getFormHeadApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/getColumns/${params.headId}`,
    method: 'get',
    params
  })
}
//获取字段信息
export const getFormFieldApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/getFormItem/${params.headId}`,
    method: 'get',
    params
  })
}

//获取数据列表 pageSzie = -521 不分页
export const getDataApi = (headId, params) => {
  // 排序
  if (!params.column && !params.order) {
    params.column = 'id'
    params.order = 'desc'
  }

  return request({
    url: `/api/${apiRequestHead}/cgform-api/getData/${headId}`,
    method: 'get',
    params
  })
}
//获取树表格数据列表
export const getTreeDataApi = (headId, params) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/getTreeData/${headId}`,
    method: 'get',
    params
  })
}
//获取树结构的所有数据
export const getTreeAllDataApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/loadTreeData`,
    method: 'get',
    params
  })
}
//获取树结构当前节点显示文本名
export const getTreeItemDataApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dictitem/loadDictItem/${params.tableName},${params.tableLine},${params.rowKey}`,
    method: 'get',
    params: {
      key: params.key
    }
  })
}

//获取树结构数据包涵所有子节点
export const getAllTreeDataApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/treeAllData/${params}`,
    method: 'get',
  })
}

//获取字典数据
export const getDicTableData = (dictCode) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dict/getDictItems/${dictCode}`,
    method: 'get',
    params: {}
  })
}
//获取表格字典数据
export const getTableDicData = (table, label, value) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dict/getDict/${table},${label},${value}`,
    method: 'get',
    params: {
      keyword: '',
    }
  })
}

//获取数据详情
export const getDataDetailApi = (headId, id, params) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/detailData/${headId}/${id}`,
    method: 'get',
    params,
  })
}


//新增数据
export const addDataApi = (headId, data) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/addData/${headId}`,
    method: 'post',
    data
  })
}
//编辑数据
export const editDataApi = (headId, data) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/editData/${headId}`,
    method: 'post',
    data
  })
}
// 删除数据
export const delDataApi = (headId, ids) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/delete/form/${headId}/${ids}`,
    method: 'post',
    data: {}
  })
}

//导出
export const exportDataApi = (headId, params) => {
  return request({
    url: `/api/${apiRequestHead}/excel-api/exportXls/${headId}`,
    method: 'get',
    responseType: 'blob',
    params,
  })
}

//导入
export const importDataApi = (headId, formData) => {
  return request({
    url: `/api/${apiRequestHead}/excel-api/importXls/${headId}`,
    method: 'post',
    headers: { "Content-Type": "multipart/form-data" },
    data: formData,
  })
}
//导入模板
export const importDataTemplateApi = (headId) => {
  return request({
    url: `/api/${apiRequestHead}/excel-api/exportXlsTemplate/${headId}`,
    method: 'get',
    responseType: 'blob',
    params: {},
  })
}

//上传文件
export const uploadeFileApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/upload/file`,
    method: 'post',
    data,
  })
}
//获取上传文件接口名
export const getUploadeFileNameApi = (link) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/get/original/name`,
    method: 'get',
    params: {
      link
    },
  })
}


// sql增强触发接口
export const touchSqlEnhanceApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-java/cgformenhance/doButton`,
    method: 'post',
    data,
  })
}


//获取附表erp配置
export const getErpColumnsApi = (headId) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/getErpColumns/${headId}`,
    method: 'get',
    params: {},
  })
}


//获取所有数据
export const getActionApi = (url, params) => {
  return request({
    url: `/api/${url}`,
    method: 'get',
    ...params
  })
}
//新增
export const postActionApi = (url, params) => {
  return request({
    url: `/api/${url}`,
    method: 'post',
    ...params
  })
}
//删除
export const deleteActionApi = (url, params) => {
  return request({
    url: `/api/${url}`,
    method: 'delete',
    ...params
  })
}