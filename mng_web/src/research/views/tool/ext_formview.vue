<template>
  <basic-container v-loading="!isShow">
    <form-view
      ref="form_view"
      v-if="isShow"
      :formOptionData="FormViewControlOption"
      :formViewControlFun="formViewControlFun.bind(this)"
    ></form-view>
  </basic-container>
</template>

<script>
import { getDataApi } from '@/api/research/codelist'
import FormView from '@/research/components/general-control/form-view.vue'
import { formTableId } from '@/research/config/index'
export default {
  data() {
    return {
      /* FormViewControlOption: {
        viewObj: {
          isShow: true,
          type: 'view',
        },
        formId: '',
        onlineFormId: '',
        formOpenType: 'edit',
        actionData: {
          type: 'onlineEdit',
          isMessage: true,
          noRouter: true, //不启用路由配置
        },
        params: {},
        btnPermissions: {
          clearBtn: false,
        },
      }, */
      isShow: false,
      FormViewControlOption: {},
      formData: {},
      code: '',
      dataTableId: formTableId,
    }
  },
  watch: {},
  components: {
    FormView,
  },
  created() {
    this.init()
  },
  methods: {
    async init() {
      //获取code
      this.code = this.$route.params.code
      if (!this.code) {
        this.code = this.$route.path.split('views/tool/formview/')[1]
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
          message: '未获取到相关配置，请检查system_form_data表是否有对应的数据',
          type: 'warning',
        })
        return false
      }
      formData = JSON.parse(formData.records[0].form_data)
      this.formData = formData
      let urlPamers = location.hash.split('?token=')[1]
      if (urlPamers.indexOf('&')) {
        let all = urlPamers.split('&')
        all.forEach((item, index) => {
          if (index != 0) {
            let key = item.split('=')[0]
            let value = item.split('=')[1]
            if (!formData.FormViewControlOption.defaultData) {
              formData.FormViewControlOption.defaultData = {}
            }
            formData.FormViewControlOption.defaultData[key] = value
          }
        })
      }
      this.FormViewControlOption = formData.FormViewControlOption

      this.isShow = true

      //监听高度变化 父高度
      setTimeout(() => {
        var $tar = document.querySelector('.basic-container')
        console.log($tar)
        var MutationObserver =
          window.MutationObserver ||
          window.webkitMutationObserver ||
          window.MozMutationObserver
        var mutationObserver = new MutationObserver(function () {
          const clientHeight =
            document.querySelector('.basic-container').offsetHeight
          window.parent.postMessage(
            {
              cmd: 'setHeight',
              params: {
                success: true,
                data: {
                  height: clientHeight,
                },
              },
            },
            '*'
          )
        })
        mutationObserver.observe($tar, {
          attributes: true, // 节点内容或节点文本的变动
          subtree: true,
        })
      }, 0)
    },
    formViewControlFun() {},
  },
}
</script>

<style></style>
