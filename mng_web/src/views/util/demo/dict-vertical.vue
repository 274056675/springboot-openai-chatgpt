<template>
  <div>
    <basic-container>
      <el-row>
        <el-col :span="24">
          <avue-crud
            :option="option"
            :table-loading="loading"
            :data="dataParent"
            :page="pageParent"
            ref="crud"
            v-model="formParent"
            :permission="permissionList"
            :before-open="beforeOpen"
            @row-del="rowDel"
            @row-update="rowUpdate"
            @row-save="rowSave"
            @row-click="handleRowClick"
            @search-change="searchChange"
            @search-reset="searchReset"
            @selection-change="selectionChange"
            @current-change="currentChange"
            @size-change="sizeChange"
            @refresh-change="refreshChange"
            @on-load="onLoadParent"
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
          </avue-crud>
        </el-col>
      </el-row>
    </basic-container>
    <basic-container>
      <el-row>
        <el-col :span="24">
          <avue-crud
            :option="option"
            :table-loading="loading"
            :data="dataChild"
            :page="pageChild"
            ref="crudChild"
            v-model="formChild"
            :permission="permissionList"
            :before-open="beforeOpenChild"
            @row-del="rowDelChild"
            @row-update="rowUpdateChild"
            @row-save="rowSaveChild"
            @search-change="searchChangeChild"
            @search-reset="searchResetChild"
            @selection-change="selectionChangeChild"
            @current-change="currentChangeChild"
            @size-change="sizeChangeChild"
            @refresh-change="refreshChangeChild"
            @on-load="onLoadChild"
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
          </avue-crud>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
  import {
    getParentList,
    getChildList,
    remove,
    update,
    add,
    getDict,
  } from "@/api/system/dict";
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        parentId: 0,
        formParent: {},
        formChild: {},
        selectionList: [],
        query: {},
        loading: true,
        pageParent: {
          pageSize: 5,
          pageSizes: [5, 10, 30, 50, 100, 200],
          currentPage: 1,
          total: 0
        },
        pageChild: {
          pageSize: 5,
          pageSizes: [5, 10, 30, 50, 100, 200],
          currentPage: 1,
          total: 0
        },
        dataParent: [],
        dataChild: [],
        option: {
          tip: false,
          searchShow: true,
          searchMenuSpan: 6,
          tree: true,
          border: true,
          index: true,
          selection: true,
          viewBtn: true,
          height: 300,
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
        }
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
    methods: {
      rowSave(row, done, loading) {
        add(row).then(() => {
          this.onLoadParent(this.pageParent);
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
          this.onLoadParent(this.pageParent);
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
            this.onLoadParent(this.pageParent);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          });
      },
      handleRowClick(row) {
        this.parentId = row.id;
        this.onLoadChild(this.pageChild);
      },
      searchReset() {
        this.query = {};
        this.onLoadParent(this.pageParent);
      },
      searchChange(params, done) {
        this.query = params;
        this.pageParent.currentPage = 1;
        this.onLoadParent(this.pageParent, params);
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
            this.onLoadParent(this.pageParent);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.$refs.crud.toggleSelection();
          });
      },
      beforeOpen(done, type) {
        if (["edit", "view"].includes(type)) {
          getDict(this.formParent.id).then(res => {
            this.formParent = res.data.data;
          });
        }
        done();
      },
      currentChange(currentPage) {
        this.pageParent.currentPage = currentPage;
      },
      sizeChange(pageSize) {
        this.pageParent.pageSize = pageSize;
      },
      refreshChange() {
        this.onLoadParent(this.page, this.query);
      },
      rowSaveChild(row, done, loading) {
        add(row).then(() => {
          this.onLoadChild(this.pageChild);
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
      rowUpdateChild(row, index, done, loading) {
        update(row).then(() => {
          this.onLoadChild(this.pageChild);
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
      rowDelChild(row) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(row.id);
          })
          .then(() => {
            this.onLoadChild(this.pageChild);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          });
      },
      searchResetChild() {
        this.query = {};
        this.onLoadChild(this.pageChild);
      },
      searchChangeChild(params, done) {
        this.query = params;
        this.pageChild.currentPage = 1;
        this.onLoadChild(this.pageChild, params);
        done();
      },
      selectionChangeChild(list) {
        this.selectionList = list;
      },
      selectionClearChild() {
        this.selectionList = [];
        this.$refs.crudChild.toggleSelection();
      },
      handleDeleteChild() {
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
            this.onLoadChild(this.pageChild);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.$refs.crudChild.toggleSelection();
          });
      },
      beforeOpenChild(done, type) {
        if (["edit", "view"].includes(type)) {
          getDict(this.formChild.id).then(res => {
            this.formChild = res.data.data;
          });
        }
        done();
      },
      currentChangeChild(currentPage) {
        this.pageChild.currentPage = currentPage;
      },
      sizeChangeChild(pageSize) {
        this.pageChild.pageSize = pageSize;
      },
      refreshChangeChild() {
        this.onLoadChild(this.pageChild, this.query);
      },
      onLoadParent(page, params = {}) {
        this.loading = true;
        getParentList(
          page.currentPage,
          page.pageSize,
          Object.assign(params, this.query)
        ).then(res => {
          const data = res.data.data;
          this.pageParent.total = data.total;
          this.dataParent = data.records;
          this.loading = false;
          this.selectionClear();
        });
      },
      onLoadChild(page, params = {}) {
        this.loading = true;
        getChildList(
          page.currentPage,
          page.pageSize,
          this.parentId,
          Object.assign(params, this.query)
        ).then(res => {
          const data = res.data.data;
          this.pageChild.total = data.total;
          this.dataChild = data.records;
          this.loading = false;
          this.selectionClear();
        });
      }
    }
  };
</script>

<style>
</style>
