<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      ref="crud"
      v-model="form"
      :page.sync="page"
      :permission="permissionList"
      :before-open="beforeOpen"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @row-save="rowSave"
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
          icon="el-icon-delete"
          v-if="permission.tenant_delete"
          plain
          @click="handleDelete"
        >删 除</el-button>
        <el-tooltip class="item" effect="dark" content="给租户配置账号额度、过期时间等授权信息" placement="top">
          <el-button
            size="small"
            plain
            v-if="userInfo.role_name.includes('administrator')"
            icon="el-icon-setting"
            @click="handleSetting"
          >授权配置</el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="给租户配置独立数据源以实现数据库隔离" placement="top">
          <el-button
            size="small"
            plain
            v-if="userInfo.role_name.includes('administrator')"
            icon="el-icon-coin"
            @click="handleDatasource"
          >数据源配置</el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="将菜单产品包与租户配置绑定" placement="top">
          <el-button
            size="small"
            plain
            v-if="userInfo.role_name.includes('administrator')"
            icon="el-icon-notebook-1"
            @click="handlePackage"
          >产品包配置</el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="将自定义的菜单集合定制为租户绑定的菜单产品包" placement="top">
          <el-button
            size="small"
            plain
            v-if="userInfo.role_name.includes('administrator')"
            icon="el-icon-notebook-2"
            @click="handlePackageSetting"
          >产品包管理</el-button>
        </el-tooltip>
      </template>
      <template slot-scope="{row}" slot="accountNumber">
        <el-tag>{{ row.accountNumber > 0 ? row.accountNumber : '不限制' }}</el-tag>
      </template>
      <template slot-scope="{row}" slot="expireTime">
        <el-tag>{{ row.expireTime ? row.expireTime : '不限制' }}</el-tag>
      </template>
    </avue-crud>
    <el-dialog title="租户授权配置" append-to-body :visible.sync="box" width="450px">
      <avue-form :option="settingOption" v-model="settingForm" @submit="handleSubmit" />
    </el-dialog>
    <el-dialog title="租户数据源配置" append-to-body :visible.sync="datasourceBox" width="450px">
      <avue-form
        :option="datasourceOption"
        v-model="datasourceForm"
        @submit="handleDatasourceSubmit"
      />
    </el-dialog>
    <el-dialog title="租户产品包配置" append-to-body :visible.sync="packageBox" width="450px">
      <avue-form
        ref="formPackage"
        :option="packageOption"
        v-model="packageForm"
        @submit="handlePackageSubmit"
      />
    </el-dialog>
    <el-dialog title="租户产品包管理" append-to-body :visible.sync="packageSettingBox" width="1000px">
      <tenant-package></tenant-package>
    </el-dialog>
  </basic-container>
</template>

<script>
import {
  getList,
  getDetail,
  remove,
  update,
  add,
  setting,
  datasource,
  packageInfo,
  packageSetting,
} from '@/api/system/tenant'
import { getDetail as packageDetail } from '@/api/system/tenantpackage'
import { mapGetters } from 'vuex'
import { getMenuTree } from '@/api/system/menu'
import { validatenull } from '@/util/validate'

export default {
  data() {
    return {
      form: {},
      selectionList: [],
      query: {},
      loading: true,
      box: false,
      datasourceBox: false,
      packageBox: false,
      packageSettingBox: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      option: {
        /* height: 'auto',
        calcHeight: 30, */
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        selection: true,
        viewBtn: true,
        dialogWidth: 900,
        dialogClickModal: false,
        column: [
          {
            label: '租户ID',
            prop: 'tenantId',
            width: 100,
            search: true,
            addDisplay: false,
            editDisplay: false,
            span: 24,
            rules: [
              {
                required: true,
                message: '请输入租户ID',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '租户名称',
            prop: 'tenantName',
            search: true,
            width: 180,
            span: 24,
            rules: [
              {
                required: true,
                message: '请输入参数名称',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '联系人',
            prop: 'linkman',
            width: 100,
            search: true,
            rules: [
              {
                required: true,
                message: '请输入联系人',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '联系电话',
            prop: 'contactNumber',
            width: 150,
          },
          {
            label: '联系地址',
            prop: 'address',
            span: 24,
            minRows: 2,
            type: 'textarea',
            hide: true,
          },
          {
            label: '账号额度',
            prop: 'accountNumber',
            width: 90,
            slot: true,
            addDisplay: false,
            editDisplay: false,
          },
          {
            label: '过期时间',
            prop: 'expireTime',
            width: 180,
            slot: true,
            addDisplay: false,
            editDisplay: false,
          },
          {
            label: '绑定域名',
            prop: 'domainUrl',
            span: 24,
          },
          {
            label: '系统背景',
            prop: 'backgroundUrl',
            type: 'upload',
            listType: 'picture-img',
            dataType: 'string',
            action: '/api/blade-resource/oss/endpoint/put-file',
            propsHttp: {
              res: 'data',
              url: 'link',
            },
            hide: true,
            span: 24,
          },
        ],
      },
      data: [],
      settingForm: {},
      settingOption: {
        column: [
          {
            label: '账号额度',
            prop: 'accountNumber',
            type: 'number',
            span: 24,
          },
          {
            label: '过期时间',
            prop: 'expireTime',
            type: 'date',
            format: 'yyyy-MM-dd hh:mm:ss',
            valueFormat: 'yyyy-MM-dd hh:mm:ss',
            span: 24,
          },
        ],
      },
      datasourceForm: {},
      datasourceOption: {
        column: [
          {
            label: '数据源',
            prop: 'datasourceId',
            search: true,
            span: 24,
            type: 'select',
            dicUrl: '/api/blade-develop/datasource/select',
            props: {
              label: 'name',
              value: 'id',
            },
            rules: [
              {
                required: true,
                message: '请选择数据源',
                trigger: 'blur',
              },
            ],
          },
        ],
      },
      packageForm: {},
      packageOption: {
        column: [
          {
            label: '产品包',
            prop: 'packageId',
            search: true,
            span: 24,
            type: 'select',
            dicUrl: '/api/blade-system/tenant-package/select',
            props: {
              label: 'packageName',
              value: 'id',
            },
          },
          {
            label: '菜单预览',
            prop: 'menuId',
            span: 24,
            type: 'tree',
            dicData: [],
            hide: true,
            multiple: true,
            props: {
              label: 'title',
            },
          },
        ],
      },
    }
  },
  watch: {
    'packageForm.packageId'() {
      if (!validatenull(this.packageForm.packageId)) {
        packageDetail(this.packageForm.packageId).then((res) => {
          this.packageForm.menuId = res.data.data.menuId
          this.initData()
        })
      }
    },
  },
  computed: {
    ...mapGetters(['userInfo', 'permission']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.tenant_add, false),
        viewBtn: this.vaildData(this.permission.tenant_view, false),
        delBtn: this.vaildData(this.permission.tenant_delete, false),
        editBtn: this.vaildData(this.permission.tenant_edit, false),
      }
    },
    ids() {
      let ids = []
      this.selectionList.forEach((ele) => {
        ids.push(ele.id)
      })
      return ids.join(',')
    },
    tenantId() {
      return this.selectionList[0].tenantId
    },
  },
  methods: {
    initData() {
      getMenuTree().then((res) => {
        const column = this.findObject(this.packageOption.column, 'menuId')
        column.dicData = res.data.data
      })
    },
    rowSave(row, done, loading) {
      add(row).then(
        () => {
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
      update(row).then(
        () => {
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
    beforeOpen(done, type) {
      if (['view'].includes(type)) {
        getDetail(this.form.id).then((res) => {
          const data = res.data.data
          if (!(data.accountNumber > 0)) {
            data.accountNumber = '不限制'
          }
          if (!data.expireTime) {
            data.expireTime = '不限制'
          }
          this.form = data
        })
      }
      done()
    },
    searchReset() {
      this.query = {}
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
    handleSetting() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      if (this.selectionList.length === 1) {
        getDetail(this.selectionList[0].id).then((res) => {
          const data = res.data.data
          this.settingForm.accountNumber = data.accountNumber
          this.settingForm.expireTime = data.expireTime
        })
      } else {
        this.settingForm.accountNumber = -1
        this.settingForm.expireTime = ''
      }
      this.box = true
    },
    handleDatasource() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      if (this.selectionList.length !== 1) {
        this.$message.warning('只能选择一条数据')
        return
      }
      getDetail(this.selectionList[0].id).then((res) => {
        const data = res.data.data
        this.datasourceForm.datasourceId = data.datasourceId
      })
      this.datasourceBox = true
    },
    handlePackage() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      if (this.selectionList.length !== 1) {
        this.$message.warning('只能选择一条数据')
        return
      }
      if (this.selectionList.length === 1) {
        packageInfo(this.selectionList[0].id).then((res) => {
          const data = res.data.data
          this.packageForm.packageId = data.id
          this.packageForm.menuId = data.menuId
        })
      } else {
        this.packageForm.menuId = ''
      }
      this.packageBox = true
      //更新字典远程数据
      setTimeout(() => {
        const form = this.$refs.formPackage
        form.updateDic('packageId')
      }, 10)
    },
    handlePackageSetting() {
      this.packageSettingBox = true
    },
    handleSubmit(form, done, loading) {
      setting(this.ids, form).then(
        () => {
          this.onLoad(this.page)
          this.$message({
            type: 'success',
            message: '配置成功!',
          })
          done()
          this.box = false
        },
        (error) => {
          window.console.log(error)
          loading()
        }
      )
    },
    handleDatasourceSubmit(form, done, loading) {
      datasource(this.tenantId, form.datasourceId).then(
        () => {
          this.$message({
            type: 'success',
            message: '配置成功!',
          })
          done()
          this.datasourceBox = false
        },
        (error) => {
          window.console.log(error)
          loading()
        }
      )
    },
    handlePackageSubmit(form, done, loading) {
      packageSetting(this.tenantId, form.packageId).then(
        () => {
          this.onLoad(this.page)
          this.$message({
            type: 'success',
            message: '配置成功!',
          })
          done()
          this.packageBox = false
        },
        (error) => {
          window.console.log(error)
          loading()
        }
      )
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
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data
        this.page.total = data.total
        this.data = data.records
        this.loading = false
        this.selectionClear()
      })
    },
  },
}
</script>

<style>
</style>
