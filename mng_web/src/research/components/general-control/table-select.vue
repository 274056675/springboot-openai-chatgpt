<template>
  <div class="table-select-box">
    <el-dialog
      v-dialogdrag
      :title="optionData.title"
      :visible.sync="optionData.isDialog"
      :destroy-on-close="optionData.destroy?true:false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      :before-close="handleClose"
      :width="optionData.width"
    >
      <avue-crud
        ref="tableSelectControl"
        v-if="optionData.isDialog"
        :option="optionData.option"
        :data="tableData"
        :search.sync="searchData"
        :page.sync="tablePage"
        :table-loading="isTableLoading"
        @search-change="searchChangeFun"
        @search-reset="searchResetFun"
        @current-change="currentChangeFun"
        @size-change="sizeChangeFun"
        @selection-change="selectionChangeFun"
      >
        <template slot="searchMenu" slot-scope="scope">
          <el-button size="small" v-if="optionData.randomBtn" @click="randomExtractFun">随机抽取</el-button>
        </template>
      </avue-crud>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setDialog(false)">取 消</el-button>
        <el-button type="primary" @click="getCurrSelectDataFun()" :loading="buttomLoading">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getDataApi } from '@/api/research/codelist'
export default {
  data() {
    return {
      buttomLoading:false,
      tableData: [],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        pageSizes: [10, 20, 30],
        background: true,
        layout: 'sizes, prev, pager, next, jumper,total',
      },
      searchData: {},
      isTableLoading: false,
      tableQueryData: {},
      tableSelectData: [],
      skip: false,
    }
  },
  watch: {
    optionData: {
      handler(newVal) {
        if (newVal.isDialog) {
          //搜索赋值
          if (newVal.isCarrySearch) {
            newVal.option.column.forEach((item) => {
              if (item.search && item.searchValue !== undefined) {
                this.tableQueryData[item.prop] = item.searchValue
              }
            })
          }
          this.getTableDataFun(newVal.tableId)
        }
      },
      immediate: true, //先执行一次handler
      deep: true,
    },
  },
  props: [
    'optionData',
    /* 
      title:'标题',
      isDialog:'显示隐藏弹窗',
      width:'表格宽度',
      tableId:'表格id',
      option:'表格配置',
      multiple:'是否多选',
      isPage:'是否分页',
      addType:{
        type:'添加方法类型',
        tableId:'添加数据表格id',
        isCell:'是否可以编辑',
      },
      asyncTableName:'存储同步表id的字段名',async_id   返回的数据{async_id:选择表格数据对象里面的可以为id的值}
      asyncTableIdName:'同步表的数据唯一id字段名' id
      isCarrySearch:'一开始是否执行搜索'
      randomBtn:'',//随机抽取按钮
      searchRandomData:'',//随机抽取数据查询条件 
      randomFilteName:'',//随机抽取过滤数据key名
      defaultData:{},//数据新增默认值
      searchData:{},默认搜索数据
      carryData:{}//点击确定后默认携带的数据
      noDisposeData:true,//不需要数据处理
    */
    'selectControlFun',
  ],
  methods: {
    //关闭弹窗
    setDialog(bool) {
      this.$refs.tableSelectControl.selectClear()
      this.selectControlFun('dialog', { bool })
    },
    //获取当前表格数据
    async getTableDataFun(tableId, page) {
      if (page === undefined && this.optionData.isPage) {
        page = {
          currentPage: this.tablePage.currentPage,
          pageSize: this.tablePage.pageSize,
        }
      }
      if (this.optionData.searchData) {
        this.tableQueryData = {
          ...this.tableQueryData,
          ...this.optionData.searchData,
        }
      }
      this.isTableLoading = true
      //通过接口获取所有树表格数据
      let tableQueryData = {}
      for (let key in this.tableQueryData) {
        if (this.tableQueryData[key] instanceof Array) {
          tableQueryData[key] = this.tableQueryData[key].join(',')
        } else if (
          this.tableQueryData[key] !== '' &&
          this.tableQueryData[key] !== undefined
        ) {
          tableQueryData[key] = this.tableQueryData[key]
        }
      }
      let data = {
        ...tableQueryData,
      }
      if (this.optionData.isPage) {
        data.pageNo = page.currentPage
        data.pageSize = page.pageSize
      } else {
        data.pageSize = -521
      }
      let tableDataRes = await getDataApi(tableId, data)
      tableDataRes = tableDataRes.data.data
      this.tableData = tableDataRes.records
      if (this.optionData.isPage) {
        this.tablePage.total = tableDataRes.total
      }
      this.isTableLoading = false
    },
    //获取当前选择的数据
    getCurrSelectDataFun() {
      if (this.tableSelectData.length <= 0) {
        if (this.optionData.messageText) {
          this.$message(this.optionData.messageText)
        } else {
          this.$message('请勾选需要添加的数据~')
        }
        return false
      }

      let checkArr = []
      if (this.optionData.noDisposeData) {
        checkArr = this.tableSelectData
      } else {
        this.tableSelectData.forEach((item) => {
          //添加数据表的id
          let obj = {}
          if (this.optionData.asyncTableIdName) {
            obj[this.optionData.asyncTableName] =
              item[this.optionData.asyncTableIdName]
          } else {
            obj[this.optionData.asyncTableName] = item.id
          }
          let dataKey = Object.keys(item)
          dataKey.forEach((key) => {
            if (key != 'id') {
              obj[key] = item[key]
            }
          })
          if (this.optionData.addType.isCell) {
            obj.$cellEdit = true
          }
          
          if (this.optionData.defaultData) {
            obj = {
              ...obj,
              ...this.optionData.defaultData,
            }
          }
          checkArr.push(obj)
        })
      }
      this.selectControlFun(this.optionData.addType.type, {
        data: checkArr,
        carryData: this.optionData.carryData,
      })
    },
    handleClose(done) {
      done()
    },
    //表格选择事件触发
    selectionChangeFun(column) {
      // column 所有选择数据的数组
      if (!this.optionData.multiple) {
        //单选
        if (this.skip) {
          return false
        }
        this.skip = true
        this.$refs.tableSelectControl.toggleSelection('')
        let currRow = []
        if (column.length > 0) {
          currRow.push(column[column.length - 1])
        }
        this.$refs.tableSelectControl.toggleSelection(currRow)
        setTimeout(() => {
          if (currRow.length >= 1) {
            this.tableSelectData = currRow[0]
          } else {
            this.tableSelectData = []
          }
          this.skip = false
        }, 0)
      } else {
        this.tableSelectData = column
      }
      
    },
    //随机抽取
    randomExtractFun() {
      this.$prompt('请输入随机抽取数量', '随机抽取', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      })
        .then(async ({ value }) => {
          value = Number(value)
          if (value > 0) {
            this.tableSelectData = []
            let data = {
              pageNo: 1,
              pageSize: -521,
              ...this.tableQueryData,
              ...this.optionData.searchRandomData,
            }
            let tableDataRes = await getDataApi(this.optionData.tableId, data)
            let randomData = tableDataRes.data.data.records
            console.log(
              this.optionData.nullSelect,
              this.optionData,
              this.optionData.randomFilteName
            )
            randomData = randomData.filter((item) => {
              if (
                this.optionData.nullSelect.includes(
                  item[this.optionData.randomFilteName]
                )
              ) {
                return false
              }
              return true
            })
            if (randomData.length <= value) {
              this.tableSelectData = randomData
            } else {
              let indexArr = []
              while (indexArr.length < value) {
                let index = Math.floor(Math.random() * randomData.length)
                if (!indexArr.includes(index)) {
                  indexArr.push(index)
                  this.tableSelectData.push(randomData[index])
                }
              }
              
            }
            if (this.tableSelectData.length <= 0) {
              this.$message('抽取失败，没有可抽取数据~')
            } else {
              this.getCurrSelectDataFun()
              this.$message({
                type: 'success',
                message: '抽取成功~',
              })
            }
          } else {
            this.$message('请输入正确的抽取数量~')
          }
        })
        .catch(() => {})
    },
    // 搜索
    searchChangeFun(params, done) {
      this.tableQueryData = params
      this.tablePage.currentPage = 1
      this.getTableDataFun(this.optionData.tableId)
      done()
    },
    // 清除搜索
    searchResetFun() {
      this.tableQueryData = {}
      this.tablePage.currentPage = 1
      this.getTableDataFun(this.optionData.tableId)
    },
    // 切换页
    currentChangeFun(page) {
      this.tablePage.currentPage = page
      this.getTableDataFun(this.optionData.tableId)
    },
    // 切换每页显示数
    sizeChangeFun(pageSize) {
      this.tablePage.currentPage = 1
      this.tablePage.pageSize = pageSize
      this.getTableDataFun(this.optionData.tableId)
    },
  },
}
</script>

<style></style>
