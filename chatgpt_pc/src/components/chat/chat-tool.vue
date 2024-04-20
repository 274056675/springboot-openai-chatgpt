<template>
  <div class="chat-tool-details" ref="chatToolDetails">
    <div
      class="chat-tool-lsjl"
      @click="
        toolLsjlPageObj.current = 1;
        toolLsjlShow = true;
        getfunhistory();
      "
    >
      历史记录
    </div>
    <div class="chat-tool-title">{{ toolData.fun_name }}</div>
    <div class="chat-tool-content" ref="chatContent">
      <div class="list-item">
        <el-input
          type="textarea"
          :maxlength="maxLength"
          show-word-limit
          :rows="4"
          :placeholder="textPlaceholder"
          v-model="text_value"
        ></el-input>
      </div>
      <template v-if="toolData.fun_json && toolData.fun_json.shortcutList">
        <div
          class="list-item"
          v-for="item in toolData.fun_json.shortcutList"
          :key="item.key"
        >
          <div class="item-label">{{ item.title }}</div>
          <div class="item-control">
            <div class="item-input" v-if="item.type == 'input' || !item.type">
              <el-input v-model="chatObj[item.key]" placeholder="请输入内容"></el-input>
            </div>
            <div class="item-textarea" v-else-if="item.type == 'textarea'">
              <el-input
                type="textarea"
                v-model="chatObj[item.key]"
                :placeholder="item.tip ? item.tip : '请输入内容'"
              ></el-input>
            </div>
            <div class="item-checkbox" v-else-if="item.type == 'checkbox'">
              <div
                class="checkbox-item"
                v-for="(check, index) in item.dicData"
                :key="index"
                :class="chatObj[item.key] === check ? 'active' : ''"
                @click="chatObj[item.key] = check"
              >
                {{ check }}
              </div>
            </div>
          </div>
        </div>
      </template>
      <div class="reply-content-box" v-if="isReply">
        <div class="history-text" v-if="isShowHistory">上一次Ai生成的内容：</div>
        <div class="history-text" v-else>超级AI大脑:</div>
        <div class="content">
          <div
            class="content-text markdown-body"
            style="text-align: left"
            :style="'max-width:' + maxHeight + 'px;'"
          >
            <vue-markdown
              :breaks="true"
              :typographer="true"
              :linkify="true"
              :source="replyText ? replyText : ''"
            ></vue-markdown>
          </div>
          <div class="content-btn">
            <div class="text-length">{{ replyText.length }}字</div>
            <div class="btn" @click="copyTextFun">
              <div class="btn-text">复制</div>
            </div>
            <div class="btn" @click="scBtnFun">
              <div class="btn-text">{{ store_status == "1" ? "收藏" : "已收藏" }}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="reply-content-box" v-if="!isReply">
        <div class="history-text">超级AI大脑:</div>
        <div class="content">{{ btnLoading ? "Ai正在生成中 ..." : "无历史消息" }}</div>
      </div>
    </div>
    <div class="chat-tool-bottom">
      <el-tooltip
        class="item"
        effect="dark"
        content="点击新建聊天，就跳回聊天界面"
        placement="top"
      >
        <div class="btn-teg xjlt" @click="changeMoreChat('')">新建聊天</div>
      </el-tooltip>
      <div class="btn-teg aisc" @click="generateFun" v-if="!btnLoading">AI生成</div>
      <div class="btn-teg aisc" v-else>Ai正在生成中</div>
    </div>
    <el-dialog :visible.sync="toolLsjlShow" width="60%" center>
      <div class="scjl-box">
        <div class="scjl-title">{{ toolData.fun_name }}历史记录</div>
        <div class="scjl-list">
          <div class="scjl-item" v-for="item in toolLsjlList" :key="item.id">
            <div class="scjl-item_title">超级AI大脑</div>
            <div class="scjl-item_content">
              <vue-markdown v-highlight :source="item.chat_content"></vue-markdown>
            </div>
            <div class="scjl-item_bottom">
              <div class="scjl-item-time">{{ item.create_time }}</div>
              <div class="btn-box">
                <div class="btn-item" @click="operationBtnFun('copy', item.id)">
                  <img class="btn-item-icon" src="../../static/drawing/copy.png" />
                  <!-- <div class="btn-item-text">复制</div> -->
                </div>
                <div class="btn-item" @click="operationBtnFun('collect', item.id)">
                  <img
                    class="btn-item-icon"
                    :src="
                      item.store_status == '1'
                        ? require('../../static/drawing/collect.png')
                        : require('../../static/drawing/collect(1).png')
                    "
                  />
                  <!-- <div class="btn-item-text">收藏</div> -->
                </div>
              </div>
            </div>
          </div>
        </div>
        <el-pagination
          :small="true"
          @size-change="toolLsjlSizeChange"
          @current-change="toolLsjlCurrentChange"
          :current-page.sync="toolLsjlPageObj.current"
          :page-size="toolLsjlPageObj.size"
          layout="prev, pager, next, jumper"
          :total="toolLsjlPageObj.total"
        ></el-pagination>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { getLastHistoryDataApi, storeApi, getfunhistoryApi } from "@/api/chat.js";
import VueMarkdown from "vue-markdown";
import website from "@/config/website";
import { getStorage } from "@/utils/storage.js";
import { fetchEventSource } from "@microsoft/fetch-event-source";
export default {
  name: "chat-tool",
  components: {
    VueMarkdown,
  },
  props: {
    toolData: {
      type: Object,
    },
    changeMoreChat: Function,
  },
  data() {
    return {
      maxHeight: 834,
      ctrl: new AbortController(), // sse断开
      toolLsjlShow: false,
      toolLsjlList: [], //历史记录
      toolLsjlPageObj: {
        //分页
        current: 1,
        size: 10,
        total: 0,
        pages: 1,
      },
      store_status: "1",
      text_value: "", //用户输入文本
      maxLength: 1000, //最大输入长度
      chatObj: {}, //聊天数据
      replyText: "", //回复文本
      isReply: false, //是否显示回复
      isShowHistory: false,
      currReplyData: {}, //当前的回复消息
      btnLoading: false, //按钮loading
      isRecon: false, //是否正在获取之前的消息
      // 打字效果
      timer: null,
      messageIndex: 0,
      messageEnd: false,
      messageList: [],
    };
  },
  watch: {
    toolData() {
      if (this.toolData.fun_json && this.toolData.fun_json.shortcutList) {
        this.toolData.fun_json.shortcutList.forEach((item) => {
          this.$set(this.chatObj, item.key, item.value ? item.value : "");
        });
      }
      if (this.toolData.fun_json.maxLength) {
        this.maxLength = this.toolData.fun_json.maxLength;
      }
      this.isReply = false;
      this.isShowHistory = false;
      this.replyText = "";
      this.currReplyData.id = "";
      if (this.isLogin) {
        this.searchNotMessage("one");
      }
    },
  },
  computed: {
    ...mapGetters(["sysConfig", "userInfo", "isLogin", "settingObj"]),
    textPlaceholder() {
      let text = "请在此处告诉Al你想要什么";
      if (this.toolData.fun_json && this.toolData.fun_json.tip) {
        text = `${text}，${this.toolData.fun_json.tip}`;
      }
      return text;
    },
  },
  mounted() {
    this.title = this.toolData.fun_name;
    this.$nextTick(() => {
      this.maxHeight = this.$refs.chatToolDetails.scrollWidth - 72;
    });
  },
  methods: {
    ...mapActions(["getUserInfoActions", "getSettingDataActions"]),
    operationBtnFun(type, id) {
      if (type === "copy") {
        let text;
        this.toolLsjlList.forEach((item) => {
          if (item.id === id) {
            text = item.chat_content;
          }
        });
        const textArea = document.createElement("textarea");
        textArea.value = text;
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
          messageId: id,
          type: "tool",
        };
        storeApi(params).then((res) => {
          this.toolLsjlList.forEach((item) => {
            if (item.id === id) {
              item.store_status = res.data;
            }
          });
        });
      }
    },
    // 获取历史记录
    getfunhistory() {
      let params = {
        funDataId: this.toolData.id,
        current: this.toolLsjlPageObj.current,
        size: this.toolLsjlPageObj.size,
      };
      getfunhistoryApi(params).then((res) => {
        let data = res.data;
        this.toolLsjlPageObj.pages = data.size;
        this.toolLsjlPageObj.size = data.size;
        this.toolLsjlPageObj.total = data.total;
        let arr = [];
        data.records.forEach((item) => {
          arr.push({
            chat_content: item.message_a,
            create_time: item.message_a_time,
            store_status: item.store_status,
            id: item.id,
          });
        });
        this.toolLsjlList = arr;
      });
    },
    // 历史记录分页
    toolLsjlSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    toolLsjlCurrentChange(val) {
      this.toolLsjlPageObj.current = val;
      this.getfunhistory();
    },
    // 工具类收藏
    scBtnFun() {
      let params = {
        messageId: this.currReplyData.id,
        type: "tool",
      };
      storeApi(params).then((res) => {
        this.store_status = res.data;
      });
    },
    // 滚动到底部
    scrollToBottom() {
      this.$nextTick(() => {
        let chatContent = this.$refs.chatContent;
        if (chatContent) {
          chatContent.scrollTop = chatContent.scrollHeight;
        }
      });
    },
    emptyText() {
      this.text_value = "";
      this.active = false;
    },
    // 复制
    copyTextFun() {
      const textArea = document.createElement("textarea");
      textArea.value = this.replyText;
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
    },
    // AI生成
    generateFun() {
      if (!this.isLogin) {
        return this.$router.push({ name: "login" });
      }
      if (this.text_value === "") {
        return this.$message.warning("文本不能为空");
      }

      if (this.userInfo.questionCou <= 0 && !this.userInfo.memberFlag) {
        this.$message.warning("您的积分已用完");
        this.getUserInfoActions();
        return false;
      }
      this.btnLoading = true;
      this.replyText = "";
      this.isReply = false;
      let text = this.toolData.fun_json.text;
      text = text.replace("{value}", this.text_value);
      for (let key in this.chatObj) {
        text = text.replace(`{${key}}`, this.chatObj[key]);
      }

      let that = this;
      fetchEventSource(`/api/${website.apiRequestHead}/chat/ai/send/moreFun`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Basic ${website.Authorization}`,
          "Blade-Auth": `bearer ${getStorage({ name: "token" })}`,
          Connection: "keep-alive",
          "Cache-Control": "no-cache",
        },
        openWhenHidden: true,
        signal: that.ctrl.signal,
        body: JSON.stringify({
          funFataId: this.toolData.id,
          systemTitle: this.toolData.fun_json.theme,
          content: text,
          text_type: this.text_type,
          type: "pc",
        }),
        onopen(res) {
          // console.log('res>>>>>>>>>>>>>>>>>>>>>>', res)
          console.log("open");
          that.isShowHistory = false;
          //发送消息间隔3秒获取数据
          let respWaitDataTimer = setInterval(() => {
            if (that.isShowHistory) {
              clearInterval(respWaitDataTimer);
              return;
            }
            //获取消息
            that.getMessageFun();
          }, 3000);

          that.timer = setInterval(() => {
            if (that.messageList[that.messageIndex] === undefined && that.messageEnd) {
              that.messageList = [];
              that.messageIndex = 0;
              that.messageEnd = false;
              clearInterval(that.timer);
            }
            if (that.messageList[that.messageIndex] !== undefined) {
              //拼接界面文本
              that.replyText = that.replyText + that.messageList[that.messageIndex];
              that.scrollToBottom();
              that.messageIndex++;
            }
          }, 30);
        },
        onmessage(event) {
          let data = JSON.parse(event.data);
          if (event.id === "param") {
            console.log("param");
          } else if (event.id === "messageId") {
            console.log("messageId");
          } else if (
            event.id != "[DONE]" &&
            event.id != "param" &&
            data.content != undefined
          ) {
            // that.replyText = that.replyText + data.content
            if (data.content.length === 1) {
              that.messageList.push(data.content);
            } else {
              for (let i = 0; i < data.content.length; i++) {
                that.messageList.push(data.content[i]);
              }
            }
          } else if (event.id === "[DONE]") {
            console.log("结束");
          }
          // console.log('event', event)
        },
        onclose() {
          console.log("close");
          that.messageEnd = true;
          that.ctrl.abort();
        },
        onerror(error) {
          console.log("error>>>>>>>>>>>>>>>", error);
          that.messageEnd = true;
          that.ctrl.abort();
        },
      });
      // sendMoreDataApi({
      //   funFataId: this.toolData.id,
      //   systemTitle: this.toolData.fun_json.theme,
      //   content: text,
      //   text_type: this.text_type,
      //   type: 'pc',
      // })
      //   .then((res) => {
      //     console.log('重新获取次数', res)
      //     //重新获取次数
      //     this.getUserInfoActions().then(() => {
      //       if (this.userInfo.stopSendTime) {
      //         this.$router.replace({ name: 'piazza' })
      //       }
      //     })
      //     this.getSettingDataActions()
      //   })
      //   .catch(() => {
      //     this.getUserInfoActions()
      //     this.btnLoading = false
      //   })
    },

    //获取消息
    getMessageFun() {
      if (this.isRecon || this.replyText) {
        return false;
      }
      this.isRecon = true;
      getLastHistoryDataApi(this.toolData.id)
        .then((res) => {
          let data = res.data;
          this.store_status = data.store_status;
          this.isRecon = false;
          if (!data.id || data.id == this.currReplyData.id) {
            return false;
          }
          this.currReplyData = data;
          this.isReply = true;
          this.btnLoading = false;
          this.isShowHistory = true;
          this.replyText = data.message_a;
          this.scrollToBottom();
        })
        .catch((err) => {
          this.isReply = true;
          this.btnLoading = false;
          this.isShowHistory = true;
        });
    },

    searchNotMessage(type) {
      console.log("正在获取之前的消息?-", this.isRecon, type);
      if (this.isRecon || this.replyText) {
        return false;
      }
      this.isRecon = true;
      //获取上一次历史
      getLastHistoryDataApi(this.toolData.id).then((res) => {
        console.log("获取上一次历史=", res, res.data, this.currReplyData.id);
        let data = res.data;
        this.store_status = data.store_status;
        this.isRecon = false;
        if (!data.id || data.id == this.currReplyData.id) {
          return false;
        }
        this.currReplyData = data;
        if (type == "one") {
          this.isReply = true;
          this.isShowHistory = true;
          this.replyText = data.message_a;
          this.scrollToBottom();
          return false;
        }
        this.isShowHistory = false;
        if (this.currReplyData.id) {
          if (
            new Date(this.currReplyData.message_q_time).getTime() <=
            new Date(data.message_q_time).getTime()
          ) {
            this.setReplyFun(data);
          }
        } else {
          this.setReplyFun(data);
        }
      });
    },
    setReplyFun(data) {
      this.isUserScoll = false;
      this.isReply = true;
      this.btnLoading = false;
      this.isAddText = true;
      let text = data.message_a;
      if (!text) {
        text = [];
      } else {
        // text = text.split('')
      }
      let index = 0;
      let currHeight = 0;
      if (this.timer) {
        clearInterval(this.timer);
      }
      this.timer = setInterval(() => {
        if (index >= text.length - 1) {
          if (this.timer) {
            clearInterval(this.timer);
          }
          this.isAddText = false;
        }
        if (text[index] === undefined || text[index] === null) {
          if (this.timer) {
            clearInterval(this.timer);
          }
        }
        this.replyText = this.replyText + text[index];
        index++;
        this.scrollToBottom();
        // if (!this.isUserScoll) {
        //   uni.pageScrollTo({
        //     duration: 50, // 过渡时间
        //     scrollTop: 20000, // 滚动的实际距离
        //   })
        // }
      }, 60);
    },
  },
  destroyed() {
    this.ctrl.abort();
  },
};
</script>

<style lang="scss" scoped>
.el-textarea__inner {
  max-height: 300px;
}

.chat-tool-details {
  height: 100%;
  position: relative;
  padding: 0 16px;

  .chat-tool-content {
    height: calc(100vh - 176px);
    overflow-y: auto;
    scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
    scrollbar-width: none;
    scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失

    .reply-content-box {
      margin-top: 20px;
      padding-bottom: 20px;

      .history-text {
        padding-left: 20px;
        padding-bottom: 10px;
        font-size: 16px;
        font-weight: 700;
        color: #960a0f;
      }

      .content {
        // width: calc(100vw - 80rpx);
        margin: 0 auto;
        border-radius: 0 15px 15px 15px;
        background-color: #ffffff;
        padding: 15px;
        font-size: 16px;
        border: 1px solid #8fad90;
        box-sizing: border-box;

        .content-text {
          max-width: 834px;
          overflow-x: auto;
        }

        .content-btn {
          margin-top: 20px;
          display: flex;
          justify-content: flex-end;
          align-items: center;
          font-size: 12px;

          .btn {
            margin-left: 10px;
            display: flex;
            align-items: center;
            border-radius: 6px;
            padding: 3px 8px;
            color: #fff;
            background-color: #90ac90;
            cursor: pointer;

            .btn-img {
              width: 14px;
              height: 14px;
            }

            .btn-text {
              text-align: cneter;
              // padding-left: 3px;
            }
          }

          .text-length {
            color: #90ac90;
          }
        }
      }
    }
  }

  .chat-tool-lsjl {
    position: absolute;
    right: 20px;
    top: 10px;
    font-size: 14px;
    font-weight: 700;
    color: #960a0f;
    cursor: pointer;
  }

  .chat-tool-title {
    padding-top: 40px;
    font-size: 18px;
    color: #960a0f;
    font-weight: 700;
  }

  .list-item {
    padding-top: 20px;
  }

  .item-label {
    margin-bottom: 10px;
    color: #848484;
    flex: 0 0 68px;
  }

  .item-control {
    flex: 1;

    .item-input {
      /deep/.el-input {
        border: 0;
        background-color: #fcfdff;
        padding: 5px !important;
      }
    }

    .item-checkbox {
      display: flex;
      flex-wrap: wrap;

      .checkbox-item {
        padding: 7px 10px;
        background-color: #f7f8fa;
        border: 1px solid #f7f8fa;
        border-radius: 8px;
        margin-right: 10px;
        box-shadow: 0 0 2px #cfcfcf;
        font-size: 12px;
        font-weight: 600;
        cursor: pointer;
      }

      .active {
        border-color: #2b85e4;
        background-color: #ecf5ff;
      }
    }
  }

  .chat-tool-bottom {
    position: absolute;
    bottom: 0;
    left: 0;
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 100%;
    height: 60px;
    background-color: #faf4ec;

    .btn-teg {
      width: 300px;
      height: 40px;
      line-height: 40px;
      border-radius: 20px;
      font-size: 16px;
      text-align: center;
      font-weight: 700;
      cursor: pointer;
    }

    .xjlt {
      background-color: #ffffff;
      color: #960a0f;
      border: 1px solid #960a0f;
    }

    .aisc {
      background-color: #960a0f;
      color: #ffffff;
    }
  }

  .scjl-box {
    padding: 20px 20px 10px;
    height: 560px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    box-sizing: border-box;

    .scjl-title {
      padding-bottom: 10px;
      font-size: 18px;
      color: #960a0f;
    }

    .scjl-list {
      display: flex;
      flex-direction: column;
      width: 100%;
      height: 100%;
      overflow-y: auto;
      scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
      scrollbar-width: none;
      scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
      padding: 20px 0;
      box-sizing: border-box;

      .scjl-item {
        .scjl-item_title {
          color: #960a0f;
          font-size: 14px;
          margin-bottom: 10px;
        }

        .scjl-item_content {
          padding: 15px 10px;
          background-color: #faf4ec;
          border-radius: 6px;
          color: #000;
        }

        .scjl-item_bottom {
          padding: 6px 0 10px;
          box-sizing: border-box;
          display: flex;
          justify-content: space-between;

          .scjl-item-time {
            font-size: 12px;
            color: #999999;
          }

          .btn-box {
            padding: 8px 10px;
            border-radius: 4px;
            display: flex;
            align-items: center;
            background-color: #ffffff;

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
        }
      }
    }
  }
}
</style>
