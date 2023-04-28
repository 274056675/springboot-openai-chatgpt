import request from '@/router/axios';

export const getList = (current, size, params, deptId) => {
  return request({
    url: '/api/blade-user/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
      deptId,
    }
  })
}
export const getAllList = (params = {}) => {
  return request({
    url: '/api/blade-user/allList',
    method: 'get',
    params,
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/blade-user/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/blade-user/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/blade-user/update',
    method: 'post',
    data: row
  })
}

export const updatePlatform = (userId, userType, userExt) => {
  return request({
    url: '/api/blade-user/update-platform',
    method: 'post',
    params: {
      userId,
      userType,
      userExt,
    }
  })
}

export const getUser = (id) => {
  return request({
    url: '/api/blade-user/detail',
    method: 'get',
    params: {
      id,
    }
  })
}

export const getUserPlatform = (id) => {
  return request({
    url: '/api/blade-user/platform-detail',
    method: 'get',
    params: {
      id,
    }
  })
}

export const getUserInfo = () => {
  return request({
    url: '/api/blade-user/info',
    method: 'get',
  })
}

export const resetPassword = (userIds) => {
  return request({
    url: '/api/blade-user/reset-password',
    method: 'post',
    params: {
      userIds,
    }
  })
}

export const updatePassword = (oldPassword, newPassword, newPassword1) => {
  return request({
    url: '/api/blade-user/update-password',
    method: 'post',
    params: {
      oldPassword,
      newPassword,
      newPassword1,
    }
  })
}

export const updateInfo = (row) => {
  return request({
    url: '/api/blade-user/update-info',
    method: 'post',
    data: row
  })
}

export const grant = (userIds, roleIds) => {
  return request({
    url: '/api/blade-user/grant',
    method: 'post',
    params: {
      userIds,
      roleIds,
    }
  })
}

export const unlock = (userIds) => {
  return request({
    url: '/api/blade-user/unlock',
    method: 'post',
    params: {
      userIds,
    }
  })
}
