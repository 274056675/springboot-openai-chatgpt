<template>
  <!-- 表单开发 自定义表单按钮-->
  <div class="menu-form-btns-box">
    <el-button
      v-for="item in that.customButtonFormEnd"
      :key="item.id"
      type="primary"
      size="small"
      @click="
              that.allCustomButtonFun(
                item.buttonCode,
                item.buttonStyle,
                item.optType,
                that,
                that.tableForm
              )
            "
    >
      <i v-if="item.buttonIcon" :class="item.buttonIcon"></i>
      {{ item.buttonName }}
    </el-button>
    <el-button
      v-if="that.tableCrudType == 'add'"
      :loading="scope.disabled"
      type="primary"
      size="small"
      @click="that.$refs.codeTestList.rowSave()"
    >
      <i class="el-icon-circle-plus-outline" v-show="!scope.disabled"></i>
      {{ that.tableOption.saveBtnText ? that.tableOption.saveBtnText : "保 存" }}
    </el-button>
    <el-button
      v-if="that.tableCrudType == 'edit'"
      :loading="scope.disabled"
      type="primary"
      size="small"
      @click="that.$refs.codeTestList.rowUpdate()"
    >
      <i class="el-icon-circle-check" v-show="!scope.disabled"></i>
      {{
      that.tableOption.updateBtnText ? that.tableOption.updateBtnText : "修 改"
      }}
    </el-button>
    <el-button size="small" @click="that.$refs.codeTestList.closeDialog()">
      <i class="el-icon-circle-close"></i>
      {{
      that.tableOption.cancelBtnText ? that.tableOption.cancelBtnText : "取 消"
      }}
    </el-button>
  </div>
</template>

<script>
export default {
  props: {
    scope: Object,
    that: Object,
  },
}
</script>

<style lang="scss" scoped>
.menu-form-btns-box {
  width: 100%;
  display: inline;
}
</style>