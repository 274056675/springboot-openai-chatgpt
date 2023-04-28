<template>
  <div class="form-control" :class="'form-control-box-' + formOption.prop">
    <avue-form ref="form" v-model="form" :option="option">
      <!-- 自定义按钮组 -->
      <template
        v-for="(btnListItem, btnListIndex) in btnListOption"
        slot-scope="scope"
        :slot="btnListItem.prop"
      >
        <div class="form-custom-btn-list" :class="scope.column.class" :key="btnListIndex">
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
        <div class="form-control-text" :class="scope.column.class" :key="rateIndex">
          <el-rate
            :size="scope.size"
            v-model="form[scope.column.prop]"
            :allow-half="scope.column.allowHalf"
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
        <div class="form-control-text" :class="scope.column.class" :key="textIndex">
          <div class="custon-text" :style="scope.column.styles">{{ scope.column.textValue }}</div>
        </div>
      </template>
      <!-- 自定义分隔符 -->
      <template
        v-for="(separatorItem, separatorIndex) in separatorOption"
        slot-scope="scope"
        :slot="separatorItem.prop"
      >
        <div class="form-control-separator" :class="scope.column.class" :key="separatorIndex">
          <el-divider
            v-if="scope.column.direction != 'empty'"
            :content-position="scope.column.contentPosition"
            :direction="scope.column.direction"
          >
            <i v-if="scope.column.textIcon" :class="scope.column.textIcon"></i>
            {{ scope.value }}
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
        <div class="form-control-button" :class="scope.column.class" :key="buttonIndex">
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
          v-model="form[monacoEditorItem.prop]"
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
          ref="tableControl"
          :key="tableIndex"
          :style="scope.column.style"
          :class="scope.column.class"
          :tableColumn="scope.column"
          :tableValue="scope.value"
          :formOpenType="formOpenType"
          :getCurrPacDataTextFun="getCurrPacDataTextFun"
          :lazyLoadFun="lazyLoadFun"
          :allFormListData="allFormListData"
        ></table-control>
      </template>
    </avue-form>
  </div>
</template>

<script>
import { getStrDataFunction } from '@/research/util/myUtil.js'
import { getDicTableData, uploadeFileApi } from '@/api/research/codelist'
import form from '@/research/mixins/form'
import { apiRequestHead } from '@/config/url.js'
import DepartControl from '@/research/components/general-control/depart-control'
import UserControl from '@/research/components/general-control/user-control'
import TableSelectControl from '@/research/components/general-control/table-select-control.vue'
import TableControl from '@/research/components/form-custom/table-control'
import MonacoEditor from '@/packages/utils/monaco-editor'
import Vue from 'vue';
export default {
  props: [
    'formOption',
    'currTabsValue',
    'currTabsProp',
    'formOpenType',
    'allExecuteRule',
    'setJsEnhanceFun',
    'getCurrPacDataTextFun',
    'lazyLoadFun',
    'allFormListData',
  ],
  components: {
    DepartControl,
    UserControl,
    TableControl,
    TableSelectControl,
    MonacoEditor,
  },
  mixins: [form],
  computed: {},
  data() {
    return {
      apiRequestHead: '',
      valueToload: false,
      optinsToLoad: false,
      form: {},
      option: {},
      rateOption: [], //评分字段
      separatorOption: [], //分隔符
      textOption: [], //文本
      btnListOption: [],
      buttonOption: [], //按钮
      userOption: [], //用户
      departOption: [], //部门
      monacoEditorOption: [], //代码编辑器
      tableSelectOption: [], //表格选择
      initSelfDefinedArr:[],//已经注册的自定义组件
      tableOption: [], // 子表
      provincesOption: [], //省市区,
      selectRemoteAll: [],
      selectDicAll: [],
    }
  },
  async mounted() {
    this.apiRequestHead = apiRequestHead
    let currOption = this.setFormOptionDataFun(this.formOption)
    this.option = {
      ...this.option,
      ...currOption,
    }
    if (['add', 'add_no'].includes(this.formOpenType)) {
      setTimeout(() => {
        //延迟配置默认值失效，重新设置默认值
        this.$refs.form.dataFormat()
      }, 0)
    }
    this.optinsToLoad = true
    this.setRemoteDataDicFun()
    if (['add', 'add_no'].includes(this.formOpenType)) {
      this.getApiDataFun()
    } else {
      this.valueToload = true
    }
    if (
      ['edit', 'view', 'noButton', 'add_router'].includes(this.formOpenType)
    ) {
      this.setCurrentFormDataFun()
    }
    setTimeout(() => {
      this.setCustomText()
    }, 0)
  },
  methods: {
    //设置当前表单数据
    setCurrentFormDataFun() {
      if (this.option.column) {
        this.option.column.forEach((item) => {
          if (item.type != 'table') {
            this.form = {
              ...this.form,
              [item.prop]: this.allFormListData[item.prop],
            }
          }
        })
      }
      setTimeout(() => {
        //防止数据不更新
        this.$refs.form.dataFormat()
      }, 0)
    },
    //初始化树控件/联集文本
    setCustomText() {
      if (this.provincesOption && this.provincesOption.length > 0) {
        this.provincesOption.forEach((item) => {
          this.setProvincesTextFun(this.form[item], item)
        })
      }
    },
    //修改省市区文本方法
    setProvincesTextFun(value, prop) {
      let text = this.getCurrPacDataTextFun(value)
      let dom = document.querySelector(
        `.form-control-box-${this.formOption.prop} label[for=${prop}]`
      )
      if (dom) {
        dom.parentNode.querySelector('input').value = text ? text : ''
      } else {
        // 处理字表省市区文本
        let dom = document.querySelector(
          `.form-control-control-provinces__${prop}`
        )
        dom = dom.parentNode.parentNode.parentNode.parentNode.querySelector(
          '.el-form-item__content .el-input input'
        )
        if (dom) {
          dom.value = text
        }
      }
    },
    //清空所有数据
    clearAllDataFun() {
      this.$refs.form.clearValidate()
      this.$refs.form.clearVal()
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
    //处理表单设计器配置数据
    setFormOptionDataFun(option) {
      let optinos = this.deepClone(option)
      delete optinos.disabled
      if (optinos.label) {
        optinos.label = ''
      }
      if (optinos.prop) {
        optinos.prop = ''
      }
      //column处理
      if (optinos.column == undefined) {
        optinos.column = []
      } else {
        optinos.column = optinos.column.map((item) => {
          return this.setOptionCloumnFun(item, optinos)
        })
      }
      optinos.menuBtn = false
      return optinos
    },
    setOptionCloumnFun(item, optinos) {
      if (optinos.labelWidth && item.labelWidth === undefined) {
        item.labelWidth = optinos.labelWidth
      }
      // if (!['tabs', 'title', 'separator', 'button'].includes(item.type)) {
      //   this.form[item.prop] = item.value
      // }
      //清除长度限制
      if (
        (item.isMaxLength !== undefined && item.isMaxLength === false) ||
        (item.isMaxLength !== true && item.maxlength === 0)
      ) {
        delete item.maxlength
      }
      if (['view', 'noButton'].includes(this.formOpenType)) {
        item.disabled = true
      }
      //评分
      if (item.type == 'rate') {
        this.rateOption.push(item)
      }
      //文本
      if (item.type == 'title') {
        this.textOption.push(item)
      }
      //按钮组
      if (item.type == 'btn-list') {
        this.btnListOption.push(item)
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
        this.userOption.push(item)
      }
      //部门
      if (item.type == 'depart') {
        this.departOption.push(item)
      }
      //表格选择
      if (item.type == 'table-select') {
        this.tableSelectOption.push(item)
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
      if (item.type == 'monaco-editor') {
        this.monacoEditorOption.push(item)
      }
      //子表
      if (item.type == 'table') {
        this.tableOption.push(item)
      }
      //省市区联动
      if (item.type == 'provinces') {
        item.type = 'cascader'
        item.lazyLoad = (node, resolve) =>
          this.lazyLoadFun(node, resolve, item.provType)
        item.formCustomType = 'provinces'
        item.class =
          `form-control-control-provinces__${item.prop}` + ' ' + item.class
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
          this.selectRemoteAll.push(item.prop)
        }
        if (item.oldDicOption == 'dic') {
          this.selectDicAll.push(item.prop)
        }
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
    //远程取值方法
    async getApiDataFun() {
      let apiColumn = []
      if (this.option.column) {
        apiColumn = [...apiColumn, ...this.option.column]
      }
      let formData = await this.mixinGetApiData(apiColumn)
      for (let key in formData.formObj) {
        if (formData.formObj[key] instanceof Array) {
          formData.formObj[key] = formData.formObj[key].join(',')
        }
      }
      this.form = {
        ...this.form,
        ...formData.formObj,
      }
      for (let key in formData.specialObj) {
        if (formData.specialObj[key].type == 'title') {
          let column = null
          if (this.option.column) {
            column = this.findObject(this.option.column, key)
          }
          if (column) {
            column.textValue = formData.specialObj[key].data
          }
        }
      }
      //
      this.valueToload = true
    },
    //选择字段远端数据处理和数据字典逻辑
    setRemoteDataDicFun() {
      //远端数据
      if (this.selectRemoteAll.length > 0) {
        this.selectRemoteAll.forEach(async (item) => {
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
      }
      //数据字典
      if (this.selectDicAll.length > 0) {
        this.selectDicAll.forEach(async (item) => {
          let column = this.findObject(this.option.column, item)
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
    //获取表单数据
    getFormData() {
      return new Promise(async (resolve) => {
        try {
          let res = true
          let formData = await this.verifyFormFun()

          let resData = {
            ...formData.data,
          }
          let promiseArr = []
          if (this.$refs.tableControl) {
            this.$refs.tableControl.forEach((item) => {
              promiseArr.push(item.getTableData())
            })
          }
          let tableDataArr = await Promise.all(promiseArr)
          if (!formData.res) {
            res = false
          }
          tableDataArr.forEach((item) => {
            if (!item.res) {
              res = false
            }
            resData = {
              ...resData,
              [item.prop]: item.data,
            }
          })
          resolve({
            res,
            tabsValue: this.currTabsValue,
            tabsProp: this.currTabsProp,
            data: resData,
          })
        } catch (error) {
          resolve({
            res: false,
            tabsValue: this.currTabsValue,
            tabsProp: this.currTabsProp,
            data: {},
          })
        }
      })
    },
    //不校验获取表单数据
    getFormDataNullVerify() {
      let formData = {
        ...this.form,
      }
      if (this.$refs.tableControl) {
        this.$refs.tableControl.forEach((item) => {
          formData = {
            ...formData,
            ...item.tableDataItemDefault,
            [item.tableProp]: item.tableData,
          }
        })
      }
      return formData
    },
    //获取当前组件所有字段配置
    getFormColumnData() {
      let column = [...this.option.column]
      if (this.$refs.tableControl) {
        this.$refs.tableControl.forEach((item) => {
          column = [...column, ...item.tableOption.column]
        })
      }
      return column
    },
    //校验表单方法
    verifyFormFun() {
      return new Promise((resolve) => {
        this.$refs.form.validate((valid, done) => {
          done()
          resolve({
            res: valid,
            prop: false,
            data: this.form,
          })
        })
      })
    },
    //按钮绑定方法
    customButtonFun(funText) {
      this.setJsEnhanceFun(funText, 'button')
      // this.getFunction(funText)
    },
    //解析函数
    getFunction(fun) {
      if (!this.validatenull(fun)) {
        //后台获取是需要注释
        fun = fun.replace(/↵/g, '\n')
        //后台获取是需要注释
        fun = fun.replace(/\/\*{1,2}[\s\S]*?\*\//gis, '')
        // fun = fun.replace(/(?:^|\n|\r)\s*\/\*[\s\S]*?\*\/\s*(?:\r|\n|$)/g, '')
        fun = fun.replace(/(?:^|\n|\r)\s*\/\/.*(?:\r|\n|$)/g, '')
        try {
          if (eval(`(${fun})`)) {
            return eval(`(${fun})`)
          } else {
            return () => {}
          }
        } catch {
          console.warn('请检查js增强编写是否有误~')
          return () => {}
        }
      }
    },
    //设置表单值{fieldName:'',value:''}
    setFormValue(obj) {
      if (obj.value instanceof Array) {
        obj.value = obj.value.join(',')
      }

      this.form = {
        ...this.form,
        [obj.fieldName]: obj.value,
      }
    },
    //js增强设置表单值
    setJsFormDataFun(obj) {
      let forKey = Object.keys(this.form)
      let tableKey = this.tableOption.map((item) => item.prop)
      if (forKey.includes(obj.fieldName) && !tableKey.includes(obj.fieldName)) {
        this.setFormValue(obj)
      }
      if (this.$refs.tableControl) {
        this.$refs.tableControl.forEach((item) => {
          item.setJsFormDataFun(obj)
        })
      }
    },
    //js增强设置控件配置
    setFormOptionsFun(key, optionsKey, optionsValue) {
      this.$nextTick(() => {
        let column = ''
        if (this.option.column) {
          column = this.findObject(this.option.column, key)
        }
        if (column && column != -1) {
          column[optionsKey] = optionsValue
        }
        if (this.$refs.tableControl) {
          this.$refs.tableControl.forEach((item) => {
            item.setFormOptionsFun(key, optionsKey, optionsValue)
          })
        }
      })
    },
    //js增强设置控件显示/隐藏
    setFormControlStateFun(key, value) {
      this.$nextTick(() => {
        key.forEach((keyItem) => {
          let column = ''
          if (this.option.column) {
            column = this.findObject(this.option.column, keyItem)
          }
          if (column && column != -1) {
            column.display = value
          }
        })
        if (this.$refs.tableControl) {
          this.$refs.tableControl.forEach((item) => {
            item.setFormControlStateFun(key, value)
          })
        }
      })
    },
    //js增强设置控件值监听
    setWatchFun(watchItems) {
      this.$nextTick(() => {
        let tableKey = this.tableOption.map((item) => item.prop)
        let keyArr = Object.keys(watchItems)
        let formKey = Object.keys(this.form)
        keyArr.forEach((keyItem) => {
          if (formKey.includes(keyItem) && !tableKey.includes(keyItem)) {
            let watchName = 'form.' + keyItem
            this.$watch(watchName, watchItems[keyItem])
          }
        })
      })
    },
    //
    //设置填值规则的值
    setFormExecuteRuleFun(rule) {
      let column = []
      if (this.option.column) {
        column = [...this.option.column]
      }

      let formData = {}
      column.forEach((item) => {
        if (item.fillRuleCode) {
          formData[item.prop] = rule[item.fillRuleCode]
        }
      })
      this.form = {
        ...this.form,
        ...formData,
      }
      if (this.$refs.tableControl) {
        this.$refs.tableControl.forEach((item) => {
          item.setFormExecuteRuleFun(rule)
        })
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.form-custom {
  .form-custom-rate {
    padding-top: 10px;
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
