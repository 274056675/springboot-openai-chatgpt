<template>
  <basic-container>
    <div class="wel">
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-platform-eleme"
                   text="开始菜单1"
                   time="1"
                   background="/img/bg/bg3.jpg"
                   color="#d56259"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-eleme"
                   text="开始菜单2"
                   time="2"
                   background="/img/bg/bg2.jpg"
                   color="#419ce7"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-delete-solid"
                   text="开始菜单3"
                   time="3"
                   color="#56b69b"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-delete"
                   text="开始菜单4"
                   time="4"
                   color="#d44858"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-tools"
                   text="开始菜单5"
                   time="5"
                   color="#3a1f7e"></basic-block>
      <basic-block :width="410"
                   :height="height"
                   icon="el-icon-setting"
                   text="开始菜单6"
                   time="6"
                   background="/img/bg/bg1.jpg"
                   dept="这是一段很长的很长很长很长的描述这是一段很长的很长很长很长的描述"
                   color="#422829"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-user-solid"
                   text="开始菜单7"
                   time="7"
                   color="#613cbd"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-star-off"
                   text="开始菜单8"
                   time="8"
                   color="#da542e"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-goods"
                   text="开始菜单9"
                   time="9"
                   color="#2e8aef"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-circle-check"
                   text="开始菜单10"
                   time="10"
                   color="#3d17b8"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-platform"
                   text="开始菜单11"
                   time="11"
                   color="#e31462"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-fold"
                   text="开始菜单12"
                   time="12"
                   color="#d9532d"></basic-block>
      <basic-block :width="410"
                   :height="height"
                   icon="el-icon-s-open"
                   text="开始菜单13"
                   time="13"
                   dept="这是一段很长的很长很长很长的描述这是一段很长的很长很长很长的描述"
                   color="#b72147"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-flag"
                   text="开始菜单14"
                   time="14"
                   color="#01a100"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-data"
                   text="开始菜单15"
                   time="15"
                   color="#0c56bf"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-grid"
                   text="开始菜单16"
                   time="16"
                   color="#0098a9"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-release"
                   text="开始菜单17"
                   time="17"
                   background="/img/bg/bg2.jpg"
                   color="#209bdf"></basic-block>
      <basic-block :width="width"
                   :height="height"
                   icon="el-icon-s-home"
                   text="开始菜单18"
                   time="18"
                   background="/img/bg/bg3.jpg"
                   color="#603bbc"></basic-block>
      <basic-block :width="515"
                   :height="height"
                   icon="el-icon-s-promotion"
                   text="开始菜单19"
                   time="19"
                   dept="这是一段很长的很长很长很长的描述这是一段很长的很长很长很长的描述"
                   color="#009bad"></basic-block>
      <basic-block :width="515"
                   :height="height"
                   icon="el-icon-s-custom"
                   text="开始菜单20"
                   time="20"
                   background="/img/bg/bg4.jpg"
                   dept="这是一段很长的很长很长很长的描述这是一段很长的很长很长很长的描述"
                   color="#d74e2a"></basic-block>
    </div>
  </basic-container>
</template>

<script>
  export default {
    data() {
      return {
        width: 200,
        height: 120,
      }
    }
  }
</script>

<style lang="scss">
  .wel {
    display: flex;
    flex-wrap: wrap;
    width: 1100px;
    margin: 0 auto;
  }
</style>
