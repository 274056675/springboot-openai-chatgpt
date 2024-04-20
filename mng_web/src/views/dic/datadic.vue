<template>
  <div class="box">
    <div class="data-dic-box">
      <avue-crud
        :option="tableDicOption"
        :data="tableDicData"
        :page="tableDicPage"
        :table-loading="loading"
        ref="crud"
        v-model="tableDicForm"
        @row-del="(row)=>rowDel(row)"
        @row-update="rowUpdate"
        @row-save="rowSave"
        @search-change="searchChange"
        @search-reset="searchReset"
        @selection-change="selectionChange"
        @current-change="currentChange"
        @size-change="sizeChange"
        @refresh-change="refreshChange"
        @on-load="onLoadParent"
      >
        <template slot-scope="scope" slot="menuLeft">
          <el-button
            size="small"
            icon="el-icon-delete"
            v-if="selectionList.length>0"
            @click="rowDel(scope.row,'确定要删除所有选择的记录？')"
          >批量删除</el-button>
        </template>
        <template slot-scope="scope" slot="menu">
          <el-button
            size="small"
            icon="el-icon-s-tools"
            type="text"
            @click="openDicListFun(scope.row.id)"
          >字典配置</el-button>
        </template>
      </avue-crud>
    </div>
    <el-drawer
      title="字典列表"
      :visible.sync="isOpenDrawer"
      :append-to-body="true"
      :modal-append-to-body="true"
      :size="800"
    >
      <div class="data-dic-list-box">
        <avue-crud
          :option="tableDicListOption"
          :data="tableDicListData"
          :page="tableDicListPage"
          :table-loading="loadingChild"
          ref="crudChild"
          v-model="tableDicListForm"
          @row-del="(row)=>rowDelChild(row)"
          @row-update="rowUpdateChild"
          @row-save="rowSaveChild"
          @search-change="searchChangeChild"
          @search-reset="searchResetChild"
          @selection-change="selectionChangeChild"
          @current-change="currentChangeChild"
          @size-change="sizeChangeChild"
          @refresh-change="refreshChangeChild"
          @on-load="onLoadChild"
        >
          <!-- <template slot-scope="scope" slot="statusForm">
            <el-switch
              v-model="scope.row.status"
              :value="scope.value"
              :disabled="scope.disabled"
              active-value="1"
              inactive-value="0"
            ></el-switch>
          </template>-->
          <template slot-scope="scope" slot="statusSearch">
            <el-select v-model="scope.row.status" placeholder="请选择" :size="scope.size">
              <el-option label="启用" :value="1"></el-option>
              <el-option label="禁用" :value="0"></el-option>
            </el-select>
          </template>
          <template slot-scope="scope" slot="menuLeft">
            <el-button
              size="small"
              icon="el-icon-delete"
              v-if="selectionChildList.length>0"
              @click="rowDelChild(scope.row,'确定要删除所有选择的记录？')"
            >批量删除</el-button>
          </template>
        </avue-crud>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  getDicDataApi,
  addDicDataApi,
  editDicDataApi,
  delDicDataApi,
  getDicListDataApi,
  addDicListDataApi,
  editDicListDataApi,
  delDicListDataApi,
} from '@/api/research/datadic'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      // 数据字典
      tableDicForm: {},
      tableDicData: [],
      tableDicOption: {
        border: true, //开启边框
        index: true, //开启序号
        selection: true, //开启选择框 并且勾选触发 @selection-change
        reserveSelection: true, //保留之前的勾选
        align: 'center', //文本对齐方式
        menuAlign: 'center', //操作栏对齐方式
        columnBtn: false,
        searchMenuSpan: 8,
        column: [
          {
            label: '字典名称',
            prop: 'dictName',
            span: 24,
            rules: [
              {
                required: true,
                trigger: 'blur',
                message: '值不能为空',
              },
            ],
            search: true,
          },
          {
            label: '字典编号',
            prop: 'dictCode',
            span: 24,
            rules: [
              {
                required: true,
                trigger: 'blur',
                message: '值不能为空',
              },
            ],
            search: true,
          },
          {
            label: '描述',
            prop: 'description',
            span: 24,
            overHidden:true,
          },
        ],
      },
      tableDicPage: {
        pageSize: 10,
        pageSizes: [10, 30, 50, 100, 200],
        currentPage: 1,
        total: 0,
      },
      selectionList: [],
      query: {},
      loading: false,
      // 字典配置
      isOpenDrawer: false,
      currentDicId: '',
      tableDicListForm: {},
      tableDicListData: [],
      tableDicListOption: {
        border: true, //开启边框
        index: true, //开启序号
        selection: true, //开启选择框 并且勾选触发 @selection-change
        reserveSelection: true, //保留之前的勾选
        align: 'center', //文本对齐方式
        menuAlign: 'center', //操作栏对齐方式
        columnBtn: false,
        searchMenuSpan: 8,
        column: [
          {
            label: '名称',
            prop: 'itemText',
            span: 24,
            rules: [
              {
                required: true,
                trigger: 'blur',
                message: '值不能为空',
              },
            ],
            search: true,
            searchSpan: 8,
          },
          {
            label: '数据值',
            prop: 'itemValue',
            span: 24,
            rules: [
              {
                required: true,
                trigger: 'blur',
                message: '值不能为空',
              },
            ],
          },
          {
            label: '描述',
            prop: 'description',
            span: 24,
            hide: true,
          },
          {
            label: '排序值',
            prop: 'sortOrder',
            span: 24,
            hide: true,
            type: 'number',
            minRows: 1,
            value: 1,
            style: {
              width: '160px !important',
            },
          },
          {
            label: '是否启用',
            prop: 'status',
            span: 24,
            hide: true,
            value: true,
            search: true,
            searchSpan: 8,
            type: 'switch',
          },
        ],
      },
      tableDicListPage: {
        pageSize: 10,
        pageSizes: [10, 30, 50, 100, 200],
        currentPage: 1,
        total: 0,
      },
      selectionChildList: [],
      childQuery: {},
      loadingChild: false,
    }
  },
  computed: {
    ...mapGetters(['userInfo', 'permission']),
    ids() {
      let ids = []
      this.selectionList.forEach((ele) => {
        ids.push(ele.id)
      })
      return ids.join(',')
    },
    idsChild() {
      let ids = []
      this.selectionChildList.forEach((ele) => {
        ids.push(ele.id)
      })
      return ids.join(',')
    },
  },
  mounted() {},
  methods: {
    // 数据字典
    rowSave(row, done, loading) {
      addDicDataApi(row)
        .then((res) => {
          if (res.data.success == true) {
            this.$message({
              message: '保存成功~',
              type: 'success',
            })
            this.onLoadParent(this.tableDicPage, this.query)
            done()
          } else {
            throw new Error()
          }
        })
        .catch(() => {
          loading()
        })
    },
    rowUpdate(row, index, done, loading) {
      editDicDataApi(row)
        .then((res) => {
          if (res.data.success == true) {
            this.$message({
              message: '修改成功~',
              type: 'success',
            })
            this.onLoadParent(this.tableDicPage, this.query)
            done()
          } else {
            throw new Error()
          }
        })
        .catch(() => {
          loading()
        })
    },
    rowDel(row, text) {
      
      let ids = ''
      if (!text) {
        ids = row.id
        
        text = '确认要删除此记录？'
      } else {
        ids = this.ids
      }
      this.$confirm(text, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async () => {
          let removeRes = await delDicDataApi(ids)
          
          if (removeRes.data.success) {
            this.tableDicPage.currentPage = 1
            this.onLoadParent(this.tableDicPage, this.query)
            this.$message({
              type: 'success',
              message: '删除成功!',
            })
            this.$refs.crud.selectClear()
          }
        })
        .catch(() => {})
    },
    searchReset() {
      this.query = {}
      this.onLoadParent(this.tableDicPage)
    },
    searchChange(params, done) {
      this.query = params
      this.tableDicPage.currentPage = 1
      this.onLoadParent(this.tableDicPage, params)
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
      this.tableDicPage.currentPage = currentPage
    },
    sizeChange(pageSize) {
      this.tableDicPage.pageSize = pageSize
    },
    refreshChange() {
      this.onLoadParent(this.tableDicPage, this.query)
    },
    async onLoadParent(page, params = {}) {
      params = {
        ...params,
        ...this.query,
      }
      this.loading = true
      let res = await getDicDataApi(page.currentPage, page.pageSize, params)
      if (res.data.success) {
        let data = res.data.data
        this.tableDicData = data.records
        this.tableDicPage.total = data.total
      }
      this.loading = false
    },

    // 字典配置
    //数据处理
    setDataRowStatus(row) {
      if (row instanceof Array) {
        row = row.map((item) => {
          if (item.status === false || item.status === true) {
            item.status = item.status ? 1 : 0
          } else {
            item.status = item.status ? true : false
          }
          return item
        })
      } else {
        if (row.status === false || row.status === true) {
          row.status = row.status ? 1 : 0
        } else {
          row.status = row.status ? true : false
        }
      }
      return row
    },
    openDicListFun(id) {
      //打开字典配置
      this.currentDicId = id
      this.onLoadChild({
        currentPage: 1,
        pageSize: 10,
      })
      this.isOpenDrawer = true
    },
    rowSaveChild(row, done, loading) {
      row = this.setDataRowStatus(row)
      row.dictId = this.currentDicId
      addDicListDataApi(row)
        .then((res) => {
          if (res.data.success == true) {
            this.$message({
              message: '保存成功~',
              type: 'success',
            })
            this.onLoadChild(this.tableDicListPage, this.childQuery)
            done()
          } else {
            throw new Error()
          }
        })
        .catch(() => {
          loading()
        })
    },
    rowUpdateChild(row, index, done, loading) {
      row = this.setDataRowStatus(row)
      row.dictId = this.currentDicId
      editDicListDataApi(row)
        .then((res) => {
          if (res.data.success == true) {
            this.$message({
              message: '修改成功~',
              type: 'success',
            })
            this.onLoadChild(this.tableDicListPage, this.childQuery)
            done()
          } else {
            throw new Error()
          }
        })
        .catch(() => {
          loading()
        })
    },
    rowDelChild(row, text) {
      
      let ids = ''
      
      if (!text) {
        
        text = '确认要删除此记录？'
        ids = row.id
      } else {
        ids = this.idsChild
      }
      this.$confirm(text, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async () => {
          let removeRes = await delDicListDataApi(ids)
          if (removeRes.data.success) {
            this.tableDicListPage.currentPage = 1
            this.onLoadChild(this.tableDicListPage, this.childQuery)
            this.$message({
              type: 'success',
              message: '删除成功!',
            })
            this.$refs.crudChild.selectClear()
          }
        })
        .catch(() => {})
    },
    searchResetChild() {
      this.childQuery = {}
      this.onLoadChild(this.tableDicListPage)
    },
    searchChangeChild(params, done) {
      this.childQuery = params
      this.tableDicListPage.currentPage = 1
      this.onLoadChild(this.tableDicListPage, params)
      done()
    },
    selectionChangeChild(list) {
      this.selectionChildList = list
    },
    currentChangeChild(currentPage) {
      this.tableDicListPage.currentPage = currentPage
    },
    sizeChangeChild(pageSize) {
      this.tableDicListPage.pageSize = pageSize
    },
    refreshChangeChild() {
      this.onLoadChild(this.tableDicListPage, this.query)
    },
    async onLoadChild(page, params = {}) {
      params.dictId = this.currentDicId
      this.loadingChild = true
      let res = await getDicListDataApi(page.currentPage, page.pageSize, params)
      if (res.data.success) {
        let data = res.data.data
        data.records = this.setDataRowStatus(data.records)
        this.tableDicListData = data.records
        
        this.tableDicListPage.total = data.total
      }
      this.loadingChild = false
    },
  },
}
</script>

<style lang="scss" scoped>
.data-dic-box,
.data-dic-list-box {
  padding: 16px;
  background-color: #fff;
}
</style>

