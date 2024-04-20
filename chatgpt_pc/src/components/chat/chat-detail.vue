<template>
  <div class="chat-detail">
    <div class="chat-content" ref="chatContent" @scroll="handleScroll">
      <div
        v-show="msgList.length != 0"
        style="font-size: 12px; text-align: center; color: #999"
      >
        {{ loadingValue }}
      </div>
      <div ref="chatContentItem">
        <chat-detail-view
          v-for="(item, index) in msgList"
          :key="item.id"
          :id="'chat_' + item.id"
          :ref="item.id"
          :item="item"
          :index="index"
          :scrollToBottom="scrollToBottom"
          :maxHeight="maxHeight"
        ></chat-detail-view>
      </div>
      <div
        style="text-align: center; font-weight: 700; color: #960a0f"
        v-show="msgList.length == 0"
      >
        在下方输入框输入问题发送即可开始聊天
      </div>
    </div>
    <div class="chat-bottom">
      <div class="chat-option-box">
        <div
          class="chat-option_teg"
          style="background-color: #960a0f"
          @click="chooseType('xjlt')"
        >
          <span style="margin-right: 4px">+</span> 新建聊天
        </div>
        <div class="chat-option_teg" @click="chooseType('ltlb')">
          <span class="iconfont icon-liebiaomoshi" style="margin-right: 4px"></span>
          聊天列表
        </div>
        <div class="chat-option_teg" @click="chooseType('scjl')">
          <span class="iconfont icon-shoucang" style="margin-right: 4px"></span> 收藏记录
        </div>
        <div class="chat-model">
          <el-dropdown @command="recommendChange" placement="top">
            <el-button type="warning" size="medium">
              聊天模型: {{ modelValue }}
              <i class="el-icon-arrow-up el-icon--right"></i>
            </el-button>
            <el-dropdown-menu>
              <template v-for="(item, index) in modelList">
                <el-dropdown-item
                  :key="index"
                  :command="beforeHandleRecommend({ index, item })"
                  >{{ item.show_name }}</el-dropdown-item
                >
              </template>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
      <div class="chat-foot-box">
        <el-input
          type="textarea"
          :autosize="{ minRows: 1, maxRows: 4 }"
          maxlength="500"
          placeholder="请输入消息，enter发送、shift+enter换行"
          v-model="msg"
          :rows="4"
          class="input-border-style"
          @keydown.native="carriageReturn"
          @keyup.native="inputClick"
        ></el-input>
        <div
          class="foot-btn"
          :class="{ active: msg && !isSend && !isWaiting }"
          @click="sendIssueFun"
        >
          <div v-show="!isSend && !isWaiting">发送</div>
          <div v-show="isSend && isWaiting">发送中</div>
          <!-- <loading-view v-show="isSend || isWaiting"></loading-view> -->
        </div>
      </div>
    </div>
    <el-dialog :visible.sync="ltlbShow" width="60%" :show-close="false">
      <div class="ltlb-box">
        <div class="ltlb-nav">
          <div class="ltlb-nav_title">聊天列表</div>
          <div class="ltlb-nav_right">
            <el-popconfirm
              title="确定删除全部列表吗？"
              @confirm="deleteChatList('all', '')"
            >
              <div slot="reference" class="empty">清空聊天列表</div>
            </el-popconfirm>
            <div class="close-ltlb" @click="ltlbShow = false">X</div>
          </div>
        </div>
        <div style="padding: 0 16px">
          <el-input
            placeholder="请输入内容"
            v-model="chatSearchValue"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </div>
        <div class="chat-list">
          <div class="chat-list-item" v-for="(item, index) in chatList" :key="index">
            <div class="item_left" @click="getChatMsg(item)">
              <div class="item_left-title">{{ item.chat_name }}</div>
              <div class="item_left-content">{{ item.chat_content }}</div>
            </div>
            <el-popconfirm
              title="确定删除该列表吗？"
              @confirm="deleteChatList('item', item.id)"
            >
              <span slot="reference" class="iconfont icon-shanchu iconShow"></span>
            </el-popconfirm>
          </div>
        </div>
      </div>
      <div style="padding: 10px 0">
        <el-pagination
          :small="true"
          @size-change="chatSizeChange"
          @current-change="chatCurrentChange"
          :current-page.sync="chatPageObj.current"
          :page-size="chatPageObj.size"
          layout="prev, pager, next, jumper"
          :total="chatPageObj.total"
        ></el-pagination>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="scjlShow" width="60%" center>
      <div class="scjl-box">
        <div class="scjl-title">收藏记录</div>
        <div class="scjl-list">
          <div class="scjl-item" v-for="item in scjlList" :key="item.id">
            <div class="scjl-item_title">超级AI大脑</div>
            <div class="scjl-item_content">
              <vue-markdown v-highlight :source="item.chat_content"></vue-markdown>
            </div>
            <div class="scjl-item_bottom">
              <div class="scjl-item-time">{{ item.create_time }}</div>
              <div class="btn-box">
                <div class="btn-item" @click="operationBtnFun('copy', item)">
                  <img class="btn-item-icon" src="../../static/drawing/copy.png" />
                  <!-- <div class="btn-item-text">复制</div> -->
                </div>
                <div class="btn-item" @click="operationBtnFun('collect', item)">
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
          @size-change="scjlSizeChange"
          @current-change="scjlCurrentChange"
          :current-page.sync="scjlPageObj.current"
          :page-size="scjlPageObj.size"
          layout="prev, pager, next, jumper"
          :total="scjlPageObj.total"
        ></el-pagination>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { cancelUserApi, getAllModelApi } from "@/api/system.js";
import { updataSettingDataApi, AIModel } from "@/api/user.js";
import {
  sendChatIssueApi,
  getChatHistoryListApi,
  getChatLastMessageApi,
  getChatListAPI,
  deleteChatItemAPI,
  deleteAllChatListAPI,
  storeApi,
  storeListApi,
} from "@/api/chat.js";
import chatDetailView from "@/components/chat/chat-detail-view";
import loadingView from "@/components/loading-view/loading-view.vue";
import { disposeStringDataUtils } from "@/utils/util";
import { mapGetters, mapActions } from "vuex";
import VueMarkdown from "vue-markdown";
import website from "@/config/website";
import { getStorage } from "@/utils/storage.js";
import { fetchEventSource } from "@microsoft/fetch-event-source";
export default {
  name: "chat-detail",
  props: {
    changeMoreChat: Function,
  },
  components: {
    chatDetailView,
    loadingView,
    VueMarkdown,
  },
  data() {
    return {
      maxHeight: 828,
      modelValue: "",
      ctrl: new AbortController(), // sse断开
      loading: false,
      loadingValue: "加载更多",
      msgList: [], // 信息列表
      modelType: "0",
      pageObj: {
        //分页
        current: 1,
        size: 10,
        total: 0,
        statId: "", //起始聊天记录id
        pages: 0,
        type: "pc",
        chatListId: "",
      },
      isRecon: false, //是否正在获取未接收信息
      loadingTimer: {}, //loading
      isGetData: false, //是否获取数据
      msg: "", // 要发送的消息
      currLastMessageId: "", //当前最后一条数据的id
      isSend: false, //是否发送中
      isWaiting: false, //是否等待发送
      modelList: [], // 模型列表
      ltlbShow: false, // 是否显示聊天列表
      chatSearchValue: "", // 聊天列表搜索内容
      chatList: [
        // {chat_name: '', chat_content: ''}
      ],
      chatPageObj: {
        //分页
        current: 1,
        size: 8,
        total: 0,
        pages: 1,
      },
      isEnter: false,
      isShift: false,
      scjlShow: false, // 显示收藏列表
      scjlList: [], //收藏列表
      scjlPageObj: {
        //收藏分页
        //分页
        current: 1,
        size: 10,
        total: 0,
        pages: 1,
      },
      // 打字效果
      timer: null,
      messageIndex: 0,
      messageEnd: false,
      messageList: [],
    };
  },
  computed: {
    ...mapGetters(["userInfo", "sysConfig", "isLogin", "settingObj"]),
  },
  watch: {
    isLogin(value) {
      if (value) this.getSettingDataActions();
    },
  },
  mounted() {
    this.getChatModel();
    if (this.isLogin) {
      this.getHistoryListFun("one");
    }
    this.$nextTick(() => {
      this.maxHeight = this.$refs.chatContentItem.scrollWidth - 66;
    });
  },
  methods: {
    ...mapActions(["getSettingDataActions", "getUserInfoActions"]),
    inputClick(e) {
      const keyCode = e.keyCode || e.which;
      if (keyCode == 13) {
        this.isEnter = false;
      }
      if (keyCode == 16) {
        this.isShift = false;
      }
    },
    // 点击enter发送消息
    carriageReturn(e) {
      const keyCode = e.keyCode || e.which;
      if (keyCode == 13) {
        this.isEnter = true;
      }
      if (keyCode == 16) {
        this.isShift = true;
      }
      if (this.isShift && this.isEnter) {
        e.returnValue = false;
        this.msg = this.msg + "\n";
        return false;
      } else if (this.isEnter) {
        e.returnValue = false;
        this.sendIssueFun();
        return false;
      }
    },
    // 获取是否滚动到顶部
    handleScroll(e) {
      const { scrollTop, clientHeight, scrollHeight } = e.target;

      if (scrollTop === 0) {
        // console.log('this.pageObj.page', this.pageObj.page, this.pageObj.current)
        if (this.isGetData) {
          return false;
        }
        if (this.pageObj.pages === this.pageObj.current) {
          return (this.loadingValue = "没有更多了");
        }
        if (this.pageObj.total == this.msgList.length) {
          return false;
        }
        this.isGetData = true;
        this.loading = true;
        this.pageObj.current++;
        this.getHistoryListFun();
      }
    },
    // 获取历史信息
    getHistoryListFun(type) {
      let data = {
        current: this.pageObj.current,
        size: this.pageObj.size,
        modelType: this.modelType,
        type: "pc",
        chatListId: this.pageObj.chatListId,
      };
      if (type != "one") data.startNum = this.pageObj.statId;
      this.loadingValue = "加载中 ...";
      getChatHistoryListApi(data).then((res) => {
        // console.log('getChatHistoryListApi>>>>>>>>>', res)
        this.loadingValue = "加载更多";
        let data = res.data;
        this.pageObj.total = data.total;
        this.pageObj.pages = data.pages;
        this.msgList = [...data.records, ...this.msgList];
        // if (this.pageObj.total == this.msgList.length) {
        // 	this.loadinStatus = "nomore";
        // } else {
        // 	this.loadinStatus = "loadmore";
        // }
        if (this.msgList.length > 0) {
          let endId = this.msgList[data.records.length - 1].id;
          // setTimeout(() => {
          //   this.$nextTick(() => {
          //     this.scrollIntoID = 'chat_' + endId
          //   })
          // }, 100)
          if (type == "one") {
            this.pageObj.statId = endId;
            this.currLastMessageId = endId;
            // let currMsgList = uni.$u
            //   .deepClone(this.msgList)
            //   .reverse()
            //   .filter((item) => {
            //     if (item.message_type == 'q') {
            //       return false
            //     }
            //     return true
            //   })
          }
        }
        if (type == "one") {
          this.scrollToBottom();
        }
        this.isGetData = false;
        this.loading = false;
      });
    },
    //ai返回回答，将回答展示出来
    setMessageFun(messgData) {
      let message = messgData;
      if (message.message_type == "a") {
        if (this.loadingTimer["chat_" + message.pid]) {
          clearTimeout(this.loadingTimer["chat_" + message.pid]);
        }
        let isAddMessage = false;
        let currPidIndex = "";
        this.isWaiting = false;
        this.isScroll = true;
        this.msgList = this.msgList.map((item, index) => {
          if (item.id == message.pid) {
            currPidIndex = index;
          }
          if (item.pid == message.pid) {
            isAddMessage = true;
            item = {
              ...message,
              linkOpen: this.userInfo.viewModel == "1",
            };
          }
          return item;
        });
        if (!isAddMessage) {
          this.msgList.splice(currPidIndex + 1, 0, {
            ...message,
          });
        }
        let lastData = this.msgList[this.msgList.length - 1];
        if (lastData.view_type == "loading") {
          this.currLastMessageId = lastData.pid;
        } else {
          this.currLastMessageId = lastData.id;
        }
        this.scrollToBottom();
      }
    },
    //查询未接收的信息
    searchNotMessage() {
      if (this.isRecon || !this.currLastMessageId) {
        return false;
      }
      this.isRecon = true;
      let startMessageId = "";
      this.msgList.forEach((item) => {
        if (item.view_type == "loading" && !startMessageId) {
          startMessageId = item.pid;
        }
      });
      if (!startMessageId) {
        startMessageId = this.currLastMessageId;
      }
      getChatLastMessageApi({
        modelType: this.modelType,
        startNum: this.currLastMessageId,
        type: "pc",
        chatListId: this.pageObj.chatListId,
      })
        .then((res) => {
          if (res.data.length > 0) {
            res.data.forEach((item) => {
              this.setMessageFun(item);
            });
          }
          this.isRecon = false;
        })
        .catch(() => {
          this.isRecon = false;
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
    // 获取时间
    getTimeFun() {
      let date = new Date();
      let year = date.getFullYear(); //年
      let month = date.getMonth() + 1; //月
      let day = date.getDate(); //日
      let hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours(); //时
      let minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes(); //分
      let second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds(); //秒
      return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    },
    // 发送消息
    async sendIssueFun() {
      if (!this.isLogin) {
        return this.$router.push({ name: "login" });
      }

      let msg = this.msg;
      if (msg === "" || this.isSend || this.isWaiting) {
        return false;
      }
      if (
        this.settingObj.chatUseRl != 1 &&
        this.userInfo.questionCou <= 0 &&
        !this.userInfo.memberFlag
      ) {
        this.$message.warning("您的积分已用完");
        this.getUserInfoActions();
        return false;
      }
      //发送
      this.isSend = true;
      this.isWaiting = true;
      this.msg = "";
      let id = `${new Date().getTime()}${Math.floor(Math.random(1) * 10000)}`;
      this.msgList.push({
        message_time: this.getTimeFun(),
        message_type: "q",
        view_type: "text",
        message_content: msg,
        id,
      });
      setTimeout(() => {
        this.scrollToBottom();
      }, 30);
      let uuid = `${new Date().getTime()}${Math.floor(Math.random(1) * 10000)}_uuid`;
      //添加等待loading
      this.msgList.push({
        message_time: this.getTimeFun(),
        message_type: "a",
        view_type: "loading",
        message_content: "",
        id: `${new Date().getTime()}${Math.floor(Math.random(1) * 10000)}`,
        pid: "",
        loading_text: this.sysConfig.ai_doing_message
          ? this.sysConfig.ai_doing_message
          : "Ai正在回复中,请稍等...",
        loading_state: "1",
        uuid,
      });

      this.pageObj.total++;

      let index = this.msgList.length - 1;
      let that = this;
      fetchEventSource(`/api/${website.apiRequestHead}/chat/ai/send/question`, {
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
          question: msg,
          modelType: this.modelType,
          startMessageId: this.pageObj.statId,
          text_type: this.text_type,
          chatListId: this.pageObj.chatListId,
          type: this.pageObj.type,
        }),
        onopen(res) {
          // console.log('res>>>>>>>>>>>>>>>>>>>>>>', res)
          //发送消息间隔3秒获取数据
          let respWaitDataTimer = setInterval(() => {
            getChatHistoryListApi({
              current: 1,
              size: that.pageObj.size,
              modelType: that.modelType,
              type: "pc",
              chatListId: that.pageObj.chatListId,
            })
              .then((res) => {
                let data = res.data;

                if (res.data.records.length == 0) {
                  return;
                }

                let respLast = res.data.records[res.data.records.length - 1];
                let currMsgLast = that.msgList[that.msgList.length - 1];
                if (
                  respLast.id != currMsgLast.id &&
                  respLast.message_type == currMsgLast.message_type
                ) {
                  that.msgList = data.records;
                  that.pageObj.total = data.total;
                  that.pageObj.pages = data.pages;

                  that.isWaiting = false;
                  that.isSend = false;
                  clearInterval(respWaitDataTimer);
                  return;
                }
              })
              .catch(() => {
                that.isWaiting = false;
                that.isSend = false;
                clearInterval(respWaitDataTimer);
              });
          }, 2000);

          that.timer = setInterval(() => {
            if (that.messageList[that.messageIndex] === undefined && that.messageEnd) {
              that.messageList = [];
              that.messageIndex = 0;
              that.messageEnd = false;
              clearInterval(that.timer);
            }
            if (that.messageList[that.messageIndex] !== undefined) {
              //拼接界面文本
              that.msgList[index].message_content =
                that.msgList[index].message_content + that.messageList[that.messageIndex];
              that.scrollToBottom();
              that.messageIndex++;
            }
          }, 40);
        },
        onmessage(event) {
          let data = JSON.parse(event.data);
          if (event.id === "param") {
            that.pageObj.chatListId = data[0].chat_list_id;
            that.msgList = that.msgList.map((msgItem) => {
              if (msgItem.id == id) {
                msgItem = {
                  ...data[0],
                };
              }
              if (msgItem.uuid == uuid) {
                msgItem.pid = data[0].id;
              }
              return msgItem;
            });
            if (data.length >= 2) {
              that.msgList = that.msgList.map((msgItem) => {
                if (msgItem.uuid == uuid) {
                  msgItem = {
                    ...data[1],
                  };
                }
                return msgItem;
              });
              //发送文本包含敏感词
              that.currLastMessageId = data[1].id;
              that.pageObj.total = that.pageObj.total + 2;
              setTimeout(() => {
                that.scrollToBottom();
              }, 30);
              that.isWaiting = false;
              that.isSend = false;
            } else {
              let currDataId = data[0].id;
              that.currLastMessageId = currDataId;
              //30秒后更换提示并且放开输入
              let loadingNum = 0;
              that.loadingTimer["chat_" + currDataId] = setTimeout(() => {
                that.msgList = that.msgList.map((item) => {
                  if (item.view_type == "loading" && item.pid == currDataId) {
                    item.loading_text = that.sysConfig.ai_doing_longtime_message
                      ? that.sysConfig.ai_doing_longtime_message
                      : "Ai正在思考中，请稍等...";
                    item.loading_state = "2";
                  }
                  if (item.view_type == "loading" && item.loadin_status == "1") {
                    loadingNum++;
                  }
                  return item;
                });
                that.isWaiting = false;
              }, 30000);
            }
          } else if (event.id === "messageId") {
            // console.log('that.msgList[index].view_type', that.msgList[index])
            if (that.msgList[index].view_type === "loading") {
              that.msgList[index].view_type = data.view_type;
              that.msgList[index].message_time = data.message_time;
            }
          } else if (event.id != "[DONE]" && data.content != undefined) {
            if (data.content.length === 1) {
              that.messageList.push(data.content);
            } else {
              for (let i = 0; i < data.content.length; i++) {
                that.messageList.push(data.content[i]);
              }
            }
            // that.msgList[index].message_content = that.msgList[index].message_content + data.content
          } else if (event.id === "error") {
            if (that.msgList[index].view_type === "loading") {
              that.msgList[index].view_type = data.view_type;
              that.msgList[index].message_time = data.message_time;
            }
            if (data.message_content.length === 1) {
              that.messageList.push(data.message_content);
            } else {
              for (let i = 0; i < data.message_content.length; i++) {
                that.messageList.push(data.message_content[i]);
              }
            }
          } else if (event.id === "image") {
            that.msgList[index].view_type = data.view_type;
            that.msgList[index].message_time = data.message_time;
            that.msgList[index].message_content = data.message_content;
          } else if (event.id === "[DONE]") {
            console.log("结束");
          }
          // console.log('event>>>>>>>>>>>>>>>>>>>', event)
        },
        onclose() {
          // 停止打字效果
          that.messageEnd = true;
          // that.isWaiting = false;
          // that.isSend = false;

          //重新获取次数
          that.getUserInfoActions().then(() => {
            if (that.userInfo.stopSendTime) {
              that.$router.replace({ name: "piazza" });
            }
          });
          that.getSettingDataActions();
          that.ctrl.abort();
        },
        onerror(error) {
          console.log("error>>>>>>>>>>>>>>>", error);
          that.messageEnd = true;
          that.isWaiting = false;
          that.isSend = false;
          that.ctrl.abort();
        },
      });

      // setTimeout(() => {
      //   sendChatIssueApi({
      //   question: msg,
      //   modelType: this.modelType,
      //   startMessageId: this.pageObj.statId,
      //   text_type: this.text_type,
      //   chatListId: this.pageObj.chatListId,
      //   type: this.pageObj.type,
      // })
      //   .then((res) => {
      //     console.log('=====发送-----', res.data)
      //     let data = res.data
      //     this.pageObj.chatListId = data[0].chat_list_id
      //     this.msgList = this.msgList.map((msgItem) => {
      //       if (msgItem.id == id) {
      //         msgItem = {
      //           ...data[0],
      //         }
      //       }
      //       if (msgItem.uuid == uuid) {
      //         msgItem.pid = data[0].id
      //       }
      //       return msgItem
      //     })
      //     if (data.length >= 2) {
      //       this.msgList = this.msgList.map((msgItem) => {
      //         if (msgItem.uuid == uuid) {
      //           msgItem = {
      //             ...data[1],
      //           }
      //         }
      //         return msgItem
      //       })
      //       //发送文本包含敏感词
      //       this.currLastMessageId = data[1].id
      //       this.pageObj.total = this.pageObj.total + 2
      //       setTimeout(() => {
      //         this.scrollToBottom()
      //       }, 30)
      //       this.isWaiting = false
      //       this.isSend = false
      //     } else {
      //       let currDataId = data[0].id
      //       this.currLastMessageId = currDataId
      //       //30秒后更换提示并且放开输入
      //       let loadingNum = 0
      //       this.loadingTimer['chat_' + currDataId] = setTimeout(() => {
      //         this.msgList = this.msgList.map((item) => {
      //           if (item.view_type == 'loading' && item.pid == currDataId) {
      //             item.loading_text = this.sysConfig.ai_doing_longtime_message ? this.sysConfig.ai_doing_longtime_message : 'Ai正在思考中，请稍等...'
      //             item.loading_state = '2'
      //           }
      //           if (item.view_type == 'loading' && item.loadin_status == '1') {
      //             loadingNum++
      //           }
      //           return item
      //         })
      //         this.isWaiting = false
      //       }, 30000)
      //       setTimeout(() => {
      //         this.scrollToBottom()
      //       }, 30)
      //       this.pageObj.total++
      //       this.isWaiting = true
      //       this.isSend = false
      //     }
      //     //重新获取次数
      //     this.getUserInfoActions().then(() => {
      //       if (this.userInfo.stopSendTime) {
      //         this.$router.replace({ name: 'piazza' })
      //       }
      //     })
      //     this.getSettingDataActions()
      //   })
      //   .catch(() => {
      //     this.msgList = this.msgList.filter((item) => {
      //       if (item.id == id || item.uuid == uuid) {
      //         return false
      //       }
      //       return true
      //     })
      //     this.isSend = false
      //   })
      // }, 1000)
    },
    // 获取模型
    getChatModel() {
      getAllModelApi().then((res) => {
        let arr = [];
        res.data.chat.forEach((item) => {
          arr.push({
            show_name: item.show_name,
            mx_lx: item.mx_lx,
            is_use_rl: item.is_use_rl,
          });
        });
        this.modelList = arr;
        if (this.isLogin) {
          // 获取当前是用的是哪个模型
          this.getSettingDataActions();
          this.modelList.forEach((item, index) => {
            if (item.mx_lx === this.settingObj.ai_model) {
              this.modelValue = item.show_name;
            }
          });
        }
      });
    },
    // 切换模型
    recommendChange(obj) {
      if (!this.isLogin) {
        return this.$router.push({ name: "login" });
      }
      if (this.chtaIndex === obj.index) return;
      let data;

      data = {
        aiModel: obj.item.mx_lx,
      };
      this.$showLoading({
        text: "更换中",
      });
      updataSettingDataApi(data)
        .then((res) => {
          this.modelValue = obj.item.show_name;
          this.getSettingDataActions();
          this.$hideLoading({
            message: "更换成功",
            type: "success",
          });
        })
        .catch(() => {
          this.$hideLoading({
            message: "更换失败",
            type: "error",
          });
        });
    },
    beforeHandleRecommend(obj) {
      return obj;
    },
    // 显示列表
    chooseType(type) {
      if (!this.isLogin) {
        return this.$router.push({ name: "login" });
      }
      if (type === "xjlt") {
        this.pageObj.chatListId = "";
        this.msgList = [];
      } else if (type === "ltlb") {
        this.chatPageObj.current = 1;
        this.getChatList();
      } else if (type === "scjl") {
        this.scjlShow = true;
        this.scjlPageObj.current = 1;
        this.getstoreList();
      }
    },
    // 收藏记录列表
    getstoreList() {
      let params = {
        current: this.scjlPageObj.current,
        size: this.scjlPageObj.size,
      };
      storeListApi(params).then((res) => {
        let data = res.data;
        this.scjlPageObj.pages = data.size;
        this.scjlPageObj.size = data.size;
        this.scjlPageObj.total = data.total;
        let arr = [];
        data.records.forEach((item) => {
          arr.push({
            chat_content: item.chat_content,
            create_time: item.create_time,
            store_status: item.store_status,
            id: item.message_id,
            chat_type: item.chat_type,
          });
        });
        this.scjlList = arr;
      });
    },
    operationBtnFun(type, item) {
      if (type === "copy") {
        const textArea = document.createElement("textarea");
        textArea.value = item.chat_content;
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
          messageId: item.id,
          type: item.chat_type,
        };
        storeApi(params).then((res) => {
          this.scjlList.forEach((scjlItem) => {
            if (scjlItem.id === item.id) {
              scjlItem.store_status = res.data;
            }
          });
        });
      }
    },
    // 请求聊天列表
    getChatList() {
      // if (this.chatPageObj.current > this.chatPageObj.pages) {
      //   return this.$message.warning('加载完毕')
      // }
      let params = {
        current: this.chatPageObj.current,
        size: this.chatPageObj.size,
      };
      this.ltlbShow = true;
      getChatListAPI(params).then((res) => {
        let data = res.data.records;
        console.log("chatList>>>>>>>>>>>>>>>>", data);
        this.chatPageObj.pages = res.data.pages;
        this.chatPageObj.total = res.data.total;
        if (this.chatPageObj.pages == 0) this.chatPageObj.pages = 1;
        // this.chatPageObj.current++
        let arr = [];
        data.forEach((item, index) => {
          console.log("index", index);
          if (item.chat_content && item.chat_content.length > 30) {
            item.chat_content = item.chat_content.slice(0, 30) + "...";
          }
          arr.push({
            chat_name: item.chat_name,
            chat_content: item.chat_content,
            id: item.fundata_id == "" ? item.id : item.fundata_id,
            chat_type: item.chat_type,
            fun_json: item.fun_json ? item.fun_json : "",
          });
        });
        this.chatList = arr;
      });
    },
    // 切换当前聊天信息
    getChatMsg(item) {
      if (item.chat_type === "chat") {
        this.msgList = [];
        this.pageObj.current = 1;
        this.pageObj.chatListId = item.id;
        this.getHistoryListFun("one");
      } else if (item.chat_type === "tools") {
        if (item.fun_json && item.fun_json?.constructor === Object) {
          let fun_json = item.fun_json;
          item.fun_json = fun_json;
        } else if (item.fun_json && item.fun_json != "") {
          let str = disposeStringDataUtils(item.fun_json);
          item.fun_json = JSON.parse(str);
        }
        let params = {
          fun_json: item.fun_json,
          fun_name: item.chat_name,
          fun_content: item.chat_content,
          id: item.id,
        };
        this.changeMoreChat(params);
      }
      this.ltlbShow = false;
    },
    // 删除聊天列表
    deleteChatList(type, id) {
      if (this.chatList.length <= 0) {
        return this.$message.warning("当前列表为空");
      }
      this.$showLoading({
        text: "删除中",
      });
      if (type === "item") {
        deleteChatItemAPI(id)
          .then((res) => {
            if (this.pageObj.chatListId == id) {
              this.pageObj.chatListId = "";
              this.msgList = [];
            }
            this.$hideLoading({
              message: "删除成功",
              type: "success",
            });
            this.chatPageObj.current = 1;
            this.getChatList();
          })
          .catch((err) => {
            this.$hideLoading({
              message: "删除失败",
              type: "error",
            });
          });
      } else if (type === "all") {
        deleteAllChatListAPI()
          .then((res) => {
            this.pageObj.chatListId = "";
            this.msgList = [];
            this.$hideLoading({
              message: "删除成功",
              type: "success",
            });
            this.chatPageObj.current = 1;
            this.getChatList();
          })
          .catch((err) => {
            this.$hideLoading({
              message: "删除失败",
              type: "error",
            });
          });
      }
    },
    // 聊天分页
    chatSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    chatCurrentChange(val) {
      this.chatPageObj.current = val;
      this.getChatList();
    },
    // 收藏记录分页
    scjlSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    scjlCurrentChange(val) {
      this.scjlPageObj.current = val;
      this.getstoreList();
    },
  },
  destroyed() {
    this.ctrl.abort();
  },
};
</script>

<style lang="scss">
.el-dialog__headerbtn {
  padding: 4px;
  color: #fff !important;
  background-color: #cccccc;
  border-radius: 50%;
}

.el-textarea__inner {
  overflow: hidden;
  border: 0;
  padding: 10px 15px;
  border-radius: 20px;
  max-height: 200px;
}

.chat-detail {
  position: relative;
  width: 100%;
  height: 100%;

  .chat-content {
    height: calc(100vh - 190px);
    box-sizing: border-box;
    padding: 10px;
    overflow-y: auto;
    scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
    scrollbar-width: none;
    scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失

    .chat-content-item {
    }
  }

  .chat-bottom {
    width: 100%;
    position: absolute;
    left: 0;
    bottom: 0px;
    background-color: #faf4ec;

    .chat-option-box {
      margin: 10px 10px 4px;
      display: flex;

      .chat-option_teg {
        width: 112px;
        height: 36px;
        line-height: 36px;
        margin-right: 12px;
        display: flex;
        font-size: 14px;
        align-items: center;
        justify-content: center;
        background-color: #e6a23c;
        color: #ffffff;
        border-radius: 10px;
        cursor: pointer;
      }

      .chat-model {
        display: flex;
        border-radius: 10px;
        overflow: hidden;

        .is-chat-model {
          border: 1px solid #960a0f;
        }
      }
    }

    .chat-foot-box {
      width: 100%;
      box-sizing: border-box;
      display: flex;
      align-items: flex-end;
      justify-content: center;
      border-top: 1px solid #f0f0f0;
      padding: 8px 6px;
      padding-bottom: calc(10px + 8px);
      z-index: 999;

      .foot-btn {
        height: 37px;
        flex: 0 0 70px;
        border-radius: 20px;
        text-align: center;
        line-height: 37px;
        background-color: #33cc33;
        color: #fff;
        font-size: 14px;
        opacity: 0.4;
        cursor: pointer;

        &.active {
          opacity: 1;
        }
      }
    }
  }

  .ltlb-box {
    width: 100%;
    height: 500px;
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
    scrollbar-width: none;
    scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
    padding: 16px 0 0;
    box-sizing: border-box;

    .ltlb-nav {
      position: relative;
      padding: 0 16px;
      padding-bottom: 30px;

      .ltlb-nav_title {
        text-align: center;
        color: #960a0f;
        font-size: 18px;
        font-weight: 700;
      }

      .ltlb-nav_right {
        position: absolute;
        right: 16px;
        top: 0;
        display: flex;
        align-items: center;

        .empty {
          width: 114px;
          height: 34px;
          margin-right: 10px;
          line-height: 34px;
          background: #ffffff;
          border: 1px solid #b1a389;
          border-radius: 18px;
          font-size: 12px;
          text-align: center;
          color: #b1a389;
          cursor: pointer;
        }

        .close-ltlb {
          width: 24px;
          height: 24px;
          line-height: 24px;
          text-align: center;
          background: #cccccc;
          border-radius: 50%;
          color: #ffffff;
          cursor: pointer;
        }
      }
    }

    .chat-list {
      width: 100%;
      height: 100%;
      padding: 10px 0;
      overflow-y: auto;
      scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
      scrollbar-width: none;
      scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
      display: flex;
      flex-direction: column;
      align-items: center;

      .chat-list-item {
        width: 100%;
        padding: 20px 0;
        display: flex;
        justify-content: space-between;
        align-items: center;

        .item_left {
          width: 100%;
          margin-left: 16px;
          font-size: 12px;
          color: #ccc;
          cursor: pointer;

          .item_left-title {
            font-size: 16px;
            color: #000;
            margin-bottom: 10px;
          }
        }

        .iconShow {
          display: none;
          margin-right: 16px;
          color: #960a0f;
          cursor: pointer;
        }
      }

      .chat-list-item:hover {
        background-color: #faf4ec;
      }

      .chat-list-item:hover .iconShow {
        display: block;
      }
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
