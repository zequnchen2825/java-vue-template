import { login, logout, getInfo } from '@/api/login'
import { TokenKey, getToken, setToken, removeToken } from '@/utils/auth'
import qs from 'qs'

const user = {
  state: {
    token: getToken(),
    userName: '',
    currentUserId: '',
    avatar: '',
    roles: [],
    buttons: [],
    permissions: [],
    menus:[]
  },
  getters:{
    getButton: state=> state.buttons
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER_NAME: (state, userName) => {
      state.userName = userName
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_CURRENT_USERID: (state, currentUserId) => {
      state.currentUserId = currentUserId
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_BUTTON: (state, buttons) => {
      state.buttons = buttons;
    },
    SET_MENU: (state, menus) =>{
      state.menus = menus;
    }

  },

  actions: {
    // 登录，返回token放入cookie
    Login({ commit }, userInfo) {
      const account = userInfo.account.trim()
      return new Promise((resolve, reject) => {
        login(qs.stringify({account :account, password:userInfo.password})).then(response => {
          if (response.code != 200) {
            return;
          }
          setToken(TokenKey, response.data)
          commit('SET_TOKEN', response.data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息，获取用户信息，拿到用户名，头像和角色
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const data = response.data
          commit('SET_MENU', data.menus)
          commit('SET_PERMISSIONS', data.permissions)
          commit('SET_ROLES', data.roles)
          commit('SET_USER_NAME', data.user.account)
          commit('SET_CURRENT_USERID', data.user.id)
          setToken('userId', data.currentUserId)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken('userId')
          removeToken(TokenKey)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken(TokenKey)
        removeToken('userId')
        resolve()
      })
    },
    SetButton({ commit }, data) {
      commit('SET_BUTTON', data)
    },
  }
}

export default user
