<template>
  <div>
    <basic-container>
      <avue-form :option="option" v-model="form" @submit="handleSubmit"/>
    </basic-container>
    <basic-container>
      <flow-design :is-display="true" :process-definition-id="processDefinitionId"></flow-design>
    </basic-container>
  </div>
</template>

<script>
  import {leaveProcess} from "@/api/work/process";

  export default {
    data() {
      return {
        processDefinitionId: '',
        form: {},
        option: {
          group: [
            {
              icon: 'el-icon-info',
              label: '请假基础信息',
              prop: 'group1',
              column: [
                {
                  label: '审批人员',
                  prop: 'taskUser',
                  type: 'select',
                  dicUrl: `/api/blade-user/user-list`,
                  props: {
                    label: "account",
                    value: "id"
                  },
                  span: 24,
                  rules: [
                    {
                      required: true,
                      message: '请选择审批人员',
                      trigger: 'blur'
                    }
                  ]
                },
                {
                  label: '开始时间',
                  prop: 'startTime',
                  type: 'datetime',
                  valueFormat: 'yyyy-MM-dd HH:mm:ss',
                  rules: [
                    {
                      required: true,
                      message: '请选择开始时间',
                      trigger: 'blur'
                    }
                  ]
                },
                {
                  label: '结束时间',
                  prop: 'endTime',
                  type: 'datetime',
                  valueFormat: 'yyyy-MM-dd HH:mm:ss',
                  rules: [
                    {
                      required: true,
                      message: '请选择结束时间',
                      trigger: 'blur'
                    }
                  ]
                },
                {
                  label: '请假理由',
                  prop: 'reason',
                  type: 'textarea',
                  span: 24,
                  rules: [
                    {
                      required: true,
                      message: '请输入请假理由',
                      trigger: 'blur'
                    }
                  ]
                },
              ]
            },
          ],
        }
      }
    },
    created() {
      this.processDefinitionId = this.$route.params.processDefinitionId;
    },
    methods: {
      handleSubmit() {
        const params = {
          processDefinitionId: this.$route.params.processDefinitionId,
          ...this.form,
        };
        leaveProcess(params).then(resp => {
          const data = resp.data;
          if (data.success) {
            this.$message.success(data.msg);
            this.$router.$avueRouter.closeTag();
            this.$router.push({path: `/work/start`});
          } else {
            this.$message.error(data.msg || '提交失败');
          }
        });
      }
    }
  }
</script>
