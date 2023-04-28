import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/blade-system/dict-biz/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getParentList = (current, size, params) => {
  return request({
    url: '/api/blade-system/dict-biz/parent-list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getChildList = (current, size, parentId, params) => {
  return request({
    url: '/api/blade-system/dict-biz/child-list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
      parentId: parentId,
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/blade-system/dict-biz/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/blade-system/dict-biz/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/blade-system/dict-biz/submit',
    method: 'post',
    data: row
  })
}


export const getDict = (id) => {
  return request({
    url: '/api/blade-system/dict-biz/detail',
    method: 'get',
    params: {
      id,
    }
  })
}
export const getDictTree = () => {
  return request({
    url: '/api/blade-system/dict-biz/tree?code=DICT',
    method: 'get'
  })
}

export const getDictionary = (params) => {
  return request({
    url: '/api/blade-system/dict-biz/dictionary',
    method: 'get',
    params,
  })
}
