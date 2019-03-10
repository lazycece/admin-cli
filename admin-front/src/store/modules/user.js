import { login, logout, getUserInfo } from '../../api/user/userOperate'
import { getCookie, setCookie, removeCookie } from '../../utils/cookie'
import { tokenKey } from '../../config/constant'
import md5 from 'crypto-js/md5'

const user = {
  state: {
    username: '',
    name: '',
    email: '',
    telephone: '',
    role: '',
    token: getCookie(tokenKey)
  },
  mutations: {
    SET_USERNAME: (state, username) => {
      state.username = username
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_EMAIL: (state, email) => {
      state.email = email
    },
    SET_TELEPHONE: (state, telephone) => {
      state.telephone = telephone
    },
    SET_ROLE: (state, role) => {
      state.role = role
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    }
  },
  actions: {
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      // only md5, can use other algorithm from crypto-js library if necessary
      const password = md5(userInfo.password.trim()).toString()
      return new Promise((resolve, reject) => {
        login(username, password).then(response => {
          commit('SET_TOKEN', response.data)
          setCookie(tokenKey, response.data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(response => {
          const data = response.data
          commit('SET_USERNAME', data.username)
          commit('SET_NAME', data.name)
          commit('SET_EMAIL', data.email)
          commit('SET_TELEPHONE', data.telephone)
          commit('SET_ROLE', data.role)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLE', '')
          removeCookie(tokenKey)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeCookie(tokenKey)
        resolve()
      })
    }
  }
}

export default user
