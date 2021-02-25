import request from '@/utils/request'

const baseUrl = '/role/'
const baseMenuUrl = '/role/menu/'

export function getList(params) {
    return request({
        url: baseUrl+'pageList',
        method: 'post',
        params
    })
}

export function updateSysRole(params) {
    return request({
        url: baseUrl+'update',
        method: 'post',
        data: params
    })
}

export function addSysRole(params) {
    return request({
        url: baseUrl+'save',
        method: 'post',
        data: params
    })
}

export function deleteSysRole(params) {
    return request({
        url: baseUrl+'delete',
        method: 'get',
        params: {
            id: params
        }
    })
}

export function addResource(roleId, resourceIds) {
    return request({
        url: baseMenuUrl+'add',
        method: 'post',
        data: {
            roleId : roleId,
            resourceIds : resourceIds
        }
    })
}

export function getResource(roleId) {
    return request({
        url: baseMenuUrl+'list',
        method: 'get',
        params: {
            roleId: roleId
        }
    })
}

export function getRoleByResourceId(params) {
    return request({
        url: baseMenuUrl+'listRoleByResoureId',
        method: 'get',
        params: params
    })
}


export function findUserByRole(params) {
    return request({
        url: '/admin/sysUser/findRoleUser',
        method: 'get',
        params: params
    })
}

