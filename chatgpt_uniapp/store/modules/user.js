import {
    setStorage,
    getStorage
} from "@/common/storage.js"
import {
    loginBySocial,
    getUserInfo,
    getSettingDataApi,
} from "@/api/user.js"
import {
    resolve
} from "../../common/mqtt.min"

const user = {
    state: {
        tenantId: '000000', //租户
        userInfo: getStorage({
            name: 'userInfo'
        }) || {}, //用户信息
        token: getStorage({
            name: 'token'
        }) || '', //token
        refreshToken: getStorage({
            name: 'refreshToken'
        }) || '', //刷新token
        mqttKey: getStorage({
            name: 'mqttKey'
        }) || '',
        sysConfig: getStorage({
            name: 'sysConfig'
        }) || {}, //系统的配置参数
        specificConfig: getStorage({
            name: 'specificConfig'
        }) || {},
        isLogin: getStorage({ name: 'token' }) ? true : false, //是否登录
        currMqttMsg: {}, //当前mqtt接收到的消息
        reconMqttTime: '', //重新连接时的时间
        settingObj: getStorage({
            name: 'settingObj'
        }) || {}, //个人设置数据
    },
    actions: {
        //根据第三方信息登录
        LoginBySocial({
            commit,
            dispatch,
            state
        }, userInfo) {
            return new Promise((resolve, reject) => {
                loginBySocial(userInfo.params, userInfo.meta).then(async res => {
                    const data = res;
                    commit('SET_TOKEN', data.access_token);
                    commit('SET_REFRESH_TOKEN', data.refresh_token);
                    commit('SET_TENANT_ID', data.tenant_id);
                    await dispatch('getUserInfoActions')
                    await dispatch('getSettingDataActions')
                    let settingData = await getSettingDataApi()
                    commit('SET_MQTT_KEY', `chatgpt_${state.userInfo.chatCode}`);
                    resolve();
                }).catch(error => {
                    console.log(error)
                    reject(error)
                })
            })
        },
        //获取并设置用户信息
        getUserInfoActions({
            commit
        }) {
            return new Promise((resolve) => {
                getUserInfo().then(userInfoRes => {
                    commit('SET_USER_INFO', userInfoRes.data);
                    resolve()
                }).catch(() => {
                    resolve()
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

        //刷新token
        refreshToken({
            state,
            commit
        }, userInfo) {
            return new Promise((resolve, reject) => {
                refreshTokenApi(state.refreshToken, state.tenantId).then(res => {
                    let data = res
                    commit('SET_USER_INFO', data)
                    commit('SET_TOKEN', data.access_token)
                    commit('SET_REFRESH_TOKEN', data.refresh_token)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        //清空登录
        FedLogOut({
            commit
        }) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '');
                commit('SET_IS_LOGIN', false);
                commit('SET_MQTT_KEY', '')
                commit('SET_USER_INFO', {})
                resolve();
            })
        },
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
        // 设置用户信息
        SET_USER_INFO(state, userInfo) {
            state.userInfo = userInfo
            setStorage({
                name: 'userInfo',
                content: state.userInfo
            })
        },
        // 设置token
        SET_TOKEN(state, token) {
            state.token = token
            setStorage({
                name: 'token',
                content: state.token
            })
        },
        // 设置刷新token
        SET_REFRESH_TOKEN(state, refreshToken) {
            state.refreshToken = refreshToken
            setStorage({
                name: 'refreshToken',
                content: state.refreshToken
            })
        },
        SET_MQTT_KEY(state, mqttKey) {
            state.mqttKey = mqttKey
            setStorage({
                name: 'mqttKey',
                content: state.mqttKey
            })
        },
        //将系统配置存储到本地
        SET_SYS_CONFIG(state, sysConfig) {
            state.sysConfig = sysConfig
            setStorage({
                name: 'sysConfig',
                content: state.sysConfig
            })
        },
        SET_SPECIFIC_CONFIG(state, specificConfig) {
            state.specificConfig = specificConfig
            setStorage({
                name: 'specificConfig',
                content: state.specificConfig
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
        SET_IS_LOGIN(state, isLogin) {
            state.isLogin = isLogin
        },
        SET_CURR_MQTT_MSG(state, currMqttMsg) {
            state.currMqttMsg = currMqttMsg
        },
        SET_RECON_MQTT_TIME(state, reconMqttTime) {
            state.reconMqttTime = reconMqttTime
        },
    }
}
export default user