package org.springblade.mng.config.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;
import org.springblade.mng.common.utils.MjkjUtils;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * 邮箱模板工具类
 */
public class EmailTemplateUtils {

	public static String TYPE_PUBLIC="public";
	public static final String DEFAULT_LANG = "zh_cn";
	private static IMjkjBaseSqlService sqlService;

	private static IMjkjBaseSqlService getSqlService() {
		if (sqlService == null) sqlService = SpringUtil.getBean(IMjkjBaseSqlService.class);
		return sqlService;
	}

	/**
	 * 获取发送模板
	 * @param type 类型
	 * @param title 标题
	 * @param map 内容
	 * @param dym 钓鱼码
	 * @return
	 */
	public static String getTemplate(String lang,String type, String title, List<String> list,String dym){
		String html="";
		if(Func.equals(type,TYPE_PUBLIC)) {//公共  #{title} #{content}
			String fileName="emailTemplate.html";
			html = getHtml(fileName);
			html=html.replaceAll("#\\{title}",title);

			String content="";
			if(Func.isNotEmpty(dym)){
				content=getDymDiv(dym);
			}
			for (String div:list) {
				if(div.startsWith("<div")){
					content+=div;
				}else{
					content+=getDiv(div);
				}

			}
			html=html.replaceAll("#\\{content}",content);
		}

		html = html.replace("#{website}", getLanguage(lang,"mng_email_tx7")).replace("#{email}", getLanguage(lang,"mng_email_tx8"));

		return html;
	}


	/**
	 * 格式化验证码有效期
	 * @param yzm
	 * @return
	 */
	public static String fomatYzmYxq(String yzm){
		String div="<div class=\"minutes\" style=\"margin: 0; padding: 0; box-sizing: border-box; color: #666666; font-size: 14px; text-align: center; padding-bottom: 24px;\">\n" +
			"            "+yzm+"</div>";
		return div;
	}

	/**
	 * 格式化验证码
	 * @param yzm
	 * @return
	 */
	public static String fomatYzm(String yzm){
		String div="<div class=\"activation\" style=\"margin: 0; box-sizing: border-box; font-size: 36px; line-height: 1.5; text-align: center; color: #659aea; padding: 24px 0 24px 0;\">" +
			"            "+yzm+" </div>";
		return div;
	}

	/**
	 * 字体加粗
	 * @param str
	 * @return
	 */
	public static String fomatFontWeight(String str){
		String div="<span class=\"tb6\" style=\"margin: 0; padding: 0; font-size: 14px; box-sizing: border-box; font-weight: 600;\">"+str+"</span>";
		return div;
	}

	/**
	 * 获取防钓鱼码
	 * @param str
	 * @return
	 */
	private static String getDymDiv(String str){
		String div="<div class=\"pd-b\" style=\"margin: 0; padding: 0; font-size: 14px; box-sizing: border-box; padding-bottom: 24px;\"><span class=\"code\" style=\"margin: 0; box-sizing: border-box; min-width: 60px; padding: 0 10px; height: 32px; line-height: 32px; border-radius: 4px 0 0 4px; background-color: #375fad; display: inline-block; color: #fff; text-align: center; font-size: 12px;\">防钓鱼码</span><span class=\"codeNumber\" style=\"margin: 0; box-sizing: border-box; min-width: 108px; padding: 0 10px; height: 32px; background: #e0edff; border-radius: 0 4px 4px 0; display: inline-block; line-height: 32px; color: #375fad; text-align: center; font-size: 12px;\"> "+str+"</span></div>";
		return div;
	}

	private static String getDiv(String str){
		String div="<div class=\"pd-t\" style=\"margin: 0; padding: 0; font-size: 14px; box-sizing: border-box; padding-top: 14px;\">"+str+"</div>";
		return div;
	}



	private static String getHtml(String fileName){
		try{
				Resource resource = new ClassPathResource("template/"+fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(),"UTF-8"));
				StringBuffer sb = new StringBuffer();
				String str = "";
				while((str=br.readLine())!=null) {
					sb.append(str);
				}
				return sb.toString();
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


	public static String getLanguage(String code){
		try{
			//后期做缓存
			String header = WebUtil.getRequest().getHeader("mj-lang");
			String language=DEFAULT_LANG;
			if(Func.isNotEmpty(header)){
				language=header;
			}
			QueryWrapper<Object> wrapper=new QueryWrapper<>();
			wrapper.eq("code",code);
			Map<String, Object> dataMap = getSqlService().getDataOneByFieldParams("coin_language", wrapper);
			if(Func.isEmpty(dataMap)){
				return "";
			}
			String text = MjkjUtils.getMap2Str(dataMap, "text");
			Map<String, String> map = JsonUtil.parse(text, Map.class);
			if(Func.isEmpty(map)){
				return "";
			}
			return map.get(language);
		}catch (Exception e){
			//e.printStackTrace();
		}
		return "";
	}

	public static String getLanguage(String lang,String code){
		try{
			String header = WebUtil.getRequest().getHeader("mj-lang");
			if(Func.isNotEmpty(header)){
				lang=header;
			}
		}catch (Exception e){

		}
		try{
			if(Func.isEmpty(lang)){
				lang=DEFAULT_LANG;
			}
			//后期做缓存
			QueryWrapper<Object> wrapper=new QueryWrapper<>();
			wrapper.eq("code",code);
			Map<String, Object> dataMap = getSqlService().getDataOneByFieldParams("coin_language", wrapper);
			if(Func.isEmpty(dataMap)){
				return "";
			}
			String text = MjkjUtils.getMap2Str(dataMap, "text");
			Map<String, String> map = JsonUtil.parse(text, Map.class);
			if(Func.isEmpty(map)){
				return "";
			}
			return map.get(lang);
		}catch (Exception e){
			//e.printStackTrace();
		}
		return "";
	}
}
