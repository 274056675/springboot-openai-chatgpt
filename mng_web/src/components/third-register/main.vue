<template>
  <el-dialog title="账号注册"
             append-to-body
             :visible.sync="accountBox"
             :close-on-click-modal="false"
             :close-on-press-escape="false"
             :show-close="false"
             width="20%">
    <el-form :model="form" ref="form" label-width="80px">
      <el-form-item v-if="tenantMode" label="租户编号">
        <el-input v-model="form.tenantId" placeholder="请输入租户编号"></el-input>
      </el-form-item>
      <el-form-item label="用户姓名">
        <el-input v-model="form.name" placeholder="请输入用户姓名"></el-input>
      </el-form-item>
      <el-form-item label="账号名称">
        <el-input v-model="form.account" placeholder="请输入账号名称"></el-input>
      </el-form-item>
      <el-form-item label="账号密码">
        <el-input v-model="form.password" placeholder="请输入账号密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="form.password2" placeholder="请输入确认密码"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
              <el-button type="primary" :loading="loading" @click="handleRegister">确 定</el-button>
            </span>
  </el-dialog>
</template>

<script>
  import {mapGetters} from "vuex";
  import {validatenull} from "@/util/validate";
  import {registerGuest} from "@/api/user";
  import {getTopUrl} from "@/util/util";
  import {info} from "@/api/system/tenant";
  import {resetRouter} from "@/router/router";

  export default {
    name: "thirdRegister",
    data() {
      return {
        form: {
          tenantId: '',
          name: '',
          account: '',
          password: '',
          password2: '',
        },
        loading: false,
        tenantMode: true,
        accountBox: false,
      };
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      this.getTenant();
    },
    mounted() {
      // 若未登录则弹出框进行绑定
      if (validatenull(this.userInfo.user_id) || this.userInfo.user_id < 0) {
        this.form.name = this.userInfo.user_name;
        this.form.account = this.userInfo.user_name;
        this.accountBox = true;
      }
    },
    methods: {
      handleRegister() {
        if (this.form.tenantId === '') {
          this.$message.warning("请先输入租户编号");
          return;
        }
        if (this.form.account === '') {
          this.$message.warning("请先输入账号名称");
          return;
        }
        if (this.form.password === '' || this.form.password2 === '') {
          this.$message.warning("请先输入密码");
          return;
        }
        if (this.form.password !== this.form.password2) {
          this.$message.warning("两次密码输入不一致");
          return;
        }
        this.loading = true;
        registerGuest(this.form, this.userInfo.oauth_id).then(res => {
          this.loading = false;
          const data = res.data;
          if (data.success) {
            this.accountBox = false;
            this.$alert("注册申请已提交,请耐心等待管理员通过!", '注册提示').then(() => {
              this.$store.dispatch("LogOut").then(() => {
                resetRouter();
                this.$router.push({path: "/login"});
              });
            })
          } else {
            this.$message.error(data.msg || '提交失败');
          }
        }, error => {
          window.console.log(error);
          this.loading = false;
        });
      },
      getTenant() {
        let domain = getTopUrl();
        // 临时指定域名，方便测试
        //domain = "https://bladex.vip";
        info(domain).then(res => {
          const data = res.data;
          if (data.success && data.data.tenantId) {
            this.form.tenantId = data.data.tenantId;
            this.tenantMode = false;
          }
        })
      },
    },
  };
</script>
