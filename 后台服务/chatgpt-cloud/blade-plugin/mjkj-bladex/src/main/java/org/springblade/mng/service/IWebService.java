package org.springblade.mng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.mng.model.ChatGptMsgModel;
import org.springblade.mng.model.WxUserInfoModel;
import org.springblade.mng.param.MoreFunParam;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IWebService {

	//删除用户
	void delWuserInfo(String wxuserId);
	//获取用户详情
	WxUserInfoModel getWxUsrInfo();

	WxUserInfoModel getWxUsrInfo(String type);


	//获取openId
	String getOpenId();

	//获取聊天码
	String getChatCode();

	//获取微信id
	String getWxuserId();

	//获取各类次数
	Integer getWuserQuestionCou(Long bladeUserId, String numType);


	//获取提问一次消耗多少次
	Integer getOneQuestionUseNum();

	//发送问题
	List<ChatGptMsgModel> sendQuestion(String question, Long startMessageId, Integer useNum, String textType, Long chatListIdL);


	//获取历史聊天记录
	IPage<Map<String, Object>>  getMessageHistoryList(Long startNum, String modelType, IPage<Object> page, String chatListId, String type);

	//获取我的最新消息
	List<Map<String, Object>>  getMessageLastList(Long startNum, String modelType, String type, String chatListId);


	//获取消息次数
	Integer getMessageCou(String wxuserId);

	//获取等级列表
	List<Map<String,Object>> getLevelList();

	//获取等级名称
	String getLevelTitle(int cou);

	//获取参数设置
	String getCsszVal(String code, String defaultVal);


	//增加用户次数  【注册->1】【分享->2】【分享注册->3】【提问->4】【5=人工】【6=广告奖励】【7=签到】【8=会员奖励】【9=更多好玩】【10=口令福利】
	void  addWxuserQuestionNum(Long bladeUserId, String wxuserId, Integer serviceType, Integer num, String questionId, String remark, String numType);

	//减用户次数  【注册->1】【分享->2】【分享注册->3】【提问->4】【5=人工】【6=广告奖励】【7=签到】【8=会员奖励】【9=更多好玩】【10=口令福利】
	void  subWxuserQuestionNum(Long bladeUserId, String wxuserId, Integer serviceType, Integer num, String questionId, String remark, String numType);

	//获取新邀请码
	String getNewInviteCode();

	//根据邀请码获取用户id
	String getWxuseridByInvitecode(String invitecode);

	Map<String,Object> getWxuserMapByInvitecode(String invitecode);

	Map<String,Object> getWxuseridByUUID(String uuid);

	//生成邀请码
	String generateQrcode();


	//获取用户自定义设置
	Map<String,Object> getWxUserSetting(String wxUserId);

	//获取热门消息
	IPage<Map<String, Object>>  getMessageHotList(IPage<Object> page);

	//发送更多好玩
	void sendMoreFun(String wxuserId, Long bladeUserId, Integer oneUseNum, MoreFunParam param);

	//签到
	void sign(String wxuserId, Date date);

	//获取历史聊天记录
	IPage<Map<String, Object>>  getSubCouList(String wxuserId, IPage<Object> page);

	//判断模型是否需要燃料
	Integer judgeModel(String modelName);

	//获取收藏列表
	IPage<Map<String,Object>>  getStoreMessage(IPage<Object> page);

	//获取聊天列表
	IPage<Map<String,Object>> getChatList(IPage<Object> page);

	//创建聊天列表
	void createNewChatList(Long id, String type, String name, String content, String fundataId, String funJson);

	//获取积分列表
	IPage<Map<String,Object>> getCreditList(IPage<Object> page);

	//获取加分列表
	IPage<Map<String,Object>> getAddCreditList(IPage<Object> page);

	//获取减分列表
	IPage<Map<String,Object>> getSubCreditList(IPage<Object> page);

	//收藏功能
	String chatStore(String messageId, String type);

	//获取被你邀请的用户
	IPage<Map<String,Object>> getInvitedUsers(IPage<Object> page);


	//获取fun历史记录
	IPage<Map<String,Object>> getFunHistory(IPage<Object> page, String funDataId);

}
