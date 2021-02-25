import request from '@/utils/request'

const baseUrl = '/admin/sysRoleResource/'

export function getList(params) {
  return request({
    url: baseUrl+'list',
    method: 'post',
    params
  })
}

export function addSysRoleResource(params) {
  return request({
    url: baseUrl+'add',
    method: 'post',
    data : params
  })
}
export function deleteSysRoleResource(id) {
  return request({
    url: baseUrl+'delete',
    method: 'get',
    params : {
      id : id
    }
  })
}
