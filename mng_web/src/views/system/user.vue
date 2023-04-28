<template>
  <el-row>
    <el-col :span="5">
      <div class="box">
        <el-scrollbar>
          <basic-container>
            <avue-tree :option="treeOption" :data="treeData" @node-click="nodeClick" />
          </basic-container>
        </el-scrollbar>
      </div>
    </el-col>
    <el-col :span="19">
      <basic-container>
        <avue-crud
          :option="option"
          :search.sync="search"
          :table-loading="loading"
          :data="data"
          ref="crud"
          v-model="form"
          :permission="permissionList"
          @row-del="rowDel"
          @row-update="rowUpdate"
          @row-save="rowSave"
          :before-open="beforeOpen"
          :page.sync="page"
          @search-change="searchChange"
          @search-reset="searchReset"
          @selection-change="selectionChange"
          @current-change="currentChange"
          @size-change="sizeChange"
          @refresh-change="refreshChange"
          @on-load="onLoad"
        >
          <template slot="menuLeft">
            <el-button
              type="danger"
              size="small"
              plain
              icon="el-icon-delete"
              v-if="permission.user_delete"
              @click="handleDelete"
            >删 除</el-button>
            <el-button
              type="info"
              size="small"
              plain
              v-if="permission.user_role"
              icon="el-icon-user"
              @click="handleGrant"
            >角色配置</el-button>
            <el-button
              type="info"
              size="small"
              plain
              v-if="permission.user_reset"
              icon="el-icon-refresh"
              @click="handleReset"
            >密码重置</el-button>
            <el-button
              type="info"
              size="small"
              plain
              v-if="userInfo.role_name.includes('admin')"
              icon="el-icon-setting"
              @click="handlePlatform"
            >平台配置</el-button>
            <el-button
              type="info"
              size="small"
              plain
              v-if="userInfo.role_name.includes('admin')"
              icon="el-icon-coordinate"
              @click="handleLock"
            >账号解封</el-button>
            <el-button
              type="success"
              size="small"
              plain
              v-if="userInfo.role_name.includes('admin')"
              icon="el-icon-upload2"
              @click="handleImport"
            >导入</el-button>
            <el-button
              type="warning"
              size="small"
              plain
              v-if="userInfo.role_name.includes('admin')"
              icon="el-icon-download"
              @click="handleExport"
            >导出</el-button>
          </template>
          <template slot-scope="{row}" slot="tenantName">
            <el-tag>{{row.tenantName}}</el-tag>
          </template>
          <template slot-scope="{row}" slot="roleName">
            <el-tag>{{row.roleName}}</el-tag>
          </template>
          <template slot-scope="{row}" slot="deptName">
            <el-tag>{{row.deptName}}</el-tag>
          </template>
          <template slot-scope="{row}" slot="userTypeName">
            <el-tag>{{row.userTypeName}}</el-tag>
          </template>
        </avue-crud>
        <el-dialog title="用户角色配置" append-to-body :visible.sync="roleBox" width="345px">
          <el-tree
            :data="roleGrantList"
            show-checkbox
            check-strictly
            default-expand-all
            node-key="id"
            ref="treeRole"
            :default-checked-keys="roleTreeObj"
            :props="props"
          ></el-tree>

          <span slot="footer" class="dialog-footer">
            <el-button @click="roleBox = false">取 消</el-button>
            <el-button type="primary" @click="submitRole">确 定</el-button>
          </span>
        </el-dialog>
        <el-dialog title="用户数据导入" append-to-body :visible.sync="excelBox" width="555px">
          <avue-form :option="excelOption" v-model="excelForm" :upload-after="uploadAfter">
            <template slot="excelTemplate">
              <el-button type="primary" @click="handleTemplate">
                点击下载
                <i class="el-icon-download el-icon--right"></i>
              </el-button>
            </template>
          </avue-form>
        </el-dialog>
        <el-dialog title="用户平台配置" append-to-body :visible.sync="platformBox">
          <avue-crud
            :option="platformOption"
            :table-loading="platformLoading"
            :data="platformData"
            ref="platformCrud"
            v-model="platformForm"
            :before-open="platformBeforeOpen"
            :page.sync="platformPage"
            :permission="platformPermissionList"
            @row-update="platformRowUpdate"
            @search-change="platformSearchChange"
            @search-reset="platformSearchReset"
            @selection-change="platformSelectionChange"
            @current-change="platformCurrentChange"
            @size-change="platformSizeChange"
            @refresh-change="platformRefreshChange"
            @on-load="platformOnLoad"
          >
            <template slot-scope="{row}" slot="tenantName">
              <el-tag>{{row.tenantName}}</el-tag>
            </template>
            <template slot-scope="{row}" slot="userTypeName">
              <el-tag>{{row.userTypeName}}</el-tag>
            </template>
          </avue-crud>
        </el-dialog>
      </basic-container>
    </el-col>
  </el-row>
</template>

<script>
import {
  getList,
  getUser,
  getUserPlatform,
  remove,
  update,
  updatePlatform,
  add,
  grant,
  resetPassword,
  unlock,
} from '@/api/system/user'
import { exportBlob } from '@/api/common'
import { getDeptTree, getDeptLazyTree } from '@/api/system/dept'
import { getRoleTree } from '@/api/system/role'
import { getPostList } from '@/api/system/post'
import { mapGetters } from 'vuex'
import website from '@/config/website'
import { getToken } from '@/util/auth'
import { downloadXls } from '@/util/util'
import { dateNow } from '@/util/date'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

export default {
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      form: {},
      search: {},
      roleBox: false,
      excelBox: false,
      platformBox: false,
      initFlag: true,
      selectionList: [],
      query: {},
      loading: true,
      platformLoading: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      platformPage: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      init: {
        roleTree: [],
        deptTree: [],
      },
      props: {
        label: 'title',
        value: 'key',
      },
      roleGrantList: [],
      roleTreeObj: [],
      treeDeptId: '',
      treeData: [],
      treeOption: {
        nodeKey: 'id',
        lazy: true,
        treeLoad: function (node, resolve) {
          const parentId = node.level === 0 ? 0 : node.data.id
          getDeptLazyTree(parentId).then((res) => {
            resolve(
              res.data.data.map((item) => {
                return {
                  ...item,
                  leaf: !item.hasChildren,
                }
              })
            )
          })
        },
        addBtn: false,
        menu: false,
        size: 'small',
        props: {
          labelText: '标题',
          label: 'title',
          value: 'value',
          children: 'children',
        },
      },
      option: {
        /* height: 'auto',
          calcHeight: 80, */
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        selection: true,
        viewBtn: true,
        dialogType: 'drawer',
        dialogClickModal: false,
        column: [
          {
            label: '登录账号',
            prop: 'account',
            search: true,
            display: false,
          },
          {
            label: '所属租户',
            prop: 'tenantName',
            slot: true,
            display: false,
          },
          {
            label: '用户姓名',
            prop: 'realName',
            search: true,
            display: false,
          },
          {
            label: '所属角色',
            prop: 'roleName',
            slot: true,
            display: false,
          },
          {
            label: '所属部门',
            prop: 'deptName',
            slot: true,
            display: false,
          },
          {
            label: '用户平台',
            prop: 'userTypeName',
            slot: true,
            display: false,
          },
          {
            label: '用户平台',
            type: 'select',
            dicUrl: '/api/blade-system/dict/dictionary?code=user_type',
            props: {
              label: 'dictValue',
              value: 'dictKey',
            },
            dataType: 'number',
            search: true,
            hide: true,
            display: false,
            prop: 'userType',
            rules: [
              {
                required: true,
                message: '请选择用户平台',
                trigger: 'blur',
              },
            ],
          },
        ],
        group: [
          {
            label: '基础信息',
            prop: 'baseInfo',
            icon: 'el-icon-user-solid',
            column: [
              {
                label: '所属租户',
                prop: 'tenantId',
                type: 'tree',
                dicUrl: '/api/blade-system/tenant/select',
                props: {
                  label: 'tenantName',
                  value: 'tenantId',
                },
                hide: !website.tenantMode,
                addDisplay: website.tenantMode,
                editDisplay: website.tenantMode,
                viewDisplay: website.tenantMode,
                rules: [
                  {
                    required: true,
                    message: '请输入所属租户',
                    trigger: 'click',
                  },
                ],
                span: 24,
              },
              {
                label: '登录账号',
                prop: 'account',
                rules: [
                  {
                    required: true,
                    message: '请输入登录账号',
                    trigger: 'blur',
                  },
                ],
              },
              {
                label: '用户平台',
                type: 'select',
                dicUrl: '/api/blade-system/dict/dictionary?code=user_type',
                props: {
                  label: 'dictValue',
                  value: 'dictKey',
                },
                dataType: 'number',
                slot: true,
                prop: 'userType',
                rules: [
                  {
                    required: true,
                    message: '请选择用户平台',
                    trigger: 'blur',
                  },
                ],
              },
              {
                label: '密码',
                prop: 'password',
                hide: true,
                editDisplay: false,
                viewDisplay: false,
                rules: [
                  { required: true, validator: validatePass, trigger: 'blur' },
                ],
              },
              {
                label: '确认密码',
                prop: 'password2',
                hide: true,
                editDisplay: false,
                viewDisplay: false,
                rules: [
                  { required: true, validator: validatePass2, trigger: 'blur' },
                ],
              },
            ],
          },
          {
            label: '详细信息',
            prop: 'detailInfo',
            icon: 'el-icon-s-order',
            column: [
              {
                label: '用户昵称',
                prop: 'name',
                hide: true,
                rules: [
                  {
                    required: true,
                    message: '请输入用户昵称',
                    trigger: 'blur',
                  },
                ],
              },
              {
                label: '用户姓名',
                prop: 'realName',
                rules: [
                  {
                    required: true,
                    message: '请输入用户姓名',
                    trigger: 'blur',
                  },
                  {
                    min: 2,
                    max: 5,
                    message: '姓名长度在2到5个字符',
                  },
                ],
              },
              {
                label: '手机号码',
                prop: 'phone',
                overHidden: true,
              },
              {
                label: '电子邮箱',
                prop: 'email',
                hide: true,
                overHidden: true,
              },
              {
                label: '用户性别',
                prop: 'sex',
                type: 'select',
                dicData: [
                  {
                    label: '男',
                    value: 1,
                  },
                  {
                    label: '女',
                    value: 2,
                  },
                  {
                    label: '未知',
                    value: 3,
                  },
                ],
                hide: true,
              },
              {
                label: '用户生日',
                type: 'date',
                prop: 'birthday',
                format: 'yyyy-MM-dd hh:mm:ss',
                valueFormat: 'yyyy-MM-dd hh:mm:ss',
                hide: true,
              },
              {
                label: '账号状态',
                prop: 'statusName',
                hide: true,
                display: false,
              },
            ],
          },
          {
            label: '职责信息',
            prop: 'dutyInfo',
            icon: 'el-icon-s-custom',
            column: [
              {
                label: '用户编号',
                prop: 'code',
              },
              {
                label: '所属角色',
                prop: 'roleId',
                multiple: true,
                type: 'tree',
                dicData: [],
                props: {
                  label: 'title',
                },
                checkStrictly: true,
                slot: true,
                rules: [
                  {
                    required: true,
                    message: '请选择所属角色',
                    trigger: 'click',
                  },
                ],
              },
              {
                label: '所属部门',
                prop: 'deptId',
                type: 'tree',
                multiple: true,
                dicData: [],
                props: {
                  label: 'title',
                },
                checkStrictly: true,
                slot: true,
                rules: [
                  {
                    required: true,
                    message: '请选择所属部门',
                    trigger: 'click',
                  },
                ],
              },
              {
                label: '所属岗位',
                prop: 'postId',
                type: 'tree',
                multiple: true,
                dicData: [],
                props: {
                  label: 'postName',
                  value: 'id',
                },
                rules: [
                  {
                    required: true,
                    message: '请选择所属岗位',
                    trigger: 'click',
                  },
                ],
              },
            ],
          },
        ],
      },
      data: [],
      platformQuery: {},
      platformSelectionList: [],
      platformData: [],
      platformForm: {},
      platformOption: {
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        selection: true,
        viewBtn: true,
        dialogClickModal: false,
        menuWidth: 120,
        editBtnText: '配置',
        column: [
          {
            label: '登录账号',
            prop: 'account',
            search: true,
            display: false,
          },
          {
            label: '所属租户',
            prop: 'tenantName',
            slot: true,
            display: false,
          },
          {
            label: '用户姓名',
            prop: 'realName',
            search: true,
            display: false,
          },
          {
            label: '用户平台',
            prop: 'userTypeName',
            slot: true,
            display: false,
          },
          {
            label: '用户平台',
            type: 'select',
            dicUrl: '/api/blade-system/dict/dictionary?code=user_type',
            props: {
              label: 'dictValue',
              value: 'dictKey',
            },
            dataType: 'number',
            search: true,
            hide: true,
            display: false,
            prop: 'userType',
            rules: [
              {
                required: true,
                message: '请选择用户平台',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '用户拓展',
            prop: 'userExt',
            type: 'textarea',
            minRows: 8,
            span: 24,
            overHidden: true,
            row: true,
            hide: true,
          },
        ],
      },
      excelForm: {},
      excelOption: {
        submitBtn: false,
        emptyBtn: false,
        column: [
          {
            label: '模板上传',
            prop: 'excelFile',
            type: 'upload',
            drag: true,
            loadText: '模板上传中，请稍等',
            span: 24,
            propsHttp: {
              res: 'data',
            },
            tip: '请上传 .xls,.xlsx 标准格式文件',
            action: '/api/blade-user/import-user',
          },
          {
            label: '数据覆盖',
            prop: 'isCovered',
            type: 'switch',
            align: 'center',
            width: 80,
            dicData: [
              {
                label: '否',
                value: 0,
              },
              {
                label: '是',
                value: 1,
              },
            ],
            value: 0,
            slot: true,
            rules: [
              {
                required: true,
                message: '请选择是否覆盖',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '模板下载',
            prop: 'excelTemplate',
            formslot: true,
            span: 24,
          },
        ],
      },
    }
  },
  watch: {
    'form.tenantId'() {
      if (this.form.tenantId !== '' && this.initFlag) {
        this.initData(this.form.tenantId)
      }
    },
    'excelForm.isCovered'() {
      if (this.excelForm.isCovered !== '') {
        const column = this.findObject(this.excelOption.column, 'excelFile')
        column.action = `/api/blade-user/import-user?isCovered=${this.excelForm.isCovered}`
      }
    },
  },
  computed: {
    ...mapGetters(['userInfo', 'permission']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.user_add, false),
        viewBtn: this.vaildData(this.permission.user_view, false),
        delBtn: this.vaildData(this.permission.user_delete, false),
        editBtn: this.vaildData(this.permission.user_edit, false),
      }
    },
    platformPermissionList() {
      return {
        addBtn: false,
        viewBtn: false,
        delBtn: false,
        editBtn: this.vaildData(this.permission.user_edit, false),
      }
    },
    ids() {
      let ids = []
      this.selectionList.forEach((ele) => {
        ids.push(ele.id)
      })
      return ids.join(',')
    },
  },
  mounted() {
    // 非租户模式默认加载管理组数据
    if (!website.tenantMode) {
      this.initData(website.tenantId)
    }
  },
  methods: {
    nodeClick(data) {
      this.treeDeptId = data.id
      this.page.currentPage = 1
      this.onLoad(this.page)
    },
    initData(tenantId) {
      getRoleTree(tenantId).then((res) => {
        const column = this.findObject(this.option.group, 'roleId')
        column.dicData = res.data.data
      })
      getDeptTree(tenantId).then((res) => {
        const column = this.findObject(this.option.group, 'deptId')
        column.dicData = res.data.data
      })
      getPostList(tenantId).then((res) => {
        const column = this.findObject(this.option.group, 'postId')
        column.dicData = res.data.data
      })
    },
    submitRole() {
      const roleList = this.$refs.treeRole.getCheckedKeys().join(',')
      grant(this.ids, roleList).then(() => {
        this.roleBox = false
        this.$message({
          type: 'success',
          message: '操作成功!',
        })
        this.onLoad(this.page)
      })
    },
    rowSave(row, done, loading) {
      row.deptId = row.deptId.join(',')
      row.roleId = row.roleId.join(',')
      row.postId = row.postId.join(',')
      add(row).then(
        () => {
          this.initFlag = false
          this.onLoad(this.page)
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
          done()
        },
        (error) => {
          window.console.log(error)
          loading()
        }
      )
    },
    rowUpdate(row, index, done, loading) {
      row.deptId = row.deptId.join(',')
      row.roleId = row.roleId.join(',')
      row.postId = row.postId.join(',')
      update(row).then(
        () => {
          this.initFlag = false
          this.onLoad(this.page)
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
          done()
        },
        (error) => {
          window.console.log(error)
          loading()
        }
      )
    },
    rowDel(row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return remove(row.id)
        })
        .then(() => {
          this.onLoad(this.page)
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
        })
    },
    searchReset() {
      this.query = {}
      this.treeDeptId = ''
      this.onLoad(this.page)
    },
    searchChange(params, done) {
      this.query = params
      this.page.currentPage = 1
      this.onLoad(this.page, params)
      done()
    },
    selectionChange(list) {
      this.selectionList = list
    },
    selectionClear() {
      this.selectionList = []
      this.$refs.crud.toggleSelection()
    },
    handleDelete() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return remove(this.ids)
        })
        .then(() => {
          this.onLoad(this.page)
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
          this.$refs.crud.toggleSelection()
        })
    },
    handleReset() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm('确定将选择账号密码重置为123456?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return resetPassword(this.ids)
        })
        .then(() => {
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
          this.$refs.crud.toggleSelection()
        })
    },
    handleGrant() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.roleTreeObj = []
      if (this.selectionList.length === 1) {
        this.roleTreeObj = this.selectionList[0].roleId.split(',')
      }
      getRoleTree().then((res) => {
        this.roleGrantList = res.data.data
        this.roleBox = true
      })
    },
    handlePlatform() {
      this.platformBox = true
    },
    handleLock() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm('确定将选择账号解封？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return unlock(this.ids)
        })
        .then(() => {
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
        })
    },
    handleImport() {
      this.excelBox = true
    },
    uploadAfter(res, done, loading, column) {
      window.console.log(column)
      this.excelBox = false
      this.refreshChange()
      done()
    },
    handleExport() {
      this.$confirm('是否导出用户数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        NProgress.start()
        exportBlob(
          `/api/blade-user/export-user?${
            this.website.tokenHeader
          }=${getToken()}&account=${this.search.account}&realName=${
            this.search.realName
          }`
        ).then((res) => {
          downloadXls(res.data, `用户数据表${dateNow()}.xlsx`)
          NProgress.done()
        })
      })
    },
    handleTemplate() {
      exportBlob(
        `/api/blade-user/export-template?${
          this.website.tokenHeader
        }=${getToken()}`
      ).then((res) => {
        downloadXls(res.data, '用户数据模板.xlsx')
      })
    },
    beforeOpen(done, type) {
      if (['edit', 'view'].includes(type)) {
        getUser(this.form.id).then((res) => {
          this.form = res.data.data
          if (this.form.hasOwnProperty('deptId')) {
            this.form.deptId = this.form.deptId.split(',')
          }
          if (this.form.hasOwnProperty('roleId')) {
            this.form.roleId = this.form.roleId.split(',')
          }
          if (this.form.hasOwnProperty('postId')) {
            this.form.postId = this.form.postId.split(',')
          }
        })
      }
      this.initFlag = true
      done()
    },
    currentChange(currentPage) {
      this.page.currentPage = currentPage
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize
    },
    refreshChange() {
      this.onLoad(this.page, this.query)
    },
    onLoad(page, params = {}) {
      this.loading = true
      getList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query),
        this.treeDeptId
      ).then((res) => {
        const data = res.data.data
        this.page.total = data.total
        this.data = data.records
        this.loading = false
        this.selectionClear()
      })
    },
    platformRowUpdate(row, index, done, loading) {
      updatePlatform(row.id, row.userType, row.userExt).then(
        () => {
          this.platformOnLoad(this.platformPage)
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
          done()
        },
        (error) => {
          window.console.log(error)
          loading()
        }
      )
    },
    platformBeforeOpen(done, type) {
      if (['edit', 'view'].includes(type)) {
        getUserPlatform(this.platformForm.id).then((res) => {
          this.platformForm = res.data.data
        })
      }
      done()
    },
    platformSearchReset() {
      this.platformQuery = {}
      this.platformOnLoad(this.platformPage)
    },
    platformSearchChange(params, done) {
      this.platformQuery = params
      this.platformPage.currentPage = 1
      this.platformOnLoad(this.platformPage, params)
      done()
    },
    platformSelectionChange(list) {
      this.platformSelectionList = list
    },
    platformSelectionClear() {
      this.platformSelectionList = []
      this.$refs.platformCrud.toggleSelection()
    },
    platformCurrentChange(currentPage) {
      this.platformPage.currentPage = currentPage
    },
    platformSizeChange(pageSize) {
      this.platformPage.pageSize = pageSize
    },
    platformRefreshChange() {
      this.platformOnLoad(this.platformPage, this.platformQuery)
    },
    platformOnLoad(page, params = {}) {
      this.platformLoading = true
      getList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query),
        this.treeDeptId
      ).then((res) => {
        const data = res.data.data
        this.platformPage.total = data.total
        this.platformData = data.records
        this.platformLoading = false
        this.selectionClear()
      })
    },
  },
}
</script>

<style>
.box {
  height: 800px;
}

.el-scrollbar {
  height: 100%;
}

.box .el-scrollbar__wrap {
  overflow: scroll;
}
</style>
