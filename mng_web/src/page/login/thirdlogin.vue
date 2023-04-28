<template>
  <div class="social-container">
    <!-- <div @click="handleClick('github')">
      <span class="container" :style="{backgroundColor:'#61676D'}">
        <i icon-class="github" class="iconfont icongithub"></i>
      </span>
      <p class="title">{{$t('login.github')}}</p>
    </div>
    <div @click="handleClick('gitee')">
      <span class="container" :style="{backgroundColor:'#c35152'}">
        <i icon-class="gitee" class="iconfont icongitee2"></i>
      </span>
      <p class="title">{{$t('login.gitee')}}</p>
    </div>-->
    <div @click="handleClick('wechat_open')">
      <span class="container" :style="{backgroundColor:'#8dc349'}">
        <i icon-class="wechat" class="iconfont icon-weixin" />
      </span>
      <p class="title">{{$t('login.wechat')}}</p>
    </div>
    <!-- <div @click="handleClick('qq')">
      <span class="container" :style="{backgroundColor:'#6ba2d6'}">
        <i icon-class="qq" class="iconfont icon-qq"/>
      </span>
      <p class="title">{{$t('login.qq')}}</p>
    </div>-->
  </div>
</template>

<script>
import website from '@/config/website'

export default {
  name: 'thirdLogin',
  methods: {
    handleClick(source) {
      window.location.href = `${website.authUrl}/${source}`
    },
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.social-container {
  margin: 20px 0;
  display: flex;
  align-items: center;
  justify-content: space-around;

  .iconfont {
    color: #fff;
    font-size: 30px;
  }
  .container {
    $height: 50px;
    cursor: pointer;
    display: inline-block;
    width: $height;
    height: $height;
    line-height: $height;
    text-align: center;
    border-radius: 4px;
    margin-bottom: 10px;
  }
  .title {
    text-align: center;
  }
}
</style>
