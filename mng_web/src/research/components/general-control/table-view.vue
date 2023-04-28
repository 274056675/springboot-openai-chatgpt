<template>
  <div class="table-view-box">
    <el-dialog
      v-dialogdrag
      element-loading-background="transparent"
      v-if="tableViewOptionData.viewObj.type == 'dialog'"
      :title="tableViewOptionData.viewObj.title"
      :visible.sync="tableViewOptionData.viewObj.isShow"
      :destroy-on-close="tableViewOptionData.viewObj.destroy?true:false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      :width="tableViewOptionData.viewObj.width"
      :before-close="tableViewDeclareFun"
      v-bind="tableViewOptionData.viewObj.params"
    >
      <code-test-list
        v-if="tableViewOptionData.viewObj.isShow"
        ref="codeView"
        :tableId="tableViewOptionData.tableId"
        :searchObj="tableViewOptionData.searchObj"
        :tableView="true"
        v-bind="tableViewOptionData.params"
      ></code-test-list>
      <span slot="footer" class="dialog-footer"></span>
    </el-dialog>
    <el-drawer
      v-if="tableViewOptionData.viewObj.type == 'drawer'"
      element-loading-background="rgba(255,255,255,0.3)"
      :title="tableViewOptionData.viewObj.title"
      :size="tableViewOptionData.viewObj.width"
      :visible.sync="tableViewOptionData.viewObj.isShow"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :modal="false"
      :append-to-body="true"
      :before-close="tableViewDeclareFun"
      v-bind="tableViewOptionData.viewObj.params"
    >
      <code-test-list
        v-if="tableViewOptionData.viewObj.isShow"
        ref="codeView"
        :tableId="tableViewOptionData.tableId"
        :searchObj="tableViewOptionData.searchObj"
        :tableView="true"
        v-bind="tableViewOptionData.params"
      ></code-test-list>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: 'tableView',
  props: ['tableViewOptionData', 'beforeClose'],
  data() {
    return {
      /*  tableViewOptionData:{
        viewObj: {
          type:'',
          title:'',
          isShow:false,
          width:'80%'
        },
        tableId: '',
        hideHeader:true,
        searchObj: {},
      } */
    }
  },
  methods: {
    tableViewDeclareFun(done) {
      this.tableViewOptionData.viewObj.isShow = false
      let type = 'close'
      if (this.tableViewOptionData.closeType) {
        type = this.tableViewOptionData.closeType
      }
      if (this.beforeClose) {
        this.beforeClose(type)
      }
      done()
    },
  },
}
</script>

<style>
</style>