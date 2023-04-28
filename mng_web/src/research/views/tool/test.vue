<template>
  <div class="code-table">
    <avue-crud
      ref="form"
      :data="tableData"
      :search.sync="tableQueryData"
      :option="tableOption"
      :before-open="beforeOpen"
      :before-close="beforeClose"
      @search-change="searchChangeFun"
      @search-reset="searchResetFun"
    >
      <template :slot="template" slot-scope="{ size }"></template>
    </avue-crud>
  </div>
</template>

<script>
export default {
  data() {
    return {
      template: '',
      tableData: [],
      tableQueryData: {},
      tableOption: {
        selection: true,
        dialogTop: '10%',
        dialogClickModal: false,
        dialogWidth: '80%',
        menuWidth: '200',
        border: true, //开启边框
        viewBtn: true,
        searchMenuSpan: 6,
        searchMenuPosition: 'left',
        expandRowKeys: [],
        maxHeight: '200',
        selectable: (row, index) => {
          console.log(row, index)
          return this.acb(row, index)
        },
        column: [
          {
            label: '姓名',
            prop: 'name',
            width: 140,
            search: true,
          },
          {
            label: '性别1',
            prop: 'sex',
            search: true,
          },
          {
            label: '手机信息1',
            prop: 'phone1',
          },
        ],
      },
      dataList: [
        {
          yhYsXx: {
            lsh: '146767126060222873820211206094431207',
            hh: '8600000',
            qs: '0',
            hm: '用户1',
            yf: '2021-12-04',
            yhId: '1467011273471115266',
            yl: 13,
            sfdw: '124水务局',
            dz: '黄村001号',
            zs: '13',
            czr: 'admin',
            zdId: '1467011580167012354',
            sfsj: '2021-12-06',
          },
          fyMx: [],
          jeMx: {
            sfkqgz: '1',
            ssdx: '零元整',
            yshj: '0.00',
            jydk: '0.00',
            yztp: 'http://oss.mj.ink/mjkj/upload/20211204/020e2d08d8d9b65adbeee466b154e8d4.png',
            djtt: '',
            ssje: '0',
            dywd: '124水厂分厅',
            sjgzwz: '1',
            jy: '0',
            sqjy: '0.00',
            bz: '',
            ewm: 'http://oss.mj.ink/mjkj/upload/20211206/4700c9943ec597d115d5cdc6efa01506.jpg',
            sjbt: '124水厂',
          },
        },
      ],
    }
  },
  mounted() {
    let cont = 0
    let tableData = []
    while (cont < 10) {
      cont = cont + 1
      let object = {
        name: '王小虎' + cont,
        sex: cont,
      }
      tableData.push(object)
    }
    setTimeout(() => {
      /* this.tableOption.selectable = (row, index) => {
        console.log(row, index)
        if (row.name == '王小虎1') {
          return false
        } else {
          return true
        }
      } */
      this.tableOption = {
        ...this.tableOption,
        selectable: (row, index) => {
          console.log(row, index)
          if (row.name == '王小虎1') {
            return false
          } else {
            return true
          }
        },
      }
    }, 1000)
    setTimeout(() => {
      this.tableData = tableData
    }, 1500)
  },
  methods: {
    acb(row, index) {
      if (row.name == '王小虎1') {
        return false
      } else {
        return true
      }
    },
    txtFun() {},
    searchChangeFun() {},
    searchResetFun() {},
    beforeOpen(done) {
      done()
    },
    beforeClose(done) {
      done()
    },
    //设置表格配置
    setTableOptionsFun(prop, obj) {
      let column = this.findObject(this.tableOption.column, prop)
      for (let key in obj) {
        column[key] = obj[key]
      }
    },
  },
}
</script>

<style lang="scss" scoped></style>
