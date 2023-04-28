import request from '@/router/axios';
import { apiRequestHead } from '@/config/url.js';


//校验规则编号是否存在
export const verifyCodeApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/duplicate/check`,
    method: 'get',
    params,
  })
}
//获取规则列表
export const getFillRuleDataApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/fill/list`,
    method: 'get',
    params,
  })
}

//新增规则
export const addFillRuleApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/fill/save`,
    method: 'post',
    data,
  })
}
//修改规则
export const editFillRuleApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/fill/update`,
    method: 'post',
    data,
  })
}

//删除规则
export const delectFillRuleApi = (ids) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/fill/remove`,
    method: 'post',
    params: {
      ids
    },
  })
}

//导入
export const importFillRuleApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/fill/importExcel`,
    method: 'post',
    data,
  })
}

//导出
export const exportFillRuleApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/fill/exportXls`,
    method: 'get',
    params,
  })
}

//执行规则
export const carryFillRuleApi = (ruleCode) => {
  return request({
    url: `/api/${apiRequestHead}/sys/sys/fill/executeRuleByCode/${ruleCode}`,
    method: 'post',
    data: {},
  })
}