<template>
  <div class="share-box">
    <div class="share-header">
      <div class="share-header_title">邀好友,享推荐收益</div>
      <div class="share-header_rule">邀请规则</div>
    </div>
    <div class="share-main">
      <div class="img-box">
        <img style="width:50px;height:50px;" src="../../static/df_avatar_nan.png" />
        <span class="iconfont icon-tianjia icon" style="margin: 0 36px;"></span>
        <span class="iconfont icon-tianjia icon"></span>
      </div>
      <div class="share_title" @click="generateQrCode">复制链接邀请好友</div>
    </div>
  </div>
</template>

<script>
import { share } from '@/utils/share'
export default {
  name: 'share',
  data() {
    return {}
  },
  methods: {
    //  邀请
    generateQrCode() {
      share()
    },
  }
}
</script>

<style lang="scss" scoped>
.share-box {
  height: 90%;
  padding: 24px;
  background-color: #faf4ec;

  .share-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .share-header_title {
      color: #970d12;
      font-size: 32px;
      font-weight: 800;
    }
    .share-header_rule {
      width: 96px;
      height: 36px;
      line-height: 36px;
      font-size: 16px;
      text-align: center;
      background-color: #970d12;
      border-radius: 20px;
      color: #fff;
      cursor: pointer;
    }
  }

  .share-main {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 226px;
    margin-top: 40px;
    background: url('../../static/index/bg2.png') no-repeat;

    .img-box {
      display: flex;
      margin-top: 60px;

      .icon {
        font-size: 48px;
        color: #970d12;
      }
    }
    .share_title {
      width: 198px;
      height: 36px;
      margin-top: 36px;
      line-height: 36px;
      text-align: center;
      background: #960a0f;
      border-radius: 18px;
      font-size: 14px;
      color: #fff;
      cursor: pointer;
    }
  }
}
</style>