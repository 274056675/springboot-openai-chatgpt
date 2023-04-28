<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      :permission="permissionList"
      v-model="form"
      ref="crud"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @row-del="rowDel"
      :before-open="beforeOpen"
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
          plain
          v-if="permission.oss_delete"
          @click="handleDelete"
        >删 除</el-button>
      </template>
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          icon="el-icon-video-play"
          size="small"
          v-if="userInfo.role_name.includes('admin')"
          @click="handleDebug(scope.row)"
        >调试</el-button>
        <el-button
          type="text"
          icon="el-icon-circle-check"
          size="small"
          v-if="permission.oss_enable"
          @click.stop="handleEnable(scope.row)"
        >启用</el-button>
      </template>
      <template slot-scope="{row}" slot="status">
        <el-tag>{{row.statusName}}</el-tag>
      </template>
      <template slot-scope="{row}" slot="category">
        <el-tag>{{row.categoryName}}</el-tag>
      </template>
    </avue-crud>
    <el-dialog title="对象存储上传调试" append-to-body :visible.sync="box" width="550px">
      <avue-form ref="form" :option="debugOption" v-model="debugForm" @submit="handleSubmit" />
    </el-dialog>
  </basic-container>
</template>

<script>
import {
  getList,
  getDetail,
  add,
  update,
  remove,
  enable,
} from '@/api/resource/oss'
import { mapGetters } from 'vuex'
import func from '@/util/func'

export default {
  data() {
    return {
      form: {},
      query: {},
      loading: true,
      box: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      selectionList: [],
      option: {
        /*  height: 'auto',
          calcHeight: 30, */
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        viewBtn: true,
        selection: true,
        menuWidth: 350,
        labelWidth: 100,
        dialogWidth: 880,
        dialogClickModal: false,
        column: [
          {
            label: '分类',
            type: 'radio',
            value: 1,
            span: 24,
            width: 100,
            searchLabelWidth: 50,
            row: true,
            dicUrl: '/api/blade-system/dict/dictionary?code=oss',
            props: {
              label: 'dictValue',
              value: 'dictKey',
            },
            dataType: 'number',
            slot: true,
            prop: 'category',
            search: true,
            rules: [
              {
                required: true,
                message: '请选择分类',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '资源编号',
            prop: 'ossCode',
            span: 24,
            width: 120,
            search: true,
            rules: [
              {
                required: true,
                message: '请输入资源编号',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '资源地址',
            prop: 'endpoint',
            span: 24,
            rules: [
              {
                required: true,
                message: '请输入资源地址',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '空间名',
            prop: 'bucketName',
            span: 24,
            width: 120,
            rules: [
              {
                required: true,
                message: '请输入空间名',
                trigger: 'blur',
              },
            ],
          },
          {
            label: 'accessKey',
            prop: 'accessKey',
            span: 24,
            search: true,
            width: 200,
            overHidden: true,
            rules: [
              {
                required: true,
                message: '请输入accessKey',
                trigger: 'blur',
              },
            ],
          },
          {
            label: 'secretKey',
            prop: 'secretKey',
            span: 24,
            width: 200,
            overHidden: true,
            rules: [
              {
                required: true,
                message: '请输入secretKey',
                trigger: 'blur',
              },
            ],
          },
          {
            label: 'appId',
            prop: 'appId',
            span: 24,
            hide: true,
            display: false,
          },
          {
            label: 'region',
            prop: 'region',
            span: 24,
            hide: true,
            display: false,
          },
          {
            label: '是否启用',
            prop: 'status',
            span: 24,
            width: 80,
            align: 'center',
            slot: true,
            addDisplay: false,
            editDisplay: false,
            viewDisplay: false,
          },
          {
            label: '备注',
            prop: 'remark',
            span: 24,
            hide: true,
          },
        ],
      },
      data: [],
      debugForm: {
        code: '',
      },
      debugOption: {
        submitText: '提交',
        column: [
          {
            label: '资源编号',
            prop: 'code',
            disabled: true,
            span: 24,
          },
          {
            label: '上传背景',
            prop: 'backgroundUrl',
            type: 'upload',
            listType: 'picture-img',
            dataType: 'string',
            action: '/api/blade-resource/oss/endpoint/put-file',
            propsHttp: {
              res: 'data',
              url: 'link',
            },
            span: 24,
          },
        ],
      },
    }
  },
  watch: {
    'form.category'() {
      const category = func.toInt(this.form.category)
      this.$refs.crud.option.column.filter((item) => {
        if (item.prop === 'appId') {
          item.display = category === 4
        }
        if (item.prop === 'region') {
          item.display = category === 4
        }
      })
    },
    'debugForm.code'() {
      const column = this.findObject(this.debugOption.column, 'backgroundUrl')
      column.action = `/api/blade-resource/oss/endpoint/put-file?code=${this.debugForm.code}`
    },
  },
  computed: {
    ...mapGetters(['userInfo', 'permission']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.oss_add),
        viewBtn: this.vaildData(this.permission.oss_view),
        delBtn: this.vaildData(this.permission.oss_delete),
        editBtn: this.vaildData(this.permission.oss_edit),
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
  methods: {
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
    handleEnable(row) {
      this.$confirm('是否确定启用这条配置?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return enable(row.id)
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
    handleDebug(row) {
      this.box = true
      this.debugForm.code = row.ossCode
      this.debugForm.backgroundUrl = ''
    },
    handleSubmit(form, done) {
      this.$message({
        type: 'success',
        message: `获取到图片地址:[${form.backgroundUrl}]`,
      })
      done()
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
    beforeOpen(done, type) {
      if (['edit', 'view'].includes(type)) {
        getDetail(this.form.id).then((res) => {
          this.form = res.data.data
        })
      }
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
