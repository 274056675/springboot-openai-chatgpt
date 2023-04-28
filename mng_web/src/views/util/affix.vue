<template>
  <div class="affix">
    <avue-affix id="avue-view">
      <span class="affix-affix">固定在最顶部</span>
    </avue-affix>
    <div class="affix-line"></div>
    <avue-affix id="avue-view"
                :offset-top="50">
      <span class="affix-affix">固定在距离顶部 50px 的位置</span>
    </avue-affix>
    <div class="affix-line"></div>
    <avue-affix id="avue-view"
                :offset-top="100">
      <span class="affix-affix">固定在距离顶部 100px 的位置</span>
    </avue-affix>
    <div class="affix-line"></div>
    <avue-affix id="avue-view"
                :offset-top="150">
      <span class="affix-affix">固定在距离顶部 150px 的位置</span>
    </avue-affix>
    <div style="height:2000px;">
      <div style="padding:15px 20px;font-size:18px;">往下拉就会出现图钉</div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss">
.affix {
  position: relative;
  background-color: #fff;
  &-affix {
    display: inline-block;
    color: #fff;
    padding: 10px 30px;
    text-align: center;
    background: rgba(0, 153, 229, 0.9);
  }
  &-line {
    height: 100px;
  }
}
</style>
