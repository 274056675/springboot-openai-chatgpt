const getters = {
  // user
	tenantId: state => state.user.tenantId,
	userInfo: state => state.user.userInfo,
	token: state => state.user.token,
	refreshToken: state => state.user.refreshToken, 
	isLogin: state => state.user.isLogin,
	inviteCode: state => state.user.inviteCode,
	shareId: state => state.user.shareId,
	settingObj: state => state.user.settingObj,
	commissionData: state => state.user.commissionData,
	isSign: state => state.user.isSign,


	//other
	sysConfig: state => state.other.sysConfig,

}
export default getters
