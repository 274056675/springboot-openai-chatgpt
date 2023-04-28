<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      :permission="permissionList"
      :before-open="beforeOpen"
      v-model="form"
      ref="crud"
      @row-del="rowDel"
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
          type="primary"
          size="small"
          plain
          v-if="permission.attach_upload"
          icon="el-icon-upload2"
          @click="handleUpload"
        >上 传</el-button>
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          plain
          v-if="permission.attach_delete"
          @click="handleDelete"
        >删 除</el-button>
      </template>
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          icon="el-icon-download"
          size="small"
          v-if="permission.attach_download"
          @click="handleDownload(scope.row)"
        >下载</el-button>
      </template>
      <template slot-scope="{row}" slot="attachSize">
        <el-tag>{{`${row.attachSize} KB`}}</el-tag>
      </template>
    </avue-crud>
    <el-dialog title="附件管理" append-to-body :visible.sync="attachBox" width="555px">
      <avue-form ref="form" :option="attachOption" v-model="attachForm" :upload-after="uploadAfter"></avue-form>
    </el-dialog>
  </basic-container>
</template>

<script>
import { getList, getDetail, remove } from '@/api/resource/attach'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      form: {},
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      attachBox: false,
      selectionList: [],
      option: {
        /* height: 'auto',
          calcHeight: 30, */
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        viewBtn: true,
        selection: true,
        dialogClickModal: false,
        column: [
          {
            label: '附件地址',
            prop: 'link',
            rules: [
              {
                required: true,
                message: '请输入附件地址',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '附件域名',
            prop: 'domainUrl',
            search: true,
            rules: [
              {
                required: true,
                message: '请输入附件域名',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '附件名称',
            prop: 'name',
            search: true,
            rules: [
              {
                required: true,
                message: '请输入附件名称',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '附件原名',
            prop: 'originalName',
            search: true,
            rules: [
              {
                required: true,
                message: '请输入附件原名',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '附件拓展名',
            prop: 'extension',
            rules: [
              {
                required: true,
                message: '请输入附件拓展名',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '附件大小',
            prop: 'attachSize',
            slot: true,
            rules: [
              {
                required: true,
                message: '请输入附件大小',
                trigger: 'blur',
              },
            ],
          },
        ],
      },
      data: [],
      attachForm: {},
      attachOption: {
        submitBtn: false,
        emptyBtn: false,
        column: [
          {
            label: '附件上传',
            prop: 'attachFile',
            type: 'upload',
            drag: true,
            loadText: '模板上传中，请稍等',
            span: 24,
            propsHttp: {
              res: 'data',
            },
            action: '/api/blade-resource/oss/endpoint/put-file-attach',
          },
        ],
      },
    }
  },
  computed: {
    ...mapGetters(['permission']),
    permissionList() {
      return {
        addBtn: false,
        editBtn: false,
        viewBtn: false,
        delBtn: this.vaildData(this.permission.attach_delete, false),
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
    handleUpload() {
      this.attachBox = true
    },
    uploadAfter(res, done, loading, column) {
      window.console.log(column)
      this.attachBox = false
      this.refreshChange()
      done()
    },
    handleDownload(row) {
      window.open(`${row.link}`)
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
