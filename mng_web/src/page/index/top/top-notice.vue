<template>
  <el-popover placement="bottom"
              width="350"
              trigger="click">

    <el-tabs v-model="activeName">
      <el-tab-pane label="邮件(1)"
                   name="first"></el-tab-pane>
      <el-tab-pane label="消息(2)"
                   name="second"></el-tab-pane>
      <el-tab-pane label="通知"
                   name="third"></el-tab-pane>
    </el-tabs>
    <el-scrollbar style="height:300px">
      <avue-notice :data="data"
                   :option="option"
                   @page-change="pageChange"></avue-notice>
    </el-scrollbar>
    <div slot="reference">
      <el-badge is-dot>
        <i class="el-icon-bell"></i>
      </el-badge>
    </div>
  </el-popover>
</template>

<script>
let list = [{
  img: '/img/bg/vip1.png',
  title: '史蒂夫·乔布斯 关注了你',
  subtitle: '05-08 15:08',
  tag: '已经开始',
  status: 0
}, {
  img: '/img/bg/vip2.png',
  title: '斯蒂夫·沃兹尼亚克 关注了你',
  subtitle: '05-08 15:08',
  tag: '未开始',
  status: 1
}, {
  img: '/img/bg/vip3.png',
  title: '蒂姆·库克 关注了你',
  subtitle: '05-08 15:08',
  status: 3,
  tag: '有错误'
}, {
  img: '/img/bg/vip4.png',
  title: '比尔·费尔南德斯 关注了你',
  subtitle: '05-08 15:08',
  status: 4,
  tag: '已完成'
}];
export default {
  name: "top-notice",
  data () {
    return {
      activeName: 'first',
      option: {
        props: {
          img: 'img',
          title: 'title',
          subtitle: 'subtitle',
          tag: 'tag',
          status: 'status'
        },
      },
      data: list,
    }
  },
  created () {

  },
  methods: {
    pageChange (page, done) {
      setTimeout(() => {
        this.$message.success('页码' + page)
        this.data = this.data.concat(list);
        done();
      }, 1000)

    },
  }
};
</script>

<style lang="scss" scoped>
</style>
