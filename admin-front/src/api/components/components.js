/**
 * @author lazy c
 * @email lazycece@gmail.com
 */
import request from '../request'

export function uploadFile(data) {
  return request({
    url: '/upload/file',
    method: 'post',
    data
  })
}
