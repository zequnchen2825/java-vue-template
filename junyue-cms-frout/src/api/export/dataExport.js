import request from '@/utils/request'


export function queryForList(params) {
    return new request({
        url: '/admin/export/task/list',
        method: 'post',
        data: params
    })
}
