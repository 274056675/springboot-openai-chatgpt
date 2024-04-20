package org.springblade.feign;

import org.springblade.core.tool.api.R;
import org.springblade.entity.QuestionNumParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

/**
 * 水务相关
 */
@FeignClient(
	value = "mjkj-chat"
)
public interface IMjkjBladexClient {
	String API_PREFIX = "/client";

	String ADD_QUESTION_NUM = API_PREFIX + "/add-question-num";//增加提问次数

	String SUB_QUESTION_NUM = API_PREFIX + "/sub-question-num";//减少提问次数

	String GET_CSSZ_VAL = API_PREFIX + "/get-cssz-val";//获取参数设置

	String GET_SHINIU_CSSZ_VAL = API_PREFIX + "/get-shiniu-cssz-val";//获取十牛参数设置

	String GET_NEW_INVITE_CODE = API_PREFIX + "/get-new-invite-code";//获取邀请码

	String GET_WXUSERID_BY_INVITE_CODE = API_PREFIX + "/get-wxuserid-by-invite-code";//根据邀请码获取用户id

	String GET_SHINIU_WXUSERID_BY_INVITE_CODE = API_PREFIX + "/get-shiniu-uuid";//根据uuid获取用户id

	String ADD_RL_NUM = API_PREFIX + "/add-rl-num";
	//增加提问次数
	@PostMapping(ADD_QUESTION_NUM)
	R addQuestionNum(@RequestBody QuestionNumParam param);

	//减少提问次数
	@PostMapping(SUB_QUESTION_NUM)
	R subQuestionNum(@RequestBody QuestionNumParam param);

	//获取参数设置
	@GetMapping(GET_CSSZ_VAL)
	R<String> getCsszValue(@RequestParam("code") String code);


	//获取参数设置
	@GetMapping(GET_NEW_INVITE_CODE)
	R<String> getNewInviteCode();

	//根据邀请码获取用户id
	@GetMapping(GET_WXUSERID_BY_INVITE_CODE)
	R<Map<String,Object>> getWxuseridByInvitecode(@RequestParam("invitecode") String invitecode);

	//根据uid获取用户id
	@GetMapping(GET_SHINIU_WXUSERID_BY_INVITE_CODE)
	R<Map<String,Object>> getWxuseridByUUID(@RequestParam("uuid") String uuid);

	//注册时增加用户燃料
	@PostMapping(ADD_RL_NUM)
	R addRlNum(@RequestBody QuestionNumParam param);
}
