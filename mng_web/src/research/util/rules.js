export const codeListRules = {
  'only': {
    pattern: 'only',
    msg: '',
    type: 'all'
  },
  'n6-16': {
    pattern: '^\\d{6,18}$',
    msg: '请输入6-16位的数字',
    type: ['int', 'Double', 'BigInt', 'BigDecimal']
  },
  '*6-16': {
    pattern: '^.{6,16}$',
    msg: '请输入6-16位任意字符',
    type: ['String', 'Text', 'Blob']
  },
  's6-18': {
    pattern: '^[a-z|A-Z]{6,18}$',
    msg: '请输入6-18位字母',
    type: ['String', 'Text']
  },
  'url': {
    pattern: '^((ht|f)tps?):\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:\\/~+#]*[\\w\\-@?^=%&\\/~+#])?$',
    msg: '请输入正规的网址',
    type: ['String', 'Text']
  },
  'm': {
    pattern: '^1[3456789]\\d{9}$',
    msg: '请输入正规的手机号码',
    type: ['String', 'Text']
  },
  'p': {
    pattern: '^[1-9]\\d{5}$',
    msg: '请输入正规的邮政编码',
    type: ['int', 'BigInt', 'String', 'Text']
  },
  's': {
    pattern: '[A-Z|a-z]+$',
    msg: '请输入字母',
    type: ['String', 'Text']
  },
  'n': {
    pattern: '^-?\\d+(\\.?\\d+|\\d?)$',
    msg: '请输入数字',
    type: ['int', 'Double', 'BigInt', 'BigDecimal']
  },
  // 'z': {
  //   pattern: 'z',
  //   msg: '请输入整数',
  //   type: ['String', 'Text']
  // },
  // '*': {
  //   pattern: '^.+$',
  //   msg: '该字段不能为空',
  // },
  'e': {
    pattern: '^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$',
    msg: '请输入正确格式的邮箱地址',
  },
  'money': {
    pattern: '^(([1-9][0-9]*)|([0]\\.\\d{0,2}|[1-9][0-9]*\\.\\d{0,5}))$',
    msg: '请输入正确的金额',
  },
}
