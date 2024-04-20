import request from './request'
import website from '@/config/website'
import { setStorage, getStorage, removeStorage } from "@/utils/storage";

//登陆接口
export const loginBySocialApi = (params, meta = {}) => request({
    url: '/api/blade-auth/token',
    method: 'post',
    meta,
    params,
})



export const refreshTokenApi = (refresh_token, tenantId) => request({
    url: '/api/blade-auth/token',
    method: 'post',
    headers: {
        'Tenant-Id': tenantId,
    },
    params: {
        tenantId,
        refresh_token,
        grantType: "refresh_token",
        scope: "all",
    }
});

//注销
export const cancelUserApi = () => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/ai/del/wxuser`,
        method: 'post',
        params: {},
    })
}


//获取用户信息，会自动携带token
export const getUserInfo = () => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/ai/getWxUserInfo`,
        method: 'get',
        params: {},
    })
}

// 发送短信验证码
export const sendMessageCode = (params) => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/send/sms?phone=${params.phone}&random=${params.random}`,
        method: 'post',
        data: {},
    })
}

// 滑动验证参数获取
export const getSlideCode = (params) => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/get/getImageCode?phone=${params.phone}&index=${params.index}&type=${params.type}`,
        method: 'get',
    })
}

//上传本地文件至服务器
export const uploadImage = (data) => {
        return request({
            url: `/api/${website.apiRequestHead}/cgform-api/upload/file`,
            method: 'post',
            header: {
                "Authorization": `Basic ${website.Authorization}`,
                'Blade-Auth': `bearer ${getStorage({ name: 'token' })} `
            },
            data
        })
    }
    // export const uploadImage = (tempFilePath,typeName) => {
    // 	return new Promise(function(resolve, reject) {
    // 		uni.uploadFile({
    // 			url: `${website.baseUrl}/${website.apiRequestHead}/cgform-api/upload/file`,
    // 			filePath: tempFilePath,
    // 			name: 'file',
    // 			header: {
    // 				"Authorization": `Basic ${website.Authorization}`,
    // 				'Blade-Auth': `bearer ${getStorage({ name: 'token' })} `
    // 			},
    // 			// #ifdef H5
    // 			formData: {
    // 				type: typeName
    // 			},
    // 			// #endif
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


//获取系统参数配置
export const getSysConifgApi = () => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/cssz/list`,
            method: 'get',
            params: {},
        })
    }

    // pc登录code&state
export const getWxOpen = (params) => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/getwxopen/userinfo?code=${params.code}&state=${params.state}`,
        method: 'get',
        params: {},
    })
}

// pc登录绑定手机号
export const bindPhoneApi = (data) => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/bindPhoneByUnionId`,
        method: 'post',
        data,
    })
}

//获取微信签名信息
export const getWxConfigApi = (url) => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/get/jsapiTicket`,
        method: 'post',
        data: {
            url
        },
    })
}

// 工具页面列表参数
export const getToolData = (params) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/tool/getAllList`,
            method: 'get',
            params: {},
        })
    }
    // 搜索工具
export const getSearchApi = (params) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/tool/getSearch?id=${params.id}&funName=${params.val}&time=${params.time}`,
            method: 'get',
            params: {},
        })
    }
    // 热门工具
export const getHotApi = (params) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/tool/gethotList`,
            method: 'get',
            params: {},
        })
    }
    // 首页工具
export const getIndexMenuApi = (params) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/indexMenu`,
            method: 'get',
            params: {},
        })
    }
    // 首页服务编辑
export const editIndexMenu = (data) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/index/updateMenuUser`,
            method: 'post',
            data,
        })
    }
    // 获取首页菜单
export const getIndexMenu = (params) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/index/getMenuUser?ids=${params}`,
            method: 'get',
            params: {},
        })
    }
    // 首页消息
export const getNoticeApi = (params) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/get/notice?id=${params}`,
            method: 'get',
        })
    }
    // 模型类型
export const getAllModelApi = () => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/getAllModel`,
        method: 'get',
    })
}

// 获取新手指南数据
export const getContentApi = () => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/get/Content`,
        method: 'get',
    })
}

// 获取消息公告列表
export const getNoticeListApi = (data) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/get/notice`,
            method: 'post',
            data
        })
    }
    // 获取未观看过消息公告列表
export const getNoticeNocheckApi = (data) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/get/noticeNocheck`,
            method: 'post',
            data
        })
    }
    // 获取未观看过消息公告列表
export const getNoticeContentApi = (id) => {
        return request({
            url: `/api/${website.apiRequestHead}/chat/open/get/noticeContent?id=${id}`,
            method: 'get'
        })
    }
    // 消息公告列表全部已读
export const getAllcheckApi = () => {
    return request({
        url: `/api/${website.apiRequestHead}/chat/open/get/allcheck`,
        method: 'get'
    })
}