<template>
  <avue-crud :data="logsList"
             :option="option">
    <template slot="menuLeft">
      <el-button type="primary"
                 size="small"
                 icon="el-icon-upload"
                 @click="send">上传服务器</el-button>
      <el-button type="danger"
                 size="small"
                 icon="el-icon-delete"
                 @click="clear">清空本地日志</el-button>
    </template>
    <template slot-scope="scope"
              slot="type">
      <el-tag type="danger"
              size="small">{{scope.label}}</el-tag>
    </template>
    <template slot-scope="props"
              slot="expand">
      <pre class="code">
        {{props.row.stack}}
      </pre>
    </template>
  </avue-crud>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "errLogs",
  data() {
    return {
      option: {
        menu: false,
        addBtn: false,
        page: false,
        border: true,
        expand: true,
        refreshBtn: false,
        headerAlign: "center",
        column: [
          {
            label: "类型",
            prop: "type",
            width: 80,
            align: "center",
            slot: true,
            dicData: [
              {
                label: "bug",
                value: "error"
              }
            ]
          },
          {
            label: "地址",
            width: 200,
            prop: "url",
            overHidden: true
          },
          {
            label: "内容",
            prop: "message",
            overHidden: true
          },
          {
            label: "错误堆栈",
            prop: "stack",
            hide: true
          },
          {
            label: "时间",
            align: "center",
            prop: "time",
            width: 200
          }
        ]
      }
    };
  },
  created() {},
  mounted() {},
  computed: {
    ...mapGetters(["logsList"])
  },
  props: [],
  methods: {
    send() {
      this.$confirm("确定上传本地日志到服务器?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$store.dispatch("SendLogs").then(() => {
            this.$parent.$parent.box = false;
            this.$message({
              type: "success",
              message: "发送成功!"
            });
          });
        })
        .catch(() => {});
    },
    clear() {
      this.$confirm("确定清空本地日志记录?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$store.commit("CLEAR_LOGS");
          window.console.log(this);
          this.$parent.$parent.box = false;
          this.$message({
            type: "success",
            message: "清空成功!"
          });
        })
        .catch(() => {});
    }
  }
};
</script>

<style lang="scss" scoped>
.code {
  font-size: 12px;
  display: block;
  font-family: monospace;
  white-space: pre;
  margin: 1em 0px;
}
</style>
