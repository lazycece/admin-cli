/**
 * @author lazy c
 * @email lazycece@gmail.com
 * 2018/03/16
 */

import request from '../request'

export function login(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/u/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/u/logout',
    method: 'post'
  })
}

export function getUserInfo() {
  return request({
    url: '/u/info',
    method: 'get'
  })
}

export function updateInfo(data) {
  return request({
    url: '/u/updateSelfInfo',
    method: 'post',
    data
  })
}

export function confirmPwd(data) {
  return request({
    url: '/u/confirmPassword',
    method: 'post',
    data
  })
}

export function updatePwd(data) {
  return request({
    url: '/u/changePassword',
    method: 'post',
    data
  })
}

