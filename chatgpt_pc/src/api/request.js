import axios from 'axios'
import store from '@/store/index'
import router from '@/router'
import website from '@/config/website'
import {  getStorage } from "@/utils/storage";
import { Message, Notification } from 'element-ui';


axios.defaults.timeout = 10 * 60 * 1000

// 请求拦截器
axios.interceptors.request.use(
  config => {
    // 每次发送请求之前判断vuex中是否存在token        
    // 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况
    // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
    if (getStorage({ name: 'token' })) {
      config.headers[website.tokenHeader] = `bearer ${getStorage({ name: 'token' })}`;
    }

    //headers判断是否需要
    const meta = (config.meta || {});
    if (meta.Authorization) {
      config.headers['Authorization'] = `Basic ${meta.Authorization}`
    } else {
      config.headers['Authorization'] = `Basic ${website.Authorization}`
    }

    return config;
  },
  error => {
    return Promise.error(error);
  })

// 响应拦截器
axios.interceptors.response.use(
  res => {
    // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据     
    // 否则的话抛出错误

    const status = res.data.code || res.status;
    if (res.status === 200) {
      console.log(res.data);
      return Promise.resolve(res.data);
    } else {
      return Promise.reject(res);
    }
  },
  error => {
    console.log('error>>>>>>>>>>>>>>>>', error)
    if (error.response && error.response.status) {
      switch (error.response.status) {
        // 401: 未登录
        // 未登录则跳转登录页面，并携带当前页面的路径
        // 在登录成功后返回当前页面，这一步需要在登录页操作。                
        case 401:
          Message({
            message: '请重新登录',
            type: 'error'
          });
          store.dispatch('FedLogOut').then(() => {
            router.replace({
              name: 'login'
            })
          });
          break;

        // 403 token过期
        // 登录过期对用户进行提示
        // 清除本地token和清空vuex中token对象
        // 跳转登录页面                
        case 403:
          Message({
            message: '登录过期，请重新登录',
            type: 'error'
          });
          // 清除token
          store.dispatch('FedLogOut').then(() => {
            router.replace({
              name: 'index'
            })
          });
          break;

        // 404请求不存在
        case 404:
          Message({
            message: '网络请求不存在',
            type: 'error'
          });
          break;
        case 405:
          Message({
            message: '请求不允许',
            type: 'error'
          });
          break;
        // 其他错误，直接抛出错误提示
        default:
          Message({
            message: error.response.data.message ? error.response.data.message : '请求错误',
            type: 'error'
          });
      }
      return Promise.reject(error.response);
    } else if (error.code === 'ECONNABORTED') {
      // store.dispatch('LogOut');
    } else {
      return Promise.reject(error);
    }
  })

export default axios;