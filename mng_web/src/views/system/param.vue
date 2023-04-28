<template>
  <basic-container>
    <avue-crud v-bind="bindVal"
               v-on="onEvent"
               v-model="form"
               :permission="permissionList"
               :page.sync="page">
      <template slot="menuLeft">
        <el-button type="danger"
                   size="small"
                   icon="el-icon-delete"
                   v-if="permission.param_delete"
                   plain
                   @click="handleDelete">删 除
        </el-button>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>

export default window.$crudCommon({
  data() {
    return {}
  },
  computed: {
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.param_add, false),
        viewBtn: this.vaildData(this.permission.param_view, false),
        delBtn: this.vaildData(this.permission.param_delete, false),
        editBtn: this.vaildData(this.permission.param_edit, false)
      };
    }
  },
  methods: {}
}, {
  //模块路径
  name: 'system/param',
})
</script>

<style>
</style>
