<template>
  <div>
    <el-row>
      <el-col :span="24">
        <third-register></third-register>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <basic-container>欢迎使用</basic-container>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'wel',
  data() {
    return {
      activeNames: ['1', '2', '3', '5'],
      logActiveNames: ['26'],
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
  },
  methods: {
    handleChange(val) {
      window.console.log(val)
    },
  },
}
</script>

<style>
.el-font-size {
  font-size: 14px;
}
</style>

