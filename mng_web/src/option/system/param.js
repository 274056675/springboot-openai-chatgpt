export default {
  /*  height: 'auto',
   calcHeight: 30, */
  tip: false,
  searchShow: true,
  searchMenuSpan: 6,
  border: true,
  index: true,
  selection: true,
  viewBtn: true,
  dialogClickModal: false,
  column: [
    {
      label: "参数名称",
      prop: "paramName",
      search: true,
      span: 24,
      rules: [{
        required: true,
        message: "请输入参数名称",
        trigger: "blur"
      }]
    },
    {
      label: "参数键名",
      prop: "paramKey",
      search: true,
      span: 24,
      rules: [{
        required: true,
        message: "请输入参数键名",
        trigger: "blur"
      }]
    },
    {
      label: "参数键值",
      prop: "paramValue",
      type: "textarea",
      span: 24,
      minRows: 6,
      rules: [{
        required: true,
        message: "请输入参数键值",
        trigger: "blur"
      }]
    }
  ]
};
