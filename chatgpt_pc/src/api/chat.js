import request from '@/api/request.js';
import website from '@/config/website';
/**
 * 聊天页面
 */
//发送聊天信息
export const sendChatIssueApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/send/question`,
		method: 'post',
		data,
	})
}

//获取历史聊天记录
export const getChatHistoryListApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/message/history`,
		method: 'post',
		data,
	})
}

//获取最新的聊天记录
export const getChatLastMessageApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/message/last`,
		method: 'post',
		data,
	})
}

//清理聊天记录
export const deleteRecordApi = (params) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/open/delete/message`,
		method: 'get',
		data: {},
	})
}

/**
 * 工具 聊天(更多好玩)
 */
//发送内容
export const sendMoreDataApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/send/moreFun`,
		method: 'post',
		data,
	})
}

//获取当前类型上一次的回复
export const getLastHistoryDataApi = (funFataId) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/get/lastMoreFunLog?funFataId=${funFataId}`,
		method: 'get',
		params: {},
	})
}

// 语音转文字
// export const getText = (filePath) => {
// 	return new Promise(function(resolve, reject) {
// 		uni.uploadFile({
// 			url: `${website.baseUrl}/${website.apiRequestHead}/chat/ai/voice/text`,
// 			filePath,
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
// 				console.log(28347982598459)
// 				reject(err);
// 			}
// 		});
// 	})
// }


// 聊天列表接口
export const getChatListAPI = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/get/chat`,
		method: 'post',
		data
	})
}

// 删除单个聊天列表
export const deleteChatItemAPI = (chatListId) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/delete/chat?chatListId=${chatListId}`,
		method: 'get',
	})
}

// 删除单个聊天列表
export const deleteAllChatListAPI = () => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/delete/allchatList`,
		method: 'get'
	})
}

// 收藏记录
export const storeApi = (params) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/store?messageId=${params.messageId}&type=${params.type}`,
		method: 'get'
	})
}

//收藏记录列表
export const storeListApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/store/list`,
		method: 'post',
		data
	})
}
//工具类历史记录
export const getfunhistoryApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/ai/get/funhistory`,
		method: 'post',
		data
	})
}