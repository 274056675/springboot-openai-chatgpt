<template>
  <div class="hangbook">
    <div class="hangbook-conttent">
      <div v-html="handbookText"></div>
    </div>
  </div>
</template>

<script>
import { getContentApi } from '@/api/system'
export default {
  name: 'handbook',
  data() {
    return {
      handbookText: '',
    }
  },
  mounted() {
    this.getContent()
  },
  methods: {
    async getContent() {
      let data = await getContentApi()
      console.log('data>>>>>>>>>>>', data)
      this.handbookText = data.data
    }
  }
}
</script>

<style lang="scss">
.hangbook {
  width: 100%;
  // height: 100%;
  display: flex;
  justify-content: center;
  .hangbook-conttent {
    width: 760px;
    // height: 100%;
    background-color: #fff;
    padding: 0 20px 10px;
    margin: 10px 0 20px;
    box-sizing: border-box;
    border-radius: 10px;
  }
}
</style>