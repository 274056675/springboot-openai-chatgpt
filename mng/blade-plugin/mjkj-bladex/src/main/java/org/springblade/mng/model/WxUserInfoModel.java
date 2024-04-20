package org.springblade.mng.model;

import lombok.Data;

import java.util.Date;

/**
 * 用户详情
 */
@Data
public class WxUserInfoModel {
	private String id;//微信用户id
	private Long bladeUserId;//用户id
	private String wxName;//名称
	private String wxName_Dim;//名称脱敏
	private String wxAvatar;//头像
	private String chatCode;//聊天号
	private String openId;

	private Integer messageCou;//消息次数，已用
	private String leveTitler;//等级

	private String viewModel;//消息显示模式

	private Date expireTime;//到期时间

	private String phone;//手机号码

	private boolean memberFlag;//是否是会议

	private Date lastChatTime;//最新聊天时间

	private Date exprotMinDate;//最新聊天时间

	private Integer questionCou;//问题次数 剩余可用

	private String questionCouStr;//问题次数 剩余可用

	private boolean buyFlag;//是否可以购买

	private String inviteCode;//邀请码

	private String posterUrl;//其他端海报地址

	private String posterWxUrl;//微信海报地址

	private Date stopSendTime;//禁言截至时间

	private Integer viewRewardAdvertCou;//今天观看视频总次数

	private String wxShareUrl;//微信专属连接

	private String isAgent;//是否代理商

	private String userCode;//用户好

	private String ShiniuPayOpen;//在十牛获取用户信息时使用

	private Integer rl_cou; //燃料次数

	private Integer rl_used_cou; //燃料已使用次数

	private String sign; //个性签名
}
