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

export const getListApiScope = (current, size, params) => {
  return request({
    url: '/api/blade-system/api-scope/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const removeApiScope = (ids) => {
  return request({
    url: '/api/blade-system/api-scope/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const addApiScope = (row) => {
  return request({
    url: '/api/blade-system/api-scope/submit',
    method: 'post',
    data: row
  })
}

export const updateApiScope = (row) => {
  return request({
    url: '/api/blade-system/api-scope/submit',
    method: 'post',
    data: row
  })
}

export const getMenuApiScope = (id) => {
  return request({
    url: '/api/blade-system/api-scope/detail',
    method: 'get',
    params: {
      id,
    }
  })
}
