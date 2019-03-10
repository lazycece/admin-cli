import Vue from 'vue'
import Router from 'vue-router'
import { homePagePathName, role } from '../config/constant'

const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow.
// so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/decorator/layout/Layout'

/** note: submenu only apppear when children.length>=1
*   detail see  https://panjiachen.github.io/vue-element-admin-site/#/router-and-nav?id=sidebar
**/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('decorator/errorPage/404'), hidden: true },
  { path: '/401', component: _import('decorator/errorPage/401'), hidden: true }
]

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

// const basicPermission = [role.admin, role.develop, role.product, role.test, role.operative]

export const asyncRouterMap = [
  {
    path: '',
    component: Layout,
    redirect: homePagePathName,
    children: [
      { path: homePagePathName, component: _import('home/index'), name: homePagePathName, meta: { title: homePagePathName, icon: 'dashboard', noCache: true }}
    ]
  },
  { path: '',
    component: Layout,
    redirect: 'noredirect',
    meta: { roles: [role.admin] },
    children: [
      { path: 'account', component: _import('account_manage/index'), name: 'account', meta: { title: 'accountManage', icon: 'accountManage' }}
    ]
  },
  // --------------------------------------------------------------------------//
  // -------------------- 多层级页面样例 as follow  ------------------------------//
  // --------------------------------------------------------------------------//
  {
    path: '/simplePageLayout',
    component: Layout,
    redirect: 'noredirect',
    name: 'simplePageModule',
    meta: { title: 'simplePageModule', icon: 'systemManage' },
    children: [
      { path: 'pageOne', component: _import('simple_page_layout/page_one/index'), name: 'pageOne', meta: { title: 'simplePageLayout.pageOne' }},
      { path: 'pageTwo', component: _import('simple_page_layout/page_two/index'), name: 'pageTwo', meta: { title: 'simplePageLayout.pageTwo' }}
    ]
  },
  {
    path: '/deepPageLayout',
    component: Layout,
    redirect: 'noredirect',
    name: 'deepPageLayout',
    meta: { title: 'deepPageModule', icon: 'subjectManage' },
    children: [
      {
        path: '/deepPageLayout/pageModule',
        component: _import('deep_page_layout/page_module/index'),
        redirect: 'noredirect',
        name: 'pageModule',
        meta: { title: 'deepPageLayout.pageModuleLayout', icon: 'selectSubject' },
        children: [
          { path: 'pageExampleOne', component: _import('deep_page_layout/page_module/page_example_one/index'), name: 'pageExampleOne', meta: { title: 'deepPageLayout.pageModule.pageExampleOne' }},
          { path: 'pageExampleTwo', component: _import('deep_page_layout/page_module/page_example_two/index'), name: 'pageExampleTwo', meta: { title: 'deepPageLayout.pageModule.pageExampleTwo' }},
          { path: 'pageExampleThree', component: _import('deep_page_layout/page_module/page_example_three/index'), name: 'pageExampleThree', meta: { title: 'deepPageLayout.pageModule.pageExampleThree' }}
        ]
      },
      { path: 'pageExampleFour', component: _import('deep_page_layout/page_example_four/index'), name: 'write', meta: { title: 'deepPageLayout.pageExampleFour', icon: 'writeSubject' }}
    ]
  }
]
