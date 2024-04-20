<template>
  <basic-container v-loading="loading">
    <div class="statement-view-box">
      <div v-loading="iframeLoading" class="iframe-box">
        <div v-if="dataViewId" class="data-view-iframe">
          <el-popover
            placement="top-start"
            title="提示"
            width="180"
            trigger="hover"
            content="按下 ESC 退出全屏"
          >
            <el-button class="iframe-el-button" slot="reference" @click="fullScreenOpenFun">全屏显示</el-button>
          </el-popover>
          <iframe
            v-show="isIframe"
            ref="iframe"
            class="iframe"
            :src="iframeUrl"
            width="100%"
            allowtransparency="true"
            frameborder="0"
            scrolling
            vspace="0"
          ></iframe>
        </div>
      </div>
    </div>
    <el-dialog
      v-loading="fullLoading"
      title="提示"
      :visible.sync="full"
      :fullscreen="true"
      :show-close="false"
      width="100%"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :close-on-press-escape="true"
      custom-class="data-view-iframe-dialog"
    >
      <iframe
        v-show="isIframe"
        ref="iframefull"
        class="iframe"
        :src="iframeUrl"
        width="100%"
        allowtransparency="true"
        frameborder="0"
        scrolling
        vspace="0"
      ></iframe>
      <span slot="footer" class="dialog-footer"></span>
    </el-dialog>
  </basic-container>
</template>

<script>
import { Base64 } from 'js-base64'
import { dataViewUrl } from '@/config/url'
import { dataTableId } from '@/research/config/index'

import { getDataApi } from '@/api/research/codelist'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      full: false,
      isIframe: false,
      loading: false,
      iframeLoading: false,
      dataTableId: dataTableId, //数据表id
      dataViewId: '',
      dataViewParams: {},
      fullLoading: false,
      isOpentFull: false,
    }
  },
  props: ['params'],
  computed: {
    ...mapGetters(['token', 'screen', 'tenantId', 'website']),
    iframeUrl() {
      let auth = `${Base64.encode(
        `${this.website.clientId}:${this.website.clientSecret}`
      )}`
      let key = Object.keys(this.dataViewParams)
      let str = ''
      if (key && key.length > 0) {
        key.forEach((item) => {
          str = str + `&${item}=${this.dataViewParams[item]}`
        })
      }
      return `${dataViewUrl}/${this.dataViewId}?exclude_token=${this.token}&exclude_auth=${auth}${str}`
    },
  },
  created() {},
  mounted() {
    this.init()
  },
  methods: {
    async init() {
      this.loading = true
      //获取code
      this.code = this.$route.params.code
      if (!this.code) {
        this.code = this.$route.path.split('views/tool/dataview/')[1]
      }
      if (this.params && this.params.code) {
        this.code = this.params.code
      }
      //获取form配置信息
      let formRes = await getDataApi(this.dataTableId, {
        pageNo: 1,
        pageSize: -521,
        form_code: this.code,
      })
      let formData = formRes.data.data
      if (!formData.records || formData.records.length <= 0) {
        this.$message({
          message:
            '未获取到相关配置，请检查system_dataview_data表是否有对应的数据',
          type: 'warning',
        })
        return false
      }
      formData = JSON.parse(formData.records[0].form_data)
      this.loading = false
      this.dataViewId = formData.id
      if (formData.params) {
        this.dataViewParams = formData.params
      }
      this.isIframe = true
      setTimeout(() => {
        this.iframeInit('iframe')
      }, 300)
    },
    //iframe窗口初始化
    iframeInit(refs) {
      this.iframeLoading = true
      let iframe = this.$refs[refs]
      let clientHeight =
        document.documentElement.clientHeight - (screen > 1 ? 200 : 130)
      
      if (!iframe) {
        this.iframeLoading = false
        return false
      }
      clientHeight = clientHeight > 400 ? clientHeight : 400
      iframe.style.height = `${clientHeight}px`
      
      if (iframe.attachEvent) {
        iframe.attachEvent('onload', () => {
          this.iframeLoading = false
        })
      } else {
        iframe.onload = () => {
          this.iframeLoading = false
        }
      }
      //优化用户体验
      setTimeout(() => {
        if (this.iframeLoading) {
          this.iframeLoading = false
        }
      }, 4000)
    },
    fullScreenOpenFun() {
      this.full = true
      if (this.isOpentFull) {
        return false
      }
      this.fullLoading = true
      setTimeout(() => {
        let iframe = this.$refs.iframefull
        
        if (iframe.attachEvent) {
          iframe.attachEvent('onload', () => {
            this.fullLoading = false
          })
        } else {
          iframe.onload = () => {
            this.fullLoading = false
          }
        }
        //优化用户体验
        setTimeout(() => {
          if (this.fullLoading) {
            this.fullLoading = false
          }
        }, 4000)
        this.isOpentFull = true
      }, 300)
    },
  },
}
</script>

<style lang="scss" scoped>
.statement-view-box {
  /deep/.avue-form__menu {
    display: none;
  }
  .iframe-box {
    min-height: 400px;
  }
}
.iframe {
  width: 100%;
  height: 100%;
  border: 0;
  overflow: hidden;
  box-sizing: border-box;
}
.data-view-iframe {
  .iframe-el-button {
    margin-bottom: 20px;
  }
}
.data-view-full-iframe {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 9999;
  iframe {
    width: 100%;
    height: 100%;
  }
}
</style>
<style lang="scss">
.data-view-iframe-dialog {
  &::-webkit-scrollbar {
    width: 0 !important;
    height: 0 !important;
  }
  .el-dialog__header {
    display: none;
  }
  .el-dialog__footer {
    display: none;
  }
  .el-dialog__body {
    padding: 0;
    height: 100%;
  }
}
</style>
