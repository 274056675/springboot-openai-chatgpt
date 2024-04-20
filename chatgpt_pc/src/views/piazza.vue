<template>
  <div class="piazza" ref="scrollView" id="scroll-view" @scroll="handleScroll">
    <div class="piazza-title">
      <span style="font-size: 2.5rem; font-weight: 700; margin: 20px 0">超级AI大脑</span>
      <span style="font-size: 1.125rem"
        >一个可以对话、聆听、学习和挑战的自然语言机器人</span
      >
    </div>
    <div class="piazza-tabs">
      <div
        class="piazza-tabs_view"
        v-for="(item, index) in tabsList"
        :key="index"
        @click="goPage(item.type)"
      >
        <img
          style="margin-right: 1.25rem; width: 3.75rem; height: 3.75rem"
          :src="require('@/' + item.img)"
        />
        <span>{{ item.value }}</span>
      </div>
    </div>
    <div class="piazza-content">
      <div class="drawing">
        <div class="drawing-piazza">
          <div class="drawing-piazza_title">AI绘画广场</div>
          <div class="drawing-piazza_recommend">
            <span style="margin: 0.375rem">排序:</span>
            <el-dropdown @command="recommendChange">
              <span class="el-dropdown-link">
                {{ recommendValue }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu>
                <template v-for="(item, index) in recommendList">
                  <el-dropdown-item :key="index" :command="beforeHandleRecommend(item)">{{
                    item
                  }}</el-dropdown-item>
                </template>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
        <div class="search">
          <el-input
            placeholder="搜描述词 找AI创作"
            v-model="params.name"
            clearable
            prefix-icon="el-icon-search"
            @change="
              params.current = 1;
              imageList = [];
              getimageList();
            "
          ></el-input>
        </div>
      </div>
      <div class="waterfall">
        <waterfallVue :columns="6" :imageList="imageList"></waterfallVue>
        <div
          style="padding: 10px 0 10px; text-align: center; color: #999; font-size: 12px"
        >
          {{ loadingValue }}
        </div>
        <div
          style="padding: 10px 0 30px; text-align: center; color: #999; font-size: 12px"
        >
          备案号: 粤ICP备2023048279号-1
        </div>
      </div>
    </div>
    <el-dialog :visible.sync="aidjShow" width="800px">
      <div class="grade">
        <div class="my-grade-box">
          <img style="width: 128px; height: 128px" :src="require('@/' + myGrade.img)" />
          <div class="my-grade">
            <div>
              我的等级: <span>{{ myGrade.value }}</span>
            </div>
            <div>{{ myGrade.useNum }}</div>
          </div>
        </div>
        <div class="grade-list">
          <div class="grade-item" v-for="(item, index) in gradeList" :key="index">
            <img style="width: 96px; height: 96px" :src="require('@/' + item.img)" />
            <div class="grade-item-useNum">{{ item.useNum }}</div>
            <div class="grade-item-value" style="margin-top: 10px">{{ item.value }}</div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCommunityList } from "@/api/plot";
import waterfallVue from "@/components/waterfall.vue";
import { mapGetters } from "vuex";
export default {
  name: "piazza",
  components: {
    waterfallVue,
  },
  data() {
    return {
      myGrade: {
        img: "static/grade/1.png",
        value: "倔强青铜",
        useNum: "使用1-30次数",
      }, // 我的等级
      aidjShow: false,
      tabsList: [
        // tabs导航数据
        { img: "static/index/chat.png", value: "AI聊天", type: "ailt" },
        { img: "static/AI-logo.png", value: "AI等级", type: "aidj" },
        { img: "static/index/zn.png", value: "新手必读指南", type: "aizn" },
      ],
      gradeList: [
        { img: "static/grade/1.png", value: "倔强青铜", useNum: "使用0-10次数" },
        { img: "static/grade/2.png", value: "秩序白银", useNum: "使用11-50次数" },
        { img: "static/grade/3.png", value: "荣耀黄金", useNum: "使用51-100次数" },
        { img: "static/grade/4.png", value: "尊贵铂金", useNum: "使用101-500次数" },
        { img: "static/grade/5.png", value: "永恒钻石", useNum: "使用501-1000次数" },
        { img: "static/grade/6.png", value: "至尊星耀", useNum: "使用1001-5000次数" },
        {
          img: "static/grade/7.png",
          value: "最强王者",
          useNum: "使用5001-1000000次数以上",
        },
      ], // 等级列表
      recommendList: ["收藏最多", "最新"],
      recommendValue: "收藏最多",
      searchValue: "", //搜索的关键词
      imageList: [], //绘画广场数据
      params: { size: 24, current: 1, type: "", name: "" }, // 绘画广场请求参数
      pages: 1, // 绘画广场请求页数
      state: false, //状态码,是否请求数据
      loading: false,
      loadingValue: "加载更多",
    };
  },
  computed: {
    ...mapGetters(["isLogin", "userInfo"]),
  },
  mounted() {
    this.getimageList();
    // this.$nextTick(() => {
    //   console.log('clientHeight', this.$refs.scrollView.clientHeight)
    // })
  },
  methods: {
    // 跳转页面
    goPage(type) {
      if (type === "ailt") {
        this.$router.push({ name: "chat" });
        this.$emit("childClick", "/index/chat");
      } else if (type === "aihh") {
        this.$router.push({ name: "drawing" });
        this.$emit("childClick", "/index/drawing");
      } else if (type === "aidj") {
        if (!this.isLogin) {
          return this.$router.push({ name: "login" });
        }
        this.gradeList.forEach((item) => {
          if (item.value === this.userInfo.leveTitler) {
            this.myGrade = item;
          }
        });
        this.aidjShow = true;
      } else if (type === "aizn") {
        this.$router.push({ name: "handbook" });
      }
    },
    // 改变绘画广场数据类型
    recommendChange(val) {
      if (this.recommendValue != val || !this.state) {
        this.recommendValue = val;
        if (val === "最新") {
          this.params.type = "new";
        } else {
          this.params.type = "";
        }
        this.params.current = 1;
        this.pages = 1;
        this.imageList = [];
        this.getimageList();
      }
    },
    beforeHandleRecommend(val) {
      return val;
    },
    // 绘画广场
    getimageList() {
      if (this.state) return;
      this.state = true;
      setTimeout(() => {
        this.state = false;
      }, 3000);
      this.loadingValue = "加载中...";
      if (this.params.current > this.pages) {
        console.log(this.pages);
        return (this.loadingValue = "已经到底了");
      }
      this.loading = true;
      getCommunityList(this.params)
        .then((res) => {
          this.imageList = res.data.records;
          this.pages = res.data.pages;
          this.params.current++;
          this.loadingValue = "加载更多";
          this.state = false;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    handleScroll(e) {
      const { scrollTop, clientHeight, scrollHeight } = e.target;
      // console.log(scrollTop, clientHeight, scrollHeight)
      if (scrollTop + clientHeight + 1 >= scrollHeight) {
        // alert('滚动到底部啦')
        this.getimageList();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.el-dialog {
  margin: 50px auto 0 !important;
}
.piazza {
  width: 100%;
  height: 100%;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
  scrollbar-width: none;
  scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失

  .piazza-title {
    text-align: center;
    display: flex;
    justify-content: center;
    flex-direction: column;
    color: #960a0f;
  }

  .piazza-tabs {
    margin-top: 1.25rem;
    display: grid;
    grid-template-columns: auto auto auto auto;
    grid-column-gap: 1.25rem;

    .piazza-tabs_view {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 15rem;
      height: 6.25rem;
      line-height: 6.25rem;
      border-radius: 0.625rem;
      font-size: 1.25rem;
      background-color: #ffffff;
      cursor: pointer;
    }
  }

  .piazza-content {
    width: 88%;
    // height: 100%;
    margin-top: 1.875rem;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    // background-color: #960a0f;

    .drawing {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .drawing-piazza {
        display: flex;
        flex-direction: column;
        color: #960a0f;

        .drawing-piazza_title {
          padding: 0.9375rem 0;
          font-size: 1.125rem;
        }

        .drawing-piazza_recommend {
          display: flex;
          align-items: center;
          padding-bottom: 0.9375rem;
          font-size: 0.75rem;

          .el-dropdown-link {
            font-size: 0.75rem;
            cursor: pointer;
            color: #960a0f;
          }

          .el-icon-arrow-down {
            font-size: 0.75rem;
          }
        }
      }

      .search {
      }
    }

    .waterfall {
      height: 100%;
    }
  }

  .grade {
    width: 800px;
    padding: 16px;
    box-sizing: border-box;

    .my-grade-box {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 10px;
      width: 100%;
      height: 160px;
      background: url("../static/grade/gradebg.png") no-repeat;
      background-size: 100% 100%;
      border-radius: 10px;

      .my-grade {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 48px;
        font-weight: 700;

        // margin-left: 10px;
        div {
          font-size: 12px;
        }

        span {
          color: #000000;
          font-size: 18px;
        }
      }
    }

    .grade-list {
      margin-top: 24px;
      display: grid;
      grid-template-columns: repeat(4, auto);
      align-items: center;
      justify-content: space-between;
      gap: 20px;

      .grade-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 166px;
        height: 200px;
        background-color: #faf4ec;
        border-radius: 10px;

        .grade-item-useNum {
          font-size: 14px;
          text-align: center;
          color: #666666;
        }

        .grade-item-value {
          font-size: 16px;
          font-weight: 700;
          color: #000;
        }
      }
    }
  }
}
</style>
