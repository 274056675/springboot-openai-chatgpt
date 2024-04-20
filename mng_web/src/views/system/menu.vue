<template>
  <basic-container>
    <avue-crud :option="option"
               :table-loading="loading"
               :data="data"
               ref="crud"
               v-model="form"
               :permission="permissionList"
               :before-open="beforeOpen"
               @row-del="rowDel"
               @row-update="rowUpdate"
               @row-save="rowSave"
               @search-change="searchChange"
               @search-reset="searchReset"
               @selection-change="selectionChange"
               @current-change="currentChange"
               @size-change="sizeChange"
               @on-load="onLoad">
      <template slot="menuLeft">
        <el-button type="danger"
                   size="small"
                   icon="el-icon-delete"
                   v-if="permission.menu_delete"
                   plain
                   @click="handleDelete">删 除
        </el-button>
      </template>
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          icon="el-icon-circle-plus-outline"
          size="small"
          @click.stop="handleAdd(scope.row,scope.index)"
          v-if="userInfo.authority.includes('admin')"
        >新增子项
        </el-button>
      </template>
      <template slot-scope="{row}"
                slot="source">
        <div style="text-align:center">
          <i :class="row.source"></i>
        </div>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
  import {add, getList, getMenu, remove, update} from "@/api/system/menu";
  import {mapGetters} from "vuex";
  import iconList from "@/config/iconList";

  export default {
    data() {
      return {
        form: {},
        loading: true,
        selectionList: [],
        query: {},
        page: {
          pageSize: 10,
          currentPage: 1,
          total: 0
        },
        option: {
          searchShow: true,
          searchMenuSpan: 6,
          tip: false,
          tree: true,
          border: true,
          index: true,
          selection: true,
          viewBtn: true,
          menuWidth: 300,
          column: [
            {
              label: "菜单名称",
              prop: "name",
              search: true,
              rules: [
                {
                  required: true,
                  message: "请输入菜单名称",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "路由地址",
              prop: "path",
              rules: [
                {
                  required: true,
                  message: "请输入路由地址",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "上级菜单",
              prop: "parentId",
              type: "tree",
              dicUrl: "/api/blade-system/menu/tree",
              hide: true,
              props: {
                label: "title"
              },
              rules: [
                {
                  required: false,
                  message: "请选择上级菜单",
                  trigger: "click"
                }
              ]
            },
            {
              label: "菜单图标",
              prop: "source",
              type: "icon",
              slot: true,
              iconList: iconList,
              rules: [
                {
                  required: true,
                  message: "请输入菜单图标",
                  trigger: "click"
                }
              ]
            },
            {
              label: "菜单编号",
              prop: "code",
              search: true,
              rules: [
                {
                  required: true,
                  message: "请输入菜单编号",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "菜单类型",
              prop: "category",
              type: "radio",
              dicData: [
                {
                  label: "菜单",
                  value: 1
                },
                {
                  label: "按钮",
                  value: 2
                }
              ],
              hide: true,
              rules: [
                {
                  required: true,
                  message: "请选择菜单类型",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "菜单别名",
              prop: "alias",
              rules: [
                {
                  required: true,
                  message: "请输入菜单别名",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "按钮功能",
              prop: "action",
              type: "radio",
              dicData: [
                {
                  label: "工具栏",
                  value: 1
                },
                {
                  label: "操作栏",
                  value: 2
                },
                {
                  label: "工具操作栏",
                  value: 3
                }
              ],
              hide: true,
              rules: [
                {
                  required: true,
                  message: "请选择按钮功能",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "菜单排序",
              prop: "sort",
              type: "number",
              rules: [
                {
                  required: true,
                  message: "请输入菜单排序",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "新窗口",
              prop: "isOpen",
              type: "radio",
              dicData: [
                {
                  label: "否",
                  value: 0
                },
                {
                  label: "是",
                  value: 1
                },
              ],
              hide: true
            },
            {
              label: "菜单备注",
              prop: "remark",
              type: "textarea",
              span: 24,
              minRows: 6,
              hide: true
            }
          ]
        },
        data: []
      };
    },

    computed: {
      ...mapGetters(["userInfo", "permission"]),
      permissionList() {
        return {
          addBtn: this.vaildData(this.permission.menu_add, false),
          viewBtn: this.vaildData(this.permission.menu_view, false),
          delBtn: this.vaildData(this.permission.menu_delete, false),
          editBtn: this.vaildData(this.permission.menu_edit, false)
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
      handleAdd(row) {
        this.$refs.crud.value.parentId = row.id;
        this.$refs.crud.option.column.filter(item => {
          if (item.prop === "parentId") {
            item.value = row.id;
            item.addDisabled = true;
          }
        });
        this.$refs.crud.rowAdd();
      },
      rowSave(row, done, loading) {
        add(row).then(() => {
          done();
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowUpdate(row, index, done, loading) {
        update(row).then(() => {
          done();
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
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
          getMenu(this.form.id).then(res => {
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
        this.loading = true;
        getList(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
          this.loading = false;
          this.data = res.data.data;
        });
      }
    }
  };
</script>

<style>
</style>
