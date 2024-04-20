package org.springblade.mng.feign;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.entity.QuestionNumParam;
import org.springblade.feign.IMjkjBladexClient;
import org.springblade.mng.service.IWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@ApiIgnore
@RestController
@AllArgsConstructor
public class BladexClient implements IMjkjBladexClient {

	@Autowired
	private IWebService webService;


	//增加提问次数
	@Override
	public R addQuestionNum(QuestionNumParam param){
		webService.addWxuserQuestionNum(param.getBladeUsrId(),param.getWxuserId(),param.getServiceType(),param.getNum(),null,"注册赠送积分","question");
		return R.data("成功");
	}

	//

	//减少提问次数
	@Override
	public R subQuestionNum(QuestionNumParam param){
		webService.subWxuserQuestionNum(param.getBladeUsrId(),param.getWxuserId(),param.getServiceType(),param.getNum(),null,null,"question");
		return R.data("成功");
	}

	//获取参数设置
	@Override
	public R<String> getCsszValue(String code){
		String val = webService.getCsszVal(code, null);
		return R.data(val);
	}

	//获取参数设置
	@Override
	public R<String> getNewInviteCode(){
		String newInviteCode = webService.getNewInviteCode();
		return R.data(newInviteCode);
	}

	//根据邀请码获取用户id
	@Override
	public R<Map<String,Object>> getWxuseridByInvitecode(String invitecode){
		Map<String, Object> dataMap = webService.getWxuserMapByInvitecode(invitecode);
		return R.data(dataMap);
	}

	//根据邀请码获取用户id
	@Override
	public R<Map<String,Object>> getWxuseridByUUID(String uuid){
		Map<String, Object> dataMap = webService.getWxuseridByUUID(uuid);
		return R.data(dataMap);
	}

	@Override
	public R addRlNum(QuestionNumParam param) {
		webService.addWxuserQuestionNum(param.getBladeUsrId(),param.getWxuserId(),param.getServiceType(),param.getNum(),null,"注册赠送燃料","rl");
		return R.data("成功");
	}

}
