<!--
  @author lazy c
  @email lazycece@gmail.com
-->
<template>
  <div class="app-container calendar-list-container">
    <div id="carouseStyle">
      <el-carousel :interval="4000" type="card" height="500px">
        <el-carousel-item >
          <h1>Welcome,  {{userInfo.name}}  !</h1>
        </el-carousel-item>
        <el-carousel-item style="text-align: center;">
          <span>{{userInfo.role | roleFilter(userInfo.role)}}</span>
        </el-carousel-item>
        <el-carousel-item style="text-align: center;">
          <span>{{userInfo.username}}</span>
        </el-carousel-item>
        <el-carousel-item style="text-align: center;">
          <span @click="handleUpdateInfo">{{userInfo.email}}</span><br/>
        </el-carousel-item>
        <el-carousel-item style="text-align: center;">
          <span @click="handleUpdateInfo">{{userInfo.telephone}}</span>
        </el-carousel-item>
        <el-carousel-item style="text-align: center">
          <el-button @click="changePassword" type="text">修改密码</el-button>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- user info dialog -->
    <el-dialog title="修改基本信息" :visible.sync="dialogInfoVisible">
      <el-form :rules="rules" ref="dialogInfo" :model="dialogInfo" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="dialogInfo.email" clearable></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="telephone">
          <el-input v-model="dialogInfo.telephone" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogInfoVisible = false">取消</el-button>
        <el-button type="primary" @click="doUpdateInfo">更新</el-button>
      </div>
    </el-dialog>
    <!-- change password dialog -->
    <el-dialog title="修改密码" :visible.sync="dialogPasswordVisible">
      <el-form :rules="rulesPwd" ref="dialogPassword" :model="dialogPassword" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-steps :active="activeStep" finish-status="success">
          <el-step title="确认密码"></el-step>
          <el-step title="新密码"></el-step>
        </el-steps>
        <br/><br/>
        <el-form-item label="密码" prop="tempPassword">
          <el-input v-model="dialogPassword.tempPassword" type="password" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogPasswordVisible = false">取消</el-button>
        <el-button v-if="activeStep===0" type="primary" @click="confirmPassword">下一步</el-button>
        <el-button v-else type="primary" @click="doChangePassword">更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import store from '../../store/index'
  import { roleValue } from '../../config/constant'
  import { validateEmail, validateTelephone } from '../../utils/validate'
  import { updateInfo, confirmPwd, updatePwd } from '../../api/user/userOperate'
  import md5 from 'crypto-js/md5'

  export default {
    name: 'userCore',
    data() {
      const validateEmailData = (rule, value, callback) => {
        if (!validateEmail(value)) {
          callback(new Error('邮箱格式不正确'))
        } else {
          callback()
        }
      }
      const validateTelNumber = (rule, value, callback) => {
        if (!validateTelephone(value)) {
          callback(new Error('手机号码格式不正确'))
        } else {
          callback()
        }
      }
      return {
        userInfo: {
          username: '',
          name: '',
          telephone: '',
          role: '',
          email: ''
        },
        dialogInfo: {
          username: store.getters.username,
          telephone: '',
          email: ''
        },
        dialogInfoVisible: false,
        rules: {
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { required: true, trigger: 'blur', validator: validateEmailData }
          ],
          telephone: [
            { required: true, message: '请输入电话号码', trigger: 'blur' },
            { required: true, trigger: 'blur', validator: validateTelNumber }
          ]
        },
        // change password
        activeStep: 0,
        dialogPassword: {
          tempPassword: ''
        },
        pwdData: {
          username: store.getters.username,
          password: ''
        },
        dialogPasswordVisible: false,
        rulesPwd: {
          tempPassword: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
        }
      }
    },
    created() {
      this.initUserInfo()
    },
    filters: {
      roleFilter(role) {
        return roleValue[role]
      }
    },
    methods: {
      initUserInfo() {
        this.userInfo.username = store.getters.username
        this.userInfo.name = store.getters.name
        this.userInfo.telephone = store.getters.telephone
        this.userInfo.role = store.getters.role
        this.userInfo.email = store.getters.email
      },
      initDialogInfo() {
        this.dialogInfo.telephone = this.userInfo.telephone
        this.dialogInfo.email = this.userInfo.email
      },
      handleUpdateInfo() {
        this.initDialogInfo()
        this.dialogInfoVisible = true
        this.$nextTick(() => {
          this.$refs['dialogInfo'].clearValidate()
        })
      },
      doUpdateInfo() {
        this.$refs['dialogInfo'].validate((valid) => {
          if (valid) {
            updateInfo(this.dialogInfo).then(response => {
              store.dispatch('GetUserInfo').then(response => {
                this.initUserInfo()
                this.$message.success('修改成功')
                this.dialogInfoVisible = false
              }).catch(_ => {})
            }).catch(_ => {})
          }
        })
      },
      changePassword() {
        this.dialogPasswordVisible = true
        this.dialogPassword.tempPassword = ''
        this.activeStep = 0
        this.$nextTick(() => {
          this.$refs['dialogPassword'].clearValidate()
        })
      },
      confirmPassword() {
        this.$refs['dialogPassword'].validate((valid) => {
          if (valid) {
            this.pwdData.password = md5(this.dialogPassword.tempPassword.trim()).toString()
            confirmPwd(this.pwdData).then(response => {
              this.activeStep = 1
              this.dialogPassword.tempPassword = ''
            }).catch(() => {})
          }
        })
      },
      doChangePassword() {
        this.$refs['dialogPassword'].validate((valid) => {
          if (valid) {
            this.pwdData.password = md5(this.dialogPassword.tempPassword.trim()).toString()
            updatePwd(this.pwdData).then(response => {
              this.$message.success('修改成功')
              this.dialogPasswordVisible = false
            }).catch(() => {})
          }
        })
      }
    }
  }
</script>
<style>
  .el-carousel__item {
    background-color: #2d3a4b;
  }
  .el-carousel__item h1 {
    text-align: center;
    font-size: 80px;
    opacity: 0.75;
    line-height: 500px;
    margin: 0;
    font-family: 新宋体;
    color: white
  }
  .el-carousel__item span {
    font-size: 50px;
    opacity: 0.75;
    line-height: 500px;
    margin: 0;
    font-family: 新宋体;
    color: white
  }
  .el-carousel__item button {
    font-size: 30px;
    opacity: 0.75;
    line-height: 500px;
    margin: 0;
    font-family: 新宋体;
    color: white
  }
</style>
