<template>
  <basic-container>
    <avue-form ref="form" :option="option" v-model="form" :upload-before="uploadBefore" :upload-after="uploadAfter"/>
  </basic-container>
</template>

<script>
  import {deployUpload} from "@/api/flow/flow";
  import {flowCategory} from "@/util/flow";

  export default {
    data() {
      return {
        form: {
          flowCategory: '',
          tenantId: '',
          flowFile: [],
          file: {},
        },
        option: {
          labelWidth: 120,
          menuBtn: false,
          column: [
            {
              label: '流程类型',
              prop: 'flowCategory',
              type: 'select',
              dicUrl: `/api/blade-system/dict/dictionary?code=flow`,
              props: {
                label: "dictValue",
                value: "dictKey"
              },
              row: true,
              span: 12,
              dataType: "number",
              rules: [
                {
                  required: true,
                  message: '请选择流程类型',
                  trigger: 'blur'
                }
              ]
            },
            {
              label: "流程模式",
              prop: "flowMode",
              type: "radio",
              dicData: [
                {
                  label: "通用流程",
                  value: 1
                },
                {
                  label: "定制流程",
                  value: 2
                }
              ],
              value: 1,
              row: true,
              span: 12,
              rules: [
                {
                  required: true,
                  message: '请选择流程模式',
                  trigger: 'blur'
                }
              ],
            },
            {
              label: "所属租户",
              prop: "tenantId",
              type: "tree",
              multiple: true,
              dicUrl: "/api/blade-system/tenant/select",
              props: {
                label: "tenantName",
                value: "tenantId"
              },
              display: false,
              row: true,
              span: 12,
              rules: [
                {
                  required: true,
                  message: '请选择所属租户',
                  trigger: 'blur'
                }
              ],
            },
            {
              label: '附件上传',
              prop: 'flowFile',
              type: 'upload',
              loadText: '附件上传中，请稍等',
              span: 24,
              propsHttp: {
                res: 'data'
              },
              tip: '请上传 bpmn20.xml 标准格式文件',
              action: '/api/blade-flow/manager/check-upload'
            },
          ]
        }
      }
    },
    watch: {
      'form.flowMode'() {
        this.$refs.form.option.column.filter(item => {
          if (item.prop === "tenantId") {
            item.display = this.form.flowMode === 2;
          }
        });
      }
    },
    methods: {
      uploadBefore(file, done) {
        this.$message.success('部署开始');
        this.file = file;
        done()
      },
      uploadAfter(res, done, loading) {
        if (!this.form.flowCategory) {
          this.$message.warning('清先选择流程类型');
          loading()
          return false;
        }
        if (this.form.flowMode === 2 && !this.form.tenantId) {
          this.$message.warning('清先选择对应租户');
          loading();
          return false;
        }
        if (res.success) {
          deployUpload(
            flowCategory(this.form.flowCategory),
            (this.form.tenantId) ? this.form.tenantId.join(",") : "",
            [this.file]
          ).then(res => {
            const data = res.data;
            if (data.success) {
              done()
            } else {
              this.$message.error(data.msg);
              loading()
            }
          })
        } else {
          this.$message.warning('请上传 bpmn20.xml 标准格式文件');
          loading()
          return false;
        }
      },
    }
  }
</script>
