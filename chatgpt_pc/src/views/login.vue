<template>
  <div class="login">
    <div class="box">
      <div class="content">
        <h2 style="color: #960a0f">登录/注册</h2>
        <div>
          <input type="text" placeholder="请输入手机号" v-model="phone" />
        </div>
        <div style="display: flex; justify-content: space-between">
          <input type="text" placeholder="请输入验证码" v-model="authCode" />
          <input
            type="submit"
            :disabled="codeTime > 0"
            v-model="codeTitle"
            style="color: #960a0f; font-size: 12px"
            @click="getCode"
          />
        </div>
        <div>
          <input type="submit" value="登录" @click="submit" />
        </div>
      </div>

      <a href="#" class="btns register" @click="$router.go(-1)">返回</a>
    </div>
    <!-- 滑动验证 -->
    >
    <Vcode :show="dialogVisible" @success="codeSuccess" :imgs="[img2, img1]"></Vcode>
  </div>
</template>

<script>
import Vcode from "vue-puzzle-vcode";
import { setStorage, getStorage, removeStorage } from "@/utils/storage";
import { mapMutations } from "vuex";

import { sendMessageCode, getSlideCode } from "@/api/system.js";

export default {
  name: "login",
  components: {
    Vcode,
  },
  data() {
    return {
      timer: null,
      codeTitle: "获取验证码",
      codeTime: 0, //验证码定时
      phone: "", //手机号
      authCode: "", // 验证码
      dialogVisible: false, //打开验证码框
      inviteCode: "", //邀请码
      uuid: "",
      bindPhoneFlag: "",
      img1: "",
      img2: "",
      random: "",
    };
  },
  mounted() {
    let urlStr = window.location.href;
    let a = urlStr.split("?")[1];

    if (a && a.split("=")[0] === "invite_code") {
      setStorage({
        name: "inviteCode",
        content: a.split("=")[1],
      });
      this.inviteCode = getStorage({ name: "inviteCode" });
    }
  },
  methods: {
    ...mapMutations(["SET_IS_LOGIN"]),
    // 验证通过
    codeSuccess() {
      this.dialogVisible = false;
      let params = {
        phone: this.phone,
        random: this.random,
      };
      this.$showLoading({
        text: "校验中",
      });
      sendMessageCode(params)
        .then((res) => {
          this.$hideLoading({
            message: "校验成功",
            type: "success",
          });
          // 验证码定时
          this.codeTime = 60;
          this.codeTitle = this.codeTime + "秒后重新获取";
          this.timer = setInterval(() => {
            if (this.codeTime > 0) {
              this.codeTime--;
              this.codeTitle = this.codeTime + "秒后重新获取";
            } else {
              clearInterval(this.timer);
              this.codeTime = 0;
              this.codeTitle = "获取验证码";
            }
          }, 1000);
        })
        .catch((err) => {
          this.$hideLoading({
            message: "校验失败",
            type: "error",
          });
        });
    },
    // 获取验证码
    getCode() {
      let check = this.formCheck(this.phone);
      if (!check.flag) {
        return this.$message.error(check.info);
      }

      let params = {
        index: 3,
        phone: this.phone,
        type: "pc",
      };
      getSlideCode(params).then((res) => {
        this.img1 = "data:image/png;base64," + res.data.image1;
        this.img2 = "data:image/png;base64," + res.data.image2;

        this.random = res.data.random;
        this.dialogVisible = true;
      });
    },
    // 验证手机号
    formCheck(value) {
      let reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (!value) {
        return {
          info: "请输入手机号!",
          flag: false,
        };
      } else if (!reg.test(value)) {
        return {
          info: "请输入正确的手机号!",
          flag: false,
        };
      } else {
        return {
          flag: true,
        };
      }
    },
    // 登录
    submit() {
      let check = this.formCheck(this.phone);
      if (!check.flag) {
        return this.$message.error(check.info);
      }
      if (this.authCode === "") {
        return this.$message({
          message: "请获取验证码登录!",
        });
      } else if (this.authCode.length != 6) {
        return this.$message({
          message: "请输入正确的验证码!",
        });
      }
      this.$showLoading({
        text: "登录中",
      });
      this.$store
        .dispatch("LoginBySocial", {
          params: {
            loginType: "wxmini",
            grantType: "wxmini",
            invite_code: this.inviteCode ? this.inviteCode : "",
            phone: this.phone,
            phoneCode: this.authCode,
            login_type: "phone",
          },
          meta: {
            Authorization: "d3htaW5pOnd4bWluaV9zZWNyZXQ=",
          },
        })
        .then((res) => {
          if (getStorage({ name: "inviteCode" })) {
            removeStorage({
              name: "inviteCode",
            });
          }
          this.$hideLoading({
            message: "登录成功",
            type: "success",
          });
          this.$router.replace({
            name: "piazza",
          });
        })
        .catch(() => {
          this.$hideLoading({
            message: "登录失败",
            type: "error",
          });
        });
    },
  },
};
</script>

<style lang="scss">
.login {
  width: 100%;
  height: 100vh;
  background-color: #faf4ec;

  .box {
    position: relative;
    top: 110px;
    display: flex;
    width: 560px;
    display: flex;
    justify-content: space-between;
    margin: 0 auto;
    .content {
      // position: absolute;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      width: 350px;
      height: 350px;
      padding: 60px 20px;
      box-shadow: inset 20px 20px 20px rgba(0, 0, 0, 0.05),
        25px 35px 20px rgba(0, 0, 0, 0.05), 25px 30px 30px rgba(0, 0, 0, 0.05),
        inset -20px -20px 25px rgba(255, 255, 255, 0.9);
      transition: 0.5s;
      border-radius: 52% 48% 33% 67% / 38% 45% 55% 62%;

      input {
        width: 100%;
        border: none;
        outline: none;
        background: transparent;
        font-size: 16px;
        padding: 10px 15px;
      }

      input[type="submit"] {
        color: #fff;
        cursor: pointer;
      }

      div:last-child {
        width: 120px;
        background: #960a0f;
        transition: 0.5s;
        box-shadow: inset 2px 5px 10px rgba(0, 0, 0, 0.1),
          15px 15px 10px rgba(0, 0, 0, 0.05), 15px 10px 15px rgba(255, 255, 255, 0.025);
      }
      div:last-child:hover {
        width: 150px;
      }
      div::before {
        content: "";
        position: absolute;
        top: 8px;
        left: 50%;
        transform: translateX(-50%);
        width: 65%;
        height: 5px;
        background: rgba(255, 255, 255, 0.5);
        border-radius: 5px;
      }
    }
    .content:hover {
      border-radius: 50%;
    }
    .content::before {
      content: "";
      position: absolute;
      top: 50px;
      left: 85px;
      width: 40px;
      height: 40px;
      border-radius: 50%;
      background: #ffff;
      opacity: 0.9;
    }
    .content::after {
      content: "";
      position: absolute;
      top: 90px;
      left: 110px;
      width: 25px;
      height: 25px;
      border-radius: 50%;
      background: #fff;
      opacity: 0.9;
    }

    div {
      position: relative;
      width: 250px;
      border-radius: 25px;
      box-shadow: inset 2px 5px 10px rgba(0, 0, 0, 0.1),
        inset -2px -5px 10px rgba(255, 255, 255, 1), 15px 15px 10px rgba(0, 0, 0, 0.05),
        15px 10px 15px rgba(0, 0, 0, 0.025);
    }

    .btns {
      position: absolute;
      right: 0;
      bottom: 0;
      width: 120px;
      height: 120px;
      background-color: #960a0f;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      text-decoration: none;
      color: #fff;
      font-size: 14px;

      border-radius: 44% 56% 65% 34% / 57% 58% 42% 43%;
      box-shadow: inset 10px 10px 10px rgba(150, 10, 15, 0.05),
        15px 15px 10px rgba(150, 10, 15, 0.1), 15px 15px 10px rgba(150, 10, 15, 0.1),
        inset 15px 15px 10px rgba(255, 255, 255, 0.5);
      transition: 0.3s;
    }
    .btns::before {
      content: "";
      position: absolute;
      top: 15px;
      left: 30px;
      width: 20px;
      height: 20px;
      border-radius: 50%;
      background: #ffff;
      opacity: 0.45;
    }
    .btns:hover {
      border-radius: 50%;
    }
    .register {
      bottom: 150px;
      right: 0;
      width: 80px;
      height: 80px;
      background-color: #960a0f;
      border-radius: 49% 51% 52% 48% / 63% 59% 41% 37%;
      box-shadow: inset 10px 10px 10px rgba(150, 10, 15, 0.05),
        15px 25px 10px rgba(150, 10, 15, 0.1), 15px 20px 20px rgba(150, 10, 15, 0.1),
        inset -10px -10px 15px rgba(255, 255, 255, 0.5);
    }
    .register::before {
      left: 20px;
      width: 15px;
      height: 15px;
    }
  }
}
</style>
