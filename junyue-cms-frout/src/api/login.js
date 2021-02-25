import request from "@/utils/request";

const baseUrl = "/login/";

export function login(data) {
  return request({
    url: baseUrl + "login",
    method: "post",
    data
  });
}

export function getInfo(token) {
  return request({
    url: baseUrl + "getInfo",
    method: "post",
    data: { token }
  });
}

export function logout() {
  return request({
    url: baseUrl + "logout",
    method: "post"
  });
}
