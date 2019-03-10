/**
 * @author lazy c
 * @email lazycece@gmail.com
 * 2018/03/16
 */

/**
 * url
 * @param url
 * @return {boolean}
 */
export function validateURL(url) {
  const regex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return regex.test(url)
}

/**
 * 正整数
 * @param number
 * @return {boolean}
 */
export function validateNumber(number) {
  const regex = /^[1-9]+[0-9]*]*$/
  return regex.test(number)
}

/**
 * 小写字母
 * @param text
 * @return {boolean}
 */
export function validateLowerCase(value) {
  const regex = /^[a-z]+$/
  return regex.test(value)
}

/**
 * 大写字母
 * @param text
 * @return {boolean}
 */
export function validateUpperCase(value) {
  const regex = /^[A-Z]+$/
  return regex.test(value)
}

/**
 * 大小写字母
 * @param text
 * @return {boolean}
 */
export function validateAlphabets(value) {
  const regex = /^[A-Za-z]+$/
  return regex.test(value)
}

/**
 * 大小写字母+数字
 * @param text
 * @return {boolean}
 */
export function validateAlphabetsAndNumer(value) {
  const regex = /^[A-Za-z0-9]+$/
  return regex.test(value)
}

/**
 * email
 * @param email
 * @returns {boolean}
 */
export function validateEmail(email) {
  const regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return regex.test(email)
}

/**
 * 验证手机号码
 * @param telephone
 * @return {boolean}
 */
export function validateTelephone(telephone) {
  const regex = /^[1][3,4,5,7,8][0-9]{9}$/
  return regex.test(telephone)
}

