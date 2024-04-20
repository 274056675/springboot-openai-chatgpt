import Vue from 'vue'
import VueRouter from 'vue-router'
import { getStorage } from '@/utils/storage'

// 解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.replace;

// push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch(err => err);
};

//replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalReplace.call(this, location, onResolve, onReject);
  return originalReplace.call(this, location).catch(err => err);
};

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'index',
    redirect: "index/piazza",
    component: () => import(/* webpackChunkName: "index" */ '../views/index.vue'),
    children: [
      {
        path: '/index/piazza',
        name: 'piazza',
        component: () => import(/* webpackChunkName: "piazza" */ '../views/piazza.vue'),
      },
      {
        path: '/index/chat',
        name: 'chat',
        component: () => import(/* webpackChunkName: "chat" */ '../views/chat.vue'),
      },

      {
        path: '/index/handbook',
        name: 'handbook',
        component: () => import(/* webpackChunkName: "handbook" */ '../views/handbook.vue'),
      },
      {
        path: '/index/inform',
        name: 'inform',
        component: () => import(/* webpackChunkName: "inform" */ '../views/inform.vue'),
      },
      {
        path: '/index/user',
        name: 'user',
        component: () => import(/* webpackChunkName: "user" */ '../views/user.vue'),
        children: [
          {
            path: '/index/user/equity-center',
            name: 'equity-center',
            meta: {
              requireAuth: true,
            },
            component: () => import(/* webpackChunkName: "equity-center" */ '../views/user/equity-center.vue'),
          },
          {
            path: '/index/user/userinfo',
            name: 'userinfo',
            meta: {
              requireAuth: true,
            },
            component: () => import(/* webpackChunkName: "userinfo" */ '../views/user/userinfo.vue'),
          },
          {
            path: '/index/user/share',
            name: 'share',
            meta: {
              requireAuth: true,
            },
            component: () => import(/* webpackChunkName: "share" */ '../views/user/share.vue'),
          },
          {
            path: '/index/user/setting',
            name: 'setting',
            meta: {
              requireAuth: true,
            },
            component: () => import(/* webpackChunkName: "setting" */ '../views/user/setting.vue'),
          },
          {
            path: '/index/user/rl-detail',
            name: 'rl-detail',
            meta: {
              requireAuth: true,
            },
            component: () => import(/* webpackChunkName: "rl-detail" */ '../views/user/rl-detail.vue'),
          },
          {
            path: '/index/user/platform',
            name: 'platform',
            component: () => import(/* webpackChunkName: "platform" */ '../views/user/platform.vue'),
          },
        ]
      },
    ]
  },
  {
    path: '/bindphone',
    name: 'bindphone',
    component: () => import(/* webpackChunkName: "bindphone" */ '../views/bindphone.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "login" */ '../views/login.vue'),
  }
]

const router = new VueRouter({
  mode:"history",
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {     // 判断该路由是否需要登录权限
    let token = getStorage({ name: 'token' })
    if (token) {              // 通过localStorage.getItem()获取当前的 token 是否存在
      next()
    }
    else {
      next({
        path: '/login',
        query: {
          redirect: to.fullPath    // 将跳转的路由path作为参数，登录成功后跳转到该路由
        }
      })
    }
  }
  else {
    next()
  }
})

export default router
