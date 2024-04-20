import Layout from '@/page/index/'

export default [
  {
    path: '/tool/codetestlist',
    component: Layout,
    children: [{
      path: ":id",
      name: 'AUTO在线表单',
      component: () =>
        import( /* webpackChunkName: "views" */ '@/research/views/tool/codetestlist.vue'),
      props: true
    }]
  },
  {
    path: '/tool/codetesttabs',
    component: Layout,
    children: [{
      path: ":code",
      name: 'AUTO在线表单tabs',
      component: () =>
        import( /* webpackChunkName: "views" */ '@/research/views/tool/codetesttabs.vue'),
      props: true
    }]
  }
]