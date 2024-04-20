package org.springblade.mng.cgform.model.email;

import lombok.Data;

import java.util.List;

@Data
public class SendEmailModel {
	private String sjr;//接收人
	private String nr;//内容
	private String yxzt;//主题
	private String fsr;//发送人

}
