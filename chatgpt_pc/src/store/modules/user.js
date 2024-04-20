import { setStorage, getStorage, removeStorage } from "@/utils/storage";
import { loginBySocialApi, getUserInfo, refreshTokenApi } from '@/api/system'
import { getSettingDataApi } from "@/api/user.js"

const user = {
    state: {
        tenantId: '000000',
        token: getStorage({ name: 'token' }) || '', //token
        isLogin: getStorage({ name: 'token' }) ? true : false, //是否登录
        userInfo: getStorage({ name: 'userInfo' }) || {}, //用户信息
        refreshToken: getStorage({ name: 'refreshToken' }) || '', //刷新token
        settingObj: getStorage({ name: 'settingObj' }) || {}, //个人设置数据
        isSign: false, // 是否签到
    },
    mutations: {
        // 设置租户
        SET_TENANT_ID(state, tenantId) {
            state.tenantId = tenantId
            setStorage({
                name: 'tenantId',
                content: state.tenantId
            })
        },
        // 设置token
        SET_TOKEN(state, token) {
            state.token = token;
            setStorage({
                name: 'token',
                content: state.token
            })
        },
        // 退出登录
        LOGIN_SUCCESS(commit, state) {
            state.token = '';
            removeStorage({ name: 'token' })
        },
        // 设置用户信息
        SET_USER_INFO(state, userInfo) {
            state.userInfo = userInfo
            setStorage({
                name: 'userInfo',
                content: state.userInfo
            })
        },
        SET_IS_LOGIN(state, isLogin) {
            state.isLogin = isLogin
        },
        // 设置刷新token
        SET_REFRESH_TOKEN(state, refreshToken) {
            state.refreshToken = refreshToken
            setStorage({
                name: 'refreshToken',
                content: state.refreshToken
            })
        },
        //存储用户个人设置
        SET_SETTING_OBJ(state, settingObj) {
            state.settingObj = settingObj
            setStorage({
                name: 'settingObj',
                content: state.settingObj
            })
        },
        // 设置签到
        SET_IS_SIGN(state, isSign) {
            state.isSign = isSign
        },
    },
    actions: {
        LoginBySocial({ commit, dispatch, state }, userInfo) {
            return new Promise((resolve, reject) => {
               
                loginBySocialApi(userInfo.params, userInfo.meta).then(async res => {
                    const data = res;
                    commit('SET_TOKEN', data.data.accessToken);
                    commit('SET_REFRESH_TOKEN', data.data.refreshToken);
                    commit('SET_TENANT_ID', data.data.tenantId);
                    commit('SET_IS_LOGIN', true)
                    await dispatch('getUserInfoActions')
        
                    resolve(data.data);
                }).catch(error => {
                    reject(error)
                })
            })
        },
        //获取用户信息
        getUserInfoActions({ commit, state }) {
            return new Promise((resolve) => {
                if (state.isLogin) {
                    getUserInfo().then(userInfoRes => {
                        commit('SET_USER_INFO', userInfoRes.data);
                        resolve()
                    }).catch((err) => {
                        resolve()
                    })
                } else {
                    resolve()
                }
            })
        },

        //清空登录
        FedLogOut({ commit }) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '');
                commit('SET_IS_LOGIN', false);
                commit('SET_USER_INFO', {})
                resolve();
            })
        },
        //退出登录
        LogOut({ commit }) {

            commit('SET_TOKEN', '');
            commit('SET_IS_LOGIN', false);
            commit('SET_USER_INFO', {})
            commit('SET_REFRESH_TOKEN', '')

        },
        //刷新token
        refreshToken({ state, commit, dispatch }, userInfo) {
            return new Promise((resolve, reject) => {
                refreshTokenApi(state.refreshToken, state.tenantId).then(async res => {
                    let data = res
                    commit('SET_TOKEN', data.data.accessToken);
                    commit('SET_REFRESH_TOKEN', data.data.refreshToken);
                    await dispatch('getUserInfoActions')
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        //获取用户个人设置
        getSettingDataActions({
            commit
        }) {
            return new Promise((resolve) => {
                getSettingDataApi().then(res => {
                    commit('SET_SETTING_OBJ', res.data);
                    resolve()
                }).catch(() => {
                    resolve()
                })
            })
        },
    },
}

export default user