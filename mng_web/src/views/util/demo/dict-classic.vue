<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      ref="crud"
      v-model="form"
      :permission="permissionList"
      :before-open="beforeOpen"
      :before-close="beforeClose"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot="menuLeft">
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          v-if="permission.dict_delete"
          plain
          @click="handleDelete"
        >删 除
        </el-button>
      </template>
      <template slot-scope="{row}" slot="isSealed">
        <el-tag>{{row.isSealed===0?'否':'是'}}</el-tag>
      </template>
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          icon="el-icon-check"
          size="small"
          @click.stop="handleAdd(scope.row,scope.index)"
        >新增子项
        </el-button>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
  import {
    getList,
    remove,
    update,
    add,
    getDict,
    getDictTree
  } from "@/api/system/dict";
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        form: {},
        selectionList: [],
        query: {},
        loading: true,
        page: {
          pageSize: 10,
          currentPage: 1,
          total: 0
        },
        option: {
          tip: false,
          searchShow: true,
          searchMenuSpan: 6,
          tree: true,
          border: true,
          index: true,
          selection: true,
          viewBtn: true,
          menuWidth: 300,
          dialogWidth: 880,
          column: [
            {
              label: "字典编号",
              prop: "code",
              search: true,
              span: 24,
              rules: [
                {
                  required: true,
                  message: "请输入字典编号",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "字典名称",
              prop: "dictValue",
              search: true,
              align: "center",
              rules: [
                {
                  required: true,
                  message: "请输入字典名称",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "上级字典",
              prop: "parentId",
              type: "tree",
              dicData: [],
              hide: true,
              props: {
                label: "title"
              },
              rules: [
                {
                  required: false,
                  message: "请选择上级字典",
                  trigger: "click"
                }
              ]
            },
            {
              label: "字典键值",
              prop: "dictKey",
              type: "number",
              rules: [
                {
                  required: true,
                  message: "请输入字典键值",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "字典排序",
              prop: "sort",
              type: "number",
              rules: [
                {
                  required: true,
                  message: "请输入字典排序",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "封存",
              prop: "isSealed",
              type: "select",
              dicData: [
                {
                  label: "否",
                  value: 0
                },
                {
                  label: "是",
                  value: 1
                }
              ],
              slot: true,
              rules: [
                {
                  required: true,
                  message: "请选择封存",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "字典备注",
              prop: "remark",
              search: true,
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
          addBtn: this.vaildData(this.permission.dict_add, false),
          viewBtn: this.vaildData(this.permission.dict_view, false),
          delBtn: this.vaildData(this.permission.dict_delete, false),
          editBtn: this.vaildData(this.permission.dict_edit, false)
        };
      },
      ids() {
        let ids = [];
        this.selectionList.forEach(ele => {
          ids.push(ele.id);
        });
        return ids.join(",");
      }
    },
    mounted() {
      getDictTree().then(res => {
        const column = this.findObject(this.optionChild.column, "parentId");
        column.dicData = res.data.data;
      });
    },
    methods: {
      handleAdd(row) {
        this.$refs.crud.value.code = row.code;
        this.$refs.crud.value.parentId = row.id;
        this.$refs.crud.option.column.filter(item => {
          if (item.prop === "code") {
            item.value = row.code;
            item.addDisabled = true;
          }
          if (item.prop === "parentId") {
            item.value = row.id;
            item.addDisabled = true;
          }
        });
        this.$refs.crud.rowAdd();
      },
      rowSave(row, done, loading) {
        add(row).then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          done();
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowUpdate(row, index, done, loading) {
        update(row).then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          done();
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowDel(row) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(row.id);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          });
      },
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
      selectionChange(list) {
        this.selectionList = list;
      },
      selectionClear() {
        this.selectionList = [];
        this.$refs.crud.toggleSelection();
      },
      handleDelete() {
        if (this.selectionList.length === 0) {
          this.$message.warning("请选择至少一条数据");
          return;
        }
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(this.ids);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.$refs.crud.toggleSelection();
          });
      },
      beforeOpen(done, type) {
        if (["edit", "view"].includes(type)) {
          getDict(this.form.id).then(res => {
            this.form = res.data.data;
          });
        }
        done();
      },
      beforeClose(done) {
        this.$refs.crud.tableForm = {};
        this.$refs.crud.value.code = "";
        this.$refs.crud.value.parentId = "";
        this.$refs.crud.value.addDisabled = false;
        this.$refs.crud.option.column.filter(item => {
          if (item.prop === "code") {
            item.value = "";
            item.addDisabled = false;
          }
          if (item.prop === "parentId") {
            item.value = "";
            item.addDisabled = false;
          }
        });
        done();
      },
      currentChange(currentPage) {
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize) {
        this.page.pageSize = pageSize;
      },
      refreshChange() {
        this.onLoad(this.page, this.query);
      },
      onLoad(page, params = {}) {
        this.loading = true;
        getList(
          page.currentPage,
          page.pageSize,
          Object.assign(params, this.query)
        ).then(res => {
          this.data = res.data.data;
          this.loading = false;
          this.selectionClear();
        });
      }
    }
  };
</script>

<style>
</style>
