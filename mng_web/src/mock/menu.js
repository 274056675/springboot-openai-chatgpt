import Mock from 'mockjs'

const top = [{
  label: "首页",
  path: "/wel/index",
  icon: 'el-icon-menu',
  meta: {
    i18n: 'dashboard',
  },
  parentId: 0
},
  {
    label: "bladex官网",
    icon: 'el-icon-document',
    meta: {
      i18n: 'website',
    },
    path: "https://bladex.vip/#/",
    parentId: 1
  },
  {
    label: "avuex官网",
    icon: 'el-icon-document',
    meta: {
      i18n: 'avuexwebsite',
    },
    path: "https://avuex.avue.top",
    parentId: 2
  },
  {
    label: "测试",
    icon: 'el-icon-document',
    path: "/test/index",
    meta: {
      i18n: 'test',
    },
    parentId: 3
  }]
export default ({mock}) => {
  if (!mock) return;
  Mock.mock('/user/getTopMenu', 'get', () => {
    return {
      data: top
    }
  })

}
