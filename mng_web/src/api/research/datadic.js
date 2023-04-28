import request from '@/router/axios';
import { apiRequestHead } from '@/config/url.js';

//获取字典列表
export const getDicDataApi = (current, size, params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dict/list`,
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}

//添加字典
export const addDicDataApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dict/save`,
    method: 'post',
    data
  })
}

//修改字典
export const editDicDataApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dict/update`,
    method: 'post',
    data
  })
}

//删除字典
export const delDicDataApi = (ids) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dict/remove`,
    method: 'post',
    params: {
      ids
    }
  })
}





//获取字典配置列表
export const getDicListDataApi = (current, size, params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dictitem/list`,
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}

//添加字典配置
export const addDicListDataApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dictitem/save`,
    method: 'post',
    data
  })
}

//修改字典配置
export const editDicListDataApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dictitem/update`,
    method: 'post',
    data
  })
}

//删除字典配置
export const delDicListDataApi = (ids) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/dictitem/remove`,
    method: 'post',
    params: {
      ids
    }
  })
}



//获取分类字典列表
export const getTreeDicDataApi = (current, size, params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/category/list`,
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}

//添加分类字典
export const addTreeDicDataApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/category/save`,
    method: 'post',
    data
  })
}

//修改分类字典
export const editTreeDicDataApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/category/update`,
    method: 'post',
    data
  })
}

//删除分类字典
export const delTreeDicDataApi = (ids) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/category/remove`,
    method: 'post',
    params: {
      ids
    }
  })
}

//查询分类字典子集
export const getTreeChildeDicDataApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/category/childList`,
    method: 'get',
    params
  })
}
