import request from '@/router/axios';
import { apiRequestHead } from '@/config/url.js';


//根据id获取某条数据

export const getFormListDetailApi = (dataId) => {
  return request({
    url: `/api/${apiRequestHead}/desform-api/desform/data/${dataId}`,
    method: 'get',
    params: {},
  })
}
//新增表单数据
export const addFormListApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/desform-api/desform/data/save`,
    method: 'post',
    data,
  })
}
//修改表单数据
export const editFormListApi = (data) => {
  return request({
    url: `/api/${apiRequestHead}/desform-api/desform/data/edit`,
    method: 'post',
    data,
  })
}


//通过表单开发id 查询表单数据
export const getDesFormDataApi = (dataId) => {
  return request({
    url: `/api/${apiRequestHead}/desform-api/desform/data/onlineform/${dataId}`,
    method: 'get',
    params: {},
  })
}
