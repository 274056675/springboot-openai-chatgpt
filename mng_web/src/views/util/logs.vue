<template>
  <basic-container>
    <h3>请打开浏览器控制台，然后点击下面的按钮</h3>
    <el-button type="danger"
               size="small"
               @click="handleNewError">触发一个错误
    </el-button>
  </basic-container>
</template>

<script>
  export default {
    methods: {
      handleNewError() {
        window.console.log(a); // eslint-disable-line
      }
    }
  };
</script>
