<template>
  <div class="rl-detail">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部" name="all">
        <div class="box" ref="init1">
          <div class="active-box" v-for="(item, index) in activeList" :key="index">
            <div style="color:#960A0F;width:36%;">{{ item.title }}</div>
            <div style="color:#0096FF;">{{ item.num }}</div>
            <div style="color:#999999;width:36%;text-align:right;">{{ item.time }}</div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="收入" name="income">
        <div class="box" ref="init2">
          <div class="active-box" v-for="(item, index) in activeList" :key="index">
            <div style="color:#960A0F;width:36%;">{{ item.title }}</div>
            <div style="color:#0096FF;">{{ item.num }}</div>
            <div style="color:#999999;width:36%;text-align:right;">{{ item.time }}</div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="支出" name="expend">
        <div class="box" ref="init3">
          <div class="active-box" v-for="(item, index) in activeList" :key="index">
            <div style="color:#960A0F;width:36%;">{{ item.title }}</div>
            <div style="color:#0096FF;">{{ item.num }}</div>
            <div style="color:#999999;width:36%;text-align:right;">{{ item.time }}</div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-pagination :small="true" @size-change="creditSizeChange" @current-change="creditCurrentChange"
      :current-page.sync="creditPageObj.current" :page-size="creditPageObj.size" layout="prev, pager, next, jumper"
      :total="creditPageObj.total"></el-pagination>
  </div>
</template>

<script>
import { getCreditListApi } from '@/api/user'
export default {
  name: 'rl-detail',
  data() {
    return {
      activeName: 'all',
      activeList: [
        //展示列表
        // { title: '时间', num: 0, time: '4008688' },
      ],
      creditPageObj: {
        current: 1, // 当前页
        size: 20, // 每页的数量
        total: 0,
      },
    }
  },
  mounted() {
    // 默认加载全部数据
    this.handleClick()
    // this.$nextTick(() => {
    //   console.log('init>>>>>>>>>>', window.getComputedStyle(this.$refs.init1).height)
    // })
  },
  methods: {
    // 点击跳转时请求数据
    handleClick(tab, event) {
      this.creditPageObj.current = 1
      this.getActiveList()
    },
    creditSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    // 切换分页
    creditCurrentChange(val) {
      this.creditPageObj.current = val
      this.getActiveList()
    },
    // 获取数据赋值
    async getActiveList() {
      let data
      let arr = []
      let params = {
        current: this.creditPageObj.current, // 当前页
        size: this.creditPageObj.size, // 每页的数量
        type: '', // ''全部, add收入, sub支出
      }
      if (this.activeName === 'all') {
        data = await getCreditListApi(params)
      } else if (this.activeName === 'income') {
        params.type = 'add'
        data = await getCreditListApi(params)
      } else if (this.activeName === 'expend') {
        params.type = 'sub'
        data = await getCreditListApi(params)
      }
      this.creditPageObj.total = data.data.total
      data.data.records.forEach((item) => {
        arr.push({
          title: item.remark,
          num: item.type === 'ADD' ? `+ ${item.num}` : `- ${item.num}`,
          time: item.create_time,
        })
      })
      this.activeList = arr
    }
  },
}
</script>

<style lang="scss">
.el-tabs__content {
  height: calc(100vh - 228px);
  overflow-y: auto;
  scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
  scrollbar-width: none;
  scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
}

.rl-detail {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;

  .box {
    // width: 68%;
    height: 100%;
    overflow-y: auto;
    scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
    scrollbar-width: none;
    scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失

    .active-box {
      height: 64px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-shadow: inset 0 -1px rgba(0, 0, 0, 0.1);
      font-size: 12px;
    }
  }
}
</style>