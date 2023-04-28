<template>
  <!-- 组件显示 -->
  <div>
    <el-dialog
      v-dialogdrag
      element-loading-background="transparent"
      v-if="formOptionData.viewObj.type == 'dialog'"
      :title="formOptionData.viewObj.title"
      :visible.sync="formOptionData.viewObj.isShow"
      :destroy-on-close="formOptionData.viewObj.destroy ? true : false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      :width="formOptionData.viewObj.width"
      v-bind="formOptionData.viewObj.params"
    >
      <div
        v-if="
          formOptionData.viewObj.destroy ? formOptionData.viewObj.isShow : true
        "
      >
        <component
          :ref="`${formOptionData.type}`"
          :is="formOptionData.type"
          :defaultData="formOptionData.defaultData"
          :params="formOptionData.params"
          :controlViewFun="controlViewFun.bind(this)"
          :isShow="formOptionData.viewObj.isShow"
        ></component>
      </div>
      <span slot="footer" class="dialog-footer"></span>
    </el-dialog>
    <el-drawer
      v-if="formOptionData.viewObj.type == 'drawer'"
      element-loading-background="rgba(255,255,255,0.3)"
      :title="formOptionData.viewObj.title"
      :size="formOptionData.viewObj.width"
      :visible.sync="formOptionData.viewObj.isShow"
      :destroy-on-close="formOptionData.viewObj.destroy ? true : false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      v-bind="formOptionData.viewObj.params"
    >
      <div
        v-if="
          formOptionData.viewObj.destroy ? formOptionData.viewObj.isShow : true
        "
      >
        <component
          :ref="`${formOptionData.type}`"
          :is="formOptionData.type"
          :defaultData="formOptionData.defaultData"
          :params="formOptionData.params"
          :controlViewFun="controlViewFun.bind(this)"
          :isShow="formOptionData.viewObj.isShow"
        ></component>
      </div>
    </el-drawer>
  </div>
</template>

<script>

export default {
  name: "controlView",
  data() {
    return {};
  },
  watch: {},
  props: [
    "formOptionData",
    /* 
      'viewObj':{
        isShow:false, //是否显示
        type:'drawer', //弹窗类型  表单:view  抽屉:drawer 弹窗:dialog
        title:'编辑', //抽屉、弹窗的标题文本
        width:1100, //弹窗宽度
      }, //展示类型配置
      type:'',//组件类型
      defaultData:{ //默认的数据
      },
    */
    "controlViewFun",
  ],
  mounted() {
    this.init();
  },
  methods: {
    async init() {},
  },
};
</script>
<style lang="scss">
.el-drawer__body {
  overflow: auto;
}
</style>
