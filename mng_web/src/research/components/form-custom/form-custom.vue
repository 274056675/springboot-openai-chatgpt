<template>
  <div
    :class="[
      { 'avue-form-work-style': option.formStyle == 'work' },
      { 'avue-form-form-style': option.formStyle != 'work' },
      {
        'avue-form-form-null-menu':
          !btnPermissions.clearBtn &&
          !btnPermissions.cancelBtn &&
          !btnPermissions.submitBtn,
      },
    ]"
    v-loading="loading"
  >
    <div class="form-custom-print-box" v-if="option.isShowPrint">
      <el-button type="text" style @click="opentPrinterFun" icon="el-icon-printer"></el-button>
    </div>
    <avue-form
      id="test-print"
      ref="form"
      class="form-custom"
      :class="{ 'avue--detail': isDetailStyle || option.formStyle == 'detail' }"
      v-model="formData"
      :option="option"
      :upload-after="uploadAfter"
      :upload-exceed="uploadExceedFun"
      @submit="formHandleSubmitFun"
    >
      <!-- 自定义按钮 -->
      <template slot="menuForm">
        <el-button
          @click="clearAllDataFun"
          v-if="btnPermissions.clearBtn == true"
          icon="el-icon-delete"
        >清空</el-button>
        <el-button
          @click="cancelBtnFun"
          v-if="btnPermissions.cancelBtn == true"
          icon="el-icon-circle-close"
        >取消</el-button>
      </template>
      <!-- tabs自定义 -->
      <template v-for="(tabItem, tabIndex) in tabsOption" slot-scope="scope" :slot="tabItem.prop">
        <div
          class="form-custom-tabs"
          :class="scope.column.class"
          :key="tabIndex"
          :style="{ width: scope.column.width }"
        >
          <el-tabs
            class="widget-form-tabs-box"
            :type="scope.column.styleType"
            :tab-position="scope.column.location"
            v-model="tabItem.tabsValue"
            :size="scope.size"
            @tab-click="(tab) => setTabsSwitchFun(tab, scope.column.prop)"
          >
            <el-tab-pane
              v-for="(paneItem, paneIndex) in scope.column.children.column"
              :key="paneIndex"
              :label="paneItem.label"
              :name="paneItem.prop"
              :disabled="paneItem.disabled"
            >
              <span slot="label">
                <i v-if="paneItem.icon" :class="paneItem.icon"></i>
                {{ paneItem.label }}
              </span>
              <form-control
                ref="formControl"
                :formOption="paneItem"
                :currTabsValue="paneItem.prop"
                :currTabsProp="scope.column.prop"
                :formOpenType="formOpenType"
                :allExecuteRule="allExecuteRule"
                :setJsEnhanceFun="setJsEnhanceFun.bind(this)"
                :getCurrPacDataTextFun="getCurrPacDataTextFun.bind(this)"
                :lazyLoadFun="lazyLoadFun.bind(this)"
                :allFormListData="allFormListData"
              ></form-control>
            </el-tab-pane>
          </el-tabs>
        </div>
      </template>
      <!-- 自定义按钮组 -->
      <template
        v-for="(btnListItem, btnListIndex) in btnListOption"
        slot-scope="scope"
        :slot="btnListItem.prop"
      >
        <div
          class="form-custom-btn-list"
          :class="scope.column.class"
          :key="btnListIndex"
          v-if="btnListItem.display"
        >
          <div
            class="btn-box"
            v-for="(childData,childIndex) in scope.column.children.column"
            :key="childIndex"
          >
            <div
              class="form-custom-button"
              v-if="childData.display"
              :class="childData.class"
              :style="{margin:`0 ${scope.column.params.margin}px`}"
            >
              <el-button
                :size="childData.size"
                :type="childData.buttonType"
                :plain="childData.plain"
                :round="childData.round"
                :circle="childData.circle"
                :disabled="childData.disabled"
                :icon="childData.buttonIcon"
                @click="customButtonFun(childData.clickFun)"
              >{{ childData.buttonText }}</el-button>
            </div>
          </div>
        </div>
      </template>
      <!-- 自定义评分 -->
      <template
        v-for="(rateItem, rateIndex) in rateOption"
        slot-scope="scope"
        :slot="rateItem.prop"
      >
        <div class="form-custom-rate" :class="scope.column.class" :key="rateIndex">
          <el-rate
            :size="scope.size"
            v-model="formData[scope.column.prop]"
            :allow-half="scope.column.allowHalf"
            :disabled="scope.disabled"
            :max="scope.column.max"
          ></el-rate>
        </div>
      </template>
      <!-- 自定义文本 -->
      <template
        v-for="(textItem, textIndex) in textOption"
        slot-scope="scope"
        :slot="textItem.prop"
      >
        <div class="form-custom-text" :class="scope.column.class" :key="textIndex">
          <div class="custon-text" :style="scope.column.styles">{{ scope.column.textValue }}</div>
        </div>
      </template>
      <!-- 自定义分隔符 -->
      <template
        v-for="(separatorItem, separatorIndex) in separatorOption"
        slot-scope="scope"
        :slot="separatorItem.prop"
      >
        <div
          class="form-custom-separator"
          :class="scope.column.class"
          :style="scope.column.style"
          :key="separatorIndex"
        >
          <el-divider
            v-if="scope.column.direction != 'empty'"
            :content-position="scope.column.contentPosition"
            :direction="scope.column.direction"
          >
            <i v-if="scope.column.textIcon" :class="scope.column.textIcon"></i>
            {{ scope.column.textValue }}
          </el-divider>
          <div v-else style="height: 25px"></div>
        </div>
      </template>
      <!-- 自定义按钮 -->
      <template
        v-for="(buttonItem, buttonIndex) in buttonOption"
        slot-scope="scope"
        :slot="buttonItem.prop"
      >
        <div class="form-custom-button" :class="scope.column.class" :key="buttonIndex">
          <el-button
            :size="scope.size"
            :type="scope.column.buttonType"
            :plain="scope.column.plain"
            :round="scope.column.round"
            :circle="scope.column.circle"
            :disabled="scope.disabled"
            :icon="scope.column.buttonIcon"
            @click="customButtonFun(scope.column.clickFun)"
          >{{ scope.column.buttonText }}</el-button>
        </div>
      </template>
      <!-- 自定义用户 -->
      <template
        v-for="(userItem, userIndex) in userOption"
        :slot="userItem.prop"
        slot-scope="scope"
      >
        <user-control
          :style="scope.column.style"
          :class="scope.column.class"
          :key="userIndex"
          :tableItemVal="scope.value"
          :tableItemName="scope.column.prop"
          :disabled="scope.disabled"
          :tableItemScope="scope"
          :multiple="scope.column.params.multiple"
          @set-form-val="setFormValue"
          :allDepart="allDepartData"
          :allUserObj="allUserData"
        ></user-control>
      </template>
      <!-- 自定义部门 -->
      <template
        v-for="(departItem, departIndex) in departOption"
        :slot="departItem.prop"
        slot-scope="scope"
      >
        <depart-control
          :style="scope.column.style"
          :class="scope.column.class"
          :key="departIndex"
          :tableItemVal="scope.value"
          :tableItemName="scope.column.prop"
          :disabled="scope.disabled"
          :tableItemScope="scope"
          :multiple="scope.column.params.multiple"
          @set-form-val="setFormValue"
        ></depart-control>
      </template>
      <!-- 自定义代码编辑器 -->
      <template
        v-for="(monacoEditorItem, monacoEditorIndex) in monacoEditorOption"
        :slot="monacoEditorItem.prop"
        slot-scope="scope"
      >
        <monaco-editor
          ref="monacoEditor"
          v-model="formData[monacoEditorItem.prop]"
          :isSetData="true"
          :keyIndex="monacoEditorIndex"
          :key="monacoEditorIndex"
          :language="monacoEditorItem.params.language"
          :height="monacoEditorItem.params.height"
        ></monaco-editor>
      </template>
      <!-- 自定义表格选择控件 -->
      <template
        v-for="(tableSelectItem, tableSelectIndex) in tableSelectOption"
        :slot="tableSelectItem.prop"
        slot-scope="scope"
      >
        <table-select-control
          :style="scope.column.style"
          :class="scope.column.class"
          :key="tableSelectIndex"
          :tableItemVal="scope.value"
          :tableItemName="scope.column.prop"
          :disabled="scope.disabled"
          :tableItemScope="scope"
          :setFormValueFun="setFormValue.bind(this)"
          v-bind="scope.column.params"
          :allDepart="allDepartData"
          :allUserObj="allUserData"
        ></table-select-control>
      </template>
      <!-- 自定义子表（table） -->
      <template
        v-for="(tableItem, tableIndex) in tableOption"
        :slot="tableItem.prop"
        slot-scope="scope"
      >
        <table-control
          v-show="scope.column.display"
          ref="tableControl"
          :key="tableIndex"
          :style="scope.column.style"
          :class="scope.column.class"
          :tableColumn="scope.column"
          :tableValue="scope.value"
          :formOpenType="formOpenType"
          :allExecuteRule="allExecuteRule"
          :getCurrPacDataTextFun="getCurrPacDataTextFun.bind(this)"
          :lazyLoadFun="lazyLoadFun.bind(this)"
          :allFormListData="allFormListData"
          :allDepart="allDepartData"
          :allUserObj="allUserData"
        ></table-control>
      </template>
      <!-- 自定义文件列表 -->
      <template
        v-for="(fileItem, fileIndex) in fileOption"
        :slot="fileItem.prop + 'Type'"
        slot-scope="scope"
      >
        <div
          :key="fileIndex"
          @click="downloadFile(scope.file.url, scope.file.name)"
          style="cursor: pointer"
        >
          <i class="el-icon-link"></i>
          <span style="flex: 1">
            {{
            formData["$Name" + fileItem.prop]
            ? formData["$Name" + fileItem.prop][scope.file.uid]
            : formData[fileItem.prop]
            }}
          </span>
          <i
            class="el-icon-close"
            v-if="!scope.disabled"
            @click.capture.stop="codeFileControlDelFun(fileItem.prop, scope)"
          ></i>
        </div>
      </template>
    </avue-form>
    <table-view
      ref="tableView"
      v-if="isTableView"
      :tableViewOptionData="tableViewOptionData"
      :beforeClose="tableViewBeforeCloseFun.bind(this)"
    ></table-view>
    <!-- 表格选择控件 -->
    <table-select
      ref="table_select"
      v-if="isTableSelectControl"
      :optionData="tableSelectControlOption"
      :selectControlFun="tableViewBeforeCloseFun.bind(this)"
    ></table-select>
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
import {
  analysisFunction,
  getCurrentDateFun,
  getStrDataFunction,
} from '@/research/util/myUtil.js'
import { cityObj } from '@/research/util/city'
import { apiRequestHead } from '@/config/url.js'
import { getDeptTree } from '@/api/system/dept'
import { getList, getAllList } from '@/api/system/user'
import form from '@/research/mixins/form'
import {
  addDataApi,
  editDataApi,
  uploadeFileApi,
  getDicTableData,
  getUploadeFileNameApi,
} from '@/api/research/codelist'
import DepartControl from '@/research/components/general-control/depart-control'
import UserControl from '@/research/components/general-control/user-control'
import TableSelectControl from '@/research/components/general-control/table-select-control.vue'
import FormControl from '@/research/components/form-custom/form-control'
import TableControl from '@/research/components/form-custom/table-control'
import TableView from '@/research/components/general-control/table-view.vue'
import TableSelect from '@/research/components/general-control/table-select.vue'
import FormView from '@/research/components/general-control/form-view.vue'
import MonacoEditor from '@/packages/utils/monaco-editor'
import { mapGetters, mapMutations } from 'vuex'
import Vue from 'vue'
export default {
  name: 'FormCustom',
  props: {
    formOption: {
      //表单配置
      tpye: Object,
      default: () => ({}),
    },
    isPreview: {
      //是否预览
      type: Boolean,
      default: false,
    },
    isDetailStyle: {
      type: Boolean,
      default: false,
    },
    formOpenType: {
      /*
      表单类型 add:新增 edit:编辑 view:查看
      流程：noButton、add_no
      add_router:路由配置跳转表单新增
      */
      type: String,
      default: 'add',
    },
    actionData: {
      type: Object,
    },
    //所有数据
    allFormListData: {
      type: Object,
    },
    //关闭dialog方法
    closeDialogForm: {
      type: Function,
    },
    //其他页面或控件传递的方法方法
    transmitFun: {
      type: Function,
    },
    // 流程提交方法
    flowSubmit: {
      type: Function,
    },
    //当前流程表单 唯一id
    flowResourceId: {
      type: String,
    },
    //表单设计绑定的表单开发id
    onlineFormId: {
      type: String,
    },
    //权限
    btnPermissions: {
      type: Object,
      default: () => ({
        clearBtn: true,
        cancelBtn: false,
        submitBtn: true,
      }),
    },
  },
  components: {
    DepartControl,
    UserControl,
    FormControl,
    TableControl,
    TableView,
    TableSelect,
    TableSelectControl,
    FormView,
    MonacoEditor,
  },
  mixins: [form],
  computed: {
    ...mapGetters(['provinces', 'userInfo']),
  },
  watch: {},
  data() {
    return {
      random: `${new Date().getTime()}${Math.floor(Math.random() * 10000)}`,
      getCurrentDateFun: getCurrentDateFun,
      loading: false,
      apiRequestHead: apiRequestHead,
      isClearCss: true,
      timer: null,
      isOptinsToLoad: false,
      isValueToload: false,
      optinsToLoad: false,
      valueToload: false,
      formData: {},
      defaultFormData: {}, //表单默认值
      option: {
        emptyBtn: false,
      },
      tabsOption: [], //tabs字段
      btnListOption: [], //按钮组
      rateOption: [], //评分字段
      separatorOption: [], //分隔符
      textOption: [], //文本
      buttonOption: [], //按钮
      userOption: [], //用户
      isUserControl: false, //是否有用户控件
      isDepartControl: false, //是否有部门控件
      departOption: [], //部门
      monacoEditorOption: [], //代码编辑器
      tableSelectOption: [], //表格选择
      initSelfDefinedArr: [], //已经注册的自定义组件
      tableOption: [], // 子表
      fileOption: [], //文件
      provincesOption: [], //省市区
      allDepartData: [], //所有的部门信息
      allUserData: {
        allList: [],
        list: [],
        total: 0,
      }, //所有的用户信息
      selectRemoteAll: {
        column: [],
        group: [],
      }, //需要远端数据的选择字段
      selectDicAll: {
        column: [],
        group: [],
      },
      allExecuteRule: {}, //所有的填值规则
      executeObj: {}, //执行
      jsEnhanceApi: {}, //js增强所有的api
      beforeSubmit: '', //表单提交前触发的函数
      disposeFormDataEnhance: '', //表单提交前触发数据处理增强
      submitFormDataEnhance: '', //表单提交数据成功后处理增强
      // 表单设计的路由配置
      routerFormCode: '',
      routerType: 'false',
      redirectsUrl: '',
      //表格弹窗控件
      isTableView: false,
      tableViewOptionData: {
        viewObj: {
          type: '',
          title: '',
          isShow: false,
          width: '80%',
        },
        tableId: '',
        hideHeader: true,
        searchObj: {},
      },
      //表格选择
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
      formDynamicFun: (type, data) => {
        
      },
      // 其他表单
      isFormControl: false,
      formControlData: {},
    }
  },
  async mounted() {
    this.loading = true
    let formOption = await this.transformToAvueOptions(this.formOption)
    let currOption = this.setFormOptionDataFun(formOption)
    //设置用户、部门相关数据
    if (this.isUserControl || this.isDepartControl) {
      this.setDepartAndUserDataFun()
    }
    this.option = {
      ...currOption,
      ...this.option,
    }
    if (this.btnPermissions.submitBtn === false) {
      this.option.submitBtn = false
    }
    if (this.formOpenType == 'edit') {
      this.option.submitText = '修改'
    }
    if (['view', 'noButton', 'add_no'].includes(this.formOpenType)) {
      this.option.menuBtn = false
    }
    if (this.formOption.jsIncidentEnhanceStr) {
      try {
        this.setJsEnhanceFun(
          this.formOption.jsIncidentEnhanceStr,
          'initEnhance'
        )
      } catch (error) {
        console.warn(error)
      }
    }
    if (['add', 'add_no'].includes(this.formOpenType)) {
      setTimeout(() => {
        //延迟配置默认值失效，重新设置默认值
        this.$refs.form.dataFormat()
      }, 0)
    }
    this.optinsToLoad = true
    //获取选择字段远端数据
    this.setRemoteDataDicFun()
    //只有新增才会执行远程取值
    if (['add', 'add_no'].includes(this.formOpenType)) {
      this.getApiDataFun()
    } else {
      this.valueToload = true
    }
    //css增强
    if (this.formOption.cssEnhanceStr) {
      this.loadStyleString(this.formOption.cssEnhanceStr)
    }
    if (this.formOption.cssEnhanceUrl) {
      let res = await this.mixinExternalEnhance(this.formOption.cssEnhanceUrl)
      this.setJsEnhanceFun(res, 'external')
    }
    if (
      ['edit', 'view', 'noButton', 'add_router'].includes(this.formOpenType)
    ) {
      this.setCurrentFormDataFun()
    }
    if (['add', 'add_no'].includes(this.formOpenType)) {
      setTimeout(() => {
        if (this.allFormListData) {
          this.$refs.form.setForm(this.allFormListData)
        }
      }, 0)
    }
    setTimeout(() => {
      this.setCustomText()
      this.setBorderHideFun()
      this.getFileNameFun()
      //判断组件初始化是否完毕
      this.timer = setInterval(async () => {
        let valueToload = true
        let optinsToLoad = true
        //获取表单数据  表单配置
        if (this.$refs.formControl) {
          this.$refs.formControl.forEach((item) => {
            if (!item.valueToload) {
              valueToload = false
            }
            if (!item.optinsToLoad) {
              optinsToLoad = false
            }
          })
        }
        if (this.$refs.tableControl) {
          this.$refs.tableControl.forEach((item) => {
            if (!item.valueToload) {
              valueToload = false
            }
            if (!item.optinsToLoad) {
              optinsToLoad = false
            }
          })
        }
        if (this.formOpenType != 'add') {
          this.valueToload = true
        }
        if (valueToload && this.valueToload && !this.isValueToload) {
          this.isValueToload = true
          this.executeAllRuleFun()
        }
        if (optinsToLoad && this.optinsToLoad && !this.isOptinsToLoad) {
          this.isOptinsToLoad = true
          this.setJsEnhanceFun(this.formOption.jsEnhanceStr)
          if (this.formOption.jsEnhanceUrl) {
            let res = await this.mixinExternalEnhance(
              this.formOption.jsEnhanceUrl
            )
            this.setJsEnhanceFun(res, 'external')
          }
        }
        if (this.isValueToload && this.isOptinsToLoad) {
          if (this && this.$refs.form && this.$refs.form.clearValidate) {
            this.$refs.form.clearValidate()
          }
          clearInterval(this.timer)
        }
        this.loading = false
      }, 1000)
    }, 0)
  },
  methods: {
    ...mapMutations(['SET_PROVINCES']),
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
    //监听文件上传
    uploadAfter(res, done, loading, column) {
      if (column.uploadType == 'file') {
        if (this.formData['$Name' + column.prop] instanceof Array) {
          this.formData['$Name' + column.prop].push(res.originalName)
        } else {
          this.formData['$Name' + column.prop] = [res.originalName]
        }
      }
      done()
    },
    codeFileControlDelFun(fileName, obj) {
      let arr = []
      if (this.formData[fileName] instanceof Array) {
        arr = this.formData[fileName]
      } else {
        arr = this.formData[fileName].split(',')
      }
      let fileStr = arr.filter((item, index) => {
        if (item == obj.file.url) {
          this.formData['$Name' + fileName] = this.formData[
            '$Name' + fileName
          ].filter((item, i) => index != i)
          return false
        }
        return true
      })
      fileStr.join(',')
      this.formData[fileName] = fileStr.join(',')
    },
    //初始化文件名
    getFileNameFun() {
      let fileArr = []
      if (this.fileOption.length > 0) {
        this.fileOption.forEach((item) => {
          fileArr.push(item.prop)
        })
      }
      //处理文件名
      if (fileArr.length > 0) {
        fileArr.forEach((fileItem) => {
          if (
            this.formData[fileItem] != '' &&
            this.formData[fileItem] != undefined
          ) {
            this.formData['$Name' + fileItem] = []
            this.formData[fileItem].split(',').forEach(async (resItem) => {
              let fileRes = await getUploadeFileNameApi(resItem)
              let fileName = resItem.split('/')
              fileName = fileName[fileName.length - 1]
              if (fileRes.data.success && fileRes.data.data) {
                fileName = fileRes.data.data
              }
              let fileNameArr = [...this.formData['$Name' + fileItem], fileName]
              this.formData = {
                ...this.formData,
                ['$Name' + fileItem]: fileNameArr,
              }
            })
          }
        })
      }
    },
    cancelBtnFun() {
      this.closeDialogForm()
    },
    //设置隐藏边框
    setBorderHideFun() {
      let hideDom = document.querySelectorAll(
        '.avue-form-work-style .el-collapse-item__content .form-border-hide'
      )
      if (hideDom.length > 0) {
        hideDom.forEach((item) => {
          item.style.height = '33px'
          let itemDom = item.parentNode.parentNode.parentNode
          if (itemDom.className.indexOf('el-form-item') != -1) {
            itemDom.style.border = '0 solid #000'
          }
        })
      }

      let leftDom = document.querySelectorAll(
        '.avue-form-work-style .el-collapse-item__content .form-border-left-show'
      )
      if (leftDom.length > 0) {
        leftDom.forEach((item) => {
          let itemDom = item.parentNode.parentNode.parentNode
          if (itemDom.className.indexOf('el-form-item') != -1) {
            itemDom.style.borderLeft = '1px solid #000'
          }
        })
      }

      let bottomDom = document.querySelectorAll(
        '.avue-form-work-style .el-collapse-item__content .form-border-bottom-show'
      )
      if (bottomDom.length > 0) {
        bottomDom.forEach((item) => {
          let itemDom = item.parentNode.parentNode.parentNode
          if (itemDom.className.indexOf('el-form-item') != -1) {
            itemDom.style.borderBottom = '1px solid #000'
          }
        })
      }

      let topDom = document.querySelectorAll(
        '.avue-form-work-style .el-collapse-item__content .form-border-top-show'
      )
      if (topDom.length > 0) {
        topDom.forEach((item) => {
          let itemDom = item.parentNode.parentNode.parentNode
          if (itemDom.className.indexOf('el-form-item') != -1) {
            itemDom.style.borderTop = '1px solid #000'
          }
        })
      }

      let leftDomHide = document.querySelectorAll(
        '.avue-form-work-style .el-collapse-item__content .form-border-left-hide'
      )
      if (leftDomHide.length > 0) {
        leftDomHide.forEach((item) => {
          let itemDom = item.parentNode.parentNode.parentNode
          if (itemDom.className.indexOf('el-form-item') != -1) {
            itemDom.style.borderLeft = '0px solid #000'
          }
        })
      }

      let bottomDomHide = document.querySelectorAll(
        '.avue-form-work-style .el-collapse-item__content .form-border-bottom-hide'
      )
      if (bottomDomHide.length > 0) {
        bottomDomHide.forEach((item) => {
          let itemDom = item.parentNode.parentNode.parentNode
          if (itemDom.className.indexOf('el-form-item') != -1) {
            itemDom.style.borderBottom = '0px solid #000'
          }
        })
      }

      let topDomHide = document.querySelectorAll(
        '.avue-form-work-style .el-collapse-item__content .form-border-top-hide'
      )
      if (topDomHide.length > 0) {
        topDomHide.forEach((item) => {
          let itemDom = item.parentNode.parentNode.parentNode
          if (itemDom.className.indexOf('el-form-item') != -1) {
            itemDom.style.borderTop = '0px solid #000'
          }
        })
      }
    },
    //打印
    opentPrinterFun() {
      this.$Print(this.$refs.form)
    },
    //触发表单提交前的方法
    triggerBeforeSubmit() {
      return new Promise(async (resolve) => {
        try {
          if (this.beforeSubmit !== '') {
            this.beforeSubmit()
              .then(() => {
                resolve({ success: true })
              })
              .catch((err) => {
                resolve({ success: false, msg: err })
              })
          } else {
            resolve({ success: true })
          }
        } catch (error) {
          resolve({ success: true })
          console.warn('表单提交前触发方法错误', error)
        }
      })
    },
    //设置当前表单数据
    setCurrentFormDataFun() {
      let allProp = []
      if (this.option.column) {
        this.option.column.forEach((item) => {
          if (item.type != 'table') {
            allProp.push(item.prop)
          }
        })
      }
      if (this.option.group) {
        this.option.group.forEach((item) => {
          item.column.forEach((col) => {
            if (col.type != 'table' && col.type != 'tabs') {
              allProp.push(col.prop)
            }
          })
        })
      }

      let formData = {}
      if (this.formOpenType == 'add_router') {
        formData = this.allFormListData
      } else {
        allProp.forEach((item) => {
          if (this.allFormListData) {
            formData[item] =
              this.allFormListData[item] == undefined
                ? ''
                : this.allFormListData[item]
          }
        })
      }

      this.$refs.form.setForm(formData)
      
    },
    //省市区懒加载方法
    lazyLoadFun(node, resolve, type) {
      if (!this.provinces.province) {
        this.SET_PROVINCES({
          ...cityObj,
        })
      }
      let level = node.level
      let data = node.data || {}
      let area_id = data.area_id
      let list = []
      let callback = () => {
        resolve(
          (list || []).map((ele) => {
            if ((type == 1 && level == 1) || (type == 2 && level == 0)) {
              return Object.assign(ele, {
                leaf: true,
              })
            } else {
              return Object.assign(ele, {
                leaf: ele.leaf,
              })
            }
          })
        )
      }
      if (level == 0) {
        list = this.provinces.province
        callback()
      } else if (level == 1) {
        list = this.provinces.city[area_id]
        callback()
      } else if (level == 2) {
        list = this.provinces.district[area_id]
        callback()
      }
    },
    //初始化树控件/联集文本
    setCustomText() {
      if (this.provincesOption && this.provincesOption.length > 0) {
        this.provincesOption.forEach((item) => {
          this.setProvincesTextFun(this.formData[item], item)
        })
      }
    },
    //修改省市区文本方法
    setProvincesTextFun(value, prop) {
      let text = this.getCurrPacDataTextFun(value)
      let dom = document.querySelector(`.form-custom label[for=${prop}]`)
      if (dom) {
        dom.parentNode.querySelector('input').value = text ? text : ''
      } else {
        // 处理字表省市区文本
        let dom = document.querySelector(
          `.form-custom-control-provinces__${prop}`
        )
        dom = dom.parentNode.parentNode.parentNode.parentNode.querySelector(
          '.el-form-item__content .el-input input'
        )
        if (dom) {
          dom.value = text
        }
      }
    },
    //获取当前省市区数据文本
    getCurrPacDataTextFun(key) {
      if (!key) {
        return ''
      }
      let value = key instanceof Array ? key : key.split(',')
      let strArr = []
      value.forEach((item, index) => {
        if (
          index == 0 &&
          this.provinces.provinceData &&
          this.provinces.provinceData[item]
        ) {
          strArr.push(this.provinces.provinceData[item].area_name)
        }
        if (
          index == 1 &&
          this.provinces.cityData &&
          this.provinces.cityData[item]
        ) {
          strArr.push(this.provinces.cityData[item].area_name)
        }
        if (
          index == 2 &&
          this.provinces.districtData &&
          this.provinces.districtData[item]
        ) {
          strArr.push(this.provinces.districtData[item].area_name)
        }
      })
      return strArr.join(' / ')
    },
    //清空所有数据
    clearAllDataFun() {
      
      this.$refs.form.clearValidate()
      this.$refs.form.resetForm()
      if (this.$refs.formControl) {
        this.$refs.formControl.forEach((item) => {
          item.clearAllDataFun()
        })
      }
      if (this.$refs.tableControl) {
        this.$refs.tableControl.forEach((item) => {
          item.clearAllDataFun()
        })
      }
      this.option.column.forEach((item) => {
        if (item.formCustomType && item.formCustomType == 'provinces') {
          this.setProvincesTextFun('', item.prop)
        }
      })
    },
    // 表单设计器配置项 转化为 Avue配置项
    transformToAvueOptions(obj) {
      return new Promise((resolve, reject) => {
        try {
          const data = this.deepClone(obj)
          if (data.column) {
            for (let i = 0; i < data.column.length; i++) {
              const col = data.column[i]
              if (
                col.type == 'dynamic' &&
                col.children &&
                col.children.column &&
                col.children.column.length > 0
              ) {
                const c = col.children.column
                c.forEach((item) => {
                  delete item.subfield
                })
                this.transformToAvueOptions(col.children).then((res) => {
                  col.children = res
                })
              } else if (col.type == 'group') {
                if (!data.group) data.group = []

                const group = {
                  label: col.label,
                  icon: col.icon,
                  prop: col.prop,
                  arrow: col.arrow,
                  collapse: col.collapse,
                  display: col.display,
                }
                this.transformToAvueOptions(col.children).then((res) => {
                  group.column = res.column
                  data.group.push(group)
                })
                data.column.splice(i, 1)
                i--
              } else if (
                ['checkbox', 'radio', 'tree', 'cascader', 'select'].includes(
                  col.type
                )
              ) {
                delete col.dicOption
              }
              if (col.change) col.change = eval(col.change)
              else delete col.change

              if (col.click) col.click = eval(col.click)
              else delete col.click

              if (col.focus) col.focus = eval(col.focus)
              else delete col.focus

              if (col.blur) col.blur = eval(col.blur)
              else delete col.blur
            }
          }
          resolve(data)
        } catch (e) {
          reject(e)
        }
      })
    },
    //处理表单设计器配置数据
    setFormOptionDataFun(option) {
      let optinos = this.deepClone(option)
      //column处理
      if (optinos.column == undefined) {
        optinos.column = []
      } else {
        optinos.column = [
          ...optinos.column.map((item) => {
            item = this.setOptionCloumnFun(
              item,
              'column',
              optinos.formStyle == 'work',
              optinos
            )
            return item
          }),
        ]
      }

      if (optinos.group) {
        optinos.group.forEach((item, index) => {
          optinos.group[index].column = optinos.group[index].column.map(
            (item) => {
              item = this.setOptionCloumnFun(
                item,
                'group',
                optinos.formStyle == 'work',
                optinos
              )
              return item
            }
          )
        })
      }
      return optinos
    },
    //数据处理方法
    setOptionCloumnFun(item, type, isWork, optinos) {
      if (optinos.labelWidth && item.labelWidth === undefined) {
        item.labelWidth = optinos.labelWidth
      }
      if (isWork) {
        item.placeholder = ' '
      }
      if (item.type == 'separator') {
        item.textValue = item.value
      }
      if (['view', 'noButton'].includes(this.formOpenType)) {
        item.disabled = true
      }
      //清除长度限制
      if (
        (item.isMaxLength !== undefined && item.isMaxLength === false) ||
        (item.isMaxLength !== true && item.maxlength === 0)
      ) {
        delete item.maxlength
      }

      //tabs
      if (item.type == 'tabs') {
        item.label = ''
        item.labelWidth = 0
        item.tabsValue = item.children.column[0].prop
        this.tabsOption.push(item)
      }
      if (item.type == 'btn-list') {
        this.btnListOption.push(item)
      }
      //评分
      if (item.type == 'rate') {
        this.rateOption.push(item)
      }
      //文本
      if (item.type == 'title') {
        this.textOption.push(item)
      }
      //按钮
      if (item.type == 'button') {
        this.buttonOption.push(item)
      }
      //分隔符
      if (item.type == 'separator') {
        this.separatorOption.push(item)
      }
      //用户
      if (item.type == 'user') {
        this.isUserControl = true
        this.userOption.push(item)
      }
      //部门
      if (item.type == 'depart') {
        this.isDepartControl = true
        this.departOption.push(item)
      }
      //表格选择
      if (item.type == 'table-select') {
        this.tableSelectOption.push(item)
      }
      //自定义控件
      if (item.type == 'self-defined') {
        if (typeof item.params == 'string') {
          item.params = getStrDataFunction(item.params)
        }
        if (!this.initSelfDefinedArr.includes(item.component)) {
          try {
            Vue.component(item.component, (res) =>
              require([`@/${item.componentPath}`], res)
            )
            this.initSelfDefinedArr.push(item.component)
          } catch (error) {
            console.warn(`${item.component}自定义组件注册异常,${error}`)
          }
        }
      }
      //代码编辑器
      if (item.type == 'monaco-editor') {
        this.monacoEditorOption.push(item)
      }
      //子表
      if (item.type == 'table') {
        item.isWork = isWork
        if (item.jsEnhanceFun) {
          try {
            let jsStr = `function jsEnhanceFun(that,parentThat){${item.jsEnhanceFun}}`
            item.jsEnhanceFun = analysisFunction(jsStr)
            item.getParentFun = () => {
              return this
            }
            if (!item.jsEnhanceFun) {
              throw new Error()
            }
          } catch (error) {
            console.warn(`子表《${item.prop}》初始化之前js增强解析错误`)
          }
        }
        if (item.assigJsEnhance) {
          try {
            let assigJsStr = `function jsEnhanceFun(that,parentThat){${item.assigJsEnhance}}`
            item.assigJsEnhance = analysisFunction(assigJsStr)
            if (!item.getParentFun) {
              item.getParentFun = () => {
                return this
              }
            }
            if (!item.assigJsEnhance) {
              throw new Error()
            }
          } catch (error) {
            console.warn(`子表《${item.prop}》赋值后js增强解析错误`)
          }
        }
        this.tableOption.push(item)
        this.findUserAndDepartFun(item.children.column)
      }
      //省市区联动
      if (item.type == 'provinces') {
        item.type = 'cascader'
        item.lazyLoad = (node, resolve) =>
          this.lazyLoadFun(node, resolve, item.provType)
        item.formCustomType = 'provinces'
        item.class =
          `form-custom-control-provinces__${item.prop}` + ' ' + item.class

        this.provincesOption.push(item.prop)
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
      //文件上传
      if (item.uploadType == 'file') {
        
        this.fileOption.push(item)
      }
      //图片上传
      if (item.uploadType == 'img' && item.limit == 1) {
        item.listType = 'picture-img'
        delete item.limit
      }
      //对宽度进行拼接
      if (item.style && item.style.width) {
        item.style.width = item.style.width + ' !important'
      }
      //需要把数组处理成字符串的数据
      if (item.type == 'select' && item.multiple) {
        item.dataType = 'string'
      }
      if (item.type == 'slider' && item.range) {
        item.dataType = 'string'
      }
      if (
        ['checkbox', 'user', 'depart', 'upload', 'cascader'].includes(item.type)
      ) {
        item.dataType = 'string'
      }
      if (item.type == 'upload') {
        item.action = item.action.replace('apiRequestHead', this.apiRequestHead)
      }
      //对MarkDown组件赋上传图片方法
      if (item.component == 'mavon-editor') {
        item.event = {
          imgAdd: (pos, $file) => {
            
            const loading = this.$loading({
              lock: true,
              text: '正在上传图片，请耐心等待一会~',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            var formdata = new FormData()
            formdata.append('file', $file)
            formdata.append('type', 0)
            uploadeFileApi(formdata)
              .then((res) => {
                let url = res.data.result.data.lj
                this.$refs.form
                  .getPropRef(item.prop)
                  .$refs.temp.$img2Url(pos, url)
                loading.close()
              })
              .catch(() => {
                this.$message.error('上传图片失败，请重新上传~')
                loading.close()
              })
          },
        }
      }
      //提取需要远端数据的选择字段
      if (['select', 'checkbox', 'radio'].includes(item.type)) {
        if (item.oldDicOption == 'remote') {
          item.dicData = []
          if (type == 'column') {
            this.selectRemoteAll.column.push(item.prop)
          }
          if (type == 'group') {
            this.selectRemoteAll.group.push(item.prop)
          }
        }
        if (item.oldDicOption == 'dic') {
          if (type == 'column') {
            this.selectDicAll.column.push(item.prop)
          }
          if (type == 'group') {
            this.selectDicAll.group.push(item.prop)
          }
        }
      }
      if (item.type == 'switch') {
        item.dicData = [
          {
            label: '', //关闭
            value: item.inactiveText ? item.inactiveText : '0',
          },
          {
            label: '', //开启
            value: item.activeText ? item.activeText : '1',
          },
        ]
      }
      //处理一开始执行校验问题
      if (item.rules && item.rules.length > 0) {
        item.rules = item.rules.map((rulesItem) => {
          if (!rulesItem.trigger) {
            rulesItem.trigger = 'blur'
          }
          return rulesItem
        })
      }
      //默认字段事件
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
    },
    findUserAndDepartFun(column) {
      column.forEach((item) => {
        if (item.type == 'user') {
          this.isUserControl = true
        }
        if (item.type == 'depart') {
          this.isDepartControl = true
        }
      })
    },
    //设置用户部门 数据
    setDepartAndUserDataFun() {
      if (this.isUserControl) {
        getDeptTree().then((res) => {
          let data = res.data.data
          this.allDepartData = data
        })
        getAllList().then((res) => {
          this.allUserData.allList = res.data.data
        })
        getList(1, 10, {}, '').then((res) => {
          let data = res.data.data
          this.allUserData.list = data.records
          this.allUserData.total = data.total
        })
      } else {
        getDeptTree().then((res) => {
          let data = res.data.data
          this.allDepartData = data
        })
      }
    },
    //远程取值方法
    async getApiDataFun() {
      let apiColumn = []
      if (this.option.column) {
        apiColumn = [...apiColumn, ...this.option.column]
      }
      if (this.option.group) {
        this.option.group.forEach((item) => {
          apiColumn = [...apiColumn, ...item.column]
        })
      }
      let formData = await this.mixinGetApiData(apiColumn)
      
      for (let key in formData.formObj) {
        if (formData.formObj[key] instanceof Array) {
          formData.formObj[key] = formData.formObj[key].join(',')
        }
      }
      this.formData = {
        ...this.formData,
        ...formData.formObj,
      }
      for (let key in formData.specialObj) {
        if (formData.specialObj[key].type == 'title') {
          let column = null
          let group = null
          if (this.option.column) {
            column = this.findObject(this.option.column, key)
          }
          if (this.option.group) {
            this.option.group.forEach((item, index) => {
              group = this.findObject(this.option.group[index].column, key)
            })
          }
          if (column && column != -1) {
            column.textValue = formData.specialObj[key].data
          } else {
            group.textValue = formData.specialObj[key].data
          }
        }
      }
      this.valueToload = true
    },
    //选择字段远端数据和数据字典处理逻辑
    setRemoteDataDicFun() {
      //远端数据
      if (
        this.selectRemoteAll.column.length > 0 ||
        this.selectRemoteAll.group.length > 0
      ) {
        this.selectRemoteAll.column.forEach(async (item) => {
          let column = this.findObject(this.option.column, item)
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
        this.selectRemoteAll.group.forEach(async (item) => {
          if (this.option.group) {
            this.option.group.forEach(async (groupItem, index) => {
              let group = this.findObject(this.option.group[index].column, item)
              if (group.dicUrl) {
                let dicData = await this.mixinGetSelectRemoteData(
                  group.dicUrl,
                  group.dicDataFormat
                )
                if (group.excludeStr) {
                  let excludeArr = group.excludeStr.split(',')
                  dicData = dicData.filter((item) => {
                    if (excludeArr.includes(item.value)) {
                      return false
                    }
                    return true
                  })
                }
                group.dicData = dicData
                if (group.isOneDefaultValue && dicData.length > 0) {
                  group.value = dicData[0].id
                }
              }
            })
          }
        })
      }
      //字典逻辑
      if (
        this.selectDicAll.column.length > 0 ||
        this.selectDicAll.group.length > 0
      ) {
        this.selectDicAll.column.forEach(async (item) => {
          let column = this.findObject(this.option.column, item)
          if (column && column.queryFormName) {
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
        this.selectDicAll.group.forEach((item) => {
          if (this.option.group) {
            this.option.group.forEach(async (groupItem, index) => {
              let group = this.findObject(this.option.group[index].column, item)
              if (group && group.queryFormName) {
                let dicRes = await getDicTableData(group.queryFormName)
                if (dicRes.data.success) {
                  if (group.excludeStr) {
                    let excludeArr = group.excludeStr.split(',')
                    dicRes.data.data = dicRes.data.data.filter((item) => {
                      if (excludeArr.includes(item.value)) {
                        return false
                      }
                      return true
                    })
                  }
                  group.dicData = dicRes.data.data
                } else {
                  group.dicData = []
                }
              }
            })
          }
        })
      }
    },
    //保存
    formHandleSubmitFun(form, done) {
      return new Promise(async (resolve) => {
        let submitRes = await this.triggerBeforeSubmit()
        if (submitRes.success == false) {
          let msg = submitRes.msg ? submitRes.msg : '提交失败，参数不符合条件'
          if (submitRes.msg != false) {
            this.$message.error(msg)
          }
          done()
          return false
        }

        form = {
          ...this.allFormListData,
          ...form,
          ...this.formData,
        }
        let promiseArr = []
        if (this.$refs.formControl) {
          this.$refs.formControl.forEach((item) => {
            promiseArr.push(item.getFormData())
          })
        }
        if (this.$refs.tableControl) {
          this.$refs.tableControl.forEach((item) => {
            promiseArr.push(item.getTableData())
          })
        }
        let formDataArr = await Promise.all(promiseArr)
        
        let isCheckFailure = false //是否校验失败
        let tabsIndexArr = []
        formDataArr.forEach((item) => {
          if (!item.res) {
            isCheckFailure = true
            if (item.tabsValue && item.tabsProp) {
              //切换到校验失败的tab
              let tabsItem = this.findObject(this.tabsOption, item.tabsProp)
              let index = this.findArray(this.tabsOption, item.tabsProp, 'prop')
              if (tabsIndexArr.includes(index)) {
                return false
              }
              tabsItem.tabsValue = item.tabsValue
              tabsIndexArr.push(index)
            }
          }
          //整合所有数据
          if (item.prop) {
            form = {
              ...form,
              [item.prop]: item.data,
            }
          } else {
            form = {
              ...form,
              ...item.data,
            }
          }
        })
        for (let key in form) {
          if (form[key] == '[]') {
            form[key] = ''
          }
        }
        if (isCheckFailure) {
          if (this.actionData && this.actionData.type == 'flow') {
            this.$message('请完善信息~')
          }
          done()
          return false
        }
        if (this.disposeFormDataEnhance) {
          form = await this.disposeFormDataEnhance(form)
        }
        // 预览
        if (this.isPreview) {
          this.$alert(form, '表单数据', {
            confirmButtonText: '确定',
            customClass: 'form-custom-preview-form-data-alert',
          })
          if (this.submitFormDataEnhance) {
            await this.submitFormDataEnhance(form)
          }
          done()
        }
        //单独修改表单设计数据
        if (this.actionData && this.actionData.type == 'onlineEdit') {
          let data = {
            ...form,
            id: this.allFormListData.id,
          }
          
          try {
            await editDataApi(this.onlineFormId, data)
          } catch (error) {
            done()
            return false
          }
          if (this.submitFormDataEnhance) {
            await this.submitFormDataEnhance(form, data.id)
          }
          if (this.actionData.isMessage) {
            this.$message({
              message: '修改成功',
              type: 'success',
            })
          }
          if (this.actionData.closeType) {
            this.closeDialogForm(this.actionData.closeType)
          } else {
            this.closeDialogForm()
          }
          done()
        }
        //单独保存表单设计数据
        if (this.actionData && this.actionData.type == 'onlineAdd') {
          let data = {
            ...form,
          }
          let codeListDataId = ''
          try {
            let resData = await addDataApi(this.onlineFormId, data)
            codeListDataId = resData.data.data
          } catch (error) {
            done()
            return false
          }
          if (this.submitFormDataEnhance) {
            await this.submitFormDataEnhance(form, codeListDataId)
          }
          if (this.actionData.isMessage) {
            this.$message({
              message: '保存成功',
              type: 'success',
            })
          }
          if (this.actionData.closeType) {
            this.closeDialogForm(this.actionData.closeType)
          } else {
            this.closeDialogForm()
          }
          done()
        }
        //只返回数据不做任何处理
        if (this.actionData && this.actionData.type == 'returnData') {
          let data = {
            ...form,
          }
          if (this.formOpenType == 'edit') {
            data = {
              ...data,
              id: this.allFormListData.id,
            }
          }
          let idKey = this.onlineFormId ? this.onlineFormId : 'data'
          this.$refs.form.validate((valid, done) => {
            done()
            let bool = true
            if (!valid) {
              bool = false
            }
            console.log({
              valid: bool,
              [idKey]: data,
            })
            resolve({
              valid: bool,
              [idKey]: data,
              dataKey: idKey,
            })
          })
        }
        //只调用关闭方法做不处理
        if (this.actionData && this.actionData.type == 'callClose') {
          this.closeDialogForm()
        }
        //只调用关闭方法传递表单数据
        if (this.actionData && this.actionData.type == 'callCloseData') {
          this.$refs.form.validate((valid, done) => {
            if (!valid) {
              done()
              return false
            } else {
              this.closeDialogForm(done, form)
            }
          })
        }
      })
    },
    //按钮绑定方法
    customButtonFun(funText) {
      this.setJsEnhanceFun(funText, 'button')
    },
    //设置表单值{fieldName:'',value:''}
    setFormValue(obj) {
      if (obj.value instanceof Array) {
        obj.value = obj.value.join(',')
      }
      this.formData[obj.fieldName] = obj.value
    },
    //tabs切换事件
    setTabsSwitchFun(tab, prop) {
      let tabsItem = this.findObject(this.tabsOption, prop)
      
      tabsItem.tabsValue = tab.name
      if (tabsItem.tabClick) {
        tabsItem.tabClick(tab)
      }
    },
    //执行填值规则
    async executeAllRuleFun() {
      let dataObj = this.getFormAllConfigAsDataAsControlFun()
      //执行填值规则
      let res = await this.mixinGetExecuteRule(
        dataObj.columnData,
        dataObj.formData
      )
      if (res) {
        this.allExecuteRule = res
        this.setFormExecuteRuleFun(res)
        dataObj.controlArr.forEach((item) => {
          item.setFormExecuteRuleFun(res)
        })
      }
    },
    //设置填值规则的值
    setFormExecuteRuleFun(rule) {
      let column = []
      if (this.option.column) {
        column = [...column, ...this.option.column]
      }
      if (this.option.group) {
        this.option.group.forEach((item) => {
          column = [...column, ...item.column]
        })
      }
      let formData = {}
      column.forEach((item) => {
        if (item.fillRuleCode) {
          formData[item.prop] = rule[item.fillRuleCode]
        }
      })
      this.formData = {
        ...this.formData,
        ...formData,
      }
    },
    //获取表单所有的配置、数据  组件
    getFormAllConfigAsDataAsControlFun() {
      let formData = {
        ...this.formData,
      }
      let columnData = [...this.option.column]
      if (this.option.group) {
        this.option.group.forEach((item) => {
          columnData = [...columnData, ...item.column]
        })
      }
      let controlArr = []
      //获取表单数据  表单配置
      if (this.$refs.formControl) {
        this.$refs.formControl.forEach((item) => {
          controlArr.push(item)
          columnData = [...columnData, ...item.getFormColumnData()]
          formData = {
            ...formData,
            ...item.getFormDataNullVerify(),
          }
        })
      }
      if (this.$refs.tableControl) {
        this.$refs.tableControl.forEach((item) => {
          controlArr.push(item)
          columnData = [...columnData, ...item.tableOption.column]
          formData = {
            ...formData,
            ...item.tableDataItemDefault,
            [item.tableProp]: item.tableData,
          }
        })
      }
      for (let key in formData) {
        if (formData[key] === undefined) {
          formData[key] = ''
        }
      }
      return {
        formData,
        columnData,
        controlArr,
      }
    },
    //js增强处理
    setJsEnhanceFun(jsStr, type) {
      if (!jsStr) {
        return false
      }
      jsStr = `function jsEnhanceFun(that,api){${jsStr}}`
      let fun = analysisFunction(jsStr)
      this.jsEnhanceApi = {
        getFormData: (key) => this.getFormDataFun(key), //获取form表单的值，如果 key 为空，则返回所有的Data,如果 key 为数组，则返回Data对象
        setFormData: (key, value, bool = false) =>
          this.setFormDataFun(key, value, bool), //设置form表单的值
        setFormOptions: (key, optionsKey, optionsValue) =>
          this.setFormOptionsFun(key, optionsKey, optionsValue), //设置字段的配置key：数据绑定Key   optionsKey:配置key  optionsValue:配置值 例：setFormOptions('select_1','dicData',[{label: '男',value: '1'} ,{label: '女',value: '2'}])
        setFormMoreOptions: (key, options, other) =>
          this.setFormMoreOptionsFun(key, options, other),
        show: (key) => this.setFormControlStateFun(key, 'show'), //显示一个或多个组件 例： 'input_1' / ['input_1','input_2']
        hide: (key) => this.setFormControlStateFun(key, 'hide'), // 隐藏一个或多个组件
        watch: (watchItems, bool) => this.setWatchFun(watchItems, bool), //监听key值的变化  子表暂不支持监听
        get: (url, parameter, config) =>
          this.mixinRequestData(url, parameter, 'get', false, config), //发送Get请求 可以是http(s)协议的绝对地址，也可以是相对于后台的地址（以/开头）。
        post: (url, parameter, config) =>
          this.mixinRequestData(url, parameter, 'post', false, config), // 发送Post请求
        put: (url, parameter, config) =>
          this.mixinRequestData(url, parameter, 'put', false, config), //发送Put请求
        delete: (url, parameter, config) =>
          this.mixinRequestData(url, parameter, 'delete', false, config), //发送Put请求
        request: (url, parameter, method) =>
          this.mixinRequestData(url, parameter, method, 'request'), // 发送请求
        executeAllFillRule: () => this.executeAllRuleFun(), // 重新执行所有的填值规则
        //表单提交前触发方法
        beforeSubmit: (fun) => {
          this.beforeSubmit = fun
        },
        disposeFormDataEnhance: (fun) => {
          this.disposeFormDataEnhance = fun
        },
        submitFormDataEnhance: (fun) => {
          this.submitFormDataEnhance = fun
        },
      }
      if (fun !== false) {
        try {
          fun(this, this.jsEnhanceApi)
        } catch (error) {
          console.warn(`表单设计增强执行异常${type}`+error)
        }
      } else {
        console.warn(`表单设计增强编写异常${type}`)
      }
    },
    //js增强获取form表单的值
    getFormDataFun(key) {
      let { formData } = this.getFormAllConfigAsDataAsControlFun()
      if (key) {
        if (key instanceof Array) {
          let obj = {}
          key.forEach((item) => {
            obj[item] = formData[item]
          })
          return obj
        } else {
          return formData[key]
        }
      } else {
        return formData
      }
    },
    //js增强设置from表单的值
    setFormDataFun(key, value, bool) {
      this.$nextTick(() => {
        if (bool) {
          let dataObj = { fieldName: key, value }
          this.setFormValue(dataObj)
        } else {
          let { controlArr } = this.getFormAllConfigAsDataAsControlFun()
          let forKey = Object.keys(this.formData)
          let dataObj = { fieldName: key, value }
          let tableKey = this.tableOption.map((item) => item.prop)
          if (forKey.includes(key) && !tableKey.includes(key)) {
            this.setFormValue(dataObj)
          }
          controlArr.forEach((item) => {
            item.setJsFormDataFun(dataObj)
          })
        }
      })
    },
    //js增强设置控件配置
    setFormOptionsFun(key, optionsKey, optionsValue) {
      this.$nextTick(() => {
        let { controlArr } = this.getFormAllConfigAsDataAsControlFun()
        let column = ''
        let group = ''
        if (this.option.column) {
          column = this.findObject(this.option.column, key)
        }
        if (this.option.group) {
          this.option.group.forEach((item, index) => {
            if (this.option.group[index].column.length > 0) {
              let currGroup = this.findObject(
                this.option.group[index].column,
                key
              )
              if (currGroup != -1) {
                group = this.findObject(this.option.group[index].column, key)
              }
            }
          })
        }
        if (column && column != -1) {
          column[optionsKey] = optionsValue
        }
        if (group && group != -1) {
          group[optionsKey] = optionsValue
        }
        controlArr.forEach((item) => {
          item.setFormOptionsFun(key, optionsKey, optionsValue)
        })
      })
    },
    setFormMoreOptionsFun(key, options, other) {
      if (other === undefined) {
        let column = ''
        let group = ''
        if (this.option.column) {
          column = this.findObject(this.option.column, key)
        }
        if (this.option.group) {
          this.option.group.forEach((item, index) => {
            if (this.option.group[index].column.length > 0) {
              let currGroup = this.findObject(
                this.option.group[index].column,
                key
              )
              if (currGroup != -1) {
                group = this.findObject(this.option.group[index].column, key)
              }
            }
          })
        }
        if (column && column != -1) {
          for (let key in options) {
            column[key] = options[key]
          }
        }
        if (group && group != -1) {
          for (let key in options) {
            group[key] = options[key]
          }
        }
      } else {
        let type = 'formControl'
        let optionName = 'option'
        if (other.type == 'table') {
          type = 'tableControl'
          optionName = 'tableOption'
        }
        let column = this.findObject(
          this.$refs[type][other.index][optionName].column,
          key
        )
        for (let key in options) {
          column[key] = options[key]
        }
        if (other.type == 'table') {
          setTimeout(() => {
            this.$refs[type][other.index].$refs.crud.init()
          }, 0)
        }

        
      }
    },
    //js增强设置控件显示/隐藏  type:'show'/'hide'
    setFormControlStateFun(key, type) {
      this.$nextTick(() => {
        let { controlArr } = this.getFormAllConfigAsDataAsControlFun()
        if (!(key instanceof Array)) {
          key = [key]
        }
        let optionsKey = 'display'
        let optionsValue = ''
        if (type == 'show') {
          optionsValue = true
        }
        if (type == 'hide') {
          optionsValue = false
        }
        key.forEach((keyItem) => {
          let column = ''
          let group = ''
          if (this.option.column) {
            column = this.findObject(this.option.column, keyItem)
          }
          if (this.option.group) {
            this.option.group.forEach((item, index) => {
              if (this.option.group[index].column.length > 0) {
                group = this.findObject(
                  this.option.group[index].column,
                  keyItem
                )
              }
            })
          }
          if (column && column != -1) {
            column[optionsKey] = optionsValue
          }
          if (group && group != -1) {
            group[optionsKey] = optionsValue
          }
        })

        controlArr.forEach((item) => {
          item.setFormControlStateFun(key, optionsValue)
        })
      })
    },
    //js增强设置控件值监听
    setWatchFun(watchItems, bool = true) {
      if (bool) {
        if (watchItems instanceof Object && !(watchItems instanceof Array)) {
          this.$nextTick(() => {
            let tableKey = this.tableOption.map((item) => item.prop)
            let keyArr = Object.keys(watchItems)
            let formKey = Object.keys(this.formData)

            keyArr.forEach((keyItem) => {
              if (formKey.includes(keyItem) && !tableKey.includes(keyItem)) {
                let watchName = 'formData.' + keyItem
                this.$watch(watchName, watchItems[keyItem])
              }
            })
            if (this.$refs.formControl) {
              this.$refs.formControl.forEach((item) => {
                item.setWatchFun(watchItems)
              })
            }
          })
        }
      } else {
        let keyArr = Object.keys(watchItems)
        keyArr.forEach((keyItem) => {
          this.$watch(keyItem, watchItems[keyItem])
        })
      }
    },
    loadStyleString(cssText) {
      if (document.querySelector(`style[id=formremovecss_${this.random}]`)) {
        document
          .querySelector(`style[id=formremovecss_${this.random}]`)
          .remove()
      }
      var style = document.createElement('style')
      style.id = 'formremovecss_' + this.random
      try {
        // firefox、safari、chrome和Opera
        style.appendChild(document.createTextNode(cssText))
      } catch (ex) {
        // IE早期的浏览器 ,需要使用style元素的stylesheet属性的cssText属性
        style.styleSheet.cssText = cssText
      }
      document.getElementsByTagName('head')[0].appendChild(style)
    },
    //获取所有控件数量
    getControlNum() {
      let num = 1
      //获取表单数据  表单配置
      if (this.$refs.formControl) {
        this.$refs.formControl.forEach((item) => {
          num = num + 1
          if (item.$refs.tableControl) {
            item.$refs.tableControl.forEach(() => {
              num = num + 1
            })
          }
        })
      }
      if (this.$refs.tableControl) {
        this.$refs.tableControl.forEach(() => {
          num = num + 1
        })
      }
      return num
    },
    //组件触发
    tableViewBeforeCloseFun(type, data) {
      if (type == 'refreshDic') {
        this.$refs.form.dicInit()
      } else if (type == 'dialog') {
        this.tableSelectControlOption.isDialog = data.bool
      } else {
        this.tableSelectControlOption.isDialog = false
        this.formDynamicFun(type, data)
      }
    },
    //其他表单提交后执行
    formViewSubmitFun(done, data) {
      if (typeof type == 'function') {
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
      }else{
        this.formControlData.viewObj.isShow = false
      }
    },
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer)
    }
    if (
      document.querySelector(`style[id=formremovecss_${this.random}]`) &&
      this.isClearCss
    ) {
      document.querySelector(`style[id=formremovecss_${this.random}]`).remove()
    }
  },
}
</script>

<style lang="scss" scoped>
.form-custom-print-box {
  display: flex;
  justify-content: flex-end;
  .el-button {
    font-size: 20px;
    color: #ccc;
    display: flex;
    width: 42px;
    justify-content: flex-end;
    height: 22px;
    padding: 0;
    margin-bottom: 20px;
    padding-right: 20px;
  }
}
.form-custom {
  .form-custom-rate {
    padding-top: 10px;
  }
  .form-custom-tabs {
    /deep/.el-tabs__nav {
      .el-tabs__item.is-top:nth-child(2) {
        padding-left: 20px;
      }
    }
  }
  &.avue--detail {
    /deep/.el-form-item__label {
      padding-right: 10px;
      padding-left: 10px;
    }
    /deep/.el-form-item__content {
      .avue-upload--list {
        padding-top: 10px;
      }
    }
  }
}
/deep/textarea {
  resize: none;
}
/deep/.el-input-number input {
  text-align: center !important;
}
/* 禁用时隐藏placeholder */
/deep/.el-form .is-disabled {
  input::-webkit-input-placeholder,
  textarea::-webkit-input-placeholder {
    opacity: 0;
  }
  input::-moz-placeholder,
  textarea::-moz-placeholder {
    /* Mozilla Firefox 19+ */
    opacity: 0;
  }
  input:-moz-placeholder,
  textarea:-moz-placeholder {
    /* Mozilla Firefox 4 to 18 */
    opacity: 0;
  }
  input:-ms-input-placeholder,
  textarea:-ms-input-placeholder {
    /* Internet Explorer 10-11 */
    opacity: 0;
  }
}
.control-align-center {
  text-align: center;
}
.control-align-left {
  text-align: left;
}
.control-align-right {
  text-align: right;
}
/deep/.el-form-item__content {
  .avue-upload--list {
    .el-upload--picture-img {
      .avue-upload__icon {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 148px;
        height: 148px;
      }
    }
  }
}
.form-custom-btn-list {
  display: flex;
  align-items: center;
  margin-bottom: -8px;
  .form-custom-button {
    margin-left: 0px !important;
    &:last-child(1) {
      margin-right: 0 !important;
    }
  }
}
</style>
<style lang="scss">
.form-custom-preview-form-data-alert {
  height: 90%;
  .el-message-box__header {
    border-bottom: 1px solid #f1f1f1;
  }
  .el-message-box__content {
    overflow: auto;
    height: calc(100% - 75px);
  }
}
</style>
<style lang="scss" scoped>
@import '@/research/styles/form.scss';
</style>
