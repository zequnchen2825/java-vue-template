import request from '@/utils/request'
const baseUrl = '/menu/'

export function getList(params) {
    return request({
        url: baseUrl + 'getList',
        method: 'post',
        data: {}
    })
}

export function updateResource(params) {
    return request({
        url: baseUrl + 'update',
        method: 'post',
        data: params
    })
}

export function addResource(params) {
    return request({
        url: baseUrl + 'save',
        method: 'post',
        data: params
    })
}

export function deleteResource(params) {
    return request({
        url: baseUrl + 'delete',
        method: 'post',
        data: {
            id: params
        }
    })
}
export function findByResource(params) {
    return request({
        url: '/admin/sysUser/findByResource',
        method: 'post',
        data: params
    })
}


export function findRoleByResource(params) {
    return request({
        url: '/role/findByResource',
        method: 'get',
        params: params
    })
}
