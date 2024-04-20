<template>
  <div class="chat">
    <div class="chat-tool">
      <div class="tool_one">
        <div class="tool_one-teg">全部工具</div>
        <div
          :class="oneIndex === i ? 'is_one-item' : ''"
          class="tool_one-teg tool_one-item"
          v-for="(item, i) in oneToolList"
          :key="i"
          @click="changeOne(i)"
        >
          {{ item.name }}
        </div>
      </div>
      <div class="tool-content">
        <div class="tool_two">
          <div class="tool_two-box" ref="twoTabs" @mousewheel="ONMouseWheel($event)">
            <div
              class="tool_two-item"
              :class="twoIndex === i ? 'is_two-item' : ''"
              v-for="(item, i) in twoToolList"
              :key="i"
              @click="changeTwo($event, i)"
            >
              {{ item.name }}
            </div>
          </div>
        </div>
        <div class="tool_three">
          <div class="tool_three-box" v-for="(item, i) in threeToolList" :key="i">
            <div class="tool_three-title">{{ item.fun_name }}</div>
            <div class="tool_three-content">
              <div
                class="grid-result"
                v-for="(child, i2) in item.children"
                :key="i2"
                @click="changeMoreChat(child)"
              >
                <div class="img-box">
                  <img style="width: 30px; height: 30px" :src="child.fun_icon" />
                </div>
                <div class="item-name">{{ child.fun_name }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="chat-content">
      <chat-detail
        v-if="chatType === 'chat'"
        :changeMoreChat="changeMoreChat"
      ></chat-detail>
      <chat-tool
        v-if="chatType === 'toolChat'"
        :toolData="toolData"
        :changeMoreChat="changeMoreChat"
      ></chat-tool>
    </div>
    <div class="chat-notice">
      <h4 style="color: #960a0f; margin-bottom: 10px">公告</h4>
      <ul style="font-size: 14px">
        <li style="margin-bottom: 20px">用心做产品，追求极致用户体验，每日迭代升级</li>
        <li style="margin-bottom: 20px">
          扫描下方二维码加入交流群，问题反馈/商务咨询请扫客服二维码
        </li>
      </ul>
      <div>
        <div class="teg-style">
          <el-image
            style="width: 120px; height: 180px"
            :src="sysConfig.chat_gfwxq"
            :preview-src-list="[sysConfig.chat_gfwxq]"
          ></el-image>
          <div style="margin-top: 4px">微信群</div>
        </div>
        <div class="teg-style">
          <el-image
            style="width: 120px; height: 140px"
            :src="sysConfig.chat_lxkf"
            :preview-src-list="[sysConfig.chat_lxkf]"
          ></el-image>
          <div style="margin-top: 4px">客服二维码</div>
        </div>
        <div class="teg-style">
          <div class="qrcode" ref="qrCodeUrl"></div>
          <div style="margin-top: 4px">APP下载</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getToolData } from "@/api/system";
import { setTreeDataUtil, disposeStringDataUtils } from "@/utils/util";
import chatDetail from "@/components/chat/chat-detail.vue";
import chatTool from "@/components/chat/chat-tool.vue";
import { mapGetters } from "vuex";
export default {
  name: "chat",
  components: {
    chatDetail,
    chatTool,
  },
  data() {
    return {
      chatType: "chat", // chat(普通) & toolChat (判断是哪种普通聊天还是工具类聊天)
      toolData: {},
      toolList: [], // 总工具类
      oneToolList: [], // 一级工具类
      oneIndex: 0, // 一级索引
      twoToolList: [], // 二级工具类
      twoIndex: 0, // 二级索引
      threeToolList: [], // 三级工具类
    };
  },
  computed: {
    ...mapGetters(["sysConfig"]),
  },
  mounted() {
    this.getListFun();
  },
  methods: {
    ONMouseWheel(e) {
      // wheelDelta的值为正（120.240...）则是鼠标向上；为负（-120，-240）则是向下。
      // 因为要兼容浏览器，所以才写成 -e.wheelDelta || -e.deltaY.部分浏览器这个e.wheelDeltaY值是没有的可能是null，部分浏览器e.deltaY这个值是没有的可能是null，
      let eventDelta = -e.wheelDelta || -e.deltaY * 40;
      let outsideWrap = this.$refs.twoTabs;
      outsideWrap.scrollLeft = outsideWrap.scrollLeft + eventDelta / 2;
    },
    // 二级tabs居中
    tabsClickScroll(refDom, e) {
      let scrollLeft = null;
      let scrollRight = null;
      let moveDistance = null;
      const clientWidthHalf = this.$refs[refDom].clientWidth / 2;

      // e.layerX获取元素相对refDom元素的位置
      moveDistance = clientWidthHalf - e.layerX;

      scrollLeft = moveDistance;
      scrollRight = -moveDistance;

      // 获取当前元素所在元素的中间位置
      const currentMiddleHalf =
        (this.$refs[refDom].clientWidth + e.target.offsetWidth) / 2;
      // 点击左侧
      // layerX 鼠标相比较于当前坐标系的位置,即如果触发元素没有设置绝对定位或相对定位,以页面为参考点,如果有,将改变参考坐标系
      if (e.layerX <= currentMiddleHalf) {
        this.$refs[refDom].scrollLeft -= scrollLeft * 2;
      } else {
        // 点击右侧
        this.$refs[refDom].scrollLeft += scrollRight * 2;
      }
    },
    changethreeList() {
      this.threeToolList = [];
      this.toolList[this.oneIndex].children[this.twoIndex].children.forEach((item) => {
        item.children.map((itemChild) => {
          if (itemChild.fun_json && itemChild.fun_json?.constructor === Object) {
            let fun_json = itemChild.fun_json;
            itemChild.fun_json = fun_json;
          } else if (itemChild.fun_json && itemChild.fun_json != "") {
            let str = disposeStringDataUtils(itemChild.fun_json);
            itemChild.fun_json = JSON.parse(str);
          }
          if (!itemChild.fun_json.shortcutList) itemChild.fun_json.shortcutList = [];
        });
        this.threeToolList.push(item);
      });
    },
    // 改变二级分类
    changeTwo(e, i) {
      if (this.twoIndex === i) return;
      this.twoIndex = i;
      this.tabsClickScroll("twoTabs", e);
      this.changethreeList();
    },
    changeTwoList() {
      this.toolList[this.oneIndex].children.forEach((item) => {
        this.twoToolList.push({ name: item.fun_name });
      });
      this.changethreeList();
    },
    // 改变一级分类
    changeOne(i) {
      if (this.oneIndex === i) return;
      this.oneIndex = i;
      this.twoIndex = 0;
      this.twoToolList = [];
      this.changeTwoList();
    },
    changeOneList() {
      this.toolList.forEach((item) => {
        this.oneToolList.push({ name: item.fun_name });
      });
    },
    async getListFun() {
      let { data: res } = await getToolData();
      res = setTreeDataUtil(res, "pid");
      this.toolList = res;
      console.log("gongju---", this.toolList);
      this.changeOneList();
      this.changeTwoList();
    },
    changeMoreChat(item) {
      if (item == "") return (this.chatType = "chat");
      this.chatType = "toolChat";
      setTimeout(() => {
        this.toolData = item;
      }, 200);
    },
  },
};
</script>

<style lang="scss" scoped>
li {
  list-style: none;
}

.chat {
  display: flex;
  width: 100%;
  height: 100%;

  .chat-tool {
    display: flex;
    // width: 360px;
    height: 100%;

    .tool_one {
      width: 72px;
      display: flex;
      flex-direction: column;
      align-items: center;
      background-color: #ffffff;
      font-size: 14px;
      font-weight: 700;

      .tool_one-teg {
        text-align: center;
        width: 100%;
        padding: 10px 0;
        margin-bottom: 10px;
      }

      .tool_one-item {
        cursor: pointer;
      }

      .tool_one-item:hover {
        color: #9d000b;
        background-color: #f5efe5;
      }

      .is_one-item {
        color: #9d000b;
        background-color: #f5efe5;
      }
    }

    .tool-content {
      width: 266px;
      background-color: #f5efe5;

      .tool_two {
        display: flex;

        ::-webkit-scrollbar {
          width: 0 !important;
        }

        ::-webkit-scrollbar {
          width: 0 !important;
          height: 0;
        }

        .tool_two-box {
          width: 266px;
          padding: 5px 10px;
          display: flex;
          align-items: center;
          white-space: nowrap;
          border-bottom: 1px solid #ccc;
          box-sizing: border-box;
          overflow-x: auto;
          scrollbar-width: none;

          .tool_two-item {
            // min-width: 64px;
            text-align: center;
            height: 22px;
            line-height: 22px;
            padding: 0 16px;
            margin: 4px 4px;
            border: 1px solid #9d000b;
            border-radius: 20px;
            cursor: pointer;
            font-size: 13px;
            font-weight: 700;
          }

          .tool_two-item:hover {
            background-color: #9d000b;
            color: #ffffff;
          }

          .is_two-item {
            background-color: #9d000b;
            color: #ffffff;
          }
        }
      }

      .tool_three {
        width: 266px;
        height: calc(100vh - 120px);
        overflow-y: auto;
        scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
        scrollbar-width: none;
        scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
        padding: 10px;
        box-sizing: border-box;

        .tool_three-box {
          overflow: hidden;
          font-weight: 600;

          .tool_three-title {
            font-size: 13px;
            text-align: center;
            padding-bottom: 10px;
          }

          .tool_three-content {
            display: grid;
            padding: 0 0 15px;
            grid-template-columns: repeat(3, auto);
            gap: 16px 8px;
            cursor: pointer;

            .grid-result {
              display: flex;
              flex-direction: column;
              justify-content: center;
              align-items: center;

              .item-name {
                font-size: 12px;
                white-space: nowrap;
                text-overflow: ellipsis;
              }
            }
          }
        }
      }
    }
  }

  .chat-content {
    flex: 1;
    width: 100%;
    height: 100%;
    background-color: #faf4ec;
  }

  .chat-notice {
    width: 280px;
    height: 100%;
    padding: 16px;
    overflow-y: auto;
    scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
    scrollbar-width: none;
    scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
    box-sizing: border-box;
    background-color: #f5efe5;

    ul,
    li {
      padding: 0;
      margin: 0;
    }

    .teg-style {
      padding-bottom: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  }
}

/deep/.el-dialog {
  margin: 50px auto 0 !important;
}
</style>
