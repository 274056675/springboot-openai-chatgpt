import request from '@/common/request.js';
import unisite from '@/common/unisite.js';
import {
	getStorage
} from '@/common/storage.js'

//登录
export const loginBySocial = (params, meta = {}) => request({
	url: '/api/blade-auth/oauth/token',
	method: 'post',
	meta,
	params,
})

//获取用户信息
export const getUserInfo = () => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/ai/getWxUserInfo`,
		method: 'get',
		params: {},
	})
}


//获取系统参数配置
export const getSysConifgApi = () => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/open/cssz/list`,
		method: 'get',
		params: {},
	})
}
//获取特定参数配置
// export const getSpecificConifgApi = () => {
// 	return request({
// 		url: `/api/${unisite.apiRequestHead}/chat/open/withdrawal/list`,
// 		method: 'get',
// 		params: {},
// 	})
// }

//发送问题
export const sendIssueApi = (data) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/ai/send/question`,
		method: 'post',
		data,
	})
}

//获取历史聊天记录
export const getHistoryListApi = (data) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/ai/message/history`,
		method: 'post',
		data,
	})
}

//获取商品列表
export const getGoodsListApi = () => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/open/goods/list`,
		method: 'get',
		params: {},
	})
}


//获取最新消息
export const getLastMessageApi = (data) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/ai/message/last`,
		method: 'post',
		data,
	})
}

//获取系统设置
export const getSettingDataApi = (params) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/ai/getWxUserSetting`,
		method: 'get',
		params,
	})
}
//翻译文本
export const translateMessageApi = (id) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/ai/translate?messageId=${id}`,
		method: 'post',
		data: {},
	})
}


// 发送短信验证码
export const sendMessageCode = (params) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/open/send/sms?phone=${params.phone}&movePosX=${params.movePosX}`,
		method: 'post',
		data: {},
	})
}

// 滑动验证参数获取
export const getSlideCode = (params) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/open/get/getImageCode?phone=${params.phone}&index=${params.index}`,
		method: 'get',
	})
}

// pc端微信登录二维码
export const getWxLogin = (data) => {
    return request({
        url: `/api/${unisite.apiRequestHead}/chat/open/wxopen/auth`,
        method: 'post',
        data,
    })
}

// pc登录code&state
export const getWxOpen = (params) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/open/getwxopen/userinfo?code=${params.code}&state=${params.state}&url=${params.url}`,
		method: 'get',
		params: {},
	})
}

// pc登录绑定手机号
export const bindPhoneApi = (data) => {
	return request({
		url: `/api/${unisite.apiRequestHead}/chat/open/bindPhoneByUnionId`,
		method: 'post',
		data,
	})
}

// 获取扫码支付参数
export const getWxPayOpen = (params) => {
    return request({
        url: `/api/${unisite.apiRequestHead}/chat/ai/buy/new/order?goodsId=${params.goodsId}&type=${params.type}`,
        method: 'get',
        data: {},
    })
}

// 获取扫码支付状态查询
export const getWxPayState = (params) => {
    return request({
        url: `/api/${unisite.apiRequestHead}/chat/open/pay/payResultSearch?orderCode=${params}`,
        method: 'get',
        data: {},
    })
}
