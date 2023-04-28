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
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot-scope="{row}" slot="suspensionState">
        <el-tag>{{row.suspensionState===1?'激活':'挂起'}}</el-tag>
      </template>
    </avue-crud>
    <el-dialog title="流程删除" append-to-body :visible.sync="followBox" width="20%">
      <el-form :model="form" ref="form" label-width="80px">
        <el-form-item label="删除理由">
          <el-input v-model="deleteReason" placeholder="请输入删除理由" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="followBox = false">关 闭</el-button>
        <el-button type="primary" @click="handleDelete">确 定</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
import { mapGetters } from 'vuex'
import { followList, deleteProcessInstance } from '@/api/flow/flow'

export default {
  data() {
    return {
      form: {},
      selectionId: '',
      processInstanceId: '',
      selectionList: [],
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      followBox: false,
      deleteReason: '',
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
        dialogWidth: 900,
        menuWidth: 100,
        dialogClickModal: false,
        column: [
          {
            label: '执行id',
            prop: 'executionId',
            search: true,
            width: 320,
          },
          {
            label: '流程key',
            prop: 'processDefinitionKey',
            search: true,
          },
          {
            label: '实例id',
            prop: 'processInstanceId',
            search: true,
            width: 320,
          },
          {
            label: '状态',
            prop: 'suspensionState',
            slot: true,
            width: 80,
          },
          {
            label: '发起人',
            prop: 'startUser',
            width: 100,
          },
          {
            label: '开始时间',
            prop: 'startTime',
            width: 165,
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
        delBtn: this.vaildData(this.permission.flow_follow_delete, false),
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
    rowDel(row) {
      this.followBox = true
      this.selectionId = row.id
      this.processInstanceId = row.processInstanceId
    },
    handleDelete() {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return deleteProcessInstance({
            deleteReason: this.deleteReason,
            processInstanceId: this.processInstanceId,
          })
        })
        .then(() => {
          this.onLoad(this.page)
          this.followBox = false
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
      followList(
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
.none-border {
  border: 0;
  background-color: transparent !important;
}
</style>
