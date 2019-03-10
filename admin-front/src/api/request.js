import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import { responseCode, tokenKey } from '../config/constant.js'
import { parseResponse } from '../api/responseParser'
import { getCookie } from '../utils/cookie'

const request = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 5000
})

request.interceptors.request.use(config => {
  if (getCookie(tokenKey)) {
    // 让每个请求携带token进行验证
    config.headers[tokenKey] = getCookie(tokenKey)
  }
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

request.interceptors.response.use(
  response => {
    const res = parseResponse(response.data)
    if (res.code === responseCode.SUCCESS) {
      return res
    }
    // invalid token(unlawful, expire and so on);
    if (res.code === responseCode.UNAUTHORIZED) {
      store.dispatch('FedLogOut').then(() => {
        // 为了重新实例化vue-router对象 避免bug
        location.reload()
      }).catch(_ => {})
    } else if (res.code === responseCode.FAIL) {
      Message({
        message: res.message,
        type: 'warning',
        duration: 5 * 1000
      })
    } else {
      return res
    }
    return Promise.reject(res.message)
  }, error => {
    console.log('err' + error)
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  })

export default request
