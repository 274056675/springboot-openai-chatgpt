import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import hljs from 'highlight.js'
import 'default-passive-events'
import 'github-markdown-css/github-markdown-light.css'
import 'highlight.js/styles/googlecode.css'

Vue.config.productionTip = false

Vue.use(ElementUI)
import { Message } from 'element-ui'
// 挂载到$message上
Vue.prototype.$message = Message

import { Loading } from 'element-ui';
let loading;

import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, axios)

// 公告列表 页面挂载
import announcementVue from '@/components/announcement/announcement'
Vue.prototype.$announcementVue = announcementVue
// this.$announcementVue({dialogVisible: true}) 使用方法

Vue.prototype.$showLoading = function (msg) {
  loading = Loading.service({
    lock: true,
    text: msg.text + ' ...',
  });
}

Vue.prototype.$hideLoading = function (msg) {
  loading.close()
  if (msg) {
    this.$message({
      message: msg.message ? msg.message : '',
      type: msg.type ? msg.type : ''
    })
  }
}

Vue.directive('highlight', (el) => {
  let blocks = el.querySelectorAll('pre code')
  blocks.forEach((block) => {
    delete block.dataset.highlighted;
    hljs.highlightBlock(block)
  })
})

// 浏览器窗口,这个地方值不会变,你任意拉扯浏览器也不会改变,获取的是你打开项目时的初始状态
Vue.prototype.width = window.innerWidth;
Vue.prototype.height = window.innerHeight;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
