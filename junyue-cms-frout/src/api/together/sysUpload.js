import request from '@/utils/request'

const baseUrl = '/admin/sysUpload/'


export function getOssInfo(params){
  return new request({
    url:baseUrl +'getOssInfo',
    method:'post',
    data:params
  })
}


export function getVideoAuth(fileName, fileSize) {
  return new request({
      url: baseUrl + 'videoAuth',
      method: 'post',
      data: {
          fileName: fileName,
          fileSize: fileSize
      }
  })
}


export function refreshVideoAuth(videoId) {
  return new request({
      url: baseUrl + 'videoRefreshAuth',
      method: 'post',
      data: {
          videoId: videoId
      }
  })
}


