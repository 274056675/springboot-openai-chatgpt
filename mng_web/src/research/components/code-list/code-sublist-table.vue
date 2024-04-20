<template>
  <div class="code-sbulist-table" :class="{'code-sbulist-table-height':tableType=='expand'}">
    <avue-crud
      ref="crud"
      :option="tableOption"
      :data="tableData"
      :search.sync="tableQueryData"
      :page.sync="tablePage"
      :permission="tablePermission"
      :row-class-name="tableRowClassNameFun"
      :table-loading="loading"
      @selection-change="selectionChangeFun"
      @row-click="tableRowClickFun"
      @current-change="currentChangeFun"
      @size-change="sizeChangeFun"
      @row-save="rowSaveFun"
      @row-update="rowUpdateFun"
    >
      <!-- 菜单自定义(表格上面的按钮栏) -->
      <template slot="menuLeft">
        <!-- 左边按钮插槽 -->
        <el-button
          v-for="item in customButtonTop"
          :key="item.id"
          size="small"
          type="primary"
          @click="
              allCustomButtonFun(item.buttonCode, item.buttonStyle, item.optType, that)
            "
        >
          <i v-if="item.buttonIcon" :class="item.buttonIcon" style="margin-right:5px"></i>
          {{ item.buttonName }}
        </el-button>
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
          v-for="item in customButtonLink"
          :key="item.id"
          type="text"
          :icon="item.buttonIcon"
          size="small"
          @click.stop="moreButtonCommand({
            type: item.buttonCode,
            row: scope.row,
            index: scope.index,
            buttonCode: item.buttonCode,
            buttonStyle: item.buttonStyle,
            optType: item.optType,
            that,
          })"
        >{{ item.buttonName }}</el-button>
      </template>
      <!-- 自定义图片控件 -->
      <template
        v-for="(item, index) in viewImageArr"
        :slot="item.fieldImageName + 'Form'"
        slot-scope="scope"
      >
        <div :key="index" class="code-sbulist-custom-image-box">
          <div
            class="box-btn"
            v-if="scope.row[item.fieldName]==undefined || scope.row[item.fieldName].length <= 0"
          >
            <div v-if="disabled">无图片</div>
            <el-upload
              v-else
              :action="item.column.action"
              :before-upload="(file)=>customUploadFun(file,scope,item,'image')"
            >
              <el-button size="small" plain icon="el-icon-upload">上传图片</el-button>
            </el-upload>
          </div>
          <div class="box-content" v-else @click="opentDialogUploadeFun('image', item, scope.row)">
            <div class="content-img">
              <img :src="scope.row[item.fieldName].split(',')[0]" alt />
            </div>
            <div
              class="content-num"
              v-if="scope.row[item.fieldName].split(',').length > 1"
            >+{{ scope.row[item.fieldName].split(',').length - 1 }}</div>
            <div class="content-icon">
              <i class="el-icon-setting"></i>
            </div>
          </div>
        </div>
      </template>
      <!-- 自定义文件控件 -->
      <template
        v-for="(item, index) in viewFileArr"
        :slot="item.fieldFileName + 'Form'"
        slot-scope="scope"
      >
        <div :key="index" class="code-sbulist-custom-file-box">
          <div
            class="box-btn"
            v-if="scope.row[item.fieldName]==undefined ||scope.row[item.fieldName].length <= 0"
          >
            <div v-if="disabled">无文件</div>
            <el-upload
              v-else
              :action="item.column.action"
              :before-upload="(file)=>customUploadFun(file,scope,item,'file')"
            >
              <el-button size="small" plain icon="el-icon-upload">上传文件</el-button>
            </el-upload>
          </div>
          <div class="box-content" v-else @click="opentDialogUploadeFun('file', item, scope.row)">
            <i class="el-icon-link"></i>
            <span
              class="content-txt"
            >{{ scope.row['$Name'+item.fieldName]?scope.row['$Name'+item.fieldName][0]:scope.row[item.fieldName]}}</span>
            <span
              class="content-num"
              v-if="scope.row[item.fieldName].split(',').length > 1"
            >+{{ scope.row[item.fieldName].split(',').length - 1 }}</span>
            <i class="el-icon-setting"></i>
          </div>
        </div>
      </template>

      <!-- 自定义用户控件 -->
      <template
        v-for="(item, index) in viewUserControlArr"
        :slot="item.fieldUserName + 'Form'"
        slot-scope="scope"
      >
        <user-control
          :key="index"
          :tableItemVal="scope.row[item.fieldName]"
          :tableItemName="item.fieldName"
          :allUser="allUserData"
          :disabled="disabled"
          :allDepart="allDepartData"
          :tableItemScope="scope"
          exhibitionType="tableEdit"
          @set-form-val="(obj) => setTableFormValue(obj, scope.row.$index)"
        ></user-control>
      </template>

      <!-- 自定义部门控件 -->
      <template
        v-for="(item, index) in viewDepartControlArr"
        :slot="item.fieldDepartName + 'Form'"
        slot-scope="scope"
      >
        <depart-control
          :key="index"
          :tableItemVal="scope.row[item.fieldName]"
          :tableItemName="item.fieldName"
          :allDepart="allDepartData"
          :disabled="disabled"
          :tableItemScope="scope"
          @set-form-val="(obj) => setTableFormValue(obj, scope.row.$index)"
        ></depart-control>
      </template>
    </avue-crud>
    <table-tree
      ref="table_tree"
      v-if="isTableTreeControl"
      :optionData="tableTreeControlOption"
      :treeControlFun="treeControlFun.bind(this)"
    ></table-tree>
    <table-select
      ref="table_select"
      v-if="isTableSelectControl"
      :optionData="tableSelectControlOption"
      :selectControlFun="selectControlFun.bind(this)"
    ></table-select>
    <form-view
      ref="form_view"
      v-if="isFormViewControl"
      :formOptionData="FormViewControlOption"
      :formViewControlFun="formViewControlFun.bind(this)"
    ></form-view>
    <el-dialog
      v-dialogdrag
      :title="dialogTitle"
      :visible.sync="isDialog"
      class="sbulist-table-dialog-box"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="dialogBeforeClose"
      width="655px"
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
          <div @click="downloadFile(scope.file.url,scope.file.name)" style="cursor: pointer">
            <i class="el-icon-link"></i>
            <span
              style="flex: 1"
            >{{dialogFormData['$Name'+dialogFormOption.column[0].prop]?dialogFormData['$Name'+dialogFormOption.column[0].prop][scope.file.uid]:dialogFormData[dialogFormOption.column[0].prop]}}</span>
            <i
              class="el-icon-close"
              v-if="!disabled"
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
  </div>
</template>

<script>
let validateRulesAll = []
let viewFileNameObj = {} //存储所有文件名
import { analysisFunction } from '@/research/util/myUtil.js'
import { apiRequestHead } from '@/config/url.js'
import { getDetails } from '@/api/research/code'
import {
  getFormHeadApi,
  getActionApi,
  postActionApi,
  deleteActionApi,
} from '@/api/research/codelist'
import { uploadeFileApi, getUploadeFileNameApi } from '@/api/research/codelist'
import DepartControl from '@/research/components/general-control/depart-control'
import UserControl from '@/research/components/general-control/user-control'
import TableTree from '@/research/components/general-control/table-tree.vue'
import TableSelect from '@/research/components/general-control/table-select.vue'
import FormView from '@/research/components/general-control/form-view.vue'
export default {
  components: {
    DepartControl,
    UserControl,
    TableTree,
    TableSelect,
    FormView,
  },
  props: [
    'boxType',
    'tableAllColumnRules',
    'disabled',
    'tableTabName',
    'tableKey',
    'currDataList',
    'allChangeFun',
    'getParentFieldValue',
    'setParentFieldValue',
    'simpleDateFormat',
    'tableColumnDic',
    'tableColumn',
    'tableType',
    'showMenu',
    'sortCustomButtonFun',
    'opentJsEnhance',
    'formParentDataId',
  ],
  filters: {},
  data() {
    return {
      that: null,
      apiHeadData: {},
      tablePermission: {}, //权限控制
      loading: false,
      table: {},
      tableData: [],
      tableAllData: [],
      tableOption: {
        addBtn: false,
        addRowBtn: true,
        menu: false,
        border: true,
        columnBtn: false,
        refreshBtn: false,
        index: true, //开启序号
        selection: true, //开启选择框
        reserveSelection: true, //保留之前的勾选
        tip: false,
        dialogFullscreen: false, //是否全屏
        rowKey: '$index',
        column: [],
      },
      tableIsPage: false,
      tablePage: {},
      // 自定义搜索
      searchFormOption: {
        column: [],
      },
      tableQueryData: {}, //搜索条件
      currRow: {},
      allUserData: [],
      allDepartData: [],
      tableNeetRules: ['text', 'password', 'textarea', 'umeditor', 'markdown'], //可以校验的控件
      //表单 单独占一行的控件
      fieldSpanOneLine: ['image', 'file'],
      // 需要字典数据的控件
      viewListSelect: ['list', 'radio', 'switch', 'list_multi', 'sel_search'],
      //所有图片
      viewImageArr: [],
      //所有文件控件
      viewFileArr: [],
      viewListTreeAllData: [],
      viewUserControlArr: [],
      viewDepartControlArr: [],

      //弹窗
      isDialog: false,
      dialogTitle: '上传图片',
      dialogFormOption: {
        submitBtn: false,
        emptyBtn: false,
        column: [{}],
      },
      dialogFormData: {},
      currentDialogField: {}, //当前弹窗操作的字段
      validateIndex: 0,
      rowClassNameBeginIndex: 2,
      tableSelectData: [],
      tableSelectIndex: [],
      //自定义按钮
      customButtonTop: [],
      customButtonLink: [],
      customOnlineEnhanceJsList: {}, //js增强list所有方法
      //js增强方法名
      customOnlineEnhanceJsName: {
        list: [],
      },
      //用于显示树表格组件
      isTableTreeControl: false,
      tableTreeControlOption: {
        tableId: '',
        defaultTree: [],
        stopTree: [],
        isDialog: false,
        defaultProps: {},
        defaulKey: '',
        title: '',
        addType: {
          type: '',
          tableId: '',
        },
        asyncTableName: '',
      },
      //用于显示表格选择组件
      isTableSelectControl: false,
      tableSelectControlOption: {
        title: '',
        isDialog: false,
        width: '',
        tableId: '',
        option: {},
        multiple: '',
        isPage: '',
        addType: {
          type: '',
          tableId: '',
          isCell: '',
        },
      },
      //用于显示表单显示组件
      isFormViewControl: false,
      FormViewControlOption: {
        viewObj: {},
        formId: '',
        formOpenType: '',
        actionData: {},
        btnPermissions: {},
      },
    }
  },
  async mounted() {
    this.loading = true
    this.that = this
    let detailsRes = ''
    if (this.tableType == 'expand') {
      detailsRes = this.tableColumn
    } else {
      detailsRes = await getDetails(this.tableTabName)
    }

    let columns = detailsRes.data.data.fieldList
    let headData = detailsRes.data.data.head
    let columnsObj = {}
    columns.forEach((item) => {
      columnsObj[item.dbFieldName] = item
    })

    if (this.currDataList && this.tableType != 'expand') {
      if (!this.disabled && !this.showMenu) {
        this.tableData = this.currDataList.map((item) => {
          item.$cellEdit = true
          return item
        })
      } else {
        this.tableData = this.deepClone(this.currDataList)
      }
    }

    //判断是否需要显示头部按钮  操作列
    if (this.showMenu) {
      this.tableOption = {
        ...this.tableOption,
        menu: true,
        columnBtn: false,
        addBtn: true,
        addRowBtn: false,
      }
    }
    let headObjKeys = Object.keys(headData)
    let formSpan = 24 //表单列布局 span属性
    headObjKeys.forEach((item) => {
      let value = headData[item]
      switch (item) {
        case 'formTemplate':
          formSpan = formSpan / (value - 0)
          break
        case 'isCheckbox':
          if (value === 'Y' && this.tableType != 'expand') {
            this.tableOption.selection = true
            this.tableOption.reserveSelection = true
          } else {
            this.tableOption = {
              ...this.tableOption,
              index: false,
              selection: false,
              reserveSelection: false,
            }
          }
          break
        case 'isPage':
          if (value === 'Y' && this.tableType == 'expand') {
            this.tableIsPage = true
            this.tablePage = {
              total: 0,
              currentPage: 1,
              pageSize: 5,
              pageSizes: [5],
              background: true,
              layout: 'sizes, prev, pager, next, jumper,total',
            }
          }
          break
        case 'isDesForm':
          if (value === 'Y') {
            this.tableOption.addBtn = false
          }
          break
        case 'hideHeader':
          if (value === 'Y') {
            this.tableOption.header = false
          }
          break
        case 'hideMenu':
          if (value === 'Y') {
            this.tableOption.menu = false
          }
          break
        default:
          break
        // desFormCode 表单编码 作用未知
      }
    })
    if (this.tableType == 'expand') {
      await new Promise((resolve) => {
        setInterval(() => {
          if (this.currDataList != undefined) {
            resolve(true)
          }
        }, 500)
      })
      this.tableAllData = this.currDataList
      this.tablePage.total = this.tableAllData.length

      this.tablePageFun()
      
    }
    if (this.showMenu) {
      this.tableOption.column = this.setTableDataFun(
        columnsObj,
        formSpan,
        false
      )
    } else {
      this.tableOption.column = this.setTableDataFun(columnsObj, formSpan)
    }
    // 数据处理
    let fileArr = []
    if (this.viewFileArr.length > 0) {
      this.viewFileArr.forEach((item) => {
        fileArr.push(item.fieldName)
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
              
              this.$refs.crud.columnInit()
            })
          }
        })
      }
    })

    if (this.disabled) {
      this.tableOption.header = false
      this.tableOption.addRowBtn = false
    }

    let allChangeFun = this.allChangeFun
    // js增强 值变化方法
    if (allChangeFun instanceof Object) {
      for (let key in allChangeFun) {
        let column = this.findObject(this.tableOption.column, key)
        if (column != -1) {
          let timer = ''
          column.change = (event) => {
            if (this.loading) {
              return false
            }
            if (timer) {
              clearTimeout(timer)
            }
            timer = setTimeout(() => {
              try {
                let currRow = this.deepClone(this.currRow)
                event.row = currRow.row
                event.target = currRow.event
                allChangeFun[key](this, event)
              } catch (error) {
                console.warn(
                  `js增强：${this.tableKey}_onlChange方法中<${key}>字段监听异常`,
                  error
                )
              }
            }, 300)
          }
        }
      }
    }
    this.table.setFieldsValue = (param, index) => {
      if (
        param instanceof Object &&
        !(param instanceof Array) &&
        index < this.tableData.length
      ) {
        this.tableData = this.tableData.map((item) => {
          if (item.$index == index) {
            item = {
              ...item,
              ...param,
            }
          }
          return item
        })
      }
    }

    if (this.showMenu || this.opentJsEnhance) {
      getFormHeadApi({ headId: this.tableTabName }).then((res) => {
        // 获取自定义按钮
        let columsData = res.data.data
        this.apiHeadData = columsData
        let allCustomButton = []
        if (columsData.cgButtonList) {
          allCustomButton = [...allCustomButton, ...columsData.cgButtonList]
        }
        if (allCustomButton.length >= 0) {
          let buttonObj = this.sortCustomButtonFun(allCustomButton)
          this.customButtonTop = buttonObj.top
          this.customButtonLink = buttonObj.link
        }
        // 获取自定义js增强
        this.initOnlineEnhanceJs(columsData.enhanceJs)
      })
    }

    setTimeout(() => {
      this.loading = false
    }, 1500)
  },
  methods: {
    //分页逻辑
    tablePageFun() {
      let { pageSize, currentPage } = this.tablePage
      
      if (!this.tableIsPage || this.tablePage.total <= pageSize) {
        this.tableData = this.tableAllData

        return false
      }
      let num = currentPage * pageSize - 1
      let numArr = []
      for (let index = num - (pageSize - 1); index <= num; index++) {
        numArr.push(index)
      }
      
      this.tableData = []
      this.tableAllData.forEach((item, index) => {
        if (numArr.includes(index)) {
          this.tableData.push(item)
        }
      })
    },
    // 切换页
    currentChangeFun(page) {
      this.tablePage.currentPage = page
      this.tablePageFun()
    },
    // 切换每页显示数
    sizeChangeFun(pageSize) {
      this.tablePage.currentPage = 1
      this.tablePage.pageSize = pageSize
      this.tablePageFun()
    },
    codeFileControlDelFun(fileName, obj) {
      
      let arr = []
      if (this.dialogFormData[fileName] instanceof Array) {
        arr = this.dialogFormData[fileName]
      } else {
        arr = this.dialogFormData[fileName].split(',')
      }
      let fileStr = arr.filter((item) => {
        return item != obj.file.url
      })
      fileStr.join(',')
      this.dialogFormData[fileName] = fileStr.join(',')
    },
    //当前点击行数据方法
    tableRowClickFun(row, column, event) {
      setTimeout(() => {
        this.currRow = {
          row,
          column,
          event,
        }
      }, 300)
    },
    //下载文件
    downloadFile(url, name) {
      var aEle = document.createElement('a') // 创建a标签
      aEle.download = name // 设置下载文件的文件名
      aEle.href = url // content为后台返回的下载地址
      aEle.click() // 设置点击事件
    },
    //上传文件 图片
    customUploadFun(file, scope, item, type) {
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
          this.tableData[scope.row.$index][item.column.prop] = url
          if (type == 'file') {
            this.tableData[scope.row.$index]['$Name' + item.column.prop] = [
              name,
            ]
          }
        })
        .catch(() => {
          this.$message.error(
            `上传${type == 'file' ? '文件' : '图片'}失败，请重新上传~`
          )
        })
      return false
    },
    //设置所有文件名
    setAllFileNameFun() {
      this.viewFileArr.forEach(async (item) => {
        this.tableData.forEach(async (dataItem) => {
          let fieldUrl = dataItem[item.fieldName]
          if (fieldUrl) {
            let fileRes = await getUploadeFileNameApi(fieldUrl)
            let fileName = fieldUrl.split('/')
            fileName = fileName[fileName.length - 1]
            if (fileRes.data.success && fileRes.data.data) {
              fileName = fileRes.data.data
            }
            viewFileNameObj[fieldUrl] = fileName
          }
        })
      })
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
    tableRowClassNameFun({ rowIndex }) {
      return `code-sublist-table-row-${rowIndex}`
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
      if (fileArr instanceof Array) {
        fileArr = fileArr.join(',')
      }
      this.tableData[this.currentDialogField.index][
        this.currentDialogField.fieldName
      ] = fileArr
      if (this.currentDialogField.type == 'file') {
        let fileNameArr = this.deepClone(
          this.dialogFormData['$Name' + this.currentDialogField.fieldName]
        )
        this.tableData[this.currentDialogField.index][
          '$Name' + this.currentDialogField.fieldName
        ] = fileNameArr
      }

      this.isDialog = false
    },
    //打开图片或文件  弹窗
    opentDialogUploadeFun(type, item, row) {
      
      this.dialogFormOption.column = []
      this.dialogFormData[item.fieldName] = this.deepClone(row[item.fieldName])
      this.dialogFormData['$Name' + item.fieldName] = this.deepClone(
        row['$Name' + item.fieldName]
      )
      this.currentDialogField = {
        fieldName: item.fieldName,
        index: row.$index,
        type,
      }
      this.dialogFormOption.column = [
        ...this.dialogFormOption.column,
        item.column,
      ]
      if (type == 'image') {
        this.dialogTitle = '上传图片'
      }
      if (type == 'file') {
        this.dialogTitle = '上传文件'
      }
      this.isDialog = true
    },
    //图片上传成功
    customImgUploadSuccessFun(response, scope, fieldName) {
      
      this.tableData[scope.row.$index][fieldName] = [response.result.data.lj]
    },
    //图片上传失败
    customImgUploadErrorFun(err, file, fileList) {
      
    },
    //校验表格数据方法
    verifyFormFun() {
      return new Promise((resolve) => {
        if (this.tableData.length <= 0) {
          resolve({
            res: true,
            tabName: this.tableTabName,
            data: { [this.tableKey]: [] },
          })
          return false
        }
        let resObj = {}

        this.$refs.crud.validateCellForm().then((res) => {
          let resJson = JSON.stringify(res)
          if (resJson == '{}' || this.showMenu) {
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
            tabName: this.tableTabName,
            data: { [this.tableKey]: allData },
          }

          resolve(resObj)
        })
      })
    },
    //设置表格弹窗表单值
    setTableFormValue(obj, index) {
      this.tableData[index][obj.fieldName] = obj.value
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
    //文件、图片上传超过限制上传数 提示
    uploadExceedFun(limit, files, fileList, column) {
      this.$message({
        showClose: true,
        message: `<${column.label}>只允许上传${limit}个文件`,
        type: 'warning',
      })
      
    },
    //表格格式数据处理
    setTableDataFun(obj, formSpan, isCell = true) {
      //先对obj排序
      let untreatedColumn = []
      let unllOrderNum = []
      for (let key in obj) {
        let value = obj[key]
        value.prop = key
        if (value.orderNum) {
          untreatedColumn.push(value)
        } else {
          unllOrderNum.push(value)
        }
      }
      untreatedColumn.sort((a, b) => {
        return a.orderNum - b.orderNum
      })
      untreatedColumn = [...untreatedColumn, ...unllOrderNum]

      let tableColumn = []
      untreatedColumn.forEach((item, index) => {
        // 文本框 单选框 开关 日期(yyyy-MM-dd) 日期（yyyy-MM-dd HH:mm:ss） 文件 图片 下拉框 下拉多选框
        // 下拉搜索框 popup弹出框 部门选择 用户选择
        let columnItem = {
          label: item.dbFieldTxt, //文本
          prop: item.dbFieldName, //字段名
          span: formSpan,
          value: item.fieldDefaultValue, //默认值
          minWidth: item.fieldLength,

          // 配置默认字段（防止动态修改不生效）
          display: true,
          hide: false,
        }
        if (isCell) {
          columnItem.cell = true
        }
        if (this.disabled) {
          columnItem.disabled = this.disabled
        }

        columnItem.order = untreatedColumn.length - index

        if (item.isReadOnly === 1) {
          //只读
          columnItem.readonly = true
        }
        //表单不显示
        if (item.isShowForm === 0 && this.showMenu == true) {
          columnItem.display = false
        }
        if (item.isShowList === 0) {
          //列表不显示
          columnItem.hide = true
          if (!this.showMenu) {
            tableColumn.push(columnItem)
            return false
          }
        }
        /* ====== 控件处理 ===== */
        //数据格式化
        if (
          [
            'radio',
            'switch',
            'list_multi',
            'sel_search',
            'sel_depart',
            'sel_user',
          ].includes(item.fieldShowType)
        ) {
          if (item.dbType == 'int') {
            columnItem.dataType = 'number'
          } else {
            columnItem.dataType = 'string'
          }
        }
        //配置字典
        if (this.viewListSelect.includes(item.fieldShowType)) {
          columnItem.props = {
            label: 'title',
            value: 'value',
          }
          if (this.tableColumnDic[item.dbFieldName]) {
            columnItem.dicData = this.tableColumnDic[item.dbFieldName]
          } else {
            columnItem.dicData = []
          }
          //开关
          if (item.fieldShowType == 'switch') {
            if (
              columnItem.value !== '' &&
              columnItem.value !== undefined &&
              typeof columnItem.value == 'string'
            ) {
              columnItem.value = Number(columnItem.value)
            }
            columnItem.props = {}
            columnItem.activeIconClass = '无'
            columnItem.inactiveIconClass = '无'

            let extend = ''
            //判断是否自定义保存参数
            if (item.fieldExtendJson) {
              try {
                extend = JSON.parse(item.fieldExtendJson)
              } catch {
                console.warn(
                  `<${item.dbFieldTxt}>自定义参数配置错误,需要符合json格式`
                )
              }
            }
            if (extend instanceof Array && extend.length == 2) {
              columnItem.dicData = [
                {
                  label: '否',
                  value: extend[1],
                },
                {
                  label: '是',
                  value: extend[0],
                },
              ]
              if (columnItem.value === '' || columnItem.value === undefined) {
                columnItem.value = extend[0]
              }
            } else {
              columnItem.dicData = [
                {
                  label: '否',
                  value: 'N',
                },
                {
                  label: '是',
                  value: 'Y',
                },
              ]
              if (columnItem.value === '' || columnItem.value === undefined) {
                columnItem.value = 'N'
              }
            }
          }
        }

        //用户控件
        if (item.fieldShowType == 'sel_user') {
          columnItem = {
            ...columnItem,
            type: 'select',
            formslot: true,
            multiple: true,
            dicData: this.allUserData,
            props: {
              label: 'realName',
              value: 'id',
            },
            minWidth: 150,
          }
          this.viewUserControlArr.push({
            fieldName: item.dbFieldName, //字段名
            fieldUserName: item.dbFieldName, //字段名
          })
        }
        //部门控件
        if (item.fieldShowType == 'sel_depart') {
          columnItem = {
            ...columnItem,
            multiple: true,
            type: 'select',
            formslot: true,
            dicData: this.allDepartData,
            props: {
              label: 'deptName',
              value: 'id',
            },
            minWidth: 150,
          }
          this.viewDepartControlArr.push({
            fieldName: item.dbFieldName, //字段名
            fieldDepartName: item.dbFieldName, //字段名
          })
        }

        //处理字段类型
        switch (item.fieldShowType) {
          case 'text':
            //文本框
            columnItem.maxlength = item.dbLength
            if (['int', 'Double'].includes(item.dbType)) {
              columnItem.type = 'number'
            }
            break
          case 'list':
            columnItem.type = 'select'
            columnItem.minWidth = 150
            //下拉框
            break
          case 'textarea':
            columnItem.type = 'textarea'
            columnItem.minRows = 2
            if (this.showMenu) {
              columnItem.span = 24
            }
            //下拉框
            break
          case 'radio':
            columnItem.type = 'radio'
            columnItem.minWidth = 110
            //单选框
            break
          case 'switch':
            columnItem.type = 'switch'
            //开关
            break
          case 'date':
            columnItem.type = 'date'
            columnItem.format = 'yyyy-MM-dd'
            columnItem.valueFormat = 'yyyy-MM-dd'
            columnItem.minWidth = 160
            //日期(yyyy-MM-dd)
            break
          case 'datetime':
            columnItem.type = 'datetime'
            columnItem.format = 'yyyy-MM-dd HH:mm:ss'
            columnItem.valueFormat = 'yyyy-MM-dd HH:mm:ss'
            columnItem.minWidth = 210
            //日期（yyyy-MM-dd HH:mm:ss）
            break

          case 'list_multi':
            columnItem.type = 'select'
            columnItem.multiple = true
            columnItem.minWidth = 150
            //下拉多选框
            break
          case 'sel_search':
            columnItem.type = 'select'
            columnItem.filterable = true
            columnItem.minWidth = 150
            //下拉搜索框
            break
          default:
            break
        }

        //扩展参数
        if (item.fieldExtendJson && !['switch'].includes(item.fieldShowType)) {
          let extend = ''
          let extendBool = true
          try {
            extend = JSON.parse(item.fieldExtendJson)
          } catch (error) {
            extend = {}
            extendBool = false
          }

          for (let key in extend) {
            if (
              key == 'uploadnum' &&
              ['image', 'file'].includes(item.fieldShowType)
            ) {
              //限制上传文件或者图片个数
              columnItem.limit = extend[key] - 0
            } else {
              columnItem[key] = extend[key]
              if (key == 'searchValue') {
                this.tableQueryData[columnItem.prop] = extend[key]
              }
            }
          }
          if (!extendBool) {
            this.$message({
              message:
                '请为<' +
                item.dbFieldTxt +
                '>配置正确格式的扩展参数（例：{"uploadnum":2}）',
              duration: 5000,
              type: 'warning',
            })
          }
        }

        //处理校验规则
        columnItem.rules = []
        if (item.fieldValidType) {
          let rules = this.tableAllColumnRules[item.fieldValidType]
            ? this.tableAllColumnRules[item.fieldValidType]
            : {}
          if (
            rules.pattern != 'only' &&
            this.tableNeetRules.includes(item.fieldShowType) &&
            rules.type.includes(item.dbType)
          ) {
            let reg = new RegExp(rules.pattern)
            validateRulesAll[item.dbFieldName] = (rule, value, callback) => {
              if (!reg.test(value)) {
                callback(new Error(`${rules.msg}`))
              } else {
                callback()
              }
            }
          } else if (rules.pattern == 'only') {
            validateRulesAll[item.dbFieldName] = (rule, value, callback) => {
              let valueShowNum = 0
              this.tableData.forEach((tableDataItem) => {
                if (value == tableDataItem[item.dbFieldName]) {
                  valueShowNum++
                }
              })
              if (valueShowNum == 1) {
                callback()
              } else {
                callback(new Error(`值不可用，系统中已存在！`))
              }
            }
          }
          if (validateRulesAll[item.dbFieldName]) {
            columnItem.rules = [
              {
                validator: validateRulesAll[item.dbFieldName],
                trigger: 'blur',
              },
            ]
          }
        }
        if (item.fieldMustInput == '1') {
          columnItem.rules.push({
            required: true,
            trigger: 'blur',
            message: '值不能为空',
          })
        }

        // 校验存储长度
        if (
          !['date', 'datetime', 'time'].includes(item.fieldShowType) &&
          !['Text'].includes(item.dbType)
        ) {
          columnItem.rules.push({
            validator: (rule, value, callback) => {
              if (value && value.length > item.dbLength) {
                callback(new Error('超过最大长度'))
              } else {
                callback()
              }
            },
            trigger: 'blur',
          })
        }
        //文件 图片
        if (['image', 'file'].includes(item.fieldShowType)) {
          columnItem.type = 'upload'
          columnItem.slot = true
          columnItem.action = `api/${apiRequestHead}/cgform-api/upload/file`
          columnItem.propsHttp = {
            res: 'data',
            url: 'link',
            name: 'originalName',
          }
          columnItem.dataType = 'string'
          if (item.fieldShowType == 'file') {
            columnItem.minWidth = 120
            columnItem.data = {
              type: 1,
            }
            columnItem.accept = '*/*'
            this.viewFileArr.push({
              fieldName: item.dbFieldName,
              fieldFileName: item.dbFieldName,
              column: columnItem,
            })
          } else {
            columnItem.listType = 'picture-card'
            columnItem.minWidth = 120
            columnItem.accept = 'image/*'
            columnItem.data = {
              type: 0,
            }
            this.viewImageArr.push({
              fieldName: item.dbFieldName,
              fieldImageName: item.dbFieldName,
              column: columnItem,
            })
          }
        }
        //处理字典
        tableColumn.push(columnItem)
      })
      
      return tableColumn
    },
    //添加数据 新值行
    addSubListData(rows) {
      let includeId = {}
      let noIncludeId = []
      let alreadyUpdata = []
      let defaultItem = {}
      this.tableOption.column.forEach((item) => {
        defaultItem[item.prop] = ''
      })

      rows.forEach((item) => {
        item.$cellEdit = true
        if (this.viewImageArr.length > 0) {
          this.viewImageArr.forEach((imgItem) => {
            if (item[imgItem.fieldName] === undefined) {
              item[imgItem.fieldName] = []
            }
          })
        }
        if (item.id) {
          includeId[item.id] = item
        } else {
          noIncludeId.push(item)
        }
      })
      this.tableData = this.tableData.map((item) => {
        if (includeId[item.id]) {
          alreadyUpdata.push(item.id)
          item = {
            ...item,
            ...includeId[item.id],
          }
        }
        return item
      })
      for (let key in includeId) {
        if (!alreadyUpdata.includes(key)) {
          noIncludeId.push(includeId[key])
        }
      }
      noIncludeId = noIncludeId.map((item) => {
        return {
          ...defaultItem,
          ...item,
        }
      })
      this.tableData = [...this.tableData, ...noIncludeId]
    },
    //清除数据
    clearSubListData() {
      this.tableData = []
    },
    //新增数据
    rowSaveFun(row, done) {
      
      // row.id = row.$index
      this.tableData.push(row)
      
      done()
    },
    //编辑数据
    rowUpdateFun(row, index, done) {
      
      this.tableData = this.tableData.map((item, i) => {
        if (i == index) {
          item = row
        }
        return item
      })
      done()
    },
    //自定义按钮触发的方法
    async allCustomButtonFun(btnCode, btnType, enhanceType, that, row) {
      /* console.log(
        '触发自定义按钮' + btnCode,
        btnCode,
        btnType,
        enhanceType,
        that,
        row
      ) */
      //触发js增强方法
      if (enhanceType == 'js') {
        if (
          btnType == 'button' &&
          this.customOnlineEnhanceJsList[btnCode] != undefined
        ) {
          try {
            this.customOnlineEnhanceJsList[btnCode](that)
          } catch (error) {
            console.warn(error)
          }
        }
        if (
          btnType == 'link' &&
          this.customOnlineEnhanceJsList[btnCode] != undefined
        ) {
          try {
            this.customOnlineEnhanceJsList[btnCode](that, row)
          } catch (error) {
            console.warn(error)
          }
        }
      }
      //触发sql增强
      if (enhanceType == 'action') {
        let apiData = {
          buttonCode: btnCode,
          formId: this.currCodeId,
        }
        if (btnType == 'link') {
          apiData.dataId = row.id
        }
        if (btnType == 'button') {
          
          if (this.tableSelectId.length == 1) {
            apiData.dataId = this.tableSelectId[0]
          } else {
            this.$message({
              message: '请选择一条数据！',
              type: 'warning',
            })
            return false
          }
        }
        if (btnType == 'form') {
          apiData.uiFormData = row
        }
        //访问接口 接口处理完才执行下面代码
        
        // await touchSqlEnhanceApi(apiData)
        // if (btnType == 'link' || btnType == 'button') {
        //   this.$refs.codeTestList.selectClear()
        //   //重新获取页面数据
        //   this.initTableData({
        //     currentPage: this.tablePage.currentPage,
        //     pageSize: this.tablePage.pageSize,
        //   })
        // }
      }
    },
    //初始化js增强部分默认方法
    initOnlineEnhanceJs(listJs) {
      let OnlineEnhanceJsList = undefined
      if (listJs) {
        OnlineEnhanceJsList = analysisFunction(listJs)
        if (OnlineEnhanceJsList !== false) {
          try {
            this.customOnlineEnhanceJsList = OnlineEnhanceJsList(
              getActionApi,
              postActionApi,
              deleteActionApi
            )
            this.customOnlineEnhanceJsName.list = Object.keys(
              this.customOnlineEnhanceJsList
            )
            
            if (this.customOnlineEnhanceJsList == undefined) {
              this.customOnlineEnhanceJsList = {}
            }
            if (this.customOnlineEnhanceJsName.list.includes('mounted')) {
              try {
                this.customOnlineEnhanceJsList.mounted(this.that)
              } catch (error) {
                console.warn(error)
              }
            }
          } catch (error) {
            console.warn(error)
          }
        } else {
          console.warn('请检查子表js增强(list)编写是否有误~')
        }
      }
    },
    //操作栏更多
    async moreButtonCommand(command) {
      this.currentRowDataObj = command.row
      if (command.buttonCode) {
        this.allCustomButtonFun(
          command.buttonCode,
          command.buttonStyle,
          command.optType,
          command.that,
          command.row
        )
      }
    },
    //树组件通用方法
    async treeControlFun(type, obj) {
      //type 方法类型 dialog:显隐弹窗  apiAdd:通过api批量新增数据 subDataAdd:子表数据新增
      if (type == 'dialog') {
        this.tableTreeControlOption.isDialog = obj.bool
      }
      //父表数据存储
      if (type == 'dataAdd') {
        this.addSubListData(obj.data)
        this.tableTreeControlOption.isDialog = false
      }
    },
    //表格选择组件通用方法
    selectControlFun(type, obj) {
      //type 方法类型 dialog:显隐弹窗
      if (type == 'dialog') {
        this.tableSelectControlOption.isDialog = obj.bool
      }
      //父表数据存储
      if (type == 'dataAdd') {
        this.addSubListData(obj.data)
        this.tableSelectControlOption.isDialog = false
      }
    },
    //表单控件通用方法
    formViewControlFun(type) {
      //type 方法类型 hide:隐藏弹窗
      if (type == 'hide') {
        this.FormViewControlOption.viewObj.isShow = false
      }
    },
  },
}
</script>

<style lang="scss">
.code-sbulist-table-height {
  height: 320px;
}
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
      flex: 0 0 28px;
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
.code-sbulist-table {
  .depart-control {
    position: relative;
  }
  .cell {
    .code-sublist-table-msg {
      position: fixed;
      left: 0;
      top: 0;
      display: none;
      z-index: 999;
      .code-sublist-table-msg-icon {
        position: absolute;
        font-size: 16px;
        top: -14px;
        left: 14px;
        color: rgba(0, 0, 0, 0.75);
      }
      .code-sublist-table-msg-text {
        padding: 6px 8px;
        color: #fff;
        text-align: left;
        text-decoration: none;
        word-wrap: break-word;
        background-color: rgba(0, 0, 0, 0.75);
        border-radius: 4px;
      }
    }
  }
}
.code-sbulist-table-disabled {
  .avue-crud__menu {
    min-height: 0;
  }
}
</style>
