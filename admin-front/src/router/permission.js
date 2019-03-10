import router from './index'
import store from '../store/index'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { tokenKey } from '../config/constant'
import { getCookie } from '../utils/cookie'

// NProgress Configuration
NProgress.configure({ showSpinner: false })

// permission judge function
function hasPermission(role, permissionRoles) {
  if (!permissionRoles) return true
  return permissionRoles.indexOf(role) >= 0
}

// no redirect whitelist
const whiteList = ['/login']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getCookie(tokenKey)) {
    /* has token */
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is home will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.role.length === 0) { // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetUserInfo').then(response => { // 拉取user_info
          const role = response.data.role
          store.dispatch('GenerateRoutes', role).then(() => { // 根据roles权限生成可访问的路由表
            router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          })
        }).catch(() => {
          store.dispatch('FedLogOut').then(() => {
            next({ path: '/login' })
          }).catch(_ => {})
        })
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        if (hasPermission(store.getters.role, to.meta.roles)) {
          next()//
        } else {
          next({ path: '/401', replace: true, query: { noGoBack: true }})
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
      // if current page is login will not trigger afterEach hook,
      // so manually handle it
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
