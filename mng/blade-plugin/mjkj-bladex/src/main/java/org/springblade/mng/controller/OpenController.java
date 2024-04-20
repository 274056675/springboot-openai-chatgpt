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
package org.springblade.mng.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.mng.config.constant.ChatgptConfig;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;

import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.*;
import org.springblade.mng.model.AiModel;
import org.springblade.mng.model.WxUserInfoModel;

import org.springblade.mng.service.*;

import org.springblade.mng.utils.imagecode.SlidePuzzleUtil;
import org.springblade.mng.utils.imagecode.SliderPuzzleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("chat/open")
@Api(value = "公共开放接口", tags = "公共开放接口")
public class OpenController {

	@Autowired
	private IMjkjBaseSqlService baseSqlService;

	@Autowired
	private IWebService webService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ISmsService smsService;



	@ApiOperationSupport(order = 1)
	@GetMapping({"/test"})
	@ApiOperation(value = "测试", notes = "测试")
	public R test() {

		return R.data("测试成功");
	}





	@ApiOperationSupport(order = 2)
	@GetMapping({"/cssz/list"})
	@ApiOperation(value = "获取参数设置列表", notes = "获取参数设置列表")
	public R getCsszMapList() {
		List<Map<String, Object>> dataMapList = baseSqlService.getDataByTable("chat_system_cssz");
		return R.data(dataMapList);
	}


	@ApiOperationSupport(order = 10)
	@PostMapping({"/send/sms"})
	@ApiOperation(value = "发送短信验证码", notes = "发送短信验证码与滑块效验")
	public R sendSms(String phone, Integer movePosX,String random) {
		if (Func.isEmpty(phone)) {
			return R.fail("手机号码不允许为空");
		}
		if (phone.length() != 11) {
			return R.fail("手机号码不正确");
		}
		String code = "";
		if (!Func.equals(phone, "13800138000")) {
			String redisKey = "imagecode:" + phone;
			try {
				if (!redisUtil.hasKey(redisKey)) {
					return R.fail("验证过期，请重试");
				}
				if (Func.isEmpty(random)) {
					Integer posX = (Integer) redisUtil.get(redisKey);
					if ((Func.isEmpty(posX) || Func.isEmpty(movePosX))) {
						return R.fail("验证过期，请重试");
					}
					if (Math.abs(posX - movePosX) > 10) {  //偏差大于10
						return R.fail("验证不通过");
					}
				}else {
					String token = (String) redisUtil.get(redisKey);
					if (!Func.equals(random,token)){
						return R.fail("验证不通过校验失败");
					}
				}
			} finally {
				redisUtil.del(redisKey);
			}
			code = Func.random(6, RandomType.INT);
			if (Func.equals(ChatgptConfig.getDebug(), "true")) {
				code = "123456";
			}
		} else {
			code = "888888";
		}

		boolean flag = smsService.sendSms(phone, code);
		if (flag) {
			return R.data("成功");
		}
		return R.fail("失败");
	}

	@ApiOperation(value = "生成图片")
	@ApiOperationSupport(order = 12)
	@GetMapping(value = "/get/getImageCode")
	public R getImageCode(String phone,String type) throws Exception {
		if (Func.isEmpty(phone)) {
			return R.fail("请输入手机号码");
		}
		File file = new File(ChatgptConfig.getUploadUrl() + "/image.png");

		if (Func.equals(type,"pc")){
			String random = Func.random(32, RandomType.ALL);
			File file1 = new File(ChatgptConfig.getUploadUrl()+"/photo.png");
			FileInputStream fileInputStream1 = new FileInputStream(file);
			FileInputStream fileInputStream2 = new FileInputStream(file1);
			BufferedImage originalImage = ImageIO.read(fileInputStream1);
			BufferedImage originalImage1 = ImageIO.read(fileInputStream2);
			String image1 = SlidePuzzleUtil.getImageBASE64(originalImage);
			String image2 = SlidePuzzleUtil.getImageBASE64(originalImage1);
			String redisKey = "imagecode:" + phone;
			Map<String,Object> map = new HashMap<>();
			map.put("image1",image1);
			map.put("image2",image2);
			map.put("random",random);
			redisUtil.set(redisKey, random);
			return R.data(map);
		}
		SliderPuzzleInfo sliderPuzzleInfo = SlidePuzzleUtil.createImage(new FileInputStream(file));
		if (Func.isEmpty(sliderPuzzleInfo)) {
			return R.fail("图片验证码生成失败");
		}
		String redisKey = "imagecode:" + phone;
		redisUtil.set(redisKey, sliderPuzzleInfo.getPosX());

		sliderPuzzleInfo.setToken(phone);
		sliderPuzzleInfo.setBigImage(null);
		sliderPuzzleInfo.setSmallImage(null);
		return R.data(sliderPuzzleInfo);
	}

	@ApiOperationSupport(order = 14)
	@GetMapping({"/get/inviteCode"})
	@ApiOperation(value = "获取当前用户的邀请码", notes = "获取当前用户的邀请码")
	public R InviteCode() {
		WxUserInfoModel wxUsrInfo = webService.getWxUsrInfo();
		if (Func.isEmpty(wxUsrInfo)) {
			return null;
		}
		String inviteCode = wxUsrInfo.getInviteCode();
		return R.data(inviteCode);
	}


	@ApiOperationSupport(order = 15)
	@GetMapping({"/delete/message"})
	@ApiOperation(value = "删除聊天信息", notes = "删除聊天信息")
	public R DeleteMessage() {
		String wxUserId = webService.getWxuserId();
		if (Func.isEmpty(wxUserId)) {
			return R.fail("请先登录");
		}
		HashMap<String, Object> deleteMap = new HashMap<>();
		deleteMap.put("is_deleted", -1);
		baseSqlService.baseUpdateDataWhere("chat_log_message", deleteMap, "wxuser_id", wxUserId);

		return R.data("删除成功");
	}





	@ApiOperationSupport(order = 18)
	@GetMapping({"/tool/getAllList"})
	@ApiOperation(value = "获取所有工具（ok）", notes = "获取所有工具")
	public R getAllToolList() {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("view_status", 1);
		wrapper.orderByAsc("pid");
		wrapper.orderByAsc("fun_sort");
		List<Map<String, Object>> dataMapList = baseSqlService.getDataListByFieldParams("chat_gjgl_fun_data", wrapper);
		if (Func.isEmpty(dataMapList)) {
			return R.data(dataMapList);
		}

		//获取所有类型
		Map<String, Map<String, Object>> typeMaps = baseSqlService.getData2Map("chat_gjgl_type", "id", false);

		for (Map<String, Object> map : dataMapList) {
			String funTypeId = MjkjUtils.getMap2Str(map, "fun_type");//类型
			if (Func.isEmpty(funTypeId)) {
				continue;
			}
			if (!typeMaps.containsKey(funTypeId)) {//不存在
				continue;
			}

			Map<String, Object> typeMap = typeMaps.get(funTypeId);
			String funTypeTitle = MjkjUtils.getMap2Str(typeMap, "fun_type");

			map.put("typeId", funTypeId);//id
			map.put("type", funTypeTitle);//标题
		}

		return R.data(dataMapList);
	}

	@ApiOperationSupport(order = 19)
	@GetMapping({"/tool/gethotList"})
	@ApiOperation(value = "获取热门工具", notes = "获取热门工具")
	public R getHotTool() {
		//查询热门工具类型下面的所有工具
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("view_status", 1);
		wrapper.eq("fun_hot", 1);
		List<Map<String, Object>> hotList = baseSqlService.getDataListByFieldParams("chat_gjgl_fun_data", wrapper);
		return R.data(hotList);
	}

	@ApiOperationSupport(order = 20)
	@GetMapping({"/tool/getSearch"})
	@ApiOperation(value = "搜索功能", notes = "搜索功能")
	public R getSearch(String id, String funName, String time) {
		//所有的四级工具
		List<Map<String, Object>> dataMapList = new ArrayList<>();
		//id为4代表查询所有四级工具
		if (id.equals("4")) {
			QueryWrapper<Object> wrapper = new QueryWrapper<>();
			wrapper.eq("is_deleted", 0);
			wrapper.eq("haschildren", 1);//只查询具体的工具
			wrapper.eq("view_status", 1);
			if ("0".equals(time)) {
				wrapper.orderByAsc("create_time");//为0代表用户没有点击最新，按照创建时间升序
			} else {
				wrapper.orderByDesc("create_time");//按照时间降序
			}
			dataMapList = baseSqlService.getDataListByFieldParams("chat_gjgl_fun_data", wrapper);
		} else {
			//查找该二级分类下的三级分类
			QueryWrapper<Object> wrapper = new QueryWrapper<>();
			wrapper.eq("pid", id);
			wrapper.eq("is_deleted", 0);
			wrapper.eq("view_status", 1);
			List<Map<String, Object>>  threeMapList = baseSqlService.getDataListByFieldParams("chat_gjgl_fun_data", wrapper);
			//该三级分类下的四级工具
			for (Map<String, Object> map : threeMapList) {
				String idThree = MjkjUtils.getMap2Str(map, "id");
				QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("pid", idThree);
				queryWrapper.eq("is_deleted", 0);
				queryWrapper.eq("view_status", 1);
				List<Map<String, Object>> fourMapList=baseSqlService.getDataListByFieldParams("chat_gjgl_fun_data", queryWrapper);
				for (Map<String, Object> fourMap : fourMapList) {
					dataMapList.add(fourMap);
				}
			}
		}

		if (Func.isEmpty(funName)) {//没有搜索
			return R.data(dataMapList);
		}


		//funName不为空代表用户输入了搜索关键字
		//id为4则循环所有的四级工具，否则循环所属三级分类下的四级工具
		//搜索的模糊查询
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.like("fun_name", funName);
		wrapper.eq("is_deleted", 0);
		wrapper.eq("haschildren", 1);//只查询具体的工具
		wrapper.eq("view_status", 1);

		//在所有的四级工具中查询
		if ("4".equals(id)) {
			wrapper.isNotNull("fun_type");
		} else {
			List<String> idsList=new ArrayList<>();
			for (Map<String, Object> map : dataMapList) {
				String ids = MjkjUtils.getMap2Str(map, "id");
				idsList.add(ids);//该二级分类下所有的四级工具id
			}

			wrapper.in("id", idsList);
			if ("0".equals(time)) {
				wrapper.orderByAsc("create_time");
			} else {
				wrapper.orderByDesc("create_time");
			}
		}

		List<Map<String, Object>> fourDataMapList = baseSqlService.getDataListByFieldParams("chat_gjgl_fun_data", wrapper);
		return R.data(fourDataMapList);
	}


	@ApiOperationSupport(order = 1)
	@GetMapping({"/indexMenu"})
	@ApiOperation(value = "首页公共菜单（ok）", notes = "首页公共菜单（ok）")
	public R getIndexMenu() {
		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("view_status", 1);//已启用
		wrapper.eq("fun_menu", 1);//默认在首页展示
		List<Map<String, Object>> indexMenuList = baseSqlService.getDataListByFieldParams("chat_gjgl_fun_data", wrapper);
		String wxuserId="";
		try {
			//拿不到证明没登录，直接走默认
			wxuserId = webService.getWxuserId();
		}catch (Exception e){
			return R.data(indexMenuList);
		}

		//获取我的自定义菜单
		Map<String, Object> myMenuMap = baseSqlService.getDataOneByField("chat_index_menu_wxuser", "wxuser_id", wxuserId);
		if (Func.isEmpty(myMenuMap)) {//没有定制过，则返回公共
			return R.data(indexMenuList);
		}

		//获取我的自定义菜单
		String menuId = MjkjUtils.getMap2Str(myMenuMap, "menu_id");
		if(Func.isEmpty(menuId)){
			return R.data(indexMenuList);
		}
		List<String> menuIdList = JSONArray.parseArray(menuId, String.class);
		if(Func.isEmpty(menuIdList)){
			return R.data(indexMenuList);
		}

		List<Map<String,Object>> myMenuList = new ArrayList<>();
		for (String id : menuIdList) {
			QueryWrapper<Object> wrapperList = new QueryWrapper<>();
			wrapperList.eq("id", id);
			wrapperList.eq("view_status", 1);
			wrapperList.eq("is_deleted", 0);
			Map<String, Object> myMenu = baseSqlService.getDataOneByFieldParams("chat_gjgl_fun_data", wrapperList);
			if(Func.isNotEmpty(myMenu)){
				myMenuList.add(myMenu);
			}
		}
		return R.data(myMenuList);

	}

	@ApiOperationSupport(order = 9)
	@GetMapping({"/get/community/image"})
	@ApiOperation(value = "收集社区图片", notes = "收集社区图片")
	public R getCommunityImage(Integer current, Integer size, String type,String name) {

		QueryWrapper<Object> wrapper = MjkjUtils.getQueryWrapper();
		wrapper.eq("open_flag", "2");//已经发布
		if (Func.equals(type,"new")){
			wrapper.orderByDesc("create_time");
		}else {
			wrapper.orderByDesc("star");
			wrapper.orderByDesc("view_cou");
		}
		if (Func.isNotEmpty(name)){
			wrapper.like("image_title",name);
		}

		IPage<Object> page = MjkjUtils.getPage(current, size);
		IPage<Map<String, Object>> pages = baseSqlService.getDataIPageByFieldParams("chat_image_info", page, wrapper);
		List<Map<String, Object>> dataMapList = pages.getRecords();

		Long userId = AuthUtil.getUserId();
		if(Func.isNotEmpty(userId) && userId!=-1){//用户有登录
			String wxuserId = webService.getWxuserId();
			for (Map<String, Object> dataMap : dataMapList) {
				String imageInfoId = MjkjUtils.getMap2Str(dataMap, "id");
				QueryWrapper<Object> wrapperStar = MjkjUtils.getQueryWrapper();
				wrapperStar.eq("image_info_id",imageInfoId);
				wrapperStar.eq("wxuser_id",wxuserId);
				wrapperStar.select("id");
				List<Map<String, Object>> starMapList = baseSqlService.getDataListByFieldParams("chat_image_info_star", wrapperStar);
				boolean be=Func.isNotEmpty(starMapList);
				dataMap.put("be", be);
			}
		}else{
			for (Map<String, Object> dataMap : dataMapList) {
				dataMap.put("be", false);//没有点赞
			}
		}


		return R.data(pages);

	}





	@ApiOperationSupport(order = 22)
	@GetMapping({"/get/notice"})
	@ApiOperation(value = "消息通知", notes = "消息通知")
	public R getMessage(String id) {

		//id有值
		Map<String, Object> dialogueNotice=new HashMap<>();
		//id为all走默认查询
		List<Map<String, Object>> dialogueNoticeList=new ArrayList<>();

		QueryWrapper<Object> wrapper = new QueryWrapper<>();
		wrapper.eq("is_deleted", 0);
		wrapper.eq("view_status", 1);

		if(Func.equals("all",id)){
			//默认查询
			wrapper.select("id", "title");
			dialogueNoticeList = baseSqlService.getDataListByFieldParams("chat_dialogue_notice", wrapper);
			if(Func.isEmpty(dialogueNoticeList)){
				return R.data(false);
			}
			return R.data(dialogueNoticeList);


		}

		wrapper.eq("id", id);
		wrapper.select("id","title","detail","create_time");
		dialogueNotice = baseSqlService.getDataOneByFieldParams("chat_dialogue_notice", wrapper);
		if(Func.isEmpty(dialogueNotice)){
			return R.data(false);
		}
		return R.data(dialogueNotice);


	}

	@ApiOperationSupport(order = 23)
	@GetMapping({"/view/image"})
	@ApiOperation(value = "点击作品图片浏览，浏览+1 ok", notes = "点击作品图片浏览，浏览+1")
	public R viewImage(String tpId) {

		if (Func.isEmpty(tpId)) {
			return R.fail("参数不准为空");
		}
		QueryWrapper<Object> wrapper = MjkjUtils.getQueryWrapper();
		wrapper.eq("id", tpId);
		wrapper.select("id,view_cou");
		Map<String, Object> imageMap = baseSqlService.getDataOneByFieldParams("chat_image_info", wrapper);
		if (Func.isEmpty(imageMap)) {
			return R.fail("作品不存在");
		}

		Integer viewCou = MjkjUtils.getMap2Integer(imageMap, "view_cou");
		if (Func.isEmpty(viewCou) || viewCou < 0) {
			viewCou = 0;
		}

		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("view_cou", ++viewCou);
		baseSqlService.baseUpdateData("chat_image_info", updateMap, tpId);

		return R.data(viewCou);

	}

	@ApiOperationSupport(order = 24)
	@GetMapping({"/getAllModel"})
	@ApiOperation(value = "获取所有的模型", notes = "获取所有的模型")
	public R getAllModel(){
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.select("mx_lx","is_use_rl","use_num","show_name","model_type","image_size");
		queryWrapper.ne("model_status",0);
		List modellist = baseSqlService.getDataListByFieldParams("chat_model", queryWrapper);
		String jsonString = JSON.toJSONString(modellist);
		List<AiModel> models = JSON.parseArray(jsonString, AiModel.class);

		List<AiModel> chatModel = new ArrayList<>();
		List<AiModel> imageModel = new ArrayList();
		for (int i=0;i<models.size();i++){
			AiModel model = models.get(i);
			if (model.getModel_type()==0){
				chatModel.add(model);
			}else {
				imageModel.add(model);
			}

		}
		Map<String,Object>  modelMap = new HashMap<>();
		modelMap.put("chat",chatModel);
		modelMap.put("image",imageModel);
		return R.data(modelMap);
	}

	@ApiOperationSupport(order = 24)
	@GetMapping({"/get/Content"})
	@ApiOperation(value = "获取新手指南", notes = "获取新手指南")
	public R getContent(){
		Map<String, Object> chatContent = baseSqlService.getDataOneByField("chat_content", "type", "新手指南");
		String content = MjkjUtils.getMap2Str(chatContent, "content");
		return R.data(content);
	}

	@ApiOperationSupport(order = 25)
	@PostMapping("/get/notice")
	@ApiOperation(value = "获取全部公告列表",notes = "获取全部公告列表")
	public R getNotices(@RequestBody  Query query){
		IPage<Object> page = Condition.getPage(query);
		QueryWrapper<Object> qw = new QueryWrapper<>();
		qw.eq("is_deleted",0);
		qw.select("id,title,create_time,content_part");
		qw.orderByDesc("id");
		IPage<Map<String, Object>> chatNotice = baseSqlService.getDataIPageByFieldParams("chat_notice", page, qw);
 		return R.data(chatNotice);
	}

	@ApiOperationSupport(order = 26)
	@GetMapping("/get/noticeContent")
	@ApiOperation(value = "获取公告内容",notes = "获取公告内容")
	public R getNoticeContent(String id){
		Map<String, Object> chatNotice = baseSqlService.getTableById("chat_notice", id);

		//判断用户有没有登录
		Long userId = AuthUtil.getUserId();
		if(Func.isNotEmpty(userId) && userId!=-1){
			String wxuserId = webService.getWxuserId();
			QueryWrapper<Object> qw = new QueryWrapper<>();
			qw.eq("wxuser_id",wxuserId);
			qw.eq("notice_id",id);
			qw.eq("is_deleted",0);
			Map<String, Object> chatNoticeState = baseSqlService.getDataOneByFieldParams("chat_notice_state", qw);
			if (Func.isEmpty(chatNoticeState)){
				Map<String,Object> insertMap = new HashMap<>();
				insertMap.put("id",IdWorker.getIdStr());
				insertMap.put("notice_id",id);
				insertMap.put("wxuser_id",wxuserId);
				baseSqlService.baseInsertData("chat_notice_state",insertMap);
			}
		}
		return R.data(chatNotice);
	}

	@ApiOperationSupport(order =27 )
	@PostMapping("/get/noticeNocheck")
	@ApiOperation(value = "获取未查看的公告列表",notes = "获取未查看的公告列表")
	public R getNoticeNoCheck(@RequestBody Query query){

		//先获取已经查看公告的id
		QueryWrapper<Object> qw = new QueryWrapper<>();
		qw.eq("wxuser_id",webService.getWxuserId());
		qw.eq("is_deleted",0);
		qw.select("notice_id");
		List<Map<String, Object>> chatNoticeStates = baseSqlService.getDataListByFieldParams("chat_notice_state", qw);
		List<String> notices = new ArrayList<>();
		for (Map<String,Object> dataMap : chatNoticeStates){
			String noticeId = MjkjUtils.getMap2Str(dataMap, "notice_id");
			notices.add(noticeId);
		}

		//开始查询未查看的公告
		IPage<Object> page = Condition.getPage(query);
		QueryWrapper<Object> notice_qw = new QueryWrapper<>();
		notice_qw.orderByDesc("id");
		if(Func.isNotEmpty(notices)){
			notice_qw.notIn("id",notices);
		}
		notice_qw.eq("is_deleted",0);
		notice_qw.select("id,title,create_time,content_part");
		IPage<Map<String, Object>> chatNotice = baseSqlService.getDataIPageByFieldParams("chat_notice", page, notice_qw);
		return R.data(chatNotice);
	}

	@ApiOperationSupport(order = 28)
	@GetMapping("/get/allcheck")
	@ApiOperation(value = "全部已读",notes = "全部已读")
	public R allCheck(){
		//先获取已经查看公告的id
		String wxuserId = webService.getWxuserId();
		QueryWrapper<Object> qw = new QueryWrapper<>();
		qw.eq("wxuser_id",wxuserId);
		qw.eq("is_deleted",0);
		qw.select("notice_id");
		List<Map<String, Object>> chatNoticeStates = baseSqlService.getDataListByFieldParams("chat_notice_state", qw);
		List<String> notices = new ArrayList<>();
		for (Map<String,Object> dataMap : chatNoticeStates){
			String noticeId = MjkjUtils.getMap2Str(dataMap, "notice_id");
			notices.add(noticeId);
		}
		//开始查询未查看的公告id
		QueryWrapper<Object> notice_qw = new QueryWrapper<>();
		notice_qw.orderByDesc("id");
		if(Func.isNotEmpty(notices)){
			notice_qw.notIn("id",notices);
		}
		notice_qw.eq("is_deleted",0);
		notice_qw.select("id");
		List<Map<String, Object>> chatNotice = baseSqlService.getDataListByFieldParams("chat_notice", notice_qw);
		if (Func.isEmpty(chatNotice)){
			return R.success("全部已读执行成功");
		}
		for (Map<String,Object> dataMap : chatNotice){
			String noticeId = MjkjUtils.getMap2Str(dataMap, "id");
			Map<String,Object> insertMap = new HashMap<>();
			insertMap.put("id",IdWorker.getIdStr());
			insertMap.put("notice_id",noticeId);
			insertMap.put("wxuser_id",wxuserId);
			baseSqlService.baseInsertData("chat_notice_state",insertMap);
		}
		return R.success("全部已读执行成功");
	}
}
