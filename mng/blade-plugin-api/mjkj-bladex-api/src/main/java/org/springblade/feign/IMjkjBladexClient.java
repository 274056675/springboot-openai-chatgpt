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
	value = "open-chat"
)
public interface IMjkjBladexClient {
	String API_PREFIX = "/client";

	String ADD_QUESTION_NUM = API_PREFIX + "/add-question-num";//增加提问次数

	String SUB_QUESTION_NUM = API_PREFIX + "/sub-question-num";//减少提问次数

	String GET_CSSZ_VAL = API_PREFIX + "/get-cssz-val";//获取参数设置

	String GET_NEW_INVITE_CODE = API_PREFIX + "/get-new-invite-code";//获取邀请码

	String GET_WXUSERID_BY_INVITE_CODE = API_PREFIX + "/get-wxuserid-by-invite-code";//根据邀请码获取用户id

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

}
