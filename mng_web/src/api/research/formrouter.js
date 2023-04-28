import request from '@/router/axios';
import { apiRequestHead } from '@/config/url.js';
//获取当前表单设计路由配置列表
export const getFormRouterListApi = (params) => {
  return request({
    url: `/api/${apiRequestHead}/desform-api/desform/route/list`,
    method: 'get',
    params,
  })
}
