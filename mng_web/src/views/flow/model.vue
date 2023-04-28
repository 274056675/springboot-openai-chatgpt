<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      ref="crud"
      v-model="form"
      :page.sync="page"
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
          type="primary"
          size="mini"
          icon="el-icon-circle-plus"
          v-if="permission.flow_model_create"
          plain
          @click="handleCreate"
        >创 建</el-button>
        <el-button
          type="danger"
          size="mini"
          icon="el-icon-delete"
          v-if="permission.flow_model_delete"
          plain
          @click="handleDelete"
        >删 除</el-button>
      </template>
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          size="mini"
          icon="el-icon-setting"
          v-if="permission.flow_model_update"
          @click.stop="handleUpdate(scope.row,scope.index)"
        >配置</el-button>
        <el-button
          type="text"
          size="mini"
          icon="el-icon-upload2"
          v-if="permission.flow_model_deploy"
          @click.stop="handleDeploy(scope.row,scope.index)"
        >部署</el-button>
        <el-button
          type="text"
          size="mini"
          icon="el-icon-delete"
          v-if="permission.flow_model_delete"
          @click.stop="handleSlotDelete(scope.row,scope.index)"
        >删除</el-button>
      </template>
      <template slot-scope="{row}" slot="version">
        <el-tag>v{{ row.version }}</el-tag>
      </template>
    </avue-crud>
    <el-dialog
      title="流程配置"
      append-to-body
      destroy-on-close
      :visible.sync="flowBox"
      :close-on-press-escape="false"
      :fullscreen="true"
      :before-close="handleNutflowClose"
      custom-class="wf-dialog"
    >
      <wf-design-base
        v-if="nutflowOption.step === 1"
        class="animated fadeIn"
        style="height: calc(100vh - 108px);"
        ref="wf-design"
        :options="nutflowOption.step1"
      ></wf-design-base>
      <wf-design-base
        v-if="nutflowOption.step === 2"
        class="animated fadeIn"
        style="height: calc(100vh - 108px);"
        ref="wf-design-view"
        :options="nutflowOption.step2"
      ></wf-design-base>
      <span slot="footer" class="avue-dialog__footer">
        <el-button size="small" @click="handleNutflowClose(() => {}, true)">取 消</el-button>
        <el-button
          v-if="nutflowOption.step === 1"
          size="small"
          type="success"
          @click="handleStep(1)"
        >下 一 步</el-button>
        <el-button
          v-if="nutflowOption.step === 2"
          size="small"
          type="success"
          @click="handleStep(-1)"
        >上 一 步</el-button>
        <el-button
          v-if="nutflowOption.step === 2"
          size="small"
          type="primary"
          @click="handleSubmitModel"
        >确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="流程部署" append-to-body :visible.sync="deployBox" width="20%">
      <avue-form ref="form" :option="optionDeploy" v-model="form" @submit="handleSubmit" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="deployBox = false">取 消</el-button>
        <el-button type="primary" @click="handleDoDeploy" :loading="deployLoading">确 定</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
import { mapGetters } from 'vuex'
import {
  modelList,
  removeModel,
  deployModel,
  submitModel,
  detail,
} from '@/api/flow/flow'
import { flowCategory } from '@/util/flow'

export default {
  data() {
    return {
      form: {},
      optionDeploy: {
        menuBtn: false,
        column: [
          {
            label: '流程类型',
            type: 'select',
            dicUrl: '/api/blade-system/dict/dictionary?code=flow',
            props: {
              label: 'dictValue',
              value: 'dictKey',
            },
            dataType: 'number',
            slot: true,
            prop: 'categoryValue',
            search: true,
            span: 24,
            rules: [
              {
                required: true,
                message: '请选择流程类型',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '流程模式',
            prop: 'flowMode',
            type: 'radio',
            dicData: [
              {
                label: '通用流程',
                value: 1,
              },
              {
                label: '定制流程',
                value: 2,
              },
            ],
            value: 1,
            span: 24,
            rules: [
              {
                required: true,
                message: '请选择流程模式',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '所属租户',
            prop: 'tenantId',
            type: 'tree',
            multiple: true,
            dicUrl: '/api/blade-system/tenant/select',
            props: {
              label: 'tenantName',
              value: 'tenantId',
            },
            display: false,
            span: 24,
            rules: [
              {
                required: true,
                message: '请选择所属租户',
                trigger: 'blur',
              },
            ],
          },
        ],
      },
      selectionId: '',
      selectionList: [],
      query: {},
      loading: true,
      deployLoading: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      deployBox: false,
      flowBox: false,
      option: {
        /* height: 'auto',
        calcHeight: 30, */
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        selection: true,
        editBtn: false,
        addBtn: false,
        viewBtn: false,
        delBtn: false,
        dialogWidth: 900,
        menuWidth: 200,
        dialogClickModal: false,
        size: 'mini',
        searchSize: 'mini',
        column: [
          {
            label: '模型主键',
            prop: 'id',
          },
          {
            label: '模型标识',
            prop: 'modelKey',
            search: true,
            overHidden: true,
          },
          {
            label: '模型名称',
            prop: 'name',
            search: true,
            overHidden: true,
          },
          {
            label: '流程版本',
            prop: 'version',
            slot: true,
            overHidden: true,
          },
          {
            label: '创建时间',
            prop: 'created',
            overHidden: true,
          },
          {
            label: '更新时间',
            prop: 'lastUpdated',
            overHidden: true,
          },
        ],
      },
      data: [],
      nutflowOption: {
        process: {},
        step: 1,
        step1: {
          toolbar: [
            'open',
            'create',
            'fit',
            'zoom-in',
            'zoom-out',
            'undo',
            'redo',
            'import',
            'preview',
          ],
        },
        step2: {
          mode: 'view',
          simulation: true,
          minimap: true,
        },
      },
    }
  },
  watch: {
    'form.flowMode'() {
      this.$refs.form.option.column.filter((item) => {
        if (item.prop === 'tenantId') {
          item.display = this.form.flowMode === 2
        }
      })
    },
  },
  computed: {
    ...mapGetters(['permission']),
    ids() {
      let ids = []
      this.selectionList.forEach((ele) => {
        ids.push(ele.id)
      })
      return ids.join(',')
    },
  },
  methods: {
    handleSubmitModel() {
      const registry = this.$refs['wf-design-view']
        .getElementRegistry()
        .getAll()
      const { businessObject } = registry[0]
      const { id, name, documentation } = businessObject
      const description =
        documentation && documentation.length > 0 ? documentation[0].text : null
      const params = {
        ...this.nutflowOption.process,
        modelKey: id,
        name,
        description,
        modelEditorXml: this.nutflowOption.process.xml,
      }
      submitModel(params).then(() => {
        this.$message.success('操作成功')
        this.handleNutflowClose()
        this.onLoad(this.page, this.query)
      })
    },
    handleStep(step) {
      if (step === 1) {
        // 下一步
        this.$refs['wf-design'].getData('xml').then((data) => {
          this.$set(this.nutflowOption.step1, 'xml', data)
          this.$set(this.nutflowOption.step2, 'xml', data)
          this.$set(this.nutflowOption.process, 'xml', data)
          this.$set(this.nutflowOption, 'step', 2)
        })
      } else this.$set(this.nutflowOption, 'step', 1)
    },
    handleNutflowClose(done, flag) {
      const initOption = {
        process: {},
        step: 1,
        step1: {
          toolbar: [
            'open',
            'create',
            'fit',
            'zoom-in',
            'zoom-out',
            'undo',
            'redo',
            'import',
            'preview',
          ],
        },
        step2: {
          mode: 'view',
          simulation: true,
          minimap: true,
        },
      }
      if (done || flag) {
        this.$confirm('确定要关闭吗？关闭未保存的修改都会丢失。', '警告', {
          type: 'warning',
        })
          .then(() => {
            this.$set(this, 'nutflowOption', initOption)
            if (typeof done == 'function') done()
            this.flowBox = false
          })
          .catch(() => {})
      } else {
        this.$set(this, 'nutflowOption', initOption)
        this.flowBox = false
      }
    },
    handleSubmit(form, done) {
      this.deployLoading = true
      deployModel({
        modelId: this.selectionId,
        category: flowCategory(form.categoryValue),
        tenantIds: form.tenantId.join(','),
      }).then((res) => {
        const data = res.data
        if (data.success) {
          this.$message({
            type: 'success',
            message: data.msg,
          })
          done()
          this.$refs.form.resetForm()
          this.deployBox = false
          this.deployLoading = false
        } else {
          done()
          this.deployLoading = false
          this.$message({
            type: 'warn',
            message: data.msg,
          })
        }
      })
    },
    searchReset() {
      this.query = {}
      this.onLoad(this.page)
    },
    searchChange(params, done) {
      this.query = params
      this.page.currentPage = 1
      this.onLoad(this.page, params)
      done()
    },
    selectionChange(list) {
      this.selectionList = list
    },
    selectionClear() {
      this.selectionList = []
      this.$refs.crud.toggleSelection()
    },
    handleDelete() {
      if (this.selectionList.length === 0) {
        this.$message.warning('请选择至少一条数据')
        return
      }
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return removeModel(this.ids)
        })
        .then(() => {
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
          this.$refs.crud.toggleSelection()
          this.onLoad(this.page)
        })
    },
    handleCreate() {
      this.flowBox = true
    },
    handleUpdate(row) {
      detail({ id: row.id }).then((res) => {
        const data = res.data.data
        const { modelEditorXml } = data
        this.$set(this.nutflowOption.step1, 'xml', modelEditorXml)
        this.$set(this.nutflowOption, 'process', data)
        this.flowBox = true
      })
    },
    handleDeploy(row) {
      this.deployBox = true
      this.selectionId = row.id
    },
    handleDoDeploy() {
      this.$refs.form.submit()
    },
    handleSlotDelete(row) {
      this.$confirm('确定将选择数据删除?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return removeModel(row.id)
        })
        .then(() => {
          this.$message({
            type: 'success',
            message: '操作成功!',
          })
          this.$refs.crud.toggleSelection()
          this.onLoad(this.page)
        })
    },
    currentChange(currentPage) {
      this.page.currentPage = currentPage
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize
    },
    refreshChange() {
      this.onLoad(this.page, this.query)
    },
    onLoad(page, params = {}) {
      this.loading = true
      modelList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data
        this.page.total = data.total
        this.data = data.records
        this.loading = false
        this.selectionClear()
      })
    },
  },
}
</script>

<style lang="scss">
.wf-dialog {
  .el-dialog__body {
    padding: 5px;
  }

  .avue-dialog__footer {
    text-align: center;
  }
}
</style>
