import request from '@/utils/request'

const baseUrl = '/admin/sysConfig/'

export function getList(params) {
  return request({
    url: baseUrl + 'list',
    method: 'post',
    params
  })
}

export function updateSysConfig(params) {
  return request({
    url: baseUrl + 'update',
    method: 'post',
    data : params
  })
}

export function addSysConfig(params) {
  return request({
    url: baseUrl + 'add',
    method: 'post',
    data : params
  })
}

export function deleteSysConfig(params) {
  return request({
    url: baseUrl + 'delete',
    method: 'get',
    params: {
      id: params
    }
  })
}
