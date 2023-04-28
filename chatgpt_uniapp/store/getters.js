const getters = {
	tenantId: state => state.user.tenantId, //租户
	userInfo: state => state.user.userInfo, //用户信息
	token: state => state.user.token, //token
	refreshToken: state => state.user.refreshToken, //刷新token
	mqttKey: state => state.user.mqttKey, //mqttKey
	isLogin: state => state.user.isLogin, //是否登录
	currMqttMsg: state => state.user.currMqttMsg, //mqtt主题消息
	reconMqttTime: state => state.user.reconMqttTime, //查找未收到信息的时间
	sysConfig: state => state.user.sysConfig, //查找未收到信息的时间
	specificConfig: state => state.user.specificConfig, //查找未收到信息的时间
	settingObj: state => state.user.settingObj,
}
export default getters