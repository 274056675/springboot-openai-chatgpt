import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/blade-resource/sms/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/blade-resource/sms/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/blade-resource/sms/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/blade-resource/sms/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/blade-resource/sms/submit',
    method: 'post',
    data: row
  })
}

export const enable = (id) => {
  return request({
    url: '/api/blade-resource/sms/enable',
    method: 'post',
    params: {
      id
    }
  })
}

export const send = (code, phones, params) => {
  return request({
    url: '/api/blade-resource/sms/endpoint/send-message',
    method: 'post',
    params: {
      code,
      phones,
      params,
    }
  })
}

