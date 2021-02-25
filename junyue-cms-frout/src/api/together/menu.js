import request from '@/utils/request'

const baseUrl = '/menu'

export function getList(params) {
  return request({
    url: baseUrl + '/getList',
    method: 'get',
    params
  })
}
export function getListByRoleId(params) {
  return request({
    url: baseUrl + '/getListByRoleId',
    method: 'get',
    params
  })
}

export function getClientMenuList(params) {
  return request({
    url: baseUrl+'list',
    method: 'post',
    data: params
  })
}

export function getClientMenuConfig(params) {
  return request({
    url: baseUrl+'config',
    method: 'post',
    data: params
  })
}

export function rollbackClientMenu(params) {
  return request({
    url: baseUrl+'rollback',
    method: 'post',
    data: params
  })
}
