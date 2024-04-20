import request from '@/router/axios';

export const getListDataScope = (current, size, params) => {
  return request({
    url: '/api/blade-system/data-scope/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const removeDataScope = (ids) => {
  return request({
    url: '/api/blade-system/data-scope/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const addDataScope = (row) => {
  return request({
    url: '/api/blade-system/data-scope/submit',
    method: 'post',
    data: row
  })
}

export const updateDataScope = (row) => {
  return request({
    url: '/api/blade-system/data-scope/submit',
    method: 'post',
    data: row
  })
}

export const getMenuDataScope = (id) => {
  return request({
    url: '/api/blade-system/data-scope/detail',
    method: 'get',
    params: {
      id,
    }
  })
}

