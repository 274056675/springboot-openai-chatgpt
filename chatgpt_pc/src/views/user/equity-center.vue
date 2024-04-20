<template>
  <div class="equity-center">
    <!-- 用户信息 -->
    <div class="teg-box">
      <div class="user-box">
        <div class="user-message">
          <div class="user-message_left">
            <el-avatar
              size="large"
              :src="
                userInfo.wxAvatar != ''
                  ? userInfo.wxAvatar
                  : require('@/static/df_avatar_nan.png')
              "
            ></el-avatar>
            <div class="user-item">
              <div class="user-item_top"></div>
            </div>
          </div>
        </div>
        <div class="rights"></div>
      </div>
      <div class="brokerage-box">
        <span style="margin-right: 10px">{{ userInfo.wxName }}</span>
        <div class="brokerage-btn"></div>
      </div>
    </div>
    <!-- 签到 -->
    <div class="sign">
      <div class="sign-title">签到领积分</div>
      <div class="sign-list">
        <div
          class="sign-list_item"
          v-for="item in signList"
          :key="item.index"
          :style="item.show ? 'background: #fff;' : ''"
        >
          <div class="yqd" v-show="item.show">已签到</div>
          <div style="color: #960a0f">
            <span class="iconfont icon-jifen"></span>
            +{{ sysConfig.sign_day_cou }}分
          </div>
          <div class="title">{{ item.day }}天</div>
        </div>
        <div
          class="sign-list_item"
          style="background: #e7b77b; cursor: pointer"
          v-if="!isSign"
          @click="goSign"
        >
          <div style="color: #960a0f">签到</div>
          <div style="margin-top: 6px; font-size: 12px">已签到{{ signdays }}天</div>
        </div>
        <div class="sign-list_item" style="background: #e7b77b; opacity: 0.7" v-else>
          <div style="color: #960a0f">明天再来</div>
          <div style="margin-top: 6px; font-size: 12px">已签到{{ signdays }}天</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import { getSignCouApi, getSignApi } from "@/api/user";

export default {
  name: "equity-center",
  data() {
    return {
      // 签到列表
      signList: [
        { day: "1", sign: "3", index: 1, show: false },
        { day: "2", sign: "5", index: 2, show: false },
        { day: "3", sign: "3", index: 3, show: false },
        { day: "4", sign: "5", index: 4, show: false },
        { day: "5", sign: "5", index: 5, show: false },
        { day: "6", sign: "3", index: 6, show: false },
        { day: "7", sign: "5", index: 7, show: false },
      ],
      // 签到天数
      signdays: 0,
      balanceList: {}, // 未提现,提现中和可提现余额
      withdrawShow: false,
      withdrawType: "", // 显示类型
      yjObj: {
        brokerage: null, // 提现金额
      },
      withdrawDialogStyle: {
        width: 0,
        height: 0,
      },
      recordPageObj: {
        current: 1, // 当前页
        size: 9, // 每页的数量
        total: 0,
      },
    };
  },
  watch: {
    withdrawShow(val) {
      if (!val) {
        this.recordPageObj.current = 1;
        this.yjObj.brokerage = null;
        this.withdrawType = "";
      }
    },
  },
  computed: {
    ...mapGetters(["isLogin", "userInfo", "sysConfig", "commissionData", "isSign"]),
  },
  mounted() {
    this.initFun();
  },
  methods: {
    ...mapMutations(["SET_IS_SIGN"]),
    ...mapActions(["getSettingDataActions"]),
    initFun() {
      // 获取签到
      this.getSignCou();

      this.getSettingDataActions();
    },

    recordSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    // 切换分页
    recordCurrentChange(val) {
      this.recordPageObj.current = val;
      if (this.withdrawType === "fyjl") {
        this.getCommissionDataFun();
      } else if (this.withdrawType === "txjl") {
        this.getWithdrawals();
      }
    },

    // 获取签到几次
    async getSignCou() {
      let arr = await getSignCouApi();
      arr = arr.data;
      this.signdays = arr["cou"];
      delete arr["cou"];
      let data = [];
      for (const key in arr) {
        data.push(arr[key]);
      }
      data.forEach((item, index) => {
        if (item === 0) {
          this.signList[index].show = false;
        } else {
          this.signList[index].show = true;
        }
      });
      if (this.signList[new Date().getDay() - 1].show) {
        this.SET_IS_SIGN(true);
        // this.isSign = true
      }
    },
    async goSign() {
      let data = await getSignApi();
      if (data.code === 200) {
        this.SET_IS_SIGN(true);
        this.getSignCou();
        // this.isSign = true
      }
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.el-dialog {
  margin: 50px auto 0 !important;
}

.el-dialog__headerbtn {
  padding: 4px;
  color: #fff !important;
  background-color: #cccccc;
  border-radius: 50%;
}

.el-form-item__label {
  padding: 0 !important;
}

.equity-center {
  width: 100%;

  .teg-box {
    display: flex;
    justify-content: space-between;

    .user-box {
      padding: 24px;
      margin-right: 20px;
      display: flex;
      flex-direction: column;
      background-color: #faf4ec;
      border-radius: 10px;

      .user-message {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .user-message_left {
          display: flex;
          align-items: center;

          .user-item {
            margin-left: 10px;

            .user-item_top {
              display: flex;
              align-items: center;
            }
          }
        }
      }

      .rights {
        .rights-list {
          display: grid;
          grid-template-columns: auto auto auto auto;
          grid-column-gap: 10px;

          .rights-list_item {
            position: relative;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 172px;
            height: 96px;
            border-radius: 10px;
            background-color: #ffffff;
            overflow: hidden;

            .hyzx {
              position: absolute;
              left: 0;
              top: 0;
              width: 80px;
              height: 24px;
              line-height: 24px;
              font-size: 12px;
              text-align: center;
              background-color: #e7b679;
              color: #ffffff;
              border-bottom-right-radius: 10px;
            }

            .title {
              padding-top: 8px;
              font-size: 12px;
              color: #960a0f;
            }
          }
        }
      }
    }

    .brokerage-box {
      display: flex;
      flex-direction: column;
      // align-items: center;
      justify-content: space-between;
      width: 100%;
      padding: 24px 16px;
      box-sizing: border-box;
      border-radius: 10px;
      background-color: #faf4ec;
    }
  }

  .sign {
    margin-top: 10px;
    padding: 24px;
    border-radius: 10px;
    background-color: #faf4ec;

    .sign-title {
      color: #960a0f;
      padding-bottom: 16px;
    }

    .sign-list {
      display: grid;
      grid-template-columns: auto auto auto auto;
      grid-column-gap: 10px;
      grid-row-gap: 10px;

      .sign-list_item {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        position: relative;
        height: 90px;
        background-color: #f8f8f8;
        border-radius: 10px;
        overflow: hidden;

        .yqd {
          position: absolute;
          left: 0;
          top: 0;
          width: 60px;
          height: 24px;
          line-height: 24px;
          font-size: 12px;
          text-align: center;
          background-color: #960a0f;
          color: #ffffff;
          border-bottom-right-radius: 10px;
        }

        .title {
          padding-top: 8px;
          font-size: 12px;
        }
      }
    }
  }

  .withdraw-box {
    width: 100%;
    height: 500px;
    padding: 16px;
    box-sizing: border-box;

    .withdraw-teg {
      width: 100%;
      height: 100%;
      overflow-y: auto;
      scrollbar-color: rgba(0, 0, 0, 0.1) rgba(251, 246, 246, 0.1); //滚动条轨道颜色   滚动条滑块的颜色
      scrollbar-width: none;
      scrollbar-width: thin; //thin模式下滚动条两端的三角按钮会消失
    }

    .withdraw-fyjl {
      display: flex;
      flex-direction: column;
    }

    .txsm-content {
      margin-left: 8px;
      padding: 12px;
    }

    .yj-submit {
      width: 100%;
      height: 44px;
      line-height: 44px;
      margin-top: 16px;
      border-radius: 20px;
      text-align: center;
      color: #fff;
      background-color: #960a0f;
      cursor: pointer;
    }
  }
}
</style>
