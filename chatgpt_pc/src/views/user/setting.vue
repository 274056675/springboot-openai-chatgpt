<template>
  <div class="setting">
    <el-tabs tab-position="left" style="height: 400px; margin-top: 20px" stretch>
      <el-tab-pane label="更换手机号">
        <div class="phone-box">
          <div class="phone_title">绑定新手机号</div>
          <div class="phone_new">
            <el-form
              label-position="top"
              label-width="80px"
              :model="phoneinfo"
              :rules="rules"
            >
              <el-form-item label="新手机号" prop="phone">
                <el-input v-model="phoneinfo.phone"></el-input>
              </el-form-item>
              <el-form-item label="验证码" prop="authCode">
                <el-input v-model="phoneinfo.authCode">
                  <template slot="append">
                    <el-button
                      style="width: 142px"
                      @click="getCode"
                      :disabled="codeTime > 0"
                      >{{ codeTitle }}</el-button
                    >
                  </template>
                </el-input>
              </el-form-item>
            </el-form>
            <div style="font-size: 12px; color: #960a0f">
              更改后将使用该手机号接收验证码
            </div>
            <el-button class="qrgg" @click="editInfoData">确认更改</el-button>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
    <Vcode :show="dialogVisible" @success="codeSuccess" :imgs="[img2, img1]"></Vcode>
  </div>
</template>

<script>
import { sendMessageCode, getSlideCode } from "@/api/system.js";
import Vcode from "vue-puzzle-vcode";
import { editInfoDataApi } from "@/api/user";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "setting",
  components: {
    Vcode,
  },
  data() {
    return {
      timer: null,
      codeTitle: "获取验证码",
      codeTime: 0, //验证码定时
      phoneinfo: {
        phone: "",
        authCode: "",
        random: "",
      },
      rules: {
        // 校验
        phone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          {
            validator: function (rule, value, callback) {
              if (/^1[34578]\d{9}$/.test(value) == false) {
                callback(new Error("请输入正确的手机号"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        authCode: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          { min: 6, max: 6, message: "请输入正确的验证码" },
        ],
      },
      zfbImageUrl: "",
      zfbVisible: false,

      img2: "",
      img1: "",
      dialogVisible: false,
    };
  },
  computed: {
    ...mapGetters(["isLogin", "settingObj"]),
  },
  mounted() {},
  methods: {
    ...mapActions(["getUserInfoActions", "getSettingDataActions"]),
    editInfoData() {
      let data = {
        phone: this.phoneinfo.phone,
        code: this.phoneinfo.authCode,
      };
      this.$showLoading({
        text: "更换中",
      });
      editInfoDataApi(data)
        .then((res) => {
          if (res.code == 200) {
            this.getUserInfoActions();
            clearInterval(this.timer);
            this.codeTime = 0;
            this.codeTitle = "获取验证码";
            this.phoneinfo.phone = "";
            this.phoneinfo.authCode = "";

            this.$hideLoading({
              message: "更换成功",
              type: "success",
            });
          } else {
            this.$hideLoading({
              message: res.msg,
              type: "success",
            });
          }
        })
        .catch((err) => {
          this.$hideLoading({
            message: "更换失败",
            type: "error",
          });
        });
    },
    codeSuccess() {
      this.dialogVisible = false;
      let params = {
        phone: this.phoneinfo.phone,
        random: this.phoneinfo.random,
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
      let check = this.formCheck(this.phoneinfo.phone);
      if (!check.flag) {
        return this.$message.error(check.info);
      }

      let params = {
        index: 3,
        phone: this.phoneinfo.phone,
        type: "pc",
      };
      getSlideCode(params).then((res) => {
        this.img1 = "data:image/png;base64," + res.data.image1;
        this.img2 = "data:image/png;base64," + res.data.image2;

        this.phoneinfo.random = res.data.random;
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
  },
};
</script>

<style lang="scss">
.phone-box {
  display: flex;
  margin-left: 60px;

  .phone_title {
    margin-right: 40px;
    font-size: 16px;
  }
}

.qrgg {
  width: 200px;
  margin-top: 40px;
  background: #960a0f;
  color: #fff;
  border-radius: 10px;
}

.qrgg:hover,
.qrgg:focus {
  background: #960a0f;
  color: #fff;
}
</style>
