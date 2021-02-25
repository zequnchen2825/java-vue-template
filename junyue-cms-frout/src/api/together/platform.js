import request from '@/utils/request'

const baseUrl = '/platform/'

export function getList(params) {
  return request({
    url: baseUrl + 'pagelist',
    method: 'get',
    params
  })
}

export function add(params) {
  return request({
    url: baseUrl+'add',
    method: 'post',
    data: params
  })
}

export function update(params) {
  return request({
    url: baseUrl+'update',
    method: 'post',
    data: params
  })
}
