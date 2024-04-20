<template>
  <div>
    <el-dialog
      custom-class="tabs-view-dialog-box"
      element-loading-background="transparent"
      v-if="tabOptionData.viewObj.type == 'dialog'"
      top="10vh"
      :title="tabOptionData.viewObj.title"
      :visible.sync="tabOptionData.viewObj.isShow"
      :destroy-on-close="tabOptionData.viewObj.destroy ? true : false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      :modal="false"
      :width="tabOptionData.viewObj.width"
      v-bind="tabOptionData.viewObj.params"
    >
      <div class="tab-view-box">
        <el-tabs
          v-model="tabsActiveName"
          @tab-click="tabsHandleClick"
          v-bind="tabOptionData.tabsParams"
        >
          <el-tab-pane
            v-for="item in tableData"
            :key="item.tabName"
            :label="item.title"
            :name="item.tabName"
          >
            <!-- 表格 -->
            <div v-if="item.type == 'table' && item.id">
              <code-test-list
                :ref="item.tabName + '_table'"
                :tranTableId="item.id"
                v-bind="item.params"
              ></code-test-list>
            </div>
            <!-- 表单 -->
            <div v-else-if="item.type == 'form'">
              <div class="form-btn-box" v-if="item.btnData">
                <div v-for="(btn, index) in item.btnData" :key="index">
                  <div class="btn-box" v-if="btn.show !== false">
                    <el-button v-bind="btn.params" @click="btn.clickFun(that)">
                      {{
                      btn.btnName
                      }}
                    </el-button>
                  </div>
                </div>
              </div>
              <form-view
                :ref="item.tabName"
                :formViewControlFun="formViewFun"
                :formOptionData="item.params"
                :params="tabOptionData.allSearchObj"
              ></form-view>
            </div>
            <!-- tabs -->
            <el-tabs
              v-else-if="item.type == 'tabs'"
              v-model="item.activeName"
              @tab-click="childTabsHandleClick"
              v-bind="item.tabsParams"
            >
              <el-tab-pane
                v-for="child in item.tabsData"
                :key="child.tabName"
                :label="child.title"
                :name="child.tabName"
              >
                <!-- 表格 -->
                <div v-if="child.type == 'table' && child.id">
                  <code-test-list
                    class="tabs-view-child-table-box"
                    :ref="child.tabName + '_child_table'"
                    :tranTableId="child.id"
                    v-bind="child.params"
                  ></code-test-list>
                </div>
                <!-- 表单 -->
                <div v-else-if="child.type == 'form'">
                  <div class="form-btn-box" v-if="child.btnData">
                    <div
                      class="btn-box"
                      v-for="(childBtn, childBtnIndex) in child.btnData"
                      :key="childBtnIndex"
                    >
                      <el-button
                        v-if="child.show !== false"
                        v-bind="childBtn.params"
                        @click="childBtn.clickFun(that)"
                      >{{ childBtn.btnName }}</el-button>
                    </div>
                  </div>
                  <form-view
                    :ref="child.tabName"
                    :formViewControlFun="formViewFun"
                    :formOptionData="child.params"
                    :params="tabOptionData.allSearchObj"
                  ></form-view>
                </div>
              </el-tab-pane>
            </el-tabs>
          </el-tab-pane>
        </el-tabs>
      </div>
      <span slot="footer" class="dialog-footer"></span>
    </el-dialog>
    <table-view
      v-if="isTableView"
      :tableViewOptionData="tableViewOptionData"
      :beforeClose="tableViewDeclareFun"
    ></table-view>
  </div>
</template>

<script>
import FormView from '@/research/components/general-control/form-view.vue'
import tableView from '@/research/components/general-control/table-view.vue'
export default {
  name: 'TabsView',
  components: { FormView, tableView },
  data() {
    return {
      tabsActiveName: '',
      tabsData: [],
      that: this,
      //表格弹窗
      isTableView: false,
      tableViewOptionData: {
        viewObj: {},
        tableId: '',
        searchObj: {},
      },
    }
  },
  watch: {
    'tabOptionData.viewObj.isShow': function (value) {
      
      if (value && this.tabOptionData.openIndex != undefined) {
        try {
          this.tabsActiveName =
            this.tabOptionData.tabsData[this.tabOptionData.openIndex].tabName
        } catch (error) {
          this.tabsActiveName = ''
        }
      }
      if (value && this.tabsActiveName) {
        setTimeout(() => {
          this.anewCurrTabsData(this.tabsActiveName)
        }, 0)
      }
    },
    tabOptionData: {
      handler(newVal, oldVal) {
        
      },
      deep: true, //深监听
    },
    //监听tab切换 查看是否需要刷新表格
    tabsActiveName(newVal) {
      
      this.anewCurrTabsData(newVal)
    },
    tableViewDeclareFun() {},
  },
  props: [
    'tabOptionData',
    /* 
      viewObj:{
        title:'',
        type:'',
        isShow:'',
        width:'100%',
      },
      tabsParams:{}, //tab相关配置
      openIndex:0,//默认打开下标值为tab的页面
      tabsData:[
        {
          id:'',//表单开发id
          title:'',//tab标题
          tabName:'',//tabkey 唯一
          type:'',//控件类型 table form
          isRefresh:false,//切换tab时是否刷新表格数据

          params:{
            //表单
            viewObj:{
              isShow:true,
              type:"view"
            },
            formId:"1474639164730269698",
            onlineFormId:"",
            formOpenType:"edit",
            actionData:{
              type:"returnData",
              isMessage:false,
              noRouter:true
            },
            params:{},
            btnPermissions:{
              clearBtn:false,
              submitBtn:false
            }
            //表格
            codetestlist props参数配置
          }, //其他参数配置

        }
      ]
    */
    'tabViewControlFun',
  ],
  computed: {
    tableData() {
      let data = this.deepClone(this.tabOptionData.tabsData)
      data = data.map((item, index) => {
        if (item.type == 'table') {
          item.params = this.tableBindFun(
            this.tabOptionData.allSearchObj,
            item.params
          )
        }
        if (item.type !== 'tabs') {
          //默认开启懒加载
          if (item.params === undefined) {
            item.params = {}
          }
          if (item.params.isLazy !== false) {
            item.params.isLazy = index != 0
          }
        }

        if (item.type == 'tabs') {
          item.tabsData = item.tabsData.map((child) => {
            if (child.type == 'table') {
              child.params = this.tableBindFun(
                this.tabOptionData.allSearchObj,
                child.params
              )
            }
            //默认开启懒加载
            if (child.params === undefined) {
              child.params = {}
            }
            if (child.params.isLazy !== false) {
              child.params.isLazy = true
            }
            return child
          })
        }
        return item
      })
      
      return data
    },
  },
  mounted() {
    this.init()
  },
  methods: {
    async init() {},
    anewCurrTabsData(newVal) {
      if (!newVal) {
        return false
      }
      this.tableData.forEach((item) => {
        if (item.tabName != newVal) {
          return false
        }
        if (item.type == 'table') {
          try {
            let dom = this.$refs[`${newVal}_table`][0]
            if (item.params.isLazy && !dom.isTableCrud) {
              dom.init()
              return false
            }
            if (item.isRefresh) {
              setTimeout(() => {
                this.$refs[`${newVal}_table`][0].tableRefreshChangeFun()
              }, 1000)
            }
          } catch (error) {}
        }
        if (item.type == 'form') {
          try {
            let dom = this.$refs[newVal][0]
            if (item.params.isLazy && !dom.isInit) {
              dom.init()
              return false
            }
            if (item.isRefresh) {
              if (item.initFun) item.initFun()
              if (item.params.viewObj.isGetData) dom.getFormDataFun()
            }
          } catch (error) {}
        }
        if (item.type == 'tabs') {
          item.tabsData.forEach((child) => {
            if (!item.activeName) {
              return false
            }
            if (child.tabName != item.activeName) {
              return false
            }
            if (child.type == 'table') {
              let childDom = this.$refs[`${child.tabName}_child_table`][0]
              console.log(
                'table=======',
                child.params.isLazy,
                childDom.isTableCrud
              )

              if (child.params.isLazy && !childDom.isTableCrud) {
                childDom.init()
                return false
              }
              if (child.isRefresh) {
                childDom.tableRefreshChangeFun()
              }
            }
            if (child.type == 'form') {
              let childDom = this.$refs[child.tabName][0]
              if (child.params.isLazy && !childDom.isInit) {
                childDom.init()
                return false
              }
              if (child.isRefresh) {
                if (child.initFun) child.initFun()
                childDom.getFormDataFun()
              }
            }
          })
        }
      })
    },
    tabsHandleClick() {},
    childTabsHandleClick(dom) {
      
      this.anewCurrTabsData(this.tabsActiveName)
    },
    tableBindFun(allSearchObj, params) {
      let length =
        typeof allSearchObj == 'object' ? Object.keys(allSearchObj).length : 0
      if (length > 0) {
        if (params && params.searchObj) {
          params.searchObj = {
            ...allSearchObj,
            ...params.searchObj,
          }
        } else {
          params = {
            ...params,
            searchObj: allSearchObj,
          }
        }
      }
      return params
    },
  },
}
</script>
<style lang="scss">
.el-drawer__body {
  overflow: auto;
}
.tab-view-box {
  .form-btn-box {
    background-color: #f1f1f1;
    padding: 10px;
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    .btn-box {
      padding-left: 10px;
    }
    & > div:nth-child(1) {
      .btn-box {
        padding-left: 0px;
      }
    }
  }
}
.tabs-view-dialog-box {
  .el-dialog__body {
    padding: 5px 20px;
  }
}
.tabs-view-child-table-box {
  .test-box-list {
    padding-top: 0px !important;
  }
}
</style>
