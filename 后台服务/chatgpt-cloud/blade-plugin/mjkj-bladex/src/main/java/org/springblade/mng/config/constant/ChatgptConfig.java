package org.springblade.mng.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "chatgpt")
public class ChatgptConfig {
	//是否调试
	private static String debug;

	//美国服务器请求地址
	private static String httpUrl;

	//chatgpt请求地址
	private static String chatgptUrl;

	//模型
	private static String chatgptModel;

	//文本长度
	private static Integer chatgptMaxToken;

	//请求最大长度
	private static Integer chatgptRequestMaxToken;

	// 文本的多样性 默认是1
	private static Integer chatgptTopP;

	//上传路径
	private static String uploadUrl;

	//背景图片
	private static String posterBgUrl;

	//移除表头
	private static String startTitleRemove;

	//个人提示
	private static String wechatRotGeRenTip;

	//机器人名称
	private static String wechatRotGeRenName;

	//机器人群回复字数长度
	private static Integer wechatRotQumMaxLen;

	//机器人群回复提示
	private static String wechatRotQumTip;


	//微信开放平台相关
	private static String openWxClientId;
	private static String openWxClientSecret;

	//logo地址
	private static String logoUrl;

	//图片模型 chatgpt   flagstudio
	private static String imageModel;
	private static String imageModelWechatRot;
	private static String flagstudioToken;

	//MidJourney的Token
	private static String midJourneyToken;

	//MidJourney的URL

	private static String baiduImageAppkey;

	private static String baiduImageSecretKey;

	//mj绘画的url
	private static String midJourneyUrl;

	//Stable Diffusion的Key
	private static String sdKey;

	//画图指令
	private static String imagePromptStart;
	private static String imagePromptEnd;
	private static String imageStyle;

	//默认头像，默认名字
	private static String defaultLogo;
	private static String defaultUserName;

	//机器人群名
	private static String wechatBotGroupName;
	public static String getDebug() {
		return debug;
	}

	public  void setDebug(String debug) {
		ChatgptConfig.debug = debug;
	}

	public static String getHttpUrl() {
		return httpUrl;
	}

	public  void setHttpUrl(String httpUrl) {
		ChatgptConfig.httpUrl = httpUrl;
	}

	public static String getChatgptUrl() {
		return chatgptUrl;
	}

	public  void setChatgptUrl(String chatgptUrl) {
		ChatgptConfig.chatgptUrl = chatgptUrl;
	}

	public static String getChatgptModel() {
		return chatgptModel;
	}

	public  void setChatgptModel(String chatgptModel) {
		ChatgptConfig.chatgptModel = chatgptModel;
	}

	public static Integer getChatgptMaxToken() {
		return chatgptMaxToken;
	}

	public  void setChatgptMaxToken(Integer chatgptMaxToken) {
		ChatgptConfig.chatgptMaxToken = chatgptMaxToken;
	}

	public static Integer getChatgptTopP() {
		return chatgptTopP;
	}

	public  void setChatgptTopP(Integer chatgptTopP) {
		ChatgptConfig.chatgptTopP = chatgptTopP;
	}

	public static String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		ChatgptConfig.uploadUrl = uploadUrl;
	}

	public static String getPosterBgUrl() {
		return posterBgUrl;
	}

	public void setPosterBgUrl(String posterBgUrl) {
		ChatgptConfig.posterBgUrl = posterBgUrl;
	}

	public static String getStartTitleRemove() {
		return startTitleRemove;
	}

	public void setStartTitleRemove(String startTitleRemove) {
		ChatgptConfig.startTitleRemove = startTitleRemove;
	}

	public static Integer getChatgptRequestMaxToken() {
		return chatgptRequestMaxToken;
	}

	public void setChatgptRequestMaxToken(Integer chatgptRequestMaxToken) {
		ChatgptConfig.chatgptRequestMaxToken = chatgptRequestMaxToken;
	}

	public static String getWechatRotGeRenTip() {
		return wechatRotGeRenTip;
	}

	public void setWechatRotGeRenTip(String wechatRotGeRenTip) {
		ChatgptConfig.wechatRotGeRenTip = wechatRotGeRenTip;
	}

	public static String getWechatRotGeRenName() {
		return wechatRotGeRenName;
	}

	public void setWechatRotGeRenName(String wechatRotGeRenName) {
		ChatgptConfig.wechatRotGeRenName = wechatRotGeRenName;
	}

	public static String getOpenWxClientId() {
		return openWxClientId;
	}

	public void setOpenWxClientId(String openWxClientId) {
		ChatgptConfig.openWxClientId = openWxClientId;
	}

	public static String getOpenWxClientSecret() {
		return openWxClientSecret;
	}

	public void setOpenWxClientSecret(String openWxClientSecret) {
		ChatgptConfig.openWxClientSecret = openWxClientSecret;
	}

	public static String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		ChatgptConfig.logoUrl = logoUrl;
	}

	public static String getImageModel() {
		return imageModel;
	}

	public  void setImageModel(String imageModel) {
		ChatgptConfig.imageModel = imageModel;
	}

	public static String getFlagstudioToken() {
		return flagstudioToken;
	}

	public  void setFlagstudioToken(String flagstudioToken) {
		ChatgptConfig.flagstudioToken = flagstudioToken;
	}

	public static String getImageModelWechatRot() {
		return imageModelWechatRot;
	}

	public void setImageModelWechatRot(String imageModelWechatRot) {
		ChatgptConfig.imageModelWechatRot = imageModelWechatRot;
	}

	public static String getImagePromptStart() {
		return imagePromptStart;
	}

	public void setImagePromptStart(String imagePromptStart) {
		ChatgptConfig.imagePromptStart = imagePromptStart;
	}

	public static String getImagePromptEnd() {
		return imagePromptEnd;
	}

	public void setImagePromptEnd(String imagePromptEnd) {
		ChatgptConfig.imagePromptEnd = imagePromptEnd;
	}

	public static String getImageStyle() {
		return imageStyle;
	}

	public void setImageStyle(String imageStyle) {
		ChatgptConfig.imageStyle = imageStyle;
	}

	public static String getDefaultLogo() {
		return defaultLogo;
	}

	public void setDefaultLogo(String defaultLogo) {
		ChatgptConfig.defaultLogo = defaultLogo;
	}

	public static String getDefaultUserName() {
		return defaultUserName;
	}

	public void setDefaultUserName(String defaultUserName) {
		ChatgptConfig.defaultUserName = defaultUserName;
	}

	public static Integer getWechatRotQumMaxLen() {
		return wechatRotQumMaxLen;
	}

	public void setWechatRotQumMaxLen(Integer wechatRotQumMaxLen) {
		ChatgptConfig.wechatRotQumMaxLen = wechatRotQumMaxLen;
	}

	public static String getWechatRotQumTip() {
		return wechatRotQumTip;
	}

	public void setWechatRotQumTip(String wechatRotQumTip) {
		ChatgptConfig.wechatRotQumTip = wechatRotQumTip;
	}

	public static String getMidJourneyToken() {
		return midJourneyToken;
	}

	public  void setMidJourneyToken(String midJourneyToken) {
		ChatgptConfig.midJourneyToken = midJourneyToken;
	}

	public static String getSdKey() {
		return sdKey;
	}

	public  void setSdKey(String sdKey) {
		ChatgptConfig.sdKey = sdKey;
	}

	public void setMidJourneyUrl(String midJourneyUrl) {
		ChatgptConfig.midJourneyUrl = midJourneyUrl;
	}
	public static String getMidJourneyUrl() {
		return midJourneyUrl;
	}

	public static String getBaiduImageAppkey() {
		return baiduImageAppkey;
	}

	public  void setBaiduImageAppkey(String baiduImageAppkey) {
		ChatgptConfig.baiduImageAppkey = baiduImageAppkey;
	}

	public static String getBaiduImageSecretKey() {
		return baiduImageSecretKey;
	}

	public  void setBaiduImageSecretKey(String baiduImageSecretKey) {
		ChatgptConfig.baiduImageSecretKey = baiduImageSecretKey;
	}

	public static String getWechatBotGroupName() {
		return wechatBotGroupName;
	}

	public  void setWechatBotGroupName(String wechatBotGroupName) {
		ChatgptConfig.wechatBotGroupName = wechatBotGroupName;
	}
}
