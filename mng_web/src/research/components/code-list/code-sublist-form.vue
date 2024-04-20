<template>
  <div class="code-sbulist-form">
    <avue-form
      ref="form"
      v-if="avueFormBool"
      v-loading="loading"
      v-model="tableForm"
      :option="formOption"
      :upload-after="uploadAfter"
      :upload-exceed="uploadExceedFun"
    >
      <!-- 自定义用户控件 -->
      <template
        v-for="(item, index) in viewUserControlArr"
        :slot="item.fieldUserName"
        slot-scope="scope"
      >
        <user-control
          :key="index"
          :tableItemVal="scope.value"
          :tableItemName="item.fieldName"
          :disabled="scope.disabled"
          :tableItemScope="scope"
          @set-form-val="setTableFormValue"
        ></user-control>
      </template>
      <!-- 自定义部门控件 -->
      <template
        v-for="(item, index) in viewDepartControlArr"
        :slot="item.fieldDepartName"
        slot-scope="scope"
      >
        <depart-control
          :key="index"
          :tableItemVal="scope.value"
          :tableItemName="item.fieldName"
          :disabled="scope.disabled"
          :tableItemScope="scope"
          @set-form-val="setTableFormValue"
        ></depart-control>
      </template>
      <!-- 自定义文件名 -->
      <template
        v-for="(item, index) in viewFileArr"
        :slot="item.fieldName + 'Type'"
        slot-scope="scope"
      >
        <div :key="index" style="cursor: pointer; display: flex; align-items: center">
          <i class="el-icon-link"></i>
          <a
            style="flex: 1"
            :href="scope.file.url"
          >{{ viewFileNameObj[scope.file.url]?viewFileNameObj[scope.file.url]:scope.file.url }}</a>
          <i
            class="el-icon-close"
            @click.capture.stop="codeFileControlDelFun(item.fieldName, scope)"
          ></i>
        </div>
      </template>
      <!-- 自定义markdown控件表单 -->
      <template
        v-for="(item, index) in viewMarkdownArr"
        slot-scope="scope"
        :slot="item.fieldMarkDownName"
      >
        <mavon-editor
          :ref="'moavonEditor_' + index"
          @imgAdd="(pos, $file) => moavonEditorImgAdd(pos, $file, index)"
          v-model="scope.value"
          :key="index"
          :editable="!scope.disabled"
        ></mavon-editor>
      </template>
    </avue-form>
  </div>
</template>

<script>
let validateRulesAll = []
import { getDetails } from '@/api/research/code'
import DepartControl from '@/research/components/general-control/depart-control'
import UserControl from '@/research/components/general-control/user-control'
import { getUploadeFileNameApi, uploadeFileApi } from '@/api/research/codelist'
import { apiRequestHead } from '@/config/url.js'
export default {
  components: {
    DepartControl,
    UserControl,
  },
  props: [
    'tableType',
    'boxType',
    'tableAllColumnRules',
    'disabled',
    'tableTabName',
    'tableKey',
    'currDataList',
    'allChangeFun',
    'tableClassName',
    'getParentFieldValue',
    'setParentFieldValue',
    'simpleDateFormat',
    'tableColumnDic',
    'tabObj',
    'addSubRows',
    'clearSubRows',
    'clearThenAddRows',
  ],
  filters: {},
  data() {
    return {
      form: {},
      loading: false,
      avueFormBool: false,
      tableForm: {},
      formOption: {
        submitBtn: false,
        emptyBtn: false,
        column: [],
      },
      allUserData: [],
      allDepartData: [],
      tableNeetRules: ['text', 'password', 'textarea', 'umeditor', 'markdown'], //可以校验的控件
      //表单 单独占一行的控件
      fieldSpanOneLine: ['image', 'file'],
      // 需要字典数据的控件
      viewListSelect: ['list', 'radio', 'switch', 'list_multi'],

      //所有文件控件
      viewFileArr: [],
      viewListTreeAllData: [],
      viewUserControlArr: [],
      viewDepartControlArr: [],
      viewFileNameObj: {},
      viewMarkdownArr: [],
    }
  },
  async mounted() {
    this.loading = true
    if (this.tableType == 'tab') {
      let columns = this.deepClone(this.tabObj.column).filter((item) => {
        if (item.prop == 'vue_info') {
          return false
        }
        return true
      })
      this.viewMarkdownArr = this.tabObj.viewMarkdownArr
      this.viewFileArr = this.tabObj.viewFileArr
      this.viewUserControlArr = this.tabObj.viewUserControlArr
      this.viewDepartControlArr = this.tabObj.viewDepartControlArr
      this.formOption.column = columns.map((item) => {
        if (item.alterDisplay) {
          item.display = true
        }
        return item
      })
    } else {
      let columns = await getDetails(this.tableTabName)
      columns = columns.data.data.fieldList
      let columnsObj = {}
      columns.forEach((item) => {
        columnsObj[item.dbFieldName] = item
      })
      if (this.currDataList && this.currDataList.length > 0) {
        this.tableForm = this.currDataList[0]
      }
      this.formOption.column = this.setTableDataFun(columns, 24)
    }

    //处理文件名
    if (this.viewFileArr.length > 0) {
      this.viewFileArr.forEach((item) => {
        let fieldUrl = this.tableForm[item.fieldName]
        if (fieldUrl) {
          let fileArr = fieldUrl.split(',')
          fileArr.forEach(async (fileArrItem) => {
            let fileRes = await getUploadeFileNameApi(fileArrItem)
            let fileName = fileArrItem.split('/')
            fileName = fileName[fileName.length - 1]
            if (fileRes.data.success && fileRes.data.data) {
              fileName = fileRes.data.data
            }
            this.viewFileNameObj = {
              ...this.viewFileNameObj,
              [fileArrItem]: fileName,
            }
          })
        }
      })
    }
    this.avueFormBool = true

    // 获取某个字段的下拉数据
    this.form.getSelectOptions = (field) => {
      let column = this.findObject(this.formOption.column, field)
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
    // 设置某个字段的下拉数据
    this.form.changeOptions = (field, options) => {
      let column = this.findObject(this.formOption.column, field)
      if (column != -1) {
        if (column.props && column.props.label && column.props.value) {
          let label = column.props.label
          let value = column.props.value
          column.dicData = options.map((item) => {
            return {
              [label]: item.label,
              [value]: item.value,
            }
          })
        }
      }
    }
    // 设置表单数据
    this.form.setFieldsValue = (param) => {
      if (param instanceof Object && !(param instanceof Array)) {
        this.tableForm = {
          ...this.tableForm,
          ...param,
        }
      }
    }
    //获取所有表单数据
    this.form.getAllFieldValue = () => {
      return this.tableForm
    }
    // 获取某个表单字段数据
    this.form.getFieldValue = (field) => {
      if (typeof field == 'string') {
        return this.tableForm[field]
      } else {
        return ''
      }
    }

    try {
      let allChangeFun = this.allChangeFun
      for (let key in allChangeFun) {
        let column = this.findObject(this.formOption.column, key)
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
                event.row = this.tableForm
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
    } catch (error) {
      console.warn(error)
    }
    // 延迟 等待赋值完毕
    setTimeout(() => {
      this.loading = false
    }, 1500)
  },
  methods: {
    codeFileControlDelFun(fileName, obj) {
      let arr = []
      if (this.tableForm[fileName] instanceof Array) {
        arr = this.tableForm[fileName]
      } else {
        arr = this.tableForm[fileName].split(',')
      }
      let fileStr = arr.filter((item) => {
        return item != obj.file.url
      })
      fileStr.join(',')
      this.tableForm[fileName] = fileStr.join(',')
    },
    //下载文件
    downloadFile(url, name) {
      var aEle = document.createElement('a') // 创建a标签
      aEle.download = name // 设置下载文件的文件名
      aEle.href = url // content为后台返回的下载地址
      aEle.click() // 设置点击事件
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
      formdata.append('type', 0)
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
    //校验表单方法
    verifyFormFun() {
      let formattingFormData = {}
      for (let key in this.tableForm) {
        if (this.tableForm[key] instanceof Array) {
          formattingFormData[key] = this.tableForm[key].join(',')
        } else {
          formattingFormData[key] = this.tableForm[key]
        }
      }
      return new Promise((resolve) => {
        this.$refs.form.validate((valid, done) => {
          done()
          let obj = {
            res: valid,
            tabName: this.tableTabName,
            type: this.tableType,
          }
          if (this.tableType == 'tab') {
            obj.data = formattingFormData
          } else {
            obj.data = { [this.tableKey]: formattingFormData }
          }
          resolve(obj)
        })
      })
    },
    //设置表格弹窗表单值
    setTableFormValue(obj) {
      this.tableForm[obj.fieldName] = obj.value
    },
    //监听文件上传
    uploadAfter(res, done) {
      this.viewFileNameObj = {
        ...this.viewFileNameObj,
        [res.link]: res.originalName,
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
    setTableDataFun(obj, formSpan) {
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
          value: item.dbDefaultVal, //默认值

          // 配置默认字段（防止动态修改不生效）
          display: true,
          hide: false,
        }
        if (this.disabled) {
          columnItem.disabled = this.disabled
        }
        //单独占一行
        if (this.fieldSpanOneLine.includes(item.fieldShowType)) {
          columnItem.span = 24
        }
        columnItem.order = untreatedColumn.length - index
        if (item.isReadOnly === 1) {
          //只读
          columnItem.readonly = true
        }
        if (item.isShowForm === 0) {
          //表单不显示
          columnItem.display = false
          tableColumn.push(columnItem)
          return false
        }
        /* ====== 控件处理 ===== */

        //数据格式化
        if (
          [
            'checkbox',
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
            columnItem.props = {}
            columnItem.activeIconClass = '无'
            columnItem.inactiveIconClass = '无'

            let extend = ''
            //判断是否自定义保存参数
            if (item.fieldExtendJson) {
              try {
                extend = JSON.parse(item.fieldExtendJson)
              } catch (error) {
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
            }

            columnItem.value = 'N'
          }
        }
        //下拉搜索配置
        if (item.fieldShowType == 'sel_search') {
          //表名  存储字段值  显示字段值
          if (
            item.dictTable != '' &&
            item.dictField != '' &&
            item.dictText != ''
          ) {
            columnItem = {
              ...columnItem,
              dicUrl: `/api/${apiRequestHead}/sys/sys/dict/getDict/${item.dictTable},${item.dictText},${item.dictField}`,
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
            this.$message({
              message: `<${item.dbFieldTxt}>下拉搜索控件的字典配置错误，需要完整配置字典table、字典code、字典text`,
              type: 'warning',
            })
            columnItem.dicData = []
          }
        }

        //文件 图片
        if (['image', 'file'].includes(item.fieldShowType)) {
          columnItem.type = 'upload'
          columnItem.action = `api/${apiRequestHead}/cgform-api/upload/file`
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
            dicData: this.allUserData,
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
            dicData: this.allDepartData,
            props: {
              label: 'deptName',
              value: 'id',
            },
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
            if (['Integer', 'Double'].includes(item.dbType)) {
              columnItem.type = 'number'
            }
            break
          case 'list':
            columnItem.type = 'select'
            //下拉框
            break
          case 'radio':
            columnItem.type = 'radio'
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
            //日期(yyyy-MM-dd)
            break
          case 'datetime':
            columnItem.type = 'datetime'
            columnItem.format = 'yyyy-MM-dd HH:mm:ss'
            columnItem.valueFormat = 'yyyy-MM-dd HH:mm:ss'
            //日期（yyyy-MM-dd HH:mm:ss）
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
                callback(new Error(rules.msg))
              } else {
                callback()
              }
            }
          } else {
            validateRulesAll[item.dbFieldName] = (rule, value, callback) => {
              callback()
            }
          }
          columnItem.rules = [
            {
              validator: validateRulesAll[item.dbFieldName],
              trigger: 'blur',
            },
          ]
        }
        if (item.fieldMustInput == '1') {
          columnItem.rules.push({
            required: true,
            trigger: 'blur',
            message: '值不能为空',
          })
        }
        //处理字典
        tableColumn.push(columnItem)
      })
      return tableColumn
    },
    //添加数据
    addSubListData(rows) {
      this.tableForm = {
        ...this.tableForm,
        ...rows,
      }
    },
    //清除数据
    clearSubListData() {
      for (let key in this.tableForm) {
        this.tableForm[key] = ''
      }
    },
  },
}
</script>

<style>
</style>