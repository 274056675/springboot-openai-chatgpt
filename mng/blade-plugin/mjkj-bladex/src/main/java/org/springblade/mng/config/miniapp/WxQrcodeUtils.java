package org.springblade.mng.config.miniapp;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 生成带参小程序二维码(输出到本地)
 *
 */
@Slf4j
public class WxQrcodeUtils {

	public static Map<String, Object> getMiniqrQrToLocal(String inviteCode, String accessToken,String fileUrl) {
		Map<String, Object> retMap = null;
		try {
			URL url = new URL("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");// 提交模式
			httpURLConnection.setRequestProperty("Content-Type", "application/x-javascript; charset=UTF-8");
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			JSONObject paramJson = new JSONObject();
			// 扫码进入的小程序页面路径，最大长度 128 字节，不能为空；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"，即可在 wx.getLaunchOptionsSync 接口中的 query 参数获取到 {foo:"bar"}
			paramJson.put("path", "/pages/index/index?inviteCode=" + inviteCode);//传入机器编码
			paramJson.put("width", 430);
			printWriter.write(paramJson.toString());
			// flush输出流的缓冲
			printWriter.flush();
			//开始获取数据
			BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
			OutputStream os = new FileOutputStream(new File(fileUrl));//本机位置
			int len;
			byte[] arr = new byte[1024];
			while ((len = bis.read(arr)) != -1) {
				os.write(arr, 0, len);
				os.flush();
			}
			os.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return retMap;
	}
}
