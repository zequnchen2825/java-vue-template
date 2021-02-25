import request from '@/utils/request'


const baseUrl = '/admin/login/'

export function login(userName, password) {
  return request({
    url: baseUrl+'authLogin',
    method: 'post',
    data: {
      userName,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: baseUrl+'info',
    method: 'post',
    data: { token }
  })
}

export function logout() {
  return request({
    url: baseUrl+'logout',
    method: 'post'
  })
}
