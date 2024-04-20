<template>
  <div
    class="table-control"
    :class="[
      'table-control-box-' + tableColumn.prop,
      { 'table-control-menu-top-hide': !tableOption.isAddRowBtn && meunButtonList.length<=0 },
      { 'table-control-page': tableOption.isSelect },
      'table-control_' + random,
    ]"
    v-if="isInit"
  >
    <div class="table-control-avue-crud" v-if="!tableOption.isBigData">
      <avue-crud
        ref="crud"
        v-model="form"
        :option="tableOption"
        :data="tableData"
        :row-style="rowStyle"
        :page.sync="tablePage"
        :search.sync="searchData"
        @search-change="moreFunObj.searchChange"
        @selection-change="selectionChangeFun"
        @size-change="moreFunObj.sizeChange"
        @current-change="moreFunObj.currentChange"
      >
        <!-- 菜单自定义(表格上面的按钮栏) -->
        <template slot="menuLeft">
          <!-- 左边按钮插槽 -->
          <el-button
            size="small"
            type="primary"
            icon="el-icon-plus"
            v-if="tableOption.isAddRowBtn && formOpenType != 'view'"
            @click="rowCellAddFun"
          >新 增</el-button>
          <el-button
            v-for="(item,index) in meunButtonList"
            :key="index"
            size="small"
            :type="item.type?item.type:'primary'"
            :icon="item.icon"
            v-bind="item.params"
            @click="item.clickFun"
          >{{item.text}}</el-button>
          <el-button
            size="small"
            type="primary"
            icon="el-icon-delete"
            @click="deleteAllSelectData"
            v-show="tableSelectIndex.length"
          >批量删除</el-button>
        </template>
        <!-- 操作列按钮插槽 -->
        <template slot-scope="scope" slot="menu">
          <el-button
            v-for="(item,index) in linkButtonList"
            :key="index"
            size="small"
            :type="item.type?item.type:'text'"
            :icon="item.icon"
            v-bind="item.params"
            @click="item.clickFun(scope.row,scope.index)"
          >{{item.text}}</el-button>
        </template>
        <!-- 自定义评分 -->
        <template
          v-for="(rateItem, rateIndex) in rateOption"
          slot-scope="scope"
          :slot="rateItem.prop + 'Form'"
        >
          <div class="form-custom-rate" :class="rateItem.class" :key="rateIndex">
            <el-rate
              :size="scope.size"
              v-model="scope.row[rateItem.prop]"
              :allow-half="rateItem.allowHalf"
              :max="rateItem.max"
            ></el-rate>
          </div>
        </template>
        <!-- 自定义用户 -->
        <template
          v-for="(userItem, userIndex) in userOption"
          :slot="userItem.prop + 'Form'"
          slot-scope="scope"
        >
          <user-control
            :style="userItem.style"
            :class="userItem.class"
            :key="userIndex"
            :tableItemVal="scope.row[userItem.prop]"
            :tableItemName="userItem.prop"
            :disabled="scope.disabled"
            :tableItemScope="Object.assign(scope,{selectable:userItem.selectable})"
            exhibitionType="tableEdit"
            :multiple="userItem.params.multiple"
            @set-form-val="(obj) => setTableFormValue(obj, scope.row.$index)"
            :allDepart="allDepart"
            :allUserObj="allUserObj"
          ></user-control>
        </template>
        <!-- 自定义部门 -->
        <template
          v-for="(departItem, departIndex) in departOption"
          :slot="departItem.prop + 'Form'"
          slot-scope="scope"
        >
          <depart-control
            :style="departItem.style"
            :class="departItem.class"
            :key="departIndex"
            :tableItemVal="scope.row[departItem.prop]"
            :tableItemName="departItem.prop"
            :disabled="scope.disabled"
            :tableItemScope="scope"
            :multiple="departItem.params.multiple"
            @set-form-val="(obj) => setTableFormValue(obj, scope.row.$index)"
          ></depart-control>
        </template>
        <!-- 自定义图片控件 -->
        <template
          v-for="(imgItem, imgIndex) in imgOption"
          :slot="imgItem.prop + 'Form'"
          slot-scope="scope"
        >
          <div :key="imgIndex" class="code-sbulist-custom-image-box">
            <div
              class="box-btn"
              v-if="
                scope.row[imgItem.prop] == undefined ||
                scope.row[imgItem.prop].length <= 0
              "
            >
              <div v-if="scope.disabled">无图片</div>
              <el-upload
                v-else
                :action="imgItem.action"
                multiple
                :limit="imgItem.limit ? imgItem.limit : 0"
                :accept="imgItem.accept"
                :before-upload="(file) => customUploadFun(file, scope, imgItem, 'file')"
              >
                <el-button size="small" plain icon="el-icon-upload">上传图片</el-button>
              </el-upload>
            </div>
            <div
              class="box-content"
              v-else
              @click="opentDialogUploadeFun('image', imgItem.prop, scope.row, imgItem)"
            >
              <div class="content-img">
                <img :src="scope.row[imgItem.prop].split(',')[0]" alt />
              </div>
              <div
                class="content-num"
                v-if="scope.row[imgItem.prop].split(',').length > 1"
              >+{{ scope.row[imgItem.prop].split(",").length - 1 }}</div>
              <div class="content-icon">
                <i class="el-icon-setting"></i>
              </div>
            </div>
          </div>
        </template>
        <!-- 自定义文件控件 -->
        <template
          v-for="(fileItem, fileIndex) in fileOption"
          :slot="fileItem.prop + 'Form'"
          slot-scope="scope"
        >
          <div :key="fileIndex" class="code-sbulist-custom-file-box">
            <div
              class="box-btn"
              v-if="
                scope.row[fileItem.prop] == undefined ||
                scope.row[fileItem.prop].length <= 0
              "
            >
              <div v-if="scope.disabled">无文件</div>
              <el-upload
                v-else
                :action="fileItem.action"
                multiple
                :limit="fileItem.limit ? fileItem.limit : 0"
                :accept="fileItem.accept"
                :before-upload="(file) => customUploadFun(file, scope, fileItem, 'file')"
              >
                <el-button size="small" plain icon="el-icon-upload">上传文件</el-button>
              </el-upload>
            </div>
            <div
              class="box-content"
              v-else
              @click="opentDialogUploadeFun('file', fileItem.prop, scope.row, fileItem)"
            >
              <i class="el-icon-link"></i>
              <span class="content-txt">
                {{
                scope.row["$Name" + fileItem.prop]
                ? scope.row["$Name" + fileItem.prop][0]
                : scope.row[fileItem.prop]
                }}
              </span>
              <span
                class="content-num"
                v-if="scope.row[fileItem.prop].split(',').length > 1"
              >+{{ scope.row[fileItem.prop].split(",").length - 1 }}</span>
              <i class="el-icon-setting"></i>
            </div>
          </div>
        </template>
        <template
          v-for="(fileItem, fileIndex) in fileOption"
          :slot="fileItem.prop"
          slot-scope="scope"
        >
          <div :key="fileIndex" class="code-sbulist-custom-file-box">
            <div
              class="box-btn"
              v-if="
                scope.row[fileItem.prop] == undefined ||
                scope.row[fileItem.prop].length <= 0
              "
            >
              <div v-if="scope.disabled || formOpenType == 'view'">无文件</div>
              <el-upload
                v-else
                :action="fileItem.action"
                multiple
                :limit="fileItem.limit ? fileItem.limit : 0"
                :accept="fileItem.accept"
                :before-upload="(file) => customUploadFun(file, scope, fileItem, 'file')"
              >
                <el-button size="small" plain icon="el-icon-upload">上传文件</el-button>
              </el-upload>
            </div>
            <div
              class="box-content"
              v-else
              @click="opentDialogUploadeFun('file', fileItem.prop, scope.row, fileItem)"
            >
              <i class="el-icon-link"></i>
              <span class="content-txt">
                {{
                scope.row["$Name" + fileItem.prop]
                ? scope.row["$Name" + fileItem.prop][0]
                : scope.row[fileItem.prop]
                }}
              </span>
              <span
                class="content-num"
                v-if="scope.row[fileItem.prop].split(',').length > 1"
              >+{{ scope.row[fileItem.prop].split(",").length - 1 }}</span>
              <i class="el-icon-setting"></i>
            </div>
          </div>
        </template>
        <!-- 自定义省市区 -->
        <template
          v-for="(rovItem, rovIndex) in provincesOption"
          :slot="rovItem.prop + 'Form'"
          slot-scope="scope"
        >
          <avue-cascader
            :key="rovIndex"
            :class="[
              rovItem.class,
              'table-control-row-cascader__' + rovItem.prop + '__' + scope.row.$index,
            ]"
            v-model="scope.row[rovItem.prop]"
            lazy
            :lazy-load="lazyLoadFun"
            :props="rovItem.props"
            :style="rovItem.style"
          ></avue-cascader>
        </template>
      </avue-crud>
    </div>
    <div class="table-control-avue-crud-big-data" v-else>
      <avue-crud
        :key="reload"
        ref="crud"
        v-model="form"
        :option="tableOption"
        :data="filteredData"
        v-loadmore="handelLoadmore"
        :data-size="tableData.length"
        :row-style="rowStyle"
        @selection-change="selectionChangeFun"
      >
        <!-- 菜单自定义(表格上面的按钮栏) -->
        <template slot="menuLeft">
          <!-- 左边按钮插槽 -->
          <el-button
            size="small"
            type="primary"
            icon="el-icon-plus"
            v-if="tableOption.isAddRowBtn && formOpenType != 'view'"
            @click="rowCellAddFun"
          >新 增</el-button>
          <el-button
            size="small"
            type="primary"
            icon="el-icon-delete"
            @click="deleteAllSelectData"
            v-show="tableSelectIndex.length"
          >批量删除</el-button>
        </template>
        <!-- 自定义限制文本长度 -->
        <template
          v-for="(item, index) in viewCustomEllipsisArr"
          :slot="item.fieldName"
          slot-scope="scope"
        >
          <avue-text-ellipsis
            :key="index"
            :text="scope.row[item.fieldName]"
            :height="40"
            :width="item.lengths"
            use-tooltip
            placement="top"
          >
            <small slot="more">...</small>
          </avue-text-ellipsis>
        </template>
      </avue-crud>
    </div>
    <!-- 文件上传 -->
    <el-dialog
      v-dialogdrag
      :title="dialogTitle"
      :visible.sync="isDialog"
      class="sbulist-table-dialog-box"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="dialogBeforeClose"
      width="530px"
    >
      <avue-form
        v-model="dialogFormData"
        :option="dialogFormOption"
        :upload-after="uploadAfter"
        :upload-exceed="uploadExceedFun"
      >
        <template
          v-if="dialogFormOption.column[0].accept != 'image/*'"
          :slot="dialogFormOption.column[0].prop + 'Type'"
          slot-scope="scope"
        >
          <div @click="downloadFile(scope.file.url, scope.file.name)" style="cursor: pointer">
            <i class="el-icon-link"></i>
            <span style="flex: 1">
              {{
              dialogFormData["$Name" + dialogFormOption.column[0].prop]
              ? dialogFormData["$Name" + dialogFormOption.column[0].prop][
              scope.file.uid
              ]
              : dialogFormData[dialogFormOption.column[0].prop]
              }}
            </span>
            <i
              class="el-icon-close"
              v-if="!scope.disabled"
              @click.capture.stop="
                codeFileControlDelFun(dialogFormOption.column[0].prop, scope)
              "
            ></i>
          </div>
        </template>
      </avue-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isDialog = false">取 消</el-button>
        <el-button type="primary" @click="saveDialogUploadeDataFun">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 用户选择 -->
    <el-dialog
      :title="userControlData.title"
      v-dialogdrag
      :visible.sync="userControlData.isShow"
      v-if="isUserControl"
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
            :props="userControlData.departProps"
            :check-strictly="true"
            node-key="value"
            :data="userControlData.deptData"
            @node-click="userControlData.treeNodeClickFun"
          ></el-tree>
        </div>
        <div class="content-right-table">
          <avue-crud
            ref="userControlTable"
            :option="userControlData.tableOption"
            :data="userControlData.userData"
            :page.sync="userControlData.pageData"
            :search.sync="userControlData.searchData"
            :table-loading="userControlData.loading"
            @selection-change="userControlData.selectionChangeFun"
            @current-change="userControlData.currentChangeFun"
            @size-change="userControlData.sizeChangeFun"
            @search-change="userControlData.searchChangeFun"
            @search-reset="userControlData.searchResetFun"
          ></avue-crud>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userControlData.isShow = false">取 消</el-button>
        <el-button type="primary" @click="userControlData.submitUserDataFun">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 其他表单 -->
    <form-view
      ref="formView"
      v-if="isFormControl"
      :formViewControlFun="formViewSubmitFun"
      :formOptionData="formControlData"
    ></form-view>
  </div>
</template>

<script>
import { getStrDataFunction } from '@/research/util/myUtil.js'
import {
  getDicTableData,
  uploadeFileApi,
  getUploadeFileNameApi,
} from '@/api/research/codelist'
import form from '@/research/mixins/form'
import { getDeptTree } from '@/api/system/dept'
import { getList } from '@/api/system/user'

import { apiRequestHead } from '@/config/url.js'
import UserControl from '@/research/components/general-control/user-control'
import DepartControl from '@/research/components/general-control/depart-control'
import FormView from '@/research/components/general-control/form-view.vue'
import Vue from 'vue';
export default {
  props: [
    'tableColumn',
    'tableValue',
    'formOpenType',
    'allExecuteRule',
    'getCurrPacDataTextFun',
    'lazyLoadFun',
    'allFormListData',
    'allDepart',
    'allUserObj',
  ],
  components: {
    UserControl,
    DepartControl,
    FormView,
  },
  computed: {
    filteredData() {
      let list = this.tableData.filter((item, index) => {
        if (index < this.currentStartIndex) {
          return false
        } else if (index > this.currentEndIndex) {
          return false
        } else {
          return true
        }
      })
      return list
    },
  },
  filters: {
    fileNameFilters(value) {
      let fileName = value.split('/')
      fileName = fileName[fileName.length - 1].split('-').slice(1).join('-')
      return fileName
    },
  },
  mixins: [form],
  watch: {
    isUserControl(newVal) {
      
      if (newVal) {
        this.initUserControlDataFun()
      }
    },
  },
  data() {
    return {
      isInit: false,
      reload: Math.random(),
      random: `${new Date().getTime()}${Math.floor(Math.random() * 10000)}`,
      apiRequestHead: '',
      valueToload: false,
      optinsToLoad: false,
      form: {},
      allTableData: [],
      allArrTableData: [],
      searchAllTableData: [],
      searchAllArrTableData: [],
      tableData: [],
      tableDataItemDefault: {},
      tableOption: {
        align: 'left',
        addBtn: false,
        columnBtn: false,
        refreshBtn: false,
        addRowBtn: false,
        menu: false,
        cellBtn: true,
        saveBtn: false,
        cancelBtn: false,
        index: true, //开启序号
        selection: true, //开启选择框
        reserveSelection: true, //保留之前的勾选
        tip: false,
        column: [],
        selectable: (row, index) => {
          return true
        },
      },
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        pageSizes: [10, 20, 30],
        background: true,
        layout: 'sizes, prev, pager, next, jumper,total',
      },
      searchData: {},
      tableProp: '',
      tableSelectData: [],
      tableSelectIndex: [],
      meunButtonList: [],
      linkButtonList: [],
      rateOption: [], //评分
      userOption: [], //用户
      departOption: [], //部门
      imgOption: [], //图片
      fileOption: [], //文件
      selectRemoteAll: [],
      selectDicAll: [],
      provincesOption: [], //省市区
      viewCustomEllipsisArr: [],
      initSelfDefinedArr:[],//已经注册的自定义组件

      //弹窗
      isDialog: false,
      dialogTitle: '上传图片',
      dialogFormOption: {
        submitBtn: false,
        emptyBtn: false,
        column: [{}],
      },
      dialogFormData: {},

      // 大数据显示
      currentStartIndex: 0,
      currentEndIndex: 12,
      fieldWidth: [],
      //其他方法
      moreFunObj: {
        sizeChange: () => {},
        currentChange: () => {},
        searchChange: () => {},
      },

      // 用户选择
      isUserControl: false,
      userControlData: {
        isShow: false,
        multiple: true,
        skip: false,
        loading: false,
        title: '选择用户',
        deptData: [],
        departProps: {
          children: 'children',
          label: 'title',
          value: 'id',
        },
        userData: [],
        userProps: {
          label: 'realName',
          value: 'id',
        },
        searchData: {},
        tableOption: {
          rowKey: 'id',
          selection: true,
          reserveSelection: true,
          menu: false,
          addBtn: false,
          columnBtn: false,
          refreshBtn: false,
          searchMenuSpan: 8,
          selectable: () => {
            return true
          },
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
            {
              prop: 'deptName',
              label: '部门',
            },
          ],
        },
        pageData: {
          total: 0,
          currentPage: 1,
          pageSize: 5,
          pageSizes: [5, 10, 20, 30],
          background: true,
          layout: 'sizes, prev, pager, next, jumper,total',
        },
      },
      // 其他表单
      isFormControl: false,
      formControlData: {},
    }
  },

  mounted() {
    this.apiRequestHead = apiRequestHead
    this.setTableOptionFun()
    this.optinsToLoad = true
    this.setRemoteDataDicFun()
    this.getApiDataFun()
    if (
      ['edit', 'view', 'noButton', 'add_router'].includes(this.formOpenType)
    ) {
      this.setCurrentTableData()
    }
    setTimeout(() => {
      this.setCustomText()
      this.getFileNameFun()
      if (this.tableColumn.assigJsEnhance) {
        try {
          let parentThat = this.tableColumn.getParentFun()
          this.tableColumn.assigJsEnhance(this, parentThat)
        } catch (error) {
          console.warn(
            `子表《${this.tableColumn.prop}》赋值后js增强执行错误:${error}`
          )
        }
      }
    }, 300)
  },
  methods: {
    rowStyle() {},
    //设置当前表格数据
    setCurrentTableData() {
      let tableDataArr = []
      if (this.allFormListData && this.allFormListData[this.tableColumn.prop]) {
        tableDataArr = this.allFormListData[this.tableColumn.prop]
      }
      if (tableDataArr && tableDataArr.length > 0) {
        this.tableData = tableDataArr.map((item) => {
          if (this.formOpenType == 'edit' && !this.tableOption.isSelect) {
            item.$cellEdit = true
          }
          return item
        })
      }
    },
    //初始化树控件/联集文本
    setCustomText() {
      if (this.provincesOption && this.provincesOption.length > 0) {
        this.provincesOption.forEach((item) => {
          this.tableData.forEach((dataItem, index) => {
            this.setProvincesTextFun(dataItem[item.prop], item.prop, index)
          })
        })
      }
    },
    //初始化文件名
    getFileNameFun() {
      let fileArr = []
      if (this.fileOption.length > 0) {
        this.fileOption.forEach((item) => {
          fileArr.push(item.prop)
        })
      }
      this.tableData.forEach((item, index) => {
        //处理文件名
        if (fileArr.length > 0) {
          fileArr.forEach((fileItem) => {
            

            if (item[fileItem] != '' && item[fileItem] != undefined) {
              this.tableData[index]['$Name' + fileItem] = []
              item[fileItem].split(',').forEach(async (resItem) => {
                let fileRes = await getUploadeFileNameApi(resItem)
                let fileName = resItem.split('/')
                fileName = fileName[fileName.length - 1]
                if (fileRes.data.success && fileRes.data.data) {
                  fileName = fileRes.data.data
                }
                
                this.tableData[index]['$Name' + fileItem] = [
                  ...this.tableData[index]['$Name' + fileItem],
                  fileName,
                ]
              })
            }
          })
        }
      })
    },
    //修改省市区文本方法
    setProvincesTextFun(value, propName, index) {
      let text = this.getCurrPacDataTextFun(value)
      let dom = document.querySelector(
        `.table-control-row-cascader__${propName}__${index} input`
      )
      if (dom) {
        dom.value = text ? text : ''
      }
    },
    //清空所有数据
    clearAllDataFun() {
      this.tableData = []
    },
    //新增数据
    rowCellAddFun() {
      this.$refs.crud.rowCellAdd()
      setTimeout(() => {
        this.tableData = this.tableData.map((item, index) => {
          if (index == this.tableData.length - 1) {
            item = {
              ...item,
              ...this.tableDataItemDefault,
            }
          }
          return item
        })
        if (this.provincesOption && this.provincesOption.length > 0) {
          this.provincesOption.forEach((item) => {
            let index = this.tableData.length - 1
            this.setProvincesTextFun(
              this.tableData[index][item.prop],
              item.prop,
              index
            )
          })
        }
      }, 0)
    },
    //处理表格配置数据
    setTableOptionFun() {
      this.tableProp = this.tableColumn.prop
      this.tableColumn.children.column = this.tableColumn.children.column.map(
        (item) => {
          if (!['view', 'noButton'].includes(this.formOpenType)) {
            item.cell = true
          }
          item.minWidth = this.tableColumn.minWidth
          return item
        }
      )
      if (this.tableColumn.children.height) {
        delete this.tableColumn.children.height
      }
      this.tableOption = {
        ...this.tableOption,
        ...this.tableColumn.children,
      }
      //选择模式
      if (this.tableOption.isSelect) {
        this.tableOption = {
          ...this.tableOption,
          selection: true,
          reserveSelection: true,
          tip: true,
          searchMenuSpan: 4,
          emptyBtn: false,
          searchBtnText: '过滤',
        }
      }
      if (['view', 'noButton'].includes(this.formOpenType)) {
        this.tableOption.selection = false
      }
      if (
        this.tableColumn.defaultDataNum > 0 &&
        ['add', 'add_no'].includes(this.formOpenType)
      ) {
        for (let index = 0; index < this.tableColumn.defaultDataNum; index++) {
          this.tableData.push({
            $cellEdit: true,
          })
        }
      }
      if (this.isBigData && !this.tableOption.maxHeight) {
        this.tableOption.maxHeight = 410
      }
      this.tableOption.column = this.tableOption.column.map((item) => {
        this.form[item.prop] = item.value
        if (this.tableColumn.isWork) {
          item.placeholder = ' '
        }
        //是否隐藏列
        if (!item.display) {
          item.hide = true
        }
        //清除长度限制
        if (
          (item.isMaxLength !== undefined && item.isMaxLength === false) ||
          (item.isMaxLength !== true && item.maxlength === 0)
        ) {
          delete item.maxlength
        }
        // 设置最小宽度
        if (item.style.width) {
          try {
            item.width = item.style.width.split('px')[0] - 0 + 20
          } catch (error) {
            console.warn('设置最小宽度失败', error)
          }
        }
        //评分
        if (item.type == 'rate') {
          this.rateOption.push(item)
        }
        //用户
        if (item.type == 'user') {
          
          item.dicData = []
          // item.dataType="string"
          item.type = 'select'
          if (item.params.multiple) {
            item.multiple = true
          }
          item.props = {
            label: 'name',
            value: 'id',
          }
          this.userOption.push(item)
        }
        //部门
        if (item.type == 'depart') {
          this.departOption.push(item)
        }
        //自定义控件
      if(item.type=='self-defined'){
        if(typeof item.params =='string'){
          item.params=getStrDataFunction(item.params)
        }
        if(!this.initSelfDefinedArr.includes(item.component)){
          try {
            Vue.component(item.component, res => require([`@/${item.componentPath}`], res))
            this.initSelfDefinedArr.push(item.component)
          } catch (error) {
            console.warn(`${item.component}自定义组件注册异常,${error}`);
          }
        }
      }
        //图片
        if (item.uploadType == 'img') {
          this.imgOption.push(item)
        }
        //文件
        if (item.uploadType == 'file') {
          
          this.fileOption.push(item)
        }
        //省市区联动
        if (item.type == 'provinces') {
          item.type = 'cascader'
          item.lazyLoad = (node, resolve) =>
            this.lazyLoadFun(node, resolve, item.provType)
          this.provincesOption.push(item)
        }
        //判断时间/日期选择器是否开启范围选择
        if (item.type == 'date' && item.isRange) {
          item.type = 'daterange'
          item.dataType = 'string'
        }
        if (item.type == 'time' && item.isRange) {
          item.type = 'timerange'
          item.dataType = 'string'
        }
        //对宽度进行拼接
        if (item.style && item.style.width) {
          item.style.width = item.style.width + ' !important'
        }
        //需要把数组处理成字符串的数据
        if (item.type == 'select' && item.multiple) {
          item.dataType = 'string'
        }
        if (
          ['checkbox', 'user', 'depart', 'upload', 'provinces'].includes(
            item.type
          )
        ) {
          item.dataType = 'string'
        }
        if (item.type == 'upload') {
          item.action = item.action.replace(
            'apiRequestHead',
            this.apiRequestHead
          )
        }
        //提取需要远端数据的选择字段
        if (['select', 'checkbox', 'radio'].includes(item.type)) {
          if (item.oldDicOption == 'remote') {
            item.dicData = []
            this.selectRemoteAll.push(item.prop)
          }
          if (item.oldDicOption == 'dic') {
            this.selectDicAll.push(item.prop)
          }
        }
        if (this.tableOption.isBigData) {
          this.viewCustomEllipsisArr.push({
            fieldName: item.prop,
            lengths: this.tableColumn.minWidth - 20,
          })
          item.slot = true
          // item.width = this.tableColumn.minWidth
        }
        item = {
          ...item,
          change: () => {},
          click: () => {},
          focus: () => {},
          blur: () => {},
          enter: () => {},
          control: () => {
            return {}
          },
        }
        return item
      })
      for (let key in this.form) {
        if (this.form[key] === undefined) {
          this.form[key] = ''
        }
      }
      this.tableDataItemDefault = this.deepClone(this.form)
      //获取table字段宽度
      if (this.tableOption.isBigData) {
        setTimeout(() => {
          this.setBigTableColWidth()
        }, 1000)
      }
      if (this.tableColumn.jsEnhanceFun) {
        try {
          let parentThat = this.tableColumn.getParentFun()
          this.tableColumn.jsEnhanceFun(this, parentThat)
        } catch (error) {
          console.warn(
            `子表《${this.tableColumn.prop}》初始化之前js增强执行错误:${error}`
          )
        }
      }
      
      // 对所有用户控件添加字典
      if (this.userOption.length > 0) {
        let timer = setInterval(() => {
          if (this.allUserObj.allList && this.allUserObj.allList.length > 0) {
            this.userOption.forEach((item) => {
              
              let column = this.findObject(this.tableOption.column, item.prop)
              if (column != -1) {
                column.dicData = this.allUserObj.allList
              }
            })
            
            clearInterval(timer)
          }
        }, 1000)
      }

      //表格初始化配置完毕
      this.isInit = true
    },
    //大数据隐藏字段显示处理
    setBigTableColWidth() {
      let col = document.querySelectorAll(
        `.table-control_${this.random} .el-table__header-wrapper colgroup col`
      )
      let filterNum = 0
      if (this.tableOption.selection) {
        filterNum++
      }
      if (this.tableOption.index) {
        filterNum++
      }
      col.forEach((item, index) => {
        if (filterNum <= index && index < col.length - 2) {
          this.fieldWidth.push(item.getAttribute('width') - 20)
        }
      })
      this.fieldWidth.forEach((item, index) => {
        this.viewCustomEllipsisArr[index].lengths = item
      })
      
    },
    //远程取值方法
    async getApiDataFun() {
      let apiColumn = []
      if (this.tableOption.column) {
        apiColumn = [...apiColumn, ...this.tableOption.column]
      }
      let formData = await this.mixinGetApiData(apiColumn)
      for (let key in formData.formObj) {
        if (formData.formObj[key] instanceof Array) {
          formData.formObj[key] = formData.formObj[key].join(',')
        }
      }
      this.tableDataItemDefault = {
        ...this.tableDataItemDefault,
        ...formData.formObj,
      }
      //预留处理特殊情况
      // for (let key in formData.specialObj) {
      // }
      //修改表格默认值
      if (['add', 'add_no'].includes(this.formOpenType)) {
        this.tableData = this.tableData.map((item) => {
          item = {
            ...item,
            ...this.tableDataItemDefault,
          }
          return item
        })
      }
      //
      this.valueToload = true
    },
    //选择字段远端数据和数据字典处理逻辑
    setRemoteDataDicFun() {
      //远端数据
      if (this.selectRemoteAll.length > 0) {
        this.selectRemoteAll.forEach(async (item) => {
          let column = this.findObject(this.tableOption.column, item)
          if (column.dicUrl) {
            let dicData = await this.mixinGetSelectRemoteData(
              column.dicUrl,
              column.dicDataFormat
            )
            if (column.excludeStr) {
              let excludeArr = column.excludeStr.split(',')
              dicData = dicData.filter((item) => {
                if (excludeArr.includes(item.value)) {
                  return false
                }
                return true
              })
            }
            column.dicData = dicData
            if (column.isOneDefaultValue && dicData.length > 0) {
              column.value = dicData[0].id
            }
          }
        })
      }
      //字典处理
      if (this.selectDicAll.length > 0) {
        this.selectDicAll.forEach(async (item) => {
          let column = this.findObject(this.tableOption.column, item)
          if (column.queryFormName) {
            let dicRes = await getDicTableData(column.queryFormName)
            if (dicRes.data.success) {
              if (column.excludeStr) {
                let excludeArr = column.excludeStr.split(',')
                dicRes.data.data = dicRes.data.data.filter((item) => {
                  if (excludeArr.includes(item.value)) {
                    return false
                  }
                  return true
                })
              }
              column.dicData = dicRes.data.data
            } else {
              column.dicData = []
            }
          }
        })
      }
    },
    //js增强设置表单值
    setJsFormDataFun({ fieldName, value }) {
      setTimeout(() => {
        if (fieldName == this.tableProp) {
          if (value instanceof Array) {
            value = value.map((item) => {
              item.$cellEdit = true
              return item
            })
            this.tableData = value
          }
        } else {
          if (value instanceof Array) {
            value = value.join(',')
          }
          let tableKey = this.tableOption.column.map((item) => item.prop)
          if (tableKey.includes(fieldName)) {
            this.tableData = this.tableData.map((item) => {
              item[fieldName] = value
              return item
            })
          }
        }
      }, 0)
    },
    //js增强设置控件配置
    setFormOptionsFun(key, optionsKey, optionsValue) {
      this.$nextTick(() => {
        let column = ''
        if (this.tableOption.column) {
          column = this.findObject(this.tableOption.column, key)
        }
        if (column && column != -1) {
          column[optionsKey] = optionsValue
          this.$refs.crud.init()
        }
      })
    },
    //设置字段多个配置
    setFormMoreOptionsFun(key, options) {
      let column = ''
      if (this.tableOption.column) {
        column = this.findObject(this.tableOption.column, key)
      }
      if (column && column != -1) {
        for (let key in options) {
          column[key] = options[key]
        }
      }
      
    },
    //js增强设置控件显示/隐藏
    setFormControlStateFun(key, value) {
      this.$nextTick(() => {
        key.forEach((keyItem) => {
          let column = ''
          if (this.tableOption.column) {
            column = this.findObject(this.tableOption.column, keyItem)
          }
          if (column && column != -1) {
            column.hide = !value
            this.$refs.crud.columnInit()
          }
        })
      })
    },

    //选择
    selectionChangeFun(column) {
      // column 所有选择数据的数组
      this.tableSelectData = column
      let indexArr = []
      column.forEach((item) => {
        indexArr.push(item.$index)
      })
      this.tableSelectIndex = indexArr
    },
    //批量删除
    deleteAllSelectData() {
      if (this.tableSelectIndex.length <= 0) {
        this.$message({
          message: '请先选择需要删除的数据~',
          type: 'warning',
        })
        return false
      }
      this.tableData = this.tableData.filter((item) => {
        if (this.tableSelectIndex.includes(item.$index)) {
          return false
        } else {
          return true
        }
      })
      this.$refs.crud.toggleSelection('')
    },
    //获取并校验表格数据方法
    getTableData() {
      return new Promise((resolve) => {
        if (this.tableData.length <= 0) {
          resolve({
            res: true,
            prop: this.tableProp,
            data: [],
          })
          return false
        }
        let resObj = {}

        this.$refs.crud.validateCellForm().then((res) => {
          let resJson = JSON.stringify(res)
          if (resJson == '{}' || resJson === undefined) {
            //校验成功
            resObj.res = true
          } else {
            //校验失败
            resObj.res = false
          }
          let allData = this.deepClone(this.tableData)
          allData = allData.map((item) => {
            let formattingFormData = {}
            for (let key in item) {
              if (item[key] instanceof Array) {
                formattingFormData[key] = item[key].join(',')
              } else {
                formattingFormData[key] = item[key]
              }
            }
            return formattingFormData
          })
          resObj = {
            ...resObj,
            prop: this.tableProp,
            data: allData,
          }

          resolve(resObj)
        })
      })
    },
    //设置填值规则的值
    setFormExecuteRuleFun(rule) {
      let column = [...this.tableOption.column]
      this.tableData = this.tableData.map((item) => {
        let formData = {}
        column.forEach((columnItem) => {
          if (columnItem.fillRuleCode) {
            formData[columnItem.prop] = rule[columnItem.fillRuleCode]
          }
        })
        item = {
          ...item,
          ...formData,
        }
        return item
      })
    },

    //设置表格弹窗表单值
    setTableFormValue(obj, index) {
      this.tableData[index][obj.fieldName] = obj.value
    },
    //关闭弹窗前 重置表单数据
    dialogBeforeClose(done) {
      this.dialogFormData[this.currentDialogField.fieldName] = []
      done()
    },
    //保存弹窗上传的文件或图片方法
    saveDialogUploadeDataFun() {
      let fileArr = this.deepClone(
        this.dialogFormData[this.currentDialogField.fieldName]
      )
      this.tableData[this.currentDialogField.index][
        this.currentDialogField.fieldName
      ] = fileArr
      this.isDialog = false
    },
    //打开图片或文件弹窗
    opentDialogUploadeFun(type, fieldName, row, columnItem) {
      this.dialogFormOption.column = []
      this.dialogFormData = this.deepClone(row)
      this.currentDialogField = {
        fieldName,
        index: row.$index,
      }
      if (this.formOpenType == 'view') {
        columnItem.disabled = true
      }
      if (type == 'image') {
        this.dialogTitle = '上传图片'
        this.isDialog = true
        // this.dialogFormOption.column.push({
        //   accept: 'image/*',
        //   action: 'api/mjkj-water/cgform-api/upload/file',
        //   dataType: 'string',
        //   label: '',
        //   listType: 'picture-card',
        //   order: 1,
        //   prop: fieldName,
        //   propsHttp: {
        //     res: 'data',
        //     url: 'link',
        //     name: 'originalName',
        //   },
        //   data: {
        //     type: 0,
        //   },
        //   span: 24,
        //   type: 'upload',
        //   value: '',
        //   labelWidth: 0,
        //   disabled: this.disabled,
        // })
      }
      if (type == 'file') {
        this.dialogTitle = '上传文件'
        this.isDialog = true
        // this.dialogFormOption.column.push({
        //   action: 'api/alioss/uploadFiles',
        //   dataType: 'array',
        //   label: '',
        //   order: 1,
        //   prop: fieldName,
        //   propsHttp: {
        //     name: 'name',
        //     res: 'result.data',
        //     url: 'lj',
        //   },
        //   span: 24,
        //   type: 'upload',
        //   value: '',
        //   labelWidth: 0,
        //   disabled: this.disabled,
        // })
      }
      this.dialogFormOption.column.push(columnItem)
    },
    //图片上传成功
    customImgUploadSuccessFun(response, scope, fieldName) {
      this.tableData[scope.row.$index][fieldName] = [response.result.data.lj]
    },
    //监听文件上传
    uploadAfter(res, done, loading, column) {
      if (column.accept == '*/*') {
        if (this.dialogFormData['$Name' + column.prop] instanceof Array) {
          this.dialogFormData['$Name' + column.prop].push(res.originalName)
        } else {
          this.dialogFormData['$Name' + column.prop] = [res.originalName]
        }
      }
      done()
    },

    codeFileControlDelFun(fileName, obj) {
      let arr = []
      if (this.dialogFormData[fileName] instanceof Array) {
        arr = this.dialogFormData[fileName]
      } else {
        arr = this.dialogFormData[fileName].split(',')
      }

      let fileStr = arr.filter((item, index) => {
        if (item == obj.file.url) {
          this.dialogFormData['$Name' + fileName] = this.dialogFormData[
            '$Name' + fileName
          ].filter((item, i) => index != i)
          return false
        }
        return true
      })
      fileStr.join(',')
      this.dialogFormData[fileName] = fileStr.join(',')
    },
    //下载文件
    downloadFile(url, name) {
      var aEle = document.createElement('a') // 创建a标签
      aEle.download = name // 设置下载文件的文件名
      aEle.href = url // content为后台返回的下载地址
      aEle.click() // 设置点击事件
    },
    //文件、图片上传超过限制上传数 提示
    uploadExceedFun(limit, files, fileList, column) {
      this.$message({
        showClose: true,
        message: `<${column.label}>只允许上传${limit}个文件`,
        type: 'warning',
      })
    },
    //上传文件 图片
    customUploadFun(file, scope, item, type) {
      this.$message('正在上传....')
      let formdata = new FormData()
      formdata.append('file', file)
      if (type == 'file') {
        formdata.append('type', 1)
      } else {
        formdata.append('type', 0)
      }
      uploadeFileApi(formdata)
        .then((res) => {
          let url = res.data.data.link
          let name = res.data.data.originalName
          this.tableData = this.tableData.map((tableItem, index) => {
            if (index == scope.row.$index) {
              tableItem[item.prop] = url
              if (type == 'file') {
                tableItem['$Name' + item.prop] = [name]
              }
            }
            return tableItem
          })
          /* this.tableData[scope.row.$index][item.prop] = url
          if (type == 'file') {
            this.tableData[scope.row.$index]['$Name' + item.prop] = [name]
          } */
          this.$message({
            message: '上传成功',
            type: 'success',
          })
        })
        .catch(() => {
          this.$message.error(
            `上传${type == 'file' ? '文件' : '图片'}失败，请重新上传~`
          )
        })
      return false
    },
    handelLoadmore(currentStartIndex, currentEndIndex) {
      this.currentStartIndex = currentStartIndex
      this.currentEndIndex = currentEndIndex
    },
    //处理手动分页数据
    setTableDataPageDataFun(num) {
      this.allArrTableData = []
      let currArr = []
      this.allTableData.forEach((item, index) => {
        let i = index + 1
        currArr.push(item)
        if (i % num == 0 || i == this.allTableData.length) {
          this.allArrTableData.push(currArr)
          currArr = []
        }
      })
    },
    setSearchTableDataPageDataFun(num) {
      this.searchAllArrTableData = []
      let currArr = []
      this.searchAllTableData.forEach((item, index) => {
        let i = index + 1
        currArr.push(item)
        if (i % num == 0 || i == this.searchAllTableData.length) {
          this.searchAllArrTableData.push(currArr)
          currArr = []
        }
      })
    },

    //初始化用户控件相关数据
    initUserControlDataFun() {
      this.userControlData.tableOption.selectable = (row, index) => {
        if (this.userControlData.selectable) {
          return this.userControlData.selectable(row, index)
        } else {
          return true
        }
      }
      this.userControlData.getDeptFun = () => {
        getDeptTree().then((deptRes) => {
          this.userControlData.deptData = deptRes.data.data
        })
      }
      this.userControlData.getDeptFun()
      this.userControlData.getUserFun = (search = {}) => {
        this.userControlData.loading = true
        let { pageSize, currentPage, currentdepartId } =
          this.userControlData.pageData
        getList(
          currentPage,
          pageSize,
          Object.assign(this.userControlData.searchData, search),
          currentdepartId
        ).then((userRes) => {
          let userData = userRes.data.data
          this.userControlData.userData = userData.records
          this.userControlData.pageData.total = userData.total
          this.userControlData.loading = false
        })
      }
      this.userControlData.getUserFun()
      this.userControlData.treeNodeClickFun = (data) => {
        this.userControlData.pageData.currentPage = 1
        this.userControlData.pageData.currentdepartId = data.id
        this.userControlData.getUserFun()
      }
      this.userControlData.selectionChangeFun = (column) => {
        if (!this.userControlData.multiple) {
          if (this.userControlData.skip) {
            return false
          }
          this.userControlData.skip = true
          this.$refs.userControlTable.toggleSelection('')
          let currRow = []
          if (column.length > 0) {
            currRow.push(column[column.length - 1])
          }
          this.$refs.userControlTable.toggleSelection(currRow)
          setTimeout(() => {
            if (currRow.length >= 1) {
              this.userControlData.selectData = [currRow[0]]
            } else {
              this.userControlData.selectData = []
            }
            this.userControlData.skip = false
          }, 0)
        } else {
          this.userControlData.selectData = column
        }
      }
      this.userControlData.currentChangeFun = (page) => {
        this.userControlData.pageData.currentPage = page
        this.userControlData.getUserFun()
      }
      this.userControlData.sizeChangeFun = (pageSize) => {
        this.userControlData.pageData.pageSize = pageSize
        this.userControlData.getUserFun()
      }
      this.userControlData.searchChangeFun = (params, done) => {
        this.userControlData.searchData = params
        this.userControlData.getUserFun()
        done()
      }
      this.userControlData.searchResetFun = () => {
        this.userControlData.getUserFun()
      }
      this.userControlData.submitUserDataFun = () => {
        this.userControlData.loading = true
        this.userControlData
          .submitFun(this.userControlData.selectData)
          .then(() => {
            this.userControlData.loading = false
            this.userControlData.isShow = false
          })
          .catch(() => {
            this.userControlData.loading = false
          })
      }
    },
    //其他表单提交后执行
    formViewSubmitFun(done, data) {
      if (this.formControlData.submitFun) {
        try {
          this.formControlData
            .submitFun(data)
            .then(() => {
              done()
              this.formControlData.viewObj.isShow = false
            })
            .catch(() => {
              done()
            })
        } catch (error) {
          done()
          console.warn('子表其他表单提交方法异常' + error)
        }
      }
    },
  },
  directives: {
    loadmore: {
      componentUpdated: function (el, binding, vnode, oldVnode) {
        // 设置默认溢出显示数量
        var spillDataNum = 12

        // 设置隐藏函数
        var timeout = false
        let setRowDisableNone = function (topNum, showRowNum, binding) {
          if (timeout) {
            clearTimeout(timeout)
          }
          timeout = setTimeout(() => {
            binding.value.call(null, topNum, topNum + showRowNum + spillDataNum)
          })
        }
        setTimeout(() => {
          let newScrollTop = ''
          let oldScrollTop = ''
          const dataSize = vnode.data.attrs['data-size']
          const oldDataSize = oldVnode.data.attrs['data-size']
          if (dataSize === oldDataSize) return
          const selectWrap = el.querySelector('.el-table__body-wrapper')
          const selectTbody = selectWrap.querySelector('table tbody')
          const selectRow = selectWrap.querySelector('table tr')
          if (!selectRow) {
            return
          }
          const rowHeight = selectRow.clientHeight
          let showRowNum = Math.round(selectWrap.clientHeight / rowHeight)

          const createElementTR = document.createElement('tr')
          let createElementTRHeight =
            (dataSize - showRowNum - spillDataNum) * rowHeight
          createElementTR.setAttribute(
            'style',
            `height: ${createElementTRHeight}px;`
          )
          selectTbody.append(createElementTR)

          // 监听滚动后事件
          selectWrap.addEventListener('scroll', function () {
            if (
              oldScrollTop &&
              newScrollTop &&
              oldScrollTop == this.scrollTop
            ) {
              return false
            }
            oldScrollTop = newScrollTop
            newScrollTop = this.scrollTop

            let topPx = this.scrollTop - spillDataNum * rowHeight
            let topNum = Math.round(topPx / rowHeight)
            let minTopNum = dataSize - spillDataNum - showRowNum
            if (topNum > minTopNum) {
              topNum = minTopNum
            }
            if (topNum < 0) {
              topNum = 0
              topPx = 0
            }
            selectTbody.setAttribute(
              'style',
              `transform: translateY(${topPx}px)`
            )
            createElementTR.setAttribute(
              'style',
              `height: ${
                createElementTRHeight - topPx > 0
                  ? createElementTRHeight - topPx
                  : 0
              }px;`
            )
            setRowDisableNone(topNum, showRowNum, binding)
          })
        })
      },
    },
  },
  activated() {
    if (this.tableOption.isBigData)
      this.$nextTick(() => {
        // this.$refs.crud.doLayout()
      })
  },
}
</script>

<style lang="scss" scope>
.table-control {
  .avue-crud__pagination {
    display: none;
  }
  .avue-crud__empty {
    padding: 16px 0;
    .avue-empty__desc {
      margin-bottom: 0;
    }
  }
  .el-table__row {
    .el-form-item {
      .avue-checkbox {
        .el-checkbox-group {
          .el-checkbox:last-child {
            margin-right: 10px;
          }
        }
      }
      .avue-radio {
        .el-radio-group {
          .el-radio:last-child {
            margin-right: 10px;
          }
        }
      }
      .el-cascader {
        input {
          box-sizing: border-box;
          height: 32px;
        }
      }
    }
  }
}
.table-control-page {
  .avue-crud__pagination {
    display: block;
  }
}
</style>
<style lang="scss">
.code-sbulist-custom-image-box {
  .box-content {
    display: flex;
    cursor: pointer;
    .content-img {
      width: 32px;
      height: 32px;
    }
    .content-num {
      width: 32px;
      height: 32px;
      background-color: rgba($color: #999, $alpha: 0.7);
      margin-left: 5px;
      color: #fff;
      line-height: 32px;
      text-align: center;
      border-radius: 2px;
    }
    .content-icon {
      line-height: 32px;
      font-size: 14px;
      padding-left: 8px;
    }
    img {
      width: 32px;
      height: 32px;
    }
  }
}
.code-sbulist-custom-file-box {
  .box-content {
    display: flex;
    align-items: center;
    cursor: pointer;
    i {
      font-size: 14px;
    }
    .content-txt {
      max-width: 100px;
      padding: 0 5px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .content-num {
      width: 28px;
      height: 28px;
      background-color: rgba($color: #999, $alpha: 0.7);
      color: #fff;
      line-height: 28px;
      text-align: center;
      margin-right: 6px;
      border-radius: 2px;
    }
  }
}
.sbulist-table-dialog-box {
  .el-dialog__header {
    border-bottom: 1px solid #f1f1f1;
  }
  .avue-form__menu--center {
    display: none;
  }
  .el-dialog__body {
    padding-bottom: 0px;
  }
}
.table-control-menu-top-hide {
  .avue-crud__menu {
    display: none;
  }
}
</style>
