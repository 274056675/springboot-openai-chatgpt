<template>
  <div
    :class="{ 'form-view-min-height': loading }"
    v-loading="loading && formOptionData.viewObj.type == 'view'"
  >
    <form-custom
      v-if="isShow && formOptionData.viewObj.type == 'view'"
      ref="formCustom"
      :formOption="widgetFormPreview"
      :formOpenType="formOptionData.formOpenType"
      :actionData="formOptionData.actionData"
      :onlineFormId="formOptionData.onlineFormId"
      :allFormListData="formData"
      :btnPermissions="formOptionData.btnPermissions"
      :closeDialogForm="performHiedViewFun.bind(this)"
      :isDetailStyle="formOptionData.isDetailStyle"
    ></form-custom>
    <el-dialog
      v-loading="loading"
      element-loading-background="transparent"
      v-if="formOptionData.viewObj.type == 'dialog'"
      top="10vh"
      :title="formOptionData.viewObj.title"
      :visible.sync="formOptionData.viewObj.isShow"
      :destroy-on-close="formOptionData.viewObj.destroy?true:false"
      :modal-append-to-body="true"
      :close-on-click-modal="false"
      :append-to-body="true"
      :width="formOptionData.viewObj.width"
      v-bind="formOptionData.viewObj.params"
      custom-class="dialog-form-view-min-height"
    >
      <form-custom
        v-if="isShow"
        ref="formCustom"
        :formOption="widgetFormPreview"
        :formOpenType="formOptionData.formOpenType"
        :actionData="formOptionData.actionData"
        :onlineFormId="formOptionData.onlineFormId"
        :allFormListData="formData"
        :closeDialogForm="performHiedViewFun.bind(this)"
        :btnPermissions="formOptionData.btnPermissions"
        :isDetailStyle="formOptionData.isDetailStyle"
      ></form-custom>
      <span slot="footer" class="dialog-footer"></span>
    </el-dialog>
    <el-drawer
      v-loading="loading"
      v-if="formOptionData.viewObj.type == 'drawer'"
      element-loading-background="rgba(255,255,255,0.3)"
      :title="formOptionData.viewObj.title"
      :size="formOptionData.viewObj.width"
      :visible.sync="formOptionData.viewObj.isShow"
      :destroy-on-close="formOptionData.viewObj.destroy?true:false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      v-bind="formOptionData.viewObj.params"
    >
      <form-custom
        v-if="isShow"
        ref="formCustom"
        :formOption="widgetFormPreview"
        :formOpenType="formOptionData.formOpenType"
        :actionData="formOptionData.actionData"
        :onlineFormId="formOptionData.onlineFormId"
        :allFormListData="formData"
        :closeDialogForm="performHiedViewFun.bind(this)"
        :btnPermissions="formOptionData.btnPermissions"
        :isDetailStyle="formOptionData.isDetailStyle"
      ></form-custom>
    </el-drawer>
  </div>
</template>

<script>
import { getdetailDataApi } from '@/api/research/form'
import { getDataApi, getDataDetailApi } from '@/api/research/codelist'
export default {
  name: 'FormView',
  data() {
    return {
      isInit: false,
      loading: false,
      timer: null,
      isShow: false,
      widgetFormPreview: '', //表单配置
      desFormData: {},
      formData: {},
    }
  },
  watch: {
    formOptionData: {
      handler(newVal) {
        
        if (this.isInit == false) {
          if (newVal.defaultData && !newVal.viewObj.isGetData) {
            this.formData = {
              ...newVal.defaultData,
            }
          }
          if (newVal.viewObj.isGetData) {
            this.getFormDataFun(newVal.tableId)
          }
        } else if (newVal.viewObj.isShow) {
          //表单显示的时候执行
          // 赋予默认值
          if (newVal.defaultData && !newVal.viewObj.isGetData) {
            this.isShow = false
            this.loading = true
            this.formData = {
              ...newVal.defaultData,
            }
            if (!newVal.viewObj.carryInit && !newVal.viewObj.isGetData) {
              setTimeout(() => {
                this.isShow = true
                this.loading = false
              }, 200)
            }
          }
          if (newVal.viewObj.carryInit) {
            this.init()
          }
          if (newVal.viewObj.isGetData) {
            this.getFormDataFun(newVal.tableId)
          }
        }
      },
      immediate: true, //一开始先执行一次handler
      deep: true, //深监听
    },
  },
  props: [
    'formOptionData',
    /* 
      formId:'表单设计id',
      onlineFormId:'表单开发id',
      params:{},//数据接口请求参数
      formOpenType:'当前弹窗类型',
      actionData:{
        type:'接口存储类型',
        noRouter:true,//不启用路由配置
        closeType:,//关闭类型
        isMessage:,//是否提示
      },
      btnPermissions:{ //表单按钮权限配置
        clearBtn: true,
        cancelBtn: false,
        submitBtn: true,
      },
      
      'viewObj':{
        isShow:false, //是否显示表单
        type:'drawer', //弹窗类型  表单:view  抽屉:drawer 弹窗:dialog
        title:'编辑', //抽屉、弹窗的标题文本
        width:1100, //弹窗宽度
        isGetData:false,//是否需要获取数据
        carryInit:false,//是否需要重新初始化表单配置
        isExternalSearch:true,//是否需要合并外部搜索
      }, //展示类型配置
      defaultData:{ //默认的数据
      },
      isDetailData:true,//是否获取父子表数据
      dataId:,//父子表数据的数据id
    */
    'formViewControlFun',
    'params', //搜索参数
  ],
  mounted() {
    if (this.formOptionData.formId && this.formOptionData.isLazy !== true) {
      this.init()
    }
  },
  methods: {
    async performHiedViewFun(type, data) {
      if (typeof type == 'function') {
        if (this.formOptionData.submitFun) {
          try {
            this.formOptionData
              .submitFun(data)
              .then(() => {
                type()
                 this.formViewControlFun('hide')
              })
              .catch(() => {
                type()
              })
          } catch (error) {
            type()
            console.warn('表单自定义提交方法异常' + error)
          }
        } else {
          type()
          console.warn('请配置自定义提交方法  submitFun')
        }
      }
      if (type) {
        return this.formViewControlFun(type, data)
      } else {
        this.formViewControlFun('hide')
      }
    },
    async init() {
      this.isShow = false
      this.loading = true
      this.widgetFormPreview = ''
      //获取表单配置
      let detailRes = await getdetailDataApi(this.formOptionData.formId)
      let options = {}
      this.desFormData = detailRes.data.data
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
      if (!this.formOptionData.viewObj.isGetData) {
        this.isInit = true
        this.isShow = true
        this.loading = false
      }
    },
    getFormDataFun() {
      //获取表单数据
      this.loading = true
      this.isShow = false
      if (this.timer) {
        clearTimeout(this.timer)
      }
      this.timer = setTimeout(async () => {
        if (this.formOptionData.defaultData) {
          this.formData = {
            ...this.formOptionData.defaultData,
          }
        } else {
          this.formData = {}
        }

        //判断搜索配置是否有值
        let searchObj = {
          ...this.formOptionData.params,
        }
        if (
          this.params &&
          this.formOptionData.viewObj.isExternalSearch !== false
        ) {
          searchObj = {
            ...searchObj,
            ...this.params,
          }
        }
        let objKey = Object.keys(searchObj)
        
        let bool = true
        objKey.forEach((item) => {
          let value = searchObj[item]
          if (value === undefined || value === '' || value === null) {
            bool = false
          }
        })
        let isGetDataInfo = false
        //通过搜索取第一条数据赋值给表单
        if (this.formOptionData.isDetailData != true) {
          let params = {}
          if (bool) {
            params = searchObj
          }
          let tableDataRes = await getDataApi(
            this.formOptionData.onlineFormId,
            params
          )
          let data = tableDataRes.data.data
          if (data && data.records && data.records.length > 0) {
            this.formData = {
              ...this.formData,
              ...data.records[0],
            }
          }
          isGetDataInfo = true
        } else {
          //通过数据id取数据赋值给表单
          //获取父子表数据
          let tableDataRes = await getDataDetailApi(
            this.formOptionData.onlineFormId,
            this.formOptionData.dataId,
            searchObj
          )
          let data = tableDataRes.data.data
          if (data) {
            this.formData = {
              ...this.formData,
              ...data,
            }
          }
          isGetDataInfo = true
        }

        // 等待表单配置获取完成后显示表单
        let timer = setInterval(() => {
          this.isShow = false
          if (this.widgetFormPreview != '' && isGetDataInfo === true) {
            clearInterval(timer)
            console.log(
              'form-view======>数据显示',
              timer,
              this.deepClone(this.formData),
              this.isShow
            )
            this.isInit = true
            this.isShow = true
            this.loading = false
          }
        }, 1000)
      }, 1000)
    },
  },
}
</script>
<style lang="scss" scoped>
.form-view-min-height {
  min-height: 100px;
}
</style>
<style lang="scss">
.el-drawer__body {
  overflow: auto;
  /* overflow-x: auto; */
}
.department-declare-info {
  .btn {
    text-align: right;
    padding-right: 20px;
  }
}
.dialog-form-view-min-height {
  .el-dialog__body {
    min-height: 100px;
  }
}
</style>
