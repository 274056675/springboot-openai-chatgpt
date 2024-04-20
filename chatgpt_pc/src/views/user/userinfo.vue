<template>
  <div class="userinfo">
    <div class="box">
      <div class="title-box">个人信息</div>
      <div class="info">
        <el-avatar
          :size="50"
          :src="userInfo.wxAvatar != '' ? userInfo.wxAvatar : require('@/static/df_avatar_nan.png')"
        ></el-avatar>
        <div class="info-other">
          <div style="font-weight:700;">
            头像
            <label class="gh" for="upload">上传</label>
            <input
              type="file"
              ref="upload"
              id="upload"
              style="position:absolute; clip:rect(0 0 0 0);"
              accept="image/png, image/jpeg, image/gif, image/jpg"
              @change="uploadImg($event)"
            />
          </div>
          <div style="font-size:12px;margin-top:6px;">您可以使用AI作品,版权作品为头像</div>
        </div>
      </div>
    </div>
    <div class="box">
      <div class="title-box">*用户名称</div>
      <el-input
        type="textarea"
        clearable
        :maxlength="20"
        :show-word-limit="true"
        placeholder="请输入名称"
        v-model="name"
        resize="none"
      ></el-input>
    </div>
    <div class="box">
      <div class="title-box">个性签名</div>
      <el-input
        type="textarea"
        clearable
        :maxlength="40"
        :show-word-limit="true"
        placeholder="请输入个性签名"
        v-model="signatureText"
      ></el-input>
    </div>
    <div class="qrgh" @click="editInfoData">确认更换</div>
    <el-dialog :visible.sync="cropperShow" width="30%">
      <div class="show-info">
        <div class="test">
          <vueCropper
            ref="cropper"
            :img="option.img "
            :outputSize="option.size"
            :info="option.info"
            :canScale="option.canScale"
            :autoCrop="option.autoCrop"
            :autoCropWidth="option.autoCropWidth"
            :autoCropHeight="option.autoCropHeight"
            :fixed="option.fixed"
            :fixedNumber="option.fixedNumber"
            :enlarge="4"
          ></vueCropper>
        </div>

        <div class="qrgh-box">
          <div class="qrgh" @click="finish2">裁剪</div>
          <div class="qrgh" @click="editTopData">确认更换</div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import { editInfoDataApi } from '@/api/user'
import { uploadImage } from '@/api/system'
import { VueCropper } from 'vue-cropper'
export default {
  name: 'userinfo',
  components: {
    VueCropper,
  },
  data() {
    return {
      name: '', // 名字
      signatureText: '', //用户签名
      cropperShow: false, // 是否开启剪切
      option: {
        //img的路径自行修改
        img: '',
        info: true,
        size: 1,
        canScale: true,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 300,
        autoCropHeight: 300,
        fixed: true,
        // 真实的输出宽高
        infoTrue: true,
        fixedNumber: [4, 4],
      },
    }
  },
  computed: {
    ...mapGetters(['isLogin', 'userInfo']),
  },
  mounted() {
    if (this.isLogin) {
      this.name = this.userInfo.wxName
      this.signatureText = this.userInfo.sign
    }
  },
  methods: {
    ...mapActions(['getUserInfoActions']),
    async editTopData() {
      this.$showLoading({
        text: '更换中',
      })
      const imgBlob = await fetch(this.option.img).then((r) => r.blob())
      const imgFile = new File([imgBlob], 'avatar', { type: imgBlob.type })
      const formData = new FormData()
      formData.append('file', imgFile)
      formData.append('type', 'avatar')
      uploadImage(formData)
        .then((res) => {
          let data = {
            wxAvatar: res.data.link,
          }
          editInfoDataApi(data)
            .then((res) => {
              this.cropperShow = false
              this.option.img = ''
              this.getUserInfoActions()
              this.$hideLoading({
                message: '更换成功',
                type: 'success',
              })
            })
            .catch((err) => {
              this.cropperShow = false
              this.$hideLoading({
                message: '更换失败',
                type: 'error',
              })
            })
        })
        .catch(() => {
          this.cropperShow = false
          this.$hideLoading({
            message: '更换失败',
            type: 'error',
          })
        })
    },
    editInfoData() {
      let data = {
        wxName: this.name,
        sign: this.signatureText,
      }
      this.$showLoading({
        text: '正在修改',
      })
      editInfoDataApi(data)
        .then((res) => {
          this.getUserInfoActions()
          this.$hideLoading({
            message: '修改成功',
            type: 'success',
          })
        })
        .catch((err) => {
          this.$hideLoading({
            message: '修改失败',
            type: 'error',
          })
        })
    },
    finish2() {
      this.$refs.cropper.getCropBlob((data) => {
        //裁剪后的图片显示
        this.option.img = window.URL.createObjectURL(new Blob([data]))
      })
    },
    uploadImg(e) {
      //上传图片
      this.option.img = ''
      var file = e.target.files[0]
      if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
        alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种')
        return false
      }
      var reader = new FileReader()
      reader.onload = (e) => {
        let data
        data = e.target.result
        if (typeof e.target.result === 'object') {
          // 把Array Buffer转化为blob 如果是base64不需要
          data = window.URL.createObjectURL(new Blob([e.target.result]))
        }
        this.option.img = data
        this.cropperShow = true
      }
       e.target.value = ''
      // 转化为base64
      // reader.readAsDataURL(file)
      // 转化为blobcs
      reader.readAsArrayBuffer(file)
    },
  },
}
</script>

<style lang="scss" scoped>
.el-textarea__inner {
  max-height: 100px;
}
// 公共
.box {
  display: flex;
  flex-direction: column;
}

.userinfo {
  // height: 100%;
}
.title-box {
  padding: 24px 0;
  font-size: 16px;
  font-weight: 700;
}

.info {
  display: flex;
  align-items: center;

  .info-other {
    margin-left: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .gh {
      display: inline-block;
      width: 60px;
      height: 26px;
      margin-left: 10px;
      line-height: 26px;
      text-align: center;
      background: #ffffff;
      border: 1px solid #666666;
      border-radius: 16px;
      font-size: 12px;
      cursor: pointer;
    }
  }
}
.qrgh {
  width: 198px;
  height: 44px;
  margin-top: 40px;
  line-height: 44px;
  text-align: center;
  border-radius: 10px;
  background-color: #960a0f;
  color: #ffffff;
  cursor: pointer;
}

.show-info {
  // margin-bottom: 50px;
}

.show-info h2 {
  margin: 0;
}

/*.title, .title:hover, .title-focus, .title:visited {
        color: black;
    }*/

.title {
  display: block;
  text-decoration: none;
  text-align: center;
  line-height: 1.5;
  margin: 20px 0px;
  background-image: -webkit-linear-gradient(left, #3498db, #f47920 10%, #d71345 20%, #f7acbc 30%, #ffd400 40%, #3498db 50%, #f47920 60%, #d71345 70%, #f7acbc 80%, #ffd400 90%, #3498db);
  color: transparent;
  background-size: 200% 100%;
  animation: slide 5s infinite linear;
  font-size: 40px;
}
.test {
  height: 285px;
}

.model {
  position: fixed;
  z-index: 10;
  width: 100vw;
  height: 100vh;
  overflow: auto;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.8);
}

.model-show {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
}
.model-show {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
}

.model img {
  display: block;
  margin: auto;
  max-width: 80%;
  user-select: none;
  background-position: 0px 0px, 10px 10px;
  background-size: 20px 20px;
  background-image: linear-gradient(45deg, #eee 25%, transparent 25%, transparent 75%, #eee 75%, #eee 100%), linear-gradient(45deg, #eee 25%, white 25%, white 75%, #eee 75%, #eee 100%);
}
.c-item {
  display: block;
  padding: 10px 0;
  user-select: none;
}

@keyframes slide {
  0% {
    background-position: 0 0;
  }

  100% {
    background-position: -100% 0;
  }
}
@media screen and (max-width: 1000px) {
  .content {
    max-width: 90%;
    margin: auto;
  }

  .test {
    height: 400px;
  }
}
.qrgh-box {
  display: flex;
  justify-content: space-around;
  align-content: center;

  .qrgh {
    width: 198px;
    margin: 10px 0;
    // height: 44px;
    // line-height: 44px;
    text-align: center;
    border-radius: 10px;
    background-color: #960a0f;
    color: #ffffff;
    cursor: pointer;
  }
}
</style>