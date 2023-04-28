<template>
  <div>
    <basic-container>
      <avue-form :option="option" v-model="form" @tab-click="handleTabClick" @submit="handleSubmit"></avue-form>
    </basic-container>
  </div>
</template>

<script>
import option from '@/option/user/info'
import { getUserInfo, updateInfo, updatePassword } from '@/api/system/user'
import md5 from 'js-md5'
import func from '@/util/func'

export default {
  data() {
    return {
      index: 0,
      option: option,
      form: {},
    }
  },
  created() {
    this.handleWitch()
  },
  methods: {
    handleSubmit(form, done) {
      if (this.index === 0) {
        updateInfo(form).then(
          (res) => {
            if (res.data.success) {
              this.$message({
                type: 'success',
                message: '修改信息成功!',
              })
            } else {
              this.$message({
                type: 'error',
                message: res.data.msg,
              })
            }
            done()
          },
          (error) => {
            window.console.log(error)
            done()
          }
        )
      } else {
        updatePassword(
          md5(form.oldPassword),
          md5(form.newPassword),
          md5(form.newPassword1)
        ).then(
          (res) => {
            if (res.data.success) {
              this.$message({
                type: 'success',
                message: '修改密码成功!',
              })
            } else {
              this.$message({
                type: 'error',
                message: res.data.msg,
              })
            }
            done()
          },
          (error) => {
            window.console.log(error)
            done()
          }
        )
      }
    },
    handleWitch() {
      if (this.index === 0) {
        getUserInfo().then((res) => {
          const user = res.data.data
          this.form = {
            id: user.id,
            avatar: user.avatar,
            name: user.name,
            realName: user.realName,
            phone: user.phone,
            email: user.email,
          }
        })
      }
    },
    handleTabClick(tabs) {
      this.index = func.toInt(tabs.index)
      this.handleWitch()
    },
  },
}
</script>

<style lang="scss" scoped>
.component-box {
  padding: 0 20px;
}
</style>
