<template>
  <el-row>
    <el-col :span="9">
      <div class="box">
        <el-scrollbar>
          <basic-container>
            <avue-tree :option="treeOption" :data="treeData" @node-click="nodeClick"/>
          </basic-container>
        </el-scrollbar>
      </div>
    </el-col>
    <el-col :span="15">
      <basic-container>
        <el-button-group>
          <el-button v-if="permission.region_add" type="primary" size="small" icon="el-icon-circle-plus-outline" @click="addChildren">新增下级</el-button>
          <el-button v-if="permission.region_delete" type="primary" size="small" icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button v-if="permission.region_debug" type="primary" size="small" icon="el-icon-video-play" @click="handleDebug">调试</el-button>
        </el-button-group>
      </basic-container>
      <basic-container>
        <avue-form ref="form" :option="regionOption" v-model="regionForm" @submit="handleSubmit">
          <template slot="code" slot-scope="{}">
            <el-input placeholder="请输入 区划子编号" v-model="regionForm.subCode">
              <template slot="prepend">{{regionForm.parentCode}}</template>
            </el-input>
          </template>
        </avue-form>
        <el-dialog title="行政区划数据调试"
                   append-to-body
                   :visible.sync="debugBox"
                   width="350px">
          <avue-form :option="debugOption" v-model="debugForm"/>
        </el-dialog>
      </basic-container>
    </el-col>
  </el-row>
</template>

<script>
  import {getLazyTree, getDetail, submit, remove} from "@/api/base/region";
  import {mapGetters} from "vuex";
  import {validatenull} from "@/util/validate";

  export default {
    data() {
      return {
        topCode: '00',
        treeCode: '',
        treeParentCode: '',
        treeData: [],
        treeOption: {
          nodeKey: 'id',
          lazy: true,
          treeLoad: function (node, resolve) {
            const parentCode = (node.level === 0) ? "00" : node.data.id;
            getLazyTree(parentCode).then(res => {
              resolve(res.data.data.map(item => {
                return {
                  ...item,
                  leaf: !item.hasChildren
                }
              }))
            });
          },
          addBtn: false,
          menu: false,
          size: 'small',
          props: {
            labelText: '标题',
            label: 'title',
            value: 'value',
            children: 'children'
          }
        },
        regionForm: {},
        regionOption: {
          labelWidth: 100,
          column: [
            {
              label: "父区划编号",
              prop: "parentCode",
              span: 24,
              disabled: true,
              rules: [{
                required: true,
                message: "请输入父区划编号",
                trigger: "blur"
              }]
            },
            {
              label: "父区划名称",
              prop: "parentName",
              span: 24,
              disabled: true,
            },
            {
              label: "区划编号",
              prop: "code",
              formslot: true,
              span: 24,
              rules: [{
                required: true,
                message: "请输入区划编号",
                trigger: "blur"
              }]
            },
            {
              label: "区划子编号",
              prop: "subCode",
              display: false,
            },
            {
              label: "区划名称",
              prop: "name",
              span: 24,
              rules: [{
                required: true,
                message: "请输入区划名称",
                trigger: "blur"
              }]
            },
            {
              label: "区划等级",
              prop: "level",
              type: "radio",
              dicUrl: "/api/blade-system/dict/dictionary?code=region",
              props: {
                label: "dictValue",
                value: "dictKey"
              },
              dataType: "number",
              span: 24,
              rules: [{
                required: true,
                message: "请选择区划等级",
                trigger: "blur"
              }]
            },
            {
              label: "区划排序",
              prop: "sort",
              type: "number",
              span: 24,
              rules: [{
                required: true,
                message: "请输入区划排序",
                trigger: "blur"
              }]
            },
            {
              label: "区划备注",
              prop: "remark",
              type: "textarea",
              minRows: 6,
              span: 24,
            },
          ]
        },
        debugBox: false,
        debugForm: {},
        debugOption: {
          labelWidth: 50,
          submitBtn: false,
          emptyBtn: false,
          column: [
            {
              label: '省份',
              prop: 'province',
              type: 'select',
              props: {
                label: 'name',
                value: 'code'
              },
              cascaderItem: ['city', 'district'],
              dicUrl: '/api/blade-system/region/select',
              span: 24,
            },
            {
              label: '地市',
              prop: 'city',
              type: 'select',
              props: {
                label: 'name',
                value: 'code'
              },
              dicFlag: false,
              dicUrl: '/api/blade-system/region/select?code={{key}}',
              span: 24,
            },
            {
              label: '区县',
              prop: 'district',
              type: 'select',
              props: {
                label: 'name',
                value: 'code'
              },
              dicFlag: false,
              dicUrl: '/api/blade-system/region/select?code={{key}}',
              span: 24,
            }
          ]
        }
      };
    },
    watch: {
      'regionForm.subCode'() {
        this.regionForm.code = this.regionForm.parentCode + this.regionForm.subCode;
      },
      'excelForm.isCovered'() {
        if (this.excelForm.isCovered !== '') {
          const column = this.findObject(this.excelOption.column, "excelFile");
          column.action = `/api/blade-system/region/import-region?isCovered=${this.excelForm.isCovered}`;
        }
      }
    },
    computed: {
      ...mapGetters(["permission"]),
      permissionList() {
        return {
          addBtn: this.vaildData(this.permission.region_add, false),
          viewBtn: this.vaildData(this.permission.region_view, false),
          delBtn: this.vaildData(this.permission.region_delete, false),
          editBtn: this.vaildData(this.permission.region_edit, false)
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
      initTree() {
        this.treeData = [];
        getLazyTree(this.topCode).then(res => {
          this.treeData = res.data.data.map(item => {
            return {
              ...item,
              leaf: !item.hasChildren
            }
          })
        });
      },
      nodeClick(data) {
        const column = this.findObject(this.regionOption.column, "parentCode");
        column.disabled = true;
        this.treeCode = data.id;
        this.treeParentCode = data.parentId;
        getDetail(this.treeCode).then(res => {
          this.regionForm = res.data.data;
          this.regionForm.subCode = this.regionForm.code.replace(this.regionForm.parentCode, '');
        })
      },
      addChildren() {
        if (validatenull(this.regionForm.code) || validatenull(this.regionForm.name)) {
          this.$message.warning("请先选择一项区划");
          return;
        }
        this.regionForm.parentCode = this.regionForm.code;
        this.regionForm.parentName = this.regionForm.name;
        this.regionForm.code = '';
        this.regionForm.subCode = '';
        this.regionForm.name = '';
        this.regionForm.level = (this.regionForm.level === 5) ? 5 : this.regionForm.level + 1;
      },
      handleSubmit(form, done, loading) {
        const parentCode = form.parentCode === this.topCode ? '' : form.parentCode;
        form.code = parentCode + form.subCode;
        submit(form).then(() => {
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          this.initTree();
          this.regionForm.subCode = '';
          this.$refs.form.resetForm();
          done();
        }, error => {
          loading();
          window.console.log(error);
        });
      },
      handleDelete() {
        if (validatenull(this.regionForm.code)) {
          this.$message.warning("请先选择一项区划");
          return;
        }
        this.$confirm(`确定将 [${this.regionForm.name}] 数据删除?`, {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(this.treeCode);
          })
          .then(() => {
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.initTree();
            this.regionForm.subCode = '';
            this.$refs.form.resetForm();
          });
      },
      uploadAfter(res, done, loading, column) {
        window.console.log(column);
        this.excelBox = false;
        this.initTree();
        done();
      },
      handleDebug() {
        this.debugBox = true;
      },
    }
  };
</script>

<style>
  .box {
    height: 800px;
  }

  .el-scrollbar {
    height: 100%;
  }

  .box .el-scrollbar__wrap {
    overflow: scroll;
  }
</style>
