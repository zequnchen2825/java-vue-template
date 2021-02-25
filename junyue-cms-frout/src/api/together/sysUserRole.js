import request from '@/utils/request'

const baseUrl = '/admin/sysUserRole/'

export function getList(params) {
  return request({
    url: baseUrl + 'list',
    method: 'post',
    params
  })
}

export function updateSysUserRole(params) {
  return request({
    url: baseUrl +'update',
    method: 'post',
    data : params
  })
}

export function addSysUserRole(params) {
  return request({
    url: baseUrl +'add',
    method: 'post',
    data : params
  })
}

export function deleteSysUserRole(params) {
  return request({
    url: baseUrl +'delete',
    method: 'get',
    params: {
      id: params
    }
  })
}
