import request from '@/utils/request'

const baseUrl = '/admin/sysConfigGroup/'

export function getList(params) {
  return request({
    url: baseUrl+'list',
    method: 'post',
    params
  })
}

export function updateSysConfigGroup(params) {
  return request({
    url: baseUrl+'update',
    method: 'post',
    data : params
  })
}

export function addSysConfigGroup(params) {
  return request({
    url: baseUrl+'add',
    method: 'post',
    data : params
  })
}

export function deleteSysConfigGroup(params) {
  return request({
    url: baseUrl+'delete',
    method: 'get',
    params: {
      id: params
    }
  })
}
