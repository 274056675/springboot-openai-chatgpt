<template>
  <basic-container>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row type="flex" class="row-bg" justify="end">
        <el-form-item>
          <el-button type="primary" @click="handleAgree">同意</el-button>
          <el-button type="danger" @click="handleDisagree">驳回</el-button>
          <el-button @click="handleCancel">关闭</el-button>
        </el-form-item>
      </el-row>
      <el-card shadow="hover">
        <div slot="header">
          <span>审批信息</span>
        </div>
        <el-form-item label="申请人">
          <el-input :disabled="true" v-model="form.flow.assigneeName"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开始时间">
              <el-input :disabled="true" v-model="form.startTime"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间">
              <el-input :disabled="true" v-model="form.endTime"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="请假理由">
          <el-input :disabled="true" type="textarea" v-model="form.reason"/>
        </el-form-item>
        <el-form-item label="批复意见">
          <el-input type="textarea" v-model="form.comment"/>
        </el-form-item>
      </el-card>
      <el-card shadow="hover">
        <div slot="header">
          <span>流程信息</span>
        </div>
        <el-row type="flex" class="row-bg">
          <el-timeline>
            <el-timeline-item :key="flow.id" :timestamp="flow.createTime" v-for="flow in flowList" placement="top">
              <el-card shadow="hover">
                <p>{{flow.assigneeName}} 在 [{{flow.createTime}}] 开始处理 [{{flow.historyActivityName}}] 环节</p>
                <p v-if="flow.historyActivityDurationTime!==''">任务历时 [{{flow.historyActivityDurationTime}}]</p>
                <p v-if="flow.comment!==''">批复意见: [{{flow.comment}}]</p>
                <p v-if="flow.endTime!==''">结束时间: [{{flow.endTime}}]</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-row>
      </el-card>
      <el-card shadow="hover">
        <div slot="header">
          <span>流程跟踪</span>
        </div>
        <el-row class="row-bg">
          <flow-design :is-display="true" :process-instance-id="processInstanceId"></flow-design>
        </el-row>
      </el-card>
    </el-form>
  </basic-container>
</template>

<script>
  import {historyFlowList, leaveDetail} from "@/api/work/process";
  import {completeTask} from "@/api/work/work";

  export default {
    data() {
      return {
        taskId: '',
        businessId: '',
        processInstanceId: '',
        src: '',
        flowList: [],
        form: {
          flow: {
            assigneeName: '',
          },
          startTime: '',
          endTime: '',
          reason: '',
          comment: '',
        },
      }
    },
    created() {
      this.init();
    },
    beforeRouteUpdate(to, from, next) {
      // 在当前路由改变，但是该组件被复用时调用
      // 举例来说，对于一个带有动态参数的路径 /foo/:id，在 /foo/1 和 /foo/2 之间跳转的时候
      // 由于会渲染同样的 Foo 组件，因此组件实例会被复用。而这个钩子就会在这个情况下被调用
      // 可以访问组件实例 `this`
      if (to.fullPath !== from.fullPath) {
        next();
        this.init();
      }
    },
    methods: {
      init() {
        this.taskId = this.$route.params.taskId;
        this.processInstanceId = this.$route.params.processInstanceId;
        this.businessId = this.$route.params.businessId;
        historyFlowList(this.processInstanceId).then(res => {
          const data = res.data;
          if (data.success) {
            this.flowList = data.data;
          }
        })
        leaveDetail(this.businessId).then(res => {
          const data = res.data;
          if (data.success) {
            this.form = data.data;
          }
        })
      },
      handleAgree() {
        if (!this.form.comment) {
          this.$message.warning('请先填写批复意见');
          return;
        }
        const params = {
          taskId: this.taskId,
          processInstanceId: this.processInstanceId,
          flag: 'ok',
          comment: this.form.comment,
        };
        completeTask(params).then(res => {
          const data = res.data;
          if (data.success) {
            this.$message.success(data.msg);
            this.$router.$avueRouter.closeTag();
            this.$router.push({path: `/work/start`});
          } else {
            this.$message.error(data.msg || '提交失败');
          }
        })
      },
      handleDisagree() {
        if (!this.form.comment) {
          this.$message.warning('请先填写批复意见');
          return;
        }
        const params = {
          taskId: this.taskId,
          processInstanceId: this.processInstanceId,
          comment: this.form.comment,
        };
        completeTask(params).then(res => {
          const data = res.data;
          if (data.success) {
            this.$message.success(data.msg);
            this.$router.$avueRouter.closeTag();
            this.$router.push({path: `/work/start`});
          } else {
            this.$message.error(data.msg || '提交失败');
          }
        })
      },
      handleCancel() {
        this.$router.$avueRouter.closeTag();
        this.$router.push({path: `/work/start`});
      }
    }
  }
</script>
