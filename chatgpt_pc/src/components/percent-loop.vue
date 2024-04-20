<template>
  <div class="percentloop" v-if="showPersent">
    <div class="percentloop">
      <div class="circle-left">
        <div :style="{ transform: 'rotate(' + transformLeft + 'deg)' }"></div>
        {{ transformLeft }}
      </div>
      <div class="circle-right">
        <div :style="{ transform: 'rotate(' + transformRight + 'deg)' }"></div>
      </div>
      <div class="number">
        {{ percentNum }} %
      </div>
    </div>
  </div>
</template>
 
<script>
export default {
  name: 'percentloop',
  props: {
    percentNum: {
      type: [String, Number],
      default: 0
    },
    showPersent: {
      type: Boolean,
      default: false
    }
  },
  computed: {
     transformLeft() {
      let deg = 0
      if (this.percentNum >= 100) {
        deg = 180
      } else if (this.percentNum >= 50) {
        deg = parseInt(360 * this.percentNum / 100) - 180
      }
      return deg
    },
    transformRight() {
      let deg = 0
      if (this.percentNum >= 50) {
        deg = 180
      } else {
        deg = parseInt(360 * this.percentNum / 100)
      }
      return deg
    }
  }
}
</script>
 
<style lang="scss">
.percentloop {
  position: absolute;
  width: 150px;
  height: 150px;
  overflow: hidden;
  // background: #0000005e;
  z-index: 100000;
  display: flex;
  align-items: center;
  justify-content: space-between;
 
  .percentloop {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    overflow: hidden;
    margin: calc(50% - 150px) calc(50% - 75px);
 
    .circle-left,
    .circle-right {
      position: absolute;
      top: 0;
      left: 0;
      width: 50%;
      height: 100%;
      background: linear-gradient(rgb(255, 123, 0), rgb(0, 251, 255));
      overflow: hidden;
 
      &>div {
        width: 100%;
        height: 100%;
        background-color: #f5efe5;
        transform-origin: right center;
        transition: all .5s linear;
 
      }
    }
 
    .circle-right {
      left: 50%;
      background: linear-gradient(rgb(255, 123, 0), rgb(0, 251, 255));
 
      &>div {
        transform-origin: left center;
      }
    }
 
    .number {
      position: absolute;
      top: 9%;
      bottom: 9%;
      left: 9%;
      right: 9%;
      background-color: #fff;
      border-radius: 50%;
      overflow: hidden;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #000;
    }
  }
}
</style>