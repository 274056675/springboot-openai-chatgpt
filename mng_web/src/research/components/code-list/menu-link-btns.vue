<template>
  <!-- 表单开发操作列按钮 -->
  <div class="menu-btns-box">
    <div class="menu-btn-list" v-if="!that.isLinkPullDown && !that.isOperationNullFun(scope.row)">
      <span
        class="btn-span"
        :class="{ 'btn-span-null': that.customButtonLink.length <= 0 }"
        v-for="item in that.customButtonLink"
        v-show="scope.row['$link$' + item.buttonCode]"
        :key="item.id"
      >
        <!-- 自定义按钮 -->
        <el-button
          v-if="scope.row['$link$' + item.buttonCode]"
          :icon="item.buttonIcon"
          @click="
                  that.moreButtonCommand({
                    type: item.buttonCode,
                    row: scope.row,
                    index: scope.index,
                    buttonCode: item.buttonCode,
                    buttonStyle: item.buttonStyle,
                    optType: item.optType,
                    that
                  })
                "
          type="text"
          size="small"
        >{{ item.buttonName }}</el-button>
      </span>
      <span class="btn-span" v-if="that.tablePermission.editBtn">
        <el-button
          type="text"
          size="small"
          @click.stop="that.operationRowFun(scope.row, scope.index, 'edit')"
        >
          {{
          that.tableOption.editBtnText ? that.tableOption.editBtnText : "编辑"
          }}
        </el-button>
      </span>
      <span
        class="btn-span"
        v-for="item in that.tableColumnMoreButton"
        :key="item.type"
        v-show="that.tablePermission[item.permissionName]"
      >
        <el-button
          v-if="that.tablePermission[item.permissionName]"
          :icon="item.buttonIcon"
          @click="
                  that.moreButtonCommand({
                    type: item.type,
                    row: scope.row,
                    index: scope.index
                  })
                "
          type="text"
          size="small"
        >{{ item.text }}</el-button>
      </span>
    </div>
    <el-button
      type="text"
      icon="el-icon-edit"
      size="small"
      @click.stop="that.operationRowFun(scope.row, scope.index, 'edit')"
      v-if="that.tablePermission.editBtn&&that.isLinkPullDown"
    >
      {{
      that.tableOption.editBtnText ? that.tableOption.editBtnText : "编辑"
      }}
    </el-button>
    <el-dropdown
      class="code-test-list-menu-more-button"
      @command="that.moreButtonCommand"
      v-if="isOperationMore"
    >
      <span class="el-dropdown-link">
        更多
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <div
          v-for="item in that.customButtonLink"
          :key="item.id"
          v-show="scope.row['$link$' + item.buttonCode]"
        >
          <el-dropdown-item
            v-if="scope.row['$link$' + item.buttonCode]"
            :command="{
                    type: item.buttonCode,
                    row: scope.row,
                    index: scope.index,
                    buttonCode: item.buttonCode,
                    buttonStyle: item.buttonStyle,
                    optType: item.optType,
                    that
                  }"
          >
            <i v-if="item.buttonIcon" :class="item.buttonIcon"></i>
            {{ item.buttonName }}
          </el-dropdown-item>
        </div>
        <div v-for="item in that.tableColumnMoreButton" :key="item.type">
          <el-dropdown-item
            :command="{
                    type: item.type,
                    row: scope.row,
                    index: scope.index
                  }"
            v-if="that.tablePermission[item.permissionName]"
          >{{ item.text }}</el-dropdown-item>
        </div>
      </el-dropdown-menu>
    </el-dropdown>
    <el-button type="text" size="small" v-if="that.isOperationNullFun(scope.row)">-</el-button>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  props: {
    scope: Object,
    that: Object,
  },
  computed: {
    ...mapGetters(['permission']),
    // 操作列 更多 是否显示
    isOperationMore() {
      if (!this.that.isLinkPullDown) {
        return false
      }
      if (
        this.that.tablePermission.moreViewBtn ||
        this.that.tablePermission.moreDelBtn
      ) {
        return true
      }
      let bool = false
      this.that.customButtonLink.forEach((item) => {
        if (this.that.isAuthBtn) {
          if (
            this.permission[
              `${item.buttonCode}_${this.currCodeId}${this.currCodeType}`
            ] &&
            this.scope.row['$link$' + item.buttonCode]
          ) {
            bool = true
          }
        } else {
          if (this.scope.row['$link$' + item.buttonCode]) {
            bool = true
          }
        }
      })
      return bool
    },
  },
}
</script>

<style lang="scss" scoped>
.menu-btns-box {
  width: 100%;
  display: inline;
  .menu-btn-list {
    .btn-span {
      padding-right: 5px;
    }
    .btn-span:nth-last-of-type(1) {
      padding-right: 0px;
    }
    .btn-span-null {
      padding-right: 0px;
    }
  }
  .code-test-list-menu-more-button {
    font-size: 12px;
    .el-dropdown-link {
      color: #409eff;
      margin-left: 10px;
      i {
        margin-left: 0;
      }
    }
  }
}
</style>