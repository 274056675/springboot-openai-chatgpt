import {mapGetters} from "vuex";

export default (app, option = {}) => {
  const mixins = {
    data() {
      return {
        selectionList: [],
        data: [],
        form: {},
        params: {},
        loading: false,
        api: require(`@/api/${option.name}`),
        option: require(`@/option/${option.name}`).default,
        page: {
          pageSizes: [10, 30, 50, 100, 200],
          pageSize: 10
        },
      }
    },
    computed: {
      ...mapGetters(['userInfo', 'permission', 'roles']),
      ids() {
        const ids = [];
        this.selectionList.forEach(ele => {
          ids.push(ele[this.rowKey]);
        });
        return ids.join(",");
      },
      bindVal() {
        return {
          ref: 'crud',
          option: this.option,
          data: this.data,
          tableLoading: this.loading
        }
      },
      onEvent() {
        return {
          'on-load': this.getList,
          'row-save': this.rowSave,
          'row-update': this.rowUpdate,
          'row-del': this.rowDel,
          'selection-change': this.selectionChange,
          'refresh-change': this.refreshChange,
          'date-change': this.dateChange,
          'search-change': this.searchChange,
          'search-reset': this.searchChange
        }
      },
      rowKey() {
        return this.option.rowKey || option.rowKey || 'id'
      }
    },
    methods: {
      getList() {
        const callback = () => {
          this.loading = true;
          this.api[option.list || 'getList'](this.page.currentPage, this.page.pageSize, this.params).then(res => {
            let data;
            if (option.res) {
              data = option.res(res.data);
            } else {
              data = res.data.data;
            }
            this.page.total = data[option.total || 'total'] || 0;
            this.data = data[option.records || 'records'];
            if (this.listAfter) {
              this.listAfter(data);
            }
            this.loading = false;
          })
        }
        if (this.listBefore) {
          this.listBefore();
        }
        callback();
      },
      rowSave(row, done, loading) {
        const callback = () => {
          delete this.form.params;
          this.api[option.add || 'add'](this.form).then((data) => {
            this.getList();
            if (this.addAfter) {
              this.addAfter(data);
            } else {
              this.$message.success('新增成功');
            }
            done();
          }).catch(() => {
            loading();
          })
        }
        if (this.addBefore) {
          this.addBefore();
        }
        callback();
      },
      rowUpdate(row, index, done, loading) {
        const callback = () => {
          delete this.form.params;
          this.api[option.update || 'update'](this.form).then((data) => {
            this.getList();
            if (this.updateAfter) {
              this.updateAfter(data);
            } else {
              this.$message.success('更新成功');
            }
            done();
          }).catch(() => {
            loading();
          })
        }
        if (this.updateBefore) {
          this.updateBefore();
        }
        callback();
      },
      rowDel(row, index) {
        const callback = () => {
          this.api[option.del || 'remove'](row[this.rowKey], row).then((data) => {
            this.getList();
            if (this.delAfter) {
              this.delAfter(data, row, index)
            } else {
              this.$message.success('删除成功');
            }
          })
        }
        if (this.delBefore) {
          this.delBefore();
          callback();
        } else {
          this.$confirm('确定将选择数据删除?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            callback();
          })
        }
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
            this.api[option.del || 'remove'](this.ids).then((data) => {
              this.getList();
              if (this.delMultiAfter) {
                this.delMultiAfter(data, this.ids);
              } else {
                this.$message.success('删除成功');
              }
            });
          });
      },
      searchChange(params, done) {
        if (done) done();
        if (this.validatenull(params)) {
          Object.keys(this.params).forEach(ele => {
            if (!['createTime_dategt', 'createTime_datelt'].includes(ele)) {
              delete this.params[ele];
            }
          })
        } else {
          Object.keys(params).forEach(ele => {
            if (this.validatenull(params[ele])) {
              delete this.params[ele];
              delete params[ele];
            }
          })
        }
        this.params = Object.assign(this.params, params);
        this.page.currentPage = 1;
        this.getList();
      },
      dateChange(date) {
        if (date) {
          this.params.createTime_dategt = date[0];
          this.params.createTime_datelt = date[1];
        } else {
          delete this.params.createTime_dategt;
          delete this.params.createTime_datelt;
        }
        this.page.currentPage = 1;
        this.getList();
      },
      selectionChange(list) {
        this.selectionList = list;
      },
      selectionClear() {
        this.selectionList = [];
        this.$refs.crud.toggleSelection();
      },
      refreshChange() {
        this.getList();
      }
    }
  }
  app.mixins = app.mixins || [];
  app.mixins.push(mixins);
  return app;
}
