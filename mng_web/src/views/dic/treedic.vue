<template>
  <div class="box">
    <div class="tree-dic-box">
      <avue-crud
        :option="tableDicOption"
        :data="tableDicData"
        :page="tableDicPage"
        :table-loading="loading"
        ref="crud"
        v-model="tableDicForm"
        :before-open="beforeOpen"
        :before-close="beforeClose"
        @tree-load="treeLoad"
        @row-del="(row)=>rowDel(row)"
        @row-update="rowUpdate"
        @row-save="rowSave"
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
            @click="addChildData(scope.row.id)"
          >添加下级</el-button>
        </template>
      </avue-crud>
    </div>
  </div>
</template>

<script>
import { apiRequestHead } from '@/config/url.js'
import {
  getTreeDicDataApi,
  addTreeDicDataApi,
  editTreeDicDataApi,
  delTreeDicDataApi,
  getTreeChildeDicDataApi,
} from '@/api/research/datadic'
import { getTreeAllDataApi, getTreeItemDataApi } from '@/api/research/codelist'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      timerInte: null,
      maps: new Map(), //存储树表格用于数据回显
      tableDicForm: {},
      tableDicData: [],
      tableDicPage: {
        pageSize: 10,
        pageSizes: [10, 30, 50, 100, 200],
        currentPage: 1,
        total: 0,
      },
      tableDicOption: {
        selection: true, //开启选择框 并且勾选触发 @selection-change
        reserveSelection: true, //保留之前的勾选
        align: 'center', //文本对齐方式
        menuAlign: 'center', //操作栏对齐方式
        columnBtn: false,
        lazy: true,
        column: [
          {
            label: '父级节点',
            prop: 'pid',
            span: 24,
            type: 'tree',
            hide: true,
            editDisabled: true,
            dicUrl: `/api/${apiRequestHead}/sys/loadTreeData`,
            dicQuery: {
              pid: 0,
              tableName: 'sys_category', //数据库表名
              text: 'name', //展开列字段名
              code: 'id', //主键名
              pidField: 'pid', //父id名
              hasChildField: 'has_child', //是否有子集key名
              condition: '',
            },
            props: {
              label: 'title',
              value: 'key',
            },
            dicFormatter: (res) => {
              return res.data
            },
            multiple: false,
            filter: false,
            lazy: true,
            dicFlag: true,
            addDisabled: false,
            treeLoad: async (node, resolve) => {
              
              if (node.data instanceof Array && node.level != 0) {
                return false
              }
              let treeRes = await getTreeAllDataApi({
                pid: node.data.key ? node.data.key : 0,
                tableName: 'sys_category', //数据库表名
                text: 'name', //展开列字段名
                code: 'id', //主键名
                pidField: 'pid', //父id名
                hasChildField: 'has_child', //是否有子集key名
                condition: '',
              })
              if (treeRes.data.success) {
                resolve(treeRes.data.data)
              } else {
                resolve([])
              }
            },
          },
          {
            label: '分类名称',
            prop: 'name',
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
            label: '分类编码',
            prop: 'code',
            span: 24,
            rules: [
              {
                required: true,
                trigger: 'blur',
                message: '值不能为空',
              },
            ],
          },
        ],
      },

      selectionList: [],
      query: {},
      loading: false,
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
  },
  mounted() {},
  methods: {
    async addChildData(id) {
      // this.tableDicForm.pid = id
      const column = this.findObject(this.tableDicOption.column, 'pid')
      column.value = id
      column.addDisabled = true
      this.setFormPidText(id)
      this.$refs.crud.rowAdd()
    },
    //设置树表格的表单pid文本
    async setFormPidText(id) {
      
      let itemRes = await getTreeItemDataApi({
        tableName: 'sys_category',
        tableLine: 'name',
        rowKey: 'id',
        key: id,
      })
      if (itemRes.data.success) {
        this.timerInte = setInterval(() => {
          let dom = document.querySelector('label[for=pid]')
          
          if (dom) {
            dom.parentNode.querySelector('input').value = itemRes.data.data[0]
              ? itemRes.data.data[0]
              : ''
            clearInterval(this.timerInte)
          }
        }, 300)
      }
    },
    // 数据字典
    rowSave(row, done, loading) {
      addTreeDicDataApi(row)
        .then((res) => {
          if (res.data.success == true) {
            this.$message({
              message: '保存成功~',
              type: 'success',
            })
            this.onLoadParent(this.tableDicPage, this.query)
            this.treeTableDataEcho('add', row)
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
      editTreeDicDataApi(row)
        .then((res) => {
          if (res.data.success == true) {
            this.$message({
              message: '修改成功~',
              type: 'success',
            })
            this.onLoadParent(this.tableDicPage, this.query)
            this.treeTableDataEcho('edit', row)
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
          let removeRes = await delTreeDicDataApi(ids)
          
          if (removeRes.data.success) {
            this.tableDicPage.currentPage = 1
            this.onLoadParent(this.tableDicPage, this.query)
            row.id = ids
            this.treeTableDataEcho('del', row)
            this.$message({
              type: 'success',
              message: '删除成功!',
            })
            this.$refs.crud.selectClear()
          }
        })
        .catch(() => {})
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
      
      this.loading = true
      params.pid = 0
      let res = await getTreeDicDataApi(page.currentPage, page.pageSize, params)
      if (res.data.success) {
        let data = res.data.data
        this.tableDicData = data.records.map((item) => {
          item.hasChildren = item.hasChild === '0' ? true : false
          return item
        })
        

        this.tableDicPage.total = data.total
      }
      this.loading = false
    },
    async treeLoad(tree, treeNode, resolve) {
      const pid = tree.id
      this.maps.set(pid, { tree, treeNode, resolve })
      let data = {
        pid,
      }
      let tableDataRes = await getTreeChildeDicDataApi(data)
      
      if (tableDataRes.data.success) {
        let resData = tableDataRes.data.data
        if (!resData) {
          resData = []
        }
        resData = resData.map((item) => {
          item.hasChildren = item.hasChild === '0' ? true : false
          return item
        })
        
        resolve(resData)
      } else {
        resolve([])
      }
    },
    //表格树 数据回显逻辑
    async treeTableDataEcho(type, row) {
      if (type == 'del') {
        this.$set(
          this.$refs.crud.$refs.table.store.states.lazyTreeNodeMap,
          row.pid,
          []
        )
      }
      this.maps.forEach((item, key) => {
        const { tree, treeNode, resolve } = this.maps.get(key)
        this.treeLoad(tree, treeNode, resolve)
      })
    },
    async beforeOpen(done, type) {
      if (['edit'].includes(type)) {
        this.setFormPidText(this.tableDicForm.pid)
      }
      done()
    },

    beforeClose(done) {
      if (this.timerInte) {
        clearInterval(this.timerInte)
      }
      const column = this.findObject(this.tableDicOption.column, 'pid')
      column.value = ''
      column.addDisabled = false
      done()
    },
  },
}
</script>

<style lang="scss" scoped>
.tree-dic-box {
  padding: 16px;
  background-color: #fff;
}
</style>

