import request from '@/utils/request'
const baseUrl = '/admin/sys/message/'


// 查询系统消息分页列表
export function listPage(params) {
    return request({
        url: baseUrl + 'list',
        method: 'post',
        data: params
    })
}

// 标记消息为已读
export function markAsRead(params) {
    return request({
        url: baseUrl + 'markAsRead',
        method: 'post',
        data: params
    })
}
