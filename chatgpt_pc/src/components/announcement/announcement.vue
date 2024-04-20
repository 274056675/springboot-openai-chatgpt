<template>
  <div class="announcement">
    <el-dialog :visible.sync="dialogVisible" width="800px" :show-close="false">
      <div class="announcement-box">
        <div class="announcement-title">消息通知</div>
        <div class="announcement-list">
          <div class="announcement-item" v-for="item in announcementList" :key="item.id">
            <div class="announcement-item_nav">
              <div class="announcement-item-title">{{ item.title }}</div>
              <div class="announcement-item-time">{{ item.create_time }}</div>
            </div>
            <div class="announcement-item-content">{{ item.content_part }}</div>
            <div class="announcement-item-ckxq" @click="goPage(item.id)">查看详情》</div>
          </div>
        </div>
        <el-pagination :small="true" @size-change="announcementSizeChange" @current-change="announcementCurrentChange"
          :current-page.sync="announcementPageObj.current" :page-size="announcementPageObj.size"
          layout="prev, pager, next, jumper" :total="announcementPageObj.total"></el-pagination>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import router from '@/router';
import { getNoticeListApi } from '@/api/system.js'
export default {
  name: 'announcement',
  data() {
    return {
      dialogVisible: false,
      announcementList: [],
      announcementPageObj: {
        current: 1, // 当前页
        size: 10, // 每页的数量
        total: 0,
      },
    }
  },
  watch: {
    dialogVisible(val) {
      if (val) {
        this.announcementPageObj.current = 1
        this.getNoticeList()
      }
    },
  },
  mounted() {
    this.getNoticeList()
  },
  methods: {
    goPage(id) {
      router.push({
        name: 'inform',
        query: { id },
      })
      this.dialogVisible = false
    },
    getNoticeList() {
      let params = {
        current: this.announcementPageObj.current,
        size: this.announcementPageObj.size,
      }
      getNoticeListApi(params)
        .then((res) => {
          let data = res.data
          this.announcementPageObj.total = data.total
          this.announcementList = data.records
        })
        .catch((err) => { })
    },
    announcementSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    // 切换分页
    announcementCurrentChange(val) {
      this.announcementPageObj.current = val
      this.getNoticeList()
    },
  },
}
</script>

<style lang="scss">
.el-dialog {
  margin-top: 50px !important;
}

.announcement {
  width: 100%;
  height: 100%;

  .announcement-box {
    display: flex;
    flex-direction: column;
    height: 600px;
    padding: 20px;
    overflow-y: auto;
    scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
    scrollbar-width: none;
    scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
    box-sizing: border-box;

    .announcement-title {
      font-size: 18px;
      color: #000;
      font-weight: 700;
    }

    .announcement-list {
      height: 100%;
      overflow-y: auto;
      scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
      scrollbar-width: none;
      scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
      color: #2d303b;

      .announcement-item {
        padding: 20px 20px 0;
        border-bottom: 2px solid #e6e6e6;

        .announcement-item_nav {
          display: flex;
          align-items: center;
          margin-bottom: 20px;

          .announcement-item-title {
            margin-right: 30px;
            font-size: 14px;
            color: #2d303b;
            font-weight: 700;
          }
        }

        .announcement-item-ckxq {
          padding: 10px 0 20px;
          color: #960a0f;
          cursor: pointer;
        }
      }
    }
  }
}
</style>