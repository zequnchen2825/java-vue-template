import request from '@/utils/request'

const baseUrl = '/admin/sysResource/'

export function getList(params) {
    return request({
        url: baseUrl+'list',
        method: 'post',
        params
    })
}
export function getAllMenu(params) {
    return request({
        url: baseUrl+'getAllMenu',
        method: 'post',
        params
    })
}

export function updateSysResource(params) {
    return request({
        url:  baseUrl+'update',
        method: 'post',
        data: params
    })
}

export function addSysResource(params) {
    return request({
        url:  baseUrl+'add',
        method: 'post',
        data: params
    })
}

export function deleteSysResource(params) {
    return request({
        url:  baseUrl+'delete',
        method: 'get',
        params: {
            id: params
        }
    })
}
