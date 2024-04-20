import { apiRequestHead } from '@/config/url.js'

// 表单设计基础控件
import MenuLeftBtns from '@/research/components/code-list/menu-left-btns.vue'
import MenuFormBtns from '@/research/components/code-list/menu-form-btns.vue'
import MenuLinkBtns from '@/research/components/code-list/menu-link-btns.vue'
// 其他控件
import CodeSublistForm from '@/research/components/code-list/code-sublist-form'
import CodeSublistTable from '@/research/components/code-list/code-sublist-table'
import DepartControl from '@/research/components/general-control/depart-control'
import UserControl from '@/research/components/general-control/user-control'
import TableSelectControl from '@/research/components/general-control/table-select-control'

import tableView from "@/research/components/general-control/table-view.vue"
import TableTree from '@/research/components/general-control/table-tree.vue'
import FormView from '@/research/components/general-control/form-view.vue'
import TableSelect from '@/research/components/general-control/table-select.vue'
import ControlView from '@/research/components/general-control/control-view.vue'
import TabsView from '@/research/components/general-control/tabs-view.vue'
// 代码编辑器
import MonacoEditor from '@/packages/utils/monaco-editor'

export default {
  components: {
    // 基础
    MenuLeftBtns,
    MenuFormBtns,
    MenuLinkBtns,
    // 其他
    CodeSublistForm,
    CodeSublistTable,
    DepartControl,
    UserControl,
    TableSelectControl,
    tableView,
    TableTree,
    FormView,
    TableSelect,
    ControlView,
    TabsView,
    MonacoEditor,
  },
  data () {
    return {
      random: `${Math.random()}`.substring(3),
      isAvueTableLoading: '',//avue表格loading
      currDateTime: '',//当前时间
      isAuthBtn: false, //是否开启系统按钮权限 控制按钮显隐
      isOneGetData: true, //是否第一次获取数据
      selectionTime: null,
      apiRequestHead: apiRequestHead,
      isLinkPullDown: true, //自定义按钮 link是否下拉显示
      isTableGetData: true, //是否一开始加载数据
      subDataIdKey: 'id', //字表的需要的数据id字段名
      tableSearchType: '',
      tablePermission: {
        addBtn: false,
        allDelBtn: false,
        editBtn: false,
        exportBtn: false,
        inportBtn: false,
        moreDelBtn: false,
        moreViewBtn: false,
        moreChildBtn: false,
      }, //权限控制
      maps: new Map(), //存储树表格用于数据回显
      themeTemplate: '', //当前主题
      timerInte: [], //定时器
      tableDescribe: '', //表描述
      that: null,
      drawer: true,
      isTableLoading: false, //操作button加载
      tableCrudType: '',
      currCodeId: '', //当前表单id
      currCodeType: '', //当前表单其他参数
      tableName: '', //数据库表名
      tableIsPage: false, //是否分页
      isOpentForm: false, //是否打开了表单弹窗
      tableInportDialog: false, //导入弹窗
      tableQueryData: {}, //搜索实时条件
      tableQueryClickData: {}, //点击查询后的搜索条件
      tableOtherQueryData: {},//其他搜索条件(清除搜索不会清空)
      tableAdvancedQueryData: {}, //高级查询条件
      isTableCrud: false, //一开始隐藏表格
      subShowMenu: '', //子表是否显示操作按钮和操作列
      sortData: {
        column: 'id',
        order: 'desc',
      },
      // 表格table配置
      tableOption: {
        align: 'center',
        dialogDrag: true,
        dialogTop: '0%',
        dialogClickModal: false,
        delBtn: false,
        editBtn: false,
        dialogWidth: '80%',
        menuWidth: '200',
        border: true, //开启边框
        columnBtn: true, //隐藏列显示隐藏按钮
        saveBtn: false,
        cancelBtn: false,
        updateBtn: false,
        addBtn: true,
        header: true,
        search: false,
        searchMenuSpan: 6,
        searchMenuPosition: 'left',
        searchIndex: 3,
        searchIcon: true,
        searchShowBtn: false,
        expandRowKeys: [],
        column: [],
        lazy: true, //暂时只能先设置 avue
        selectable: (row, index) => {
          //通过增强配置是否可以选择
          if (
            this.customOnlineEnhanceJsName &&
            this.customOnlineEnhanceJsName.list.includes('selectable')
          ) {
            try {
              return this.customOnlineEnhanceJsList.selectable(row, index)
            } catch (error) {
              console.warn(error)
            }
          } else {
            return true
          }
        },
      },
      tableForm: {},
      tableColumnMoreButton: [
        {
          type: 'view',
          text: '详情',
          permissionName: 'moreViewBtn',
        },
        {
          type: 'del',
          text: '删除',
          permissionName: 'moreDelBtn',
        },
      ], //默认操作列按钮
      tableSelectData: [], //表格选中的数据
      tableSelectId: [], //表格选中的数据Id
      tableData: [], //表格数据
      tablePage: {}, //表格分页
      tableDataIsTree: false, //是否使用 树结构数据处理方式
      tableTreeParentIdName: '', //树结构数据 父id的key名
      tableTreeUnfoldName: '', //树结构数据展开列
      tableTreeChildern: '', //树结构数据是否有子集
      tableColumnDic: {},
      tableColumnItemForm: {},
      tableAllColumnRules: [], //所有校验规则
      columHeaderArr: [],//自定义表头搜索
      tableNeetRules: ['text', 'password', 'textarea', 'umeditor', 'markdown'], //可以校验的控件
      // 需要字典数据的控件
      viewListSelect: [
        'list',
        'radio',
        'checkbox',
        'switch',
        'list_multi',
        'pca',
      ],
      //需要字典数据的控件，并且以树形式展示
      viewListTree: ['sel_tree', 'cat_tree'],
      viewAllTreeKey: [],
      //是否有省市区控件
      isProvinces: false,
      //表单 单独占一行的控件
      fieldSpanOneLine: ['textarea', 'markdown', 'umeditor', 'image', 'file', 'monaco-editor'],
      //所有的省市区联动控件
      viewPcaArr: [],
      //所有markdown控件
      viewMarkdownArr: [],
      //所有文件控件
      viewFileArr: [],
      //所有联动控件
      viewLinkDownArr: [],
      // 所有联动控件关联字段
      viewLinkDownFieldArr: [],
      //联表查询字段
      uniteFormKeyObj: {},
      //所有联动控件字典
      viewLinkDownDicObj: {},
      viewFileNameObj: {}, //存储所有文件名
      viewPcaNameObj: {}, //存储所有省市区id对应的文本
      //需要自定义搜索的字典处理
      customSearchArr: [],
      //当前操作的列的数据
      currentRowDataObj: {},
      // 部门
      allDepartData: {}, //所有部门数据
      viewDepartControlArr: [], //所有部门控件
      // 用户
      viewUserControlArr: [], //所有用户控件
      //所有表格选择控件
      viewTableSelectArr: [],
      //所有的代码编辑器控件
      viewMonacoEditor: [],
      initSelfDefinedArr: [],
      viewMapArr: [], //所有地图控件
      allUserData: {}, //所有用户数据
      allUserObj: {},
      isTableInfo: false, //是否存在主附表
      // 父子表tabs切换配置
      tabsOption: {
        column: [],
      },
      tabsType: {}, //当前tabs
      listTabsOption: {
        column: [],
      },

      //自定义按钮
      customButtonTop: [], //表格顶部操作栏按钮
      customButtonLink: [], //表格操作列按钮
      customButtonFormEnd: [], //表单操作菜单 底部
      customButtonFormSide: [], //表单操作菜单 侧面

      dicAllData: {},
      //end 高级查询
      customOnlineEnhanceJsList: {}, //js增强list所有方法
      customOnlineEnhanceJsForm: {}, //js增强form所有方法
      //js增强方法名
      customOnlineEnhanceJsName: {
        list: [],
        form: [],
      },
      form: {
        getSelectOptions: null,
        changeOptions: null,
        triggleChangeValues: null,
        getAllFieldValue: null,
        getFieldValue: null,
      },
      otherPortId:false,//其他导入 导出 下载模板表id
      // 导入
      inportOption: {
        column: [
          {
            label: '',
            prop: 'inportexcel',
            type: 'upload',
            accept:
              ' application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
            span: 24,
            labelWidth: 0,
            labelslot: true,
            multiple: false,
            limit: 1,
            propsHttp: {
              res: 'data',
            },
            tip: '只能上传 xls / xlsx 文件',
          },
        ],
      },
      inportForm: {
        inportexcel: [],
        inportList: [],
      },

      //表单设计 配置值
      widgetFormPreview: {},
      isDialogFormDesign: false,
      isFormFullscreenDesign: false,
      formOpenType: 'add',
      formActionData: {
        type: 'formlist',
      },
      formBtnPermissions: {},
      allFormListData: {},
      dialogFormTitle: '新增',
      //erp
      tableErpRadioId: '', //当前erp主题选择数据的id
      tableErpRadioObj: {}, //当前erp主题选择数据

      erpControlsArr: [
        'text',
        'radio',
        'switch',
        'date',
        'datetime',
        'file',
        'image',
        'list_multi',
        'sel_search',
        'list',
        'popup',
        'sel_depart',
        'sel_user',
      ],
      //innerTable
      expandObj: {},
      listTabsType: {}, //列表tabs

      //树选择控件配置
      isTableTreeControl: false,
      tableTreeControlOption: {
        tableId: '',
        defaultTree: [],
        stopTree: [],
        isDialog: false,
        defaultProps: {},
        defaulKey: '',
        title: '',
        addType: {
          type: '',
          tableId: '',
        },
        asyncTableName: '',
      },
      //其他表单控件配置
      isFormViewControl: false,
      FormViewControlOption: {
        viewObj: {},
        formId: '',
        formOpenType: '',
        actionData: {},
        btnPermissions: {},
        customFun: () => { }
      },
      //组件配置
      isControlView: false,
      controlViewOption: {
        type: '',
        viewObj: {},
        params: {},
        defaultData: {},
        customFun: () => { }
      },
      // 资源审核
      isZyscControl: false,
      //表格选择控件配置
      isTableSelectControl: false,
      tableSelectControlOption: {
        title: '',
        isDialog: false,
        width: '',
        tableId: '',
        option: {},
        multiple: '',
        isPage: '',
        addType: {
          type: '',
          tableId: '',
          isCell: '',
        },
        submitFun: () => { },
      },
      //tabs显示控件
      isTabsView: false,
      tabsOptionData: {
        viewObj: {
          title: '',
          type: 'dialog',
          isShow: false,
          width: '90%',
        },
        tabsParams: {},
        openIndex: 0,
        tabsData: [],
      },
      //其他表格弹窗控件配置
      isTableView: false,
      tableViewOptionData: {
        viewObj: {},
        tableId: '',
        searchObj: {},
      },
      //子表是否需要js增强和自定义按钮
      subOpentJsEnhance: '',
      isInitEnhance: false, //判断增强是否初始化完毕

      // 大数据展示方式
      displayModeType: 'normal',
      currentStartIndex: 0,
      currentEndIndex: 50,
      isOpentAuthFocus: false, //是否开启自动聚焦
      authFocusObj: {
        //自动聚焦配置
        currBigDataTrEl: '',
        inputAttr: '',
      },
    }
  },
  filters: {
    // 时间格式化
    dateFilter (date, format) {
      date = new Date(date)
      format = format || 'yyyy-MM-dd hh:mm:ss'
      if (date != 'Invalid Date') {
        let o = {
          'M+': date.getMonth() + 1, //month
          'd+': date.getDate(), //day
          'h+': date.getHours(), //hour
          'H+': date.getHours(), //hour
          'm+': date.getMinutes(), //minute
          's+': date.getSeconds(), //second
          'q+': Math.floor((date.getMonth() + 3) / 3), //quarter
          S: date.getMilliseconds(), //millisecond
        }
        if (/(y+)/.test(format))
          format = format.replace(
            RegExp.$1,
            (date.getFullYear() + '').substr(4 - RegExp.$1.length)
          )
        for (let k in o)
          if (new RegExp('(' + k + ')').test(format))
            format = format.replace(
              RegExp.$1,
              RegExp.$1.length === 1
                ? o[k]
                : ('00' + o[k]).substr(('' + o[k]).length)
            )
        return format
      }
      return ''
    },
  },
  watch: {
    subShowMenu (val) {
      let arr = val.split('/')
      this.tabsOption.column = this.tabsOption.column.map((item) => {
        if (item.key == arr[0]) {
          item.showMenu = arr[1] == 'true' ? true : false
        }
        return item
      })
    },
    subOpentJsEnhance (val) {
      let arr = val.split('/')
      this.tabsOption.column = this.tabsOption.column.map((item) => {
        if (item.key == arr[0]) {
          item.opentJsEnhance = arr[1] == 'true' ? true : false
        }
        return item
      })
    },
    currMainDataId (val) {
      if (this.tableId) {
        if (val) {
          this.tableOption.header = true
        } else {
          this.tableOption.header = false
        }
        setTimeout(() => {
          this.initTableData()
        }, 300)
      }
    },
  },
  computed: {
    filteredData () {
      let list = this.tableData.filter((item, index) => {
        if (index < this.currentStartIndex) {
          return false
        } else if (index > this.currentEndIndex) {
          return false
        } else {
          return true
        }
      })
      return list
    },

  },
  props: {
    //erp附表id
    tableId: {
      type: String,
      default: '',
    },
    //不通过路由传递的表id
    tranTableId: {
      type: String,
      default: '',
    },
    currMainDataId: {
      type: String,
      default: '',
    },
    currMainDataObj: {
      type: Object,
      default: () => { },
    },
    foreignKeys: {
      type: Array,
      default: () => [],
    },
    tableType: {
      type: String,
      default: '',
    },
    //默认搜索值(表格初始化后只执行一次)
    defaultSearchObj: {
      //对象里面的值为空则不获取表格数据：{key:''}
      type: Object,
      default: () => { },
    },
    //默认搜索值（每次都执行）
    searchObj: {
      type: Object,
      default: () => { },
    },
    tableView: {
      type: Boolean,
      default: false,
    },
    hideHeader: {
      type: Boolean,
      default: true,
    },
    //其他页面或控件传递的方法方法
    transmitFun: {
      type: Function,
    },
    //其他参数
    otherParams: {
      type: Object,
      default: () => { },
    },
    //开启懒加载
    isLazy: {
      type: Boolean,
      default: false,
    }
  },
  methods: {
    //创建dom方法
    createDomFun (domName, htmlText, type = 'end', nextDom) {
      let timer = setInterval(() => {
        let dom = document.querySelector(domName)
        if (dom) {
          clearInterval(timer)
          let div = document.createElement('div')
          if (type == 'end') {
            dom.appendChild(div)
          } else {
            if (nextDom) {
              dom.insertBefore(div, nextDom)
            }
            dom.insertBefore(div, dom.childNodes[0])
          }
          div.style.width = "100%"
          div.innerHTML = htmlText
        }
      }, 300);

    }
  },
}
