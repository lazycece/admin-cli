/**
 * @author lazy c
 * @email lazycece@gmail.com
 */

/**
 * 自定义返回消息结构
 */
const responseData = {
  code: '',
  message: '',
  data: ''
}

export function parseResponse(response) {
  responseData.code = response.status
  responseData.message = response.message
  responseData.data = response.body
  return responseData
}
