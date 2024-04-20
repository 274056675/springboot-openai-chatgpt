package org.springblade.mng.utils.ltsms;



import org.springblade.mng.config.constant.ChatgptConfig;

import java.io.File;
import java.io.FileInputStream;


/**
 *生成二维码
 */
public class MakeerWeimaUtils {

	/**
	 * 生成二维码
	 * @param shareUrl
	 * @param inviteCode
	 * @return
	 * @throws Exception
	 */
	public static FileInputStream generateQrCode(String shareUrl,String inviteCode) throws Exception {

		//这里设置地址
		String encodeUrl = shareUrl+"?inviteCode="+inviteCode;

		// 生成的二维码的路径及名称
		String fileSaveUrl=ChatgptConfig.getUploadUrl()+"/";

		//生成的二维码图片名字
		String encode = QRCodeUtil.encode(encodeUrl, ChatgptConfig.getLogoUrl(), fileSaveUrl, true);

		File file=new File(fileSaveUrl+encode);
		FileInputStream inputStream = new FileInputStream(file);
		return inputStream;
	}

	/**
	 * 生成二维码
	 * @param shareUrl
	 * @param inviteCode
	 * @return
	 * @throws Exception
	 */
	public static FileInputStream generateQrCodeImage(String shareUrl,String inviteCode,String imageId) throws Exception {

		//这里设置地址
		String encodeUrl = shareUrl+"?inviteCode="+inviteCode+"&imageId="+imageId;

		// 生成的二维码的路径及名称
		String fileSaveUrl=ChatgptConfig.getUploadUrl()+"/";

		//生成的二维码图片名字
		String encode = QRCodeUtil.encode(encodeUrl, ChatgptConfig.getLogoUrl(), fileSaveUrl, true);

		File file=new File(fileSaveUrl+encode);
		FileInputStream inputStream = new FileInputStream(file);
		return inputStream;
	}

}
