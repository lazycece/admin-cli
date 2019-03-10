const getters = {
  sidebar: state => state.app.sidebar,
  language: state => state.app.language,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,
  errorLogs: state => state.errorLog.logs,
  // user
  username: state => state.user.username,
  name: state => state.user.name,
  email: state => state.user.email,
  telephone: state => state.user.telephone,
  role: state => state.user.role,
  token: state => state.user.token
}
export default getters
