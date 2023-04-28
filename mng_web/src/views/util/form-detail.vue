<template>
  <basic-container>
    <h3>{{$route.query.id?'编辑':'新增'}}</h3>
    <avue-form :option="option"
               v-model="form">
      <template slot="menuForm">
        <el-button icon="el-icon-back"
                   @click="handleBack()">返 回</el-button>
      </template>
    </avue-form>
  </basic-container>
</template>

<script>
export default {
  data() {
    return {
      form: {},
      option: {
        labelWidth: 110,
        column: [
          {
            label: "姓名",
            prop: "name"
          }
        ]
      }
    };
  },
  methods: {
    handleBack() {
      this.$router.$avueRouter.closeTag();
      this.$router.back();
    }
  }
};
</script>

<style>
</style>
