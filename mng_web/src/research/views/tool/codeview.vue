<template>
  <div class="code-view">
    <avue-crud
      ref="crud"
      :page.sync="tablePage"
      :data="tableData"
      :option="tableOption"
      :before-open="tableBeforeOpen"
      @search-change="searchChange"
      @selection-change="selectionChange"
      @on-load="onLoad"
      @row-save="saveTable"
      @row-update="updateTable"
    ></avue-crud>
  </div>
</template>

<script>
export default {}
</script>

<style>
</style>