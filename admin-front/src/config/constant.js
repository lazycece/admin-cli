/**
 * @author lazy c
 * @email lazycece@gmail.com
 * 2018/03/16
 */

/**
 * 首页路径名
 * @type {string}
 */
export const homePagePathName = 'home'

/**
 * 请求状态响应码
 */
export const responseCode = {
  SUCCESS: 200,
  FAIL: 800,
  UNAUTHORIZED: -105,
  FORBIDDEN: 403
}

/**
 * 与服务端约定的token的key
 * @type {string}
 */
export const tokenKey = 'ADMIN_TOKEN'

/**
 * 用户权限角色, 与服务端值保持一致
 */
export const role = {
  admin: 'ADMIN',
  develop: 'DEVELOP',
  test: 'TEST',
  product: 'PRODUCT',
  operative: 'OPERATIVE'
}
export const roleValue = {
  ADMIN: '管理员',
  DEVELOP: '研发',
  TEST: '测试',
  PRODUCT: '产品',
  OPERATIVE: '运营'
}

/**
 * 数据状态
 * key：与服务端一致 value：type类型
 */
export const statusType = {
  active: 'success',
  del: 'warning',
  wait: 'info',
  end: 'danger'
}
export const statusMap = {
  active: '正常',
  del: '禁用',
  wait: '等待',
  end: '结束'
}

/**
 * 订单状态
 * key：与服务端一致 value：type类型
 */
export const orderStatusType = {
  wait: 'info',
  success: 'success',
  fail: 'danger',
  cancel: 'info'
}
