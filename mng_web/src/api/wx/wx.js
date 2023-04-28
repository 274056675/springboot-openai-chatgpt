import request from '@/router/axios'
import {
  apiRequestHead
} from '@/config/url.js'

//获取微信绑定二维码
export const getWxBindUrlApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/mjkj/water/sy/getBindUserWxUrl`,
    method: 'post',
    params
  })
}
//获取微信绑定列表
export const getWxBindListApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/mjkj/water/sy/getBindUserWxList`,
    method: 'post',
    params
  })
}

//绑定微信
export const setWxBindApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/mjkj/water/sy/bindUserWx`,
    method: 'post',
    params
  })
}

//解绑微信
export const setWxUnBindApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/mjkj/water/sy/unBindUserWx`,
    method: 'post',
    params
  })
}