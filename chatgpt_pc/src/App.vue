<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import { getSysConifgApi } from "@/api/system.js";
import { getStorage } from "@/utils/storage.js";
export default {
  name: "app",
  data() {
    return {
      pingTime: 0, //心跳触发时间戳
      refreshLock: false, //刷新token锁
      tokenTimer: null, //刷新token
      worktimer: null, //网络监听定时器
      pingTimer: null, //心跳监听定时器
      isInit: false, //是否初始化
      alipingTime: false, //app是否失活
    };
  },
  created() {
    // if (this.$router.path !== 'index/piazza') {
    //   this.$router.replace({ name: 'piazza' })
    // }
    setTimeout(() => {
      this.initFun();
    }, 2000);
  },
  watch: {
    isLogin: {
      handler(value) {
        if (value) {
          this.getSettingDataActions();
        }
      },
      immediate: true,
    },
  },
  computed: {
    ...mapGetters(["isLogin"]),
  },
  mounted() {
    let that = this;
    // 这里的this.width和this.height就是main.js中全局挂载的两个变量
    this.pageSize(this.width, this.height);
    window.addEventListener("resize", function () {
      //这里传的参数是当前浏览器窗口的宽高
      that.pageSize(window.innerWidth, window.innerHeight);
    });
  },
  methods: {
    ...mapMutations(["SET_INVITE_CODE", "SET_SYS_CONFIG"]),
    ...mapActions(["getSettingDataActions"]),
    initFun() {
      //监听网络链接 当无网络后跳转到提示页面
      // if (this.initFun == false) {
      //   uni.onNetworkStatusChange((res) => {
      //     if (res.networkType == 'none') {
      //       uni.reLaunch({
      //         url: `/uni_modules/uni-upgrade-center-app/pages/no-network`,
      //         fail: (err) => {},
      //       })
      //     }
      //   })
      // }
      // this.isInit = true
      //获取系统参数配置
      getSysConifgApi()
        .then((res) => {
          let data = res.data;
          let sysConfig = {};
          data.forEach((item) => {
            //包含文件字段处理
            if (item.type == "2") {
              sysConfig[item.code] = item.file;
            } else {
              sysConfig[item.code] = item.val;
            }
          });
          this.SET_SYS_CONFIG(sysConfig);
        })
        .catch((error) => {
          if (error.errMsg && error.errMsg.indexOf("(-1009)") != -1) {
            //没有网络
            this.initFun();
          }
        });

      // this.refreshToken()
      if (this.pingTimer) {
        clearInterval(this.pingTimer);
      }
      //监听网络
    },
    // 定时检测token
    refreshToken() {
      this.tokenTimer = setInterval(() => {
        const token =
          getStorage({
            name: "token",
            debug: true,
          }) || {};
        if (token && token.content) {
          let day = (new Date().getTime() - token.datetime) / (1000 * 60 * 60 * 24);
          if (day >= 1 && !this.refreshLock) {
            this.refreshLock = true;
            this.$store
              .dispatch("refreshToken")
              .then(() => {
                this.refreshLock = false;
              })
              .catch(() => {
                this.refreshLock = false;
              });
          }
        }
      }, 60000);
    },
    pageSize(w, h) {
      let appDiv = document.getElementById("app");
      if (w == window.screen.width) {
        if (!this.isFullscreenForNoScroll()) {
          // 浏览器最大化但是未全屏
          appDiv.style.maxWidth = w + "px";
          appDiv.style.maxHeight = h + "px";
          appDiv.style.minWidth = w + "px";
          appDiv.style.minHeight = h + "px";
        } else {
          // 浏览器最大化且全屏
          appDiv.style.maxWidth = window.screen.width + "px";
          appDiv.style.maxHeight = window.screen.height + "px";
          appDiv.style.minWidth = window.screen.width + "px";
          appDiv.style.minHeight = window.screen.height + "px";
        }
      } else {
        // 浏览器不是最大化
        appDiv.style.maxWidth = this.width + "px";
        appDiv.style.maxHeight = this.height + "px";
        appDiv.style.minWidth = this.width + "px";
        appDiv.style.minHeight = this.height + "px";
      }
    },
    isFullscreenForNoScroll() {
      let wholeScreenFlag = false;
      var explorer = window.navigator.userAgent.toLowerCase();
      if (explorer.indexOf("chrome") > 0) {
        //webkit
        if (window.innerHeight === window.screen.height) {
          wholeScreenFlag = true;
        } else {
          wholeScreenFlag = false;
        }
      } else {
        //IE 9+  fireFox
        if (window.outerHeight === window.screen.height) {
          wholeScreenFlag = true;
        } else {
          wholeScreenFlag = false;
        }
      }
      return wholeScreenFlag;
    },
  },
};
</script>

<style lang="scss">
@import url("@/assets/icon/iconfont.css");
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
input[type="number"] {
  -moz-appearance: textfield;
}
inpit {
  border: none;
}
body {
  margin: 0;
  padding: 0;
}
a {
  color: #555;
  text-decoration: none;
}
a:hover {
  text-decoration: underline;
}
img {
  border: none;
  vertical-align: middle;
}
table {
  border-collapse: collapse;
}
.clearfix:before,
.clearfix:after {
  content: " ";
  display: inline-block;
  height: 0;
  clear: both;
  visibility: hidden;
}
.clearfix {
  *zoom: 1;
}

::-webkit-scrollbar-track {
  background: rgba(251, 246, 246, 0.1);
  border-radius: 0;
}

::-webkit-scrollbar {
  -webkit-appearance: none;
  width: 8px;
}
::-webkit-scrollbar-thumb {
  cursor: pointer;
  border-radius: 2px;
  background: rgba(0, 0, 0, 0.1);
  transition: color 0.2s ease;
}
#app {
  width: 100%;
  height: 100%;
  min-width: 1200px !important;
}

.el-tabs__item:hover {
  color: #960a0f;
  // background-color: orange ;
}
.el-tabs__item.is-active {
  color: #960a0f;
}
.el-tabs__active-bar {
  background-color: #960a0f;
}
.el-dropdown-menu__item {
  color: #dd6e19;
  &:hover {
    background-color: #f5efe5;
  }
}
</style>
