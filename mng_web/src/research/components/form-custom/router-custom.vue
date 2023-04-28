<template>
  <!-- 表单设计路由配置 转跳到表单 -->
  <div class="router-custom">
    <el-dialog
      v-loading="formLoading"
      :title="formDesignTitle"
      :visible.sync="isDialogFormDesign"
      :fullscreen="isFormFullscreenDesign"
      :modal-append-to-body="true"
      :append-to-body="true"
      :close-on-click-modal="true"
      :before-close="handleClose"
      width="80%"
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
          :actionData="actionData"
          :allFormListData="allFormListData"
          :closeDialogForm="closeDialogForm.bind(this)"
        ></form-custom>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getdetailDataApi, getFormIdApi } from '@/api/research/form'
export default {
  // currFormCode：当前表单编码  isDialogForm:是否弹窗表单
  props: ['currFormCode', 'destroyRouterFun', 'formData'],
  mounted() {
    this.init()
  },
  methods: {
    async init() {
      //根据code获取表单id
      let idRes = await getFormIdApi(this.currFormCode)
      this.formId = idRes.data.data.id
      this.formDesignTitle = idRes.data.data.formName
      //查询配置数据
      let detailRes = await getdetailDataApi(this.formId)
      let options = {}
      this.actionData.desForm = detailRes.data.data
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
      this.allFormListData = this.formData
      this.isDialogFormDesign = true
      this.formLoading = false
    },
    handleClose(done) {
      this.destroyRouterFun()
      done()
    },
    closeDialogForm() {
      this.isDialogFormDesign = false
      this.destroyRouterFun()
    },
  },
  data() {
    return {
      formLoading: true,
      formId: '',
      formDesignTitle: '', //弹窗文本
      isFormFullscreenDesign: false, //全屏开关
      isDialogFormDesign: false, //弹窗开关
      widgetFormPreview: {}, //表单配置
      formOpenType: 'add_router', //类型
      actionData: {
        type: 'formlist',
      },
      allFormListData: {}, //当前数据
    }
  },
}
</script>

<style>
</style>