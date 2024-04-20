<template>
  <div class="user-control">
    <div class="user-control-box">
      <avue-select
        v-if="multiple"
        v-model="selectValue"
        placeholder="请选择 用户"
        type="tree"
        ref="avueSelect"
        :props="userProps"
        :multiple="multiple"
        :dic="allUserData"
        :size="tableItemScope ? tableItemScope.size : ''"
        :disabled="true"
        @click="openUserDialogFun(tableItemVal, tableItemName, !disabled)"
      ></avue-select>
      <avue-select
        v-else
        v-model="tableItemVal"
        placeholder="请选择 用户"
        type="tree"
        ref="avueSelect"
        :props="userProps"
        :dic="allUserData"
        :size="tableItemScope ? tableItemScope.size : ''"
        :disabled="true"
        @click="openUserDialogFun(tableItemVal, tableItemName, !disabled)"
      ></avue-select>
    </div>
    <el-dialog
      title="根据部门选择用户"
      v-dialogdrag
      :visible.sync="userDialog"
      v-if="userDialog"
      class="user_dialog_box"
      :modal-append-to-body="true"
      :append-to-body="true"
      width="1200px"
      top="20px"
    >
      <div class="user_dialog_content">
        <div class="content-left-tree">
          <el-tree
            ref="userDepartTree"
            :props="departProps"
            :check-strictly="true"
            node-key="value"
            :data="allDepartData"
            @node-click="userControlNodeClickFun"
          ></el-tree>
        </div>
        <div class="content-right-table">
          <avue-crud
            ref="userControlTable"
            :option="userControlTableOption"
            :data="userControlTableData"
            :page.sync="userControlTablePage"
            :table-loading="loading"
            :search.sync="searchData"
            @selection-change="userControlSelectionChangeFun"
            @current-change="userControlCurrentChangeFun"
            @size-change="userControlSizeChangeFun"
            @search-change="userControlSearchChangeFun"
            @search-reset="userControlSearchResetFun"
          ></avue-crud>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userDialog = false">
          {{
          disabled ? "关闭" : "取 消"
          }}
        </el-button>
        <el-button type="primary" @click="setUserInputValueFun" v-if="!disabled">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList } from '@/api/system/user'
export default {
  props: [
    'tableItemVal',
    'tableItemName',
    'disabled',
    'size',
    'tableItemScope',
    'setFormValueFun',
    'multiple',
    'allDepart',
    'allUserObj',
  ],

  data() {
    return {
      skip: false,
      loading: false,
      userDialog: false,
      searchData: {},
      userProps: {
        label: 'realName',
        value: 'id',
      },
      departProps: {
        children: 'children',
        label: 'title',
        value: 'id',
      },
      allUserData: [], //所有用户数据
      allDepartData: [], //所有部门数据
      userControlTableSelectId: [], //用户选择的数据id
      userControlTableOption: {
        rowKey: 'id',
        selection: true,
        reserveSelection: true,
        menu: false,
        addBtn: false,
        columnBtn: false,
        refreshBtn: false,
        searchMenuSpan: 8,
        column: [
          {
            prop: 'account',
            label: '用户账号',
            search: true,
            searchSpan: 8,
          },
          {
            prop: 'realName',
            label: '用户姓名',
            search: true,
            searchSpan: 8,
          },
          /*  {
            prop: 'sex',
            label: '性别',
            type: 'radio',
            dicData: [
              { label: '男', value: 1 },
              { label: '女', value: 0 },
            ],
          },
          {
            prop: 'phone',
            label: '手机',
          }, */
          {
            prop: 'deptName',
            label: '部门',
          },
        ],
      },
      userControlTableData: [], //当前表格页数据
      userControlTablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 5,
        pageSizes: [5, 10, 20, 30],
        background: true,
        layout: 'sizes, prev, pager, next, jumper,total',
        currentdepartId: '',
      },
    }
  },
  watch: {
    allDepart: {
      deep: true,
      immediate: true, //先执行一次handler
      handler: function (newV) {
        this.allDepartData = newV
      },
    },
    allUserObj: {
      deep: true,
      immediate: true, //先执行一次handler
      handler: function (newV) {
        if (newV) {
          this.allUserData = newV.allList
          this.userControlTablePage.total = newV.total
          this.userControlTableData = newV.list
        }
      },
    },
  },
  computed: {
    selectValue: {
      get() {
        if (this.tableItemVal && this.tableItemVal instanceof Array) {
          if (this.tableItemVal.length > 0) {
            return this.tableItemVal
          }
          return ''
        } else {
          if (this.tableItemVal) {
            return this.tableItemVal.split(',')
          }
          return ''
        }
      },
      set() {},
    },
  },

  async mounted() {
    this.userControlTableOption = {
      ...this.userControlTableOption,
      selectable: (row, index) => {
        if (this.tableItemScope.selectable) {
          return this.tableItemScope.selectable(row, index)
        } else if (
          this.tableItemScope.column &&
          this.tableItemScope.column.selectable
        ) {
          return this.tableItemScope.column.selectable(row, index)
        } else {
          return true
        }
      },
    }
    
    if (this.disabled) {
      this.userControlTableOption.tip = false
      this.userControlTableOption.selectable = () => {
        return false
      }
    }
  },

  methods: {
    //获取所有用户
    async getAllUserInfoFun(search = {}) {
      search = {
        ...this.searchData,
        ...search,
      }
      this.loading = true
      let { currentPage, pageSize, currentdepartId } = this.userControlTablePage
      let userRes = await getList(
        currentPage,
        pageSize,
        search,
        currentdepartId
      )
      let data = userRes.data.data
      this.userControlTablePage.total = data.total
      this.userControlTableData = data.records
      this.loading = false
    },
    //打开用户选择弹窗
    openUserDialogFun(value, fieldName, bool) {
      if (!bool) {
        return false
      }
      this.userDialog = true
      setTimeout(() => {
        this.$refs.userControlTable.toggleSelection('')
        let userCheckedArr = []
        this.allUserData.forEach((item) => {
          if (value != undefined && value.includes(item.id)) {
            userCheckedArr.push(item)
          }
        })
        this.$refs.userControlTable.toggleSelection(userCheckedArr)
      }, 0)
      /* this.setParentFormValFun({
        fieldName,
        value,
      }); */
      this.userControlTablePage.currentPage = 1
      this.userControlTablePage.pageSize = 5
      this.userControlTablePage.currentdepartId = ''
      this.userControlSearchResetFun()
    },
    //设置用户控件值
    setUserInputValueFun() {
      this.setParentFormValFun({
        fieldName: this.tableItemName,
        value: [],
      })
      this.setParentFormValFun({
        fieldName: `$${this.tableItemName}`,
        value: '',
      })
      setTimeout(() => {
        this.setParentFormValFun({
          fieldName: this.tableItemName,
          value: this.userControlTableSelectId,
        })
        let text = ''
        if (this.userControlTableSelectId) {
          this.allUserData.forEach((item) => {
            if (
              this.userControlTableSelectId.includes(item[this.userProps.value])
            ) {
              if (text) {
                text = `${text},${item[this.userProps.label]}`
              } else {
                text = item[this.userProps.label]
              }
            }
          })
        }
        console.log('设置用户', {
          fieldName: `$${this.tableItemName}`,
          value: text,
        })
        this.setParentFormValFun({
          fieldName: `$${this.tableItemName}`,
          value: text,
        })
        this.userDialog = false
      }, 0)
    },
    //用户控件表格选择
    userControlSelectionChangeFun(column) {
      if (!this.multiple) {
        //单选
        if (this.skip) {
          return false
        }
        this.skip = true
        this.$refs.userControlTable.toggleSelection('')
        let currRow = []
        if (column.length > 0) {
          currRow.push(column[column.length - 1])
        }
        this.$refs.userControlTable.toggleSelection(currRow)
        setTimeout(() => {
          if (currRow.length >= 1) {
            this.userControlTableSelectId = [currRow[0].id]
          } else {
            this.userControlTableSelectId = []
          }
          this.skip = false
        }, 0)
      } else {
        //多选
        let idArr = []
        column.forEach((item) => {
          idArr.push(item.id)
        })
        this.userControlTableSelectId = idArr
      }
    },
    //用户控件表格搜索
    userControlSearchChangeFun(params, done) {
      this.searchData = params
      this.getAllUserInfoFun()
      done()
    },
    //用户控件表格清空搜索
    userControlSearchResetFun() {
      this.getAllUserInfoFun()
    },
    //用户控件表格切换页
    userControlCurrentChangeFun(page) {
      this.userControlTablePage.currentPage = page
      this.getAllUserInfoFun()
    },
    //用户控件表格每页显示数
    userControlSizeChangeFun(pageSize) {
      this.userControlTablePage.pageSize = pageSize
      this.getAllUserInfoFun()
    },
    //用户控件 点击部门树触发
    userControlNodeClickFun(data) {
      
      this.userControlTablePage.currentPage = 1
      this.userControlTablePage.currentdepartId = data.id
      this.getAllUserInfoFun()
    },
    //调用父组件设置表单值方法{fieldName:'',value:''}
    setParentFormValFun(obj) {
      
      if (obj.value && obj.value instanceof Array) {
        obj.value = obj.value.join(',')
      }
      if (this.setFormValueFun) {
        this.setFormValueFun(obj)
      }
      this.$emit('set-form-val', obj)
    },
  },
}
</script>

<style lang="scss">
.user_dialog_box {
  .user_dialog_content {
    padding: 10px;
    display: flex;
    background-color: rgb(236, 236, 236);
    .content-left-tree {
      background-color: #fff;
      flex: 0 0 290px;
      box-sizing: border-box;
      padding: 24px;
      margin-right: 10px;
      border-radius: 5px;
    }
    .content-right-table {
      flex: 1;
      box-sizing: border-box;
      background-color: #fff;
      border-radius: 5px;
      padding: 24px;
      .avue-crud__menu {
        margin-bottom: 0px;
        display: none;
      }
    }
  }
}
.user_dialog_box {
  .el-dialog__header {
    border-bottom: 1px solid #f1f1f1;
  }
}
.user-control-box {
  display: flex;
  align-items: center;
  input::-webkit-input-placeholder {
    opacity: 1 !important;
  }
  input::-moz-placeholder {
    /* Mozilla Firefox 19+ */
    opacity: 1 !important;
  }
  input:-moz-placeholder {
    /* Mozilla Firefox 4 to 18 */
    opacity: 1 !important;
  }
  input:-ms-input-placeholder {
    /* Internet Explorer 10-11 */
    opacity: 1 !important;
  }
  input {
    border-radius: 4px;
    border-right: 1px solid #e4e7ed;
    cursor: pointer !important;
    background-color: #f5f7fa !important;
  }
  input {
    background-color: #fff !important;
    cursor: pointer !important;
    color: #606266 !important;
    padding-right: 15px !important;
  }
  .el-input__suffix {
    display: none;
  }
}
.user-control-box-yes {
  display: flex;
  align-items: center;
  .el-button {
    border-radius: 0px 3px 3px 0;
  }
  input {
    border-radius: 4px 0px 0px 4px;
    border-right: 0;
    cursor: text !important;
  }
  // &.user-control-border-show {
  //   input {
  //     border-radius: 4px;
  //     border-right: 1px solid #e4e7ed;
  //     cursor: pointer !important;
  //   }
  // }
  &.user-control-border-show {
    input::-webkit-input-placeholder {
      opacity: 0 !important;
    }
    input::-moz-placeholder {
      /* Mozilla Firefox 19+ */
      opacity: 0 !important;
    }
    input:-moz-placeholder {
      /* Mozilla Firefox 4 to 18 */
      opacity: 0 !important;
    }
    input:-ms-input-placeholder {
      /* Internet Explorer 10-11 */
      opacity: 0 !important;
    }
    input {
      border-radius: 4px;
      border-right: 1px solid #e4e7ed;
      cursor: pointer !important;
      background-color: #f5f7fa !important;
    }
  }
  &.user-control-border-view {
    input {
      border: none;
    }
  }
}
</style>
