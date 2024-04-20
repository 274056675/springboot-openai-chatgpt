<template>
  <basic-container>
    <div class="code-test-box-tabs">
      <el-tabs v-model="tabsActiveName" @tab-click="tabsHandleClick" v-bind="tabsParams">
        <el-tab-pane
          v-for="item in tabsData"
          :key="item.tabName"
          :label="item.title"
          :name="item.tabName"
        >
          <!-- 表格 -->
          <div v-if="item.type=='table' && item.id">
            <code-test-list
              :ref="item.tabName+'_table'"
              :tranTableId="item.id"
              v-bind="item.params"
            ></code-test-list>
          </div>
          <!-- 表单 -->
          <div v-else-if="item.type=='form'">
            <form-view
              :ref="item.tabName+'_form'"
              :formViewControlFun="formViewFun"
              :formOptionData="item.params"
            ></form-view>
          </div>
          <!-- tabs -->
          <el-tabs
            v-else-if="item.type=='tabs'"
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
              <div v-if="child.type=='table' && child.id">
                <code-test-list
                  :ref="child.tabName+'_child_table'"
                  :tranTableId="child.id"
                  v-bind="child.params"
                ></code-test-list>
              </div>
              <!-- 表单 -->
              <div v-else-if="child.type=='form'">
                <div class="form-btn-box" v-if="child.btnData">
                  <el-button
                    v-for="(childBtn,childBtnIndex) in child.btnData"
                    :key="childBtnIndex"
                    v-bind="childBtn.params"
                    @click="childBtn.clickFun(that)"
                  >{{childBtn.btnName}}</el-button>
                </div>
                <form-view
                  :ref="child.tabName"
                  :formViewControlFun="formViewFun"
                  :formOptionData="child.params"
                ></form-view>
              </div>
            </el-tab-pane>
          </el-tabs>
          <!-- 其他控件 -->
          <div v-else>
            <component :ref="item.tabName" :is="item.type" :params="item.params"></component>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </basic-container>
</template>

<script>
import { mapGetters } from 'vuex'
import { getDataApi } from '@/api/research/codelist'
import FormView from '@/research/components/general-control/form-view'

import { tabsTableId } from '@/research/config/index'
export default {
  components: {
    FormView,
  },
  watch: {
    tabsActiveName(newVal, oldVal) {
      if (!oldVal || oldVal == '0') {
        return false
      }
      let data = {}
      this.tabsData.forEach((item) => {
        if (item.tabName == newVal) {
          data = item
        }
      })
      setTimeout(() => {
        if (data.type == 'table') {
          let dom = this.$refs[data.tabName + '_table'][0]
          if (data.params.isLazy && !dom.isTableCrud) {
            dom.init()
            return false
          }
          if (data.isRefresh) {
            dom.tableRefreshChangeFun()
          }
        } else if (data.type == 'form') {
          let dom = this.$refs[data.tabName + '_form'][0]
          if (data.params.isLazy && !dom.isInit) {
            dom.init()
            return false
          }
          if (data.isRefresh) {
            dom.getFormDataFun()
          }
        } else {
          try {
            let dom = this.$refs[data.tabName][0]
            if (!this.$refs[data.tabName][0].refreshDataFun) {
              return false
            }
            if (data.isRefresh) {
              dom.refreshDataFun()
            }
          } catch (error) {
            console.warn()
            'codetesttabls其他控件刷新异常', error
          }
        }
      }, 300)
    },
  },
  data() {
    return {
      tabsActiveName: '',
      code: '',
      dataTableId: tabsTableId,
      tabsData: [],
      tabsParams: {},
      currActive: false,
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
  },
  created() {
    this.init()
  },
  methods: {
    async init() {
      //获取code
      this.code = this.$route.params.code
      if (!this.code) {
        this.code = this.$route.path.split('views/tool/codetesttabs/')[1]
      }
      //获取tabs配置信息
      let tabsRes = await getDataApi(this.dataTableId, {
        pageNo: 1,
        pageSize: -521,
        tabs_code: this.code,
      })
      let tabsData = tabsRes.data.data
      if (!tabsData.records || tabsData.records.length <= 0) {
        this.$message({
          message: '未获取到相关配置，请检查system_tabs_data表是否有对应的数据',
          type: 'warning',
        })
        return false
      }
      tabsData = JSON.parse(tabsData.records[0].tabs_data)
      let roleArr = this.userInfo.role_name.split(',')
      tabsData = tabsData.filter((item) => {
        if (item.roleName && item.roleName.length > 0) {
          let bool = false
          roleArr.forEach((roleItem) => {
            if (item.roleName.includes(roleItem)) {
              bool = true
            }
          })
          return bool
        }
        if (item.type == 'tabsParams') {
          this.tabsParams = item.params
          return false
        }

        //代理商-充值一览、提现一览、交易流水 特殊处理
        if(['dlsgl_czyl','dlsgl_txyl','dlsgl_jyls'].includes(this.code)){
          
          if(this.userInfo.detail.level>=item.params.searchObj.level){
            return false
          }
        }
        return true
      })
      tabsData = tabsData.map((item) => {
        if (item.type == 'table') {
          if (item.isUserKey) {
            if (item.params.searchObj) {
              item.params.searchObj = {
                ...item.params.searchObj,
                [item.isUserKey]: this.userInfo.user_id,
              }
            } else {
              item.params.searchObj = {
                [item.isUserKey]: this.userInfo.user_id,
              }
            }
          }
        } else if (item.type == 'form') {
          if (item.isUserKey) {
            if (item.params.params) {
              item.params.params = {
                ...item.params.params,
                [item.isUserKey]: this.userInfo.user_id,
              }
            } else {
              item.params.params = {
                [item.isUserKey]: this.userInfo.user_id,
              }
            }
          }
        }
        //是否开启懒加载 默认开启
        if(item.params===undefined){
          item.params={}
        }
        if (item.params.isLazy !== false) {
          item.params.isLazy = true
        }

        if (item.activeName) {
          this.tabsActiveName = item.activeName
          this.currActive = true
          item.params.isLazy = false
        }
        return item
      })
      if (!this.currActive) {
        this.tabsActiveName = tabsData[0].tabName
        tabsData[0].params.isLazy = false
      }
      this.tabsData = tabsData
    },
    //tabs切换触发
    tabsHandleClick() {},
    childTabsHandleClick(){

    },
    formViewFun() {},
  },
  beforeDestroy() {},
}
</script>

<style lang="scss" scoped>
.test-box-list {
  padding: 0 !important;
}
</style>