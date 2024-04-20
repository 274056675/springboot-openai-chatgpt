<template>
  <div class="select-control">
    <div class="select-control-box">
      <avue-select
        ref="tableSelect"
        v-model="selectValue"
        :placeholder="'请选择 ' + selecText"
        type="tree"
        :props="tableProps"
        :multiple="true"
        :dic="allTableData"
        :size="tableItemScope ? tableItemScope.size : ''"
        :disabled="true"
        @click="openTableSelectDialogFun(tableItemVal, tableItemName, true)"
      ></avue-select>
    </div>
    <el-dialog
      :title="'请选择 ' + selecText"
      v-dialogdrag
      :visible.sync="selectDialog"
      v-if="selectDialog"
      class="user_dialog_box"
      :modal-append-to-body="true"
      :append-to-body="true"
      width="1200px"
      top="20px"
    >
      <div class="user_dialog_content" v-loading="isTableLoading">
        <div class="content-left-tree" v-if="isTree">
          <el-tree
            ref="userDepartTree"
            :props="treeProps"
            :check-strictly="true"
            node-key="value"
            :data="allTreeData"
            @node-click="treeNodeClickFun"
          ></el-tree>
        </div>
        <div class="content-right-table">
          <avue-crud
            ref="tableControl"
            :option="tableOption"
            :data="tableData"
            :page.sync="tablePage"
            :table-loading="loading"
            :search.sync="tableQueryData"
            @selection-change="selectionChangeFun"
            @current-change="currentChangeFun"
            @size-change="sizeChangeFun"
            @search-change="searchChangeFun"
            @search-reset="searchResetFun"
          ></avue-crud>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="selectDialog = false">
          {{ disabled ? "关闭" : "取 消" }}
        </el-button>
        <el-button type="primary" @click="setInputValueFun" v-if="!disabled"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { apiRequestHead } from "@/config/url.js";
import { setTreeDataUtil } from "@/research/util/myUtil";
import {
  getDataApi,
  getTreeDataApi,
  getActionApi,
  postActionApi,
} from "@/api/research/codelist";
export default {
  props: [
    "tableItemVal", //当前选择数据值
    "tableItemName", //当前控件字段名
    "disabled", //是否禁用
    "size", //控件大小
    "tableItemScope", //控件配置
    "setFormValueFun", //设置值的方法
    "multiple", //是否多选
    "selecText", //文本
    "configJson", //其他配置函数
    "isTree", //是否开启树筛选
    "treeDataUrl", //树数据请求路径
    "treeTableId", //树数据表单开发id
    "treeParams", //树数据请求参数
    "treeMethod", //树数据请求方法
    "treeFormatt", //树数据格式化
    "tableId", //表格数据 表单开发id
    "treeApiMode", //数据获取方式 table 表单开发  custom 自定义
  ],
  data() {
    return {
      getActionApi,
      postActionApi,
      apiRequestHead,
      outherObj: {
        searchObj: {}, //默认搜索值
      },
      tableQueryData: {}, //搜索参数
      isTableLoading: false,
      skip: false,
      loading: false,
      selectDialog: false,
      tableProps: {
        label: "label",
        value: "id",
      },
      treeProps: {
        children: "children",
        label: "label",
        value: "id",
        searchProp: "id",
      },
      allTableData: [],
      allTreeData: [],
      allTableSelectId: [], //用户选择的数据id
      tableOption: {
        rowKey: "id",
        selection: true,
        reserveSelection: true,
        menu: false,
        addBtn: false,
        columnBtn: false,
        refreshBtn: false,
        searchMenuSpan: 8,
        column: [],
      },
      tableData: [], //当前表格页数据
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        pageSizes: [10, 20, 30],
        background: true,
        layout: "sizes, prev, pager, next, jumper,total",
        currentTreeId: "",
      },
    };
  },
  watch: {},
  computed: {
    selectValue: {
      get() {
        if (this.tableItemVal && this.tableItemVal instanceof Array) {
          if (this.tableItemVal.length > 0) {
            return this.tableItemVal;
          }
          return "";
        } else {
          if (this.tableItemVal) {
            return this.tableItemVal.split(",");
          }
          return "";
        }
      },
      set() {},
    },
  },

  async mounted() {
    this.init();
    //禁用选择
    if (this.disabled) {
      this.tableOption.selectable = () => {
        return false;
      };
    }
  },

  methods: {
    //初始化项目
    async init() {
      let bool = true;
      if (this.tableItemScope) {
        let column = this.tableItemScope.column;
        if (column && column.dicData && column.dicData.length > 0) {
          this.allTableData = column.dicData;
          bool = false;
        }
      }
      this.isTableLoading = true;
      try {
        let getDataFun = `function getDataFun(that){${this.configJson}}`;
        getDataFun = this.getFunction(getDataFun);
        let data = getDataFun();
        for (let key in data) {
          if (key == "tablecolumn") {
            this.tableOption.column = data[key];
          } else {
            this[key] = data[key];
          }
        }
      } catch (error) {
        console.warn("其他配置格式异常");
      }
      this.allTreeData = await this.getTreeDataFun();
      
      if (bool) {
        this.allTableData = await this.getTableDataFun("all");
      }
      
      this.isTableLoading = false;
      await this.getTableDataFun();
    },
    //获取树表格数据
    getTreeDataFun() {
      return new Promise((resolve) => {
        if (!this.isTree) {
          resolve([]);
          return false;
        }
        if (this.treeTableId && this.treeApiMode == "table") {
          getTreeDataApi(this.treeTableId).then((res) => {
            try {
              let data = res.data.data.records;
              data = setTreeDataUtil(data, "pid");
              resolve(data);
            } catch (error) {
              resolve([]);
            }
          });
        } else if (this.treeDataUrl && this.treeApiMode == "custom") {
          let url = this.treeDataUrl;
          
          if (url.indexOf("/") == 0) {
            url = url.replace("/api/", this.apiRequestHead + "/");
          } else {
            url = url.replace("api/", "");
          }
          let params = {};
          try {
            params = {
              ...JSON.parse(this.treeParams),
            };
          } catch (error) {
            console.warn("表格选择控件，请求参数配置异常");
          }
          let apiType = "getActionApi";
          if (this.treeMethod == "post") {
            apiType = "postActionApi";
          }
          this[apiType](url, {
            ...params,
          }).then((res) => {
            resolve(this.dataFormatting(res, this.treeFormatt));
          });
        } else {
          resolve([]);
        }
      });
    },
    //获取表格数据
    getTableDataFun(type) {
      return new Promise((resolve) => {
        if (this.tableId) {
          let params = {
            pageNo: this.tablePage.currentPage,
            pageSize: type == "all" ? -521 : this.tablePage.pageSize,
          };
          if (type != "all") {
            params = {
              ...params,
              ...this.outherObj.searchObj,
              ...this.tableQueryData,
            };
            if (this.tablePage.currentTreeId) {
              params = {
                ...params,
                [this.treeProps.searchProp]: this.tablePage.currentTreeId,
              };
            }
          }
          this.loading = true;
          getDataApi(this.tableId, params).then((res) => {
            try {
              if (type != "all") {
                this.tableData = res.data.data.records;
                this.tablePage.total = res.data.data.total;
              }
              resolve(res.data.data.records);
              this.loading = false;
            } catch (error) {
              resolve([]);
              this.loading = false;
            }
          });
        } else {
          resolve([]);
        }
      });
    },
    //格式化数据
    dataFormatting(res, formatt) {
      if (!formatt) {
        return res;
      }
      formatt = formatt.split(".");
      formatt.forEach((item) => {
        res = res[item];
      });
      return res;
    },
    //解析函数
    getFunction(fun) {
      if (fun) {
        fun = fun.replace(/↵/g, "\n");
        fun = fun.replace(/\/\*{1,2}[\s\S]*?\*\//gis, "");
        // fun = fun.replace(/(?:^|\n|\r)\s*\/\*[\s\S]*?\*\/\s*(?:\r|\n|$)/g, '')
        fun = fun.replace(/(?:^|\n|\r)\s*\/\/.*(?:\r|\n|$)/g, "");
        try {
          if (eval(`(${fun})`)) {
            return eval(`(${fun})`);
          } else {
            return () => {};
          }
        } catch {
          console.warn("请检查其他配置编写是否有误~");
          return () => {};
        }
      }
    },
    //打开表格选择弹窗
    openTableSelectDialogFun(value, fieldName, bool) {
      if (!bool) {
        return false;
      }
      this.selectDialog = true;
      setTimeout(() => {
        this.$refs.tableControl.toggleSelection("");
        let selectCheckedArr = [];
        this.allTableData.forEach((item) => {
          if (value != undefined && value.includes(item.id)) {
            selectCheckedArr.push(item);
          }
        });
        this.$refs.tableControl.toggleSelection(selectCheckedArr);
      }, 0);
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = 10;
      this.tablePage.currentTreeId = "";
      this.getTableDataFun();
    },
    //设置选择控件值
    setInputValueFun() {
      this.setParentFormValFun({
        fieldName: this.tableItemName,
        value: this.allTableSelectId,
      });
      this.selectDialog = false;
    },
    //表格选择
    selectionChangeFun(column) {
      if (!this.multiple) {
        //单选
        if (this.skip) {
          return false;
        }
        this.skip = true;
        this.$refs.tableControl.toggleSelection("");
        let currRow = [];
        if (column.length > 0) {
          currRow.push(column[column.length - 1]);
        }
        this.$refs.tableControl.toggleSelection(currRow);
        setTimeout(() => {
          if (currRow.length >= 1) {
            this.allTableSelectId = [currRow[0].id];
          } else {
            this.allTableSelectId = [];
          }
          this.skip = false;
        }, 0);
      } else {
        //多选
        let idArr = [];
        column.forEach((item) => {
          idArr.push(item.id);
        });
        this.allTableSelectId = idArr;
      }
    },
    //用户控件表格搜索
    searchChangeFun(params, done) {
      this.tableQueryData = params;
      done();
    },
    //用户控件表格清空搜索
    searchResetFun() {
      this.tableQueryData = {};
      this.tablePage.currentPage = 1;
      this.getTableDataFun();
    },
    //表格切换页
    currentChangeFun(page) {
      this.tablePage.currentPage = page;
      this.getTableDataFun();
    },
    //表格每页显示数
    sizeChangeFun(pageSize) {
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = pageSize;
      this.getTableDataFun();
    },
    //点击部门树触发
    treeNodeClickFun(data) {
      
      this.tablePage.currentPage = 1;
      this.tablePage.currentTreeId = data[this.treeProps.value]
        ? data[this.treeProps.value]
        : "id";
      this.getTableDataFun();
    },
    //调用父组件设置表单值方法{fieldName:'',value:''}
    setParentFormValFun(obj) {
      
      if (obj.value && obj.value instanceof Array) {
        obj.value = obj.value.join(",");
      }
      this.setFormValueFun(obj);
    },
  },
};
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
.select-control-box {
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
.select-control-box-yes {
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
  // &.select-control-border-show {
  //   input {
  //     border-radius: 4px;
  //     border-right: 1px solid #e4e7ed;
  //     cursor: pointer !important;
  //   }
  // }
  &.select-control-border-show {
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
  &.select-control-border-view {
    input {
      border: none;
    }
  }
}
</style>
