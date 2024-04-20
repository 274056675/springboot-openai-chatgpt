<template>
  <basic-container>
    <avue-crud :option="option"
               :data="data"
               ref="crud"
               v-model="form"
               :permission="permissionList"
               :page="page"
               :before-open="beforeOpen"
               @search-change="searchChange"
               @search-reset="searchReset"
               @current-change="currentChange"
               @size-change="sizeChange"
               @on-load="onLoad">
    </avue-crud>
  </basic-container>
</template>

<script>
  import {getUsualList, getUsualLogs} from "@/api/logs";
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        form: {},
        selectionList: [],
        query: {},
        page: {
          pageSize: 10,
          currentPage: 1,
          total: 0
        },
        option: {
          height: 'auto',
          calcHeight: 210,
          searchShow: true,
          searchMenuSpan: 6,
          tip: false,
          border: true,
          index: true,
          viewBtn: true,
          editBtn: false,
          addBtn: false,
          delBtn: false,
          menuWidth: 120,
          dialogType: 'drawer',
          column: [
            {
              label: "服务id",
              prop: "serviceId",
              search: true
            },
            {
              label: "服务host",
              prop: "serverHost",
              search: true
            },
            {
              label: "服务ip",
              prop: "serverIp"
            },
            {
              label: "软件环境",
              prop: "env"
            },
            {
              label: "日志级别",
              prop: "logLevel"
            },
            {
              label: "日志id",
              prop: "logId"
            },
            {
              label: "请求接口",
              prop: "requestUri"
            },
            {
              label: "日志时间",
              prop: "logData"
            },
            {
              label: "用户代理",
              prop: "userAgent",
              span: 24,
              hide: true
            },
            {
              label: "请求数据",
              prop: "params",
              type: "textarea",
              span: 24,
              minRows: 2,
              hide: true
            }
          ]
        },
        data: []
      };
    },
    computed: {
      ...mapGetters(["permission"]),
      permissionList() {
        return {
          viewBtn: this.vaildData(this.permission.log_usual_view, false)
        };
      }
    },
    methods: {
      searchReset() {
        this.query = {};
        this.onLoad(this.page);
      },
      searchChange(params, done) {
        this.query = params;
        this.page.currentPage = 1;
        this.onLoad(this.page, params);
        done();
      },
      beforeOpen(done, type) {
        if (["edit", "view"].includes(type)) {
          getUsualLogs(this.form.strId).then(res => {
            this.form = res.data.data;
          });
        }
        done();
      },
      currentChange(currentPage){
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize){
        this.page.pageSize = pageSize;
      },
      onLoad(page, params = {}) {
        getUsualList(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
          const data = res.data.data;
          this.page.total = data.total;
          this.data = data.records;
        });
      }
    }
  };
</script>

<style>
</style>
