import request from '@/router/axios';
import crypto from '@/util/crypto.js';
import { apiRequestHead } from '@/config/url.js';
//表单开发获取列表
export const getList = (current, size, params) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/list`,
    method: 'get',
    params: {
      ...params,
      current,
      size
    }
  })
}

//表单开发 获取每个数据详情
export const getDetails = (headId) => {
  return request({
    url: `/api/${apiRequestHead}/cgform-api/detail/listByHeadId`,
    method: 'get',
    params: {
      headId,
    }
  })
}
