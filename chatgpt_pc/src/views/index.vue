<template>
  <div class="index">
    <el-container style="height: 100%">
      <el-header>
        <div class="header">
          <div class="header-left">
            <div class="headline-img">
              <img style="width: 40px; height: 40px" src="../static/AI-logo.png" />
              <div class="headline-text" @click="$router.push({ name: 'index' })">
                超级AI大脑
              </div>
            </div>
            <el-menu
              :default-active="activeIndex"
              class="el-menu-demo"
              mode="horizontal"
              @select="handleSelect"
              router
              background-color="#eddbc3"
              active-text-color="#960A0F"
              text-color="#000000"
            >
              <el-menu-item index="/index/piazza">广场</el-menu-item>
              <el-menu-item index="/index/chat">AI聊天</el-menu-item>
            </el-menu>
            <a
              style="padding: 0 20px; color: #960a0f; text-decoration: none"
              href="https://gitee.com/ylzl/springboot-openai-chatgpt?_from=gitee_search"
              target="_Blank"
              >本站源码</a
            >
          </div>
          <div class="header-right">
            <div class="user-message">
              <div class="teg-style user-message_invite" @click="generateQrCode">
                邀请好友得积分
              </div>
              <div class="teg-style user-message_sign" @click="goSign">
                <span class="iconfont icon-qiandao"></span>
                <span style="margin-left: 5px">{{ isSign ? "已签到" : "签到" }}</span>
              </div>
              <div class="teg-style user-message_integral">
                <span class="iconfont icon-jifen"></span>
                <span style="margin-left: 5px"
                  >积分: {{ userInfo.questionCou ? userInfo.questionCou : 0 }}</span
                >
              </div>
            </div>
            <div class="user-login-box">
              <el-tooltip
                class="item"
                effect="dark"
                content="消息通知"
                placement="bottom"
              >
                <el-badge
                  :value="informList.length ? informList.length : 0"
                  :max="99"
                  style="margin: 0 10px"
                >
                  <span
                    class="iconfont icon-xiaoxi"
                    style="
                      color: #960a0f;
                      cursor: pointer;
                      margin-left: 10px;
                      font-size: 18px;
                    "
                    @click="informShow = !informShow"
                  ></span>
                </el-badge>
              </el-tooltip>
              <div
                class="user-login"
                @click="$router.push({ name: 'login' })"
                v-if="!isLogin"
              >
                登录/注册
              </div>
              <div class="avatar-box" v-else>
                <el-dropdown @command="recommendChange">
                  <el-avatar
                    class="avatar"
                    size="large"
                    :src="
                      userInfo.wxAvatar != ''
                        ? userInfo.wxAvatar
                        : require('@/static/df_avatar_nan.png')
                    "
                  ></el-avatar>
                  <el-dropdown-menu slot="dropdown">
                    <div
                      style="display: flex; flex-direction: column; align-items: center"
                    >
                      <el-dropdown-item
                        style="display: flex; align-items: center"
                        :command="beforeHandleRecommend('userinfo')"
                      >
                        <el-avatar
                          class="avatar"
                          size="medium"
                          :src="
                            userInfo.wxAvatar != ''
                              ? userInfo.wxAvatar
                              : require('@/static/df_avatar_nan.png')
                          "
                        ></el-avatar>
                        <span style="margin-left: 6px; font-size: ">{{ wxName }}</span>
                      </el-dropdown-item>
                      <el-dropdown-item
                        :command="beforeHandleRecommend('equity-center')"
                        style="
                          display: flex;
                          align-items: center;
                          color: #fff;
                          background: #e9b97d;
                          border-radius: 20px;
                          margin: 10px 0;
                        "
                      >
                        <span class="iconfont icon-huiyuan"></span>
                        <span style="margin-left: 6px">权益中心</span>
                      </el-dropdown-item>
                      <el-dropdown-item
                        v-for="(item, index) in userList"
                        :key="index"
                        class="dropdown-item"
                        :command="beforeHandleRecommend(item.type)"
                      >
                        <span
                          class="iconfont"
                          :class="item.icon"
                          style="margin-right: 6px"
                        ></span>
                        <span>{{ item.text }}</span>
                      </el-dropdown-item>
                      <el-dropdown-item
                        style="margin-top: 6px"
                        :command="beforeHandleRecommend('tcdl')"
                        >退出登录</el-dropdown-item
                      >
                    </div>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </div>
          </div>
        </div>
      </el-header>
      <el-main :style="defaultHeight">
        <router-view @childClick="childClick"></router-view>
      </el-main>
    </el-container>
    <el-dialog :visible.sync="informShow" width="30%" :modal="false" :show-close="false">
      <div class="user-inform">
        <div class="inform-nav">
          <div class="inform-nav_left">
            <div class="nav_left-xxtz">消息通知</div>
            <div style="cursor: pointer; margin-right: 20px" @click="informShow = false">
              关闭
            </div>
            <div style="cursor: pointer" @click="getAllcheck">全部已读</div>
          </div>
          <div style="cursor: pointer" @click="putAnnouncementVue">查看更多></div>
        </div>
        <div style="padding-left: 20px; color: #606266" v-show="informList.length === 0">
          无消息公告
        </div>
        <div class="inform-list" v-loading="informloading">
          <div class="inform-item" v-for="item in informList" :key="item.id">
            <div class="inform-item_nav">
              <div class="inform-item_title">{{ item.title }}</div>
              <div class="inform-item_time">{{ item.create_time }}</div>
            </div>
            <div class="inform-item_content" style="font-size: 14px">
              {{ item.content_part }}
            </div>
            <div class="inform-item_ckxq" @click="goPage(item.id)">查看详情</div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import { getSignCouApi, getSignApi, generateQrCodeApi } from "@/api/user";
import { share } from "@/utils/share";
import { getNoticeNocheckApi, getAllcheckApi } from "@/api/system";
export default {
  name: "index",
  data() {
    return {
      // activeIndex: '/index/piazza',
      activeIndex: "",
      userList: [
        { icon: "icon-yonghu", text: "个人信息", type: "userinfo" },
        { icon: "icon-fenxiang", text: "邀请好友", type: "share" },
        { icon: "icon-shezhi", text: "账号设置", type: "setting" },
        { icon: "icon-jiangcheng", text: "积分明细", type: "rl-detail" },
        { icon: "icon-cengji", text: "关于平台", type: "platform" },
      ],
      defaultHeight: {},
      informList: [], // 消息通知列表
      informPageObj: {
        current: 1, // 当前页
        size: 10, // 每页的数量
        total: 0,
      },
      informShow: false,
      informloading: false,
    };
  },
  watch: {
    $route(e) {
      // 监听路由，当路由改变时获取到路由的name也就是菜单的index
      this.activeIndex = e.path;
    },
    isLogin(val) {
      if (val) {
        this.getUserInfoActions();
        this.getSignCou();
      }
    },
    informShow(val) {
      if (val) {
        console.log("informShow", this.informShow);
        this.getNoticeNocheck();
      }
    },
  },
  created() {
    //页面创建时执行一次getHeight进行赋值，顺道绑定resize事件
    window.addEventListener("resize", this.getHeight);
    this.getHeight();
  },
  mounted() {
    if (this.isLogin) {
      this.getUserInfoActions();
      this.getSignCou();
      this.getNoticeNocheck();
    }
  },
  computed: {
    ...mapGetters(["isLogin", "userInfo", "isSign"]),
    wxName() {
      return this.userInfo.wxName.length >= 5
        ? this.userInfo.wxName.slice(0, 4) + "..."
        : this.userInfo.wxName;
    },
  },
  methods: {
    ...mapMutations(["SET_IS_SIGN"]),
    ...mapActions(["getUserInfoActions"]),
    // 全部已读
    getAllcheck() {
      if (this.informList.length === 0) {
        return false;
      }
      this.informloading = true;
      getAllcheckApi()
        .then((res) => {
          console.log("全部已读", res);
          this.informloading = false;
          this.getNoticeNocheck();
        })
        .catch(() => [(this.informloading = false)]);
    },
    goPage(id) {
      this.$router.push({
        name: "inform",
        query: { id },
      });
      this.$nextTick(() => {
        setTimeout(() => {
          this.getNoticeNocheck();
        }, 300);
      });
    },
    // 获取消息列表
    getNoticeNocheck() {
      if (!this.isLogin) return this.$router.push({ name: "login" });
      let params = {
        current: this.informPageObj.current,
        size: this.informPageObj.size,
      };
      getNoticeNocheckApi(params).then((res) => {
        let arr = [];
        let data = res.data;
        data.records.forEach((item) => {
          arr.push(item);
        });
        this.informList = arr;
      });
    },
    putAnnouncementVue() {
      this.informShow = false;
      this.$announcementVue({ dialogVisible: true });
    },
    //  邀请
    generateQrCode() {
      if (!this.isLogin) {
        return this.$router.push({ name: "login" });
      }
      share();
    },
    // 签到
    async goSign() {
      if (!this.isLogin) {
        return this.$router.push({ name: "login" });
      }
      if (this.isSign) {
        return this.$message.warning("今日已签到");
      }
      let data = await getSignApi();
      if (data.code === 200) {
        this.$message.success("签到成功");
        this.getSignCou();
        this.SET_IS_SIGN(true);
        // this.isSign = true
      } else {
        this.$message.error("签到失败");
      }
    },
    // 获取签到几次
    async getSignCou() {
      let arr = await getSignCouApi();
      arr = arr.data;
      let data = [];
      for (const key in arr) {
        if (typeof arr[key] === "number") {
          data.push(arr[key]);
        }
      }
      // console.log(data[new Date().getDay()],new Date().getDay(), '111111data<<<<<<<<<<<<<<<<<<<<<<',data)
      if (data[new Date().getDay() - 1] === 1) {
        // this.isSign = true
        this.SET_IS_SIGN(true);
      }
    },

    //定义方法，获取高度减去头
    getHeight() {
      this.defaultHeight.height = window.innerHeight - 60 + "px";
    },
    handleSelect(key, keyPath) {
      this.activeIndex = key;
    },
    childClick(value) {
      this.activeIndex = value;
    },
    // 用户点击事件
    recommendChange(val) {
      if (this.activeIndex != "") {
        this.activeIndex = "";
      }
      if (val === "tcdl") {
        this.$showLoading({
          text: "退出中",
        });
        this.$store
          .dispatch("LogOut")
          .then(() => {
            this.$hideLoading({
              message: "退出成功",
              type: "success",
            });
            this.$router.push({ name: "index" });
          })
          .catch(() => {
            this.$hideLoading({
              message: "退出失败",
              type: "error",
            });
          });
      } else {
        this.$router.push({
          name: `${val}`,
        });
      }
    },
    beforeHandleRecommend(val) {
      return val;
    },
    goCreative() {
      if (!this.isLogin) {
        return this.$router.push({ name: "login" });
      }
      if (this.activeIndex != "") {
        this.activeIndex = "";
      }
      this.$router.push({ name: "creative" });
    },
  },
};
</script>
<style lang="scss" scoped>
.el-dropdown-menu {
  width: 160px;
}

.index {
  position: relative;
  height: 100%;
  background-image: url("../static/index/bg1.png");

  /deep/.el-dialog {
    margin-right: 20px;
  }
}

.user-inform {
  display: flex;
  flex-direction: column;
  // position: absolute;
  // top: 70px;
  // right: 40px;
  // width: 420px;
  height: 560px;
  padding: 12px;
  box-sizing: border-box;
  background-color: #ffffff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  z-index: 999;

  .inform-nav {
    padding: 10px 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 14px;
    color: #960a0f;

    .inform-nav_left {
      display: flex;
      align-items: center;

      .nav_left-xxtz {
        margin-right: 18px;
        font-size: 18px;
        font-weight: 700;
        color: #000;
      }
    }
  }

  .inform-list {
    width: 100%;
    height: 100%;
    overflow-y: auto;
    scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
    scrollbar-width: none;
    scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失

    .inform-item {
      padding: 25px 15px 10px 15px;
      border-top: 2px solid #e6e6e6;
      font-size: #2d303b;
      color: #606266;

      .inform-item_nav {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 5px;

        .inform-item_title {
          color: #2d303b;
        }

        .inform-item_time {
          font-size: 12px;
        }
      }

      .inform-item_ckxq {
        padding: 20px 0;
        color: #960a0f;
        cursor: pointer;
      }
    }
  }
}

.el-header {
  background-color: #eddbc3;
  z-index: 9;
}

.el-main {
  padding: 2px;
  // height: calc(100vh -  60px);
  // background-color: #faf4ec;
}

.header {
  width: 100%;
  display: flex;
  justify-content: space-between;

  .header-left {
    // width: 25%;
    flex: 1;
    display: flex;
    align-items: center;

    .headline-img {
      display: flex;
      align-items: center;
      padding-right: 20px;

      .headline-text {
        font-size: 16px;
        font-weight: 700;
        color: #960a0f;
        cursor: pointer;
      }
    }
  }

  .header-right {
    flex: 1;
    display: flex;

    .user-message {
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: flex-end;
      align-items: center;

      .teg-style {
        width: 15%;
        height: 36px;
        line-height: 36px;
        font-weight: 400;
        text-align: center;
        border-radius: 20px;
        font-size: 14px;
        color: #960a0f;
        background: #ffffff;
      }

      .user-message_invite {
        background-color: #e7b679;
        color: #ffffff;
        cursor: pointer;
      }

      .teg-style {
        width: 18%;
        margin: 0 18px;
      }

      .user-message_sign {
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
      }

      .user-message_vip {
        color: #ffffff;
        background: #960a0f;
        cursor: pointer;
      }
    }

    .user-login-box {
      height: 100%;
      display: flex;
      align-items: center;

      .user-login {
        width: 96px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        margin-left: 10px;
        background: #ffffff;
        border-radius: 20px;
        font-size: 14px;
        color: #960a0f;
        cursor: pointer;
      }

      .avatar-box {
        margin-left: 10px;

        .avatar {
          cursor: pointer;
        }

        .dropdown-item {
          display: flex;
          align-items: center;
        }
      }
    }
  }
}
</style>
