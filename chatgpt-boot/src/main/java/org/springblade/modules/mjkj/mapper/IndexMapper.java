/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.modules.mjkj.mapper;

import java.util.List;
import java.util.Map;

/**
 *首页相关
 */
public interface IndexMapper {

	//普通用户数
	Integer  getCommonUserCou();

	//群机器人用户数
	Integer getRobotUserCou();

	//付费用户数
	Integer getPayUserCou();

	//会员信息
	List<Map<String, Object>> grtMember();

	//今日新增会员
	Integer getNewMemberTodayCou();

	//今日新增付费会员
	Integer getNewPayMemberTodayCou();

	//今日活跃用户
	Integer getActiveUserTodayCou();

	//本年收入汇总
	Map<String,Object> getCurrentYearTotalIncomeCou();

	//本季度收入汇总
	Map<String,Object> getTotalQuarterlyRevenueCou();

	//本月收入汇总
	Map<String,Object> getTotalMonthlyIncomeCou();

	//本年充值会员数
	Integer getCurrentYearMemberCou();

	//本季度充值会员数
	Integer getQuarterMemberCou();

	//本月充值会员数
	Integer getMonthMemberCou();

	//总提问数
	Integer getTotalQuestions();
	//总画图数
	Integer getTotalNumberDrawings();
	//机器人提问数
	Integer getSwarmRobotsTotalQuestions();

	//今日问题数
	Integer getTodayQuestions();
	//今日画图数
	Integer getTodayNumberDrawings();
	//今日群提问数
	Integer getTodayGroupQuestions();
	//今日所收入佣金
	Map<String,Long> getTodayCommissionsEarned();
	//今日实收入
	Map<String,Long> getTodayRevenue();

	//总提现金额
	Map<String,Object> getTotalWithdrawals();
	//提现次数
	Integer getTotalQuantity();
	//总收入佣金
	Map<String,Object> getTotalCommissions();
	//会员数
	Integer getTotalNumberMembers();
	//总收入
	Map<String,Object> getGrossIncome();
	//总付费会员数
	Integer getNumberPaidMembers();

}
