import request from '@/api/request.js';
import website from '@/config/website';

//生成图片
export const getAiImageApi = (data) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/drawimage/send/image`,
		method: 'post',
		data,
	})
}
//发布审核
export const issueApi = (id, index) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/drawimage/publish/image?tpId=${id}`,
		method: 'get',
		params: {},
	})
}

//获取社区
export const getCommunityList = (params) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/open/get/community/image?size=${params.size}&current=${params.current}&type=${params.type}&name=${params.name}`,
		method: 'get',
		params: {},
	})
}

//获取历史作品记录
export const getHistoryList = (params) => {
	return request({
		url: `/api/${website.apiRequestHead}/chat/drawimage/get/image/history?size=${params.size}&current=${params.current}`,
		method: 'get',
		params: {},
	})
}
