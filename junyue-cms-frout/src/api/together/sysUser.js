import request from '@/utils/request'

const baseUrl = '/admin/sysUser/'

export function getList(params) {
  return request({
    url: baseUrl+'list',
    method: 'post',
    params
  })
}

export function updateSysUser(params) {
  return request({
    url:  baseUrl+'update',
    method: 'post',
    data : params
  })
}

export function addSysUser(params) {
  return request({
    url:  baseUrl+'add',
    method: 'post',
    params: {
      username: params
    }
  })
}

export function deleteSysUser(params) {
  return request({
    url:  baseUrl+'delete',
    method: 'get',
    params: {
      username: params
    }
  })
}

export function addRole(userId, roleIds) {
  return request({
    url:  baseUrl+'addRole',
    method: 'post',
    data: {
      userId: userId,
      roleIds: roleIds
    }
  })
}

export function getRole(userId) {
  return request({
    url:  baseUrl+'getRole',
    method: 'get',
    params: {
      userId: userId
    }
  })
}

export function changePwd(params) {
  return request({
    url:  baseUrl+'changePwd',
    method: 'post',
    data : params
  })
}

export function updateUser(params) {
  return request({
    url:  baseUrl+'updateUser',
    method: 'post',
    data : params
  })
}


export function getUserInfo(params) {
  return request({
      url: baseUrl + 'get',
      method: 'get',
      params: params
  })
}



export function getRoleUser(params){
    return request({
        url:  baseUrl+'listRoleUser',
        method: 'get',
        params: params
    })
}
export function findAllUser(){
  return request({
      url:  baseUrl+'findAllUser',
      method: 'get'
  })
}
export function batchAddUser(params){
  return request({
      url:  baseUrl+'batchAddUser',
      method: 'post',
      data : params
  })
}
export function batchDeleteUser(params){
  return request({
      url:  baseUrl+'batchDeleteUser',
      method: 'post',
      data : params
  })
}
export function findByRoleType(params){
    return request({
        url:  baseUrl+'findByRoleType',
        method: 'post',
        data : params
    })
}
