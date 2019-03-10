<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>

    <breadcrumb class="breadcrumb-container"></breadcrumb>

    <div class="right-menu">
      <error-log class="errLog-container right-menu-item"></error-log>

      <el-tooltip effect="dark" :content="userName" placement="bottom">
        <svg-icon iconClass="userInfo" class="userInfo right-menu-item" ></svg-icon>
      </el-tooltip>

      <el-tooltip effect="dark" :content="$t('navbar.logout')" placement="bottom">
        <span @click="doLogout"  class="pointer-hand">
          <svg-icon iconClass="logout" class="logout right-menu-item" ></svg-icon>
        </span>
      </el-tooltip>

      <el-tooltip effect="dark" :content="$t('navbar.screenfull')" placement="bottom">
        <screenfull class="screenfull right-menu-item"></screenfull>
      </el-tooltip>

    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SvgIcon from '../../../../components/SvgIcon/index.vue'
import store from '../../../../store/index'

export default {
  data() {
    return {
      userName: store.getters.name
    }
  },
  components: {
    SvgIcon,
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name',
      'avatar'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    doLogout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus{
     outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .logout {
      width: 30px;
      height: 23px;
      margin-right: 30px;
    }
    .userInfo {
      width: 28px;
      height:40px;
      margin-right: 10px;
    }
    .screenfull {
      width: 30px;
      height: 20px;
      margin-right: 10px;
      margin-left: 30px;
    }
    .international{
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 50px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
    .pointer-hand {
      display: inline-block;
      cursor: pointer;
      fill: #5a5e66;;
      width: 20px;
      height: 20px;
      vertical-align: 10px;
    }
  }
}
</style>
