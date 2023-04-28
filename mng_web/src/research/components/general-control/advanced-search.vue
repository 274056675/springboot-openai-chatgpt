<template>
  <span class="advanced-search">
    <el-tooltip class="item" effect="dark" placement="top" :disabled="!isAdvancedSearch">
      <div slot="content" style="font-size: 14px">
        已有高级查询条件生效&nbsp;&nbsp;|&nbsp;&nbsp;
        <span
          @click="AdvancedSearchEmptyFun"
          style="cursor: pointer; color: #1890ff"
        >清空</span>
      </div>
      <el-button
        size="small"
        type="primary"
        :icon="
                isAdvancedSearch ? 'el-icon-loading' : 'el-icon-takeaway-box'
              "
        @click="isDialog = true"
      >高级查询</el-button>
    </el-tooltip>
    <el-dialog
      v-dialogdrag
      title="高级查询构造器"
      :visible.sync="isDialog"
      class="advanced_search_dialog_box"
      :modal-append-to-body="true"
      :append-to-body="true"
      width="1000px"
    >
      <div class="advanced_search_dialog">
        <div class="conditions_box">
          <div class="conditions_box_item_1">
            <span>过滤条件匹配：</span>
            <el-select v-model="advanceMatchingType" placeholder="请选择" size="medium">
              <el-option
                v-for="item in filtersData"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div
            class="conditions_box_item_2"
            v-for="(conditionsItem, index) in advancedSearchData"
            :key="index"
          >
            <div class="item">
              <el-select
                v-model="conditionsItem.field"
                placeholder="请选择查询字段"
                size="medium"
                @change="conditionsFieldChangeFun(index, conditionsItem.field)"
              >
                <el-option
                  v-for="item in fieldList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </div>
            <div class="item item-rule">
              <el-select v-model="conditionsItem.rule" placeholder="请选择" size="medium">
                <el-option
                  v-for="item in advancedSearchSelectCondition"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </div>
            <!-- 更多控件类型 待遇到时完善 -->
            <div class="item item-val">
              <el-select
                v-model="conditionsItem.val"
                placeholder="请选择"
                size="medium"
                v-if="['select','switch'].includes(conditionsItem.type)"
              >
                <div>
                  <el-option
                    v-for="item in dicAllData[conditionsItem.field]"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </div>
              </el-select>
              <el-date-picker
                v-else-if="conditionsItem.type === 'date'"
                v-model="conditionsItem.val"
                type="date"
                placeholder="请选择日期"
                size="medium"
              ></el-date-picker>
              <el-date-picker
                v-else-if="conditionsItem.type === 'datetime'"
                v-model="conditionsItem.val"
                type="datetime"
                placeholder="请选择日期"
                size="medium"
              ></el-date-picker>
              <el-input
                v-model="conditionsItem.val"
                @keydown.enter.native="advancedSearchBtnFun('query')"
                placeholder="请输入值"
                size="medium"
                v-else
              ></el-input>
            </div>
            <div class="item">
              <el-button icon="el-icon-plus" size="medium" @click="advancedSearchBtnFun('add')"></el-button>
            </div>
            <div class="item">
              <el-button
                icon="el-icon-minus"
                size="medium"
                @click="advancedSearchBtnFun('del', { index })"
              ></el-button>
            </div>
          </div>
        </div>
        <div class="record_box">
          <div class="record_box_title">保存的查询</div>
          <div class="record_box_list">
            <div
              class="record_box_list_item"
              v-for="saveItem in advancedSearchSaveDate"
              :key="saveItem.title"
              @click.stop="advancedSearchSaveListFun('replace', saveItem)"
            >
              <div class="item-left-icon">
                <i class="el-icon-tickets"></i>
              </div>
              <div class="item-name">{{ saveItem.title }}</div>
              <div
                class="item-right-close"
                @click.stop="advancedSearchSaveListFun('del', saveItem)"
              >
                <i class="el-icon-circle-close"></i>
              </div>
            </div>
            <div class="null-list" v-if="advancedSearchSaveDate.length===0">
              <svg
                width="124"
                height="112"
                viewBox="0 0 184 152"
                xmlns="http://www.w3.org/2000/svg"
              >
                <g fill="none" fill-rule="evenodd">
                  <g transform="translate(24 31.67)">
                    <ellipse
                      fill-opacity="0.8"
                      fill="#F5F5F7"
                      cx="67.797"
                      cy="106.89"
                      rx="67.797"
                      ry="12.668"
                    />
                    <path
                      d="M122.034 69.674L98.109 40.229c-1.148-1.386-2.826-2.225-4.593-2.225h-51.44c-1.766 0-3.444.839-4.592 2.225L13.56 69.674v15.383h108.475V69.674z"
                      fill="#AEB8C2"
                    />
                    <path
                      d="M101.537 86.214L80.63 61.102c-1.001-1.207-2.507-1.867-4.048-1.867H31.724c-1.54 0-3.047.66-4.048 1.867L6.769 86.214v13.792h94.768V86.214z"
                      fill="url(#linearGradient-1)"
                      transform="translate(13.56)"
                    />
                    <path
                      d="M33.83 0h67.933a4 4 0 0 1 4 4v93.344a4 4 0 0 1-4 4H33.83a4 4 0 0 1-4-4V4a4 4 0 0 1 4-4z"
                      fill="#F5F5F7"
                    />
                    <path
                      d="M42.678 9.953h50.237a2 2 0 0 1 2 2V36.91a2 2 0 0 1-2 2H42.678a2 2 0 0 1-2-2V11.953a2 2 0 0 1 2-2zM42.94 49.767h49.713a2.262 2.262 0 1 1 0 4.524H42.94a2.262 2.262 0 0 1 0-4.524zM42.94 61.53h49.713a2.262 2.262 0 1 1 0 4.525H42.94a2.262 2.262 0 0 1 0-4.525zM121.813 105.032c-.775 3.071-3.497 5.36-6.735 5.36H20.515c-3.238 0-5.96-2.29-6.734-5.36a7.309 7.309 0 0 1-.222-1.79V69.675h26.318c2.907 0 5.25 2.448 5.25 5.42v.04c0 2.971 2.37 5.37 5.277 5.37h34.785c2.907 0 5.277-2.421 5.277-5.393V75.1c0-2.972 2.343-5.426 5.25-5.426h26.318v33.569c0 .617-.077 1.216-.221 1.789z"
                      fill="#DCE0E6"
                    />
                  </g>
                  <path
                    d="M149.121 33.292l-6.83 2.65a1 1 0 0 1-1.317-1.23l1.937-6.207c-2.589-2.944-4.109-6.534-4.109-10.408C138.802 8.102 148.92 0 161.402 0 173.881 0 184 8.102 184 18.097c0 9.995-10.118 18.097-22.599 18.097-4.528 0-8.744-1.066-12.28-2.902z"
                    fill="#DCE0E6"
                  />
                  <g transform="translate(149.65 15.383)" fill="#FFF">
                    <ellipse cx="20.654" cy="3.167" rx="2.849" ry="2.815" />
                    <path d="M5.698 5.63H0L2.898.704zM9.259.704h4.985V5.63H9.259z" />
                  </g>
                </g>
              </svg>
              <div class="text">没有保存任何查询</div>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <div class="btn-left">
          <el-button @click="advancedSearchBtnFun('reset')" size="small">重 置</el-button>
          <el-button @click="advancedSearchBtnFun('save')" size="small">保存查询条件</el-button>
        </div>
        <div class="btn-right">
          <el-button @click="isDialog = false" size="small">关 闭</el-button>
          <el-button type="primary" @click="advancedSearchBtnFun('query')" size="small">查 询</el-button>
        </div>
      </div>
    </el-dialog>
  </span>
</template>

<script>
export default {
  props: ['fieldList', 'storageKey', 'dicAllData'],
  watch: {
    isDialog(value) {
      if (value) {
        //重新获取本地缓存
        let localStorage = window.localStorage.getItem(this.storageKey)
        this.advancedSearchSaveDate = localStorage
          ? JSON.parse(localStorage)
          : []
      }
    },
  },
  data() {
    return {
      isDialog: false,
      isAdvancedSearch: false,
      filtersData: [
        {
          label: 'AND（所有条件都要求匹配） ',
          value: 'and',
        },
        {
          label: 'OR（条件中的任意一个匹配）',
          value: 'or',
        },
      ],
      advancedSearchData: [
        {
          type: 'input', //控件类型
          rule: 'eq', //条件
          val: '', //条件值
          field: '', //查询类型 fieldList
        },
      ],
      advancedSearchSelectCondition: [
        {
          label: '等于',
          value: 'eq',
        },
        {
          label: '包含',
          value: 'like',
        },
        {
          label: '以..开始',
          value: 'right_like',
        },
        {
          label: '以..结尾',
          value: 'left_like',
        },
        {
          label: '在..中',
          value: 'in',
        },
        {
          label: '不等于',
          value: 'ne',
        },
        {
          label: '大于',
          value: 'gt',
        },
        {
          label: '大于等于',
          value: 'ge',
        },
        {
          label: '小于',
          value: 'lt',
        },
        {
          label: '小于等于',
          value: 'le',
        },
      ],
      advanceMatchingType: 'and',
      advancedSearchSaveDate: [],
    }
  },
  methods: {
    //高级查询按钮操作 (重置：reset 保存查询：save 查询：query 添加：add 删除：del)
    advancedSearchBtnFun(type, data) {
      if (type === 'reset') {
        this.advancedSearchData = [
          {
            type: 'input', //控件类型
            rule: 'eq', //条件
            val: '', //条件值
            field: '', //查询类型 fieldList
          },
        ]
      }
      if (type === 'save') {
        this.$prompt('请输入保存的名称', '', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /\S/,
          inputErrorMessage: '名称不能为空',
        })
          .then(({ value }) => {
            let localStorage = window.localStorage.getItem(this.storageKey)
            localStorage = localStorage ? JSON.parse(localStorage) : []
            let data = []
            let replaceIndex = ''
            localStorage.forEach((item, index) => {
              if (item.title === value) {
                replaceIndex = index
              }
            })
            if (replaceIndex !== '') {
              this.$confirm(`${value} 已存在，是否覆盖？`, {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
              })
                .then(() => {
                  localStorage[replaceIndex] = {
                    title: value,
                    matchType: this.advanceMatchingType,
                    records: this.advancedSearchData,
                  }
                  data = localStorage
                  window.localStorage.setItem(
                    this.storageKey,
                    JSON.stringify(data)
                  )
                  this.advancedSearchSaveDate = data
                  this.$message({
                    message: '保存成功',
                    type: 'success',
                  })
                })
                .catch(() => {})
            } else {
              data = [
                ...localStorage,
                {
                  title: value,
                  matchType: this.advanceMatchingType,
                  records: this.advancedSearchData,
                },
              ]
              window.localStorage.setItem(this.storageKey, JSON.stringify(data))
              this.advancedSearchSaveDate = data
              this.$message({
                message: '保存成功',
                type: 'success',
              })
            }
          })
          .catch(() => {})
      }
      if (type === 'query') {
        let bool = true
        this.advancedSearchData.forEach((item) => {
          if (item.val === '' || item.field === '') {
            bool = false
          }
        })
        if (bool == false) {
          this.$message({
            message: '查询条件不能为空',
            type: 'warning',
          })
          return false
        }
        let data = {
          superQueryParams: JSON.stringify(this.advancedSearchData),
          superQueryMatchType: this.advanceMatchingType,
        }
        this.isAdvancedSearch = true
        this.$emit('set-advanced-search', data)
        this.isDialog = false
      }
      if (type === 'add') {
        this.advancedSearchData.push({
          type: 'input', //控件类型
          rule: 'eq', //条件
          val: '', //条件值
          field: '', //查询类型 fieldList
        })
      }
      if (type === 'del') {
        if (this.advancedSearchData.length === 1) {
          this.advancedSearchData = [
            {
              type: 'input', //控件类型
              rule: 'eq', //条件
              val: '', //条件值
              field: '', //查询类型 fieldList
            },
          ]
          return false
        }
        this.advancedSearchData = this.advancedSearchData.filter(
          (item, index) => {
            if (index == data.index) {
              return false
            }
            return true
          }
        )
      }
    },
    //高级查询 选择查询字段类型
    conditionsFieldChangeFun(index, fieldName) {
      console.log('==========>', this.fieldList, index, fieldName)
      this.fieldList.forEach((item) => {
        if (item.value == fieldName) {
          this.advancedSearchData[index].val = ''
          this.advancedSearchData[index].type = item.type
        }
      })
    },
    //高级查询 保存的查询列表操作
    advancedSearchSaveListFun(type, data) {
      if (type === 'replace') {
        this.advanceMatchingType = data.matchType
        this.advancedSearchData = data.records
      }
      if (type === 'del') {
        this.$confirm('是否删除当前查询？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.advancedSearchSaveDate = this.advancedSearchSaveDate.filter(
              (item) => {
                if (item.title == data.title) {
                  return false
                }
                return true
              }
            )
            window.localStorage.setItem(
              this.storageKey,
              JSON.stringify(this.advancedSearchSaveDate)
            )
          })
          .catch(() => {})
      }
    },
    //清空当前的高级查询
    AdvancedSearchEmptyFun() {
      this.isAdvancedSearch = false
      this.advancedSearchData = [
        {
          type: 'input', //控件类型
          rule: 'eq', //条件
          val: '', //条件值
          field: '', //查询类型 fieldList
        },
      ]
      this.advanceMatchingType = 'and'
      //重新执行搜索
      this.$emit('reset-advanced-search')
    },
  },
}
</script>

<style lang="scss">
.advanced_search_dialog_box {
  .dialog-footer {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
.advanced_search_dialog {
  display: flex;
  .conditions_box_item_1 {
    .el-select {
      width: 300px;
    }
  }
  .conditions_box_item_2 {
    display: flex;
    align-items: center;
    padding-top: 20px;
    margin-right: 60px;
    .item {
      padding-right: 10px;
      .el-button {
        padding: 10px 12px;
      }
      .el-select {
        width: 220px;
      }
    }
    .item-rule {
      .el-select {
        width: 100px;
      }
    }
    .item-val {
      .el-input {
        width: 220px;
      }
    }
  }
  .record_box {
    border: 1px solid #e8e8e8;
    width: 200px;
    height: 100%;
    box-sizing: border-box;
    .record_box_title {
      border-bottom: 1px solid #e8e8e8;
      font-size: 16px;
      padding: 5px 0 5px 10px;
    }
    .record_box_list {
      padding: 5px 0;
    }
    .record_box_list_item {
      height: 24px;
      margin: 4px 0;
      display: flex;
      align-items: center;
      cursor: pointer;
      .item-left-icon {
        padding: 0 5px;
      }
      .item-name {
        flex: 1;
      }
      .item-right-close {
        font-size: 16px;
        padding: 0 5px;
        opacity: 0;
        visibility: hidden;
        transition: all 0.3s;
      }
      &:hover {
        background-color: #e6f7ff;
        .item-right-close {
          visibility: visible;
          opacity: 1;
        }
      }
    }
    .null-list {
      text-align: center;
      .text {
        color: #999;
      }
    }
  }
}
</style>