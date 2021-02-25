import Cookies from 'js-cookie'

import store from '@/store'

// SESSION 和http session redis共享不一致，导致两个系统不能共享sessionId
export const TokenKey = 'session'

export function getToken(key = TokenKey) {
  return Cookies.get(TokenKey)
}

export function setToken(key = TokenKey, token) {
  return Cookies.set(key, token)
}

export function removeToken(key = TokenKey) {
  return Cookies.remove(key)
}


export function hasPermission(permission) {
  const permissions = store.state.user.permissions;
  return (typeof permission === 'string') ? permissions.includes(permission) : false;
}


export function hasRole(role) {
  const roles = store.state.user.roles;
  return (typeof role === 'string') ? roles.includes(role) : false;
}

export function hasAnyRole(roles) {
  if (!Array.isArray(roles)) {
    return false;
  }

  for (let idx in roles) {
    if (hasRole(roles[idx])) {
      return true;
    }
  }

  return false;
}

