package org.springblade.mng.utils.ltsms;

import lombok.Data;

@Data
public class TextMessage {
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 企业编号
	 */
	private String enterpriseId;

	/**
	 * 短信模板id
	 */
	private String tempId;
	/**
	 * 企业的账号
	 */
	private String tempAccount;
	/**
	 * 加密规则
	 */
	private String tempCer;
	/**
	 * 消息类型：默认填7
	 */
	private String msgType ="7";
	/**
	 * 消息内容：要发送的短信内容，并且对内容进行UrlEncode编码
	 */
	private String tempArgs;
	/**
	 * 主叫号码：默认填111
	 */
	private String Caller="111";
	/**
	 * 时间戳
	 */
	private String timestamp;

	public String toUrlencode() {
		return "phone=" + phone +
			"&enterpriseId=" + enterpriseId +
			"&tempId=" + tempId +
			"&tempAccount=" + tempAccount +
			"&tempCer=" + tempCer +
			"&msgType=" + msgType +
			"&tempArgs=" + tempArgs +
			"&Caller=" + Caller +
			"&timestamp=" + timestamp;
	}
}

