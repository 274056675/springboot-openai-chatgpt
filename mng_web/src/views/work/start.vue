<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      v-model="form"
      ref="crud"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot="menuLeft">
        <el-radio-group v-model="mode" size="small">
          <el-radio-button label="1">通用流程</el-radio-button>
          <el-radio-button label="2">定制流程</el-radio-button>
        </el-radio-group>
      </template>
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          size="small"
          icon="el-icon-video-play"
          v-if="permission.work_start_flow"
          @click.stop="handleStart(scope.row)"
        >发起</el-button>
        <el-button
          type="text"
          size="small"
          icon="el-icon-search"
          v-if="permission.work_start_image"
          @click.stop="handleImage(scope.row,scope.index)"
        >流程图</el-button>
      </template>
      <template slot-scope="{row}" slot="tenantId">
        <el-tag>{{row.tenantId===''?'通用':row.tenantId}}</el-tag>
      </template>
      <template slot-scope="{row}" slot="version">
        <el-tag>v{{row.version}}</el-tag>
      </template>
      <template slot-scope="{row}" slot="suspensionState">
        <el-tag>{{row.suspensionState===1?'激活':'挂起'}}</el-tag>
      </template>
      <template slot-scope="{row}" slot="category">
        <el-tag>{{row.categoryName}}</el-tag>
      </template>
    </avue-crud>
    <flow-design is-dialog :is-display.sync="flowBox" :process-definition-id="processDefinitionId"></flow-design>
  </basic-container>
</template>

<script>
import { mapGetters } from 'vuex'
import { startList } from '@/api/work/work'
import { flowCategory, flowRoute } from '@/util/flow'

export default {
  data() {
    return {
      form: {},
      mode: '1',
      selectionId: '',
      selectionList: [],
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      processDefinitionId: '',
      flowBox: false,
      workBox: false,
      option: {
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
        menuWidth: 150,
        dialogWidth: 900,
        dialogClickModal: false,
        column: [
          {
            label: '租户编号',
            prop: 'tenantId',
            slot: true,
            width: 120,
          },
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
            width: 100,
          },
          {
            label: '流程标识',
            prop: 'key',
          },
          {
            label: '流程名称',
            prop: 'name',
            search: true,
          },
          {
            label: '流程版本',
            prop: 'version',
            slot: true,
            width: 80,
          },
          {
            label: '状态',
            prop: 'suspensionState',
            slot: true,
            width: 80,
          },
          {
            label: '部署时间',
            prop: 'deploymentTime',
            width: 165,
          },
        ],
      },
      data: [],
    }
  },
  watch: {
    mode() {
      this.onLoad(this.page)
    },
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
    async handleStart(row) {
      this.$router.push({
        path: `/work/process/${flowRoute(this.flowRoutes, row.category)}/form/${
          row.id
        }`,
      })
    },
    handleImage(row) {
      this.processDefinitionId = row.id
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
        mode: this.mode,
      }
      this.loading = true
      startList(
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
