<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      ref="crud"
      v-model="form"
      :permission="permissionList"
      :page.sync="page"
      :before-open="beforeOpen"
      @search-change="searchChange"
      @search-reset="searchReset"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    ></avue-crud>
  </basic-container>
</template>

<script>
import { getUsualList, getUsualLogs } from '@/api/logs'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      form: {},
      selectionList: [],
      query: {},
      loading: true,
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
        viewBtn: true,
        editBtn: false,
        addBtn: false,
        delBtn: false,
        menuWidth: 120,
        dialogType: 'drawer',
        column: [
          {
            label: '服务id',
            prop: 'serviceId',
            search: true,
          },
          {
            label: '服务host',
            prop: 'serverHost',
            search: true,
          },
          {
            label: '服务ip',
            prop: 'serverIp',
          },
          {
            label: '软件环境',
            prop: 'env',
            width: '80',
          },
          {
            label: '日志级别',
            prop: 'logLevel',
          },
          {
            label: '日志id',
            prop: 'logId',
          },
          {
            label: '请求接口',
            prop: 'requestUri',
          },
          {
            label: '日志时间',
            prop: 'createTime',
            width: '180',
          },
          {
            label: '用户代理',
            prop: 'userAgent',
            span: 24,
            hide: true,
          },
          {
            label: '日志数据',
            prop: 'logData',
            type: 'textarea',
            span: 24,
            minRows: 2,
            hide: true,
          },
          {
            label: '请求数据',
            prop: 'params',
            type: 'textarea',
            span: 24,
            minRows: 2,
            hide: true,
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
        viewBtn: this.vaildData(this.permission.log_usual_view, false),
      }
    },
  },
  methods: {
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
    beforeOpen(done, type) {
      if (['edit', 'view'].includes(type)) {
        getUsualLogs(this.form.id).then((res) => {
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
      getUsualList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data
        this.page.total = data.total
        this.data = data.records
        this.loading = false
      })
    },
  },
}
</script>

<style>
</style>
