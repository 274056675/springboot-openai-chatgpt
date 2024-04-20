<template>
  <div class="code-test-box" v-loading="isTableLoading">
    <!-- avue-crud 正常表格显示 -->
    <div
      class="test-box-list"
      :class="`test-box-list_${currCodeId}_${random}`"
      v-if="displayModeType == 'normal'"
    >
      <avue-crud
        v-loading="isAvueTableLoading"
        ref="codeTestList"
        v-model="tableForm"
        v-if="isTableCrud"
        :option="tableOption"
        :data="tableData"
        :page.sync="tablePage"
        :search.sync="tableQueryData"
        :before-open="beforeOpenFun"
        :before-close="beforeCloseFun"
        :permission="tablePermission"
        :cell-style="cellStyle"
        :row-style="rowStyle"
        @selection-change="selectionChangeFun"
        @row-save="rowSaveFun"
        @row-update="rowUpdateFun"
        @row-del="rowDelFun"
        @refresh-change="tableRefreshChangeFun"
        @search-change="searchChangeFun"
        @search-reset="searchResetFun"
        @current-change="currentChangeFun"
        @size-change="sizeChangeFun"
        @expand-change="expandChanges"
        @tree-load="treeLoadFun"
        @sort-change="sortChange"
        @row-click="rowClick"
        :upload-exceed="uploadExceedFun"
        :upload-after="uploadAfter"
      >
        <!-- 菜单自定义(表格上面的按钮栏) -->
        <template slot="menuLeft">
          <!-- 左边按钮插槽 -->
          <menu-left-btns :that="that"></menu-left-btns>
        </template>
        <!-- 操作列按钮插槽 -->
        <template slot-scope="scope" slot="menu">
          <menu-link-btns :scope="scope" :that="that"></menu-link-btns>
        </template>
        <!-- 自定义表单按钮插槽 -->
        <template slot="menuForm" slot-scope="scope">
          <menu-form-btns :scope="scope" :that="that"></menu-form-btns>
        </template>
        <!-- 自定义erp模板 单选列 -->
        <template slot="vue_radio" slot-scope="scope">
          <div class="code-test-list-erp-radio">
            <el-radio
              v-model="tableErpRadioId"
              @change="tableErpRadioObj = setSaveOrUpdataFun(scope.row)"
              :label="scope.row.id"
              :size="scope.size"
            ></el-radio>
          </div>
        </template>
        <!-- 自定义markdown控件表单 -->
        <template
          v-for="(item, index) in viewMarkdownArr"
          slot-scope="scope"
          :slot="item.fieldMarkDownName + 'Form'"
        >
          <mavon-editor
            :ref="'moavonEditor_' + index"
            @imgAdd="(pos, $file) => moavonEditorImgAdd(pos, $file, index)"
            :key="index"
            v-model="scope.value"
            :editable="!scope.disabled"
          ></mavon-editor>
        </template>
        <!-- 自定义省市区表格列 -->
        <template v-for="(item, index) in viewPcaArr" :slot="item.fieldPcaName" slot-scope="scope">
          <div :key="index">
            {{
            viewPcaNameObj[scope.row[item.fieldName]]
            ? viewPcaNameObj[scope.row[item.fieldName]]
            : scope.row[item.fieldName]
            }}
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
            :tableItemName="item.fieldName"
            :tableItemVal="scope.value"
            :disabled="scope.disabled"
            :tableItemScope="scope"
            :multiple="scope.column.multiple ? true : false"
            @set-form-val="setTableFormValue"
            :allDepart="allDepartData[item.fieldUserName]"
            :allUserObj="allUserObj"
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
            :tableItemVal="scope.value"
            :tableItemName="item.fieldName"
            :disabled="scope.disabled"
            :tableItemScope="scope"
            :multiple="scope.column.multiple ? true : false"
            @set-form-val="setTableFormValue"
          ></depart-control>
        </template>
        <!-- 自定义表格选择控件 -->
        <template
          v-for="(item, index) in viewTableSelectArr"
          :slot="item.fieldTableSelectName + 'Form'"
          slot-scope="scope"
        >
          <table-select-control
            :key="index"
            :tableItemVal="scope.value"
            :tableItemName="item.fieldName"
            :disabled="scope.disabled"
            :tableItemScope="scope"
            v-bind="{
              multiple: scope.column.multiple,
              selecText: scope.column.selecText,
              configJson: scope.column.configJson,
              isTree: scope.column.isTree,
              treeDataUrl: scope.column.treeDataUrl,
              treeTableId: scope.column.treeTableId,
              treeParams: scope.column.treeParams,
              treeMethod: scope.column.treeMethod,
              treeFormatt: scope.column.treeFormatt,
              tableId: scope.column.tableId,
              treeApiMode: scope.column.treeApiMode,
            }"
            @set-form-val="setTableFormValue"
          ></table-select-control>
        </template>
        <!-- 自定义编译器 -->
        <template
          v-for="(item, index) in viewMonacoEditor"
          slot-scope="scope"
          :slot="item.monacoName + 'Form'"
        >
          <div :key="index">
            <div v-if="scope.column.exampleBtn" style="margin-bottom:2px">
              <el-button type="info" size="mini" @click="scope.column.exampleFun">
                {{ scope.column.exampleText
                }}
              </el-button>
            </div>
            <monaco-editor
              ref="monacoEditor"
              v-model="tableForm[item.monacoName]"
              :isSetData="true"
              :keyIndex="index"
              :language="scope.column.editorType"
              :height="scope.column.editorHeight"
            ></monaco-editor>
          </div>
        </template>
        <!-- 自定义联动控件表格列 -->
        <template
          v-for="(item, index) in viewLinkDownFieldArr"
          :slot="item.fieldLinkDownName"
          slot-scope="scope"
        >
          <div :key="index">
            {{
            viewLinkDownDicObj[item.parentName][scope.row[item.fieldName]]
            ? viewLinkDownDicObj[item.parentName][scope.row[item.fieldName]]
            : scope.row[item.fieldName]
            }}
          </div>
        </template>
        <!-- 自定义文件表格列  文件-->
        <template v-for="(item, index) in viewFileArr" :slot="item.fieldName" slot-scope="scope">
          <div :key="index">
            <el-popover placement="top" width="160">
              <div class="view-file-download-list">
                <a
                  v-for="item in scope.row['$File' + item.fieldName]"
                  :key="item.url"
                  :href="item.url"
                  :download="item.name"
                >
                  <i class="el-icon-download"></i>
                  {{ item.name }}
                </a>
              </div>
              <el-button
                slot="reference"
                icon="el-icon-download"
                type="primary"
                size="small"
                plain
                v-if="scope.row[item.fieldName]"
              >下载</el-button>
            </el-popover>
            <div v-if="!scope.row[item.fieldName]">无文件</div>
          </div>
        </template>
        <!-- 自定义表单文件控件 文件列表list -->
        <template
          v-for="(item, index) in viewFileArr"
          :slot="item.fieldName + 'Type'"
          slot-scope="scope"
        >
          <div :key="index" style="cursor: pointer; display: flex; align-items: center">
            <i class="el-icon-link"></i>
            <a style="flex: 1" :href="scope.file.url">
              {{
              viewFileNameObj[scope.file.url]
              ? viewFileNameObj[scope.file.url]
              : scope.file.url
              }}
            </a>
            <i
              class="el-icon-close"
              @click.capture.stop="codeFileControlDelFun(item.fieldName, scope)"
            ></i>
          </div>
        </template>
        <!-- 自定义附表 -->
        <template slot="vue_infoForm" slot-scope="scope">
          <div class="info-form-box" v-if="isTableInfo == true">
            <avue-tabs :option="tabsOption" @change="tabsHandleChange"></avue-tabs>
            <span
              v-for="item in tabsOption.column"
              :key="item.prop"
              v-show="tabsType.prop == item.prop"
              :class="'info-form-box-tabs-span-' + item.prop"
            >
              <code-sublist-form
                :tableType="item.tableType"
                :boxType="scope.column.boxType"
                :tableAllColumnRules="tableAllColumnRules"
                :disabled="scope.disabled"
                :tableTabName="item.prop"
                :tableKey="item.key"
                :currDataList="item.dataList"
                :allChangeFun="item.onlChangeFun"
                :tableClassName="'info-form-box-tabs-span-' + item.prop"
                :tableColumnDic="item.allDicData"
                :getParentFieldValue="form.getAllFieldValue.bind(this)"
                :setParentFieldValue="form.setFieldsValue.bind(this)"
                :simpleDateFormat="simpleDateFormat.bind(this)"
                ref="codeSublistForm"
                v-if="item.tableType == 'form'"
              ></code-sublist-form>
              <code-sublist-form
                :tableType="item.tableType"
                :tabObj="item.tabObj"
                :boxType="scope.column.boxType"
                :tableAllColumnRules="tableAllColumnRules"
                :disabled="scope.disabled"
                :tableTabName="item.prop"
                :tableKey="item.key"
                :currDataList="item.dataList"
                :allChangeFun="item.onlChangeFun"
                :tableClassName="'info-form-box-tabs-span-' + item.prop"
                :tableColumnDic="item.allDicData"
                :simpleDateFormat="simpleDateFormat.bind(this)"
                :addSubRows="form.addSubRows.bind(this)"
                :clearSubRows="form.clearSubRows.bind(this)"
                :clearThenAddRows="form.clearThenAddRows.bind(this)"
                ref="codeMasterlistForm"
                v-if="item.tableType == 'tab'"
              ></code-sublist-form>
              <code-sublist-table
                :boxType="scope.column.boxType"
                :tableAllColumnRules="tableAllColumnRules"
                :disabled="scope.disabled"
                :tableTabName="item.prop"
                :tableKey="item.key"
                :currDataList="item.dataList"
                :allChangeFun="item.onlChangeFun"
                :tableColumnDic="item.allDicData"
                :showMenu="item.showMenu"
                :formParentDataId="item.formParentDataId"
                :getParentFieldValue="form.getAllFieldValue.bind(this)"
                :setParentFieldValue="form.setFieldsValue.bind(this)"
                :simpleDateFormat="simpleDateFormat.bind(this)"
                :sortCustomButtonFun="sortCustomButtonFun"
                :opentJsEnhance="item.opentJsEnhance"
                ref="codeSublistTable"
                v-if="item.tableType == 'table'"
              ></code-sublist-table>
            </span>
          </div>
        </template>
        <!-- 自定义展开列 -->
        <template slot="expand" slot-scope="{ row }">
          <div class="info-form-box">
            <el-tabs v-model="listTabsType">
              <el-tab-pane
                v-for="item in tabsOption.column"
                :key="item.prop"
                :label="item.label"
                :name="item.prop"
              >
                <code-sublist-table
                  :disabled="true"
                  :tableColumn="expandObj[item.prop].column"
                  :currDataList="expandObj[item.prop].data"
                  :tableTabName="item.prop"
                  :tableKey="item.key"
                  :tableColumnDic="item.allDicData"
                  tableType="expand"
                  ref="codeTableExpand"
                ></code-sublist-table>
              </el-tab-pane>
            </el-tabs>
          </div>
        </template>
      </avue-crud>
    </div>
    <!-- avue-crud 大数据展示方式 -->
    <div
      class="test-box-list"
      :class="`test-box-list_${currCodeId}`"
      v-if="displayModeType == 'bigData'"
    >
      <avue-crud
        ref="codeTestList"
        v-loadmore="handelLoadmore"
        v-model="tableForm"
        v-if="isTableCrud"
        :data-size="tableData.length"
        :option="tableOption"
        :data="filteredData"
        :page.sync="tablePage"
        :search.sync="tableQueryData"
        :before-open="beforeOpenFun"
        :before-close="beforeCloseFun"
        :permission="tablePermission"
        :cell-style="cellStyle"
        :row-style="rowStyle"
        @selection-change="selectionChangeFun"
        @row-save="rowSaveFun"
        @row-update="rowUpdateFun"
        @row-del="rowDelFun"
        @refresh-change="tableRefreshChangeFun"
        @search-change="searchChangeFun"
        @search-reset="searchResetFun"
        @current-change="currentChangeFun"
        @size-change="sizeChangeFun"
        @expand-change="expandChanges"
        @tree-load="treeLoadFun"
        @sort-change="sortChange"
        :upload-exceed="uploadExceedFun"
        :upload-after="uploadAfter"
      >
        <!-- 菜单自定义(表格上面的按钮栏) -->
        <template slot="menuLeft">
          <!-- 左边按钮插槽 -->
          <menu-left-btns :that="that"></menu-left-btns>
        </template>
        <template slot-scope="scope" slot="menu">
          <menu-link-btns :scope="scope" :that="that"></menu-link-btns>
        </template>
      </avue-crud>
    </div>
    <div class="test-box-erp" v-if="themeTemplate == 'erp' && tableId == ''">
      <avue-tabs :option="tabsOption" @change="tabsHandleChange"></avue-tabs>
      <span
        v-for="item in tabsOption.column"
        :key="item.prop"
        v-show="tabsType.prop == item.prop"
        :class="'info-form-box-tabs-span-' + item.prop"
      >
        <code-test-list
          :tableId="item.prop"
          :currMainDataId="tableErpRadioId"
          :currMainDataObj="tableErpRadioObj"
          :foreignKeys="item.foreignKeys"
          :tableType="item.tableType"
        ></code-test-list>
      </span>
    </div>
    <!-- 导入 -->
    <el-dialog
      v-dialogdrag
      title="导入EXCEL"
      :visible.sync="tableInportDialog"
      class="code-test-list-dialog-inport-box"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      width="500px"
    >
      <div class="code-test-list-inport-box">
        <div class="inport-tip">
          <el-button type="text" @click="downloadInportTemplateFun">下载导入模板</el-button>
        </div>
        <avue-form
          :option="inportOption"
          v-model="inportForm"
          :upload-error="(file, column) => carryTableButtonFun('inportDel', { file, column })
          "
          :upload-before="(file, done, loading) =>
    carryTableButtonFun('inportAdd', { file, done, loading })
    "
          :upload-exceed="(limit, files, fileList, column) =>
    carryTableButtonFun('inportLimit', {
      limit,
      files,
      fileList,
      column,
    })
    "
        >
          <template slot="inportexcelType" slot-scope="scope">
            <span>
              <i class="el-icon-document"></i>
              <span>{{ scope.file.name }}</span>
              <i
                class="el-icon-close"
                @click="carryTableButtonFun('inportDel', { file: scope.file })"
              ></i>
            </span>
          </template>
          <template slot="inportexcelLabel">{{ "" }}</template>
        </avue-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="tableInportDialog = false" size="small">取 消</el-button>
        <el-button
          type="primary"
          @click="carryTableButtonFun('inportConfirm')"
          size="small"
          :loading="isTableLoading"
        >开始导入</el-button>
      </div>
    </el-dialog>
    <!-- 引用自定义表单设计 -->
    <el-dialog
      v-dialogdrag
      :title="dialogFormTitle"
      :visible.sync="isDialogFormDesign"
      :fullscreen="isFormFullscreenDesign"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      :width="widgetFormPreview.dialogWidth ? widgetFormPreview.dialogWidth : '80%'
        "
      class="dialog-form-design-box"
    >
      <div class="form-design-box-content">
        <div class="content-fullscreen" @click="isFormFullscreenDesign = !isFormFullscreenDesign">
          <i class="el-icon-full-screen"></i>
        </div>
        <form-custom
          ref="formCustom"
          v-if="isDialogFormDesign"
          :formOption="widgetFormPreview"
          :formOpenType="formOpenType"
          :onlineFormId="currCodeId"
          :actionData="formActionData"
          :btnPermissions="formBtnPermissions"
          :allFormListData="allFormListData"
          :closeDialogForm="closeDialogForm.bind(this)"
          :openRouterFun="openRouterFun.bind(this)"
          :transmitFun="formCustomOtherFun.bind(this)"
        ></form-custom>
      </div>
    </el-dialog>
    <!-- 其他表格弹窗控件 -->
    <table-view
      v-if="isTableView"
      :tableViewOptionData="tableViewOptionData"
      :beforeClose="tableViewDeclareFun"
    ></table-view>
    <!-- 其他表单控件 -->
    <form-view
      v-if="isFormViewControl"
      :formOptionData="FormViewControlOption"
      :formViewControlFun="formViewControlFun.bind(this)"
    ></form-view>
    <!-- 树选择控件 -->
    <table-tree
      v-if="isTableTreeControl"
      :optionData="tableTreeControlOption"
      :treeControlFun="treeControlFun.bind(this)"
    ></table-tree>
    <!-- 表格选择控件 -->
    <table-select
      ref="table_select"
      v-if="isTableSelectControl"
      :optionData="tableSelectControlOption"
      :selectControlFun="selectControlFun.bind(this)"
    ></table-select>
    <!-- tabs控件 -->
    <tabs-view
      ref="tabs_view"
      v-if="isTabsView"
      :tabOptionData="tabsOptionData"
      :tabsViewFun="tabsViewFun.bind(this)"
    ></tabs-view>
    <!-- 组件 -->
    <control-view
      ref="control_view"
      v-if="isControlView"
      :formOptionData="controlViewOption"
      :controlViewFun="controlViewFun.bind(this)"
    ></control-view>
    <!-- 文件系统-资源审核 -->
    <zysc-control ref="zysc_control" v-if="isZyscControl"></zysc-control>
  </div>
</template>

<script>
// 表单配置
let validateRulesAll = {}
let provinces = {}
import { setTreeDataUtil, analysisFunction, findParentNodeFun, downloadFileFun, getCurrentDateFun, getStrDataFunction } from '@/research/util/myUtil.js'
import { dateFormat } from '@/util/date'
import { codeListRules } from '@/research/util/rules'
import { cityObj } from '@/research/util/city'

import { mapGetters, mapMutations } from 'vuex'

import { getDetails } from '@/api/research/code'
import { getDeptTree } from '@/api/system/dept'
import { getList } from '@/api/system/user'
import { getFormHeadApi, getFormFieldApi, getDataApi, getTreeDataApi, addDataApi, getDataDetailApi, editDataApi, delDataApi, getTreeAllDataApi, getTreeItemDataApi, exportDataApi, importDataApi, uploadeFileApi, getUploadeFileNameApi, getDicTableData, getTableDicData, touchSqlEnhanceApi, getErpColumnsApi, getActionApi, postActionApi, deleteActionApi, importDataTemplateApi } from '@/api/research/codelist'
import { getdetailDataApi, getFormIdApi } from '@/api/research/form'
import codetestlist from '@/research/mixins/codetestlist'
import Vue from 'vue'
export default {
  name: 'codeTestList',
  mixins: [codetestlist],
  components: {},
  filters: {},
  watch: {},
  computed: {
    ...mapGetters(['provinces', 'userInfo', 'permission']),
  },
  props: {},
  data() {
    return {
      getCurrentDateFun: getCurrentDateFun,
      setTreeDataUtil: setTreeDataUtil,
    }
  },
  created() {
    this.currDateTime = this.getCurrentDateFun('dataTime')
    if (this.isLazy) {
      return false
    }
    this.init()
  },
  beforeDestroy() {
    this.timerInte.forEach((item) => {
      if (item) {
        clearInterval(item)
      }
    })
  },
  directives: {
    loadmore: {
      componentUpdated: function (el, binding, vnode, oldVnode) {
        // 设置默认溢出显示数量
        var spillDataNum = 50

        // 设置隐藏函数
        var timeout = false
        let setRowDisableNone = function (topNum, showRowNum, binding) {
          // 0  7
          if (timeout) {
            clearTimeout(timeout)
          }
          timeout = setTimeout(() => {
            binding.value.call(null, topNum, topNum + showRowNum + spillDataNum) // null  0   0 + 7 + 10
          })
        }
        setTimeout(() => {
          let newScrollTop = ''
          let oldScrollTop = ''
          const dataSize = vnode.data.attrs['data-size'] // 113
          const oldDataSize = oldVnode.data.attrs['data-size'] //113
          if (dataSize === oldDataSize) return
          const selectWrap = el.querySelector('.el-table__body-wrapper')
          const selectTbody = selectWrap.querySelector('table tbody')
          const selectRow = selectWrap.querySelector('table tr')
          if (!selectRow) {
            return
          }
          const rowHeight = selectRow.clientHeight //49
          let showRowNum = Math.round(selectWrap.clientHeight / rowHeight) //348  /  49  =  7

          const createElementTR = document.createElement('tr')
          let createElementTRHeight = (dataSize - showRowNum - spillDataNum) * rowHeight //(11340 - 7 - 10) * 49  =  554827
          createElementTR.setAttribute(
            'style',
            `height: ${createElementTRHeight}px;` //554827
          )
          selectTbody.append(createElementTR)

          // 监听滚动后事件
          selectWrap.addEventListener('scroll', function () {
            if (oldScrollTop && newScrollTop && oldScrollTop == this.scrollTop) {
              return false
            }
            oldScrollTop = newScrollTop
            newScrollTop = this.scrollTop

            let topPx = this.scrollTop - spillDataNum * rowHeight //滚动高度0 - 10 * 49  = - 490
            let topNum = Math.round(topPx / rowHeight) // -8
            let minTopNum = dataSize - spillDataNum - showRowNum //11340 - 10 - 7
            if (topNum > minTopNum) {
              // - 8 > 11323 ?
              topNum = minTopNum // 11323
            }
            if (topNum < 0) {
              topNum = 0
              topPx = 0
            }
            selectTbody.setAttribute('style', `transform: translateY(${topPx}px)`)
            createElementTR.setAttribute('style', `height: ${createElementTRHeight - topPx > 0 ? createElementTRHeight - topPx : 0}px;`)
            setRowDisableNone(topNum, showRowNum, binding)
          })
        })
      },
    },
  },
  mounted() {},
  methods: {
    ...mapMutations(['SET_PROVINCES']),
    //判断是否显示操作列 - 按钮
    isOperationNullFun(item) {
      if (this.tableOption.menu === false) {
        return false
      }
      if (this.tablePermission.editBtn || this.tablePermission.moreDelBtn || this.tablePermission.moreViewBtn) {
        return false
      }
      let bool = true
      for (let key in item) {
        if (key.indexOf('$link$') == 0 && item[key]) {
          bool = false
        }
      }
      return bool
    },
    handelLoadmore(currentStartIndex, currentEndIndex) {
      this.currentStartIndex = currentStartIndex
      this.currentEndIndex = currentEndIndex
      setTimeout(() => {
        this.setInuptEventFun()
      }, 300)
    },
    setInuptEventFun() {
      if (!this.isOpentAuthFocus) {
        return false
      }
      //设置监听
      let wacthEl = document.querySelectorAll(this.authFocusObj.inputAttr)
      wacthEl.forEach((item) => {
        if (item.onkeydown) {
          return false
        }
        item.onkeydown = (event) => {
          if (event.keyCode == 13) {
            let trEl = ''
            let currEl = ''
            if (event.path) {
              event.path.forEach((el) => {
                if (el.className == 'el-table__row' && !trEl) {
                  trEl = el
                }
              })
            } else if (event.trEl) {
              trEl = findParentNodeFun(event.trEl, 'el-table__row', 'code-test-box')
            }
            currEl = trEl.querySelector(this.authFocusObj.inputAttr)
            trEl = trEl.nextSibling.querySelector(this.authFocusObj.inputAttr)
            if (trEl) {
              trEl.focus()
              let index = this.currentEndIndex - this.currentStartIndex - 1
              if (currEl == document.querySelectorAll(this.authFocusObj.inputAttr)[index]) {
                this.authFocusObj.currBigDataTrEl = currEl
              }
            }
          }
        }
      })
      this.$nextTick(() => {
        if (this.authFocusObj.currBigDataTrEl) {
          this.authFocusObj.currBigDataTrEl.onkeydown({
            keyCode: 13,
            trEl: this.authFocusObj.currBigDataTrEl,
          })
          this.authFocusObj.currBigDataTrEl = ''
        }
      })
    },

    //初始化
    async init() {
      this.isTableLoading = true
      this.that = this
      //获取动态路由id
      if (this.tableId) {
        this.currCodeId = this.tableId
        if (this.hideHeader) {
          this.tableOption.header = false
        }
      } else if (this.tranTableId) {
        this.currCodeId = this.tranTableId
        if (this.otherParams && this.otherParams.currCodeType) {
          this.currCodeType = this.otherParams.currCodeType
        }
      } else {
        this.currCodeId = this.$route.params.id
        if (!this.currCodeId) {
          let params = this.$route.path.split('views/tool/codetestlist/')[1]
          if (params.indexOf('/') != -1) {
            params = params.split('/')[0]
            this.currCodeType = params.split('/')[1]
          }
          this.currCodeId = params
        }
      }
      this.tableOption.dialogCustomClass = `zhxy-online-form-table-dialog-${this.currCodeId}`

      this.tableAllColumnRules = codeListRules
      let PromiseArr = []

      let columsList = []
      let headData = {}
      let allCustomButton = []

      let columsData = null
      let formItemData = null
      PromiseArr[0] = new Promise((resolve) => {
        getDetails(this.currCodeId).then((res) => {
          columsList = res.data.data.fieldList
          headData = res.data.data.head
          if (headData.isAuthBtn === 'Y') {
            this.isAuthBtn = true
          }
          this.themeTemplate = headData.themeTemplate
          this.tableName = headData.tableName
          this.tableDescribe = headData.tableTxt
          this.tableSearchType = headData.searchPattern
          resolve()
        })
      })
      PromiseArr[1] = new Promise((resolve) => {
        getFormHeadApi({ headId: this.currCodeId }).then((res) => {
          columsData = res.data.data
          if (columsData.cgButtonList) {
            allCustomButton = [...allCustomButton, ...columsData.cgButtonList]
          }
          // 设置部门和用户
          let userKey = Object.keys(columsData.userOptions)
          let deptKey = Object.keys(columsData.deptOptions)
          let isDept = columsData.deptOptions && deptKey.length > 0
          if (isDept) {
            this.allDepartData = columsData.deptOptions
          }
          if (columsData.userOptions && Object.keys(columsData.userOptions).length > 0) {
            this.allUserData = columsData.userOptions
            this.allUserObj.allList = this.allUserData[userKey[0]]
            getList(1, 5, {}, '').then((userRes) => {
              let userData = userRes.data.data
              this.allUserObj.list = userData.records
              this.allUserObj.total = userData.total
            })
            getDeptTree().then((deptRes) => {
              userKey.forEach((item) => {
                this.allDepartData[item] = deptRes.data.data
              })
            })
          }
          this.tableColumnDic = columsData.dictOptions
          resolve()
        })
      })
      PromiseArr[2] = new Promise((resolve) => {
        getFormFieldApi({ headId: this.currCodeId }).then((res) => {
          formItemData = res.data.data
          if (formItemData.cgButtonList) {
            allCustomButton = [...allCustomButton, ...formItemData.cgButtonList]
          }
          this.tableColumnItemForm = formItemData
          resolve()
        })
      })
      await Promise.all(PromiseArr)
      // 表格配置处理
      let columns = await this.setTableDataFun(columsList, headData)
      //自定义搜索字典处理
      let customSearchPromiseArr = []
      this.customSearchArr.forEach((item) => {
        customSearchPromiseArr.push(
          new Promise((resolve) => {
            this.customSearchFun(item).then((searchObj) => {
              let columnItem = this.findObject(columns, item.dbFieldName)
              if (columnItem != -1) {
                for (let key in searchObj) {
                  columnItem[key] = searchObj[key]
                }
              }
              resolve()
            })
          })
        )
      })
      await Promise.all(customSearchPromiseArr)
      //判断是否有附表
      if (headData.subTableStr && this.themeTemplate != 'erp') {
        //父子表处理
        columns.push({
          labelWidth: 0,
          label: '',
          prop: 'vue_info',
          span: 24,
          hide: true,
          formslot: true,
        })
        this.isTableInfo = true
      }

      this.tableOption.column = columns
      if (this.tableDataIsTree) {
        let column = this.findObject(this.tableOption.column, this.tableTreeParentIdName)
        column.editDisabled = true
      }
      if (this.tableOption.addBtn) {
        //初始化js增强
        this.initOnlineEnhanceJs(columsData.enhanceJs, formItemData.enhanceJs)
      } else {
        //自定义表单不初始化 form增强
        this.initOnlineEnhanceJs(columsData.enhanceJs)
      }
      this.isInitEnhance = true

      if (this.isProvinces) {
        this.getPacDicDataFun()
      }
      //处理自定义按钮数据
      let buttonObj = this.sortCustomButtonFun(allCustomButton)
      if (this.isAuthBtn) {
        buttonObj.top = buttonObj.top.filter((item) => this.permission[`${item.buttonCode}_${this.currCodeId}${this.currCodeType}`])
      }
      this.customButtonTop = buttonObj.top
      this.customButtonLink = buttonObj.link
      this.customButtonFormSide = buttonObj.side
      this.customButtonFormEnd = buttonObj.end
      // 增强初始化avue表格配置
      if (this.customOnlineEnhanceJsName.list.includes('columnInit')) {
        try {
          this.customOnlineEnhanceJsList.columnInit(this.that)
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | columnInit方法执行异常：${error}`)
        }
      }
      //设置默认搜索值
      this.tableOption.column.forEach((item) => {
        if (item.searchValue) {
          this.tableQueryData[item.prop] = item.searchValue
        }
      })
      //显示表格
      this.isTableCrud = true
      //初始化表格数据
      if (this.isTableGetData) {
        await this.initTableData(this.tablePage, true)
      }
      //处理附表数据
      this.subTableDataFun()
      if (this.customOnlineEnhanceJsName.list.includes('created')) {
        try {
          this.customOnlineEnhanceJsList.created(this.that)
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | created方法执行异常：${error}`)
        }
      }

      //处理主题模板
      let templateObj = {}
      if (this.themeTemplate == 'tab') {
        templateObj = {
          label: headData.tableTxt,
          prop: headData.id,
          key: headData.tableName,
          tableType: 'tab',
          allDicData: this.tableColumnDic,
          dataList: [],
          tabObj: {
            column: this.tableOption.column,
            viewMarkdownArr: this.viewMarkdownArr,
            viewFileArr: this.viewFileArr,
            viewUserControlArr: this.viewUserControlArr,
            viewDepartControlArr: this.viewDepartControlArr,
          },
        }
      }
      this.themeTemplateTableFun(headData.themeTemplate, templateObj)
      setTimeout(() => {
        this.isTableLoading = false
      }, 1000)
    },
    //列样式
    cellStyle(obj) {
      if (this.customOnlineEnhanceJsName.list.includes('cellStyle')) {
        try {
          return this.customOnlineEnhanceJsList.cellStyle(this.that, obj)
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | cellStyle方法执行异常：${error}`)
        }
      }
    },
    //行样式
    rowStyle(obj) {
      if (this.customOnlineEnhanceJsName.list.includes('rowStyle')) {
        try {
          return this.customOnlineEnhanceJsList.rowStyle(this.that, obj)
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | rowStyle方法执行异常：${error}`)
        }
      }
    },
    //获取搜索所有开启范围查询的时间配置
    getSearchRangeControlFun(searchData) {
      let timeArr = []
      let data = {}
      let remove = []
      this.tableOption.column.forEach((item) => {
        if (item.search && item.searchRange) {
          timeArr.push(item.prop)
        }
        if (item.children && item.children.length > 0) {
          item.children.forEach((child) => {
            if (child.headSearch && child.searchRange) {
              timeArr.push(child.prop)
            }
          })
        }
      })
      if (timeArr.length <= 0) {
        return {
          data,
          remove,
        }
      }
      for (let key in searchData) {
        if (timeArr.includes(key) && searchData[key]) {
          let currTime = []
          if (searchData[key].indexOf(',') != -1) {
            currTime = searchData[key].split(',')
          } else if (searchData[key] instanceof Array) {
            currTime = searchData[key]
          }
          data[key + '_begin'] = currTime[0]
          data[key + '_end'] = currTime[1]
        } else if (timeArr.includes(key) && !searchData[key]) {
          remove.push(key)
        }
      }
      return {
        data,
        remove,
      }
    },
    // 附表数据处理
    async subTableDataFun() {
      if (this.tableId && !this.tableView && this.tableColumnItemForm.schema) {
        return false
      }
      let properties = this.tableColumnItemForm.schema.properties
      if (properties) {
        let subList = []
        let subListObj = {}
        if (this.themeTemplate == 'erp') {
          let erpRes = await getErpColumnsApi(this.currCodeId)
          subList = erpRes.data.data.subList
          subList.forEach((item) => {
            subListObj[item.headId] = item
          })
        }

        let attachedData = []
        for (let key in properties) {
          let obj = properties[key]
          if (obj.view == 'tab') {
            attachedData.push(obj)
          }
        }
        attachedData.sort((a, b) => {
          return a.order - b.order
        })
        attachedData.forEach((item) => {
          let allDicData = {}
          if (item.relationType === 1) {
            for (let key in item.properties) {
              let itemField = item.properties[key]
              if (itemField.enum) {
                allDicData[key] = itemField.enum
              }
            }
          } else {
            item.columns.forEach((columnItem) => {
              if (columnItem.options) {
                allDicData[columnItem.key] = columnItem.options
              }
            })
          }
          this.tabsOption.column.push({
            label: item.describe, //表描述
            prop: item.id, //表id
            key: item.key, //表名
            tableType: item.columns ? 'table' : 'form', //表类型
            allDicData, //所有表下拉数据
            dataList: [], //表数据
            foreignKeys: subListObj[item.id] ? subListObj[item.id].foreignKeys : [], //主附关联字段
          })
        })
        if (this.tabsOption.column.length > 0) {
          this.tabsType = this.tabsOption.column[0]
        }
      }
    },
    // 内嵌子表主题(一对多) 展开处理
    async expandChanges(row, expendList) {
      if (this.themeTemplate != 'innerTable') {
        return false
      }
      let dataRes = await getDataDetailApi(this.currCodeId, row.id)
      this.tabsOption.column.forEach((item) => {
        this.expandObj[item.prop].data = dataRes.data.data[item.key]
      })
      if (expendList.length > 0) {
        this.listTabsType = this.tabsOption.column[0].prop + ''
        if (expendList.length) {
          this.tableOption.expandRowKeys = []
          if (row) {
            this.tableOption.expandRowKeys.push(row.id)
          }
        } else {
          this.tableOption.expandRowKeys = []
        }
      }
    },
    // 刷新数据
    tableRefreshChangeFun() {
      this.$refs.codeTestList.selectClear()
      this.initTableData()
    },
    //删除文件控件 列表数据方法
    codeFileControlDelFun(fileName, obj) {
      if (this.tableForm['$File' + fileName]) {
        let fileArr = this.tableForm['$File' + fileName].filter((item) => {
          return item.url != obj.file.url
        })
        this.tableForm['$File' + fileName] = fileArr
      }

      let arr = this.tableForm[fileName].split(',')
      let fileStr = arr.filter((item) => {
        return item != obj.file.url
      })
      fileStr.join(',')
      this.tableForm[fileName] = fileStr.join(',')
    },
    // 表格头部按钮方法
    async carryTableButtonFun(type, obj) {
      //导出
      if (type == 'export') {
        let text = ''
        let isAll = true
        if (this.tableSelectData.length > 0) {
          isAll = false
          text = '是否导出当前已勾选的数据？'
        } else {
          isAll = true
          text = '是否导出所有数据'
        }
        this.$confirm(text, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(async () => {
            let params = {}
            if (!isAll) {
              params = {
                column: 'id',
                order: 'desc',
                superQueryMatchType: 'and',
                pageNo: 1,
                pageSize: -1,
                selections: this.tableSelectId.join(','),
              }
            } else {
              params = {
                ...params,
                ...this.tableQueryData,
              }
            }

            //erp主题 导出添加额外条件
            if (this.currMainDataId) {
              this.foreignKeys.forEach((item) => {
                params[item.field] = this.currMainDataObj[item.key]
              })
            }
            let paramsKey = Object.keys(params)
            paramsKey.forEach((item) => {
              if (params[item] instanceof Array) {
                params[item] = params[item].join(',')
              }
            })
            let res = await exportDataApi(this.otherPortId ? this.otherPortId : this.currCodeId, {
              paramsStr: JSON.stringify(params),
              pageNo: 1,
              pageSize: -1,
            })
            let blob = new Blob([res.data], {
              type: 'application/vnd.ms-excel',
              name: this.tableDescribe,
            })
            let url = window.URL.createObjectURL(blob)
            var a = document.createElement('a')
            document.body.appendChild(a)
            a.style = 'display: none'
            a.href = url
            a.download = this.tableDescribe
            a.click()
            window.URL.revokeObjectURL(url)
          })
          .catch(() => {})
      }
      //导入
      if (type == 'inport') {
        this.inportForm.inportexcel = []
        this.inportForm.inportList = []
        this.tableInportDialog = true
      }
      if (type == 'inportDel') {
        let uid = obj.file.url.split('://')[1]
        this.inportForm.inportexcel = this.inportForm.inportexcel.filter((item) => {
          let itemUid = item.value.split('://')[1]
          return itemUid != uid
        })
        this.inportForm.inportList = this.inportForm.inportList.filter((item) => {
          return item.file.uid != uid
        })
      }
      if (type == 'inportLimit') {
        this.$message({
          message: '只能选择一个文件进行导入~',
          type: 'warning',
        })
      }
      if (type == 'inportAdd') {
        this.inportForm.inportexcel.push({
          label: obj.file.name,
          value: 'http://' + obj.file.uid,
        })
        this.inportForm.inportList.push({ file: obj.file })
        obj.loading()
      }
      if (type == 'inportConfirm') {
        if (this.inportForm.inportList.length <= 0) {
          this.$message({
            message: '请先选择需要导入的excel文件',
            type: 'warning',
          })
          return false
        }

        let formData = new FormData()
        formData.append('validateStatus', this.inportForm.validateStatus)
        formData.append('files', this.inportForm.inportList[0].file)
        let importRes = await importDataApi(this.otherPortId ? this.otherPortId : this.currCodeId, formData)

        if (importRes.data.success) {
          this.tableInportDialog = false
          this.$message({
            message: '导入成功',
            type: 'success',
          })
          this.inportForm.inportexcel = []
          this.inportForm.inportList = []
          this.tablePage.currentPage = 1
          this.initTableData()
        }
      }
    },
    //设置表格配置
    setTableOptionsFun(prop, obj) {
      let column = this.findObject(this.tableOption.column, prop)
      for (let key in obj) {
        column[key] = obj[key]
      }
    },
    //获取配置信息
    getTableOptionsFun(prop, key) {
      let column = this.findObject(this.tableOption.column, prop)
      if (key) {
        return column[key]
      } else {
        return column
      }
    },
    //时间格式化
    simpleDateFormat(millisecond, format) {
      try {
        return dateFormat(new Date(millisecond), format)
      } catch (error) {
        console.warn(error)
      }
    },
    //对自定义按钮排序 并且赋值
    sortCustomButtonFun(arr) {
      let allButtonList = []
      let nullOrder = []
      let top = []
      let link = []
      let side = []
      let end = []
      arr.forEach((item) => {
        if (item.orderNum) {
          allButtonList.push(item)
        } else {
          nullOrder.push(item)
        }
      })
      allButtonList.sort((a, b) => {
        return a.orderNum - b.orderNum
      })
      allButtonList = [...allButtonList, ...nullOrder]
      allButtonList.forEach((item) => {
        if (item.buttonStyle == 'button') {
          top.push(item)
        }
        if (item.buttonStyle == 'link') {
          link.push(item)
        }
        if (item.buttonStyle == 'form') {
          if (item.optPosition == '1') {
            side.push(item)
          } else if (item.optPosition == '2') {
            end.push(item)
          }
        }
      })
      return {
        top,
        link,
        side,
        end,
      }
    },
    //下载导入模板
    downloadInportTemplateFun() {
      importDataTemplateApi(this.otherPortId ? this.otherPortId : this.currCodeId).then((res) => {
        downloadFileFun(res.data, this.tableDescribe)
      })
    },
    //父子表tabs切换方法
    tabsHandleChange(column) {
      this.tabsType = column
    },
    //监听文件上传
    uploadAfter(res, done) {
      this.viewFileNameObj = {
        ...this.viewFileNameObj,
        [res.link]: res.originalName,
      }
      done()
    },
    //操作列方法
    operationRowFun(row, index, type) {
      this.currentRowDataObj = row
      if (type == 'edit') {
        if (this.tableOption.addBtn) {
          this.$refs.codeTestList.rowEdit(row, index)
        } else {
          this.formDesignButtonTriggerFun(type, {
            row,
            index,
          })
        }
      }
    },
    //文件、图片上传超过限制上传数 提示
    uploadExceedFun(limit, files, fileList, column) {
      this.$message({
        showClose: true,
        message: `<${column.label}>只允许上传${limit}个文件`,
        type: 'warning',
      })
    },
    //markdown控件上传图片方法
    moavonEditorImgAdd(pos, $file, index) {
      const loading = this.$loading({
        lock: true,
        text: '正在上传图片，请耐心等待一会~',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      var formdata = new FormData()
      formdata.append('file', $file)
      uploadeFileApi(formdata)
        .then((res) => {
          let url = res.data.data.link
          this.$refs['moavonEditor_' + index][0].$img2Url(pos, url)
          loading.close()
        })
        .catch(() => {
          this.$message.error('上传图片失败，请重新上传~')
          loading.close()
        })
    },
    //表格获取数据
    initTableData(
      page = {
        currentPage: this.tablePage.currentPage,
        pageSize: this.tablePage.pageSize,
      },
      one = false
    ) {
      return new Promise(async (resolve) => {
        if (this.tableId && this.currMainDataId == '' && !this.tableView) {
          resolve()
          return false
        }
        //如果默认搜索对象值为空则不会获取表格数据
        let isGetData = true
        for (let key in this.defaultSearchObj) {
          if (this.defaultSearchObj[key] === '' || this.defaultSearchObj[key] === undefined || this.defaultSearchObj[key] === null) {
            isGetData = false
          }
        }
        if (!isGetData) {
          resolve()
          return false
        }

        if (!one) {
          this.isAvueTableLoading = true
        }
        this.tableSelectData = []
        this.tableSelectId = []
        let tableQueryData = {}
        if (this.isOneGetData) {
          this.tableQueryData = {
            ...this.tableQueryData,
            ...this.defaultSearchObj,
          }
          this.isOneGetData = false
        }
        //处理时间区间格式数据
        let timeObj = this.getSearchRangeControlFun(this.tableQueryData)
        this.tableQueryData = {
          ...this.tableQueryData,
          ...timeObj.data,
          ...this.searchObj,
        }

        for (let key in this.tableQueryData) {
          if (this.tableQueryData[key] instanceof Array) {
            tableQueryData[key] = this.tableQueryData[key].join(',')
          } else if (this.tableQueryData[key] !== '' && this.tableQueryData[key] !== undefined) {
            tableQueryData[key] = this.tableQueryData[key]
          }
          if (timeObj.remove.includes(key)) {
            delete this.tableQueryData[key + '_begin']
            delete this.tableQueryData[key + '_end']
          }
        }

        let data = {
          ...tableQueryData,
          ...this.tableAdvancedQueryData,
          ...this.sortData,
          ...this.tableOtherQueryData,
        }

        for (let key in data) {
          if (this.uniteFormKeyObj[key]) {
            data[key] = `${this.uniteFormKeyObj[key]}#eq#${data[key]}`
          }
        }
        if (this.tableIsPage) {
          data.pageNo = page.currentPage
          data.pageSize = page.pageSize
        } else {
          data.pageSize = -521
        }
        let tableDataRes = {}
        if (this.tableDataIsTree) {
          data.hasQuery = false
          tableDataRes = await getTreeDataApi(this.currCodeId, data)
        } else {
          if (this.foreignKeys && this.currMainDataId) {
            this.foreignKeys.forEach((item) => {
              data[item.field] = this.currMainDataObj[item.key]
            })
          }
          tableDataRes = await getDataApi(this.currCodeId, data)
        }
        tableDataRes = tableDataRes.data.data
        if (this.tableIsPage) {
          this.tablePage.total = tableDataRes.total
        }
        let timerInt = setInterval(async () => {
          if (this.isInitEnhance) {
            clearInterval(timerInt)
            let clData = await this.getTableListDataFun(tableDataRes.records)
            this.tableData = clData
            if (this.customOnlineEnhanceJsName.list.includes('getDataEnd')) {
              try {
                this.customOnlineEnhanceJsList.getDataEnd(this.that, tableDataRes)
              } catch (error) {
                console.warn(`${this.tableName}/${this.currCodeId} | getDataEnd方法执行异常：${error}`)
              }
            }
          }
        }, 200)

        if (!one) {
          this.isAvueTableLoading = false
        }
        resolve()
      })
    },
    //获取所有数据id 导出/批量操作使用
    getAllTableDataIdList() {
      return new Promise(async (resolve) => {
        this.isTableLoading = true
        let tableQueryData = {}
        this.tableQueryData = {
          ...this.tableQueryData,
          ...this.searchObj,
        }
        for (let key in this.tableQueryData) {
          if (this.tableQueryData[key] instanceof Array) {
            tableQueryData[key] = this.tableQueryData[key].join(',')
          } else if (this.tableQueryData[key] !== '' && this.tableQueryData[key] !== undefined) {
            tableQueryData[key] = this.tableQueryData[key]
          }
        }
        let data = {
          ...tableQueryData,
          ...this.tableAdvancedQueryData,
          ...this.sortData,
          pageNo: 1,
          pageSize: -521,
          allIdFlag: 1,
        }
        let tableDataRes = await getDataApi(this.currCodeId, data)
        tableDataRes = tableDataRes.data.data
        this.isTableLoading = false
        resolve(tableDataRes.idList)
      })
    },
    //表格选择事件触发
    selectionChangeFun(column) {
      // column 所有选择数据的数组

      this.tableSelectData = column
      let idArr = []
      column.forEach((item) => {
        idArr.push(item.id)
      })
      this.tableSelectId = idArr
      if (this.selectionTime) {
        clearTimeout(this.selectionTime)
      }
      this.selectionTime = setTimeout(() => {
        if (this.customOnlineEnhanceJsName.list.includes('selectionChange')) {
          try {
            this.customOnlineEnhanceJsList.selectionChange(this.that, column)
          } catch (error) {
            console.warn(`${this.tableName}/${this.currCodeId} | selectionChange方法执行异常：${error}`)
          }
        }
      }, 300)
    },
    // 窗口打开前
    async beforeOpenFun(done, type) {
      // 判断erp主题 附表一对一数据是否存在
      if (this.currMainDataId && this.tableType == 'form' && type == 'add') {
        let data = {
          pageNo: 1,
          pageSize: -521,
        }
        this.foreignKeys.forEach((item) => {
          data[item.field] = this.currMainDataObj[item.key]
        })
        let tableDataRes = await getDataApi(this.currCodeId, data)
        if (tableDataRes.data.data.total >= 1) {
          this.$message({
            message: '一对一的表只能新增一条数据',
            type: 'warning',
          })
          return false
        }
      }
      this.isTableLoading = true
      this.tabsType = this.tabsOption.column[0]
      this.isOpentForm = true
      this.tableCrudType = type

      this.tabsOption.column = this.tabsOption.column.map((item) => {
        item.dataList = []
        return item
      })
      //获取数据 设置数据值
      if (['edit', 'view'].includes(type)) {
        let detailRes = await getDataDetailApi(this.currCodeId, this.tableForm.id)
        if (detailRes.data.success) {
          let data = detailRes.data.data

          let subName = []
          let tableForm = {}
          let pacArr = []
          this.viewPcaArr.forEach((item) => {
            pacArr.push(item.fieldName)
          })
          this.tabsOption.column = this.tabsOption.column.map((item) => {
            subName.push(item.key)
            item.dataList = data[item.key]
            item.formParentDataId = this.tableForm[this.subDataIdKey]
            return item
          })
          for (let key in data) {
            if (!subName.includes(key)) {
              tableForm[key] = data[key]
            }
            if (pacArr.includes(key)) {
              tableForm[key] = tableForm[key].split(',')
            }
          }
          if (this.themeTemplate == 'tab') {
            //TAB主题(一对多)设置主表值
            this.tabsOption.column = this.tabsOption.column.map((item) => {
              if (item.id == this.currCodeId) {
                item.dataList = tableForm
              }
              return item
            })
          }
          if (!['tab'].includes(this.themeTemplate)) {
            this.tableForm = tableForm
          }
        }
      }

      //窗口打开前 调用js增强 beforeAdd beforeEdit 方法
      if (type == 'add' && this.customOnlineEnhanceJsName.list.includes('beforeAdd')) {
        try {
          this.customOnlineEnhanceJsList.beforeAdd(this.that)
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | beforeAdd方法执行异常：${error}`)
        }
      }
      if (type == 'edit' && this.customOnlineEnhanceJsName.list.includes('beforeEdit')) {
        try {
          this.customOnlineEnhanceJsList.beforeEdit(this.that, {
            ...this.tableForm,
          })
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | beforeEdit方法执行异常：${error}`)
        }
      }
      done()
      this.isTableLoading = false
      if (this.tableOption.addBtn) {
        setTimeout(() => {
          if ((type == 'add' || type == 'edit') && this.customButtonFormSide.length > 0) {
            this.broadsideButtonRealizeFun()
          }
          if (this.customOnlineEnhanceJsName.form.includes('loaded')) {
            //执行js增强 loaded方法
            try {
              this.customOnlineEnhanceJsForm.loaded(this.that)
            } catch (error) {
              console.warn(`${this.tableName}/${this.currCodeId} | loaded方法执行异常：${error}`)
            }
          }
        }, 300)
      }

      if (['edit', 'view'].includes(type)) {
        // 树表格设置表单上级文本
        if (this.tableDataIsTree) {
          this.setFormPidText(this.tableForm[this.tableTreeParentIdName])
        }
        //设置树控件表单上级文本
        if (this.viewAllTreeKey.length > 0) {
          this.viewAllTreeKey.forEach((item) => {
            let prop = this.findObject(this.tableOption.column, item)
            if (prop && prop.apiData) {
              this.setFormPidText(this.tableForm[item], prop.apiData.tableName, prop.apiData.text, item)
            }
          })
        }
        //设置省市区文本
        if (this.viewPcaArr.length > 0) {
          this.viewPcaArr.forEach((item) => {
            let text = this.getCurrPacDataTextFun(this.tableForm[item.fieldName])
            let length = this.timerInte.length
            this.timerInte[length] = setInterval(() => {
              let dom = document.querySelector(`label[for=${item.fieldName}]`)
              if (dom) {
                dom.parentNode.querySelector('input').value = text ? text : ''
                clearInterval(this.timerInte[length])
              }
            }, 300)
          })
        }
      }
    },
    //获取省市区数据
    getPacDicDataFun() {
      if (!this.provinces.province) {
        this.SET_PROVINCES({
          ...cityObj,
        })
        provinces = cityObj
      } else {
        provinces = this.provinces
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
        if (index == 0 && provinces.provinceData && provinces.provinceData[item]) {
          strArr.push(provinces.provinceData[item].area_name)
        }
        if (index == 1 && provinces.cityData && provinces.cityData[item]) {
          strArr.push(provinces.cityData[item].area_name)
        }
        if (index == 2 && provinces.districtData && provinces.districtData[item]) {
          strArr.push(provinces.districtData[item].area_name)
        }
      })
      return strArr.join(' / ')
    },
    //处理修改保存的数据
    setSaveOrUpdataFun(row) {
      return new Promise(async (resolve) => {
        //处理部门 用户控件数据空数组
        let nullArr = []
        let pacArr = []
        if (this.viewUserControlArr.length > 0 || this.viewDepartControlArr.length > 0 || this.viewPcaArr.length > 0) {
          this.viewUserControlArr.forEach((item) => {
            nullArr.push(item.fieldName)
          })
          this.viewDepartControlArr.forEach((item) => {
            nullArr.push(item.fieldName)
          })
          this.viewPcaArr.forEach((item) => {
            pacArr.push(item.fieldName)
          })
        }

        let rowKey = Object.keys(row)
        rowKey.forEach((key) => {
          if (nullArr.includes(key) && row[key] instanceof Array && row[key].length == 0) {
            row[key] = ''
          }
          if (pacArr.includes(key)) {
            row[key] = row[key].join(',')
          }
        })
        if (this.customOnlineEnhanceJsName.form.includes('updateSubmitDate')) {
          try {
            row = await this.customOnlineEnhanceJsForm.updateSubmitDate(this.that, row)
            resolve(row)
          } catch (error) {
            this.$message({
              message: error,
              type: 'warning',
            })
            resolve(row)
          }
        } else {
          resolve(row)
        }
      })
    },
    // 保存
    async rowSaveFun(row, done, loading) {
      let masterScheduleRes = await this.masterScheduleVerifyFun()
      if (!masterScheduleRes.res) {
        loading()
        return false
      }

      for (let key in masterScheduleRes.data) {
        if (!(masterScheduleRes.data[key] instanceof Array)) {
          masterScheduleRes.data[key] = [masterScheduleRes.data[key]]
        }
      }

      if (this.customOnlineEnhanceJsName.form.includes('beforeSubmit')) {
        try {
          await this.customOnlineEnhanceJsForm.beforeSubmit(this.that, row)
        } catch (error) {
          this.$message({
            message: error,
            type: 'warning',
          })
          loading()
          return false
        }
      }
      row = await this.setSaveOrUpdataFun(row)
      row = {
        ...row,
        ...masterScheduleRes.masterData,
        ...masterScheduleRes.data,
      }

      //erp主题 保存替换附表 跟主表绑定的字段数据
      if (this.currMainDataId) {
        this.foreignKeys.forEach((item) => {
          row[item.field] = this.currMainDataObj[item.key]
        })
      }
      addDataApi(this.currCodeId, row)
        .then(() => {
          this.$message({
            message: '新增成功~',
            type: 'success',
          })
          this.$refs.codeTestList.selectClear()
          this.tablePage.currentPage = 1
          this.initTableData()
          //树表格触发数据回显
          if (this.tableDataIsTree) {
            this.treeTableDataEcho('add')
          }

          done()
        })
        .catch((err) => {
          if (err) {
            this.$message.error(err)
          } else {
            this.$message.error('新增失败，请重新尝试~')
          }

          loading()
        })
    },
    // 编辑
    async rowUpdateFun(row, index, done, loading) {
      let masterScheduleRes = await this.masterScheduleVerifyFun()

      if (!masterScheduleRes.res) {
        loading()
        return false
      }

      for (let key in masterScheduleRes.data) {
        if (!(masterScheduleRes.data[key] instanceof Array)) {
          masterScheduleRes.data[key] = [masterScheduleRes.data[key]]
        }
      }

      row = await this.setSaveOrUpdataFun(row)
      row = {
        ...row,
        ...masterScheduleRes.masterData,
        ...masterScheduleRes.data,
      }
      //erp主题 编辑替换附表 跟主表绑定的字段数据
      if (this.currMainDataId) {
        this.foreignKeys.forEach((item) => {
          row[item.field] = this.currMainDataObj[item.key]
        })
      }

      if (this.customOnlineEnhanceJsName.form.includes('editSubmit')) {
        try {
          await this.customOnlineEnhanceJsForm.editSubmit(this.that, row)
        } catch (error) {
          this.$message({
            message: error,
            type: 'warning',
          })
          loading()
          return false
        }
      }

      editDataApi(this.currCodeId, row)
        .then((res) => {
          this.$message({
            message: '修改成功~',
            type: 'success',
          })
          this.$refs.codeTestList.selectClear()
          this.initTableData()
          //树表格触发数据回显
          if (this.tableDataIsTree) {
            this.treeTableDataEcho('edit')
          }

          done()
        })
        .catch((err) => {
          this.$message.error('修改失败，请重新尝试~')

          loading()
        })
    },
    // 保存和编辑之前对 主附表进行校验并获取数据
    masterScheduleVerifyFun() {
      return new Promise(async (resolve) => {
        //不需要校验主附表
        if (!this.isTableInfo) {
          resolve({ res: true })
          return false
        }
        //校验 所有一对一附表
        let verifyErrorArr = []
        let allPromise = []
        if (this.themeTemplate == 'tab') {
          this.$refs.codeMasterlistForm.forEach((item) => {
            allPromise.push(item.verifyFormFun())
          })
        }
        if (this.$refs.codeSublistForm && this.$refs.codeSublistForm.length > 0) {
          this.$refs.codeSublistForm.forEach((item) => {
            allPromise.push(item.verifyFormFun())
          })
        }
        if (this.$refs.codeSublistTable && this.$refs.codeSublistTable.length) {
          this.$refs.codeSublistTable.forEach((item) => {
            allPromise.push(item.verifyFormFun())
          })
        }

        let verifyArr = await Promise.all(allPromise)
        verifyArr.forEach((item) => {
          if (!item.res) {
            verifyErrorArr.push(item)
          }
        })

        if (verifyErrorArr.length > 0) {
          resolve({ res: false })
          this.tabsOption.column.forEach((item, index) => {
            if (item.prop == verifyErrorArr[0].tabName) {
              document.querySelector(`#tab-${index}`).click()
            }
          })
          // this.$message.error('保存失败，请检查附表内容是否填写正确！')
          return false
        }
        //校验成功后返回所有 主附表数据
        let allTableData = {}
        let masterData = {}
        verifyArr.forEach((item) => {
          if (item.type == 'tab') {
            masterData = item.data
          } else {
            allTableData = {
              ...allTableData,
              ...item.data,
            }
          }
        })
        resolve({ res: true, data: allTableData, masterData })
      })
    },
    // 删除
    rowDelFun(row) {
      if (this.customOnlineEnhanceJsName.list.includes('beforeDelete')) {
        try {
          this.customOnlineEnhanceJsList.beforeDelete(this.that, row)
        } catch (error) {
          console.warn(error)
        }
      }
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          delDataApi(this.currCodeId, row.id)
            .then((res) => {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.tablePage.currentPage = 1
              this.initTableData()
              //树表格触发数据回显
              if (this.tableDataIsTree) {
                this.treeTableDataEcho('del', row)
              }
              this.$refs.codeTestList.selectClear()
            })
            .catch(() => {
              this.$message.error('删除失败，请重新尝试~')
            })
        })
        .catch(() => {})
    },
    //批量删除
    deleteAllSelectData() {
      if (this.tableSelectId.length <= 0) {
        this.$message({
          message: '请先选择需要删除的数据~',
          type: 'warning',
        })
        return false
      }

      this.$confirm(`此操作将永久已选择的${this.tableSelectId.length}条记录, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          delDataApi(this.currCodeId, this.tableSelectId.join(','))
            .then((res) => {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.tablePage.currentPage = 1
              this.initTableData()
              this.$refs.codeTestList.toggleSelection('')
            })
            .catch(() => {
              this.$message.error('删除失败，请重新尝试~')
            })
        })
        .catch(() => {})
    },
    //表格树懒加载
    async treeLoadFun(tree, treeNode, resolve) {
      const pid = tree.id
      this.maps.set(pid, { tree, treeNode, resolve })
      let data = {
        column: 'id',
        order: 'desc',
        hasQuery: false,
        // pageNo:1,
        // pageSize:10,
        pid,
      }
      let tableDataRes = await getTreeDataApi(this.currCodeId, data)

      if (tableDataRes.data.success) {
        let resData = tableDataRes.data.data.records
        if (!resData) {
          resData = []
        }
        resData = resData.map((item) => {
          item.hasChildren = item[this.tableTreeChildern] === '0' ? true : false
          return item
        })

        let timerInt = setInterval(async () => {
          if (this.isInitEnhance) {
            clearInterval(timerInt)
            resData = await this.getTableListDataFun(resData)
            if (this.customOnlineEnhanceJsName.list.includes('getDataEnd')) {
              try {
                this.customOnlineEnhanceJsList.getDataEnd(this.that, tableDataRes)
              } catch (error) {
                console.warn(`${this.tableName}/${this.currCodeId} | getDataEnd方法执行异常：${error}`)
              }
            }
            resolve(resData)
          }
        }, 200)
      } else {
        resolve([])
      }
    },
    //表格树 数据回显逻辑
    async treeTableDataEcho(type, row) {
      if (type == 'del') {
        this.$set(this.$refs.codeTestList.$refs.table.store.states.lazyTreeNodeMap, row.pid, [])
      }
      this.maps.forEach((item, key) => {
        const { tree, treeNode, resolve } = this.maps.get(key)
        this.treeLoadFun(tree, treeNode, resolve)
      })
    },
    // 窗口关闭前
    beforeCloseFun(done, type) {
      this.isOpentForm = false

      //处理树表格默认值
      if (this.tableDataIsTree) {
        this.timerInte.forEach((item) => {
          if (item) {
            clearInterval(item)
          }
        })
        this.timerInte = []
        let column = this.findObject(this.tableOption.column, this.tableTreeParentIdName)
        column.value = ''
        column.addDisabled = false
      }
      //处理被js增强 修改过的下拉框
      this.tableOption.column = this.tableOption.column.map((item) => {
        if (item.oldDicDate && item.oldDicDate.isReplace) {
          item.dicData = this.deepClone(item.oldDicDate.dicData)
          item.oldDicDate = {}
        }
        return item
      })
      if (this.customOnlineEnhanceJsName.list.includes('beforeClose')) {
        try {
          this.customOnlineEnhanceJsList.beforeClose(this.that, type)
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | beforeClose方法执行异常：${error}`)
        }
      }

      done()
    },
    // 搜索
    searchChangeFun(params, done) {
      this.tableQueryData = params
      this.tableQueryClickData = params
      this.tablePage.currentPage = 1
      if (this.$refs.codeTestList) {
        this.$refs.codeTestList.selectClear()
      }
      this.initTableData()
      done()
    },
    // 清除搜索
    searchResetFun() {
      this.tableQueryData = {}
      this.tableOption.column.forEach((item) => {
        if (item.emptySearch) {
          this.tableQueryData[item.prop] = item.emptySearch
        }
      })
      this.tableQueryClickData = {}
      this.tablePage.currentPage = 1
      this.$refs.codeTestList.selectClear()
      this.initTableData()
    },
    // 切换页
    currentChangeFun(page) {
      this.tablePage.currentPage = page
      this.initTableData()
    },
    // 切换每页显示数
    sizeChangeFun(pageSize) {
      this.tablePage.currentPage = 1
      this.tablePage.pageSize = pageSize
      this.initTableData()
    },
    //排序逻辑处理
    sortChange({ column, prop, order }) {
      let type = 'desc' //降序
      if (order == 'ascending') {
        //升序
        type = 'asc'
      }
      if (order == null) {
        this.sortData = {
          column: 'id',
          order: 'desc',
        }
      } else {
        this.sortData = {
          column: prop,
          order: type,
        }
      }
      this.tablePage.currentPage = 1
      this.initTableData()
    },
    //行单击事件
    rowClick(row, column, event) {
      if (this.customOnlineEnhanceJsName.list.includes('rowClick')) {
        try {
          return this.customOnlineEnhanceJsList.rowClick(this.that, {
            row,
            column,
            event,
          })
        } catch (error) {
          console.warn(`${this.tableName}/${this.currCodeId} | rowClick${error}`)
        }
      }
    },
    //表格格式数据处理
    setTableDataFun(columsList, headData) {
      return new Promise((resolve) => {
        let headObjKeys = Object.keys(headData)
        let formSpan = 24 //表单列布局 span属性
        headObjKeys.forEach((item) => {
          let value = headData[item]
          switch (item) {
            case 'formTemplate':
              formSpan = formSpan / (value - 0)
              break
            case 'isCheckbox':
              if (value === 'Y') {
                this.tableOption.selection = true
                this.tableOption.reserveSelection = true
              }
              break
            case 'indexShow':
              if (value === 'Y') {
                this.tableOption.index = true
              }
              break
            case 'indexTitle':
              if (value) {
                this.tableOption.indexLabel = value
              }
              break
            case 'isPage':
              if (value === 'Y') {
                this.tableIsPage = true
                this.tablePage = {
                  total: 0,
                  currentPage: 1,
                  pageSize: 10,
                  pageSizes: [10, 20, 30, 50],
                  background: true,
                  layout: 'sizes, prev, pager, next, jumper,total',
                }
              } else {
                this.displayModeType = 'bigData'
                this.tableOption.height = 400
              }
              break
            case 'isTree':
              if (value === 'Y') {
                this.tableColumnMoreButton.splice(1, 0, {
                  type: 'treeChildern',
                  text: '添加下级',
                  permissionName: 'moreChildBtn',
                })
                this.tableDataIsTree = true
                this.tableTreeParentIdName = headData.treeParentIdField
                this.tableTreeUnfoldName = headData.treeFieldname
                this.tableTreeChildern = headData.treeIdField
                this.tableOption.lazy = true
                this.tableOption.tree = true
              } else {
                this.tableOption.lazy = false
              }
              break
            case 'isDesForm':
              if (value === 'Y') {
                this.tableOption.addBtn = false
                this.getDesFormOptionDataFun(headData.desFormCode)
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
            case 'menuStyle':
              if (value === 'Y') {
                this.isLinkPullDown = true
              } else {
                this.isLinkPullDown = false
              }
              break
            case 'basicFunction':
              this.setBasicFunctionFun(value)
              break
            case 'isTableData':
              if (value == 'Y') {
                this.isTableGetData = false
              }
              break
            default:
              break
          }
        })

        //先对obj排序
        let columsObj = {}
        columsList.forEach((item) => {
          columsObj[item.dbFieldName] = item
        })
        let untreatedColumn = []
        let unllOrderNum = []
        for (let key in columsObj) {
          let value = columsObj[key]
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
          //树结构
          //  文本框:text  密码:password  下拉框:list  单选框:radio  多选框:checkbox  开关:switch
          //  日期(yyyy-MM-dd):date  日期（yyyy-MM-dd HH:mm:ss）:datetime  时间（HH:mm:ss）:time
          //  文件:file  图片:image  多行文本:textarea  下拉多选框:list_multi  下拉搜索框:sel_search
          let columnItem = {
            label: item.dbFieldTxt, //文本
            prop: item.dbFieldName, //字段名
            span: formSpan,
            value: item.fieldDefaultValue, //默认值
            showColumn: true,
            minWidth: item.fieldLength,

            // 配置默认字段（防止动态修改不生效）
            display: true,
            hide: false,
          }
          //是否需要联表查询
          if (item.uniteFormKey) {
            this.uniteFormKeyObj[item.dbFieldName] = item.uniteFormKey
          }
          //单独占一行
          if (this.fieldSpanOneLine.includes(item.fieldShowType)) {
            columnItem.span = 24
          }
          columnItem.order = untreatedColumn.length - index
          //查询配置
          if (item.isShowSearch === 1) {
            columnItem.search = true
            //自定义查询
            if (item.queryConfigFlag == '1') {
              //默认值
              if (item.queryDefVal !== '' || item.queryDefVal !== undefined) {
                columnItem.searchValue = item.queryDefVal
              }
              this.customSearchArr.push(item)
            }
          }
          //开启排序
          if (item.sortFlag === '1') {
            columnItem.sortable = 'custom'
          }
          //只读
          if (item.isReadOnly === 1) {
            columnItem.readonly = true
          }
          //是否可控
          if (item.isShowColumn === 1) {
            columnItem.showColumn = true
          } else {
            columnItem.showColumn = false
          }
          //表单不显示
          if (item.isShowForm === 0 || this.themeTemplate == 'tab') {
            columnItem.display = false
          }
          if (item.isShowForm !== 0 && this.themeTemplate == 'tab') {
            columnItem.alterDisplay = true
          }
          //列表不显示
          if (item.isShowList === 0) {
            columnItem.hide = true
          }
          /* ====== 控件处理 ===== */
          columnItem.dbType = item.dbType
          // 如果是erp表格的附表,一些控件无效
          if (this.tableId != '' && !this.erpControlsArr.includes(item.fieldShowType) && !this.tableView) {
            tableColumn.push(columnItem)
            return false
          }
          //超出隐藏
          if (['textarea', 'umeditor', 'markdown', 'monaco-editor'].includes(item.fieldShowType)) {
            columnItem.overHidden = true
          }
          //数据格式化
          if (['checkbox', 'radio', 'switch', 'list_multi', 'sel_search', 'sel_depart', 'sel_user', 'table-select', 'cat_tree', 'map'].includes(item.fieldShowType)) {
            if (['int', 'Double', 'BigDecimal'].includes(item.dbType)) {
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
              if (columnItem.value !== '' && columnItem.value !== undefined && typeof columnItem.value == 'string') {
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
                  console.warn(`<${item.dbFieldTxt}>自定义参数配置错误,需要符合json格式`)
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
                if (columnItem.value === '' && columnItem.value === undefined) {
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
                if (columnItem.value === '' && columnItem.value === undefined) {
                  columnItem.value = 'N'
                }
              }
            }

            //省市区
            if (item.fieldShowType == 'pca') {
              this.isProvinces = true
              this.viewPcaArr.push({
                fieldName: item.dbFieldName, //字段名
                fieldPcaName: item.dbFieldName,
              })
              columnItem = {
                ...columnItem,
                separator: '/',
                props: {
                  label: 'area_name',
                  value: 'area_id',
                },
                lazy: true,
                lazyLoad: (node, resolve) => {
                  let level = node.level
                  let data = node.data || {}
                  let area_id = data.area_id
                  let list = []
                  let callback = () => {
                    setTimeout(() => {
                      resolve(
                        (list || []).map((ele) => {
                          return Object.assign(ele, {
                            leaf: ele.leaf,
                          })
                        })
                      )
                    }, 0)
                  }
                  if (level == 0) {
                    list = this.provinces.province
                    callback()
                  }
                  if (level == 1) {
                    list = this.provinces.city[area_id]
                    callback()
                  } else if (level == 2) {
                    list = this.provinces.district[area_id]
                    callback()
                  }
                },
              }
            }
            this.dicAllData[item.prop] = columnItem.dicData
          }
          //下拉搜索配置
          if (item.fieldShowType == 'sel_search') {
            //表名  存储字段值  显示字段值
            if (item.dictTable != '' && item.dictField != '' && item.dictText != '') {
              columnItem = {
                ...columnItem,
                dicUrl: `/api/${this.apiRequestHead}/sys/sys/dict/getDict/${item.dictTable},${item.dictText},${item.dictField}`,
                dicFlag: true,
                dicQuery: {
                  keyword: '',
                },
                props: {
                  label: 'title',
                  value: 'value',
                },
                dicFormatter: (res) => {
                  return res.data
                },
              }
            } else {
              if (item.dictTable != '' || item.dictField != '' || item.dictText != '') {
                this.$message({
                  message: `<${item.dbFieldTxt}>下拉搜索控件的字典配置错误，需要完整配置字典table、字典code、字典text`,
                  type: 'warning',
                })
              }

              columnItem.dicData = []
            }
          }
          //配置树字典
          if (this.viewListTree.includes(item.fieldShowType)) {
            this.viewAllTreeKey.push(item.dbFieldName)
            if (item.fieldShowType == 'cat_tree') {
              //存储需要保存的文本字段名
              let dictText = item.dictText
              item.dicCodeValue = item.dictField
              //如果是分类字典树控件 就给默认值
              item.dictText = 'id,pid,name,has_child'
              item.dictField = this.tableColumnItemForm.schema.properties[item.dbFieldName] ? this.tableColumnItemForm.schema.properties[item.dbFieldName].id : undefined
              item.dictTable = 'sys_category'
              if (dictText) {
                //保存树id对应文本
                columnItem.control = async (val) => {
                  if (val) {
                    let itemRes = await getTreeItemDataApi({
                      tableName: 'sys_category',
                      tableLine: 'name',
                      rowKey: 'id',
                      key: val,
                    })
                    if (itemRes.data.success) {
                      if (this.themeTemplate == 'tab') {
                        this.$refs.codeMasterlistForm[0].tableForm[dictText] = itemRes.data.data[0]
                      } else {
                        this.tableForm[dictText] = itemRes.data.data[0]
                      }
                    }
                  } else {
                    if (this.themeTemplate == 'tab') {
                      this.$refs.codeMasterlistForm[0].tableForm[dictText] = ''
                    } else {
                      this.tableForm[dictText] = ''
                    }
                  }
                  return {}
                }
              }
            }
            if (item.dictText && item.dictText.split(',').length == 4) {
              let keyList = item.dictText.split(',')
              let apiData = {
                pid: item.dictField === undefined ? 0 : item.dictField,
                tableName: item.dictTable,
                text: keyList[2],
                code: keyList[0],
                pidField: keyList[1],
                hasChildField: keyList[3],
                condition: '',
              }
              if (item.fieldShowType == 'cat_tree') {
                apiData.condition = `{code:${item.dicCodeValue}}`
              }
              //获取树数据配置
              columnItem = {
                ...columnItem,
                apiData,
                props: {
                  label: 'title',
                  value: 'key',
                },
                filter: false,
                dicFlag: true,
                lazy: true,
                dicUrl: `/api/${this.apiRequestHead}/sys/loadTreeData`,
                dicQuery: apiData,
                treeLoad: async (node, resolve) => {
                  let apiObj = this.deepClone(apiData)
                  if (node.level != 0) {
                    apiObj.pid = node.data.key
                    apiObj.condition = ''
                  }
                  let treeRes = await getTreeAllDataApi(apiObj)
                  if (treeRes.data.success) {
                    resolve(treeRes.data.data)
                  } else {
                    resolve([])
                  }
                },
              }
            }
          }
          //markdown控件
          if (item.fieldShowType == 'markdown') {
            columnItem.formslot = true
            this.viewMarkdownArr.push({
              fieldName: item.dbFieldName,
              fieldMarkDownName: item.dbFieldName,
            })
          }
          //富文本控件
          if (item.fieldShowType == 'umeditor') {
            columnItem = {
              ...columnItem,
              component: 'AvueUeditor',
              params: {
                options: {
                  action: `api/${this.apiRequestHead}/cgform-api/upload/file`,
                  props: {
                    res: 'data',
                    url: 'link',
                  },
                },
              },
            }
          }
          //文件 图片
          if (['image', 'file'].includes(item.fieldShowType)) {
            columnItem.type = 'upload'
            columnItem.action = `api/${this.apiRequestHead}/cgform-api/upload/file`
            columnItem.propsHttp = {
              res: 'data',
              url: 'link',
              name: 'originalName', //阿里云限制死了文件名 此配置无效 文件名只能是阿里云上的文件名 需要逻辑替换
            }
            columnItem.dataType = 'string'
            if (item.fieldShowType == 'image') {
              columnItem.listType = 'picture-card'
              columnItem.accept = 'image/*'
              columnItem.data = {
                type: 0,
              }
            }
            if (item.fieldShowType == 'file') {
              columnItem.data = {
                type: 1,
              }
              columnItem.slot = true
              this.viewFileArr.push({
                fieldName: item.dbFieldName,
              })
            }
          }
          //用户控件
          if (item.fieldShowType == 'sel_user') {
            columnItem = {
              ...columnItem,
              type: 'select',
              formslot: true,
              multiple: true,
              dicData: this.allUserData[columnItem.prop],
              props: {
                label: 'realName',
                value: 'id',
              },
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
              dicData: this.allDepartData[columnItem.prop],
              props: {
                label: 'deptName',
                value: 'id',
              },
              isTree: true,
            }
            this.viewDepartControlArr.push({
              fieldName: item.dbFieldName, //字段名
              fieldDepartName: item.dbFieldName, //字段名
            })
          }
          if (item.fieldShowType == 'table-select') {
            columnItem = {
              ...columnItem,
              type: 'select',
              formslot: true,
              multiple: true,
              dicData: [],
              props: {
                label: 'label',
                value: 'id',
              },
            }
            this.viewTableSelectArr.push({
              fieldName: item.dbFieldName, //字段名
              fieldTableSelectName: item.dbFieldName, //字段名
            })
          }

          if (item.fieldShowType == 'monaco-editor') {
            columnItem = {
              ...columnItem,
              editorType: 'javascript',
              editorHeight: '200px',
            }
            this.viewMonacoEditor.push({
              monacoName: item.dbFieldName,
            })
          }
          //联动控件
          if (item.fieldShowType == 'link_down') {
            let linkObj = this.tableColumnItemForm.schema.properties[item.dbFieldName]
            if (linkObj) {
              let linkFieldArr = linkObj.config.linkField.split(',')
              let linkDownObj = {
                dicTable: linkObj.config,
                fieldName: item.dbFieldName,
                allFieldName: [item.dbFieldName, ...linkFieldArr],
              }
              this.viewLinkDownArr.push(linkDownObj)
              linkDownObj.allFieldName.forEach((fieldItem) => {
                this.viewLinkDownFieldArr.push({
                  fieldName: fieldItem,
                  fieldLinkDownName: fieldItem,
                  parentName: item.dbFieldName,
                })
              })
              this.viewLinkDownDicObj[item.dbFieldName] = {}
              if (this.tableColumnDic[item.dbFieldName]) {
                let dicArr = this.tableColumnDic[item.dbFieldName]
                dicArr.forEach((dicItem) => {
                  let key = dicItem.value + ''
                  this.viewLinkDownDicObj[item.dbFieldName][key] = dicItem.label
                })
              }
              columnItem = {
                ...columnItem,
                dicUrl: `/api/${this.apiRequestHead}/cgform-api/querySelectOptions`,
                dicFlag: true,
                dicQuery: {
                  table: linkDownObj.dicTable.table,
                  txt: linkDownObj.dicTable.txt,
                  key: linkDownObj.dicTable.key,
                  idField: linkDownObj.dicTable.idField,
                  pidField: linkDownObj.dicTable.pidField,
                  condition: linkDownObj.dicTable.condition,
                },
                cascaderItem: [...linkDownObj.allFieldName.filter((fItem) => item.dbFieldName != fItem)],
                type: 'select',
                props: {
                  label: 'label',
                  value: 'id',
                },
                dicFormatter: (res) => {
                  return res.data
                },
              }
            }
          }
          //地图控件
          if (item.fieldShowType == 'map') {
            columnItem.type = 'map'
            columnItem.params = {}
            this.viewMapArr.push(columnItem.prop)
          }
          // 开启时间范围查询
          if (['date', 'time', 'datetime'].includes(item.fieldShowType) && item.isShowSearch && item.queryMode == 'group') {
            columnItem.searchRange = true
            columnItem.searchSpan = 6
            columnItem.dataType = 'string'
          }
          //处理字段类型
          switch (item.fieldShowType) {
            case 'text':
              //文本框
              if (['int', 'Double', 'BigDecimal'].includes(item.dbType)) {
                columnItem.type = 'number'
              }
              break
            case 'password':
              columnItem.type = 'password'
              //密码
              break
            case 'list':
              columnItem.type = 'select'
              //下拉框
              break
            case 'radio':
              columnItem.type = 'radio'
              //单选框
              break
            case 'checkbox':
              columnItem.type = 'checkbox'
              //多选框
              break
            case 'switch':
              columnItem.type = 'switch'
              //开关
              break
            case 'date':
              columnItem.type = 'date'
              columnItem.format = 'yyyy-MM-dd'
              columnItem.valueFormat = 'yyyy-MM-dd'
              break
            case 'datetime':
              columnItem.type = 'datetime'
              columnItem.format = 'yyyy-MM-dd HH:mm:ss'
              columnItem.valueFormat = 'yyyy-MM-dd HH:mm:ss'
              break
            case 'time':
              columnItem.type = 'time'
              columnItem.valueFormat = 'HH:mm:ss'
              break
            case 'textarea':
              columnItem.type = 'textarea'
              //多行文本
              break
            case 'list_multi':
              columnItem.type = 'select'
              columnItem.multiple = true
              //下拉多选框
              break
            case 'sel_search':
              columnItem.type = 'select'
              columnItem.filterable = true
              //下拉搜索框
              break
            case 'pca':
              columnItem.type = 'cascader'
              columnItem.filterable = true
              //省市区
              break
            case 'sel_tree':
              columnItem.type = 'tree'
              //自定义树控件
              break
            case 'cat_tree':
              columnItem.type = 'tree'
              //分类字典树控件
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
              if (key == 'uploadnum' && ['image', 'file'].includes(item.fieldShowType)) {
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
                message: '请为<' + item.dbFieldTxt + '>配置正确格式的扩展参数（例：{"uploadnum":2,"showLength":200}）',
                duration: 5000,
                type: 'warning',
              })
            }
          }

          //自定义组件
          if (item.fieldShowType == 'self-defined') {
            if (!this.initSelfDefinedArr.includes(columnItem.component)) {
              try {
                Vue.component(columnItem.component, (res) => require([`@/${columnItem.componentPath}`], res))
                this.initSelfDefinedArr.push(columnItem.component)
              } catch (error) {
                console.warn(`${item.component}自定义组件注册异常,${error}`)
              }
            }
          }

          //树表格展开列配置
          if (this.tableDataIsTree && item.dbFieldName == this.tableTreeParentIdName) {
            columnItem = {
              ...columnItem,
              type: 'tree',
              multiple: false,
              filter: false,
              lazy: true,
              dicFlag: true,
              dicUrl: `/api/${this.apiRequestHead}/sys/loadTreeData`,
              dicQuery: {
                pid: 0,
                tableName: this.tableName, //数据库表名
                text: this.tableTreeUnfoldName, //展开列字段名
                code: 'id', //主键名
                pidField: this.tableTreeParentIdName, //父id名
                hasChildField: this.tableTreeChildern, //是否有子集key名
                condition: '',
              },
              dicFormatter: (res) => {
                return res.data
              },
              props: {
                label: 'title',
                value: 'key',
              },
              treeLoad: async (node, resolve) => {
                if (node.data instanceof Array && node.level != 0) {
                  return false
                }
                let treeRes = await getTreeAllDataApi({
                  pid: node.data.key ? node.data.key : 0,
                  tableName: this.tableName, //数据库表名
                  text: this.tableTreeUnfoldName, //展开列字段名
                  code: 'id', //主键名
                  pidField: this.tableTreeParentIdName, //父id名
                  hasChildField: this.tableTreeChildern, //是否有子集key名
                  condition: '',
                })
                if (treeRes.data.success) {
                  resolve(treeRes.data.data)
                } else {
                  resolve([])
                }
              },
            }
          }

          //处理校验规则
          columnItem.rules = []
          if (item.fieldValidType) {
            let rules = codeListRules[item.fieldValidType] ? codeListRules[item.fieldValidType] : {}
            if (rules.type == 'all' && rules.pattern == 'only') {
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
                  callback(new Error(`不可用，系统中已存在！`))
                }
              }
            }
            if (rules.pattern != 'only' && this.tableNeetRules.includes(item.fieldShowType) && rules.type.includes(item.dbType)) {
              let reg = new RegExp(rules.pattern)
              validateRulesAll[item.dbFieldName] = (rule, value, callback) => {
                if (!reg.test(value)) {
                  callback(new Error(rules.msg))
                } else {
                  callback()
                }
              }
            }
            if (validateRulesAll[item.dbFieldName]) {
              columnItem.rules.push({
                validator: validateRulesAll[item.dbFieldName],
                trigger: 'blur',
              })
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
          if (!['date', 'datetime', 'time'].includes(item.fieldShowType) && !['Text'].includes(item.dbType)) {
            columnItem.rules.push({
              validator: (rule, value, callback) => {
                value = value + ''
                if (value.length > item.dbLength) {
                  callback(new Error('超过最大长度'))
                } else {
                  callback()
                }
              },
              trigger: 'blur',
            })
          }

          //处理字典
          tableColumn.push(columnItem)
        })

        //联动处理
        let linkDown = {}
        this.viewLinkDownArr.forEach((linkItem) => {
          linkItem.allFieldName.forEach((item, index) => {
            if (index != 0) {
              let currField = {}
              currField = {
                type: 'select',
                props: {
                  label: 'label',
                  value: 'id',
                },
                dicUrl: `api/${this.apiRequestHead}/cgform-api/querySelectOptions?table=${linkItem.dicTable.table}&txt=${linkItem.dicTable.txt}&key=${linkItem.dicTable.key}&idField=${linkItem.dicTable.idField}&pidField=${linkItem.dicTable.pidField}&pidValue={{key}}`,
                dicFormatter: (res) => {
                  return res.data
                },
              }
              linkDown[item] = currField
            }
          })
        })
        tableColumn = tableColumn.map((tableItem) => {
          if (linkDown[tableItem.prop]) {
            return {
              ...tableItem,
              ...linkDown[tableItem.prop],
            }
          }
          if (tableItem.type == 'upload' && tableItem.listType == 'picture-img') {
            tableItem.span = formSpan
          }
          return tableItem
        })
        resolve(tableColumn)
      })
    },
    //设置基础按钮功能
    setBasicFunctionFun(value) {
      value = value.split(',')
      let basicArr = ['editBtn', 'addBtn', 'moreViewBtn', 'moreDelBtn', 'allDelBtn', 'inportBtn', 'exportBtn']
      basicArr.forEach((item) => {
        if (value.includes(item) && (!this.isAuthBtn || (this.isAuthBtn && this.permission[`${item}_${this.currCodeId}${this.currCodeType}`]))) {
          this.tablePermission[item] = true
          if (item == 'addBtn' && this.tableDataIsTree) {
            this.tablePermission.moreChildBtn = true
          }
        } else {
          this.tablePermission[item] = false
          if (item == 'addBtn' && this.tableDataIsTree) {
            this.tablePermission.moreChildBtn = false
          }
        }
      })
    },
    //查询配置 搜索处理
    customSearchFun(data) {
      return new Promise(async (resolve) => {
        let obj = {}
        if (['list', 'sel_search', 'list_multi'].includes(data.queryValidType) && data.queryDictField) {
          let dicData = []
          if (data.queryDictField && !data.queryDictTable && !data.queryDictText) {
            let dicRes = await getDicTableData(data.queryDictField)
            if (dicRes.data.success) {
              dicData = dicRes.data.data
            }
          } else {
            let dicRes = await getTableDicData(data.queryDictTable, data.queryDictText, data.queryDictField)
            if (dicRes.data.success) {
              dicData = dicRes.data.data
            }
          }
          obj.dicData = dicData
        }
        //处理字段类型
        switch (data.queryValidType) {
          case 'list':
            obj.searchType = 'select'
            //下拉框
            break
          case 'date':
            obj.searchType = 'date'
            break
          case 'datetime':
            obj.searchType = 'datetime'
            break
          case 'time':
            obj.searchType = 'time'
            break
          case 'list_multi':
            obj.searchType = 'select'
            obj.searchMultiple = true
            //下拉多选框
            break
          case 'sel_search':
            obj.searchType = 'select'
            obj.searchFilterable = true
            obj.searchMultiple = false
            //下拉搜索框
            break
          default:
            break
        }
        resolve(obj)
      })
    },
    //表格处理数据
    getTableListDataFun(data) {
      return new Promise(async (resolve) => {
        let setData = (data) => {
          data = data.map((item) => {
            //自定义按钮 link 校验
            if (this.customButtonLink.length > 0) {
              this.customButtonLink.forEach((linkItem) => {
                let isShow = this.linkButtonFiltersFun({
                  exp: linkItem.exp,
                  row: item,
                })
                if (this.isAuthBtn) {
                  if (this.permission[`${linkItem.buttonCode}_${this.currCodeId}${this.currCodeType}`] && isShow) {
                    item['$link$' + linkItem.buttonCode] = true
                  } else {
                    item['$link$' + linkItem.buttonCode] = false
                  }
                } else {
                  item['$link$' + linkItem.buttonCode] = isShow
                }
              })
            }
            //文件名处理
            if (this.viewFileArr.length > 0) {
              this.viewFileArr.forEach((fieldItem) => {
                if (item[fieldItem.fieldName]) {
                  let fileArr = item[fieldItem.fieldName].split(',')
                  let fileInfo = []
                  fileArr.forEach(async (fileArrItem) => {
                    getUploadeFileNameApi(fileArrItem).then((fileRes) => {
                      let fileName = fileArrItem.split('/')
                      fileName = fileName[fileName.length - 1]
                      if (fileRes.data.success && fileRes.data.data) {
                        fileName = fileRes.data.data
                      }
                      this.viewFileNameObj = {
                        ...this.viewFileNameObj,
                        [fileArrItem]: fileName,
                      }
                      fileInfo.push({
                        url: fileArrItem,
                        name: fileName,
                      })
                    })
                  })
                  item['$File' + fieldItem.fieldName] = fileInfo
                }
              })
            }
            //省市区处理
            if (this.viewPcaArr.length > 0) {
              this.viewPcaArr.forEach((pcaItem) => {
                let key = item[pcaItem.fieldName]
                if (key) {
                  let strArr = this.getCurrPacDataTextFun(key)
                  this.viewPcaNameObj = {
                    ...this.viewPcaNameObj,
                    [key]: strArr,
                  }
                }
              })
            }
            //树表格处理
            if (this.tableDataIsTree) {
              item.hasChildren = item[this.tableTreeChildern] === '0' ? true : false
              if (item.children && item.children.length > 0) {
                item.children = setData(item.children)
              }
            }
            return item
          })
          return data
        }
        data = setData(data)
        if (this.isInitEnhance) {
          if (this.customOnlineEnhanceJsName.list.includes('setDataFun')) {
            try {
              data = await this.customOnlineEnhanceJsList.setDataFun(this.that, data)
            } catch (error) {
              console.warn(`${this.tableName}/${this.currCodeId} | setDataFun方法执行异常：${error}`)
            }
          }
        }
        resolve(data)
        //刷新布局
        if (this.displayModeType == 'bigData') {
          this.$nextTick(() => {
            this.currentStartIndex = 0
            this.currentEndIndex = 50
            this.$refs.codeTestList.doLayout()
            this.$refs.codeTestList.refreshTable()
            setTimeout(() => {
              this.setInuptEventFun()
            }, 0)
          })
        }
        this.isAvueTableLoading = false
      })
    },
    //操作栏更多
    async moreButtonCommand(command) {
      this.currentRowDataObj = command.row
      let type = command.type
      if (type == 'view') {
        if (this.tableOption.addBtn) {
          this.$refs.codeTestList.rowView(command.row, command.index)
        } else {
          this.formDesignButtonTriggerFun(type, command)
        }
      }
      if (type == 'treeChildern' && this.tableDataIsTree) {
        //添加下级
        const column = this.findObject(this.tableOption.column, this.tableTreeParentIdName)
        column.value = command.row.id
        column.addDisabled = true
        this.setFormPidText(command.row.id)
        this.$refs.codeTestList.rowAdd()
      }
      if (type == 'del') {
        this.rowDelFun(command.row, command.index)
      }
      if (command.buttonCode) {
        this.allCustomButtonFun(command.buttonCode, command.buttonStyle, command.optType, command.that, command.row)
      }
    },
    //设置树表格的表单pid文本
    async setFormPidText(id, tableName = this.tableName, tableLine = this.tableTreeUnfoldName, form = 'pid') {
      let itemRes = await getTreeItemDataApi({
        tableName,
        tableLine,
        rowKey: 'id',
        key: id,
      })
      if (itemRes.data.success) {
        let length = this.timerInte.length
        this.timerInte[length] = setInterval(() => {
          let dom = document.querySelector(`label[for=${form}]`)
          if (dom) {
            if (itemRes.data.data instanceof Array) {
              dom.parentNode.querySelector('input').value = itemRes.data.data[0] ? itemRes.data.data[0] : ''
            }
            clearInterval(this.timerInte[length])
          }
        }, 300)
      }
    },
    //设置表格弹窗表单值
    setTableFormValue(obj) {
      setTimeout(() => {
        this.tableForm[obj.fieldName] = obj.value
      }, 0)
    },

    /*
      自定义按钮事件
      btnCode:按钮编码
      btnType:按钮类型(button/link/form)
      enhanceType:增强类型(js/action)
      that:vue实例
      row:表单按钮、操作列按钮会携带当前表单数据、当前行数据
    */
    async allCustomButtonFun(btnCode, btnType, enhanceType, that, row) {
      //触发js增强方法
      if (enhanceType == 'js') {
        if (btnType == 'button' && this.customOnlineEnhanceJsList[btnCode] != undefined) {
          try {
            this.customOnlineEnhanceJsList[btnCode](that)
          } catch (error) {
            console.warn(error)
          }
        }
        if (btnType == 'link' && this.customOnlineEnhanceJsList[btnCode] != undefined) {
          try {
            this.customOnlineEnhanceJsList[btnCode](that, row)
          } catch (error) {
            console.warn(error)
          }
        }
        if (btnType == 'form' && this.customOnlineEnhanceJsForm[btnCode] != undefined) {
          try {
            this.customOnlineEnhanceJsForm[btnCode](that, row)
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

        await touchSqlEnhanceApi(apiData)
        if (btnType == 'link' || btnType == 'button') {
          this.$refs.codeTestList.selectClear()
          //重新获取页面数据
          this.initTableData()
        }
      }
    },
    //弹窗侧边按钮列表实现
    broadsideButtonRealizeFun() {
      let that = this
      let dialogDom = document.querySelector(`.zhxy-online-form-table-dialog-${this.currCodeId}`)
      if (dialogDom) {
        let divBg = document.createElement('div')
        divBg.className = 'code-text-list-dialog-broadside-box-bg'
        dialogDom.appendChild(divBg)
        let div = document.createElement('div')
        div.className = 'code-text-list-dialog-broadside-box'
        dialogDom.appendChild(div)
        let broadsideDom = document.querySelector('.code-text-list-dialog-broadside-box')
        let itemListDom = ''
        let customButtonFormSideObj = {}
        this.customButtonFormSide.forEach((item) => {
          customButtonFormSideObj[item.id] = item
          if (item.buttonIcon) {
            itemListDom = itemListDom + `<div class="list-item" data-id="${item.id}"><button  type="button" class="el-button el-button--primary el-button--small"><i class="item.buttonIcon"></i><span>${item.buttonName}</span></button></div>`
          } else {
            itemListDom = itemListDom + `<div class="list-item"  data-id="${item.id}"><button  type="button" class="el-button el-button--primary el-button--small"><span>${item.buttonName}</span></button></div>`
          }
        })

        broadsideDom.innerHTML = `
        <div class="broadside-box">
          <div class="broadside-box-icon">
            <button type="button" class="el-button el-button--default is-plain"><i class="el-icon-s-fold"></i></button>
          </div>
          <div class="broadside-box-list-box">
            <div class="list-title">
              其他操作
            </div>
            <div class="list-box">
              ${itemListDom}
            </div>
          </div>
        </div>
        `
        let bgDom = document.querySelector('.code-text-list-dialog-broadside-box-bg')
        let listDom = document.querySelector('.broadside-box-list-box')
        document.querySelector('.broadside-box-icon button').onclick = () => {
          bgDom.classList.add('active')
          listDom.classList.add('active')
        }
        bgDom.onclick = () => {
          bgDom.classList.remove('active')
          listDom.classList.remove('active')
        }
        document.querySelectorAll('.broadside-box-list-box .list-box .list-item').forEach((item) => {
          item.onclick = function () {
            let id = this.dataset.id

            that.allCustomButtonFun(customButtonFormSideObj[id].buttonCode, customButtonFormSideObj[id].buttonStyle, customButtonFormSideObj[id].optType, that, that.tableForm)
          }
        })
      }
    },
    //自定义按钮表达式校验 是否显示link类型的按钮
    linkButtonFiltersFun(obj) {
      let value = obj.exp
      let row = obj.row
      let key = ''
      let keyValue = ''
      let rowValue = undefined
      let exp = ''
      try {
        if (!value) {
          return true
        }
        //等于
        if (value.indexOf('#eq#') != -1) {
          exp = '#eq#'
        }
        //不等于
        if (value.indexOf('#ne#') != -1) {
          exp = '#ne#'
        }
        //并且等于
        if (value.indexOf('#moreeq#') != -1) {
          exp = '#moreeq#'
        }
        //判断空/非空
        if (value.indexOf('#empty#true') != -1 || value.indexOf('#empty#false') != -1) {
          exp = '#empty#'
        }
        //判断字段是否存在
        if (value.indexOf('#nonentity#true') != -1 || value.indexOf('#nonentity#false') != -1) {
          exp = '#nonentity#'
        }
        //in表达式 等于数组的某个值
        if (value.indexOf('#in#') != -1) {
          exp = '#in#'
        }
        //custom 自定义表达式
        if (value.indexOf('#custom#') != -1) {
          exp = '#custom#'
        }
        key = value.split(exp)[0]
        keyValue = value.split(exp)[1]
        rowValue = row[key] === undefined ? undefined : row[key] + ''
        if (rowValue === undefined && exp == '#nonentity#') {
          if (keyValue != 'false' && keyValue != 'true') {
            return true
          }
          if (keyValue == 'false' && rowValue === undefined) {
            return true
          }
          if (keyValue == 'true' && rowValue !== undefined) {
            return true
          }
          return false
        } else if (rowValue !== undefined && exp == '#nonentity#') {
          if (keyValue != 'false' && keyValue != 'true') {
            return true
          }
          if (keyValue == 'true') {
            return true
          }
          return false
        } else if (rowValue === undefined && !['#nonentity#', '#custom#'].includes(exp)) {
          return true
        }
        if (exp == '#eq#') {
          if (rowValue == keyValue) {
            return true
          } else {
            return false
          }
        }
        if (exp == '#moreeq#') {
          let bool = true
          let eqArr = value.split(',')
          eqArr.forEach((item) => {
            let eqkey = item.split(exp)[0]
            let eqKeyValue = item.split(exp)[1]
            let eqRowValue = row[eqkey] + ''
            if (eqRowValue != eqKeyValue) {
              bool = false
            }
          })
          return bool
        }
        if (exp == '#ne#') {
          if (rowValue != keyValue) {
            return true
          } else {
            return false
          }
        }
        if (exp == '#empty#') {
          if (keyValue != 'true' && keyValue != 'false') {
            return true
          }
          if (keyValue == 'true' && rowValue === '') {
            return true
          }
          if (keyValue == 'false' && rowValue !== '') {
            return true
          }
          return false
        }
        if (exp == '#in#') {
          let valueArr = keyValue.split(',')
          if (valueArr.includes(rowValue)) {
            return true
          }
          return false
        }
        if (exp == '#custom#') {
          key.split(',').forEach((item) => {
            keyValue = keyValue.replace(item, row[item])
          })
          let bool = true
          try {
            bool = eval(`(${keyValue})`)
          } catch (error) {
            console.warn(`自定义按钮表达式有误${keyValue};${error}`)
          }

          return bool
        }
        return true
      } catch (error) {
        return true
      }
    },
    //初始化js增强部分默认方法
    initOnlineEnhanceJs(listJs, formJs) {
      //获取选择控件数据值
      this.form.getSelectOptions = (field) => {
        if (!this.isOpentForm) {
          return false
        }
        if (this.themeTemplate == 'tab') {
          return this.$refs.codeMasterlistForm[0].form.getSelectOptions(field)
        }
        let column = this.findObject(this.tableOption.column, field)
        if (column != -1) {
          let fieldColumn = this.deepClone(column)
          if (fieldColumn.dicData) {
            return fieldColumn.dicData
          } else {
            return []
          }
        } else {
          return []
        }
      }
      this.form.changeOptions = (field, options) => {
        if (!this.isOpentForm) {
          return false
        }
        if (this.themeTemplate == 'tab') {
          this.$refs.codeMasterlistForm[0].form.changeOptions(field, options)
          return false
        }

        let column = this.findObject(this.tableOption.column, field)
        if (column != -1) {
          if (column.props && column.props.label && column.props.value) {
            let label = column.props.label
            let value = column.props.value
            options = options.map((item) => {
              return {
                [label]: item.label,
                [value]: item.value,
              }
            })
          }
          column.oldDicDate = {
            isReplace: true,
            dicData: this.deepClone(column.dicData),
          }
          column.dicData = options
        }
      }
      this.form.setFieldsValue = (param) => {
        if (!this.isOpentForm) {
          return false
        }
        if (this.themeTemplate == 'tab') {
          this.$refs.codeMasterlistForm[0].form.setFieldsValue(param)
          return false
        }
        if (param instanceof Object && !(param instanceof Array)) {
          this.tableForm = {
            ...this.tableForm,
            ...param,
          }
        }
      }
      this.form.getAllFieldValue = () => {
        if (!this.isOpentForm) {
          return false
        }
        if (this.themeTemplate == 'tab') {
          return this.$refs.codeMasterlistForm[0].form.getAllFieldValue()
        }
        return this.tableForm
      }
      this.form.getFieldValue = (field) => {
        if (!this.isOpentForm) {
          return false
        }
        if (this.themeTemplate == 'tab') {
          return this.$refs.codeMasterlistForm[0].form.getFieldValue(field)
        }
        if (typeof field == 'string') {
          return this.tableForm[field]
        } else {
          return ''
        }
      }
      this.form.getAllSubObj = () => {
        let subList = []
        let subObj = {}
        if (this.$refs.codeSublistTable instanceof Array) {
          subList = [...subList, ...this.$refs.codeSublistTable]
        }
        if (this.$refs.codeSublistForm instanceof Array) {
          subList = [...subList, ...this.$refs.codeSublistForm]
        }
        subList.forEach((item) => {
          subObj[item.tableKey] = item
        })
        return subObj
      }
      this.form.addSubRows = (tbname, rows) => {
        if (!this.isOpentForm) {
          return false
        }
        let subObj = this.form.getAllSubObj()
        let subType = ''
        if (!subObj[tbname]) {
          return false
        }
        if (typeof rows != 'object') {
          return false
        }
        if (rows instanceof Array && rows.length < 0) {
          return false
        }

        //判断是一对一 还是一对多
        if (subObj[tbname].$el.className == 'code-sbulist-form') {
          subType = 'form'
        }
        if (subObj[tbname].$el.className == 'code-sbulist-table') {
          subType = 'table'
        }
        //判断是否需要处理传入的数据类型
        if (subType == 'form' && rows instanceof Array) {
          rows = rows[0]
        }
        if (subType == 'table' && rows instanceof Object && !(rows instanceof Array)) {
          rows = [rows]
        }
        //调用附表的添加数据方法
        subObj[tbname].addSubListData(rows)
      }
      this.form.clearSubRows = (tbname) => {
        if (!this.isOpentForm) {
          return false
        }
        let subObj = this.form.getAllSubObj()
        if (!subObj[tbname]) {
          return false
        }
        subObj[tbname].clearSubListData()
      }
      this.form.clearThenAddRows = (tbname, rows) => {
        if (!this.isOpentForm) {
          return false
        }
        this.form.clearSubRows(tbname)
        this.form.addSubRows(tbname, rows)
      }

      let OnlineEnhanceJsList = undefined
      let OnlineEnhanceJsForm = undefined
      if (listJs) {
        OnlineEnhanceJsList = analysisFunction(listJs)
        if (OnlineEnhanceJsList !== false) {
          try {
            this.customOnlineEnhanceJsList = OnlineEnhanceJsList(getActionApi, postActionApi, deleteActionApi)
            this.customOnlineEnhanceJsName.list = Object.keys(this.customOnlineEnhanceJsList)
            if (this.customOnlineEnhanceJsList == undefined) {
              this.customOnlineEnhanceJsList = {}
            }
          } catch (error) {
            console.warn(error)
          }
        } else {
          console.warn('请检查js增强(list)编写是否有误~')
        }
      }
      if (formJs) {
        OnlineEnhanceJsForm = analysisFunction(formJs)
        if (OnlineEnhanceJsForm !== false) {
          try {
            this.customOnlineEnhanceJsForm = OnlineEnhanceJsForm(getActionApi, postActionApi, deleteActionApi)

            this.customOnlineEnhanceJsName.form = Object.keys(this.customOnlineEnhanceJsForm)
            if (this.customOnlineEnhanceJsForm == undefined) {
              this.customOnlineEnhanceJsForm = {}
            }
          } catch (error) {
            console.warn(error)
          }
        } else {
          console.warn('请检查js增强(form)编写是否有误~')
        }
      }
      if (this.customOnlineEnhanceJsName.form.includes('onlChange')) {
        try {
          let allChangeFun = this.customOnlineEnhanceJsForm.onlChange(this.that)
          for (let key in allChangeFun) {
            let column = this.findObject(this.tableOption.column, key)

            if (column != -1) {
              column.change = (event) => {
                try {
                  event.row = this.tableForm
                  allChangeFun[key](this.that, event)
                } catch (error) {
                  console.warn(`onlChange方法中<${key}>字段监听异常`, error)
                }
              }
            }
          }
        } catch (error) {
          console.warn(error)
        }
      }
      //处理附表 数据变更事件
      this.tabsOption.column = this.tabsOption.column.map((item) => {
        if (this.customOnlineEnhanceJsName.form.includes(`${item.key}_onlChange`)) {
          item.onlChangeFun = this.customOnlineEnhanceJsForm[`${item.key}_onlChange`](this.that)
        }
        return item
      })
    },
    //使用表单设计
    //获取表单设计配置信息
    async getDesFormOptionDataFun(formCode) {
      //先通过表单设计code获取id
      let idRes = await getFormIdApi(formCode)
      let formId = idRes.data.data.id
      //获取表单设计的配置
      getdetailDataApi(formId).then((detailRes) => {
        this.formActionData.desForm = detailRes.data.data
        let options = {}
        if (detailRes.data.success && detailRes.data.data.formDesignJson) {
          options = detailRes.data.data.formDesignJson
        }
        if (typeof options == 'string') {
          try {
            options = eval('(' + options + ')')
          } catch (e) {
            console.error('非法配置')
            options = { column: [] }
          }
        }
        this.widgetFormPreview = this.deepClone(options)
      })
    },
    //关闭自定义表格弹窗
    closeDialogForm(code, obj) {
      if (code) {
        this.customButtonLink.forEach((linkItem) => {
          obj = {
            ...obj,
            ['$link$' + linkItem.buttonCode]: this.linkButtonFiltersFun({
              exp: linkItem.exp,
              row: obj,
            }),
          }
        })
      } else {
        this.isDialogFormDesign = false
        if (this.formOpenType == 'add' || this.formOpenType == 'edit') {
          this.$refs.codeTestList.selectClear()
          if (this.formOpenType == 'add') {
            this.tablePage.currentPage = 1
          }
          this.$refs.codeTestList.selectClear()
          this.initTableData()
          //树表格触发数据回显
          if (this.tableDataIsTree) {
            this.treeTableDataEcho(this.formOpenType)
          }
        }
      }
    },
    //开启路由配置表单
    openRouterFun(obj) {
      if (obj.type == '1') {
        //打开新表单
        this.isRouterCustom = true
        this.routerFormCode = obj.code
        this.routerFormData = obj.formData
      }
      if (obj.type == '2') {
        //转跳到菜单
        this.$router.push({ path: obj.url, query: { dataId: obj.dataId } })
      }
      if (obj.type == '3') {
        //转跳到网站
        window.open(`${obj.url}?dataId=${obj.dataId}`)
      }
    },
    //自定义表单其他方法
    formCustomOtherFun(type, data) {},
    //自定义按钮方法
    async formDesignButtonTriggerFun(type, obj) {
      this.formOpenType = type
      if (type == 'add') {
        this.dialogFormTitle = '新增'
        this.allFormListData = {}
      }
      if (['edit', 'view'].includes(type)) {
        //查询表单开发详情数据
        let detailRes = await getDataDetailApi(this.currCodeId, obj.row.id)
        obj.row = {
          ...obj.row,
          ...detailRes.data.data,
        }

        this.allFormListData = {
          ...obj.row,
        }
      }
      if (type == 'edit') {
        this.dialogFormTitle = '编辑'
      }
      if (type == 'view') {
        this.dialogFormTitle = '查看'
      }
      this.isDialogFormDesign = true
    },
    //主题处理
    themeTemplateTableFun(type, obj) {
      //ERP主题(一对多)  (暂时无法实现 等待功能开发完整后再实现)
      if (type == 'erp') {
        this.tableOption.selection = false
        this.tableOption.reserveSelection = false
        this.tableOption.column.splice(0, 0, {
          order: 0,
          lable: '',
          prop: 'vue_radio',
          type: 'radio',
          display: false,
          width: 60,
          align: 'center',
        })
      }

      //内嵌子表主题(一对多) (avue 行展开有问题 等待解决)
      if (type == 'innerTable') {
        this.tableOption.expand = true
        this.tableOption.rowKey = 'id'

        this.tabsOption.column.forEach(async (item) => {
          this.expandObj[item.prop] = {}
          this.expandObj[item.prop].column = await getDetails(item.prop)
        })
      }
      //TAB主题(一对多)
      if (type == 'tab') {
        this.tabsOption.column.splice(0, 0, obj)
      }
    },
    //树组件通用方法
    async treeControlFun(type, obj) {
      //type 方法类型 dialog:显隐弹窗  apiAdd:通过api批量新增数据 subDataAdd:子表数据新增
      if (type == 'dialog') {
        this.tableTreeControlOption.isDialog = obj.bool
      }
      //父表数据存储
      if (type == 'apiAdd') {
        this.tableTreeControlOption.isDialog = false
        this.isTableLoading = true
        let promiseArr = []
        obj.data.forEach((item) => {
          promiseArr.push(
            new Promise((resolve) => {
              addDataApi(obj.tableId, item)
                .then(() => {
                  resolve()
                })
                .catch(() => {
                  resolve()
                })
            })
          )
        })
        await Promise.all(promiseArr)
        this.$refs.codeTestList.selectClear()
        this.tablePage.currentPage = 1
        this.initTableData()
        this.isTableLoading = false
      }
      //字表数据存储
      if (type == 'subDataAdd') {
        this.form.addSubRows(obj.tableId, obj.data)
      }
    },
    //表单控件通用方法
    formViewControlFun(type, data) {
      //type 方法类型 hide:隐藏弹窗
      if (type == 'hide') {
        this.FormViewControlOption.viewObj.isShow = false
      }
      if (type == 'hidegetData') {
        this.tableRefreshChangeFun()
        this.FormViewControlOption.viewObj.isShow = false
      }
      if (type == 'customFun') {
        try {
          this.FormViewControlOption.customFun(data)
        } catch (error) {
          console.warn('表单控件自定义处理方法异常====>' + error)
        }
      }
    },
    //组件通用方法
    controlViewFun(type) {
      if (type == 'hide') {
        this.controlViewOption.viewObj.isShow = false
      }
      if (type == 'hidegetData') {
        this.tableRefreshChangeFun()
        this.controlViewOption.viewObj.isShow = false
      }
      if (type == 'customFun') {
        try {
          this.controlViewOption.customFun()
        } catch (error) {
          console.warn('自定义组件处理方法异常====>' + error)
        }
      }
    },
    tableViewDeclareFun(type) {
      this.tableViewOptionData.viewObj.isShow = false
    },
    //表格选择组件通用方法
    async selectControlFun(type, obj) {
      //type 方法类型 dialog:显隐弹窗
      if (type == 'dialog') {
        this.tableSelectControlOption.isDialog = obj.bool
      } else if (type == 'resetAddTableData') {
        //重置表格添加
        if (this.customButtonLink && this.customButtonLink.length > 0) {
          obj.data = obj.data.map((item) => {
            this.customButtonLink.forEach((linkItem) => {
              let isShow = this.linkButtonFiltersFun({
                exp: linkItem.exp,
                row: item,
              })
              if (this.isAuthBtn) {
                if (this.permission[`${linkItem.buttonCode}_${this.currCodeId}${this.currCodeType}`] && isShow) {
                  item['$link$' + linkItem.buttonCode] = true
                } else {
                  item['$link$' + linkItem.buttonCode] = false
                }
              } else {
                item['$link$' + linkItem.buttonCode] = isShow
              }
            })
            return item
          })
        }

        this.tableData = obj.data
        this.tableSelectControlOption.isDialog = false
      } else if (type == 'customDispose') {
        this.tableSelectControlOption.submitFun(this, obj)
      }
    },
    //tabs显示控件
    tabsViewFun(type) {
      if (type == '') {
        if (type == 'hide') {
          this.tabsOptionData.viewObj.isShow = false
        }
        if (type == 'hidegetData') {
          this.tableRefreshChangeFun()
          this.tabsOptionData.viewObj.isShow = false
        }
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.code-test-box {
  /deep/.el-table__fixed-right {
    .el-table__row.expanded + tr {
      pointer-events: none;
      visibility: hidden;
      opacity: 0;
    }
  }

  /deep/.el-table__fixed {
    .el-table__row.expanded + tr {
      pointer-events: none;
      visibility: hidden;
      opacity: 0;
    }
  }
}

.test-box-list {
  background-color: #fff;
  padding: 16px;
  margin-bottom: 20px;

  .code-test-list-erp-radio {
    /deep/.el-radio__label {
      display: none;
    }
  }
}

.advanced_search_dialog_box {
  .dialog-footer {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.advanced_search_dialog {
  display: flex;

  .conditions_box_item_1 {
    .el-select {
      width: 300px;
    }
  }

  .conditions_box_item_2 {
    display: flex;
    align-items: center;
    padding-top: 20px;
    margin-right: 60px;

    .item {
      padding-right: 10px;

      .el-button {
        padding: 10px 12px;
      }

      .el-select {
        width: 220px;
      }
    }

    .item-rule {
      .el-select {
        width: 100px;
      }
    }

    .item-val {
      .el-input {
        width: 220px;
      }
    }
  }

  .record_box {
    border: 1px solid #e8e8e8;
    width: 200px;
    height: 100%;
    box-sizing: border-box;

    .record_box_title {
      border-bottom: 1px solid #e8e8e8;
      font-size: 16px;
      padding: 5px 0 5px 10px;
    }

    .record_box_list {
      padding: 5px 0;
    }

    .record_box_list_item {
      height: 24px;
      margin: 4px 0;
      display: flex;
      align-items: center;
      cursor: pointer;

      .item-left-icon {
        padding: 0 5px;
      }

      .item-name {
        flex: 1;
      }

      .item-right-close {
        font-size: 16px;
        padding: 0 5px;
        opacity: 0;
        visibility: hidden;
        transition: all 0.3s;
      }

      &:hover {
        background-color: #e6f7ff;

        .item-right-close {
          visibility: visible;
          opacity: 1;
        }
      }
    }

    .null-list {
      text-align: center;

      .text {
        color: #999;
      }
    }
  }
}
</style>
<style lang="scss">
.view-file-download-list {
  display: flex;
  flex-direction: column;

  a {
    display: block;
    margin-bottom: 5px;

    i {
      padding-right: 5px;
    }

    &:hover {
      background-color: #f2f2f2;
    }
  }
}

.view-file-download-list a:hover {
  color: #409eff;
}

.code-text-list-dialog-broadside-box-bg {
  position: absolute;
  right: 0;
  top: 0;
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.65);
  opacity: 0.3;
  z-index: 1023;
  opacity: 0;
  visibility: hidden;
  transition: all 0.2s;

  &.active {
    visibility: visible;
    opacity: 1;
  }
}

.code-text-list-dialog-broadside-box {
  position: absolute;
  right: 0;
  top: 0;
  height: 100%;
  z-index: 1024;

  .broadside-box {
    position: relative;
    height: 100%;
  }

  .broadside-box-icon {
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(-50%);

    button {
      font-size: 20px;
      padding: 5px 8px;
      border-right: 0;
      border-radius: 4px 0 0 4px;
    }
  }

  .broadside-box-list-box {
    position: absolute;
    right: 0;
    top: 0;
    z-index: 2;
    height: 100%;
    background-color: #fff;
    opacity: 0;
    visibility: hidden;
    transition: all 0.5s;

    &.active {
      visibility: visible;
      opacity: 1;
    }

    .list-title {
      padding: 16px;
      border-radius: 4px 4px 0 0;
      background: #fff;
      border-bottom: 1px solid #e8e8e8;
      color: rgba(0, 0, 0, 0.85);
      white-space: nowrap;
      font-size: 14px;
      text-align: left;
    }

    .list-box {
      padding: 0 24px;

      .list-item {
        padding-top: 12px;

        button {
          min-width: 100px;
        }
      }
    }
  }
}

.code-test-list-dialog-inport-box {
  .el-dialog__header {
    border-bottom: 1px solid #f2f2f2;
    padding: 15px 20px;
  }

  /deep/.el-dialog__body {
    border-top: 1px solid #e8e8e8;
    border-bottom: 1px solid #e8e8e8;
  }

  .avue-form__menu {
    display: none;
  }

  .inport-tip {
    text-align: right;
    padding-right: 20px;
  }
}

.dialog-form-design-box {
  .el-dialog__header {
    border-bottom: 1px solid #f2f2f2;
    padding: 15px 20px;
  }

  .form-design-box-content {
    .content-fullscreen {
      position: absolute;
      top: 20px;
      right: 55px;
      cursor: pointer;
    }
  }
}

.avue-crud__dialog {
  .el-dialog {
    margin-top: 10vh !important;
  }

  .el-dialog__body {
    .avue--detail {
      .avue-ueditor {
        .w-e-toolbar {
          display: none;
        }

        .w-e-text-container {
          border-width: 0 !important;
        }
      }
    }
  }
}

.avue-dialog--fullscreen {
  .el-dialog {
    margin-top: 0px !important;
  }
}
</style>
