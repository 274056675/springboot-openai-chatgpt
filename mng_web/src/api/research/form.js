import request from '@/router/axios';
import { apiRequestHead } from '@/config/url.js';

// 根据表单code查询表单id
export const getFormIdApi = (code) => {
  return request({
    url: `/api/${apiRequestHead}/desform-api/desform/code/${code}`,
    method: 'get',
    data: {},
  })
}
//获取当前表单详情数据
export const getdetailDataApi = (headId, lock) => {
  let params = {}
  if (lock) {
    params.lock = lock
  }
  return request({
    url: `/api/${apiRequestHead}/desform-api/desform/${headId}`,
    method: 'get',
    params,
  })
}

//远程取值
export const getRemoteValuesApi = (url) => {
  return request({
    url,
    method: 'get',
    params: {}
  })
}

//填值规则
export const executeRuleByApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/executeRuleByCodeBatch`,
    method: 'put',
    data
  })
}

//获取选择字段远端数据
export const getSelectRemoteDataApi = (url) => {
  if (url.indexOf('/api/') == 0) {
    url = url.replace('/api/', `/api/${apiRequestHead}/`)
  }
  return request({
    url: url,
    method: 'get',
    params: {}
  })
}

//js/css外部增强
export const getJsOrCssStrApi = (url) => {
  return request({
    url,
    method: 'get',
    params: {}
  })
}

export const getActionApi = (url, params = {}, config = {}) => {
  return request({
    url,
    method: 'get',
    params,
    ...config
  })
}
export const postActionApi = (url, data, config = {}) => {
  return request({
    url,
    method: 'post',
    data,
    ...config
  })
}
export const putActionApi = (url, data, config = {}) => {
  return request({
    url,
    method: 'put',
    data,
    ...config
  })
}
export const deleteActionApi = (url, data, config = {}) => {
  return request({
    url,
    method: 'delete',
    data,
    ...config
  })
}
export const requestActionApi = (url, data, method) => {
  let obj = {
    url,
    method,
  }
  if (method == 'get') {
    obj.params = data
  } else {
    obj.data = data
  }
  return request(obj)
}