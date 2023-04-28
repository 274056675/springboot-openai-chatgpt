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
          <el-button v-if="permission.region_import" type="primary" size="small" icon="el-icon-upload2" @click="handleImport">导入</el-button>
          <el-button v-if="permission.region_export" type="primary" size="small" icon="el-icon-download" @click="handleExport">导出</el-button>
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
        <el-dialog title="行政区划数据导入"
                   append-to-body
                   :visible.sync="excelBox"
                   width="555px">
          <avue-form :option="excelOption" v-model="excelForm" :upload-after="uploadAfter">
            <template slot="excelTemplate">
              <el-button type="primary" @click="handleTemplate">
                点击下载<i class="el-icon-download el-icon--right"></i>
              </el-button>
            </template>
          </avue-form>
        </el-dialog>
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
  import {exportBlob} from "@/api/common";
  import {mapGetters} from "vuex";
  import {validatenull} from "@/util/validate";
  import {downloadXls} from "@/util/util";
  import {dateNow} from "@/util/date";
  import {getToken} from "@/util/auth";
  import NProgress from 'nprogress';
  import 'nprogress/nprogress.css';

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
              prop: "regionLevel",
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
        excelBox: false,
        excelForm: {},
        excelOption: {
          submitBtn: false,
          emptyBtn: false,
          column: [
            {
              label: '模板上传',
              prop: 'excelFile',
              type: 'upload',
              drag: true,
              loadText: '模板上传中，请稍等',
              span: 24,
              propsHttp: {
                res: 'data'
              },
              tip: '请上传 .xls,.xlsx 标准格式文件',
              action: "/api/blade-system/region/import-region"
            },
            {
              label: "数据覆盖",
              prop: "isCovered",
              type: "switch",
              align: "center",
              width: 80,
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
              value: 0,
              slot: true,
              rules: [
                {
                  required: true,
                  message: "请选择是否覆盖",
                  trigger: "blur"
                }
              ]
            },
            {
              label: '模板下载',
              prop: 'excelTemplate',
              formslot: true,
              span: 24,
            }
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
              cascader: ['city'],
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
              cascader: ['district'],
              dicFlag: false,
              dicUrl: '/api/blade-system/region/select?code={{province}}',
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
              dicUrl: '/api/blade-system/region/select?code={{city}}',
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
        this.regionForm.regionLevel = (this.regionForm.regionLevel === 5) ? 5 : this.regionForm.regionLevel + 1;
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
      handleImport() {
        this.excelBox = true;
      },
      handleExport() {
        this.$confirm("是否导出行政区划数据?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          NProgress.start();
          exportBlob(`/api/blade-system/region/export-region?${this.website.tokenHeader}=${getToken()}`).then(res => {
            downloadXls(res.data, `行政区划数据${dateNow()}.xlsx`);
            NProgress.done();
          })
        });
      },
      handleTemplate() {
        exportBlob(`/api/blade-system/region/export-template?${this.website.tokenHeader}=${getToken()}`).then(res => {
          downloadXls(res.data, "行政区划模板.xlsx");
        })
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
