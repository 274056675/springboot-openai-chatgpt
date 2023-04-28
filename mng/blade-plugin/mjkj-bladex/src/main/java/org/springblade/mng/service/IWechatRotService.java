package org.springblade.mng.service;

import org.springblade.mng.param.wechatrot.WechatUserParam;

/**
 * 微信机器人
 */
public interface IWechatRotService {

	//机器人发送消息
	String sendText(WechatUserParam param);

	//机器人发送消息
	String sendImage(WechatUserParam param, String size, boolean autoEnglish);
}
