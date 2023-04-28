
package org.springblade.auth.granter;

import org.springblade.auth.constant.AuthConstant;
import org.springblade.auth.service.BladeUserDetails;
import org.springblade.auth.utils.TokenUtil;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.social.props.SocialProperties;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.system.user.entity.*;
import org.springblade.system.user.feign.IMjkjUserClient;
import org.springblade.system.user.feign.IUserClient;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信小程序登录
 */
public class WxMiniTokenGranter extends AbstractTokenGranter {
	private static final String GRANT_TYPE = "wxmini";
	private static final Integer AUTH_SUCCESS_CODE = 2000;

	private final IUserClient userClient;

	private IMjkjUserClient mjkjUserClient;

	private final SocialProperties socialProperties;

	protected WxMiniTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, IUserClient userClient, IMjkjUserClient mjkjUserClient, SocialProperties socialProperties) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userClient = userClient;
		this.mjkjUserClient =mjkjUserClient;
		this.socialProperties = socialProperties;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		// 请求头租户信息
		HttpServletRequest request = WebUtil.getRequest();

		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		String wxCode = parameters.get("wxCode");//微信-code
		String wxName = parameters.get("wxName");//微信-wxName
		String wxAvatar = parameters.get("wxAvatar");//微信-code
		String inviteCode = parameters.get("invite_code");//邀请码
		String loginType = parameters.get("login_type");//登录类型 wx=微信小程序   phone=手机号码登录 wxopen_qrcode=微信开放平台
		String phone = parameters.get("phone");//手机号码
		String phoneCode = parameters.get("phoneCode");//手机-code

		String uuid = parameters.get("uuid");
		if(Func.isEmpty(loginType)){
			loginType="wx";
		}
		//登录人id
		Long userId = AuthUtil.getUserId();
		R<UserInfo> userInfoR =null;
		if(Func.isNotEmpty(userId) && userId!=-1){//当前人是登录状态
			String userAccount = AuthUtil.getUserAccount();
			String tenantId = AuthUtil.getTenantId();
			userInfoR = userClient.userInfo(userAccount, tenantId);
		}
		if(Func.isEmpty(userInfoR)){//登录-------------
			WxUserParam wxUserParam=new WxUserParam();
			wxUserParam.setCode(wxCode);
			wxUserParam.setWxName(wxName);
			wxUserParam.setWxAvatar(wxAvatar);
			wxUserParam.setInviteCode(inviteCode);
			wxUserParam.setPhone(phone);
			if(Func.equals(loginType,"wx")){//微信小程序登录  不用
				userInfoR = mjkjUserClient.wxMiniLogin(wxUserParam);
			}else if(Func.equals(loginType,"phone")){//手机号码登录
				SmsCodeParam codeParam=new SmsCodeParam();
				codeParam.setPhone(phone);
				codeParam.setCode(phoneCode);
				R<Boolean> checkPhoneR = mjkjUserClient.checkPhone(codeParam);
				if(Func.isEmpty(checkPhoneR)){
					throw new InvalidGrantException("验证码不正确");
				}
				Boolean data = checkPhoneR.getData();
				if(!data){
					throw new InvalidGrantException("验证码不正确");
				}
				wxUserParam=new WxUserParam();
				wxUserParam.setCode(phoneCode);
				wxUserParam.setPhone(phone);
				wxUserParam.setInviteCode(inviteCode);
				userInfoR = mjkjUserClient.phoneLogin(wxUserParam);
			}else if(Func.equals(loginType,"wxopen_qrcode")){//微信开放平台登录
				WxOpenParam wxOpenParam=new WxOpenParam();
				wxOpenParam.setType("login");//注册
				wxOpenParam.setUuid(uuid);
				userInfoR = mjkjUserClient.wxOpen(wxOpenParam);
			}else{
				throw new InvalidGrantException("登录类型有误");
			}
		}



		// 远程调用，获取认证信息
		BladeUserDetails bladeUserDetails;
		if (userInfoR.isSuccess()) {
			User user = userInfoR.getData().getUser();
			Kv detail = userInfoR.getData().getDetail();
			if (user == null) {
				throw new InvalidGrantException("用户不存在");
			}

			if(Func.isNotEmpty(userInfoR.getData().getErrorMsg())){
				throw new UserDeniedAuthorizationException(userInfoR.getData().getErrorMsg());
			}

			bladeUserDetails = new BladeUserDetails(user.getId(),
				user.getTenantId(), userInfoR.getData().getOauthId(), user.getName(), user.getRealName(), user.getDeptId(), user.getPostId(), user.getRoleId(), Func.join(userInfoR.getData().getRoles()), Func.toStr(user.getAvatar(), TokenUtil.DEFAULT_AVATAR),
				user.getAccount(), AuthConstant.ENCRYPT + user.getPassword(), detail, true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList(Func.join(userInfoR.getData().getRoles())));
		} else {
			throw new InvalidGrantException("登录授失败");
		}


		// 组装认证数据，关闭密码校验
		Authentication userAuth = new UsernamePasswordAuthenticationToken(bladeUserDetails, null, bladeUserDetails.getAuthorities());
		((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);

		// 返回 OAuth2Authentication
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

}
