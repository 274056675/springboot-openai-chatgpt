<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      ref="crud"
      v-model="form"
      :page.sync="page"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          size="small"
          icon="el-icon-info"
          v-if="permission.work_send_detail"
          @click.stop="handleDetail(scope.row)"
        >详情</el-button>
        <el-button
          type="text"
          size="small"
          icon="el-icon-search"
          v-if="permission.work_send_follow"
          @click.stop="handleImage(scope.row,scope.index)"
        >流程图</el-button>
      </template>
      <template slot-scope="{row}" slot="processDefinitionVersion">
        <el-tag>v{{row.processDefinitionVersion}}</el-tag>
      </template>
      <template slot-scope="{row}" slot="processIsFinished">
        <el-tag>{{row.processIsFinished==='finished' ? '已完成' : '未完成'}}</el-tag>
      </template>
    </avue-crud>
    <flow-design is-dialog :is-display.sync="flowBox" :process-instance-id="processInstanceId"></flow-design>
  </basic-container>
</template>

<script>
import { mapGetters } from 'vuex'
import { sendList } from '@/api/work/work'
import { flowCategory, flowRoute } from '@/util/flow'
export default {
  data() {
    return {
      form: {},
      selectionId: '',
      selectionList: [],
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      processInstanceId: '',
      flowBox: false,
      workBox: false,
      option: {
        /* height: 'auto',
          calcHeight: 30, */
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        selection: true,
        editBtn: false,
        addBtn: false,
        viewBtn: false,
        delBtn: false,
        dialogWidth: 900,
        menuWidth: 150,
        dialogClickModal: false,
        column: [
          {
            label: '流程分类',
            type: 'select',
            row: true,
            dicUrl: '/api/blade-system/dict/dictionary?code=flow',
            props: {
              label: 'dictValue',
              value: 'dictKey',
            },
            dataType: 'number',
            slot: true,
            prop: 'category',
            search: true,
            hide: true,
            width: 100,
          },
          {
            label: '流程名称',
            prop: 'processDefinitionName',
            search: true,
          },
          {
            label: '当前步骤',
            prop: 'taskName',
          },
          {
            label: '流程版本',
            prop: 'processDefinitionVersion',
            slot: true,
            width: 80,
          },
          {
            label: '流程进度',
            prop: 'processIsFinished',
            slot: true,
            width: 80,
          },
          {
            label: '申请时间',
            prop: 'createTime',
            width: 165,
          },
        ],
      },
      data: [],
    }
  },
  computed: {
    ...mapGetters(['permission', 'flowRoutes']),
    ids() {
      let ids = []
      this.selectionList.forEach((ele) => {
        ids.push(ele.id)
      })
      return ids.join(',')
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
    selectionChange(list) {
      this.selectionList = list
    },
    selectionClear() {
      this.selectionList = []
      this.$refs.crud.toggleSelection()
    },
    async handleDetail(row) {
      
        this.$router.push({
          path: `/work/process/${flowRoute(
            this.flowRoutes,
            row.category
          )}/detail/${row.processInstanceId}/${row.businessId}`,
        })
    },
    handleImage(row) {
      this.processInstanceId = row.processInstanceId
      this.flowBox = true
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
      const query = {
        ...this.query,
        category: params.category ? flowCategory(params.category) : null,
      }
      this.loading = true
      sendList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, query)
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
