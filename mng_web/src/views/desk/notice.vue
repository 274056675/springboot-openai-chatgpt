<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      ref="crud"
      @row-del="rowDel"
      v-model="form"
      :permission="permissionList"
      @row-update="rowUpdate"
      @row-save="rowSave"
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
          v-if="permission.notice_delete"
          @click="handleDelete"
        >删 除</el-button>
      </template>
      <template slot-scope="{row}" slot="category">
        <el-tag>{{row.categoryName}}</el-tag>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import { getList, remove, update, add, getNotice } from '@/api/desk/notice'
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
      selectionList: [],
      option: {
        /* height: 'auto',
          calcHeight: 30, */
        dialogWidth: 950,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        viewBtn: true,
        selection: true,
        excelBtn: true,
        dialogClickModal: false,
        column: [
          {
            label: '通知标题',
            prop: 'title',
            span: 24,
            row: true,
            search: true,
            rules: [
              {
                required: true,
                message: '请输入通知标题',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '通知类型',
            type: 'select',
            dicUrl: '/api/blade-system/dict/dictionary?code=notice',
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
                message: '请输入通知类型',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '通知时间',
            prop: 'releaseTimeRange',
            type: 'datetime',
            format: 'yyyy-MM-dd hh:mm:ss',
            valueFormat: 'yyyy-MM-dd hh:mm:ss',
            searchRange: true,
            hide: true,
            addDisplay: false,
            editDisplay: false,
            viewDisplay: false,
            search: true,
            rules: [
              {
                required: true,
                message: '请输入通知时间',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '通知日期',
            prop: 'releaseTime',
            type: 'date',
            format: 'yyyy-MM-dd hh:mm:ss',
            valueFormat: 'yyyy-MM-dd hh:mm:ss',
            rules: [
              {
                required: true,
                message: '请输入通知日期',
                trigger: 'click',
              },
            ],
          },
          {
            label: '通知内容',
            prop: 'content',
            component: 'AvueUeditor',
            options: {
              action: '/api/blade-resource/oss/endpoint/put-file',
              props: {
                res: 'data',
                url: 'link',
              },
            },
            hide: true,
            minRows: 6,
            span: 24,
          },
        ],
      },
      data: [],
    }
  },
  computed: {
    ...mapGetters(['permission']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.notice_add, false),
        viewBtn: this.vaildData(this.permission.notice_view, false),
        delBtn: this.vaildData(this.permission.notice_delete, false),
        editBtn: this.vaildData(this.permission.notice_edit, false),
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
        getNotice(this.form.id).then((res) => {
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
      const { releaseTimeRange } = this.query
      let values = {
        ...params,
        ...this.query,
      }
      if (releaseTimeRange) {
        values = {
          ...values,
          releaseTime_datege: releaseTimeRange[0],
          releaseTime_datelt: releaseTimeRange[1],
        }
        values.releaseTimeRange = null
      }
      this.loading = true
      getList(page.currentPage, page.pageSize, values).then((res) => {
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
