<template>
  <div class="menu-left-btns-box">
    <!-- 表单设计引用的新增 -->
    <el-button
      size="small"
      type="primary"
      icon="el-icon-plus"
      @click="that.formDesignButtonTriggerFun('add')"
      v-if="!that.tableOption.addBtn && that.tablePermission.addBtn"
    >{{that.tableOption.addBtnText ? that.tableOption.addBtnText : "新增"}}</el-button>
    <!-- 表单开发自定义按钮 -->
    <el-button
      v-for="item in that.customButtonTop"
      :key="item.id"
      size="small"
      type="primary"
      @click="that.allCustomButtonFun(item.buttonCode,item.buttonStyle,item.optType,that)"
    >
      <i v-if="item.buttonIcon" :class="item.buttonIcon" style="margin-right: 5px"></i>
      {{ item.buttonName }}
    </el-button>
    <!-- 导出 -->
    <el-button
      size="small"
      type="primary"
      icon="el-icon-upload2"
      @click="that.carryTableButtonFun('export')"
      v-if="that.tablePermission.exportBtn"
    >{{that.tableOption.excelBtnText ? that.tableOption.excelBtnText : "导出"}}</el-button>
    <!-- 导入 -->
    <el-button
      size="small"
      type="primary"
      icon="el-icon-download"
      @click="that.carryTableButtonFun('inport')"
      v-if="that.tablePermission.inportBtn"
    >{{that.tableOption.inportBtnText ? that.tableOption.inportBtnText : "导入"}}</el-button>
    <!-- 批量删除 -->
    <el-button
      size="small"
      type="primary"
      icon="el-icon-search"
      @click="that.searchChangeFun(that.tableQueryData,()=>{})"
      v-if="that.tableSearchType=='interior'"
    >搜索</el-button>
    <el-button
      size="small"
      type="primary"
      icon="el-icon-refresh-right"
      @click="that.searchResetFun"
      v-if="that.tableSearchType=='interior'"
    >清空搜索</el-button>
    <el-button
      size="small"
      icon="el-icon-delete"
      @click="that.deleteAllSelectData"
      v-show="that.tableSelectId.length &&that.themeTemplate != 'erp' &&that.tablePermission.allDelBtn"
    >批量删除</el-button>
  </div>
</template>

<script>
export default {
  props: {
    that: Object,
  },
}
</script>

<style lang="scss" scoped>
.menu-left-btns-box {
  width: 100%;
  display: inline;
}
</style>