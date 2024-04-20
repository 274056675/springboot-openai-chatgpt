import request from '@/router/axios';
import { apiRequestHead } from '@/config/url.js';

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
