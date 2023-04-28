import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/blade-report/report/rest/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const remove = (ids) => {
  return request({
    url: '/api/blade-report/report/rest/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}
