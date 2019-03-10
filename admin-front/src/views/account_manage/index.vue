<!--
  @author lazy c
  @email lazycece@gmail.com
  2018/4/5
-->
<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">

      <!-- form -->
      <el-input @keyup.enter.native="handleQuery" style="width: 200px;" class="filter-item" placeholder="姓名" clearable v-model="queryData.name">
      </el-input>
      <el-select clearable class="filter-item" style="width: 130px" v-model="queryData.role" placeholder=角色>
        <el-option v-for="item in listRole" :key="item.key" :label="item.value" :value="item.key">
        </el-option>
      </el-select>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleQuery">查询</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" @click="handleCreate" type="primary" icon="el-icon-edit">添加</el-button>
    </div>

    <!-- table -->
    <el-table :key='tableKey' :data="listData" v-loading="listLoading" element-loading-text="给我一点时间" border fit highlight-current-row style="width: 100%">
      <el-table-column type="index" :index="indexMethod" align="center" label="序号" width="70"></el-table-column>
      <el-table-column align="center" width="120" label="姓名">
        <template slot-scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" align="center" label="用户名">
        <template slot-scope="scope">
          <span>{{scope.row.username}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="邮箱">
        <template slot-scope="scope">
          <span>{{scope.row.email}}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="电话号码">
        <template slot-scope="scope">
          <span>{{scope.row.telephone}}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="角色">
        <template slot-scope="scope" v>
          <span>{{scope.row.role | roleFilter}}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter(scope.row.status)">{{scope.row.status | statusValueFilter}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column width="150" align="center" label="创建时间">
        <template slot-scope="scope">
          <span>{{scope.row.createAt | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" align="center" label="更新时间">
       <template slot-scope="scope">
        <span>{{scope.row.updateAt | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
       </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="编辑者">
        <template slot-scope="scope">
          <span>{{scope.row.editor}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="300" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="scope.row.status==='active'" type="info" size="mini" @click="handleBackList(scope.row.uuid, 'del')">封号</el-button>
          <el-button v-if="scope.row.status==='del'" type="success" size="mini" @click="handleBackList(scope.row.uuid, 'active')">解封</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row.uuid)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- page -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handlePageChange"
                     :current-page="queryData.page" :page-sizes="[10,20,30, 50]" :page-size="queryData.limit"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <!-- dialog -->
    <el-dialog :title="dialogTextMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dialogForm" :model="dialogForm" label-position="left" label-width="70px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="dialogForm.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="tempPassword">
          <el-input v-if="dialogStatus==='create'"  type="password" v-model="dialogForm.tempPassword" clearable></el-input>
          <el-input v-else @readonly="true"  type="password" v-model="dialogForm.tempPassword"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="dialogForm.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="dialogForm.email" clearable></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="telephone">
          <el-input v-model="dialogForm.telephone" clearable></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select clearable class="filter-item" v-model="dialogForm.role" placeholder="请选择">
            <el-option v-for="item in listRole" :key="item.key" :label="item.value" :value="item.key"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">创建</el-button>
        <el-button v-else type="primary" @click="updateData">更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { query, getRoles, create, deleteUser, addBackList, edit } from '../../api/user/accountManage'
  import waves from '../../plugin/directive/waves' // 水波纹指令
  import { statusMap, roleValue, statusType } from '../../config/constant'
  import { validateAlphabets, validateEmail, validateTelephone } from '../../utils/validate'
  import md5 from 'crypto-js/md5'

  export default {
    name: 'accountManage',
    directives: {
      waves
    },
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!validateAlphabets(value)) {
          callback(new Error('只允许包含大小写字母'))
        } else {
          callback()
        }
      }
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
        // query and table
        queryData: {
          username: '',
          role: '',
          page: 1,
          limit: 10
        },
        tableKey: 0,
        listRole: null,
        listData: null,
        total: null,
        listLoading: true,
        // dialog
        dialogForm: {
          uuid: undefined,
          name: '',
          username: '',
          password: '',
          email: '',
          telephone: '',
          role: '',
          tempPassword: ''
        },
        dialogFormVisible: false,
        dialogStatus: '',
        dialogTextMap: {
          update: '编辑',
          create: '新建'
        },
        rules: {
          name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { required: true, trigger: 'blur', validator: validateUsername }
          ],
          tempPassword: [
            { required: true, trigger: 'blur', message: '请输入密码' },
            { min: 6, max: 20, message: '密码长度为6-20,', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { required: true, trigger: 'blur', validator: validateEmailData }
          ],
          telephone: [
            { required: true, message: '请输入电话号码', trigger: 'blur' },
            { required: true, trigger: 'blur', validator: validateTelNumber }
          ],
          role: [{ required: true, message: '请选择角色', trigger: 'change' }]
        }}
    },
    created() {
      getRoles().then(response => {
        this.listRole = response.data
      })
      this.getList()
    },
    filters: {
      statusFilter(status) {
        return statusType[status]
      },
      roleFilter(roleKey) {
        return roleValue[roleKey]
      },
      statusValueFilter(statusKey) {
        return statusMap[statusKey]
      }
    },
    methods: {
      indexMethod(index) {
        return (this.queryData.page - 1) * this.queryData.limit + index + 1
      },
      getList() {
        this.listLoading = true
        query(this.queryData).then(response => {
          this.listData = response.data.data
          this.total = response.data.total
          this.listLoading = false
        }).catch(() => {})
      },
      handleQuery() {
        this.queryData.page = 1
        this.getList()
      },
      handlePageChange(page) {
        this.queryData.page = page
        this.getList()
      },
      handleSizeChange(limit) {
        this.queryData.limit = limit
        this.getList()
      },
      resetDialogForm() {
        this.dialogForm = {
          uuid: undefined,
          name: '',
          username: '',
          password: '',
          email: '',
          telephone: '',
          role: '',
          tempPassword: ''
        }
      },
      handleCreate() {
        this.resetDialogForm()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dialogForm'].clearValidate()
        })
      },
      createData() {
        this.$refs['dialogForm'].validate((valid) => {
          if (valid) {
            this.dialogForm.password = md5(this.dialogForm.tempPassword.trim()).toString()
            create(this.dialogForm).then(response => {
              this.getList()
              this.dialogFormVisible = false
              this.$message.success('创建成功')
            }).catch(() => {})
          }
        })
      },
      handleDelete(uuid) {
        this.$confirm('此操作将永久删除该账号, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteUser(uuid).then(response => {
            this.getList()
            this.$message.success('删除成功')
          }).catch(() => {})
        }).catch(() => {
          this.$message.info('已取消操作')
        })
      },
      handleBackList(uuid, type) {
        const data = {
          uuid: uuid,
          type: type
        }
        addBackList(data).then(response => {
          this.getList()
          this.$message.success('操作成功')
        }).catch(() => {})
      },
      handleUpdate(object) {
        this.dialogForm = Object.assign({}, object)
        this.dialogForm.tempPassword = '88888888'
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dialogForm'].clearValidate()
        })
      },
      updateData() {
        this.$refs['dialogForm'].validate((valid) => {
          if (valid) {
            this.dialogForm.password = md5(this.dialogForm.tempPassword.trim()).toString()
            edit(this.dialogForm).then(response => {
              this.getList()
              this.dialogFormVisible = false
              this.$message.success('操作成功')
            }).catch(() => {})
          }
        })
      }
    }
  }
</script>
