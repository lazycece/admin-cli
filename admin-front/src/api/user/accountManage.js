/**
 * @author lazy c
 * @email lazycece@gmail.com
 * 2018/04/17
 */
import request from '../request'

export function query(data) {
  return request({
    url: '/account/query',
    method: 'get',
    params: data
  })
}

export function getRoles() {
  return request({
    url: '/account/roles',
    method: 'get'
  })
}

export function create(data) {
  return request({
    url: '/account/add',
    method: 'post',
    data
  })
}

export function edit(data) {
  return request({
    url: '/account/edit',
    method: 'post',
    data
  })
}

export function deleteUser(uuid) {
  const data = {
    uuid: uuid
  }
  return request({
    url: '/account/delete',
    method: 'post',
    data
  })
}

export function addBackList(data) {
  return request({
    url: '/account/backList',
    method: 'post',
    data
  })
}
