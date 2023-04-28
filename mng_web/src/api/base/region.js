import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/blade-system/region/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getLazyTree = (parentCode, params) => {
  return request({
    url: '/api/blade-system/region/lazy-tree',
    method: 'get',
    params: {
      ...params,
      parentCode
    }
  })
}

export const getDetail = (code) => {
  return request({
    url: '/api/blade-system/region/detail',
    method: 'get',
    params: {
      code
    }
  })
}

export const remove = (id) => {
  return request({
    url: '/api/blade-system/region/remove',
    method: 'post',
    params: {
      id,
    }
  })
}

export const submit = (row) => {
  return request({
    url: '/api/blade-system/region/submit',
    method: 'post',
    data: row
  })
}

