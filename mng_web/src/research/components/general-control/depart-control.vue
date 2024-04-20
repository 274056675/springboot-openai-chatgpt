<template>
  <div class="depart-control">
    <div class="depart-control-box" :class="{ 'depart-control-box-disabled': disabled }">
      <!-- <div class="depart-control-icon">
        <svg
          viewBox="64 64 896 896"
          data-icon="cluster"
          width="1em"
          height="1em"
          fill="currentColor"
          aria-hidden="true"
          focusable="false"
          class
        >
          <path
            d="M888 680h-54V540H546v-92h238c8.8 0 16-7.2 16-16V168c0-8.8-7.2-16-16-16H240c-8.8 0-16 7.2-16 16v264c0 8.8 7.2 16 16 16h238v92H190v140h-54c-4.4 0-8 3.6-8 8v176c0 4.4 3.6 8 8 8h176c4.4 0 8-3.6 8-8V688c0-4.4-3.6-8-8-8h-54v-72h220v72h-54c-4.4 0-8 3.6-8 8v176c0 4.4 3.6 8 8 8h176c4.4 0 8-3.6 8-8V688c0-4.4-3.6-8-8-8h-54v-72h220v72h-54c-4.4 0-8 3.6-8 8v176c0 4.4 3.6 8 8 8h176c4.4 0 8-3.6 8-8V688c0-4.4-3.6-8-8-8zM256 805.3c0 1.5-1.2 2.7-2.7 2.7h-58.7c-1.5 0-2.7-1.2-2.7-2.7v-58.7c0-1.5 1.2-2.7 2.7-2.7h58.7c1.5 0 2.7 1.2 2.7 2.7v58.7zm288 0c0 1.5-1.2 2.7-2.7 2.7h-58.7c-1.5 0-2.7-1.2-2.7-2.7v-58.7c0-1.5 1.2-2.7 2.7-2.7h58.7c1.5 0 2.7 1.2 2.7 2.7v58.7zM288 384V216h448v168H288zm544 421.3c0 1.5-1.2 2.7-2.7 2.7h-58.7c-1.5 0-2.7-1.2-2.7-2.7v-58.7c0-1.5 1.2-2.7 2.7-2.7h58.7c1.5 0 2.7 1.2 2.7 2.7v58.7zM360 300a40 40 0 1 0 80 0 40 40 0 1 0-80 0z"
          />
        </svg>
      </div>-->
      <avue-input-tree
        :key="treeKey"
        v-model="tableItemVal"
        placeholder="请选择 部门"
        type="tree"
        :multiple="multiple"
        :checkStrictly="true"
        :dic="allDepartData"
        :props="departProps"
        :size="tableItemScope ? tableItemScope.size : ''"
        :disabled="true"
        @click="openDepartDialogFun(tableItemVal, tableItemName,!disabled)"
      ></avue-input-tree>
    </div>
    <el-dialog
      v-dialogdrag
      :title="this.disabled ? '部门' : '选择部门'"
      :visible.sync="departDialog"
      class="depart_dialog_box"
      :modal-append-to-body="false"
      :append-to-body="true"
      width="450px"
    >
      <div style="margin-bottom: 5px">
        <el-input placeholder="输入部门名称进行搜索" v-model="filterText"></el-input>
      </div>
      <el-tree
        ref="departTree"
        :props="departProps"
        show-checkbox
        :check-strictly="true"
        :default-expand-all="true"
        node-key="id"
        :data="allDepartData"
        :filter-node-method="filterNode"
        @check-change="handleClick"
        @node-click="nodeClick"
      ></el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="departDialog = false">取 消</el-button>
        <el-button type="primary" @click="setDepartInputValueFun">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getDeptTree } from '@/api/system/dept'
export default {
  props: [
    'tableItemVal',
    'tableItemName',
    'disabled',
    'tableItemScope',
    'setFormValueFun',
    'multiple',
  ],
  data() {
    return {
      filterText: '',
      allDepartData: [],
      departProps: {
        children: 'children',
        label: 'title',
        value: 'id',
      },
      departDialog: false,
    }
  },
  watch: {
    filterText(val) {
      this.$refs.departTree.filter(val)
    },
  },
  computed: {
    treeKey() {
      if (this.tableItemVal && this.tableItemVal instanceof Array) {
        return this.tableItemVal.join('')
      }
      return 0
    },
  },
  mounted() {
    
    //获取部门数据
    getDeptTree().then((res) => {
      this.allDepartData = res.data.data
    })
    // 禁用
    if (this.disabled) {
      this.departProps.disabled = () => {
        return true
      }
    }
  },
  methods: {
    //单选逻辑
    handleClick(data, checked) {
      if (checked == true && !this.multiple) {
        this.$refs.departTree.setCheckedNodes([data])
      }
    },
    nodeClick(data) {
      if (!this.multiple) {
        this.$refs.departTree.setCheckedNodes([data])
      }
    },
    // 过滤
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    //打开部门选择弹窗
    openDepartDialogFun(value, fieldName, bool) {
      if (!bool) {
        return false
      }
      
      if (!(value instanceof Array)) {
        if (value && typeof value == 'string') {
          value = value.split(',')
        } else {
          value = []
        }
      }
      this.departDialog = true
      setTimeout(() => {
        this.$refs.departTree.setCheckedKeys(value)
      }, 0)
      this.setParentFormValFun({
        fieldName,
        value,
      })
    },
    //设置部门控件值
    setDepartInputValueFun() {
      this.setParentFormValFun({
        fieldName: this.tableItemName,
        value: [],
      })
      setTimeout(() => {
        this.setParentFormValFun({
          fieldName: this.tableItemName,
          value: this.$refs.departTree.getCheckedKeys(),
        })
        this.departDialog = false
      }, 0)
    },
    //调用父组件设置表单值方法{fieldName:'',value:''}
    setParentFormValFun(obj) {
      if (obj.value && obj.value instanceof Array) {
        obj.value = obj.value.join(',')
      } else {
        obj.value = ''
      }
      if (this.setFormValueFun) {
        this.setFormValueFun(obj)
      }
      this.$emit('set-form-val', obj)
    },
  },
}
</script>

<style lang="scss">
.depart_dialog_box {
  .el-dialog__header {
    border-bottom: 1px solid #f1f1f1;
  }
}
.depart-control-box {
  .el-select__tags {
    // padding-left: 22px;
  }
  input::-webkit-input-placeholder {
    opacity: 1 !important;
  }
  input::-moz-placeholder {
    /* Mozilla Firefox 19+ */
    opacity: 1 !important;
  }
  input:-moz-placeholder {
    /* Mozilla Firefox 4 to 18 */
    opacity: 1 !important;
  }
  input:-ms-input-placeholder {
    /* Internet Explorer 10-11 */
    opacity: 1 !important;
  }
  input {
    background-color: #fff !important;
    cursor: pointer !important;
    color: #565c69 !important;
    // padding-left: 28px !important;
    // padding-right: 15px !important;
  }
  .el-input__suffix {
    cursor: pointer;
    display: none;
  }
  .depart-control-icon {
    position: absolute;
    left: 8px;
    top: 50%;
    transform: translateY(-50%);
    margin-top: 2px;
    z-index: 999;
  }
}
.depart-control-box-disabled {
  cursor: pointer !important;
  input::-webkit-input-placeholder {
    opacity: 0 !important;
  }
  input::-moz-placeholder {
    /* Mozilla Firefox 19+ */
    opacity: 0 !important;
  }
  input:-moz-placeholder {
    /* Mozilla Firefox 4 to 18 */
    opacity: 0 !important;
  }
  input:-ms-input-placeholder {
    /* Internet Explorer 10-11 */
    opacity: 0 !important;
  }
  input {
    background-color: #f5f7fa !important;
  }
}
.avue--detail {
  .depart-control-box-disabled {
    input {
      background-color: #fff !important;
    }
  }
}
</style>
