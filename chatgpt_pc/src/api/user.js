import request from './request'
import website from '@/config/website'


//修改用户信息
export const editInfoDataApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/update/wuserInfo`,
		method: 'post',
		data,
	})
}
//获取分享id
export const getInviteurlApi = () => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/get/inviteurl`,
		method: 'get',
		params: {},
	})
}


//获取分享id
export const getShareIdApi = () => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/open/get/shareUniqueCode`,
		method: 'get',
		params: {},
	})
}

//使用分享id
export const useShareIdApi = (id) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/open/check/shareUniqueCode?uniqueCode=${id}`,
		method: 'get',
		params: {},
	})
}

//确认点击分享
export const affirmShareIdApi = () => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/open/save/shareUniqueCode`,
		method: 'post',
		params: {},
	})
}

//生成推广二维码
export const generateQrCodeApi = () => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/generate/h5/qrcode`,
		method: 'get',
		params: {},
	})
}
//签到
export const getSignApi = () => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/sign`,
		method: 'post',
		data: {},
	})
}
//获取签到总数
export const getSignCouApi = () => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/get/signCou`,
		method: 'get',
		params: {},
	})
}

//获取系统设置
export const getSettingDataApi = (params) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/getWxUserSetting`,
		method: 'get',
		params,
	})
}
//修改系统设置
export const updataSettingDataApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/update/wuserSetting`,
		method: 'post',
		data,
	})
}

// 上传本地文件至服务器
// export const uploadImage = (tempFilePath) => {
// 	return new Promise(function(resolve, reject) {
// 		uni.uploadFile({
// 			url: `${website.baseUrl}/${website.apiRequestHead}/cgform-api/upload/file`,
// 			filePath: tempFilePath,
// 			name: 'file',
// 			header: {
// 				"Authorization": `Basic ${website.Authorization}`,
// 				'Blade-Auth': `bearer ${getStorage({ name: 'token' })} `
// 			},
// 			success: (uploadFileRes) => {
// 				try {
// 					const data = JSON.parse(uploadFileRes.data)
// 					if (data.code === 200) {
// 						resolve(data);
// 					} else {
// 						reject(data)
// 					}
// 				} catch (err) {
// 					reject(err)
// 				}
// 			},
// 			fail: (err) => {
// 				reject(err);
// 			}
// 		});
// 	})
// }
//清理聊天记录
export const deleteRecordApi = (params) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/open/delete/message`,
		method: 'get',
		data: {},
	})
}
//获取微信签名信息
export const getWxConfigApi = (url) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/open/get/jsapiTicket`,
		method: 'post',
		data:{
			url
		},
	})
}

// 查询积分明细
export const getCreditListApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/get/creditList`,
		method: 'post',
		data
	})
}
