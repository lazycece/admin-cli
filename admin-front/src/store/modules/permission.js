import { asyncRouterMap, constantRouterMap } from '@/router'

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * meta中没有role，则默认有权限
 * @param role
 * @param route
 */
function hasPermission(role, route) {
  if (route.meta && route.meta.roles) {
    return route.meta.roles.indexOf(role) >= 0
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param asyncRouterMap
 * @param role
 */
function filterAsyncRouter(asyncRouterMap, role) {
  return asyncRouterMap.filter(route => {
    if (hasPermission(role, route)) {
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children, role)
      }
      return true
    }
    return false
  })
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, role) {
      return new Promise(resolve => {
        const accessedRouters = filterAsyncRouter(asyncRouterMap, role)
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }
  }
}

export default permission
