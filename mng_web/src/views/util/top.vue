<template>
  <div>
    <div style="height:2000px;background-color:#fff;">
      <div style="padding:15px 20px;font-size:18px;">往下拉就会出现返回菜单</div>
    </div>
    <avue-back-top id="avue-view"></avue-back-top>
    <avue-back-top id="avue-view"
                   :height="100"
                   :bottom="200">
      <div class="top">返回顶端</div>
    </avue-back-top>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.top {
  padding: 10px;
  font-size: 14px;
  background: rgba(0, 153, 229, 0.7);
  color: #fff;
  text-align: center;
  border-radius: 2px;
}
</style>
