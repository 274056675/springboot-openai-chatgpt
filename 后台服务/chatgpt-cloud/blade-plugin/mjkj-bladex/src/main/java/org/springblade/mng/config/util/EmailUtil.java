package org.springblade.mng.config.util;


import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.mng.cgform.service.IMjkjBaseSqlService;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * @author jh
 * @version 1.0
 * @date 2022/5/30 18:54
 */
public class EmailUtil {

	private static IMjkjBaseSqlService sqlService;
//	private static String myEmailAccount = "18272687065@163.com";
//	private static String myEmailPassword = "BFFXODWSSFZPPGZU";
//	private static String smtp = "smtp.163.com";
//	private static String receiveMailAccount = "1027158641@qq.com";


	public static IMjkjBaseSqlService getSqlService() {
		if (sqlService == null)
			sqlService = SpringUtil.getBean(IMjkjBaseSqlService.class);
		return sqlService;
	}

	/**
	 * 亚马逊 发送邮箱
	 * @param receiveMailAccount 收件人
	 * @param emailContent       邮件内容
	 * @param theme              邮件主题
	 * @return
	 */
	public static Boolean sendYmx(String receiveMailAccount, String emailContent, String theme) {
		Transport transport =null;
		try {
			//发送人
			String FROM = "support@coinub.com";
			String FROMNAME = "Coinhouse";

			//账号
			String SMTP_USERNAME = "AKIASJK7EKGTXEKPKRMP";
			//密码
			String SMTP_PASSWORD = "BDe4rVMGWzqGGG9aK0OhcT838g/ROyfihHsoIpsLF7fK";

			//亚马逊主机
			String HOST = "email-smtp.ap-southeast-1.amazonaws.com";

			//端口
			int PORT = 587;


			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");


			Session session = Session.getDefaultInstance(props);


			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAccount));
			msg.setSubject(theme);
			msg.setContent(emailContent, "text/html;charset=UTF-8");

			transport = session.getTransport();
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if(Func.isNotEmpty(transport)){
				try{
					transport.close();
				}catch (Exception e){

				}
			}

		}
	}

	/**
	 * @param myEmailAccount
	 * @param myEmailPassword
	 * @param smtp
	 * @param receiveMailAccount 收件人
	 * @param emailContent       邮件内容
	 * @param theme              邮件主题
	 * @param fjrmc              发件人名称
	 * @return
	 */
	public static Boolean send(String myEmailAccount, String myEmailPassword, String host, String receiveMailAccount, String emailContent, String theme, String fjrmc) {
		try {
			// 1. 创建参数配置, 用于连接邮件服务器的参数配置
			Properties props = new Properties();
			// 使用的协议（JavaMail规范要求）
			props.put("mail.smtp.ssl.protocols", "TLSv1.2");

			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.setProperty("mail.smtp.port", "465");
			//props.put("mail.smtp.port", "25"); 官方不支持25端口

			// 发件人的邮箱的 SMTP 服务器地址
			props.setProperty("mail.smtp.host", host);
			// 需要请求认证
			props.setProperty("mail.smtp.auth", "true");
			// 2. 根据配置创建会话对象, 用于和邮件服务器交互
			Session session = Session.getInstance(props);
			// 设置为debug模式, 可以查看详细的发送 log
			session.setDebug(true);
			// 3. 创建一封邮件    session、发件人、收件人、邮件内容
			MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount, emailContent, theme, fjrmc);
			// 4. 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();
			// 5. 使用 邮箱账号 和 密码/授权码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			transport.connect(myEmailAccount, myEmailPassword);
			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());
			// 7. 关闭连接
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 创建邮件
	 *
	 * @param session
	 * @param myEmailAccount
	 * @param receiveMailAccount
	 * @param message
	 * @return
	 */
	private static MimeMessage createMimeMessage(Session session, String myEmailAccount, String receiveMailAccount, String message, String yjzt, String fjrmc) {
		try {
			// 1. 创建一封邮件
			MimeMessage mimeMessage = new MimeMessage(session);
			// 2. From: 发件人   emailUserName、昵称、编码格式
			mimeMessage.setFrom(new InternetAddress(myEmailAccount, fjrmc, "UTF-8"));
			// 3. To: 收件人（可以增加多个收件人）
			mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, receiveMailAccount, "UTF-8"));
			//多个收件人
//			String[] split = receiveMailAccount.split(",");
//			for(int i = 0; i < split.length; i++){
//				mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(split[i]));
//			}
			// 4. Subject: 邮件主题
			mimeMessage.setSubject(yjzt, "UTF-8");
			// 5. Content: 邮件正文（可以使用html标签）
			mimeMessage.setContent(message, "text/html;charset=UTF-8");
			// 6. 设置发件时间
			mimeMessage.setSentDate(new Date());
			// 7. 保存设置
			mimeMessage.saveChanges();
			return mimeMessage;
		} catch (Exception e) {
		}
		return null;
	}

}
