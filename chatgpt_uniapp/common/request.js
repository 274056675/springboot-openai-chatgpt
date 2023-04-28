import {
	getStorage
} from './storage.js';
import unisite from './unisite';
import store from '@/store/index.js'
import Vue from 'vue'


function service(options = {}) {
	let replaceName = 'api'
	if (options.url.indexOf('/api') === 0) {
		replaceName = '/api'
	}
	if (options.url.indexOf('/api') === 0 || options.url.indexOf('api') === 0) {
		options.url = options.url.replace(replaceName, unisite.baseUrl)
	}
	const meta = (options.meta || {});
	options.header = {
		'content-type': 'application/json',
		...options.header,
	}
	// 判断本地是否存在token，如果存在则带上请求头
	if (getStorage({
			name: 'token'
		})) {
		options.header[unisite.tokenHeader] = `bearer ${getStorage({ name: 'token' })} `
	}
	if (meta.Authorization) {
		options.header['Authorization'] = `Basic ${meta.Authorization}`;
	} else {
		options.header['Authorization'] = `Basic ${unisite.Authorization}`;
	}
	//数据处理
	if (['GET', "get"].includes(options.method) && options.params) {
		let data = options.params
		if (!options.data) {
			options.data = {}
		}
		options.data = {
			...options.data,
			...data,
		}
	} else if (['POST', "post"].includes(options.method) && options.params) {
		let keyArr = Object.keys(options.params)
		if (keyArr.length > 0) {
			let urlStr = '&'
			if (options.url.indexOf('?') == -1) {
				urlStr = "?"
			}
			keyArr.forEach(item => {
				urlStr = urlStr + `${item}=${options.params[item]}&`
			})
			urlStr = urlStr.substring(0, urlStr.length - 1)
			options.url = options.url + urlStr
		}
	}
	return new Promise((resolved, rejected) => {
		options.success = (res) => {
			console.log(res)
			const status = res.data.code || res.statusCode;
			const msg = res.data.msg || res.errMsg
			if (status === 401) {
				store.dispatch('FedLogOut').then(() => {
					uni.redirectTo({
						url: "/pages/subPack/loginpc"
					})
				})
				rejected(res)
			} else if (status !== 200) {
				
				// 非成功状态码弹窗
				if(status!=404){
					uni.showToast({
						icon: 'none',
						duration: 2000,
						title: `${msg} `
					});
				}
				if (status == 404 && options.url.indexOf('cssz/list')!=-1 || options.url.indexOf('getWxUserInfo')!=-1) {
					uni.showToast({
						icon: 'none',
						duration: 2000,
						title: `系统正在维护中`
					});
					return false
				}
				if (status == 400 && res.data.msg == "登录已过期，请重新登录") {
					store.dispatch('FedLogOut').then(() => {
						uni.redirectTo({
							url: "/pages/subPack/loginpc"
						})
					})
					rejected(res)
				}
				// 返回错误信息
				rejected(res)
			} else {
				// 请求回来的状态码为200则返回内容
				resolved(res.data)
			}
		};
		options.fail = (err) => {
			rejected(err);
		};
		uni.request(options);
	});
}

export default service;
