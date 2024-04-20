<template>
  <basic-container>
    <avue-crud :option="option"
               :table-loading="loading"
               :data="data"
               ref="crud"
               v-model="form"
               :permission="permissionList"
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
                   v-if="permission.role_delete"
                   plain
                   @click="handleDelete">删 除
        </el-button>
        <el-button size="small"
                   icon="el-icon-delete"
                   @click="handleRole"
                   plain>权限设置
        </el-button>
      </template>
    </avue-crud>
    <el-dialog title="角色配置"
               :visible.sync="box"
               width="345px">
      <el-tabs type="border-card">
        <el-tab-pane label="菜单权限">
          <el-tree :data="menuGrantList"
                   show-checkbox
                   node-key="id"
                   ref="treeMenu"
                   :default-checked-keys="menuTreeObj"
                   :props="props">
          </el-tree>
        </el-tab-pane>
        <el-tab-pane label="数据权限">
          <el-tree :data="dataScopeGrantList"
                   show-checkbox
                   node-key="id"
                   ref="treeDataScope"
                   :default-checked-keys="dataScopeTreeObj"
                   :props="props">
          </el-tree>
        </el-tab-pane>
      </el-tabs>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="box = false">取 消</el-button>
        <el-button type="primary"
                   @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
  import {add, getList, getRole, getRoleTree, grant, grantTree, remove, update} from "@/api/system/role";
  import {mapGetters} from "vuex";
  import website from '@/config/website';

  export default {
    data() {
      return {
        form: {},
        box: false,
        props: {
          label: "title",
          value: "key"
        },
        menuGrantList: [],
        dataScopeGrantList: [],
        menuTreeObj: [],
        dataScopeTreeObj: [],
        selectionList: [],
        query: {},
        loading: true,
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
          column: [
            {
              label: "角色名称",
              prop: "roleName",
              search: true,
              span: 24,
              rules: [
                {
                  required: true,
                  message: "请输入角色名称",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "所属租户",
              prop: "tenantId",
              type: "tree",
              dicUrl: "/api/blade-system/tenant/select",
              addDisplay: false,
              editDisplay: false,
              viewDisplay: website.tenantMode,
              span: 24,
              props: {
                label: "tenantName",
                value: "tenantId"
              },
              hide: !website.tenantMode,
              search: website.tenantMode,
              rules: [{
                required: true,
                message: "请输入所属租户",
                trigger: "click"
              }]
            },
            {
              label: "角色别名",
              prop: "roleAlias",
              search: true,
              span: 24,
              rules: [
                {
                  required: true,
                  message: "请输入角色别名",
                  trigger: "blur"
                }
              ]
            },
            {
              label: "上级角色",
              prop: "parentId",
              dicData: [],
              type: "tree",
              hide: true,
              span: 24,
              props: {
                label: "title"
              },
              rules: [
                {
                  required: false,
                  message: "请选择上级角色",
                  trigger: "click"
                }
              ]
            },
            {
              label: "角色排序",
              prop: "sort",
              type: "number",
              span: 24,
              rules: [
                {
                  required: true,
                  message: "请输入角色排序",
                  trigger: "blur"
                }
              ]
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
          addBtn: this.vaildData(this.permission.role_add, false),
          viewBtn: this.vaildData(this.permission.role_view, false),
          delBtn: this.vaildData(this.permission.role_delete, false),
          editBtn: this.vaildData(this.permission.role_edit, false)
        };
      },
      ids() {
        let ids = [];
        this.selectionList.forEach(ele => {
          ids.push(ele.id);
        });
        return ids.join(",");
      },
      idsArray() {
        let ids = [];
        this.selectionList.forEach(ele => {
          ids.push(ele.id);
        });
        return ids;
      }
    },
    methods: {
      submit() {
        const menuList = this.$refs.treeMenu.getCheckedKeys();
        const dataScopeList = this.$refs.treeDataScope.getCheckedKeys();
        grant(this.idsArray, menuList, dataScopeList).then(() => {
          this.box = false;
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          this.onLoad(this.page);
        });

        /*const menuLIst = this.$refs.tree.getCheckedKeys();
        grant(this.idsArray, menuLIst).then(() => {
          this.box = false;
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          this.onLoad(this.page);
        });*/
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
      handleRole() {
        if (this.selectionList.length !== 1) {
          this.$message.warning("只能选择一条数据");
          return;
        }
        this.menuTreeObj = [];
        this.dataScopeTreeObj = [];
        grantTree()
          .then(res => {
            this.menuGrantList = res.data.data.menu;
            this.dataScopeGrantList = res.data.data.dataScope;
            getRole(this.ids).then(res => {
              this.menuTreeObj = res.data.data.menu;
              this.dataScopeTreeObj = res.data.data.dataScope;
              this.box = true;
            });
          });


        /*this.defaultObj = [];
        grantTree()
          .then(res => {
            this.list = res.data.data;
            return getRole(this.ids);
          })
          .then(res => {
            this.defaultObj = res.data.data;
            this.box = true;
          });*/
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
      currentChange(currentPage) {
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize) {
        this.page.pageSize = pageSize;
      },
      onLoad(page, params = {}) {
        this.loading = true;
        getList(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
          this.data = res.data.data;
          this.loading = false;
          getRoleTree().then(res => {
            const column = this.findObject(this.option.column, "parentId");
            column.dicData = res.data.data;
          });
        });
      }
    }
  };
</script>

<style>
</style>
