import request from '@/utils/request'

const baseUrl = '/admin/parental/system/'

export function getOssInfo(params) {
    return new request({
        url:'/admin/sysUpload/getOssInfo',
        method: 'post',
        data: params
    })
}


export function getVideoAuth(fileName, fileSize, subject) {
    return new request({
        url: '/admin/sysUpload/videoAuth',
        method: 'post',
        data: {
            fileName: fileName,
            fileSize: fileSize,
            subject: subject
        }
    })
}


export function refreshVideoAuth(videoId) {
    return new request({
        url: '/admin/sysUpload/videoRefreshAuth',
        method: 'post',
        data: {
            videoId: videoId
        }
    })
}

export function getServerUrl() {
    return new request({
        url: '/admin/sysUpload/getServerUrl',
        method: 'post',
        data: {}
    })
}

export function getConfigUrlBySubject(params) {
    return new request({
        url: '/admin/sysUpload/getConfigUrlBySubject',
        method: 'post',
        data: params
    })
}

export function getConfigUrlBySubjectForWechat(params) {
  return new request({
    url: '/admin/sysUpload/getConfigUrlBySubjectForWechat',
    method: 'post',
    data: params
  })
}

export function findQrByDepartmentId(params) {
  return new request({
    url: '/admin/sysUpload/findQrByDepartmentId',
    method: 'post',
    data: params
  })
}
