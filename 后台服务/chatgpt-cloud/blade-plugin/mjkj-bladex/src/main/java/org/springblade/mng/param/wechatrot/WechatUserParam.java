package org.springblade.mng.param.wechatrot;

import lombok.Data;

@Data
public class WechatUserParam {
	private String wechatId;
	private String wechatName;
	private String wechatContent;
	private String wechatMessageId;
	private String wechatMessageType;//wechat=个人  wechatgroup=微信群
	private String wechatMessageSign;//验证 c6cfdf3ee1e3f14b33b7ba7207690252
	private String groupName;
}
