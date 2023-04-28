package org.springblade.mng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mng.model.WxUserInfoModel;
import org.springblade.mng.param.ChatLogShareMessageParam;
import org.springblade.mng.param.MoreFunParam;
import org.springblade.plugin.message.model.ChatGptMsgModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 前
 */
public interface IWebService {

	//获取用户详情
	WxUserInfoModel getWxUsrInfo();

	//获取openId
	String getOpenId();

	//获取聊天码
	String getChatCode();

	//获取微信id
	String getWxuserId();

	//获取问题可提问次数
	Integer getWuserQuestionCou(Long bladeUserId);

	//获取是否开启 提问消耗次数
	boolean getQuestionNumFlag();
	//更多好玩
	boolean getMoreFunQuestionNumFlag();
	//获取提问一次消耗多少次
	Integer getOneQuestionUseNum();

	//发送问题 不用发送
	List<ChatGptMsgModel> sendQuestion(String question,Long startMessageId, String modelType,boolean memberFlag);

	//获取历史聊天记录
	IPage<Map<String, Object>>  getMessageHistoryList(Long startNum,String modelType,IPage<Object> page);

	//获取我的最新消息
	List<Map<String, Object>>  getMessageLastList(Long startNum,String modelType);


	//获取消息次数
	Integer getMessageCou(Long bladeUserId);

	//获取等级列表
	List<Map<String,Object>> getLevelList();

	//获取等级名称
	String getLevelTitle(int cou);

	//获取参数设置
	String getCsszVal(String code,String defaultVal);

	//校验敏感词
	boolean checkSensitiveWord(String str);

	//获取手机号码
	String getPhoneNum(String code);

	//增加用户次数  【注册->1】【分享->2】【分享注册->3】【提问->4】【5=人工】【6=广告奖励】【7=签到】【8=会员奖励】【9=更多好玩】【10=口令福利】
	void  addWxuserQuestionNum(Long bladeUserId,String wxuserId,Integer serviceType,Integer num,String questionId,String remark);

	//减用户次数  【注册->1】【分享->2】【分享注册->3】【提问->4】【5=人工】【6=广告奖励】【7=签到】【8=会员奖励】【9=更多好玩】【10=口令福利】
	void  subWxuserQuestionNum(Long bladeUserId,String wxuserId,Integer serviceType,Integer num,String questionId,String remark);

	//获取新邀请码
	String getNewInviteCode();

	//根据邀请码获取用户id
	String getWxuseridByInvitecode(String invitecode);

	Map<String,Object> getWxuserMapByInvitecode(String invitecode);

	//生成邀请码
	String generateQrcode();

	//生成文件二维码
	String generateFileQrcode(String  fileCode);

	//获取用户自定义设置
	Map<String,Object> getWxUserSetting(String wxUserId);

	//发送更多好玩
	void sendMoreFun(String wxuserId,Long bladeUserId,boolean memberFlag,MoreFunParam param);

	//签到
	void sign(String wxuserId, Date date);

	//添加用户分享的消息
	void addShareLog(String wxuserId,ChatLogShareMessageParam param);
	//用户获取被分享的消息
	List<Map<String, Object>> getShareLog(String onlyId);
	/**
	 * 处理用户提现
	 * @param wxuserId
	 * @param amount 提现金额
	 * @param feeAmount 手续费
	 */
	void withdrawalHandle(String wxuserId, BigDecimal amount,BigDecimal feeAmount);

	//获取本微信用户id下所有的子用户(推广)
	List<Map<String, Object>> getChildUsers(String wxuserId);

	//获取历史聊天记录
	IPage<Map<String, Object>>  getSubCouList(String wxuserId,IPage<Object> page);

	//保存图片
	void saveDALLEImages(String id,String prompt,String size);

}
