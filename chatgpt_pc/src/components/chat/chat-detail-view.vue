<template>
  <div class="chat-detail-view">
    <div class="word" v-if="item.message_type === 'a'">
      <img src="../../static/AI-logo.png" />
      <div class="info" v-if="item.view_type == 'text'">
        <div class="time">{{ item.message_time }}</div>
        <!-- <div class="info-content">{{currText}}</div> -->
        <div class="info-content markdown-body" :style="'max-width:' + maxHeight + 'px;'">
          <!-- <vue-markdown v-highlight :source="currText">this.item.message_content</vue-markdown> -->
          <vue-markdown
            :breaks="true"
            :typographer="true"
            :linkify="true"
            :source="
              item.message_content != '' ? item.message_content : item.loading_text
            "
          ></vue-markdown>
        </div>
        <div class="btn-box">
          <div class="btn-item" @click="operationBtnFun('copy')">
            <img class="btn-item-icon" src="../../static/drawing/copy.png" />
            <div class="btn-item-text">复制</div>
          </div>
          <div class="btn-item" @click="operationBtnFun('collect')">
            <img
              class="btn-item-icon"
              :src="
                store_status == '1'
                  ? require('../../static/drawing/collect.png')
                  : require('../../static/drawing/collect(1).png')
              "
            />
            <div class="btn-item-text">收藏</div>
          </div>
        </div>
      </div>
      <div class="info" v-if="item.view_type == 'image'">
        <p class="time">{{ item.message_time }}</p>
        <el-image
          style="max-width: 400px; max-height: 400px"
          :src="item.message_content"
          :preview-src-list="[item.message_content]"
        ></el-image>
      </div>
      <div class="info" v-if="item.view_type == 'loading'">
        <p class="time">{{ item.message_time }}</p>
        <div class="info-content">
          {{ item.loading_text ? item.loading_text : "请稍等" }}
        </div>
        <loading-view :size="20"></loading-view>
      </div>
      <div class="info" v-if="item.view_type == 'error'">
        <div class="time">{{ item.message_time }}</div>
        <!-- <div class="info-content">{{currText}}</div> -->
        <div class="info-content">
          <div style="color: #960a0f">
            {{ item.message_content != "" ? item.message_content : item.loading_text }}
          </div>
        </div>
      </div>
    </div>
    <!-- 我的 -->
    <div class="word-my" v-if="item.message_type === 'q'">
      <div class="info-my">
        <p class="time">{{ item.message_time }}</p>
        <div class="info-content-my">{{ item.message_content }}</div>
      </div>
      <img :src="userInfo.wxAvatar ? userInfo.wxAvatar : dfAvatar" />
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { storeApi } from "@/api/chat";
import LoadingView from "@/components/loading-view/loading-view.vue";
import VueMarkdown from "vue-markdown";
export default {
  name: "chat-detail-view",
  components: {
    LoadingView,
    VueMarkdown,
  },
  props: {
    item: {
      type: Object,
    },
    index: {
      type: Number,
      default: 0,
    },
    list: {
      type: Array,
    },
    scrollToBottom: {
      type: Function,
    },
    currAdObj: {
      type: Object,
    },
    aDStyleObj: {
      type: Object,
    },
    isPreview: {
      type: Boolean,
      default: false,
    },
    maxHeight: Number,
  },
  data() {
    return {
      store_status: "1", // 是否收藏
      dfAvatar: require("@/static/df_avatar_nan.png"),
      textTimer: null,
      currText: "",
      paused: false,
      exportShow: false, //确认导出吗
      chatDetailBoxMaxWidth: 0,
      // systemInfo: uni.getSystemInfoSync(),
      isBtn: false,
      isFy: false,
      fyLoading: "",
      fyObj: {}, //翻译obj
      loadingAd: "", //等等回复的
      adTimer: null,
      isAdError: false,
    };
  },
  watch: {
    item: {
      handler(value) {
        this.store_status = value.store_status;
        // this.scrollToBottom()
        // this.initFun()
      },
      immediate: true,
    },
  },
  computed: {
    ...mapGetters(["sysConfig", "userInfo", "advertisObj"]),
  },
  mounted() {},
  methods: {
    initFun() {
      if (this.textTimer) {
        return false;
      }
      if (this.item.view_type == "text") {
        let text = "";
        text = this.item.message_content;
      } else if (
        this.item.view_type == "loading" &&
        this.sysConfig.chat_advert_message_flag === "true" &&
        this.advertisObj.chat
      ) {
        let adList = this.advertisObj.chat;

        if (adList.length) {
          let index = Math.floor(Math.random() * adList.length);
          this.loadingAd = adList[index].advert_wx_id;
          // this.setAd()
        }
      }
    },
    operationBtnFun(type) {
      if (type === "copy") {
        const textArea = document.createElement("textarea");
        textArea.value = this.item.message_content;
        // 使text area不在viewport，同时设置不可见
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();
        return new Promise((resolve, reject) => {
          // 执行复制命令并移除文本框
          document.execCommand("copy") ? resolve() : reject(new Error("出错了"));
          textArea.remove();
        })
          .then(() => {
            this.$message.success("复制成功");
          })
          .catch(() => {
            this.$message.error("复制失败");
          });
      } else if (type === "collect") {
        let params = {
          messageId: this.item.id,
          type: "chat",
        };
        storeApi(params).then((res) => {
          this.store_status = res.data;
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.chat-detail-view {
  .word {
    display: flex;
    margin-bottom: 20px;
    img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
    .info {
      position: relative;
      margin-left: 10px;
      cursor: pointer;

      &:hover .btn-box {
        display: flex;
      }
      .btn-box {
        display: none;
        position: absolute;
        bottom: -24px;
        padding: 8px 10px;
        border-radius: 4px;
        min-width: 60px;
        align-items: center;
        background-color: #ffffff;
        &::before {
          position: absolute;
          left: 8px;
          top: -8px;
          content: "";
          border-right: 10px solid #fff;
          border-top: 8px solid transparent;
          border-bottom: 8px solid transparent;
        }

        .btn-item {
          display: flex;
          align-items: center;
          margin-left: 10px;

          &:nth-child(1) {
            margin-left: 0;
          }
          .btn-item-icon {
            width: 15px;
            height: 15px;
          }

          .btn-item-text {
            font-size: 12px;
            padding-left: 2px;
            color: #960a0f;
          }
        }
      }
      .time {
        font-size: 12px;
        color: rgba(51, 51, 51, 0.8);
        margin: 0;
        height: 20px;
        line-height: 20px;
        margin-top: -5px;
      }
      .info-content {
        padding: 10px;
        font-size: 14px;
        background: #fff;
        position: relative;
        margin-top: 8px;
        border-radius: 6px;
        box-sizing: border-box;
        overflow-x: auto;
      }
      //小三角形
      .info-content::before {
        position: absolute;
        left: -8px;
        top: 8px;
        content: "";
        border-right: 10px solid #fff;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
      }
    }
  }

  .word-my {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 20px;
    img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
    .info-my {
      width: 90%;
      margin-left: 10px;
      text-align: right;
      .time {
        font-size: 12px;
        color: rgba(51, 51, 51, 0.8);
        margin: 0;
        height: 20px;
        line-height: 20px;
        margin-top: -5px;
        margin-right: 10px;
      }
      .info-content-my {
        max-width: 70%;
        padding: 10px;
        box-sizing: border-box;
        font-size: 14px;
        float: right;
        margin-right: 10px;
        position: relative;
        margin-top: 8px;
        background: #a3c3f6;
        text-align: left;
        border-radius: 6px;
        overflow: hidden;
      }
      //小三角形
      .info-content-my::after {
        position: absolute;
        right: -8px;
        top: 8px;
        content: "";
        border-left: 10px solid #a3c3f6;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
      }
    }
  }
}
</style>
