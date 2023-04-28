<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {};
  },
  watch: {},
  created() {},
  methods: {},
  computed: {},
};
</script>
<style lang="scss">
#app {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.avue--detail .el-col {
  margin-bottom: 0;
}
.avue-dialog {
  .el-dialog__body {
    .avue-input-icon__list {
      .avue-input-icon__item {
        p {
          display: none;
        }
      }
    }
  }
}
.avue-ueditor {
  ._wangEditor_btn_sourcecode {
    display: none;
  }
}
</style>
