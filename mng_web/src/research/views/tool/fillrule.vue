<template>
  <basic-container>
    <avue-crud
      ref="crud"
      v-model="tableForm"
      :page.sync="tablePage"
      :data="tableData"
      :option="tableOption"
      :before-open="tableBeforeOpen"
      @search-change="searchChange"
      @search-reset="searchResetFun"
      @selection-change="selectionChange"
      @on-load="onLoad"
      @row-save="saveTableFun"
      @row-update="updateTableFun"
      @current-change="currentChange"
      @size-change="sizeChange"
    >
      <template slot="menuLeft">
        <el-button
          size="small"
          type="primary"
          icon="el-icon-upload2"
          @click="tableHandleCommand({ type:'export',data:{} })"
        >导出</el-button>
        <el-button
          size="small"
          type="primary"
          icon="el-icon-download"
          @click="tableHandleCommand({ type:'clickExport',data:{} })"
        >导入</el-button>
        <el-dropdown v-if="tableSelectArr.length>0" @command="tableHandleCommand">
          <el-button size="small">
            批量操作
            <i class="el-icon-arrow-down"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item :command="{ type: 'removeAll', data: {} }">
              <i class="el-icon-delete"></i> 删除
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
      <!-- 自定义操作按钮 -->
      <template slot-scope="scope" slot="menu">
        <el-dropdown @command="tableHandleCommand">
          <span class="el-dropdown-link" style="font-size:12px;color:#09ADFF;margin-left: 10px;">
            更多
            <i class="el-icon-arrow-down"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item :command="{ type: 'test', data: scope.row }">功能测试</el-dropdown-item>
            <el-dropdown-item :command="{ type: 'del', data: scope.row }">删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
    </avue-crud>
    <el-upload
      style="display:none"
      ref="exportUpload"
      :show-file-list="false"
      accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      action="action"
      :before-upload="exportFileFun"
    >
      <el-button size="small" type="primary" icon="el-icon-download">导入</el-button>
    </el-upload>
  </basic-container>
</template>

<script>
import {
  verifyCodeApi,
  getFillRuleDataApi,
  addFillRuleApi,
  editFillRuleApi,
  delectFillRuleApi,
  importFillRuleApi,
  exportFillRuleApi,
  carryFillRuleApi,
} from '@/api/research/fillrule'

export default {
  components: {},
  data() {
    return {
      //表格分页
      tablePage: {
        //设置表分页信息
        pageSize: 10, //每页显示数
        pagerCount: 5, //页码按钮的数量，当总页数超过该值时会折叠
        total: 0, //总条目数
        pageSizes: [10, 20, 30], //配置每页显示数的下拉框
      },
      tableForm: {},

      //表格设置
      tableOption: {
        //table表格配置
        searchMenuSpan: 8,
        border: true, //开启边框
        index: true, //开启序号
        indexLabel: '#', //序号文本 默认#
        selection: true, //开启选择框 并且勾选触发 @selection-change
        labelWidth: 110, //label宽度
        dialogWidth: '70%', //弹出层宽度
        dialogFullscreen: false, //是否全屏展开
        reserveSelection: true, //保留之前的勾选
        align: 'center', //文本对齐方式
        menuAlign: 'center', //操作栏对齐方式
        delBtn: false,
        columnBtn: false,
        refreshBtn: false,
        column: [
          //表头
          {
            label: '规则名称', //文本
            prop: 'ruleName', //字段名
            search: true, //开启搜索
            span: 24,
            rules: [
              {
                required: true,
                message: '请输入表单名称',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '规则Code', //文本
            prop: 'ruleCode', //字段名
            search: true, //开启搜索
            span: 24,
            rules: [
              {
                required: true,
                message: '请输入表单编码',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '规则实现类',
            prop: 'ruleClass',
            span: 24,
            rules: [
              {
                required: true,
                message: '请输入表单编码',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '规则参数',
            prop: 'ruleParams',
            span: 24,
            rules: [],
          },
        ],
      },
      //表格数据
      tableData: [],
      tableSelectArr: [],
      //搜索数据
      tableQuery: {},
    }
  },
  computed: {},
  mounted() {
    let timer = ''
    let ruleCodeValidate = (rule, value, callback) => {
      console.log(rule, value)
      if (timer) {
        clearTimeout(timer)
      }
      timer = setTimeout(async () => {
        let obj = {
          tableName: 'sys_fill_rule',
          fieldName: 'rule_code',
          fieldVal: value,
        }
        if (this.tableForm.id) {
          obj.dataId = this.tableForm.id
        }
        let res = await verifyCodeApi(obj)
        if (res.data.success) {
          callback()
        } else {
          callback('当前表单编码已存在！')
        }
      }, 300)
    }
    let ruleParamsValidate = (rule, value, callback) => {
      if (value === '') {
        callback()
      }
      try {
        JSON.parse(value)
        callback()
      } catch (error) {
        callback('请输入JSON字符串,如：{"test":123}')
      }
    }
    let columnCode = this.findObject(this.tableOption.column, 'ruleCode')
    columnCode.rules.push({ validator: ruleCodeValidate, trigger: 'blur' })
    let columnparams = this.findObject(this.tableOption.column, 'ruleParams')
    console.log(columnparams)
    columnparams.rules.push({ validator: ruleParamsValidate, trigger: 'blur' })
  },
  methods: {
    //获取数据
    async onLoad() {
      //page 分页信息  操作分页触发
      let obj = {
        current: this.tablePage.currentPage,
        size: this.tablePage.pageSize,
        ...this.tableQuery,
      }
      let dataRes = await getFillRuleDataApi(obj)
      if (dataRes.data.success) {
        let data = dataRes.data.data
        this.tableData = data.records
        this.tablePage.total = data.total
      }
      console.log('表格数据', dataRes)
    },
    //保存
    saveTableFun(row, done, loading) {
      console.log('保存===========>', row)
      if (!row.isonlineform) {
        row.cgformCode = ''
        row.cgformTemplate = ''
        row.desformDesignJson = ''
      } else {
        //获取当前选择online表单的数据并且处理成表单设计器json
      }
      addFillRuleApi(row)
        .then((addRes) => {
          if (addRes.data.success) {
            this.$message({
              message: '新增成功',
              type: 'success',
            })
            this.tablePage.currentPage = 1
            this.onLoad()
            done()
          } else {
            loading()
          }
        })
        .catch(() => {
          loading()
        })
    },
    //编辑
    async updateTableFun(row, index, done, loading) {
      if (!row.isonlineform) {
        row.cgformCode = ''
        row.cgformTemplate = ''
        row.desformDesignJson = ''
      } else {
        //获取当前选择online表单的数据并且处理成表单设计器json
      }
      editFillRuleApi(row)
        .then((upDateRes) => {
          if (upDateRes.data.success) {
            this.$message({
              message: '修改成功',
              type: 'success',
            })
            this.onLoad()
            done()
          } else {
            loading()
          }
        })
        .catch(() => {
          loading()
        })
    },
    //删除
    async delTableFun(row) {
      this.$confirm('确认要删除此记录?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async () => {
          let delRes = await delectFillRuleApi(row.id)
          if (delRes.data.success) {
            this.$message({
              message: '删除成功',
              type: 'success',
            })
            this.$refs.crud.selectClear()
            this.onLoad()
          }
        })
        .catch(() => {})
    },
    //选择
    selectionChange(list) {
      this.tableSelectArr = []
      list.forEach((item) => {
        this.tableSelectArr.push(item.id)
      })
    },
    //切换页
    currentChange(currentPage) {
      this.tablePage.currentPage = currentPage
      this.onLoad()
    },
    //切换每页显示数
    sizeChange(pageSize) {
      this.tablePage.pageSize = pageSize
      this.tablePage.currentPage = 1
      this.onLoad()
    },
    searchChange(params, done) {
      //params 搜索数据  done() 取消加载
      console.log('@search-change 搜索 ====>', params)
      this.tablePage.currentPage = 1
      this.tableQuery = params
      this.onLoad()
      done()
    },
    searchResetFun() {
      this.tablePage.currentPage = 1
      this.tableQuery = {}
      this.onLoad()
    },
    tableBeforeOpen(done, type) {
      let columnCode = this.findObject(this.tableOption.column, 'ruleCode')
      if (type == 'add') {
        columnCode.disabled = false
      } else {
        columnCode.disabled = true
      }
      done()
    },
    //导入
    exportFileFun(file) {
      console.log(file)
      return false
    },
    //更多操作
    /*  
     del:删除  removeAll:批量删除  test:功能测试  export:导出  clickExport:点击导入  carry:功能测试
    */
    tableHandleCommand(obj) {
      let type = obj.type
      let row = obj.data
      if (obj.type == 'removeAll') {
        this.$confirm('确认要删除所选择的记录?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(async () => {
            let allDelRes = await delectFillRuleApi(
              this.tableSelectArr.join(',')
            )
            if (allDelRes.data.success) {
              this.tablePage.currentPage = 1
              this.onLoad()
              this.$message({
                message: '删除成功',
                type: 'success',
              })
              this.$refs.crud.selectClear()
            }
          })
          .catch(() => {})
      }
      if (obj.type == 'del') {
        this.delTableFun(row)
      }
      if (type == 'test') {
        carryFillRuleApi(row.ruleCode).then((carryRes) => {
          console.log(carryRes)
          if (carryRes.data.success) {
            this.$alert(`生成结果：${carryRes.data.data}`, '填值规则功能测试', {
              confirmButtonText: '确定',
              type: 'success',
            })
          }
        })
      }
      if (type == 'clickExport') {
        console.log(this.$refs.exportUpload)
        this.$refs.exportUpload.$children[0].$refs.input.click()
      }
      if (type == 'import') {
      }
      if (type == 'carry') {
      }
    },
  },
}
</script>

<style lang="scss" scoped>
</style>