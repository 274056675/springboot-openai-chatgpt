<template>
  <avue-crud
    :option="option"
    :table-loading="loading"
    :data="data"
    :page.sync="page"
    :permission="permissionList"
    :before-open="beforeOpen"
    v-model="form"
    ref="crud"
    @row-update="rowUpdate"
    @row-save="rowSave"
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
      <el-button type="danger" size="small" icon="el-icon-delete" plain @click="handleDelete">删 除</el-button>
    </template>
  </avue-crud>
</template>

<script>
import {
  getList,
  getDetail,
  add,
  update,
  remove,
} from '@/api/system/tenantpackage'
import { mapGetters } from 'vuex'
import { getMenuTree } from '@/api/system/menu'

export default {
  name: 'tenantPackage',
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
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        viewBtn: true,
        selection: true,
        dialogClickModal: false,
        dialogWidth: 600,
        column: [
          {
            label: '产品包名',
            prop: 'packageName',
            search: true,
            span: 24,
            rules: [
              {
                required: true,
                message: '请输入产品包名称',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '菜单列表',
            prop: 'menuId',
            span: 24,
            type: 'tree',
            dicData: [],
            hide: true,
            multiple: true,
            props: {
              label: 'title',
            },
            rules: [
              {
                required: true,
                message: '请选择菜单',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '备注',
            prop: 'remark',
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
        addBtn: true,
        viewBtn: false,
        delBtn: true,
        editBtn: true,
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
    initData() {
      getMenuTree().then((res) => {
        const column = this.findObject(this.option.column, 'menuId')
        column.dicData = res.data.data
      })
    },
    rowSave(row, done, loading) {
      row.menuId = row.menuId.join(',')
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
          loading()
          window.console.log(error)
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
          loading()
          console.log(error)
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
      if (['add', 'edit'].includes(type)) {
        this.initData()
      }
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
