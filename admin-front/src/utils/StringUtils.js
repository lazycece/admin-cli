/**
 * @author lazy c
 * @email lazycece@gmail.com
 * 2018/03/16
 */

/**
 * 随机生成12位产长度的字符串
 * @return {string}
 */
export function randomStr() {
  const timestamp = +new Date() + ''
  const randomNum = parseInt((1 + Math.random()) * 65536) + ''
  return (+(randomNum + timestamp)).toString(36)
}

