<template>
  <basic-container>
    <h3>标签</h3>
    <el-button type="primary"
               size="small"
               @click="$router.push('/test/index')">打开一个页面
    </el-button>
    <el-button type="primary"
               size="small"
               @click="$router.$avueRouter.closeTag('/test/index')">关闭打开的页面
    </el-button>
    <el-button type="primary"
               size="small"
               @click="$router.$avueRouter.closeTag()">关闭本标签
    </el-button>

  </basic-container>
</template>

<script>
  export default {
    methods: {}
  };
</script>

<style>
</style>
