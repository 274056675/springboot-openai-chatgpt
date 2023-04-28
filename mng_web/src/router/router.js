/**
 * 全站路由配置
 *
 * meta参数说明
 * keepAlive是否缓冲页面
 * isTab是否加入到tag导航
 * isAuth是否需要授权
 */
import Vue from 'vue';
import VueRouter from 'vue-router';
import PageRouter from './page/' // 页面路由
import ViewsRouter from './views/' // 页面路由
import AvueRouter from './avue-router'; //封装的路由控制方法
import i18n from '@/lang' // Internationalization 国际化 多语言
import Store from '../store/'; // vuex
Vue.use(VueRouter)
//创建路由
export const createRouter = () => new VueRouter({
  routes: [...PageRouter, ...ViewsRouter]
})
const Router = createRouter() // 获得 route 实例
// 初始化和注册 AvueRouter
AvueRouter.install(Vue, {
  router: Router,
  store: Store,
  i18n: i18n,
  keepAlive: false,
});
Router.$avueRouter.formatRoutes(Store.state.user.menuAll, true); // 动态路由核心方法
Router.addRoutes([...PageRouter, ...ViewsRouter]);
export function resetRouter () {  // 重置路由 比如用于身份验证失败，需要重新登录时 先清空当前的路有权限
  const newRouter = createRouter()
  Router.matcher = newRouter.matcher // reset router
  AvueRouter.install(Vue, {
    router: Router,
    store: Store,
    i18n: i18n,
    keepAlive: false,
  });
}
export default Router
