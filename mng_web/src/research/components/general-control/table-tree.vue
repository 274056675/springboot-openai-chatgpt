<template>
  <div class="table-tree-box">
    <el-dialog
      v-dialogdrag
      :title="optionData.title"
      :visible.sync="optionData.isDialog"
      :destroy-on-close="optionData.destroy?true:false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :append-to-body="true"
      :before-close="handleClose"
      width="500px"
      v-loading="loading"
    >
      <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
      <el-tree
        ref="elTree"
        :data="treeData"
        show-checkbox
        default-expand-all
        :default-checked-keys="optionData.defaultTree"
        :node-key="optionData.defaulKey"
        :filter-node-method="filterNode"
        :check-strictly="optionData.checkStrictly"
        highlight-current
        :props="treeProps"
        @check-change="parentModules"
      ></el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setDialog(false)">取 消</el-button>
        <el-button type="primary" @click="getCurrSelectTreeFun()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getAllTreeDataApi,
  getDataApi,
  getActionApi,
  postActionApi,
} from '@/api/research/codelist'
export default {
  data() {
    return {
      filterText: '',
      treeData: [],
      treeProps: {},
      uniqueValue: '',
      loading: false,
      getActionApi,
      postActionApi,
    }
  },
  watch: {
    filterText(val) {
      this.$refs.elTree.filter(val)
    },
    optionData: {
      handler(newVal, oldVal) {
        if (
          oldVal == undefined ||
          (oldVal && (newVal.tableId != oldVal.tableId || newVal.isRefresh))
        ) {
          this.getTableDataFun(newVal.tableId)
        }
        if (oldVal == undefined) {
          //多选
          this.treeProps.disabled = this.stopTreeFun
          this.treeProps = {
            ...this.treeProps,
            ...newVal.defaultProps,
          }
        }
      },
      immediate: true, //先执行一次handler
      deep: true,
    },
  },
  props: [
    'optionData',
    /* 'tableId', //表单开发id
    apiName:'',//接口名
    'defaultTree', //默认勾选的值
    'stopTree',//禁用勾选值
    'isDialog', //是否显示
    'defaultProps', //显示字段
    'defaulKey', //树绑定key
    'title', //标题
    'addType',//新增方式(对象)  {type:新增类型,tableId:新增表单id}
    'asyncTableName',//存储同步表id的字段名
    asyncTableIdName:'同步表的数据唯一id字段名'
    radio:true, //单选
    */
    'treeControlFun',
  ],
  methods: {
    //节点过滤
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    //关闭弹窗
    setDialog(bool) {
      this.$refs.elTree.setCheckedKeys([])
      this.treeControlFun('dialog', { bool })
    },
    //获取当前tree数据
    getTableDataFun(tableId) {
      //通过接口获取所有树表格数据
      if (this.optionData.apiName == 'getData') {
        getDataApi(tableId, { pageSzie: -521, pageNo: 1 }).then((res) => {
          
          if (res.data.success) {
            this.treeData = res.data.data.records
          }
        })
      } else if (this.optionData.apiName == 'getTreeData') {
        getAllTreeDataApi(tableId).then((res) => {
          
          if (res.data.success) {
            this.treeData = res.data.data
          }
        })
      } else if (this.optionData.apiName == 'externalData') {
        this.treeData = this.optionData.treeData
      }
    },
    //获取当前选择的数据
    async getCurrSelectTreeFun() {
      let checkArr = this.$refs.elTree.getCheckedNodes(true)
      
      //排除禁用节点
      checkArr = checkArr.filter((item) => {
        if (
          this.optionData.stopTree.includes(item[this.optionData.defaulKey])
        ) {
          return false
        }
        return true
      })
      if (checkArr.length <= 0) {
        this.$message('请勾选需要添加的数据~')
        return false
      }
      
      let tableArr = []
      checkArr.forEach((item) => {
        let obj = {}
        if (this.optionData.asyncTableIdName) {
          if (this.optionData.apiName == 'getData') {
            obj[this.optionData.asyncTableName] =
              item[this.optionData.asyncTableIdName]
          } else {
            obj[this.optionData.asyncTableName] =
              item.data[this.optionData.asyncTableIdName]
          }
        } else if (this.optionData.asyncTableName) {
          if (this.optionData.apiName == 'getData') {
            obj[this.optionData.asyncTableName] = item.id
          } else {
            obj[this.optionData.asyncTableName] = item.data.id
          }
        }
        let dataKey = []
        if (this.optionData.apiName == 'getData') {
          dataKey = Object.keys(item)
        } else {
          dataKey = Object.keys(item.data)
        }

        dataKey.forEach((key) => {
          if (key != 'id') {
            if (this.optionData.apiName == 'getData') {
              obj[key] = item[key]
            } else {
              obj[key] = item.data[key]
            }
          }
        })
        if (this.optionData.addType.isCell) {
          obj.$cellEdit = true
        }
        tableArr.push(obj)
      })
      this.treeControlFun(this.optionData.addType.type, {
        data: tableArr,
        tableId: this.optionData.addType.tableId,
      })
      this.$refs.elTree.setCheckedKeys([])
    },
    //设置禁用节点
    stopTreeFun(data) {
      let stopKey = this.optionData.stopDefaulKey
      stopKey = stopKey ? stopKey : this.optionData.defaulKey
      if (data.disabled) {
        return true
      }
      return this.optionData.stopTree.includes(data[stopKey])
    },
    handleClose(done) {
      this.$refs.elTree.setCheckedKeys([])
      done()
    },
    //单选方法
    parentModules(data, checkbox) {
      if (this.optionData.radio) {
        if (checkbox) {
          this.$refs.elTree.setCheckedKeys([data.id])
          this.uniqueValue = this.$refs.elTree.getCheckedKeys().toString()
        } else {
          this.uniqueValue = this.$refs.elTree.getCheckedKeys().toString()
          if (this.uniqueValue.length == 0) {
            this.uniqueValue = ''
          }
        }
      }
    },
  },
}
</script>

<style></style>
