package org.springblade.mng.utils.ltsms;

import java.security.MessageDigest;

/**
 * 32位小写MD5加密
 */
public class MD532Utils {
	public static String set32md5(String tempCer,String timestamp) {
		String encryptStr = tempCer + timestamp;
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(encryptStr.getBytes());
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			encryptStr = hexValue.toString();
			System.out.println(encryptStr);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return encryptStr;
	}

}
