<template>
  <div class="inform">
    <div class="inform-conttent">
      <div v-html="informText"></div>
    </div>
  </div>
</template>

<script>
import { getNoticeContentApi } from '@/api/system'
export default {
  name: 'inform',
  data() {
    return {
      informText: '',
      informId: ''
    }
  },
  watch: {
    '$route.query.id': function() {
      this.informId = this.$route.query.id
      this.getNoticeContent(this.informId)
    }
  },
  mounted() {
    this.informId = this.$route.query.id
    this.getNoticeContent(this.informId)
  },
  methods: {
    getNoticeContent(id) {
      getNoticeContentApi(id).then(res => {
        this.informText = res.data.content
      })
    }
  }
}
</script>

<style lang="scss">
.inform {
  width: 100%;
  // height: 100%;
  display: flex;
  justify-content: center;
  .inform-conttent {
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